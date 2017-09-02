package cn.ccnu.shop.adminuser;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class AdminUserAction extends ActionSupport implements
		ModelDriven<AdminUser> {
	// ģ��������������ʹ��
	private AdminUser adminUser = new AdminUser();
	// ע��Service
	private AdminUserService adminUserService;

	public AdminUser getModel() {
		return adminUser;
	}

	public void setAdminUserService(AdminUserService adminUserService) {
		this.adminUserService = adminUserService;
	}

	/**
	 * ��̨��½�ķ���:
	 */
	public String login() {
		AdminUser existAdminUser = adminUserService.login(adminUser);
		if (existAdminUser == null) {
			// ��½ʧ��
			this.addActionError("�û������������!");
			return LOGIN;
		} else {
			// ��½�ɹ�
			ServletActionContext.getRequest().getSession()
					.setAttribute("existAdminUser", existAdminUser);
			return "loginSuccess";
		}
	}

}
