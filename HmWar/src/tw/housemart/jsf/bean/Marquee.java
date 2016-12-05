package tw.housemart.jsf.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tw.housemart.ejb.OrderManager;
import tw.housemart.ejb.UserManager;
import tw.housemart.jpa.Orders;
import tw.housemart.jpa.User;
import tw.housemart.util.RandomList;

@Named
@RequestScoped
public class Marquee implements Serializable {
	private Logger log = LoggerFactory.getLogger(Marquee.class);
	
	private String communityName;
	
	@Inject
	private Login login;
	
	@EJB
	private UserManager uMgr;
	@EJB
	private OrderManager oMgr;
	
	@PostConstruct
	public void init(){
		log.trace("init");
		communityName="";
	}
	
	@PreDestroy
	public void destorhy(){
		
	}

	public String getCommunityName() {
		
		String cName="";
		
		if(login.checkLogin()){
			User u=uMgr.findUser(login.getName());
		     cName=u.getAddress().getCommunity().getName();
		}
		List<Orders> order= oMgr.findOrderBeforeSend();
		Map<String,Integer> oCount=new HashMap<String,Integer>();
		for(int i=0;i<order.size();i++){
			String name=order.get(i).getDeliverLocation().getCommunity().getName();
			if(oCount.containsKey(name)){
				oCount.put(name, oCount.get(name)+1);
			}else{
				oCount.put(name,1);
			}			
		}	
		Iterator<String> itor =oCount.keySet().iterator();
		List<String> randomKey=new ArrayList<String>();
		while(itor.hasNext()){
			randomKey.add(itor.next());
		}
		randomKey=new RandomList<String>(randomKey).Random();
		for(int i=0;i<randomKey.size();i++){
			String key=randomKey.get(i);
			int count=oCount.get(key);
			if (cName.equals(key)) {
				key = "<font color=red>" + key +"("+count+")"+ "</font>";
			}else{
				key=key+"("+count+")";
			}
			communityName+=" "+key;
		}	
		
		log.trace("communityName: {}",communityName);		
		return communityName;
	}
}
