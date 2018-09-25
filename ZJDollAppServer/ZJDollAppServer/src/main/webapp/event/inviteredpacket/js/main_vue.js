var downloadUrl = "http://a.app.qq.com/o/simple.jsp?pkgname=os.zjwy.zengjing.doll";
$(function() {
	getUserInfo();
})

function getUserInfo() {
	$.post("../../api/user/baseInfo", 
			JSON.stringify(
					{"uid":request("uid")}
					),
    function(data) {
		var result = JSON.parse(data);
    	if(result.state) {
    		inviteInfo.uname = result.name;
    		inviteInfo.uhead = result.head;
    	}
    })
}

//参数
function request(paramName) {
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
}

// 领取
function get() {
	$.post("../../api/redpacket/saveInvitePreRecord", 
			JSON.stringify(
			    {uid: request("uid"),
		        unionId: request("unionId")}
			),
    function(data) {
    	var result = JSON.parse(data);
    	if(result.state) {
    		window.location.href = downloadUrl;
    	}
    })
}
