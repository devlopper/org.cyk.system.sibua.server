package org.cyk.system.sibua.server.business.impl.user;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.business.api.user.FileBusiness;
import org.cyk.system.sibua.server.business.api.user.UserFileBusiness;
import org.cyk.system.sibua.server.persistence.api.user.UserFilePersistence;
import org.cyk.system.sibua.server.persistence.entities.user.UserFile;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.server.business.AbstractBusinessEntityImpl;
import org.cyk.utility.server.business.BusinessFunctionCreator;

@ApplicationScoped
public class UserFileBusinessImpl extends AbstractBusinessEntityImpl<UserFile, UserFilePersistence> implements UserFileBusiness,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenExecuteCreateBefore__(UserFile userFile, Properties properties,BusinessFunctionCreator function) {
		super.__listenExecuteCreateBefore__(userFile, properties, function);
		if(StringHelper.isBlank(userFile.getFile().getIdentifier()))
			__inject__(FileBusiness.class).create(userFile.getFile());
	}

}
