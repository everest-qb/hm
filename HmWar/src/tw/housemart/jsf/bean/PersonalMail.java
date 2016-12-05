package tw.housemart.jsf.bean;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tw.housemart.ejb.UserManager;
import tw.housemart.util.ChannelRead;

import com.sun.mail.imap.IMAPStore;

@Named
@SessionScoped
public class PersonalMail implements Serializable {
	private Logger log = LoggerFactory.getLogger(PersonalMail.class);
	@Inject
	private GlobalVars global;
	
	@EJB
	private UserManager uMgr;
	
	private Date cacheTime;
	private List<String[]> cacheMail;
	
	
	public List<String[]> findMail(String name, String passwd) {		
		
		List<String[]> returnValue = new ArrayList<String[]>();
		if(!uMgr.checkAccountCreated(name)) return returnValue;
		Calendar nowCal=Calendar.getInstance();
		Calendar cacheCal=Calendar.getInstance();
		if(cacheTime!=null){
			cacheCal.setTime(cacheTime);
			cacheCal.add(Calendar.MINUTE, 5);
		}
		if(cacheMail==null ||nowCal.after(cacheCal)){
			Map<String, String> pMap = global.getMailProperties();
			try {
				Properties props = new Properties();
				Session session = Session.getDefaultInstance(props, null);
				IMAPStore store = (IMAPStore) session.getStore("imap");
				store.connect(pMap.get("internal.mail.smtp.host"), name, passwd);
				Folder inbox = store.getFolder("INBOX");
				inbox.open(Folder.READ_ONLY);
				Message[] messages = inbox.getMessages();
				for (int i = 0; i < messages.length; i++) {
					Message m = messages[i];										
					InputStream input=m.getInputStream();
					byte[] read=ChannelRead.readToEnd(input);
					
					String[] tmp = { m.getSubject(), new String(read, "UTF-8") };
					returnValue.add(tmp);
				}
			} catch (Exception e) {
				log.warn("Get mail fail! {}", e);
			}
			cacheTime=Calendar.getInstance().getTime();
			cacheMail = returnValue;			
		}else{
			return cacheMail;
		}
		log.trace("cache time {}",cacheTime);
		return returnValue;
	}
		
	
	@PostConstruct
	public void init(){
		
	}
	
	@PreDestroy
	public void destorhy(){
		
	}
}
