$(function() {
	
	var attributeApp = {
		$dataList : $('#dataList'),
        $addItem  : $('.addItem'),
        addPostUrl    : 'attribute/add/',
        editUrl   : "attribute/edit/{{attrId}}",
        editPostUrl : "attribute/edit/",
        deleteUrl : "attribute/delete/{{attrId}}",
        dialog : void 0,
        bootTable : void 0,
        defaultAttr : {valueInfo : "", attrValueId : ""},
        avSeq     : 0,
		init : function() {
            this.bindEvents();
            this.bootTable = $.GLOBAL.utils.loadBootTable({
                table : this.$dataList,
                //removeBtn : $('#removeRecord'),
                refreshBtn : $('#refreshRecord'),
                idField : "attrId",
                pagination : true,
                pageSize : 10,
                url: 'attribute/list',
                sidePagination:'server',
                queryAddParams: function() {
                    return {
                         //keyword: "ddd"
                    }
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
                        field: 'attrName'  
                    } ,
                    {
                        field: 'sort'  
                    } ,
                    {
                    	field: 'searchable',
                    	formatter:function(value,row,index){
                    		var result = "否";
                    		if(parseInt(value)){
                    			result = "是";
                    		}
                    		return result;
                    	}
                    },
                    {
                        field: 'list',
                        sortable: false,
                        formatter:function(value,row,index){  
                        	
                        	var result  = "";
                        	
                            _(value).each(function(value, index) {
                                result+='<button class="btn btn-default btn-sm">'+value['valueInfo']+'</button>&nbsp;';
                            });
                        	return result; 
                        }
                    }, 
                    { 
                       
                        field: 'attrId',
                        align: 'center',
                        checkbox: false,
                        formatter:function(value,row,index){  
                          
                            return ' <a  title="修改" href="#" class="editItem" data-id="'+row.attrId+'"> <i class="fa fa-edit "  style="font-size:20px"></i></a>'
                                    +' <a  title="删除" href="#" class="removeItem" data-id="'+row.attrId+'"> <i class="fa fa-trash"  style="font-size:20px"></i></a>'
                                    ;
              
                        } 
                    }
                 ]
            });
            
        	$.validator.addMethod("valueunique", function(value,element) {
    			var isNotRepeat = true;
    			var allInput = $(element).closest('tbody').find('input[type=text]');
    			allInput.each(function() {
    				if($(this).val() == $(element).val() && $(this).attr('name') != $(element).attr('name')) {
    					isNotRepeat = false;
    				}
    			});
    			    			
    			return this.optional(element) ||  isNotRepeat;
    		}, "选项值不能重复");
    
		},
        bindEvents : function() {
            //add edit 
            var that = this;
            this.$addItem.on("click", function() {
            	var subAttrHtml;
            	that.defaultAttr.avSeq  = that.avSeq ++ ;
				subAttrHtml = template('subItemTpl', that.defaultAttr);
				
               that.dialog =  BootstrapDialog.show({
                    title: '添加属性',
                    //type : BootstrapDialog.TYPE_DEFAULT,
                    message: $(template('addEditTpl', {subAttrHtml: subAttrHtml})),
                    draggable: true,
                    buttons: [{
                        label: '保存',
                        cssClass: 'btn-primary saveAddEditTpl',
                        action: function(dialog, e) {
                            that.save($(e.target));
                        }
                    }, {
                        label: '取消',
                        action: function(dialog, e) {
                            dialog.close($(e.target));
                        }
                    }],
                    onshown : function() {
						that.initInputMask();
                    	that.updateDialogValueList();
			           
                    }
               });
               that.dialog.getModalDialog().css('width', '500px');
            });
            this.$dataList.on("click", "a.editItem", _(this.editItemHandler).bind(this));
            this.$dataList.on("click", "a.removeItem", _(this.removeItemHandler).bind(this));
            $('body').on('click', ".addEditTpl .addSubItem", _(this.addSubItemHandler).bind(this));
            $('body').on('click', "#subItemList .removeSubItem", _(this.moveDelete).bind(this));
            
            
    
        },
        initInputMask : function() {
        	
        	$('#addEditForm').find('input[name="sort"]').inputmask({
        		"mask" : "9",
        		repeat : 3,
        		"greedy": false
        	});
        },
        
        updateDialogValueList : function() {
        	var $subList = $('#subItemList');
        	if($subList.find("tbody>tr").length==1) {
        		$subList.find("tbody>tr").eq(0).find('td').last().find('p').hide();
        	} else {
        		$subList.find("tbody>tr").eq(0).find('td').last().find('p').show();
        	}
        },
        removeItemHandler : function(e) {
        	e.preventDefault();
        	var $target = $(e.target),
        		that    = this,
    			attrId  = $target.parent().data('id');
        	that.dialog =  BootstrapDialog.show({
                title: '删除属性',
                
                type : BootstrapDialog.TYPE_WARNING,
                message: message('admin.dialog.deleteConfirm'),
                draggable: true,
                size : BootstrapDialog.SIZE_SMALL,
                buttons: [{
                    label: '确认删除',
                    cssClass: 'btn-primary saveAddEditTpl',
                    action: function(dialog) {
                    	dialog.close();
                        that.doRemove( attrId);
                    }
                }, {
                    label: '取消',
                    action: function(dialog) {
                        dialog.close();
                    }
                }]
            });
        },
        doRemove : function( attrId) {
        	var that    = this;
        	$.ajax(
            { 
        		type         : 'GET',
				url          : urlPrefix+this.deleteUrl.template({attrId: attrId}),
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
    			
    			
    		});
        },
        editItemHandler: function(e) {
        	e.preventDefault();
        	var $target = $(e.target),
        		attrId = $target.parent().data('id');
        	
        	//disabled $target 
        	$target.prop("disabled", true);
        	
        	var that = this;
        	$.ajax(
            { 
        		type: 'GET',
				url: urlPrefix+this.editUrl.template({attrId: attrId}),
				dataType: 'json',
				contentType: 'application/json',
			})
            .done(function(result) {
            	if(result.code == "ACK") {
            		var subAttrHtml = "";
            		if(result.data.list.length > 0) {
            			_(result.data.list).each(function(value, index) {
            				value.avSeq  = that.avSeq ++ ;
            				subAttrHtml += template('subItemTpl', value);
            			});
            		}
            		result.data.subAttrHtml = subAttrHtml;
            		result.data.searchable = parseInt(result.data.searchable);
            		var message = $(template('addEditTpl', result.data));
	                that.dialog =  BootstrapDialog.show({
	                    title: '编辑属性',
	                    message: $(template('addEditTpl', result.data)),
	                    draggable: true,
	                    buttons: [{
	                        label: '保存',
	                        cssClass: 'btn-primary saveAddEditTpl',
	                        action: function(dialog, e) {
	                            that.save($(e.target));
	                        }
	                    }, {
	                        label: '取消',
	                        action: function(dialog) {
	                            dialog.close();
	                        }
	                    }],
	                    onshown : function() {
                            that.initInputMask();
	                    	that.updateDialogValueList();
	                       
	                    }
	                });
	                that.dialog.getModalDialog().css('width', '500px');
            	} else {
                    //
                }
            })
            .always(function() {
            	$target.prop("disabled", false);
            })
            .fail(function(result) {
            	alert("获取数据失败"); 
            })
            ;
        	
        	
        	
        },
        addSubItemHandler : function (e) {
        	var that = this;
        	
            var subHtml = $(template('subItemTpl', {avSeq : this.avSeq ++}));
            //bindEvent
            var links = subHtml.find("a");
            //links.eq(0).on('click', this.moveUp);
            //links.eq(1).on('click', this.moveDown);
            //links.eq(0).on('click', this.moveDelete);
            
            $('#subItemList').append(subHtml);
            that.updateDialogValueList();
        },
        moveUp : function(e) {
            var $obj = $(e.target);
            var current = $(obj).parent().parent();
            var prev = current.prev();  
            if (current.index()>1) current.insertBefore(prev); 
        } , 
        moveDown: function (obj) {  
            var $obj = $(e.target);
            var current = $(obj).parent().parent(); 
            var next = current.next();
            if (next) current.insertAfter(next);
        },
        moveDelete : function(e) {
            var $obj = $(e.target);
             $obj.closest('tr').remove();
             var that = this;
             that.updateDialogValueList();
        },
        getFormJson : function($form) {
        	var arr = $form.frmSerialize(), seq, seqs = [];
        	var tempData = $form.frmSerialize();
        	data = {
        		"token":"",
        		"attrType":"1",
        		attrId : tempData.attrId,
				sort   : tempData.sort,
				searchable : tempData.searchable,
        		attrName: tempData.attrName,
        		list : []
        	};
        	_(tempData).each(function(value, index) {
        		if(/_[\d]+$/.test(index)) {
        			//delete data[index];
        			seq = index.split("_")[1];
        			seqs.push(seq);
        		}
        		
        	});
        	seqs = _.sortBy(_(seqs).uniq(), function(num) {
        	    return num;
        	});
        	
        	var allValues = [];
        	_(seqs).each(function(value, index) {
        		var curVal = $.trim(tempData["list.valueInfo_"+value]);
        		if(_.indexOf(allValues, curVal) != -1) {
        			return false;
        		}
        		allValues.push(curVal);
        		var obj = {
        			"valueInfo" : curVal,
        			"sort"      : value,
        			"attrValueId" : tempData["list.attrValueId_"+value],
        		};
        		if(_.isEmpty(data.attrId)) {
        			delete obj.attrValueId;
        		}
        		data.list.push(obj);
        	});
        	
        	return data;
        },
        save : function($btn) {
        	var $form = $('#addEditForm'),
       		    that = this,
                isValid = true,
                attrId = $form.find('input[name="attrId"]').val(),
                action = attrId ? this.editPostUrl: this.addPostUrl,
                data = this.getFormJson($form);
            //data = data.replace(/valueInfo_\d+/g, "valueInfo");
           
            
            
            //同名只检查一次
            if( $('#addEditForm').validate().form()) {
            	$btn.saving();
	            $.ajax({
				  type: 'POST',
				  url: urlPrefix+action,
				  dataType: 'json',
				  contentType: 'application/json',
				  data:  JSON.stringify(data),
				  $loadingObject : $form
                })
	        	.done(function(result) {
	        	 	
	        	 	if(result.code == "ACK") {
		        	 	$('#addEditForm').loadingInfo({
		        	 		type : "success",
		        	 		text : "保存成功",
		        	 		callBack : function() {
		        	 			that.dialog.close();
	                       	    that.bootTable.refresh(); 
		        	 		}
		        	 	});
	        	 	}
			     
	        	 })
	        	 .always(function(result) {
	        		 $btn.saved();
	        	 
	        	 });
	            
            } else {
            	//console.log("valid fail");
            }
        }    

	};
	
    attributeApp.init();
	
	
});