/**
 * Created by 51967 on 2018/1/22.
 */

jQuery(document).ready(function($) {
 var baseUrl = "https://www.realgamecloud.com";
    var downloadUrl = "http://t.xiehou360.com/DollPubAppServer";
    var getBaseUrl = function() {
        var url = location.href;
        if (url.indexOf("realgamecloud.com") != -1) {
            baseUrl = "https://www.realgamecloud.com";
        }else{
            baseUrl = 'http://t.xiehou360.com/DollPubAppServer';
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
        //发送请求
        $.ajax({
            url: baseUrl + '/api/dollCompose/getDollDetail',
            type: 'post',
            data: JSON.stringify(
                {
                    "uid":uid,
                    "loginKey":loginKey,
                    "id":id,
                    "type":1
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
                var datamsg = data.data;
                var id = datamsg.id;
                var name = datamsg.name;
                var path = datamsg.path;
                var jewelNum = datamsg.jewelNum;
                var imgRoom = datamsg.imgRoom;
                var html = '<section class="patchList comList" id="'+id+'"><div class="patchList_pic comList_pic">'+
                    '<img src="'+path+'"/></div>'+
                    '<div class="patchList_data">'+
                    '<p class="p_name c_name">'+name+'</p>'+
                    '<p class="p_need j_need new_need">需要：<img src="http://lianai-image-sys.qiniudn.com/mall/z_pic_1.png" alt=""/><b>' + jewelNum + '</b></p></div></section>'+
                    '<section class="com_pic">'+
                    '<h6>娃娃详情</h6>'+
                    '<img src="'+imgRoom+'" alt=""/></section>';
                $('.particulars').append(html);
                //合成按钮
                var btnAlert ='<div class="com_btn" ><button class="com_btn1"  >兑换</button></div>'
//添加到particulars前
                $('.particulars').append(btnAlert);
                //点击取消或正确
                $('.com_btn1').click(function () {
                    //渲染
                    var htmlMsater = ' <div class="overlay" style="display: none"></div><div class="waDhPop" style="display: none"> <p>确认消耗<em>'+jewelNum+'</em>块钻石</p><p>兑换<em>'+datamsg.name+'</em>?</p > <span class="buttonBox ks-clear"> <i class="dhCancle">取消</i> <i class="dhConfirm">确定</i> </span></div>';
                    $('.master').html(htmlMsater);
                    //点击取消
                    $('.overlay').css({
                        display : 'block'
                    });
                    $('.waDhPop').css({
                        display : 'block'
                    });
                    $('.dhCancle').click(function () {
                        $('.master').hide();
                        window.location.reload();
                    });

                    //点击确认
                    $('.dhConfirm').click(function (){
                        $.ajax({
                            url: baseUrl + '/api/goodsExchange/exchange',
                            type: 'post',
                            //cache:true,
                            data: JSON.stringify(
                                {
                                    "uid":uid,
                                    "loginKey":loginKey,
                                    'dollId':id
                                }
                            ),
                            error: function (data) {
                                var data = eval('(' + data + ')');
                                var datamsg = data.msg;
                                var toslt = '<div class="prompt prompt_6" style="display: none"> <p>'+datamsg+'</p></div>'
                                $('.master').html(toslt);
                                $(".prompt_6").show().delay(3000).hide(300);
                            },
                            success: function (data) {
                                var data = eval('(' + data + ')');
                                if(data.state){
                                    $('.overlay').hide();
                                    $('.waDhPop').hide();
                                    $(".prompt_1").show().delay(3000).hide(300);
                                } else if (data.result == 600){
                                    $('.overlay').hide();
                                    $('.waDhPop').hide();
                                    $(".prompt_5").show().delay(3000).hide(300);
                                }else {
                                    $('.overlay').hide();
                                    $('.waDhPop').hide();
                                    $(".prompt_2").show().delay(3000).hide(300);
                                }
                            }
                        });
                    })
                });
            }
        });
})
