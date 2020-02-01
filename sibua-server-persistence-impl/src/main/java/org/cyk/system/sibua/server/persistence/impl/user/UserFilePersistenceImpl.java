package org.cyk.system.sibua.server.persistence.impl.user;
import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.persistence.api.query.ReadUserFileByUsers;
import org.cyk.system.sibua.server.persistence.api.user.UserFilePersistence;
import org.cyk.system.sibua.server.persistence.entities.user.UserFile;
import org.cyk.system.sibua.server.persistence.entities.user.UserFileType;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.persistence.query.QueryContext;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.server.persistence.AbstractPersistenceEntityImpl;
import org.cyk.utility.server.persistence.PersistenceFunctionReader;

@ApplicationScoped
public class UserFilePersistenceImpl extends AbstractPersistenceEntityImpl<UserFile> implements UserFilePersistence,ReadUserFileByUsers,Serializable {
	private static final long serialVersionUID = 1L;

	private String readByUsersIdentifiers,readByUserIdentifierByFileIdentifierByType;
	
	@Override
	protected void __listenPostConstructPersistenceQueries__() {
		super.__listenPostConstructPersistenceQueries__();
		addQueryCollectInstances(readByUsersIdentifiers, "SELECT userFile FROM UserFile userFile WHERE userFile.user.identifier IN :usersIdentifiers");
		addQueryCollectInstances(readByUserIdentifierByFileIdentifierByType, "SELECT userFile FROM UserFile userFile WHERE "
				+ "userFile.user.identifier = :userIdentifier AND userFile.file.identifier = :fileIdentifier AND userFile.type = :type");
	}
	
	@Override
	public Collection<UserFile> readByUsersIdentifiers(Collection<String> identifiers, Properties properties) {
		if(CollectionHelper.isEmpty(identifiers))
			return null;
		if(properties == null)
			properties = new Properties();
		properties.setIfNull(Properties.QUERY_IDENTIFIER, readByUsersIdentifiers);
		return __readMany__(properties, ____getQueryParameters____(properties,identifiers));
	}
	
	@Override
	public UserFile readByUserIdentifierByFileIdentifierByType(String userIdentifier, String fileIdentifier,UserFileType type) {
		if(StringHelper.isBlank(userIdentifier) || StringHelper.isBlank(fileIdentifier) || type == null)
			return null;
		if(properties == null)
			properties = new Properties();
		properties.setIfNull(Properties.QUERY_IDENTIFIER, readByUserIdentifierByFileIdentifierByType);
		return __readOne__(properties, ____getQueryParameters____(properties,userIdentifier,fileIdentifier,type));
	}
	
	@Override
	protected String __getQueryIdentifier__(Class<?> klass, Properties properties, Object... objects) {
		if(PersistenceFunctionReader.class.equals(klass)) {
			if(Boolean.TRUE.equals(__isFilterByKeys__(properties, UserFile.FIELD_USER)))
				return readByUsersIdentifiers;
		}
		return super.__getQueryIdentifier__(klass, properties, objects);
	}
	
	@Override
	protected Object[] __getQueryParameters__(QueryContext queryContext, Properties properties, Object... objects) {
		if(queryContext.getQuery().isIdentifierEqualsToOrQueryDerivedFromQueryIdentifierEqualsTo(readByUsersIdentifiers)) {
			if(ArrayHelper.isEmpty(objects))
				objects = new Object[] {queryContext.getFilterByKeysValue(UserFile.FIELD_USER)};
			return new Object[]{"usersIdentifiers",objects[0]};
		}
		if(queryContext.getQuery().isIdentifierEqualsToOrQueryDerivedFromQueryIdentifierEqualsTo(readByUserIdentifierByFileIdentifierByType)) {
			return new Object[]{"userIdentifier",objects[0],"fileIdentifier",objects[1],"type",objects[2]};
		}
		return super.__getQueryParameters__(queryContext, properties, objects);
	}
	
}