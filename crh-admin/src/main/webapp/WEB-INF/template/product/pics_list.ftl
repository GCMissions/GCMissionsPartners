<label class="col-sm-2 control-label"><span class="requiredField">*</span>商品图片：</label>
<div class="form-goods-pic">
	<div class="container">
	   <div class="title">
          <button type="button" class="btn btn-primary addNewPic"><i class="fa fa-plus"></i> 添加图片</button>
       </div>
		<div class="goodspic-list">
			<ul>
			</ul>
		</div>
		<div class="mt20" >
        <p>建议上传格式为jpg/jpeg/png，尺寸为640X640，拖曳图片可以调整顺序，至少1张，最多上传20张</p>
      </div>
	</div>
</div>

<script id="picItemTpl" type="text/html">
<li class="goodspic-upload" id="upload_{{id}}">
    <div class="upload-thumb">
        <img src="{{showImageUrl}}"  id="img_{{id}}">
        <input type="hidden" name="picId[{{id}}]" value="{{imageId}}"  id="name_{{id}}">
        <input type="hidden" name="picUrl[{{id}}]" value="{{imageUrl}}"  id="name_{{id}}">
    </div>

    <div class="upload-setDefault">
    	<div class="form-group">
    		<input type=hidden name="goodsCover[{{id}}]" class="goodsCover " value="{{isCover}}" >
            <a href="javascript:;" class="btnSetCover {{if isCover}}isCover{{/if}}"></a>
    	</div>
    </div>
    <div class="upload-btn">
    	
	    <a href="javascript:void(0);">
		    <span><input type="file" hidefocus="true" size="1" class="input-file" name="file" id="{{id}}"></span>
		    <p><i class="fa fa-fw fa-upload"></i>上传</p>
	    </a>
	</div>
	
	<div class="remove-btn">
    	
	    <a href="javascript:void(0);">
		   
		    <p><i class="fa fa-fw fa-times"></i>删除</p>
	    </a>
	</div>
	
</li>
   
	
</script>
<script id="picShowItemTpl" type="text/html">
<li class="goodspic-upload-show" id="upload_{{id}}">
	<div class="upload-item">
	<a href="{{showImageUrl}}" target="_blank" title="点击查看大图">
        <img src="{{showImageUrl}}"  id="img_{{id}}">
    </a>
    </div>
    <div class="upload-setDefault">
    	{{if isCover}}
    	封面图片
    	{{/if}}
    </div>
</li>
</script>