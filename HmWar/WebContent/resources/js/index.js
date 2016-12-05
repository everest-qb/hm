var globalRepeatAdvertiseflag=true;
function advertiseRepeat(component) {
	if(globalRepeatAdvertiseflag){
		component.switchToItem(component.nextItem());
	}	
}