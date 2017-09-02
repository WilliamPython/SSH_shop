package cn.ccnu.shop.product;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.ccnu.shop.utils.PageHibernateCallback;

public class ProductDao extends HibernateDaoSupport {

	// DAO层查询热门商品只显示10个
	@SuppressWarnings("unchecked")
	public List<Product> findHot() {

		// DetachedCriteria criteria = DetachedCriteria.forClass(Product.class);
		// criteria.add(Restrictions.eq("is_hot", 1));
		// List<Product> list =
		// this.getHibernateTemplate().findByCriteria(criteria, 0, 10);

		List<Product> hotList = this.getHibernateTemplate().executeFind(
				new PageHibernateCallback<Product>(
						"from Product where is_hot=?", new Object[] { 1 }, 0,
						10));
		if (hotList.size() > 0) {
			return hotList;
		}
		return null;
	}

	// 查看最新商品
	@SuppressWarnings("unchecked")
	public List<Product> findNew() {
		List<Product> NewList = this.getHibernateTemplate().executeFind(
				new PageHibernateCallback<Product>(
						"from Product order by pdate desc", null, 0, 10));
		if (NewList.size() > 0) {
			return NewList;
		}
		return null;
	}

	public int findTotalCountByCid(Integer cid) {
		// String hql =
		// "select count(*) from Product p,CategorySecond cs where p.categorysecond=cs and cs.category.cid=?";
		String hql = "select count(*) from Product p join p.categorysecond cs join cs.category c where c.cid=?";
		List<Long> list = this.getHibernateTemplate().find(hql, cid);

		return list.get(0).intValue();
	}

	public List<Product> findByPage(Integer page, int limit, Integer cid) {

		String hql = "select p from Product p join p.categorysecond cs join cs.category c where c.cid=?";

		List<Product> list = this.getHibernateTemplate().executeFind(
				new PageHibernateCallback<Product>(hql, new Object[] { cid },
						(page - 1) * limit, limit));

		return list;
	}

	public int findTotalCountByCsid(Integer csid) {
		String hql = "select count(*) from Product p join p.categorysecond cs where cs.csid=?";
		List<Long> list = this.getHibernateTemplate().find(hql, csid);

		return list.get(0).intValue();
	}

	public List<Product> findByCsPage(Integer page, int limit, Integer csid) {
		String hql = "select p from Product p join p.categorysecond cs where cs.csid=?";

		List<Product> list = this.getHibernateTemplate().executeFind(
				new PageHibernateCallback<Product>(hql, new Object[] { csid },
						(page - 1) * limit, limit));

		return list;
	}

	public Product findByPid(Integer pid) {
		return this.getHibernateTemplate().get(Product.class, pid);
	}

	public int findTotalCount() {
		List<Long> list = this.getHibernateTemplate().find(
				"select count(*) from Product");
		return list.get(0).intValue();
	}

	public List<Product> findByPage(Integer page, int limit) {
		List<Product> list = this.getHibernateTemplate().execute(
				new PageHibernateCallback<Product>("from Product", null,
						(page - 1) * limit, limit));
		if (list != null && list.size() > 0) {
			return list;
		}
		return null;
	}

	public void save(Product product) {
		this.getHibernateTemplate().save(product);
	}
}
