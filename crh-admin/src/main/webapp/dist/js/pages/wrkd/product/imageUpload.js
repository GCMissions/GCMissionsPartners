
// 初始化图片对象
var defaultImageData = {
	showImageUrl : uiBase + "img/default_goods_image_240.gif",
	imageUrl : "",
	isCover : 0,
	CoverText : "[设为封面]",
	imageId : ""
}; 

// 图片可拖动调整位置
$(".goodspic-list ul").sortable({connectWith:".connectList"}).disableSelection();

// 事件绑定
function imageOperInit(elem) {
	 // 图片上传
	 $(elem).on('change', '.upload-btn input[type="file"]', function(){
         ajaxFileUpload($(this).attr('id'));
     });
     // 图片移除
	 $(elem).on('click', '.remove-btn', function(e){
		 removePic(e);
	 });
}

// 加载图片列表（编辑状态下）
function renderPicList(images, coverImgUrl) {
	var picList = $(".goodspic-list ul");
	_(images).each(function(image, index) {
		var defaultData = $.extend({id : index}, defaultImageData);
		if (coverImgUrl && coverImgUrl == image.imageUrl) {
			image.isCover = 1;
			image.CoverText = "[封面图片]";
		}
		if (image.imageUrl) {
			image.showImageUrl = image.imageUrl;
		}

		var $newPic = template('picItemTpl', $.extend(defaultData, image));
		picList.append($newPic);
	});
}

// 加载图片列表（查看状态下）
function showPicList(images, coverImgUrl) {
	var picList = $(".goodspic-list ul");
	_(images).each(function(image, index) {
        var defaultData = $.extend({id: index}, defaultImageData);
        if (coverImgUrl && coverImgUrl == image.imageUrl) {
        	image.isCover = 1;
        	image.CoverText = "[封面图片]";
        }
        if (image.imageUrl) {
        	image.showImageUrl = image.imageUrl;
        }
        
        var $newPic = template('picShowItemTpl', $.extend(defaultData, image));
        picList.append($newPic);
    });
	// 禁用排序
	$(".goodspic-list ul").sortable('destroy');
}

// 添加图片
var currentSeq = $('.goodspic-list').find('ul li').length;
function addNewPic(maxPics, elem) {
	var imgUl = $('.goodspic-list').find('ul'),
		imgLen = $(imgUl).find('li').length;
	if (maxPics <= imgLen) {
		$(elem).loadingInfo("error", "最多只能添加 "+ maxPics + " 张图片");
		return false;
	}
    var $newPic = template('picItemTpl', $.extend({id: currentSeq}, defaultImageData));
    $(imgUl).append($newPic);
    currentSeq++;
}

// 图片上传ajax
function ajaxFileUpload(id) {
    $('#img_' + id + '').attr('src', uiBase + "/img/loading.gif");
    $.ajaxFileUpload({
        url : $.GLOBAL.config.ossUploadUrl.template({source: uploadSourcesMap.kdProduct}), 
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
            	$('#img_' + id + '').attr('src', defaultImageData.showImageUrl);
            	$(window).loadingInfo("error", data.messge);
            }
        }
    });
    
    return false;
}

// 移除图片
function removePic(e) {
	$(e.target).closest('.goodspic-upload').remove();
}

// 获取图片列表
function getPicList(elem) {
    var picData = [], sort = 0,
        picIds  = $(elem).find('.goodspic-list input[name^=picId]');
    
    $(picIds).each(function(key, value) {
        var seq = $(value).attr('name').replace("picId[",'').replace("]", '');
        var url =  $(elem).find('.goodspic-list input[name="picUrl['+seq+']"]').val();
        var key =  $(elem).find('.goodspic-list input[name="picKey['+seq+']"]').val();
        var picId =  $(elem).find('.goodspic-list input[name="picId['+seq+']"]').val();
        if(!_.isEmpty(url)) {
            picData.push({
                imageUrl : url,
                imageKey : key,
                sort     : sort++,
                imageId  : picId,
                title	 : "吾儿网"
            });
        }
    });
    return picData;
}

//获取所有图片数量（包括有图片和无图片）
function getAllPicCount(elem) {
    return $(elem).find('input[name^=picId]').length;
}
