package cn.ccnu.shop.cart;

import cn.ccnu.shop.product.Product;

/**
 * ���ﳵ��
 * 
 * @author William Python
 * 
 */
public class CartItem {
	private Product product;// ��Ʒ��Ϣ

	private Integer count;// ����
	private Double subTotal;// С��

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Double getSubTotal() {
		return product.getShop_price() * count;
	}

}
