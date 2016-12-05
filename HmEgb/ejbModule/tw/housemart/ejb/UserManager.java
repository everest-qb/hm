package tw.housemart.ejb;

import java.io.Serializable;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import tw.housemart.jpa.Community;
import tw.housemart.jpa.DeliverLocation;
import tw.housemart.jpa.Linux;
import tw.housemart.jpa.PersonalGood;
import tw.housemart.jpa.PersonalGoodPK;
import tw.housemart.jpa.Role;
import tw.housemart.jpa.User;


@Stateless
@LocalBean
public class UserManager implements UserManagerLocal,Serializable {

	@PersistenceContext(unitName="HmJpa")
	private EntityManager em;
	
	public void update(User u){
		em.merge(u);
	}

	public void removePersonalCollect(String userName,String gId){
		PersonalGoodPK pk=new PersonalGoodPK();
		pk.setName(userName);
		pk.setGId(gId);
		PersonalGood pg= em.find(PersonalGood.class, pk);
		if(pg!=null)
			em.remove(pg);
	}
	
	public void personalCollect(String userName,String gId){
		PersonalGoodPK pk=new PersonalGoodPK();
		pk.setName(userName);
		pk.setGId(gId);
		PersonalGood pg= em.find(PersonalGood.class, pk);
		if(pg==null){
			pg =new PersonalGood();
			pg.setId(pk);
			em.persist(pg);
		}
	}
	
	public boolean checkAccountCreated(String name){
		boolean returnValue=false;
		Linux l=em.find(Linux.class, name);
		if(l==null)
			returnValue=true;
		return returnValue;
	}
	
	public boolean insertLinuxAccount(String name,String passwd){
		boolean returnValue=false;
		Linux account=new Linux();
		account.setName(name);
		account.setPassword(passwd);
		try {
			em.persist(account);
			returnValue = true;
		}catch(Exception e){
			
		}
		return returnValue;
	}
	
	public User findUser(String id){
		User u=em.find(User.class, id);
		if(u!=null)
			u.getAddress().getCommunity();
		return u;
	}
	
	@SuppressWarnings("unchecked")
	public boolean insertNormalUser(User u){
		Role r=em.find(Role.class, u.getRoles().get(0).getRoleName());
		u.getRoles().clear();
		u.getRoles().add(r);		
		
		List<Community> c = em
				.createQuery("select c from Community c where c.name=:name")
				.setParameter("name",
						u.getAddress().getCommunity().getName())
				.getResultList();
		List<DeliverLocation> dl = em
				.createQuery(
						"select dl from DeliverLocation dl where dl.address=:address")
				.setParameter("address", u.getAddress().getAddress())
				.getResultList();
		boolean isCommunityExist = (c!=null && c.size()>0)? true:false;
		boolean isAddressExist = (dl!=null && dl.size()>0)? true:false;
		
		if(isCommunityExist){
			Community cObj=c.get(0);
			if(isAddressExist){
				DeliverLocation dlObj = dl.get(0);
				if(cObj.getId()!=dlObj.getCommunity().getId()){
					System.out
					.println("deliver_location.community and community.id exist,but it is different!");
				}				
				u.setAddress(dlObj);
			}else{				
				u.getAddress().setCommunity(cObj);
				em.persist(u.getAddress());							
			}
		}else{
			if(isAddressExist){//TODO :  not support multi-language
				DeliverLocation dlObj = dl.get(0);
				System.out.println("It is very strange! The address is exist ,but community not.");
				String[] tmp=u.getAddress().getAddress().split("\\d++많");
				String address=tmp[0]+"xx많";			
				Community cObj= new Community();
				cObj.setAddress(address);
				cObj.setName(u.getAddress().getCommunity().getName());
				em.persist(cObj);
				u.setAddress(dlObj);
			}else{//TODO :  not support multi-language
				String[] tmp=u.getAddress().getAddress().split("\\d++많");
				String address=tmp[0]+"xx많";			
				u.getAddress().getCommunity()
						.setAddress(address);
				em.persist(u.getAddress().getCommunity());
				em.persist(u.getAddress());			
			}		
		}		
		
/*		if (c.size() > 0) {
			isCommunityExist = true;
			u.getAddress().setCommunity(c.get(0));
		} else {//TODO :  not support multi-language
			String[] tmp=u.getAddress().getAddress().split("\\d++많");
			String address=tmp[0]+"xx많";			
			u.getAddress().getCommunity()
					.setAddress(address);
			em.persist(u.getAddress().getCommunity());
		}
				
		if (dl.size() > 0) {
			DeliverLocation dlObj = dl.get(0);
			if (isCommunityExist) {
				if (dlObj.getCommunity().getId() == u.getAddress()
						.getCommunity().getId()) {
					System.out.println("OK");
				} else {
					System.out
							.println("deliver_location.community and community.id exist,but it is different!");
				}
				u.setAddress(dlObj);
			} else {
				dlObj.setCommunity(u.getAddress().getCommunity());
				u.setAddress(dlObj);
				em.merge(u.getAddress());
			}
		} else {
			em.persist(u.getAddress());
		}*/
		
		em.persist(u);
		
		return true;
	}
	
    public UserManager() {
      
    }

}
