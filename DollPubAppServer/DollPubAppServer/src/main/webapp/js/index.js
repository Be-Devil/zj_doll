var index = 0; //下标
var timer=null; //定时器
var mybtn=new Array()
mybtn[0]=0 ;
mybtn[1]=1 ;
mybtn[2]=5 ;

//滚屏--------------------------------------------------------------
$(function(){
    $('.content>section').eq(0).removeClass('out').siblings().addClass('out');
    $(document).mousewheel(function(event,d) {
    	clearTimeout(timer);
    	timer = setTimeout('myMousewheel('+d+')',200);
    });
    $(".gps li").click(function(){
    	var i=$(this).index();

    	clearTimeout(timer);
    	timer = setTimeout('rolling('+i+')',200);
    });
	
	
	$(".nav li").click(function(){
        $(this).addClass("txthot").siblings('li').removeClass('txthot');
    	var i=$(this).index();
		var j = mybtn[i] ;
		clearTimeout(timer);
    	timer = setTimeout('rolling('+j+')',200);
    	
    });

//滚屏--------------------------------------------------------------
});

function myMousewheel(d){
	var this_index = index-d;
	(this_index>5)?this_index=5:(this_index<0?this_index=0:"");
	rolling(this_index);
}

function rolling(i){
	index=i;
    $('.gps li').eq(i).addClass('current').siblings().removeClass();
    /* 切换整屏 一屏就是高度100% */
    $('.content').stop().animate({top:-i*100+'%'}, 1000);
    /*当前索引删除类，动画入场*/
    $('.content>section').eq(i).stop().removeClass('out').siblings().stop().addClass('out');
}





