var productFloorApp = {
    //nopagination 
    floorsConfig : {
        "1" : {
            "name"  : "热销商品",
            "ads" : {  //adposition
				2 : {  } 
			},
			"adsAmount" : 1,
            "maxAmount" : 4,
			"indexMaxSeq" : 4 //首页显示时的最大序号
        },
        "2" : { 
            "name"  : "核心产品",   
            "ads" : {
                1 : {  },
                6 : {  }
            }, 
            "adsAmount" : 2,
            "maxAmount" : -1,
			"indexMaxSeq" : 10
        },
        "3" : {
            "name"  : "高端产品",
            "ads" : {
                1: {  }, 
                6: {  }
            },  
			"adsAmount" : 2,
            "maxAmount" : -1,
			"indexMaxSeq" : 10
        },
        "4" : {
            "name"  : "区域产品",
            "ads" : {
                1: {  }, 
                6: {  }
            } , 
			"adsAmount" : 2,
            "maxAmount" : -1,
			"indexMaxSeq" : 10
        },
        "5" : {
            "name"  : "当地特供",
            "ads" : {
                1: {  }, 
                6: {  }
            } , 
			"adsAmount" : 2,
            "maxAmount" : -1,
			"indexMaxSeq" : 10
        },
    },
    plsChooseCity   : "请选择您要设置的城市! ", 
    floorDataUrl    : "productPfloor/floorData", //other paramters regionId floorType(1,2,3,4) 
    productsListUrl : "productPfloor/searchProduct",
    loadOpenCityUrl : "region/findOpenCity",
	saveFloorUrl    : "productPfloor/add",
    defaultImg   : uiBase + "/img/default_goods_image_240.gif",
	removeBtn : ".remove-btn",
    openedCityId : 0, //当前已经打开的城市 防止当前城市 重复打开
    //@TODO support self-defined position 
    addImageHint : "(点击图标，选择要上传的图片，若不上传，默认选择{0}商品的封面主图,图片尺寸建议: 230px*300px)",  //1号位/6号位
    addImageGroup:"(点击图标，选择要上传的图片，图片尺寸建议: 465px*740px)",
    floorLoaded  : true, //楼层时否加载 完成
    $floorGoodsListTable : void 0,  
    countyRegionId  : 100000,
	selectedGoods : {},
    productsList : [],//store the goodsList for current floor
	haveChanged : false,
    relatedGoodsHtml : "<div class='popTreeDialogSection box-body form-horizontal'>"
    				+"<div class='row'>"
    					+"<div class='col-md-12'>"
	    				  	  +"<div class=' form-group col-md-8'>"
	    				  	  	 +"<label class='control-label'>商品条码：</label><input type=text class='form-control ' id='rProductCode'>"
		                      +"</div>"
	                      +"</div>"
                      +"</div>"
                      +"<div class='row'>"
  							+"<div class='col-md-12'>"
  							
                      +"<div class='form-group col-md-8'>"
                      +"<label class='control-label'>商品名称：</label><input type=text class='form-control ' id='rProductName'><button type=button id='rRefreshRecord' class='btn btn-primary pull-right'>开始搜索</button>"
                    +"</div>"
                    
                    +"</div>"
                    +"</div>"
                   
                    +"<div class='row treeTable'>"
                        +"<div class='col-sm-12' style='max-height:500px;overflow:scroll'>"
                            +"<table class='tree_table' id='relatedGoodsList'>"
                            +"<thead class='borderRow'>"
                            +"<th width=50% class='text-center'></th>"
                            +"<th width=50% class='text-center'>序号</th>"
                            +"<th width=50% class='text-center'>商品条码</th>"
                            +"<th width=50% class='text-center'>商品名称</th>"
                            +"<th width=50% class='text-center'>分类</th>"
                           
                            +"<th width=50% class='text-center'>品牌</th>"
                           
                            +"</thead>"
                            +"<tbody>"
                            +"</tbody>"
                            +"</table>"
                        +"</div>" 
                    +"</div>"  
                    +"</div>"
                    ,
    relatedGoodsTr :      '<tr data-id="{{productId}}"><td></td><td>{{productCode}}</td><td>{{productName}}</td><td>{{brandName}}</td><td>{{cateName}}</td><td>{{shiefText}}</td><td><a  title="删除" href="#" class="removeItem" data-id="{{productId}}"> <i class="fa fa-remove "  style="font-size:20px"></i></a></td></tr>',
	init : function() {
		this.$areaTable = $('.tree_table');
		this.$cityFloorContainer = $('#cityFloorContainer').html(this.plsChooseCity);
        this.loadCitys();
        this.initEventHandle();
	},
    initEventHandle : function() {
        var  that    = this;
        
        this.$areaTable.on("click", "tr button", _(this.openCity).bind(this));
        
         
        this.$cityFloorContainer.on("click", ".floorGoodsList a.removeItem", _(this.removeGoods).bind(this));
        
        this.$cityFloorContainer.on('change', '.upload-btn input[type="file"]', function(){
            var id = $(this).attr('id'); //
            that.ajaxFileUpload(id);
        });
        this.$cityFloorContainer.on('click', ".nav-tabs li a", _(this.switchFloor).bind(this));
        this.$cityFloorContainer.on('click', "a.removeGoodsItem", _(this.removeGoods).bind(this));
        this.$cityFloorContainer.on('click', "button.addGoods", _(this.addGoods).bind(this));
		this.$cityFloorContainer.on('click', this.removeBtn, _(this.removePic).bind(this));
		this.$cityFloorContainer.on('click', "button.saveFloorData", _(this.save).bind(this));
    },
    loadCitys : function() {
        //load city data
        var that = this;
        $.ajax({
        	type         : "GET",
            url          : urlPrefix + this.loadOpenCityUrl,
            dataType     : 'json',
			contentType  : 'application/json'
        })
        .done(function(result) {
            if(result.code == "ACK") {
                //build area 
                /*
                <tr data-tt-id="1" ><td colspan="2">浙江省</td></tr>
				<tr data-tt-id="11" data-tt-parent-id="1"  data-city-name="杭州市"><td><span ></span>杭州市</td><td >
                <button type="button" class="btn btn-default">楼层推荐Mock</button></td></tr>
							 	
                */
                var data   = result.data;
                _(data).each(function(value, index) {
                    if(value.regionId == that.countyRegionId) {
                        //countryRecord = value;
                        return;
                    }
                    result  += "<tr  data-tt-id='"+value.regionId+"' data-city-name='"+value.regionName+"'><td colspan='2'>"+value.regionName+"</td></tr>";
                           
                    
                    if(!_.isUndefined(value.childrenList) && value.childrenList.length>0) {
                        _(value.childrenList).each(function(item, key) {
                           
                            result += "<tr data-tt-id='"+item.regionId+"' data-tt-parent-id='"+item.parentId+"' data-city-name='"+item.regionName+"'><td>"+item.regionName+"</td>"
                                   +'<td><button type="button" class="btn btn-default">楼层推荐</button></td>'
                                   +"</tr>";
                        });
                        
                    }  
                });
                result += '<tr data-tt-id="'+that.countyRegionId+'" data-city-name="无配送网点城市" ><td >无配送网点城市</td><td><button type="button" class="btn btn-default">楼层推荐</button></td></tr>';
                that.$areaTable.find('tbody').html(result);
                that.$areaTable.treetable({expandable : true,indent: -10});
                that.$areaTable.treetable("expandAll");
            }
        })
        .always(function() {
            
        })
        ;
        //render html
       
		
    },
    openCity : function(e) {
        var $target = $(e.target);
            cityId = $target.closest('tr').data('tt-id'),
            cityName = $target.closest('tr').data('city-name'),
            that    = this;
        this.$currentOpenedCityButton = $target;
        if(this.openedCityId == cityId) return false;
        function doSwitch() {
			that.$areaTable.find("tr button").prop('disabled', false).removeClass("btn-primary").text('楼层推荐');
			that.$currentOpenedCityButton.prop('disabled', true).addClass("btn-primary").text("加载中...");
			that.openCityFloor(cityId, cityName, 1);
		}
        
		if(this.haveChanged) {
			BootstrapDialog.show({ 
				title: "提醒",
				type : BootstrapDialog.TYPE_WARNING,
				message : '{0} 第{1}的数据已更改,你确定不保存吗?'.format(that.openedCityName, that.floorsConfig[that.openedFloor].name),
				draggable: true,
				size : BootstrapDialog.SIZE_SMALL,
				buttons: [{
					label: '保存更改',
					cssClass: 'btn-primary',
					action: function(dialog) {
						
						//$(arguments[1].target).text("正在保存...");
						dialog.close();
						$.when(that.save(dialog.$modalContent)).then(function() {
							//dialog.close();
							doSwitch();
						});
					}
				},
				{
					label: '放弃更改',
					cssClass: 'btn-primary ',
					action: function(dialog) {
						dialog.close();
						doSwitch();
					}
				},
				
				{
					label: '取消',
					cssClass: 'btn-default ',
					action: function(dialog) {
						dialog.close();
						
					}
				}]
			});
		} else {
			doSwitch();
		}
      
       
            
    },
    openCityFloor : function(cityId, cityName, floor) {
        var that    = this;
        this.floorLoaded = false;
        $.ajax(
        { 
            type         : 'POST',
            url          : urlPrefix+this.floorDataUrl,
            dataType     : 'json',
            contentType  : 'application/json',
            data         : JSON.stringify({regionId : cityId, floorType: floor})
		})
		.done(function(result) {
			if(result.code == "ACK") {
                that.openedCityId = cityId;
                that.openedCityName = cityName;
                that.openedFloor = floor;
                that.productsList  = result.data.list || [];
                $('#cityFloorContainer').html(template('floorTpl', {"cityName" : that.openedCityName}));
                //当前楼层 不可再次打开
				that.$currentOpenedCityButton.text('楼层推荐');
				
                that.haveChanged = false;
				//change nav-tabs active
				that.$cityFloorContainer.find('.nav-tabs li').eq(floor-1).addClass('active');
				
				
				that.renderAds(result.data.list, floor);
                that.rendImageHint();
                that.renderGoodsTable(cityId, floor, result.data.list);
                
			}
		})
        .always(function() {
            that.floorLoaded = true;
        });
    },
    
    /**
     * {
        1 => {
         position :
         imageUrl : 
        },
        6 => {
            position :
            imageUrl : 
        }
     }
    */
    renderAds : function(ads, floor) {
        var that  = this,
            adInfo,
            ads = ads || [],
            adsListUl =  $('#adPositions').find('.goodspic-list').find('ul').empty();
        _(that.floorsConfig[floor]['ads']).each(function(adConfig, position) {
            var adInfo = $.extend(true, {showImageUrl : that.defaultImg, position: position, viewImageUrl : "javascript:;"}, adConfig);
            if(!_.isUndefined(ads[position-1])) {
                if(!_.isEmpty(ads[position-1]['image'])) {
                    adInfo.imageUrl = ads[position-1]['image'];
                    adInfo.viewImageUrl = fileUrlPrefix + ads[position-1]['image'];
                    adInfo.showImageUrl = adInfo.viewImageUrl;
                }
                adInfo = $.extend( adInfo, ads[position]);
            } 
            adsListUl.append(template('picItemTpl', adInfo));
        });
      
       
    },
    rendImageHint :function() {
        var that  = this,adPositionText = "",
            $addImageHint = $('#cityFloorContainer').find('.addImageHint');
        if(that.floorsConfig[this.openedFloor].adsAmount == 1 ) {
//            adPositionText = "1号位";
        	$addImageHint.html(that.addImageGroup);
        } else if(that.floorsConfig[this.openedFloor].adsAmount == 2 ) {
            adPositionText = "1号位/6号位";
        } else {
            adPositionText = "";
        }
        adPositionText && $addImageHint.html(that.addImageHint.format(adPositionText));
    },
    renderGoodsTable : function(cityId, floor, products) {
        var that  = this,
            result = "",
            pagination = that.floorsConfig[floor].maxAmount == -1 ? true : false;
            /*
            <th>商品条码</th>
            <th>商品名称</th>
            <th>品牌</th>
            <th>类别</th>
            <th>操作</th>
            */ 
        _(products).each(function(product, key) {
			product.shiefText = "未上架";
			if(product.shiefStatus == '1') {
				product.shiefText = "已上架";
			}
            result += that.relatedGoodsTr.template(product);
        });   
		var $listTbody = this.getGoodsListTable().find('tbody');
        $listTbody.html(result).sortable({connectWith:".connectList"}).disableSelection();
        $listTbody.on('sortstop', _(function() { 
			this.updateGoodsListSeq();
			this.haveChanged = true;
		}).bind(this));
        this.updateGoodsListSeq();    
    }, 
   
    switchFloor : function(e) {
        var $target   = $(e.target),
        	$parent   = $target.parent(),
            floorType = $parent.index() + 1, 
            that      = this;
            
         
        if(this.floorLoaded == true) {   
			function doSwitch() {
				$parent.find('li').removeClass('active');
				$parent.addClass('active');
				that.openCityFloor(that.openedCityId, that.openedCityName, floorType);  
			}
			if(this.haveChanged) {
				BootstrapDialog.show({
					title: "提醒",
					type : BootstrapDialog.TYPE_WARNING,
					message : '{0} 的数据已更改,你确定不保存吗?'.format(that.floorsConfig[that.openedFloor].name),
					draggable: true,
					size : BootstrapDialog.SIZE_SMALL,
					buttons: [{
						label: '保存更改',
						cssClass: 'btn-primary',
						action: function(dialog) {
							var $dialogBtn = $(arguments[1].target);
							//$dialogBtn.text("正在保存...");
							dialog.close();
							$.when(that.save(dialog.$modalContent)).then(function() {
								//dialog.close();
								doSwitch();
							}).fail(function() {
								console.log(arguments);
							});
						}
					},
					{
						label: '放弃更改',
						cssClass: 'btn-primary ',
						action: function(dialog) {
							dialog.close();
							doSwitch();
						}
					},
					
					{
						label: '取消',
						cssClass: 'btn-default ',
						action: function(dialog) {
							dialog.close();
							
						}
					}]
				});
			} else {
				doSwitch();
			}
        	  
        }
    },
    
    //image upload
    // 图片上传ajax
    ajaxFileUpload:   function (id, o) {
        var that = this,
        imgUploadUrl;
        $('#img_' + id + '').attr('src', uiBase + "/img/loading.gif");
        if(id==2){
        	that.imgUploadUrl=$.GLOBAL.config.uploadUrl.template({source: uploadSourcesMap.group})
        }else{
        	that.imgUploadUrl=$.GLOBAL.config.uploadUrl.template({source: uploadSourcesMap.product})
        }
        $.ajaxFileUpload({
            url : that.imgUploadUrl,
            secureuri : false,
            fileElementId : id,
            dataType : 'json', 
            global : false,
            data : {},
            success : function (data, status) {
                if (data.code == "ACK") {
					if(_.isEmpty(data.data)) {
						that.$cityFloorContainer.loadingInfo("error", "上传失败！"+data.message);
					}else {
						$('#name_{{id}}'.template({id: id})).val(data.data);
						$('#img_{{id}}'.template({id: id})).attr('src', fileUrlPrefix + data.data);
						$('#viewPicInTab_{{id}}'.template({id: id})).attr('href', fileUrlPrefix + data.data);
						that.haveChanged = true;
					}
                } else {
					$(window).loadingInfo("error", data.message);
				}
                
            } 
        });
        return false;
 
    },
    
    addGoods : function() {
        var that = this;
        if(!this.canAddGoods()) {
            this.$cityFloorContainer.loadingInfo('error', "当前楼层最多只能添加{0}个商品".format(this.floorsConfig[this.openedFloor]['maxAmount']));
            return false;
        }
        that.dialog =  BootstrapDialog.show({
            title: '添加{0} - {1}商品'.format(this.openedCityName, this.floorsConfig[this.openedFloor].name),
            //type : BootstrapDialog.TYPE_DEFAULT,
            message: $(this.relatedGoodsHtml),
            draggable: true,
            buttons: [{
                label: '确认',
                cssClass: 'btn-primary',
                action: function(dialog) {
                    that.insertRelatedGoods();
                    dialog.close();
                }
            }, {
                label: '取消',
                action: function(dialog) {
                    dialog.close();
                }
            }],
            onshown : function() {
                that.initRelatedTable();
                
               
            }
       });
       that.dialog.getModalDialog().css('width', '700px');
    }, 
    insertRelatedGoods : function() {
        var that = this;
        _(this.selectedGoods).each(function(value, key) {
            if(_.indexOf(that.getCurrentGoodsIds(), parseInt(key)) == -1 && that.canAddGoods()) {
				that.haveChanged = true;
				value.shiefText = "已上架";
                that.getGoodsListTable().find('tbody').append(that.relatedGoodsTr.template(value));
            }
        });
        this.selectedGoods = {};
        this.updateGoodsListSeq();
        
    },
    initRelatedTable : function() {
        var that = this;
        this.relatedBootTable = $.GLOBAL.utils.loadBootTable({
            table : $('#relatedGoodsList'),
            //removeBtn : $('#removeRecord'),
            refreshBtn : $('#rRefreshRecord'),
            idField : "productId",
            pagination : true,
            pageSize : 10,
            url: that.productsListUrl,
            sidePagination:'server',
            queryAddParams: function() { 
                return {
                    productName : $('#rProductName').val(),
                    productCode : $('#rProductCode').val(),
					regionId    : that.openedCityId
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
                    align : 'center',
                    width : 50,
                    formatter:function(value,row,index){  
                        return index+1; 
                    }
                } ,
                {
                    field: 'productCode'  
                } ,
                {
                    field: 'productName'  
                } ,
                {
                    field: 'cateName'  
                } ,
                {
                    field: 'brandName'  
                } 
            ]
            
        });
        this.relatedBootTable.$dataListTable.on("check.bs.table uncheck.bs.table check-all.bs.table uncheck-all.bs.table", _(this.selectRelatedGoods).bind(this));
        
        this.relatedBootTable.$dataListTable.on("post-body.bs.table ", _(this.reCheckedRows).bind(this)); 
        
         
    },
    selectRelatedGoods : function(/*event, row, element*/) {
        var that = this;
        if(arguments.length == 3) {
            var row = arguments[1];
            var element = arguments[2];
            if(!_.isUndefined(element) && element.get(0).type == "checkbox") {
                if(element.is(":checked") ) {
                    this.selectedGoods[row.productId] = row;
                } else {
                    delete this.selectedGoods[row.productId];
                }
            }
        } else if(arguments.length == 2) { //check-all uncheck-all
            var rows = arguments[1];
            _(rows).each(function(row, key) {
                if(row[0] == false) {
                    delete that.selectedGoods[row.productId];
                } else {
                    that.selectedGoods[row.productId] = row;
                }
            });
        } else {
            return false;
        }
        //this.selectedGoods = this.$dataListTable.bootstrapTable('getSelections');
        
         
        
    }, 
    reCheckedRows : function() {
        this.relatedBootTable.$dataListTable.bootstrapTable('checkBy', {field:'productId', values:_(_(this.selectedGoods).keys()).map(function(str, k) {
        	return parseInt(str);
        })});
    },
    getCurrentGoodsIds : function() {
        var ids = [];
        this.getGoodsListTable().find('tbody>tr').each(function() {
            ids.push(parseInt($(this).data('id')));
        });
        return ids;
    },
	
	
    getGoodsListTable : function() {
        return this.$cityFloorContainer.find('table.floorGoodsList');
    },
    canAddGoods : function() {
        //that.openedFloor
        var goodsAmount = this.getCurrentGoodsIds().length;
        var allowAmount = this.floorsConfig[this.openedFloor]['maxAmount'];
        if(allowAmount == -1 || goodsAmount<allowAmount) {
            return true;
        }
        return false;
    },
    //@TODO 删除后会导致广告位出现变化 时 应给予提醒.
    removeGoods :function(e) {
        e.preventDefault();
        var $target   = $(e.target),
            productId = $target.data('id'),
            that = this;
        $target.closest('tr').remove();  
        
		this.haveChanged = true;
		
        _(this.productsList).each(function(value, key) {
            if(value.productId == productId) {
                delete that.productsList[key];
            }
        });
        this.updateGoodsListSeq();    
    },
    updateGoodsListSeq : function() {
		var that = this;
        this.getGoodsListTable().find('tbody>tr').each(function() {
			//功能待定
			/*
			if($(this).index() + 1 < = that.floorsConfig[that.openedFloor]['indexMaxSeq']) {
				$(this).find("td:first").text($(this).index() + 1);
			} else {
				$(this).find("td:first").text("");
			}
			*/
			$(this).find("td:first").text($(this).index() + 1);
        });
    },
    
    removePic : function (e) {
    	var $target = $(e.target),
    		position = $target.closest('.goodspic-upload').data('position');
    //		picName = $goodUpload.find('input[name="pic.name"]').val();
		$('#img_{0}'.format(position)).attr("src",this.defaultImg);	
		var $image  = $('#name_{0}'.format(position)),
			imageUrl  = $image.val(),
			originUrl = $image.data('origin');	
		$image.val('');
		if(imageUrl && imageUrl == originUrl) {
			this.haveChanged = true;		
		}
		
		$('#viewPicInTab_{0}'.format(position)).href('javascript:;');	
    },
	getFloorData : function() {
		var data = {
			regionId : this.openedCityId,
			floorType: this.openedFloor,
			list     : []
		},
		that = this, image = "", $imgObj,
		tempObject = {}
		;
		var relatedGoods = [];
        this.getGoodsListTable().find('tbody>tr').each(function() {
            tempObject = {
				productId : $(this).data('id'),
				sort : $(this).index() + 1 
			};
			$imgObj = $('#name_{0}'.format(tempObject.sort));
			if(!_.isUndefined($imgObj)) { 
				
				if(!_.isEmpty($imgObj.val())) {
					tempObject.image = $imgObj.val();
				}
			}
			data.list.push(tempObject);
        });
		
		return data;
	},
    //save floor
    save : function() {
        var data = this.getFloorData(),
        	that = this,
		    dtd = $.Deferred(),
        	$saveBtn = this.$cityFloorContainer.find('button.saveFloorData');
        	$saveBtn.prop('disabled', true).text("正在保存...");
    		$.ajax(
            { 
                type         : 'POST',
                url          : urlPrefix+this.saveFloorUrl,
                dataType     : 'json',
                contentType  : 'application/json',
    			data         : JSON.stringify(data)
    		})
    		.done(function(result) {
    			if(result.code == "ACK") {
                    
                    that.haveChanged = false;
    				that.$cityFloorContainer.loadingInfo(
    				{
    					type : "success", 
    					text : result.message,
    					callBack : function() {
    						dtd.resolve();
    					}
    				});
                    
    			}
    		})
    		.fail(function() {
    			dtd.reject();
    		}).always(function() {
    			$saveBtn.prop('disabled', false).text("保存设置");
    		});
    		return dtd;		
    }
};