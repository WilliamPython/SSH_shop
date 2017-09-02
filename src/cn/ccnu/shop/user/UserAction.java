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

	// 跳转到注册页面
	public String registPage() throws Exception {
		return "registPageSuccess";
	}

	@InputConfig(resultName = "registInput")
	public String regist() throws Exception {
		// 校验验证码
		String CheckCode1 = (String) ServletActionContext.getRequest()
				.getSession().getAttribute("CheckCode");
		if (CheckCode == null || !CheckCode1.equalsIgnoreCase(CheckCode)) {
			this.addActionError("验证码错误！");
			return "registInput";
		}
		// 注册用户
		user.setState(0);// 刚注册，0表示未激活
		user.setCode(CommonUtils.uuid() + CommonUtils.uuid());
		userService.regist(user);

		// 发送激活邮件（163邮箱有点问题收费，建议使用新浪邮箱）
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
			this.addActionMessage("注册成功，请去邮箱激活！");
		} catch (MessagingException e) {
			this.addActionError("邮件由于某种原因发送失败,请重新注册！");
			return "SendMailFailure";
			// throw new RuntimeException(e);
		}
		return "registSuccess";
	}

	public String active() throws Exception {
		User ExitUser = userService.findUserByCode(user.getCode());
		if (ExitUser == null) {
			this.addActionMessage("激活失败!激活码有误!");
		} else {
			ExitUser.setState(1);// 状态改为激活态：state=1
			userService.update(ExitUser);
			this.addActionMessage("激活成功!请去登录!");
		}
		return "activeSuccess";
	}

	public String loginPage() throws Exception {
		return "loginPageSuccess";
	}

	@InputConfig(resultName = "loginInput")
	public String login() throws Exception {
		// 校验验证码
		String CheckCode1 = (String) ServletActionContext.getRequest()
				.getSession().getAttribute("CheckCode");
		if (CheckCode == null || !CheckCode1.equalsIgnoreCase(CheckCode)) {
			this.addActionError("验证码错误！");
			return "loginInput";
		}
		// 调用service
		User ExitUser = userService.login(user);
		if (ExitUser == null) {
			this.addActionError("用户名或密码错误或用户未激活!");
			return "loginInput";
		}
		// 安全处理，下次重新生成Session
		// ServletActionContext.getRequest().getSession().invalidate();
		// 将用户存放到session中
		ServletActionContext.getRequest().getSession()
				.setAttribute("ExitUser", ExitUser);

		// 处理是否记住用户状态
		IsRemUserState();

		return "loginSuccess";
	}

	private String[] isRememberUsername;

	public void setIsRememberUsername(String[] isRememberUsername) {
		this.isRememberUsername = isRememberUsername;
	}

	/**
	 * 处理是否记住用户状态
	 * 
	 * @throws UnsupportedEncodingException
	 */
	private void IsRemUserState() throws UnsupportedEncodingException {
		HttpServletResponse response = ServletActionContext.getResponse();
		if (isRememberUsername != null && isRememberUsername.length > 0) {
			// 使用URLEncoder解决cookie中出现的中文乱码问题
			String username = URLEncoder.encode(user.getUsername(), "utf-8");
			Cookie c1 = new Cookie("usernamecookie", username);
			c1.setMaxAge(10 * 24 * 60 * 60);
			response.addCookie(c1);
		} else {
			// 没有勾选记住用户状态，就清除同名cookie
			Cookie[] cookies = ServletActionContext.getRequest().getCookies();
			if (cookies != null && cookies.length > 0) {
				for (Cookie c : cookies) {
					if (c.getName().equals("usernamecookie")) {
						c.setMaxAge(0);// 清除同名cookie
						response.addCookie(c);// 注意一定要回写修改
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
			response.getWriter().write("<font color='green'>用户名不存在</font>");
		} else {
			response.getWriter().write("<font color='red'>用户名已存在</font>");
		}
		return NONE;
	}

	public String loginOut() throws Exception {
		ServletActionContext.getRequest().getSession().invalidate();
		return "loginOut";
	}
}
