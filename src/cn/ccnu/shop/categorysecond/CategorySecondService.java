package cn.ccnu.shop.categorysecond;

import java.util.List;

import cn.ccnu.shop.utils.PageBean;

public class CategorySecondService {
	private CategorySecondDao categorySecondDao;

	public void setCategorySecondDao(CategorySecondDao categorySecondDao) {
		this.categorySecondDao = categorySecondDao;
	}

	public PageBean<CategorySecond> findByPage(Integer page) {
		PageBean<CategorySecond> pageBean = new PageBean<CategorySecond>();

		pageBean.setPage(page);

		int limit = 10;
		pageBean.setLimit(limit);

		int totalPage, totalCount;

		totalCount = categorySecondDao.getCount();

		if (totalCount % limit == 0) {
			totalPage = totalCount / limit;
		} else {
			totalPage = totalCount / limit + 1;
		}

		pageBean.setTotalCount(totalCount);
		pageBean.setTotalPage(totalPage);

		List<CategorySecond> csList = categorySecondDao.findByPage(page, limit);
		pageBean.setList(csList);

		return pageBean;
	}

	public boolean save(CategorySecond categorySecond, boolean isUpdate) {

		CategorySecond _categorySecond = categorySecondDao
				.findByCsname(categorySecond.getCsname());
		if (_categorySecond == null) {
			if (!isUpdate) {
				categorySecondDao.save(categorySecond);
			}
			return true;
		}
		return false;
	}

	public void delete(CategorySecond categorySecond) {
		categorySecondDao.delete(categorySecond);
	}

	public List<CategorySecond> findAll() {
		return categorySecondDao.findAll();
	}

}
