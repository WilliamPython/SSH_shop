<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="span24">
	<ul class="mainNav">
	
		<li>
			<a href="${pageContext.request.contextPath }/index.action">首页</a>
			|
		</li>
	<s:iterator var="category" value="#session.CategoryList">
		<li>
			<a href="${pageContext.request.contextPath }/product_findByCid?cid=<s:property value="#category.cid"/>&page=1"><s:property value="#category.cname"/></a>
			|
		</li>
	</s:iterator>	
	</ul>
</div>