var messageApp = {
	deleteUrl : "pMessage/delete/{{messageId}}",
	
	init : function() {
		
		this.$dataList = $('#merchantList');
		
		
		this.initBootTable();
		
		
		return this;
	},
	initBootTable: function() {
		this.bootTable = $.GLOBAL.utils.loadBootTable({
            table : this.$dataList,
            //removeBtn : $('#removeRecord'),
            refreshBtn : $('#refreshRecord'),
            idField : "messageId",
            pagination : true,
            pageSize : 10,
            url: 'pMessage/list',
            sidePagination:'server',
            queryAddParams: function() {
                return {
                     type   : $('#sOrgType').val(),
                     title  : $('#sTitle').val()
                }
            },
            columns: [
                {
                	width : 50,
                    align: 'center',
                    formatter:function(value,row,index){  
                    	return index+1; 
                    }
                    
                } ,
                {
                    field: 'title'  
                } ,
                 
                {
                    field: 'createDate',
                    sortable: false,
                    formatter:function(value,row,index){  
                    	//@TODO need convert To String ???
                    	var result  = "";
                    	
                      
                    	return value; 
                    }
                }, 
                { 
                   
                    field: 'messageId',
                    align: 'center',
                    checkbox: false,
                    formatter:function(value,row,index){  
                      
                        return ' <a  title="查看" href="'+urlPrefix+'pMessage/view/'+row.messageId+'" class="editItem" data-id="'+row.messageId+'"> <i class="fa fa-eye "  style="font-size:20px"></i></a>'
                               ;
          
                    } 
                }
             ]
        });
	}
};	
     
     