/**
 *	坑：act一定一定要在第一位，否则数据被抛弃，所以不能用遍历对象取key/val的方法生成query，必须手工拼字符串以保证act一定是第一位
 *     eventid类型的，class1/class2必须填，否则被抛弃
 *  注意：这里没有使用jQuery，如果使用了jQuery，还得考虑独立jQuery版本的问题，即优先使用window.udbJQuery，再使用window.jQuery
 **/
(function() {
	var acceptURL = (location.protocol == "https:" ? "https:" : "http:") + "//ylog.hiido.com/j.gif";

	var browser = (function() {
		var ua = navigator.userAgent.toLowerCase();
		// 不要用window.external || ''，在某些游戏登录器上，会报尚未实现的问题
		var external = '';
		
		try{
			external = window['external'] || '';
		} catch(ex) {
			external = '';
		}

		var core, m, extra, version, os;

		var numberify = function(s) {
			var c = 0;
			return parseFloat(s.replace(/\./g, function() {
				return (c++ == 1) ? '' : '.';
			}));
		};

		try{
	        if ((/windows|win32/i).test(ua)) {
	            os = 'windows';
	        } else if ((/macintosh/i).test(ua)) {
	            os = 'macintosh';
	        } else if ((/rhino/i).test(ua)) {
	            os = 'rhino';
	        }

			if((m = ua.match(/applewebkit\/([^\s]*)/)) && m[1]){
				core = 'webkit';
				version = numberify(m[1]);
			}else if((m = ua.match(/presto\/([\d.]*)/)) && m[1]){
				core = 'presto';
				version = numberify(m[1]);
			}else if(m = ua.match(/msie\s([^;]*)/)){
				core = 'trident';
				version = 1.0;
				if ((m = ua.match(/trident\/([\d.]*)/)) && m[1]) {
					version = numberify(m[1]);
				}
			}else if(/gecko/.test(ua)){
				core = 'gecko';
				version = 1.0;
				if((m = ua.match(/rv:([\d.]*)/)) && m[1]){
					version = numberify(m[1]);
				}
			}

			if(/world/.test(ua)){
				extra = 'world';
			}else if(/360se/.test(ua)){
				extra = '360';
			}else if((/maxthon/.test(ua)) || typeof external.max_version == 'number'){
				extra = 'maxthon';
			}else if(/tencenttraveler\s([\d.]*)/.test(ua)){
				extra = 'tt';
			}else if(/se\s([\d.]*)/.test(ua)){
				extra = 'sogou';
			}
		}catch(e){}
		
		var ret = {
			'OS':os,
			'CORE':core,
			'Version':version,
			'EXTRA':(extra?extra:false),
			'IE': /msie/.test(ua),
			'OPERA': /opera/.test(ua),
			'MOZ': /gecko/.test(ua) && !/(compatible|webkit)/.test(ua),
			'IE5': /msie 5 /.test(ua),
			'IE55': /msie 5.5/.test(ua),
			'IE6': /msie 6/.test(ua),
			'IE7': /msie 7/.test(ua),
			'IE8': /msie 8/.test(ua),
			'IE9': /msie 9/.test(ua),
			'IE10': /msie 10/.test(ua),
			'IE11': /msie 11/.test(ua),
			'SAFARI': !/chrome\/([\d.]*)/.test(ua) && /\/([\d.]*) safari/.test(ua),
			'CHROME': /chrome\/([\d.]*)/.test(ua),
			'IPAD':/\(ipad/i.test(ua),
			'IPHONE':/\(iphone/i.test(ua),
			'ITOUCH':/\(itouch/i.test(ua),
			'MOBILE':/mobile/i.test(ua)
		};

		var arr = ["IE5", "IE55", "IE6", "IE7", "IE8", "IE9", "IE10", "IE11", "SAFARI", "OPERA", "MOZ", "CHROME"];
		var type = "OTHER";

		for (var i = 0; i < arr.length; i++) {
			if (ret[arr[i]]) {
				type = arr[i];
				break;
			}
		}

		ret.type = type;

		return ret;
	})();

	var cookie = (function() {
		var that = {
			set: function(sKey, sValue, oOpts){
				var arr = [];
				var d, t;
				var cfg = {
					'expire': null,
					'path': '/',
					'domain': null,
					'secure': null,
					'encode': true
				};

				for (var p in cfg) {
					if (p in oOpts) {
						cfg[p] = oOpts[p];
					}
				}
				
				if (cfg.encode == true) {
					sValue = escape(sValue);
				}
				arr.push(sKey + '=' + sValue);

				if (cfg.path != null) {
					arr.push('path=' + cfg.path);
				}
				if (cfg.domain != null) {
					arr.push('domain=' + cfg.domain);
				}
				if (cfg.secure != null) {
					arr.push(cfg.secure);
				}
				if (cfg.expire != null) {
					d = new Date();
					t = d.getTime() + cfg.expire * 3600000;
					d.setTime(t);
					arr.push('expires=' + d.toGMTString());
				}
				document.cookie = arr.join(';');
			},
			get: function(sKey){
				sKey = sKey.replace(/([\.\[\]\$])/g, '\\\$1');
				var rep = new RegExp(sKey + '=([^;]*)?;', 'i');
				var co = document.cookie + ';';
				var res = co.match(rep);
				if (res) {
					return res[1] || "";
				}
				else {
					return '';
				}
			},
			remove: function(sKey, oOpts){
				oOpts = oOpts || {};
				oOpts.expire = -10;
				that.set(sKey, '', oOpts);
			}
		};
		return that;
	})();

	// 登录有效率使用的统计函数
	// suc:
	//		0: 用户触发登录操作
	//		1: 成功登录或者登录失败但服务正常（可能是密码错了之类的）
	//		2: 成功显示authxxx.do
	//		3: 用户打开登录层
	// rptype: 
	//         0: 普通/快速登录：UDB后端响应用户点击UDB登录按钮
	//         1: 二次认证登录：UDB后端响应用户点击UDB登录按钮
	//         2： 用户点击UDB登录按钮，提交登录
	//         3: UDB响应业务系统登录，首次弹出登录页面
	//         4: 点击业务系统登录按钮
	//         5: 点击二次认证登录按钮
	var sendLogin = function(data, callback, timeout) {
		var def = {
			act: "websecloginsta",      //////////////////////////////////////////////////////////////////////
			suc: 0,
			des: '未初始化值',
			ct: 0,
			st: new Date().getTime(),
			bak5: browser.type,    //bak5 浏览器类型//////////////////////////////////////////////////////////
			appid: 0,
			bak2: "",
			time: Math.floor(new Date().getTime() / 1000),
			passport:'0',
			rptype:'99',
			uid:'0',  //yyuid
			bak1:'0'  //①up帐号密码登陆；②tk为ticket票据登陆；③otp为otp code登陆
		}

		for (var p in def) {
			if (data[p] == null) {
				data[p] = def[p];
			}
		}

		var query = "act=" + encodeURIComponent(data['act'])
					 + "&suc=" + encodeURIComponent(data['suc'])
					 + "&des=" + encodeURIComponent(data['des'])
					 + "&ct=" + encodeURIComponent(data['ct'])
					 + "&st=" + encodeURIComponent(data['st'])
					 + "&bak5=" + encodeURIComponent(data['bak5'])
					 + "&bak2=" + encodeURIComponent(data['bak2'])
					 + "&appid=" + encodeURIComponent(data['appid'])
					 + "&time=" + encodeURIComponent(data['time'])
					 + "&passport=" + encodeURIComponent(data['passport'])
					 + "&rptype=" + encodeURIComponent(data['rptype'])
					 + "&uid=" + encodeURIComponent(data['uid'])
					 + "&bak1=" + encodeURIComponent(data['bak1']);
		
		send(query, callback, timeout);
	}

	var sendEvent = function(data, callback, timeout) {
		var ui = cookie.get("hiido_ui");
		ui = ui == '' ? Math.random() : ui;
		var uid = cookie.get("yyuid");
		var username = cookie.get("username");

		var def = {
			"act": "webevent",
			"time": Math.floor(new Date().getTime() / 1000), // 太不专业了，居然是以秒为单位
			"ui": ui,
			"username": "",
			"eventid": "",
			"value": "1",
			"class1": "udb",
			"class2": "ad",
			"eventype": "1",
			"uid": uid,
			"username": username,
			"bak1": "",
			"bak2": "",
			"bak3": "",
			"parm1": "",
			"parm2": "",
			"parm3": ""
		}

		for (var p in def) {
			if (data[p] == null) {
				data[p] = def[p];
			}
		}

		var query = "act=" + encodeURIComponent(data['act'])
				 + "&time=" + encodeURIComponent(data['time'])
				 + "&ui=" + encodeURIComponent(data['ui'])
				 + "&username=" + encodeURIComponent(data['username'])
				 + "&eventid=" + encodeURIComponent(data['eventid'])
				 + "&value=" + encodeURIComponent(data['value'])
				 + "&class1=" + encodeURIComponent(data['class1'])
				 + "&class2=" + encodeURIComponent(data['class2'])
				 + "&eventype=" + encodeURIComponent(data['eventype'])
				 + "&uid=" + encodeURIComponent(data['uid'])
				 + "&username=" + encodeURIComponent(data['username'])
				 + "&bak1=" + encodeURIComponent(data['bak1'])
				 + "&bak2=" + encodeURIComponent(data['bak2'])
				 + "&bak3=" + encodeURIComponent(data['bak3'])
				 + "&parm1=" + encodeURIComponent(data['parm1'])
				 + "&parm2=" + encodeURIComponent(data['parm2'])
				 + "&parm3=" + encodeURIComponent(data['parm3']);
		
		send(query, callback, timeout);
	}

	//发包文档通用方法
	var sendCommon = function(data, callback, timeout) { 
		var uid = cookie.get("yyuid");
			
		var def = {
			"act": "",
			"time": Math.floor(new Date().getTime() / 1000),
			"uid": uid
		}
		for (var p in def) {
			if (data[p] == null) {
				data[p] = def[p];
			}
		}
		var query = "act=" + encodeURIComponent(data["act"]);

		for (var p in data) {
				if(p == "act") continue; //act必须在第一位
				query += "&"+ p + "=" + encodeURIComponent(data[p]);
		}
	
		send(query, callback, timeout);
	}

	// 原始的通过IMG加载方式
	var send = function(query, callback, timeout) {
		var called = false;
		var img = new Image();
		timeout = timeout ? timeout: 500;

		var cb = function() {
			// 防止多次调用
			if (called || (callback == null)) {
				return;
			}

			called = true;

			try {
				callback();
			}catch(ex) {}
		}

		img.onreadystatechange = function() {
			if(img.readyState == "complete" || img.readyState == "loaded") {
				cb();
			}
		}

		img.onload = function() {
			if (img.complete == true) {
				cb();
			}
		}

		img.onerror = cb;
		img.src = acceptURL + "?" + query;

		setTimeout(cb, timeout);
	}

	window.UDBHiidoStatistics = {
		send: send,
		sendLogin: sendLogin,
		sendEvent: sendEvent,
		sendCommon: sendCommon,
		browser: browser
	}
})();