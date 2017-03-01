var settingApp = {
	saveUrl : "paraSetting/edit",
	init : function() {
		this.$navTabs = $('ul.nav-tabs');

		this.initTimeSelect();
		this.bindEvents();
		this.initForm();
		return this;
		
	},
	bindEvents : function() {
		var that=this;
		$('button.submitMainForm').on("click", _(this.submitForm).bind(this));
		
		var skipflag = $("input[name='skipFlag']");
		var skipVal = $("#skipFlagVal").val();
		for(var i=0;i<skipflag.length;i++){
			if(skipflag[i].value==skipVal){
				skipflag[i].checked='checked';
			}
		}
		/*//app 文件上传
		$('#appFile').on('click',function(){
			alert($("#appVersion_android").val());
			that.ajaxFileUpload("android",$("#appVersion_android").val());
		});*/
	},
	initTimeSelect : function() {
		$.pages.initDateTime();
	},
	initForm : function() {
		this.validator = $('#mainForm').validate({
			rules : {
				C_hotline : {
					isPhone : true
				},
				C_timeSpace : {
					digits : true,
					min : 1
				},
				c_orderDay : {
					digits : true,
					min : 1
				},
				c_serviceQQ : {
					digits : true,
					minlength:5,
					maxlength:15
				},
				
				overTimeOrderInterval: {
					digits : true,
					min : 1
				},
				overTimeOrderTwiceInterval :{
					digits : true,
					min : 1
				},
				overTimePunishAmount : {
					price : true
				},
				z_newOrderInterval: {
					digits : true,
					min : 1
				},
				z_noOrderTakingAgain: {
					digits : true,
					min : 1
				},
				remindTime : {
					digits : true,
					min : 1
				},
				appAd_countDown : {
					digits : true
				}
			}

		});
		$('input[name="C_timeSpace"]').inputmask({
    		"mask" : "9",
    		repeat : 3,
    		"greedy": false
    	});
		$('input[name="c_orderDay"]').inputmask({
    		"mask" : "9",
    		repeat : 3,
    		"greedy": false
    	});
		$('input[name="remindTime"]').inputmask({
			"mask" : "9",
			repeat : 3,
			"greedy": false
		});
		$('input[name="c_serviceQQ"]').inputmask({
			"mask" : "9",
			repeat : 15,
			"greedy": false
		});
		$('input[name="overTimeOrderInterval"]').inputmask({
			"mask" : "9",
			repeat : 6,
			"greedy": false
		});
		$('input[name="z_newOrderInterval"]').inputmask({
			"mask" : "9",
			repeat : 6,
			"greedy": false
		});
		$('input[name="z_noOrderTakingAgain"]').inputmask({
			"mask" : "9",
			repeat : 6,
			"greedy": false
		});
	},
	submitForm : function() {

		if (!this.validator.form()) {
			this.toggleTabError();
		} else {
			this.toggleTabError();
			this.doSave();
		}
	},
	toggleTabError : function() {
		this.$navTabs.find('li:eq(0)').removeClass('has-error');
		this.$navTabs.find('li:eq(1)').removeClass('has-error');
		if ($('#tab_1').find('label.fieldError').length > 0) {
			this.$navTabs.find('li:eq(0)').addClass('has-error')
		}
		if ($('#tab_2').find('label.fieldError').length > 0) {
			this.$navTabs.find('li:eq(2)').addClass('has-error')
		}
	},
	partSave:function(){
		var that = this;
		var formData = $('#mainForm').frmSerialize();
		var result = {
				//dtoList : {
					c_hotline : formData['C_hotline'],
					c_timeSpace : formData['C_timeSpace'],
					c_orderTimeStart : formData['C_orderTimeStart'],
					c_orderTimeEnd : formData['C_orderTimeEnd'],
					remindTime : formData['remindTime'],
					c_orderDay : formData['c_orderDay'],
					c_serviceQQ : formData['c_serviceQQ'],
					c_authServiceHint : formData['c_authServiceHint'],
					overTimeOrderInterval : formData['overTimeOrderInterval'],
					z_newOrderInterval : formData['z_newOrderInterval'],
					z_noOrderTakingAgain : formData['z_noOrderTakingAgain'],
					overTimeOrderTwiceInterval : formData['overTimeOrderTwiceInterval'],
					overTimePunishAmount : formData['overTimePunishAmount'],
					
					appType_android : formData['appType_android'],
					appVersion_android : formData['appVersion_android'],
					appDownLoadUrl_android : formData['appDownLoadUrl_android'],
					appForceUpdate_android : formData['appForceUpdate_android'],
					appUpdateDesc_android : formData['appUpdateDesc_android'],
					
					appAdCountDown : $("#appAd_countDown").val(),
					appAdSkipFlag : $("input[name='skipFlag']:checked").val()
				//}

			};
		
		$('button.submitMainForm').prop('disabled', true).text('保存中....');
		$.ajax({
			type : 'post',
			url : urlPrefix + that.saveUrl,
			dataType : 'json',
			contentType : 'application/json',
			data : JSON.stringify(result)
		}).done(
				function(result) {
					if (result.code == "ACK") {
						$('button.submitMainForm').prop('disabled', false)
								.text('保存成功');
						$('#mainForm').loadingInfo({
							type : "success",
							text : message("admin.message.success")
						});
					}
				}).always(function() {
			$('button.submitMainForm').prop('disabled', false).text('保存');
		});
	},
	doSave : function() {
		var that = this;
		
		if(($("#appFile").val()!=""&&$("#appFile").val()!=undefined)){
			var dtd = $.Deferred(); // 新建一个Deferred对象
			var wait = function(dtd){
				var tasks = function(){
					 $('button.submitMainForm').prop('disabled', true).text('文件上传中....');
					 $.ajaxFileUpload({
							url : urlPrefix + "paraSetting/uploadApp",
							secureuri : false,
							fileElementId : "appFile",
							dataType : 'json',
							global : false,
							data : {"version":$('#appVersion_android').val()},
							success : function (data, status) {
								if (data.code == "ACK") {
									$('input[name="appDownLoadUrl_android"]').val(data.data);
								}else{
									alert(data.message);
								}
								dtd.resolve(); // 改变Deferred对象的执行状态
							}
							
						});
				};
				setTimeout(tasks,1000);
				return dtd;
			}

			$.when(wait(dtd)).done(function(){
				var formData = $('#mainForm').frmSerialize();
				var result = {
						//dtoList : {
							c_hotline : formData['C_hotline'],
							c_timeSpace : formData['C_timeSpace'],
							c_orderTimeStart : formData['C_orderTimeStart'],
							c_orderTimeEnd : formData['C_orderTimeEnd'],
							remindTime : formData['remindTime'],
							c_orderDay : formData['c_orderDay'],
							c_serviceQQ : formData['c_serviceQQ'],
							overTimeOrderInterval : formData['overTimeOrderInterval'],
							z_newOrderInterval : formData['z_newOrderInterval'],
							z_noOrderTakingAgain : formData['z_noOrderTakingAgain'],
							overTimeOrderTwiceInterval : formData['overTimeOrderTwiceInterval'],
							overTimePunishAmount : formData['overTimePunishAmount'],
							
							appType_android : formData['appType_android'],
							appVersion_android : formData['appVersion_android'],
							appDownLoadUrl_android : formData['appDownLoadUrl_android'],
							appForceUpdate_android : formData['appForceUpdate_android'],
							appUpdateDesc_android : formData['appUpdateDesc_android'],
							
							appAdCountDown : $("#appAd_countDown").val(),
							appAdSkipFlag : $("input[name='skipFlag']:checked").val()
						//}

					};
				
				$('button.submitMainForm').prop('disabled', true).text('保存中....');
				$.ajax({
					type : 'post',
					url : urlPrefix + that.saveUrl,
					dataType : 'json',
					contentType : 'application/json',
					data : JSON.stringify(result)
				}).done(
						function(result) {
							if (result.code == "ACK") {
								$('button.submitMainForm').prop('disabled', false)
										.text('保存成功');
								$('#mainForm').loadingInfo({
									type : "success",
									text : message("admin.message.success")
								});
							}
						}).always(function() {
					$('button.submitMainForm').prop('disabled', false).text('保存');
				});
			});
		}else{
			that.partSave();
		}
	}
};
var templateApp = {
	saveUrl : "messageModel/edit",
	init : function() {
		this.bindEvents();
	},
	bindEvents : function() {
		$('button.submitMainForm').on("click", _(this.submitForm).bind(this));
	},
	submitForm : function() {

		this.doSave();
	},
	doSave : function() {
		var formData = $('#mainForm').frmSerialize(), result = {};
		result = formData;
		var that = this;
		$('button.submitMainForm').prop('disabled', true).text('保存中....');
		$.ajax({
			type : 'post',
			url : urlPrefix + this.saveUrl,
			dataType : 'json',
			contentType : 'application/json',
			data : JSON.stringify(result)
		}).done(
				function(result) {
					if (result.code == "ACK") {
						$('button.submitMainForm').prop('disabled', false)
								.text('保存成功');
						$('#mainForm').loadingInfo({
							type : "success",
							text : message("admin.message.success")
						});
					}
				}).always(function() {
			$('button.submitMainForm').prop('disabled', false).text('保存');
		});
	}
};