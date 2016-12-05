package tw.housemart.jsf.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.apache.shiro.crypto.hash.Sha1Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tw.housemart.ejb.OrderManager;
import tw.housemart.ejb.UserManager;
import tw.housemart.jpa.Community;
import tw.housemart.jpa.DeliverLocation;
import tw.housemart.jpa.Role;
import tw.housemart.jpa.User;

@ManagedBean
@ViewScoped
public class Register implements Serializable {

	private Logger log = LoggerFactory.getLogger(Register.class);

	private String name;

	private String mail;

	private String tel;

	private String password;

	private String communityName;

	private String address;
	
	private boolean nameDuplicate;

	@Inject
	private Login login;

	@EJB
	private OrderManager oGm;
	@EJB
	private UserManager um;

	public List<String> findAutocompleteCommunity(String prefix) {log.trace("findAutocompleteCommunity: {}",prefix);		
		List<String> returnValue = oGm.findCommunity(prefix);
		if (returnValue == null)
			return new ArrayList<String>();
		return returnValue;
	}

	public void guestAddress() {log.trace("guestAddress: {}",communityName);
		if (communityName != null && communityName.length() > 0) {
			address = oGm.findCommunityAddress(communityName);
		}
	}

	public String toRegister(){
		//role NormalUser
		Role r=new Role();
		r.setRoleName("NormalUser");
		List<Role> lr= new ArrayList<Role>();
		lr.add(r);
		Sha1Hash hash =new Sha1Hash(password);
		Community c= new Community();
		c.setName(communityName);
		DeliverLocation dl=new DeliverLocation();
		dl.setAddress(address);
		dl.setCommunity(c);
		
		User u =new User();
		u.setMail(mail);
		u.setName(name);		
		u.setPassword(hash.toString());//shiro password encode
		u.setRoles(lr);
		u.setTel(tel);
		u.setAddress(dl);		
		boolean isAdded= um.insertNormalUser(u);//add user
		//login
		if(isAdded){
			um.insertLinuxAccount(name, password);
			login.setName(name);
			login.setPassword(password);
			String condition=login.check();
			if("success".equals(condition)){
				log.trace("register and login");
			}else{
				log.trace("register but not login!");
			}
		}		
		return "toMain";
	}
	
	public void checkDuplicateName(){
		if(um.findUser(name)!=null){
			nameDuplicate=true;log.trace("checkDuplicateName {}:found",name);
		}else{
			nameDuplicate=false;log.trace("checkDuplicateName {}:none",name);
		}
	}
	
	/**
	 * rule HM0000000
	 * HM housemart
	 * 00 type
	 * 00000  random number
	 * @return  member number 
	 */
	private String genRandomMember(){
		String returnValue="";
		Random r=new Random();
		int rIn=r.nextInt(99999)+1;
		returnValue="HM00"+rIn;
		while(um.findUser(returnValue)!=null){
			rIn=r.nextInt(99999)+1;
			returnValue="HM00"+rIn;
		}
		log.trace("ne nember:{}",returnValue);
		return returnValue;
	}
	
	public Register() {
		log.trace("Contractor");
	}

	@PostConstruct
	public void init() {
		log.trace("init");
		this.nameDuplicate=false;
		this.name = genRandomMember();
				//login.getName();		
	}

	@PreDestroy
	public void destory() {
		log.trace("destory");
	}

	public String getName() {
		log.trace("get name:", name);
		return name;
	}

	public void setName(String name) {
		log.trace("set naem:", name);
		this.name = name;
	}

	public String getMail() {
		log.trace("get mail:", mail);
		return mail;
	}

	public void setMail(String mail) {
		log.trace("set mail:", mail);
		this.mail = mail;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getPassword() {
		log.trace("get password:", password);
		return password;
	}

	public void setPassword(String password) {
		log.trace("set password:", password);
		this.password = password;
	}


	public String getAddress() {
		log.trace("get address:", address);
		return address;
	}

	public void setAddress(String address) {
		log.trace("set address:", address);
		this.address = address;
	}

	public String getCommunityName() {
		return communityName;
	}

	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}

	public boolean isNameDuplicate() {log.trace("get nameDuplicate:{}",nameDuplicate);
		return nameDuplicate;
	}

	public void setNameDuplicate(boolean nameDuplicate) {log.trace("set nameDuplicate:{}",nameDuplicate);
		this.nameDuplicate = nameDuplicate;
	}
}
