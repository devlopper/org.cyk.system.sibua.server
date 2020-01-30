package org.cyk.system.sibua.server.persistence.impl.user;
import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.persistence.api.query.ReadFunctionByAdministrativeUnits;
import org.cyk.system.sibua.server.persistence.api.query.ReadFunctionByUsers;
import org.cyk.system.sibua.server.persistence.api.user.FunctionPersistence;
import org.cyk.system.sibua.server.persistence.entities.user.Function;
import org.cyk.system.sibua.server.persistence.impl.ApplicationScopeLifeCycleListener;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.persistence.query.QueryContext;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.throwable.ThrowableHelper;
import org.cyk.utility.server.persistence.AbstractPersistenceEntityImpl;

@ApplicationScoped
public class FunctionPersistenceImpl extends AbstractPersistenceEntityImpl<Function> implements FunctionPersistence,ReadFunctionByUsers,ReadFunctionByAdministrativeUnits,Serializable {
	private static final long serialVersionUID = 1L;

	private String readByUsersIdentifiers,readByAdministrativeUnitsCodes;
	
	@Override
	protected void __listenPostConstructPersistenceQueries__() {
		super.__listenPostConstructPersistenceQueries__();
		if(ApplicationScopeLifeCycleListener.isUserEnabled())
			addQueryCollectInstances(readByUsersIdentifiers, "SELECT function FROM Function function WHERE EXISTS (SELECT userFunction FROM UserFunction userFunction WHERE userFunction.function = function AND userFunction.user.identifier IN :usersIdentifiers)  ORDER BY function.code ASC");
		addQueryCollectInstances(readByAdministrativeUnitsCodes, "SELECT function FROM Function function WHERE SUBSTRING(function.code,2,8) IN :administrativeUnitsCodes ORDER BY function.code ASC");
	}
	
	@Override
	public Collection<Function> readByUsersIdentifiers(Collection<String> identifiers, Properties properties) {
		if(CollectionHelper.isEmpty(identifiers))
			return null;
		if(properties == null)
			properties = new Properties();
		properties.setIfNull(Properties.QUERY_IDENTIFIER, readByUsersIdentifiers);
		return __readMany__(properties, ____getQueryParameters____(properties,identifiers));
	}
	
	@Override
	public Collection<Function> readByAdministrativeUnitsCodes(Collection<String> codes, Properties properties) {
		if(CollectionHelper.isEmpty(codes))
			return null;
		if(properties == null)
			properties = new Properties();
		properties.setIfNull(Properties.QUERY_IDENTIFIER, readByAdministrativeUnitsCodes);
		return __readMany__(properties, ____getQueryParameters____(properties,codes));
	}
	
	@Override
	public Long countByAdministrativeUnitsCodes(Collection<String> codes, Properties properties) {
		ThrowableHelper.throwNotYetImplemented("Function.countByAdministrativeUnitsCodes");
		return null;
	}
	
	@Override
	protected Object[] __getQueryParameters__(QueryContext queryContext, Properties properties, Object... objects) {
		if(queryContext.getQuery().isIdentifierEqualsToOrQueryDerivedFromQueryIdentifierEqualsTo(readByUsersIdentifiers)) {
			return new Object[]{"usersIdentifiers",objects[0]};
		}
		if(queryContext.getQuery().isIdentifierEqualsToOrQueryDerivedFromQueryIdentifierEqualsTo(readByAdministrativeUnitsCodes)) {
			if(ArrayHelper.isEmpty(objects)) {
				objects = new Object[] {queryContext.getFilterFieldByKeys(Function.FIELD_ADMINISTRATIVE_UNITS).getValue()};
			}
			return new Object[]{"administrativeUnitsCodes",objects[0]};
		}
		return super.__getQueryParameters__(queryContext, properties, objects);
	}
}