<#assign headComponents = ["bootTable", "innerPage"] >
<#include "/header.ftl" />
<link rel="stylesheet" href="${uiBase}vendor/bootstrap-slider/boostrap-slider.css" >
<link rel="stylesheet" href="${uiBase}/css/pages/activity/activity_info.css?v=${resourceVersion}">
</head>
<body class="hold-transition skin-blue sidebar-mini">
    <div class="wrapper" style="overflow:visible">
      <!-- Content Wrapper. Contains page content -->
      <div class="content-wrapper page-content-wrapper">
       <!-- Content Header (Page header) -->
        <section class="content-header">
         <ol class="breadcrumb">
            <li><i class="fa fa-dashboard"></i>活动管理</li>
            <li>活动信息</li>
          </ol>
        </section>
        
        <!-- Main content -->
        <div class="row pad">
        	<div class="col-md-12">
        		<div class="box box-primary">
        	 		<div class="box-body nav-tabs-custom"> 
						<!-- tab 页标题 -->
						<div class="myNav">
							<ul class="nav">
								<li><a href="#tab_1" data-toggle="tab">商品基础信息</a></li>
		                 		<li class="active"><a href="#tab_2" data-toggle="tab">库存与规格</a></li>  
		                 		<li><a href="#tab_3" data-toggle="tab">购买信息</a></li> 
		                 		<li><a href="#tab_4" data-toggle="tab">商品详情介绍</a></li>
		              		</ul>
		              		<br class="clearfix" />
	              		</div>
	               		 
		                <!-- TAB页 -->
		                <div class="tab-content">
		                	<!-- 商品基础信息 -->
							<div class='tab-pane' id="tab_1">
								<div class="tab_div">
									
								</div>
		                  	</div>
		                  	<!-- 商品基础信息 end -->
		                  	
		                  	<!-- 库存与规格 -->
							<div class='tab-pane active' id="tab_2">
								<div class="tab_div">
									<!-- form -->
									<form role="form">
						                <div class="form-group row">
						                	<div class="col-md-2" style="text-align:right;">
						                  		<label><span class="requiredField">*</span>商品活动日期：</label>
						                    </div>
						                    <div class="col-md-10 actDate">
							              	   <a id="addActDate" href="javascript:void(0);" target="_self">+添加活动日期</a>
							                   <ul class="clearfix">
							                  	
							                   </ul>
						                    </div>
						                </div>
						                
						                <div class="form-group row">
						                	<div class="col-md-2" style="text-align:right;">
						                    	<label><span class="requiredField">*</span>商品规格：</label>
						                	</div>
						                  	<div class="col-md-10">
						                  		<div class="mainSpec">
						                  			<div>
						                  				<select class="form-control" disabled>
									                    	<option selected="selected">人数</option>
								                    	</select>
								                    	<div class="subSpec clearfix"> 
								                    		<ul class="clearfix">
								                    		</ul>
								                    		<div class="selSubSpec">
								                    			<input type="text" />
 																<button type="button" class="btn btn-primary" id="addSubSpecConfirm">确定</button>
								                    			<button type="button" class="btn btn-primary" id="addSubSpecCancel">取消</button>
								                    		</div>
						                  					<a href="javascript:void(0);">+添加</a>
								                    	</div>
						                  			</div>
						                  		</div>
						                  		<div class="selMainSpec"> 
						                  			<select class="form-control">
									                    <option disabled="disabled">人数</option>
									                    <option>课程</option>
									                    <option>套餐</option>
									                    <option>系列</option>
									                    <option>规格</option>
								                    </select>
								                    <button type="button" class="btn btn-primary" id="addMainSpecConfirm">确定</button>
								                    <button type="button" class="btn btn-primary" id="addMainSpecCancel">关闭</button>
						                  		</div>
								                <button type="button" class="btn btn-primary" id="addMainSpec">添加主规格</button>
						                    </div>
						                </div>
						                
						                <div class="form-group row">
						                	<div class="col-md-2" style="text-align:right;">
						                    	<label><span class="requiredField">*</span>库存：</label>
						                	</div>
						                  	<div class="col-md-10" style="background-color:#d7d7d7;">
						                  		<div class="table-responsive">
						                  			<table class="table">
						                  				<thead>
							                  				<tr>
							                  					<th>商品规格</th>
							                  					<th style="text-align:center;">默认每次活动库存数量</th>
							                  					<th style="text-align:center;">价格（元）</th>
							                  				</tr>
						                  				</thead>
						                  				<tbody class="stockInfo">
						                  				
						                  				</tbody>
						                  			</table>
						                  		</div>
						                    </div>
						                </div>
						                <div class="form-group row">
						                	<div class="col-md-2"></div>
						                	<div class="col-md-10">
												<label class="checkbox-inline">
											      <input type="radio" name="stockType" id="sepc_stock" value="0" checked> 按规格
											    </label>
											    <label class="checkbox-inline">
											      <input type="radio" name="stockType" id="total_stock" value="1"> 按人数
											      <input type="text" id="modify_num"/>
											      <a href="javascript:void(0)" id="modifyStock">修改</a> 
											    </label>
											    <label class="checkbox-inline">
											      <input type="radio" name="stockType" id="no_stock" value="2"> 该商品不需要库存
											    </label>
						                	</div>
						                </div>
						                <div class="form-group row">
						                	<div class="col-md-2"></div>
						                	<div class="col-md-10">
												<label class="checkbox-inline">
													<span class="requiredField">*</span>显示库存
												</label>
											    <label class="checkbox-inline">
											      <input type="radio" name="show_stock" id="show_stock" value="0" checked> 是
											    </label>
											    <label class="checkbox-inline">
											      <input type="radio" name="show_stock" id="hide_stock" value="1"> 否
											    </label>
						                	</div>
						                </div>
						                <div style="text-align:center">
						                	<button type="button" class="btn btn-primary btn-save" id="saveStockInfo">保&nbsp;&nbsp;存</button>
						                 </div>
						              </form> <!-- form end -->
								</div>
		                  	</div>
		                  	<!-- 库存与规格  end -->
		                  	
		                  	<!-- 购买信息 -->
							<div class='tab-pane' id="tab_3">
								<div class="tab_div">
									<!-- form -->
									<form role="form">
						                <div class="form-group row">
						                	<div class="col-md-2" style="text-align:right;">
						                  		<label><span class="requiredField">*</span>每人限购：</label>
						                    </div>
						                    <div class="col-md-10" id="limitNum">
							              	   <input type="text" value="0"/>
							              	   <span>（0表示不限购）</span>
						                    </div>
						                </div>
						                <div class="form-group row">
						                	<div class="col-md-2" style="text-align:right;">
						                  		<label><span class="requiredField">*</span>用户必填信息：</label>
						                    </div>
						                    <div class="col-md-10 requireInfo">
						                     	<label class="checkbox-inline">
					                       			<input type="radio" value="0" name="is_need" id="yes_need" checked/>需要
					                       		 </label>
					                       		<div>
					                       			<select class="form-control">
					                       				<option selected="selected">参与人数</option>
					                       				<option>姓名</option>
					                       			</select>
					                       			<a href="javascript:void(0);">+添加</a>
					                       			<ul>
					                       			</ul>
					                       		</div>
					                       		<label class="checkbox-inline">
					                       			<input type="radio" value="1" name="is_need" id="no_need"/>不需要填写必填信息
					                       		</label>
						                    </div>
						                </div>
						                <div class="form-group row">
						                	<div class="col-md-2" style="text-align:right;">
						                  		<label><span class="requiredField">*</span>开售时间：</label>
						                    </div>
						                    <div class="col-md-10 sale_time">
							              	    <label class="checkbox-inline">
											      <input type="radio" name="sale_type" id="right_sale" value="0" checked> 立即开售
											    </label>
											    <label class="checkbox-inline">
											      <input type="radio" name="sale_type" id="ontime_sale" value="1"> 定时开售
											    </label>
											    <div class="dateDiv" style="margin-bottom: 0px;margin-left:10px">
										    		<input size="10" type="text" id="sale_start" class="form-control keyword" placeholder="开始时间" readonly>
										    		<span class="add-on" style="display:none"><i class="icon-remove"></i></span>
										   			<span class="add-on"><i class="icon-calendar"></i></span>
												</div>
												<div class="dateDiv" style="margin-bottom: 0px;margin-left:20px">
										    		<input size="10" type="text" id="sale_end" class="form-control keyword" placeholder="结束时间" readonly>
										    		<span class="add-on" style="display:none"><i class="icon-remove"></i></span>
										    		<span class="add-on"><i class="icon-calendar"></i></span>
												</div>
												<span>（不设定表示该商品一直有效）</span>
						                    </div>
						                </div>
						                <div style="text-align:center">
						                	<button type="button" class="btn btn-primary btn-save" id="savePurchaseInfo">保&nbsp;&nbsp;存</button>
						                </div>
						              </form> 
						              <!-- form end -->
								</div>
		                  	</div>
		                  	<!-- 购买信息  end -->
		                  	
		                  	<!-- 商品详情介绍 -->
							<div class='tab-pane' id="tab_4">
								<div class="tab_div">
									
								</div>
		                  	</div>
		                  	<!-- 商品详情介绍  end -->
		                </div>
		                <!-- TAB页 end -->
					</div>
				</div>
			</div>
        </div>
        <!-- /.content -->
        <div class="clearfix"></div>
      </div><!-- /.content-wrapper -->
   </div><!-- ./wrapper -->
   
<!-- 活动时间选择模态框（Modal） -->
<div class="modal fade" id="dateDialog" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
   <div class="modal-dialog">
      <div class="modal-content">
         <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                  &times;
            </button>
            <h4 class="modal-title"> 添加活动日期 </h4>
         </div>
         <div class="modal-body">
        	<form id="addEditForm" method="post" class="form-horizontal">
				<div class="box-body nav-tabs-custom"> 
					<!-- tab 页标题 -->
					<div>
						<ul class="nav nav-tabs">
				     		<li class="active"><a href="#time_1" data-toggle="tab">日期</a></li>  
				     		<li><a href="#time_2" data-toggle="tab">时间段</a></li> 
				  		</ul>
				  		<br class="clearfix" />
					</div>
					 
				    <!-- TAB页 -->
				    <div class="tab-content">
				    	<!-- 日期 -->
						<div class='tab-pane active' id="time_1">
							<div class="tab_div">
								<div class="box-body form-horizontal">
									<div class="form-group row">
										<label class="col-sm-2 control-label" >月份</label>
										<div class="col-sm-10">
											<input id="monthSlider" type="text" data-slider-min="1" data-slider-max="12" data-slider-step="1" data-slider-value="3"/>
										</div>
									</div>
									<div class="form-group row">
									   <label class="col-sm-2 control-label">日期</label>
									   <div class="col-sm-10">
									  		<input id="daySlider" type="text" data-slider-min="1" data-slider-max="30" data-slider-step="1" data-slider-value="3"/>
									   </div>
									</div>
								</div>
							</div>
				      	</div>
				      	<!-- 日期 end -->
				      	<!-- 时间段 -->
						<div class='tab-pane' id="time_2">
							<div class="tab_div">
								<div class="box-body form-horizontal">
									<div class="form-group row">
										<label class="col-sm-3 control-label" >开始日期</label>
										<div class="col-sm-9 dateDiv" style="margin-bottom: 0px;">
										    <input size="25" type="text" id="act_start" class="form-control keyword" readonly>
										    <span class="add-on" style="display:none"><i class="icon-remove"></i></span>
										    <span class="add-on"><i class="icon-calendar"></i></span>
										</div>
									</div>
									<div class="form-group row">
									   <label class="col-sm-3 control-label">结束日期</label>
									   <div class="col-sm-9 dateDiv" style="margin-bottom: 0px;">
										    <input size="25" type="text" id="act_end" class="form-control keyword" readonly>
										    <span class="add-on" style="display:none"><i class="icon-remove"></i></span>
										    <span class="add-on"><i class="icon-calendar"></i></span>
										</div>
									</div>
								</div>
							</div>
				      	</div>
				      	<!-- 时间段 end -->
				     </div>
				</div>
			</form>
         </div>
         <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            <button type="button" class="btn btn-primary" id="addActDateComfirm"> 确定 </button>
         </div>
      </div><!-- /.modal-content -->
     </div>
</div><!-- /.modal -->
<!-- 活动时间选择模态框（Modal） -->

<!-- 通用消息提示框 -->
<div class="modal fade" id="alertMsgDialog" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
   
</div><!-- /.modal -->

</body>
<#include "/footer.ftl" />
<script src="${uiBase}vendor/bootstrap-slider/bootstrap-slider.min.js" ></script>
<script type="text/javascript" src="${uiBase}js/pages/activity/activity_info.js?v=${resourceVersion}"></script>
<script id="modifyStockModalTpl" type="text/html">
<div class="table-responsive">
	<table class="table">
		<thead>
			<tr>
				<th style="text-align:center;">活动日期</th>
				<th style="text-align:center;">库存数</th>
			</tr>
		</thead>
		<tbody>
			{{if actDateList}}
				{{each actDateList}}
					<tr style="text-align:center;"> 
						<td>{{$value}}</td><td><input type="text" value="{{count}}"/></td>
					</tr>
				{{/each}}
			{{/if}}
		</tbody>
	</table>
</div>
</script>
<script id="mainSpecTpl" type="text/html">
<div>
	<select class="form-control" disabled>
        <option>{{mainSpec}}</option>
	</select>
	<a href="javascript:void(0);" id="delMainSpec" target="_self">删除</a>
	<div class="subSpec clearfix"> 
		<ul class="clearfix">
		</ul>
		<div class="selSubSpec">
			<input type="text" />
			<button type="button" class="btn btn-primary" id="addSubSpecConfirm">确定</button>
			<button type="button" class="btn btn-primary" id="addSubSpecCancel">取消</button>
		</div>
		<a href="javascript:void(0);" id="addSubSpec">+添加</a>
	</div>
</div>
</script>
<script id="stockInfoTpl_1" type="text/html">
	<tr subspec="{{subSpec}}">
		<td style="vertical-align: middle;">
			<div class="spec_area form-group">
				<label>{{subSpec}}</label>
				<div>
					<label>最小单位量：</label>
					<input type="text" class="form-control unit_num" value="0"/>
					<span>×</span>
					<input type="text" class="form-control group_num" value="0"/>
				</div>
			</div>
		</td>
		<td style="vertical-align: middle;">
			<div class="num_area">
				<span>0</span>
			</div>
		</td>
		<td style="vertical-align: middle;">
			<div class="price_area">
				{{if secondList.length > 0}}
					<ul>
					{{each secondList as data index}}
						<li subspec="{{data}}">
							<span>{{data}}</span>
							{{if thirdList.length > 0 }}
								<ul>
								{{each thirdList as value seqNum}}
									<li subspec="{{value}}"><span>{{value}}</span><input type="text" /></li>
								{{/each}}
								</ul>
							{{else}}
								<input type="text" />
							{{/if}}
						</li>
					{{/each}}
					</ul>
				{{else}}
					<input type="text"/>
				{{/if}}
			</div>
		</td>
	</tr>
</script><!-- 第一个主规格添加子规格 -->

<script id="stockInfoTpl_2" type="text/html">
{{if secondList.length > 0}}
	<ul>
		{{each secondList as data index}}
			<li subspec="{{data}}">
				<span>{{data}}</span>
				{{if thirdList.length > 0}}
					<ul>
					{{each thirdList as value seqNum}}
						<li subspec="{{value}}">
							<span>{{value}}</span><input type="text" />
						</li>
					{{/each}}
					</ul>
				{{else}}
					<input type="text"/>
				{{/if}}
			</li>
		{{/each}}
	</ul>
{{/if}}
</script><!-- 第二、三个主规格添加子规格 -->

<script id="alertMsgModalTpl" type="text/html">
   <div class="modal-dialog">
      <div class="modal-content">
         <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                  &times;
            </button>
            <h4 class="modal-title" id="myModalLabel"> {{title}}</h4>
         </div>
         <div class="modal-body">{{msg}}</div>
         <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
         </div>
      </div><!-- /.modal-content -->
</script>
</html>
