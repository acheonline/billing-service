package ru.achernayvskiy0n.billingservice.persistence;

public class UserAccountInfoRepositoryException extends Exception {

	public UserAccountInfoRepositoryException(Throwable cause) {
		super(cause.getMessage(), cause);
	}

	public UserAccountInfoRepositoryException(String message) {
		super(message);
	}

}