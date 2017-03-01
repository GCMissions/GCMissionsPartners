// 
var productApp = {
	cateId : 0,	
    maxPics : 20,
    currentSeq : 0,
    $mainForm : void 0,
    isCoverText : "[封面图片]",
    notCoverText : "[设为封面]",
    $picList : $('.goodspic-list'),
    coverElement : ".goodsCover",
    removeBtn : ".remove-btn",
    newPostUrl : "activity/add",
    editPostUrl : "activity/update",
    defaultImg   : uiBase + "img/default_goods_image_240.gif",
    listUrl    : "activity/",
    init : function(productId, imagesList) {
    	this.productId = productId; 
        this.productImage = productImage;
    	this.$navTabs = $('ul.nav-tabs');
        this.$mainForm = $('#mainForm');
        this.$goodspicList = $('.goodspic-list');
        this.defaultImageData = {showImageUrl : this.defaultImg, imageUrl : "", isCover : 0, CoverText : this.notCoverText, imageId: ""}; 
        this.initEvents();
        $("#firstCategory").trigger('change');
        this.renderLists(imagesList);   
        
        //init sort
        $(".goodspic-list ul").sortable({connectWith:".connectList"}).disableSelection();
        return this;
        
    },
    getSecondCate : function(e) {
    	var $target = $(e.target),
    		firstCate = $target.val();
    	$.ajax({
    		type         : 'get',
			url          : urlPrefix + "activity/getSubCategoryByParent/" + firstCate,
			dataType     : 'json',
			contentType  : 'application/json',
			success : function(msg){
				var data = msg.data;
				var _opt = "";
				if(data && data.length != 0){
					for(var i=0; i<data.length; i++){
						_opt += "<option value='"+data[i].cateId+"'>"+data[i].cateName+"</option>";
					}
				}else{
					_opt = "<option value=''>无二级分类</option>";
				}
				$("#secondCategory").html(_opt);
				$("#secondCategory").selectpicker('val', '');
				$("#secondCategory").selectpicker('refresh');
			}
    	});
    },
    renderLists : function(images) {
        var that = this, 
            isCover, 
            defaultData = {};
    	
        this.initImages = images;
        
        _(images).each(function(image, ik) {
            isCover = 0;
            
            var defaultData = $.extend({id: that.currentSeq}, that.defaultImageData);
            if(that.productImage && that.productImage == image.imageUrl) {
            	image.isCover = 1;
            	image.CoverText = that.isCoverText;
            }
            if(image.imageUrl) {
            	image.showImageUrl = image.imageUrl;
            }
            
            var $newPic = template('picItemTpl', $.extend(defaultData, image));
            
            
            that.$picList.find('ul').append($newPic);
            that.currentSeq ++ ;
        });
       
    },
    initEvents : function() {
        var that = this;
        this.validator = $('#mainForm').validate({
        	rules : {
        		originalPrice : {
        			price : true,
        			max   : 99999
         		}
        	}
        });
        this.initInputMask();
        $('#saveBaseInfo').on("click", _(this.submitForm).bind(this));
        $('#saveDetailInfo').on("click", _(this.submitDetail).bind(this));
        $('.addNewPic').on('click', _(this.addNewPic).bind(this));
        // 商品图片ajax上传
        
        this.$mainForm.on('change', '.upload-btn input[type="file"]', function(){
            var id = $(this).attr('id');
            that.ajaxFileUpload(id);
        });
        //
        this.$mainForm.on('click', this.removeBtn, _(this.removePic).bind(this));
        
        //获得二级品类
        $("#firstCategory").on('change',_(this.getSecondCate).bind(this));
        jQuery.validator.addMethod("category", function(value, element) { 
			return this.optional(element) || that.checkCategory(value); 
		},"商品品类必填且有子品类时子品类必填");
    
    },
    //商品品类：如果有二级则选二级，没有二级就选一级
    checkCategory : function($secondCateId) {
    	var f_cate = $("#firstCategory"),
    	opt_sel = f_cate.find("option:selected"),
    	hasSon = opt_sel.attr("data-hasSon"),
    	fCateId = f_cate.val();
    	if (!fCateId) {
    		return false;
    	}
    	if($secondCateId){
    		return true;
    	} else {
    		return !hasSon;
    	} 
    	
    },
    initInputMask : function() {
    	$('#price').inputmask("mask", {
    		alias : "decimal",
    		rightAlignNumerics : false
    		
    	});
    },
    submitForm : function() {
    	if(!this.$mainForm.validate().form()) {
    		this.$navTabs.find('li:eq(0) a').tab('show') ;
    		this.$navTabs.find('li:eq(0)').addClass('has-error')
    	} else {
    		this.$navTabs.find('li:eq(0)').removeClass('has-error');
            var tempData = $('#mainForm').frmSerialize();
            
            this.doSave();
    	}
    },
    submitDetail : function() {
    	var that = this;
    	 var formData = $('#mainForm1').frmSerialize();
    	 var description= _.isUndefined(formData['description']) ? "" : formData['description'] ;
    	 //如果没有商品，则返回基本信息界面
    	 if(!that.productId || that.productId == 0){
    		 $("#detail-li").removeClass("active");
			 $("#base-a").click();
			 $("#base-li").addClass("active");
			 that.$mainForm.validate().form();
			 return;
    	 }
		 var result = {
    		productId : that.productId,
    		description : description
    	 };
		 $('#saveDetailInfo').prop('disabled', true).text('保存中....');
    	 $.ajax(
            { 
        		type         : 'post',
				url          : urlPrefix + "activity/saveDetail",
				dataType     : 'json',
				contentType  : 'application/json',
				data         : JSON.stringify(result)
		 })
		 .done(function(result) {
			if(result.code == "ACK") {
				$('#saveDetailInfo').prop('disabled', true).text('保存成功');
				$("#mainForm1").loadingInfo({
    				type : "success", 
    				text: message("admin.message.success"),
    				callBack : function() {
    				}
    			});
			} 
		 }).always(function() {
			 $('#saveDetailInfo').prop('disabled', false).text('保存');
		 });
    },
    doSave : function() {
    	 var result = this.getPostData(),
    	     url    = this.newPostUrl,
    	 	 that   = this;
    	 if (this.productId) {
     		 url = this.editPostUrl;
             result.productId = this.productId;
             result.cateId = $("#cateId").val();
     	 }
    	 $('#saveBaseInfo').prop('disabled', true).text('保存中....');
    	 $.ajax(
            { 
        		type         : 'post',
				url          : urlPrefix+url,
				dataType     : 'json',
				contentType  : 'application/json',
				data         : JSON.stringify(result)
		 })
		 .done(function(result) {
			if(result.code == "ACK") {
				that.productId = result.data.productId;
				$('#saveBaseInfo').prop('disabled', true).text('保存成功');
				$("#tab_1 input[name='vipFlag']").val($("#tab_1 input[name='isVip']:checked").val());
				that.$mainForm .loadingInfo({
    				type : "success", 
    				text: message("admin.message.success"),
    				callBack : function() {
    				}
    			});
			} 
		 }).always(function() {
			 $('#saveBaseInfo').prop('disabled', false).text('保存');
		 });
    },
    
    addNewPic : function() {
    	if(this.$goodspicList.find('li').length >= this.maxPics) {
    		this.$mainForm.loadingInfo("error", "最多只能添加 "+ this.maxPics + " 张图片");
    		return false;
    	}
    	
        var $newPic = template('picItemTpl', $.extend({id: this.currentSeq}, this.defaultImageData));
        this.$picList.find('ul').append($newPic);
        this.currentSeq ++ ;
    },
    
    // 图片上传ajax
    ajaxFileUpload:   function (id, o) {
        var that = this;
        $('#img_' + id + '').attr('src', uiBase + "/img/loading.gif");
    
        $.ajaxFileUpload({
            url : $.GLOBAL.config.ossUploadUrl.template({source: uploadSourcesMap.product}), 
            secureuri : false,
            fileElementId : id,
            dataType : 'json',
            global : false,
            data : {},
            success : function (data, status) {
                if (data.code == "ACK") {
                    $('input[name="picUrl['+id+']"]').val(data.data.url);
                    $('input[name="picKey['+id+']"]').val(data.data.key);
                    $('#img_{{id}}'.template({id: id})).attr('src', data.data.url);
                } else {
                	$('#img_' + id + '').attr('src', that.defaultImg);
                	$(window).loadingInfo('error', data.message);
                }
            }
        });
        return false;
    },
   
    removePic : function (e) {
    	var $target = $(e.target),
    		$goodUpload = $target.closest('.goodspic-upload'),
    		picName = $goodUpload.find('input[name="pic.name"]').val(),
    		imgKey=$goodUpload.find('input[name^="picKey"]').val();
           
        $goodUpload.remove();
    },
    
    getPostData : function() {
       
        var formData = $('#mainForm').frmSerialize();
        var categoryId = "";
        if (formData['secondCategory']) {
        	categoryId = formData['secondCategory'];
        } else {
        	categoryId = formData['firstCategory'];
        }
        var result = {
            originalPrice : formData['originalPrice'],
            productName: formData['productName'],
            cateId: categoryId,
            orgId: formData['orgId'],
            rebackNote : formData['rebackNote'],	
            note   : formData['note'],	
            isCaptcha : formData['isCaptcha'],
        	vip : formData['isVip'],
        };
        
        // file
        var picData = [], sort = 0;  
        var picIds  = $('#mainForm').find('input[name^=picId]');
        
        // ignore the item with empty picUrl
        $(picIds).each(function(key, value ) {
            var seq = $(value).attr('name').replace("picId[",'').replace("]", '');
            var url =  $('#mainForm').find('input[name="picUrl['+seq+']"]').val();
            var key =  $('#mainForm').find('input[name="picKey['+seq+']"]').val();
            var picId =  $('#mainForm').find('input[name="picId['+seq+']"]').val();
            if(!_.isEmpty(url)) {
                picData.push({
                    imageUrl : url,
                    imageKey : key,
                    sort     : sort++,
                    imageId  : picId,
                    title	 : "吾儿网"
                });
                if($('#mainForm').find('input[name="goodsCover['+seq+']"]').val()==1) {
                    result.image = url;
                }
            }
           
        });
        result.listImages = picData;
        return result;
    }
};
