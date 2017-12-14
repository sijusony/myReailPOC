package org.myretail.handler;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.myretail.exception.TargetPricingException;
import org.myretail.util.MessageConstant;
import org.myretail.util.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
//@CacheConfig(cacheNames = "productName")
public class ProductInfoHandler {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	PropertiesUtil propertiesUtil;

	/**
	 * Gets product name from redsky api
	 * Product name for a given id will be cached for 24 hours
	 * @param id
	 * @return
	 * @throws JsonProcessingException
	 * @throws IOException
	 */
	@Cacheable(value="productName", key="#id")
	public String getPrdInfoFrmRedsky(int id) throws JsonProcessingException, IOException {

		JsonNode product = null;
		RestTemplate restTemplate = new RestTemplate();
		int counter = 0;
		boolean isProductInfoAvailable = false;
		do {
			logger.info("Calling Redsky API for product ="+id);
			try {
				ResponseEntity<String> responseEntity = restTemplate.getForEntity(propertiesUtil.getRedskyURL()+id+MessageConstant.EXCLUSIONS,
						String.class);
				if(responseEntity.getStatusCode().equals(HttpStatus.OK)) {
					product = retreiveProductName(responseEntity);
					isProductInfoAvailable = true;
				}else {
					//
					isProductInfoAvailable = false;
					counter++;
					logger.error("Error calling redsky service");
					
				}
			} catch (RestClientException e) {
				isProductInfoAvailable = false;
				counter++;
				if(null == product && counter>=3) {
					logger.error("Error connecting to redsky service" + e.getMessage());
					throw new TargetPricingException("Error calling redsky service"+e.getMessage());
				}
			}
			//retry 3 times in case there is no response from redsky
		} while(counter <=3 && !isProductInfoAvailable);
		
		return product.asText();
	}

	private JsonNode retreiveProductName(ResponseEntity<String> responseEntity)
			throws IOException, JsonProcessingException {
		JsonNode product;
		ObjectMapper mapper = new ObjectMapper();
		JsonNode root = mapper.readTree(responseEntity.getBody());
		product = root.path(MessageConstant.PRODUCT)
				.path(MessageConstant.ITEM).path(MessageConstant.PRODUCT_DESCRIPTION).path(MessageConstant.TITLE);
		return product;
	}
	
	/*@CacheEvict(allEntries = true)
    public void clearCache(){}*/


}
