<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="sub-container">
    <div id="word_count" style="height:600px;width: 1200px;margin: auto;"></div>
</div>
<script>
    var x_axis = []
    for (var i = 0; i < topic_num; i++) {
        x_axis.push("主题" + (i + 1));
    }
    option = {

        title: {
            text: '各个主题取前5/10个词的Coherence Score - 条形图'
        },
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data: ['M=5', 'M=10']
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
                data: Coherence_5,
                markLine: {
                    data: [
                        {type: 'average', name: '平均值'}
                    ]
                }
            },
            {
                name: 'M=10',
                type: 'bar',
                data: Coherence_10,
                markLine: {
                    data: [
                        {type: 'average', name: '平均值'}
                    ]
                }
            }
        ]
    };

    var chart = echarts.init(document.getElementById('word_count'), "dark");
    chart.setOption(option);

</script>