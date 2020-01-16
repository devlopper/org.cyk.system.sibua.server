package org.cyk.system.sibua.server.persistence.api.user;

import org.cyk.system.sibua.server.persistence.entities.user.File;
import org.cyk.utility.server.persistence.PersistenceEntity;

public interface FilePersistence extends PersistenceEntity<File> {

	File readBySha1(String sha1);
	
}