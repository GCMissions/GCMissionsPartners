$(function() {
	var creatShipment = {
			$chooseProductBtn  : $('#chooseProduct'),
			saveStockURL : 'orgStock/setting',
			selectedGoods : {},
		    listUrl    : "product/",
		    relatedGoodsHtml : "<div class='popTreeDialogSection box-body '><div class='row col-md-12'>"
		                    +"<div class='col-sm-5'><label class='control-label'>编号：</label><input type=text class='form-control' id='rProductCode'></div>"
		                    +"<div class='col-sm-5'><label class='control-label'>名称：</label><input type=text class='form-control' id='rProductName'></div>"
		                    +"<div class='col-sm-2'><button type=button id='rRefreshRecord' class='btn btn-primary'>搜索</div>"
		                    +"</div>"
		                    +"<div class='row treeTable'>"
		                        +"<div class='col-sm-12' style='margin-top: 30px;'>"
		                            +"<table class='tree_table' id='relatedGoodsList'>"
		                            +"<thead class='borderRow'>"
		                            +"<th width=50% class='text-center'></th>"
		                            +"<th width=50% class='text-center'>序号</th>"
		                            +"<th width=50% class='text-center'>单瓶商品编号</th>"
		                            +"<th width=50% class='text-center'>单瓶商品名称</th>"
		                            +"<th width=50% class='text-center'>销售价</th>"
		                           
		                            +"</thead>"
		                            +"<tbody>"
		                            +"</tbody>"
		                            +"</table>"
		                        +"</div>" 
		                    +"</div>" 
		                    +"</div>"
		                    ,
		    relatedGoodsTr : "<tr data-id={{goodsId}} data-productName='{{goodsName}}'><td></td><td>{{goodsCode}}</td><td>{{goodsName}}</td><td>{{price}}</td><td>{{productStockNum}}</td><td><input type='text' class='stockNum'></td></tr>",
			init :function(){
				var date = new Date();
				var year = date.getFullYear();
				var month = date.getMonth()+1;
				var day = date.getDate();
				var orgKeyword = $('#chooseOrg').val();
				//this.getSupplier();
				this.$relatedGoodsListTable = $('#relatedGoodsListTable');
				/*var searchSelect = $('#chooseShop').bsSuggest({
				      url: "/matchingOrg/"+orgKeyword +" ", 
				      allowNoKeyword: false, //无输入时不执行过滤请求 
				      getDataMethod: "url", //总是从 URL 获取数据
				      fnAdjustAjaxParam: function(keyword, opts) {
				    	//调整 ajax 请求参数方法，用于更多的请求配置需求。如对请求关键字作进一步处理、修改超时时间等
				    	    console.log('ajax 请求参数调整：', keyword, opts);
				    	    return {
				    	        timeout: 10000,
				    	        data: {
				    	            t: (new Date()).getTime(),
				    	            wd: $('#customKeyword').val() +  keyword
				    	        }
				    	    };
				    	}
				    	  
				});*/
				
				//搜索下拉框
			    $("#chooseOrg").bsSuggest({
			        allowNoKeyword: true,   //是否允许无关键字时请求数据。为 false 则无输入时不执行过滤请求
			        idField: 'orgId',                    //每组数据的哪个字段作为 data-id，优先级高于 indexId 设置（推荐）
			        keyField: 'word', 
			        multiWord: true,         //以分隔符号分割的多关键字支持
			        separator: ",",          //多关键字支持时的分隔符，默认为空格
			        getDataMethod: "url",    //获取数据的方式，总是从 URL 获取
			        //type:"POST",
			        url: urlPrefix + "platformShipment/matchingOrg/?type="+$("#orgCate").val()+"&keyword="+ $('#chooseOrg').val() +"", /*优先从url ajax 请求 json 帮助数据，注意最后一个参数为关键字请求参数*/
			        fnAdjustAjaxParam: function(keyword, opts){
			        	var type=$("#orgCate").val();
			        	return {
			        		 type: 'POST',
			                 url: urlPrefix + "platformShipment/matchingOrg",
			                 dataType: 'json',
			                 //dataType: 'jsonp',
			                 timeout: 5000,
			                 data: {keyword:keyword,type:type}
			            };
			        },
			        //jsonp: 'cb',                        //如果从 url 获取数据，并且需要跨域，则该参数必须设置
			        fnProcessData: function (json) {    // url 获取数据时，对数据的处理，作为 fnGetData 的回调函数
			            var index, len, data = {value: []};
			            if (!json || !json.list || json.list.length === 0) {
			                return false;
			            }

			            len = json.list.length;

			            for (index = 0; index < len; index++) {
			                data.value.push({
			                    "word": json.list[index].orgName,
			                    "orgId":json.list[index].orgId
			                });
			            }
			            //data.defaults = 'baidu';

			            //字符串转化为 js 对象
			            return data;
			        }
			    })
				
				this.bindEvents();
			},
			
			bindEvents : function(){
				var _self = this;
				
				$('#back').on("click", function() {
					window.location.href = urlPrefix + "platformShipment/";
				});
				
				/*$('#chooseOrg').on("click", function() {
					if($('#chooseOrg').val()){
						_self.dialog =  BootstrapDialog.show({
				            title: '重置商品列表',
				            type : BootstrapDialog.TYPE_WARNING,
				            message: "切换商家后，商品列表将清空",
				            draggable: true,
				            size : BootstrapDialog.SIZE_SMALL,
				            buttons: [{
				                label: '确认',
				                cssClass: 'btn-primary ',
				                action: function(dialog) {
				                	dialog.close();
				                	$('#relatedGoodsListTable').find('tbody').empty();
				                }
				            }, {
				                label: '取消',
				                action: function(dialog) {
				                    dialog.close();
				                }
				            }]
				        });
					}
				});*/
				
				$('#createShipment').on("click", function() {
					var orgId = $('#chooseOrg').val();
					if(!orgId){
						$("body").loadingInfo("error", "请选择要发货的商家");
						return false;
					}
					_self.creatShipment();
				});
				
				$('#chooseOrg').on('onSetSelectValue', function (e, keyword) {
					var tableLength = $('#relatedGoodsListTable').find('tbody').find('tr').length;
					var oldId = _self.choosenOrg;
					var oldName = _self.choosenOrgName;
					if(tableLength > 0){
						_self.dialog =  BootstrapDialog.show({
				            title: '重置商品列表',
				            type : BootstrapDialog.TYPE_WARNING,
				            message: "切换商家后，商品列表将清空",
				            draggable: true,
				            size : BootstrapDialog.SIZE_SMALL,
				            buttons: [{
				                label: '确认',
				                cssClass: 'btn-primary ',
				                action: function(dialog) {
				                	_self.choosenOrg = keyword.id;
									_self.choosenOrgName = keyword.key;
				                	dialog.close();
				                	$('#relatedGoodsListTable').find('tbody').empty();
				                	$('#chooseOrg').val(_self.choosenOrgName);
				                }
				            }, {
				                label: '取消',
				                action: function(dialog) {
				                	_self.choosenOrg = oldId;
									_self.choosenOrgName = oldName;
									$('#chooseOrg').val(_self.choosenOrgName);
				                    dialog.close();
				                }
				            }]
				        });
					}else{
						_self.choosenOrg = keyword.id;
						_self.choosenOrgName = keyword.key;
						$('#chooseOrg').val(_self.choosenOrgName);
					}
			        console.log('onSetSelectValue: ', keyword);
			        _self.isSetValue = true;
			    });
				
				$("#orgCate").on('change',function(){
					$('#chooseOrg').val("");
				})
				
				$('#chooseOrg').on('change',function(){
					if(!_self.isSetValue){
						_self.choosenOrg = "";
						_self.choosenOrgName = "";
					}
					_self.isSetValue = false;
					 
				})
				
				//选择商品dialog
				this.$chooseProductBtn.on("click",_(this.addRelatedGoods).bind(this));
				
				},
				
				creatShipment : function(){
					var _self=this;
					var data = {};
					var detailList = [];
					var gogo = true;
					var shipmentValid = true;
					data.orgId = _self.choosenOrg;
					data.type = 0;
					data.shipmentDetails = detailList;
					$('#relatedGoodsListTable .stockNum').each(function(i){
						if($(this).val() <= 0 || !$(this).val())
							shipmentValid = false;
						detailList[i] = {};
						detailList[i].goodsId = $(this).closest('tr')[0].dataset.id;
						detailList[i].goodsName = $(this).closest('tr')[0].dataset.productname;
						detailList[i].shipmentNum = $(this).val();
					});
					if(detailList.length == 0){
						$("body").loadingInfo("error", "请选择要发货的商品");
						return false;
					}
					if(!shipmentValid){
						$("body").loadingInfo("error", "发货数量必须大于0");
						return false;
					}
					$.ajax({
						url : urlPrefix + "platformShipment/save",
						type : 'POST',
						dataType : 'json',
						contentType : 'application/json',
						data : JSON.stringify(data),
						success: function(){
							$('body').loadingInfo({
			    				type : "success", 
			    				text : "成功创建发货单",
			    				callBack : function() {
			    					window.location.href = urlPrefix + "platformShipment/";
			    					//window.location.href=urlPrefix + that.listUrl;
			    				}
			    			});
							/*$('body').loadingInfo('success', '发货成功');
							window.location= '../platformShipment/';*/
						}
					});
				},
				
				addRelatedGoods : function() {
			        var _self = this;
			        var orgName = $('#chooseOrg').val();
			        if(!orgName || !_self.choosenOrg){
			        	$("body").loadingInfo("error", "请通过下拉列表选择要发货的商家");
						return false;
			        }
			        _self.dialog =  BootstrapDialog.show({
			            title: '关联单瓶商品',
			            //type : BootstrapDialog.TYPE_DEFAULT,
			            message: $(this.relatedGoodsHtml),
			            draggable: true,
			            buttons: [{
			                label: '确认',
			                cssClass: 'btn-primary',
			                action: function(dialog) {
			                	_self.insertRelatedGoods();
			                    dialog.close();
			                }
			            }, {
			                label: '取消',
			                action: function(dialog) {
			                    dialog.close();
			                }
			            }],
			            onshown : function() {
			            	_self.initRelatedTable();
			                
			               
			            }
			       });
			       _self.dialog.getModalDialog().css('width', '800px');
			       _self.isSetValue = false;
			    },
			    
			    initRelatedTable : function() {
			        var that = this;
			        this.relatedBootTable = $.GLOBAL.utils.loadBootTable({
			            table : $('#relatedGoodsList'),
			            //removeBtn : $('#removeRecord'),
			            refreshBtn : $('#rRefreshRecord'),
			            idField : "goodsId",
			            pagination : true,
			            pageSize : 10,
			            url: 'platformShipment/select',
			            sidePagination:'server',
			            queryAddParams: function() { 
			                return {
			                	orgId : that.choosenOrg,
			                    goodsName : $('#rProductName').val(),
			                    goodsCode : $('#rProductCode').val(),
			                }
			            },
			            columns: [
			                {
			                   
			                    align: 'center',
			                    checkbox: true,
			                    onClickCell : function(field, value, row, $element) {
			                        that.selectRelatedGoods(row, $element);
			                    }
			                } ,
			                {
			                    formatter:function(value,row,index){  
			                        return index+1; 
			                    }
			                } ,
			                {
			                    field: 'goodsCode'  
			                } ,
			                {
			                    field: 'goodsName'  
			                } ,
			                {
			                    field: 'price'  
			                }
			            ]
			            
			        });
			        this.relatedBootTable.$dataListTable.on("check.bs.table uncheck.bs.table check-all.bs.table uncheck-all.bs.table", _(this.selectRelatedGoods).bind(this));
			        
			        this.relatedBootTable.$dataListTable.on("post-body.bs.table ", _(this.reCheckedRows).bind(this)); 
			        
			         
			    },
			    
			    insertRelatedGoods : function() {
			        var _self = this;
			        _(this.selectedGoods).each(function(value, key) {
			            if(_.indexOf(_self.getCurrentRelatedGoodsIds(), parseInt(key)) == "-1") {
			            	_self.$relatedGoodsListTable.find('tbody').append(_self.relatedGoodsTr.template(value));
			            }
			        });
			        this.initInputMask();
			        this.selectedGoods = {};
			        this.updateRelatedGoodsListSeq();
			        
			    },
			    
			    updateRelatedGoodsListSeq : function() {
			    	this.$relatedGoodsListTable.find('tbody>tr').each(function() {
			    		$(this).find("td:first").text($(this).index() + 1);
			        });
			    },
			    
			    initInputMask : function(){
			    	$('.stockNum').inputmask({
			    		"mask" : "9",
			    		repeat : 6,
			    		"greedy": false
			    	})
			    },
			    
			    getCurrentRelatedGoodsIds : function() {
			        var ids = [];
			        this.$relatedGoodsListTable.find('tbody>tr').each(function() {
			            ids.push(parseInt($(this).data('id')));
			        });
			        return ids;
			    },
			    
			    selectRelatedGoods : function(/*event, row, element*/) {
			        var that = this;
			        if(arguments.length == 3) {
			            var row = arguments[1];
			            var element = arguments[2];
			            if(!_.isUndefined(element) && element.get(0).type == "checkbox") {
			                if(element.is(":checked") ) {
			                    this.selectedGoods[row.goodsId] = row;
			                } else {
			                    delete this.selectedGoods[row.goodsId];
			                }
			            }
			        } else if(arguments.length == 2) { //check-all uncheck-all
			            var rows = arguments[1];
			            _(rows).each(function(row, key) {
			                if(row[0] == false) {
			                    delete that.selectedGoods[row.goodsId];
			                } else {
			                    that.selectedGoods[row.goodsId] = row;
			                }
			            });
			        } else {
			            return false;
			        }
			        //this.selectedGoods = this.$dataListTable.bootstrapTable('getSelections');
			         
			    }, 
			    
			    reCheckedRows : function() {
			        this.relatedBootTable.$dataListTable.bootstrapTable('checkBy', {field:'goodsId', values:_(_(this.selectedGoods).keys()).map(function(str, k) {
			        	return parseInt(str);
			        })});
			    },
		
				/*getSupplier : function() {
					$.ajax({
						url : urlPrefix + "platformShipment/matchingOrg",
						type : "post",
						dataType : "json",
						success : function(allOrg) {
							var data = allOrg.list;
							var opts = "<option value='' disabled selected>请选择商家</option>";
							if (data.length != 0) {
								for (var i = 0; i < data.length; i++) {
									opts += "<option value='"
											+ data[i].orgId + "'>"
											+ data[i].orgName
											+ "</option>";
								}
							}
							$("#chooseShop").html(opts);
							$("#chooseShop").selectpicker('refresh');
						}

					});
				},*/	
			
	};
	
	creatShipment.init();
});