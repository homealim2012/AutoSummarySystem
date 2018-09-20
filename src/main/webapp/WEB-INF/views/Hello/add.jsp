<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";

%>

<!DOCTYPE html>
<html>
<head>
    <base href="<%=basePath%>">
</head>

<body>
<form:form action="hello/add" method="post" modelAttribute="comment">
    comment_id:<form:input path="comment_id"/><form:errors path="comment_id"/><br>
    comment_content:<form:input path="comment_content"/><form:errors path="comment_content"/><br>
    comment_time:<form:input path="comment_time"/><form:errors path="comment_time"/><br>
    <input type="submit" value="提交"/>
</form:form>
<img src="resources/111.jpg"/>
</body>
</html>