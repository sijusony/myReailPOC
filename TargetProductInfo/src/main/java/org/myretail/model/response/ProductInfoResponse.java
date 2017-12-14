package org.myretail.model.response;

import java.io.Serializable;

import org.myretail.model.Product;
import org.myretail.model.response.ResponseHeader;


public class ProductInfoResponse implements Serializable{
	
	private static final long serialVersionUID = -5869768973878452544L;

	private Product product;
	
	private ResponseHeader headerResponse;

	public Product getProduct() {
		return product;
	}

	public ProductInfoResponse() {
		super();
	}

	public ProductInfoResponse(Product product, ResponseHeader headerResponse) {
		super();
		this.product = product;
		this.headerResponse = headerResponse;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public ResponseHeader getHeaderResponse() {
		return headerResponse;
	}

	public void setHeaderResponse(ResponseHeader headerResponse) {
		this.headerResponse = headerResponse;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((headerResponse == null) ? 0 : headerResponse.hashCode());
		result = prime * result + ((product == null) ? 0 : product.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductInfoResponse other = (ProductInfoResponse) obj;
		if (headerResponse == null) {
			if (other.headerResponse != null)
				return false;
		} else if (!headerResponse.equals(other.headerResponse))
			return false;
		if (product == null) {
			if (other.product != null)
				return false;
		} else if (!product.equals(other.product))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ProductInfoResponse [product=" + product + ", headerResponse=" + headerResponse + "]";
	}
	
	//setter,getters and other object defaults will be implemented by Lombak @Data

	
	
	}
