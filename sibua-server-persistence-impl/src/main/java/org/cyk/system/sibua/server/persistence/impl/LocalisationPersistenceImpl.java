package org.cyk.system.sibua.server.persistence.impl;
import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.persistence.api.LocalisationPersistence;
import org.cyk.system.sibua.server.persistence.api.query.ReadLocalisationByUsers;
import org.cyk.system.sibua.server.persistence.entities.Localisation;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.persistence.query.QueryContext;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.server.persistence.AbstractPersistenceEntityImpl;

@ApplicationScoped
public class LocalisationPersistenceImpl extends AbstractPersistenceEntityImpl<Localisation> implements LocalisationPersistence,ReadLocalisationByUsers,Serializable {
	private static final long serialVersionUID = 1L;
	
	private String readByUsersIdentifiers;
	
	@Override
	protected void __listenPostConstructPersistenceQueries__() {
		super.__listenPostConstructPersistenceQueries__();
		//if(ApplicationScopeLifeCycleListener.isUserEnabled())
		//	addQueryCollectInstances(readByUsersIdentifiers, "SELECT localisation FROM Localisation localisation WHERE EXISTS (SELECT userLocalisation FROM UserLocalisation userLocalisation WHERE userLocalisation.localisation = localisation AND userLocalisation.user.identifier IN :usersIdentifiers)  ORDER BY localisation.code ASC");
	}
	
	@Override
	public Collection<Localisation> readByUsersIdentifiers(Collection<String> identifiers, Properties properties) {
		if(CollectionHelper.isEmpty(identifiers))
			return null;
		if(properties == null)
			properties = new Properties();
		properties.setIfNull(Properties.QUERY_IDENTIFIER, readByUsersIdentifiers);
		return __readMany__(properties, ____getQueryParameters____(properties,identifiers));
	}
	
	@Override
	protected Object[] __getQueryParameters__(QueryContext queryContext, Properties properties, Object... objects) {
		if(queryContext.getQuery().isIdentifierEqualsToOrQueryDerivedFromQueryIdentifierEqualsTo(readByUsersIdentifiers)) {
			return new Object[]{"usersIdentifiers",objects[0]};
		}
		return super.__getQueryParameters__(queryContext, properties, objects);
	}
	
}