package tw.housemart.ejb;

import java.io.Serializable;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tw.housemart.jpa.Approperty;


@Stateless
@LocalBean
public class ApManager implements ApManagerLocal,Serializable {

	private Logger log = LoggerFactory.getLogger(ApManager.class);
	
	@PersistenceContext(unitName="HmJpa")
	private EntityManager em;
	
	public void changeClusterReloadTime(long time){
		String value=Long.toString(time);
		Approperty a=em.find(Approperty.class, "cluster.reload");
		a.setAvalue(value);
	}
	
	/*public boolean changeClusterReload(boolean flag){
		boolean returnValue=false;
		String aValue="";
		if(flag){
			aValue="true";
		}else{
			aValue="false";
		}
		Approperty a=em.find(Approperty.class, "cluster.reload");
		a.setAvalue(aValue);
		return returnValue;
	}*/
	
	public boolean changeSkin(String name){
		boolean returnValue=false;
		Approperty a=em.find(Approperty.class, "skin.name");
		a.setAvalue(name);
		return returnValue;
	}
   
    public ApManager() {
      
    }

}
