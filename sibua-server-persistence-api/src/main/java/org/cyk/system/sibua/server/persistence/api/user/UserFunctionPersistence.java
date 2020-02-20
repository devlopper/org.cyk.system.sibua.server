package org.cyk.system.sibua.server.persistence.api.user;

import org.cyk.system.sibua.server.persistence.entities.user.UserFunction;
import org.cyk.utility.__kernel__.persistence.query.QueryIdentifierBuilder;
import org.cyk.utility.server.persistence.PersistenceEntity;

public interface UserFunctionPersistence extends PersistenceEntity<UserFunction> {

	String READ_BY_USERS_IDENTIFIERS = QueryIdentifierBuilder.getInstance().build(UserFunction.class, "readByUsersIdentifiers");
	
}