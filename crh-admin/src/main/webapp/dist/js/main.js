/*! main.js
 */
//Make sure jQuery has been loaded before main.js
if (typeof jQuery === "undefined") {
  throw new Error("jQuery is required");
}

$.HTSHOP = $.HTSHOP || {}; 
$.HTSHOP.options = {
  //Add slimscroll to navbar menus
  //This requires you to load the slimscroll plugin
  //in every page before app.js
  navbarMenuSlimscroll: true,
  navbarMenuSlimscrollWidth: "3px", //The width of the scroll bar
  navbarMenuHeight: "200px", //The height of the inner menu
  //General animation speed for JS animated elements such as box collapse/expand and
  //sidebar treeview slide up/down. This options accepts an integer as milliseconds,
  //'fast', 'normal', or 'slow'
  animationSpeed: 500,
  //Sidebar push menu toggle button selector
  sidebarToggleSelector: "[data-toggle='offcanvas']",
  //Activate sidebar push menu
  sidebarPushMenu: true,
  //Activate sidebar slimscroll if the fixed layout is set (requires SlimScroll Plugin)
  sidebarSlimScroll: true,
  //Enable sidebar expand on hover effect for sidebar mini
  //This option is forced to true if both the fixed layout and sidebar mini
  //are used together
  sidebarExpandOnHover: false,
  //BoxRefresh Plugin
  enableBoxRefresh: true,
  //Bootstrap.js tooltip
  enableBSToppltip: true,
  BSTooltipSelector: "[data-toggle='tooltip']",
  
 
  screenSizes: {
    xs: 480,
    sm: 768,
    md: 992,
    lg: 1200
  }
 
};
$(function () {
    "use strict";
    //Easy access to options
    var o = $.HTSHOP.options;
     
    //Set up the object
    _init(o);

    //Activate the layout maker
    $.HTSHOP.layout.activate();

    //Enable sidebar tree view controls
    $.HTSHOP.tree('.sidebar');
   

    //Add slimscroll to navbar dropdown
    //used for dropdown-menu in the page-top
    if (typeof $.fn.slimscroll != 'undefined') {
       
        /*
        $("ul.sidebar-menu").slimScroll({
			height: "100%",
			railOpacity: .9,
			alwaysVisible: !1
		});
		*/
		
    }
    $.HTSHOP.pushMenu.activate(o.sidebarToggleSelector);
    if (o.enableBSToppltip) {
        $('body').tooltip({
            selector: o.BSTooltipSelector
        });
    }
  
    //Activate fast click
    //@TODO should both exists in pages.js
    if (typeof FastClick != 'undefined') {
        FastClick.attach(document.body);
    }
    /*
    * INITIALIZE BUTTON TOGGLE
    * ------------------------
    */
    $('.btn-group[data-toggle="btn-toggle"]').each(function () {
        var group = $(this);
        $(this).find(".btn").on('click', function (e) {
            group.find(".btn.active").removeClass("active");
            $(this).addClass("active");
            e.preventDefault();
        });
    });
    
     
});  

function _init(options) {
  'use strict';
  /* Layout
   * ======
   * Fixes the layout height in case min-height fails.
   *
   * @type Object
   * @usage $.HTSHOP.layout.activate()
   *        $.HTSHOP.layout.fix()
   *        $.HTSHOP.layout.fixSidebar()
   */
  $.HTSHOP.layout = {
    activate: function () {
      var _this = this;
      //_this.fix();
      //_this.fixSidebar();
      /*
       * @TODO
       * better switch to ul.sidebar-menu
       * slimScroll 会设置 overflow:hidden 导致:hover时显示子菜单功能  失效 
       * */
      $("section.sidebar").slimScroll({
			height: "100%",
			railOpacity: .9,
			alwaysVisible: !1
		});
      $(window, ".wrapper").resize(function () {
        // _this.fix();
        //_this.fixSidebar();
      });
    },
    fix: function () {
      //Get window height and the wrapper height
      var neg = $('.main-header').outerHeight() + $('.main-footer').outerHeight();
      var window_height = $(window).height();
      var sidebar_height = $(".sidebar").height();
      //Set the min-height of the content and sidebar based on the
      //the height of the document.
      if ($("body").hasClass("fixed")) {
        $(".content-wrapper, .right-side").css('min-height', window_height - $('.main-footer').outerHeight());
      } else {
        var postSetWidth;
        if (window_height >= sidebar_height) {
          $(".content-wrapper, .right-side").css('min-height', window_height - neg);
          postSetWidth = window_height - neg;
        } else {
          $(".content-wrapper, .right-side").css('min-height', sidebar_height);
          postSetWidth = sidebar_height;
        }

        

      }
    },
    fixSidebar: function () {
      //Make sure the body tag has the .fixed class
      if (!$("body").hasClass("fixed")) {
        if (typeof $.fn.slimScroll != 'undefined') {
          $("section.sidebar").slimScroll({destroy: true}).height("auto");
        }
        return;
      } else if (typeof $.fn.slimScroll == 'undefined' && window.console) {
        window.console.error("Error: the fixed layout requires the slimscroll plugin!");
      }
      /*
       *  $("ul.sidebar-menu").slimScroll({
			height: "100%",
			railOpacity: .9,
			alwaysVisible: !1
		});
       * */
      //Enable slimscroll for fixed layout
      if ($.HTSHOP.options.sidebarSlimScroll) {
        if (typeof $.fn.slimScroll != 'undefined') {
          //Destroy if it exists
          $("section.sidebar").slimScroll({destroy: true}).height("auto");
          //Add slimscroll
          $("section.sidebar").slimscroll({
            height: ($(window).height() - $(".main-header").height()) + "px",
            color: "rgba(0,0,0,0.2)",
            size: "3px"
          });
        }
      }
    }
  };

  /* PushMenu()
   * ==========
   * Adds the push menu functionality to the sidebar.
   *
   * @type Function
   * @usage: $.HTSHOP.pushMenu("[data-toggle='offcanvas']")
   */
  $.HTSHOP.pushMenu = {
    activate: function (toggleBtn) {
      //Get the screen sizes
      var screenSizes = $.HTSHOP.options.screenSizes;

      //Enable sidebar toggle
      $(toggleBtn).on('click', function (e) {
        e.preventDefault();

        //Enable sidebar push menu
        if ($(window).width() > (screenSizes.sm - 1)) {
          if ($("body").hasClass('sidebar-collapse')) {
            $("body").removeClass('sidebar-collapse').addClass('sidebar-open').trigger('expanded.pushMenu');
          } else {
            $("body").addClass('sidebar-collapse').removeClass('sidebar-open').trigger('collapsed.pushMenu');
          }
        }
        //Handle sidebar push menu for small screens
        else {
          if ($("body").hasClass('sidebar-open')) {
            $("body").removeClass('sidebar-open').addClass('sidebar-collapse').trigger('collapsed.pushMenu');
          } else {
            $("body").addClass('sidebar-open').removeClass('sidebar-collapse').trigger('expanded.pushMenu');
          }
        }
      });
      
      $(window).on("load resize", function() {
      	//$(this).width() < 769 && ($("body").addClass("sidebar-collapse"), $(".main-sidebar").fadeIn());
      	if($(this).width() <= screenSizes.sm) { 
      		$("body").addClass('sidebar-collapse').trigger('collapsed.pushMenu');
      	}
      	//
      });
      
      $(".content-wrapper").click(function () {
        //Enable hide menu when clicking on the content-wrapper on small screens
        if ($(window).width() <= (screenSizes.sm - 1) && $("body").hasClass("sidebar-open")) {
          $("body").removeClass('sidebar-open');
        }
      });

      //Enable expand on hover for sidebar mini
      if ($.HTSHOP.options.sidebarExpandOnHover
              || ($('body').hasClass('fixed')
                      && $('body').hasClass('sidebar-mini'))) {
        this.expandOnHover();
      }
    },
    expandOnHover: function () {
      var _this = this;
      var screenWidth = $.HTSHOP.options.screenSizes.sm - 1;
      //Expand sidebar on hover
      $('.main-sidebar').hover(function () {
        if ($('body').hasClass('sidebar-mini')
                && $("body").hasClass('sidebar-collapse')
                && $(window).width() > screenWidth) {
          _this.expand();
        }
      }, function () {
        if ($('body').hasClass('sidebar-mini')
                && $('body').hasClass('sidebar-expanded-on-hover')
                && $(window).width() > screenWidth) {
          _this.collapse();
        }
      });
    },
    expand: function () {
      $("body").removeClass('sidebar-collapse').addClass('sidebar-expanded-on-hover');
    },
    collapse: function () {
      if ($('body').hasClass('sidebar-expanded-on-hover')) {
        $('body').removeClass('sidebar-expanded-on-hover').addClass('sidebar-collapse');
      }
    }
  };

  /* Tree()
   * ======
   * Converts the sidebar into a multilevel
   * tree view menu.
   *
   * @type Function
   * @Usage: $.HTSHOP.tree('.sidebar')
   */
  $.HTSHOP.tree = function (menu) {
    var _this = this;
    var animationSpeed = $.HTSHOP.options.animationSpeed;
    $(document).on('click', menu + ' li.treeview a', function (e) {
      //Get the clicked link and the next element
      var $this = $(this);
      var checkElement = $this.next();

      //Check if the next element is a menu and is visible
      if ((checkElement.is('.treeview-menu')) && (checkElement.is(':visible'))) {
        //Close the menu
        checkElement.slideUp(animationSpeed, function () {
          checkElement.removeClass('menu-open');
          //Fix the layout in case the sidebar stretches over the height of the window
          //_this.layout.fix();
        });
        checkElement.parent("li").removeClass("active");
      }
      //If the menu is not visible
      else if ((checkElement.is('.treeview-menu')) && (!checkElement.is(':visible'))) {
        //Get the parent menu
        var parent = $this.parents('ul').first();
        //Close all open menus within the parent
        //var ul = parent.find('ul:visible').slideUp(animationSpeed);
        //Remove the menu-open class from the parent
        //ul.removeClass('menu-open');
        //Get the parent li
        var parent_li = $this.parent("li");
        $this.closest('ul.sidebar-menu').find('li.treeview').removeClass("active").each(function() {
            $(this).find('.treeview-menu').removeClass('menu-open').slideUp();
        });
        //close all opened item
        
        //Open the target menu and add the menu-open class
        checkElement.slideDown(animationSpeed, function () {
          //Add the class active to the parent li
          checkElement.addClass('menu-open');
          //parent.find('li.active').removeClass('active');
          parent_li.addClass('active');
          //Fix the layout in case the sidebar stretches over the height of the window
          //_this.layout.fix();
        });
        
      }
      //if this isn't a link, prevent the page from being redirected
      if (checkElement.is('.treeview-menu')) {
        e.preventDefault();
      }
    });
	
	$(document).on('click', menu + ' li.treeview>a', function (e) {
		if($('body').is(".sidebar-collapse")) {
			$('body').removeClass("sidebar-collapse").addClass("sidebar-open");
		}
	});
  };

  

 
    var _mainPage = function(options) {
        this.options = options;
        
        this.loadIndexPage();
        this.loadNotice();
        window.topManager = this; //@TODO 
    }
   
    _mainPage.prototype.loadIndexPage  = function() {
		$('#content-main').append(
			'<iframe class="J_iframe" name="iframe0" width="100%" height="100%"'+ 
			' src="'+$.GLOBAL.config.indexUrl+'" frameborder="0" data-id="'+$.GLOBAL.config.indexUrl+'" seamless></iframe>'
		);
	}
    _mainPage.prototype.loadNotice = function() {
    	//console.log("loadNotice", arguments);  
    	
    }; 
    _mainPage.prototype.openPage = function() {}; 
    $.HTSHOP.mainPage = _mainPage;
    
    
    new $.HTSHOP.mainPage(options);
    
}
/* ------------------
 * - Custom Plugins -
 * ------------------
 * All custom plugins are defined below.
 */

/*
 * BOX REFRESH BUTTON
 * ------------------
 * This is a custom plugin to use with the component BOX. It allows you to add
 * a refresh button to the box. It converts the box's state to a loading state.
 *
 * @type plugin
 * @usage $("#box-widget").boxRefresh( options );
 */
(function ($) {

  "use strict";

  $.fn.boxRefresh = function (options) {

    // Render options
    var settings = $.extend({
      //Refresh button selector
      trigger: ".refresh-btn",
      //File source to be loaded (e.g: ajax/src.php)
      source: "",
      //Callbacks
      onLoadStart: function (box) {
        return box;
      }, //Right after the button has been clicked
      onLoadDone: function (box) {
        return box;
      } //When the source has been loaded

    }, options);

    //The overlay
    var overlay = $('<div class="overlay"><div class="fa fa-refresh fa-spin"></div></div>');

    return this.each(function () {
      //if a source is specified
      if (settings.source === "") {
        if (window.console) {
          window.console.log("Please specify a source first - boxRefresh()");
        }
        return;
      }
      //the box
      var box = $(this);
      //the button
      var rBtn = box.find(settings.trigger).first();

      //On trigger click
      rBtn.on('click', function (e) {
        e.preventDefault();
        //Add loading overlay
        start(box);

        //Perform ajax call
        box.find(".box-body").load(settings.source, function () {
          done(box);
        });
      });
    });

    function start(box) {
      //Add overlay and loading img
      box.append(overlay);

      settings.onLoadStart.call(box);
    }

    function done(box) {
      //Remove overlay and loading img
      box.find(overlay).remove();

      settings.onLoadDone.call(box);
    }

  };

})(jQuery);

/*
 * EXPLICIT BOX ACTIVATION
 * -----------------------
 * This is a custom plugin to use with the component BOX. It allows you to activate
 * a box inserted in the DOM after the app.js was loaded.
 *
 * @type plugin
 * @usage $("#box-widget").activateBox();
 */
(function ($) {

  'use strict';

  $.fn.activateBox = function () {
    $.HTSHOP.boxWidget.activate(this);
  };

})(jQuery);

(function ($) {
//var getMessageNum = function(){
//	$.ajax({
//		url:'message/getUnredMessageNum',
//		type:'get',
//		dataType:'json',
//		success:function(msg){
//			if(msg.code=="ACK"){
//				$("#message_total").html(msg.data);
//			}
//		}
//	});
//};
//getMessageNum();
//setInterval(getMessageNum,1000*30);
$('.message').on('click',function(e){
	e.preventDefault();
	e.stopPropagation();
	if(!_.isUndefined(top.topManager)) {
        top.topManager.tabBarObject.messageClickHandler($(this),e);
    }else{
    	window.topManager.tabBarObject.messageClickHandler($(this),e);
    }
    
});
})(jQuery);
//修改资料
$('body').on("click", "a.changeProfile", function(e) {
	e.preventDefault();
	var $target = $(e.target),
		loginId = $target.data('loginid'); //id
	
	var dialog =  BootstrapDialog.show({
		title: '修改资料',
		message: $($.GLOBAL.config.dialogLoading), 
		draggable: true,
		buttons: [{
			label: '保存',
			cssClass: 'btn-primary',
			action: function(dialog, e) {
				save(dialog, $(e.target));
			}
		}, {
			label: '取消',
			action: function(dialog) {
				dialog.close();
			}
		}],
		
	});
	dialog.getModalDialog().css('width', '500px');
	
	//添加自定义的验证
	$.validator.addMethod("isphone", function(value,element) {
		  var length = value.length;
		  var mobile = /^1[0-9]{10}$/;
		  return this.optional(element) || (length == 11 && mobile.test(value));
	}, "请正确填写电话号码");
	
	var doneFunction = _.partial(function(dialog, response){
		if(response.code == "ACK") {
			//set dialog message
			var message = template('changeProfileTpl',response.data);
			dialog.$modalBody.find('.bootstrap-dialog-message').html(message);
			dialog.$modalBody.find('input[name=password]').val('');
		}
	}, dialog);
	
	$.ajax({
		type: "POST",
		url : $.GLOBAL.config.editUserUrl.template({id : loginId}),
		dataType: "json",
		contentType: "application/json"
	})
	.done(doneFunction);
	var save  = function(dialog, $btn) {
		var $form = $('#addEditForm'),
   		    
            userId = loginId,
            
            data = getJson($form);
		var flag = $form.validate().form();
        if(flag) {
        	if(_.isUndefined(data.userName) && _.isUndefined(data.password) ) {
        		dialog.close();
        		return false;
        	}
        	$btn.saving();
            $.ajax({
			  type: 'POST',
			  url: $.GLOBAL.config.editUserPostUrl,
			  dataType: 'json',
			  contentType: 'application/json',
			  data:  JSON.stringify(data)
            })
        	.done(function(result) {
    	 		if(result.code == "ACK"){
    	 			$form.loadingInfo({
    	 				type : "success",
    	 				text: message("admin.message.success"),
    	 				callBack : function() {
    	 					dialog.close();
    	 					window.location.reload();
    	 				}
    	 			});
    	 		}
        	 })
        	 .fail(function(result) {
        		  
        	 }).
        	 always(function() {
        		 $btn.saved();
        		 
        	 });
        } else {
        	
        }
    };
    var getJson =  function($form) {
		$.fn.serializeObject = function(){
		    var o = {};
		    var a = this.serializeArray();
		    $.each(a, function() {
		        if (o[this.name] !== undefined) {
		            if (!o[this.name].push) {
		                o[this.name] = [o[this.name]];
		            }
		            o[this.name].push($.trim(this.value) || '');
		        } else {
		            o[this.name] =$.trim(this.value) || '';
		        }
		    });
		    return o;
		};
		var data = $form.serializeObject();
		if(data.password!='') {
			data.password =  $.md5(data.password);
		} else {
			delete data.password;
		}
		
		return data;
	};
	
});

//商家二维码
var myqrcode = {
	qrconfig : $.GLOBAL.config.qrcodes,
	init : function() {
		this.bindEvents();
		return this;
	},
	bindEvents : function() {
		$("ul.user-actions").find('.showQRCode').on('click', _(this.showDialog).bind(this));	
	},
	showDialog : function(e) {
		e.preventDefault();
		var that 	 = this,
			$target  = $(e.target),
			userInfo = {
				loginId  : $target.data('loginid'),
				orgId    : $target.data('orgid'),
				userName : $target.data('username'),
			};
			
		this.qrText= this.qrconfig.qrText.template(userInfo);
		this.defaultConfig = this.qrconfig.defaultConfig;
		
		this.dialog =  BootstrapDialog.show({
			title: '查看我的二维码',
			message: $(template('myQrcodeTpl', $.extend(true, {
				qrText  : this.qrText,
				qrcodes : this.qrconfig.otherSizes
			}, this.defaultConfig  ))), 
			draggable: true,
			buttons: [{
				label : '关闭',
				action: function(dialog) {
					dialog.close();
				}
			}],
			onshown : function() {
				that.initPageEvents();
			    
			}
		});
		this.$messageBody = this.dialog.$modalBody;
		
		
	},
	initPageEvents : function() {
		var that 	 = this;
		this.$messageBody.find('button.dlNormal').on('click', function() {
			var oCanvas = $('#defaultQrcode').find('canvas').get(0);
			that.saveCanvas(oCanvas,  that.defaultConfig.width, that.defaultConfig.height);
		});
		
		this.$messageBody.find('.moreSizeQrcode').on('click', _(function() {
			this.$messageBody.find('.otherSizeList').toggleClass('hide');
		}).bind(this));
		this.$messageBody.find('.otherSizeList').on('click', _(function(e) {
			var $target  = $(e.target);
			that.buildOtherSize($target, $target.data('width'), $target.data('height') );
		}).bind(this));
		
		this.loadDefaultQrcode();
	},
	loadDefaultQrcode : function() {
		this.buildQrcode($('#defaultQrcode'),  this.defaultConfig.width, this.defaultConfig.height);
	},
	buildOtherSize : function($target, width, height) {
		//生成
		$target.prop("disabled", true);
		$('#otherQrcode').qrcode({ 
			
			width: width, //宽度 
			height:height, //高度 
			text: this.qrText
		}); 
		
		//下载
		var pCanvas = $('#otherQrcode').find('canvas').get(0);
		this.saveCanvas(pCanvas, width, height);
		$target.prop("disabled", false);
		
	},
	buildQrcode: function($item, width, height) {
		$item.qrcode({ 
			
			width: width, //宽度 
			height:height, //高度 
			text: this.qrText
		}); 
	},
	saveCanvas: function(pCanvas,  width, height) {
		var bRes     = false,
			strType  = "PNG",
		    fileName =  $.GLOBAL.config.systemName + "二维码_"+width;
		if (strType == "PNG")
			bRes = Canvas2Image.saveAsPNGWithFileName(pCanvas, fileName+".png", width, height);
		if (strType == "BMP")
			//bRes = Canvas2Image.saveAsBMP(pCanvas, false, width, height);
		if (strType == "JPEG")
			//bRes = Canvas2Image.saveAsJPEG(pCanvas, false, width, height);

		if (!bRes) {
			alert("Sorry, this browser is not capable of saving " + strType + " files!");
			return false;
		}
	}
};
$(function () {
	if($('#myQrcodeTpl').length>0) {
		window.qrcodeInstance = myqrcode.init();
	}
});
//logout
$('body').on('click', "a.logout", function(e) {
	e.preventDefault();
	$.ajax(
    { 
		type         : 'POST',
		url          : $.GLOBAL.config.logoutUrl,
		dataType     : 'json',
		contentType  : 'application/json'
	})
	.done(function(result) {
		if(result.code == "ACK") {
			location.href = $.GLOBAL.config.mainUrl;
		}
	})
	.fail(function(result) {
		
		
	});
});

//footer 
$('.main-footer').html(template('mainFooterTpl', {version : $.GLOBAL.config.version, currentYear : (new Date).getFullYear()}));