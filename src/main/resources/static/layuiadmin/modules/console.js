/** layuiAdmin.std-v1.0.0 LPPL License By http://www.layui.com/admin/ */
;layui.define(function (e) {
    layui.use(["admin", "carousel"], function () {
        var e = layui.$, t = (layui.admin, layui.carousel), a = layui.element, i = layui.device();
        e(".layadmin-carousel").each(function () {
            var a = e(this);
            t.render({
                elem: this,
                width: "100%",
                arrow: "none",
                interval: a.data("interval"),
                autoplay: a.data("autoplay") === !0,
                trigger: i.ios || i.android ? "click" : "hover",
                anim: a.data("anim")
            })
        }), a.render("progress")
    }), layui.use(["carousel", "echarts"], function () {
        var e = layui.$, t = layui.carousel, a = layui.echarts, i = [],l = [ {
            title: {text: "最近一周新增的用户量", x: "center", textStyle: {fontSize: 14}},
            tooltip: {trigger: "axis", formatter: "{b}<br>新增用户：{c}"},
            xAxis: [{type: "category", data: []}],
            yAxis: [{type: "value"}],
            series: [{type: "line", data: []}]
        },{
            title: {text: "访客浏览器分布", x: "center", textStyle: {fontSize: 14}},
            tooltip: {trigger: "item", formatter: "{a} <br/>{b} : {c} ({d}%)"},
            legend: {orient: "vertical", x: "left", data: ["Chrome", "Firefox", "IE 8.0", "Safari", "其它浏览器"]},
            series: [{
                name: "访问来源",
                type: "pie",
                radius: "55%",
                center: ["50%", "50%"],
                data: [{value: 9052, name: "Chrome"}, {value: 1610, name: "Firefox"}, {
                    value: 3200,
                    name: "IE 8.0"
                }, {value: 535, name: "Safari"}, {value: 1700, name: "其它浏览器"}]
            }]
        },{
            title: {text: "今日流量趋势", x: "center", textStyle: {fontSize: 14}},
            tooltip: {trigger: "axis"},
            legend: {data: ["", ""]},
            xAxis: [{
                type: "category",
                boundaryGap: !1,
                data: ["06:00", "06:30", "07:00", "07:30", "08:00", "08:30", "09:00", "09:30", "10:00", "11:30", "12:00", "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00", "17:30", "18:00", "18:30", "19:00", "19:30", "20:00", "20:30", "21:00", "21:30", "22:00", "22:30", "23:00", "23:30"]
            }],
            yAxis: [{type: "value"}],
            series: [{
                name: "PV",
                type: "line",
                smooth: !0,
                itemStyle: {normal: {areaStyle: {type: "default"}}},
                data: [111, 222, 333, 444, 555, 666, 3333, 33333, 55555, 66666, 33333, 3333, 6666, 11888, 26666, 38888, 56666, 42222, 39999, 28888, 17777, 9666, 6555, 5555, 3333, 2222, 3111, 6999, 5888, 2777, 1666, 999, 888, 777]
            }, {
                name: "UV",
                type: "line",
                smooth: !0,
                itemStyle: {normal: {areaStyle: {type: "default"}}},
                data: [11, 22, 33, 44, 55, 66, 333, 3333, 5555, 12666, 3333, 333, 666, 1188, 2666, 3888, 6666, 4222, 3999, 2888, 1777, 966, 655, 555, 333, 222, 311, 699, 588, 277, 166, 99, 88, 77]
            }]
        }
        ]
        console.log(l)
        e.ajax({
            type: 'post',// ajax请求的方式，post或get
            url: '/ims/back/statistics/getAddDailyUsers'  ,
            // contentType: "application/json",// 默认就是表单数据(不写)，如果此时要传递的是json字符串，
            // 那么此时就用application/json,如果此时要传递图片"multipart/form-data，还有数组，集合，还有对象，那么contentType必须指定为false
            data: {
                queryDateTime: new Date().toLocaleDateString(),
                days: 7
            },// 要上传的参数,把数组对象转换为JSON字符串
            dataType: 'json',// 服务器响应数据：json,text,html,xml
            processData: true,//布尔值,一般都不用设置，规定通过请求发送的数据是否转换为查询字符串。默认是 true。如果此时上传的时候，有图片，这里必须设置false,
            success: function (res) {// 请求成功，回调函数,data，指的就是服务器返回的数据
                console.log(res)
                let  time  = []
                let sdata = []

                for(var key in  res.data){
                    time.push(key)
                    sdata.push(res.data[key])
                    // console.log("属性：" + key + ",值：" + map[key]);
                }
                // res.data.forEach( (v,k) => {
                //     time.push(k)
                //     sdata.push(v)
                // })
                l[0].xAxis[0].data =time
                l[0].series[0].data = sdata
            },
        });
        console.log(l)

        var n = e("#LAY-index-dataview").children("div"), r = function (e) {
            i[e] = a.init(n[e], layui.echartsTheme), i[e].setOption(l[e]), window.onresize = i[e].resize
        };
        if (n[0]) {
            r(0);
            var o = 0;
            t.on("change(LAY-index-dataview)", function (e) {
                r(o = e.index)
            }), layui.admin.on("side", function () {
                setTimeout(function () {
                    r(o)
                }, 300)
            }), layui.admin.on("hash(tab)", function () {
                layui.router().path.join("") || r(o)
            })
        }

        // i[e] = a.init(n[e], layui.echartsTheme), i[e].setOption(l[e]), window.onresize = i[e].resize

    }), layui.use("table", function () {
        var e = (layui.$, layui.table);
        e.render({
            elem: "#LAY-index-topSearch",
            url: layui.setter.base + "json/console/top-search.js",
            page: !0,
            cols: [[{type: "numbers", fixed: "left"}, {
                field: "keywords",
                title: "关键词",
                minWidth: 300,
                templet: '<div><a href="https://www.baidu.com/s?wd={{ d.keywords }}" target="_blank" class="layui-table-link">{{ d.keywords }}</div>'
            }, {field: "frequency", title: "搜索次数", minWidth: 120, sort: !0}, {
                field: "userNums",
                title: "用户数",
                sort: !0
            }]],
            skin: "line"
        }), e.render({
            elem: "#LAY-index-topCard",
            url: layui.setter.base + "json/console/top-card.js",
            page: !0,
            cellMinWidth: 120,
            cols: [[{type: "numbers", fixed: "left"}, {
                field: "title",
                title: "标题",
                minWidth: 300,
                templet: '<div><a href="{{ d.href }}" target="_blank" class="layui-table-link">{{ d.title }}</div>'
            }, {field: "username", title: "发帖者"}, {field: "channel", title: "类别"}, {
                field: "crt",
                title: "点击率",
                sort: !0
            }]],
            skin: "line"
        })
    }), e("console", {})
});
