$(function() {
	var heads = ["名称","操作"];
	var category = {
		editUrl :"edit",
		addUrl  :"add",
		coverElement : ".goodsCover",
		$dataList:$("#treeMenu"),
		init : function(){
			var that=this;
			this.bindEvents();
			$.ajax({
				type: "post",
				dataType: "json",
				url:"list",
				async:true,
				success: function(response){
					if(response.code == "ACK"){
						that.bootTable=that.TreeTable("treeMenu", heads, response.data);
        	 		}
				},
			});
			//this.TreeTable("treeMenu", heads, tNodes);
		},		
		bindEvents : function(){
			var that = this;
			$('.addItem').on("click", function() {	
				$.ajax({
					type: "POST",
					dataType: "json",
					url:"addData",
					async:true,
					success: function(response){
						that.dialog = BootstrapDialog.show({
					        title: '添加品类',
					        message: $(template('addEditTpl',response.data )),
					        draggable: true,
					        buttons: [{
					            label: '保存',
					            cssClass: 'btn-primary',
					            action: function(dialog) {
					                //dialog.setMessage('Message 1');
					            	that.save();
					            }
					        }, {
					            label: '取消',
					            action: function(dialog) {
					                dialog.close();
					            }
					        }],
							onshown : function() {	
								that.loadCategoryTree();
								that.initImageEvent();
							} 
						});	
	        	 	},
					error: function(xhr,type) {
						alert('Ajax error!');
					}
				});							
			});
			
			this.$dataList.on("click", "a.editItem", function() {
				var cateId= this.dataset.id,
					editurl="editData/"+cateId
				$.ajax({
					type: "POST",
					dataType: "json",
					url:editurl,
					async:true,
					success: function(response){
						that.dialog = BootstrapDialog.show({
					        title: '修改品类',
					        message: $(template('editTpl',response.data )),
					        draggable: true,
					        buttons: [{
					            label: '保存',
					            cssClass: 'btn-primary',
					            action: function(dialog) {
					                //dialog.setMessage('Message 1');
					            	that.save();
					            }
					        }, {
					            label: '取消',
					            action: function(dialog) {
					                dialog.close();
					            }
					        }],
							onshown : function() {	
								that.loadCategoryTree();
								that.initImageEvent();
							} 
						});	
	        	 	},
					error: function(xhr,type) {
						alert('Ajax error!');
					}
				});	
			});
			this.$dataList.on("click", "a.viewItem", function() {
				var cateId= this.dataset.id,
					editurl="editData/"+cateId
				$.ajax({
					type: "POST",
					dataType: "json",
					url:editurl,
					async:true,
					success: function(response){
						that.dialog = BootstrapDialog.show({
					        title: '查看品类',
					        message: $(template('viewTpl',response.data )),
					        draggable: true,
					        buttons: [{
					            label: '确定',
					            action: function(dialog) {
					                dialog.close();
					            }
					        }],
							onshown : function() {	
								that.loadCategoryTree();
								that.initImageEvent();
							} 
						});	
	        	 	},
					error: function(xhr,type) {
						alert('Ajax error!');
					}
				});	
			});
			this.$dataList.on("click", "a.removeItem", function() {
				var cateId= this.dataset.id,
					delUrl= "delete/"+cateId;
				BootstrapDialog.show({
					title: '删除品类',
					type : BootstrapDialog.TYPE_WARNING,
					size : BootstrapDialog.SIZE_SMALL,
			        message: ("确认删除此品类吗？"),
			        draggable: true,
			        buttons: [{
			            label: '确定',
			            cssClass: 'btn-primary',
			            action: function(dialog) {
			            	$.ajax({
								type: "get",
								url : delUrl,
								dataType : "JSON",
								contentType : 'application/json',
								data: JSON.stringify(),
								success:function(response){
									if(response.code=="ACK"){
										dialog.close();
										location.reload(true);
									}
									else{
										dialog.close();
//										$('body').loadingInfo({ text : response.message});
									}
								},
				                error: function(xhr,type) {
									alert('Ajax error!');
								}
							});
			            }
			        }, {
			            label: '取消',
			            action: function(dialog) {
			                dialog.close();
			            }
			        }]
				 });
			});
			
		},
		initImageEvent : function() {
			var that = this;
			// 图片ajax上传
	        $('#addEditForm').on('change', '.upload-btn input[type="file"]', function(){
	            var id = $(this).attr('id');
	            that.ajaxFileUpload(id);
	        });
		},
		// 图片上传ajax
	    ajaxFileUpload:   function (id, o) {
	        var that = this;
	        $('#img_0').attr('src', uiBase + "/img/loading.gif");
	    
	        $.ajaxFileUpload({
	            url : $.GLOBAL.config.ossUploadUrl.template({source: uploadSourcesMap.product}), 
	            secureuri : false,
	            fileElementId : id,
	            dataType : 'json',
	            global : false,
	            data : {},
	            success : function (data, status) {
	                if (data.code == "ACK") {
	                    $('input[name="picUrl0"]').val(data.data.url);
	                    $('input[name="picKey0').val(data.data.key);
	                    $('#img_0').attr('src', data.data.url);
	                    
	                }else {
						$(window).loadingInfo("error", data.message);
					}
	                
	            }
	        });
	        return false;
	 
	    },
		//添加页初始化Ztree
		loadCategoryTree : function() {
	        var setting = {
				async: {
	                autoParam : ["pId"],
	                type      : "post",
	                dataType  : "json",
	                dataFilter: this.ajaxDataFilter,
	                enable    : true,
	                url       : this.getUrl,
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
	    	var $categoryTree = $("#treeDemo");
	    	var $categoryName = $("#catpid");	    	
	        var options = {
	    		url: "list",
	    		$idInput: $parentId,
	    		$nameInput: $categoryName,
	    		$ztree: $categoryTree,
	    		setting: 
	    		{
	    			async: {
	    				autoParam : ["pId"],
	                    type      : "post",
	                    dataType  : "json",
	                    dataFilter: this.ajaxDataFilter,
	                    enable    : true,
	                    url       : this.getUrl,
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
		initDropdown : function() {
			var promiseCate = $.ajax(
		            { 
		        		type         : 'GET',
						url          : urlPrefix,
						dataType     : 'json',
						contentType  : 'application/json'
		     }).then(function(data) {
		    	 
		     });
		},
		showCategoryTree : function() {
	        var $cateSelect  = $("#cateId"),
	            that         = this,
	            cityOffset       = $cateSelect.offset();
	        this.$cateMenuContent = $cateSelect.parent().find('.menuContent');  
	            
	        this.$cateMenuContent.css({left: "0px"}).slideDown("fast");

	        //$("body").on("mousedown.cate",_(this.cateMenuBodyDown).bind(this));
	    },
	    hideCateMenu : function() {
	        this.$cateMenuContent.fadeOut("fast");
			$("body").off("mousedown.cate");
	    },
		ajaxDataFilter : function (treeId, parentNode, responseData) {
		    if (!responseData.data) return null;
		    for( var i =0,l=responseData.data.length;i<l;i++){		    	
		    	if(responseData.data[i].hasSon){
		    		responseData.data[i].isParent=true;}
		    	else{responseData.data[i].isParent=false;}
		    }		 
			return responseData.data;
		},
		getUrl : function (treeId,treeNode){
	    	if(treeNode){
	    		return "list/"+treeNode.cateId;
	    	}
	    	else{
	    		return "list/10000";//数据库顶级品类parentId设为10000
	    	}
	    },
	    //列表页树形结构
		TreeTable : function (tree_id, heads, tNodes) {
		    if ($("#" + tree_id).length > 0) {
		        var newNodes = [];
		        var sort = function (s_pid) {
		            for (var i = 0, l = tNodes.length; i < l; i++) {
		                if (tNodes[i].parentId == s_pid) {
		                    var len = newNodes.length;
		                    if (len > 0 && newNodes[len - 1].cateId == s_pid) {
		                        newNodes[len - 1].isParent = true;
		                    }
		                    newNodes.push(tNodes[i]);
		                    sort(tNodes[i].cateId);
		                }
		            }
		        }
		        sort("");

		        var head_html = "<thead><tr>";
		        var column_count = heads.length;
		        for (var i = 0; i < column_count; i++) {
		            head_html += "<td>" + heads[i] + "</td>";
		        }
		        head_html += "</tr></thead>";

		        var body_html = "<tbody>";
		        for (var k = 0, l = newNodes.length; k < l; k++) {
		            var node = newNodes[k];
		            body_html += "<tr data-tt-id='" + node.cateId + "' " + (node.parentId ? "data-tt-parent-id='" + node.parentId + "'" : "") + "><td class = 'td_left'><span class='" + (node.isParent ? "parent" : "children") + "'>" + node.cateName + "</span></td>";
		            for (var j = 0; j < column_count - 1; j++) {
		     //           body_html += "<td>" + (node.td[j] ? node.td[j] : "") + "</td>"; "+urlPrefix+"category/editPage/"+node.cateId+"
		     			body_html += "<td> <a  title='修改' href='javascript:;' class='editItem' data-id='"+node.cateId+"'> <i class='fa fa-edit'  style='font-size:20px'></i></a><a  title='查看' href='javascript:;' class='viewItem' data-id='"+node.cateId+"'> <i class='fa fa-eye'  style='font-size:20px'></i></a><a title='删除' href='javascript:;' class='removeItem' data-id='"+node.cateId+"'><i class='fa fa-trash' style='font-size:20px'></i></a></td>"
		            }
		            body_html += "</tr>";
		        }
		        body_html += "</tbody>";
		        $("#" + tree_id).html(head_html + body_html);

		        $("#" + tree_id).treetable({ expandable: true });
		        // Highlight selected row
		      /*   $("#" + tree_id + " tbody tr").mousedown(function () {
		            $("tr.selected").removeClass("selected");
		            $(this).addClass("selected");
		        }); */

		        // Drag & Drop
		        $("#" + tree_id + " .file, #" + tree_id + " .folder").draggable({
		            helper: "clone",
		            opacity: .75,
		            refreshPositions: true, // Performance?
		            revert: "invalid",
		            revertDuration: 300,
		            scroll: true
		        });

		        $("#" + tree_id + " .folder").each(function () {
		            $(this).parents("tr").droppable({
		                accept: ".file, .folder",
		                drop: function (e, ui) {
		                    var droppedEl = ui.draggable.parents("tr");
		                    obj.treetable("move", droppedEl.data("ttId"), $(this).data("ttId"));
		                },
		                hoverClass: "accept",
		                over: function (e, ui) {
		                    var droppedEl = ui.draggable.parents("tr");
		                    if (this != droppedEl[0] && !$(this).is(".expanded")) {
		                        obj.treetable("expandNode", $(this).data("ttId"));
		                    }
		                }
		            });
		        });
		    }
		},
		getFormJson : function($form) {
			var tempData = $form.frmSerialize();
			var parentId = $("#categoryId").val(),
				catId=$("#catId").val();
			
			data = {	
				cateName:tempData.cateName.replace(/(^\s*)|(\s*$)/g, ""),
				sort	:tempData.sort,
				typeId	:tempData.type,	
				image	:tempData.picUrl0,
				imageKey:tempData.picKey0
			};
			if(!parentId){data.parentId=0;}else{data.parentId=parentId;}
			if(catId){data.cateId=catId;}
			return data;
		},
		save : function() {
			var $form = $('#addEditForm'),
			    that = this,
			    cateName = $form.find('#catId').val(),
			    action = cateName ? that.editUrl: that.addUrl,
			    data = this.getFormJson($form);
			if($('#addEditForm').validate().form()) {
			    $.ajax({
				  type: 'POST',
				  url: action,
				  dataType: 'json',
				  contentType: 'application/json',
				  data:  JSON.stringify(data)
			    })
				.done(function(result) {
			 		if(result.code == "ACK"){
			 			that.dialog.close();
			 			window.location.href= urlPrefix + "category/";
			 		}
//				 	$('#addEditForm').loadingInfo({
//				 		type : "success",
//				 		callBack : function() {
//				 			that.dialog.close();
//				 			window.location.href= urlPrefix + "category/";
//				 		}
//				 	});
				 })
				 .fail(function(result) {
				 	$('#addEditForm').loadingInfo({
				 		text : "保存失败",
				 		type : "error",
				 		callBack : function() {
				 			//that.dialog.close();
			           	    //that.bootTable.refresh(); 
				 		}
				 	})
				 });
			} else {
				console.log("valid fail");
			}
		}
	};
	category.init();  
});
