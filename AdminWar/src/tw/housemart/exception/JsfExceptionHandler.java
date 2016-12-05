package tw.housemart.exception;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import javax.ejb.EJBException;
import javax.el.ELException;
import javax.faces.FacesException;
import javax.faces.application.NavigationHandler;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;

import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsfExceptionHandler extends ExceptionHandlerWrapper {
	private Logger log = LoggerFactory.getLogger(JsfExceptionHandler.class);
	private ExceptionHandler wrapped;

	public JsfExceptionHandler(ExceptionHandler wrapped) {
		this.wrapped = wrapped;
	}

	public ExceptionHandler getWrapped() {
		return wrapped;
	}

	@Override
	public void handle() throws FacesException {
		Iterator<ExceptionQueuedEvent> itor = getUnhandledExceptionQueuedEvents()
				.iterator();
		while (itor.hasNext()) {
			ExceptionQueuedEvent event = itor.next();
			ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event
					.getSource();
			Throwable t = context.getException();
			while ((t instanceof FacesException || t instanceof EJBException || t instanceof ELException)
					&& t.getCause() != null) {
				t = t.getCause();
			}
			log.info("HANDLE: {}", t.getMessage());
			if (t instanceof AuthorizationException) {
				FacesContext fc = FacesContext.getCurrentInstance();
				ExternalContext externalContext = fc.getExternalContext();
				Map<String, Object> requestMap = externalContext.getRequestMap();
				//NavigationHandler nav = fc.getApplication().getNavigationHandler();
				try {
					requestMap.put("exceptionMessage", t.getMessage());
					//nav.handleNavigation(fc, null, "/error");
					//fc.renderResponse();
					try {
						externalContext.dispatch("/error.jsp");
					} catch (IOException e) {						
						log.error("error page not found!");
					}
					fc.responseComplete();
				} finally {
					itor.remove();
				}
			}

		}

		getWrapped().handle();
	}

}
