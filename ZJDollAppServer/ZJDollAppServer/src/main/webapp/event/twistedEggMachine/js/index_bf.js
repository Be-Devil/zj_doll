/**
 * Created by 51967 on 2018/3/9.
 */
/**
 * Created by 51967 on 2018/3/9.
 */
var hasInitSwiper = false;
var getBaseUrl = function() {
    var url = location.href;
    if (url.indexOf("mengquww.com") != -1) {
        baseUrl = "http://www.mengquww.com";
    } else {
        baseUrl = "http://122.11.48.251:6639/DollAppServer";
    }
    return baseUrl;
};
getBaseUrl();
// webView.getSetting().setDomStorageEnabled(true);
var uid = request('uid');
var loginKey = request('loginKey');
//动态获取uid和loginKey
function request(paramName) {
    var url = location.href;
    var paraString = url.substring(url.indexOf("?") + 1, url.length).split(
        "&");
    var params = {};
    for (i = 0; j = paraString[i]; i++) {
        params[j.substring(0, j.indexOf("=")).toLowerCase()] = j.substring(
            j.indexOf("=") + 1, j.length);
        if (paramName.toLowerCase() == j.substring(0, j.indexOf("=")).toLowerCase()) {
            var returnValue1 = j.substring(j.indexOf("=") + 1, j.length);
            if (typeof(returnValue1) != "undefined" && returnValue1 != null && returnValue1 != '') {
                return returnValue1;
            }
        }
    }
    var returnValue = params[paramName.toLowerCase()];
    if (typeof(returnValue) == "undefined") {
        return "";
    } else {
        return returnValue;
    }
}

function readyHtml() {
    getBaseUrl();
    uid = request('uid');
    loginKey = request('loginKey');
    $.ajax({
        url: baseUrl + '/api/twistedEggMachineWeb/getInfo',
        type: 'post',
        data: JSON.stringify({ "uid": uid, "loginKey": loginKey }),
        error: function(data) {
            //console.log(data);
        },
        success: function(data) {
            var data = eval('(' + data + ')');
            var coin = data.coin == undefined ? 0 : data.coin;
            var teggNum = data.teggNum == undefined ? 0 : data.teggNum;
            //金币
            var JB = '<li><img src="http://lianai-image-sys.qiniudn.com/e20180311/images/ico.png" alt=""></li> <li>' + coin + '</li> <li><img src="http://lianai-image-sys.qiniudn.com/e20180311/images/add.png" alt="" ></li>'
            $('.userJB').append(JB);
            //扭蛋任务
            var taggNUM = '<p>' + teggNum + '</p>';
            $('.taggNumber').html(taggNUM);
            //用户获奖
        }
    });
    $('.userJB').click(function() {
        window.location = "../../6@#1";
    })
}readyHtml();
$('.one').click(function () {
    alert(123)
})


