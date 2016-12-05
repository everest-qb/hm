package tw.housemart.ejb;

import java.io.Serializable;
import java.util.List;






import javax.ejb.Local;

import tw.housemart.jpa.Good;

@Local
public interface GoodsSearchLocal extends Serializable{
	public List<Good> findAll();
	public Good findById(String id);
	public float findGoodsPrice(String id);
}
