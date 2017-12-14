package org.myretail.controller;

import java.util.List;

import org.myretail.model.Product;
import org.myretail.model.response.ProductInfoResponse;
import org.myretail.model.response.ResponseHeader;
import org.myretail.service.ProductInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value="/rest/myretail")
public class ProductInfoController {
	


	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	ProductInfoService productService;
	
	/**
	 * Create pricing information for the given product in cassandra
	 * @param product
	 * @return
	 */
	@RequestMapping(value = "/product", method = RequestMethod.POST)    
	ProductInfoResponse create(@RequestBody Product product) {      
		logger.info("Inside ProductInfoController.create, Product="+product);
      return productService.createProduct(product);
    }
	
	/**
	 * Get latest pricing information for the given product id
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/product/{id}", method = RequestMethod.GET)
    ProductInfoResponse findById(@PathVariable("id") int id) {   
		logger.info("Inside ProductInfoController.findById, id="+id);
		return productService.getProduct(id);
    }
 
	/**
	 * Delete product record for the given product
	 * @param id
	 */
    @RequestMapping(value = "/product/{id}", method = RequestMethod.DELETE)
    ResponseHeader delete(@PathVariable(value="id") int id) {
    	logger.info("Inside ProductInfoController.delete, id="+id);
    	return productService.deleteProduct(id); 
    }
 
    /**
     * Get all product information available in database
     * @return
     */
    @RequestMapping(value="/product", method = RequestMethod.GET)
    List<Product> findAll() {
    		logger.info("Inside ProductInfoController.findAll");
			return productService.getAllProducts();
    }
 
    
	 /**
	  * Updates the given product infromation
	  * @param product
	  * @return
	  */
    @RequestMapping(value = "/product", method = RequestMethod.PUT)
    ProductInfoResponse update(@RequestBody Product product) {
    	logger.info("Inside ProductInfoController.update, Product="+product);
        return productService.updateProduct(product);
    }
    
    /**
     * Custom exception handler for ProductInfoController
     * @param ex
     * @return
     */
   /* @ExceptionHandler(TargetPricingException.class)
    public ResponseEntity<ProductInfoResponse> exceptionHandler(RuntimeException ex) {
    	logger.error("Exception calling Pricing application :" +ex.getMessage());
    	ProductInfoResponse response = new ProductInfoResponse();
    	ResponseHeader header = new ResponseHeader();
    	header.setErrorCode(MessageUtil.ERROR);
    	header.setErrorMessage(ex.getMessage());
    	response.setHeaderResponse(header);
    	return new ResponseEntity<ProductInfoResponse>(response, HttpStatus.OK);
    }*/
	
}
