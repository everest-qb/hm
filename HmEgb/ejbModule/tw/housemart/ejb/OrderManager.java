package tw.housemart.ejb;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import tw.housemart.jpa.Community;
import tw.housemart.jpa.DeliverLocation;
import tw.housemart.jpa.OrderDetail;
import tw.housemart.jpa.Orders;

@Stateless
@LocalBean
public class OrderManager implements OrderManagerLocal,Serializable {

	@PersistenceContext(unitName = "HmJpa")
	private EntityManager em;

	public List<Orders> findOrderBeforeSend(){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -5);
		List<Orders> returnValue = em
				.createQuery(
						"select o from Orders o where o.status>=0 and o.status<=100 and o.createT > :mTime order by o.createT ",
						Orders.class)
				.setParameter("mTime", new Timestamp(cal.getTimeInMillis()))
				.getResultList();		
		if(returnValue!=null){
			for(int i=0;i<returnValue.size();i++){
				returnValue.get(i).getDeliverLocation().getCommunity();
			}
		}		
		return returnValue;
	}
	
	public boolean updateStatus(Orders o){
		Orders order=em.find(Orders.class, o.getId());
		if(order!=null){
			order.setStatus(o.getStatus());
		}
		return true;
	}
	
	public List<Orders> findMonthlyOrders(){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -1);
		List<Orders> returnValue = em
				.createQuery(
						"select o from Orders o where o.status>=0 and o.createT > :mTime order by o.createT desc",
						Orders.class)
				.setParameter("mTime", new Timestamp(cal.getTimeInMillis()))
				.getResultList();		
		if(returnValue!=null){
			for(int i=0;i<returnValue.size();i++){
				returnValue.get(i).getDeliverLocation().getCommunity();
			}
		}		
		return returnValue;
	}
	
	public boolean deleteOrder(int orderId, String belonUserName) {
		try {
			Orders o = em
					.createQuery(
							"select o from Orders o where o.id=:id and o.userName=:name",
							Orders.class).setParameter("id", orderId)
					.setParameter("name", belonUserName).getSingleResult();
			//em.remove(o);
			o.setStatus((byte)-110);
		} catch (NoResultException ex) {
			System.out.println(" no order find ,delete fail! ");
		}

		return true;
	}

	public List<Orders> find4OrderManage(String userName) {

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -1);
		List<Orders> returnValue = em
				.createQuery(
						"select o from Orders o where o.userName=:name and o.status>=0 and o.createT > :mTime order by o.createT desc",
						Orders.class).setParameter("name", userName)
				.setParameter("mTime", new Timestamp(cal.getTimeInMillis()))
				.getResultList();

		for (int i = 0; i < returnValue.size(); i++) {
			Orders o = returnValue.get(i);
			o.getDeliverLocation();
			List<OrderDetail> details = o.getOrderDetails();
			for (int j = 0; j < details.size(); j++) {
				OrderDetail d = details.get(j);
				d.getGoods();
			}
		}

		return returnValue;
	}

	@SuppressWarnings("unchecked")
	public List<String> findTel(String nu4code) {
		return em
				.createQuery(
						"select distinct o.tel from Orders o where o.tel like :code")
				.setParameter("code", nu4code + "%").getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<String> findCommunity(String chr2code) {
		return em
				.createQuery(
						"select distinct c.name from Community c where c.name like :code")
				.setParameter("code", chr2code + "%").getResultList();
	}

	@SuppressWarnings("unchecked")
	public String findCommunityAddress(String cName) {
		List<String> list = em
				.createQuery(
						"select c.address from Community c where c.name=:name")
				.setParameter("name", cName).getResultList();
		if (list.size() > 0)
			return list.get(0);
		return "";
	}

	@SuppressWarnings("unchecked")
	public boolean insertOrder(Orders o) {
		List<Community> c = em
				.createQuery("select c from Community c where c.name=:name")
				.setParameter("name",
						o.getDeliverLocation().getCommunity().getName())
				.getResultList();
		List<DeliverLocation> dl = em
				.createQuery(
						"select dl from DeliverLocation dl where dl.address=:address")
				.setParameter("address", o.getDeliverLocation().getAddress())
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
				o.setDeliverLocation(dlObj);
			}else{
				o.getDeliverLocation().setCommunity(cObj);
				em.persist(o.getDeliverLocation());
			}
		}else{
			if(isAddressExist){
				DeliverLocation dlObj = dl.get(0);
				System.out.println("It is very strange! The address is exist ,but community not.");
				String[] tmp=o.getDeliverLocation().getAddress().split("\\d++많");
				String address=tmp[0]+"xx많";			
				Community cObj= new Community();
				cObj.setAddress(address);
				cObj.setName(o.getDeliverLocation().getCommunity().getName());
				em.persist(cObj);
				o.setDeliverLocation(dlObj);
			}else{//TODO :  not support multi-language
				String[] tmp=o.getDeliverLocation().getAddress().split("\\d++많");
				String address=tmp[0]+"xx많";			
				o.getDeliverLocation().getCommunity()
						.setAddress(address);
				em.persist(o.getDeliverLocation().getCommunity());
				em.persist(o.getDeliverLocation());			
			}		
		}
		
		
		/*if (c.size() > 0) {			
			o.getDeliverLocation().setCommunity(c.get(0));
		} else {
			String[] tmp=o.getDeliverLocation().getAddress().split("\\d++많");
			String address=tmp[0];			
			o.getDeliverLocation().getCommunity()
					.setAddress(address);
			em.persist(o.getDeliverLocation().getCommunity());
		}


		if (dl.size() > 0) {
			DeliverLocation dlObj = dl.get(0);
			if (isCommunityExist) {
				if (dlObj.getCommunity().getId() == o.getDeliverLocation()
						.getCommunity().getId()) {
					System.out.println("OK");
				} else {
					System.out
							.println("deliver_location.community and community.id exist,but it is different!");
				}
				o.setDeliverLocation(dlObj);
			} else {
				dlObj.setCommunity(o.getDeliverLocation().getCommunity());
				o.setDeliverLocation(dlObj);
				em.merge(o.getDeliverLocation());
			}
		} else {
			em.persist(o.getDeliverLocation());
		}*/

		em.persist(o);

		return true;
	}

	public OrderManager() {

	}

}
