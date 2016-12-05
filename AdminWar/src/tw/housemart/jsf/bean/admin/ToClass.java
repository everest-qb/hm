package tw.housemart.jsf.bean.admin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
import tw.housemart.ejb.Search;
import tw.housemart.jpa.Classification;
import tw.housemart.jpa.Goodsgroup;
import tw.housemart.jpa.Goodstype;


@Named("toClass1")
@RequestScoped
@Admin
public class ToClass implements Serializable {
	private Logger log = LoggerFactory.getLogger(ToClass.class);
	
	private String selectKind;
	private String id;
	private String twName;
	private String cnName;
	private String usName;
	
	@Inject @Admin
	private SelectUtil sUtil;
	
	
	@EJB
	private Search search;
	@EJB
	private GoodsMamager gMgr;
	
	public String updateKind(){
		String sGroup=sUtil.getGroupSelectForModify();
		if("group".equals(sGroup)){
			Goodsgroup temp=new Goodsgroup();
			temp.setCnname(cnName);
			temp.setId(id);
			temp.setUsname(usName);
			temp.setTwname(twName);
			if(gMgr.updateKind(temp)){
				clean();
			}
		}else if("type".equals(sGroup)){
			Goodstype temp=new Goodstype();
			temp.setCnname(cnName);
			temp.setId(id);
			temp.setUsname(usName);
			temp.setTwname(twName);
			if(gMgr.updateKind(temp)){
				clean();
			}
		}else if("classification".equals(sGroup)){
			Classification temp=new Classification();
			temp.setCnname(cnName);
			temp.setId(id);
			temp.setUsname(usName);
			temp.setTwname(twName);
			if(gMgr.updateKind(temp)){
				clean();
			}
		}
		
		
		return "updateKindReturn";
	}
	
	public String SearchItemByIdOfKind(){
		String sGroup=sUtil.getGroupSelectForModify();		
		if("group".equals(sGroup)){
			Goodsgroup g =search.findGroupById(id);
			twName=g.getTwname();
			cnName=g.getCnname();
			usName=g.getUsname();
		}else if("type".equals(sGroup)){
			Goodstype t=search.findTypeById(id);
			twName=t.getTwname();
			cnName=t.getCnname();
			usName=t.getUsname();
		}else if("classification".equals(sGroup)){
			Classification c=search.findClassificationById(id);
			twName=c.getTwname();
			cnName=c.getCnname();
			usName=c.getUsname();
		}		
		return "searchItemReturn";
	}
	
	public List<String> findAutoccompleteId(String prefix){
		List<String> returnValue=new ArrayList<String>();
		String sGroup=sUtil.getGroupSelectForModify();
		log.trace("{} {}",prefix,sGroup);
		if("group".equals(sGroup)){
			returnValue=search.findGroupIdByKey(prefix);
		}else if("type".equals(sGroup)){
			returnValue=search.findTypeIdByKey(prefix);
		}else if("classification".equals(sGroup)){
			return search.findClassificationIdByKey(prefix);
		}
		
		return returnValue;
	}
	
	public List<Goodsgroup> findAllGoodsGroup(){
		return search.findAllGoodsGroup();		
	}
	
	public String addNew(){
		log.trace(selectKind);
		log.trace(id);
		log.trace(twName);
		if("group".equals(selectKind)){
			Goodsgroup g=new Goodsgroup();
			g.setCnname(cnName);
			g.setId(id);
			g.setTwname(twName);
			g.setUsname(usName);
			if(gMgr.addNewGroup(g)){
				clean();
			}
		}else if("type".equals(selectKind)){
			Goodstype t =new Goodstype();
			t.setCnname(cnName);
			t.setId(id);
			t.setTwname(twName);
			t.setUsname(usName);
			if(gMgr.addNewType(t)){
				clean();
			}
		}else if("classification".equals(selectKind)){
			Classification c =new Classification();
			c.setCnname(cnName);
			c.setId(id);
			c.setTwname(twName);
			c.setUsname(usName);
			if(gMgr.addNewClassification(c)){
				clean();
			}
		}				
		return "addnewReturn";
	}
	
	
	private void clean(){
		this.cnName="";
		this.id="";
		this.twName="";
		this.usName="";
	}
	
	@PostConstruct
	public void init(){		
		selectKind="type";
	}
	
	@PreDestroy
	public void destory(){
		
	}

	public String getSelectKind() {
		return selectKind;
	}

	public void setSelectKind(String selectKind) {
		this.selectKind = selectKind;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTwName() {
		return twName;
	}

	public void setTwName(String twName) {
		this.twName = twName;
	}

	public String getUsName() {
		return usName;
	}

	public void setUsName(String usName) {
		this.usName = usName;
	}

	public String getCnName() {
		return cnName;
	}

	public void setCnName(String cnName) {
		this.cnName = cnName;
	}

	public SelectUtil getsUtil() {
		return sUtil;
	}

	public void setsUtil(SelectUtil sUtil) {
		this.sUtil = sUtil;
	}

}
