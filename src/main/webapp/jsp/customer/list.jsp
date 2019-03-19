<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<s:debug/>
<table>
    <tr>
        <td>客户名称</td>
        <td>客户级别</td>
        <td>客户来源</td>
        <td>所属行业</td>
        <td>联系地址</td>
        <td>联系电话</td>
        <td>操作</td>
    </tr>
    <c:forEach items="${customers}" var="customer">
        <tr>
            <td>${customer.custName}</td>
            <td>${customer.custLevel}</td>
            <td>${customer.custSource}</td>
            <td>${customer.custIndustry}</td>
            <td>${customer.custAddress}</td>
            <td>${customer.custPhone}</td>
            <td><a href="">删除</a></td>
            <td><a href="">修改</a></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
