<#assign headComponents = ["bootTable"] >
<#include "/header.ftl" /> 
<link rel="stylesheet" href="${uiBase}/css/pages/product.css?v=${resourceVersion}">
<style>

.datetimepicker   table.table-condensed{width:190px}  
.datetimepicker   table.table-condensed thead{display: none  !important}
</style>
</head>
<body class="hold-transition skin-blue sidebar-mini">
    <div class="wrapper" style="overflow:visible">
        <div class="content-wrapper page-content-wrapper">
         	<section class="content-header">
         
	          <ol class="breadcrumb">
	            <li><i class="fa fa-dashboard"></i>系统设置</li>
	            <li class="active">
				参数设置
	            </li>
	          </ol>
          
          
            </section>
            
            
           
            <div class="row pad">
                <div class="col-md-12">
                  <!-- Custom Tabs -->
                   <form role="form" class="form-horizontal" method=post id="mainForm">
                  <div class="nav-tabs-custom">
                    <ul class="nav nav-tabs">
                      <li class="active"><a href="#tab_1" data-toggle="tab">消费者端</a></li>
                      <li><a href="#tab_2" data-toggle="tab">平台系统</a></li>
                      <li><a href="#tab_3" data-toggle="tab">终端配送商</a></li>
                      <li><a href="#tab_4" data-toggle="tab">APP版本</a></li>
                      <li><a href="#tab_5" data-toggle="tab">APP活动广告</a></li> 
                    </ul>
                    <div class="tab-content">
                      <div class="tab-pane active" id="tab_1">
                          <div class="box-body">
                             
                              <div class="form-group ">
                                  <label class="col-sm-2 control-label">消费者端客服热线：</label>
                                  <div class="col-sm-5">
                                    <input type="text" class="form-control" name="C_hotline"  value="${paraList.c_hotline}">
                                  </div>
                                  <div class="col-sm-4">
                                  </div>
                              </div>
                              <div class="form-group ">
                                <label class="col-sm-2 control-label">配送时间(分钟)：</label>
                                  <div class="col-sm-5">
                                    <input type="text" class="form-control" name="C_timeSpace" maxlength=3 value="${paraList.c_timeSpace}"> 
                                  </div>
                                  <div class="col-sm-4">
                                  </div>
                              </div>
                              <div class="form-group  ">
                                <label class="col-sm-2 control-label"> 配送时间：</label>
                                  <div class="col-sm-8">
                                  		<div class=" no-padding datetimeInputGroup pull-left"  >
                                           <div class="input-group date datetimeInput  no-padding pull-left"   data-date="${paraList.c_orderTimeStart}"  data-date-type='time'     data-date-format="hh:ii"  >
                                                <input class="form-control startTimeSelection" style="width:161px" size="16"  name="C_orderTimeStart"   type="text" id="startDate" value="${paraList.c_orderTimeStart}"  readonly placeholder="开始时间">
                                                 <span class="add-on" style="display: none;"><i class="icon-remove"></i></span>
                                        		<span class="add-on"><i class="icon-calendar"></i></span>
                                            </div>
                                             <span class="pull-left textTo">至</span> 
                                            <div class="input-group date datetimeInput no-padding pull-right"    data-date="${paraList.c_orderTimeEnd}"  data-date-type='time'   data-date-format="hh:ii"   >
                                                <input class="form-control endTimeSelection" id="endDate"  style="width:161px" name="C_orderTimeEnd" size="16" type="text" value="${paraList.c_orderTimeEnd}" readonly placeholder="结束时间">
                                                 <span class="add-on" style="display: none;"><i class="icon-remove"></i></span>
                                       			 <span class="add-on"><i class="icon-calendar"></i></span>
                                            </div>
                                             
                                        </div>
                                  </div>
                              </div>
                              
                               <div class="form-group ">
                                <label class="col-sm-2 control-label">消费者可下单天数(天)：</label>
                                  <div class="col-sm-5">
                                    <input type="text" class="form-control" name="c_orderDay" maxlength=3 value="${paraList.c_orderDay}"> 
                                  </div>
                                  <div class="col-sm-4">
                                  </div>
                              </div>
                             
                              <div class="form-group ">
                                <label class="col-sm-2 control-label">客服QQ：</label>
                                  <div class="col-sm-5">
                                    <input type="text" class="form-control" name="c_serviceQQ"  value="${paraList.c_serviceQQ}"> 
                                  </div>
                                  <div class="col-sm-4">
                                  </div>
                              </div>
                              
                              <div class="form-group ">
                              <label class="col-sm-2 control-label">认证服务提示：</label>
                                <div class="col-sm-5">
                                  <input type="text" class="form-control" name="c_authServiceHint"  value="${paraList.c_authServiceHint}"> 
                                </div>
                                <div class="col-sm-4">
                                </div>
                            </div>
                              
                              
                          </div>
                      </div>		
                       <!-- /.tab-pane -->
                      <div class="tab-pane" id="tab_2">
                         <div class="box-body">
                          
							  <div class="form-group  ">
                                <label class="col-sm-2 control-label">订单最大超时时长(分钟)：</label>
                                <div class="col-sm-5">
                              
                                <input type="text" class="form-control" name="remindTime"    value="${paraList.remindTime}"  maxlength="3"> 
                                <br>(超过设置时间，平台将进行催单提醒)
                                
                                </div>
                              </div>
                              
                               <div class="form-group  ">
                                <label class="col-sm-2 control-label">超时订单轮询时间(秒)：</label>
                                <div class="col-sm-5">
                              
                                <input type="text" class="form-control" name="overTimeOrderInterval"   value="${paraList.overTimeOrderInterval}" > 
                              
                                </div>
                              </div>
                              <div class="form-group  ">
                                <label class="col-sm-2 control-label">催单后未接单时间(分)：</label>
                                <div class="col-sm-5">
                              
                                <input type="text" class="form-control" name="overTimeOrderTwiceInterval"   value="${paraList.overTimeOrderTwiceInterval}"  maxlength="4"> 
                                
                                
                                </div>
                              </div>
                              <div class="form-group  ">
                                <label class="col-sm-2 control-label">催单后未接单惩罚金额(元)：</label>
                                <div class="col-sm-5">
                              
                                <input type="text" class="form-control" name="overTimePunishAmount"   value="${paraList.overTimePunishAmount}"    maxlength="4"> 
                                
                                
                                </div>
                              </div>
                        </div>
                      </div>
                      <!-- /.tab-pane -->
                      
                       <div class="tab-pane" id="tab_3">
                         <div class="box-body">
                          
							  <div class="form-group  ">
                                <label class="col-sm-2 control-label">新订单轮询时间(秒)：</label>
                                <div class="col-sm-5">
                              
                                <input type="text" class="form-control" name="z_newOrderInterval"   value="${paraList.z_newOrderInterval}"  > 
                                
                                
                                </div>
                              </div>
                        </div>
                      </div>
                      <!-- /.tab-pane -->
                      
                      <!-- /.tab-pane -->
                      
                       <div class="tab-pane" id="tab_4">
                         <div class="box-body">
                         		
                         		<div class="form-group  ">
					             	  	 <input type="hidden"  name="appType_android" value="1" >   
										  <label class="col-sm-2 control-label">Android</label> 
		                              </div>	
		                          
									  <div class="form-group  ">
		                                <label class="col-sm-2 control-label">版本号：</label>
		                                <div class="col-sm-5">
		                                <input type="text" class="form-control" name="appVersion_android" id="appVersion_android"  value="${paraList.appVersion_android}"  > 
		                                </div>
		                              </div>
		                              
		                               <div class="form-group  ">
		                                <label class="col-sm-2 control-label">下载地址：</label>
		                                <div class="col-sm-5">
		                                <input type="text" id="appDownLoadUrl_android" class="form-control" name="appDownLoadUrl_android" value="${paraList.appDownLoadUrl_android}"  > 
		                                </div>
		                              </div>
		                              	
		                              	<div class="form-group ">
		                              	     <label class="col-sm-2 control-label"></label>
											 <div class="col-sm-5">
									               <input id="appFile" type="file" name="appFile" >
									         </div>
										</div>
		                              
		                              <div class="form-group  ">
		                                <label class="col-sm-2 control-label">是否强制更新：</label>
		                                <div class="col-sm-5">
		                                	<input  type="radio" name="appForceUpdate_android" value="1" <#if paraList.appForceUpdate_android = "1"> checked="true" </#if> > 是</input>  
		                                	<input  type="radio" name="appForceUpdate_android" value="0" <#if paraList.appForceUpdate_android = "0"> checked="true" </#if> > 否</input>  
		                                </div>
		                              </div>  
		                              
		                               <div class="form-group  ">
		                                <label class="col-sm-2 control-label">更新说明：</label>
		                                <div class="col-sm-5">
		                                <textarea rows="5" cols="15" class="form-control" name="appUpdateDesc_android"  >${paraList.appUpdateDesc_android}</textarea> 
		                                </div>
		                              </div>
                             
                        </div>
                      </div>
                      <!-- /.tab-pane -->
                      
                      <!-- /.tab-pane -->
                      
                       <div class="tab-pane" id="tab_5">
                         <div class="box-body">
                          
							  <div class="form-group  ">
                                <label class="col-sm-2 control-label">活动广告倒计时(秒)：</label>
                                <div class="col-sm-5">                            
                                <input type="text" class="form-control" id="appAd_countDown" name="appAd_countDown"   value="${paraList.appAdCountDown}"  > 
                                </div>
                              </div>
                              <div class="form-group  ">
                                <label class="col-sm-2 control-label">是否允许跳过：</label>
                                <div class="col-sm-5">                         
                                <#list appAdSkipFlagEnum as flag>   
                                	<input name="skipFlag" value="${flag.key}" type="radio"/>${flag.value}
                                </#list>
                                </div>
                                <input type="hidden" id="skipFlagVal" value="${paraList.appAdSkipFlag}"/>
                              </div>
                        </div>
                      </div>
                      <!-- /.tab-pane -->
                     
                    </div>
                    <!-- /.tab-content -->
                  </div>
                 
                  <!-- nav-tabs-custom -->
                  <div class="row">
                   <div class="col-sm-12 text-center">
                     <#if !isReview>
                    <button class="btn btn-primary submitMainForm" type="button"  >保存</button>
                    </#if>
                   
                   
                   </div>
                  </div>
                </div>
                 </form>
            </div>
           
            <div class="clearfix"></div>
        </div>
       
        <!-- /.row -->

    </div><!-- ./wrapper -->
   
  </body>
  <#include "/footer.ftl" />
	 <script src="${uiBase}/vendor/input-mask/jquery.inputmask.js"></script>
	<script type="text/javascript" src="${uiBase}/vendor/ajaxfileupload/ajaxfileupload.js"></script> 
  <script type="text/javascript" src="${uiBase}/js/pages/setting/setting.js?v=${resourceVersion}"></script> 

  <script>
  $(function() {
  	settingApp.init();
  });
  
  </script>
</html>
