package cn.ccnu.shop.user;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import cn.itcast.commons.CommonUtils;
import cn.itcast.mail.Mail;
import cn.itcast.mail.MailUtils;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;

@SuppressWarnings("serial")
public class UserAction extends ActionSupport implements ModelDriven<User> {
	private UserService userService;
	private String CheckCode;

	private User user = new User();

	@Override
	public User getModel() {
		return user;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setCheckCode(String checkCode) {
		CheckCode = checkCode;
	}

	// ��ת��ע��ҳ��
	public String registPage() throws Exception {
		return "registPageSuccess";
	}

	@InputConfig(resultName = "registInput")
	public String regist() throws Exception {
		// У����֤��
		String CheckCode1 = (String) ServletActionContext.getRequest()
				.getSession().getAttribute("CheckCode");
		if (CheckCode == null || !CheckCode1.equalsIgnoreCase(CheckCode)) {
			this.addActionError("��֤�����");
			return "registInput";
		}
		// ע���û�
		user.setState(0);// ��ע�ᣬ0��ʾδ����
		user.setCode(CommonUtils.uuid() + CommonUtils.uuid());
		userService.regist(user);

		// ���ͼ����ʼ���163�����е������շѣ�����ʹ���������䣩
		Properties pro = new Properties();
		pro.load(this.getClass().getClassLoader()
				.getResourceAsStream("MailInfo.properties"));

		Session session = MailUtils.createSession(
				pro.getProperty("MailServerName"), pro.getProperty("username"),
				pro.getProperty("password"));

		Mail mail = new Mail(pro.getProperty("from"), user.getEmail(),
				pro.getProperty("subject"), MessageFormat.format(
						pro.getProperty("content"), user.getCode()));
		try {
			MailUtils.send(session, mail);
			this.addActionMessage("ע��ɹ�����ȥ���伤�");
		} catch (MessagingException e) {
			this.addActionError("�ʼ�����ĳ��ԭ����ʧ��,������ע�ᣡ");
			return "SendMailFailure";
			// throw new RuntimeException(e);
		}
		return "registSuccess";
	}

	public String active() throws Exception {
		User ExitUser = userService.findUserByCode(user.getCode());
		if (ExitUser == null) {
			this.addActionMessage("����ʧ��!����������!");
		} else {
			ExitUser.setState(1);// ״̬��Ϊ����̬��state=1
			userService.update(ExitUser);
			this.addActionMessage("����ɹ�!��ȥ��¼!");
		}
		return "activeSuccess";
	}

	public String loginPage() throws Exception {
		return "loginPageSuccess";
	}

	@InputConfig(resultName = "loginInput")
	public String login() throws Exception {
		// У����֤��
		String CheckCode1 = (String) ServletActionContext.getRequest()
				.getSession().getAttribute("CheckCode");
		if (CheckCode == null || !CheckCode1.equalsIgnoreCase(CheckCode)) {
			this.addActionError("��֤�����");
			return "loginInput";
		}
		// ����service
		User ExitUser = userService.login(user);
		if (ExitUser == null) {
			this.addActionError("�û��������������û�δ����!");
			return "loginInput";
		}
		// ��ȫ�����´���������Session
		// ServletActionContext.getRequest().getSession().invalidate();
		// ���û���ŵ�session��
		ServletActionContext.getRequest().getSession()
				.setAttribute("ExitUser", ExitUser);

		// �����Ƿ��ס�û�״̬
		IsRemUserState();

		return "loginSuccess";
	}

	private String[] isRememberUsername;

	public void setIsRememberUsername(String[] isRememberUsername) {
		this.isRememberUsername = isRememberUsername;
	}

	/**
	 * �����Ƿ��ס�û�״̬
	 * 
	 * @throws UnsupportedEncodingException
	 */
	private void IsRemUserState() throws UnsupportedEncodingException {
		HttpServletResponse response = ServletActionContext.getResponse();
		if (isRememberUsername != null && isRememberUsername.length > 0) {
			// ʹ��URLEncoder���cookie�г��ֵ�������������
			String username = URLEncoder.encode(user.getUsername(), "utf-8");
			Cookie c1 = new Cookie("usernamecookie", username);
			c1.setMaxAge(10 * 24 * 60 * 60);
			response.addCookie(c1);
		} else {
			// û�й�ѡ��ס�û�״̬�������ͬ��cookie
			Cookie[] cookies = ServletActionContext.getRequest().getCookies();
			if (cookies != null && cookies.length > 0) {
				for (Cookie c : cookies) {
					if (c.getName().equals("usernamecookie")) {
						c.setMaxAge(0);// ���ͬ��cookie
						response.addCookie(c);// ע��һ��Ҫ��д�޸�
					}
				}
			}
		}

	}

	public String checkUserName() throws Exception {
		User ExitUser = userService.findByUserName(user.getUsername());

		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=UTF-8");
		if (ExitUser == null) {
			response.getWriter().write("<font color='green'>�û���������</font>");
		} else {
			response.getWriter().write("<font color='red'>�û����Ѵ���</font>");
		}
		return NONE;
	}

	public String loginOut() throws Exception {
		ServletActionContext.getRequest().getSession().invalidate();
		return "loginOut";
	}
}
