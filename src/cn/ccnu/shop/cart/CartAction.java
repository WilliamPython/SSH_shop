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

	// ��ӵ����ﳵ
	public String addCartItem() throws Exception {
		CartItem item = new CartItem();
		item.setProduct(productService.findByPid(pid));
		item.setCount(count);

		Cart cart = getCart();
		cart.addCartItem(item);

		return "addCartItemSuccess";
	}

	// ��ȡ���ﳵ
	private Cart getCart() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		Cart cart = (Cart) session.getAttribute("cart");
		if (cart == null) {
			cart = new Cart();
			session.setAttribute("cart", cart);
		}
		return cart;
	}

	// �Ƴ�������
	public String removeCartItem() throws Exception {
		Cart cart = getCart();
		cart.removeCartItem(pid);

		return "removeCartItemSucess";
	}

	// ��չ��ﳵ
	public String clearCart() throws Exception {
		Cart cart = getCart();
		cart.clearCart();

		return "clearCartSucess";
	}

	// �ҵĹ��ﳵ
	public String myCart() throws Exception {
		return "myCartSucess";
	}
}
