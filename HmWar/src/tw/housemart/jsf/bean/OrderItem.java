package tw.housemart.jsf.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class OrderItem implements Serializable{
	
	
	
	
	@PostConstruct
	public void init(){
		
	}
	
	@PreDestroy
	public void destorhy(){
		
	}

}
