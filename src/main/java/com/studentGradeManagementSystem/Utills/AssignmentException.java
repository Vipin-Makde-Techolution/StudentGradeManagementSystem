package com.studentGradeManagementSystem.Utills;

import org.springframework.http.HttpStatus;

public class AssignmentException extends RuntimeException {

	private Throwable exception;
	private String errorMessage;
	private HttpStatus status;

	public Throwable getExcep() {
		return exception;
	}

	public void setExcep(Throwable exception) {
		this.exception = exception;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public AssignmentException(Throwable exception, String errorMessage) {
		super();
		this.exception = exception;
		this.errorMessage = errorMessage;
	}

	public AssignmentException(Throwable exception, String errorMessage, HttpStatus status) {
		super();
		this.exception = exception;
		this.errorMessage = errorMessage;
		this.status = status;
	}

	public AssignmentException(String errorMessage, HttpStatus status) {
		super();
		this.errorMessage = errorMessage;
		this.status = status;
	}


	public AssignmentException() {
		super();
	}

	public AssignmentException(String message) {
		super(message);
	}

}
