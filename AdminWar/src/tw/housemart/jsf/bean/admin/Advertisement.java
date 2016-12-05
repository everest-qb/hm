package tw.housemart.jsf.bean.admin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tw.housemart.util.AdvertiseComparator;
import tw.housemart.cdi.annotation.Admin;
import tw.housemart.ejb.ApManager;
import tw.housemart.ejb.Search;
import tw.housemart.ejb.GoodsSearch;
import tw.housemart.jpa.Advertise;
import tw.housemart.jpa.Announcement;

@Named("advertisement1")
@ApplicationScoped
@Admin
public class Advertisement implements Serializable{
	
	/**
BannerLeft
BannerRight
-------
 
Top
Bargin
Brand
Quantity
Ontime
Combination
	 */

	private Logger log = LoggerFactory.getLogger(Advertisement.class);
	@Inject @Admin
	private GlobalVars gVars;	
	
	@EJB
	private  GoodsSearch gSearch;
	@EJB
	private Search eSearch;
	@EJB
	private ApManager apMgr;
	
	private Map<String,Advertise> cache;
	private List<Announcement> announceList;
	private List<Announcement> conventionList;
	private List<tw.housemart.jpa.Advertisement> advertisememtList;
	private long time;

	@PostConstruct
	public void init() {
		log.trace("init");
		cache=new HashMap<String,Advertise>();
		List<Advertise> list=gSearch.findAllAdvertisement();
		for(int i=0;i<list.size();i++){
			Advertise ad=list.get(i);
			cache.put(ad.getId(),ad);
		}
		announceList=eSearch.findAnnouncement();
		conventionList=eSearch.findConvention();
		advertisememtList=eSearch.findAllAdvertisement();
		time=eSearch.findClusterReloadTime();
	}
	
	/*public void reloadAdvertisement(){
		advertisememtList.clear();
		advertisememtList=eSearch.findAllAdvertisement();
	}
	
	public void reloadAnnounce(){	
		announceList.clear();
		announceList=eSearch.findAnnouncement();
	}
	
	public void reloadConvention(){		
		conventionList.clear();
		conventionList=eSearch.findConvention();
	}
	*/
	public void reloadCache(){
		Map<String,Advertise> cacheTmp=new HashMap<String,Advertise>();
		List<Advertise> list=gSearch.findAllAdvertisement();
		for(int i=0;i<list.size();i++){
			Advertise ad=list.get(i);
			cacheTmp.put(ad.getId(),ad);
		}
		Map<String,Advertise> cleanCache=cache;
		cache=cacheTmp;
		cleanCache.clear();
		log.trace("reload advertise cache!");
	}
	
	public List<Advertise> findLike(String key){
		getCache();
		List<Advertise> returnValue=new ArrayList<Advertise>();
		Iterator<String> itor =cache.keySet().iterator();
		while(itor.hasNext()){
			String id =itor.next();
			if(id.startsWith(key)){
				returnValue.add(cache.get(id));
			}
		}	
		if(returnValue.size()>0){
			//AdvertiseComparator
			Collections.sort(returnValue,new AdvertiseComparator());
		}
		return returnValue;
	}
	
	public String findGid(String id){
		getCache();
		Advertise ad=cache.get(id);
		return ad.getGId();
	}
	
	public String findSrc(String id) {	
		getCache();
		Advertise ad=cache.get(id);		
		return ad.getPicSrc();
	}

	@PreDestroy
	public void destory() {

	}

	public Advertisement() {

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

	public Search geteSearch() {
		return eSearch;
	}

	public void seteSearch(Search eSearch) {
		this.eSearch = eSearch;
	}

	public List<Announcement> getAnnounceList() {
		if(isClusterReload()){
			reloadAll();
		}
		return announceList;
	}

	public void setAnnounceList(List<Announcement> announceList) {
		this.announceList = announceList;
	}

	public List<Announcement> getConventionList() {
		if(isClusterReload()){
			reloadAll();
		}
		return conventionList;
	}

	public List<tw.housemart.jpa.Advertisement> getAdvertisememtList() {
		if(isClusterReload()){
			reloadAll();			
		}
		return advertisememtList;
	}

	public void setAdvertisememtList(List<tw.housemart.jpa.Advertisement> advertisememtList) {
		this.advertisememtList = advertisememtList;
	}

	public void setConventionList(List<Announcement> conventionList) {
		this.conventionList = conventionList;
	}

	public boolean isClusterReload() {
		boolean returnValue=false;
		if(eSearch.findClusterReloadTime()!=time){
			returnValue=true;
		}		
		return returnValue;
	}

	public Map<String, Advertise> getCache() {
		if(isClusterReload()){
			reloadAll();
		}		
		return cache;
	}
	
	private void reloadAll(){
		time=eSearch.findClusterReloadTime();
		reloadCache();				
		announceList=eSearch.findAnnouncement();
		conventionList=eSearch.findConvention();
		advertisememtList=eSearch.findAllAdvertisement();
	}
}
