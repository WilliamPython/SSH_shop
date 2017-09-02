package cn.ccnu.shop.index;

import java.util.List;

import cn.ccnu.shop.category.Category;
import cn.ccnu.shop.category.CategoryService;
import cn.ccnu.shop.product.Product;
import cn.ccnu.shop.product.ProductService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class IndexAction extends ActionSupport {
	private CategoryService categoryService;
	private ProductService productService;
	private List<Product> NewList;
	private List<Product> HotList;

	public List<Product> getNewList() {
		return NewList;
	}

	public List<Product> getHotList() {
		return HotList;
	}

	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	@Override
	public String execute() throws Exception {
		// 将所有的一级分类存入session，以后查询一级分类不用去数据库查询
		List<Category> CategoryList = categoryService.findAll();
		ActionContext.getContext().getSession()
				.put("CategoryList", CategoryList);
		// 查询热门商品
		HotList = productService.findHot();
		// 查询最新商品
		NewList = productService.findNew();
		// 返回首页
		return "indexSuccess";
	}
}
