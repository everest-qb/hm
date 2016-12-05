package tw.housemart.jsf.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tw.housemart.ejb.OrderManager;
import tw.housemart.jpa.Orders;

@Named
@RequestScoped
public class OrderManage implements Serializable {	
	private Logger log = LoggerFactory.getLogger(OrderManage.class);
	
	private List<Orders> orderList;
	private int deleteId;
	
	@EJB
	private OrderManager oMer;
	@Inject
	private Login login;
	
	public OrderManage() {log.trace("Condtractor");
		orderList=new ArrayList<Orders>();
	}

	public void delOrder(){
		log.debug("del order {}",deleteId);
		if(login.checkLogin()){
			oMer.deleteOrder(deleteId, login.getName());
		}else{
			log.trace("del order not login");
		}		
	}
	
	@PostConstruct
	public void init(){
		log.trace("init..");
		if(login!=null && login.checkLogin()){
			orderList=oMer.find4OrderManage(login.getName());
		}
	}
	
	@PreDestroy
	public void destorhy(){
		
	}

	public List<Orders> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<Orders> orderList) {
		this.orderList = orderList;
	}

	public OrderManager getoMer() {
		return oMer;
	}

	public void setoMer(OrderManager oMer) {
		this.oMer = oMer;
	}

	public Login getLogin() {
		return login;
	}

	public void setLogin(Login login) {
		this.login = login;
	}

	public int getDeleteId() {
		return deleteId;
	}

	public void setDeleteId(int deleteId) {log.trace("set del id {}",deleteId);
		this.deleteId = deleteId;
	}

	
}
