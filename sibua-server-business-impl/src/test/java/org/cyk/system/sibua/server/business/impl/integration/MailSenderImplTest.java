package org.cyk.system.sibua.server.business.impl.integration;

import java.io.Serializable;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.protocol.smtp.AbstractMailSenderImpl;
import org.cyk.utility.__kernel__.protocol.smtp.Message;
import org.cyk.utility.__kernel__.protocol.smtp.Properties;

import com.icegreen.greenmail.util.GreenMailUtil;

public class MailSenderImplTest extends AbstractMailSenderImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __send__(Message message,Properties protocolProperties, Listener listener) throws Exception{
		GreenMailUtil.sendTextEmailTest(CollectionHelper.getFirst(message.getReceivers()).toString(), protocolProperties.getAuthenticationCredentials().getIdentifier()
				, message.getSubject(), message.getBody());
	}

}
