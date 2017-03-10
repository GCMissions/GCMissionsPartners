<script type="text/javascript">
	var fileUrlPrefix = "${file}";
	var systemName    = "Church";
	var urlPrefix     = "${urlPrefix}";
	var uiBase        = "${uiBase}";
</script>

<!-- jQuery 2.1.4 -->
<script src="${uiBase}vendor/jQuery/jQuery-2.1.4.min.js"></script> 

<!-- jQuery UI 1.11.4 -->
<script src="${uiBase}vendor/jQueryUI/jquery-ui.min.js"></script> 
<!-- Resolve conflict in jQuery UI tooltip with Bootstrap tooltip -->
<script type="text/javascript">
  $.widget.bridge('uibutton', $.ui.button);
</script>
<!-- Bootstrap 3.3.5 -->
<script src="${uiBase}vendor/bootstrap/js/bootstrap.min.js"></script>
<!-- FastClick -->
<script src="${uiBase}vendor/fastclick/fastclick.min.js"></script>
<!-- underscore -->
<script src="${uiBase}vendor/underscore-min.js"></script>
<!-- tencent template compact syntax version -->
<script src="${uiBase}vendor/template.js"></script>
<#list headComponents as x>
    <#if x == "ueditor">
    <#elseif x == 'commonPage'>	
    <#assign mainPage = false >
    <#elseif x == 'bootTable'>	
<script src="${uiBase}vendor/bootstrap-table/bootstrap-table.js" ></script>
<script src="${uiBase}vendor/bootstrap-table/locale/bootstrap-table-zh-CN.min.js" ></script>
    <#elseif x = "innerPage">
    	<#assign mainPage = false >    
    	
    <#elseif x == 'mainPage'>	
        <#assign mainPage = true >    
    </#if>
</#list>
<script src="${uiBase}vendor/bootstrap-dialog/js/bootstrap-dialog.min.js" ></script>
<script src="${uiBase}js/global.js?v=${resourceVersion}" ></script>
<script src="${uiBase}vendor/jquery.blockUI.js" ></script>
<#if mainPage == true >
<script src="${uiBase}js/main.js?v=${resourceVersion}" ></script>
<script src="${uiBase}js/task.js?v=${resourceVersion}" ></script>
<script src="${uiBase}js/content_tabs.js?v=${resourceVersion}" ></script>
<#else>
<script src="${uiBase}js/pages/pages.js?v=${resourceVersion}" ></script>
<script src="${uiBase}vendor/bootstrap-select/js/bootstrap-select.js" ></script><#-- 源码有bug 只对bootstrap-select.js 做了修改. min 未改-->
<script src="${uiBase}vendor/bootstrap-datatimepicker/js/bootstrap-datetimepicker.min.js" ></script>
<script src="${uiBase}vendor/jquery-validator/jquery.validate.js" ></script>
<script src="${uiBase}vendor/jquery-validator/additional-methods.min.js" ></script>
<script type="text/javascript" src="${uiBase}vendor/ueditor1_4_3_2/ueditor.config.js"></script>
<script type="text/javascript" src="${uiBase}vendor/ueditor1_4_3_2/ueditor.all.min.js"></script>
<script type="text/javascript" src="${uiBase}vendor/jquery.qrcode.min.js"></script>
</#if>