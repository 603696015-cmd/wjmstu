$(
		function sssssssssss(){
		var setting = {
		callback: {
				onClick: onClick
			}
		};
		var  treetablename =$('#treehiddeninfo').val();
		var	zNodes;
	$.ajax({
			  type: 'POST',
			  url: "mode_treeviwe.action",
			  data: {tableName:treetablename},
			  async:false,//
			  success: function(data){
			zNodes=eval("("+data+")");
		$(document).ready(function(){
			$.fn.zTree.init($("#treeDemo"), setting, zNodes);
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			var node =zTree.getNodeByParam('id',1,null);
			var typebindId=$('#treehiddeninfo').attr('title');
			zTree.expandNode(node, true, false , true);
			if(typebindId!=""&&typebindId!=0){
				var nodes =zTree.getNodeByParam('id',typebindId,null);
				zTree.selectNode(nodes);

			}
		});		  	
}
});	
		}		
		);
		function onClick(event, treeId, treeNode, clickFlag) {
			var  treeurlnale=$('#treehiddeninfo').attr('name');
			if(treeurlnale!=""){
				treeurlnale=treeurlnale+treeNode.id; 
				window.location.href=treeurlnale;
			
			}
			
			
		}
