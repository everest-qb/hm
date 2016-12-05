package tw.housemart.jsf.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.shiro.crypto.hash.Sha1Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tw.housemart.ejb.UserManager;
import tw.housemart.jpa.User;

@Named
@RequestScoped
public class Member implements Serializable {
	private Logger log = LoggerFactory.getLogger(Member.class);
	
	private String mail;
	private String tel;
	private String password;
	private boolean changed;
	
	@Inject
	private Login login; 
	
	@EJB
	private UserManager um;
	
	public String toChange(){
		if(!login.checkLogin()) return "returnChange";
		User u=login.getUser();
		if(!u.getMail().equals(mail)){
			u.setMail(mail);
		}
		if(!u.getTel().equals(tel)){
			u.setTel(tel);
		}
		if(!login.getPassword().equals(password)){
			login.setPassword(password);
			Sha1Hash hash =new Sha1Hash(password);
			u.setPassword(hash.toString());
		}
		um.update(u);
		changed=true;
		return "returnChange";
	}
	
	@PostConstruct
	public void init() {
		log.trace("init");
		if(login.checkLogin()){			
			User u=login.getUser();
			mail=u.getMail();
			tel=u.getTel();
			password=login.getPassword();			
		}
		changed=false;
	}

	@PreDestroy
	public void destory() {
		log.trace("destory");
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isChanged() {
		return changed;
	}

	public void setChanged(boolean changed) {
		this.changed = changed;
	}


}
