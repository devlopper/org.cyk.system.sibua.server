package org.cyk.system.sibua.server.persistence.api.user;

import org.cyk.system.sibua.server.persistence.entities.user.UserFile;
import org.cyk.system.sibua.server.persistence.entities.user.UserFileType;
import org.cyk.utility.server.persistence.PersistenceEntity;

public interface UserFilePersistence extends PersistenceEntity<UserFile> {

	UserFile readByUserIdentifierByFileIdentifierByType(String userIdentifier,String fileIdentifier,UserFileType type);
	
}
