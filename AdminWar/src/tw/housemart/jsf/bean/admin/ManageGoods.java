package tw.housemart.jsf.bean.admin;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tw.housemart.cdi.annotation.Admin;
import tw.housemart.ejb.GoodsMamager;
import tw.housemart.ejb.GoodsSearch;
import tw.housemart.ejb.SupplyManager;
import tw.housemart.jpa.Good;
import tw.housemart.jpa.Supplier;

@Named("manageGoods1")
@RequestScoped
@Admin
public class ManageGoods implements Serializable {
	private Logger log = LoggerFactory.getLogger(ManageGoods.class);
	
	private GoodItem item;
	private GoodDetail detail;
	private String searchGoodsId;
	private int supplyerId;
	
	@EJB
	private GoodsMamager gMgr;
	@EJB
	private GoodsSearch gSearch;
	@EJB
	private SupplyManager sMgr;
	
	public String generateGoodsId(){
		Supplier supply=sMgr.findById(supplyerId);
		SimpleDateFormat f =new SimpleDateFormat("yyyyMMdd");
		String dateStr=f.format(Calendar.getInstance().getTime());
		String goodCode="";
		String supCode="";
		if(supplyerId<10){
			supCode="00"+supplyerId;
		}else if(supplyerId<100){
			supCode="0"+supplyerId;
		}else{
			supCode=""+supplyerId;
		}
		
		if(supply.getMaxGid()+1<10){
			goodCode="00"+(supply.getMaxGid()+1);
		}else if(supply.getMaxGid()+1<100){
			goodCode="0"+(supply.getMaxGid()+1);
		}else{
			goodCode=""+(supply.getMaxGid()+1);
		}
		
		log.trace("{}/{}",supplyerId,supCode);
		log.trace(supCode+goodCode+dateStr);		
		item.setId(supCode+goodCode+dateStr);
		return "generateGoodsIdReturn";
	}
	
	public List<Supplier> findAllSupplier(){
		return sMgr.findAllSupplier();
	}
	
	public String searchGoods(){
		log.trace(searchGoodsId);
		Good g=gSearch.findById(searchGoodsId);		
		item.setId(searchGoodsId);
		item.setDesc(g.getDesc());
		item.setGoodName(g.getName());
		item.setPicName(g.getIcon());
		item.setPrice(g.getPrice());		
		detail.setExternalPage(g.getWeb());
		detail.setPicName01(g.getPicName01());
		detail.setPicName02(g.getPicName02());
		return "searchReturn"; 
	}
	
	public List<String> findAutoGoodsId(String prefix){
		log.trace(prefix);
		List<String> returnValue=gSearch.findGoodsId(prefix);
		if(returnValue==null)
			return new ArrayList<String>();
		return returnValue;
	}
	
	public String modifyGoods(){
		log.trace(item.getId());
		log.trace(item.getGoodName());
		log.trace(item.getPicName());
		log.trace(""+item.getPrice());
		log.trace(item.getDesc());
		log.trace(detail.getPicName01());
		log.trace(detail.getPicName02());
		log.trace(detail.getExternalPage());
		if(item.getId().length()==14){
			Good g=new Good();
			g.setDesc(item.getDesc());
			g.setGId(item.getId());
			g.setIcon(item.getPicName());
			g.setName(item.getGoodName());
			g.setPicName01(detail.getPicName01());
			g.setPicName02(detail.getPicName02());
			g.setPrice(item.getPrice());
			g.setWeb(detail.getExternalPage());
			gMgr.updateGoods(g);
			log.trace("update");
		}
		
		return "modifyReturn";
	}
	
	public String addNewGoods(){
		log.trace(item.getId());
		log.trace(item.getGoodName());
		log.trace(item.getPicName());
		log.trace(""+item.getPrice());
		log.trace(item.getDesc());
		log.trace(detail.getPicName01());
		log.trace(detail.getPicName02());
		log.trace(detail.getExternalPage());
		if(item.getId().length()==14){
			Good g=new Good();
			g.setDesc(item.getDesc());
			g.setGId(item.getId());
			g.setIcon(item.getPicName());
			g.setName(item.getGoodName());
			g.setPicName01(detail.getPicName01());
			g.setPicName02(detail.getPicName02());
			g.setPrice(item.getPrice());
			g.setWeb(detail.getExternalPage());
			gMgr.addNewGoods(g);
			log.trace("save");
		}
		
		return "returnManage";
	}
	
	@PostConstruct
	public void init(){
		if(item==null)item=new GoodItem();
		if(detail==null)detail=new GoodDetail();
	}
	
	@PreDestroy
	public void destorhy(){
		
	}

	public GoodItem getItem() {
		return item;
	}

	public void setItem(GoodItem item) {
		this.item = item;
	}

	public GoodDetail getDetail() {
		return detail;
	}

	public void setDetail(GoodDetail detail) {
		this.detail = detail;
	}

	public GoodsMamager getgMgr() {
		return gMgr;
	}

	public void setgMgr(GoodsMamager gMgr) {
		this.gMgr = gMgr;
	}

	public GoodsSearch getgSearch() {
		return gSearch;
	}

	public void setgSearch(GoodsSearch gSearch) {
		this.gSearch = gSearch;
	}

	public String getSearchGoodsId() {
		return searchGoodsId;
	}

	public void setSearchGoodsId(String searchGoodsId) {
		this.searchGoodsId = searchGoodsId;
	}

	public int getSupplyerId() {
		return supplyerId;
	}

	public void setSupplyerId(int supplyerId) {
		this.supplyerId = supplyerId;
	}
	
}
