package com.assignment.transferservice.exception;

/**
 * RepositoryException
 */
public class RepositoryException extends Exception {

	private static final long serialVersionUID = 1L;

	public RepositoryException(String message) {
		super(message);
	}

	public RepositoryException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
