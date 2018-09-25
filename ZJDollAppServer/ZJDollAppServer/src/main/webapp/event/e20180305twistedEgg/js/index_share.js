/**
 * Created by 51967 on 2018/3/8.
 */
var baseUrl = "http://122.11.48.251:6639/DollAppServer";
var downloadUrl = "http://www.mengquww.com/download/gw";

var getBaseUrl = function() {
    var url = location.href;
    if (url.indexOf("mengquww.com") != -1) {
        baseUrl = "http://www.mengquww.com";
    }else  {
        baseUrl = "http://122.11.48.251:6639/DollAppServer";
    }
    return baseUrl;
};
getBaseUrl();
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
    }
    var returnValue = params[paramName.toLowerCase()];
    if (typeof(returnValue) == "undefined") {
        return "";
    } else {
        return returnValue;
    }
}
// alert(uid);
jQuery(document).ready(function ($) {
	var myDate = new Date(); 
	var month = myDate.getMonth() + 1;         
	var day = myDate.getDate();
	$(".data").html(month + "月" + day + "号");
    function readyHtml() {
            $.ajax({
                url:baseUrl + '/api/twistedEggWeb/getList',
                type:'post',
                data:JSON.stringify({"uid":uid,"loginKey":loginKey}),
                success:function (data) {
                    var arr = [];
                    var data = eval('(' + data + ')');
                    var currAmt = data.currAmt;
                    if(currAmt == 0){
                        var TodayCZ = '<p>（ 今天暂未充值 ）</p>';
                        $('.dtzw').append(TodayCZ);
                    }else {
                        var TodayCZ = '<p>（ 今天充值'+currAmt+'元 ）</p>';
                        $('.dtzw').append(TodayCZ);
                    }


                    var twistedEggList = data.twistedEggList;
                    $.each(twistedEggList,function (index,ele) {
                        (function(index){
                            teggId = twistedEggList[index].teggId;
                            status = twistedEggList[index].status;
                            amt = twistedEggList[index].amt;
                            var innerHtml = ' <li><span>累计充值</span><p>满'+amt+'元</p> <button index="'+status+'" id="'+teggId+'" class="btn1">'+status+'</button></li>';
                            $('.box').append(innerHtml);
                        })(index)
                    });
                   btnArr(arr);
                   console.log(arr);
                    var arr1 = arr[0];
                    var arr2 = arr[1];
                    var arr3 = arr[2];
                    var arr4 = arr[3];
                    var arr5 = arr[4];
                    var arr6 = arr[5];
                    btnText(arr1,0);
                    btnText(arr2,1);
                    btnText(arr3,2);
                    btnText(arr4,3);
                    btnText(arr5,4);
                    btnText(arr6,5);
                    $('.btn1').click(btnClick1);

                },
                error:function (data) {
                    console.log(data);
                }
            })
    }
    readyHtml();
    $('.sx').click(function () {
        // alert(1231)
        window.location.reload();
    })

});

function btnArr(arr){
    $('.box button').each(function(index,ele){
        console.log(ele);
        var btnText = $(ele).text();
        arr.push(btnText);
    });
    return arr;
}
function btnText(arr,index){
    if (arr == -1){
        $('.btn1').eq(index).text('去充值').css({
            "background-color":'#ff9759'
        });
    }else if(arr == 0){
        $('.btn1').eq(index).text('领取').css({
            "background-color":'#ff6b6b'
        });
    }else if(arr == 1){
        $('.btn1').eq(index).text('去抽奖').css({
            "background-color":'#f34949'
        });
    }else if(arr == 2){
        console.log($('.btn1').eq(index));
        $('.btn1').eq(index).text('已过期').css({
            "background-color":'#FDF116',
            color:"#C28B17"
        });
    }
}
function btnClick1(){
    var btnIndex = $(this).attr("index");
    if(btnIndex == 0){
    	window.location = downloadUrl;
    }else if (btnIndex == 1){
    	window.location = downloadUrl;

    }else if (btnIndex == -1){
    	window.location = downloadUrl;

    }



}
