$(function(){
	var advertiseAdd = {
		currentSeq: 0,
		validator : void 0,
		$removeBtn : $(".remove-btn"),
		$mainForm:$("#mainForm"),
		editUrl :"advertisement/edit",
		isCoverText : "",
	    notCoverText : "",
		defaultImg   : uiBase + "img/default_goods_image_240.gif",
		imgSize :{
			0 : "(建议尺寸 370px*180px)",
			1 : "(建议尺寸 990px*460px)",
			2 : "(建议尺寸 300px*170px)",
			3 : "(建议尺寸 300px*720px)",
			4 : "(建议尺寸 300px*720px)",
		},
		init : function(){						
			this.$adsList = $('.adspic-list');
			this.initEvents();			
			this.defaultImageData = {showImageUrl : this.defaultImg, imageUrl : "", isCover : 0, CoverText : this.notCoverText}; 
			this.reviewPic();
//			this.$adsList.find("ul").sortable({connectWith:".connectList"}).disableSelection();
		},

		initEvents :function(){
			var that = this;
//			var date = new Date();
//			var year = date.getFullYear();
//			var month = date.getMonth()+1;
//			var day = date.getDate();
//			//开始日期
//			$('#usDate,#ueDate').datetimepicker({
//				minView: 'month',
//				format: 'yyyy-mm-dd hh:ii:ss',
//				language: 'ch',
//				//endDate: year+'-'+month+'-'+day,
//				autoclose : true,
//				todayBtn : true
//			});
//			//使用日期
//			$('#usDate').on('changeDate',function(){
//				$('#ueDate').datetimepicker('setStartDate', $('#usDate').val());
//				if($('#usDate').val()=="" && $("#usDate").next().css('display') == 'inline-block'){
//					$("#usDate").next().css('display','none');
//				}else{
//					$("#usDate").next().css('display','inline-block');
//				}
//			});
//			//结束日期
//			$('#ueDate').on('changeDate',function(){
//				if ($('#ueDate').val()) {
//					$('#usDate').datetimepicker('setEndDate', $('#ueDate').val());
//				}else{
//					$('#usDate').datetimepicker('setEndDate', year+'-'+month+'-'+day);
//				};
//				if($('#ueDate').val()=="" && $("#ueDate").next().css('display') == 'inline-block'){
//					$("#ueDate").next().css('display','none');
//				}else{
//					$("#ueDate").next().css('display','inline-block');
//				}
//			});
			//选择广告位
//			$("#adLocal").on('change',  function(e) {
//				//广告位最大图片数
//				var adnum=$(e.target).val().split(";")[0];
//				var imgLen=that.$adsList.find(".adspic-upload").length;
//				if(adnum<imgLen){
//					for(i=0;i<imgLen-adnum;i++){
//						that.$adsList.find(".adspic-upload").last().remove();
//					}
//				}
//				
//			});
			
			$("#type").on('change',  function(e) {
				var urlFlag=$(e.target).val();
				if(urlFlag==0){
					$("#url").attr('placeholder',"输入商品条码");
				}else if(urlFlag==1){
					$("#url").attr('placeholder',"http://");
				}
				
			});
			//添加删除图片
			$('.addNewPic').on('click', _(this.addNewPic).bind(this));
			this.$adsList.on('click','.remove-btn' , function(e){
				$(e.target).closest('.adspic-upload').remove();
			});
			// 商品图片ajax上传
			$('.upload-btn').on('click',function(){
				var id = $(this).attr('id');
				that.ajaxFileUpload(id);
			});
			
			$(".adsForm").on('change', '.upload-btn input[type="file"]', function(){
	            var id = $(this).attr('id');
	            that.ajaxFileUpload(id);
	        });
			//返回
			$('#return').on('click',function(){
		        window.location.href= urlPrefix + "advertisement/";
			});
			//保存
			$('#advertAdd').on('click',function(){
		        that.save();
		       
			});
			
		},
		reviewPic : function(){
			var that=this;
			var adLocal = $("#adLocal").val();
			$(".title").append(that.imgSize[adLocal]);
			var image={};
			var url=$("#imgUrl").val();			
			var defaultData = $.extend({id: that.currentSeq}, that.defaultImageData);
			if(url){
				image.ImageUrl = url;
				image.showImageUrl = fileUrlPrefix + url;
				var $newPic = template('picItemTpl', $.extend(defaultData, image));
				$(".adspic-list").find('ul').append($newPic);
			}
			var type = $("#type").val();
			if(type==0){
				$("#url").attr('placeholder',"输入商品条码");
			}else if(type==1){
				$("#url").attr('placeholder',"http://");
			}
		},
		addNewPic : function() {
			var maxPics = 1;
			if($(".adspic-list").find('li').length >= maxPics) {
				$(".adsForm").loadingInfo("error", "最多只能添加 "+ maxPics + " 张图片");
				return false;
			}	
		    var $newPic = template('picItemTpl', $.extend({id: this.currentSeq}, this.defaultImageData));
		    $(".adspic-list").find('ul').append($newPic);
		    this.currentSeq ++ ;
		},
		ajaxFileUpload : function  (id, o) {
			var that = this;
			$('#img_' + id + '').attr('src', uiBase + "/img/loading.gif");	
			$.ajaxFileUpload({
				url : $.GLOBAL.config.uploadUrl.template({source: uploadSourcesMap.ad}),
				secureuri : false,
				fileElementId : id,
				dataType : 'json',
				global : false,
				data : {},
				success : function (data, status) {
					if (data.code == "ACK") {
						$('input[name="picUrl['+id+']"]').val(data.data);
						$('#img_{{id}}'.template({id: id})).attr('src', fileUrlPrefix+data.data);   
					}else {
						$(window).loadingInfo("error", data.message);
					}
				}
				
			});
			return false;  
		},
		getFormJson : function($form) {
			var tempData = $form.frmSerialize();
			var adPosition = tempData.adLocal.split(";")[1];
			data = {	
					local :tempData.adLocal,
					title : tempData.adName,
					sort : tempData.sort,
//					image:	tempData.picUrl[0],
					url  :tempData.url,
					urlFlag:tempData.type,
			};
			//multifile
	        var picData = [];  
	        var picIds  = $('#mainForm').find('input[name^=picId]');
	  //      var sort = 0;
	        //@TODO ignore the item with empty picUrl
	        
	        $(picIds).each(function(key, value ) {
	            var seq = $(value).attr('name').replace("picId[",'').replace("]", '');
	            var url =  $('#mainForm').find('input[name="picUrl['+seq+']"]').val();
	            if(!_.isEmpty(url)) {
	                picData.push({
	                    imageUrl : url,
	  //                  sort : sort++
	                });
	            }
	           
	        });
	        if(picData[0]){ data.image = picData[0].imageUrl;}
			return data;
		},
		save : function(){
			var that = this,
				$form = $(".adsForm"),
				url=this.editUrl,
				data = this.getFormJson($form),
				adId = $('#adId').val();
			if(adId) data.adId = adId;
			$.ajax({ 
	        		type         : 'post',
					url          : urlPrefix+url,
					dataType     : 'json',
					contentType  : 'application/json',
					data         : JSON.stringify(data)
				 })
				 .done(function(result) {
					if(result.code == "ACK") {
						that.$mainForm .loadingInfo({
		    				type : "success", 
		    				text: message("admin.message.success"),
		    				callBack : function() {
		    					window.location.href=urlPrefix + "advertisement/";;
		    				}
		    			});
					}
				 });
		},
	};
 
	advertiseAdd.init();	
});