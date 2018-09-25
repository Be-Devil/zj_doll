$(function () {
    var needRefresh = sessionStorage.getItem("need-refresh");
    if(needRefresh){
        location.reload();
        sessionStorage.removeItem("need-refresh");
    }
});
jQuery(document).ready(function($) {
    //判断路由是线上还是线下
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
    var id = request('id');
    var index = request('index');
    if(index == '' || index == null) {
        index = 0;
    }

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

    /*Tab切换*/
    // var defaultTabId="";
    $('.tab').click(function () {
        var ele = $(this);
        ele.addClass("listHot").siblings(".tab").removeClass("listHot");
        var index = ele.index();
        $('section.listBox>div').eq(index).show().siblings().hide();
        if( index == 0){
            conversionPost();
        }else{
            compoundPost();
        }
    });
    $('.tab').eq(index).trigger('click');

    //商品兑换
    //本地储存一份数据
    var conversionAjaxData;
    function conversionPost(){
        $.ajax({
            url: baseUrl + '/api/goodsExchange/getList',
            type: 'post',
            data: JSON.stringify({"uid":uid,"loginKey":loginKey}),
            // error: function () {
            //     alert('error');
            // },
            success: function (data) {
                //判断数据更新，如果有根据就发送请求，没有就拿本地的。
                if (conversionAjaxData != data) {
                    conversionAjaxData = data;
                    conversionSuccessCallBack(conversionAjaxData);
                }
            }
        });
    }
    function conversionSuccessCallBack(data) {
        var data = eval('(' + data + ')');
        var jewel = data.jewel;
        //渲染钻石
        var listHtml ='<div class="jewelNum"><div class="jewelNumTop">'+
            '<a href="gain.html?uid='+uid+'&loginKey='+loginKey+'"><span class="gainList"><img src="http://lianai-image-sys.qiniudn.com/mall/z_pic.png" alt=""/> <em>'+jewel+'</em><img src="http://lianai-image-sys.qiniudn.com/2018125/btn_chongzhi_24_normal@2x.png" alt="" class="add"/></span></a></div>'+
            '<p>回收“背包-寄存中”的娃娃可获得钻石，钻石可用于兑换不同的物品哟~~</p></div>';
        $('.conversion').before(listHtml);
        //渲染对应的数据
        var exchangeDolls = data.exchangeDolls;
        $.each(exchangeDolls, function (index, ele) {
            var id = exchangeDolls[index].id;
            var inventory = exchangeDolls[index].inventory;
            var path = exchangeDolls[index].path;
            var name = exchangeDolls[index].name;
            var jewelNum = exchangeDolls[index].jewelNum;
            var html = ' <a id="'+id+'" href="gain_shop.html?uid='+uid+'&loginKey='+loginKey+'&id='+id+'"><div class="patchList" id="'+id+'"><div class="patchList_pic">'+
                '<img src="' + path + '" alt=""/></div>'+
                '<div class="patchList_data">'+
                '<p class="p_name">' + name + '</p>'+
                '<p class="p_num">库存<em>' + inventory + '</em></p>'+
                '<p class="p_need j_need">需要：<img src="http://lianai-image-sys.qiniudn.com/mall/z_pic_1.png" alt=""/><b>' + jewelNum + '</b></p></div>'+
                '<span class="compose">兑换 <img src="http://lianai-image-sys.qiniudn.com/mall/icon.png" alt=""/></span></div></a>';
            $(html).appendTo($('.conversion'));
        });
    }
    //商品兑换结束
    //娃娃合成
    // 请求的数据本地保存一份
    var compoundAjaxData;
    function compoundPost(){
        $.ajax({
            url: baseUrl + '/api/dollCompose/getList',
            type: 'post',
            data: JSON.stringify({ "uid":uid,"loginKey":loginKey}),
            // error: function () {
            //     alert('error');
            // },
            success: function (data) {
                if (compoundAjaxData != data) {
                    compoundAjaxData = data;
                    compoundSuccessCallBack(compoundAjaxData);
                }
            }
        });
    }
    function compoundSuccessCallBack(data) {
        var data = eval('(' + data + ')');
        var rareNum = data.rareNum;
        var commNum = data.commNum;
        var listHtml = '<div class="patchNum"><div class="patchNumTop">'+
            '<span class="patchL"><img src="http://lianai-image-sys.qiniudn.com/mall/pic_1.png" alt=""/>&nbsp;<em>' + commNum + '</em></span>' +
            '<span class="patchR"><img src="http://lianai-image-sys.qiniudn.com/mall/pic_2.png" alt=""/>&nbsp;<em>' + rareNum + '</em></span></div>' +
            '<p>每日签到既有机会获 <img src="http://lianai-image-sys.qiniudn.com/mall/pic_3.png" alt=""/>普通娃娃碎片<img src="http://lianai-image-sys.qiniudn.com/mall/pic_4.png" alt=""/>稀有娃娃碎片，集齐指定数量的碎片可合成娃娃！</p></div>';
        $('.compound').before(listHtml);
        //渲染数据
        var composeDolls = data.composeDolls;
        $.each(composeDolls, function (index, ele) {
            var id = composeDolls[index].id;
            var inventory = composeDolls[index].inventory;
            var path = composeDolls[index].path;
            var name = composeDolls[index].name;
            var commDebrisNum = composeDolls[index].commDebrisNum;
            var rareDebrisNum = composeDolls[index].rareDebrisNum;
            var html ='<a href="./compose.html?uid='+uid+'&loginKey='+loginKey+'&id='+id+'"><div class="patchList" id="'+id+'"><div class="patchList_pic">' +
                '<img src="' + path + '" alt=""/></div>' +
                '<div class="patchList_data">' +
                '<p class="p_name">' + name + '</p>' +
                '<p class="p_num">库存<em>' + inventory + '</em></p>' +
                '<p class="p_need">需要：<img src="http://lianai-image-sys.qiniudn.com/mall/pic_3.png" alt=""/><b>' + commDebrisNum + '</b>&nbsp;+&nbsp;<img src="http://lianai-image-sys.qiniudn.com/mall/pic_4.png" alt=""/><b>' + rareDebrisNum + '</b></p></div>' +
                '<span class="compose"compose>合成 <img src="http://lianai-image-sys.qiniudn.com/mall/icon.png" alt=""/></span></div></a>';
            $('.compound').append(html);
        });
    }
    //娃娃合成结束

});


