package org.myretail.dao.impl;

import java.util.List;

import org.myretail.dao.ProductPriceInfoDao;
import org.myretail.model.Product;
import org.myretail.util.MyCassandraTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
/**
 * 
 * @author siju
 * DAO layer which connects to database
 */
@Repository
public class ProductInfoDaoImpl implements ProductPriceInfoDao{
	
	 @Autowired
	    private MyCassandraTemplate cassandraTemplate;


	@Override
	public Product createProductPriceInfo(Product product) {
		return cassandraTemplate.create(product);
	}

	@Override
	public Product getProductPriceInfo(int id) {
		return cassandraTemplate.findById(id, Product.class);
	}

	@Override
	public Product updateProductPriceInfo(Product product) {
		return cassandraTemplate.update(product, Product.class);
	}

	@Override
	public void deleteProductPriceInfo(int id) {
		cassandraTemplate.deleteById(id, Product.class);
	}

	@Override
	public List<Product> getAllProductPriceInfo() {
		return cassandraTemplate.findAll(Product.class);
	}

	@Override
	public List<Product> select(int id) {
		return cassandraTemplate.select("select * from price_info where product_id="+id+" limit 1", Product.class);
	}

}
