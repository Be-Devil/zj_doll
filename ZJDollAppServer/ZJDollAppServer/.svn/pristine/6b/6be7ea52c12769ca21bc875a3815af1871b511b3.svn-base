
function request(paramName) {
    var url = location.href;
    var paraString = url.substring(url.indexOf("?") + 1, url.length).split("&");
    var params = {}
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

function getBaseUrl() {
    var url = location.href;
    var baseUrl = "";
    if (url.indexOf("mengquww.com") != -1) {
        baseUrl = "http://www.mengquww.com";
    }else{
        baseUrl = 'http://t.xiehou360.com/DollAppServer';
    }
    //baseUrl = "../..";
    return baseUrl;
}


function loading() {
    lookVue.uid = request("uid");
    lookVue.loginKey = request("loginKey");
    $.post(getBaseUrl() + "/api/busOwner/getShareRecords", JSON.stringify({"uid": lookVue.uid, "loginKey": lookVue.loginKey}), function (data) {
        var jsonObj = JSON.parse(data);
        if (jsonObj != null && jsonObj.state) {
            lookVue.shareRecords = jsonObj.shareRecords
        }
    })
}

jQuery(document).ready(function ($) {
    //console.info("123")
    loading()

    /*刷新按钮*/
    $('.refurbish').click(function () {
        window.location.reload();
    });
    /*玩法说明按钮*/
    $('.rulesBtn').click(function () {
        window.location.href = "rules.html";
    });

});






