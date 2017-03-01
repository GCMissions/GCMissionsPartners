$(function(){
	var barcode = {
			$dataList : $('#dataList'),
	        $searchItem : $('#searchBtn'),
	        $textInput:$('#text-input'),
	        $importExcelBtn:$('#importExcel'),
	        $fileInput:$('#excel'),
	        dialog : void 0,
	        bootTable : void 0,
	        
	        init : function() {
	        	this.bindEvents();
				this.bootTable = $.GLOBAL.utils.loadBootTable({
					table : this.$dataList,
					refreshBtn : $('#refreshRecord'),
					url: 'barcodeCycle/list',
					sidePagination:'server',
					pagination : true,
					queryParamsType: "limit",
					queryAddParams: function() {
						return {
							prefixCode: ""
						}
					},
					columns: [
						{
							width: 50,
							align: 'center',
							formatter:function(value,row,index){  
	                        	return index+1; 
	                        }
						},
						{
							field: 'cycleId',
							align: 'center'
						} ,
						{
							field: 'source',
							align: 'center',
							formatter:function(value,row,index){  
								var handleField;
								if(row.source == "0"){
									handleField = 'PC';
								}else if(row.source == "1"){
									handleField = 'MPOS';
								}
								else{
									handleField = '未知';
								}
								return handleField;
							} 
						} ,
						{
							field: 'status',
							align: 'center',
						} ,
						{
							field: 'fromName',
							align: 'center'
						},
						{
							field: 'toName',
							align: 'center'
						},
						{
							field: 'orderId',
							align: 'center',
							formatter:function(value,row,index){  
								var handleField;
								var oldValue=value;
								handleField=oldValue.replace(/,/g,'<br/>')
								return handleField;
							} 
						},
						{
							field: 'createDate',
							align: 'center'
						},
						{
							field: 'createUserName',
							align: 'center'
						},
						{
							field: 'packCode',
							align: 'center'
						},
						{
							field: 'content',
							align: 'center'
						}
						
					 ]
				});
			},
			bindEvents : function() {
				var that = this;
				that.$searchItem.on("click",function() {
					var searchStr=that.$textInput.val();
					if(searchStr.indexOf('http')>=0){
						//如果触发回车。现在需求是需要删除乱码以及最后一位等号
						searchStr=searchStr.substring(0,searchStr.indexOf('c=')+18);
						that.$textInput.val(searchStr);
						console.log('searchStr'+searchStr);
					}
					that.bootTable.options.queryAddParams = function(){
						return {
							prefixCode: $("input[name='prefixCode']").val(),
						}
					};
					that.bootTable.refresh();
				});
				that.$textInput.on('keydown',function(e) {
					if(e.which==13){
						
						that.$searchItem.click();
					}
				});
				that.$importExcelBtn.on("click",function(){
					that.$fileInput.click();
				});
				that.$fileInput.on("change",upload);
				function upload(){
					console.log(that.$fileInput.val())
					console.log('id is '+that.$fileInput.attr('id'))
					
					$('body').loadingInfo("show", '正在导入,请耐心等待',200000);
					$.ajaxFileUpload({
		                url:"upload",            //需要链接到服务器地址  
		                secureuri:false,  
		    			dataType : 'json',
		    			global : false,
		                fileElementId:'excel',                    //文件选择框的id属性
		                type:"post",
		                success: function(data, status){     
		                	if (data.code == "ACK") {
		    					$('body').loadingInfo("success", data.message,3500);
		    				}
		                	else{
		                		$('body').loadingInfo("error", data.message,3500);
		                	}
		                		that.$fileInput=$('#excel')
		                		that.$fileInput.on("change",upload);
		                },error: function (data, status, e){  
		                	$('body').loadingInfo("error", "上传失败",3500);
		                	that.$fileInput=$('#excel')
	                		that.$fileInput.on("change",upload);
		                }  
		            });  
				}
				
			}
	};
	
	barcode.init();
});