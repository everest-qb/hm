package tw.housemart.exception;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

public class JsfExceptionFactory extends ExceptionHandlerFactory {

	private ExceptionHandlerFactory parent;
	
	public JsfExceptionFactory(ExceptionHandlerFactory parent){
		this.parent=parent;
	}
	
	public ExceptionHandler getExceptionHandler() {
		
		return new JsfExceptionHandler(parent.getExceptionHandler());
	}

}
