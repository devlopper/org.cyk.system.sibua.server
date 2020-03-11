package org.cyk.system.sibua.server.persistence.impl;
import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.persistence.api.AdministrativeUnitFunctionTypePersistence;
import org.cyk.system.sibua.server.persistence.entities.AdministrativeUnitFunctionType;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.persistence.query.QueryContext;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.server.persistence.AbstractPersistenceEntityImpl;
import org.cyk.utility.server.persistence.PersistenceFunctionReader;

@ApplicationScoped
public class AdministrativeUnitFunctionTypePersistenceImpl extends AbstractPersistenceEntityImpl<AdministrativeUnitFunctionType> implements AdministrativeUnitFunctionTypePersistence,Serializable {
	private static final long serialVersionUID = 1L;

	private String readByAdministrativeUnitsCodes;
	
	@Override
	protected void __listenPostConstructPersistenceQueries__() {
		super.__listenPostConstructPersistenceQueries__();
		addQueryCollectInstances(readByAdministrativeUnitsCodes, "SELECT administrativeUnitFunctionType FROM AdministrativeUnitFunctionType administrativeUnitFunctionType WHERE administrativeUnitFunctionType.administrativeUnit.code IN :administrativeUnitsCodes");
	}
	
	@Override
	protected String __getQueryIdentifier__(Class<?> klass, Properties properties, Object... objects) {
		if(PersistenceFunctionReader.class.equals(klass)) {
			if(Boolean.TRUE.equals(__isFilterByKeys__(properties, AdministrativeUnitFunctionType.FIELD_ADMINISTRATIVE_UNIT)))
				return readByAdministrativeUnitsCodes;
		}
		return super.__getQueryIdentifier__(klass, properties, objects);
	}
	
	@Override
	protected Object[] __getQueryParameters__(QueryContext queryContext, Properties properties,Object... objects) {
		if(queryContext.getQuery().isIdentifierEqualsToOrQueryDerivedFromQueryIdentifierEqualsTo(readByAdministrativeUnitsCodes)) {
			if(ArrayHelper.isEmpty(objects))
				objects = new Object[] {queryContext.getFilterByKeysValue(AdministrativeUnitFunctionType.FIELD_ADMINISTRATIVE_UNIT)};
			return new Object[]{"administrativeUnitsCodes",objects[0]};
		}
		return super.__getQueryParameters__(queryContext, properties, objects);
	}
}