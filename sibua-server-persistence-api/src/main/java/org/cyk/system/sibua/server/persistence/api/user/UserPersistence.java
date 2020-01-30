package org.cyk.system.sibua.server.persistence.api.user;

import java.util.Collection;

import org.cyk.system.sibua.server.persistence.entities.user.User;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.server.persistence.PersistenceEntity;

public interface UserPersistence extends PersistenceEntity<User> {

	Collection<User> readByElectronicMailAddresses(Collection<String> electronicMailAddresses,Properties properties);
	
	default Collection<User> readByElectronicMailAddresses(Collection<String> electronicMailAddresses) {
		if(CollectionHelper.isEmpty(electronicMailAddresses))
			return null;
		return readByElectronicMailAddresses(electronicMailAddresses, null);
	}
	
	default Collection<User> readByElectronicMailAddresses(Properties properties,String...electronicMailAddresses) {
		if(ArrayHelper.isEmpty(electronicMailAddresses))
			return null;
		return readByElectronicMailAddresses(CollectionHelper.listOf(electronicMailAddresses), properties);
	}
	
	default Collection<User> readByElectronicMailAddresses(String...electronicMailAddresses) {
		if(ArrayHelper.isEmpty(electronicMailAddresses))
			return null;
		return readByElectronicMailAddresses(CollectionHelper.listOf(electronicMailAddresses), null);
	}
}
