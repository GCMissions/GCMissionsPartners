$(function() {
	var charityDetail = {
		init:function(){
			var _self = this;
			var actId = $("#charityId").val();
			_self.getData(actId);
		},
		getData:function(id){
			var _self = this;
			$.ajax({
				type:'POST',
				url:urlPrefix+"coolbag/charity/getCharityDetail",
				dataType:'json',
				data: JSON.stringify({actId : id}),
	            contentType: 'application/json; charset=utf-8',
				success:function(result){
					if(result.code === 'ACK' && result.data){
						//活动详情
						var actDetailData = result.data;
						var actDetail = document.getElementById('actDetail').innerHTML;
						var actDetailHtml = template(actDetail, {obj:actDetailData});
						$('.container').html(actDetailHtml);
						$('#actDesc').html($('#actDesc').text());
						$('#proDesc').html($('#proDesc').text());
						$('#feedBackDesc').html($('#feedBackDesc').text());
						//渲染轮播广告
						var mySwiper = new Swiper ('.swiper-container', {
							loop : true,
						    autoplay: 3000,
							// 如果需要分页器
						    pagination : '.swiper-pagination',
						    preventClicks : false
						});
						if(actDetailData.status === '3'){
							$('body').css('padding-bottom','7rem');
							$('.service').show();
							$('.adver-div').hide();
						}else if(actDetailData.status === '4'){
							$('.service').hide();
							$('.adver-div').show();
							$('body').css('padding-bottom','2rem');
						}
						_self.featureId = actDetailData.featureId;
						_self.featureName = actDetailData.featureName;
						$('.service').find('a').attr('href','../wekd/productDetail.html?id=' + _self.featureId + '&tag=' + _self.featureName+'');
						
					};
					
				}
			});
		}	
	}
	charityDetail.init();
});