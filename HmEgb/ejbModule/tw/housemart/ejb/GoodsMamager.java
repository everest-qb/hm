package tw.housemart.ejb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import tw.housemart.jpa.Advertise;
import tw.housemart.jpa.Category;
import tw.housemart.jpa.Classification;
import tw.housemart.jpa.Good;
import tw.housemart.jpa.Goodsgroup;
import tw.housemart.jpa.Goodstype;
import tw.housemart.jpa.Supplier;

@Stateless
@LocalBean
public class GoodsMamager implements GoodsMamagerLocal,Serializable {

	@PersistenceContext(unitName="HmJpa")
	private EntityManager em;
	
	public <T> boolean updateKind(T kind){
		boolean returnValue=false;
		if(kind instanceof Goodsgroup){			
			em.merge(kind);
			returnValue=true;
		}else if(kind instanceof Goodstype){
			em.merge(kind);
			returnValue=true;
		}else if(kind instanceof Classification){
			em.merge(kind);
			returnValue=true;
		}
		return returnValue;
	}
	
	public boolean addNewGroup(Goodsgroup group){			
		boolean returnValue=false;
		if (group.getId() != null && group.getId().length() > 0) {
			Goodsgroup g = em.find(Goodsgroup.class, group.getId());
			if (g == null) {
				em.persist(group);
				returnValue = true;
			}}
		return returnValue;
	}
	
	public boolean addNewType(Goodstype type){
		boolean returnValue=false;
		if (type.getId() != null && type.getId().length() > 0) {
			Goodstype g = em.find(Goodstype.class, type.getId());
			if (g == null) {
				em.persist(type);
				returnValue = true;
			}}
		return returnValue;
	}
	
	public boolean addNewClassification(Classification classification){
		boolean returnValue=false;
		if (classification.getId() != null && classification.getId().length() > 0) {
			Classification g = em.find(Classification.class, classification.getId());
			if (g == null) {
				em.persist(classification);
				returnValue = true;
			}}
		return returnValue;
	}
	
	public boolean addCategoryMap(String categoryName,String goodsId){
		Category category;
		try{
		category=em.createQuery("select c from Category c where c.name=:name",Category.class)
				.setParameter("name", categoryName).getSingleResult();
		category.getGoods();
		}catch(Exception e){
			category=new Category();
			category.setGoods(new ArrayList<Good>());
			category.setName(categoryName);
			em.persist(category);		
		}		
		
		Good good=em.find(Good.class, goodsId);
		List<Good> gList=category.getGoods();
		if(gList.contains(good)){
			return false; 
		}else{
			gList.add(good);
		}
		return true;		
	}
	
	public boolean delCategoryMap(String categoryName,String goodsId){
		Category category;
		try{
		category=em.createQuery("select c from Category c where c.name=:name",Category.class)
				.setParameter("name", categoryName).getSingleResult();
		category.getGoods();
		}catch(Exception e){
			System.out.println("no category :"+categoryName);
			return false;
		}		
		Good good=em.find(Good.class, goodsId);
		List<Good> gList=category.getGoods();
		int index=gList.lastIndexOf(good);
		if(index>=0){
			System.out.println("map has goods");
			gList.remove(index);
		}else{
			System.out.println("map not has goods");
			return false;
		}
		return true;
	}
	
	public boolean delAdvertisement(String id){
		Advertise ad=em.find(Advertise.class, id);
		if(ad==null){
			return false;
		}else{
			em.remove(ad);
		}
		return true;
	}
	
	public boolean addNewAdvertisement(Advertise ad) {
		String id = "";
		try {
			Object obj = em
					.createQuery(
							"select max(ad.id) from Advertise ad where ad.id like :ADID")
					.setParameter("ADID", ad.getId() + "%").getSingleResult();
			if (obj != null) {
				id = (String) obj;
				String nu = id.substring(id.length() - 2);				
				int n = Integer.parseInt(nu) + 1;
				if (n < 10) {
					id = ad.getId() + "0" + n;
				} else if (n < 100) {
					id = ad.getId() + n;
				} else {
					return false;
				}
			}else{
				id = ad.getId() + "01";
			}
		} catch (NoResultException ex) {
			id = ad.getId() + "01";
		}
		ad.setId(id);
		em.persist(ad);
		return true;
	}
	
	public boolean updateAdvertisement(Advertise ad){
		Advertise advertise=em.find(Advertise.class, ad.getId());
		if(advertise!=null){
			em.merge(ad);
		}else{
			return false;
		}		
		return true;		
	}
	
	public boolean updateGoods(Good g) {
		Good good = em.find(Good.class, g.getGId());
		if (good == null) {
			return false;
		}else{
			em.merge(g);			
		}

		return true;
	}
	
	public boolean addNewGoods(Good g){		
		if(em.find(Good.class, g.getGId())==null){
			em.persist(g);
			int supplyerId= Integer.parseInt(g.getGId().substring(0, 3));
			Supplier s=em.find(Supplier.class, supplyerId);
			s.setMaxGid(s.getMaxGid()+1);
		}else{
			return false;			
		}		
		return true;
	}
	
    public GoodsMamager() {
       
    }

}
