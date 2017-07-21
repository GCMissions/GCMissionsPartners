//Settings drop-down menu
function setdownArrow() {
    //$(".downArrow").offset().left = $(".tabs li:last").offset().left;
    var lilfet = $(".tabs li:last").offset().left + 1;
    var liwidth = $(".tabs li:last").width();
    $(".downArrow").css("left", (lilfet + liwidth) + "px");
}
function ShowMsg(msg) {
    $(".errorInfo").text(msg);
    //$(".errorInfo").fadeIn(100);
    //$(".errorInfo").fadeOut(6000);
    $(".errorInfo").show().delay(3000).fadeOut(200);
}
function addTab(title, url) {
    if ($('#tabs').tabs('exists', title)) {
        $('#tabs').tabs('select', title);//Check and refresh
        var currTab = $('#tabs').tabs('getSelected');
        var url = $(currTab.panel('options').content).attr('src');
        if (url != undefined && currTab.panel('options').title != 'homepage') {

            $("#divLoading").show();
            $('#tabs').tabs('update', {
                tab: currTab,
                options: {
                    content: createFrame(url)
                }
            })
        }
    } else {
        var content = createFrame(url);
        $('#tabs').tabs('add', {
            title: title,
            content: content,
            closable: true
        });
    }
    tabClose();
    if (title != 'homepage') {
        setdownArrow();
    }
}
//Set select tab
function SelectTable(title, url, topTitle) {
    if ($('#tabs').tabs('exists', title)) {
        var currTab = $('#tabs').tabs('select', title);
    } else {
        addTab1(title, url, $("div[topname='" + topTitle + "']").attr("topvalue"));
    }
}



function addTab1(title, url, argId) {
    var varClosable = true;
    if (title == "homepage" || title == "Entry date data") {
        varClosable = false;
    }
    $("#divLoading").show();
    $(".parentMenu").removeClass("menuS");
    $("#parent" + argId).addClass("menuS");
    $("#hidCurTopMenuId").val(argId);

    if ($('#tabs').tabs('exists', title)) {
        $('#tabs').tabs('select', title);//Check and refresh
        var currTab = $('#tabs').tabs('getSelected');
        var urlOld = $(currTab.panel('options').content).attr('src');
        if (urlOld != undefined && currTab.panel('options').title != 'homepage') {

            $("#divLoading").show();
            $('#tabs').tabs('update', {
                tab: currTab,
                options: {
                    content: createFrame(url)
                }
            });
        }
    } else {
        var content = createFrame(url);
        $('#tabs').tabs('add', {
            title: title,
            content: content,
            closable: varClosable,
            id: argId
        });
    }
    tabClose();
    if (title != 'homepage') {
        setdownArrow();
    }
}
function childAddTab(title, url, topTitle) {
    addTab1(title, url, $("div[topname='" + topTitle + "']").attr("topvalue"));
}
function createFrame(url) {
    var s = '<iframe scrolling="auto" frameborder="0"  src="' + url + '" style="width:100%;height:100%;"></iframe>';
    return s;
}

function CloseTab() {
    parent.$.messager.confirm('System hint', 'Tip: are you sure you want to close the current page?', function (r) {
        if (r) {
            var currTab = $('#tabs').tabs('getSelected');
            $('#tabs').tabs('close', currTab.panel('options').title);
            setdownArrow();
        }
    });
}

function CloseTabByTitle(title) {
    $('#tabs').tabs('close', title);
    setdownArrow();
}
function childAddTabAndCloseTab(title, url, topTitle, closeTitle) {
    $('#tabs').tabs('close', closeTitle);
    addTab1(title, url, $("div[topname='" + topTitle + "']").attr("topvalue"));
}
function tabCloseFun(obj) {
    var subtitle = $(obj).parent().children(".tabs-inner").children(".tabs-title").text();
    return;
}
function tabClose() {
    /*Click the TAB tab*/
    $(".tabs-inner").click(function () {
        var subtitle = $(this).children(".tabs-closable").text();
        $(".parentMenu").removeClass("menuS");
        if (subtitle != null && subtitle != '') {
            var tab = $('#tabs').tabs('getTab', subtitle);
            $("#parent" + tab.panel('options').id).addClass("menuS");
            $("#hidCurTopMenuId").val(tab.panel('options').id);
        }
    });
    /*close the TAB tab*/
    $(".tabs-close").click(function () {
        var subtitle = $(this).parent().find(".tabs-closable").text();
        $('#tabs').tabs('close', subtitle);
        setdownArrow();
        return false;
    });
    /*Double Click the TAB tab*/
    $(".tabs-inner").dblclick(function () {
        var subtitle = $(this).children(".tabs-closable").text();
        $('#tabs').tabs('close', subtitle);
        setdownArrow();
    });
}

function  updateCurrentTab(title,url){
	  var currTab = $('#tabs').tabs('getSelected');
	  var content = '<iframe scrolling="auto" frameborder="0"  src="' + url + '" style="width:100%;height:100%;"></iframe>';
	  $('#tabs').tabs('update', {
          tab: currTab,
          options: {
        	  title:title,
              content: content
          }
      });
}

function UpdateTabFirst() {
    var firstTab = $('#tabs').tabs('getTab', 'homepage');
    var url = $(firstTab.panel('options').content).attr('src');
    var content = '<iframe scrolling="auto" frameborder="0"  src="' + url + '" style="width:100%;height:100%;"></iframe>';
    if (url != undefined) {
        $('#tabs').tabs('update', {
            tab: firstTab,
            options: {
                content: content
            }
        });

        var ieset = navigator.userAgent;
        if (ieset.indexOf("MSIE 6.0") > -1 || ieset.indexOf("Chrome") > -1) {
            var currTab1 = $('#tabs').tabs('getTab', 'homepage');
            setTimeout(function () { refreshTab(currTab1) }, 0);
        }
    }
}
function refreshTab(refresh_tab) {
    if (refresh_tab && refresh_tab.find('iframe').length > 0) {
        var _refresh_ifram = refresh_tab.find('iframe')[0];
        var refresh_url = _refresh_ifram.src;
        _refresh_ifram.contentWindow.location.href = refresh_url;
    }
}
//Bind menu events
function tabCloseEven() { 
    //refresh
    $('#mm-tabupdate').click(function () {
        var currTab = $('#tabs').tabs('getSelected');
        var url = $(currTab.panel('options').content).attr('src');
        if (url != undefined) {

            $("#divLoading").show();
            $('#tabs').tabs('update', {
                tab: currTab,
                options: {
                    content: createFrame(url)
                }
            });
        }
    });
    //close the current 
    $('#mm-tabclose').click(function () {
        var currtab_title = $('#mm').data("currtab");
        $('#tabs').tabs('close', currtab_title);
        setdownArrow();
    });
    //cloase all 
    $('#mm-tabcloseall').click(function () {
        $('.tabs-inner span').each(function (i, n) {
            var t = $(n).text();
            if (t != 'homepage') {
                $('#tabs').tabs('close', t);
            }
        });
        setdownArrow();
    });
    //close all except the current
    $('#mm-tabcloseother').click(function () {
        var prevall = $('.tabs-selected').prevAll();
        var nextall = $('.tabs-selected').nextAll();
        if (prevall.length > 0) {
            prevall.each(function (i, n) {
                var t = $('a:eq(0) span', $(n)).text();
                if (t != 'homepage') {
                    $('#tabs').tabs('close', t);
                }
            });
        }
        if (nextall.length > 0) {
            nextall.each(function (i, n) {
                var t = $('a:eq(0) span', $(n)).text();
                if (t != 'homepage') {
                    $('#tabs').tabs('close', t);
                }
            });
        }
        setdownArrow();
        return false;
    });
    //close all the right TAB
    $('#mm-tabcloseright').click(function () {
        var nextall = $('.tabs-selected').nextAll();
        if (nextall.length == 0) {
            //msgShow('system hint ',"There's no more left behind ",'error');
            alert("There's no more left behind");
            return false;
        }
        nextall.each(function (i, n) {
            var t = $('a:eq(0) span', $(n)).text();
            $('#tabs').tabs('close', t);
        });
        return false;
    });
    //close all the left TAB
    $('#mm-tabcloseleft').click(function () {
        var prevall = $('.tabs-selected').prevAll();
        if (prevall.length == 0) {
            alert("There's no more");
            return false;
        }
        prevall.each(function (i, n) {
            var t = $('a:eq(0) span', $(n)).text();
            $('#tabs').tabs('close', t);
        });
        return false;
    });

    //logout
    $("#mm-exit").click(function () {
        $('#mm').menu('hide');
    });
}
//Load timeout information
function LoadByPwd() {
    $('#win').dialog({
        title: 'Login has timed out. Please re-enter your password:',
        width: 300,
        height: 150,
        closed: false,
        cache: false,
        closable: false,
        href: '/admin/login.html',
        modal: true
    });
}
$(function () {
	eval("");
    document.onkeydown = function (e) {
        var ev = document.all ? window.event : e;
        if (ev.keyCode == 13) {
            if ($(document.activeElement).parent().parent().parent().find('.button').first()) {
                $(document.activeElement).parent().parent().parent().find('.button').first().click();
            } else if (typeof ($("#tabs .panel:visible iframe")[0]) != 'undefined' && $("#tabs .panel:visible iframe")[0].contentWindow.$('.blueButton').first().html() != null) {
                $("#tabs .panel:visible iframe")[0].contentWindow.$('.blueButton').first().click();
            }
        }
    }
    tabCloseEven();
    $(".parentMenu").mouseenter(function () {
        OpenSecondMenu($(this).attr("id").replace("parent", ""));
    });
    $(".parentMenu").mouseleave(function () {
        TopMenuMouseOut();
    });
    $(".memberInfo").mouseenter(function () {
        $(".MImore").fadeIn(250);
    });
    $(".memberInfo").mouseleave(function () {
        $(".MImore").fadeOut(250);
    });

    //ConstraintActive();
    //open homepage
    childAddTab('homepage', pathPrefix+'index', '');
    //Close default tab
    $('.tabs-inner span').each(function (i, n) {
        var t = $(n).text();
        if (t != 'homepage') {
            $('#tabs').tabs('close', t);
        }
    });
});

function OpenSecondMenu(arg) {
	$(".parentMenu").removeClass("menuS");
	$("#parent" + arg).addClass("menuS");

	//child menu show
	var left_height = $(".leftMenu").height();  //Get the total height of the left menu
	$(".parentMenu").mouseover(function(){
		var secondHeight = $(this).find(".secondFloat").height();  //Get the total height of the current menu
		var leftmenu_index = $(this).index();                    //Gets the index of the current <Li>, which is the number
		var top = leftmenu_index*70+22;                               //Get the top distance
		var upHeight = $(".upHeight").val();                         //Scroll up to a height
		$(this).find(".secondFloat").show();
		$(this).find(".secondFloat").css({"top":top+"px"});
		
		//When the scroll height is reached, the attributes are first obtained and the relevant parameters are obtained by intercepting the string
		var rollHeight= parseInt( $('.roll_ul').css('marginTop') );  //Get UL upward scrolling height
		var middel_height =  (left_height-top)/2;              //Get half height on the left menu
		//Determine the height of the top of the distance greater than the half of the menu on the left, and whether or not to scroll
		if(top > middel_height && upHeight==0){
			var bottom_height = parseInt(left_height-top-70);
			$(this).find(".secondFloat").css({"bottom":bottom_height+"px","top":"auto"});
		}
		if(top > middel_height && upHeight!=0){
			var up_bottom_height = parseInt(left_height-top-70);
			$(this).find(".secondFloat").css({"bottom":up_bottom_height+"px","top":"auto"});
		}
		
		//Determines whether scrolling and calculates scroll values, if scroll, rollHeight is not 0
		if(rollHeight!=0){
			var roll_bottom_height = parseInt(left_height-top-70)-rollHeight;
			if(top < middel_height && upHeight!=0){
				var roll_top = parseInt(top)+rollHeight;
				
				$(this).find(".secondFloat").css({"top":roll_top+"px"});
			}
			if(top > middel_height && upHeight!=0){
				
				$(this).find(".secondFloat").css({"bottom":roll_bottom_height+"px","top":"auto"});
			}
		}
		
		//Determine if the submenu needs to be widened to 0
		
		if(parseInt(secondHeight+top) > parseInt(left_height)){
			$(this).find("ul").find("li").find("ul").css("width","480px");
		}
		
		
		
		
	})
}



function OpenWindow(obj) {
	
    var sobState = "1";
    var $this = $(obj);
    var href = pathPrefix + $this.attr('src');
    var title = $this.text();
    addTab1(title, href, $this.attr('index'));
    //$('.secondFloat').hide();
}
//Turn off the secondary menu when the mouse moves out of the primary menu
function TopMenuMouseOut() {
    $(".parentMenu").removeClass("menuS");
    $("#parent" + $("#hidCurTopMenuId").val()).addClass("menuS");
    //$('.secondFloat').hide();
}

//change Password
function userPwd() {
    $('#win').dialog({
        title: 'change Password',
        width: 300,
        height: 220,
        closed: false,
        cache: false,
        closable: true,
        href: '/User/PwdEdit',
        modal: true
    });
}
//drop out
function signOut() {
    $.messager.confirm('system hint', "Are you sure you want to drop out of the system?", function (r) {
        if (r) {
            $("#hidout").val("1");
            window.location.href = '/admin/login.html';
        }
    });
}
