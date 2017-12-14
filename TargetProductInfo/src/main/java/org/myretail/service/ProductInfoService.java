package org.myretail.service;


import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.myretail.util.ProductUtil;
import org.myretail.dao.ProductPriceInfoDao;
import org.myretail.handler.ProductInfoHandler;
import org.myretail.model.Product;
import org.myretail.model.response.ProductInfoResponse;
import org.myretail.model.response.ResponseHeader;
import org.myretail.util.MessageConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.myretail.exception.TargetPricingException;

/**
 * 
 * @author siju
 * Service method for Product Information
 */
@Service
public class ProductInfoService {

	

	private static final String PRICING_INFORMATION_NOT_AVAILABLE_FOR_THE_PRODUCT_PRODUCT_ID = "Pricing information not available for the product, productID: ";

	private static final String CALLING_PRODUCT_INFO_SERVICE_GET_PRODUCT_ID = "calling ProductInfoService.getProduct, id= ";

	private static final String EXCEPTION_IN_PRODUCT_INFO_SERVICE_DELETE_PRODUCT_ID = "Exception in ProductInfoService.deleteProduct, id= ";

	private static final String CALLING_PRODUCT_INFO_SERVICE_UPDATE_PRODUCT_PRODUCT = "calling ProductInfoService.updateProduct, Product= ";

	private static final String NO_PRODUCT_INFO_AVAIALBE_IN_REDSKY_ID = "No Product Info avaialbe in Redsky, id= ";

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	ProductPriceInfoDao productInfoDao; 

	@Autowired
	ProductInfoHandler productHandler;
	
	/**
	 * Service to get Product for the given id
	 * combines the pricing information from cassandra with product name from redsky service
	 * @param id
	 * @return
	 */
	public ProductInfoResponse getProduct(int id){
		
		ProductInfoResponse response = new ProductInfoResponse();
    	try {
    			logger.info(CALLING_PRODUCT_INFO_SERVICE_GET_PRODUCT_ID+id );
    			//get all products for the primary key
    			List<Product> products = productInfoDao.select(id);
    			String productName = getPrdInfoFrmRedsky(id);
    			if(StringUtils.isEmpty(productName) ) {
    				handleError(productName, NO_PRODUCT_INFO_AVAIALBE_IN_REDSKY_ID+id );
    			}
    			
    			//get the latest value of Product from db. 
    			Product product = getLatestProductData(products);
    			if(null ==  product) {
    				handleError(productName, PRICING_INFORMATION_NOT_AVAILABLE_FOR_THE_PRODUCT_PRODUCT_ID+id );
    			}
    			
    			product.setProductName(productName);
				populateResponse(product, response, MessageConstant.SUCCESS, HttpStatus.OK.toString());
		} catch (Exception e) {
			logger.error("Exception in ProductInfoService.getProduct, id= "+id , e);
			throw new TargetPricingException(e);
		}
    	return response;
	}

	private void handleError(String productName, String message) {
			logger.error(message );
			throw new TargetPricingException(message );
	}
	
	/**
	 * Service to Insert/update pricing information in cassandra DB
	 * @param product
	 * @return
	 */
	public ProductInfoResponse createProduct(Product product) {
		
		ProductInfoResponse response = new ProductInfoResponse();
		
    	try {
    			logger.info("calling ProductInfoService.createProduct, Product= "+product );
    			ProductUtil.validateProductRequest(product);
    			updateProductModel(product);
    			Product productResponse = productInfoDao.createProductPriceInfo( product);
    			populateResponse(productResponse, response, MessageConstant.SUCCESS, HttpStatus.CREATED.toString());
    			
		} catch (Exception e) {
			logger.error("Exception in ProductInfoService.createProduct, Product= "+product , e);
			throw new TargetPricingException(e);
		}
    	return response;
	}

	

	public ProductInfoResponse updateProduct(Product product) {
		ProductInfoResponse response = new ProductInfoResponse();
		try {
			logger.info(CALLING_PRODUCT_INFO_SERVICE_UPDATE_PRODUCT_PRODUCT+product );
			ProductUtil.validateProductRequest(product);
			updateProductModel(product);
			Product productResponse =  productInfoDao.updateProductPriceInfo(product);
			populateResponse(productResponse, response, MessageConstant.SUCCESS, HttpStatus.CREATED.toString());
		} catch (Exception e) {
			logger.error("Exception in ProductInfoService.createProduct, Product= "+product , e);
			throw new TargetPricingException(e);
		}
    	return response;
		
	}


	/**
	 * Serive to Delete a product based on the give ProductId
	 * @param id
	 * @return
	 */
	public ResponseHeader deleteProduct(int id) {
		ResponseHeader responseHeader = null;
		try {
			productInfoDao.deleteProductPriceInfo(id);
			responseHeader = populateErrorCode(MessageConstant.SUCCESS, HttpStatus.ACCEPTED.toString());
		} catch (Exception e) {
			logger.error(EXCEPTION_IN_PRODUCT_INFO_SERVICE_DELETE_PRODUCT_ID+id , e);
			throw new TargetPricingException(e);
		}
		return responseHeader;

	}

	/**
	 * Serivice to get all products 
	 * @return
	 */
	public List<Product> getAllProducts() {
		return productInfoDao.getAllProductPriceInfo();
	}
	
	public String getPrdInfoFrmRedsky(int id) throws JsonProcessingException, IOException {
		return productHandler.getPrdInfoFrmRedsky(id);

	}
	
	
	private ProductInfoResponse populateResponse(Product product, ProductInfoResponse response, 
			String errorMessage, String errorCode) {
		
		response.setProduct(product);
		ResponseHeader responseHeader = populateErrorCode(errorMessage, errorCode);
		response.setHeaderResponse(responseHeader);
		return response;
		
	}

	private ResponseHeader populateErrorCode(String errorMessage, String errorCode) {
		ResponseHeader responseHeader = new ResponseHeader();
		responseHeader.setErrorMessage(errorMessage);
		responseHeader.setErrorCode(errorCode);
		return responseHeader;
	}

	/**
	 *  If timestamp is not populated from request then set the values to current Date
		update ts, refractor code 
	 * @param product
	 */
	private void updateProductModel(Product product) {
		//i
		if(null==product.getUpdateTs() ){
			product.setUpdateTs(new Date());
		}
		if(null==product.getSourceTs() ){
			product.setSourceTs(new Date());
		}
	}
	
	private Product getLatestProductData(List<Product> products) {
		if(null != products && products.size() > 0 ) {
			return products.get(0);
		}
		return null;
	}

	
}
