$(function(){
 var appMessAdd = {
	currentSeq: 0,
	isCoverText : "",
    notCoverText : "",
    $mainForm : $("#messageForm"),
    mainImgErrorTxt  : "请选择一张图片！",
    editFormErrorTxt : "请输入消息内容",
	defaultImg   : uiBase + "img/default_goods_image_240.gif",
	init : function(){
		this.$mainForm = $('#mainForm');
		this.$mainImgError = this.$mainForm.find('.mainImgError'); //主图没选择时错误提示
		this.$editFormError = this.$mainForm.find('.editFormError');//编辑器内容为空时提示
		this.$adsList = $('.adspic-list');
		this.bindEvents();
		this.defaultImageData = {showImageUrl : this.defaultImg, imageUrl : "", isCover : 0, CoverText : this.notCoverText}; 
	},
	bindEvents :function(){
		var that=this;
		$('.addNewPic').on('click', _(this.addNewPic).bind(this));
		this.$adsList.on('click','.remove-btn' , function(e){
			$(e.target).closest('.adspic-upload').remove();
		});
		$("#messageForm").on('change', '.upload-btn input[type="file"]', function(){
            var id = $(this).attr('id');
            that.ajaxFileUpload(id);
        });
		this.$mainForm.on("click", 'button.submitMainForm', _(this.submitForm).bind(this));
	},
	submitForm : function() {
		if(!$('#mainForm').validate().form() || !this.validateEditor()) {
			return false;   		
    	} else {
            this.doSave();
    	}
    },
    validateEditor : function(){
    	var $form = $('#mainForm');
		var	data = this.getFormJson($form);
    	this.$mainImgError.empty();
		if(!data.image) {
			this.$mainImgError.show().text(this.mainImgErrorTxt);
			return false;
		}else if(!data.content){
			this.$editFormError.show().text(this.editFormErrorTxt);
			return false;
		}
		return true;
    },
    doSave : function(){
    	var that = this,
    		$form = $('#mainForm');
		var	data = this.getFormJson($form);
		$.ajax({ 
    		type         : 'post',
			url          : 'add',
			dataType     : 'json',
			contentType  : 'application/json',
			data         : JSON.stringify(data)
		 })
		 .done(function(result) {
			if(result.code == "ACK") {
				that.$mainForm.loadingInfo({
    				type : "success", 
    				text: message("admin.message.success"),
    				callBack : function() {
    					window.location.href=urlPrefix + "appMessage/";
    				}
    			});
			}
		 });			
    },
	addNewPic : function() {
		
		var maxPics = 1;
		if($(".adspic-list").find('li').length >= maxPics) {
			$("#messageForm").loadingInfo("error", "最多只能添加 "+ maxPics + " 张图片");
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
			url : $.GLOBAL.config.uploadUrl.template({source: uploadSourcesMap.appmessageImg}),
			secureuri : false,
			fileElementId : id,
			dataType : 'json',
			global : false,
			data : {},
			success : function (data, status) {
				if (data.code == "ACK") {
					$('input[name="picUrl['+id+']"]').val(data.data);
					$('#img_{{id}}'.template({id: id})).attr('src', fileUrlPrefix+data.data);   
				}
			}
			
		});
		return false;  
	},
	getFormJson : function($form) {
		var formData = $form.frmSerialize();
		var data = {
			title:	formData.appTitle,
			content:	formData.message,
		}
		//multifile
        var picData = [];  
        var picIds  = $('#mainForm').find('input[name^=picId]');
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
 };
 appMessAdd.init();
});