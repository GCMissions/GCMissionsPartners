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
    copyGoodsHtml : "<div class='popTreeDialogSection box-body form-horizontal'>"
		+"<div class='form-group '>"
         +"<div class='col-sm-12'>" 
          +"<select name='cFirstCategory' id='cFirstCategory' class='selectpicker form-control' style='width:30%;' title='一级商品品类'></select>" 
          +"<select name='cSecondCategory' id='cSecondCategory' class='selectpicker form-control' style='width:30%;margin-left:25px;' title='二级商品品类'>" 
          +	"</select>"
          +"<select name='cOrgId' id='cOrgId' class='selectpicker form-control' style='width:30%;margin-left:25px;' title='服务商'></select>" 
         +"</div>"
        +"</div>"
        +"<div class='form-group '>"
         +"<div class='col-sm-5'><input type=text class='form-control' id='cActivityName' style='width:70%' placeholder='商品名称'></div>"
         +"<div class='col-sm-2'><button type=button id='rRefreshRecord' class='btn btn-primary'><i class='fa fa-search'></i>查询</div>"
        +"</div>"
        +"<div class='row 　col-md-12　treeTable'>"
                +"<table class='tree_table' id='copyGoodsList'>"
                +"<thead class='borderRow'>"
                +"<th  class='text-center'></th>"
                +"<th  class='text-center'>序号</th>"
                +"<th  class='text-center'>商品品类</th>"
                +"<th  class='text-center'>服务商</th>"
                +"<th  class='text-center'>商品名称</th>"
                +"</thead>"
                +"<tbody>"
                +"</tbody>"
                +"</table>"
        +"</div>" 
        +"</div>"
        ,
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
			async		 : false,
			contentType  : 'application/json',
			success : function(msg){
				var data = msg.data;
				var _opt = "";
				if(data && data.length != 0){
					for(var i=0; i<data.length; i++){
						_opt += "<option value='"+data[i].cateId+"'>"+data[i].cateName+"</option>";
					}
					$("#secondCategory").attr('disabled',false);
				}else{
					$("#secondCategory").attr('disabled',true);
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
            if (that.productImage && that.productImage == image.imageUrl) {
            	image.isCover = 1;
            	image.CoverText = that.isCoverText;
            }
            if (image.imageUrl) {
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
        $('#copy-product').on('click', _(this.addCopyGoods).bind(this));
        $("button[name='save-reback']").on('click',function(){
        	window.location.href = urlPrefix + "activity/";
        });
        $("#save-reback").on('click',function(){
        	$('#mytabs li a[href="#tab_1"]').tab('show');
        	$('#firstCategory').attr('disabled',true);
        	$('#secondCategory').attr('disabled',true);
        	$('#copy-product').attr('disabled',true);
        	$('#secondCategory-error').removeClass('hidden');
		});
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
    					$('#mytabs li a[href="#tab_3"]').tab('show');
    				}
    			});
			} 
		 }).always(function() {
			 $('#saveDetailInfo').prop('disabled', false).text("下一步");
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
    	 $('#copy-product').attr('disabled',true);
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
				// 传递给库存及购买信息设置界面
				actStockInstance.productId = result.data.productId;
				// 是否是VIP商品
		    	var vipFlag = $("input[name='isVip']:checked").val();
		    	if ('0' == vipFlag) {
    				$("#tab_3 .stockInfo .vip-price").prop('disabled', true);
    			} else {
    				$("#tab_3 .stockInfo .vip-price").prop('disabled', false);
    			}
				$('#saveBaseInfo').prop('disabled', true).text('保存成功');
				that.$mainForm .loadingInfo({
    				type : "success", 
    				text: message("admin.message.success"),
    				callBack : function() {
    					$("#base-li").removeClass("active");
    					$("#ss-a").click();
    					$("#detail-li").addClass("active");
    					$("#tab_1").removeClass("active");
    					$("#tab_2").addClass("active");
    				}
    			});
			} 
		 }).always(function() {
			 $('#saveBaseInfo').prop('disabled', false).text("下一步");
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
    addCopyGoods : function() {
        var that = this;
        that.dialog =  BootstrapDialog.show({
            title: '选择商品',
            //type : BootstrapDialog.TYPE_DEFAULT,
            message: $(this.copyGoodsHtml),
            draggable: true,
            buttons: [{
                label: '确认',
                cssClass: 'btn-primary',
                action: function(dialog) {
                	var activities = $('#copyGoodsList').bootstrapTable('getSelections');
                	if(!activities || activities.length==0){
                		$(window).loadingInfo({
            				type : "error", 
            				text: message("请选择要复制的商品"),
            				callBack : function() {
            				}
            			});
                	}else{
                		that.CopyActivities(activities[0]);
                        dialog.close();
                	}
                    
                }
            }, {
                label: '取消',
                action: function(dialog) {
                    dialog.close();
                }
            }],
            onshown : function() {
                that.initDialogEvent();
                that.initCopyTable();
               
            }
       });
       that.dialog.getModalDialog().css('width', '700px');
    }, 
    CopyActivities : function($activity) { //copy origin product to target product
    	var that = this;
    	// 事件传递，复制商品的库存以及购买信息
    	actStockInstance.copyInfoDeal($activity.productId);
    	//base
    	$("#productName").val(that.escape2Html($activity.productName));
    	//判断是否是子品类，是子品类则获得父品类
    	var isSon = true;
    	$("#firstCategory").find("option").each(function(){
    		var $this = $(this);
    		if ($this.text()==$activity.cateName) {
    			isSon = false;
    			$("#firstCategory").val($activity.cateId);
    			$("#firstCategory").trigger('change');
    		}
    	});
    	if(isSon){
    		//获得父品类
    		$.ajax({
        		type         : 'get',
    			url          : urlPrefix + "activity/getParentCateBySon/" + $activity.cateId,
    			dataType     : 'json',
    			async        : false,
    			contentType  : 'application/json',
    			success : function(msg){
    				var data = msg.data;
    				$("#firstCategory").val(data);
    				$("#firstCategory").trigger('change');
    			}
        	});
    	}
    	//获得二级品类
    	$("#secondCategory").find("option").each(function(){
    		var $this = $(this);
    		if ($this.text()==$activity.cateName) {
    			$("#secondCategory").val($activity.cateId);
    			$("#secondCategory").trigger('change');
    		}
    	});
    	$("#orgId").find("option").each(function(){
    		var $this = $(this);
    		if ($this.text()==$activity.orgName) {
    			$("#orgId").val($activity.orgId);
    			$("#orgId").trigger('change');
    		}
    	});
    	
    	//是否需要验证码复制
    	$("input[name='isVip']").each(function(){
    		if ($(this).val() == $activity.vip){
    			$(this).prop("checked", true);
    			if ('0' == $activity.vip) {
    				$("#tab_3 .stockInfo .vip-price").prop('disabled', true);
    			} else {
    				$("#tab_3 .stockInfo .vip-price").prop('disabled', false);
    			}
    		} else {
    			$(this).prop("checked", false);
    		}
    	});
    	
    	$("#note").val($activity.note);
    	$("#rebackNote").val($activity.rebackNote);
    	$("#price").val($activity.price);
    	$("#originalPrice").val($activity.originalPrice);
    	//是否需要验证码复制
    	$("input[name='isCaptcha']").each(function(){
    		var $this = $(this);
    		$this.attr("checked","false");
    		if($this.val()==$activity.isCaptcha && !$this.attr("checked")){
    			$this.attr("checked","true");
    		}
    	});
    	//处理图片id
    	for(var i=0; i<$activity.listImages.length; i++){
    		$activity.listImages[i].imageId = "";
    	}
    	$(".goodspic-list ul").html("");
    	that.renderLists($activity.listImages);
    	//detail
    	UE.getEditor('ueEditor-platform').execCommand('cleardoc');;
    	var des = that.escape2Html($activity.description);
    	UE.getEditor('ueEditor-platform').execCommand('insertHtml',des);
    	
    },
    escape2Html : function(str) {//编辑时处理描述字符串中的特殊字符
    	if(str && str.length > 0){
    		var arrEntities={'lt':'<','gt':'>','nbsp':' ','amp':'&','#34':'"'};
        	return str.replace(/&(lt|gt|nbsp|amp|#34);/ig,function(all,t){return arrEntities[t];});
    	}else{
    		return "";
    	}
    	
    },
    initCopyTable : function() {//初始化复制表格
        var that = this;
        this.relatedBootTable = $.GLOBAL.utils.loadBootTable({
            table : $('#copyGoodsList'),
            //removeBtn : $('#removeRecord'),
            refreshBtn : $('#rRefreshRecord'),
            idField : "id",
            pagination : true,
            singleSelect : true,
            pageSize : 10,
            url: 'activity/list',
            sidePagination:'server',
            queryAddParams: function() { 
            	var fCateId = $("#cFirstCategory").val(),
            	sCateId = $("#cSecondCategory").val(),categoryId;
            	categoryId = sCateId?sCateId:fCateId;
                return {
                    categoryId : categoryId,
                    orgId : $("#cOrgId").val(),
                    productName : $.trim($('#cActivityName').val())
                }
            },
            columns: [
                {
                	field : 'id',
                    align: 'center',
                    radio: true
                } ,
                {
					width: 0,
					align: 'center',
                    formatter:function(value,row,index){  
                        return index+1; 
                    }
                } ,
                {
                    field: 'psCate'  
                } ,
                {
                    field: 'orgName'  
                },
                {
                    field: 'productName'  
                } 
            ]
            
        });
        
    },
    initDialogEvent : function() {//初始化复制弹框
    	var that = this;
    	that.initDialogFCategory();
    	$("#cSecondCategory").attr('disabled',true);
    	$("#cSecondCategory").selectpicker('val', '');
		$("#cSecondCategory").selectpicker('refresh');
    	$("#cFirstCategory").on('change',function() {
    		that.initDialogSCategory();
    	});
    	that.initOrgs();
    },
    initOrgs : function() {
    	$.ajax({
    		type         : 'get',
			url          : urlPrefix + "activity/getOrgs",
			dataType     : 'json',
			contentType  : 'application/json',
			success : function(msg){
				var data = msg.data;
				var _opt = "<option value=''>不限</option>";
				if(data && data.length != 0){
					for(var i=0; i<data.length; i++){
						_opt += "<option value='"+data[i].orgId+"'>"+data[i].orgName+"</option>";
					}
				}
				$("#cOrgId").html(_opt);
				$("#cOrgId").selectpicker('val', '');
				$("#cOrgId").selectpicker('refresh');
			}
    	});
    },
    initDialogFCategory : function() {//初始化复制弹出框一级品类
    	$.ajax({
    		type         : 'get',
			url          : urlPrefix + "activity/getParentCate",
			dataType     : 'json',
			async        : false,	
			contentType  : 'application/json',
			success : function(msg){
				var data = msg.data;
				var _opt = "<option value=''>不限</option>";
				if(data && data.length != 0){
					for(var i=0; i<data.length; i++){
						_opt += "<option value='"+data[i].cateId+"'>"+data[i].cateName+"</option>";
					}
				}
				$("#cFirstCategory").html(_opt);
				$("#cFirstCategory").selectpicker('val', '');
				$("#cFirstCategory").selectpicker('refresh');
			}
    	});
    },
    initDialogSCategory : function() {//初始化复制弹出框二级品类
    	var categoryId = $("#cFirstCategory").val();
    	if (categoryId) {
    		$.ajax({
        		type         : 'get',
    			url          : urlPrefix + "activity/getSubCategoryByParent/" + categoryId,
    			dataType     : 'json',
    			async        : false,	
    			contentType  : 'application/json',
    			success : function(msg){
    				var data = msg.data;
    				var _opt = "";
    				if(data && data.length != 0){
    					_opt = "<option value=''>不限</option>";
    					for(var i=0; i<data.length; i++){
    						_opt += "<option value='"+data[i].cateId+"'>"+data[i].cateName+"</option>";
    					}
    					$("#cSecondCategory").attr('disabled',false);
    				}else{
    					$("#cSecondCategory").attr('disabled',true);
    				}
    				$("#cSecondCategory").html(_opt);
    				$("#cSecondCategory").selectpicker('val', '');
    				$("#cSecondCategory").selectpicker('refresh');
    			}
        	});
    	}else{
    		$("#cSecondCategory").html("");
    		$("#cSecondCategory").attr('disabled',true);
    		$("#cSecondCategory").selectpicker('val', '');
			$("#cSecondCategory").selectpicker('refresh');
    	}
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
        var picData = [];  
        var picIds  = $('#mainForm').find('input[name^=picId]');
        var sort = 0;
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
