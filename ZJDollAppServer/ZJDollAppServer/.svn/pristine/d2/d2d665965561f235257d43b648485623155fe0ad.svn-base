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
    } else {
        baseUrl = 'http://t.xiehou360.com/DollAppServer';
    }
    //baseUrl = "../..";
    return baseUrl;
}

function getParam() {
    vuePageInfo.uid = request("uid");
    vuePageInfo.loginKey = request("loginKey");
    vuePageInfo.unionId = request("unionId");
    if (vuePageInfo.busId == 0){
        vuePageInfo.busId = request("busId");
    }
    //console.info("--->" + vuePageInfo.busId)
}


function loadPlayerRankings() {
    //console.info("loadPlayerRankings");
    $.post(getBaseUrl() + "/api/busOwner/getPlayerRankings", JSON.stringify({"uid": vuePageInfo.uid, "loginKey": vuePageInfo.loginKey, "busId": vuePageInfo.busId}), function (data) {
        var jsonObj = JSON.parse(data);
        if (jsonObj && jsonObj.state) {
            vuePageInfo.playerRankingItems = jsonObj.playerRankingItems;
            vuePageInfo.userInfo = jsonObj.playerRankingItems[0];
        }
    })
}

//当前轮时间信息
function loadTimeInfo() {
    //console.info("loadTimeInfo");
    $.post(getBaseUrl() + "/api/busOwner/getTimeInfo", JSON.stringify({"uid": vuePageInfo.uid, "loginKey": vuePageInfo.loginKey}), function (data) {
        var jsonObj = JSON.parse(data);
        if (jsonObj != null && jsonObj.state) {
            vuePageInfo.timeInfo = jsonObj;
        }
    })
}

//萌店列表
function loadShopList() {
    //console.info("loadShopList");
    $.post(getBaseUrl() + "/api/busOwner/getShopList", JSON.stringify({"uid": vuePageInfo.uid, "loginKey": vuePageInfo.loginKey}), function (data) {
        var jsonObj = JSON.parse(data);
        if (jsonObj != null && jsonObj.state) {
            vuePageInfo.shopItems = jsonObj.shopItems
        }
    });

    if (vuePageInfo.busId !== 0) {
        $.post(getBaseUrl() + "/api/busOwner/isMengDian", JSON.stringify({"uid": vuePageInfo.uid, "loginKey": vuePageInfo.loginKey, "busId": vuePageInfo.busId}), function (data) {
            var jsonObj = JSON.parse(data);
            if (jsonObj != null && jsonObj.state) {
                //console.info("萌店")
            } else {
                if (vuePageInfo.shopItems && vuePageInfo.shopItems.length > 0) {
                    vuePageInfo.busId = vuePageInfo.shopItems[0].busId
                }
            }
        })
    } else {
        if (vuePageInfo.shopItems && vuePageInfo.shopItems.length > 0) {
            vuePageInfo.busId = vuePageInfo.shopItems[0].busId
        }
    }

    //console.info("busId=>" + vuePageInfo.busId);

    if (vuePageInfo.shopItems != null && vuePageInfo.shopItems.length > 0) {

        //console.info(vuePageInfo.shopItems.length)

        if (vuePageInfo.shopItems.length <= 4 ){
            vuePageInfo.machine = false;
            var parent = $("#fn");
            for (var i = 0; i < vuePageInfo.shopItems.length; i++) {
                var data = vuePageInfo.shopItems[i];
                if (data.busId + "" == vuePageInfo.busId + "") {
                    var $div = $("<div class='machine machine_2 active' ><span class='busImg'><img  src=" + data.img + " ></span><em>" + data.busId + "</em></div>");
                } else {
                    var $div = $("<div class='machine machine_2' ><span class='busImg'><img  src=" + data.img + " ></span><em>" + data.busId + "</em></div>");
                }
                //var $div = $("<div class='machine machine_2' ><span class='busImg'><img  src=" + data.img + " ></span><em>" + data.busId + "</em></div>");
                parent.append($div);
            }

        }else {
            vuePageInfo.machine = true;
            var parent = $("#div");
            for (var i = 0; i < vuePageInfo.shopItems.length; i++) {
                var data = vuePageInfo.shopItems[i];
                if (data.busId + "" == vuePageInfo.busId + "") {
                    var $div = $("<div class='swiper-slide machine active' ><span class='busImg'><img  src=" + data.img + " ></span><em>" + data.busId + "</em></div>");
                } else {
                    var $div = $("<div class='swiper-slide machine' ><span class='busImg'><img  src=" + data.img + " ></span><em>" + data.busId + "</em></div>");
                }
                parent.append($div);
            }


        }
        if (vuePageInfo.machine){
            swiper();
        }
    }

}

//经营收益信息
function loadIncomeInfo() {

    //console.info("loadIncomeInfo");
    $.post(getBaseUrl() + "/api/busOwner/getIncomeInfo", JSON.stringify({"uid": vuePageInfo.uid, "loginKey": vuePageInfo.loginKey, "busId": vuePageInfo.busId}), function (data) {
        var jsonObj = JSON.parse(data);
        //console.info(jsonObj);
        if (jsonObj != null && jsonObj.state) {
            vuePageInfo.ratio = jsonObj;
            vuePageInfo.ratioInfo = vuePageInfo.ratio.ratioInfos;
            vuePageInfo.showType = 1;
            //console.info("显示类型1=>" + vuePageInfo.showType);
        } else {
            if (700 == jsonObj.result ) {

                vuePageInfo.showType = 0;
            } else {
                vuePageInfo.showType = 0;
            }
            //console.info("显示类型2=>" + vuePageInfo.showType);
        }
    });

    //console.info("显示类型=>" + vuePageInfo.showType);
    if (vuePageInfo.showType == 0){
        $(".bodyRanking").show();
        $(".contenBody").hide();
    }else {
        $(".bodyRanking").hide();
        $(".contenBody").show();
    }

    if(location.href.indexOf("shareIndex.html") > -1){
        $(".contenBody").show();
    }
    if(location.href.indexOf("index.html") > -1){
        $(".bodyRanking").show();
    }
    //$.ajaxSetup({async : true});
}

//查看分享值记录
function loadShareRecords() {
    //console.info("loadShareRecords");
    $.post(getBaseUrl() + "/api/busOwner/getShareRecords", JSON.stringify({"uid": vuePageInfo.uid, "loginKey": vuePageInfo.loginKey, "unionId": vuePageInfo.unionId, "busId": vuePageInfo.busId}), function (data) {
        var jsonObj = JSON.parse(data);
        if (jsonObj != null && jsonObj.state) {
            vuePageInfo.shareUser = jsonObj.shareRecords[0];
            vuePageInfo.shareRecords = jsonObj.shareRecords;
        }
    });
}

//萌主基本信息
function loadBusOwnerInfo() {
    //console.info("loadBusOwnerInfo");
    $.post(getBaseUrl() + "/api/busOwner/getBusOwnerInfo", JSON.stringify({"uid": vuePageInfo.uid, "loginKey": vuePageInfo.loginKey, "busId": vuePageInfo.busId}), function (data) {
        var jsonObj = JSON.parse(data);
        if (jsonObj != null && jsonObj.state) {
            if (jsonObj.busOwnerInfos && jsonObj.busOwnerInfos.length > 0){
                vuePageInfo.champions0 = jsonObj.busOwnerInfos[0];
                vuePageInfo.champions1 = jsonObj.busOwnerInfos[1];
                vuePageInfo.champions2 = jsonObj.busOwnerInfos[2];
                if (vuePageInfo.champions0.uname && vuePageInfo.champions0.uname.length > 10){
                    vuePageInfo.champions0.uname = vuePageInfo.champions0.uname.toString().substring(0,10) + "..."
                }
                if (vuePageInfo.champions1.uname && vuePageInfo.champions1.uname.length > 10){
                    vuePageInfo.champions1.uname = vuePageInfo.champions1.uname.toString().substring(0,10) + "..."
                }
                if (vuePageInfo.champions2.uname && vuePageInfo.champions2.uname.length > 10){
                    vuePageInfo.champions2.uname = vuePageInfo.champions2.uname.toString().substring(0,10) + "..."
                }
            }
        }
    })
}

//+分享值
function addShareValue() {
    $.post(getBaseUrl() + "/api/busOwner/addShareValue", JSON.stringify({"uid": vuePageInfo.uid, "loginKey": vuePageInfo.loginKey, "unionId": vuePageInfo.unionId, "busId": vuePageInfo.busId}), function (data) {
        var jsonObj = JSON.parse(data);
        if (jsonObj != null && jsonObj.state) {
            openPromptBox(jsonObj.msg);
            setTimeout(function () {
                // window.location.reload();
                vuePageInfo.timeInfo.remainingTime = 0;
                loadIncomeInfo();
                loadTimeInfo();
                loadPlayerRankings();
                loadShareRecords();
                loadBusOwnerInfo();
            }, 3000);
        } else {
            openPromptBox(jsonObj.msg)
        }
    })
}

function lookBtn() {
    window.location.href = "look.html?uid=" + vuePageInfo.uid + "&loginKey=" + vuePageInfo.loginKey;
}

function addBtn() {
    //vuePageInfo.busId = request("busId");
    window.location.href = getBaseUrl() + "/16@#" + vuePageInfo.busId;
}

function openPromptBox(msg) {
    $('.cue').html(msg);
    $('.cue').css("display", "");
    $('.cue').fadeIn().delay(1000).fadeOut(500);
    // window.location.reload();
}

function timer() {

    var time = window.setInterval(function () {

        var day = 0,
            hour = 0,
            minute = 0,
            second = 0;//时间默认值

        if (vuePageInfo.timeInfo.remainingTime > 0) {

            day = Math.floor(vuePageInfo.timeInfo.remainingTime / (60 * 60 * 24));


            hour = Math.floor(vuePageInfo.timeInfo.remainingTime / (60 * 60)) - (day * 24);

            minute = Math.floor(vuePageInfo.timeInfo.remainingTime / 60) - (day * 24 * 60) - (hour * 60);

            second = Math.floor(vuePageInfo.timeInfo.remainingTime) - (day * 24 * 60 * 60) - (hour * 60 * 60) - (minute * 60);

        }

        if (hour <= 9) {
            $('.hour_show').html('<s id="h"></s><span class="time">0</span><span class="time">' + hour + '</span>');
        } else {
            var h = String(hour);
            $('.hour_show').html('<s id="h"></s><spanclass="time">' + h[0] + '</span><span class="time">' + h[1] + '</span>');
        }

        if (minute <= 9) {
            $('.minute_show').html('<s></s><span class="time">0</span><span class="time">' + minute + '</span>');
        } else {
            var m = String(minute);
            $('.minute_show').html('<s></s><span class="time">' + m[0] + '</span><span class="time">' + m[1] + '</span>');
        }

        if (second <= 9) {
            $('.second_show').html('<s></s><span class="time">0</span><span class="time">' + second + '</span>');
        } else {
            var s = String(second);
            $('.second_show').html('<s></s><span class="time">' + s[0] + '</span><span class="time">' + s[1] + '</span>');
        }

        $('.day_show').html(day);

        vuePageInfo.timeInfo.remainingTime--;

    }, 1000);
}

function swiper() {
    var mySwiper = new Swiper('#topNav', {
        freeMode: true,
        freeModeMomentumRatio: 0.5,
        slidesPerView: 'auto',

    });
    swiperWidth = mySwiper.container[0].clientWidth
    maxTranslate = mySwiper.maxTranslate();
    maxWidth = -maxTranslate + swiperWidth / 2
    mySwiper.on('tap', function (swiper, e) {
        slide = swiper.slides[swiper.clickedIndex];
        slideLeft = slide.offsetLeft;
        slideWidth = slide.clientWidth;
        slideCenter = slideLeft + slideWidth / 2;
        // 被点击slide的中心点

        mySwiper.setWrapperTransition(300)

        if (slideCenter < swiperWidth / 2) {

            mySwiper.setWrapperTranslate(0)

        } else if (slideCenter > maxWidth) {

            mySwiper.setWrapperTranslate(maxTranslate)

        } else {

            nowTlanslate = slideCenter - swiperWidth / 2;

            mySwiper.setWrapperTranslate(-nowTlanslate)

        }
        $("#topNav .active").removeClass('active');

        $("#topNav .swiper-slide").eq(swiper.clickedIndex).addClass('active');
    });



}


jQuery(document).ready(function ($) {
    $.ajaxSetup({async : false});
    var $contenLi = $('.contenUl li');
    $('.contenUl li').click(function () {
        var $this = $(this);
        var $mzPic = $(".mzPic");
        var i = $this.data("index");
        $this.removeClass("mzShow" + i).addClass("mzHide" + i);
        i = parseInt($this.index()) + 1;
        $(".contenUl li").each(function (i, domEle) {
            $(this).removeClass("mzShow" + (i + 1)).addClass("mzHide" + (i + 1));
        });
        $this.removeClass("mzHide" + i).addClass("mzShow" + i);
        var index = $contenLi.index(this);
        $('div.contenBox>section').eq(index).show().siblings().hide();
    });

    var $shareLi = $('.shareUl li');
    $('.shareUl li').click(function () {
        var $this = $(this);
        var $Pic = $(".Pic");
        var i = $this.data("index");
        $this.removeClass("Show" + i).addClass("Hide" + i);
        i = parseInt($this.index()) + 1;
        $(".shareUl li").each(function (i, domEle) {
            $(this).removeClass("Show" + (i + 1)).addClass("Hide" + (i + 1));
        });
        $this.removeClass("Hide" + i).addClass("Show" + i);
        var index = $shareLi.index(this);
        $('div.shareBox>section').eq(index).show().siblings().hide();
    });



    getParam();
    loadShopList();
    loadIncomeInfo();
    loadTimeInfo();
    loadPlayerRankings();
    loadShareRecords();
    loadBusOwnerInfo();
    timer();

    /*刷新按钮*/
    $('.refurbish').click(function () {
        window.location.reload();
    });
    /*玩法说明按钮*/
    $('.rulesBtn').click(function () {
        window.location.href = "rules.html";
    });
    $('.lookBtn').click(function () {
        lookBtn()
    });
    $('.addBtn').click(function () {
        addBtn()
    });
    $('.support').click(function () {
        addShareValue()
    });

    $(".busImg").off("click");
    $(".busImg").click(function () {
        vuePageInfo.busId = $(this).next().text();
        var reqBusId = request("busId");
        if (!reqBusId || reqBusId ===0){
            //console.info("busId is null...");
            location.href = window.location.href + "&busId=" + vuePageInfo.busId
        }else {
            if (reqBusId !== vuePageInfo.busId){
                var url = window.location.href.replace("busId=" + reqBusId,"busId=" + vuePageInfo.busId);
                history.pushState({},"",url);
                //处理选中
                if (!vuePageInfo.machine){
                    $(".footerNumer>div").click(function(){
                        $(this).addClass('active');
                        $(this).siblings("div").removeClass('active');
                    });
                }
            }
        }
        vuePageInfo.timeInfo.remainingTime = 0;
        loadPlayerRankings();
        loadTimeInfo();
        loadIncomeInfo();
        loadShareRecords();
        loadBusOwnerInfo();
        //console.info("img click...")
    });

});






