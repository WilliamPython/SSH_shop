package cn.ccnu.shop.categorysecond;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import cn.ccnu.shop.category.Category;
import cn.ccnu.shop.product.Product;

/**
 * ��������ʵ��
 * 
 * @author William Python
 */
public class CategorySecond implements Serializable {
	private int csid;
	private String csname;

	private Category category;// һ������

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	private Set<Product> products = new HashSet<Product>();// ��Ʒ

	public Set<Product> getProducts() {
		return products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}

	public int getCsid() {
		return csid;
	}

	public void setCsid(int csid) {
		this.csid = csid;
	}

	public String getCsname() {
		return csname;
	}

	public void setCsname(String csname) {
		this.csname = csname;
	}

	@Override
	public String toString() {
		return "CategorySecond [csid=" + csid + ", csname=" + csname + "]";
	}

}
