/*基础http通用接口*/
    var baseUrl = "http://www.nuannuanzhibo.com/";

    var downloadUrl = "http://www.nuannuanzhibo.com/qrcode/main/index.html";

    var getBaseUrl = function() {　　　　
        var url = location.href;
        if (url.indexOf("tj-nbiot.com") != -1) {
            baseUrl = "http://www.nuannuanzhibo.com/";
        } else if (url.indexOf("t.xiehou360.com") != -1) {
            baseUrl = 'http://t.xiehou360.com/LiveWebServer/';
        }else if (url.indexOf("nuannuanzhibo.com") != -1) {
        	baseUrl = "http://www.nuannuanzhibo.com/";
        }
        return baseUrl;
    };


    var getDownloadUrl = function() {　　　　
        return downloadUrl;
    };

    var request = function(paramName) {
        var url = location.href;
        var paraString = url.substring(url.indexOf("?") + 1, url.length).split(
            "&");
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
    };

    //读取cookies 
    var getCookie = function(name) {
        var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
        //alert(arr,reg);
        if (arr = document.cookie.match(reg))

            return unescape(arr[2]);

        else
            return null;
    };

    function setCookie(name, value) {
        var Days = 30;
        var exp = new Date();
        exp.setTime(exp.getTime() + Days * 24 * 60 * 60 * 1000);
        document.cookie = name + "=" + escape(value) + ";expires=" + exp.toGMTString();
    }


    var isWeixinVisit = function() {
        var ua = navigator.userAgent;
        var isWeixin = /MicroMessenger/i.test(ua);
        return isWeixin;
    }

    var isShareVisit = function() {
        var shareFlag=request('shareFlag');
        if (shareFlag==1) {
            return true;
        }
        return false;
    }
