package tw.housemart.jsf.bean.admin;

import java.io.Serializable;
import java.util.ArrayList;
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

import tw.housemart.cdi.annotation.Admin;
import tw.housemart.ejb.GoodsMamager;
import tw.housemart.ejb.GoodsSearch;
import tw.housemart.jpa.Advertise;

/**
 * @author xyzqb
 *   put for sell and advertise  manage
 */
@Named("manageList1")
@RequestScoped
@Admin
public class ManageList implements Serializable{
	private Logger log = LoggerFactory.getLogger(ManageList.class);
	
	private Advertise advertise;
	private String imageSrc;
	
	@EJB
	private GoodsSearch gSearch;
	@EJB
	private GoodsMamager gMgr;
	@Inject @Admin
	private Advertisement adv;

	public String delAdvertiseMent(){
			
		if(advertise.getId().matches("\\w+([0-9][0-9])")){
			log.trace(advertise.getId());
			gMgr.delAdvertisement(advertise.getId());
			adv.reloadCache();
		}else{
			log.warn("Can't delete {}",advertise.getId());
		}	
		advertise=new Advertise();
		return "delAdReturn";
	}
	
	public String loadmage(){log.trace(advertise.getGId());
		if(advertise.getGId()!=null && advertise.getGId().length()>0){
			imageSrc=gSearch.findGoodIcon(advertise.getGId());
		}		log.trace(imageSrc);
		return "loadImgReturn";
	}
	
	public String updateAdvertise(){
		log.trace(advertise.getId());
		log.trace(advertise.getGId());
		log.trace(advertise.getPicSrc());	
		gMgr.updateAdvertisement(advertise);
		adv.reloadCache();
		return "updateAdReturn";
	}
	
	public String findAdvertisement(){
		if(advertise.getId()!=null && advertise.getId().length()>0){
			advertise=gSearch.findAdById(advertise.getId());
			loadmage();
		}		
		return "searchAdReturn";
	}
	
	public Map<String,String> findAllAdvertiseSelect(){
		return gSearch.findAllAdvertiseId();
	}
	
	public String addNewAdvertise(){		
		log.trace(advertise.getId());
		log.trace(advertise.getGId());
		log.trace(advertise.getPicSrc());
		gMgr.addNewAdvertisement(advertise);
		adv.reloadCache();
		return "newAdvertiseReturn";
	}
	
	public List<String> findAutoGoodsId(String prefix){
		log.trace(prefix);
		List<String> returnValue=gSearch.findGoodsId(prefix);
		if(returnValue==null)
			return new ArrayList<String>();
		return returnValue;
	}
	
	@PostConstruct
	public void init(){
		if(advertise==null)advertise=new Advertise();
	}
	
	@PreDestroy
	public void destorhy(){
		
	}

	public GoodsSearch getgSearch() {
		return gSearch;
	}

	public void setgSearch(GoodsSearch gSearch) {
		this.gSearch = gSearch;
	}

	public Advertise getAdvertise() {
		return advertise;
	}

	public void setAdvertise(Advertise advertise) {
		this.advertise = advertise;
	}

	public Advertisement getAdv() {
		return adv;
	}

	public void setAdv(Advertisement adv) {
		this.adv = adv;
	}

	public GoodsMamager getgMgr() {
		return gMgr;
	}

	public void setgMgr(GoodsMamager gMgr) {
		this.gMgr = gMgr;
	}

	public String getImageSrc() {
		return imageSrc;
	}

	public void setImageSrc(String imageSrc) {
		this.imageSrc = imageSrc;
	}
}
