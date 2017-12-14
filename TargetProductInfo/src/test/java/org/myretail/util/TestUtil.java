package org.myretail.util;

import java.util.Date;

import org.myretail.model.Product;
import org.myretail.model.response.ProductInfoResponse;
import org.myretail.model.response.ResponseHeader;

public class TestUtil {

	
	public static ProductInfoResponse createMockResponseObject(Date date,int productId,String currency) {
		ProductInfoResponse mockResponse = new ProductInfoResponse();
		    Product product = createProduct(date, productId, currency);
		    ResponseHeader header = new ResponseHeader();
		    header.setErrorCode("200");
		    header.setErrorMessage("SUCCESS");
		    mockResponse.setProduct(product);
		    mockResponse.setHeaderResponse(header);
		return mockResponse;
	}

	public static Product createProduct(Date date, int productId, String currency) {
		Product product = new Product();
		product.setCurrencyCode(currency);
		product.setCurrentPrice(20.20);
		product.setProductName("Test");
		product.setUpdateTs(date);
		product.setProductId(productId);
		product.setSourceTs(date);
		return product;
	}
	
}
