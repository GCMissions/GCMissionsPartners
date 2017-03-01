$(function() {
	var productApp = {
		bootTable : void 0,
		numReg : '^[-+]?[0-9]+(\.[0-9]+)?$',
		init : function(){
			this.$dataList = $('#dataList');
			this.initTable(); 
	        
	        this.bindEvents();
	        $("#firstCate").trigger('change');
	        
	        this.copyLink();
		},
		
		bindEvents : function(){
			var that = this;
			$("#firstCate").on('change',function(e){
				$("#secondCate").html('');
				var firstCateId = $('#firstCate').val();
				if(firstCateId==0||firstCateId==null){
					firstCateId = -1;
				}
				$.ajax({
					type: "GET",
					url:urlPrefix+"activity/getSubCategoryByParent/"+firstCateId,
					dataType: 'json',
					success: function(result){
						var data = result.data;
						if(result.code == 'ACK'){
							if(data && data.length != 0){
								$("#secondCate").attr('disabled',false);
								$("#secondCate").append('<option value="">不限</option>');
							}else{
								$("#secondCate").attr('disabled',true);
							}
							$.each(data,function(i,v){
								$("#secondCate").append('<option value="'+ v.cateId+'">'+ v.cateName +'</option>');
							});
							if($("#secondCate").data('val')) {
								$("#secondCate").val($("#secondCate").data('val'));
								$('#secondCate').data('val', '');
							}
						
							$("#secondCate").selectpicker('refresh');
						}
					}
				});
			});
			$("#lowPrice").on('change',function(){
				that.valiPrice();
			});
			$("#highPrice").on('change',function(){
				that.valiPrice();
			});
					
			$("#seletAll").on('click',function(){
				$('input[name=product]').prop("checked",this.checked);
				
				$('input[name=product]').each(function(){
					$(this).on('click',function(){
						 if ( !this.checked ){
							 $("#seletAll").prop("checked",false);
						 }
						 var sflag = true;
						 $('input[name=product]').each(function(){
							 if (!this.checked){
								 sflag = false;
							 }
						 });
						 if(sflag){
							 $("#seletAll").prop("checked",true);
						 }
					})
					
				});
			});
			
			$("#search").on('click',function(){
				$("#seletAll").prop("checked",false);
			});

			$("#deleteItem").on("click",function(){
				
				 var ids = "";
				 var idCount= 0 ;
				 $("input[name=product]:checked").each(function(){
					 ids += $(this).val()+";";
					 idCount++;
				 });
				 ids = ids.substr(0,ids.length-1);
				 if(idCount == 0){
					 that.dialog =  BootstrapDialog.show({
				            title: '请选择要删除的商品',
				            type : BootstrapDialog.TYPE_WARNING,
				            message: message('已选择'+idCount+'条商品'),
				            draggable: true,
				            size : BootstrapDialog.SIZE_SMALL,
				            buttons: [{
				                label: '返回',
				                cssClass: 'btn-primary ',
				                action: function(dialog) {
				                	dialog.close();
				                    
				                }
				            }]
				        });
				 }else{
					 that.dialog =  BootstrapDialog.show({
					 title: '删除商品',
					 type : BootstrapDialog.TYPE_WARNING,
					 message: message('admin.dialog.deleteConfirm'),
					 
					 draggable: true,
					 size : BootstrapDialog.SIZE_SMALL,
					 buttons: [{
						 label: '确认删除',
						 cssClass: 'btn-primary ',
						 action: function(dialog) {
							 dialog.close();
							 that.doDelete(that,ids,idCount);
						 }
					 }, {
						 label: '取消',
						 action: function(dialog) {
							 dialog.close();
						 }
					 }]
					 });
				 }
			});
		},
		
		initTable : function() {
			var that = this;
			this.bootTable = $.GLOBAL.utils.loadBootTable({
	            table : this.$dataList,
	            refreshBtn : $('#search'), 
	            pagination : true,
	            pageSize : 50,
	            url: 'activity/activityList',
	            sidePagination:'server',
	            queryAddParams: function() {
	                var queryObj =  {
	                    productName:  $.trim($('#productName').val()),
	                    productCode: $.trim($('#productCode').val()),
	                    orgId		: $('#orgId').val(),
	                    vipFlag : $('#vipFlag').val(),
	                    firstCateId     : $('#firstCate').val(),
	                    secondCateId     : $('#secondCate').val() || $('#secondCate').data('val'),
	                    lowPrice    : $.trim($('#lowPrice').val()),       
	                    highPrice  : $.trim($('#highPrice').val()), 
	                } ;
	                if (queryObj.firstCateId =='' && queryObj.secondCateId =='') {
	                	queryObj.firstCateId= 0;
	                }
	                return queryObj;
	            },
	            initSearchForm : true, 
			    fillSearchData : function(data) {
			    	$('#productName').val(data.productName);
			    	$('#productCode').val(data.productCode);
			    	
			  
			    	$('#secondCate').selectpicker();
			    
			    	
			    	
			    	$('#orgId').val(data.orgId);
			    	$('#vipFlag').val(data.vipFlag);
			    	$('#secondCate').data('val', data.secondCateId);
			    	$('#secondCate').selectpicker('val', data.secondCateId);
			    	$('#firstCate').val(data.firstCateId);
			    	
			    	$('#lowPrice').val(data.lowPrice);
			    	$('#highPrice').val(data.highPrice);
			    	
			    	
			    	
			    },		
	            columns: [
                    {
                    	field: 'productId',
                    	align: 'center',
                    	formatter:function(value,row,index){  
                    		return '<input type="checkbox" name="product" value="'+row.productId+'">';
                    	}
                    } ,
					{
	                    field: 'productCode',
	                } ,
	                {
	                    field: 'cateName',
	                } ,
	                {
	                    field: 'orgName',
	                    width:180
	                } ,
	                {
	                    field: 'productName',
	                    width:300,
	                    formatter: function(value,row,index){
	                    	var str = value;
	                    	re=new RegExp(" ","g");
	                    	var newstr=str.replace(re,"&nbsp;");
	                    	return '<span>'+newstr+'</span>';
	                    }
	                } ,
	                {
	                    field: 'price',
	                } ,
	                {
	                    field: 'createDate',
	                    width:100
	                } ,
	                {
	                    field: 'productId',
	                    align: 'center',
	                    width:90,
	                    formatter: function(value,row,index){
	                    	var result = ' <a   href="'+urlPrefix+'activity/viewPage/'+row.productId+'" class="viewItem" data-id="'+row.productId+'"> 查看</a>'
	                    	if (row.saleFlag != 1) {
	                    		result += ' <a href="'+urlPrefix+'activity/editPage/'+row.productId+'" class="editItem" data-id="'+row.productId+'"> 编辑</a>'
	                    	}
	                    	result += ' <a  href="'+urlPrefix+'productRate/'+row.productId+'" class="productRateItem">评价</a>'
	                    	result += ' <a  href="javascript:void(0);" target="_self" class="copyLink" data-id="'+row.productId+'">链接</a>'
		                    return result;
	                    }
	                } 
	             ],
	             onPageChange: function () {
	            	 $("#seletAll").prop("checked",false);
	             }
	        });
		},
		doDelete : function(that,ids,idCount){
			 $.ajax({
			     type: "POST",
			     url: urlPrefix+ "activity/delete",
			     data:{"ids":ids},
			     dataType:"json",
//			     contentType : "application/x-www-form-urlencoded; charset=utf-8",
			     success: function(result){
					  if (result.code == 'ACK'){
					    $('body').loadingInfo("success", result.message);
						var num =  $('#dataList').bootstrapTable('getOptions').pageNumber;
						var size =  $('#dataList').bootstrapTable('getOptions').pageSize;
						var record =  $('#dataList').bootstrapTable('getOptions').totalRows;
						var total =  $('#dataList').bootstrapTable('getOptions').totalPages
						if(num != 1 && num == total && record%size == idCount){
							num = num-1;
							 $('#dataList').bootstrapTable('refresh', {query: {currentPage:num}});
						}else{
							 $('#dataList').bootstrapTable('refresh', {query: {currentPage:num}});
						}
						$("#seletAll").prop("checked",false);
					 }
			     }
			 });
		},
		
		copyLink : function() {
			var that = this;
			var webHost = $('#product').attr('web-host');
			$('#dataList').on('click', '.copyLink',  function() {
				var productId = $(this).attr('data-id');
				var productUrl = webHost + "/web/compiledtemp/page/product/productDetail.html?id=" + productId;
				// 创建元素用于复制
			    var aux = document.createElement("input");
				// 设置元素内容
			    aux.setAttribute("value", productUrl);
			    // 将元素插入页面进行调用
			    document.body.appendChild(aux);
			    // 复制内容
			    aux.select();
			    // 复制选中的文字到剪贴板
				document.execCommand("Copy", "false", null);
			    // 删除创建元素
			    document.body.removeChild(aux);
				$(window).loadingInfo("success", "已复制到剪贴板！");
			});
		},
		
		valiPrice : function(){
			var lowp = $.trim($('#lowPrice').val());
			var highp = $.trim($('#highPrice').val());
			var lflag = true;
			var hflag = true;
			var reg = new RegExp(this.numReg);
			if(lowp){
				if(!reg.test(lowp)){
					lflag = false;
				}else{
					lflag = true;
				}
			}
			if(highp){
				if(!reg.test(highp)){
					hflag = false;
				}else{
					hflag = true;
				}
			}
			if(lflag&&hflag){
				$('#numVali').hide();
			}else{
				$('#numVali').show();
				
			}
			if(lowp&&highp){
				if(lflag && hflag){
					if(parseInt(highp)<parseInt(lowp)){
						$('#numCom').show();
					}else{
						$('#numCom').hide();
						
					}
				}
			}else{
				$('#numCom').hide();
				
			}
		},
		
	}.init();
	
});