package cn.ccnu.shop.cart;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import cn.ccnu.shop.product.ProductService;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class CartAction extends ActionSupport {
	private Integer pid;
	private Integer count;

	private ProductService productService;

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	// 添加到购物车
	public String addCartItem() throws Exception {
		CartItem item = new CartItem();
		item.setProduct(productService.findByPid(pid));
		item.setCount(count);

		Cart cart = getCart();
		cart.addCartItem(item);

		return "addCartItemSuccess";
	}

	// 获取购物车
	private Cart getCart() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		Cart cart = (Cart) session.getAttribute("cart");
		if (cart == null) {
			cart = new Cart();
			session.setAttribute("cart", cart);
		}
		return cart;
	}

	// 移除购物项
	public String removeCartItem() throws Exception {
		Cart cart = getCart();
		cart.removeCartItem(pid);

		return "removeCartItemSucess";
	}

	// 清空购物车
	public String clearCart() throws Exception {
		Cart cart = getCart();
		cart.clearCart();

		return "clearCartSucess";
	}

	// 我的购物车
	public String myCart() throws Exception {
		return "myCartSucess";
	}
}
