package com.miller.osWorks.domain.exception;

public class NotFoundEntityException extends BussinessException{

	private static final long serialVersionUID = 1L;

	public NotFoundEntityException(String message) {
		super(message);
	}
}
