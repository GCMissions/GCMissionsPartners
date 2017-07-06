$(function() {
    var tabBanner = function() {
        var $sideMenu = $('ul.sidebar-menu'),
            $menuItems = $sideMenu.find("ul.treeview-menu a.J_menuItem"),
            $contentWrapper = $('.content-wrapper');    
        
        this.$sideMenu = $sideMenu;
        this.$menuItems = $menuItems;
        this.$contentWrapper = $contentWrapper;
        
        this.$menuItems.each(function(k) {
            if (!$(this).attr("data-index")) {
                $(this).attr("data-index", k)
            }
        }); 
        
        this.$menuItems.on("click", _(this.menuItemClickHandler).bind(this));

        
        $(".J_menuTabs").on("click", ".J_menuTab i", _(this.closeTab).bind(this));

        
        $(".J_tabCloseOther").on("click", _(this.closeOthers).bind(this));

       
        $(".J_tabShowActive").on("click", _(this.showCurrent).bind(this));
        $(".J_tabRefreshActive").on("click", _(this.refreshCurrent).bind(this));

       
        $(".J_menuTabs").on("click", ".J_menuTab", _(this.changeTab).bind(this));

        
        $(".J_menuTabs").on("dblclick", ".J_menuTab", this.dbClick);
        $(".J_tabLeft").on("click", _(this.clickLeft).bind(this));
        $(".J_tabRight").on("click", _(this.clickRight).bind(this));
        $(".J_tabCloseAll").on("click", function() {
            $(".page-tabs-content").children("[data-id]").not(":first").each(function() {
                $('.J_iframe[data-id="' + $(this).data("id") + '"]').remove();
                $(this).remove()
            });
            $(".page-tabs-content").children("[data-id]:first").each(function() {
                $('.J_iframe[data-id="' + $(this).data("id") + '"]').show();
                $(this).addClass("active")
            });
            $(".page-tabs-content").css("margin-left", "0")
        });
    };
    tabBanner.prototype.closeTab = function (e) {
    	var that = this;
    	var $target = $(e.target);
    	
        var parentItem = $target.parents(".J_menuTab");
        var m = parentItem.data("id"),
            l = parentItem.width();
        if (parentItem.hasClass("active")) {
            if (parentItem.next(".J_menuTab").size()) {
                var k = parentItem.next(".J_menuTab:eq(0)").data("id");
                parentItem.next(".J_menuTab:eq(0)").addClass("active");
                $(".J_mainContent .J_iframe").each(function() {
                    if ($(this).data("id") == k) {
                        $(this).show().siblings(".J_iframe").hide();
                        return false
                    }
                });
                var n = parseInt($(".page-tabs-content").css("margin-left"));
                if (n < 0) {
                    $(".page-tabs-content").animate({
                        marginLeft: (n + l) + "px"
                    }, "fast")
                }
                parentItem.remove();
                $(".J_mainContent .J_iframe").each(function() {
                    if ($(this).data("id") == m) {
                        $(this).remove();
                        return false
                    }
                })
            }
            if (parentItem.prev(".J_menuTab").size()) {
                var k = parentItem.prev(".J_menuTab:last").data("id");
                parentItem.prev(".J_menuTab:last").addClass("active");
                $(".J_mainContent .J_iframe").each(function() {
                    if ($(this).data("id") == k) {
                        $(this).show().siblings(".J_iframe").hide();
                        return false
                    }
                });
                parentItem.remove();
                $(".J_mainContent .J_iframe").each(function() {
                    if ($(this).data("id") == m) {
                        $(this).remove();
                        return false;
                    }
                })
            }
        } else {
            parentItem.remove();
            $(".J_mainContent .J_iframe").each(function() {
                if ($(this).data("id") == m) {
                    $(this).remove();
                    return false
                }
            });
            this.jump($(".J_menuTab.active"))
        }
        return false
    };
    tabBanner.prototype.closeOthers = function() {
        $(".page-tabs-content").children("[data-id]").not(":first").not(".active").each(function() {
            $('.J_iframe[data-id="' + $(this).data("id") + '"]').remove();
            $(this).remove()
        });
        $(".page-tabs-content").css("margin-left", "0")
    };
    tabBanner.prototype.changeTab = function (e) {
    	var that = this;
    	var $target = $(e.target);
        if (!$target.hasClass("active")) {
            var k = $target.data("id");
            $(".J_mainContent .J_iframe").each(function() {
                if ($(this).data("id") == k) {
                	$(this).show().siblings(".J_iframe").hide();
                    return false
                }
            });
            $target.addClass("active").siblings(".J_menuTab").removeClass("active");
            that.jump($target);
        }
    };
    tabBanner.prototype.dbClick = function() {
        var l = $('.J_iframe[data-id="' + $(this).data("id") + '"]');
        var k = l.attr("src")
    };
    tabBanner.prototype.showCurrent = function() {
        this.jump($(".J_menuTab.active"))
    };
    tabBanner.prototype.refreshCurrent = function() {
    	 $(".J_mainContent .J_iframe").each(function() {
             if ($(this).is(":visible") ) {
                 $(this).attr('src',  $(this).attr('src'));
                 return false;
             }
         });
    };
    
    tabBanner.prototype.totalWidth = function(l) {
        var k = 0;
        $(l).each(function() {
            k += $(this).outerWidth(true)
        });
        return k
    };
    tabBanner.prototype.jump = function(n) {
        var o = this.totalWidth($(n).prevAll()),
            q = this.totalWidth($(n).nextAll());
        var l = this.totalWidth($(".content-tabs").children().not(".J_menuTabs"));
        var k = $(".content-tabs").outerWidth(true) - l;
        var p = 0;
        if ($(".page-tabs-content").outerWidth() < k) {
            p = 0
        } else {
            if (q <= (k - $(n).outerWidth(true) - $(n).next().outerWidth(true))) {
                if ((k - $(n).next().outerWidth(true)) > q) {
                    p = o;
                    var m = n;
                    while ((p - $(m).outerWidth()) > ($(".page-tabs-content").outerWidth() - k)) {
                        p -= $(m).prev().outerWidth();
                        m = $(m).prev()
                    }
                }
            } else {
                if (o > (k - $(n).outerWidth(true) - $(n).prev().outerWidth(true))) {
                    p = o - $(n).prev().outerWidth(true)
                }
            }
        }
        $(".page-tabs-content").animate({
            marginLeft: 0 - p + "px"
        }, "fast")
    };
    tabBanner.prototype.clickLeft = function() {
        var o = Math.abs(parseInt($(".page-tabs-content").css("margin-left")));
        //@TODO error in  next line
        var l = this.totalWidth($(".content-tabs").children().not(".J_menuTabs"));
        var k = $(".content-tabs").outerWidth(true) - l;
        var p = 0;
        if ($(".page-tabs-content").width() < k) {
            return false
        } else {
            var m = $(".J_menuTab:first");
            var n = 0;
            while ((n + $(m).outerWidth(true)) <= o) {
                n += $(m).outerWidth(true);
                m = $(m).next()
            }
            n = 0;
            if (this.totalWidth($(m).prevAll()) > k) {
                while ((n + $(m).outerWidth(true)) < (k) && m.length > 0) {
                    n += $(m).outerWidth(true);
                    m = $(m).prev()
                }
                p = this.totalWidth($(m).prevAll())
            }
        }
        $(".page-tabs-content").animate({
            marginLeft: 0 - p + "px"
        }, "fast")
    };
    tabBanner.prototype.clickRight = function (e) {
    	//e.preventDefault();
    	var $target = $(e.target);
        var o = Math.abs(parseInt($(".page-tabs-content").css("margin-left")));
        var l = this.totalWidth($(".content-tabs").children().not(".J_menuTabs"));
        var k = $(".content-tabs").outerWidth(true) - l;
        var p = 0;
        if ($(".page-tabs-content").width() < k) {
            return false
        } else {
            var m = $(".J_menuTab:first");
            var n = 0;
            while ((n + $(m).outerWidth(true)) <= o) {
                n += $(m).outerWidth(true);
                m = $(m).next()
            }
            n = 0;
            while ((n + $(m).outerWidth(true)) < (k) && m.length > 0) {
                n += $(m).outerWidth(true);
                m = $(m).next()
            }
            p = this.totalWidth($(m).prevAll());
            if (p > 0) {
                $(".page-tabs-content").animate({
                    marginLeft: 0 - p + "px"
                }, "fast")
            }
        }
    };
    tabBanner.prototype.menuItemClickHandler = function (e) {
        e.preventDefault();
        var $target = $(e.target);
        var href = $target.attr("href") || $target.data("href"),
            m    = $target.data("index"),
            text = $target.data("text") || $target.text(),
            l    = $.trim(text),
            that = this,
            k    = true;
        if (href == undefined || $.trim(href).length == 0) {
            return false
        }
        $(".J_menuTab").each(function() {
            if ($(this).data("id") == href) {
                if (!$(this).hasClass("active")) {
                    $(this).addClass("active").siblings(".J_menuTab").removeClass("active");
                    that.jump($target);
                    $(".J_mainContent .J_iframe").each(function() {
                        if ($(this).data("id") == href) {
                            $(this).show().siblings(".J_iframe").hide();
							$(this).attr('src', $(this).attr('src'));
                            return false
                        }
                    })
                } else {
                	$(".J_mainContent .J_iframe").each(function() {
                        if ($(this).data("id") == href) {
							$(this).attr('src', $(this).attr('src'));
                            return false
                        }
                    })
                }
                k = false;
                return false
            }
        });
        if (k) {
            var p = '<a href="javascript:;" class="active J_menuTab" data-id="' + href + '">' + l + ' <i class="fa fa-times-circle"></i></a>';
            $(".J_menuTab").removeClass("active");
            var n = $('<iframe class="J_iframe" name="iframe' + m + '" width="100%" height="100%"  frameborder="0" data-id="' + href + '"></iframe>');
            n.attr('src', $.GLOBAL.config.urlPrefix + href);
            $(".J_mainContent").find("iframe.J_iframe").hide().parents(".J_mainContent").append(n);
           
            $(".J_menuTabs .page-tabs-content").append(p);
            this.jump($(".J_menuTab.active"))
        }
        
        var screenSizes = $.HTSHOP.options.screenSizes;
        if ($(window).width() <= (screenSizes.sm - 1)) {
        	$("body").removeClass('sidebar-open').addClass('sidebar-collapse').trigger('collapsed.pushMenu');
        }
        return false
    };
    tabBanner.prototype.messageClickHandler = function (element,e) {
        var $target =element;
        var href = $target.attr("href") || $target.data("href"),
            text = "mail ",
            m = '1000',
            l    = "mail",
            that = this,
            k    = true;
        if (href == undefined || $.trim(href).length == 0) {
            return false
        }
        $(".J_menuTab").each(function() {
            if ($(this).data("id") == href) {
                if (!$(this).hasClass("active")) {
                    $(this).addClass("active").siblings(".J_menuTab").removeClass("active");
                    that.jump($target);
                    $(".J_mainContent .J_iframe").each(function() {
                        if ($(this).data("id") == href) {
                            $(this).show().siblings(".J_iframe").hide();
							$(this).attr('src', $(this).attr('src'));
                            return false
                        }
                    })
                } else {
                	$(".J_mainContent .J_iframe").each(function() {
                        if ($(this).data("id") == href) {
							$(this).attr('src', $(this).attr('src'));
                            return false
                        }
                    })
                }
                k = false;
                return false
            }
        });
        if (k) {
            var p = '<a href="javascript:;" class="active J_menuTab" data-id="' + href + '">' + l + ' <i class="fa fa-times-circle"></i></a>';
            $(".J_menuTab").removeClass("active");
            var n = $('<iframe class="J_iframe" name="iframe' + m + '" width="100%" height="100%"  frameborder="0" data-id="' + href + '"></iframe>');
            n.attr('src', $.GLOBAL.config.urlPrefix + href);
            $(".J_mainContent").find("iframe.J_iframe").hide().parents(".J_mainContent").append(n);
           
            $(".J_menuTabs .page-tabs-content").append(p);
            this.jump($(".J_menuTab.active"))
        }
        return false
    }
  
    window.topManager.tabBarObject = new tabBanner();
    
});
