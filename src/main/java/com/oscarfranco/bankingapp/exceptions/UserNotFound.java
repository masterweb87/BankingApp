package com.oscarfranco.bankingapp.exceptions;

public class UserNotFound extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public UserNotFound(String message) {
		super(message);
	}
}
