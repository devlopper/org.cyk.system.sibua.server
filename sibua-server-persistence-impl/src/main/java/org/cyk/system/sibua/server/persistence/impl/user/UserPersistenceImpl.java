package org.cyk.system.sibua.server.persistence.impl.user;
import java.io.Serializable;
import java.lang.reflect.Field;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.persistence.api.ActivityPersistence;
import org.cyk.system.sibua.server.persistence.api.AdministrativeUnitPersistence;
import org.cyk.system.sibua.server.persistence.api.LocalisationPersistence;
import org.cyk.system.sibua.server.persistence.api.SectionPersistence;
import org.cyk.system.sibua.server.persistence.api.query.ReadActivityByUsers;
import org.cyk.system.sibua.server.persistence.api.query.ReadAdministrativeUnitByUsers;
import org.cyk.system.sibua.server.persistence.api.query.ReadFileByUsers;
import org.cyk.system.sibua.server.persistence.api.query.ReadFunctionByUsers;
import org.cyk.system.sibua.server.persistence.api.query.ReadLocalisationByUsers;
import org.cyk.system.sibua.server.persistence.api.query.ReadSectionByUsers;
import org.cyk.system.sibua.server.persistence.api.query.ReadUserFileByUsers;
import org.cyk.system.sibua.server.persistence.api.user.FilePersistence;
import org.cyk.system.sibua.server.persistence.api.user.FunctionPersistence;
import org.cyk.system.sibua.server.persistence.api.user.UserFilePersistence;
import org.cyk.system.sibua.server.persistence.api.user.UserPersistence;
import org.cyk.system.sibua.server.persistence.entities.user.User;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.persistence.query.QueryContext;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.string.Strings;
import org.cyk.utility.server.persistence.AbstractPersistenceEntityImpl;
import org.cyk.utility.server.persistence.PersistenceFunctionReader;

@ApplicationScoped
public class UserPersistenceImpl extends AbstractPersistenceEntityImpl<User> implements UserPersistence,Serializable {
	private static final long serialVersionUID = 1L;

	private String readByAccessTokens,readByElectronicMailAddresses;
	
	@Override
	protected void __listenPostConstructPersistenceQueries__() {
		super.__listenPostConstructPersistenceQueries__();
		addQueryCollectInstances(readByAccessTokens, "SELECT user FROM User user WHERE user.accessToken IN :accessTokens");
		addQueryCollectInstances(readByElectronicMailAddresses, "SELECT user FROM User user WHERE user.electronicMailAddress IN :electronicMailAddresses");
	}
	
	@Override
	protected void __listenExecuteReadAfterSetFieldsValues__(User user, Strings fieldsNames, Properties properties) {
		super.__listenExecuteReadAfterSetFieldsValues__(user, fieldsNames, properties);
		if(CollectionHelper.isEmpty(fieldsNames)) {
			user.setUserFiles(((ReadUserFileByUsers)__inject__(UserFilePersistence.class)).readByUsers(user));
		}
	}
	
	@Override
	protected void __listenExecuteReadAfterSetFieldValue__(User user, Field field, Properties properties) {
		super.__listenExecuteReadAfterSetFieldValue__(user, field, properties);
		if(field.getName().equals(User.FIELD_LOCALISATIONS))
			user.setLocalisations(((ReadLocalisationByUsers)__inject__(LocalisationPersistence.class)).readByUsers(user));
		else if(field.getName().equals(User.FIELD_SECTIONS))
			user.setSections(((ReadSectionByUsers)__inject__(SectionPersistence.class)).readByUsers(user));
		else if(field.getName().equals(User.FIELD_ACTIVITIES))
			user.setActivities(((ReadActivityByUsers)__inject__(ActivityPersistence.class)).readByUsers(user));
		else if(field.getName().equals(User.FIELD_FUNCTIONS))
			user.setFunctions(((ReadFunctionByUsers)__inject__(FunctionPersistence.class)).readByUsers(user));
		else if(field.getName().equals(User.FIELD_ADMINISTRATIVE_UNITS))
			user.setAdministrativeUnits(((ReadAdministrativeUnitByUsers)__inject__(AdministrativeUnitPersistence.class)).readByUsers(user));
		else if(field.getName().equals(User.FIELD_FILES))
			user.setFiles(((ReadFileByUsers)__inject__(FilePersistence.class)).readByUsers(user));
		else if(field.getName().equals(User.FIELD_USER_FILES))
			user.setUserFiles(((ReadUserFileByUsers)__inject__(UserFilePersistence.class)).readByUsers(user));
	}

	@Override
	protected String __getQueryIdentifier__(Class<?> klass, Properties properties, Object... objects) {
		if(PersistenceFunctionReader.class.equals(klass)) {
			if(Boolean.TRUE.equals(__isFilterByKeys__(properties, User.FIELD_ACCESS_TOKEN)))
				return readByAccessTokens;
			if(Boolean.TRUE.equals(__isFilterByKeys__(properties, User.FIELD_ELECTRONIC_MAIL_ADDRESS)))
				return readByElectronicMailAddresses;
		}
		return super.__getQueryIdentifier__(klass, properties, objects);
	}

	@Override
	protected Object[] __getQueryParameters__(QueryContext queryContext, Properties properties,Object... objects) {
		if(queryContext.getQuery().isIdentifierEqualsToOrQueryDerivedFromQueryIdentifierEqualsTo(readByAccessTokens)) {
			if(ArrayHelper.isEmpty(objects))
				objects = new Object[] {queryContext.getFilterByKeysValue(User.FIELD_ACCESS_TOKEN)};
			return new Object[]{"accessTokens",objects[0]};
		}
		if(queryContext.getQuery().isIdentifierEqualsToOrQueryDerivedFromQueryIdentifierEqualsTo(readByElectronicMailAddresses)) {
			if(ArrayHelper.isEmpty(objects))
				objects = new Object[] {queryContext.getFilterByKeysValue(User.FIELD_ELECTRONIC_MAIL_ADDRESS)};
			return new Object[]{"electronicMailAddresses",objects[0]};
		}
		return super.__getQueryParameters__(queryContext, properties, objects);
	}
}