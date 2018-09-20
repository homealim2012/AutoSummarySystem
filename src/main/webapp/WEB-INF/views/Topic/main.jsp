<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";

%>

<!DOCTYPE html>
<html class="no-js">
<head>
    <base href="<%=basePath%>">
    <title>摘要展示 - JLTMMR自动文本摘要系统</title>
    <script src="resources/statistics.js"></script>
    <link rel="shortcut icon" href="resources/favicon.ico"/>
    <link rel="bookmark" href="resources/favicon.ico"/>
    <link rel="stylesheet" type="text/css" href="resources/particles.css"/>
    <link rel="stylesheet" type="text/css" href="resources/load.css"/>
    <link rel="stylesheet" type="text/css" href="resources/component_button.css"/>
    <link rel="stylesheet" type="text/css" href="resources/login/css/component_modal.css"/>
    <link rel="stylesheet" type="text/css" href="resources/component_main.css"/>
    <link rel="stylesheet" type="text/css" href="resources/component_menu.css"/>
    <link rel="stylesheet" type="text/css" href="resources/all/component_bigmenu.css"/>
    <link rel="stylesheet" type="text/css" href="resources/all/photo/index.css"/>
    <link rel="stylesheet" type="text/css" href="resources/pagePagination.css"/>
    <style>html, body {
        font-family: "微软雅黑";
    }</style>
    <script src="resources/modernizr-custom.js"></script>
</head>
<body>

<nav class="menu menu--valentine">
    <ul class="pages-nav menu__list">
        <li class="pages-nav__item menu__item"><a class="menu__link link link--page" href="<%=basePath%>">首页</a></li>
        <li class="pages-nav__item menu__item menu__item--current"><a class="menu__link link link--page"
                                                                      href="#page-all">分析的摘要</a></li>
        <li class="pages-nav__item menu__item"><a class="menu__link link link--page" href="#page-topic">查看主题</a></li>
        <li class="pages-nav__item menu__item"><a class="menu__link link link--page" href="#page-document">查看原始文档</a>
        </li>
        <li class="pages-nav__item menu__item"><a class="menu__link link link--page" href="#page-word">查看单词</a></li>
        <!-- <li class="pages-nav__item menu__item"><a class="menu__link link link--page" href="User/login">退出</a></li> -->
    </ul>
</nav>
<!-- /navigation-->
<!-- pages stack -->
<div class="pages-stack">
    <!-- page -->
    <div class="page" id="page-all" data-page-url="Topic/all"></div>
    <div class="page" id="page-topic" data-page-url="Topic/topic"></div>
    <div class="page" id="page-document" data-page-url="Topic/document"></div>
    <div class="page" id="page-word" data-page-url="Topic/word"></div>
</div>
<!-- /pages-stack -->
<button class="menu-button"><span>Menu</span></button>


<div id="scene" class="scene unselectable"
     data-friction-x="0.1"
     data-friction-y="0.1"
     data-scalar-x="25"
     data-scalar-y="15">
    <div id="particles-js" style="" class="layer" data-depth="0.10"></div>
</div>
<script src="resources/login/js/parallax.js"></script>
<script src="resources/login/js/classie.js"></script>
<script src="resources/jquery.min.js"></script>
<script src="resources/particles.js"></script>
<script src="resources/clipboard.min.js"></script>
<script src="resources/echarts/echarts.common.min.js"></script>
<script src="resources/echarts/dark.js"></script>
<script src="resources/jquery-ui.js"></script>
<script src="resources/jquery.pagination.js"></script>
<script src="resources/wordcloud2.js"></script>
<script src="resources/tsne/d3.min.js"></script>
<script src="resources/tsne/scienceai-tsne.min.js"></script>
<script src="resources/tsne/karpathy-tsne.js"></script>
<script src="resources/tsne/tsneez.js"></script>
<script src="resources/main.js"></script>

<script>
    $scene = $('#scene');
    var parallax = new Parallax($scene[0]);
    var info = JSON.parse(localStorage.getItem("info"));
    var jlmlmr = info["jlmlmr"];
    var jlmlmr_abs = info["jlmlmr_abs"];
    var singlemr = info["singlemr"];
    var random_choose = info["random_choose"];
    var filter_sentence = info["filter_sentence"];
    var ori_sentence = info["ori_sentence"];
    var topic_words = info["topic_words"];
    var topic_words_score = info["topic_words_score"];
    var time_stamp = info["time_stamp"];
    /*
    if (localStorage.getItem("zan_cai_status") == null) {
        var zanCaiStatus = []; //0 means no, 1 means zan, -1 means cai
        for (var i=0; i<100; i++) {
            zanCaiStatus[i] = 0;
        }
        localStorage.setItem("zan_cai_status", JSON.stringify(zanCaiStatus));
    }
    var zanCaiStatus = JSON.parse(localStorage.getItem("zan_cai_status"));
    */
    var zanCaiStatus = []; //0 means no, 1 means zan, -1 means cai
    for (var i=0; i<100; i++) {
        zanCaiStatus[i] = 0;
    }
    var zanCaiSentenceStatus = []; //0 means no, 1 means zan, -1 means cai
    for (var i=0; i<100; i++) {
        zanCaiSentenceStatus[i] = 0;
    }
</script>
</body>
</html>
