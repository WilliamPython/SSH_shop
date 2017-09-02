<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="span10 last">
		<div class="topNav clearfix">
			<ul>
			<s:if test="#session.ExitUser==null">
				<li id="headerLogin" class="headerLogin" style="display: list-item;">
					<a href="${pageContext.request.contextPath }/user_loginPage">登录</a>|
				</li>
				<li id="headerRegister" class="headerRegister" style="display: list-item;">
					<a href="${pageContext.request.contextPath }/user_registPage">注册</a>|
				</li>
			</s:if>
			<s:else>
				<li id="headerUsername" class="headerUsername"><s:property value="#session.ExitUser.username"/></li>
				<li id="headerLogout" class="headerLogout">
					<a href="${pageContext.request.contextPath }/user_loginOut">[退出]</a>|
				</li>
				<li id="headerLogout" class="headerLogout">
					<a href="${pageContext.request.contextPath }/order_myOrder">我的订单</a>|
				</li>
			</s:else>
						<li>
							<a>会员中心</a>
							|
						</li>
						<li>
							<a>购物指南</a>
							|
						</li>
						<li>
							<a>关于我们</a>
							
						</li>
			</ul>
		</div>
		<div class="cart">
			<a href="${pageContext.request.contextPath}/cart_myCart">购物车</a>
		</div>
			<div class="phone">
				客服热线:
				<strong>96008/53277764</strong>
			</div>
</div>