$(function() {

	var productApp = {
		bootTable : void 0,
		init : function(){
			this.$dataList = $('#dataList');
			this.initTable(); 
	        
	        this.bindEvents();
	        
	        this.copyLink();
		},
		bindEvents : function(){
			var that = this;
			
			$("#lowPrice").on('change', function(){
				that.valiPrice(this);
			});
			$("#highPrice").on('change', function(){
				that.valiPrice(this);
			});
					
			$("#selectAll").on('click', function(){
				$('input[name=product]:enabled').prop("checked", this.checked);
				$('input[name=product]').each(function(){
					
					$(this).on('click', function(){
						 if (!this.checked){
							 $("#selectAll").prop("checked", false);
						 }
						 var allChecked = true;
						 $('input[name=product]').each(function(){
							 if (!this.checked){
								 allChecked = false;
								 return;
							 }
						 });
						 $("#selectAll").prop("checked", allChecked);
					});
					
				});
			});
			
			$("#search").on('click', function(){
				$("#selectAll").prop("checked", false);
			});

			$("#deleteItem").on("click", function(){
				 var ids = new Array();
				 $("input[name=product]:checked").each(function() {
					ids.push($(this).val());
				 });
				 if (ids.length == 0) {
					 that.dialog =  BootstrapDialog.show({
				            title: '请选择要删除的商品',
				            type : BootstrapDialog.TYPE_WARNING,
				            message: message('已选择0条商品'),
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
				 } else {
					 that.dialog =  BootstrapDialog.show({
					 title: '删除商品',
					 type : BootstrapDialog.TYPE_WARNING,
					 message: "您确定要删除吗？删除之后会自动将关联的广告位删除，请谨慎操作。",
					 draggable: true,
					 size : BootstrapDialog.SIZE_SMALL,
					 buttons: [{
						 label: '确认删除',
						 cssClass: 'btn-primary ',
						 action: function(dialog) {
							 dialog.close();
							 that.doDelete(ids);
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
			this.bootTable = $.GLOBAL.utils.loadBootTable({
	            table : this.$dataList,
	            refreshBtn : $('#search'), 
	            pagination : true,
	            pageSize : 20,
	            url: 'coolbag/product/search',
	            sidePagination:'server',
	            queryAddParams: function() {
	                var queryObj =  {
	                    pname:  $('#productName').val(),
	                    pcode: $.trim($('#productCode').val()),
	                    actTag: $("select[name='actTags']").val(),
	                    lowPrice    : $.trim($('#lowPrice').val()),       
	                    highPrice  : $.trim($('#highPrice').val()), 
	                } ;
	                
	                return queryObj;
	            },
	            initSearchForm : true, 
			    fillSearchData : function(data) {
			    	 $("#productName").val(data.pname);
			    	 $("#productCode").val(data.pcode);
			    	 $("select[name='actTags']").val(data.actTag);
			    	 
			    	 $("#lowPrice").val(data.lowPrice);
			    	 $("#highPrice").val(data.highPrice);
			    	
			    },
			    
	            columns: [
                    {
                    	field: 'id',
                    	align: 'center',
                    	formatter:function(value,row,index){  
                    		if ("2" == row.saleStatus) {
                    			return '<input type="checkbox" name="product" value="'+ row.id +'" disabled>';
                    		}
                    		return '<input type="checkbox" name="product" value="'+ row.id +'">';
                    	}
                    } ,
					{
	                    field: 'pcode',
	                } ,
	                {
	                    field: 'pname',
	                    width: 300,
	                    formatter: function(value,row,index){
	                    	var str = value;
	                    	var re = new RegExp(" ","g");
							var newstr = str.replace(re, "&nbsp;");
	                    	return '<span>'+ newstr +'</span>';
	                    }

	                } ,
	                {
	                    field: 'priceRange',
	                } ,
	                {
	                    field: 'createDate',
	                    width:300
	                } ,
	                {
	                    field: 'id',
	                    align: 'center',
	                    width:90,
	                    formatter: function(value, row, index){
	                    	var prefix = "<a href='" + urlPrefix + "coolbag/product/page?oper=";
	                    	var result = prefix + "view&productId=" + row.id + "' class='viewItem' data-id='" + row.id +"'> 查看</a>"
	                    	if (row.saleStatus != "2") {
	                    		result += prefix + "edit&productId=" + row.id + "' class='editItem' data-id='" + row.id +"'> 编辑</a>"
	                    	}
	                    	result += ' <a  href="javascript:void(0);" target="_self" class="copyLink" data-id="'+ row.id +'"> 链接</a>';
	                    	
		                    return result;
	                    }
	                } 
	             ],
	             onPageChange: function () {
	            	 $("#selectAll").prop("checked",false);
	             }
	        });
		},
		
		copyLink : function() {
			var that = this;
			var webHost = $('#product').attr('web-host');
			$('#dataList').on('click', '.copyLink',  function() {
				var productId = $(this).attr('data-id');
				var productUrl = webHost + "/kd-web/compiledtemp/shop/proDetail.html?proId=" + productId;
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
		
		doDelete : function(ids){
			 $.ajax({
			     type: "POST",
			     url: urlPrefix+ "coolbag/product/delete",
			     data: {
			    	 "productIds":ids
			     },
			     dataType: "json",
			     success: function(result){
					  if (result.code == 'ACK'){
					  	 $('body').loadingInfo("success", result.message);
						 var num =  $('#dataList').bootstrapTable('getOptions').pageNumber,
						 	 size =  $('#dataList').bootstrapTable('getOptions').pageSize,
						     record =  $('#dataList').bootstrapTable('getOptions').totalRows,
						     total =  $('#dataList').bootstrapTable('getOptions').totalPages;
						 if (num != 1 && num == total && record % size == idCount) {
							 num = num-1;
							 $('#dataList').bootstrapTable('refresh', {query: {currentPage:num}});
						 }else{
							 $('#dataList').bootstrapTable('refresh', {query: {currentPage:num}});
						 }
						 $("#selectAll").prop("checked", false);
					  }
			     }
			 });
		},
		
		valiPrice : function(elem){
			var price = $.trim($(elem).val()), numReg = /^([1-9]\d{0,}|0)([.]\d{0,2})?$/;
			
			if (price && !numReg.test(price)) {
				$('#msg_num_valid').text("请输入有效的数字").show();
				return;
			} else {
				$('#msg_num_valid').hide();
			}
			
			var lowp = $.trim($('#lowPrice').val()), highp = $.trim($('#highPrice').val());
			if (lowp && highp && parseFloat(highp) < parseFloat(lowp)) {
				$('#msg_num_valid').text("请输入正确的价格范围").show();
				return;
			}
			
			$('#msg_num_valid').hide();
		},
		
	}.init();
})