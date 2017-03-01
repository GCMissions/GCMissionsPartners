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
//        this.renderLists(imagesList);   
        
        //init sort
        $(".goodspic-list ul").sortable({connectWith:".connectList"}).disableSelection();
        return this;
        
    },
    convertToReviewPage : function() {
        var that = this;
        $(".mt20").off().remove();
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
            	image.showImageUrl = image.imageUrl;
            }
            
            var $newPic = template('picShowItemTpl', $.extend(defaultData, image));
            
            
            that.$picList.find('ul').append($newPic);
            that.currentSeq ++ ;
        });
        $(".goodspic-list ul").sortable('destroy');
    },
    //商品品类：如果有二级则选二级，没有二级就选一级
    
};
