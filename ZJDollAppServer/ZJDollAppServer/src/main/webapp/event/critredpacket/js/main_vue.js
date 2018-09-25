var downloadUrl = "http://www.mengquww.com/download/gw";
$(function() {
	getPageInfo();
})

function getPageInfo() {
	$.post("../../api/redpacket/getPageInfo", 
			JSON.stringify(
					{"uid":request("uid")}
					),
    function(data) {
		var result = JSON.parse(data);
    	if(result.state) {
    		pageInfo.uname = result.name;
    		pageInfo.uhead = result.head;
    		pageInfo.uamt = result.amt;
    		pageInfo.records = result.records;
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

//领取
function get() {
	window.location.href = downloadUrl;
}

// 下载
function download() {
	window.location.href = downloadUrl;
}