package org.myretail.util;


import org.springframework.http.HttpStatus;
import org.apache.commons.lang3.StringUtils;
import org.myretail.exception.InputValidationException;
import org.myretail.model.Product;

public class ProductUtil {

	public static void validateProductRequest(Product product) {

		if(product.getProductId() == 0) {
			throw new InputValidationException("Product ID is mandatory parameter",HttpStatus.BAD_REQUEST.value());
		}
		if(StringUtils.isEmpty(product.getCurrencyCode()) || product.getCurrencyCode().length()>3){
			throw new InputValidationException("Currency Code cannot be blank/Greater than 3 characters",HttpStatus.BAD_REQUEST.value());
		}


	}
}
