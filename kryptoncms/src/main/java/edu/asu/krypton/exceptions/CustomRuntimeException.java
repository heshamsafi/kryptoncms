package edu.asu.krypton.exceptions;



/**
 * @author hesham
 * 
 * throwing a normal java.lang.RuntimeException gets caught by the modules of the framework
 * which was a major bug source so i created this class to act exactly as a java.lang.RuntimeException
 * but not get Caught by the framework instead of the intended application specific catch clause
 * 
 * N.B .. it extends java.lang.Exception and not java.lang.RuntimeException because if it extended 
 * java.lang.RuntimeException it would get caught in the framework
 */
public class CustomRuntimeException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public CustomRuntimeException() {}

	public CustomRuntimeException(String message) {
		super(message);
	}

	public CustomRuntimeException(Throwable cause) {
		super(cause);
	}

	public CustomRuntimeException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

}
