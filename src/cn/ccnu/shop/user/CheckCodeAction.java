package cn.ccnu.shop.user;

import java.awt.image.BufferedImage;

import org.apache.struts2.ServletActionContext;

import cn.itcast.vcode.utils.VerifyCode;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class CheckCodeAction extends ActionSupport {
	@Override
	public String execute() throws Exception {
		/*
		 * 1. 创建验证码类
		 */
		VerifyCode vc = new VerifyCode();
		/*
		 * 2. 得到验证码图片
		 */
		BufferedImage image = vc.getImage();
		/*
		 * 3. 把图片上的文本保存到session中
		 */
		ServletActionContext.getRequest().getSession()
				.setAttribute("CheckCode", vc.getText());
		/*
		 * 4. 把图片响应给客户端
		 */
		VerifyCode.output(image, ServletActionContext.getResponse()
				.getOutputStream());

		return NONE;
	}
}
