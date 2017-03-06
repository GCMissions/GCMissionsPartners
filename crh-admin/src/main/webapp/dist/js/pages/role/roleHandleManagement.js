$(function(){
	var role = {
		$submitBtn : $('.submitMainForm'),
		addURL : 'role/add',
		editURL : 'role/edit',
		
		init : function() {
        	this.$submitBtn.on("click",_(this.save).bind(this));
        	
//        	$('#back').on("click", function() {
//				window.location.href = urlPrefix + 'role/';
//			});
        	
        	this.checkBoxFunc();
		},	
		
		//将form表单的值转化为json
		getJson : function($form) {
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
			if(!data.functionIds){
				data.functionIds = [];
			}else if(!(data.functionIds instanceof Array)){
				data.functionIds = new Array(data.functionIds);
			}
			//当某一组checkbox未全部勾选,还是要传回其父节点的值
			this.getParentCheckbox(data.functionIds);
			return data;
		},
		
		getParentCheckbox : function(functionIds) {
			if(functionIds.length > 0) {
				var checkboxData = $("[id^='checkAll']");
				$.each(checkboxData, function() {
					var subCheckbox = $(this).parent().siblings(".child").find("input[type='checkbox']"),
					subChecked = subCheckbox.filter(":checked"),
					subCheckedLength = subChecked.length,
					subLength = subCheckbox.length;
					
					if(subCheckedLength >0 && subCheckedLength < subLength) {
						functionIds.push(this.value);
					}
				});
			}
		},
		
		checkBoxFunc : function() {
			//点击全选时其下的checkbox全部选中,当某一组checkbox全部勾选时全选checkbox也选中
			$("[id^='checkAll']").on('click',function(){
        		var parent = $(this).parent();
        		parent.siblings(".child").find("input[type='checkbox']").prop("checked", this.checked);
        	});
	    	$("input[id^='sub']").on('click',function() {
	    		var checkAllId = "#checkAll_" + this.id.replace(/[^0-9]/ig,"");
	    		var $subCheckbox = $(this).closest('.child').find("input[type='checkbox']");
	    		$(checkAllId).prop("checked" , $subCheckbox.length == $subCheckbox.filter(":checked").length ? true :false);
	    	});
		},
		
		save : function(e) {
			var that = this,
				$btn = $(e.target),
				$form = $('#addEditForm'),
				roleId = $form.find('input[name="roleId"]').val(),
	            action = roleId ? that.editURL: that.addURL;
			if($form.validate().form()) {
				var data = that.getJson($form);	
				$btn.saving();
	            $.ajax({
				  type: 'POST',
				  url: urlPrefix + action,
				  dataType: 'json',
				  contentType: 'application/json',
				  data:  JSON.stringify(data),
				  $loadingObject: $(window)
	            })
	        	.done(function(result) {
        	 		if(result.code == "ACK"){
        	 			$(window).loadingInfo({
            				type : "success", 
            				text: message("admin.message.success"),
            				callBack : function() {
            					window.location.href = urlPrefix + 'role/';
            				},
            				timeouts : 3000
            			}); 
        	 		}
	        	 })
	        	 .fail(function(result) {
	        		 $(window).loadingInfo({
	        	 		text : "Save Failed",
	        	 		type : "error",
	        	 		callBack : function() {
	        	 		}
	        	 	})
	        	 })
	        	 .always(function(result) {
	        		 $btn.saved();
	        	 });
	        } else {
	        	console.log("valid fail");
	        }
		}
	}
	role.init();
});