package tw.housemart.ejb;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Local;

@Local
public interface OrderManagerLocal extends Serializable{
	/**
	 * @param nu4code  TEL prefix 4 number
	 * @return  all TEL numbers 
	 */
	public List<String> findTel(String nu4code);
	
	public List<String> findCommunity(String chr2code);

}
