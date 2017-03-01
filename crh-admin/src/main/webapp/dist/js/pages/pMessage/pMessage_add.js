var messageApp = {
	
	chooseMerchantUrl : "pMessage/memberList", //?
	listUrl : "pMessage/",
	newUrl : "pMessage/add",
	merchantTr 		  : '<tr data-id="{{orgId}}"><td></td>'
			+'<td>{{orgCode}}</td><td>{{orgName}}</td><td>{{contact}}</td><td>{{phone}}</td><td>{{address}}</td>'
			+'<td><a  title="删除" href="#" class="removeItem" data-id="{{orgId}}"> <i class="fa fa-remove "  style="font-size:20px"></i></a></td>'
			+'</tr>',
	selectedMerchants : {},
	merchantErrorTxt  : "请至少选择一个商家！",
	init : function() {
		
		this.$mainForm = $('#mainForm');
		this.$merchantError = this.$mainForm.find('.merchantError'); //商家没选择时错误提示
		this.$merchantListTable = $('#merchantsList'); 
		this.bindEvents();
		
		
		
		return this;
	},

	bindEvents : function() {
		this.validator = this.$mainForm.validate({
        	rules : {
        		title : {
        			required : true,
        			maxlength : 50
        		},
        		content : {
        			required : true
        			
        		}
        		
        	}, 
        	messages : {
        		title : {
        			required : "标题不能为空"
        		},
        		content : {
        			required : "内容不能为空"
        		}
        	}
        	
        });
		this.$mainForm.on("click", 'input[name=orgType]', _(this.changeOrgType).bind(this));
		this.$mainForm.on("click", 'button.submitMainForm', _(this.submitForm).bind(this));
		this.$merchantListTable.on("click", "a.removeItem", _(this.removeGoods).bind(this));
		this.$mainForm.on('click', ".addMerchant", _(this.addMerchantHandler).bind(this));
	},
	changeOrgType : function() {
		this.$merchantListTable.children('tbody').empty();
	},
    addMerchantHandler : function() {
    	var that = this;
    	
		
		that.dialog =  BootstrapDialog.show({
            title: '选择商家',
            message: $(template('chooseMerchantTpl', {})),
            draggable: true,
            buttons: [{
                label: '确认',
                cssClass: 'btn-primary',
                action: function(dialog) {
                    that.insertMerchant();
                    dialog.close();
                }
            }, {
                label: '取消',
                action: function(dialog) {
                    dialog.close();
                }
            }],
            onshown : function() {
                that.initMerchantTable();
                
               
            }
       });
       that.dialog.getModalDialog().css('width', '700px');
	   
	   
    },
    removeGoods :function(e) {
        e.preventDefault();
        var $target   = $(e.target),
            productId = $target.data('id'),
            that = this;
        $target.closest('tr').remove();  
        
		
        this.updateGoodsListSeq();    
    },
    
	initMerchantTable : function () {
		var that    = this;
		this.$table  =  $('#chooseMerchantsList');
		this.bootTable = $.GLOBAL.utils.loadBootTable({
            table : this.$table,
            //removeBtn : $('#removeRecord'),
            refreshBtn : $('#searchMerchant'),
            idField : "orgId",
            pagination : true,
            pageSize : 10,
            url: this.chooseMerchantUrl,
            sidePagination:'server',
            queryAddParams: function() {
                return {
                    orgType	: that.$mainForm.find('input[name=orgType]:checked').val(),
					orgCode : $.trim($('#sOrgCode').val()),
					orgName : $.trim($('#sOrgName').val()),
					contact : $.trim($('#sContact').val()),
					phone   : $.trim($('#sPhone').val())
                } 
            },
            columns: [
				{
                    align: 'center',
                    checkbox: true,
                    onClickCell : function(field, value, row, $element) {
                        that.selectMerchant(row, $element);
                    }
                } ,
                {
                    width: 50,
                    align: 'center',
                    formatter:function(value,row,index){  
                    	return index+1; 
                    }
                } ,
                {
                    field: 'orgCode'  
                } ,
                {
                    field: 'orgName'  
                } ,
				{
                    field: 'contact'  
                } ,
				{
                    field: 'phone'  
                } ,
				{
                    field: 'address'  
                } 
                
             ]
        });
		this.bootTable.$dataListTable.on("check.bs.table uncheck.bs.table check-all.bs.table uncheck-all.bs.table", _(this.selectMerchant).bind(this));
        
        this.bootTable.$dataListTable.on("post-body.bs.table ", _(this.reCheckedRows).bind(this)); 
       
	},
	selectMerchant : function(/*event, row, element*/) {
        var that = this;
        if(arguments.length == 3) {
            var row = arguments[1];
            var element = arguments[2];
            if(!_.isUndefined(element) && element.get(0).type == "checkbox") {
                if(element.is(":checked") ) {
                    this.selectedMerchants[row.orgId] = row;
                } else {
                    delete this.selectedMerchants[row.orgId];
                }
            }
        } else if(arguments.length == 2) { //check-all uncheck-all
            var rows = arguments[1];
            _(rows).each(function(row, key) {
                if(row[0] == false) {
                    delete that.selectedMerchants[row.orgId];
                } else {
                    that.selectedMerchants[row.orgId] = row;
                }
            });
        } else {
            return false;
        }
        
    }, 
    reCheckedRows : function() {
        this.bootTable.$dataListTable.bootstrapTable('checkBy', {field:'orgId', values:_(_(this.selectedMerchants).keys()).map(function(str, k) {
        	return parseInt(str);
        })});
    },
	insertMerchant : function() {
        var that = this;
        _(this.selectedMerchants).each(function(value, key) {
            if(_.indexOf(that.getCurrentOrgIds(), parseInt(key)) == -1 ) {
                that.$merchantListTable.find('tbody').append(that.merchantTr.template(value));
            }
        });
        this.selectedMerchants = {};
        this.updateMerchantListSeq();
        
    },
	
	getCurrentOrgIds : function() {
		var ids = [];
        this.$merchantListTable.find('tbody>tr').each(function() {
            ids.push(parseInt($(this).data('id')));
        });
        return ids;
	},
	updateMerchantListSeq : function() {
		var that = this;
        this.$merchantListTable.find('tbody>tr').each(function() {
			$(this).find("td:first").text($(this).index() + 1);
        });
    },
	getPostData : function() {
		var formData = $('#mainForm').frmSerialize();
        var result = {
			title: formData['title'],
			content: _.isUndefined(formData['content']) ? "" : formData['content'] ,
			orgType : formData['orgType'],
        };
		var merchants = [];
        _(this.getCurrentOrgIds()).each(function(value, index) {
            merchants.push(value);
        });
        result.orgIds = merchants;
        return result;
	},
	validateMerchants : function() {
		this.$merchantError.empty();
		if(this.getCurrentOrgIds().length==0) {
			this.$merchantError.show().text(this.merchantErrorTxt);
			return false;
		}
		return true;
	},
	submitForm : function() {
		
    	if(!this.validator.form() || !this.validateMerchants()) {
			return false;
    		
    	} else {
            this.doSave();
    	}
    },
	doSave : function() {
    	 var result = this.getPostData(),
    	     url    = this.newUrl,
    	 	 that   = this;
    	 
    	 $.ajax(
            { 
        		type         : 'post',
				url          : urlPrefix+url,
				dataType     : 'json',
				contentType  : 'application/json',
				data         : JSON.stringify(result)
		 })
		 .done(function(result) {
			if(result.code == "ACK") {
				that.$mainForm .loadingInfo({
    				type : "success", 
    				text: message("admin.message.success"),
    				callBack : function() {
    					window.location.href=urlPrefix + that.listUrl;
    				}
    			});
			}
		 });
    },
	
};