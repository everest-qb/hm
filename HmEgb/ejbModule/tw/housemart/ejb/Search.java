package tw.housemart.ejb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import tw.housemart.jpa.Advertisement;
import tw.housemart.jpa.Announcement;
import tw.housemart.jpa.Approperty;
import tw.housemart.jpa.Classification;
import tw.housemart.jpa.Explain;
import tw.housemart.jpa.Goodsgroup;
import tw.housemart.jpa.Goodstype;
import tw.housemart.jpa.PersonalGood;
import tw.housemart.jpa.PersonalGoodPK;


@Stateless
@LocalBean
public class Search implements SearchLocal,Serializable {
	
	@PersistenceContext(unitName="HmJpa")
	private EntityManager em;
	
	public boolean findPersonalExist(String userName,String gId){
		boolean returnValue=false;
		PersonalGoodPK pk=new PersonalGoodPK();
		pk.setGId(gId);
		pk.setName(userName);
		PersonalGood pg=em.find(PersonalGood.class,pk);
		if(pg!=null)returnValue=true;
		return returnValue;
	}
	
	public long findClusterReloadTime(){
		long returnValue=0;
		Approperty a=em.find(Approperty.class, "cluster.reload");
		try{
			returnValue=Long.parseLong(a.getAvalue());
		}catch(Exception e){
			
		}
		return returnValue;
	}
	
	/*public boolean findClusterReload(){
		boolean returnValue=false;
		Approperty a=em.find(Approperty.class, "cluster.reload");
		if("true".equals(a.getAvalue())){
			returnValue=true;
		}
		return returnValue;
	}*/
	
	public String findSkin(){
		String returnValue="chm";
		try{
		Approperty a=em.find(Approperty.class, "skin.name");
		returnValue=a.getAvalue();
		}catch(Exception e){
			
		}
		return returnValue;
	}
	
	@SuppressWarnings("unchecked")
	public List<Advertisement> findAllAdvertisement(){
		return em.createNamedQuery("Advertisement.findAll").getResultList();
	}
	
	public Goodsgroup findGroupById(String id){
		return em.find(Goodsgroup.class, id);
	}
	
	public Goodstype findTypeById(String id){
		return em.find(Goodstype.class, id);
	}
	
	public Classification findClassificationById(String id){
		return em.find(Classification.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<String> findClassificationIdByKey(String prefix){		
		List<String> returnValue =em.createQuery("select g.id from Classification g where g.id like :key ")
		.setParameter("key", prefix+"%").getResultList();
		return returnValue;
	}
	
	@SuppressWarnings("unchecked")
	public List<String> findTypeIdByKey(String prefix){		
		List<String> returnValue =em.createQuery("select g.id from Goodstype g where g.id like :key ")
		.setParameter("key", prefix+"%").getResultList();
		return returnValue;
	}
	
	@SuppressWarnings("unchecked")
	public List<String> findGroupIdByKey(String prefix){		
		List<String> returnValue =em.createQuery("select g.id from Goodsgroup g where g.id like :key ")
		.setParameter("key", prefix+"%").getResultList();
		return returnValue;
	}
	
	public List<Goodstype> findGoodstypeByGroup(String prefix){
		return  em.createQuery("select g from Goodstype g where "
				+ "g.id like :key",Goodstype.class)
				.setParameter("key", prefix+".%")
				.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Goodstype> findAllGoodstype(){
		return em.createNamedQuery("Goodstype.findAll").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Classification> findAllClassification(){
		return em.createNamedQuery("Classification.findAll").getResultList();
	}	
	
	@SuppressWarnings("unchecked")
	public List<Goodsgroup> findAllGoodsGroup(){
		List<Goodsgroup> returnValue=em.createNamedQuery("Goodsgroup.findAll").getResultList();
		return returnValue;
	}
	
	public Map<String,String> findMailProperties(){
		Map<String,String> returnValue=new HashMap<String,String>(); 
		List<Approperty> pList= em.createQuery("select a from Approperty a where "
				+ "a.id like 'app.mail.%' or a.id like 'mail.smtp.%' or a.id like 'internal.%'",Approperty.class)
				.getResultList();
		for(int i=0;i<pList.size();i++){
			Approperty a=pList.get(i);
			returnValue.put(a.getId(), a.getAvalue());
		}
		return returnValue;
	}
    
	public List<Announcement> findAnnouncement(){
		return em.createQuery("select a from Announcement a where a.id>50 order by a.createT desc",Announcement.class)
		.getResultList();
	}
	
	public List<Announcement> findConvention(){
		return em.createQuery("select a from Announcement a where a.id < 51 order by a.createT desc",Announcement.class)
				.getResultList();	
	}
	
	@SuppressWarnings("unchecked")
	public List<String> findAllExplain(){
		List<String> returnValue=new ArrayList<String>();
		List<Explain> list=em.createNamedQuery("Explain.findAll").getResultList();
		
		for(int i=0;i<list.size();i++){
			returnValue.add(list.get(i).getId());
		}
		return returnValue;
	}
	
    public Search() {
      
    }

	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}

}
