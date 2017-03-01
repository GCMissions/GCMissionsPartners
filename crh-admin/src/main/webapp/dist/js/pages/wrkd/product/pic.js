// 
var productApp = {
	cateId : 0,	 
    maxPics : 20,
    currentSeq : 0,
    $mainForm : void 0,
    isCoverText : "[����ͼƬ]",
    notCoverText : "[��Ϊ����]",
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
        $('.addNewPic').on('click', _(this.addNewPic).bind(this));
        // ��ƷͼƬajax�ϴ�
        
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
    
    addNewPic : function() {
    	if(this.$goodspicList.find('li').length >= this.maxPics) {
    		this.$mainForm.loadingInfo("error", "���ֻ����� "+ this.maxPics + " ��ͼƬ");
    		return false;
    	}
    	
        var $newPic = template('picItemTpl', $.extend({id: this.currentSeq}, this.defaultImageData));
        this.$picList.find('ul').append($newPic);
        this.currentSeq ++ ;
    },
    /*
    http://localhost:8080/admin/web/main/addImage/{source}   souceֵ��global�ж���  ����{"code":"ACK","message":"","data":"/wly-admin/static/upload/image/brand/201605134e19bd834-7f3b-49e5-8e85-907489e836f6-source.jpg","nonBizError":false}
    
    */
    // ͼƬ�ϴ�ajax
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
                    
                }else {
                	$(window).loadingInfo("error", data.messge);
                }
                
            }
        });
        return false;
 
    },
   
    removePic : function (e) {
    	var $target = $(e.target),
    		$goodUpload = $target.closest('.goodspic-upload'),
    		picName = $goodUpload.find('input[name="pic.name"]').val()
    		imgKey=$goodUpload.find('input[name^="picKey"]').val();
           
        $goodUpload.remove();
        //oss��ͼƬɾ��
        /*$.ajax({
    		type         : 'POST',
			url          : urlPrefix + "main/imageDelete",
			data         :{
				key : imgKey
			},
			dataType     : 'json',
			success : function(result){
				if(result.code!='NACK'){
					
				}
			}
    	});*/
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
//            description: _.isUndefined(formData['description']) ? "" : formData['description'] ,
            rebackNote : formData['rebackNote'],	
            note   : formData['note'],	
            isCaptcha : formData['isCaptcha']
        
        };
        //file
        var picData = [];  
        var picIds  = $('#mainForm').find('input[name^=picId]');
        var sort = 0;
        //@TODO ignore the item with empty picUrl
        
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
                    title	 : "�����"
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
