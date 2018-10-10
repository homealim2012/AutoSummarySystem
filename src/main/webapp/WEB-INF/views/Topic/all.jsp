<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="sub-container">
    <div id="bl-main" class="bl-main">
        <section>
            <div class="bl-box">
                <h2 class="bl-icon bl-icon-works">中心句扩展</h2>
            </div>
            <div class="bl-content">
                <jsp:include page="jlmlmr.jsp"/>
            </div>
            <span class="bl-icon bl-icon-close"></span>
        </section>

        <section>
            <div class="bl-box">
                <h2 class="bl-icon bl-icon-about">中心句扩展+流形排序</h2>
            </div>
            <div class="bl-content">
                <jsp:include page="jtmmr.jsp"/>
            </div>
            <span class="bl-icon bl-icon-close"></span>
        </section>

        <section>
            <div class="bl-box">
                <h2 class="bl-icon bl-icon-blog">SingleMR</h2>
            </div>
            <div class="bl-content">
                <jsp:include page="singlemr.jsp"/>
            </div>
            <span class="bl-icon bl-icon-close"></span>
        </section>

        <section>
            <div class="bl-box">
                <h2 class="bl-icon bl-icon-contact">随机选择</h2>
            </div>
            <div class="bl-content">
                <jsp:include page="random_choose.jsp"/>
            </div>
            <span class="bl-icon bl-icon-close"></span>
        </section>

    </div>
</div>
<script src="resources/all/boxlayout.js"></script>
<script>
    $(function () {
        Boxlayout.init();
    });
</script>