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

	// ��̨��ѯ����һ������
	public String adminFindAll() throws Exception {
		List<Category> cList = categoryService.findAll();

		ActionContext.getContext().getValueStack().set("cList", cList);

		return "adminFindAllSuccess";
	}

	// ��̨����µ�һ������
	public String save() throws Exception {
		boolean flag = categoryService.save(category, false);
		if (!flag) {
			this.addActionError("�������Ѵ���");
			return "saveFailure";
		}
		return "saveSuccess";
	}

	// ��̨����µ�һ������
	public String delete() throws Exception {
		categoryService.delete(category);

		return "deleteSuccess";
	}

	// ��̨Ԥ�༭һ������
	public String editPre() throws Exception {
		category = categoryService.findByCid(category.getCid());

		return "editPreSuccess";
	}

	// ��̨����һ������
	public String update() throws Exception {
		boolean flag = categoryService.save(category, true);
		if (!flag) {
			this.addActionError("�������Ѵ���");
			return "updateFailure";
		}
		categoryService.update(category);

		return "updateSuccess";
	}
}
