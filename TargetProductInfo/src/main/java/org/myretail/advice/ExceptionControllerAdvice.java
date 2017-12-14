package org.myretail.advice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.myretail.exception.InputValidationException;
import org.myretail.exception.TargetPricingException;
import org.myretail.model.response.ProductInfoResponse;
import org.myretail.model.response.ResponseHeader;

/**
 * 
 * @author siju
 *
 */
@ControllerAdvice
public class ExceptionControllerAdvice {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * Custom exception handler
	 * @param ex
	 * @return
	 */
	 @ExceptionHandler({TargetPricingException.class,Exception.class})
	    public ResponseEntity<ProductInfoResponse> exceptionHandler(RuntimeException ex) {
	    	logger.error("Exception calling Pricing application :" +ex.getMessage());
	    	ProductInfoResponse response = createErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR.toString());
	    	return new ResponseEntity<ProductInfoResponse>(response, HttpStatus.OK);
	    }


	 /**
	  * Handles validation errors
	  * @param ex
	  * @return
	  */
	 @ExceptionHandler(InputValidationException.class)
	    public ResponseEntity<ProductInfoResponse> exceptionHandlerValidation(RuntimeException ex) {
	    	logger.error("Exception calling Pricing application :" +ex.getMessage());
	    	ProductInfoResponse response = createErrorResponse(ex, HttpStatus.BAD_REQUEST.toString());
	    	return new ResponseEntity<ProductInfoResponse>(response, HttpStatus.OK);
	    }

	private ProductInfoResponse createErrorResponse(RuntimeException ex, String tst) {
		ProductInfoResponse response = new ProductInfoResponse();
		ResponseHeader header = new ResponseHeader();
		header.setErrorCode(tst);
		header.setErrorMessage(ex.getMessage());
		response.setHeaderResponse(header);
		return response;
	}
}
