package cn.ccnu.shop.category;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public class CategoryService {
	private CategoryDao categoryDao;

	public void setCategoryDao(CategoryDao categoryDao) {
		this.categoryDao = categoryDao;
	}

	public List<Category> findAll() {
		return categoryDao.findAll();
	}

	public boolean save(Category category, boolean isUpdate) {
		Category _category = categoryDao.findByCname(category.getCname());
		if (_category == null) {
			if (!isUpdate) {
				categoryDao.save(category);
			}
			return true;
		}
		return false;
	}

	public void delete(Category category) {
		// 为了级联删除CategorySecond，必须先查询后删除
		category = categoryDao.findByCid(category.getCid());
		categoryDao.delete(category);
	}

	public Category findByCid(int cid) {
		return categoryDao.findByCid(cid);
	}

	public void update(Category category) {
		categoryDao.update(category);
	}
}
