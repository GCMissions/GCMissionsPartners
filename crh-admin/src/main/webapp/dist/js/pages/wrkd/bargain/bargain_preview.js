$(function() {
	var bargainDetail = {
		init:function(){
			var _self = this;
			var actId = $("#bargainId").val();
			_self.getData(actId);
		},
		getData:function(id){
			var _self = this;
			$.ajax({
				type:'POST',
				url:urlPrefix+"kdBargain/preview",
				dataType:'json',
				data:{"actId":id},
				success:function(result){
					var data = result.data;
					if(result.code==='ACK'){
						var groupImgHtml = template($('#groupImgTemp').html(), {data:data});
						$('#groupImg').append(groupImgHtml);				
						$('#groupName').html(data.name);
						$('#groupNum').html(data.remainingAmount);//商品剩余数量
						$('#salePrice').html(data.productPrice?('￥'+data.productPrice):'');//原价
						$('#productDesc').html('<img src="'+data.explainInfoPic+'" />');//活动说明
						$('#productDetail').html(HTMLDecode(data.detailDesc));//商品详情
						$('#desc').html(data.productDesc);//商品描述
						if(data.actStatus==='2'){
							$('#joinBtn').addClass('disabled');
						}
						//渲染轮播广告
						setTimeout(function(){
							var mySwiper = new Swiper ('.swiper-container', {
								loop : true,
						    	autoplay: 2000,
								// 如果需要分页器
						    	pagination : '.swiper-pagination',
						    	autoplayDisableOnInteraction : false
							});
						},1000);
						$('.mainBody').css('display','block');
					}else{
						alert(result.message);
					}
					
				}
			});
		}	
	}
	bargainDetail.init();
});