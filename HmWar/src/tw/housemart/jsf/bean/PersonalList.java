package tw.housemart.jsf.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tw.housemart.ejb.GoodsSearch;
import tw.housemart.jpa.Good;

@ManagedBean
@ViewScoped
public class PersonalList implements Serializable {
	private Logger log = LoggerFactory.getLogger(PersonalList.class);
	
	private List<GoodItem> store;
	private List<GoodItem> history;
	
	@Inject
	private Login login;
	
	@EJB
	private GoodsSearch gSearch;
	
	
	@PostConstruct
	public void init(){
		store=new ArrayList<GoodItem>();
		history=new ArrayList<GoodItem>();
		if(login.checkLogin()){
			List<Good> glist = gSearch.findHistory(login.getName());
			log.trace("history size:{}",glist.size());
			for (int j = 0; j < glist.size(); j++) {
				Good gObj = glist.get(j);
				GoodItem itme = new GoodItem();
				itme.setId(gObj.getGId());
				itme.setGoodName(gObj.getName());
				itme.setPicName(gObj.getIcon());
				itme.setPrice(gObj.getPrice());
				itme.setDesc(gObj.getDesc());
				itme.setTitle(gObj.getTitle());
				history.add(itme);
			}
			
			glist=gSearch.findStore(login.getName());
			log.trace("store size:{}",glist.size());
			for (int j = 0; j < glist.size(); j++) {
				Good gObj = glist.get(j);
				GoodItem itme = new GoodItem();
				itme.setId(gObj.getGId());
				itme.setGoodName(gObj.getName());
				itme.setPicName(gObj.getIcon());
				itme.setPrice(gObj.getPrice());
				itme.setDesc(gObj.getDesc());
				itme.setTitle(gObj.getTitle());
				store.add(itme);
			}
		}		
	}
	
	@PreDestroy
	public void destorhy(){
		
	}

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}

	public List<GoodItem> getStore() {
		return store;
	}

	public void setStore(List<GoodItem> store) {
		this.store = store;
	}

	public List<GoodItem> getHistory() {
		return history;
	}

	public void setHistory(List<GoodItem> history) {
		this.history = history;
	}
}
