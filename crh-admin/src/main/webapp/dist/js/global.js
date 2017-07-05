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
    ossUploadNewUrl : urlPrefix + "main/ossUploadImage/{{source}}",//File name re generation (excluding Chinese)
    ossUploadUrlList : urlPrefix + "main/ossAddImageList/{{source}}",
    uploadSourcesMap :{
    	slides : 10,
    	resource : 11,
    	partner : 12
    },
    //qrcode 
	qrcodes : {
		qrText : "http://www.wuliangye.com/qrcode{{orgId}}", //@TODO  //Merchant two-dimensional code contents, existing variables{{loginId}}, {{orgId}}, {{userName}}
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
	
    newOrderTips : window.newOrderTips, //Open a new order reminder
    newOrderInterval : window.newOrderInterval, //How long to detect a new order unit: second
    newOrderCookieName : "wlyadmin_LastCheckOrder",
	overTimeOrderTips : window.overTimeOrderTips,         //Whether to open a timeout order reminder
	overTimeOrderInterval : window.overTimeOrderInterval, //How long to detect a timeout order unit: second
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
	    //@TODO Define a streamlined version
	    base : [['undo', 'redo','bold', 'italic', 'underline', 'paragraph', 'fontfamily', 'fontsize']]
    },
    defaultGoodsImg  :  uiBase + "img/default_goods_image_240.gif",
    dialogLoading : "<div><img src='"+uiBase+"img/loading.gif"+"'>loading...</div>"
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
         * Function Description: Get the URL parameter
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
         * Function Description: Get the specified Parameter value in the URL
         * @param name {String} Parameter name
         * @returns {String}  Parameter value
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
            text : "operating...",
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
		text = text || this.text()+"ing..." || "saving...";
		return this.prop('disabled', true).text(text);
	};
	$.fn.saved = function(text) {
		text = text || this.data('orginaltext') || "save";
		return this.prop('disabled', false).text(text);
	};
}(jQuery));
//token
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
                title: 'Opration Error!',
                type : BootstrapDialog.TYPE_WARNING,
                message: error.message,
                draggable: true,
                size : BootstrapDialog.SIZE_SMALL,
                buttons: [{
                    label: 'Ok',
                    cssClass: 'btn-primary saveAddEditTpl',
                    action: function(dialog) {
                        dialog.close();
                        $.GLOBAL.utils.getWindow().location.reload(true);
                    }
                }]
            });
        } else  if( error.code === 'SESSION_TIME_OUT'){
            BootstrapDialog.show({
                title: 'Login has expired',
                type : BootstrapDialog.TYPE_WARNING,
                message: error.message,
                draggable: true,
                size : BootstrapDialog.SIZE_SMALL,
                buttons: [{
                    label: 'sure',
                    cssClass: 'btn-primary saveAddEditTpl',
                    action: function(dialog) {
                        dialog.close();
                        $.GLOBAL.utils.getWindow().location.reload(true);
                    }
                }]
            });
        } else{ 
            
            BootstrapDialog.show({
                title: 'Opration Error!',
                type : BootstrapDialog.TYPE_WARNING,
                message: "Abnormal server",
                draggable: true,
                size : BootstrapDialog.SIZE_SMALL,
                buttons: [{
                    label: 'Ok',
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
            title: 'Please try again later!',
            type : BootstrapDialog.TYPE_WARNING,
            message: 'Please wait while the server is busy' || 'unknown mistake',
            draggable: true,
            size : BootstrapDialog.SIZE_SMALL,
            buttons: [{
                label: 'sure',
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
                        text : result.message || "Insufficient permissions" ,
                        timeouts : 5000
                        
                    });
                }else if(result.code === 'NACK'  || result.code === 'BUSINESS_ERROR') {
                  
                	
                	$loadingObject.loadingInfo({
                        type : "error",
                        text : result.message || "Data (parameter) error",
                        timeouts : 5000
                        
                    });
                }
            }
        }
    }catch(Error){
    	console.log(Error);
    }
});

    
$(document).ajaxComplete(function(event, request, settings) {
	var loginStatus = request.getResponseHeader("loginStatus");
	var tokenStatus = request.getResponseHeader("tokenStatus");
	var sysStatus = request.getResponseHeader("sysStatus");
	var resJson = request.responseJSON || {};
	if (loginStatus == "accessDenied" ) { //|| resJson.code == "SESSION_TIME_OUT"
		//$.message("warn", "Login timeout, please log in again");
		$(window).loadingInfo('error', 'Login timeout, please log in again');
		setTimeout(function() {
			$.GLOBAL.utils.getWindow().location.reload(true);
		}, 2000);
	} else if (loginStatus == "unauthorized") { // || resJson.code == "UNAUTHORIZED"
		//$.message("warn", "Sorry, you do not have this permission!");
		$('body').loadingInfo('error', 'Sorry, you do not have this permission!');
		
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
	"admin.message.success": "Success",
	"admin.message.error": "Operating Error",
	"admin.dialog.ok": "sure",
	"admin.dialog.cancel": "cancle",
	"admin.dialog.deleteConfirm": "Are you sure you want to delete?",
	"admin.dialog.clearConfirm": "Are you sure you want to clear it?",
	"admin.browser.title": "Select a document please",
	"admin.browser.upload": "Local Upload",
	"admin.browser.parent": "Parent directory",
	"admin.browser.orderType": "The way to sort",
	"admin.browser.name": "name",
	"admin.browser.size": "size",
	"admin.browser.type": "type",
	"admin.browser.select": "Select a document",
	"admin.upload.sizeInvalid": "The upload file size exceeds the limit",
	"admin.upload.typeInvalid": "The upload file is not formatted correctly",
	"admin.upload.invalid": "The upload file format or size is incorrect",
	"admin.validate.required": "Required",
	"admin.validate.email": "E-mail is malformed",
	"admin.validate.url": "The URL is malformed",
	"admin.validate.date": "Date format error",
	"admin.validate.dateISO": "Date format error",
	"admin.validate.pointcard": "Credit card is malformed",
	"admin.validate.number": "Only number",
	"admin.validate.digits": "Only zero or positive integers",
	"admin.validate.minlength": "Length is not allowed to be less than 0",
	"admin.validate.maxlength": "Length is not allowed to be greater than 0",
	"admin.validate.rangelength": "The length must be between 0 and 1",
	"admin.validate.min": "Not allowed less than 0",
	"admin.validate.max": "Not allowed to be greater than 0",
	"admin.validate.range": "Must be between0 and 1",
	"admin.validate.accept": "The input suffix is incorrect",
	"admin.validate.equalTo": "Two input inconsistencies",
	"admin.validate.remote": "input error",
	"admin.validate.integer": "Only integers",
	"admin.validate.positive": "Only positive numbers",
	"admin.validate.negative": "Only negative numbers",
	"admin.validate.decimal": "out of range",
	"admin.validate.pattern": "wrong format",
	"admin.validate.extension": "The file is malformed",
	"admin.dialog.editConfirm":"Click OK after the organization will not be able to add the subordinate organization, is it OK?",
};

// multi-language
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
//add Cookie
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

// remove Cookie
function getCookie(name) {
	if (name != null) {
		var value = new RegExp("(?:^|; )" + encodeURIComponent(String(name)) + "=([^;]*)").exec(document.cookie);
		return value ? decodeURIComponent(value[1]) : null;
	}
}

// removeCookie
function removeCookie(name, options) {
	addCookie(name, null, options);
}
/*cookie end */



/**
 * iTsai WebTools(Web development toolset)
 * 
 * @author Chihpeng Tsai(470597142@qq.com)
 * @description Form processing tools.
 */
 
(function() {
    if (!window.iTsai) iTsai = {};
})();
 
iTsai.form = {
    toString : function() {
        return 'iTsai.form - Form processing tools';
    },
    /**
     * Set the box value, if there is a form in the form of inquiries, or in the full text query
     * 
     * @param{String}name: radio's name
     * @param{$()} frm jQuery object
     * @returns
     */
    getRadioValue : function(name, frm) {
        if (frm && frm.find)
            return frm.find('input[name="' + name + '"]:checked').val();
        return $('input[name="' + name + '"]:checked').val();
    },
    /**
     * Set the box value, if there is a form in the form of inquiries, or in the full text query
     * 
     * @param{String}name: radio's name
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
     * Set the value of the select drop-down box
     * 
     * @param{String} selectId: drop-down box's id number
     * @param{String/Number} value 
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
     * Execute carriage return data in the id area<br>
     * In actual processing, the submit button should be placed outside the id area to avoid duplicate submission
     * 
     * @param{String} id binded object's ID number
     * @param{Function} fn selected function
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
     * The input control collection is serialized into object<br>
     * Name or number as the key, the value attribute as the value
     * 
     * @param {Array}
     *            inputs input/select/textarea object List
     * @return {object} json object {key:value,...}
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
 
            // To determine whether the input box has been serialized
            if (input.hasClass('_isSerialized')) {
                continue;
            }
 
            // input tag 
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
                // Non-input input tag,such as:select,textarea
                value = input.val();
            }
 
            json[name || id] = $.trim(value);
            //Clear the serialization flag
            input.removeClass('_isSerialized');
        }
        return json;
    },
    /**
     * Fill the value into the input tag
     * 
     * @param{Array} inputs: 	The enter label set
     * @param{String/Number} value 
     * @returns {___anonymous188_8285}
     */
    _deserializeInputs : function(inputs, value) {
        if (!inputs && value == null) {
            return this;
        }
 
        for ( var i = inputs.length - 1; i >= 0; i--) {
            var input = $(inputs[i]);
            // To determine whether the input box has been serialized
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
     * Find the data field starting with fieldset in groups<br>
     * 
     * @param {Array}
     *            groups Input box grouping container collection
     * @return {Object} json object {key:value,...}
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
            // Adding serialization Tags
            inputs.addClass('_isSerialized');
        }
        return json;
    },
    /**
     * To serialize form values, the result is returned as key/value in form key, form object name (name||id), and value as its value.<br>
     * HTML format:<br>
     * 1).Form: the container is usually a form form (if it does not exist in the body container, which contains the father) input label and sub container;<br>
     * 2).The child container (or not): it must include the attribute fieldset=, XXX, div tag, which contains input tags and sub containers.<br>
     * After serialization, jsonobject. will be generated with XXX as the primary key. If the child container is nested, then the jsonobject of the different groupings is generated with fieldset as the primary key.<br>
     * 3).Enter the label: enter the label as input type label(ncluding:'checkbox','color','date','datetime','datetime-local',<br>
     * 'email','file','hidden','month','number','password','radio','range
     * ','reset','search','submit',<br>
     * 'tel','text','time ','url','week').
     * and 'button','reset','submit','image'Will be filtered out.
     * 
     * @param{$()} frm jQuery form object
     * @returns {Object} jsonobject At most two layers are included
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
     * Fill in the form content: populate the form with JSON data form data and parse only a single layer JSON structure
     * 
     * @param{$()} frm jQuery  Form objects (or other container tagged objects, such as: DIV)
     * @param{Object} json A serialized JSON data object contains at most two nested layers
     * @returns {Object} iTsai.form object
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
     * Gets the valid input tag
     * 
     * @param {$()}
     *            container Label container
     * @returns {[]} inputs jQueryobject array
     */
    _filterInputs : function(container) {
        var inputs = $(container
                .find('input[type!=button][type!=reset][type!=submit][type!=image][type!=file],select,textarea'));
        return inputs;
    },
    /**
     * Find qualified input Tags
     * 
     * @param{Array} inputs jQueery Enter tag array
     * @param{String} key   Query keywords
     * @returns{Array} input  tag array
     */
    _findInputs : function(inputs, key) {
        var input = $(inputs.filter('input[name=' + key + '],input[id=' + key
                + '],textarea[name=' + key + '],textarea[id=' + key
                + '],select[name=' + key + '],select[id=' + key + ']'));
        return input;
    },
    /**
     * Fill in the form content: populate the form with JSON data form data, and parse the two - layer JSON structure at most
     * 
     * @param{$()} frm jQuery Form objects (or other container tagged objects, such as: DIV)
     * @param{Object} json A serialized JSON data object contains at most two nested layers
     * @returns {Object} iTsai.form object
     */
    deserialize : function(frm, json) {
        frm = frm || $('body');
        if (!frm || !json) {
            return this;
        }
 
        // Cache JSON first layer data object
        var objects = {};
        // Caching JSON nested layer data (second layers) will be assigned first to avoid coverage
        var groups = {};
 
        // Data grouping
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
 
        // Fill in nested layer data
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
 
        // Fill the first layer data
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

	// Click the node to automatically check this node
	function onClick(e, treeId, treeNode) {
		var keys = this._z.data.getSettings()[treeId].data.key;
		$nameInput.val(treeNode[keys.name]);
		$idInput.val(treeNode[keys.id]);
		$idInput.add($nameInput).change(); // Add the change event, so this input box can be valid validated or otherwise validated
		onBodyDown(null, false);
	}
	
	var ztreeToool = $.fn.zTree.init($ztree, setting);
	
	$("body").bind("mousedown", onBodyDown);
	
	function onBodyDown(event, show){
		// Not according to event, force to open and close
		if (show != null && typeof show == "boolean") {
			if (show) {
				$ztree.parent().slideDown("fast");
			} else {
				$ztree.parent().fadeOut("fast");
			}
			return;
		}
		// According to event, turn on and off
		if (event.target.id == $nameInput.attr("id") 
				|| ($(event.target).hasClass("arrow") && $(event.target).siblings("#" + $nameInput.attr("id")).size() > 0)) {
			// If you click on the input box and the corresponding arrow button, automatically switch the open and close state
			if ($ztree.parent().is(":hidden")) {
				$ztree.parent().slideDown("fast");
			} else {
				$ztree.parent().fadeOut("fast");
			}
		} else if (event.target.id != $ztree.parent().attr("id") 
				&& $(event.target).parents("#" + $ztree.parent().attr("id")).size() == 0) {
			// If you click anywhere except the display box, close the drop down box
			if (!$ztree.parent().is(":hidden"))
				$ztree.parent().fadeOut("fast");
		}
	}
	
};
