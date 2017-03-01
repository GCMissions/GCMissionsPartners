$(function() {
	var groupDetail = {
		init:function(){
			var _self = this;
			var id = $("#teamBuyId").val();
			_self.getData(id);
			$('.tab-child').click(function(){
				if($(this).attr('href')==='productDetail'){
					$('#productDetail').addClass('active');
					$('#groupDesc').removeClass('active');
				}else{
					$('#groupDesc').addClass('active');
					$('#productDetail').removeClass('active');
				}
			});
		},
		getData:function(id){
			var _self = this;
			$.ajax({
				type:'POST',
				url:urlPrefix+"groupPurchase/preview",
				dataType:'json',
				data:{"id":id},
				success:function(result){
					var data = result.data;
					if(result.code==='ACK'){
						
						var groupImgTemp = document.getElementById('groupImgTemp').innerHTML;
						var groupImgHtml = template(groupImgTemp, {data:data});
						$('#groupImg').html(groupImgHtml);
						$("#groupName").html(data.teamName);
						$("#groupNum").html(data.surplusCount);//团购商品剩余数量
						$(".product-price").html(data.formatProductPrice?(data.formatProductPrice):'');//原价
						$(".group-price").html(data.formatTeamPrice?(data.formatTeamPrice):'');//团购价格
						$("#startCount").html(data.startCount);//成团数量 
						$("#productDetail").html(HTMLDecode(data.description));//商品详情
						$('#groupDesc').html('<img src="'+data.image+'" alt="活动说明"/>');//活动说明
						$("#specialDesc").html(data.specialDesc);//特别说明
						
						var endflag = data.status;//活动是否已结束,4已结束
						if(endflag==='4'){
							$('.mainBody').css('margin-bottom','0px').append('<div class="groupEnd-box"><p>活动已结束</p><p>共团购出<span id="joinedNum">'+data.saleCount+'</span>件商品</p></div>');
						}else if(endflag==='3'){
							var groupListHtml = template($('#groupListTemp').html(), {list:data.recordList});
							$('.group-list-box').append(groupListHtml);
							/*if(data.recordList.length){
								//倒计时
								$this.effectiveEndDate = data.effectiveEndDate;
								setInterval($this.getRTime,0); 
							}*/
							var footerHtml = template($('#footerTemp').html(), {data:data});
							$('#footerHtml').append(footerHtml);
						}
						
						$('.mainBody').css('display','block');
					}
					
				}
			});
		}	
	}
	groupDetail.init();
});