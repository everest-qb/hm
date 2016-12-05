package tw.housemart.jsf.bean.admin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
import tw.housemart.ejb.Search;
import tw.housemart.jpa.Category;
import tw.housemart.jpa.Classification;
import tw.housemart.jpa.Good;
import tw.housemart.jpa.Goodsgroup;

@Named("manageCategory1")
@RequestScoped
@Admin
public class ManageCategory implements Serializable {
	private Logger log = LoggerFactory.getLogger(ManageCategory.class);
	
	
	private String goodsId;	
	private Good good;
	private String classficationId;
	private String goodstypeId;
	private String goodsgroupId;
	@Inject @Admin
	private SelectUtil select;
	
	@EJB
	private GoodsSearch gSearch;
	@EJB
	private GoodsMamager gMgr;
	@EJB
	private Search search;
		
	
	public List<Goodsgroup> findAllGoodsGroup(){
		return search.findAllGoodsGroup();		
	}
	
	/*public List<Goodstype> findAllGoodstype(){
		return search.findAllGoodstype();
	}*/
	
	public List<Classification> findAllClassification(){
		return  search.findAllClassification();		
	}
	
	public String loadmage(){
	if(goodsId!=null && goodsId.length()>0){
		good=gSearch.findById(goodsId);
	}		log.trace(good.getGId());
	return "loadImgReturn";
}
	
	public String delMap(){
		String id=goodstypeId+"."+classficationId;
		log.trace("{}/{}",id,goodsId);		
		if(gMgr.delCategoryMap(id,goodsId))		
			clean();
		return "delMapReturn";
	}
	
	public String addMap(){
		String id=goodstypeId+"."+classficationId;
		log.trace("{}/{}",id,goodsId);		
		if(gMgr.addCategoryMap(id, goodsId))
			clean();
		return "addMapReturn";
	}
	
	public List<String> findAutoGoodsId(String prefix){
		log.trace(prefix);
		List<String> returnValue=gSearch.findGoodsId(prefix);
		if(returnValue==null)
			return new ArrayList<String>();
		return returnValue;
	}
	
	public Map<String,Integer> findCategory(){
		Map<String,Integer> returnValue=new TreeMap<String,Integer>();
		List<Category> category= gSearch.findAllCategory();
		for(int i=0;i<category.size();i++){
			Category c=category.get(i);
			returnValue.put(c.getName(), c.getId());
		}		
		return returnValue;
	}
	
	private void clean(){		
		this.classficationId="";		
	}
	
	@PostConstruct
	public void init(){
		good=new Good();
		
	}
	
	@PreDestroy
	public void destory(){
		
	}

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public GoodsMamager getgMgr() {
		return gMgr;
	}

	public void setgMgr(GoodsMamager gMgr) {
		this.gMgr = gMgr;
	}

	public Good getGood() {
		return good;
	}

	public void setGood(Good good) {
		this.good = good;
	}

	public String getClassficationId() {
		return classficationId;
	}

	public void setClassficationId(String classficationId) {
		this.classficationId = classficationId;
	}

	public String getGoodstypeId() {
		return goodstypeId;
	}

	public void setGoodstypeId(String goodstypeId) {
		this.goodstypeId = goodstypeId;
	}

	public String getGoodsgroupId() {
		return goodsgroupId;
	}

	public void setGoodsgroupId(String goodsgroupId) {
		this.goodsgroupId = goodsgroupId;
		
		if(goodsgroupId!=null && goodsgroupId.length()>0){
			if(select.getId()!=goodsgroupId){
			select.setTypeList(search.findGoodstypeByGroup(goodsgroupId));
			}
			select.setId(goodsgroupId);
		}
	}

	public SelectUtil getSelect() {
		return select;
	}

	public void setSelect(SelectUtil select) {
		this.select = select;
	}

}
