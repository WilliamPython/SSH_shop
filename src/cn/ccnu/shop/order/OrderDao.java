package cn.ccnu.shop.order;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.ccnu.shop.utils.PageHibernateCallback;

public class OrderDao extends HibernateDaoSupport {

	public int save(Order order) {
		return (Integer) this.getHibernateTemplate().save(order);
	}

	public Order findByOid(int oid) {
		return this.getHibernateTemplate().get(Order.class, oid);
	}

	public void update(Order order) {
		this.getHibernateTemplate().update(order);
	}

	public List<Order> findByUid(Integer uid) {
		String hql = "select o from Order o where o.user.uid=? order by ordertime desc";
		List<Order> OList = this.getHibernateTemplate().find(hql, uid);
		return OList;
	}

	public int getCount() {
		List<Long> list = this.getHibernateTemplate().find(
				"select count(*) from Order");
		if (list != null && list.size() > 0) {
			return list.get(0).intValue();
		}
		return 0;
	}

	public List<Order> findByPage(Integer page, int limit) {
		List<Order> list = this.getHibernateTemplate().execute(
				new PageHibernateCallback<Order>(
						"from Order order by ordertime desc", null, (page - 1)
								* limit, limit));
		return list;
	}

	public int getCountByState(Integer state) {
		List<Long> list = this.getHibernateTemplate().find(
				"select count(*) from Order where state=?", state);
		if (list != null & list.size() > 0) {
			return list.get(0).intValue();
		}
		return 0;
	}

	public List<Order> findByState(Integer page, int limit, Integer state) {
		List<Order> list = this.getHibernateTemplate().execute(
				new PageHibernateCallback<Order>(
						"from Order where state=?  order by ordertime desc",
						new Object[] { state }, (page - 1) * limit, limit));
		return list;
	}
}
