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
                    			用户详情
                    			</label>
                    		</div>
                    		<div class="col-sm-12">
                    			<div class="col-sm-6">
                    				<label class="control-label">
                    				手机号：${memberDetail.mobile}
                    				</label>
                    			</div>
                    			<div class="col-sm-6">
                    				<label class="control-label">
                    				注册时间：${memberDetail.createDate}
                    				</label>
                    			</div>
                    		</div>
                    		<div class="col-sm-12">
                    			<div class="col-sm-6">
                    				<label class="control-label">
                    				昵称：${memberDetail.custName}
                    				</label>
                    			</div>
                    			<div class="col-sm-6">
                    				<label class="control-label">
                    				是否VIP：${memberDetail.vip}
                    				</label>
                    			</div>
                    		</div>
                    		<div class="col-sm-12">
                    			<div class="col-sm-6">
                    				<label class="control-label">
                    				性别：${memberDetail.sex}
                    				</label>
                    			</div>
                    			<div class="col-sm-6">
                    				<label class="control-label">
                    				VIP有效期：${memberDetail.vipDate}
                    				</label>
                    			</div>
                    		</div>
                    	</div>
                    </div>
                    <div class="box-body">
                    <div class="col-sm-12">
                    	<label class="control-label" style="font-size: 25px;"> 
                    	拥有的优惠券
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

<script type="text/javascript" src="${uiBase}js/pages/member/member_detail.js?v=${resourceVersion}"></script>