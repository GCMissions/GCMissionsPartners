var CatLoader={
	init:function(){
		var self = this;
		$("#nextBtn").attr("disabled",true);
		this.loadChildren(0);
		
		$("#nextBtn").click(function(){
			var catid  = self.getSelectedCatId();
			if(!catid){
				//alert("请选择商品的分类");
				$('#cate-container').loadingInfo("warn", "请选择商品的分类");
			}else{
				location.href=$.GLOBAL.config.urlPrefix+"product/addPage/"+catid;
			}
			
		});
	},
	loadChildren:function(catid){
		var self = this;
		$.ajax({
			url      : $.GLOBAL.config.urlPrefix + 'category/list/'+catid, 
			dataType : 'json',
            method   : "post",
			cache    : false
			
		})
        .done(function(result){
            if(result.code== "ACK"){
                //when no child node .You can get into  next step !
                if(result.data.length>0) {
                    self.appendCatList(result.data,catid);
                } 
               
                self.refreshPath();
            }
        });
	},
	appendCatList:function(catJson,catid){
		if(catJson.length==0) return ;
		var self= this;
		var selHtml ="<div class='cat_box'  id='box_"+catid+"'>";
		$.each(catJson,function(i,cat){
			selHtml+="<li catid="+cat.cateId+" >";
			selHtml+=cat.cateName;
			selHtml+="</li>";
		});
		selHtml+="</div>";
		$(selHtml).appendTo( $("#cate-container") )
		.find("li").on("click", function(e) {
			$("#nextBtn").attr("disabled",false);
			var $this= $(this);
			self.removeChildBox( $this.parent().attr("id")  );
			//$("#nextBtn").attr("disabled",true);
			self.loadChildren($this.attr("catid"));
			
			$this.siblings().removeClass("selected");
			$this.addClass("selected");
			
			
		});
	 
	},
	removeChildBox:function(boxid){
		var flag= false;
		$("#cate-container .cat_box").each(function(){
			var $this= $(this);
			if(flag){
				$this.remove();
			}
			if($this.attr("id")== boxid){
				flag = true;
			}
		});
	},
	refreshPath:function(){
		var pathbox =$("#cate-path .category-path").empty();
		var html ="";
		$("#cate-container li.selected").each(function(i,v){
			if(i!=0) html+="<li>&nbsp;&gt;&nbsp;</li>";
			var name = $(this).text();
			html+=("<li>"+name+"</li>"); 
		});
		pathbox.append(html);
	},
	getSelectedCatId:function(){
		var catEl=  $("#cate-container li.selected:last");
		if(catEl.length==0){
			return false;
		}else{
			return catEl.attr("catid");
		}
		
	}
		
};
$(function(){
	CatLoader.init();
});
