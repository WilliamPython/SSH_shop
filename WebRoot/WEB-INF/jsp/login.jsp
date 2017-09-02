<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>会员登录</title>
<meta name="author" content="Mango Team">
<meta name="copyright" content="Mango">
<link href="${pageContext.request.contextPath }/css/common.css" rel="stylesheet" type="text/css"/>
<link href="${pageContext.request.contextPath }/css/login.css" rel="stylesheet" type="text/css"/>

<script type="text/javascript">
//更换验证码图片
function change(){
	 var CheckCodeImage=document.getElementById("CheckCodeImage");
	 CheckCodeImage.src="${pageContext.request.contextPath }/CheckCode.action?time="+new Date().getTime();
}
//获取cookie
function RememberCookie(){
	  var usernameInput=document.getElementById("username");
	  var usernamevalue =usernameInput.value;
	  if(usernamevalue == null || usernamevalue == ""){
		  //防止中文乱码
		  usernameInput.value = decodeURI("${ cookie.usernamecookie.value }");
	  }
}
</script>
</head>
<body onload="RememberCookie()">

<div class="container header">
	<div class="span5">
		<div class="logo">
			<a href="http://localhost:8080/mango/">
				<img src="${pageContext.request.contextPath }/image/r___________renleipic_01/logo.gif" alt="传智播客">
			</a>
		</div>
	</div>
	<div class="span9">
<div class="headerAd">
	<img src="${pageContext.request.contextPath }/image/header.jpg" width="320" height="50" alt="正品保障" title="正品保障">
</div>	
</div>
	<%@ include file="header.jsp" %>
	<%@ include file="menu.jsp" %>
	<div class="span24">
		<div class="tagWrap">
			<ul class="tag">
						<li class="icon" style="background: url(http://storage.shopxx.net/demo-image/3.0/tag/hot.gif) right no-repeat;">
							<a >热销</a>
						</li>
						<li class="icon" style="background: url(http://storage.shopxx.net/demo-image/3.0/tag/new.gif) right no-repeat;">
							<a>最新</a>
						</li>
			</ul>
			<div class="hotSearch">
					热门搜索:
						<a>水蜜桃</a>
						<a>西瓜</a>
						<a>紫薯</a>
						<a>大米</a>
						<a>玉米</a>
						<a>茄子</a>
						<a>辣椒</a>
						<a>圣女果</a>
						<a>鱿鱼丝</a>
			</div>
			<div class="search">
			
				<form id="productSearchForm"  method="get">
					<input name="keyword" class="keyword" value="商品搜索" maxlength="30">
					<button type="submit">搜索</button>
				</form>
			</div>
		</div>
	</div>
</div>	<div class="container login">
		<div class="span12">
<div class="ad">
					<img src="${pageContext.request.contextPath }/image/login.jpg" width="500" height="330" alt="会员登录" title="会员登录">
</div>		</div>
		<div class="span12 last">
			<div class="wrap">
				<div class="main">
					<div class="title">
						<strong>会员登录</strong>USER LOGIN
					</div>
					<form id="loginForm"  method="post" action="${pageContext.request.contextPath }/user_login" novalidate="novalidate">
						<table>
							<tbody><tr>
								<th>
										用户名/E-mail:
								</th>
								<td>
									<input type="text" id="username" name="username" class="text" maxlength="20"><span><font color="red"><s:fielderror fieldName="username"/></font></span>
								</td>
							</tr>
							<tr>
								<th>
									密&nbsp;&nbsp;码:
								</th>
								<td>
									<input type="password" id="password" name="password" class="text" maxlength="20" autocomplete="off"><span><font color="red"><s:fielderror fieldName="password"/></font></span>
								</td>
							</tr>
								<tr>
									<th>
										验证码:
									</th>
									<td>
										<span class="fieldSet">
											<input type="text" id="CheckCode" name="CheckCode" class="textcaptcha" maxlength="4" autocomplete="off">
											<img id="CheckCodeImage" class="CheckCodeImage" src="${pageContext.request.contextPath }/CheckCode.action" title="点击更换验证码" onclick="change()">
										</span>
									</td>
								</tr>
							<tr>
								<th>&nbsp;
									
								</th>
								<td>
									<label>
										<input type="checkbox" id="isRememberUsername" name="isRememberUsername" value="true" checked="checked">在十天内记住用户名
									</label>
									<label>
										&nbsp;&nbsp;<a >找回密码</a>
									</label>
								</td>
							</tr>
							<tr>
								<th>&nbsp;
									
								</th>
								<td>
									<input type="submit" class="submit" value="登 录">
									<font color="red"><s:actionerror/></font>
								</td>
								
							</tr>
							<tr class="register">
								<th>&nbsp;
									
								</th>
								<td>
									<dl>
										<dt>还没有注册账号？</dt>
										<dd>
											立即注册即可体验在线购物！
											<a href="./会员注册.htm">立即注册</a>
										</dd>
									</dl>
								</td>
							</tr>
						</tbody></table>
					</form>
				</div>
			</div>
		</div>
	</div>
<div class="container footer">
	<div class="span24">
	  <div class="footerAd"><img src="${pageContext.request.contextPath }/image/footer.jpg" width="950" height="52" alt="我们的优势" title="我们的优势" /></div>	
	</div>
	<div class="span24">
		<ul class="bottomNav">
					<li>
						<a >关于我们</a>
						|
					</li>
					<li>
						<a>联系我们</a>
						|
					</li>
					<li>
						<a>招贤纳士</a>
						|
					</li>
					<li>
						<a>法律声明</a>
						|
					</li>
					<li>
						<a>友情链接</a>
						|
					</li>
					<li>
						<a target="_blank">支付方式</a>
						|
					</li>
					<li>
						<a  target="_blank">配送方式</a>
						|
					</li>
					<li>
						<a>服务声明</a>
						|
					</li>
					<li>
						<a>广告声明</a>
						
					</li>
		</ul>
	</div>
	<div class="span24">
		<div class="copyright">Copyright © 2005-2013 Mango商城 版权所有</div>
	</div>
</div>

</body>
</html>