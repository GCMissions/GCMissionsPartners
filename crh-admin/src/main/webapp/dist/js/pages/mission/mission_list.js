$(function() {
	var missionList = {

		init : function() {
			var _self = this;
			_self.initEvents();
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
			//获取弹框
			var edit =function(id){
				var that = this;
				$.ajax({
					type: "GET",
					url: urlPrefix + "mission/view/"+id,
					dataType: 'json',
					contentType: 'application/json'
				})
				.done(function(response){
					if(response != null && response != ""){
						that.dialog =  BootstrapDialog.show({
							title: 'Edit Mission',
							message: $(template('addEditTpl',response)),
							draggable: true,
							buttons: [{
								label: 'Save',
								cssClass: 'btn-primary saveAddEditTpl editMission',
								action: function(dialog, e) {
									save($(e.target));
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
			}
			
			setTimeout(function(){
				$(".editMissionList").on("click",function(){
					var id = $(this).prop('value');
					edit(id);
				});
			},1000);
			
			//保存弹框
			var saveMission = function(target){
				var $form = $('#addEditForm'),
	   		    that = this,
	            userId = $form.find('input[name="id"]').val(),
	            action = userId ? that.editURL: that.addURL,
	            data = that.getJson($form);
				
			};
			
		
			
		},
		salu:function(){
			//获取数据库富文本的值，并且给其设置
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
							}, 2000);
						}
					}
				});

			};

			//保存当前富文本的值
			var savebtn = function() {
				// 获取当前编辑的内容
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
							$("body").loadingInfo("warn", "Save Successfully");
						}
					}
				});
			};

			    setVal();
				//ckedit three button switch
				var edit = $("#editbtn");
				var save = $("#savebtn");
				var reset = $("#resetbtn");
				var title = $("#title");
				reset.hidden();
				save.hidden();
				edit.show();
				CKEDITOR.instances.TextArea1.setReadOnly(true);
				title.attr("readonly",true);
				reset.hidden();
				edit.onclick = function(){
					CKEDITOR.instances.TextArea1.setReadOnly(false);
					title.attr("readonly",false);
					save.show();
					reset.show();
					edit.hidden();
				};
				
				reset.onclick = function(){
					setVal();
					CKEDITOR.instances.TextArea1.setReadOnly(true);
					title.attr("readonly",true);
					save.hidden();
					edit.show();
					reset.hidden();
				};
				
				save.onlcick = function(){
					savebtn();
					CKEDITOR.instances.TextArea1.setReadOnly(true);
					title.attr("readonly",true);
					edit.show();
					reset.hidden();
					save.hidden();
			};
		},
		
		mod:function(){
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