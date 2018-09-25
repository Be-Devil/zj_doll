require.config({
	paths: {
		jquery: "http://lianai-image-sys.qiniudn.com/web_common/jquery.min",
		vue: "http://lianai-image-sys.qiniudn.com/web_common/vue.min",
		httpUtils: "./httpUtils",
		myInfoComponents: "./myInfoComponents",
	},
})
require(["./main"]);