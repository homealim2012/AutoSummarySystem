<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<body>
<div style="height: 100%;width: 100%;">
    <div id="root">
    </div>
</div>
<script src="resources/all/photo/index.js"></script>
<script>
    var ig = new ImageGallery({
        "Id": "#root",
        "imglist": photoList
    });
</script>
</body>
</html>
