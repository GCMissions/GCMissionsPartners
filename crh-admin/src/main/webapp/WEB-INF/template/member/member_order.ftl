<#assign headComponents = ["bootTable","bootDialog"] >
<#include "/header.ftl" />
<style>
	label.role_checkbox {
   		font-weight: 500;
   	    min-width: 38%;	
   	    margin-top: 6px;
	}
	.form-control {
    	width: 68%;
	}
	.fa-edit, .fa-trash, .fa-eye { 
		cursor: pointer;	
	}
</style>
</head>
<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper" style="overflow:visible">
      <!-- Content Wrapper. Contains page content -->
      <div class="content-wrapper page-content-wrapper">
       <!-- Content Header (Page header) -->
        <section class="content-header">
          <ol class="breadcrumb">
            <li><i class="fa fa-dashboard"></i> 平台用户管理</li>
          	<li>用户详情</li>
          </ol>
        </section>
        
        <!-- Main content -->
        <div class="row pad">
             <div class="col-md-12">
                <div class="box box-primary">
                    <div class="form-horizontal search-group" id="search-area" >
                    	<div class="box-body">
                    	<input type="hidden" id="memberId" value="${memberId}">
                    		<div class="col-sm-12">
                    			<label class="control-label" style="font-size: 25px;margin-bottom: 2%;"> 
                    			消费详情
                    			</label>
                    		</div>
                    		<div class="col-sm-12">
                    			<div class="col-sm-6">
                    				<label class="control-label">
                    				有效订单数：${memberOrderDetail.effectiveOrderCount}
                    				</label>
                    			</div>
                    			<div class="col-sm-6">
                    				<label class="control-label">
                    				有效消费金额（元）：${memberOrderDetail.effectivePrice}
                    				</label>
                    			</div>
                    		</div>
                    		<div class="col-sm-12">
                    			<div class="col-sm-6">
                    				<label class="control-label">
                    				退款次数：${memberOrderDetail.refundCount}
                    				</label>
                    			</div>
                    			<div class="col-sm-6">
                    				<label class="control-label">
                    				取消订单数：${memberOrderDetail.cancelOrderCount}
                    				</label>
                    			</div>
                    		</div>
                    	</div>
                    </div>
                    <div class="box-body">
                    <div class="col-sm-12">
                    	<label class="control-label" style="font-size: 25px;"> 
                    	用户订单
                    	</label>
                    </div>
                      <table id="dataList" class="table table-bordered table-hover" >
                      </table>
                    </div>
                    <div class="col-md-12" style="margin-top: 20px;">
						<div class="col-sm-3 col-sm-offset-5">
							<button class="btn  btn-success backPage" id="orderBack" ><i class="fa fa-backward"></i> 返  回</button>
						</div>
					</div>
                </div>
             </div>
        </div>
        <div class="clearfix"></div>
        
      </div>
   </div>


</body>
<#include "/footer.ftl" />

<script type="text/javascript" src="${uiBase}js/pages/member/member_order.js?v=${resourceVersion}"></script>