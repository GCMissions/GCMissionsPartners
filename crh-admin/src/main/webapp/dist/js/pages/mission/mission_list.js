$(function() {
	var missionList = {

		init : function() {
			var _self = this;
			_self.initEvents();
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
								result += "<a href='" + urlPrefix
										+ "mission/view/" + value
										+ "' class='detailItem' data-id='"
										+ value + "'>Edit</a>";
								return result;
							}
						} ]
			});

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
//								.insertText(msg.content);
							}, 2000);
						}
					}
				});

			};

			$("#savebtn").click(function() {
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
							alert("save Successfully");
						}
					}
				});
			});

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
							alert("Change Model Successfully");
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

			var code = $("#model").val();
			if (code == 1) {
				$("#missionTable").show();
				$("#textAreal").hide();
			} else {
				$("#missionTable").hide();
				$("#textAreal").show();
			}

			setVal();
			$("#model").change(function(){
				changeModel();
				changeTab();
			});
				

		}
	}.init();

})