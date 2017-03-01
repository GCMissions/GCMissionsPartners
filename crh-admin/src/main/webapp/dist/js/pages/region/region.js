var regionApp = {
	getProvinceUrl : "regionCity/",
	allProvinceUrl : "regionCity/list",
	provinceUrl : "region/findAllProvOpen",	
	cityUrl     : "region/findProvOpenCity/{{regionId}}",
    saveUrl     : "regionCity/update",
    selectedProvinceId : 0,
    selectedCityIds    : [],
    provinceItem : ' <span class="mr-15 provinceItem" data-id="{{regionId}}">{{regionName}} (<span class="openNum">{{openNum}}</span>/{{cityNum}})</span>',
    cityItem     : '<span class="mr-15 {{active}}" data-id="{{id}}">{{name}}</span>',
	previousOpenNum : 0,
	
    init : function() {
		this.$provinceList = $('.provinceList');
        this.$cityList     = $('.cityList div.form-group');
        this.$saveBtn      = $('#save');
        this.initHandler();
        this.loadProvince();
        
	},
	
	loadProvince : function() {
        var that = this;
		$.ajax(
        { 
            type         : 'POST',
            url          : urlPrefix + that.allProvinceUrl,
            dataType     : 'json',
            contentType  : 'application/json'
        })
        .done(function(result) {
            if(result.code == "ACK") {
                that.fillProvinceList(result.data);
                $(".region").find('span.provinceItem').eq(0).click();
            }
        })
        .fail(function(result) {
            $('body').loadingInfo("error", message("admin.message.error"));
            
        });
	},
	
    initHandler : function() {
        this.$provinceList.on("click", "span.provinceItem", _(this.getCityList).bind(this));
        this.$cityList.on("click", "span", _(this.toggleCity).bind(this));
        this.$saveBtn.on("click", _(this.save).bind(this));
        this.$saveBtn.prop("disabled", true);
    },
    
    save : function() {
        var activeCitys = this.$cityList.find('span'),
            that = this;
        that.selectedCityIds  = [];
        var postData = {
                "list" : []
            };
        _(activeCitys).each(function(data) {
        	var dataId = data.getAttribute("data-id");
        	if($(data).hasClass('active')) {
        		postData.list.push({
        			"id" : dataId,
        			"name" :"",
        			"openFlag" : "1"
        		})
        	}else{
        		postData.list.push({
        			"id" : dataId,
        			"name" :"",
        			"openFlag" : "0"
        		})
        	}
        });
        
        that.$saveBtn.prop("disabled", true);
        $.ajax(
        { 
            type         : 'POST',
            url          : urlPrefix + that.saveUrl,
            dataType     : 'json',
            contentType  : 'application/json',
            data         :  JSON.stringify(postData)
        })
        .done(function(result) {
            if(result.code == "ACK") {
                 that.$cityList.loadingInfo("success", message("保存成功! "));
                 window.location.href = urlPrefix + that.getProvinceUrl;
            }
        })
        .always(function() {
            that.$saveBtn.prop("disabled", false);
        })
        .fail(function(result) {
            that.$cityList.loadingInfo("error", message("admin.message.error"));
            
        });
    },
    
    getCityList : function(e) {
    	var that = this,
    		$target = $(e.target),
    		previousFinalNum = $(".region div>span.active").find('span');
    	if(that.previousOpenNum != previousFinalNum.text()) {
    		that.dialog =  BootstrapDialog.show({
                title: '提示信息',
                type : BootstrapDialog.TYPE_WARNING,
                message: '当前的操作还没保存，确定要切换到其他省份么?',
                draggable: true,
                size : BootstrapDialog.SIZE_SMALL,
                buttons: [{
                    label: '保存当前操作',
                    cssClass: 'btn-primary saveAddEditTpl',
                    action: function(dialog) {
                    	dialog.close();
                        that.save();
                    }
                }, {
                    label: '直接切换到其他省份',
                    action: function(dialog) {
                        dialog.close();
                        previousFinalNum.text(that.previousOpenNum);
                        that.callCityList($target);
                    }
                }]
            });
    	}else{
    		that.callCityList($target);
    	}
    },
    
    callCityList : function(target) {
    	var that = this,
			regionId = target.data('id') ||  target.parent().attr('data-id');
    	$(".region div>span.active").removeClass('active');
    	target.data('id')? target.addClass('active'): target.parent('span').addClass('active');
		that.selectedProvinceId  = regionId;
		that.$saveBtn.prop("disabled", true);
		$.ajax({ 
			type         : 'GET',
			url          : urlPrefix + that.cityUrl.template({regionId : regionId}),
			dataType     : 'json',
			contentType  : 'application/json'
		})
		.done(function(result) {
			if(result.code == "ACK") {
				that.fillCityList(result.data);
			}
		})
		.always(function() {
			that.$saveBtn.prop("disabled", false);
		})
		.fail(function(result) {
			$('body').loadingInfo("error", message("admin.message.error"));
			
		});
    },
    
    fillCityList : function(data) {
        var that = this,
        	result = "",
        	count = 0;
        _(data).each(function(value, index) {
        	if(value.openFlag == "1") {
                value.active = "active";
                count ++;
            }
            result += that.cityItem.template(value);
        });
        that.$cityList.html(result);
        $(".region div>span.active").find('span').text(count);
        that.previousOpenNum = count;
    },
    
    fillProvinceList : function(data) {
        var that = this,
        	result = "";
        _(data).each(function(value, index) {
            
            result += that.provinceItem.template(value);
        });
        that.$provinceList.html(result);
    },
    
    toggleCity : function(e) {
         var $target = $(e.target),
             regionId = $target.data('id'),
             that = this;
        $target.toggleClass('active');   
        
        var openNum = $('.provinceItem.active').find("span.openNum");
        var num = openNum.text();
        if($target.hasClass('active')) {
        	openNum.text(parseInt(num)+1);
        }else{
        	openNum.text(parseInt(num)-1);
        }
    }
    
};