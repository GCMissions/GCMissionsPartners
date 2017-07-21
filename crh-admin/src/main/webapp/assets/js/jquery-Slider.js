(function($) {
	$.fn.Scroll = function() {
		var _btnUp = $("#btnUp"); // up button 
		var _btnDown = $("#btnDown"); //down button
		var _this = this.eq(0).find("ul:first");
		var lineH = 70; // get line height
		var line = 1; // The number of rows to scroll each time
		var speed = 500; // Scrolling speed, the greater the value, the slower the speed (milliseconds)
		var m = line; // Variables used for calculat
		var count = _this.find(".parentMenu").length; // The total number of <li> elements
		var upHeight = line * lineH;
		var c = 0;
//		setParentMenuTop(0);
		function scrollUp() {
			if (!_this.is(":animated")) { // Determines whether the element is in animation, and if not animated, append the animation.
				if (m < count-5) { // Determine whether the M is smaller than the number of screens (7 per screen)
					m += line;
					_this.animate({
						marginTop : "-=" + upHeight + "px"
					}, speed);
					c = c+1;
					$(".upHeight").val("");  //Record the scroll height in the page
					$(".upHeight").val(upHeight);  //Record the scroll height in the page
//					setParentMenuTop(c);
				}else{
					_btnUp.removeClass("hover");
				}
			}
		}
//		function setParentMenuTop(c){
//			for(var i=1;i<=count;i++){
//				if(i-c==7){
//					$(".secondFLoat"+i).css("bottom","0px");
//					$(".secondFLoat"+i).css("top","auto");
//				}else{
//					var height =  $(".secondFLoat"+i).height();
//					var num = 520-(i-c-1)*lineH;
//					if(height>num){
//						var top = (i-c-1)*lineH-(height-num);
//					}else{
//						var top = (i-c-1)*lineH+22;
//					}
//					$(".secondFLoat"+i).css("top",top+"px");
//				}
//			}
//		}
		function scrollDown() {
			if (!_this.is(":animated")) {
				if (m > line) { // Determine whether the M is larger than the number of screens
					m -= line;
					_this.animate({
						marginTop : "+=" + upHeight + "px"
					}, speed);
					c = c-1;
//					setParentMenuTop(c);
				}else{
					_btnDown.removeClass("hover");
				}
			}
		}
		function init(){
			
		}
		_btnUp.mouseover(function(){
			if (!_this.is(":animated")) { // Determine whether the M is larger than the number of screens
				if (m < count-6) {
					_btnUp.addClass("hover");
				}
			}
		});
		_btnUp.mouseout(function(){
			_btnUp.removeClass("hover");
		});
		_btnDown.mouseover(function(){
			if (!_this.is(":animated")) {
				if (m > line) {
					_btnDown.addClass("hover");
				}
			}
		});
		_btnDown.mouseout(function(){
			_btnDown.removeClass("hover");
		});
		_btnUp.bind("click", scrollUp);
		_btnDown.bind("click", scrollDown);
	};
})(jQuery);
$(function(){
})