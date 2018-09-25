"use strict";
(function (t) {
    var y;
    try {
        _____()
    } catch (E) {
        if (E.stack) {
            y = g(E.stack.replace(/[\s\S]*\?/i, ""))
        }
    }

    function g(M) {
        var e = M;
        e = e.substr(1);
        var H = {};
        if (e === "") {
            return H
        }
        var L = e.split("&");
        for (var J = 0, K = L.length; J < K; J++) {
            var G = L[J];
            var I = G.split("=");
            H[I[0]] = I[1]
        }
        return H
    }

    var l = function () {
    }, h = {}, q = 0, j = true, w = null;
    var b = [/([a-zA-Z|a-zA-Z\d])+(\.)+(yy|yue|edu24ol|ued|udb|duowan|yystatic|hiido|qq|baidu|gclick|minisplat|baidustatic|huanju|huanjuyun|sina|weibo|damai|1931|100|apple|bdstatic|miibeian|12377|cyberpolice|gov|c-cnzz|uc|alicdn|aliyun|alibaba|google-analytics|zhiniu8|google)+(\.)+[A-Za-z]{2,14}/i, /((https|http):\/\/)+([a-zA-Z|a-zA-Z\d])+(\.)+(yy|yue|edu24ol|ued|udb|duowan|yystatic|hiido|qq|baidu|gclick|minisplat|huanju|baidustatic|huanjuyun|sina|weibo|damai|1931|100|apple|bdstatic|miibeian|12377|cyberpolice|gov|c-cnzz|uc|alicdn|aliyun|alibaba|google-analytics|zhiniu8|google)+(\.)+[A-Za-z]{2,14}/i, /([a-zA-Z|a-zA-Z\d])+(\.)+(yy|yue|edu24ol|ued|udb|duowan|yystatic|hiido|qq|baidu|gclick|minisplat|baidustatic|huanjuyun|huanju|sina|weibo|damai|1931|100|apple|bdstatic|miibeian|12377|cyberpolice|gov|c-cnzz|uc|alicdn|aliyun|alibaba|google-analytics|zhiniu8|google)+(:[0-9]{1,4})+(\.)+[A-Za-z]{2,14}/i, /[a-zA-Z0-9]\:\/\/[a-zA-Z0-9_/]*/i, /([A-Za-z]{2,14})+(\.)+([A-Za-z]{2,14})+(\.)+(yy|ued|udb|duowan|yystatic|hiido|qq|google|sogou|baidu|gclick|minisplat|baidustatic|huanjuyun|sina|weibo|damai|1931|zhiniu8)+(\.)+[A-Za-z]{2,14}/i, /(javascript:;)|(javascript:void\(0\);)|(#)|("")|(^\/)|(javascript: void(0);)|(^\{\$\w*\})/i, /about:blank/i, /(^\/+[\s\S]*) | (^\.\.+[\s\S]*)/i, /^\.\.+[\s\S]*/i, /^data\:/i];
    var z = ["BAIDU_DUP_wrapper", "BAIDU_DSPUI_FLOWBAR"];
    var u = ["text", "#text", "IFRAME", "SCRIPT", "IMG"];
    var r = ["1qa2ws"];
    var n = ["alert", "location"];
    if (!console) {
        t.console = {
            log: function () {
                return true
            }
        }
    }

    function x(H, I, G, e) {
        var J = {"eventid": 10010793, "bak1": H, "bak2": I, "bak3": G, "parm1": e};
        m(H, I, G, e);
        t.on_security_interdiction && t.on_security_interdiction.call(t, J)
    }

    function B(e, G) {
        if (typeof hiidoEvent != "undefined" && !w) {
            w = new hiidoEvent("yylive", "20018243")
        }
        if (w) {
            if (G) {
                w.setActtype(101)
            } else {
                w.setActtype(102);
                w.setMoreinfo(e)
            }
            if (y) {
                e["page_type"] = y.pageType
            } else {
                e["page_type"] = "101"
            }
            e["hi_url"] = location.href ? location.href : "";
            w.setMoreinfo(e);
            w.reportJudge()
        }
    }

    function m(H, J, G, I) {
        var e = {}, K = "";
        e.url = H ? H : "";
        e.classname = J ? J : "";
        e.name = G ? G : "";
        e.iframeurl = I ? I : "";
        e.pathname = t.location.pathname;
        e.hostname = t.location.hostname;
        e.ua = navigator.userAgent;
        for (var L in e) {
            if (e[L] !== "") {
                K += L + "=" + e[L] + "&"
            }
        }
        (new Image).src = "http://h5.yy.com/hostage/report?" + K
    }

    function v(J, I) {
        if (J === b) {
            if (typeof(I) === "undefined" || I === "" || I == '"') {
                return true
            }
        } else {
            if (typeof(I) === "undefined" || I === "" || I == '"') {
                return false
            }
        }
        var H = J.length, e = 0;
        for (; e < H; e++) {
            var G = new RegExp(J[e]);
            if (G.test(I.replace("https://", "").replace("http://", ""))) {
                return true
            }
        }
        return false
    }

    function f() {
        var e = this;
        if (typeof($) != "undefined") {
            $(document) && $(document).on("click", "a", function (H) {
                var G = $(arguments[0].currentTarget).attr("href");
                if (($(H.target).closest(".w-foot").length == 0 && $(H.target).closest(".w-head-main").length != 1) || ($(H.target).closest(".w-foot").length != 1 && $(H.target).closest(".w-head-main").length == 0)) {
                    if (!v(b, G)) {
                        return false
                    }
                }
            })
        }
    }

    function d(e, G) {
        var H = (e === "onclick");
        document.addEventListener && document.addEventListener(e.substr(2), function (I) {
            D(I.target, H, e, G)
        }, true)
    }

    function D(J, L, G, H) {
        var e = J.isScan, I = "", K = 0;
        if (!e) {
            e = J.isScan = ++q
        }
        K = (e << 8) | H;
        if (K in h) {
            return
        }
        h[K] = true;
        if (J.nodeType !== Node.ELEMENT_NODE) {
            return
        }
        if (J[G]) {
            I = J.getAttribute(G);
            if (I && v(n, I)) {
                J[G] = null;
                x("", "", I, "");
                B({in_event: "eventName:" + G + "----" + I})
            }
        }
        if (L && J.tagName === "A" && J.protocol === "javascript:") {
            I = J.href.substr(11);
            if (v(n, I)) {
                J.href = "javascript:void(0)";
                x("", "", I, "");
                B({in_event: I})
            }
        }
        D(J.parentNode)
    }

    function i() {
        var G = t.MutationObserver || t.WebKitMutationObserver || t.MozMutationObserver;
        if (!G) {
            return
        }
        var e = new G(function (H) {
            H.forEach(function (J) {
                var I = J.addedNodes;
                for (var K = 0; K < I.length; K++) {
                    var L = I[K];
                    if (L.tagName === "SCRIPT" || L.tagName === "IFRAME") {
                        if (L.tagName === "IFRAME" && L.src && !v(b, L.src)) {
                            L.parentNode && L.parentNode.removeChild(L);
                            x("", "insertIFRMAETag", "", L.src);
                            B({iframe: 1, s_link: L.src})
                        } else {
                            if (L.src) {
                                if (!v(b, L.src)) {
                                    L.parentNode && L.parentNode.removeChild(L);
                                    x(L.src, "insertScriptTag", "", "");
                                    B({dyn_script: 1, s_link: L.src})
                                }
                            }
                        }
                    }
                }
            })
        });
        e.observe(document, {subtree: true, childList: true})
    }

    function c() {
        document.addEventListener && document.addEventListener("DOMNodeInserted", function (J) {
            var H = J.target;
            var I = /(src|href)=\"([^\"]*?)\"/g;
            var G = H.outerHTML ? H.outerHTML : "";
            if (!H.src || H.src == "") {
                return
            }
            if (!v(b, H.src) || !F(G)) {
                if (/^(w\-head|w\-foot)/i.test(H.classList ? H.classList[0] : "w-head") || /^thirdparty-frame/i.test(H.id) || !H.src || !H.href) {
                } else {
                    H.parentNode.removeChild(H);
                    x(H.src ? H.src : "", H.className ? H.className : "", H.name ? H.name : "", "");
                    B({
                        dyn_script: 1,
                        s_link: H.src ? H.src : G.match(I) ? G.match(I).join("") : "",
                        c_name: H.className ? H.className : ""
                    })
                }
            }
        }, true)
    }

    function A(e) {
        var G = e.document.write;
        e.document.write = function (H) {
            if (!F(H)) {
                x("", H, "", "");
                B({s_node: H});
                return
            }
            G.apply(document, arguments)
        }
    }

    function F(G) {
        var K = /(src|href)=\"([^\"]*?)\"/g;
        var J = G.match(K) ? G.match(K) : "";
        if (J instanceof Array) {
            for (var I = 0, e = J.length; I < e; I++) {
                var H = v(b, (J[I]).replace(/(src|href)="/g, ""));
                if (!H) {
                    return false
                }
                if (e == 1) {
                    return H
                }
                if (I == (e - 1) && e != 1) {
                    return true
                }
            }
        } else {
            return true
        }
    }

    function k(e) {
        var G = e.Element.prototype.setAttribute;
        e.Element.prototype.setAttribute = function (H, I) {
            if (this.tagName === "SCRIPT" && /^src$/i.test(H)) {
                if (!v(b, I)) {
                    x(I, "", "", "");
                    B({a_name: I});
                    return
                }
            }
            G.apply(this, arguments)
        }
    }

    function p() {
        o(t)
    }

    function o(H) {
        k(H);
        A(H);
        var G = H.MutationObserver || H.WebKitMutationObserver || H.MozMutationObserver;
        if (!G) {
            return
        }
        var e = new G(function (I) {
            I.forEach(function (K) {
                var J = K.addedNodes;
                for (var L = 0; L < J.length; L++) {
                    var M = J[L];
                    if (M.tagName === "IFRAME") {
                        M.contentWindow && o(M.contentWindow)
                    }
                }
            })
        });
        e.observe(document, {subtree: true, childList: true})
    }

    function C() {
        try {
            Object.defineProperty(Function.prototype, "call", {
                value: Function.prototype.call,
                writable: false,
                configurable: false,
                enumerable: true
            });
            Object.defineProperty(Function.prototype, "apply", {
                value: Function.prototype.apply,
                writable: false,
                configurable: false,
                enumerable: true
            })
        } catch (G) {
        }
    }

    var a = {
        set: function (G, H) {
            var e = new Date();
            e.setTime(e.getTime() + 60 * 1000);
            document.cookie = G + "=" + H + ";expires=" + e.toGMTString()
        }, get: function (J) {
            var H = document.cookie.replace(/[ ]/g, "");
            var K = H.split(";");
            var G;
            for (var I = 0; I < K.length; I++) {
                var e = K[I].split("=");
                if (J == e[0]) {
                    G = e[1];
                    break
                }
            }
            return G
        }
    };

    function s() {
        var G = "type";
        if (self !== top) {
            var H = document.referrer, L = b.length, J = 0;
            for (; J < L; J++) {
                var K = new RegExp(b[J], "i");
                if (K.test(H)) {
                    return
                }
            }
            var I = location.href;
            var N = I.split("#");
            if (location.search) {
                N[0] += "&" + G + "=3"
            } else {
                N[0] += "?" + G + "=3"
            }
            try {
                if (!a.get("HtpLocTmp")) {
                    top.location.href = N.join("#");
                    a.set("HtpLocTmp", "1")
                }
                x("", "", "", H);
                B({url_link: H})
            } catch (M) {
                x("", "", "", H);
                B({url_link: H})
            }
        }
    }

    l.init = function () {
        if (y && y["pageType"] && y["pageType"].indexOf("bbs") == -1) {
            c()
        }
        j && f();
        i();
        C();
        p();
        s();
        setTimeout(function () {
            B({}, "visit")
        }, 2000)
    };
    if (typeof define === "function" && define.amd) {
        define("security", [], function () {
            return l
        })
    } else {
        t.security = l
    }
    if (navigator.appName == "Microsoft Internet Explorer" && (navigator.appVersion.split(";")[1].replace(/[ ]/g, "") == "MSIE6.0" || navigator.appVersion.split(";")[1].replace(/[ ]/g, "") == "MSIE7.0" || navigator.appVersion.split(";")[1].replace(/[ ]/g, "") == "MSIE8.0")) {
        return
    } else {
        if (!(/localhost/i).test(location.host) || (navigator.appName === "Microsoft Internet Explorer" && (navigator.appVersion.match(/7./i) !== "7." || navigator.appVersion.match(/8./i) !== "8."))) {
            l.init()
        }
    }
})(window);