package org.cyk.system.sibua.server.business.api.user;

import java.io.ByteArrayOutputStream;
import java.util.Collection;

import org.cyk.system.sibua.server.business.entities.IdentificationSheet;
import org.cyk.system.sibua.server.persistence.entities.user.User;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.server.business.BusinessEntity;

public interface UserBusiness extends BusinessEntity<User> {

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
}
