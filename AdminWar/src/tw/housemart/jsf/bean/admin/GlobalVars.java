package tw.housemart.jsf.bean.admin;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;





import tw.housemart.cdi.annotation.Admin;
import tw.housemart.ejb.ApManager;
import tw.housemart.ejb.Search;
import tw.housemart.util.StaticVars;


@Named("globalVars1")
@ApplicationScoped
@Admin
public class GlobalVars implements Serializable {

	private  String BANNER_LEFT;
	private  String BANNER_RIGHT;
	
	private Logger log = LoggerFactory.getLogger(GlobalVars.class);
	//private String imagePath;
	private String skinName;
	private boolean jsfDebug;
	private List<String> explain;
	private Map<String,String> mailProperties;
	/**
	 * For local server used.
	 * May be remove.
	 */
	//private boolean localFlag;
	private Map<Byte,String> statusMap;
	private long time;
	
	@EJB
	private Search eSearch;
	@EJB
	private ApManager apMgr;
	
	public void clusterReload(){
		time=System.currentTimeMillis();
		apMgr.changeClusterReloadTime(time);
	}
	
	public String findLocalLanguage(){
		Locale locale=FacesContext.getCurrentInstance().getViewRoot().getLocale();
		return locale.getLanguage()+"-"+locale.getCountry();
	}
	
	public List<String> findExplainByType(String key){
		Set<String> returnValue=new TreeSet<String>();		
		for(int i=0;i<explain.size();i++){
			if(explain.get(i).startsWith(key)){
				returnValue.add(explain.get(i));
			}
		}
		
		return new ArrayList<String>(returnValue);
	}
	
	@PostConstruct
	public void init(){		
		BANNER_LEFT=StaticVars.BANNER_LEFT;
		BANNER_RIGHT=StaticVars.BANNER_RIGHT;
		//imagePath="/appResources/imgs/";
		//localFlag=true;
		//imagePath=File.separator+"appResources"+ File.separator+"imgs"+ File.separator;
		jsfDebug=true;
	
		statusMap=new HashMap<Byte,String>();
		statusMap.put((byte) 0, "ordermanage.status.0");
		statusMap.put((byte)50, "ordermanage.status.50");
		statusMap.put((byte)100, "ordermanage.status.100");
		statusMap.put((byte)127, "ordermanage.status.127");
		statusMap.put((byte)-110, "ordermanage.status.-110");
		explain=eSearch.findAllExplain();
		mailProperties=eSearch.findMailProperties();
		skinName= eSearch.findSkin();
		time=eSearch.findClusterReloadTime();
	}
	
	public String findOrderStatusMap(byte statusId){
		return statusMap.get(statusId);
	}
	
	@PreDestroy
	public void destory(){
		
	}

	/*public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}*/

	/*public boolean isLocalFlag() {
		return localFlag;
	}

	public void setLocalFlag(boolean localFlag) {
		this.localFlag = localFlag;
	}*/

	public boolean isJsfDebug() {
		return jsfDebug;
	}

	public void setJsfDebug(boolean jsfDebug) {
		this.jsfDebug = jsfDebug;
	}

	public String getSkinName() {
		if(isClusterReload()){
			reloadAll();
		}
		return skinName;
	}
	
	public void setSkinName(String skinName) {	
		apMgr.changeSkin(skinName);		
		this.skinName=skinName;
	}

	public List<String> getExplain() {
		if(isClusterReload()){
			reloadAll();
		}
		return explain;
	}

	public void setExplain(List<String> explain) {
		this.explain = explain;
	}

	public Search geteSearch() {
		return eSearch;
	}

	public void seteSearch(Search eSearch) {
		this.eSearch = eSearch;
	}

	public Map<String, String> getMailProperties() {
		if(isClusterReload()){
			this.mailProperties=eSearch.findMailProperties();
		}
		return mailProperties;
	}

	public void setMailProperties(Map<String, String> mailProperties) {
		this.mailProperties = mailProperties;
	}

	public  String getBannerLeft() {
		return BANNER_LEFT;
	}

	public  String getBannerRight() {
		return BANNER_RIGHT;
	}

	public ApManager getApMgr() {
		return apMgr;
	}

	public void setApMgr(ApManager apMgr) {
		this.apMgr = apMgr;
	}
	
	public boolean isClusterReload() {
		boolean returnValue=false;
		if(eSearch.findClusterReloadTime()!=time){
			returnValue=true;
		}		
		return returnValue;
	}

	private void reloadAll(){
		time=eSearch.findClusterReloadTime();
		explain=eSearch.findAllExplain();
		mailProperties=eSearch.findMailProperties();
		skinName= eSearch.findSkin();		
	}

	public long getTime() {
		return time;
	}
	
	/*public void setClusterReload(boolean clusterReload) {
		apMgr.changeClusterReload(clusterReload);
	}*/	
}
