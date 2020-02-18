package org.cyk.system.sibua.server.persistence.impl.user;
import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.persistence.api.query.ReadUserProgramByUsers;
import org.cyk.system.sibua.server.persistence.api.user.UserProgramPersistence;
import org.cyk.system.sibua.server.persistence.entities.user.UserFile;
import org.cyk.system.sibua.server.persistence.entities.user.UserProgram;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.persistence.query.QueryContext;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.server.persistence.AbstractPersistenceEntityImpl;
import org.cyk.utility.server.persistence.PersistenceFunctionReader;

@ApplicationScoped
public class UserProgramPersistenceImpl extends AbstractPersistenceEntityImpl<UserProgram> implements UserProgramPersistence,ReadUserProgramByUsers,Serializable {
	private static final long serialVersionUID = 1L;

	private String readByUsersIdentifiers;
	
	@Override
	protected void __listenPostConstructPersistenceQueries__() {
		super.__listenPostConstructPersistenceQueries__();
		addQueryCollectInstances(readByUsersIdentifiers, "SELECT userProgram FROM UserProgram userProgram WHERE userProgram.user.identifier IN :usersIdentifiers");
	}
	
	@Override
	public Collection<UserProgram> readByUsersIdentifiers(Collection<String> identifiers, Properties properties) {
		if(CollectionHelper.isEmpty(identifiers))
			return null;
		if(properties == null)
			properties = new Properties();
		properties.setIfNull(Properties.QUERY_IDENTIFIER, readByUsersIdentifiers);
		return __readMany__(properties, ____getQueryParameters____(properties,identifiers));
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
		return super.__getQueryParameters__(queryContext, properties, objects);
	}
}