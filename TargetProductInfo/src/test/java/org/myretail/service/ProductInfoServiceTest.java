/*package org.myretail.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.myretail.dao.ProductPriceInfoDao;
import org.myretail.exception.TargetPricingException;
import org.myretail.handler.ProductInfoHandler;
import org.myretail.model.Product;
import org.myretail.model.response.ProductInfoResponse;
import org.myretail.model.response.ResponseHeader;
import org.myretail.util.MessageConstant;
import org.myretail.util.ProductUtil;
import org.myretail.util.TestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ProductInfoServiceTest {
	
	
	@Mock
	ProductPriceInfoDao productInfoDao; 

	@Mock
	ProductInfoHandler productHandler;
	
	
	@InjectMocks
	private ProductInfoService prodService;
	
	@Autowired
    private CacheManager cacheManager;
	
	
	@Test
	public void getProduct_PRODUCT_NOT_AVAILALBE(){
		
		int productId = 123;
		try {
			List<Product> products  = new ArrayList<>();
			Product product = TestUtil.createProduct(new Date(), productId, "USD");
			products.add(product);
			
			//mocking dao call
			when(productInfoDao.select(Mockito.anyInt())).thenReturn(products);
			ProductInfoResponse res = prodService.getProduct(123);
			
			System.out.println(res);
			//assertEquals("SUCCESS", res.getHeaderResponse().getErrorMessage());
			assertTrue(false);
		} catch (Exception e) {
			String errorMessage = "No Product Info avaialbe in Redsky, id= "+productId+"";
			assertTrue(e.getMessage().contains(errorMessage));
			
		}
	}
	
	@Test
	public void getProduct_PRODUCT_SUCCESS(){
		int productId = 123;
		try {
			List<Product> products  = new ArrayList<>();
			Product product = TestUtil.createProduct(new Date(), productId, "USD");
			products.add(product);
			
			//mocking dao response
			when(productInfoDao.select(Mockito.anyInt())).thenReturn(products);
			//mocking redsky call
			when(productHandler.getPrdInfoFrmRedsky(Mockito.anyInt())).thenReturn("test product");
			ProductInfoResponse res = prodService.getProduct(123);
			
			
			
			
			System.out.println(res);
			assertEquals("SUCCESS", res.getHeaderResponse().getErrorMessage());
			assertEquals("test product", res.getProduct().getProductName());
			assertTrue(true);
		} catch (Exception e) {
			assertTrue(false);
		}
	}
	
	@Test
	public void createProduct_SUCCESS(){
		int productId = 123;
		try {
			Product product = TestUtil.createProduct(new Date(), productId, "USD");
			
			when(productInfoDao.createProductPriceInfo( product)).thenReturn(product);
			ProductInfoResponse res = prodService.createProduct(product);
			assertEquals("SUCCESS", res.getHeaderResponse().getErrorMessage());
			assertTrue(true);
		} catch (Exception e) {
			assertTrue(false);
			
		}

	}
	
	
	@Test
	public void createProduct_throwing_vaidation_error_productId(){
		int productId = 0;
		try {
			Product product = TestUtil.createProduct(new Date(), productId, "USD");
			
			when(productInfoDao.createProductPriceInfo( product)).thenReturn(product);
			ProductInfoResponse res = prodService.createProduct(product);
			assertTrue(false);
		} catch (Exception e) {
			assertTrue(true);
			assertTrue(e.getMessage().contains("Product ID is mandatory parameter"));
		}

	}
	
	@Test
	public void createProduct_throwing_validation_error_currency(){
		int productId = 123;
		try {
			Product product = TestUtil.createProduct(new Date(), productId, "USD121");
			
			when(productInfoDao.createProductPriceInfo( product)).thenReturn(product);
			ProductInfoResponse res = prodService.createProduct(product);
			assertTrue(false);
		} catch (Exception e) {
			assertTrue(true);
			assertTrue(e.getMessage().contains("Currency Code cannot be blank"));
		}

	}
	

	@Test
	public void updateProduct_SUCCESS(){
		int productId = 123;
		try {
			Product product = TestUtil.createProduct(new Date(), productId, "USD");
			
			when(productInfoDao.updateProductPriceInfo( product)).thenReturn(product);
			ProductInfoResponse res = prodService.updateProduct(product);
			assertEquals("SUCCESS", res.getHeaderResponse().getErrorMessage());
			assertTrue(true);
		} catch (Exception e) {
			assertTrue(false);
		}

	}
	
	@Test
	public void updateProduct_throwing_vaidation_error_productId(){
		int productId = 0;
		try {
			Product product = TestUtil.createProduct(new Date(), productId, "USD");
			
			when(productInfoDao.createProductPriceInfo( product)).thenReturn(product);
			ProductInfoResponse res = prodService.updateProduct(product);
			assertTrue(false);
		} catch (Exception e) {
			assertTrue(true);
			assertTrue(e.getMessage().contains("Product ID is mandatory parameter"));
		}

	}
	
	@Test
	public void deleteProduct_SUCCESS(){
		int productId = 0;
		try {
			
			ResponseHeader res = prodService.deleteProduct(123);
			//populateErrorCode(MessageConstant.SUCCESS, HttpStatus.ACCEPTED.toString());
			assertTrue(true);
		} catch (Exception e) {
			assertTrue(false);
		}

	}
	
	@Test
	public void getAll_SUCCESS(){
		try {
			List<Product> products  = new ArrayList<>();
			Product product = TestUtil.createProduct(new Date(), 123, "USD");
			Product product1 = TestUtil.createProduct(new Date(), 456, "USD");
			products.add(product);
			products.add(product1);
			
			when(productInfoDao.getAllProductPriceInfo()).thenReturn(products);
			List<Product> productsResp = prodService.getAllProducts();
			assertTrue(productsResp.size()==2);
			assertTrue(true);
		} catch (Exception e) {
			assertTrue(false);
		}

	}
	
	
	//@Test
	public void assert_true() {
			assertTrue(true);
		}
	


}
*/