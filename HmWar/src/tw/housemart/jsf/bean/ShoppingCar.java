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
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tw.housemart.ejb.GoodsSearch;
import tw.housemart.ejb.OrderManager;
import tw.housemart.jpa.Community;
import tw.housemart.jpa.DeliverLocation;
import tw.housemart.jpa.Good;
import tw.housemart.jpa.OrderDetail;
import tw.housemart.jpa.Orders;

@Named
@SessionScoped
public class ShoppingCar implements Serializable {

	private Logger log = LoggerFactory.getLogger(ShoppingCar.class);
	private Map<String, Integer> orders;
	private String itemId;
	private String tel;
	private String address;
	private String communityName;
	private Map<String, GoodItem> goodsInfo;

	@Inject
	private GlobalVars gVars;
	@Inject
	private Login login;

	@EJB
	private GoodsSearch gSearch;
	@EJB
	private OrderManager oGm;

	public ShoppingCar() {
		log.trace("Contractor");
	}

	@PostConstruct
	public void init() {
		orders = new HashMap<String, Integer>();
		goodsInfo = new HashMap<String, GoodItem>();
		if (login.checkLogin()) {
			communityName = login.getUser().getAddress().getCommunity()
					.getName();
			address = login.getUser().getAddress().getAddress();
		}
		log.trace("init");
	}

	@PreDestroy
	public void destory() {
		log.trace("destory");
	}

	/*
	 * public GoodItem findGoodItem(String id) { log.trace("find goods item {}",
	 * id); if (id != null && id.length() > 0) { Good g = gSearch.findById(id);
	 * GoodItem good = new GoodItem(); if (g != null) { good.setId(g.getGId());
	 * good.setGoodName(g.getName()); good.setPicName(g.getIcon()); } return
	 * good; } else return null; }
	 */

	public int findAmountOfOrder(String id) {
		if (orders.containsKey(id)) {
			return orders.get(id);
		} else {
			return 0;
		}
	}

	public void delOrder(String id) {
		if (orders.containsKey(id)) {
			orders.remove(id);
			goodsInfo.remove(id);
		}
	}

	public String findMail() {
		return login.getUser().getMail();
	}

	public List<String> findAutocompleteTel(String prefix) {
		List<String> returnValue = oGm.findTel(prefix);
		if (returnValue == null)
			return new ArrayList<String>();
		return returnValue;
	}

	public List<String> findAutocompleteCommunity(String prefix) {
		log.trace("Auto community {}", prefix);
		List<String> returnValue = oGm.findCommunity(prefix);
		if (returnValue == null)
			return new ArrayList<String>();
		return returnValue;
	}

	public float findPrice(String id) {
		if (goodsInfo.containsKey(id)) {
			return orders.get(id) * goodsInfo.get(id).getPrice();
		} else {
			return 0;
		}
	}

	public float findTotalPrice() {
		Iterator<String> keys = orders.keySet().iterator();
		float returnValue = 0;
		while (keys.hasNext()) {
			String key = keys.next();
			float priice = goodsInfo.get(key).getPrice();
			returnValue += priice * orders.get(key);
		}
		return returnValue;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public int getAmountOfItem() {
		if (itemId != null && orders.containsKey(itemId)) {
			return orders.get(itemId);
		} else {
			return 0;
		}
	}

	public void setAmountOfItem(int amountOfItem) {
		log.trace("set amount of item {} for {} ",amountOfItem,itemId);
		if (itemId != null && amountOfItem > 0) {
			orders.put(itemId, amountOfItem);
			Good g = gSearch.findById(itemId);
			GoodItem good = new GoodItem();
			if (g != null) {
				good.setId(g.getGId());
				good.setGoodName(g.getName());
				good.setPicName(g.getIcon());
				good.setPrice(g.getPrice());
				goodsInfo.put(itemId, good);
			}
		} else if (itemId != null && amountOfItem == 0) {
			orders.remove(itemId);
			goodsInfo.remove(itemId);
		}
	}

	public void guestAddress() {
		if (communityName != null && communityName.length() > 0) {
			address = oGm.findCommunityAddress(communityName);
		}
	}

	public String toPay() {
		log.trace("to pay:");
		if (orders.size() <= 0)
			return "toMain";
		Orders o = new Orders();
		Community c = new Community();
		c.setName(communityName);
		DeliverLocation dl = new DeliverLocation();
		dl.setAddress(address);
		dl.setCommunity(c);
		o.setDeliverLocation(dl);
		if (login.checkLogin()) {
			o.setUserName(login.getName());
		} else {
			o.setTel(tel);
		}
		List<OrderDetail> list = new ArrayList<OrderDetail>();
		Iterator<String> iterator = orders.keySet().iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			// GoodItem item=goodsInfo.get(key);
			OrderDetail detail = new OrderDetail();
			detail.setAmount(orders.get(key).byteValue());
			Good g = new Good();
			g.setGId(key);
			detail.setGoods(g);
			detail.setOrder(o);
			detail.setPrice(goodsInfo.get(key).getPrice());
			list.add(detail);
		}
		o.setOrderDetails(list);

		oGm.insertOrder(o);
		cleanCar();
		return "orderManage";
	}

	private void cleanCar() {
		orders = new HashMap<String, Integer>();
		goodsInfo = new HashMap<String, GoodItem>();
		tel = "";
		address = "";
		communityName = "";
		this.itemId = "";
	}

	public Map<String, Integer> getOrders() {
		return orders;
	}

	public void setOrders(Map<String, Integer> orders) {
		this.orders = orders;
	}

	public GlobalVars getgVars() {
		return gVars;
	}

	public void setgVars(GlobalVars gVars) {
		this.gVars = gVars;
	}

	public GoodsSearch getgSearch() {
		return gSearch;
	}

	public void setgSearch(GoodsSearch gSearch) {
		this.gSearch = gSearch;
	}

	public Login getLogin() {
		return login;
	}

	public void setLogin(Login login) {
		this.login = login;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getAddress() {
		if (login.checkLogin() && (address == null || address.length() == 0)) {
			address = login.getUser().getAddress().getAddress();
		}
		return address;
	}

	public Map<String, GoodItem> getGoodsInfo() {
		return goodsInfo;
	}

	public void setGoodsInfo(Map<String, GoodItem> goodsInfo) {
		this.goodsInfo = goodsInfo;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCommunityName() {
		if (login.checkLogin()
				&& (communityName == null || communityName.length() == 0)) {
			communityName = login.getUser().getAddress().getCommunity()
					.getName();
		}
		return communityName;
	}

	public void setCommunityName(String communityName) {
		log.trace("Communrty {}", communityName);
		this.communityName = communityName;
	}

}
