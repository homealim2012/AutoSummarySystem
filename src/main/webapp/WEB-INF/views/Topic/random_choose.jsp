<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="sub-container" id="abstract_random_choose">
    <!--
    <textarea id="abstract_random_choose" readonly="readonly" name="text" rows="20" cols="80"  wrap="SOFT" tabindex="0" dir="ltr" spellcheck="false" autocapitalize="off" autocomplete="off" autocorrect="off" style="font-size:24px; color:black; box-sizing: border-box; overflow-y: auto; overflow-x: auto;"></textarea>
    -->
</div>
<script>
    var sentences = random_choose.split("\n");
    var htmlContent = "";
    for (var i=0; i<sentences.length; i++) {
        if (htmlContent.length > 0) {
            htmlContent += "\n";
        }
        htmlContent += "<li style='font-size: 30px;'>" + (i+1) +"、"+
                sentences[i] +
                "</li>";
    }
    document.getElementById('abstract_random_choose').innerHTML = htmlContent;
</script>