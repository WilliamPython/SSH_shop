package cn.ccnu.shop.categorysecond;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.ccnu.shop.utils.PageHibernateCallback;

public class CategorySecondDao extends HibernateDaoSupport {

	public int getCount() {
		List<Long> list = this.getHibernateTemplate().find(
				"select count(*) from CategorySecond");
		if (list.size() > 0) {
			return list.get(0).intValue();
		}
		return 0;
	}

	public List<CategorySecond> findByPage(Integer page, int limit) {
		List<CategorySecond> list = this.getHibernateTemplate()
				.execute(
						new PageHibernateCallback<CategorySecond>(
								"from CategorySecond", null,
								(page - 1) * limit, limit));
		return list;
	}

	public void save(CategorySecond categorySecond) {
		this.getHibernateTemplate().save(categorySecond);
	}

	public CategorySecond findByCsname(String csname) {
		List<CategorySecond> csList = this.getHibernateTemplate().find(
				"from CategorySecond where csname=?", csname);
		if (csList != null && csList.size() > 0) {
			return csList.get(0);
		}
		return null;
	}

	public void delete(CategorySecond categorySecond) {
		this.getHibernateTemplate().delete(categorySecond);
	}

	public List<CategorySecond> findAll() {
		List<CategorySecond> csList = this.getHibernateTemplate().find(
				"from CategorySecond");
		if (csList != null && csList.size() > 0) {
			return csList;
		}
		return null;
	}
}
