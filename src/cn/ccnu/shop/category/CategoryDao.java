package cn.ccnu.shop.category;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class CategoryDao extends HibernateDaoSupport {

	@SuppressWarnings("unchecked")
	public List<Category> findAll() {
		List<Category> CategoryList = this.getHibernateTemplate().find(
				"from Category");
		if (CategoryList.size() > 0) {
			return CategoryList;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public Category findByCname(String cname) {
		List<Category> CategoryList = this.getHibernateTemplate().find(
				"from Category where cname=?", cname);
		if (CategoryList.size() > 0) {
			return CategoryList.get(0);
		}
		return null;
	}

	public void save(Category category) {
		this.getHibernateTemplate().save(category);
	}

	public void delete(Category category) {
		//
		this.getHibernateTemplate().delete(category);
	}

	public Category findByCid(int cid) {
		return this.getHibernateTemplate().get(Category.class, cid);
	}

	public void update(Category category) {
		this.getHibernateTemplate().update(category);
	}

}
