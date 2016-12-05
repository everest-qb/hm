package tw.housemart.util;

import java.util.Comparator;

import tw.housemart.jpa.Advertise;

public class AdvertiseComparator implements Comparator<Advertise> {

	@Override
	public int compare(Advertise o1, Advertise o2) {
		String id0=o1.getGId();
		id0=id0.substring(6, 14)+id0.substring(0, 6);
		String id1=o2.getGId();
		id1=id1.substring(6, 14)+id1.substring(0, 6);
		
		return id0.compareTo(id1);
	}

}
