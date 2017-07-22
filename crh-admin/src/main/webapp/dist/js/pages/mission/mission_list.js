$(function() {
	var missionList = {

		init : function() {
			var _self = this;
			_self.initEvents();
			_self.editDialog();
			_self.salu();
			_self.mod();
		},
		initEvents : function() {
			var bootTable = $.GLOBAL.utils.loadBootTable({
				table : $('#dataList'),
				pagination : true,
				pageSize : 4,
				url : 'mission/list',
				sidePagination : 'server',
				queryAddParams : function() {

				},
				columns : [
						{
							field : 'id',
							width : '5%'
						},
						{
							field : 'title',
							width : '25%',
						},
						{
							field : 'content',
							width : '60%'
						},
						{
							field : 'id',
							width : '10%',
							formatter : function(value, row, id) {
								var result = "";
								result += "<button value ="+value +" class='editMissionList btn  btn-primary' style='font-weight:100' id='missionEdit'>Edit</button>";
								return result;
							}
						} ]
			});
		
		},
		
		editDialog:function(){
			var that = this;
			setTimeout(function(){
				$(".editMissionList").on("click",function(){
					var id = $(this).prop('value');
					$.ajax({
						type: "GET",
						url: urlPrefix + "mission/view/"+id,
						dataType: 'json',
						contentType: 'application/json;charset=utf-8',
					})
					.done(function(response){
						if(response != null && response != ""){
							that.dialog=BootstrapDialog.show({
								title: 'Edit Mission',
								message:  $(template('editMission',response)),
								draggable: true,
								buttons: [{
									label: 'Save',
									cssClass: 'btn-primary saveAddEditTpl',
									action: function(dialog, e) {
										saveMission($(e.target));
									}
								}, {
									label: 'Cancel',
									action: function(dialog) {
										dialog.close();
									}
								}],
								
							});
						}
					});
				});
			},100);
			
			var getJson = function($form) {
				$.fn.serializeObject = function(){
				    var o = {};
				    var a = this.serializeArray();
				    $.each(a, function() {
				        if (o[this.name] !== undefined) {
				            if (!o[this.name].push) {
				                o[this.name] = [o[this.name]];
				            }
				            o[this.name].push($.trim(this.value) || '');
				        } else {
				            o[this.name] = $.trim(this.value) || '';
				        }
				    });
				    return o;
				};
				var data = $form.serializeObject();
//				data.password =  $.md5(data.password);
				if(data.roleIds && !(data.roleIds instanceof Array)) {
					data.roleIds = new Array(data.roleIds);
				}	
				return data;
			}
			//save dialog
			var saveMission = function($btn){
				var $form = $('#addEditForm'),
	            userId = $form.find('input[name="id"]').val(),
	            data =getJson($form);
				
		        if($form.validate().form()) {
		        	$btn.saving();
		            $.ajax({
					  type: 'POST',
					  url: urlPrefix + "mission/save",
					  dataType: 'json',
					  contentType: 'application/json',
					  data:  JSON.stringify(data),
					  $loadingObject : $form
		            })
		        	.done(function(result) {
	        	 		if(result.code == "ACK"){
	        	 			$form.loadingInfo({
	        	 				type : "success",
	        	 				text: message("admin.message.success"),
	        	 				callBack : function() {
	        	 					that.dialog.close();
	        	 					that.bootTable.refresh(); 
	        	 				}
	        	 			});
	        	 		}
		        	 })
		        	 .fail(function(result) {
		        		 $form.loadingInfo({
		        	 		text : "Save failed",
		        	 		type : "error",
		        	 		callBack : function() {
		        	 			//that.dialog.close();
		                   	    //that.bootTable.refresh(); 
		        	 		}
		        	 	})
		        	 })
		        	 .always(function(result) {
		        		 $btn.saved();
		        	 });
		        } else {
		        	console.log("valid fail");
		        }
			};
		},
		salu:function(){
			//
			var setVal = function() {
				$.ajax({
					type : 'POST',
					url : urlPrefix + "salutatory/get",
					contentType : "application/json;charset=utf-8",
					dataType : 'json',
					success : function(msg) {
						if (msg != null && msg != "") {
							$("#sal").attr("value", msg.id);
							$("#title").attr("value", msg.title);
							setTimeout(function() {
								var aa = msg.content;
								aa = aa.replace(new RegExp("&lt;","g"),"<").replace(new RegExp("&gt;","g"),">")
								CKEDITOR.instances.TextArea1
										.insertHtml(aa);
							});
						}
					}
				});

			};

			//save
			var savebtn = function() {
				var content = CKEDITOR.instances.TextArea1.getData();
				var title = $("#title").val();
				var data  =  {
						"id" : parseInt($("#sal").val(), 10),
						"title" : title,
						"content" : content
					};
				$.ajax({
					type : 'POST',
					url : urlPrefix + "salutatory/save",
					contentType : "application/json;charset=utf-8",
					dataType : 'json',
					data : JSON.stringify(data),
					success : function(msg) {
						if (msg.code == "ACK") {
							$("body").loadingInfo("error", "Save Successfully");
						}
					}
				});
			};
				//ckeditor three button switch
				var edit = $("#editbtn");
				var save = $("#savebtn");
				var reset = $("#resetbtn");
				var title = $("#title");
				reset.hide();
				save.hide();
				edit.show();
				setTimeout(function(){
					setVal();
//					CKEDITOR.instances.TextArea1.setReadOnly(true);
//				 	CKEDITOR.config.readOnly = true;
					title.attr("disabled",true);
					edit.on("click",function(){
						CKEDITOR.instances.TextArea1.setReadOnly(false);
//						CKEDITOR.config.readOnly = false;
						title.attr("disabled",false);
						save.show();
						reset.show();
						edit.hide();
					});
					
					reset.on("click",function(){
						setVal();
						CKEDITOR.instances.TextArea1.setReadOnly(true);
//						CKEDITOR.config.readOnly = true;
						title.attr("disabled",true);
						save.hide();
						edit.show();
						reset.hide();
					});
					
					save.on("click",function(){
						savebtn();
						CKEDITOR.instances.TextArea1.setReadOnly(true);
//						CKEDITOR.config.readOnly = true;
						title.attr("disabled",true);
						edit.show();
						reset.hide();
						save.hide();
					});
				},2000);
//				
		},
		
		mod:function(){
			debugger;
			var code = $("#model").val();
			if (code == 1) {
				$("#missionTable").show();
				$("#textAreal").hide();
			} else {
				$("#missionTable").hide();
				$("#textAreal").show();
			}
			var changeModel = function() {
				$.ajax({
					type : 'POST',
					url : urlPrefix + "model/change",
					dataType : 'json',
					data : {
						"id" : $("#model").val()
					},
					success : function(msg) {
						if (msg.code == "ACK") {
							$("body").loadingInfo("warn", "Update Successfully");
						}
					}
				});
			};

			var changeTab = function() {
				var code = $("#model").val();
				if (code == 1) {
					$("#missionTable").show();
					$("#textAreal").hide();
				} else {
					$("#missionTable").hide();
					$("#textAreal").show();
				}
			};
			changeTab();
			
			
			$("#model").change(function(){
				changeTab();
			});
				
			$("#change").click(function(){
				changeModel();
			});
		}
		
		
	}.init();

})