/**
 * Created by 51967 on 2018/3/9.
 */
/**
 * Created by 51967 on 2018/3/9.
 */

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

//立flag  Swiper=false;
var hasInitSwiper = false;



jQuery(document).ready(function()  {
    function readyHtml() {
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
                $('.userJB').html(JB);
                //扭蛋任务
                var taggNUM = '<p>' + teggNum + '</p>';
                $('.taggNumber').html(taggNUM);
                //用户获奖
                var awardRecords = data.awardRecords;
                console.log(awardRecords);
                var userHJTotal = '';
                $(awardRecords).each(function(index, ele) {
                    var award = awardRecords[index].award;
                    var name = awardRecords[index].name;
                    var time = awardRecords[index].time;
                    // var userHJ = '<p>恭喜 <em>昵称'+name+'</em> 扭蛋开出 <i>'+award+'</i></p>'
                    var userHJ = '<div class="swiper-slide"><p>恭喜 <em>' + name + '</em> 扭蛋开出 <i>' + award + '</i></p></div>'
                    userHJTotal += userHJ;
                });
                if (!hasInitSwiper) {
                    $('.swiper-wrapper').html(userHJTotal);
                    hasInitSwiper = true;
                    var mySwiper = new Swiper('.swiper-container', {
                        pagination: '.swiper-pagination',
                        paginationClickable: true,
                        loop: true,
                        autoplay: 0.11,
                        freeMode: true,
                        speed: 5000,
                        autoplayDisableOnInteraction: false, //注意此参数，默认为true
                        observer: true, //修改swiper自己或子元素时，自动初始化swiper
                        observeParents: true, //修改swiper的父元素时，自动初始化swiper
                    });
                }else{
                    $('.swiper-wrapper').append(userHJTotal);
                }
                //  手气榜
                var rankingList = data.rankingList;
                var ulhtmlTotal = '';
                $(rankingList).each(function(index, ele) {
                    id = rankingList[index].uid;
                    name = rankingList[index].name;
                    head = rankingList[index].head;
                    ranking = rankingList[index].ranking;
                    console.log(ranking);
                    var value = rankingList[index].value;
                    if (index == 0) {
                        //手气榜我
                        var rankingMe = ' <div class="list_ranking">' + ranking + '</div>' +
                            '<div class="list_pic"><img src="' + head + '" alt=""></div>' +
                            '<div class="list_name" id="' + id + '">' + name + '</div> ' +
                            '<div class="list_number">手气值&nbsp;|&nbsp;<em>' + value + '</em></div>';
                        $('.today_me').html(rankingMe);
                    } else {
                        //手气榜用户list
                        var ulhtml = '  <li> ' +
                            '<div class="user_listBox">' +
                            ' <div class="list_ranking user_ranking">' +
                            '<i>' + ranking + '</i></div> ' +
                            '<div class="list_pic"> <img src="' + head + '" alt=""/> ' +
                            //'<i><img class="show" style="display:none" src="images/top1.png" alt=""/></i> ' +
                            '<i><div class="show" style="display:none"/></i> ' +
                            '</div> <div class="list_name" id="' + id + '">' + name + '</div>' +
                            ' <div class="list_number">手气值&nbsp;|&nbsp;<em>' + value + '</em></div></div> </li>';
                        ulhtmlTotal += ulhtml;
                    }
                })
                $('.user_list').html(ulhtmlTotal);
                if ($('.today_me .list_ranking').text() == 0) {
                    $('.today_me .list_ranking').text('未上榜');
                }
            }
        });
    }
    readyHtml();
    //分享
    $('.userJB').click(function() {
        window.location = "../../6@#1";
    })
    //扭蛋记录
    $('.nd').click(function() {
        //阴影冒泡
        $(".MaxBg").on("touchmove",function(e){
            e.preventDefault()
        })
        //弹框上方左右的空隙冒泡
        $(".record1").on("touchmove",function(e){
            e.preventDefault()
        })
        //弹框list下方左右的空隙冒泡
        $(".record2").on("touchmove",function(e){
            e.preventDefault()
        });
        $.ajax({
            url: baseUrl + '/api/twistedEggMachineWeb/getRecords',
            type: 'post',
            data: JSON.stringify({
                "uid": uid,
                "loginKey": loginKey
            }),
            error: function(data) {
                // console.log(data);
            },
            success: function(data) {
                console.log(data);
                $('.record').show();
                $('.record1').show();
                $('.record2').show();
                $('.MaxBg').show();
                var recordListTotal = '';
                var data = eval('(' + data + ')');
                var state = data.state;
                var result = data.result;
                var datamsg = data.msg;
                if (result == 0) {
                    var awardRecords = data.awardRecords;
                    // console.log(awardRecords);
                    $(awardRecords).each(function(index, ele) {
                        var name = awardRecords[index].name;
                        var time = awardRecords[index].time;
                        var award = awardRecords[index].award;
                        var aaa = award;
                        //超过三个字的奖品后面用...代替
                        if (award.length > 3){
                            aaa = award.substring(0,3)+"...";
                        }
                        var record = '  <li>恭喜扭出 <em>' + aaa + '</em> <i>' + time + '</i></li>';
                        recordListTotal += record;
                    })
                    $('.recordList').html(recordListTotal);
                }
            }
        })
        //关闭按钮,取消冒泡
        $('.close').click(function() {
            $('.record').hide();
            $('.record1').hide();
            $('.record2').hide();
            $('.MaxBg').hide();
            move();
        })
    });
    //刷新
    $('.sx').click(function() {
        window.location.reload();
    });
    //十次
    $('.ten').click(function() {
        if (uid == 17417032) {
            alert('.ten' + loginKey + ' ' + window.location.href);
        }
        //冒泡阻止多次点击
        stop();
        event.stopPropagation();
        $.ajax({
            url: baseUrl + '/api/twistedEggMachineWeb/play',
            type: 'post',
            data: JSON.stringify({
                "uid": uid,
                "loginKey": loginKey,
                "type": 0,
                "flag": 1
            }),
            error: function(data) {
                if (uid == 17417032) {
                    alert('.ten error' + data);
                }
            },
            success: function(data) {
                if (uid == 17417032) {
                    alert('.ten success' + data);
                }
                var data = eval('(' + data + ')');
                var state = data.state;
                var result = data.result;
                var datamsg = data.msg;
                if (state) {
                    $('.nd_tk').show();
                    $('.nd_tk_type2').show();
                    $('.nd_tk_type1').hide();
                    $('.MaxBg').show();
                    //十连奖品渲染
                    var awardItems = data.awardItems;
                    var tenJPTotal = '';
                    $(awardItems).each(function(index, ele) {
                        var coin = awardItems[index].coin;
                        var type = awardItems[index].type;
                        var awardName = awardItems[index].awardName;
                        var icon = awardItems[index].icon;
                        var tenJP = '  <div class="gifList"> ' +
                            '<i class="nd_tk_pic"></i> ' +
                            '<img src="' + icon + '" alt="" class="gifPic"> ' +
                            '<p>' + awardName + '</p> </div>';
                        tenJPTotal += tenJP;
                    });
                    $('.gifListBox').html(tenJPTotal);
                    // 关闭
                    $('.gifBtn').click(function() {
                        $('.nd_tk').hide();
                        $('.nd_tk_type2').hide();
                        $('.nd_tk_type1').hide();
                        $('.MaxBg').hide();
                        readyHtml();
                        move();
                    })
                } else {
                    //吐司
                    $('.ts').show().delay(3000).hide(300);
                    var tosol = ' <p>' + datamsg + '</p>'
                    $('.ts').html(tosol);
                    move();
                }
            }
        })
    });
    //一次
    $('.one').click(function() {
        stop();
        event.stopPropagation();
        $.ajax({
            url: baseUrl + '/api/twistedEggMachineWeb/play',
            type: 'post',
            data: JSON.stringify({
                "uid": uid,
                "loginKey": loginKey,
                "type": 0,
                "flag": 0
            }),
            error: function(data) {
                console.log(data);
            },
            success: function(data) {
                if (uid == 17417032) {
                    alert('.ten success' + data);
                }
                var data = eval('(' + data + ')');
                var state = data.state;
                var result = data.result;
                var datamsg = data.msg;
                if (state) {
                    $('.nd_tk').show();
                    $('.nd_tk_type1').show();
                    $('.nd_tk_type2').hide();
                    $('.MaxBg').show();
                    //一次奖品
                    var awardItems = data.awardItems;
                    $(awardItems).each(function(index, ele) {
                        var coin = awardItems[index].coin;
                        var type = awardItems[index].type;
                        var awardName = awardItems[index].awardName;
                        var icon = awardItems[index].icon;
                        var OneZJ = '<i class="nd_tk_pic"></i><img src="' + icon + '" alt="" class="gifPic"> <p>' + awardName + '</p> <img src="http://lianai-image-sys.qiniudn.com/e20180311/images/btnlq.png" alt="" class="lq">'
                        $('.nd_tk_type1').html(OneZJ);
                    })
                    //领取关闭弹框
                    $('.lq').click(function() {
                        $('.nd_tk').hide();
                        $('.nd_tk_type1').hide();
                        $('.nd_tk_type2').hide();
                        $('.MaxBg').hide();
                        readyHtml();
                        move();
                    })
                } else {
                    //吐司
                    $('.ts').show().delay(3000).hide(300);
                    var tosol = ' <p>' + datamsg + '</p>'
                    $('.ts').html(tosol);
                    move();
                }
            }
        })
    });
    //任务
    $('.rw').click(function() {
        stop();
        event.stopPropagation();
        $('.MaxBg1').show();
        $.ajax({
            url: baseUrl + '/api/twistedEggMachineWeb/play',
            type: 'post',
            data: JSON.stringify({
                "uid": uid,
                "loginKey": loginKey,
                "type": 1,
                "flag": 0
            }),
            error: function(data) {
                console.log(data);
            },
            success: function(data) {
                if (uid == 17417032) {
                    alert('.ten success' + data);
                }
                var data = eval('(' + data + ')');
                var state = data.state;
                var result = data.result;
                if (state == true && result == 0) {
                    $('.nd_tk').show();
                    $('.nd_tk_type1').show();
                    $('.nd_tk_type2').hide();
                    $('.MaxBg').show();
                    //奖品
                    var awardItems = data.awardItems;
                    var OneZJTotal = '';
                    $(awardItems).each(function(index, ele) {
                        var coin = awardItems[index].coin;
                        var type = awardItems[index].type;
                        var awardName = awardItems[index].awardName;
                        var icon = awardItems[index].icon;
                        var OneZJ = '<i class="nd_tk_pic"></i><img src="' + icon + '" alt="" class="gifPic"> <p>' + awardName + '</p> <img src="http://lianai-image-sys.qiniudn.com/e20180311/images/btnlq.png" alt="" class="lq">'
                        OneZJTotal += OneZJ;
                    })
                    $('.nd_tk_type1').html(OneZJTotal);
                    $('.lq').click(function() {
                        $('.nd_tk').hide();
                        $('.nd_tk_type1').hide();
                        $('.nd_tk_type2').hide();
                        $('.MaxBg').hide();
                        readyHtml();
                        move();
                        //window.location.reload();
                    })
                } else if (result == -1) {
                    // alert(12312)
                    var datamsg = data.msg;
                    // alert(datamsg)
                    $('.ts').show().delay(3000).hide(300);
                    var tosol = ' <p>' + datamsg + '</p>';
                    $('.ts').html(tosol);
                    move();

                } else if (result == 600) {
                    $('.box_nd').show();
                    $('.bz').click(function() {
                        $('.box_nd').hide();
                    })
                }else{
                    var datamsg = data.msg;
                    // alert(datamsg)
                    $('.ts').show().delay(3000).hide(300);
                    var tosol = ' <p>' + datamsg + '</p>';
                    $('.ts').html(tosol);
                    move();
                }
            }
        })
    });
    //实现滚动条无法滚动
    var mo = function(e){e.preventDefault();};
    /***禁止滑动***/
    function stop(){
        document.body.style.overflow='hidden';
        document.addEventListener("touchmove",mo,false);//禁止页面滑动
    }
    /***取消滑动限制***/
    function move(){
        document.body.style.overflow='';//出现滚动条
        document.removeEventListener("touchmove",mo,false);
    }
});




