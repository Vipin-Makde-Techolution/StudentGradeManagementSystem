package com.studentGradeManagementSystem.Utills;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ApplicationHelper {

	public static ResponseEntity<Error> errorResponseBuilder(HttpStatus statusCode, String errorMessage) {
		return new ResponseEntity<Error>(new Error(errorMessage), statusCode);
	}
}
