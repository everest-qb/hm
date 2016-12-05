package tw.housemart.jsf.bean.admin;

import java.io.Serializable;
import java.util.Iterator;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tw.housemart.cdi.annotation.Admin;
import tw.housemart.ejb.UserManager;
import tw.housemart.jpa.User;

@Named("login1")
@SessionScoped
@Admin
public class Login implements Serializable {
	private Logger log = LoggerFactory.getLogger(Login.class);
	private String name;
	private String password;
	private User user;
	
	@EJB
	private  UserManager uMgr;
	
	@PostConstruct
	public void init(){
		this.name="";
		this.password="";
	}
	
	@PreDestroy
	public void destorhy(){
		
	}
	
	public String check(){			
		Subject currentUser = SecurityUtils.getSubject();
		if(currentUser.isAuthenticated()) return "success";
		UsernamePasswordToken  token=new UsernamePasswordToken(name,password);		
		try{
			currentUser.login(token);
			user=uMgr.findUser(name);
			return "success";
		}catch(UnknownAccountException aex){
			log.info("account not found! {}",name);
			return "register";
		}catch(LockedAccountException lex){
			log.info("account locked! {}",name);
			generateMessage("header.login.locked.msg");
			return "";
		}catch(Exception ex){//AuthenticationException  UnknownAccountException
			log.info(ex.getMessage());
			generateMessage("header.login.password.error");			
			return "";
		}		
	}
	
	public boolean chkAdmin(){	
		Subject currentUser = SecurityUtils.getSubject();
		if(currentUser.hasRole("ADMIN")){
			return true;
		}
		return false;
	}
	
	public boolean checkLogin(){
		Subject currentUser = SecurityUtils.getSubject();
		return currentUser.isAuthenticated();
	}
	
	public String logout(){
		Subject currentUser = SecurityUtils.getSubject();
		user=null;
		this.name="";
		this.password="";
		currentUser.logout();		
		return "login";
	}
	
	public void tets(){
		generateMessage("header.login.password.error");
	}
	
	private String generateMessage(String key){
		FacesContext context =FacesContext.getCurrentInstance();
		String msg="";
		try{
		ResourceBundle boundle=ResourceBundle.getBundle("tw.housemart.gmsg.gmsg",context.getViewRoot().getLocale());
		msg=boundle.getString(key);
		}catch(Exception ex){
			
		}
		UIComponent c=findComponent(context.getViewRoot(),"loginId");
		context.addMessage(c.getClientId(), new FacesMessage(msg));
		return "";
	}
	
	private UIComponent findComponent(UIComponent c, String cId) {		
	    if (cId.equals(c.getId())) {
	      return c;
	    }
	    Iterator<UIComponent>childs = c.getFacetsAndChildren();
	    while (childs.hasNext()) {
	      UIComponent tmpC = findComponent(childs.next(), cId);
	      if (tmpC != null) {
	        return tmpC;
	      }
	    }
	    return null;
	  }
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public UserManager getuMgr() {
		return uMgr;
	}

	public void setuMgr(UserManager uMgr) {
		this.uMgr = uMgr;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
		
}
