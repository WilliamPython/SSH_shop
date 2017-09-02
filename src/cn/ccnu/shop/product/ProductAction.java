package cn.ccnu.shop.product;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

import cn.ccnu.shop.category.Category;
import cn.ccnu.shop.category.CategoryService;
import cn.ccnu.shop.categorysecond.CategorySecond;
import cn.ccnu.shop.categorysecond.CategorySecondService;
import cn.ccnu.shop.utils.PageBean;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

@SuppressWarnings("serial")
public class ProductAction extends ActionSupport implements
		ModelDriven<Product> {
	private ProductService productService;
	private CategoryService categoryService;
	private CategorySecondService categorySecondService;

	private PageBean<Product> pageBean;// 分页显示的数据
	private Integer page;// 获取当前页数
	private Integer cid;
	private Integer csid;

	private Product product = new Product();

	@Override
	public Product getModel() {
		return product;
	}

	public void setCategorySecondService(
			CategorySecondService categorySecondService) {
		this.categorySecondService = categorySecondService;
	}

	public Integer getCsid() {
		return csid;
	}

	public Integer getCid() {
		return cid;
	}

	public void setCsid(Integer csid) {
		this.csid = csid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public PageBean<Product> getPageBean() {
		return pageBean;
	}

	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	public String findByCid() throws Exception {
		List<Category> CategoryList = categoryService.findAll();
		// 将CategoryList手动压栈
		ActionContext.getContext().getValueStack()
				.set("CategoryList", CategoryList);
		// 设置pageBean
		pageBean = productService.findByPage(cid, page);

		return "findByCidSuccess";
	}

	public String findByCsid() throws Exception {
		List<Category> CategoryList = categoryService.findAll();
		// 将CategoryList手动压栈
		ActionContext.getContext().getValueStack()
				.set("CategoryList", CategoryList);
		// 设置pageBean
		pageBean = productService.findByCsPage(csid, page);

		return "findByCsidSuccess";
	}

	public String findByPid() throws Exception {
		List<Category> CategoryList = categoryService.findAll();
		// 将CategoryList手动压栈
		ActionContext.getContext().getValueStack()
				.set("CategoryList", CategoryList);

		product = productService.findByPid(product.getPid());

		return "findByPidSuccess";
	}

	public String adminFindAll() throws Exception {
		pageBean = productService.findByPage(page);

		return "adminFindAllSuccess";
	}

	public String addPage() throws Exception {
		List<CategorySecond> csList = categorySecondService.findAll();
		ActionContext.getContext().getValueStack().set("csList", csList);

		return "addPageSuccess";
	}

	// 文件上传的三个参数
	private File upload;
	private String uploadContentType;
	private String uploadFileName;

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getUploadContentType() {
		return uploadContentType;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public String save() throws Exception {
		// 保存上传图片
		String path = ServletActionContext.getServletContext().getRealPath(
				"/products");
		FileUtils.copyFile(upload, new File(path + "/" + csid + "/"
				+ uploadFileName));

		product.setImage("products/" + csid + "/" + uploadFileName);

		CategorySecond cs = new CategorySecond();
		cs.setCsid(csid);
		product.setCategorysecond(cs);

		product.setPdate(new Date());

		productService.save(product);

		return "saveSuccess";
	}
}
