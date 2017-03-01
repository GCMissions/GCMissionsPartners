//global.js

$.GLOBAL = $.GLOBAL || {},
$.GLOBAL.config = {
	version : "1.0.0",
	systemName : systemName,
    urlPrefix : urlPrefix,
    uiBase    : uiBase,
    mainUrl   : urlPrefix + "main/",  
    menuUrl   : urlPrefix + "main/menu",    
    loginUrl  : urlPrefix + "main/login",   
    logoutUrl : urlPrefix + "login/logout",    
    indexUrl  : urlPrefix + "main/index",
 
    editUserUrl  : urlPrefix + 'user/selfEditData/', //post-->getData
    editUserPostUrl  : urlPrefix +  'user/selfEdit',       //post --> saveData
    uploadUrl : urlPrefix + "main/addImage/{{source}}", //source  uploadSourcesMap.product
    ossUploadUrl : urlPrefix + "main/ossAddImage/{{source}}",
    ossUploadNewUrl : urlPrefix + "main/ossUploadImage/{{source}}",//文件名重新生成（不包含中文）
    ossUploadUrlList : urlPrefix + "main/ossAddImageList/{{source}}",
    uploadSourcesMap :{
    	product : 10,      //吾儿网平台系统商品
    	brand   : 11,      //吾儿网平台系统品牌
        ad      : 12,      //吾儿网平台系统广告
        fproduct: 13,      //吾儿网平台系统楼层商品 
        ueditor : 14,      //ueeditor
        appmessageImg:15,  //App消息图片
        group	:16,	   //团购图片
        appmessage : 20,    //吾儿网平台系统APP消息
        coolbag : 21, //吾儿酷袋图片
        myActivity : 22, // 我的活动图片
        imageMaterial : 23, //我的素材库图片
        kdAdvertise : 24, // 酷袋广告位图片
        kdProduct : 25, // 酷袋商品
        appStartup:26//app启动页
    },
    //qrcode 
	qrcodes : {
		qrText : "http://www.wuliangye.com/qrcode{{orgId}}", //@TODO  //商家的二维码内容,现有变量{{loginId}}, {{orgId}}, {{userName}}
		defaultConfig : {
			sideWidth: "8", //cm
			scanWidth : "0.5",//m
			width: 222,
			height:222
		},
		otherSizes : [
			{
				sideWidth: "12",
				scanWidth : "0.8",
				width: 297,
				height:297
			},
			{
				sideWidth: "15",
				scanWidth : "1",
				width: 372,
				height:372
			},
			{
				sideWidth: "30",
				scanWidth : "1.5",
				width: 740,
				height:740
			}
		]
	},
	
    newOrderTips : window.newOrderTips, //开启新订单提醒
    newOrderInterval : window.newOrderInterval, //多久检测一次新订单 单位:秒
    newOrderCookieName : "wlyadmin_LastCheckOrder",
	overTimeOrderTips : window.overTimeOrderTips,         //是否开启 超时订单提醒
	overTimeOrderInterval : window.overTimeOrderInterval, //多久检测一次超时订单 单位:秒
    overTimeOrderCookieName : "wlyadmin_LastCheckOverTimeOrder",
    UEToolBars : {
    	full :  [[
	        'fullscreen', 'source', '|', 'undo', 'redo', '|',
	        'bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'superscript', 'subscript', 'removeformat', 'formatmatch', 'autotypeset', 'blockquote', 'pasteplain', '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc', '|',
	        'rowspacingtop', 'rowspacingbottom', 'lineheight', '|',
	        'customstyle', 'paragraph', 'fontfamily', 'fontsize', '|',
	        'directionalityltr', 'directionalityrtl', 'indent', '|',
	        'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|', 'touppercase', 'tolowercase', '|',
	        'link', 'unlink', 'anchor', '|', 'imagenone', 'imageleft', 'imageright', 'imagecenter', '|',
	        'simpleupload', 'insertimage', 'emotion', 'insertvideo', 'music', 'map', 'pagebreak', 'template', 'background', '|',
	        'horizontal', 'date', 'time', 'spechars', 'wordimage', '|',
	        'inserttable', 'deletetable', 'insertparagraphbeforetable', 'insertrow', 'deleterow', 'insertcol', 'deletecol', 'mergecells', 'mergeright', 'mergedown', 'splittocells', 'splittorows', 'splittocols', 'charts', '|',
	        'print', 'preview', 'searchreplace', 'help', 'drafts'
	    ]],
	    //@TODO 定义一个精简版
	    base : [['undo', 'redo','bold', 'italic', 'underline', 'paragraph', 'fontfamily', 'fontsize']]
    },
    defaultGoodsImg  :  uiBase + "img/default_goods_image_240.gif",
    dialogLoading : "<div><img src='"+uiBase+"img/loading.gif"+"'>加载中...</div>"
};
window.uploadSourcesMap = $.GLOBAL.config.uploadSourcesMap;






$.GLOBAL.utils = {
	loadBootTable: function(options) {
        return new bootTableApp(options);
	},
   
	getSearchData : function(containerId) {
		var result = {};
		$(containerId)
			.find('input,textarea,select')
			.each(
                function() {
                    var key;
                    if ($(this).attr("data-ignore") == "true") {
                        return true;
                    }
                    if ($(this)
                            .hasClass(
                                    "select2-focusser select2-offscreen")
                            || $(this).hasClass(
                                    "select2-input")) {
                        return true;
                    }
                    key = $(this).attr('name');
                    if (key) {
                        if ($(this).attr("Type") == "checkbox") {
                            if ($(this).val() == "true") {
                                result[key] = true;
                            } else {
                                result[key] = false;
                            }
                        } else if ($(this).attr("Type") == "radio") {
                            if ($(this).is(":checked")) {
                                result[key] = $(this).val();
                            } else {
                                return;
                            }
                        } else {
                            result[key] = $(this).val();
                        }
                    }
                });
        return result;
    },
    getWindow: function() {
        if(!_.isUndefined(top.window)) {
            return top.window;
        } 
        return window;
    },
    isEmail: function(e) {
		var t = /^([a-zA-Z0-9_\.\-\+])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
		return t.test(e) ? !0 : !1
	},
	isPhoneNumber: function(e) {
		var t = /^1[0-9]{10}$/;
		return t.test(e) ? !0 : !1
	},
	isDate: function(e) {
		var t = /^(\d{4})\-(\d{1,2})\-(\d{1,2})$/;
		return t.test(e) ? !0 : !1
	},
	isNumber: function(e) {
		var t = /^\d*$/;
		return null == e.match(t) ? !1 : !0
	},
	isQq: function(e) {
		var t = /^\d{5,10}$/;
		return t.test(e) ? !0 : !1
	},
	isDecimal: function(e) {
		var t = /^\d{0,8}\.{0,1}(\d{1,2})?$/;
		return null == e.match(t) ? !1 : !0
	},
	get_urlparam : function(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) return decodeURIComponent(r[2]);
        return "";
    }
};

$.GLOBAL.utils.url = (function (window, document, undefined) {
    return {
        /**
         * 函数描述：获取URL参数
         *
         * @param  {string} url
         * @return {object} Object
         */
        getQueryStringObj: function (url) {
            var search = url.slice(url.indexOf('?') + 1),
                result = {},
                queryString = search || location.search.slice(1),
                re = /([^&=]+)=([^&]*)/g,
                m;

            while ((m = re.exec(queryString)) !== null) {
                result[decodeURIComponent(m[1])] = decodeURIComponent(m[2]);
            }
            return result;
        },

        /**
         * 函数描述：获取URL中指定参数值
         * @param name {String} 参数名称
         * @returns {String}  参数值
         */
        getQueryString: function (name) {
            var reg = new RegExp("(^|&)@name=([^&]*)(&|$)".replace("@name", name), "i"),
                r = window.location.search.substr(1).match(reg);
            if (r !== null) {
                return unescape(r[2]);
            }
            return null;
        }
    };
}(window, document, undefined));


var bootTableApp = function(options) {
    this.$dataListTable = $(options.table);
    this.selectedIds=[];
    if(!_.isUndefined(options.removeBtn)) {
        this.$removeBtn = $(options.removeBtn);
    }
    if(!_.isUndefined(options.refreshBtn)) {
        this.$refreshBtn = $(options.refreshBtn);
    }
    
    this.options = options;
  
    this.init();
    this.eventHandle();
    
}
bootTableApp.prototype.eventHandle = function() {
    var that = this;
    this.$dataListTable.on('check.bs.table uncheck.bs.table ' +
        'check-all.bs.table uncheck-all.bs.table', function () {
        if(!_.isUndefined(that.$removeBtn)) {  
            that.$removeBtn.prop('disabled', !that.$dataListTable.bootstrapTable('getSelections').length);
        }

    });
    this.$dataListTable.on("check.bs.table", function(e, row, chkbox) {
    	if(chkbox[0].type == "radio") {
            chkbox.closest('tbody').find('tr').removeClass('success');
        }
        chkbox.closest('tr').addClass('success');
    });
    this.$dataListTable.on("uncheck.bs.table", function(e, row, chkbox) {
        //e.text("Event: uncheck.bs.table")
         chkbox.closest('tr').removeClass('success');
    });
    if(!_.isUndefined(that.$refreshBtn)) {  
        this.$refreshBtn.on('click', function() {
            that.refresh();
        });
    }
    
};
bootTableApp.prototype.refresh = function() {
   
    this.$dataListTable.bootstrapTable('refresh', {url: this.options.url});
};

bootTableApp.prototype.refreshThis = function() {
	var num = this.$dataListTable.bootstrapTable('getOptions').pageNumber
    this.$dataListTable.bootstrapTable('refresh', {query: {currentPage:num}});
};
/*
 * {"pageSize":10,"currentPage":1,"totalRecord":2,"totalPages":1,"list":{[],[]}}
 * */
bootTableApp.prototype.responseHandler = function(res) {
    if(this.pagination == true) {
    	if(_.isUndefined(res.list)) {
    		res.list = [];
    	}
    	res.rows = res.list;
    	if(_.isNull(res.rows)) {
    		res.rows = [];
    	}
    	res.total = res.totalRecord;
	    return res;
	} else {
		if(_.isUndefined(res.data)) {
    		res.data = [];
    	}
		return res.data;
	}
};
bootTableApp.prototype.queryDefaultParams = function(params) {
   return {
        pageSize: params.limit,
        currentPage: Math.floor(params.offset/params.limit)+1,
        orderField: params.sort,
    };
};
bootTableApp.prototype.getIdSelections = function() {
	var results = [], that = this;
    $.map(this.$dataListTable.bootstrapTable('getSelections'), function (row) {
        results.push(row[that.options.idField]);
    });
    return results;
};   
bootTableApp.prototype.init = function() {
	if(this.options.url){
		this.options.url =  $.GLOBAL.config.urlPrefix+this.options.url;
	}
	
	if(!this.options.pageList){
		this.options.pageList = [10,20,25,50];
	}
	
    this.options.locale  = "zh-CN";
    this.options.method  =  "post";
    this.options.cache   = false;
    this.options.striped = true;
    //this.options.queryParams = this.options.queryAddParams || function() { return {};};
    
    this.options.queryParams = _(function(params){
        return $.extend(this.options.queryAddParams(params), this.queryDefaultParams(params));
    }).bind(this);
    this.options.responseHandler = this.responseHandler;
    
    
    this.$dataListTable.bootstrapTable(this.options);
   
};


(function ($) {
    'use strict';
    //bug fix: 20160602
    
    //use $(window).loadingInfo('error', 'msg') instead  $('body').loadingInfo('error', 'msg')
    $.fn.loadingInfo = function () {
    	var options = {};
    	if ($.isPlainObject(arguments[0])) {
    		options = arguments[0];
    	} else if (typeof arguments[0] === "string" && typeof arguments[1] === "string") {
    		options.type = arguments[0];
    		options.text = arguments[1];
    		if(_.isNumber(arguments[2])) {
                options.timeouts = arguments[2];
            }
    	} else {
    		return false;
    	}
    	
    	 
        var settings = $.extend({
            type : "success",
            text : "操作中...",
            callBack : $.noop,
            timeouts : 2000
        }, options);
        return this.each(function () {
            var item = $(this);
            if(settings.type == "success" || settings.type == "ok" || settings.type == "finish") {
                
                item.block({ 
                     css: {  backgroundColor:'#9adda7',border:'1px solid #a8cbb9',color:'#fff',width:'auto',padding:'20px 40px 20px 20px'} , 
                     message:"<div class='success'>"+settings.text+"</div>",
                     showOverlay:false,
                     baseZ : 1051
                }); 
                setTimeout(
                function() {
                    item.unblock();
                    if(_.isFunction(settings.callBack)) {
                        settings.callBack();
                    }
                }, settings.timeouts);
            } else if(settings.type == "error" || settings.type == "warn") {
                item.block({ 
                    css: {  backgroundColor:'#faa499',border:'1px solid #f49292',color:'#fff',width:'auto',padding:'20px 40px 20px 20px',fontSize: "16px"} , 
                    message:"<div class='error'>"+settings.text+"</div>",
                    baseZ : 1051,
                    showOverlay:false
                }); 
                setTimeout(
                function() {
                    item.unblock();
                    if(_.isFunction(settings.callBack)) {
                        settings.callBack();
                    }
                }, settings.timeouts);
            } else if(settings.type == "info" || settings.type == "show") {
                item.block({ 
                    css: {  backgroundColor:'#f8c684',border:'1px solid #f7b885',color:'#fff',width:'auto',padding:'20px 40px'} , 
                    message:settings.text,
                    baseZ : 1051,
                    showOverlay:false
                }); 
            }
        });
   };
}(jQuery));
(function ($) {
    'use strict';
	$.fn.saving = function(text) {
		this.data("orginaltext", this.text());
		text = text || this.text()+"中..." || "保存中...";
		return this.prop('disabled', true).text(text);
	};
	$.fn.saved = function(text) {
		text = text || this.data('orginaltext') || "保存";
		return this.prop('disabled', false).text(text);
	};
}(jQuery));
//令牌
$.ajaxSetup ({
	cache: false //close AJAX cache
});

$(document).ajaxSend(function(event, request, settings) {
	if (!settings.crossDomain && settings.type != null && settings.type.toLowerCase() == "post") {
		var token = getCookie("token");
		if (token != null) {
			request.setRequestHeader("token", token);
		}
	}
});

//ajax global event

/*
ACK = "ACK";
NACK = "NACK";
REDIRECT = "REDIRECT";
VALIDATION_ERROR = "VALIDATION_ERROR";
COMMON_ERROR = "COMMON_ERROR";
SESSION_TIME_OUT = "SESSION_TIME_OUT";
BUSINESS_ERROR = "BUSINESS_ERROR";
UNAUTHORIZED = "UNAUTHORIZED"; 

ajaxError --> ajaxComplete
*/
$(document).ajaxError(function(event, xhr, settings, thrownError ) {
    try{
        var error = $.parseJSON(xhr.responseText);
        if(error.code === 'UNAUTHORIZED' ){
            BootstrapDialog.show({
                title: '对不起，您无此操作权限！',
                type : BootstrapDialog.TYPE_WARNING,
                message: error.message,
                draggable: true,
                size : BootstrapDialog.SIZE_SMALL,
                buttons: [{
                    label: '确认',
                    cssClass: 'btn-primary saveAddEditTpl',
                    action: function(dialog) {
                        dialog.close();
                        $.GLOBAL.utils.getWindow().location.reload(true);
                    }
                }]
            });
        } else  if( error.code === 'SESSION_TIME_OUT'){
            BootstrapDialog.show({
                title: '登录已过期',
                type : BootstrapDialog.TYPE_WARNING,
                message: error.message,
                draggable: true,
                size : BootstrapDialog.SIZE_SMALL,
                buttons: [{
                    label: '确认',
                    cssClass: 'btn-primary saveAddEditTpl',
                    action: function(dialog) {
                        dialog.close();
                        $.GLOBAL.utils.getWindow().location.reload(true);
                    }
                }]
            });
        } else{ 
            
            BootstrapDialog.show({
                title: '操作错误！',
                type : BootstrapDialog.TYPE_WARNING,
                message: error.message,
                draggable: true,
                size : BootstrapDialog.SIZE_SMALL,
                buttons: [{
                    label: '确认',
                    cssClass: 'btn-primary saveAddEditTpl',
                    action: function(dialog) {
                        dialog.close();
                        
                        //$.GLOBAL.utils.getWindow().location.reload();
                    }
                }]
            });
        }
    }catch(Error){
       
        BootstrapDialog.show({
            title: '请稍后重试！',
            type : BootstrapDialog.TYPE_WARNING,
            message: '服务器繁忙请稍候再试' || '未知错误',
            draggable: true,
            size : BootstrapDialog.SIZE_SMALL,
            buttons: [{
                label: '确认',
                cssClass: 'btn-primary saveAddEditTpl',
                action: function(dialog) {
                    dialog.close();
                   
                }
            }]
        });
        console.log(Error);
    }
});
$(document).ajaxSuccess(function(event, xhr, settings) {
	//all common ajax must be wrapped by resultDto
    try{
    	var $loadingObject =  $(window);
    	if(!_.isUndefined(arguments[2].$loadingObject) && arguments[2].$loadingObject instanceof jQuery) {
    		$loadingObject = arguments[2].$loadingObject;
    	}
    	
        // Status 206 (Partial Content) indicates that the partial HTML string
        // are returned as the ajax result, we need avoid to parseJSON against
        // the HTML string.
        if(xhr.status != 206){
            
            var result = $.parseJSON(xhr.responseText);
            if(result && result.hasOwnProperty('code') &&
                    result.hasOwnProperty('message') && result.hasOwnProperty('data')){
                if(result.code === 'ACK' && result.message){
                    /*
                	$('body').loadingInfo({
                        type : "error",
                        text : result.message
                    });
                    */
                    
                }else if(result.code === 'VALIDATION_ERROR'){
                   
                	$loadingObject.loadingInfo({
                        type : "error",
                        text : result.message
                    });
                   
                }else if(result.code === 'REDIRECT'){
                    //@TODO need test
                    location.href = result.data;
                }else if(result.code === 'UNAUTHORIZED'){
                    
                	$loadingObject.loadingInfo({
                        type : "error",
                        text : result.message || "权限不足" ,
                        timeouts : 5000
                        
                    });
                }else if(result.code === 'NACK'  || result.code === 'BUSINESS_ERROR') {
                  
                	
                	$loadingObject.loadingInfo({
                        type : "error",
                        text : result.message || "数据(参数)错误",
                        timeouts : 5000
                        
                    });
                }
            }
        }
    }catch(Error){
    	
    	
        /*
         *有Erorr发生
        $('body').find('.ajax-backdrop').remove();
        popup({
            title: '请稍后重试',
            msg:  '服务器繁忙请稍候再试' || '未知错误'
        });
        
        BootstrapDialog.show({
            title: '请稍后重试！',
            type : BootstrapDialog.TYPE_WARNING,
            message: '服务器繁忙请稍候再试' || '未知错误',
            draggable: true,
            size : BootstrapDialog.SIZE_SMALL,
            buttons: [{
                label: '确认',
                cssClass: 'btn-primary ',
                action: function(dialog) {
                    dialog.close();
                   
                }
            }]
        });
        console.log(Error);
        */
    	console.log(Error);
    }
});

    
$(document).ajaxComplete(function(event, request, settings) {
	var loginStatus = request.getResponseHeader("loginStatus");
	var tokenStatus = request.getResponseHeader("tokenStatus");
	var sysStatus = request.getResponseHeader("sysStatus");
	var resJson = request.responseJSON || {};
	if (loginStatus == "accessDenied" ) { //|| resJson.code == "SESSION_TIME_OUT"
		//$.message("warn", "登录超时，请重新登录");
		$(window).loadingInfo('error', '登录超时，请重新登录');
		setTimeout(function() {
			$.GLOBAL.utils.getWindow().location.reload(true);
		}, 2000);
	} else if (loginStatus == "unauthorized") { // || resJson.code == "UNAUTHORIZED"
		//$.message("warn", "对不起，您无此操作权限！");
		$('body').loadingInfo('error', '对不起，您无此操作权限！');
		
	} else if (tokenStatus == "accessDenied") {
		var token = getCookie("token");
		if (token != null) {
			$.extend(settings, {
				global: false,
				headers: {token: token}
			});
			$.ajax(settings);
		}
	} else if (sysStatus == "500" || /5[\d]{2}/.test(request.status)) {
		//$.message("warn", "操作错误");
		//$('body').loadingInfo('error', '操作错误！');
	}
});




// "edit/{{attrId}}/{{who}}".template({attrId:5,who:"me"}) ==> edit/5/me
String.prototype.template = function(data) {
	if(!_.isUndefined(template)) {
		return template.compile(this)(data);
	} else {
		return this;
	}
};
//"edit/{0}/{1}".fromat(5, 'me') ==> edit/5/me
String.prototype.format = function(data) {
    var _args = arguments;
    return this.replace(/{(\d+)}/g, function(match, number) { 
        return typeof _args[number] !== 'undefined' ? _args[number] : match;
    });
};



$(function () {
    $('body').on('click', '.openInTab', function(e) { 
    	window.topManager.tabBarObject.menuItemClickHandler(e);
    });
});

//===================================================================
var messages = {
	"admin.message.success": "操作成功",
	"admin.message.error": "操作错误",
	"admin.dialog.ok": "确&nbsp;&nbsp;定",
	"admin.dialog.cancel": "取&nbsp;&nbsp;消",
	"admin.dialog.deleteConfirm": "您确定要删除吗？",
	"admin.dialog.clearConfirm": "您确定要清空吗？",
	"admin.browser.title": "选择文件",
	"admin.browser.upload": "本地上传",
	"admin.browser.parent": "上级目录",
	"admin.browser.orderType": "排序方式",
	"admin.browser.name": "名称",
	"admin.browser.size": "大小",
	"admin.browser.type": "类型",
	"admin.browser.select": "选择文件",
	"admin.upload.sizeInvalid": "上传文件大小超出限制",
	"admin.upload.typeInvalid": "上传文件格式不正确",
	"admin.upload.invalid": "上传文件格式或大小不正确",
	"admin.validate.required": "必填",
	"admin.validate.email": "E-mail格式错误",
	"admin.validate.url": "网址格式错误",
	"admin.validate.date": "日期格式错误",
	"admin.validate.dateISO": "日期格式错误",
	"admin.validate.pointcard": "信用卡格式错误",
	"admin.validate.number": "只允许输入数字",
	"admin.validate.digits": "只允许输入零或正整数",
	"admin.validate.minlength": "长度不允许小于{0}",
	"admin.validate.maxlength": "长度不允许大于{0}",
	"admin.validate.rangelength": "长度必须在{0}-{1}之间",
	"admin.validate.min": "不允许小于{0}",
	"admin.validate.max": "不允许大于{0}",
	"admin.validate.range": "必须在{0}-{1}之间",
	"admin.validate.accept": "输入后缀错误",
	"admin.validate.equalTo": "两次输入不一致",
	"admin.validate.remote": "输入错误",
	"admin.validate.integer": "只允许输入整数",
	"admin.validate.positive": "只允许输入正数",
	"admin.validate.negative": "只允许输入负数",
	"admin.validate.decimal": "数值超出了允许范围",
	"admin.validate.pattern": "格式错误",
	"admin.validate.extension": "文件格式错误",
	"admin.dialog.editConfirm":"点击确定后该组织将无法添加下级组织，是否确定?",
};

// 多语言
function message(code) {
	if (code != null) {
		var content = messages[code] != null ? messages[code] : code;
		if (arguments.length == 1) {
			return content;
		} else {
			if ($.isArray(arguments[1])) {
				$.each(arguments[1], function(i, n) {
					content = content.replace(new RegExp("\\{" + i + "\\}", "g"), n);
				});
				return content;
			} else {
				$.each(Array.prototype.slice.apply(arguments).slice(1), function(i, n) {
					content = content.replace(new RegExp("\\{" + i + "\\}", "g"), n);
				});
				return content;
			}
		}
	}
}   
/*cookie*/
//添加Cookie
function addCookie(name, value, options) {
	if (arguments.length > 1 && name != null) {
		if (options == null) {
			options = {};
		}
		if (value == null) {
			options.expires = -1;
		}
		if (typeof options.expires == "number") {
			var time = options.expires;
			var expires = options.expires = new Date();
			expires.setTime(expires.getTime() + time * 1000);
		}
		document.cookie = encodeURIComponent(String(name)) + "=" + encodeURIComponent(String(value)) + (options.expires ? "; expires=" + options.expires.toUTCString() : "") + (options.path ? "; path=" + options.path : "") + (options.domain ? "; domain=" + options.domain : ""), (options.secure ? "; secure" : "");
	}
}

// 获取Cookie
function getCookie(name) {
	if (name != null) {
		var value = new RegExp("(?:^|; )" + encodeURIComponent(String(name)) + "=([^;]*)").exec(document.cookie);
		return value ? decodeURIComponent(value[1]) : null;
	}
}

// 移除Cookie
function removeCookie(name, options) {
	addCookie(name, null, options);
}
/*cookie end */



/**
 * iTsai WebTools(Web开发工具集)
 * 
 * @author Chihpeng Tsai(470597142@qq.com)
 * @description 表单处理工具.
 */
 
(function() {
    if (!window.iTsai) iTsai = {};
})();
 
iTsai.form = {
    toString : function() {
        return 'iTsai.form - 表单处理工具';
    },
    /**
     * 获取单选框值,如果有表单就在表单内查询,否则在全文查询
     * 
     * @param{String}name radio名称
     * @param{$()} frm jQuery object
     * @returns
     */
    getRadioValue : function(name, frm) {
        if (frm && frm.find)
            return frm.find('input[name="' + name + '"]:checked').val();
        return $('input[name="' + name + '"]:checked').val();
    },
    /**
     * 设置单选框值,如果有表单就在表单内查询,否则在全文查询
     * 
     * @param{String}name radio名称
     * @param{String} value
     * @param{$()} frm
     * @returns
     */
    setRadioValue : function(name, value, frm) {
        if (frm && frm.find)
            return frm
                    .find('input[name="' + name + '"][value="' + value + '"]')
                    .attr('checked', true);
        return $('input[name="' + name + '"][value="' + value + '"]').attr(
                'checked', true);
    },
    /**
     * 设置select下拉框的值
     * 
     * @param{String} selectId 下拉框id号
     * @param{String/Number} value 值
     * @param{$()} form jQuery object
     * @returns
     */
    setSelectValue : function(selectId, value, frm) {
        if (frm && frm.find)
            return frm.find('#' + selectId + ' option[value="' + value + '"]')
                    .attr('selected', true);
        return $('#' + selectId + ' option[value="' + value + '"]').attr(
                'selected', true);
    },
    /**
     * 在id区域内执行回车提交数据<br>
     * 实际处理中应该将提交按键放在id区域外,避免重复提交
     * 
     * @param{String} id 被绑定对象的ID号
     * @param{Function} fn 要选择的函数
     * @returns {Boolean}
     */
    bindingEnterKey : function(id, fn) {
        $('#' + id).keydown(function(e) {
            if (e.keyCode == 13) {
                if (fn)
                    fn();
            }
        });
    },
    /**
     * 将输入控件集合序列化成对象<br>
     * 名称或编号作为键，value属性作为值
     * 
     * @param {Array}
     *            inputs input/select/textarea的对象集合
     * @return {object} json 对象 {key:value,...}
     */
    _serializeInputs : function(inputs) {
        var json = {};
        if (!inputs) {
            return json;
        }
        for ( var i = inputs.length - 1; i >= 0; i--) {
            var input = $(inputs[i]);
            var type = input.attr('type');
            if (type) {
                type = type.toLowerCase();
            }
            var tagName = input.get(0).tagName;
            var id = input.attr('id');
            var name = input.attr('name');
            var value = null;
 
            // 判断输入框是否已经序列化过
            if (input.hasClass('_isSerialized')) {
                continue;
            }
 
            // input输入标签
            if (tagName == 'INPUT' && type) {
                switch (type) {
                case 'checkbox': {
                    value = input.is(':checked');
                }
                    break;
 
                case 'radio': {
                    if (input.is(':checked')) {
                        value = input.attr('value');
                    } else {
                        continue;
                    }
                }
                    break;
 
                default: {
                    value = input.val();
                }
                }
            } else {
                // 非input输入标签，如：select,textarea
                value = input.val();
            }
 
            json[name || id] = $.trim(value);
            // 清除序列化标记
            input.removeClass('_isSerialized');
        }
        return json;
    },
    /**
     * 将值填充到输入标签里面
     * 
     * @param{Array} inputs 输入标签集合
     * @param{String/Number} value 值
     * @returns {___anonymous188_8285}
     */
    _deserializeInputs : function(inputs, value) {
        if (!inputs && value == null) {
            return this;
        }
 
        for ( var i = inputs.length - 1; i >= 0; i--) {
            var input = $(inputs[i]);
            // 判断输入框是否已经序列化过
            if (input.hasClass('_isSerialized')) {
                continue;
            }
            var type = input.attr('type');
            if (type) {
                type = type.toLowerCase();
            }
            if (type) {
                switch (type) {
                case 'checkbox': {
                    input.attr('checked', value);
                }
                    break;
 
                case 'radio': {
                    input.each(function(i) {
                        var thiz = $(this);
                        if (thiz.attr('value') == value) {
                            thiz.attr('checked', true);
                        }
                    });
                }
                    break;
 
                default: {
                    input.val(value);
                }
                }
            } else {
                input.val(value);
            }
 
            input.addClass('_isSerialized');
        }
 
        return this;
    },
    /**
     * 在分组中查找 fieldset (如：fieldset="user")开头的数据域<br>
     * 
     * @param {Array}
     *            groups 输入框分组容器集合
     * @return {Object} json 对象 {key:value,...}
     */
    _serializeGroups : function(groups) {
        var json = {};
        if (!groups) {
            return json;
        }
 
        for ( var i = groups.length - 1; i >= 0; i--) {
            var group = $(groups[i]);
            var key = group.attr('fieldset');
            if (!key) {
                continue;
            }
            var inputs = group
                    .find('input[type!=button][type!=reset][type!=submit],select,textarea');
            json[key] = this._serializeInputs(inputs);
            // 添加序列化标记
            inputs.addClass('_isSerialized');
        }
        return json;
    },
    /**
     * 序列化表单值,结果以key/value形式返回key为表单对象名称(name||id),value为其值.<br>
     * HTML格式：<br>
     * 1).表单容器：通常是一个form表单（如果不存在就以body为父容器），里面包含输入标签和子容器;<br>
     * 2).子容器（也可以没有）：必须包括属性fieldset="XXX" div标签，里面包含输入标签和子容器。<br>
     * 序列化后将生成以XXX为主键的json对象.如果子容器存在嵌套则以fieldset为主键生成不同分组的json对象.<br>
     * 3).输入标签：输入标签为input类型标签（包括：'checkbox','color','date','datetime','datetime-local',<br>
     * 'email','file','hidden','month','number','password','radio','range
     * ','reset','search','submit',<br>
     * 'tel','text','time ','url','week'）.
     * 而'button','reset','submit','image'会被过虑掉.
     * 
     * @param{$()} frm jQuery表单对象
     * @returns {Object} json对象 最多包含两层结构
     */
    serialize : function(frm) {
        var json = {};
        frm = frm || $('body');
        if (!frm) {
            return json;
        }
 
        var groups = frm.find('div[fieldset]');
        var jsonGroup = this._serializeGroups(groups);
 
        var inputs = frm
                .find('input[type!=button][type!=reset][type!=submit][type!=image],select,textarea');
        var json = this._serializeInputs(inputs);
 
        for ( var key in jsonGroup) {
            json[key] = jsonGroup[key];
        }
        return json;
    },
    /**
     * 填充表单内容：将json数据形式数据填充到表单内，只解析单层json结构
     * 
     * @param{$()} frm jQuery表单对象（或其它容器标签对象，如：div）
     * @param{Object} json 序列化好的json数据对象，最多只包含两层嵌套
     * @returns {Object} iTsai.form 对象
     */
    deserializeSimple : function(frm, json) {
        frm = frm || $('body');
        if (!frm || !json) {
            return this;
        }
 
        var _deserializeInputs = this._deserializeInputs;
        for ( var key in json) {
            var value = json[key];
            _deserializeInputs(frm, key, value);
        }
        return this;
    },
    /**
     * 获取合法的输入标签
     * 
     * @param {$()}
     *            container 标签容器
     * @returns {[]} inputs jQuery对象数组
     */
    _filterInputs : function(container) {
        var inputs = $(container
                .find('input[type!=button][type!=reset][type!=submit][type!=image][type!=file],select,textarea'));
        return inputs;
    },
    /**
     * 查找符合条件的输入标签
     * 
     * @param{Array} inputs jQueery输入标签数组
     * @param{String} key 查询关键字
     * @returns{Array} input 标签数组
     */
    _findInputs : function(inputs, key) {
        var input = $(inputs.filter('input[name=' + key + '],input[id=' + key
                + '],textarea[name=' + key + '],textarea[id=' + key
                + '],select[name=' + key + '],select[id=' + key + ']'));
        return input;
    },
    /**
     * 填充表单内容：将json数据形式数据填充到表单内，最多解析两层json结构
     * 
     * @param{$()} frm jQuery表单对象（或其它容器标签对象，如：div）
     * @param{Object} json 序列化好的json数据对象，最多只包含两层嵌套
     * @returns {Object} iTsai.form 对象
     */
    deserialize : function(frm, json) {
        frm = frm || $('body');
        if (!frm || !json) {
            return this;
        }
 
        // 缓存json第一层数据对象
        var objects = {};
        // 缓存json嵌套层数据（第二层），将首先被赋值，以避免覆盖
        var groups = {};
 
        // 数据分组
        for ( var key in json) {
            var value = json[key];
            if (typeof value == 'object' && !$.isArray(value)) {
                groups[key] = value;
            } else {
                objects[key] = value;
            }
        }
 
        var _deserializeInputs = this._deserializeInputs;
        var _filterInputs = this._filterInputs;
        var _findInputs = this._findInputs;
 
        // 填充嵌套层数据
        for ( var key in groups) {
            var json = groups[key];
            var div = frm.find('div[fieldset="' + key + '"]');
            if (!div.length) {
                continue;
            }
            var inputs = _filterInputs(div);
            if (!inputs.length) {
                continue;
            }
            for ( var k in json) {
                var val = json[k];
                var input = _findInputs(inputs, k);
                _deserializeInputs(input, val);
            }
        }
 
        // 填充第一层数据
        var inputs = _filterInputs(frm);
        for ( var key in objects) {
            var value = objects[key];
            var input = _findInputs(inputs, key);
            _deserializeInputs(input, value);
        }
 
        inputs.filter('._isSerialized').removeClass('_isSerialized');
        return this;
    }
};

(function ($) {
    $.fn.frmSerialize = function() {
       return iTsai.form.serialize($(this));
    }; 
    $.fn.frmDeSerialize = function(json) {
       return iTsai.form.deserialize($(this), json);
    };  
}(jQuery));

/*
 * jzTree dropdown 
 */
$.dropDownMenu = function(options){
	var $idInput = options.$idInput,
	$nameInput = options.$nameInput,
	$ztree = options.$ztree;
	
	var setting = 
		{
			async: {
				autoParam: ["id"],
				enable: true,
				type: "POST",
				url: options.url
			},
			view: {
				dblClickExpand: false
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				onClick: onClick, 
				onAsyncSuccess : function() {
					console.log(arguments);
				}
			}
		};
	
	$.extend(setting, options.setting, options.initData);
	setting.callback.onClick = onClick;

	// 点击节点自动check此节点
	function onClick(e, treeId, treeNode) {
		var keys = this._z.data.getSettings()[treeId].data.key;
		$nameInput.val(treeNode[keys.name]);
		$idInput.val(treeNode[keys.id]);
		$idInput.add($nameInput).change(); // 添加change事件，所以这个输入框可以进行valid验证 或 其他验证
		onBodyDown(null, false);
	}
	
	var ztreeToool = $.fn.zTree.init($ztree, setting);
	
	$("body").bind("mousedown", onBodyDown);
	
	function onBodyDown(event, show){
		// 不根据event,强制打开，关闭
		if (show != null && typeof show == "boolean") {
			if (show) {
				$ztree.parent().slideDown("fast");
			} else {
				$ztree.parent().fadeOut("fast");
			}
			return;
		}
		// 根据event，打开关闭
		if (event.target.id == $nameInput.attr("id") 
				|| ($(event.target).hasClass("arrow") && $(event.target).siblings("#" + $nameInput.attr("id")).size() > 0)) {
			// 如果是点击输入框 和 对应的 arrow 按钮，自动切换打开关闭状态
			if ($ztree.parent().is(":hidden")) {
				$ztree.parent().slideDown("fast");
			} else {
				$ztree.parent().fadeOut("fast");
			}
		} else if (event.target.id != $ztree.parent().attr("id") 
				&& $(event.target).parents("#" + $ztree.parent().attr("id")).size() == 0) {
			// 如果是点击除 显示框 以外的任何地方，关闭下拉框
			if (!$ztree.parent().is(":hidden"))
				$ztree.parent().fadeOut("fast");
		}
	}
	
};
