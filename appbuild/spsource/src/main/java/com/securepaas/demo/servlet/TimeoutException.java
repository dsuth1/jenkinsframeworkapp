package com.securepaas.demo.servlet;

public class TimeoutException extends Exception {
	private static final long serialVersionUID = -7382656825857280447L;
	public TimeoutException()
	{
		super("Failed to retrieve connection due to a timeout.");
	}
	public TimeoutException(String message)
	{
		super(message);
	}
	public TimeoutException(String message, Throwable cause)
	{
		super(message,cause);
	}
    public TimeoutException(Throwable cause) 
    {
    	super(cause);
    }
}
