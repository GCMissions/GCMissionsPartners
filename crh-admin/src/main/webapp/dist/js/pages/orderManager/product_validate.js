$(function(){
	var validate = {
		initEvents : function(){
			$("#fCategory").change(function(){
				var firstCate = $(this).val();
				if (firstCate == "" || firstCate == null) {
					$("#sCategory").attr("disabled", true);
					$("#sCategory").selectpicker('val', '');
					$("#sCategory").selectpicker('refresh');
				} else {
					getSecondCate(firstCate);
				}
			});
			
			var getSecondCate = function(firstCate) {
			    $.ajax({
			    	type         : 'get',
					url          : urlPrefix + "activity/getSubCategoryByParent/" + firstCate,
					dataType     : 'json',
					contentType  : 'application/json',
					success : function(msg){
						var data = msg.data;
						$("#sCategory").html('');
						if(msg.code == 'ACK'){
							if(data && data.length != 0){
								$("#sCategory").attr('disabled',false);
								$("#sCategory").append('<option value="">不限</option>');
							}else{
								$("#sCategory").attr('disabled',true);
							}
							$.each(data,function(i,v){
								$("#sCategory").append('<option value="'+ v.cateId+'">'+ v.cateName +'</option>');
							});
							$("#sCategory").selectpicker('val', '');
							$("#sCategory").selectpicker('refresh');
						}
					}
			    })
			};
			
			var table = $.GLOBAL.utils.loadBootTable({
				table : $('#dataList'),
				sidePagination : 'server',
				refreshBtn : $('#refreshRecord'),
				pagination : true,
				url : "validate/list",
				queryParamsType : "limit",
				queryAddParams : function(){
					var firstCategory = $("#fCategory").val();
					var secondCategory = $("#sCategory").val();
					var productType = $(".pType").val();
					var productName = $.trim($(".pname-input").val());
					var minPrice = $(".min-price").val();
					var maxPrice = $(".max-price").val();
					return {
						firstCategory : firstCategory,
						secondCategory : secondCategory,
						productType : productType,
						productName : productName,
						minPrice : minPrice,
						maxPrice : maxPrice
					}
				},
				columns : [{
					title : '商品编号',
                    field: 'productCode',
                    width : '10%',
                } ,
                {
                	title : '商品品类',
                    field: 'cateName',
                    width : '10%',
                    //sortable: true
                } ,
                {
                	title : '服务商',
                    field: 'orgName',
                    width : '10%',
                    //sortable: true
                } ,
//                {
//                	title : '商品类型',
//                	field : 'productType',
//                	width : '10%',
//                },
                {
                	title : '商品名称',
                    field: 'productName',
                    width : '10%',
                    //sortable: true
                } ,
                {
                	title : '价格（元）',
                    field: 'price',
                    width : '10%',
                    //sortable: true
                } ,
                {
                	title : '创建时间',
                    field: 'createDate',
                    width : '10%',
                    //sortable: true
                },
                {
                	title : '操作',
                	field : 'isCaptcha',
                	width : '10%',
                	formatter : function(value,row,index){
                		var oprate = value.split(";");
                		var productId = oprate[0];
                		var isCaptcha = oprate[1];
                		if (isCaptcha == "0") {
                			return "<a class='validate' data-id='" + productId + "'>验证</a>";
                		} else {
                			return "-";
                		}
                	}
                }]
			});
			
			$("#dataList").on('click','.validate', function(){
				var productId = $(this).data("id");
				doValidate(productId);
			});
			
			var doValidate = function(productId){
				window.location.href = "doValidate/" + productId;
			};
			
			$(".condition-input").on('change','.min-price',function(){
				valiPrice();
			});
			
			$(".condition-input").on('change','.max-price',function(){
				valiPrice();
			});
			
			var valiPrice = function(){
				var numReg = '^[-+]?[0-9]+(\.[0-9]+)?$';
				var lowp = $.trim($('.min-price').val());
				var highp = $.trim($('.max-price').val());
				var lflag = true;
				var hflag = true;
				var reg = new RegExp(numReg);
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
						if(highp * 1 < lowp * 1){
							$('#numCom').show();
						}else{
							$('#numCom').hide();
							
						}
					}
				}else{
					$('#numCom').hide();
					
				}
			};
		},
		init:function(){
			var _self = this;
			_self.initEvents();
		}	
	}.init();
});