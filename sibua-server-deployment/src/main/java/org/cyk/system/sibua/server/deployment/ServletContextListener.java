package org.cyk.system.sibua.server.deployment;

import java.io.Serializable;

import javax.annotation.Resource;
import javax.mail.Session;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;

import org.cyk.system.sibua.server.representation.impl.ApplicationScopeLifeCycleListener;
import org.cyk.utility.__kernel__.variable.VariableHelper;
import org.cyk.utility.__kernel__.variable.VariableName;
import org.cyk.utility.server.deployment.AbstractServletContextListener;

@WebListener
public class ServletContextListener extends AbstractServletContextListener implements Serializable {
	private static final long serialVersionUID = 1L;

	@Resource(name = "java:jboss/mail/gmail")
    private Session session;
	
	@Override
	public void __initialize__(ServletContext context) {
		/*
		MailSenderImpl.SESSION = session;
		System.out.println("ServletContextListener.__initialize__() MAIL SESSION 0 : "+session.getProperties());
		//session.getProperties().put("mail.smtp.host", 587);
		session.getProperties().put("mail.smtp.password", "P@sSw0rd@2O18");
		session.getProperties().put("mail.smtp.port", 587);
		session.getProperties().put("mail.smtp.starttls.enable", Boolean.TRUE);
		session.getProperties().put("mail.smtp.ssl.enable", Boolean.TRUE);
		System.out.println("ServletContextListener.__initialize__() MAIL SESSION 1 : "+session.getProperties());
		*/
		VariableHelper.write(VariableName.PROTOCOL_SIMPLE_MAIL_TRANSFER_ENABLE, Boolean.TRUE);
		VariableHelper.write(VariableName.PROTOCOL_SIMPLE_MAIL_TRANSFER_HOST, "smtp.gmail.com");
		VariableHelper.write(VariableName.PROTOCOL_SIMPLE_MAIL_TRANSFER_PORT, 587);
		VariableHelper.write(VariableName.PROTOCOL_SIMPLE_MAIL_TRANSFER_AUTHENTICATION_REQUIRED, Boolean.TRUE);
		VariableHelper.write(VariableName.PROTOCOL_SIMPLE_MAIL_TRANSFER_SECURED_CONNECTION_REQUIRED, Boolean.TRUE);
		VariableHelper.write(VariableName.PROTOCOL_SIMPLE_MAIL_TRANSFER_AUTHENTICATION_CREDENTIALS_USER_IDENTIFIER, "kycdev@gmail.com");
		VariableHelper.write(VariableName.PROTOCOL_SIMPLE_MAIL_TRANSFER_AUTHENTICATION_CREDENTIALS_USER_SECRET, "P@sSw0rd@2O18");
		
		VariableHelper.write(VariableName.SYSTEM_LOGGING_THROWABLE_PRINT_STACK_TRACE, Boolean.TRUE);
		
		super.__initialize__(context);
		__inject__(ApplicationScopeLifeCycleListener.class).initialize(null);		
	}

}
