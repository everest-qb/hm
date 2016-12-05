package tw.housemart.util;

import java.util.Comparator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tw.housemart.jsf.bean.GoodItem;

public class GoodComparator implements Comparator<GoodItem> {

	private Logger log = LoggerFactory.getLogger(GoodComparator.class);
	
	@Override
	public int compare(GoodItem obj0, GoodItem obj1) {
		String id0=obj0.getId();
		id0=id0.substring(6, 14)+id0.substring(0, 6);
		String id1=obj1.getId();
		id1=id1.substring(6, 14)+id1.substring(0, 6);		
		return id0.compareTo(id1);
	}

}
