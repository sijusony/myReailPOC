package org.myretail.model;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;


/**
 * 
 * @author siju
 * Model for Product
 */
//@Data
@Table(value="price_info")
public class Product implements Serializable{
	
	private static final long serialVersionUID = 273821766508130875L;

	@NotBlank
	@NotEmpty(message = "Product Id cannot be empty.")
	@PrimaryKey("product_id") 
	private long productId;		
	
	//@PrimaryKeyColumn(name = "source_ts",type = PrimaryKeyType.CLUSTERED, ordinal = 1, ordering = Ordering.DESCENDING)
	@NotBlank
	@Column("source_ts") 
	private Date sourceTs;		
	
	//@PrimaryKeyColumn(name = "update_ts", type = PrimaryKeyType.CLUSTERED, ordinal = 2, ordering = Ordering.DESCENDING)
	@NotBlank
	@Column("update_ts") 
	private Date updateTs;
	
	@Column("product_name") 
	@NotBlank
	private String productName;
	
	@Column("currency_code") 
	@NotBlank
	private String currencyCode;
	
	@Min(0)
	@Column("current_price")
	@NotEmpty(message = "Product Price cannot be Empty.")
	private double currentPrice;
	
	

	public Product() {
		super();
	}

	public Product(long productId, Date sourceTs, Date createTs, Date updateTs, String productName, String currencyCode,
			double currentPrice) {
		super();
		this.productId = productId;
		this.sourceTs = sourceTs;
		this.updateTs = updateTs;
		this.productName = productName;
		this.currencyCode = currencyCode;
		this.currentPrice = currentPrice;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public Date getSourceTs() {
		return sourceTs;
	}

	public void setSourceTs(Date sourceTs) {
		this.sourceTs = sourceTs;
	}

	public Date getUpdateTs() {
		return updateTs;
	}

	public void setUpdateTs(Date updateTs) {
		this.updateTs = updateTs;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public double getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(double currentPrice) {
		this.currentPrice = currentPrice;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((currencyCode == null) ? 0 : currencyCode.hashCode());
		long temp;
		temp = Double.doubleToLongBits(currentPrice);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + (int) (productId ^ (productId >>> 32));
		result = prime * result + ((productName == null) ? 0 : productName.hashCode());
		result = prime * result + ((sourceTs == null) ? 0 : sourceTs.hashCode());
		result = prime * result + ((updateTs == null) ? 0 : updateTs.hashCode());
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
		Product other = (Product) obj;
		if (currencyCode == null) {
			if (other.currencyCode != null)
				return false;
		} else if (!currencyCode.equals(other.currencyCode))
			return false;
		if (Double.doubleToLongBits(currentPrice) != Double.doubleToLongBits(other.currentPrice))
			return false;
		if (productId != other.productId)
			return false;
		if (productName == null) {
			if (other.productName != null)
				return false;
		} else if (!productName.equals(other.productName))
			return false;
		if (sourceTs == null) {
			if (other.sourceTs != null)
				return false;
		} else if (!sourceTs.equals(other.sourceTs))
			return false;
		if (updateTs == null) {
			if (other.updateTs != null)
				return false;
		} else if (!updateTs.equals(other.updateTs))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Product [productId=" + productId + ", sourceTs=" + sourceTs + ", updateTs="
				+ updateTs + ", productName=" + productName + ", currencyCode=" + currencyCode + ", currentPrice="
				+ currentPrice + "]";
	}

}
