package cn.ccnu.shop.cart;

import cn.ccnu.shop.product.Product;

/**
 * 购物车项
 * 
 * @author William Python
 * 
 */
public class CartItem {
	private Product product;// 商品信息

	private Integer count;// 数量
	private Double subTotal;// 小计

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
