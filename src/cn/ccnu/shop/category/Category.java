package cn.ccnu.shop.category;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import cn.ccnu.shop.categorysecond.CategorySecond;

/**
 * 一级分类实体类
 * 
 * @author William Python
 * 
 */
public class Category implements Serializable {
	private Integer cid;
	private String cname;

	private Set<CategorySecond> categoryseconds = new HashSet<CategorySecond>();

	public Set<CategorySecond> getCategoryseconds() {
		return categoryseconds;
	}

	public void setCategoryseconds(Set<CategorySecond> categoryseconds) {
		this.categoryseconds = categoryseconds;
	}

	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

}
