package tw.housemart.jsf.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tw.housemart.ejb.GoodsSearch;
import tw.housemart.ejb.Search;
import tw.housemart.ejb.UserManager;
import tw.housemart.jpa.Good;

@Named
@RequestScoped
public class GoodDetail implements Serializable {

	private Logger log = LoggerFactory.getLogger(GoodDetail.class);
	private String detailId;
	private String picName01;
	private String picName02;
	private String externalPage;
	private String personal;
	private String removePersonal;

	@Inject
	private GlobalVars gVars;
	@Inject
	private Login login;

	@EJB
	private GoodsSearch gSearch;
	@EJB
	private Search search;
	@EJB
	private UserManager uMgr; 

	
	public boolean personalExist(String gid){
		if(!login.checkLogin()) return false;
		return search.findPersonalExist(login.getName(), gid);
	}
	
	@PostConstruct
	public void init() {

		detailId = "";
		picName01 = "";
		picName02 = "";
		externalPage = "";
	}

	@PreDestroy
	public void destory() {

	}

	private void generateData(String id) {
		Good g = gSearch.findById(id);
		if (g != null){
			detailId=g.getGId();
			picName01=g.getPicName01();
			picName02=g.getPicName02();
			externalPage=g.getWeb();
		}else{
			log.warn("Cant not find {} detail!", id);
		}
	}

	public String getDetailId() {
		return detailId;
	}

	public void setDetailId(String detailId) {

		this.detailId = detailId;
		generateData(detailId);
	}

	public String getPicName01() {
		return picName01;
	}

	public void setPicName01(String picName01) {
		this.picName01 = picName01;
	}

	public String getPicName02() {
		return picName02;
	}

	public void setPicName02(String picName02) {
		this.picName02 = picName02;
	}

	public GlobalVars getgVars() {
		return gVars;
	}

	public void setgVars(GlobalVars gVars) {
		this.gVars = gVars;
	}

	public String getExternalPage() {
		return externalPage;
	}

	public void setExternalPage(String externalPage) {
		this.externalPage = externalPage;
	}

	public GoodsSearch getgSearch() {
		return gSearch;
	}

	public void setgSearch(GoodsSearch gSearch) {
		this.gSearch = gSearch;
	}

	public void setPersonal(String personal) {
		log.trace("put into personal {}",personal);
		if(login.checkLogin())
			uMgr.personalCollect(login.getName(), personal);
		this.personal = personal;
	}

	public void setRemovePersonal(String removePersonal) {
		log.trace("remove personal {}",removePersonal);
		if(login.checkLogin()){
			uMgr.removePersonalCollect(login.getName(), removePersonal);
		}
		this.removePersonal = removePersonal;
	}
}
