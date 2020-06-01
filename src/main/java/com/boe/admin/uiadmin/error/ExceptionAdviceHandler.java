package com.boe.admin.uiadmin.error;

import com.boe.admin.uiadmin.common.Result;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

@RestControllerAdvice
@Slf4j
public class ExceptionAdviceHandler {
	
	
	@ExceptionHandler(value = {NoHandlerFoundException.class})
	@ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Result<Object> noHandlerFoundException(HttpServletRequest req, Exception e) {
		log.error("##################### >>>>>>>>> : NoHandlerFoundException -- {}", ExceptionUtils.getMessage(e));
		return Result.of(null, "接口不存在", 404);
    }

	@ExceptionHandler(InternalAuthenticationServiceException.class)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Result<Object> InternalAuthenticationServiceException(HttpServletRequest request, InternalAuthenticationServiceException e) {
		log.error("###################>>>>>>>: InternalAuthenticationServiceException -- {}", ExceptionUtils.getMessage(e));

		return Result.of(null, "用户名错误", 401);
	}
	@ExceptionHandler(BadCredentialsException.class)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Result<Object> BadCredentialsException(HttpServletRequest request, BadCredentialsException e) {
		log.error("###################>>>>>>>: BadCredentialsException -- {}", ExceptionUtils.getMessage(e));
		
		return Result.of(null, "密码错误", 401);
	}
	@ExceptionHandler(AccessDeniedException.class)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Result<Object> AccessDeniedException(HttpServletRequest request, AccessDeniedException e) {
		log.error("###################>>>>>>>: AccessDeniedException -- {}", ExceptionUtils.getMessage(e));
		
		return Result.of(null, "没有访问权限", 401);
	}
	
	
	
	
	@ExceptionHandler(ExpiredJwtException.class)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Result<Object> ExpiredJwtException(HttpServletRequest request, ExpiredJwtException e) {
		log.error("###################>>>>>>>: ExpiredJwtException -- {}", ExceptionUtils.getMessage(e));
		
		return Result.of(null, "token过期", 401);
	}
	
	
	
	

    /**
    * 校验数据的处理
    */
   @ExceptionHandler(value = MethodArgumentNotValidException.class)
   @ResponseStatus(HttpStatus.OK)
   @ResponseBody
   public Object methodArgumentNotValidException(MethodArgumentNotValidException e) {
       log.info("{}", ExceptionUtils.getStackTrace(e));
       String msg = e.getBindingResult().getFieldError().getDefaultMessage();
       return Result.of(null, "请求参数有误: " + msg, 400);
   }
   
   /**
    * 缺失请求参数
    */
   @ExceptionHandler(value = MissingServletRequestParameterException.class)
   @ResponseStatus(HttpStatus.OK)
   @ResponseBody
   public Object missingServletRequestParameterException(MissingServletRequestParameterException e) {
       
	   return Result.of(null, "请求参数有误", 400);
   }
   /**
    * 参数校验错误
    */
   @ExceptionHandler(value = ConstraintViolationException.class)
   @ResponseStatus(HttpStatus.OK)
   @ResponseBody
   public Object constraintViolationException(ConstraintViolationException e) {
       
       Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
       for (ConstraintViolation<?> item : violations) {
           String message = ((PathImpl) item.getPropertyPath()).getLeafNode().getName()+ ":" + item.getMessage();
           return Result.of(null, "请求参数有误: " + message, 400);
       }
 
       
       return Result.of(null, "请求参数有误: " + e.getMessage(), 400);
   }
	
	

	// 捕捉其他所有异常
	@ExceptionHandler(Throwable.class)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Result<Object> throwable(HttpServletRequest request, Throwable e) {
		log.error(ExceptionUtils.getMessage(e), e);
		Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");

		return Result.of(null, e.getMessage(), statusCode == null ? 500 : statusCode);
	}

}
