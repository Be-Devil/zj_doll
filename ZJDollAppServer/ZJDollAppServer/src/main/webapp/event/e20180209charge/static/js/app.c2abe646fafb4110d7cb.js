webpackJsonp([1],{"3RPH":function(t,e,n){var a;void 0===(a=function(){var t="http://www.mengquww.com/download/gw",e=function(t){var e=location.href,n=e.substring(e.indexOf("?")+1,e.length).split("&"),a={};for(i=0;j=n[i];i++)a[j.substring(0,j.indexOf("=")).toLowerCase()]=j.substring(j.indexOf("=")+1,j.length);var o=a[t.toLowerCase()];return void 0===o?"":o};return{getBaseUrl:function(){var t=location.href;return-1!=t.indexOf("mengquww.com")?"http://www.mengquww.com":"http://t.xiehou360.com/DollAppServer"},request:e,getCookie:function(t){var e,n=new RegExp("(^| )"+t+"=([^;]*)(;|$)");return(e=document.cookie.match(n))?unescape(e[2]):null},setCookie:function(t,e){var n=new Date;n.setTime(n.getTime()+2592e6),document.cookie=t+"="+escape(e)+";expires="+n.toGMTString()},isWeixinVisit:function(){var t=navigator.userAgent;return/MicroMessenger/i.test(t)},isShareVisit:function(){return 1==e("shareFlag")},getDownloadUrl:function(){return t},jumpDownloadUrl:function(){window.location.href=t},isMobile:function(){var t=navigator.userAgent.toLowerCase(),e="ipad"==t.match(/ipad/i),n="iphone os"==t.match(/iphone os/i),i="midp"==t.match(/midp/i),a="rv:1.2.3.4"==t.match(/rv:1.2.3.4/i),o="ucweb"==t.match(/ucweb/i),s="android"==t.match(/android/i),r="windows ce"==t.match(/windows ce/i),c="windows mobile"==t.match(/windows mobile/i);return!!(e||n||i||a||o||s||r||c)}}}.apply(e,[]))||(t.exports=a)},"51lU":function(t,e,n){var a;void 0===(a=function(){var t="http://www.mengquww.com/download/gw",e=function(t){var e=location.href,n=e.substring(e.indexOf("?")+1,e.length).split("&"),a={};for(i=0;j=n[i];i++)a[j.substring(0,j.indexOf("=")).toLowerCase()]=j.substring(j.indexOf("=")+1,j.length);var o=a[t.toLowerCase()];return void 0===o?"":o};return{getBaseUrl:function(){var t=location.href;return-1!=t.indexOf("mengquww.com")?"http://www.mengquww.com/":-1!=t.indexOf("t.xiehou360.com")?"http://122.11.48.251:6639/DollAppServer/":"/"},request:e,getCookie:function(t){var e,n=new RegExp("(^| )"+t+"=([^;]*)(;|$)");return(e=document.cookie.match(n))?unescape(e[2]):null},setCookie:function(t,e){var n=new Date;n.setTime(n.getTime()+2592e6),document.cookie=t+"="+escape(e)+";expires="+n.toGMTString()},isWeixinVisit:function(){var t=navigator.userAgent;return/MicroMessenger/i.test(t)},isShareVisit:function(){return 1==e("shareFlag")},getDownloadUrl:function(){return t},jumpDownloadUrl:function(){window.location.href=t}}}.apply(e,[]))||(t.exports=a)},"F+jZ":function(t,e,n){var i;void 0===(i=function(){var t=function(t){if(t){var e=t.split("-");return new Date(e[0],parseInt(e[1])-1,e[2])}return new Date};return{getDateAdd:function(t){var e=new Date;e.setDate(e.getDate()+t);return e.getFullYear()+"-"+(e.getMonth()+1)+"-"+e.getDate()},convertDateFromString:t,getDateAddByString:function(e,n){var i=t(e);i.setDate(i.getDate()+n);return i.getFullYear()+"-"+(i.getMonth()+1)+"-"+i.getDate()},getTimestamp:function(){var t=Date.parse(new Date);return t/=1e3},reload:function(){window.location.reload()},fmtDate:function(t){var e=t,n=new Date(parseInt(e)),i=1900+n.getYear(),a="0"+(n.getMonth()+1),o="0"+n.getDate(),s="0"+n.getHours(),r="0"+n.getMinutes(),c="0"+n.getSeconds();return i+"-"+a.substring(a.length-2,a.length)+"-"+o.substring(o.length-2,o.length)+" "+s.substring(s.length-2,s.length)+":"+r.substring(r.length-2,r.length)+":"+c.substring(c.length-2,c.length)}}}.apply(e,[]))||(t.exports=i)},HRyL:function(t,e,n){"use strict";Object.defineProperty(e,"__esModule",{value:!0}),n.d(e,"fetch",function(){return u});var i=n("//Fk"),a=n.n(i),o=n("mvHQ"),s=n.n(o),r=n("mtWM"),c=(n("mw3O"),n("3RPH"));r.defaults.timeout=5e3,r.defaults.headers.post["Content-Type"]="application/x-www-form-urlencoded;charset=UTF-8",r.defaults.baseURL=c.getBaseUrl(),r.interceptors.request.use(function(t){return"post"===t.method&&(t.data=s()(t.data),console.info("request config.data",t.data)),t},function(t){return console.info("错误的传参","fail"),a.a.reject(t)});var u=function(t,e){return console.info("url:"+t),console.info("params:"+e),new a.a(function(n,i){r.post(t,e).then(function(t){n(t.data)},function(t){i(t)}).catch(function(t){i(t)})})}},NHnr:function(t,e,n){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var i,a=n("7+uW"),o=n("bOdI"),s=n.n(o),r=(n("xKOl"),n("sh2e")),c=n.n(r),u=n("F+jZ"),l=n.n(u),d=n("3RPH"),g=n.n(d),h={name:"app",components:{},data:function(){return{uid:1832306,userInfo:{},list:[],imgChange:!0,inviteCode:0,remainSecond:0,day:0,hour:0,minute:0,second:0,isShare:!1}},filters:{fmtDate:function(t){return t?l.a.fmtDate(t):""}},created:function(){var t=this;this.uid=g.a.request("uid"),console.log(this.uid),this.getList(),this.getState(),setInterval(function(){t.changeImg()},500)},methods:(i={jumpDownload:function(){g.a.jumpDownloadUrl()},getList:function(){var t=this;c.a.getChargeRank(this.uid).then(function(e){console.log(e),t.list=e.rankingList}).catch(function(t){console.log(t)})},getState:function(){var t=this,e=this;c.a.getStatus(this.uid).then(function(t){console.log(t),e.remainSecond=t.residueTime}).catch(function(t){console.log(t)}),setInterval(function(){t.handleCounter()},1e3)},handleCounter:function(){if(this.remainSecond>0){this.remainSecond=this.remainSecond-1;var t=Math.floor(this.remainSecond/86400),e=Math.floor(this.remainSecond/3600)-24*t,n=Math.floor(this.remainSecond/60)-24*t*60-60*e,i=Math.floor(this.remainSecond)-24*t*60*60-60*e*60-60*n;if(this.remainSecond>0){var a=String(t),o=String(n),s=String(i),r=String(e);this.day=a,this.hour=r,this.minute=o,this.second=s}}},changeImg:function(){this.imgChange=!this.imgChange},jumpCharge:function(){this.isShare?g.a.jumpDownloadUrl():window.location.href="http://t.xiehou360.com/6@#0"}},s()(i,"jumpDownload",function(){g.a.jumpDownloadUrl()}),s()(i,"reload",function(){window.location.reload()}),i)},m={render:function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("section",{staticClass:"maxBox"},[n("div",{staticClass:"top"},[n("img",{attrs:{src:"http://lianai-image-sys.qiniudn.com/mq/bgTop.png",alt:""}}),t._v(" "),n("button",{staticClass:"paybtn",on:{click:t.jumpCharge}}),t._v(" "),t.remainSecond>0?n("div",{staticClass:"top_time"},[n("h5",[t._v("距离活动结束还有")]),t._v(" "),n("p",[n("strong",{staticClass:"top_day"},[n("s"),n("span",{staticClass:"time"},[t._v(t._s(t.day<10?"0":t.day[0]))]),n("span",{staticClass:"time"},[t._v(t._s(t.day<10?t.day:t.day[1]))])]),t._v("天")]),t._v(" "),n("p",[n("strong",{staticClass:"top_hour"},[n("s",{attrs:{id:"h2"}}),n("span",{staticClass:"time"},[t._v(t._s(t.hour<10?"0":t.hour[0]))]),n("span",{staticClass:"time"},[t._v(t._s(t.hour<10?t.hour:t.hour[1]))])]),t._v("时")]),t._v(" "),n("p",[n("strong",{staticClass:"top_minute"},[n("s"),n("span",{staticClass:"time"},[t._v(t._s(t.minute<10?"0":t.minute[0]))]),n("span",{staticClass:"time"},[t._v(t._s(t.minute<10?t.minute:t.minute[1]))])]),t._v("分")]),t._v(" "),n("p",[n("strong",{staticClass:"top_second"},[n("s"),n("span",{staticClass:"time"},[t._v(t._s(t.second<10?"0":t.second[0]))]),n("span",{staticClass:"time"},[t._v(t._s(t.second<10?t.second:t.second[1]))])]),t._v("秒")])]):t._e()]),t._v(" "),n("div",{staticClass:"listBox"},[t._l(t.list,function(e,i){return 0!=i||t.isShare?t._e():n("div",{staticClass:"list_me"},[n("div",{staticClass:"list_ranking"},[t._v("未上榜")]),t._v(" "),n("div",{staticClass:"list_pic"},[n("img",{attrs:{src:e.head,alt:""}})]),t._v(" "),n("div",{staticClass:"list_name"},[t._v(t._s(e.name))]),t._v(" "),n("div",{staticClass:"list_number"},[t._v("福气值 | "),n("em",[t._v(t._s(e.value))])])])}),t._v(" "),n("ul",{staticClass:"user_list"},t._l(t.list,function(e,i){return i>0?n("li",[n("div",{staticClass:"user_listBox"},[n("div",{staticClass:"list_ranking user_ranking"},[n("i",[t._v(t._s(i))])]),t._v(" "),n("div",{staticClass:"list_pic"},[n("img",{attrs:{src:e.head,alt:""}}),t._v(" "),1==i?n("i",[n("img",{attrs:{src:"http://lianai-image-sys.qiniudn.com/mq/pic_1.png",alt:""}})]):t._e(),t._v(" "),2==i?n("i",[n("img",{attrs:{src:"http://lianai-image-sys.qiniudn.com/mq/pic_2.png",alt:""}})]):t._e(),t._v(" "),3==i?n("i",[n("img",{attrs:{src:"http://lianai-image-sys.qiniudn.com/mq/pic_3.png",alt:""}})]):t._e()]),t._v(" "),n("div",{staticClass:"list_name"},[t._v(t._s(e.name))]),t._v(" "),n("div",{staticClass:"list_number"},[t._v("福气值 | "),n("em",[t._v(t._s(e.value))])])])]):t._e()}))],2),t._v(" "),n("div",{staticClass:"footer"}),t._v(" "),n("button",{staticClass:"refurbish",on:{click:t.reload}}),t._v(" "),n("div",{staticClass:"down",on:{click:t.jumpCharge}},[n("img",{attrs:{src:"http://lianai-image-sys.qiniudn.com/mq/payBnt.png",alt:""}})])])},staticRenderFns:[]},f=n("VU/8")(h,m,!1,function(t){n("j1dG")},null,null).exports;new a.a({el:"#app",render:function(t){return t(f)}})},j1dG:function(t,e){},sh2e:function(t,e,n){var i=n("HRyL");t.exports={getDolls:function(t){return i.fetch("api/userDoll/getDolls",{userId:t})},getWin:function(t,e){return i.fetch("api/share/win",{uid:t,busId:e})},getInvite:function(t){return i.fetch("api/share/inviteCode",{uid:t})},getChargeRank:function(t){return i.fetch("api/newyearRecharge/getRanking",{uid:t})},getStatus:function(t){return i.fetch("api/newyearRecharge/getStatus",{uid:t})},getExchangeList:function(t){return i.fetch("api/goodsExchange/getList",{uid:t})}}},xKOl:function(t,e,n){function i(){window.location.href=o.getDownloadUrl()}function a(t){var e=o.request(t);return null==e||void 0==e||""==e?e=o.getCookie(t):o.setCookie(t,e),e}var o=n("51lU");if(o.isShareVisit()){var s=document.createElement("a");s.innerHTML='<div class="float_bar" style="width: 100%;background-color: white;max-width: 640px;text-align: left;"><img class="float_bar_img " src="http://lianai-image-sys.qiniudn.com/e20170411/bottom_bar.png" style=" width: 100%;position: fixed;max-width: 640px;z-index: 999;" /><div class="float_bar_area" style="width: 100%;max-width: 640px;"><img class="float_bar_area_content " src="http://lianai-image-sys.qiniudn.com/e20170411/bottom_bar.png" style="width: 100%;opacity: 0;" /></div></div>',s.href=o.getDownloadUrl();var r=document.body.firstChild;document.body.insertBefore(s,r)}t.exports={getUid:function(){return o.isShareVisit()?1e4:a("uid")},getLoginKey:function(){return a("loginKey")},queryUser:function(t,e){$.getJSON(o.getBaseUrl()+"WebEntry.web",{cmd:"cmd.webfollow",action:"getUserInfo",uid:e},function(e){t(e)})},jumpUserInfo:function(t){o.isShareVisit()?i():t>0&&(window.location.href="http://www.xiehou360.com/7@#"+t)},jumpLive:function(t){o.isShareVisit()?i():t>0&&(window.location.href="http://www.xiehou360.com/1@#"+t)},jumpCharge:function(){o.isShareVisit()?i():window.location.href="http://t.xiehou360.com/2@#1"},jumpDownloadUrl:i,getDefaultHead:function(){return"http://lianai-image-sys.qiniudn.com/live_web/logo_256.png"},jumpBus:function(t){o.isShareVisit()?i():window.location.href="http://t.xiehou360.com/4@#"+t}}}},["NHnr"]);
//# sourceMappingURL=app.c2abe646fafb4110d7cb.js.map