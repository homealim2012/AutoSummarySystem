<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>

<div class="sub-container" id="abstract_jltmmr">
    <!--
    <textarea id="abstract_jlmlmr" readonly="readonly" name="text" rows="20" cols="80"  wrap="SOFT" tabindex="0" dir="ltr" spellcheck="false" autocapitalize="off" autocomplete="off" autocorrect="off" style="font-size:24px; color:black; box-sizing: border-box; overflow-y: auto; overflow-x: auto;"></textarea>
    -->

</div>
<script>
    var upImgSrc = "resources/image/up.png";
    var up2ImgSrc = "resources/image/up2.png";
    var downImgSrc = "resources/image/down.png";
    var down2ImgSrc = "resources/image/down2.png";

    var sentences = jlmlmr.split("\n");
    var htmlContent = "";
    for (var i=0; i<sentences.length; i++) {
        if (htmlContent.length > 0) {
            htmlContent += "\n";
        }
        htmlContent += "<li style='font-size: 30px;'>" +
                           (i+1) +"、"+
                            sentences[i] +
                        "</li>";
    }
    document.getElementById('abstract_jltmmr').innerHTML = htmlContent;

</script>