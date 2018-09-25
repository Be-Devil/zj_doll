<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
        <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <!--ie 兼容-->
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>增景物娱</title>
        <meta name="keywords" content="娃娃机,在线,物娱,直播,在线抓娃娃,娃娃,低延迟,抓娃娃,口袋,抓娃娃,欢乐抓娃娃">
        <meta name="description" content="我们提供在线抓娃娃一体化解决方案，低延时低成本，助您更快上线。">
        <meta itemprop="name" content="我们提供在线抓娃娃一体化解决方案，低延时低成本，助您更快上线。">
        <meta itemprop="image" content="http://lianai-image-sys.qiniudn.com/wa/zengjinglogo.png">
        <link rel="stylesheet" type="text/css" href="https://lianai-resource-ssl.imlianai.com/zjweb/style.css" />
        <link rel="stylesheet" type="text/css" href="https://lianai-resource-ssl.imlianai.com/zjweb/index.css" />

        <link href="http://cdn.bootcss.com/Swiper/4.1.0/css/swiper.min.css" rel="stylesheet">
        <link href="https://cdn.bootcss.com/animate.css/3.5.2/animate.min.css" rel="stylesheet">
        <style type="text/css">
            .swiper-container {
                width: 100%;
                height: 100%;
                margin-left: auto;
                margin-right: auto;
            }
            
            .swiper-slide {
                position: relative;
            }
            
            .swiper-pagination.swiper-pagination-clickable.swiper-pagination-bullets {
                right: 50px !important;
            }
            
            .swiper-pagination-bullet.swiper-pagination-bullet-active {
                background-color: transparent;
                border: 4px solid #fff;
            }
            
            .swiper-pagination-bullet {
                position: relative;
                left: 50%;
                transform: translateX(-50%);
                background-color: #fff;
                opacity: 1;
                margin: 20px 0 !important;
            }
            
            .py-nav {
                position: fixed;
                z-index: 150;
                width: 76%;
                height: 100px;
                left: 12%;
                top: 0;
            }
            
            .py-logo {
                width: 200px;
                position: absolute;
                bottom: 0;
            }
            
            .py-nav-ul {
                position: absolute;
                right: 5%;
                bottom: 0;
                height: 50px;
            }
            
            .py-nav-ul li {
                position: relative;
                float: left;
                padding: 0 2px;
                margin: 0 50px;
                line-height: 50px;
                font-size: 17px;
                color: #bbb;
                cursor: pointer;
                text-align: center;
                transition: all .3s ease;
                border-bottom: 2px solid transparent;
            }
            
            .py-nav-ul li.py-nav-active {
                color: #fff;
                border-bottom: 2px solid #fff;
            }
            
            .py-bg-1 {
                background: url('http://lianai-image-sys.qiniudn.com/z/bg_p1.jpg') no-repeat center center;
                background-size: 100% 100%;
            }
            
            .py-bg-1-1 {
                position: absolute;
                top: 50%;
                left: 15%;
                transform: translateY(-50%);
            }
            
            .py-bg-1-1 img {
                width: 74%;
                max-width: 520px;
            }
            
            .py-bg-1-2 {
                position: absolute;
                top: 50%;
                right: 10%;
                transform: translateY(-50%);
            }
            
            .py-bg-1-2 img {
                float: right;
                width: 74%;
                max-width: 660px;
            }
            
            .py-bg-animate-1 {
                animation: py-bg-animate-1 1.3s ease;
            }
            
            @-webkit-keyframes py-bg-animate-1 {
                from {
                    transform: translate(30%, -50%);
                    opacity: 0;
                }
                to {
                    transform: translateX(0, -50%);
                    opacity: 1;
                }
            }
            
            @keyframes py-bg-animate-1 {
                from {
                    transform: translate(30%, -50%);
                    opacity: 0;
                }
                to {
                    transform: translateX(0, -50%);
                    opacity: 1;
                }
            }
            
            .py-bg-animate-2 {
                animation: py-bg-animate-2 1.3s ease;
            }
            
            @-webkit-keyframes py-bg-animate-2 {
                from {
                    transform: translate(-30%, -50%);
                    opacity: 0;
                }
                to {
                    transform: translateX(0, -50%);
                    opacity: 1;
                }
            }
            
            @keyframes py-bg-animate-2 {
                from {
                    transform: translate(-30%, -50%);
                    opacity: 0;
                }
                to {
                    transform: translateX(0, -50%);
                    opacity: 1;
                }
            }
            
            .py-bg-2 {
                background: url('http://lianai-image-sys.qiniudn.com/z/bg_p2.jpg') no-repeat center center;
                background-size: 100% 100%
            }
            
            .py-bg-3 {
                background: url('http://lianai-image-sys.qiniudn.com/z/bg_p3.jpg') no-repeat center center;
                background-size: 100% 100%
            }
            
            .py-bg-3-1,
            .py-bg-4-1 {
                position: absolute;
                top: 50%;
                left: 50%;
                transform: translate(-50%, -50%);
                opacity: 0;
                width: 100%;
                max-width: 913px;
                max-height: 610px;
                display: block;
            }
            
            .py-bg-animate-3 {
                animation: py-bg-animate-3 1.3s .3s ease;
            }
            
            @-webkit-keyframes py-bg-animate-3 {
                from {
                    top: 70%;
                    opacity: 0;
                }
                to {
                    top: 50%;
                    opacity: 1;
                }
            }
            
            @keyframes py-bg-animate-3 {
                from {
                    top: 70%;
                    opacity: 0;
                }
                to {
                    top: 50%;
                    opacity: 1;
                }
            }
            
            .py-bg-4 {
                background: url('http://lianai-image-sys.qiniudn.com/z/bg_p4.jpg') no-repeat center center;
                background-size: 100% 100%
            }
            
            .py-bg-5 {
                background: url('http://lianai-image-sys.qiniudn.com/z/bg_p5.jpg') no-repeat center center;
                background-size: 100% 100%
            }
            
            .page5_left ul li {
                transition: opacity .6s ease;
            }
            
            .page5_left,
            .page6_right {
                opacity: 0;
            }
            
            .py-bg-6 {
                background: url('http://lianai-image-sys.qiniudn.com/z/bg_p6.jpg') no-repeat center center;
                background-size: 100% 100%
            }
        </style>
        <script src="http://lianai-image-sys.qiniudn.com/20180119/font.js"></script>
        <script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>

    </head>

    <body style="background-color: #000;">
        <div class="py-nav">
            <img class="py-logo" src="http://lianai-image-sys.qiniudn.com/20180119/LOGO.png" alt="增景物娱" />
            <ul class="py-nav-ul">
                <li class="py-nav-active" index="0">首页</li>
                <li index="4">体验中心</li>
                <li index="5">联系我们</li>
            </ul>
        </div>
        <div class="swiper-container">
            <div class="swiper-wrapper">
                <div class="swiper-slide py-bg-1">
                    <div class="py-bg-1-1">
                        <img src="http://lianai-image-sys.qiniudn.com/20180119/pic_slogan.png" alt="">
                    </div>
                    <!--图片-->
                    <div class="py-bg-1-2">
                        <img src="http://lianai-image-sys.qiniudn.com/20180119/pic_1.png" alt="">
                    </div>
                </div>
                <div class="swiper-slide py-bg-2"></div>
                <div class="swiper-slide py-bg-3">
                    <img class="py-bg-3-1" src="http://lianai-image-sys.qiniudn.com/z/pic_3.png" alt="">
                </div>
                <div class="swiper-slide py-bg-4">
                    <img class="py-bg-4-1" src="http://lianai-image-sys.qiniudn.com/z/pic_4.png" />
                </div>
                <div class="swiper-slide py-bg-5">
                    <div class="max-width">
                        <div class="page5_left">
                            <ul>
                                <li style="display:block"><img src="http://lianai-image-sys.qiniudn.com/z/Bitmap.png" alt="" /></li>
                                <li style="display:block"><img src="http://lianai-image-sys.qiniudn.com/z/Bitmap2.png" alt="" /></li>
                                <li style="display:block"><img src="http://lianai-image-sys.qiniudn.com/z/Bitmap3.png" alt="" /></li>
                            </ul>
                        </div>
                        <div class="page6_right">
                            <img src="http://lianai-image-sys.qiniudn.com/z/pic_slogan.png" alt="">
                            <div class="code ks-clear">
                                <span>
                                 <img src="http://lianai-image-sys.qiniudn.com/z/down.png" alt="" style="width:72px">
                                <p>APP下载</p>
                              </span>
                                <!-- APP -->
                                <span>
                                <img src="http://lianai-image-sys.qiniudn.com/z/pic_QR1.png" alt="">
                                <p>H5体验</p>
                              </span>
                                <!-- H5 -->
                            </div>
                            <a href="http://www.realgamecloud.com/download/zjios"><button id="iosDownload" class="pageBtn ios">iOS下载</button></a>
                            <a href="http://www.realgamecloud.com/download/zj"><button id="androidDownload" class="pageBtn Android">Android下载</button></a>
                        </div>
                    </div>
                </div>
                <div class="swiper-slide py-bg-6">
                    <div class="max-width ">
                        <div style="width:100%;">
                            <img src="http://lianai-image-sys.qiniudn.com/z/footer.png" alt="" style="margin:10% 0 5% 0;">
                        </div>
                        <span class="bottom_notice" style="position:relative;bottom:0;">广州增景物娱科技有限公司 realgamecloud.com 
 <a href="http://www.miitbeian.gov.cn/" target="_blank" id="icp">粤ICP备18005480号-1</a> </span>
                    </div>

                </div>

            </div>
            <!-- Add Pagination -->
            <div class="swiper-pagination"></div>
        </div>
    </body>
    <script src='http://lianai-image-sys.qiniudn.com/20180119/jquery-1.8.3.min.js'></script>
    <script src="http://cdn.bootcss.com/Swiper/4.1.0/js/swiper.min.js"></script>
    <script type="text/javascript">
        /*panyi*/

        var swiper = new Swiper('.swiper-container', {
            on: {
                init: function() {
                    $('.py-bg-1-1').addClass('py-bg-animate-1')
                    $('.py-bg-1-2').addClass('py-bg-animate-2')
                },
                slideChange: function() {
                    var index = swiper.activeIndex;
                    if(index == 0) {
                        $('.py-nav-ul li').removeClass('py-nav-active');
                        $('.py-nav-ul li').eq(0).addClass('py-nav-active')
                    } else if(index == 1) {
                        $('.py-bg-1-2').addClass('py-bg-animate-3')
                    } else if(index == 2) {
                        setTimeout(function() {
                            $('.py-bg-3-1').css({
                                opacity: 1
                            })
                        }, 400)
                        $('.py-bg-3-1').addClass('py-bg-animate-3')
                    } else if(index == 3) {
                        setTimeout(function() {
                            $('.py-bg-4-1').css({
                                opacity: 1
                            })
                        }, 400)
                        $('.py-bg-4-1').addClass('py-bg-animate-3')
                    } else if(index == 4) {
                        setTimeout(function() {
                            $('.page5_left,.page6_right').css({
                                opacity: 1
                            })
                            $('.page5_left').addClass('animated fadeInLeft')
                            $('.page6_right').addClass('animated fadeInRight')
                        }, 300)
                        $('.py-nav-ul li').removeClass('py-nav-active');
                        $('.py-nav-ul li').eq(1).addClass('py-nav-active')
                    } else if(index == 5) {
                        $('.py-nav-ul li').removeClass('py-nav-active');
                        $('.py-nav-ul li').eq(2).addClass('py-nav-active')
                    }
                }
            },
            direction: 'vertical',
            mousewheel: true,
            speed: 500,
            pagination: {
                el: '.swiper-pagination',
                clickable: true,
            },
        });
        var changeIndex = 0
        setInterval(function() {
            var i = changeIndex % 3;

            $('.page5_left ul li').css({
                opacity: 0
            })
            $('.page5_left ul li').eq(i).css({
                opacity: 1
            })
            changeIndex++
        }, 3000)
        $('.py-nav-ul li').click(function() {
            $('.py-nav-ul li').removeClass('py-nav-active');
            $(this).addClass('py-nav-active')
            var index = $(this).attr('index')
            swiper.slideTo(index);
        })
        /*panyi*/

        var shareTitel = "增景物娱";
        var shareDesc = "我们提供在线抓娃娃一体化解决方案，低延时低成本，助您更快上线。";
        var shareUrl = "http://www.realgamecloud.com/";
        var head = "http://lianai-image-sys.qiniudn.com/wa/zengjinglogo.png";
        var visit_qq = "http://www.realgamecloud.com/";

        function doConfig() {
            $.getJSON('http://www.realgamecloud.com/api/wechatSupport/info', {
                url: document.location.href,
                type: 2
            }, function(data) {
                if(data.state) {
                    var res = data.js;
                    wx.config({
                        debug: false,
                        appId: res.appId,
                        timestamp: res.timestamp,
                        nonceStr: res.nonceStr,
                        signature: res.signature,
                        jsApiList: ['onMenuShareTimeline',
                            'onMenuShareAppMessage', 'onMenuShareQQ',
                            'onMenuShareWeibo', 'hideMenuItems'
                        ]
                    });

                    //getChargeOrder(uid,code,1);
                    wx.ready(function() {
                        wx.hideMenuItems({
                            menuList: [
                                'menuItem:openWithQQBrowser',
                                'menuItem:openWithSafari'
                            ]
                            // 要隐藏的菜单项，只能隐藏“传播类”和“保护类”按钮，所有menu项见附录3
                        });

                        wx.onMenuShareAppMessage({
                            title: shareTitel,
                            desc: shareDesc,
                            link: shareUrl,
                            imgUrl: head,
                            trigger: function(res) {},
                            success: function(res) {},
                            cancel: function(res) {},
                            fail: function(res) {

                            }
                        });

                        wx.onMenuShareTimeline({
                            title: shareTitel,
                            link: shareUrl,
                            imgUrl: head,
                            trigger: function(res) {

                            },
                            success: function(res) {

                            },
                            cancel: function(res) {

                            },
                            fail: function(res) {

                            }
                        });

                        wx.onMenuShareQQ({
                            title: shareTitel,
                            desc: shareDesc,
                            link: visit_qq,
                            imgUrl: head,
                            success: function() {},
                            cancel: function() {}
                        });

                    });

                }
            });
        }

        doConfig();
    </script>

</html>