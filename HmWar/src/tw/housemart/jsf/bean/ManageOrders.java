package tw.housemart.jsf.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tw.housemart.ejb.OrderManager;
import tw.housemart.ejb.UserManager;
import tw.housemart.jpa.Orders;
import tw.housemart.jpa.User;

@ManagedBean
@ViewScoped
public class ManageOrders implements Serializable {

	private Logger log = LoggerFactory.getLogger(ManageOrders.class);
	
	private Vector<Byte> status;
	private List<Orders> listOrders;
	private long orderId;
	private byte changeStatus;
	private byte location;
	
	@EJB
	private OrderManager oMgr;
	@EJB
	private UserManager uMgr;
	
	public User findUser(String name){
		return uMgr.findUser(name);
	}
	
	public void toAction(){
		log.trace("ACT:{}",listOrders.size());
		log.trace("ACT:{}",orderId);
		log.trace("ACT:{}",changeStatus);
		Orders delO=null;
		for(int i=0;i<listOrders.size();i++){
			Orders o=listOrders.get(i);
			if(changeStatus<0){
				if(o.getId()==orderId){
					delO=o;
					o.setStatus(changeStatus);
					oMgr.updateStatus(o);
				}
			}else{
				if(o.getId()==orderId){
					o.setStatus(changeStatus);
					oMgr.updateStatus(o);
				}
			}			
		}
		if(delO!=null)listOrders.remove(delO);
		
		//save to db
	}
	
	public List<Orders> findOrderByStatus(byte key){
		log.trace("{}",""+key);
		List<Orders> returnValue=new ArrayList<Orders>();
		for(int i=0;i<listOrders.size();i++){
			Orders o=listOrders.get(i);
			if(o.getStatus()==key){
				returnValue.add(o);
			}
		}
		return returnValue;
	}
	
	@PostConstruct
	public void init(){
		status=new Vector<Byte>();
		status.add((byte)0);status.add((byte)50);
		status.add((byte)100);status.add((byte)127);
		location=0;
		listOrders=oMgr.findMonthlyOrders();log.trace("init...");
	}
	
	@PreDestroy
	public void destorhy(){
		
	}

	public OrderManager getoMgr() {
		return oMgr;
	}

	public void setoMgr(OrderManager oMgr) {
		this.oMgr = oMgr;
	}

	public List<Orders> getListOrders() {
		return listOrders;
	}

	public void setListOrders(List<Orders> listOrders) {
		this.listOrders = listOrders;
	}

	public Vector<Byte> getStatus() {
		return status;
	}

	public void setStatus(Vector<Byte> status) {
		this.status = status;
	}

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public byte getChangeStatus() {
		return changeStatus;
	}

	public void setChangeStatus(byte changeStatus) {
		this.changeStatus = changeStatus;
	}

	public UserManager getuMgr() {
		return uMgr;
	}

	public void setuMgr(UserManager uMgr) {
		this.uMgr = uMgr;
	}

	public byte getLocation() {
		return location;
	}

	public void setLocation(byte location) {
		this.location = location;
	}
}
