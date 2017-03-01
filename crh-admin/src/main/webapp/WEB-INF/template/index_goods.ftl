
<#include "/header.ftl" /> 

</head>
<body class="hold-transition skin-blue sidebar-mini">
    <div class="wrapper" style="overflow:visible">
        <div class="content-wrapper page-content-wrapper">
         	<section class="content-header">
	          <ol class="breadcrumb">
	            <li><i class="fa fa-dashboard"></i>cfang test qrcode</li>
	           
	          </ol>
            </section>
			
			<!-- Main content -->
			<div class="row pad">
				<div class="col-md-12">
					<div class="box box-primary">                  
						<div class="box-body" id="messageForm">
						<img src="${uiBase}img/wly-round.png" id="logo" style="display:none">
						
						<div class="row form-group">
							<div class="col-md-12">
								<div class="col-sm-2 form-app-pic">
									 <label class="control-label">我的二维码：</label>
									 <button type=button  class="btn btn-sucess dlNormal">下载这个二维码(png)</button>
								</div>
								<div class="col-sm-6 ">
									<div id="normalQrcode">
									</div>
								</div>
							</div>
						</div> 
						<div class="row form-group">
							<div class="col-md-12">
								<div class="col-sm-2 form-app-pic">
									 <label class="control-label">其它尺寸的二维码：</label>
									 <button type=button class="btn btn-sucess dlOther">下载这个二维码(png)</button>
								</div>
								<div class="col-sm-6 ">
									<div id="otherSizeQrcode" style="display:none">
									</div>
								</div>
							</div>
						</div> 
						<div class="row form-group">
							<div class="col-md-12">
								<div class="col-sm-2 form-app-pic">
									 width: <input type=text id="qrwidth">
									 height: <input type=text id="qrheight">
									 <button type=button class="btn btn-sucess btnGenerateQRcode">GenerateQRcode</button>
								
								</div>
							</div>
						</div> 
						
						
						 
						</form>
						</div>           
					</div>
				 </div>
			</div>
			<div class="clearfix"></div>              	

						
            
            
        </div> <!-- content-wrapper -->
       
        

    </div><!-- ./wrapper -->
   
  </body>
  <#include "/footer.ftl" />
<script src="${uiBase}vendor/canvas2image/base64.js"></script>
<script src="${uiBase}vendor/canvas2image/canvas2image.js"></script>
  
  <script type="text/javascript" src="${uiBase}vendor/jquery.qrcode.min.js"></script>
  <script>
  
  $(function() {
    var logoImg = new Image();  
  	function drawBeauty(beauty){
		var mycv = document.getElementById("cv");  
		var myctx = mycv.getContext("2d");
		myctx.drawImage(beauty, 0, 0);
	}
	$('#logo').load(function() {
		buildQrcode($('#normalQrcode'), 170, 170);
		
		logoImg.src = $('#logo').attr('src'); 
		
		var myctx = $('#normalQrcode').find('canvas').get(0).getContext("2d");
		myctx.drawImage(logoImg, 50, 50);
	});
	



	 var url = "http://www.wuliangye.com/gy2355";
	 function buildQrcode($item, width, height) {
		$item.qrcode({ 
			
			width: width, //宽度 
			height:height, //高度 
			text: url
		}); 
	 }
	 function saveCanvas(pCanvas, strType,  width, height) {
		var bRes = false,
		 fileName =  "二维码"+width+"*"+height;
		if (strType == "PNG")
			bRes = Canvas2Image.saveAsPNGWithFileName(pCanvas, fileName+".png", width, height);
		if (strType == "BMP")
			bRes = Canvas2Image.saveAsBMP(pCanvas, false, width, height);
		if (strType == "JPEG")
			bRes = Canvas2Image.saveAsJPEG(pCanvas, false, width, height);

		if (!bRes) {
			alert("Sorry, this browser is not capable of saving " + strType + " files!");
			return false;
		}
	}
	 
	 
	 $('button.dlNormal').on('click', function() {
	 	var oCanvas = $('#normalQrcode').find('canvas').get(0);
	 	saveCanvas(oCanvas, "PNG", 170, 170);
	 	
	 });
	  $('button.dlOther').on('click', function() {
	   	buildQrcode($('#otherSizeQrcode'), $('#qrwidth').val(), $('#qrheight').val());
	   
	 	var oCanvas = $('#otherSizeQrcode').find('canvas').get(0);
	 	saveCanvas(oCanvas, "PNG",  $('#qrwidth').val(), $('#qrheight').val());
	 	
	 });
  });
  </script>
  </html>