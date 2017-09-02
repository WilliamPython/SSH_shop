package cn.ccnu.shop.web.interceptor;

import org.apache.struts2.ServletActionContext;

import cn.ccnu.shop.adminuser.AdminUser;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

/**
 * 管理员登陆拦截器
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
		action.addActionError("您还没有登陆，访问受限！");
		return action.LOGIN;
	}
}
