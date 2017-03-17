/*
 * pages.js
 * 用于执行内面的相关组件初始化.
 */
//dom加载完成后 立即加载的功能
$(function() {
	 
	/**
	 * 用于日期控件格式化
	 */
	$.fn.datetimepicker.dates['ch'] = {
        days: ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六","星期日"],
        daysShort: ["日", "一", "二", "三", "四", "五", "六","日"],
        daysMin: ["日", "一", "二", "三", "四", "五", "六","日"],
        months: ["01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"],
        monthsShort: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
        meridiem:["上午","下午"],
        suffix:[],
        today:"今日"
	};
	var datetimepicker = {
        init: function(){
            $(".dateDiv .icon-remove, .datetimeInput .icon-remove").click(function(){
                $(this).closest("div").find("input").val("");
                $(this).closest("div").find("input").trigger("changeDate");
            });
            $(".dateDiv .icon-calendar, .datetimeInput .icon-calendar").click(function(){
                $(this).closest("div").find("input").focus();
            });
            
            $('.datetimeInput input').on('changeDate',function(){
            	var $curInput = $(this);
				if ( $curInput.val()) {
					//$('#usDate').datetimepicker('setEndDate', $('#ueDate').val());
				}else{
					//$('#usDate').datetimepicker('setEndDate', year+'-'+month+'-'+day);
				};
				if( $curInput.val()=="" &&  $curInput.next().css('display') == 'inline-block'){
					 $curInput.next().css('display','none');
				}else{
					 $curInput.next().css('display','inline-block');
				}
			});
            
            
        }
	}.init();
	
	if(!_.isUndefined(top.topManager)) {
		$('body').on('click', ".openInTab", top.topManager.tabBarObject.menuItemClickHandler);
	} else {
		$('body').on('click', ".openInTab", function(e) {
			e.preventDefault();
			var $target   = $(e.target),
			    href      = $target.attr("href") || $target.data("href");
			location.href = $.GLOBAL.config.urlPrefix + href;
		});
	}
	
	// ueEditor编辑器
    function  initUeEditor($ue) {
        if(typeof(UE) != "undefined") {
        	 UE.Editor.prototype.loadServerConfig  = function() {
				var me = this;
				setTimeout(function(){
					try{
						var configUrl = uiBase + 'vendor/ueditor1_4_3_2/jsp/config.json'; 
						$.ajax({ 
							type         : 'GET',
							url          : configUrl,
							dataType     : 'json',
							contentType  : 'application/json',
							global       : false,
							complete     : function(r) {
								var config = eval("("+r.responseText+")");
								UE.utils.extend(me.options, config);
								me.fireEvent('serverConfigLoaded');
								me._serverConfigLoaded = true;
							}
						});
					
					} catch(e){
						  console.log(e);
					}
				}); 
			 };
             if($ue.length == 1) { 
                /*
                 *  toolbars,ue-width, ue-height
                 * */ 
                var toolbars = $ue.data('toolbars')   || $.GLOBAL.config.UEToolBars['full'],
                    width    = $ue.data('ue-width')   || '100%',
                    height   = $ue.data('ue-height')  || 320;
                $ue.html($ue.html().replace(/&amp;/g, "&").replace(/&quot;/g, '"').replace(/&lt;/g, "<").replace(/&gt;/g, ">"));
                // 统一使用上传到oss
                var uploadUrl = "main/ossUploadImage/";
                var ue = UE.getEditor($ue.attr("id"), {
                    serverUrl         : urlPrefix + uploadUrl + $.GLOBAL.config.uploadSourcesMap.ueditor,
                    toolbars          : toolbars,
                    initialFrameWidth : width,
                    initialFrameHeight: height, 
                    scaleEnabled      : true,
                    elementPathEnabled:false
                });
                ue.ready(function(){
                    ue.execCommand("serverparam", {"token": getCookie("token")});
                });
             }
        }
    }
	//后台 富文本编辑器图片上传
    initUeEditor($("#ueEditor-platform"));
    //酷袋 富文本编辑器 图片上传
    initUeEditor($("#ueEditor"));
	//@TODO datetimepicker
    
    // 验证消息
	if($.validator != null) {
		$.extend($.validator.messages, {
		    required: message("admin.validate.required"),
			email: message("admin.validate.email"),
			url: message("admin.validate.url"),
			date: message("admin.validate.date"),
			dateISO: message("admin.validate.dateISO"),
			pointcard: message("admin.validate.pointcard"),
			number: message("admin.validate.number"),
			digits: message("admin.validate.digits"),
			minlength: $.validator.format(message("admin.validate.minlength")),
			maxlength: $.validator.format(message("admin.validate.maxlength")),
			rangelength: $.validator.format(message("admin.validate.rangelength")),
			min: $.validator.format(message("admin.validate.min")),
			max: $.validator.format(message("admin.validate.max")),
			range: $.validator.format(message("admin.validate.range")),
			accept: message("admin.validate.accept"),
			equalTo: message("admin.validate.equalTo"),
			remote: message("admin.validate.remote"),
			integer: message("admin.validate.integer"),
			positive: message("admin.validate.positive"),
			negative: message("admin.validate.negative"),
			decimal: message("admin.validate.decimal"),
			pattern: message("admin.validate.pattern"),
			extension: message("admin.validate.extension"),
		});
		
		$.validator.setDefaults({
			errorClass: "fieldError",
			ignore: ".ignore",
			ignoreTitle: true,
			onkeyup: false,
			errorPlacement: function(error, element) {
				var fieldSet = element.closest("span.fieldSet"),
					bs       = element.closest('.bootstrap-select');
				
				if (fieldSet.size() > 0) {
					error.appendTo(fieldSet);
				} else if(bs.length>0) {
					bs.after(error);
				}else {
					error.insertAfter(element);
				}
			},
			submitHandler: function(form) {
				if ($(form).find(":submit").is(":disabled") || $(form).find(":submit").hasClass("disabled")) {
					return;
				}
				$(form).find(":submit").prop("disabled", true).addClass("disabled");
//				$(form).encodeForm();
				form.submit();
			}
		});
		//override required add trim to the value
		$.validator.addMethod("required", function( value, element, param ) {

			// Check if dependency is met
			if ( !this.depend( param, element ) ) {
				return "dependency-mismatch";
			}
			if ( element.nodeName.toLowerCase() === "select" ) {

				// Could be an array for select-multiple or a string, both are fine this way
				var val = $( element ).val();
				return val && val.length > 0;
			}
			if ( this.checkable( element ) ) {
				return this.getLength( value, element ) > 0;
			}
			return $.trim(value).length > 0;
		});
		
		//添加自定义的验证
		$.validator.addMethod("isphone", function(value,element) {
			//var length = value.length;
			//var mobile = /^(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/;
 			var tel = /^\d{3,4}-?\d{7,8}$/;
			return this.optional(element) || tel.test(value);
		}, "Please fill in the right number");
	
		jQuery.validator.addMethod("price", function(value, element) {         
			return this.optional(element) || /^\d+(\.\d{1,2})?$/.test(value.trim());         
		}, "Format error (small digital at most two)");     
		
		jQuery.validator.addMethod("isMobile", function(value, element) {
			  var length = value.length;
			  var mobile = /^1[0-9]{10}$/;
			  return this.optional(element) || (length == 11 && mobile.test(value));
		}, "Please fill in the mobile phone number correctly");
		
		jQuery.validator.addMethod("userName", function(value, element) {
			  var userName =  /^[0-9A-Za-z\u4E00-\u9FA5a-zA-Z]{2,20}$/;
			  return this.optional(element) || (userName.test(value));
		}, "The format of user name is incorrect");
		
		jQuery.validator.addMethod("uname", function(value, element) {
			  var userName =  /^[\u4E00-\u9FA5a-zA-Z]{2,10}$/;
			  return this.optional(element) || (userName.test(value));
		}, "The format of user name is incorrect");
		
		jQuery.validator.addMethod("rolename", function(value, element) {
			  var roleName =  /^[\u4E00-\u9FA5a-zA-Z]{2,20}$/;
			  return this.optional(element) || (roleName.test(value));
		}, "The format of role name is incorrect");
		
		jQuery.validator.addMethod("isEmail", function(value, element) {
			  return this.optional(element) || /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/.test(value);
		}, "The format of Email is incorrect");
		
		jQuery.validator.addMethod("isPwd", function(value, element) {
			 var isPwd = /^[A-Za-z0-9]{6,12}$/;
			 return this.optional(element) || (isPwd.test(value));
		}, "Password must be 6 to 12 letters or Numbers");
	}
	

	
	//刷新
    $('button.reloadPage').on("click", function() {
    	location.reload(true);
    });
    $('button.backPage, i.backPage').on("click", function() {
    	var backUrl = $.GLOBAL.utils.get_urlparam('backUrl');
    	if(backUrl && backUrl.indexOf(location.host) !== -1) {
    		window.location.href = backUrl;
    	} else {
    		window.history.back();	
    	}
    	
    });
	
});
//需要手动加载的功能
$.pages = {};
$.pages.initDateTime = function() {
	$('.datetimeInput').each(function() {
		var format = $(this).data('date-format') || 'yyyy-MM-dd',
		    dateType = $(this).data('date-type') || 'day',
		    options = {
		        minView: 'month',
		        format: format,
		        language: 'ch',
		        //endDate: year+'-'+month+'-'+day,
		        autoclose : true,
		        todayBtn : true
	    	};
	    
	    var value =  $(this).find('input').val();
	    if(value) {
	    	options.initialDate = value;
	    	$(this).find('.icon-remove').parent().show();
	    } else $(this).find('.icon-remove').parent().hide();
	    
	    if(dateType == "time") {
	    	
	    	
	    	options.startView = 1;  
	    	options.minView =  'day' ;  
	    	options.minuteStep = 30;
	    	options.todayBtn = false;
	  
	    	delete options.minView;
	    	//delete options.minView;
	    }
	    //initialDate  
	    //startDate
	    //endDate 
	    
	
	    $(this).find('input').datetimepicker(options);
	    if($(this).parent().is('.datetimeInputGroup')) {
	    	var $selections = $(this).parent().find('.datetimeInput');
	    	var $startTimeSelectionInput = $selections.eq(0).find('input');
	    	var $endTimeSelectionInput = $selections.eq(1).find('input');
	    	if($(this).index() == 0) {
	    		$startTimeSelectionInput.on("click", function() {
	    			$startTimeSelectionInput.datetimepicker("setEndDate", $endTimeSelectionInput.val());
	    		});
	    		$startTimeSelectionInput.datetimepicker().on('show', function() {
	    			$endTimeSelectionInput.datetimepicker('hide');
	    		});
	    		$startTimeSelectionInput.parent().find('.input-group-addon .glyphicon-remove').on('click', function() {
	    			$startTimeSelectionInput.datetimepicker('reset');
	    		});
	    	} else {
	    		$endTimeSelectionInput.datetimepicker().on('show', function() {
	    			$startTimeSelectionInput.datetimepicker('hide');
	    		});
	    		$endTimeSelectionInput.on("click", function() {
	    			$endTimeSelectionInput.datetimepicker("setStartDate", $startTimeSelectionInput.val());
		   		});
	    		$endTimeSelectionInput.parent().find('.input-group-addon .glyphicon-remove').on('click', function() {
	    			$endTimeSelectionInput.datetimepicker('reset');
	    		});
	    		
	    	}
	    }
	});
};
