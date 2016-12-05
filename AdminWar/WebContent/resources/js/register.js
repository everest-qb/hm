function confirmPwd(pE,cE,mE){
	if(pE.value==cE.value){
		mE.innerHTML='O';
		mE.style.color='green';
	}else{
		mE.innerHTML='X';
		mE.style.color='red';
	}
}