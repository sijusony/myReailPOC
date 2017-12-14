package org.myretail;


import org.myretail.handler.ProductInfoHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class TargetProductInfoApplication {
	
	@Autowired
    private CacheManager cacheManager;
	@Autowired
	private ProductInfoHandler handler;

	
	public static void main(String[] args) 
	{
		SpringApplication.run(TargetProductInfoApplication.class, args);   
		
	}

	
	

}
