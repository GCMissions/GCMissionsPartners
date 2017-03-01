<div class="goodspic-list">
<#if listImages??>
	<ul>
	<#list listImages as image>
		<#if isReview == "2">
		<li class="goodspic-upload" id="upload_${image_index}">
    		<div class="upload-thumb">
        		<img src="${image.imageUrl}"  id="img_${image_index}">
        		<input type="hidden" name="picId[${image_index}]" value="${image_index}"  id="name_${image_index}">
        		<input type="hidden" name="picUrl[${image_index}]" value="${image.imageUrl}"  id="name_${image_index}">
				<input type="hidden" name="picKey[${image_index}]" value="${image.imageKey}"  id="name_${image_index}">
    		</div>

    		<div class="upload-setDefault">
    			<div class="form-group">
    				<input type=hidden name="goodsCover[${image_index}]" class="goodsCover " value="{{isCover}}" >
            		<a href="javascript:;" class="btnSetCover {{if isCover}}isCover{{/if}}"></a>
    			</div>
    		</div>
    		<div class="upload-btn">
	    		<a href="javascript:void(0);">
		    		<span><input type="file" hidefocus="true" size="1" class="input-file" name="file" id="${image_index}"></span>
		    		<p><i class="fa fa-fw fa-upload"></i>上传</p>
	    		</a>
			</div>
			<div class="remove-btn">
	    		<a href="javascript:void(0);">
		    		<p><i class="fa fa-fw fa-times"></i>删除</p>
	    		</a>
			</div>
		</li>
		<#else>
		<li class="goodspic-upload-show goodspic-upload" id="upload_${image_index}">
			<div class="upload-item">
				<a href="${image.imageUrl}" target="_blank" title="点击查看大图"> 
					<img src="${image.imageUrl}" id="img_${image_index}" />
				</a>
			</div>
			<div class="upload-setDefault"><#if image_index == 0>  </#if></div>
		</li>
		</#if>
	</#list>
	</ul>
	<#if isReview == "2">
	<div class="mt20">
		<p>建议上传格式为jpg/jpeg/png，尺寸为640X640，拖曳图片可以调整顺序，至少3张，最多上传5张</p>
	</div>
	</#if>

<#else>
	<div class="form-goods-pic" style='margin:0;'>
		<div class="container">
			<div class="goodspic-list">
				<ul>
				</ul>
			</div>
			<div class="mt20">
				<p>建议上传格式为jpg/jpeg/png，尺寸为640X640，拖曳图片可以调整顺序，至少3张，最多上传5张</p>
			</div>
		</div>
	</div>
	<!-- 添加图片 说明 -->
</#if>
</div>
<script id="picItemTpl" type="text/html">
<li class="goodspic-upload" id="upload_{{id}}">
    <div class="upload-thumb">
        <img src="{{showImageUrl}}"  id="img_{{id}}">
        <input type="hidden" name="picId[{{id}}]" value="{{imageId}}"  id="name_{{id}}">
        <input type="hidden" name="picUrl[{{id}}]" value="{{imageUrl}}"  id="name_{{id}}">
		<input type="hidden" name="picKey[{{id}}]" value="{{imageKey}}"  id="name_{{id}}">
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
<!-- 添加图片样式 -->

<script id="picShowItemTpl" type="text/html">
<li class="goodspic-upload-show" id="upload_{{id}}">
	<div class="upload-item">
	<a href="{{showImageUrl}}" target="_blank" title="点击查看大图">
        <img src="{{showImageUrl}}"  id="img_{{id}}">
    </a>
    </div>
    <div class="upload-setDefault">
    	{{if isCover > 0}}
    	封面图片
    	{{/if}}
    </div>
</li>
</script>
<!-- 图片显示 -->