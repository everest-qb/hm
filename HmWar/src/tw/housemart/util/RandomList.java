package tw.housemart.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomList<T> {

	private List<T> list;
	public RandomList(List<T> list) {
		//this.list=new ArrayList<T>(list.size());
		//Collections.copy(list,this.list);	
		this.list=list;
	}

	public  List<T> Random(){
		if(list==null)return new ArrayList<T>();
		if(list.size()==0)return new ArrayList<T>();
		
		List<T> returnValue=  new ArrayList<T>(list.size());		
		Random r=new Random();
		int size=list.size();
		for(int i=0;i<size;i++){
			int index=r.nextInt(list.size());
			returnValue.add(list.remove(index));
		}
		return returnValue;
	}
	
}
