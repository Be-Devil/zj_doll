/**
 * Created by 51967 on 2018/1/22.
 */
//http://t.xiehou360.com/DollAppServer/api/goodsExchange/getList
jQuery(document).ready(function ($) {
    var baseUrl = "http://www.mengquww.com";
    var downloadUrl = "http://t.xiehou360.com/DollAppServer";
    var getBaseUrl = function() {
        var url = location.href;
        if (url.indexOf("mengquww.com") != -1) {
            baseUrl = "http://www.mengquww.com";
        }else{
            baseUrl = 'http://t.xiehou360.com/DollAppServer';
        }
        return baseUrl;
    };
    getBaseUrl();
    // 动态获取uid和loginKey 一进来就能获取用户的id和loginKey
    var uid = request('uid');
    var loginKey = request('loginKey');
    var index = request('index');
    var id = request('id');


    //动态获取uid和loginKey
    function request(paramName) {
        var url = location.href;
        var paraString = url.substring(url.indexOf("?") + 1, url.length).split(
            "&");
        var params = {};
        for (i = 0; j = paraString[i]; i++) {
            params[j.substring(0, j.indexOf("=")).toLowerCase()] = j.substring(
                j.indexOf("=") + 1, j.length);
        }
        var returnValue = params[paramName.toLowerCase()];
        if (typeof(returnValue) == "undefined") {
            return "";
        } else {
            return returnValue;
        }
    }
    //本地保持一份数据
    var dollSize = 0;
    var idArr = [];
    //发送请求
    function readyHtml() {
        $.ajax({
            url: baseUrl + '/api/goodsExchange/getRecycleList',
            type: 'post',
            data: JSON.stringify({"uid": uid, "loginKey": loginKey}),
            error: function (data) {
                var data = eval('(' + data + ')');
                var datamsg = data.msg;
                var toslt = '<div class="prompt prompt_6" style="display: none"> <p>'+datamsg+'</p></div>'
                $('.master').html(toslt);
                $(".prompt_6").show().delay(3000).hide(300);
            },
            success: function (data) {
                //转义成对象
                var data = eval('(' + data + ')');
                data.jewel = 0;
                //拿到对象
                var recycleList = data.recycleList;
                dollSize = recycleList.length;
                // 判断exchageDolls是否为空，渲染对应的页面
                if (recycleList != '') {
                    $('.empty').hide();
                    $('.gainTle').hide();
                    $('.recordAll').show();
                    $('.appyFooter').show();
                    // 遍历
                    $.each(recycleList, function (index, ele) {
                        var id = recycleList[index].id;
                        var path = recycleList[index].path;
                        var name = recycleList[index].name;
                        var jewel = recycleList[index].jewel;
                        var getTime = recycleList[index].getTime;
                        var dollId = recycleList[index].dollId;
                        //处理时间
                        var time = new Date(getTime);
                        var str = "";
                        var Hours = time.getHours();
                        var minutes = time.getMinutes();
                        var Seconds = time.getSeconds();
                        if (Hours < 10) {
                            Hours = '0' + Hours;
                        }
                        if (minutes < 10) {
                            minutes = '0' + minutes;
                        }
                        if (Seconds < 10) {
                            Seconds = '0' + Seconds;
                        }
                        str = time.getFullYear() + '-' + time.getMonth() + 1 + '-' + time.getDate() + ' ' + Hours + ':' + minutes + ':' + Seconds;
                        // 渲染商品
                        var htmlList = '<div class="recordList appyList"> <div class="moppetImg">' +
                            '<img src="' + path + '" alt=""></div>' +
                            '<div class="moppetDetail"> ' +
                            '<p class="moppetName">' + name + '</p>' +
                            '<p class="apapNumber">' + str + '</p> </div> ' +
                            '<div class="appyStatu">' +
                            '<img src="http://lianai-image-sys.qiniudn.com/mall/z_pic_1.png" alt=""><em>' + jewel + '</em></div>' +
                            '<div class="appybtn"><input type="checkbox" name="appy" id="' + index + '" index="' + jewel + '" class="checkboxBtn"><span class="appyPic"></span></div></div>';
                        $(htmlList).appendTo($('.recordAll'));

                        //渲染总数
                        var sumHtml = '<div class="submit_btn">' +
                            '<input type="checkbox" name="appyAll" class="checkboxBtnAll ' + id + '"/>' +
                            '<span class="appyPic" id="allBtn"></span>全选</div> ' +
                            '<div class="total"> ' +
                            '<span index="' + jewel + '">合计：<img src="http://lianai-image-sys.qiniudn.com/mall/z_pic_1.png" alt=""/></span><em>0</em> </div>'+
                            '<button type="button" class="sub sub1" id="' + index + '" index="' + jewel + '">获取钻石</button>';
                        $(sumHtml).appendTo($('.appyFooter'));
                    });
                    /*选择*/
                                      //初始化为0累加
                    var num = 0;
                    //设置空数组接收
                    var arr = [];
                    //判断选中
                    $(".recordAll").each(function (i, domEle) {
                        $(".checkboxBtn").id = i;
                        $(".checkboxBtn ").click(function () {
                            if ($(this).prop("checked")) {//点击选中
                                num++;
                                $(this).siblings("span").addClass("cityBtnHot");
                                $(this).siblings("span").addClass("cityBtnHot1");
                                //下面的代码改成：把recycleList[index].jewelNum 加到 data对象中的jewel属性
                                data.jewel += recycleList[this.id].jewel;
                                data.id = recycleList[this.id].id;
                                idArr.push(data.id);
                                //然后刷新对应的界面
                                refreshUI(data, data.id);
                            } else {//取消选中
                                num--;
                                //赋值id和钻石数
                                var jewelNum = $(this).attr('index');
                                //添加类
                                $(this).siblings("span").removeClass("cityBtnHot");
                                $(this).siblings("span").addClass("cityBtnHot1");
                                //下面的代码改成：data对象中的jewel属性里的值减去 recycleList[index].jewelNum的值
                                data.jewel -= recycleList[this.id].jewel;
                                data.id = recycleList[this.id].id;
                                //删除对应的id
                                Array.prototype.indexOf = function (val) {
                                    for (var i = 0; i < this.length; i++) {
                                        if (this[i] == val) return i;
                                    }
                                    return -1;
                                };
                                Array.prototype.remove = function (val) {
                                    var index = this.indexOf(val);
                                    if (index > -1) {
                                        this.splice(index, 1);
                                    }
                                };
                                idArr.remove(data.id);
                                //然后刷新对应的界面
                                refreshUI(data, data.id);
                            }
                        });
                    });
                    function sum(jewelNum) {
                        arr.push(jewelNum);
                    }

                    function idsum(id) {
                        idArr.push(id);
                    }
                    /**
                     * 刷新界面
                     */
                    function refreshUI(data, id) {
                        $('.total em').text(data.jewel);
                        // idArr.push(id);

                    }

                    //全选
                    $(".appyFooter").each(function (i, domEle) {
                        $(".checkboxBtnAll").click(function () {
                            if ($(this).prop("checked")) {//点击选中
                                var v = $(this).attr("checked");
                                $("input[type=checkbox]").prop("checked", true);
                                $("input[type=checkbox]").siblings("span").addClass("cityBtnHot");
                                //清零
                                num = dollSize;
                                data.jewel = 0;
                                idArr = [];
                                // 遍历
                                for (var i = 0; i < recycleList.length; i++) {
                                    data.jewel += recycleList[i].jewel;
                                    arr.push(data.jewel);
                                    data.id = recycleList[i].id;
                                    idArr.push(data.id);
                                }
                                refreshUI(data, data.id);
                            } else {//取消选中
                                $("input[type=checkbox]").prop("checked", false);
                                $("input[type=checkbox]").siblings("span").removeClass("cityBtnHot");
                                //遍历数组
                                for (var i = 0; i < recycleList.length; i++) {
                                    //总数直接等于0
                                    data.jewel = 0;
                                    idArr.length = 0;
                                    window.location.reload();
                                }
                                refreshUI(data, data.id);
                            }
                        });
                    });
                    //蒙版
                    $('.sub1').click(function () {
                        var jewelNum = $(this).attr('index');
                        var htmlMsater = '<div class="overlay" style="display: none"></div><div class="waDhPop" style="display: none"><p>确认回收<em>' + num + '</em>个娃娃获取<em>' + data.jewel + '</em>钻？</p><span class="buttonBox ks-clear"><i class="dhCancle">取消</i><i class="dhConfirm">确定</i></span></div>';
                        $('.master').append(htmlMsater);

                        $('.overlay').css({
                            display: 'block'
                        });
                        $('.waDhPop').css({
                            display: 'block'
                        });
                        $('.dhCancle').click(function () {
                            $('.master').hide();
                            window.location.reload();
                        });
                        $('.dhConfirm').click(function () {
                            if(idArr == '' || idArr == null){
                                $('.master').hide();
                                $('.prompt_3').show().delay(3000).hide(300);
                                window.onload = function(){
                                    setTimeout("location.reload()",3000);
                                };
                                window.onload();
                            }else {
                                $('.master').hide();
                                $.ajax({
                                    url: baseUrl + '/api/goodsExchange/recycleList',
                                    type: 'post',
                                    //cache:true,
                                    data: JSON.stringify(
                                        {
                                            "uid": uid,
                                            "loginKey": loginKey,
                                            "dollList": idArr
                                        }),
                                    error: function (data) {
                                        var data = eval('(' + data + ')');
                                        var datamsg = data.msg;
                                        var toslt = '<div class="prompt prompt_6" style="display: none"> <p>'+datamsg+'</p></div>'
                                        $('.master').html(toslt);
                                        $(".prompt_6").show().delay(3000).hide(300);
                                    },
                                    success: function (data) {
                                        var data = eval('(' + data + ')');
                                        if (data.state) {
                                            $('.overlay').hide();
                                            $('.waDhPop').hide();
                                            $(".prompt_4").show().delay(3000).hide(300);
                                            window.onload = function(){
                                                setTimeout("location.reload()",3000);
                                            }
                                            window.onload();
                                        } else {
                                            $('.overlay').hide();
                                            $('.waDhPop').hide();
                                            $(".prompt_3").show().delay(3000).hide(300);
                                            window.onload = function(){
                                                setTimeout("location.reload()",3000);
                                            };
                                            window.onload();
                                        }
                                    }
                                });
                            }
                        });
                    });
                }
            }
        })
    }
    readyHtml();
});
