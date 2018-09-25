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
    <!-- 移动端的兼容 -->
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <title>增景物娱</title>
    <meta name="keywords" content="娃娃机,在线,物娱,直播,在线抓娃娃,娃娃,低延迟,抓娃娃,口袋,抓娃娃,欢乐抓娃娃">
    <meta name="description" content="我们提供在线抓娃娃一体化解决方案，低延时低成本，助您更快上线。">
    <meta itemprop="name" content="我们提供在线抓娃娃一体化解决方案，低延时低成本，助您更快上线。">
    <meta itemprop="image" content="http://lianai-image-sys.qiniudn.com/wa/zengjinglogo.png">
    <link rel="stylesheet" href="http://lianai-image-sys.qiniudn.com/20180119/new_zjwy1.css">

    <link href="http://cdn.bootcss.com/Swiper/4.1.0/css/swiper.min.css" rel="stylesheet">
    <link href="https://cdn.bootcss.com/animate.css/3.5.2/animate.min.css" rel="stylesheet">
    <style type="text/css">
      body {
        background-color: #000
      }
      
      .swiper-container {
        width: 100%;
        height: 100%;
        margin-left: auto;
        margin-right: auto;
      }
      
      .swiper-slide {
        position: relative;
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
        width: 100%;
        height: 60px;
        left: 0;
        top: 0;
      }
      
      .py-logo {
        width: 150px;
        position: absolute;
        top: 0.4rem;
        left: 15px;
      }
      
      .py-menu {
        position: fixed;
        top: 0.54rem;
        right: 0.41rem;
        width: 0.36rem;
        height: 0.28rem;
        background: url(http://lianai-image-sys.qiniudn.com/20180119/btn_menu.png) no-repeat center center;
        -webkit-background-size: 100% 100%;
        background-size: 100% 100%;
        z-index: 150;
      }
      
      .py-modal {
        position: fixed;
        z-index: 200;
        width: 100%;
        height: 100%;
        background-color: rgba(0, 0, 0, 0);
        display: none;
        transition: all .6s ease;
      }
      
      .py-nav-ul {
        position: absolute;
        top: 15%;
        left: 0;
        width: 80%;
        height: 60%;
        margin-left: 10%;
      }
      
      .py-nav-ul li {
        position: relative;
        width: 100%;
        height: 50px;
        line-height: 50px;
        font-size: 22px;
        margin: 20px 0;
        color: #bbb;
        cursor: pointer;
        text-align: center;
        opacity: 0;
        transition: all .6s ease;
        transform: scale(2);
        border-bottom: 2px solid transparent;
      }
      
      .py-nav-ul li.py-nav-active {
        color: #fff;
        /*border-bottom: 2px solid #fff;*/
      }
      
      .py-bg-1 {
        background: url('http://lianai-image-sys.qiniudn.com/z/bg_p1.jpg') no-repeat center center;
        background-size: 100% 100%;
      }
      
      .py-bg-1-1 {
        position: absolute;
        top: 20%;
        left: 0;
        width: 70%;
        margin-left: 15%;
      }
      
      .py-bg-1-1 img {
        width: 100%;
      }
      
      .py-bg-1-2 {
        position: absolute;
        top: 40%;
        left: 0;
        width: 90%;
        margin-left: 5%;
      }
      
      .py-bg-1-2 img {
        width: 100%;
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
        background: url('http://lianai-image-sys.qiniudn.com/20180119/bg_p2.jpg') no-repeat center center;
        background-size: 100% 100%
      }
      
      .py-bg-2-1 {
        width: 200px;
        margin: 2rem auto 0;
        display: block;
      }
      
      .py-bg-2-2 {
        width: 82%;
        margin: 1rem auto 0;
        display: block;
      }
      
      .py-bg-3 {
        background: url('http://lianai-image-sys.qiniudn.com/z/bg_p3.jpg') no-repeat center center;
        background-size: 100% 100%
      }
      
      .py-bg-3-h1 {
        font-size: 0.52rem;
        color: #fff;
        line-height: .52rem;
        text-align: center;
        margin-top: 120px;
        position: relative;
      }
      
      .py-bg-3-h1:after {
        content: "";
        position: absolute;
        bottom: -30px;
        left: 50%;
        transform: translateX(-50%);
        width: 50px;
        height: 3px;
        background-color: #fff
      }
      
      .py-bg-3-1,
      .py-bg-4-1 {
        position: absolute;
        top: 58%;
        left: 50%;
        transform: translate(-50%, -50%);
        width: 86%;
        opacity: 0;
        max-width: 900px;
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
          top: 58%;
          opacity: 1;
        }
      }
      
      @keyframes py-bg-animate-3 {
        from {
          top: 70%;
          opacity: 0;
        }
        to {
          top: 58%;
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
      
      .page5_top ul li {
        position: absolute;
        transition: opacity .6s ease;
      }
      
      .page5_top {
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
        width: 220px;
        height: 300px;
      }
      
      .page5_left,
      .page6_right {
        opacity: 0;
      }
      
      .py-bg-6 {
        background: url('http://lianai-image-sys.qiniudn.com/z/bg_p6.jpg') no-repeat center center;
        background-size: 100% 100%
      }
      
      .py-bg-6-1 {
        width: 90%;
        margin-left: 5%;
        margin-top: 120px;
      }
      
      .call {
        text-align: center;
        line-height: .72rem;
        /*color: #fff;
        background: linear-gradient(90deg, #03d2b3 0, #0988f5);
        box-shadow: 0 2px 15px rgba(5, 201, 185, .37);*/
        animation: btn-animate 2s linear infinite;
        transform: translateY(2px);
      }
      
      @-webkit-keyframes btn-animate {
        0 {
          transform: translateY(2px);
        }
        50% {
          transform: translateY(-4px);
        }
        100% {
          transform: translateY(2px);
        }
      }
    </style>
    <script src="http://lianai-image-sys.qiniudn.com/20180119/font.js"></script>
    <script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>

  </head>

  <body>
    <div class="py-nav">
      <img class="py-logo" src="http://lianai-image-sys.qiniudn.com/20180119/LOGO.png" alt="增景物娱" />
      <div class="py-menu"></div>
    </div>
    <div class="py-modal">
      <ul class="py-nav-ul">
        <li class="py-nav-active" index="0">首页</li>
        <li index="3">体验中心</li>
        <li index="4">联系我们</li>
      </ul>
    </div>
    <div class="swiper-container">
      <div class="swiper-wrapper">
        <div class="swiper-slide py-bg-1">
          <div class="py-bg-1-1">
            <img src="http://lianai-image-sys.qiniudn.com/20180119/pic_slogan.png" />
          </div>
          <!--图片-->
          <div class="py-bg-1-2">
            <img src="http://lianai-image-sys.qiniudn.com/20180119/pic_1.png" alt="">
          </div>
        </div>
        <div class="swiper-slide py-bg-2">
          <img class="py-bg-2-1" src="http://lianai-image-sys.qiniudn.com/20180119/p2_tittle.png">
          <img class="py-bg-2-2" src="http://lianai-image-sys.qiniudn.com/20180119/text.png">
        </div>
        <div class="swiper-slide py-bg-3">
          <h1 class="py-bg-3-h1">增景一站式解决方案</h1>
          <img class="py-bg-3-1" src="http://lianai-image-sys.qiniudn.com/20180119/pic_p3.png" alt="">
        </div>
        <div class="swiper-slide" id="four">
          <div class="photo">
            <img src="http://lianai-image-sys.qiniudn.com/20180119/Bitmap.png" alt="">
          </div>
          <!--二维码-->
          <div class="big">
            <div class="left">

            </div>
            <div class="right">
              <a href="http://www.realgamecloud.com/download/zj"><button>ios下载</button></a>
              <a href="http://www.realgamecloud.com/download/zjios"><button>Android下载</button></a>
            </div>
          </div>

        </div>
        <div class="swiper-slide" id="five">
          <!--九宫格-->
          <div class="jiu">
            <img src="http://lianai-image-sys.qiniudn.com/20180119/pic_p5_1.png" alt="">
          </div>
          <!--ip-->
          <div class="ip">
            <p>联系地址：</p>
            <p>广州市天河区科韵路16号广州信息港B栋301房</p>
          </div>
          <div class="clearfix"></div>
          <!--李先生-->
          <div class="lix">
            <img src="http://lianai-image-sys.qiniudn.com/2018205/li.png" alt="">
            <ul>
              <li>
                <p>李先生</p>
              </li>
              <li>
                <p>微信号：18102715921</p>
              </li>
              <li>
                <p>联系电话：18102715921</p>
              </li>
            </ul>
          </div>
          <div class="clearfix"></div>
          <!--赵先生-->
          <div class="zhao">
            <img src="http://lianai-image-sys.qiniudn.com/2018205/zhao.png" alt="">
            <ul>
              <li>
                <p>赵先生</p>
              </li>
              <li>
                <p>微信号：18583375999</p>
              </li>
              <li>
                <p>联系电话：18583375999</p>
              </li>
            </ul>
          </div>
          <div class="clearfix"></div>

        </div>

      </div>
      <!-- Add Pagination -->
      <div class="swiper-pagination"></div>
    </div>
    <a class="call" href="tel:18102715921">拨打电话</a>

    <!--蒙版-->
    <!--<div id="show" id="hide">
      <div id="panel"></div>
      <div id="login">
        <p id="go1bt">首页</p>
        <hr>
        <p id="go2bt">体验中心</p>
        <hr>
        <p id="go3bt">联系我们</p>
      </div>
    </div>-->

  </body>
  <script src='http://lianai-image-sys.qiniudn.com/20180119/jquery-1.8.3.min.js'></script>
  <script src="http://cdn.bootcss.com/Swiper/4.1.0/js/swiper.min.js"></script>
  <script type="text/javascript">
    /*panyi*/

    var swiper = new Swiper('.swiper-container', {
      on: {
        init: function() {
          setTimeout(function() {
            $('.py-bg-1-1').addClass('animated tada')
            $('.py-bg-1-2').addClass('animated tada')
          }, 100)
        },
        slideChange: function() {
          var index = swiper.activeIndex;
          if(index == 0) {

          } else if(index == 1) {
            $('.py-bg-2-1').addClass('animated fadeInDown')
            $('.py-bg-2-2').addClass('animated fadeInUp')
          } else if(index == 2) {
            setTimeout(function() {
              $('.py-bg-3-1').css({
                opacity: 1
              })
            }, 400)
            $('.py-bg-3-1').addClass('py-bg-animate-3')
          } else if(index == 3) {
            $('.photo').addClass('animated zoomInDown')
          } else if(index == 4) {
            $('.jiu').addClass('animated fadeInUp')
          }
        }
      },
      direction: 'vertical',
      slidesPerView: 1,
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

      $('.page5_top ul li').css({
        opacity: 0
      })
      $('.page5_top ul li').eq(i).css({
        opacity: 1
      })
      changeIndex++
    }, 3000)
    $('.py-nav-ul li').click(function() {
      $('.py-nav-ul li').removeClass('py-nav-active');
      $(this).addClass('py-nav-active')
      var index = $(this).attr('index')
      swiper.slideTo(index);
      closeModal()
    })
    $('.py-menu').click(function() {
      $('.py-modal').css({
        display: 'block'
      })
      setTimeout(function() {
        $('.py-modal').css({
          'background-color': 'rgba(0,0,0,.7)'
        })
        $('.py-nav-ul li').css({
          opacity: 1,
          transform: 'scale(1)'
        })
      }, 1)
    })
    $('.py-modal').click(function() {
      closeModal()
    })

    function closeModal() {

      $('.py-modal').css({
        'background-color': 'rgba(0,0,0,0)'
      })
      $('.py-nav-ul li').css({
        opacity: 0,
        transform: 'scale(2)'
      })
      setTimeout(function() {
        $('.py-modal').css({
          display: 'none'
        })
      }, 600)
    }
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