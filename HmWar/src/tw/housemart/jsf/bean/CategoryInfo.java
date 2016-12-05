package tw.housemart.jsf.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tw.housemart.ejb.GoodsSearch;
import tw.housemart.ejb.Search;
import tw.housemart.jpa.Classification;
import tw.housemart.jpa.Goodsgroup;
import tw.housemart.jpa.Goodstype;

@Named
@SessionScoped
public class CategoryInfo implements Serializable {
	private Logger log = LoggerFactory.getLogger(CategoryInfo.class);
	
	private int categoryId;
	private List<Classification> categoryList;
	private String categoryTrait;
	private String categoryType;
	private List<Goodsgroup> mainTypeList;
	
	@EJB
	private  GoodsSearch gSearch;
	@EJB
	private Search search;
	
	public String findTraitResource(String key){
		return "main.goodstype."+key;
	}
	
	public List<Goodstype> findGoodsType(String prefix){
		return gSearch.findGType(prefix);	
	}

	public String findCategoryResource(String key){
		return "main.category."+key;
	}
	
	@PostConstruct
	public void init(){
		log.trace("init");
		categoryId=3;
		categoryTrait="new";
		categoryType="food.cookie";
		categoryList=search.findAllClassification();
		mainTypeList=search.findAllGoodsGroup();
	}
	
	@PreDestroy
	public void destorhy(){
		
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryTrait() {
		return categoryTrait;
	}

	public void setCategoryTrait(String categoryTrait) {
		this.categoryTrait = categoryTrait;
	}

	public String getCategoryType() {
		return categoryType;
	}

	public void setCategoryType(String categoryType) {log.trace("set CategoryType {}",categoryType);
		this.categoryType = categoryType;
	}

	public List<Goodsgroup> getMainTypeList() {
		return mainTypeList;
	}

	public void setMainTypeList(List<Goodsgroup> mainTypeList) {
		this.mainTypeList = mainTypeList;
	}

	public List<Classification> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<Classification> categoryList) {
		this.categoryList = categoryList;
	}
}
