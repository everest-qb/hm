var  gobalImg;
function changeImageSize(obj){
	var tmp=jQuery.type(obj);
	if(gobalImg==obj) return;
	if(tmp!= "undefined"){	
		$(gobalImg).animate({height:'100',width:'100'});
		$(obj).animate({height:'170',width:'170'});
		gobalImg=obj;
	}else{
		$(obj).animate({height:'170',width:'170'});
		gobalImg=obj;
	}	
}