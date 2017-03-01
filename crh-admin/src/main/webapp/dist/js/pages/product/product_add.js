// 
var productApp = {
	cateId : 0,	
    maxPics : 20,
    maxRelatedGoods : 8,
    currentSeq : 0,
    $mainForm : void 0,
	$navTabs : void 0,
    validator : void 0,
    isCoverText : "[封面图片]",
    notCoverText : "[设为封面]",
    $picList : $('.goodspic-list'),
    coverElement : ".goodsCover",
    removeBtn : ".remove-btn",
    newPostUrl : "product/add",
    editPostUrl : "product/edit",
    defaultImg   : uiBase + "img/default_goods_image_240.gif",
    selectedGoods : {},
    listUrl    : "product/",
    copyGoodsHtml : "<div class='popTreeDialogSection box-body form-horizontal'>"
		+"<div class='form-group '>"
         +"<div class='col-sm-7'>" 
          +"<select name='cFirstCategory' class='form-control' style='width:30%;'></select>" 
          +"<select name='cSecondCategory' class='form-control' style='width:30%;margin-left:25px;'></select>"
          +"<select name='cServiceId' class='form-control' style='width:30%;margin-left:25px;'></select>" 
         +"</div>"
        +"</div>"
        +"<div class='form-group '>"
         +"<div class='col-sm-5'><input type=text class='form-control' id='cProductName' style='width:70%' placeholder='商品名称'></div>"
         +"<div class='col-sm-2'><button type=button id='rRefreshRecord' class='btn btn-primary'><i class='fa fa-search'></i>开始搜索</div>"
        +"</div>"
        +"<div class='row 　col-md-12　treeTable'>"
                +"<table class='tree_table' id='copyGoodsList'>"
                +"<thead class='borderRow'>"
                +"<th  class='text-center'></th>"
                +"<th  class='text-center'>序号</th>"
                +"<th  class='text-center'>商品品类</th>"
                +"<th  class='text-center'>供应商</th>"
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
            if(that.productImage && that.productImage == image.imageUrl) {
            	image.isCover = 1;
            	image.CoverText = that.isCoverText;
            }
            if(image.imageUrl) {
            	image.showImageUrl = fileUrlPrefix + image.imageUrl;
            }
            
            var $newPic = template('picItemTpl', $.extend(defaultData, image));
            
            
            that.$picList.find('ul').append($newPic);
            that.currentSeq ++ ;
        });
       
    },
    convertToReviewPage : function() {
        var that = this;
        $('#copy-product').off().remove();
        $('.addNewPic').off().remove();
        that.$picList.find('ul').empty();
        _(imagesList).each(function(image, ik) {
            isCover = 0;
            
            var defaultData = $.extend({id: that.currentSeq}, that.defaultImageData);
            if(that.productImage && that.productImage == image.imageUrl) {
            	image.isCover = 1;
            	image.CoverText = that.isCoverText;
            }
            if(image.imageUrl) {
            	image.showImageUrl = fileUrlPrefix + image.imageUrl;
            }
            
            var $newPic = template('picShowItemTpl', $.extend(defaultData, image));
            
            
            that.$picList.find('ul').append($newPic);
            that.currentSeq ++ ;
        });
        $(".goodspic-list ul").sortable('destroy');
    },
    initEvents : function() {
        var that = this;
        this.validator = $('#mainForm').validate({
        	rules : {
        		secondCategory : {
        			required : true
        		},
        		productName : {
        			required : true
        		},
        		serviceId : {
        			required : true
        		},
        		price : {
        			required : true,
        			price : true,
        			max   : 9999999.99
         		}
        	}, 
        	messages : {
        		productName : {
        		},
        		productCode : {
        		},
        		
        		weight : {
        		},
        	}
        	
        });
        this.initInputMask();
        $('button.submitMainForm').on("click", _(this.submitForm).bind(this));
        $('.addNewPic').on('click', _(this.addNewPic).bind(this));
        $('#copy-product').on('click', _(this.addCopyGoods).bind(this));
        // 商品图片ajax上传
        
        this.$mainForm.on('change', '.upload-btn input[type="file"]', function(){
            var id = $(this).attr('id');
            that.ajaxFileUpload(id);
        });
        //
        this.$mainForm.on('click', this.removeBtn, _(this.removePic).bind(this));
        
        
    
    },
    initInputMask : function() {
    	$('#price').inputmask("mask", {
    		alias : "decimal",
    		rightAlignNumerics : false
    		
    	});
    },
    submitForm : function() {
    	if(!this.validator.form()) {
    		
    		this.$navTabs.find('li:eq(0) a').tab('show') ;
    		this.$navTabs.find('li:eq(0)').addClass('has-error')
    	} else {
    		this.$navTabs.find('li:eq(0)').removeClass('has-error');
            var tempData = $('#mainForm').frmSerialize();
            
            this.doSave();
    	}
    },
    doSave : function() {
    	 var result = this.getPostData(),
    	     url    = this.newPostUrl,
    	 	 that   = this;
    	 if(this.productId) {
    		url = this.editPostUrl;
            result.productId = this.productId;
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
                	var productId = $('#copyGoodsList').bootstrapTable('getSelections'); 
                    that.CopyGoods(productId[0]);
                    dialog.close();
                }
            }, {
                label: '取消',
                action: function(dialog) {
                    dialog.close();
                }
            }],
            onshown : function() {
                that.initCopyTable();
                
               
            }
       });
       that.dialog.getModalDialog().css('width', '700px');
    }, 
    CopyGoods : function($product) { //copy origin product to target product
    	
    },
    initCopyTable : function() {
        var that = this;
        this.relatedBootTable = $.GLOBAL.utils.loadBootTable({
            table : $('#copyGoodsList'),
            //removeBtn : $('#removeRecord'),
            refreshBtn : $('#rRefreshRecord'),
            idField : "productId",
            pagination : true,
            singleSelect : true,
            pageSize : 10,
            url: 'product/list',
            sidePagination:'server',
            queryAddParams: function() { 
                return {
                    firstCategory : $('#cFirstCategory').val(),
                    secondCategory : $('#cSecondCategory').val(),
                    serviceId : $('#cServiceId').val(),
                    productName : $.trim($('#cProductName').val())
                }
            },
            columns: [
                {
                   
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
                    field: 'productCategory'  
                } ,
                {
                    field: 'productName'  
                } ,
                {
                    field: 'serviceName'  
                } 
            ]
            
        });
        
    },
    /*
    http://localhost:8080/admin/web/main/addImage/{source}   souce值在global中定义  返回{"code":"ACK","message":"","data":"/wly-admin/static/upload/image/brand/201605134e19bd834-7f3b-49e5-8e85-907489e836f6-source.jpg","nonBizError":false}
    
    */
    // 图片上传ajax
    ajaxFileUpload:   function (id, o) {
        var that = this;
        $('#img_' + id + '').attr('src', uiBase + "/img/loading.gif");
    
        $.ajaxFileUpload({
            url : $.GLOBAL.config.uploadUrl.template({source: uploadSourcesMap.product}), 
            secureuri : false,
            fileElementId : id,
            dataType : 'json',
            global : false,
            data : {},
            success : function (data, status) {
                if (data.code == "ACK") {
                    $('input[name="picUrl['+id+']"]').val(data.data);
                    $('#img_{{id}}'.template({id: id})).attr('src', fileUrlPrefix+data.data);
                    
                } else {
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
            price: formData['price'],
            productName: formData['productName'],
            firstCategory: formData['firstCategory'],
            secondCategory: formData['secondCategory'],
            serviceId: formData['serviceId'],
            desc: _.isUndefined(formData['introduction']) ? "" : formData['introduction'] ,
            note   : formData['note'],	
            refundsDescription   : formData['refundsDescription'],	
        
        };
        //file
        var picData = [];  
        var picIds  = $('#mainForm').find('input[name^=picId]');
        var sort = 0;
        //@TODO ignore the item with empty picUrl
        
        $(picIds).each(function(key, value ) {
            var seq = $(value).attr('name').replace("picId[",'').replace("]", '');
            var url =  $('#mainForm').find('input[name="picUrl['+seq+']"]').val();
            var picId =  $('#mainForm').find('input[name="picId['+seq+']"]').val();
            if(!_.isEmpty(url)) {
                picData.push({
                    imageUrl : url,
                    sort     : sort++,
                    imageId  : picId
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
