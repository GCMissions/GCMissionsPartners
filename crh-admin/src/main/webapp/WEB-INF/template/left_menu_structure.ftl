
<input type="hidden" value="" class="upHeight">
<div class="leftMenu" style="width:100%;margin-right:9px;height:100%;" id="leftMenu" >
    <div class="lmenuPrev">
         <a href="javascript:;" id="btnUp">向上</a>
    </div>
    <div class="lmenu fl">
        <ul class="roll_ul">
            <li id="parent1" class="parentMenu" rel="1" islast="">
                    <a style="cursor: pointer">
                         <div class="cover"></div>
                         <div class="icon" >
                                    <img src="${uiBase}/images/menu_01.gif" />
                         </div>
                         <div index="tfun2" style="display:none;" class="newFunction"></div>
                         <div topvalue="2" topname="" class="text">商品</div>
                    </a>
                    <div id="1" class="secondFloat secondFLoat1 " >
                        <div class="second">
                            <ul class="child_width">
                                <li>
                                    <div class="title">商品管理</div>
                                    <ul>
                                        <li>
                                            <div class="newFunction" style="margin-top: 10px; *margin-left: -20px;display:none;" index="tfun3"></div>
                                            <a onclick="OpenWindow(this)" style="cursor:pointer" index="3" src="./shop/admin/goods!list.do" class="cs-navi-tab">商品列表</a>
                                        </li>
                                        <li>
                                            <div class="newFunction" style="margin-top: 10px; *margin-left: -20px;display:none;" index="tfun4"></div>
                                            <a onclick="OpenWindow(this)" style="cursor:pointer" index="4" src="./shop/admin/goods!selectCat.do" class="cs-navi-tab">添加商品</a>
                                        </li>
                                        <li>
                                            <div class="newFunction" style="margin-top: 10px; *margin-left: -20px;display:none;" index="tfun5"></div>
                                            <a onclick="OpenWindow(this)" style="cursor:pointer" index="5" src="./shop/admin/goodsStore!listGoodsStore.do?optype=purchase" class="cs-navi-tab">进货</a>
                                        </li>
                                        <li>
                                            <div class="newFunction" style="margin-top: 10px; *margin-left: -20px;display:none;" index="tfun6"></div>
                                            <a onclick="OpenWindow(this)" style="cursor:pointer" index="6" src="./shop/admin/goodsStore!listGoodsStore.do?optype=view" class="cs-navi-tab">库存维护</a>
                                        </li>
                                    </ul>
                                </li>
                                <li>
                                    <div class="title">商品设置</div>
                                    <ul>
                                        <li>
                                            <div class="newFunction" style="margin-top: 10px; *margin-left: -20px;display:none;" index="tfun8"></div>
                                            <a onclick="OpenWindow(this)" style="cursor:pointer" index="8" src="./shop/admin/cat!list.do" class="cs-navi-tab">分类列表</a>
                                        </li>
                                        <li>
                                            <div class="newFunction" style="margin-top: 10px; *margin-left: -20px;display:none;" index="tfun9"></div>
                                            <a onclick="OpenWindow(this)" style="cursor:pointer" index="9" src="brand_list" class="cs-navi-tab">品牌列表</a>
                                        </li>
                                        <li>
                                            <div class="newFunction" style="margin-top: 10px; *margin-left: -20px;display:none;" index="tfun10"></div>
                                            <a onclick="OpenWindow(this)" style="cursor:pointer" index="10" src="./shop/admin/type!list.do" class="cs-navi-tab">类型列表</a>
                                        </li>
                                        <li>
                                            <div class="newFunction" style="margin-top: 10px; *margin-left: -20px;display:none;" index="tfun11"></div>
                                            <a onclick="OpenWindow(this)" style="cursor:pointer" index="11" src="./shop/admin/spec!list.do" class="cs-navi-tab">规格列表</a>
                                        </li>
                                    </ul>
                                </li>
                                <li>
                                    <div class="title">标签管理</div>
                                    <ul>
                                        <li>
                                            <div class="newFunction" style="margin-top: 10px; *margin-left: -20px;display:none;" index="tfun13"></div>
                                            <a onclick="OpenWindow(this)" style="cursor:pointer" index="13" src="./shop/admin/tag!list.do" class="cs-navi-tab">标签列表</a>
                                        </li>
                                        <li>
                                            <div class="newFunction" style="margin-top: 10px; *margin-left: -20px;display:none;" index="tfun14"></div>
                                            <a onclick="OpenWindow(this)" style="cursor:pointer" index="14" src="./shop/admin/goodsShow!taglist.do" class="cs-navi-tab">标签商品设置</a>
                                        </li>
                                        <li>
                                            <div class="newFunction" style="margin-top: 10px; *margin-left: -20px;display:none;" index="tfun15"></div>
                                            <a onclick="OpenWindow(this)" style="cursor:pointer" index="15" src="./shop/admin/brandsShow!list.do" class="cs-navi-tab">品牌标签设置</a>
                                        </li>
                                    </ul>
                                </li>
                                
                            </ul>
                        </div>
                        <!-- second -->
                    </div>
            </li>
            <li id="parent16" class="parentMenu" rel="16" islast="">
                    <a style="cursor: pointer">
                         <div class="cover"></div>
                         <div class="icon" >
                                    <img src="${uiBase}/images/menu_default.gif" />
                         </div>
                         <div index="tfun2" style="display:none;" class="newFunction"></div>
                         <div topvalue="2" topname="" class="text">订单</div>
                    </a>
                    <div id="16" class="secondFloat secondFLoat2 ">
                        <div class="second">
                            <ul class="child_width">
                                <li>
                                    <div class="title">订单管理</div>
                                    <ul>
                                        <li>
                                            <div class="newFunction" style="margin-top: 10px; *margin-left: -20px;display:none;" index="tfun18"></div>
                                            <a onclick="OpenWindow(this)" style="cursor:pointer" index="18" src="./shop/admin/order!list.do" class="cs-navi-tab">订单列表</a>
                                        </li>
                                        <li>
                                            <div class="newFunction" style="margin-top: 10px; *margin-left: -20px;display:none;" index="tfun19"></div>
                                            <a onclick="OpenWindow(this)" style="cursor:pointer" index="19" src="./shop/admin/order!notPayOrder.do" class="cs-navi-tab">待结算订单</a>
                                        </li>
                                        <li>
                                            <div class="newFunction" style="margin-top: 10px; *margin-left: -20px;display:none;" index="tfun20"></div>
                                            <a onclick="OpenWindow(this)" style="cursor:pointer" index="20" src="./shop/admin/order!notShipOrder.do" class="cs-navi-tab">待发货订单</a>
                                        </li>
                                        <li>
                                            <div class="newFunction" style="margin-top: 10px; *margin-left: -20px;display:none;" index="tfun21"></div>
                                            <a onclick="OpenWindow(this)" style="cursor:pointer" index="21" src="./shop/admin/order!notRogOrder.do" class="cs-navi-tab">待收货订单</a>
                                        </li>
                                    </ul>
                                </li>
                                <li>
                                    <div class="title">单据管理</div>
                                    <ul>
                                        <li>
                                            <div class="newFunction" style="margin-top: 10px; *margin-left: -20px;display:none;" index="tfun23"></div>
                                            <a onclick="OpenWindow(this)" style="cursor:pointer" index="23" src="./shop/admin/orderReport!paymentList.do" class="cs-navi-tab">收款单</a>
                                        </li>
                                        <li>
                                            <div class="newFunction" style="margin-top: 10px; *margin-left: -20px;display:none;" index="tfun24"></div>
                                            <a onclick="OpenWindow(this)" style="cursor:pointer" index="24" src="./shop/admin/orderReport!returnedList.do" class="cs-navi-tab">退货单</a>
                                        </li>
                                    </ul>
                                </li>
                                <li>
                                            <div class="title">发票管理</div>
                                            <ul>
                                                    <li>
                                                        <div class="newFunction" style="margin-top: 10px; *margin-left: -20px;display:none;" index="tfun26"></div>
                                                        <a onclick="OpenWindow(this)" style="cursor:pointer" index="26" src="./shop/admin/invoice!list_invoice.do" class="cs-navi-tab">发票内容管理</a>
                                                    </li>
                                            </ul>
                                        </li>
                                
                            </ul>
                        </div>
                        <!-- second -->
                    </div>
            </li>
            <!--- other menu -->
     </ul>
    </div>
    <div class="lmenuNext">
         <a href="javascript:;" id="btnDown">向下</a>
    </div>
</div>