package org.cyk.system.sibua.server.persistence.api.query;

import java.util.Collection;
import java.util.stream.Collectors;

import org.cyk.system.sibua.server.persistence.entities.user.User;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.properties.Properties;

public interface ReadByUsers<ENTITY> {

	Collection<ENTITY> readByUsersIdentifiers(Collection<String> identifiers,Properties properties);
	
	default Collection<ENTITY> readByUsersIdentifiers(Collection<String> identifiers) {
		if(CollectionHelper.isEmpty(identifiers))
			return null;
		return readByUsersIdentifiers(identifiers,null);
	}
	
	default Collection<ENTITY> readByUsersIdentifiers(Properties properties,String...identifiers) {
		if(ArrayHelper.isEmpty(identifiers))
			return null;
		return readByUsersIdentifiers(CollectionHelper.listOf(identifiers),properties);
	}
	
	default Collection<ENTITY> readByUsersIdentifiers(String...identifiers) {
		if(ArrayHelper.isEmpty(identifiers))
			return null;
		return readByUsersIdentifiers(CollectionHelper.listOf(identifiers),null);
	}
	
	default Collection<ENTITY> readByUsers(Collection<User> users,Properties properties) {
		if(CollectionHelper.isEmpty(users))
			return null;
		return readByUsersIdentifiers(users.stream().map(User::getIdentifier).collect(Collectors.toList()), properties);
	}
	
	default Collection<ENTITY> readByUsers(Collection<User> users) {
		if(CollectionHelper.isEmpty(users))
			return null;
		return readByUsers(users,null);
	}
	
	default Collection<ENTITY> readByUsers(Properties properties,User...users) {
		if(ArrayHelper.isEmpty(users))
			return null;
		return readByUsers(CollectionHelper.listOf(users),properties);
	}
	
	default Collection<ENTITY> readByUsers(User...users) {
		if(ArrayHelper.isEmpty(users))
			return null;
		return readByUsers(CollectionHelper.listOf(users),null);
	}
}
