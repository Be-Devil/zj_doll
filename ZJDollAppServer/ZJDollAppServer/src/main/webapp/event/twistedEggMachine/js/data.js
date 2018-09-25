function readyHtml() {
    getBaseUrl();
    uid = request('uid');
    loginKey = request('loginKey');
    alert('132123')
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
            let userHJTotal = '';
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
            //  手气棒我
            var rankingList = data.rankingList;
            let ulhtmlTotal = '';
            $(rankingList).each(function(index, ele) {
                id = rankingList[index].uid;
                name = rankingList[index].name;
                head = rankingList[index].head;
                ranking = rankingList[index].ranking;
                console.log(ranking);
                var value = rankingList[index].value;
                if (index == 0) {
                    var rankingMe = ' <div class="list_ranking">' + ranking + '</div>' +
                        '<div class="list_pic"><img src="' + head + '" alt=""></div>' +
                        '<div class="list_name" id="' + id + '">' + name + '</div> ' +
                        '<div class="list_number">手气值&nbsp;|&nbsp;<em>' + value + '</em></div>';
                    $('.today_me').html(rankingMe);
                } else {
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
    $('.userJB').click(function() {
        window.location = "../../6@#1";
    })
}