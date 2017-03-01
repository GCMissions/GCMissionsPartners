var productApp = {
	categoryUrl : "",
	brandUrl    : "",
	deleteUrl : "product/delete/{{productId}}",
	init : function(cateId) {
		this.initTimeSelect();
        
        this.$dataList = $('#dataList');
        this.loadCategoryTree();
        this.initTable(); 
        
        this.bindEvents();
	},
	bindEvents: function() {
		this.$dataList.on("click", "a.removeItem", _(this.removeItemHandler).bind(this));
        //$('#cateId').on('click', _(this.showCategoryTree).bind(this));
	},
    showCategoryTree : function() {
        var $cateSelect  = $("#cateId"),
            that         = this,
            cityOffset       = $cateSelect.offset();
        this.$cateMenuContent = $cateSelect.parent().find('.menuContent');  
            
        this.$cateMenuContent.css({left: "0px"}).slideDown("fast");

        //$("body").on("mousedown.cate",_(this.cateMenuBodyDown).bind(this));
    },
    loadCategoryTree : function() {
        var setting = {
			async: {
                autoParam : ["pId"],
                type      : "post",
                dataType  : "json",
                dataFilter: this.categoryTreeAjaxDataFilter,
                enable    : true,
                url       : this.getCategoryTreeUrl,
            },
			view: {
				dblClickExpand: false
			},
			data: {
                key: {
                    id   : "cateId",
                    name : "cateName",
                    pId  : "parentId"
                },
                simpleData: {
                    enable: true,
                    idKey : "id",
                    pIdKey: "pId",
                    rootPId : 0
                    //checked: true
                }
            }
		};

        
       // $.fn.zTree.init($("#cateZtree"), setting);
        
        var $parentId = $("#categoryId");
    	var $categoryTree = $("#cateZtree");
    	var $categoryName = $("#cateId");
    	
    	
        var options = {
    		url: urlPrefix + "category/list",
    		$idInput: $parentId,
    		$nameInput: $categoryName,
    		$ztree: $categoryTree,
    		setting: 
    		{
    			async: {
    				autoParam : ["pId"],
                    type      : "post",
                    dataType  : "json",
                    dataFilter: this.categoryTreeAjaxDataFilter,
                    enable    : true,
                    url       : this.getCategoryTreeUrl,
    			},
		        data: {
		            key: {
		                id   : "cateId",
		                name : "cateName",
		                pId  : "parentId"
		            },
		            simpleData: {
		                enable: true,
		                idKey : "id",
		                pIdKey: "pId",
		                rootPId : 0
		                //checked: true
		            }
		        }
    		}
    	};
    	
    	$.dropDownMenu(options);        	
    },
    categoryTreeAjaxDataFilter : function (treeId, parentNode, responseData) {
        if (!responseData.data) return null;
        var isRootCates = true;
        for( var i =0,l=responseData.data.length;i<l;i++){
            responseData.data[i].isParent=true;
            if(responseData.data[i].parentId) {
            	isRootCates = false;
            }
        	if(responseData.data[i].hasSon) {
	    		responseData.data[i].isParent=true;
	    	} else {
	    		responseData.data[i].isParent=false;
	    	}
        }
        if(isRootCates && responseData.data.length > 0) {
        	var rootCate = {cateId:0,cateName : "不限", hasSon:false, isParent:true, parentId:0, isNoLimitCate : 1};
        	responseData.data.splice(0, 0, rootCate);
        }
        return responseData.data;
    },
    /*
     * "category/list/0" will get all the data
     * */
    getCategoryTreeUrl :  function (treeId,treeNode){
    	if(treeNode && treeNode.isNoLimitCate != void 0) return false;
        if(treeNode){
            return urlPrefix + "category/list/"+treeNode.cateId;
        }
        else{
            return urlPrefix + "category/list/0";
        }
    },
    
    
    cateMenuBodyDown : function(event) {
        var $target = $(event.target);
        //if (!($target.attr('id') ==  "menuBtn" || $target.attr('id') == "citySel" || $target.haveClass("menuContent") || $(event.target).parents(".menuContent").length>0)) {
           // this.hideCateMenu();
        //}
    },
    hideCateMenu : function() {
        this.$cateMenuContent.fadeOut("fast");
		$("body").off("mousedown.cate");
    },
	removeItemHandler : function(e) {
		e.preventDefault();
    	var $target = $(e.target),
    		that    = this,
    		productId  = $target.parent().data('id');
    	
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
                    that.doRemove( productId);
                }
            }, {
                label: '取消',
                action: function(dialog) {
                    dialog.close();
                }
            }]
        });
    },
    doRemove : function( productId) {
    	var that    = this;
    	$.ajax(
        { 
    		type         : 'POST',
			url          : urlPrefix+this.deleteUrl.template({productId: productId}),
			dataType     : 'json',
			contentType  : 'application/json'
		})
		.done(function(result) {
			$('body').loadingInfo({
				type : "success", 
				text: message("admin.message.success"),
				callBack : function() {
					//@TODO 删除行
                    that.dialog.close();
                   	that.bootTable.refresh(); 
				}
			});
		})
		.fail(function(result) {
			$('body').loadingInfo("error", message("admin.message.error"))
			
		});
    },
	initTimeSelect : function() {
		$.pages.initDateTime();
		
		
	},
	
	initTable : function() {
		this.bootTable = $.GLOBAL.utils.loadBootTable({
            table : this.$dataList,
            //removeBtn : $('#removeRecord'),  
            refreshBtn : $('#refreshRecord'), 
            //idField : "productId",
            pagination : true,
            pageSize : 10,
            url: 'product/list',
            sidePagination:'server',
            queryAddParams: function() {
                var queryObj =  {
                    productName: $.trim($('#productName').val()),
                    productCode: $.trim($('#productCode').val()),
                    cateId     : $('#categoryId').val(),
                    brandId    : $('#brandId').val(), 
                    
                    startDate  : $('#startDate').val(), 
                    endDate    : $('#endDate').val(),
                    relateProductId  : 0
                } ;
                 
                if(queryObj.startDate && $.GLOBAL.utils.isDate(queryObj.startDate)) {
                	queryObj.startDate += " 00:00:00";
                }
                if(queryObj.endDate && $.GLOBAL.utils.isDate(queryObj.endDate)) {
                	queryObj.endDate += " 23:59:59";
                }
                if(queryObj.cateId=='') {
                	queryObj.cateId= 0;
                }
                return queryObj;
            },
            columns: [
				{
					width : 50,
					align: 'center',
                    formatter:function(value,row,index){  
                    	return index+1;
                    }
                } ,
                {
                    field: 'productCode',
                    //sortable: true
                } ,
                {
                    field: 'productName',
                    //sortable: true
                } ,
                {
                	field: 'goodName',
                	//sortable: true
                } ,
                 {
                    field: 'cateName',
                    //sortable: true
                } ,
                {
                    field: 'brandName',
                    //sortable: true
                } ,
                {
                    field: 'specNum',
                    //sortable: true
                } ,
                {
                    field: 'price',
                    //sortable: true
                } ,
                {
                    field: 'createDate',
                    //sortable: true
                } ,
               
                {
                    field: 'productId',
                    align: 'center',
                    formatter: function(value,row,index){  
                      
                        return  ' <a  title="查看" href="'+urlPrefix+'product/viewPage/'+row.productId+'" class="viewItem" data-id="'+row.productId+'"> <i class="fa fa-eye "  style="font-size:20px"></i></a>'
		                        + ' <a  title="修改" href="'+urlPrefix+'product/editPage/'+row.productId+'"> <i class="fa fa-edit "  style="font-size:20px"></i></a>'
		                        +' <!--<a  title="删除" href="#" class="removeItem" data-id="'+row.productId+'"> <i class="fa fa-trash "  style="font-size:20px"></i></a>-->'
		                        ;
          
                    } 
                } 
             ]
        });

	},
	initDropdown : function() {
		var promiseCate = $.ajax(
	            { 
	        		type         : 'GET',
					url          : urlPrefix+this.categoryUrl,
					dataType     : 'json',
					contentType  : 'application/json'
	     }).then(function(data) {
	    	 
	     });
	}
};