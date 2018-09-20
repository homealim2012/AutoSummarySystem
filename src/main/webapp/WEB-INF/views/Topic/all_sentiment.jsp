<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="sub-container">
    <div id="sentiment" style="height:600px;width: 1200px;margin: auto;"></div>
</div>
<script>
    var x_axis = []
    for (var i = 0; i < topic_num; i++) {
        x_axis.push("主题" + (i + 1));
    }
    option = {
        title: {
            text: '各个主题正负极性分析 - 条形图'
        },
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data: ['M=5']
        },
        calculable: true,
        xAxis: [
            {
                type: 'category',
                data: x_axis
            }
        ],
        yAxis: [
            {
                type: 'value',
                scale: true

            }
        ],
        series: [
            {
                name: 'M=5',
                type: 'bar',
                data: Rate,
                markLine: {
                    data: [
                        {type: 'average', name: '平均值'}
                    ]
                },
                barMaxWidth: "40%"
            }
        ]
    };

    var chart = echarts.init(document.getElementById('sentiment'), "dark");
    chart.setOption(option);

</script>
