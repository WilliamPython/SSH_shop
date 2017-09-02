package cn.ccnu.shop.category;

import java.util.List;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class CategoryAction extends ActionSupport implements
		ModelDriven<Category> {
	private CategoryService categoryService;

	private Category category = new Category();

	@Override
	public Category getModel() {
		return category;
	}

	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	// 后台查询所有一级分类
	public String adminFindAll() throws Exception {
		List<Category> cList = categoryService.findAll();

		ActionContext.getContext().getValueStack().set("cList", cList);

		return "adminFindAllSuccess";
	}

	// 后台添加新的一级分类
	public String save() throws Exception {
		boolean flag = categoryService.save(category, false);
		if (!flag) {
			this.addActionError("分类名已存在");
			return "saveFailure";
		}
		return "saveSuccess";
	}

	// 后台添加新的一级分类
	public String delete() throws Exception {
		categoryService.delete(category);

		return "deleteSuccess";
	}

	// 后台预编辑一级分类
	public String editPre() throws Exception {
		category = categoryService.findByCid(category.getCid());

		return "editPreSuccess";
	}

	// 后台更新一级分类
	public String update() throws Exception {
		boolean flag = categoryService.save(category, true);
		if (!flag) {
			this.addActionError("分类名已存在");
			return "updateFailure";
		}
		categoryService.update(category);

		return "updateSuccess";
	}
}
