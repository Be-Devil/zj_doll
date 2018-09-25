
var vuePageInfo = new Vue({
        el: '.indexVue',
        data: {
            uid:0,
            loginKey:"",
            busId:0,
            unionId:"",
            playerRankingItems: [],
            //当前用户信息
            userInfo:{
                uhead: "",
                uname: "",
                uindex: 0,
                catDollIndex:0,
                ranking:0,
                shareValue:0,
                uid:0
            },
            shareUser:{
                conValue:0,
                uhead:"",
                uname:""
            },
            shareList:[],
            //萌主
            champions0:{
                uid: 0,
                uname: "",
                uhead: ""
            },
            champions1:{
                uid: 0,
                uname: "",
                uhead: ""
            },
            champions2:{
                uid: 0,
                uname: "",
                uhead: ""
            },
            timeInfo: {
                eMonthStr: "",
                eDayStr: "",
                sMonthStr: "",
                sDayStr: "",
                remainingTime: 0
            },
            shopItems:[],
            shareRecords:[],
            ratioInfo:[],
            ratio:{
                monthStr:"",
                dayStr:"",
                totalIncome:0,
                rewardRatio:0,
                getIncome:0
            },
            showType:1,
            machine:false,
            num:0
        },
        // methods: {
        //     timer(time){
        //         return timer(time)
        //     },
        //     swiper(){
        //         return swiper()
        //     },
        //     getBaseUrl() {
        //         var url = location.href;
        //         var baseUrl = "";
        //         if (url.indexOf("mengquww.com") != -1) {
        //             baseUrl = "http://www.mengquww.com";
        //         }else{
        //             baseUrl = 'http://t.xiehou360.com/DollAppServer';
        //         }
        //         baseUrl = "../..";
        //         return baseUrl;
        //     },
        //     request(paramName) {
        //         var url = location.href;
        //         var paraString = url.substring(url.indexOf("?") + 1, url.length).split("&");
        //         var params = {}
        //         for (i = 0; j = paraString[i]; i++) {
        //             params[j.substring(0, j.indexOf("=")).toLowerCase()] = j.substring(
        //                 j.indexOf("=") + 1, j.length);
        //         }
        //         var returnValue = params[paramName.toLowerCase()];
        //         if (typeof(returnValue) === "undefined") {
        //             return "";
        //         } else {
        //             return returnValue;
        //         }
        //     },
        //
        //     //玩家排行榜列表
        //     loadPlayerRankings() {
        //         //console.info("loadPlayerRankings");
        //         var _ = this;
        //         _.uid = _.request("uid");
        //         _.loginKey = _.request("loginKey");
        //         _.busId = _.request("busId");
        //         $.post(_.getBaseUrl()+"/api/busOwner/getPlayerRankings", JSON.stringify({"uid": _.uid, "loginKey": _.loginKey,"busId":_.busId}), function (data) {
        //             var jsonObj = JSON.parse(data);
        //             if (jsonObj && jsonObj.state) {
        //                 Object.assign(_.playerRankingItems, jsonObj.playerRankingItems);
        //                 Object.assign(_.userInfo, jsonObj.playerRankingItems[0]);
        //             }
        //         })
        //     },
            // //当前轮时间信息
            // loadTimeInfo() {
            //     //console.info("loadTimeInfo");
            //     var _ = this;
            //     _.uid = _.request("uid");
            //     _.loginKey = _.request("loginKey");
            //     $.post(_.getBaseUrl()+"/api/busOwner/getTimeInfo", JSON.stringify({"uid": _.uid, "loginKey": _.loginKey
            //     }), function (data) {
            //         var jsonObj = JSON.parse(data);
            //         if (jsonObj && jsonObj.state) {
            //             Object.assign(_.timeInfo, jsonObj);
            //             _.timer(_.timeInfo.remainingTime);
            //         }
            //
            //     })
            // },
            //
            // //萌店列表
            // loadShopList(){
            //     //console.info("loadShopList");
            //     var _ = this;
            //     _.uid = _.request("uid");
            //     _.loginKey = _.request("loginKey");
            //     $.ajaxSetup({async : false});
            //     $.post(_.getBaseUrl()+"/api/busOwner/getShopList", JSON.stringify({"uid": _.uid, "loginKey": _.loginKey}), function (data) {
            //         var jsonObj = JSON.parse(data);
            //         if (jsonObj && jsonObj.state) {
            //             Object.assign(_.shopItems, jsonObj.shopItems);
            //             if(_.shopItems && _.shopItems.length > 0){
            //                 var parent = $("#div");
            //                 _.shopItems.forEach(data=>{
            //                     if(data.busId + "" === _.busId + ""){
            //                         var $div = $("<div class='swiper-slide machine active' ><span class='busImg'><img  src="+data.img+" ></span><em>"+data.busId+"</em></div>");
            //                     }else{
            //                         var $div = $("<div class='swiper-slide machine' ><span class='busImg'><img  src="+data.img+" ></span><em>"+data.busId+"</em></div>");
            //                     }
            //                     parent.append($div);
            //                 })
            //                 //console.info(parent)
            //             }
            //             _.swiper()
            //         }
            //     })
            // },
            //
            // //经营收益信息
            // loadIncomeInfo(){
            //     //console.info("loadIncomeInfo");
            //     var _ = this;
            //     _.uid = _.request("uid");
            //     _.loginKey = _.request("loginKey");
            //     $.post(_.getBaseUrl()+"/api/busOwner/getIncomeInfo", JSON.stringify({"uid": _.uid, "loginKey": _.loginKey, "busId":_.busId
            //     }), function (data) {
            //         var jsonObj = JSON.parse(data);
            //         if (jsonObj && jsonObj.state) {
            //             Object.assign(_.ratio, jsonObj);
            //             _.ratioInfo = _.ratio.ratioInfos
            //         }
            //     })
            // },
            //
            // //查看分享值记录
            // loadShareRecords() {
            //     var _ = this;
            //     _.uid = _.request("uid");
            //     _.loginKey = _.request("loginKey");
            //     _.unionId = _.request("unionId");
            //     _.busId = _.request("busId");
            //
            //     if(_.busId && _.busId !== 0){
            //         $.post(_.getBaseUrl()+"/api/busOwner/isMengDian", JSON.stringify({"uid": _.uid, "loginKey": _.loginKey,"busId":_.busId}), function (data) {
            //             var jsonObj = JSON.parse(data);
            //             if (jsonObj && jsonObj.state) {
            //                 console.info("萌店")
            //             }else{
            //                 if(_.shopItems && _.shopItems.length > 0){
            //                     _.busId = _.shopItems[0].busId
            //                 }
            //             }
            //         })
            //     }else{
            //         if(_.shopItems && _.shopItems.length > 0){
            //             _.busId = _.shopItems[0].busId
            //         }
            //     }
            //     console.info("next...");
            //     $.post(_.getBaseUrl()+"/api/busOwner/getShareRecords", JSON.stringify({"uid": _.uid, "loginKey": _.loginKey,"unionId":_.unionId,"busId":_.busId}), function (data) {
            //         var jsonObj = JSON.parse(data);
            //         //console.info(JSON.stringify(jsonObj))
            //         if (jsonObj && jsonObj.state) {
            //             Object.assign(_.shareUser,jsonObj.shareRecords[0]);
            //
            //             _.shareRecords = jsonObj.shareRecords;
            //
            //             //console.info(JSON.stringify(_.shareUser))
            //         }
            //     });
            //     $.ajaxSetup({async : true});
            // },
            //
            // //萌主基本信息
            // loadBusOwnerInfo() {
            //     var _ = this;
            //     _.uid = _.request("uid");
            //     _.loginKey = _.request("loginKey");
            //     _.busId = _.request("busId");
            //     $.post(_.getBaseUrl()+"/api/busOwner/getBusOwnerInfo", JSON.stringify({"uid": _.uid, "loginKey": _.loginKey,"busId":_.busId}), function (data) {
            //         //console.info(data)
            //         var jsonObj = JSON.parse(data);
            //         if (jsonObj && jsonObj.state) {
            //             Object.assign(_.champions, jsonObj);
            //         }
            //     })
            // },
            //
            // //+分享值
            // addShareValue(){
            //     var _ = this;
            //     _.unionId = _.request("unionId");
            //     _.busId = _.request("busId");
            //     $.post(_.getBaseUrl()+"/api/busOwner/addShareValue", JSON.stringify({"uid": _.uid, "loginKey": _.loginKey,"unionId":_.unionId,"busId":_.busId}), function (data) {
            //         var jsonObj = JSON.parse(data);
            //         if (jsonObj && jsonObj.state) {
            //             console.info(jsonObj.msg)
            //             _.openPromptBox(jsonObj.msg)
            //         }else{
            //             _.openPromptBox(jsonObj.msg)
            //         }
            //     })
            // },
            // lookBtn(){
            //     window.location.href = "look.html?uid=" + this.uid + "&loginKey=" + this.loginKey;
            // },
            // addBtn(){
            //     var _ = this;
            //     _.busId = _.request("busId");
            //     console.info(_.busId)
            //     window.location.href = this.getBaseUrl() + "/16@#" + _.busId;
            // },
            // openPromptBox(msg) {
            //     $('.cue').html(msg);
            //     $('.cue').css("display", "");
            //     $('.cue').fadeIn().delay(1000).fadeOut(500);
            // }

        // },
        // mounted() {
        //     this.loadPlayerRankings();
        //     this.loadTimeInfo();
        //     this.loadShopList();
        //     this.loadShareRecords();
        //     this.loadIncomeInfo();
        //     this.loadBusOwnerInfo();
        // },
    });



