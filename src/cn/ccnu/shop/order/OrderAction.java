package cn.ccnu.shop.order;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import cn.ccnu.shop.cart.Cart;
import cn.ccnu.shop.cart.CartItem;
import cn.ccnu.shop.user.User;
import cn.ccnu.shop.utils.PageBean;
import cn.ccnu.shop.utils.PaymentUtil;
import cn.itcast.commons.CommonUtils;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

@SuppressWarnings("serial")
public class OrderAction extends ActionSupport implements ModelDriven<Order> {

	private Order order = new Order();

	private OrderService orderService;

	private String pd_FrpId;
	// 付款成功后的需要的参数:
	private String r3_Amt;
	private String r6_Order;

	public void setR3_Amt(String r3_Amt) {
		this.r3_Amt = r3_Amt;
	}

	public void setR6_Order(String r6_Order) {
		this.r6_Order = r6_Order;
	}

	public void setPd_FrpId(String pd_FrpId) {
		this.pd_FrpId = pd_FrpId;
	}

	@Override
	public Order getModel() {
		return order;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	// 生成订单
	public String saveOrder() throws Exception {
		HttpSession session = ServletActionContext.getRequest().getSession();
		// 获取购物车
		Cart cart = (Cart) session.getAttribute("cart");
		if (cart == null) {
			this.addActionMessage("您还没有购物，请您先去购物！");
			return "msg";
		}
		order.setOrdertime(new Date());
		order.setState(1);
		order.setTotal(cart.getTotal());

		User user = (User) session.getAttribute("ExitUser");
		if (user == null) {
			this.addActionMessage("您还没有登录，请您先去登录！");
			return "msg";
		}
		order.setUser(user);

		for (CartItem cartItem : cart.getCartItems()) {
			OrderItem orderItem = new OrderItem();

			orderItem.setCount(cartItem.getCount());
			orderItem.setSubtotal(cartItem.getSubTotal());
			orderItem.setProduct(cartItem.getProduct());

			orderItem.setOrder(order);

			order.getOrderItems().add(orderItem);
		}
		// 保存订单，级联保存了订单项
		int oid = orderService.save(order);

		// 必须回数据库查一下(因为此时order的oid和Orderitem的itemid都没有值，需要数据库自动生成，但又对页面有作用)
		order = orderService.findByOid(oid);

		// 清空购物车
		cart.clearCart();

		return "saveOrderSuccess";
	}

	// 支付订单
	public String payOrder() throws Exception {
		// 修改订单(填充name、addr和phone属性)
		Order _order = orderService.findByOid(order.getOid());
		_order.setAddr(order.getAddr());
		_order.setName(order.getName());
		_order.setPhone(order.getPhone());
		orderService.update(_order);

		// 加载商家收款易宝账户信息
		String p1_MerId = null, keyValue = null, p8_Url = null;
		Properties pro = new Properties();
		try {
			pro.load(getClass().getClassLoader().getResourceAsStream(
					"merchantInfo.properties"));
			p1_MerId = pro.getProperty("p1_MerId");
			keyValue = pro.getProperty("keyValue");
			p8_Url = pro.getProperty("responseURL");
		} catch (IOException e) {
			// e.printStackTrace();
			this.addActionMessage("抱歉，服务器读取商户支付信息出错！");
			return "msg";
		}

		// 定义付款的参数:
		String p0_Cmd = "Buy";
		// String p1_MerId = "10001126856";
		// 订单号处理一下是为了处理易宝订单重复支付问题
		String p2_Order = CommonUtils.uuid() + order.getOid().toString();
		String p3_Amt = "0.01";
		String p4_Cur = "CNY";
		String p5_Pid = "";
		String p6_Pcat = "";
		String p7_Pdesc = "";
		// String p8_Url = "http://localhost:8080/SSH_shop/order_callBack";
		String p9_SAF = "";
		String pa_MP = "";
		String pr_NeedResponse = "1";
		// String keyValue =
		// "69cl522AV6q613Ii4W6u8K6XuW8vM1N6bFgyv769220IuYe9u37N4y7rI4Pl";
		String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt,
				p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP,
				pd_FrpId, pr_NeedResponse, keyValue);
		// 拼接支付公司接口地址
		StringBuffer sb = new StringBuffer(
				"https://www.yeepay.com/app-merchant-proxy/node?");
		sb.append("p0_Cmd=").append(p0_Cmd).append("&");
		sb.append("p1_MerId=").append(p1_MerId).append("&");
		sb.append("p2_Order=").append(p2_Order).append("&");
		sb.append("p3_Amt=").append(p3_Amt).append("&");
		sb.append("p4_Cur=").append(p4_Cur).append("&");
		sb.append("p5_Pid=").append(p5_Pid).append("&");
		sb.append("p6_Pcat=").append(p6_Pcat).append("&");
		sb.append("p7_Pdesc=").append(p7_Pdesc).append("&");
		sb.append("p8_Url=").append(p8_Url).append("&");
		sb.append("p9_SAF=").append(p9_SAF).append("&");
		sb.append("pa_MP=").append(pa_MP).append("&");
		sb.append("pd_FrpId=").append(pd_FrpId).append("&");
		sb.append("pr_NeedResponse=").append(pr_NeedResponse).append("&");
		sb.append("hmac=").append(hmac);
		// 重定向前往支付公司相关网站
		ServletActionContext.getResponse().sendRedirect(sb.toString());

		return NONE;
	}

	// 支付公司执行我们回调的方法
	public String callBack() throws Exception {
		// 修改订单状态（*************************************）
		// 数据库中的oid:_oid
		String _oid = r6_Order.substring(32, r6_Order.length());
		Order _order = orderService.findByOid(Integer.parseInt(_oid));
		_order.setState(2);
		orderService.update(_order);
		// 页面提示
		this.addActionMessage("订单付款成功!订单号:" + _oid + " 付款金额:" + r3_Amt);
		return "msg";
	}

	// 查看我的订单
	public String myOrder() throws Exception {
		User user = (User) ServletActionContext.getRequest().getSession()
				.getAttribute("ExitUser");
		List<Order> oList = orderService.findByUid(user.getUid());
		ActionContext.getContext().getValueStack().set("oList", oList);
		return "myOrderSuccess";
	}

	// 选中订单去付款
	public String findByOid() throws Exception {

		order = orderService.findByOid(order.getOid());

		return "findByOidSuccess";
	}

	// 选中订单去确认收货
	public String confirmByOid() throws Exception {

		Order _order = orderService.findByOid(order.getOid());
		_order.setState(4);
		orderService.update(_order);

		return "confirmByOidSuccess";
	}

	private Integer page;

	public void setPage(Integer page) {
		this.page = page;
	}

	// 后台查询所有订单
	public String adminFindAll() throws Exception {

		PageBean<Order> pageBean = orderService.findByPage(page);
		ServletActionContext.getContext().getValueStack()
				.set("pageBean", pageBean);

		return "adminFindAllSuccess";
	}

	// 后台确认发货
	public String adminUpdateState() throws Exception {
		order = orderService.findByOid(order.getOid());
		order.setState(3);

		orderService.update(order);

		return "adminUpdateStateSuccess";
	}

	// 后台按状态查询订单
	public String adminFindByState() throws Exception {
		PageBean<Order> pageBean = orderService.findByState(page,
				order.getState());
		ServletActionContext.getContext().getValueStack()
				.set("pageBean", pageBean);

		return "adminFindByStateSuccess";
	}
}
