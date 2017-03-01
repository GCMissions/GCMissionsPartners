$(function() {
	var details = {
		firstInit: true,
		init : function(){
			if(this.firstInit){
				this.initTable();
			}
			
			this.firstInit = false;
		},
		initTable : function(){
			this.table = $.GLOBAL.utils.loadBootTable({
				table : $('#detailList'),
			    url: 'order/findDetails',
			    queryParamsType: "limit",
			    queryAddParams: function() {
			    	return {"orderId":orderId};
			    },
			    columns: [
					{
						width:50,
						title: '序号',
					    align: 'center',
					    formatter:function(value,row,index){  
					    	return index+1; 
					    }
					} , {
			        	title: '商品编号',
			            field: 'proudctCode'
			        } , {
			        	title: '商品名称',
			            field: 'proudctName'
			        } , {
			        	title: '服务商名称',
			        	field: 'orgName'
			        } , {
			        	title: '商品价格',
			        	field: 'price'
			        } , {
			        	title: '活动时间',
			        	field: 'actDate'
			        } , {
			        	title: '规格',
			        	field: 'specInfo',
			        	formatter : function(value,row,index){
			        		var info = eval(value.replace(/&#34;/g,"'"));
			        		var result = "";
			        		if(info){
				        		$.each(info,function(i,v){
				        			result += "<span class='subSpec'>"+ v.subSpec +"</span>";
				        		});
			        		}
			        		return result;
			        	}
			        	
			        } , {
			        	title: '必填信息',
			        	field: 'personalInfo',
			        	formatter : function(value,row,index){
			        		var info = eval(value.replace(/&#34;/g,"\""));
			        		var result = "";
			        		if(info){
				        		$.each(info,function(i,v){
				        			result += "<p class='personInfo'>"+ v.key + ":" + v.value +"</span>";
				        		});
			        		}
			        		return result;
			        	}
			        }, {
			        	title: '用户备注',
			        	field: 'remark'
			        }
			     ]
			});
		}
	}.init();
	
	var coupons = {
		firstInit: true,
		init : function(){
			if(this.firstInit){
				this.initTable();
				this.bindEvent();
			}
			
			this.firstInit = false;
		},
		bindEvent : function(){
			var _this = this;
			$("body").on("click", ".couponDel", function(){
				_this.delCoupon($(this).data("id"));
			});
		},
		delCoupon : function(id){
			var _this = this;
			$.ajax({
				type: "GET",
				url: urlPrefix+'/order/delCoup/'+id,
				dataType: 'json'
			}).done(function(response){
				if(response.code == "ACK"){
					_this.table.refresh();
				}
			});
		},
		initTable : function(){
			this.table = $.GLOBAL.utils.loadBootTable({
				table : $('#couponList'),
			    url: 'order/findCoupons',
			    queryParamsType: "limit",
			    queryAddParams: function() {
			    	return {"orderId":orderId};
			    },
			    onLoadSuccess:function(data){
			    	if(data.length == 0){
			    		$('#couponList').find(".no-records-found").find("td").html("该笔订单没有获得任何优惠券");
			    	}
			    },
			    columns: [
					{
						width:50,
						title: '序号',
					    align: 'center',
					    formatter:function(value,row,index){  
					    	return index+1; 
					    }
					} , {
			        	title: '优惠券名称',
			            field: 'couponName'
			        } , {
			        	title: '优惠券金额（元）',
			            field: 'value'
			        } , {
			        	title: '有效期',
			        	field: 'dateTime',
			        	formatter : function(value,row,index){
			        		if(row.effectDate && row.invalidDate){
			        			return row.effectDate + " 至 " + row.invalidDate;
			        		}
			        		return "-";
			        	}
			        } , {
			        	title: '是否使用',
			        	field: 'statusStr'
			        } , {
			        	title: '使用品类',
			        	field: 'cateNames',
			        	formatter : function(value,row,index){
			        		if($.trim(value)){
			        			return value;
			        		} else {
			        			return "全品类";
			        		}
			        	}
					} , {
			        	title: '操作',
			        	field: 'id',
			            align: 'center',
			            formatter:function(value,row,index){ 
			            	if(row.status == 0 && status == "已退款"){
			            		return  ' <a  title="删除" class="couponDel" data-id="'+row.couponId+'" > <i class="fa fa-trash-o "  style="font-size:20px"></i></a>';
			            	}
			            } 
			        }
				]
			});
		}
	}.init();
	
});