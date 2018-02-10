<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title></title>
    <script src="${pageContext.request.contextPath}/statics/js/jquery-2.2.3.min.js"></script>
    <script src="${pageContext.request.contextPath}/statics/js/d3.min.js"></script>
    <script src="${pageContext.request.contextPath}/statics/js/d3.layout.cloud.js"></script>
</head>
<body>
<button onclick="similar()">我做了相似度推荐文件</button>
<div id="word_cloud">
    <script src="${pageContext.request.contextPath}/statics/js/node.js" ></script>
</div>
<script type="text/javascript">
    function similar() {
        $.get("/similarity",function () {
            alert("相似度推荐文件创建成功");
        })
    }


    var fill = d3.scale.category20();
    //输出20种类别的颜色 ---颜色比例尺

    //word cloud layout  词云布局，d3布局中为词云设立的单独的一种布局方式
    //d3.layout.cloud() 构造一个词云的布局实例
    //on(type,listener) 注册特定的listener来接收布局中特定类型的event。
    //目前只有 word和end这两种event是被支持的。
    //word这种event在该布局完成对每一个word的布局时被调度。
    //end这种活动在改布局完成对所有的words的布局时被调度。
    //注册的listener通过两个参数被调度：
    //被成功布局的单词数组
    //以[{x0,y0},{x1,y1}]形式为界限，代表words范围    a bounds object of the form [{x0, y0}, {x1, y1}] representing the extent of the placed objects.
    //

    //start 布局算法   初始化单词数组上的各种参数，并且从最大的单词开始布局单词，
    //从矩形区域的中间开始，每一个单词在布局时都要检测是否与先前已经布局好的单词位置冲突。
    //一旦检测到冲突，该算法会沿着螺旋线重新布局该单词。
    //如果一个单词不能在沿着螺旋线的任何地方被布局，该单词最终将不会显示在词云图上，该问题可能在后续版本中被解决。

    //stop() 停止布局算法

    // timeInterval([time])   布局时通过setInterval来避免浏览器的event loop被锁住。

    //words([words array].map(function(d)(return{text:d;size:某一个数值}))
    //为words数组中的每一个word分配一个字体大小
    var color = d3.scale.linear()
        .domain([0,1,2,3,4,5,6,10,15,20,100])
        .range(["#ddd", "#ccc", "#bbb", "#aaa", "#999", "#888", "#777", "#666", "#555", "#444", "#333", "#222"]);


    d3.layout.cloud().size([600, 300]) //size([x,y])  词云显示的大小
    //map 返回新的object数组
        .words(${words})
        //~~的作用是单纯的去掉小数部分，不论正负都不会改变整数部分
        //这里的作用是产生0 1
        .rotate(0)
        .font("Impact")
        .fontSize(function(d) { return d.size; })
        .on("end", draw)//结束时运行draw函数
        .start();


    //append()使用函数在指定元素的结尾添加内容
    //transform:translate(x,y)  定义2d旋转，即平移，向右平移x,向下平移y
    function draw(words) {
        d3.select("#word_cloud").append("svg")//根据id选择父对象插入svg
            .attr("width", "100%")
            .attr("height", "100%")
            .attr("viewBox","0 0 900 400")
            .attr("style", "border: 1px solid black")
            .attr("preserveAspectRatio","xMaxYMax meet")
            .attr("class", "wordcloud")
            .append("g")
            .attr("transform", "translate(400,200)")
            .selectAll("text")
            .data(words)
            .enter().append("text")
            .style("font-size", function(d) { return d.size + "px"; })
            .style("fill", function(d, i) { return color(i); })
            .attr("transform", function(d) {
                return "translate(" + [d.x, d.y] + ")rotate(" + d.rotate + ")";
            })
            .text(function(d) { return d.text; });
    }


</script>
</body>
</html>
