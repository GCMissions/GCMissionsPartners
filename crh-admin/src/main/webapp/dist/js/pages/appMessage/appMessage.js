$(function(){
	var appMassage={
	$dataList : $('#dataList'),
	init : function() {
		this.bindEvents();
		this.bootTable = $.GLOBAL.utils.loadBootTable({
			table : this.$dataList,
//		  	idField : "appMessageId",
		  	pagination : true,
		  	url: 'appMessage/list',
		  	sidePagination:'server',
		  	queryParamsType: "limit",
		  	queryAddParams: function() {
		  	return {
		  		title: ""
		  	}
		},
 	    columns: [
 	             {
						width : 50,
						align: 'center',
						formatter:function(value,row,index){  
							return index+1;
						}
	              },
 	              {
 	            	  field: 'title',
 	              },
 	              {
 	            	  field: 'createDate',
 	              },
 	              {
 	            	  //title: '操作',
 	            	  field: 'messageId',
 	            	  align: 'center',
 	            	  checkbox: false,
 	            	  formatter:function(value,row,index){         	               
 	            		  return ' <a  title="查看" href="detail/'+row.messageId+'" class="viewItem" data-id="'+row.messageId+'"> <i class="fa fa-eye "  style="font-size:20px"></i></a>';
 	            	  } 
 	              }
            ]
        });
	},
	bindEvents : function(){
		var that=this;
		$("#addItem").on("click",function(){
			
		});
		$("#search").on("click", function() {
			that.bootTable.options.queryAddParams = function(){
				var title=$("#appTitle").val();
				var result= {
						title:title,
				}
				return result;
			};
			that.bootTable.refresh();
		});
	},
	};
	
	appMassage.init();
});