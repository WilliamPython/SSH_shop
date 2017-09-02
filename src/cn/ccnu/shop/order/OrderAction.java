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
	// ����ɹ������Ҫ�Ĳ���:
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

	// ���ɶ���
	public String saveOrder() throws Exception {
		HttpSession session = ServletActionContext.getRequest().getSession();
		// ��ȡ���ﳵ
		Cart cart = (Cart) session.getAttribute("cart");
		if (cart == null) {
			this.addActionMessage("����û�й��������ȥ���");
			return "msg";
		}
		order.setOrdertime(new Date());
		order.setState(1);
		order.setTotal(cart.getTotal());

		User user = (User) session.getAttribute("ExitUser");
		if (user == null) {
			this.addActionMessage("����û�е�¼��������ȥ��¼��");
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
		// ���涩�������������˶�����
		int oid = orderService.save(order);

		// ��������ݿ��һ��(��Ϊ��ʱorder��oid��Orderitem��itemid��û��ֵ����Ҫ���ݿ��Զ����ɣ����ֶ�ҳ��������)
		order = orderService.findByOid(oid);

		// ��չ��ﳵ
		cart.clearCart();

		return "saveOrderSuccess";
	}

	// ֧������
	public String payOrder() throws Exception {
		// �޸Ķ���(���name��addr��phone����)
		Order _order = orderService.findByOid(order.getOid());
		_order.setAddr(order.getAddr());
		_order.setName(order.getName());
		_order.setPhone(order.getPhone());
		orderService.update(_order);

		// �����̼��տ��ױ��˻���Ϣ
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
			this.addActionMessage("��Ǹ����������ȡ�̻�֧����Ϣ����");
			return "msg";
		}

		// ���帶��Ĳ���:
		String p0_Cmd = "Buy";
		// String p1_MerId = "10001126856";
		// �����Ŵ���һ����Ϊ�˴����ױ������ظ�֧������
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
		// ƴ��֧����˾�ӿڵ�ַ
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
		// �ض���ǰ��֧����˾�����վ
		ServletActionContext.getResponse().sendRedirect(sb.toString());

		return NONE;
	}

	// ֧����˾ִ�����ǻص��ķ���
	public String callBack() throws Exception {
		// �޸Ķ���״̬��*************************************��
		// ���ݿ��е�oid:_oid
		String _oid = r6_Order.substring(32, r6_Order.length());
		Order _order = orderService.findByOid(Integer.parseInt(_oid));
		_order.setState(2);
		orderService.update(_order);
		// ҳ����ʾ
		this.addActionMessage("��������ɹ�!������:" + _oid + " ������:" + r3_Amt);
		return "msg";
	}

	// �鿴�ҵĶ���
	public String myOrder() throws Exception {
		User user = (User) ServletActionContext.getRequest().getSession()
				.getAttribute("ExitUser");
		List<Order> oList = orderService.findByUid(user.getUid());
		ActionContext.getContext().getValueStack().set("oList", oList);
		return "myOrderSuccess";
	}

	// ѡ�ж���ȥ����
	public String findByOid() throws Exception {

		order = orderService.findByOid(order.getOid());

		return "findByOidSuccess";
	}

	// ѡ�ж���ȥȷ���ջ�
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

	// ��̨��ѯ���ж���
	public String adminFindAll() throws Exception {

		PageBean<Order> pageBean = orderService.findByPage(page);
		ServletActionContext.getContext().getValueStack()
				.set("pageBean", pageBean);

		return "adminFindAllSuccess";
	}

	// ��̨ȷ�Ϸ���
	public String adminUpdateState() throws Exception {
		order = orderService.findByOid(order.getOid());
		order.setState(3);

		orderService.update(order);

		return "adminUpdateStateSuccess";
	}

	// ��̨��״̬��ѯ����
	public String adminFindByState() throws Exception {
		PageBean<Order> pageBean = orderService.findByState(page,
				order.getState());
		ServletActionContext.getContext().getValueStack()
				.set("pageBean", pageBean);

		return "adminFindByStateSuccess";
	}
}
