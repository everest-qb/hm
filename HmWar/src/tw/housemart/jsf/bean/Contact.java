package tw.housemart.jsf.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.mail.Authenticator;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.mail.imap.IMAPStore;

@Named
@RequestScoped
public class Contact implements Serializable {
	private Logger log = LoggerFactory.getLogger(Contact.class);
	
	@Inject
	private Login login;
	@Inject
	private GlobalVars global;
	@Inject
	private PersonalMail pMail;
	
	private String userName;
	
	private String contactInfo;
	
	private String clontent;
	
	private boolean sended;
	
	public List<String[]> findMail(){		
		 if(!login.checkLogin()) return new ArrayList<String[]>();		 
		 return pMail.findMail(login.getName(), login.getPassword());
	}
	
	public String toSendMail(){
		log.trace(userName);
		log.trace(clontent);
		/*try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/
		if(mailInternal()){
			sended=true;
			clontent="";
		}			
		return "sendMailReturn";
	}
	
	private boolean mailInternal(){
		boolean returnValue=false;
		Map<String,String> pMap=global.getMailProperties();
		Properties props = new Properties();
		props.put("mail.smtp.host", pMap.get("internal.mail.smtp.host")); 
		props.put("mail.smtp.auth", pMap.get("internal.mail.smtp.auth"));  
		props.put("mail.smtp.port", pMap.get("internal.mail.smtp.port"));
		Session session = Session.getInstance(props);
		   try {  
	        	String mail=pMap.get("internal.app.mail.address"); 
	        	MimeMessage message = new MimeMessage(session);  
	            message.setFrom(new InternetAddress(mail));  
	            message.setRecipients(Message.RecipientType.TO, InternetAddress  
	                    .parse(mail));  
	            message.setSubject("The are messages from C.H.M by "+userName);            
	            message.setContent(clontent+"\n\n\n"+contactInfo,"text/html ; charset=utf-8");  
	            Transport.send(message);   
	            returnValue=true;
	        } catch (Exception e) {  
	        	returnValue=false;
	        	log.warn("MAIL FAIL!", e);;
	        } 
		return returnValue;
	}
	
	/**
	 * @deprecated
	 */
	private boolean mail(){
		boolean returnValue=false;		
		Map<String,String> pMap=global.getMailProperties();
		final String username = pMap.get("app.mail.user");  
        final String password = pMap.get("app.mail.password");
		Properties props = new Properties();  
        props.put("mail.smtp.host", pMap.get("mail.smtp.host"));  
        props.put("mail.smtp.socketFactory.port", pMap.get("mail.smtp.socketFactory.port"));  
        props.put("mail.smtp.socketFactory.class",  
        		pMap.get("mail.smtp.socketFactory.class"));  
        props.put("mail.smtp.auth", pMap.get("mail.smtp.auth"));  
        props.put("mail.smtp.port", pMap.get("mail.smtp.port"));
        
        Session session = Session.getInstance(props, new Authenticator() {  
            protected PasswordAuthentication getPasswordAuthentication() {  
                return new PasswordAuthentication(username, password);  
            }  
        }); 
        
        
        try {  
        	String mail=pMap.get("app.mail.address"); 
        	MimeMessage message = new MimeMessage(session);  
            message.setFrom(new InternetAddress(mail));  
            message.setRecipients(Message.RecipientType.TO, InternetAddress  
                    .parse(mail));  
            message.setSubject("The are messages from C.H.M by "+userName);            
            message.setContent(clontent+"\n\n\n"+contactInfo,"text/html ; charset=utf-8");  
            Transport.send(message);   
            returnValue=true;
        } catch (Exception e) {  
        	returnValue=false;
        	log.warn("MAIL FAIL!", e);;
        } 
        
        return returnValue;
	}
	
	@PostConstruct
	public void init(){
		if(login.checkLogin()){
			userName=login.getName();
			contactInfo=login.getUser().getMail();
		}
		sended=false;
	}
	
	@PreDestroy
	public void destorhy(){
		
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getContactInfo() {
		return contactInfo;
	}

	public void setContactInfo(String contactInfo) {
		this.contactInfo = contactInfo;
	}

	public String getClontent() {
		return clontent;
	}

	public void setClontent(String clontent) {
		this.clontent = clontent;
	}

	public boolean isSended() {
		return sended;
	}

	public void setSended(boolean sended) {
		this.sended = sended;
	}
}
