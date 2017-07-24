package com.coupons.exceptions;

@SuppressWarnings("serial")
public class AllExceptions extends Exception 
{

	public AllExceptions() {
		super();
	}

	public AllExceptions(String msg, Throwable cause) {
		super(msg, cause);
	}

	public AllExceptions(String message) {
		super(message);
	}
	
}
