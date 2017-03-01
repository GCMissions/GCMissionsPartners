$(function(){
	var id = $('#intersectionId').val();
	//过滤html转义字符
	function HTMLDecode(text){
	    var temp = document.createElement('div');
	    temp.innerHTML = text;
	    var output = temp.innerText || temp.textContent;
	    temp = null;
	    return output;
	}
	$.ajax({
		type:'POST',
		url:urlPrefix+"intersection/preview",
		dataType:'json',
		data:{"id":id},
		success:function(result){
			var data = result.data;
			if(result.code==='ACK'){
				$('.content').html(HTMLDecode(data));
			}
			
		}
	});
});