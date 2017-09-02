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
		 * 1. ������֤����
		 */
		VerifyCode vc = new VerifyCode();
		/*
		 * 2. �õ���֤��ͼƬ
		 */
		BufferedImage image = vc.getImage();
		/*
		 * 3. ��ͼƬ�ϵ��ı����浽session��
		 */
		ServletActionContext.getRequest().getSession()
				.setAttribute("CheckCode", vc.getText());
		/*
		 * 4. ��ͼƬ��Ӧ���ͻ���
		 */
		VerifyCode.output(image, ServletActionContext.getResponse()
				.getOutputStream());

		return NONE;
	}
}
