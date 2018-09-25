/**
 * Created by 51967 on 2018/2/6.
 */

$(document).ready(function () {
    //路径
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
    // var baseUrl = 'http://122.11.48.251:6639/DollAppServer';
    //获取uid和loginKey
    var uid = request('uid');
    var loginKey = request('loginKey');
    //立一个flag=false
    var flag = false;
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
    //渲染头像
    function readyHtml() {
        $.ajax({
            url:baseUrl +'/api/user/baseInfo',
            type:'post',
            data:JSON.stringify({
                "uid":uid,
                "loginKey":loginKey
            }),
            error:function (data) {
                var data = eval('(' + data + ')');
                var msg = data.msg;
                var check = '<div class="reminder_iph" style="display: block"> ' +
                    '<p>'+ msg +'</p> </div>'
                $('.account').html(check);
            },
            success:function (data) {
                var data = eval('(' + data + ')');
                var uid = data.uid;
                var name = data.name;
                var head = data.head;
                var User =' <ul> ' +
                    '<li>正在绑定的萌趣号</li> ' +
                    '<li><img src="'+head+'" alt=""></li> ' +
                    '<li>' + name +'</li> ' +
                    '<li>'+ uid + '</li> </ul>'
                $('.portrait').append(User);
            }
        })
    };
    //按下事件
    $("#inp").keydown(function(event) {
        var reg = /^\d{11}$/;
        var value = $(this).val();
        var e = event || window.event || arguments.callee.caller.arguments[0];
        if(e.keyCode === 8) {
            return true;
        }
        if(e.keyCode == 32) {
            return false;
        }
        if(reg.test(value)) {
            return false;
        }
    });
    //按钮变红
    $("#inp").focus(function(){
        $('#btn').addClass('active');
        $('#submit').addClass('active1');
    });
    //判断号码
    var myreg = /^[1][3,4,5,7,8][0-9]{9}$/;
    $("#inp").blur(function(){
        //获取值
        var pone = $("#inp").val();
        if(pone == ''){
            $('.reminder_iph').show().delay(3000).hide(300);
            $('#btn').removeClass('active');
            $('#submit').removeClass('active1');
            flag = false;
            return false;
        }else if(!myreg.test(pone)) {
            $('.reminder_iph1').show().delay(3000).hide(300);
            $('#btn').removeClass('active');
            $('#submit').removeClass('active1');
            flag = false;
            return false;
        }else {
            flag = true;
        }
    });
//60s倒计时
    $('#btn').click(function () {
        var pone = $("#inp").val();
        if(pone == ''){
            $('.reminder_iph').show().delay(3000).hide(300);
            return false;
        }else if(!myreg.test(pone)){
            $('.reminder_iph1').show().delay(3000).hide(300);
            return false;
        }else{
            $.ajax({
                url:baseUrl + '/api/redpacket/checkcode',
                type:'post',
                data:JSON.stringify({
                    "uid":uid,
                    "loginKey":loginKey,
                    "number":pone
                }),
                error:function () {

                },
                success:function (data) {
                    var data = eval('(' + data + ')');
                    if(data.state != true){
                        $('.reminder_iph3').show().delay(3000).hide(300);
                        $('#tip').html(data.msg);
                        return false;
                    }
                    function invokeSettime(obj){
                        var countdown=60;
                        settime(obj);
                        function settime(obj) {
                            if (countdown == 0) {
                                $(obj).attr("disabled",false);
                                $(obj).text("发送");
                                countdown = 60;
                                $('#btn').addClass('active');
                                return;
                            } else {
                                $(obj).attr("disabled",true);
                                $(obj).text( + countdown + " s");
                                countdown--;
                                $('#btn').removeClass('active');
                            }
                            setTimeout(function() {
                                    settime(obj) }
                                ,1000)
                        }
                    }
                    new invokeSettime("#btn");
                }
            });
        }
    });
//    提交表单s
    $('#submit').click(function () {
        var pone = $("#inp").val();
        var verify = $("#yz").val();
        if (pone == '' || verify == ''){
            $('.reminder_verify2').show().delay(3000).hide(300);
        }else if(!myreg.test(pone)){
            $('.reminder_iph1').show().delay(3000).hide(300);
        } else {
            $.ajax({
                url:baseUrl +'/api/redpacket/bindPhone',
                type:'post',
                data:JSON.stringify({
                    "uid":uid,
                    "loginKey":loginKey,
                    "number":pone,
                    "checkCode":verify
                }),
                error:function () {
                    $('.reminder_verify1').show().delay(3000).hide(300);
                    // window.location.reload();
                },
                success:function (data) {
                    var data = eval('(' + data + ')');
                   if (data.state == true){
                       $('.account').hide();
                       $('.accomplish').show();
                   }else {
                       $('.reminder_verify').show().delay(3000).hide(300);
                       $('#tip1').html(data.msg);
                       // window.location.reload();
                   }
                }
            })
        }
    });
    readyHtml();
});