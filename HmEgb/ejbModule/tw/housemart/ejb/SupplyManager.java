package tw.housemart.ejb;

import java.io.Serializable;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import tw.housemart.jpa.Supplier;


@Stateless
@LocalBean
public class SupplyManager implements SupplyManagerLocal,Serializable {

	@PersistenceContext(unitName="HmJpa")
	private EntityManager em;
	
	public Supplier findById(int id){
		 return em.find(Supplier.class, id);
	}
	
	public List<Supplier> findAllSupplier(){
		return em.createNamedQuery("Supplier.findAll", Supplier.class).getResultList();
	}
   
    public SupplyManager() {
       
    }

}
