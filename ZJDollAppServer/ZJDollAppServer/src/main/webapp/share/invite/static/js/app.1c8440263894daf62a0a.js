webpackJsonp([1],{"3RPH":function(t,e,n){var o;void 0===(o=function(){var t="http://www.mengquww.com/download/gw",e=function(t){var e=location.href,n=e.substring(e.indexOf("?")+1,e.length).split("&"),o={};for(i=0;j=n[i];i++)o[j.substring(0,j.indexOf("=")).toLowerCase()]=j.substring(j.indexOf("=")+1,j.length);var a=o[t.toLowerCase()];return void 0===a?"":a};return{getBaseUrl:function(){var t=location.href;return-1!=t.indexOf("mengquww.com")?"http://www.mengquww.com":"http://122.11.48.251:6639/DollAppServer"},request:e,getCookie:function(t){var e,n=new RegExp("(^| )"+t+"=([^;]*)(;|$)");return(e=document.cookie.match(n))?unescape(e[2]):null},setCookie:function(t,e){var n=new Date;n.setTime(n.getTime()+2592e6),document.cookie=t+"="+escape(e)+";expires="+n.toGMTString()},isWeixinVisit:function(){var t=navigator.userAgent;return/MicroMessenger/i.test(t)},isShareVisit:function(){return 1==e("shareFlag")},getDownloadUrl:function(){return t},jumpDownloadUrl:function(){window.location.href=t},isMobile:function(){var t=navigator.userAgent.toLowerCase(),e="ipad"==t.match(/ipad/i),n="iphone os"==t.match(/iphone os/i),i="midp"==t.match(/midp/i),o="rv:1.2.3.4"==t.match(/rv:1.2.3.4/i),a="ucweb"==t.match(/ucweb/i),r="android"==t.match(/android/i),s="windows ce"==t.match(/windows ce/i),c="windows mobile"==t.match(/windows mobile/i);return!!(e||n||i||o||a||r||s||c)}}}.apply(e,[]))||(t.exports=o)},"51lU":function(t,e,n){var o;void 0===(o=function(){var t="http://www.mengquww.com/download/gw",e=function(t){var e=location.href,n=e.substring(e.indexOf("?")+1,e.length).split("&"),o={};for(i=0;j=n[i];i++)o[j.substring(0,j.indexOf("=")).toLowerCase()]=j.substring(j.indexOf("=")+1,j.length);var a=o[t.toLowerCase()];return void 0===a?"":a};return{getBaseUrl:function(){var t=location.href;return-1!=t.indexOf("mengquww.com")?"http://www.mengquww.com/":-1!=t.indexOf("t.xiehou360.com")?"http://122.11.48.251:6639/DollAppServer/":"/"},request:e,getCookie:function(t){var e,n=new RegExp("(^| )"+t+"=([^;]*)(;|$)");return(e=document.cookie.match(n))?unescape(e[2]):null},setCookie:function(t,e){var n=new Date;n.setTime(n.getTime()+2592e6),document.cookie=t+"="+escape(e)+";expires="+n.toGMTString()},isWeixinVisit:function(){var t=navigator.userAgent;return/MicroMessenger/i.test(t)},isShareVisit:function(){return 1==e("shareFlag")},getDownloadUrl:function(){return t},jumpDownloadUrl:function(){window.location.href=t}}}.apply(e,[]))||(t.exports=o)},"F+jZ":function(t,e,n){var i;void 0===(i=function(){var t=function(t){if(t){var e=t.split("-");return new Date(e[0],parseInt(e[1])-1,e[2])}return new Date};return{getDateAdd:function(t){var e=new Date;e.setDate(e.getDate()+t);return e.getFullYear()+"-"+(e.getMonth()+1)+"-"+e.getDate()},convertDateFromString:t,getDateAddByString:function(e,n){var i=t(e);i.setDate(i.getDate()+n);return i.getFullYear()+"-"+(i.getMonth()+1)+"-"+i.getDate()},getTimestamp:function(){var t=Date.parse(new Date);return t/=1e3},reload:function(){window.location.reload()},fmtDate:function(t){var e=t,n=new Date(parseInt(e)),i=1900+n.getYear(),o="0"+(n.getMonth()+1),a="0"+n.getDate(),r="0"+n.getHours(),s="0"+n.getMinutes(),c="0"+n.getSeconds();return i+"-"+o.substring(o.length-2,o.length)+"-"+a.substring(a.length-2,a.length)+" "+r.substring(r.length-2,r.length)+":"+s.substring(s.length-2,s.length)+":"+c.substring(c.length-2,c.length)}}}.apply(e,[]))||(t.exports=i)},HRyL:function(t,e,n){"use strict";Object.defineProperty(e,"__esModule",{value:!0}),n.d(e,"fetch",function(){return u});var i=n("//Fk"),o=n.n(i),a=n("mvHQ"),r=n.n(a),s=n("mtWM"),c=(n("mw3O"),n("3RPH"));s.defaults.timeout=5e3,s.defaults.headers.post["Content-Type"]="application/x-www-form-urlencoded;charset=UTF-8",s.defaults.baseURL=c.getBaseUrl(),s.interceptors.request.use(function(t){return"post"===t.method&&(t.data=r()(t.data),console.info("request config.data",t.data)),t},function(t){return console.info("错误的传参","fail"),o.a.reject(t)});var u=function(t,e){return console.info("url:"+t),console.info("params:"+e),new o.a(function(n,i){s.post(t,e).then(function(t){n(t.data)},function(t){i(t)}).catch(function(t){i(t)})})}},NHnr:function(t,e,n){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var i=n("7+uW"),o=(n("xKOl"),n("sh2e")),a=n.n(o),r=n("F+jZ"),s=n.n(r),c=n("3RPH"),u=n.n(c),l={name:"app",components:{},data:function(){return{uid:1832306,did:1,info:{}}},filters:{fmtDate:function(t){return t?s.a.fmtDate(t):""}},created:function(){this.uid=u.a.request("uid"),console.log(this.uid);var t=this;a.a.getInvite(this.uid).then(function(e){console.log(e),t.info=e.data}).catch(function(t){console.log(t)})},methods:{jumpDownload:function(){u.a.jumpDownloadUrl()},copyToClipboard:function(){var t=this.info.code,e=document.createElement("textarea");e.style.position="fixed",e.style.top="0",e.style.left="0",e.style.width="2em",e.style.height="2em",e.style.padding="0",e.style.border="none",e.style.outline="none",e.style.boxShadow="none",e.style.background="transparent",e.value=t,document.body.appendChild(e),e.select();try{document.execCommand("copy")}catch(t){}document.body.removeChild(e)}}},d={render:function(){var t=this.$createElement,e=this._self._c||t;return e("section",{staticClass:"maxBox"},[e("img",{staticClass:"s_bg",attrs:{src:"http://lianai-image-sys.qiniudn.com/wa/code-bg.png",alt:""}}),this._v(" "),e("div",{staticClass:"codeBox"},[e("div",{staticClass:"codeBoxIn"},[e("span",{staticClass:"codeHead"},[e("img",{attrs:{src:this.info.head,alt:""}})]),this._v(" "),e("p",{staticClass:"codeName"},[this._v(this._s(this.info.name))]),this._v(" "),e("div",{directives:[{name:"show",rawName:"v-show",value:this.info.code>0,expression:"info.code>0"}],staticClass:"type_1"},[e("p",{staticClass:"codeTXt"},[this._v("我的邀请码")]),this._v(" "),e("p",{staticClass:"code"},[this._v(this._s(this.info.code))]),this._v(" "),e("div",{staticClass:"copy",on:{click:this.copyToClipboard}},[this._v("复制邀请")])]),this._v(" "),e("div",{directives:[{name:"show",rawName:"v-show",value:0==this.info.code,expression:"info.code==0"}],staticClass:"type_2"},[this._v("邀请你一起免费抓娃娃~又萌又有趣~")])]),this._v(" "),this._m(0,!1,!1)]),this._v(" "),e("div",{staticClass:"footer s_footer"},[e("img",{attrs:{src:"http://lianai-image-sys.qiniudn.com/wa/footer.png",alt:""},on:{click:this.jumpDownload}})])])},staticRenderFns:[function(){var t=this.$createElement,e=this._self._c||t;return e("div",{staticClass:"rules"},[this._v("下载即可"),e("em",[this._v("免费玩3次")]),this._v("，输入邀请码"),e("em",[this._v("再免费玩3次")]),this._v("，分享给好友最高获600币！")])}]},h=n("VU/8")(l,d,!1,function(t){n("eLOK")},null,null).exports;new i.a({el:"#app",render:function(t){return t(h)}})},eLOK:function(t,e){},sh2e:function(t,e,n){var i=n("HRyL");t.exports={getDolls:function(t){return i.fetch("api/userDoll/getDolls",{userId:t})},getWin:function(t,e){return i.fetch("api/share/win",{uid:t,busId:e})},getInvite:function(t){return i.fetch("api/share/inviteCode",{uid:t})},getChargeRank:function(t){return i.fetch("api/newyearRecharge/getRanking",{uid:t})},getStatus:function(t){return i.fetch("api/newyearRecharge/getStatus",{uid:t})}}},xKOl:function(t,e,n){function i(){window.location.href=a.getDownloadUrl()}function o(t){var e=a.request(t);return null==e||void 0==e||""==e?e=a.getCookie(t):a.setCookie(t,e),e}var a=n("51lU");if(a.isShareVisit()){var r=document.createElement("a");r.innerHTML='<div class="float_bar" style="width: 100%;background-color: white;max-width: 640px;text-align: left;"><img class="float_bar_img " src="http://lianai-image-sys.qiniudn.com/e20170411/bottom_bar.png" style=" width: 100%;position: fixed;max-width: 640px;z-index: 999;" /><div class="float_bar_area" style="width: 100%;max-width: 640px;"><img class="float_bar_area_content " src="http://lianai-image-sys.qiniudn.com/e20170411/bottom_bar.png" style="width: 100%;opacity: 0;" /></div></div>',r.href=a.getDownloadUrl();var s=document.body.firstChild;document.body.insertBefore(r,s)}t.exports={getUid:function(){return a.isShareVisit()?1e4:o("uid")},getLoginKey:function(){return o("loginKey")},queryUser:function(t,e){$.getJSON(a.getBaseUrl()+"WebEntry.web",{cmd:"cmd.webfollow",action:"getUserInfo",uid:e},function(e){t(e)})},jumpUserInfo:function(t){a.isShareVisit()?i():t>0&&(window.location.href="http://www.xiehou360.com/7@#"+t)},jumpLive:function(t){a.isShareVisit()?i():t>0&&(window.location.href="http://www.xiehou360.com/1@#"+t)},jumpCharge:function(){a.isShareVisit()?i():window.location.href="http://t.xiehou360.com/2@#1"},jumpDownloadUrl:i,getDefaultHead:function(){return"http://lianai-image-sys.qiniudn.com/live_web/logo_256.png"},jumpBus:function(t){a.isShareVisit()?i():window.location.href="http://t.xiehou360.com/4@#"+t}}}},["NHnr"]);
//# sourceMappingURL=app.1c8440263894daf62a0a.js.map