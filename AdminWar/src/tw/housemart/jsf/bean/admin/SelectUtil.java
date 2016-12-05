package tw.housemart.jsf.bean.admin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tw.housemart.cdi.annotation.Admin;
import tw.housemart.jpa.Goodstype;

@Named("selectUtil1")
@SessionScoped
@Admin
public class SelectUtil implements Serializable {

	private Logger log = LoggerFactory.getLogger(SelectUtil.class);

	private String id;
	private List<Goodstype> typeList;
	
	private String groupSelectForModify;
	
	
	
	@PostConstruct
	public void init(){
		typeList= new ArrayList<Goodstype>();
		groupSelectForModify="group";
	}
	
	@PreDestroy
	public void destory(){
		
	}

	public List<Goodstype> getTypeList() {
		return typeList;
	}

	public void setTypeList(List<Goodstype> typeList) {
		this.typeList = typeList;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGroupSelectForModify() {
		return groupSelectForModify;
	}

	public void setGroupSelectForModify(String groupSelectForModify) {
		this.groupSelectForModify = groupSelectForModify;
	}
}
