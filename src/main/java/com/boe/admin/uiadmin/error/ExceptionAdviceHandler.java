package com.boe.admin.uiadmin.error;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.boe.admin.uiadmin.common.Result;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class ExceptionAdviceHandler {

	@ExceptionHandler(InternalAuthenticationServiceException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public Result<Object> InternalAuthenticationServiceException(HttpServletRequest request, InternalAuthenticationServiceException e) {
		log.error("###################>>>>>>>: InternalAuthenticationServiceException -- {}", ExceptionUtils.getMessage(e));

		return Result.of(null, "用户名错误", 401);
	}
	@ExceptionHandler(BadCredentialsException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public Result<Object> BadCredentialsException(HttpServletRequest request, BadCredentialsException e) {
		log.error("###################>>>>>>>: BadCredentialsException -- {}", ExceptionUtils.getMessage(e));
		
		return Result.of(null, "密码错误", 401);
	}
	@ExceptionHandler(AccessDeniedException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public Result<Object> AccessDeniedException(HttpServletRequest request, AccessDeniedException e) {
		log.error("###################>>>>>>>: AccessDeniedException -- {}", ExceptionUtils.getMessage(e));
		
		return Result.of(null, "没有访问权限", 401);
	}
	
	

	// 捕捉其他所有异常
	@ExceptionHandler(Throwable.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public Result<Object> throwable(HttpServletRequest request, Throwable e) {
		log.error("###################>>>>>>>: Throwable -- {}", ExceptionUtils.getMessage(e));
		Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");

		return Result.of(null, e.getMessage(), statusCode == null ? 500 : statusCode);
	}

}
