package cn.ccnu.shop.product;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.ccnu.shop.utils.PageBean;

@Transactional
public class ProductService {
	private ProductDao productDao;

	public void setProductDao(ProductDao productDao) {
		this.productDao = productDao;
	}

	public List<Product> findHot() {
		return productDao.findHot();
	}

	public List<Product> findNew() {
		return productDao.findNew();
	}

	public PageBean<Product> findByPage(Integer cid, Integer page) {
		PageBean<Product> pageBean = new PageBean<Product>();

		int limit = 12;
		pageBean.setLimit(limit);

		pageBean.setPage(page);

		int totalCount = 0;
		totalCount = productDao.findTotalCountByCid(cid);
		pageBean.setTotalCount(totalCount);

		int totalPage = 0;
		if (totalCount % limit == 0) {
			totalPage = totalCount / limit;
		} else {
			totalPage = totalCount / limit + 1;
		}
		pageBean.setTotalPage(totalPage);

		List<Product> list = productDao.findByPage(page, limit, cid);
		pageBean.setList(list);

		return pageBean;
	}

	public PageBean<Product> findByCsPage(Integer csid, Integer page) {
		PageBean<Product> pageBean = new PageBean<Product>();

		int limit = 10;
		pageBean.setLimit(limit);

		pageBean.setPage(page);

		int totalCount = 0;
		totalCount = productDao.findTotalCountByCsid(csid);
		pageBean.setTotalCount(totalCount);

		int totalPage = 0;
		if (totalCount % limit == 0) {
			totalPage = totalCount / limit;
		} else {
			totalPage = totalCount / limit + 1;
		}
		pageBean.setTotalPage(totalPage);

		List<Product> list = productDao.findByCsPage(page, limit, csid);
		pageBean.setList(list);

		return pageBean;
	}

	public Product findByPid(Integer pid) {
		return productDao.findByPid(pid);
	}

	// 后台查询所有商品
	public PageBean<Product> findByPage(Integer page) {
		PageBean<Product> pageBean = new PageBean<Product>();

		int limit = 10;
		pageBean.setLimit(limit);

		pageBean.setPage(page);

		int totalCount = 0;
		totalCount = productDao.findTotalCount();
		pageBean.setTotalCount(totalCount);

		int totalPage = 0;
		if (totalCount % limit == 0) {
			totalPage = totalCount / limit;
		} else {
			totalPage = totalCount / limit + 1;
		}
		pageBean.setTotalPage(totalPage);

		List<Product> list = productDao.findByPage(page, limit);
		pageBean.setList(list);

		return pageBean;
	}

	public void save(Product product) {
		productDao.save(product);
	}

}
