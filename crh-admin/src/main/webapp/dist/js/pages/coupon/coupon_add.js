$(function(){
	var couponAdd = {			
		initEvents:function(){	
			
			var that = this;
			var date = new Date();
			var year = date.getFullYear();
			var month = date.getMonth()+1;
			var day = date.getDate();
			
			//初始化日期设置
			$('#usDate').datetimepicker({
				minView: 'month',
				format: 'yyyy-mm-dd',
				language: 'ch',
				startDate: year+'-'+month+'-'+day,
				autoclose : true,
				todayBtn : true
			});
			$('#ueDate').datetimepicker({
				minView: 'month',
				format: 'yyyy-mm-dd',
				language: 'ch',
				startDate: year+'-'+month+'-'+day,
				autoclose : true,
				todayBtn : true
			});
			
			//绑定某些事件
			
			//开始使用日期
			$('#usDate').on('changeDate',_(this.changeStartDate).bind($('#usDate')));
			
			//结束使用日期
			$('#ueDate').on('changeDate',_(this.changeEndDate).bind($('#ueDate')));			
			
			//选择优惠券类别
			$("#useCategory").on('change',_(this.checkedSetVal2).bind($("#useCategory")));
			
			//选择适用平台设值
			$("#webUse").on('change',_(this.checkedSetVal).bind($("#webUse")));
			
			//保存优惠券
			$('#couponAdd').on("click",_(this.submitForm).bind(this));
	        
	        //点击获得商品品类
	        $('.fetchType').on('click', _(this.fetchTypeDeal).bind(this));
	        
	        
	        $('#fetchTypeSelect').on('click', _(this.showfetchTypeMenu).bind(this));
	        
	        $('.useType').on('click', _(this.useTypeDeal).bind(this));
	        
	        $('#useTypeSelect').on('click', _(this.showuseTypeMenu).bind(this));
	       
			//验证
			this.validator = $('#couponAddForm').validate({
	        	rules : {
	        		couponName : {
	        			required : true,
	        			maxlength:50
	        		},
	        		valueYuan : {
	        			required : true,
	        			digits:false,
	        			min:0.01,
	        			price : true,
	        			max   : 99999
	        		},
	        		fetchTypeYuan : {
	        			required : true,
	        			digits:false,
	        			min:0.01,
	        			price : true,
	        			max   : 99999
	        		},
	        		useTypeYuan : {
	        			required : true,
	        			digits:false,
	        			min:0.01,
	        			price : true,
	        			max   : 99999
	        		}
	        	}, 
	        	messages : {
	        		couponName : {
	        			required : "优惠券名称不能为空"
	        		},
	        		valueYuan : {
	        			required : "优惠券面额不能为空"
	        		},
	        		fetchTypeYuan : {
	        			required : "优惠券获取条件不能为空"	        			
	        		},
	        		useTypeYuan : {
	        			required : "优惠券使用条件不能为空"	        			
	        		}

	        	},
	        	errorPlacement: function(error, element) { //指定错误信息位置 
	        		if (element.is(':radio') || element.is(':checkbox')||element.hasClass("fetchTypeYuan")||element.hasClass("useTypeYuan")) { //如果是radio或checkbox 
	        		var eid = element.attr('name'); //获取元素的name属性 
	        		error.appendTo(element.parent()); //将错误信息添加当前元素的父结点后面 
	        		} else { 
	        		error.insertAfter(element); 
	        		} 	        		
	        	}, 
	        	
	        });
			
		},
		
		initInputMask : function() {
	    	$('#price').inputmask("mask", {
	    		alias : "decimal",
	    		rightAlignNumerics : false
	    		
	    	});
	    },
		
		fetchTypeDeal : function(){
			if($("input[name='fetchType']:checked").val() =='1') {
				$("#fetchType").hide();
				$(".fetchTypeDetailDiv").hide();
			}else {
				$("#fetchType").show();
				$(".fetchTypeDetailDiv").show();
				if($("#fetchTypeInit").val() == 0){
					//初始化ztree
					this.loadCategoryTree("categoryTree");
					$("#fetchTypeInit").val(1);
				}
			}
			
		},
		
		showfetchTypeMenu : function(){
			$(".Tree").modal("show");
		},
		
		useTypeDeal : function(){
			if($("input[name='useType']:checked").val() =='1') {
				$("#useType").hide();
				$(".useTypeDetailDiv").hide();
			}else {
				$("#useType").show();
				$(".useTypeDetailDiv").show();
				if($("#useTypeInit").val() == 0){
					//初始化ztree
					this.loadCategoryTree("categoryTree2");
					$("#useTypeInit").val(1);
				}
			}
			
		},
		
		showuseTypeMenu : function(){
			$(".Tree2").modal("show");
		},
		
		//添加页初始化Ztree
		loadCategoryTree : function(targetName) {
			var setting;
			if(targetName == 'categoryTree2'){
				setting = {
						check: {
							//设置 zTree 的节点上是否显示 checkbox / radio 默认值: false
							enable: true,
							chkboxType: {"Y":"s", "N":"ps"},
							autoCheckTrigger: true
						},
						view: {
							//双击节点时，是否自动展开父节点的标识,默认值: true
							dblClickExpand: true
						},
						data: {
							simpleData: {
								enable: true
							}
						},
						callback: {
							beforeClick: this.beforeClick2,
							onCheck: this.onCheck2
						}
					};
			}else{
				setting = {
						check: {
							//设置 zTree 的节点上是否显示 checkbox / radio 默认值: false
							enable: true,
							chkboxType: {"Y":"s", "N":"ps"},
							autoCheckTrigger: true
						},
						view: {
							//双击节点时，是否自动展开父节点的标识,默认值: true
							dblClickExpand: true
						},
						data: {
							simpleData: {
								enable: true
							}
						},
						callback: {
							beforeClick: this.beforeClick,
							onCheck: this.onCheck
						}
					};
			}
		    var zNodes = $("#zNodes").val();
		    if(zNodes =='' || zNodes == null){
		    	zNodes = [];
		    	 $.ajax({
						type: "post",
						dataType: "json",
						url:urlPrefix + 'category/list',
						async:true,
						success: function(response){
							if(response.code == "ACK"){
								var tNodes = response.data;
								for (var i = 0, l = tNodes.length; i < l; i++) {
									var tempNode = new Object();
									tempNode.id=tNodes[i].cateId;
									tempNode.pId=tNodes[i].parentId;
									tempNode.name=tNodes[i].cateName;
									if(tempNode.pId == 0){
										tempNode.open=true;
									}
					                zNodes.push(tempNode);
					            }
								$.fn.zTree.init($("#"+targetName), setting, zNodes);
		        	 		}
						},
					});
		    }
		    //初始化ztree
			$.fn.zTree.init($("#"+targetName), setting, zNodes);
		},
		
		beforeClick : function(treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj("categoryTree");
			zTree.checkNode(treeNode, !treeNode.checked, null, true);
			return false;
		},
		
		beforeClick2 : function(treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj("categoryTree2");
			zTree.checkNode(treeNode, !treeNode.checked, null, true);
			return false;
		},
		
		//点击ztree时触发操作
		onCheck : function(e, treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj("categoryTree"),
			// true：获取 被勾选 的节点集合  false:获取 未勾选 的节点集合
			nodes = zTree.getCheckedNodes(true),
			v = "";
			var categoryIds = "";
			for (var i=0, l=nodes.length; i<l; i++) {
				if(!(nodes.length > 1 && nodes[i].isParent)){
					v += nodes[i].name + ",";
					categoryIds += nodes[i].id + ",";
				}
			}
			if (v.length > 0 ) v = v.substring(0, v.length-1);
			if (categoryIds.length > 0 ) categoryIds = categoryIds.substring(0, categoryIds.length-1);
			var cityObj = $("#fetchTypeDetail");
			$("#fetchTypeData").val(categoryIds);
			if(v !=''){
				$(".fetchTypeDetail").addClass("hidden");
			}else{
				$(".fetchTypeDetail").removeClass("hidden");
			}
			cityObj.html(v);
		},
		
		onCheck2 : function(e, treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj("categoryTree2"),
			// true：获取 被勾选 的节点集合  false:获取 未勾选 的节点集合
			nodes = zTree.getCheckedNodes(true),
			v = "";
			var categoryIds = "";
			for (var i=0, l=nodes.length; i<l; i++) {
				if(!(nodes.length > 1 && nodes[i].isParent)){
					v += nodes[i].name + ",";
					categoryIds += nodes[i].id + ",";
				}
			}
			if (v.length > 0 ) v = v.substring(0, v.length-1);
			if (categoryIds.length > 0 ) categoryIds = categoryIds.substring(0, categoryIds.length-1);
			var cityObj = $("#useTypeDetail");
			$("#useTypeData").val(categoryIds);
			if(v !=''){
				$(".useTypeDetail").addClass("hidden");
			}else{
				$(".useTypeDetail").removeClass("hidden");
			}
			cityObj.html(v);
		},

		changeStartDate : function(e) {
			var endDate =  this.closest(".date").find(".endDate");
			if (e.date) {
				endDate.datetimepicker('setStartDate', this.val());
			}else{
				endDate.datetimepicker('setStartDate', new Date());
			}
			if(this.val()=="" && this.next().css('display') == 'inline-block'){
				this.next().css('display','none');
				this.parent().next(".dateError").removeClass("hidden");
			}else{
				this.next().css('display','inline-block');
				this.parent().next(".dateError").addClass("hidden");
			}
		},

		changeEndDate : function(e) {
			var date = new Date();
			var year = date.getFullYear();
			var month = date.getMonth()+1;
			var day = date.getDate();
			var startDate =  this.closest(".date").find(".beginDate");

			if (e.date) {
				startDate.datetimepicker('setEndDate', this.val());
			}else{
				startDate.datetimepicker('setEndDate', '');
			};
			if(this.val()=="" && this.next().css('display') == 'inline-block'){
				this.next().css('display','none');
				this.parent().next(".dateError").removeClass("hidden");
			}else{
				this.next().css('display','inline-block');
				this.parent().next(".dateError").addClass("hidden");
			}
		},
		
		checkedSetVal : function() {
			if(this.is(':checked')){
				this.attr('value','1');
			}else {
				this.attr('value','0');
			}
			if($("#webUse").is(':checked')) {
				$(".platError").addClass("hidden");
			}else {
				$(".platError").removeClass("hidden");
			}
		},
		
		checkedSetVal2 : function() {
			if(this.is(':checked')){
				this.attr('value','1');
			}else {
				this.attr('value','0');
			}
			if($("#useCategory").is(':checked')) {
				$(".categoryError").addClass("hidden");
			}else {
				$(".categoryError").removeClass("hidden");
			}
		},
		
		validatePlat : function() {
			if(!($("#webUse").is(':checked'))) {
				$(".platError").removeClass("hidden");
				return false;
			}
			return true;
		},
		
		validateCategoryType : function() {
			if(!($("#useCategory").is(':checked'))) {
				$(".categoryError").removeClass("hidden");
				return false;
			}
			return true;
		},
		
		validateDate : function() {
			var validateDateFlag = true;
			var usDate = $("#usDate").val();
			var ueDate = $("#ueDate").val();

			if(usDate=="") $("#usDate").parent().next(".dateError").removeClass("hidden");
			else $("#usDate").parent().next(".dateError").addClass("hidden");

			if(ueDate=="") $("#ueDate").parent().next(".dateError").removeClass("hidden");
			else $("#ueDate").parent().next(".dateError").addClass("hidden");

			if(usDate==""||ueDate=="")  validateDateFlag =  false;
			else validateDateFlag =  true;
			return validateDateFlag;

		},
		
		validateCategory :function(){
			var falg = true;
			if($("input[name='fetchType']:checked").val() =='0') {
				if($("#fetchTypeData").val() == ''){
					$(".fetchTypeDetail").removeClass("hidden");
					falg =  false;
				}
			}
			if($("input[name='useType']:checked").val() =='0') {
				if($("#useTypeData").val() == ''){
					$(".useTypeDetail").removeClass("hidden");
					falg =  false;
				}
			}
			return falg;
		},
		
		
		submitForm : function(){
			var formFlag = this.validator.form();
			//webUse
			var platFlag = this.validatePlat();
			//时间校验
			var dateFlag = this.validateDate();
			var categoryTypeFlag = this.validateCategoryType();
			var categoryFlag = this.validateCategory();
			var $form = $('#couponAddForm');
			if(!$form.validate().form() || !formFlag||!platFlag|| !dateFlag || !categoryTypeFlag ||!categoryFlag) {
				return false;
			}else {
				this.doSave();
			}
		},
		
		
		
		
		doSave : function(){
			var that = this;
			var couponAddForm = {
					couponName : $("#couponName").val(),
					valueYuan  : $("#valueYuan").val(),
					effectDate : $("#usDate").val(),
					invalidDate: $("#ueDate").val(),
		    		webUse     : $("#webUse").val(),
		    		type     : $("#useCategory").val(),
		    		fetchValueLimitYuan : $("#fetchTypeYuan").val(),
		    		userValueLimitYuan : $("#useTypeYuan").val(),
		    		fetchType : $("input[name='fetchType']:checked").val(),
					fetchTypeDetail : $("#fetchTypeData").val(),
					useType : $("input[name='useType']:checked").val(),
					useTypeDetail : $("#useTypeData").val(),
			};
            if(couponAddForm.effectDate && $.GLOBAL.utils.isDate(couponAddForm.effectDate)) {
	    		couponAddForm.effectDate += " 00:00:00";
            }
            if(couponAddForm.invalidDate && $.GLOBAL.utils.isDate(couponAddForm.invalidDate)) {
            	couponAddForm.invalidDate += " 23:59:59";
            }
			
			$.ajax({
				type: "POST",
				url: "saveCoupon",
				contentType: "application/json;charset=utf-8",
				data: JSON.stringify(couponAddForm),
				dataType: "json",
				success: function(message){
					if(message.code == "ACK"){
						window.location.href= urlPrefix + "coupon/";
					}
				},
				error:function(message){
					//alert('shibai');
				}
			});
		},
		
		
			
		init:function(){
			var _self = this;
			_self.initEvents();
		}
	}.init();
	
	
});