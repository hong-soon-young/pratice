package com.example.practice.api.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.practice.api.dto.CommonResponseModel;
import com.example.practice.api.exception.CustomException;

@RestControllerAdvice
public class RestControllerAdviceHandler {
	@ExceptionHandler(value = {CustomException.class})
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	public CommonResponseModel<?> handle(Exception e) {
		String statusCode = String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value());
		return doHandle(e, statusCode);
	}
	
	private CommonResponseModel<?> doHandle(Exception e, String statusCode) {
		CommonResponseModel<?> commonResponseModel = new CommonResponseModel<>();
		commonResponseModel.setReturnCode(statusCode);
		commonResponseModel.setMessage(e.getMessage());
		
		return commonResponseModel;
	}
	
}
