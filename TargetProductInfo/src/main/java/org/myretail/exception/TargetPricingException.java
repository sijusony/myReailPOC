package org.myretail.exception;

@SuppressWarnings("serial")
public class TargetPricingException extends RuntimeException {

	public TargetPricingException(String message) {
	        super(message);
	    }

	    public TargetPricingException(String message, Throwable cause) {
	        super(message, cause);
	    }
	    
	    public TargetPricingException(Throwable cause) {
	        super( cause);
	    }

}
