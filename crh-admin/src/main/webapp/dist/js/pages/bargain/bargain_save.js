// 
var bargainApp = {
    maxPics : 1,
    currentSeq : 0,
    $mainForm : void 0,
	$navTabs : void 0,
	dialog : void 0,
    validator : void 0,
    isCoverText : "[封面图片]",
    notCoverText : "[设为封面]",
    $picList : $('.goodspic-list'),
    setCoverElement : "a.btnSetCover",
    coverElement : ".goodsCover",
    removeBtn : ".remove-btn",
    newPostUrl : "bargain/add",
    editPostUrl : "bargain/update",
    defaultImg   : uiBase + "img/default_goods_image_240.gif",
    selectedGoods : {},
    listUrl    : "bargain/",
    previewBargainHtml :'<div><div><img src="'+uiBase + 'img/preview_wechat.jpg" style="width:564px;"></div><div class="detail-container"><div id="des" class="des"></div><div style="height:15px;"></div></div></div>',
    init : function(id, imagesList) {
    	this.id = id; 
    	this.$navTabs = $('ul.nav-tabs');
        this.$mainForm = $('#mainForm');
        this.$goodspicList = $('.goodspic-list');
        this.defaultImageData = {showImageUrl : this.defaultImg, imageUrl : "", isCover : 0, CoverText : this.notCoverText, imageId: ""}; 
        this.initEvents();
        this.renderLists(imagesList);   
        //init sort
        $(".goodspic-list ul").sortable({connectWith:".connectList"}).disableSelection();
        return this;
        
    },
    renderLists : function(images) {
        var that = this, 
            isCover, 
            defaultData = {}
            ;
    	
        this.initImages = images;
        _(images).each(function(image, ik) {
            isCover = 0;
            var defaultData = $.extend({id: that.currentSeq}, that.defaultImageData);
            if(image.imageUrl) {
            	image.showImageUrl =  image.imageUrl;
            }
            var $newPic = template('picItemTpl', $.extend(defaultData, image));
            that.$picList.find('ul').append($newPic);
            that.currentSeq ++ ;
        });
    },
    convertToReviewPage : function() {
        var that = this;
        $('.addNewPic').off().remove();
        that.$picList.find('ul').empty();
        _(imagesList).each(function(image, ik) {
            isCover = 0;
            var defaultData = $.extend({id: that.currentSeq}, that.defaultImageData);
            if(image.imageUrl) {
            	image.showImageUrl =  image.imageUrl;
            }
            var $newPic = template('picShowItemTpl', $.extend(defaultData, image));
            that.$picList.find('ul').append($newPic);
            that.currentSeq ++ ;
        });
        $(".goodspic-list ul").sortable('destroy');
    },
    initDateTime : function () {
    	var that = this;
		//开始日期
    	var date = new Date();
    	var year = date.getFullYear();
    	var month = date.getMonth()+1;
    	var day = date.getDate();
    	var hours = that.checkTime(date.getHours());
    	var min = that.checkTime(date.getMinutes());

    	$('#startDate,#endDate').datetimepicker({
    		minView: 'hour',
    		format: 'yyyy-MM-dd hh:ii',
    		language: 'ch',
    		startDate: year+'-'+month+'-'+day + " "+hours + ":" + min,
    		autoclose : true,
    		todayBtn : true
    	});
    	//创建日期
    	$('#startDate').on('changeDate',function(){
    		$('#endDate').datetimepicker('setStartDate', $('#startDate').val());
    		if($('#startDate').val()=="" && $("#startDate").next().css('display') == 'inline-block'){
    			$("#startDate").next().css('display','none');
    		}else{
    			$("#startDate").next().css('display','inline-block');
    		}
    	});

    	//结束日期
    	$('#endDate').on('changeDate',function(){
    		if ($('#endDate').val()) {
    			$('#startDate').datetimepicker('setEndDate', $('#endDate').val());
    		}else{
    			$('#startDate').datetimepicker('setEndDate', year+'-'+month+'-'+day+ " "+hours + ":" + min);
    		};
    		if($('#endDate').val()=="" && $("#endDate").next().css('display') == 'inline-block'){
    			$("#endDate").next().css('display','none');
    		}else{
    			$("#endDate").next().css('display','inline-block');
    		}
    	});
    	if (!this.id) {
    		$('#startDate').val(year+'-'+month+'-'+day+ " "+hours + ":" + min);
        	$('#startDate').trigger("changeDate");
    	}
    	
    },
    checkTime : function(mins){
    	if (mins<10){
    		mins = "0" + mins;
        }
        return mins;
    },
    initEvents : function() {
        var that = this;
        that.initDateTime();
        $('button.submitMainForm').on("click", _(this.submitForm).bind(this));
        $('.addNewPic').on('click', _(this.addNewPic).bind(this));
        this.validator = $('#mainForm').validate({
        	rules : {
        		price : {
        			price : true,
        			max   : 99999
         		},
         		basePrice : {
        			price : true,
        			max   : 99999
         		},
         		bargainMinAmount : {
        			minPrice : true,
        			max   : 99999
         		},
         		bargainMaxAmount : {
        			maxPrice : true,
        			max   : 99999,
//        			randomPrice : true
         		},
         		bargainAmount : {
        			price : true,
        			max   : 99999
         		},
        	},
        	messages:{
        		bargainMinAmount:{
					max:$.validator.format("最小金额不能大于99999")
				},
				bargainMaxAmount:{
					max:$.validator.format("最大金额不能大于99999")
				}
        	},
        	errorPlacement: function(error, element) {
				var fieldSet = element.closest("span.fieldSet"),
					bs       = element.closest('.bootstrap-select');
				
				if (fieldSet.size() > 0) {
					error.appendTo(fieldSet);
				} else if(bs.length>0) {
					bs.after(error);
				} else if ($(element).attr("type") === "checkbox" ){
					error.insertAfter($(element).parent().parent().parent());
				} else if ($(element).attr("name") === "bargainMinAmount" || $(element).attr("name") === "bargainMaxAmount"){
					error.insertAfter($(element).parent());
				} else {
					error.insertAfter(element);
				}
			},
        });
        jQuery.validator.addMethod("minPrice", function(value, element) {
        	return this.optional(element) || /^\d+(\.\d{1,2})?$/.test(value.trim());         
		},'最小金额格式错误！');
        jQuery.validator.addMethod("maxPrice", function(value, element) {
        	return this.optional(element) || /^\d+(\.\d{1,2})?$/.test(value.trim());         
		},'最大金额格式错误！');
        // 商品图片ajax上传
        this.$mainForm.on('change', '.upload-btn input[type="file"]', function(){
            var id = $(this).attr('id');
            that.ajaxFileUpload(id);
        });
        this.$mainForm.on('change', 'input[name="bargainType"]', function(){
        	var type = $(this).val();
            if (type==0) {
            	$("#random").removeClass("hide");
            	$("#fixed").addClass("hide");
            }else{
            	$("#random").addClass("hide");
            	$("#fixed").removeClass("hide");
            }
        });
        //
        this.$mainForm.on('click', this.removeBtn, _(this.removePic).bind(this));
        //预览
        $("#preview").on('click',function(){
        	var des = UE.getEditor('ueEditor-platform').getContent();
        	that.dialog =  BootstrapDialog.show({
				 title: '预览商品详情',
				 message: $(that.previewBargainHtml),
				 draggable: true,
				 size : BootstrapDialog.SIZE_NORMAL,
				 onshown : function(dialog){
					 $("#des").html(des);
					 $('.des img').addClass("img-width");
				 },
				 buttons: [{
					 label: '关闭',
					 action: function(dialog) {
						 dialog.close();
					 }
				 }]
				 });
        });
    },
    submitForm : function() {
    	if(this.$mainForm.valid()) {
    		this.doSave();
    	}
    },
    doSave : function() {
    	if(!this.getPostData()) return;
    	 var result = this.getPostData(),
    	     url    = this.newPostUrl,
    	 	 that   = this;
    	 if(this.id) {
    		url = this.editPostUrl;
            result.id = this.id;
    	 }
    	 $('button.submitMainForm').prop('disabled', true).text('保存中....');
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
				$('button.submitMainForm').prop('disabled', true).text('保存成功');
				that.$mainForm .loadingInfo({
    				type : "success", 
    				text: message("admin.message.success"),
    				callBack : function() {
    					window.location.href=urlPrefix + that.listUrl;
    				}
    			});
			} 
		 }).always(function() {
			 $('button.submitMainForm').prop('disabled', false).text('保存');
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
/*   
 *  http://localhost:8080/admin/web/main/addImage/{source}   souce值在global中定义  返回{"code":"ACK","message":"","data":"/wrw-admin/static/upload/image/brand/201605134e19bd834-7f3b-49e5-8e85-907489e836f6-source.jpg","nonBizError":false}
    
    */
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
                    $('#img_{{id}}'.template({id: id})).attr('src', data.data.url);
                    
                }else {
					$(window).loadingInfo("error", data.message);
				}
                
            }
        });
        return false;
 
    },
   
    removePic : function (e) {
    	var $target = $(e.target),
    		$goodUpload = $target.closest('.goodspic-upload'),
    		picName = $goodUpload.find('input[name="pic.name"]').val();
           
        $goodUpload.remove();
    },
    
    getPostData : function() {
       
        var formData = $('#mainForm').frmSerialize();
        var result = {
            productName: formData['productName'],
            price: formData['price'],
            basePrice: formData['basePrice'],
            effectiveStartDate:formData['effectiveStartDate'],
            effectiveEndDate:formData['effectiveEndDate'],
            bargainType:formData['bargainType'],
            description: _.isUndefined(formData['description']) ? "" : formData['description'] ,
            status:"",
            id:"",
            playerTotal:0,
            bargainMinAmount:'',
            bargainMaxAmount:'',
            bargainAmount:'',
            
        };
        //
        if (parseFloat(formData['price']) <= parseFloat(formData['basePrice'])) {
        	$(window).loadingInfo("error", "商品原价必须大于商品底价!");
        	return false;
        }
        
        if (!formData['effectiveStartDate'] || !formData['effectiveEndDate']) {
        	$(window).loadingInfo("error", "请选择砍价有效期!");
        	return false;
        }
        if(formData['bargainType']==0) {
        	if(formData['bargainMinAmount'] && formData['bargainMaxAmount']){
        		if(!(/^\d+(\.\d{1,2})?$/.test(formData['bargainMinAmount'])) ||  !(/^\d+(\.\d{1,2})?$/.test(formData['bargainMaxAmount']))){
        			$(window).loadingInfo("error", "金额范围中最小、大值格式不正确!");
            		return false;
        		}
        		if (parseFloat(formData['bargainMinAmount']) >= parseFloat(formData['bargainMaxAmount'])){
        			$(window).loadingInfo("error", "金额范围中最小值必须小于最大值!");
            		return false;
        		}
        		if (parseFloat(formData['bargainMaxAmount']) > ((parseFloat(formData['price']))-parseFloat(formData['basePrice']))) {
        			$(window).loadingInfo("error", "金额范围中最大值必须小于等于商品原价和商品底价的差!");
            		return false;
        		}
        		result.bargainMinAmount = formData['bargainMinAmount'];
            	result.bargainMaxAmount = formData['bargainMaxAmount'];
        	}else{
        		$(window).loadingInfo("error", "请输入金额范围!");
        		return false;
        	}
        	
        } else {
        	if (formData['bargainAmount']){
        		if(!(/^\d+(\.\d{1,2})?$/.test(formData['bargainAmount']))){
        			$(window).loadingInfo("error", "固定金额格式不正确!");
            		return false;
        		}
        		if (parseFloat(formData['bargainAmount']) > ((parseFloat(formData['price']))-parseFloat(formData['basePrice']))) {
        			$(window).loadingInfo("error", "固定金额必须小于等于商品原价和商品底价的差!");
            		return false;
        		}
        		result.bargainAmount = formData['bargainAmount'];
        	} else {
        		$(window).loadingInfo("error", "请输入金额!");
        		return false;
            }
        	
        }
        //file
        if ($('#mainForm').find('input[name="picUrl[0]"]').val()) {
        	result.bargainImage = $('#mainForm').find('input[name="picUrl[0]"]').val();
        }else{
        	$(window).loadingInfo("error", "请添加图片!");
        	return false;
        }
        if (!result.description) {
        	$(window).loadingInfo("error", "请添加商品详情!");
        	return false;
        }
        return result;
    }
};
