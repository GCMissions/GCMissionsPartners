<script id="mainSpecInitTpl" type="text/html">
{{each dataList as spec index}}
   <div class="m-box">
		<input class="form-control u-inputwidth mainSpec" value="{{spec.mainSpec}}" title="{{spec.mainSpec}}" readonly />
		<a class="fl j-seldelete" href="javascript:void(0);">删除</a>
		<div class="subSpec clearfix">
			<ul class="clearfix fl j-selcont">
				{{each spec.subSpecs as subSpec seqNum}}
					<li><b>{{subSpec}}</b><span>×</span></li>
				{{/each}}
			</ul>
			<div class="selSubSpec fl disnone">
				<input type="text" onfocus="this.select()"/>
				<button class="btn btn-primary j-selsure">确定</button>
				<button class="btn btn-primary j-selcancel">取消</button>
			</div>
			<a class="fl j-seladd" href="javascript:void(0);">+添加</a>
		</div>
	</div>
{{/each}}
</script><!-- 主规格显示 -->

<script id="mainSpecTpl" type="text/html">
   <div class="m-box">
		<input class="form-control u-inputwidth mainSpec" value="{{mainSpec}}" title="{{mainSpec}}" readonly />
		<a class="fl j-seldelete" href="javascript:void(0);">删除</a>
		<div class="subSpec clearfix">
			<ul class="clearfix fl disnone j-selcont"></ul>
			<div class="selSubSpec fl disnone">
				<input type="text" onfocus="this.select()"/>
				<button class="btn btn-primary j-selsure">确定</button>
				<button class="btn btn-primary j-selcancel">取消</button>
			</div>
			<a class="fl j-seladd" href="javascript:void(0);">+添加</a>
		</div>
	</div>
</script><!-- 主规格添加 -->

<script id="tableInfo" type="text/html">
{{each firstList as value}}
	<tr>
		<td class="lft">
			<div class="spf clearfix">
				<ul {{if secondList.length == 0}} class="last-ul" {{/if}}>
					<li>
						<span title="{{value}}">{{value}}</span>
						{{if secondList.length > 0}}
						<ul {{if thirdList.length == 0}} class="last-ul" {{/if}}>
							{{each secondList as svalue}}
							<li class="last-li">
								<span title="{{svalue}}">{{svalue}}</span>
								{{if thirdList.length > 0}}
								<ul {{if fourthList.length == 0}} class="last-ul" {{/if}}>
									{{each thirdList as tvalue}}
									<li>
										<span title="{{tvalue}}">{{tvalue}}</span>
										{{if fourthList.length > 0}}
										<ul class="last-ul">
											{{each fourthList as fvalue}}
											<li>
												<span title="{{fvalue}}">{{fvalue}}</span>
											</li>
											{{/each}}
										</ul>
										{{/if}}
									</li>
									{{/each}}
								</ul>
								{{/if}}
							</li>
							{{/each}}
						</ul>
						{{/if}}
					</li>
				</ul>
			</div>
		</td>
		<td class="cent">
			<div class="u-price">
				{{each inputList as lvalue}}
					<input class="u-inpt" type="text" value='0' onfocus="this.select()"/>
				{{/each}}
			</div>
		</td>
		<td class="cent">
			<div class="u-vip-price">
				{{each inputList as lvalue}}
					<input class="u-inpt" type="text" value='0' onfocus="this.select()"/>
				{{/each}}
			</div>
		</td>
		<td class="cent">
			<div class="u-stock">
				{{each inputList as lvalue}}
					<input class="u-inpt" type="text" value='0' onfocus="this.select()"/>
				{{/each}}
			</div>
		</td>
		<td class="cent">
			<div class="u-limit">
				{{each inputList as lvalue}}
					<input class="u-inpt" type="text" value='0' onfocus="this.select()"/>
				{{/each}}
			</div>
		</td>
	</tr>  
{{/each}} 															
</script>

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