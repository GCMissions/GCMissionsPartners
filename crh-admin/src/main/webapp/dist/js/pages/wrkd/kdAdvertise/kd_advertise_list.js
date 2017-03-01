$(function(){
	var kdAdvertise = {
			initEvents:function(){
				var globalIds = [];	
				var table = $.GLOBAL.utils.loadBootTable({
					table : $('#dataList'),
					refreshBtn : $('#search'),
				    sidePagination:'server',
				    pagination : true,
				    url : "kdAdvertise/list",
				    queryParamsType: "limit",
				    queryAddParams: function() {
				    	var advertiseName = $.trim($("#advertiseName").val());
				    	var advertiseType = $("#advertiseType").val();
				    	var result= {
				    			advertiseName:advertiseName,
				    			type: advertiseType
						}
				    	return result;
				    },
				    columns: [{
				    	field : 'advertiseName',
				    	align : 'center',
				    	title : '广告名称',
				    	width : '25%'
				    }, {
				    	field : 'skipType',
				    	align : 'center',
				    	title : '跳转类型',
				    	width : '17%'
				    }, {
				    	field : 'type',
				    	align : 'center',
				    	title : '所属模式',
				    	width : '18%'
				    }, {
				    	field : 'skipUrl',
				    	align : 'center',
				    	title : '跳转链接',
				    	width : '30%'
				    }, {
				    	field: 'advertiseId',
						align: 'center',
						title: '操作',
						width: '10%',
						formatter: function(value,row,index){
							var result = "";
							if (row.type == "模式一") {
								result += ' <a href="'+urlPrefix+'kdAdvertise/advertiseList/'+ value +'/1"> 查看</a>';
							} else {
								result += ' <a href="'+urlPrefix+'kdAdvertise/advertiseList/'+ value +'/2"> 查看</a>';
							}
							return result;
						} 
				    }]
				});
			},
			
			init:function(){
				var _self = this;
				_self.initEvents();
			}
	}.init();
});