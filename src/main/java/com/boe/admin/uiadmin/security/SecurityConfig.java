package com.boe.admin.uiadmin.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.DigestUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @EnableWebSecurity注解以及WebSecurityConfigurerAdapter一起配合提供基于web的security。 自定义类
 * 继承了WebSecurityConfigurerAdapter来重写了一些方法来指定一些特定的Web安全设置。
 *
 */
@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private MyUserDetailsService userService;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

		// 校验用户
		auth.userDetailsService(userService).passwordEncoder(new PasswordEncoder() {
			// 对密码进行加密
			@Override
			public String encode(CharSequence charSequence) {
				log.info("对密码进行加密: {}", charSequence.toString());
				return DigestUtils.md5DigestAsHex(charSequence.toString().getBytes());
			}

			// 对密码进行判断匹配
			@Override
			public boolean matches(CharSequence charSequence, String s) {
				String encode = DigestUtils.md5DigestAsHex(charSequence.toString().getBytes());
				boolean res = s.equals(encode);
				return res;
			}
		});

	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable()
				// 因为使用JWT，所以不需要HttpSession
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
				// OPTIONS请求全部放行
				.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
				// 登录接口放行
				.antMatchers("/auth/login").permitAll()
				// 其他接口全部验证
				.anyRequest().authenticated();

		// 使用自定义的 Token过滤器 验证请求的Token是否合法
		http.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
		http.headers().cacheControl();

	}

	@Bean
	public JwtTokenFilter authenticationTokenFilterBean() throws Exception {
		return new JwtTokenFilter();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

}
