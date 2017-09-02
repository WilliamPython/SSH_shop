package cn.ccnu.shop.categorysecond;

import java.util.List;

import cn.ccnu.shop.category.Category;
import cn.ccnu.shop.category.CategoryService;
import cn.ccnu.shop.utils.PageBean;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class CategorysecondAction extends ActionSupport implements
		ModelDriven<CategorySecond> {
	private CategorySecondService categorySecondService;
	private CategoryService categoryervice;
	private Integer page;

	private CategorySecond categorySecond = new CategorySecond();
	private Integer cid;

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	@Override
	public CategorySecond getModel() {
		// TODO Auto-generated method stub
		return categorySecond;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public void setCategoryervice(CategoryService categoryervice) {
		this.categoryervice = categoryervice;
	}

	public void setCategorySecondService(
			CategorySecondService categorySecondService) {
		this.categorySecondService = categorySecondService;
	}

	public String adminFindAll() throws Exception {

		PageBean<CategorySecond> pageBean = categorySecondService
				.findByPage(page);
		ActionContext.getContext().getValueStack().set("pageBean", pageBean);

		return "adminFindAllSuccess";
	}

	public String addPage() throws Exception {
		List<Category> cList = categoryervice.findAll();
		ActionContext.getContext().getValueStack().set("cList", cList);

		return "addPageSuccess";
	}

	public String save() throws Exception {
		Category category = new Category();
		category.setCid(cid);
		categorySecond.setCategory(category);

		boolean flag = categorySecondService.save(categorySecond, false);
		if (!flag) {
			this.addActionError("二级分类名已存在");

			List<Category> cList = categoryervice.findAll();
			ActionContext.getContext().getValueStack().set("cList", cList);

			return "saveFailure";
		}

		return "saveSuccess";
	}

	public String delete() throws Exception {
		categorySecondService.delete(categorySecond);

		return "deleteSuccess";
	}
}
