package cn.ccnu.shop.order;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.ccnu.shop.utils.PageBean;

@Transactional
public class OrderService {
	private OrderDao orderDao;

	public void setOrderDao(OrderDao orderDao) {
		this.orderDao = orderDao;
	}

	public int save(Order order) {
		return orderDao.save(order);
	}

	public Order findByOid(int oid) {
		return orderDao.findByOid(oid);
	}

	public void update(Order order) {
		orderDao.update(order);
	}

	public List<Order> findByUid(Integer uid) {
		return orderDao.findByUid(uid);
	}

	public PageBean<Order> findByPage(Integer page) {
		PageBean<Order> pageBean = new PageBean<Order>();

		pageBean.setPage(page);

		int limit = 5;
		pageBean.setLimit(limit);

		int totalPage, totalCount;

		totalCount = orderDao.getCount();

		if (totalCount % limit == 0) {
			totalPage = totalCount / limit;
		} else {
			totalPage = totalCount / limit + 1;
		}

		pageBean.setTotalCount(totalCount);
		pageBean.setTotalPage(totalPage);

		List<Order> csList = orderDao.findByPage(page, limit);
		pageBean.setList(csList);

		return pageBean;
	}

	public PageBean<Order> findByState(Integer page, Integer state) {
		PageBean<Order> pageBean = new PageBean<Order>();

		pageBean.setPage(page);

		int limit = 2;
		pageBean.setLimit(limit);

		int totalPage, totalCount;

		totalCount = orderDao.getCountByState(state);

		if (totalCount % limit == 0) {
			totalPage = totalCount / limit;
		} else {
			totalPage = totalCount / limit + 1;
		}

		pageBean.setTotalCount(totalCount);
		pageBean.setTotalPage(totalPage);

		List<Order> csList = orderDao.findByState(page, limit, state);
		pageBean.setList(csList);

		return pageBean;
	}

}
