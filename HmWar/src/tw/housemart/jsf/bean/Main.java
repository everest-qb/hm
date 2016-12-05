package tw.housemart.jsf.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tw.housemart.util.RandomList;
import tw.housemart.ejb.GoodsSearch;
import tw.housemart.jpa.Good;

@ManagedBean
@ViewScoped
public class Main implements Serializable{

	private Logger log = LoggerFactory.getLogger(Main.class);
	private List<GoodItem> dataList;
	private String SearchKey;
	
	@Inject
	private GlobalVars gVars;
	@Inject
	private CategoryInfo categoryInfo;
	
	@EJB
	private  GoodsSearch gSearch;
	
	public void searchGoods(){
		log.trace(SearchKey);				
		List<Good> glist = gSearch.searchByName(SearchKey);
		dataList.clear();
		for (int j = 0; j < glist.size(); j++) {
			Good gObj = glist.get(j);
			GoodItem itme = new GoodItem();
			itme.setId(gObj.getGId());
			itme.setGoodName(gObj.getName());
			itme.setPicName(gObj.getIcon());
			itme.setPrice(gObj.getPrice());
			itme.setDesc(gObj.getDesc());
			itme.setTitle(gObj.getTitle());
			dataList.add(itme);			
		}
		//Collections.sort(dataList, new GoodComparator());
		RandomList<GoodItem> r= new RandomList<GoodItem>(dataList);
		dataList=r.Random();
	}
	
	public void changeGoodsType(){
		log.trace("{}/{}",categoryInfo.getCategoryTrait(),categoryInfo.getCategoryType());
		changeToCategory();
	}
	
	public void changeToCategory() {				
		List<Good> glist = gSearch.findByCategoryName(categoryInfo.getCategoryType()+"."+categoryInfo.getCategoryTrait());
		dataList.clear();
		for (int j = 0; j < glist.size(); j++) {
			Good gObj = glist.get(j);
			GoodItem itme = new GoodItem();
			itme.setId(gObj.getGId());
			itme.setGoodName(gObj.getName());
			itme.setPicName(gObj.getIcon());
			itme.setPrice(gObj.getPrice());
			itme.setDesc(gObj.getDesc());
			itme.setTitle(gObj.getTitle());
			dataList.add(itme);			
		}
		//Collections.sort(dataList, new GoodComparator());
		RandomList<GoodItem> r= new RandomList<GoodItem>(dataList);
		dataList=r.Random();
	}
	
	@PostConstruct
	public void init() {
		dataList = new ArrayList<GoodItem>();

		Map<String, String> params = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestParameterMap();
		String goodsId = params.get("gid");
		log.trace("init {}", goodsId);
		if (goodsId != null && goodsId.length() > 0) {
			if(gVars.getBannerLeft().equals(goodsId) || gVars.getBannerRight().equals(goodsId)){
				List<Good> glist=gSearch.findByBanner(goodsId);
				for (int j = 0; j < glist.size(); j++) {
					Good gObj = glist.get(j);
					GoodItem itme = new GoodItem();
					itme.setId(gObj.getGId());
					itme.setGoodName(gObj.getName());
					itme.setPicName(gObj.getIcon());
					itme.setPrice(gObj.getPrice());
					itme.setDesc(gObj.getDesc());
					itme.setTitle(gObj.getTitle());
					dataList.add(itme);
				}
			}else{
				Good gObj = gSearch.findById(goodsId);
				GoodItem itme = new GoodItem();
				itme.setId(gObj.getGId());
				itme.setGoodName(gObj.getName());
				itme.setPicName(gObj.getIcon());
				itme.setPrice(gObj.getPrice());
				itme.setDesc(gObj.getDesc());
				itme.setTitle(gObj.getTitle());
				dataList.add(itme);	
				categoryInfo.setCategoryType("");
				categoryInfo.setCategoryTrait("new");
				log.trace("from gid: {}",categoryInfo.getCategoryType()+"."+categoryInfo.getCategoryTrait());
			}			
		} else {			
			List<Good> glist = gSearch.findByCategoryName(categoryInfo.getCategoryType()+"."+categoryInfo.getCategoryTrait());
			for (int j = 0; j < glist.size(); j++) {
				Good gObj = glist.get(j);
				GoodItem itme = new GoodItem();
				itme.setId(gObj.getGId());
				itme.setGoodName(gObj.getName());
				itme.setPicName(gObj.getIcon());
				itme.setPrice(gObj.getPrice());
				itme.setDesc(gObj.getDesc());
				itme.setTitle(gObj.getTitle());
				dataList.add(itme);
			}
		}
		//Collections.sort(dataList, new GoodComparator());
		RandomList<GoodItem> r= new RandomList<GoodItem>(dataList);
		dataList=r.Random();
	}
	
	@PreDestroy
	public void destorhy(){
		
	}

	public List<GoodItem> getDataList() { 		
		return dataList;
	}

	public void setDataList(List<GoodItem> dataList) {
		this.dataList = dataList;
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

	public CategoryInfo getCategoryInfo() {
		return categoryInfo;
	}

	public void setCategoryInfo(CategoryInfo categoryInfo) {
		this.categoryInfo = categoryInfo;
	}

	public String getSearchKey() {
		return SearchKey;
	}

	public void setSearchKey(String searchKey) {
		SearchKey = searchKey;
	}

}
