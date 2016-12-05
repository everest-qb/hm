package tw.housemart.ejb;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import tw.housemart.jpa.Advertise;
import tw.housemart.jpa.Category;
import tw.housemart.jpa.Good;
import tw.housemart.jpa.Goodstype;

@Stateless
@LocalBean
public class GoodsSearch implements GoodsSearchLocal,Serializable{
	
	@PersistenceContext(unitName="HmJpa")
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	public List<Good> findStore(String userName){
		List<Good> returnalue=new ArrayList<Good>();
		returnalue=em.createQuery("select g from Good g where g.gId in (select p.id.gId from PersonalGood p where p.id.name=:NAME)")
				.setParameter("NAME",userName )
				.getResultList();
		return returnalue;
	}
	
	@SuppressWarnings("unchecked")
	public List<Good> findHistory(String userName){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -90);
		List<Good> returnalue=new ArrayList<Good>();
		returnalue=em.createQuery("select distinct d.goods from Orders o INNER JOIN o.orderDetails d WHERE o.userName=:NAME and o.createT > :mTime")
				.setParameter("NAME",userName )
				.setParameter("mTime", new Timestamp(cal.getTimeInMillis()))
				.getResultList();
		return returnalue;
	}
	
	@SuppressWarnings("unchecked")
	public List<Good> findByBanner(String id){
		List<Good> returnalue=new ArrayList<Good>();
		returnalue=em.createQuery("select g from Good g where g.gId in (select b.id.gId from Banner b where b.id.id=:ID)")
				.setParameter("ID",id )
				.getResultList();	
		return returnalue;
	}
	
	@SuppressWarnings("unchecked")
	public List<Good> searchByName(String key){
		List<Good> returnValue=em.createNativeQuery("select g.* from goods g where g.gId in (select goodsId from category_goods where categoryId >50)"
				+ " and ( g.name like ? or g.description like ? or g.title like ? or g.gId= ? )", Good.class)
				.setParameter(1, "%"+key+"%")
				.setParameter(2, "%"+key+"%")
				.setParameter(3, "%"+key+"%")
				.setParameter(4, key)
				.getResultList();
		/*List<Good> returnValue=em.createQuery("select g from Good g where g.name like :namekey or g.desc like :desckey")
				.setParameter("namekey", "%"+key+"%")
				.setParameter("desckey", "%"+key+"%")
				.getResultList();*/
		return returnValue;
	}
	
	@SuppressWarnings("unchecked")
	public List<Goodstype> findGType(String prefix){
		List<Goodstype> returnValue=em.createQuery("select g from Goodstype g where g.sort>0 and g.id like :key order by g.sort")
		.setParameter("key", prefix+"%").getResultList();
		return returnValue;
	}
	
	public String findGoodIcon(String gId){
		Good g=em.find(Good.class, gId);
		if(g!=null){
			return g.getIcon();
		}
		return "";
	}
	
	public Advertise findAdById(String id){
		return em.find(Advertise.class, id);	
	}
	
	@SuppressWarnings("unchecked")
	public Map<String,String> findAllAdvertiseId(){
		Map<String,String> returnValue=new TreeMap<String,String>();
		List<String> list=em.createQuery("select ad.id from Advertise ad order by ad.id").getResultList();
		for(int i=0;i<list.size();i++){
			returnValue.put(list.get(i), list.get(i));
		}
		
		return returnValue;
	}
	
	@SuppressWarnings("unchecked")
	public List<String> findGoodsId(String nu3code){
		return em.createQuery("select g.gId from Good g where g.gId like :code")
				.setParameter("code", nu3code+"%").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Advertise> findAllAdvertisement(){
		return em.createNamedQuery("Advertise.findAll").getResultList();
	}
    
	@SuppressWarnings("unchecked")
	public List<Good> findAll(){
		return em.createNamedQuery("Good.findAll").getResultList();		
	}
	@SuppressWarnings("unchecked")
	public List<Good> findByCategoryName(String name){
		List<Good> returnValue=new ArrayList<Good>();
		List<Category> category=em.createQuery("select c from Category c where c.name =:name")
		.setParameter("name", name).getResultList();
		for(int i=0;i<category.size();i++){
			Category c=category.get(i);
			returnValue.addAll(c.getGoods());
		}		
		return returnValue;
	}
	
	public List<Good> findByCategory(Category c){
		Category category=em.find(Category.class, c.getId());		
		List<Good> returnValue=category.getGoods();		
		return returnValue;
	}
	
	@SuppressWarnings("unchecked")
	public List<Category> findTraitCategory(){
		return em.createQuery("select c from Category c where c.id<50").getResultList();	
	}
	
	@SuppressWarnings("unchecked")
	public List<Category> findAllCategory(){
		return em.createNamedQuery("Category.findAll").getResultList();	
	}
	
	public float findGoodsPrice(String id){
		Good g=em.find(Good.class, id);
		return g.getPrice();
	}
	
	public Good findById(String id){
		return em.find(Good.class, id);
	}
	
    public GoodsSearch() {
       
    }

	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}

}
