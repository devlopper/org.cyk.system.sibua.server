package org.cyk.system.sibua.server.business.api.user;

import java.io.ByteArrayOutputStream;
import java.util.Collection;

import org.cyk.system.sibua.server.business.entities.IdentificationSheet;
import org.cyk.system.sibua.server.persistence.api.user.UserPersistence;
import org.cyk.system.sibua.server.persistence.entities.user.User;
import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.server.business.BusinessEntity;

public interface UserBusiness extends BusinessEntity<User> {

	void notifyAccessToken(Collection<User> users);
	
	default void notifyAccessToken(User...users) {
		if(ArrayHelper.isEmpty(users))
			return;
		notifyAccessToken(CollectionHelper.listOf(users));
	}
	
	default void notifyAccessTokenByIdentifiers(Collection<String> usersIdentifiers) {
		if(CollectionHelper.isEmpty(usersIdentifiers))
			return;
		Collection<User> users = DependencyInjection.inject(UserPersistence.class).readBySystemIdentifiers(CollectionHelper.cast(Object.class,usersIdentifiers));
		if(CollectionHelper.isEmpty(users))
			return;
		notifyAccessToken(users);
	}
	
	default void notifyAccessTokenByIdentifiers(String...usersIdentifiers) {
		if(ArrayHelper.isEmpty(usersIdentifiers))
			return;
		notifyAccessTokenByIdentifiers(CollectionHelper.listOf(usersIdentifiers));
	}
	
	default void notifyAccessTokenByElectronicMailAddresses(Collection<String> electronicMailAddresses) {
		if(CollectionHelper.isEmpty(electronicMailAddresses))
			return;
		Collection<User> users = DependencyInjection.inject(UserPersistence.class).readByElectronicMailAddresses(electronicMailAddresses, null);
		if(CollectionHelper.isEmpty(users))
			return;
		notifyAccessToken(users);
	}
	
	default void notifyAccessTokenByElectronicMailAddresses(String...electronicMailAddresses) {
		if(ArrayHelper.isEmpty(electronicMailAddresses))
			return;
		notifyAccessTokenByElectronicMailAddresses(CollectionHelper.listOf(electronicMailAddresses));
	}
	
	Collection<IdentificationSheet> buildIdentificationSheets(Collection<User> users);
	
	default Collection<IdentificationSheet> buildIdentificationSheets(User...users) {
		if(ArrayHelper.isEmpty(users))
			return null;
		return buildIdentificationSheets(CollectionHelper.listOf(users));
	}

	ByteArrayOutputStream buildIdentificationSheetsReport(Collection<User> users);
	
	default ByteArrayOutputStream buildIdentificationSheetsReport(User...users) {
		if(ArrayHelper.isEmpty(users))
			return null;
		return buildIdentificationSheetsReport(CollectionHelper.listOf(users));
	}
	
	default ByteArrayOutputStream buildIdentificationSheetsReportByIdentifiers(Collection<String> identifiers) {
		if(CollectionHelper.isEmpty(identifiers))
			return null;
		Collection<User> users = findBySystemIdentifiers(CollectionHelper.cast(Object.class, identifiers));
		if(CollectionHelper.isEmpty(users))
			return null;
		return buildIdentificationSheetsReport(users);
	}
	
	default ByteArrayOutputStream buildIdentificationSheetsReportByIdentifiers(String...identifiers) {
		if(ArrayHelper.isEmpty(identifiers))
			return null;
		return buildIdentificationSheetsReportByIdentifiers(CollectionHelper.listOf(identifiers));
	}
}
