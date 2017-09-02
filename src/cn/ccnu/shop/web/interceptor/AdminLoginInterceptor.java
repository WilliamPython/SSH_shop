package cn.ccnu.shop.web.interceptor;

import org.apache.struts2.ServletActionContext;

import cn.ccnu.shop.adminuser.AdminUser;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

/**
 * ����Ա��½������
 * 
 * @author William Python
 * 
 */
public class AdminLoginInterceptor extends MethodFilterInterceptor {

	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		AdminUser admin = (AdminUser) ServletActionContext.getRequest()
				.getSession().getAttribute("existAdminUser");
		if (admin != null) {
			return invocation.invoke();
		}
		ActionSupport action = (ActionSupport) invocation.getAction();
		action.addActionError("����û�е�½���������ޣ�");
		return action.LOGIN;
	}
}
