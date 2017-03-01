<!-- model template -->
<script id="viewStockListTpl" type="text/html">
	{{if actStockList}} 
           {{each actStockList}}
               {{if $value.stockType != "2"}}
                   <div class="m-box">
                       <div class="title">
                       	<div class="m-dot"></div>
                           <label class="act_date">{{$value.actDate | subStr}}</label>
                           {{if $value.stockType == "0"}}
                           		<span>( 按规格 )</span>
                           {{else}}
                           		<span>( 按人数 )</span>
                           {{/if}}
                           <button class="btn btn-primary pull-right">编辑</button>
                       </div>
                       {{if $value.stockType == "1"}}
                       		<p class="kcnum">
                       			<label>库存数量：{{$value.originalCount}}</label>
                       			<label>剩余库存数量：<span class="red">{{$value.totalCount}}</span></label>
                       		</p>
                       {{/if}}
                       <div class="boxin">
                           <table >
                               <thead>
                               <tr>
                                   <th>商品规格</th>
                                   <th>库存数量</th>
                                   <th>剩余库存量</th>
                               </tr>
                               </thead>
                               <tbody>
                              	{{if $value.actSpecList}}
                              		{{each $value.actSpecList as data index}}
                              			<tr>
                                    		<td><label>{{data.subSpec}}</label></td>
										{{if $value.stockType == "1"}}
                                    		<td class="delete">{{data.unitNum * data.groupNum}}</td>
                                    		<td class="delete"><span class="red">{{data.total}}</span></td>
										{{else}}
                                    		<td>{{data.unitNum * data.groupNum}}</td>
                                    		<td><span class="red">{{data.total}}</span></td>
										{{/if}}
                              			</tr>
                              		{{/each}}
                              	{{/if}}
                               </tbody>
                           </table>
                       </div>
                   </div>
               {{else}}
                   <div class="m-box">
                       <div class="title">
                       	<div class="m-dot"></div>
                           <label>{{$value.actDate | subStr}}</label>
                           <span>( 不需要库存 )</span>
                           <button class="btn btn-primary pull-right">编辑</button>
                       </div>
                       <div class="boxin">
                           <p class="nostock">该活动日期无需库存</p>
                       </div>
                   </div>
               {{/if}}
           {{/each}}
    {{/if}}	                         		
</script><!-- 库存状态 -->

<script id="actDateTpl" type="text/html">
{{if actDateList}}
	{{each actDateList}}
		<li>{{$value.actDate}}<span>×</span></li>
	{{/each}}
{{/if}}	
</script><!-- 添加活动日期-->

<script id="mainSpecTpl" type="text/html">
<div>
	<input class="form-control" value="{{mainSpec}}" readonly>
	<a href="javascript:void(0);" id="delMainSpec" target="_self">删除</a>
	<div class="subSpec clearfix"> 
		<ul class="clearfix">
		{{if subSpecList}}
			{{each subSpecList}}
				<li>{{$value}}<span>×</span></li>
			{{/each}}
		{{/if}}
		</ul>
		<div class="selSubSpec">
			<input type="text" onfocus="this.select()"/>
			<button type="button" class="btn btn-primary" id="addSubSpecConfirm">确定</button>
			<button type="button" class="btn btn-primary" id="addSubSpecCancel">取消</button>
		</div>
		<a href="javascript:void(0);" id="addSubSpec">+添加</a>
	</div>
</div>
</script><!-- 添加主规格-->

<script id="subSpecTpl" type="text/html">
{{if subSpecList}}
	{{each subSpecList}}
		<li>{{$value}}<span>×</span></li>
	{{/each}}
{{/if}}
</script><!-- 添加子规格-->

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
						<td>{{$value}}</td>
						<td><input type="text" value="{{count}}" onfocus="this.select()"/></td>
					</tr>
				{{/each}}
			{{/if}}
			{{if status}}
				{{each actDateStockList}}
					<tr style="text-align:center;"> 
						<td>{{$value.actDate}}</td>
						<td><input type="text" value="{{$value.count}}" onfocus="this.select()"/></td>
					</tr>
				{{/each}}
			{{/if}}
		</tbody>
	</table>
</div>
</script><!-- 修改每个活动日期的库存量-->

<script id="stockInfoCopyTpl" type="text/html">
{{each actSpecList}}
	<tr subspec="{{$value.subSpec}}">
		<td style="vertical-align: middle;">
			<div class="spec_area form-group">
				<label>{{$value.subSpec}}</label>
				<div>
					<label>最小单位量:</label>
					<input type="text" class="form-control unit_num" value="{{$value.unitNum}}" onfocus="this.select()"/>
					<span>×</span>
					<input type="text" class="form-control group_num" value="{{$value.groupNum}}" onfocus="this.select()"/>
				</div>
			</div>
		</td>
		<td style="vertical-align: middle;">
			<div class="num_area">
				<span>{{$value.unitNum * $value.groupNum}}</span>
			</div>
		</td>
		<td style="vertical-align: middle;">
			<div class="price_area">
				{{if $value.priceDesc.price[0].subSpec}}
					<ul>
					{{each $value.priceDesc.price as data index}}
						<li subspec="{{data.subSpec}}">
							<span>{{data.subSpec}}</span>
							{{if data.price[0].subSpec }}
								<ul>
								{{each data.price as value seqNum}}
									<li subspec="{{value.subSpec}}">
										<span>{{value.subSpec}}</span>
										{{if value.limit}}
											<input type="text" class="limit" value="{{value.limit}}" onfocus="this.select()" />
										{{else}}
											<input type="text" class="limit" value="0" onfocus="this.select()" />
										{{/if}}
										{{if value.vipPrice}}
											<input type="text" class="vip-price" value="{{value.vipPrice}}" onfocus="this.select()" />
										{{else}}
											<input type="text" class="vip-price" value="0" onfocus="this.select()" />
										{{/if}}
										<input type="text" class="price" value="{{value.price}}" onfocus="this.select()" />
									</li>
								{{/each}}
								</ul>
							{{else}}
								{{if data.limit}}
									<input type="text" class="limit" value="{{data.limit}}" onfocus="this.select()" />
								{{else}}
									<input type="text" class="limit" value="0" onfocus="this.select()" />
								{{/if}}
								{{if data.vipPrice}}
									<input type="text" class="vip-price" value="{{data.vipPrice}}" onfocus="this.select()" />
								{{else}}
									<input type="text" class="vip-price" value="0" onfocus="this.select()" />
								{{/if}}
								<input type="text" class="price" value="{{data.price}}" onfocus="this.select()" />
							{{/if}}
						</li>
					{{/each}}
					</ul>
				{{else}}
					{{if $value.priceDesc.limit}}
						<input type="text" class="limit" value="{{$value.priceDesc.limit}}" onfocus="this.select()" />
					{{else}}
						<input type="text" class="limit" value="0" onfocus="this.select()" />
					{{/if}}	
					{{if $value.priceDesc.vipPrice}}
						<input type="text" class="vip-price" value="{{$value.priceDesc.vipPrice}}" onfocus="this.select()" />
					{{else}}
						<input type="text" class="vip-price" value="0" onfocus="this.select()" />
					{{/if}}				
					<input type="text" class="price" value="{{$value.priceDesc.price}}" onfocus="this.select()" />
				{{/if}}
			</div>
		</td>
	</tr>
{{/each}}
</script><!-- 第一个主规格添加子规格 复制已有商品信息 -->

<script id="stockInfoTpl_1" type="text/html">
{{each firstList as elem serNum}}
	<tr subspec="{{elem}}">
		<td style="vertical-align: middle;">
			<div class="spec_area form-group">
				<label title="{{elem}}">{{elem}}</label>
				<div>
					<label>最小单位量:</label>
					<input type="text" class="form-control unit_num" value="0" onfocus="this.select()"/>
					<span>×</span>
					<input type="text" class="form-control group_num" value="0" onfocus="this.select()"/>
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
							<span title="{{data}}">{{data}}</span>
							{{if thirdList.length > 0 }}
								<ul>
								{{each thirdList as value seqNum}}
									<li subspec="{{value}}">
										<span title="{{value}}">{{value}}</span>
										<input type="text" class="limit" value="0" onfocus="this.select()"/>
										<input type="text" class="vip-price" value="0" onfocus="this.select()"/>
										<input type="text" class="price" value="0" onfocus="this.select()"/>
									</li>
								{{/each}}
								</ul>
							{{else}}
								<input type="text" class="limit" value="0" onfocus="this.select()"/>
								<input type="text" class="vip-price" value="0" onfocus="this.select()"/>
								<input type="text" class="price" value="0" onfocus="this.select()"/>
							{{/if}}
						</li>
					{{/each}}
					</ul>
				{{else}}
					<input type="text" class="limit" value="0" onfocus="this.select()"/>
					<input type="text" class="vip-price" value="0" onfocus="this.select()"/>
					<input type="text" class="price" value="0" onfocus="this.select()"/>
				{{/if}}
			</div>
		</td>
	</tr>
{{/each}}
</script><!-- 第一个主规格添加子规格 -->

<script id="stockInfoTpl_2" type="text/html">
{{if secondList.length > 0}}
	<ul>
		{{each secondList as data index}}
			<li subspec="{{data}}">
				<span title="{{data}}">{{data}}</span>
				{{if thirdList.length > 0}}
					<ul>
					{{each thirdList as value seqNum}}
						<li subspec="{{value}}">
							<span title="{{value}}">{{value}}</span>
							<input type="text" class="limit" value="0" onfocus="this.select()"/>
							<input type="text" class="vip-price" value="0" onfocus="this.select()"/>
							<input type="text" class="price" value="0" onfocus="this.select()"/>
						</li>
					{{/each}}
					</ul>
				{{else}}
					<input type="text" class="limit" value="0" onfocus="this.select()"/>
					<input type="text" class="vip-price" value="0" onfocus="this.select()"/>
					<input type="text" class="price" value="0" onfocus="this.select()"/>
				{{/if}}
			</li>
		{{/each}}
	</ul>
{{else}}
	<input type="text" class="limit" value="0" onfocus="this.select()"/>
	<input type="text" class="vip-price" value="0" onfocus="this.select()"/>
	<input type="text" class="price" value="0" onfocus="this.select()"/>
{{/if}}
</script><!-- 第二、三个主规格添加子规格 -->

<script id="initRequireFieldsTpl" type="text/html">
 {{if requireFields}}
	{{if requireMask}}
		{{each requireFields as filed index}}
		<li>
			{{if requireMask[index] == "0" }}
				{{filed}}<label class='u-bt'><input type='checkbox' value='1' disabled/>必填</label>
			{{else}}
				{{filed}}<label class='u-bt'><input type='checkbox' value='1' checked disabled/>必填</label>
			{{/if}}
		</li>
		{{/each}}
	{{else}}
		{{each requireFields as filed index}}
		<li>
			{{filed}}<label class='u-bt'><input type='checkbox' value='1' checked disabled/>必填</label>
		</li>
		{{/each}}
	{{/if}}
  {{/if}}
</script><!-- 购买信息必填字段 -->

<script id="partakeInfoTpl" type="text/html">
{{if actPartakes}}
	{{each actPartakes}}
	<tr>
		<td>
			<span class="sub-spec">{{$value.subSpec}}</span><span>×</span>
			<input type="text" class="form-control" value="{{$value.unitNum}}" onfocus="this.select()"/>
		</td>
		<td style="vertical-align: middle;">
			{{if $value.enabled}}
				<input type="checkbox" value="1" checked/>
			{{else}}
				<input type="checkbox" value="1" />
			{{/if}}
			<span>启用</span>
		</td>
	</tr>
	{{/each}}
{{/if}}
</script><!-- 参与人数信息 -->

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
</script><!-- 操作提示信息 -->