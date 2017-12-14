package org.myretail.dao;


import java.util.List;

import org.myretail.model.Product;

/**
 * 
 * @author siju
 * DAO layer Interface
 */
public interface ProductPriceInfoDao {
	
    public Product createProductPriceInfo(Product product);
    
    public Product getProductPriceInfo(int id);
    
    public Product updateProductPriceInfo(Product product);
    
    public void deleteProductPriceInfo(int id);
    
    public List<Product> getAllProductPriceInfo();
    
    public List<Product> select(int id) ;
	

}
