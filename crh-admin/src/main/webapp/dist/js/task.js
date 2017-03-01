var orderRemind = {
	taskCounts  : 0,
	$sound      : void 0,//@TODO NOT IMPLEMENT
	$message    : void 0,
	curAnimateStatus      : "", //当前 动画状态(showing, shown, hiding,hidden)
	maxBottom 	: "50px",
	minBottom 	: "-100px",
//	messageBody : '您有 <strong style="color:#ff0000">{{count}}</strong> 个{{typeText}}<p align="center" style="word-break:break-all">'
	messageBody : '您有{{typeText}}<p align="center" style="word-break:break-all">'
				+'<a href="{{link}}" data-text="{{tabText}}"  class="openInTab" style="color:#ff0000">点击查看{{typeText}}</a>',
	
	//可配置参数
	defaults : {
		cookieName 			  : "",//$.GLOBAL.config.newOrderCookieName,
		messageKeepingSeconds : 4, //4秒后 信息开始消失
		animateSeconds        : 2, //显示,隐藏直动画 持续时间(秒)
		orderInterval         : 30, //心跳间隔(秒) $.GLOBAL.config.newOrderInterval
		typeText              : "新订单",
		tabText               : "订单列表",
		orderUrl              : "",//"distributionOrder/list",//@TODO 查看新订单地址
		url                   : "" //ajax 请求地址 不含urlPrefix : "distributionOrder/getNewOrderRemind"
	},
	options : {},
	
	checkOrder : function() {
		var lastCheckOrder = getCookie(this.options.cookieName);
		var today = (new Date()).getTime();

		if (lastCheckOrder == null || today-lastCheckOrder >= this.options.orderInterval * 1e3) {
			addCookie(this.options.cookieName, today);	
			try {
				$.ajax({
				  type: 'GET',
				  url: $.GLOBAL.config.urlPrefix+this.options.url,
				  dataType : 'json',
				  global : false,
				  complete: _(function(result){
					  
					  if(result.responseJSON.code == 'ACK'){
						 this.checkOrderResponse(result.responseJSON.data);
					  } 
					}).bind(this)
				});
			} catch (e) { }
		}
	},
	checkOrderResponse: function(data) {
		//data = 4;
		if(data>0) {
			this.playSound();
			this.beforeShowMessage(data);
			this.showMessage();
			
		}
	},
	beforeShowMessage : function(count) {
		var message = this.messageBody.template({count:count, link: this.options.orderUrl, typeText : this.options.typeText, tabText : this.options.tabText})
		this.$message.find('.popMsgContent').html('<p>{0}</p>'.format(message));
	},
	showMessage : function() {
		this.curAnimateStatus = "showing";
		this.$message.animate({'bottom': this.maxBottom}, this.options.animateSeconds * 1e3,  _(function() {
			this.curAnimateStatus = "shown";
			this.hideMsgObj = setTimeout(_(this.hideMessage).bind(this), this.options.messageKeepingSeconds * 1e3);
		}).bind(this));
	},
	
	hideMessage : function() {
		this.curAnimateStatus = "hiding";
		this.$message.animate({'bottom': this.minBottom}, this.options.animateSeconds * 1e3, _(function() {
			this.curAnimateStatus = "hidden";
		}).bind(this));
	},
	playSound : function() {
		if(this.options.typeText === "新订单"){
			//var borswer = window.navigator.userAgent.toLowerCase();
			var src = ""+$.GLOBAL.config.uiBase+"newOrder.mp3";
			$('<embed name="embedPlay" src="'+src+'" autostart="true" hidden="true" loop="false"></embed>').appendTo('body');
			$('<audio id="audioPlay" src="'+src+'"></audio>').appendTo('body');
		      if ( navigator.userAgent.indexOf("MSIE")>0 && navigator.userAgent.indexOf("MSIE 9.0")>0){
		          var embed = document.embedPlay;
		          embed.volume = 100;
		      }else{
		    	  var audio = document.getElementById( "audioPlay");
		    	  audio.play();
		      }
		}
		/*var path;
	     if(flag){
	      path = successMusic;
	     }else{
	      path = errorMusic;
	     }
	      var borswer = window.navigator.userAgent.toLowerCase();
	      if ( borswer.indexOf( "ie" ) >= 0|| (borswer.indexOf( "trident" ) > -1&& borswer.indexOf( "rv" ) >-1)){
	        var strEmbed = '<embed name="embedPlay" src="'+ path +'" autostart="true" hidden="true" loop="false"></embed>';
	     $( "#soundContainer" ).html( strEmbed );
	        var embed = document.embedPlay;
	        embed.volume = 100;
	      }else{
	        var strAudio = "<audio id='audioPlay' src='"+ path +"' hidden='true'>";
	     $( "#soundContainer" ).html( strAudio );
	        var audio = document.getElementById( "audioPlay");
	        audio.play();
	      }*/
		//@TODO play the wav 
		//$sound.get(0).play(); 
	},
	
	start: function(options) {
		
		
		this.options = $.extend(true, {}, this.defaults, options);
		if(this.options.cookieName == "" || this.options.orderUrl == '' || this.options.url == '') {
			//console.log("cookieName , orderUrl and url are required!");
			return false;
		}
		
		var $message = $(template('orderRemindMessageTpl', {id: orderRemind.taskCounts, typeText : this.options.typeText})),
			that     = this;
		$message.each(function() {
			if($(this).is('.newOrderPopMsg')) {
				that.$message = $(this);
			}
		});
		
		
		$('body').append(this.$message);
		
		
		this.$message.on('click', ".closeMsg", _(this.hideMessage).bind(this));
		
		this.$message.on('mouseenter', _(function() {
			if(this.$message.is(':animated')) {
				this.$message.stop(false, false);
			}
		}).bind(this));
		
		this.$message.on('mouseleave', _(function() {
			if(this.curAnimateStatus == "showing" ) {
				this.showMessage();
			}
			if(this.curAnimateStatus == "hiding") {
				this.hideMessage();
			}
		}).bind(this));
		if(orderRemind.taskCounts > 0) {
			this.$message.css('z-index', this.$message.css('z-index') + 1).css('right', (orderRemind.taskCounts+1)*22);
			setTimeout( _(function() {
				this.intvalObj = setInterval(_(this.checkOrder).bind(this), this.options.orderInterval * 1e3);
			}).bind(this), orderRemind.taskCounts*10*1e3);
		} else {
			this.intvalObj = setInterval(_(this.checkOrder).bind(this), this.options.orderInterval * 1e3);
		}
		return this;
	},
	stop: function() {
		clearInterval(this.intvalObj);
	}
};

if($.GLOBAL.config.newOrderTips === true && $.GLOBAL.config.newOrderInterval > 0) {
	window.orderRemindInstance1 = $.extend(true, {} , orderRemind).start({
		cookieName 			  : $.GLOBAL.config.newOrderCookieName,
		orderInterval         : $.GLOBAL.config.newOrderInterval,
		orderUrl              : "distributionOrder/listNewOrder",
		url                   : "distributionOrder/getNewOrderRemind"
	});
	orderRemind.taskCounts ++ ;
}

if( $.GLOBAL.config.overTimeOrderTips === true && $.GLOBAL.config.overTimeOrderInterval > 0) {
	window.orderRemindInstance2 = $.extend(true, {} , orderRemind).start({
		cookieName 			  : $.GLOBAL.config.overTimeOrderCookieName,
		orderInterval         : $.GLOBAL.config.overTimeOrderInterval,
		orderUrl              : "order/1?daley=1",
		tabText               : "异常订单",
		typeText              : "超时订单",
		url                   : "order/delayNum"
	});
	orderRemind.taskCounts ++ ;
}
