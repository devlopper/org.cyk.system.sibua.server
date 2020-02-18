package org.cyk.system.sibua.server.persistence.api.user;

import org.cyk.system.sibua.server.persistence.entities.user.UserProgram;
import org.cyk.utility.__kernel__.persistence.query.QueryIdentifierBuilder;
import org.cyk.utility.server.persistence.PersistenceEntity;

public interface UserProgramPersistence extends PersistenceEntity<UserProgram> {

	String READ_BY_USERS_IDENTIFIERS = QueryIdentifierBuilder.getInstance().build(UserProgram.class, "readByUsersIdentifiers");
	
}