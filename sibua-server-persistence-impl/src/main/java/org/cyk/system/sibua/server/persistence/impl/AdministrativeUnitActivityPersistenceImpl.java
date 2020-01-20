package org.cyk.system.sibua.server.persistence.impl;
import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.persistence.api.AdministrativeUnitActivityPersistence;
import org.cyk.system.sibua.server.persistence.api.query.ReadAdministrativeUnitActivityByActivities;
import org.cyk.system.sibua.server.persistence.api.query.ReadAdministrativeUnitActivityByAdministrativeUnits;
import org.cyk.system.sibua.server.persistence.entities.AdministrativeUnitActivity;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.persistence.query.QueryContext;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.server.persistence.AbstractPersistenceEntityImpl;
import org.cyk.utility.server.persistence.PersistenceFunctionReader;

@ApplicationScoped
public class AdministrativeUnitActivityPersistenceImpl extends AbstractPersistenceEntityImpl<AdministrativeUnitActivity> implements AdministrativeUnitActivityPersistence,ReadAdministrativeUnitActivityByAdministrativeUnits,ReadAdministrativeUnitActivityByActivities,Serializable {
	private static final long serialVersionUID = 1L;

	private String readByAdministrativeUnitsCodes,readByAdministrativeUnitBeneficiairesCodes,readByActivitiesCodes,readByAdministrativeUnitsCodesByActivitiesCodes,readWhereIsGestionnaireOrBeneficiaireByAdministrativeUnitsCodes;
	
	@Override
	protected void __listenPostConstructPersistenceQueries__() {
		super.__listenPostConstructPersistenceQueries__();
		addQueryCollectInstances(readByAdministrativeUnitsCodes, "SELECT administrativeUnitActivity FROM AdministrativeUnitActivity administrativeUnitActivity WHERE administrativeUnitActivity.administrativeUnit.code IN :administrativeUnitsCodes");
		addQueryCollectInstances(readByAdministrativeUnitBeneficiairesCodes, "SELECT administrativeUnitActivity FROM AdministrativeUnitActivity administrativeUnitActivity WHERE administrativeUnitActivity.administrativeUnitBeneficiaire.code IN :administrativeUnitsBeneficiairesCodes");
		addQueryCollectInstances(readByActivitiesCodes, "SELECT administrativeUnitActivity FROM AdministrativeUnitActivity administrativeUnitActivity WHERE administrativeUnitActivity.activity.code IN :activitiesCodes");
		addQueryCollectInstances(readByAdministrativeUnitsCodesByActivitiesCodes
				, "SELECT administrativeUnitActivity FROM AdministrativeUnitActivity administrativeUnitActivity "
						+ "WHERE administrativeUnitActivity.administrativeUnit.code IN :administrativeUnitsCodes AND administrativeUnitActivity.activity.code IN :activitiesCodes");
		addQueryCollectInstances(readWhereIsGestionnaireOrBeneficiaireByAdministrativeUnitsCodes, "SELECT administrativeUnitActivity FROM AdministrativeUnitActivity administrativeUnitActivity "
				+ "WHERE administrativeUnitActivity.administrativeUnit.code IN :administrativeUnitsCodes OR (administrativeUnitActivity.administrativeUnitBeneficiaire IS NOT NULL AND administrativeUnitActivity.administrativeUnitBeneficiaire.code IN :administrativeUnitsCodes)");
	}
	
	@Override
	public Collection<AdministrativeUnitActivity> readByAdministrativeUnitsCodes(Collection<String> codes, Properties properties) {
		if(CollectionHelper.isEmpty(codes))
			return null;
		if(properties == null)
			properties = new Properties();
		properties.setIfNull(Properties.QUERY_IDENTIFIER, readByAdministrativeUnitsCodes);
		return __readMany__(properties, ____getQueryParameters____(properties,codes));
	}
	
	@Override
	public Collection<AdministrativeUnitActivity> readByAdministrativeUnitBeneficiairesCodes(Collection<String> codes, Properties properties) {
		if(CollectionHelper.isEmpty(codes))
			return null;
		if(properties == null)
			properties = new Properties();
		properties.setIfNull(Properties.QUERY_IDENTIFIER, readByAdministrativeUnitBeneficiairesCodes);
		return __readMany__(properties, ____getQueryParameters____(properties,codes));
	}
	
	@Override
	public Collection<AdministrativeUnitActivity> readWhereIsGestionnaireOrBeneficiaireByAdministrativeUnitsCodes(Collection<String> administrativeUnitsCodes, Properties properties) {
		if(CollectionHelper.isEmpty(administrativeUnitsCodes))
			return null;
		if(properties == null)
			properties = new Properties();
		properties.setIfNull(Properties.QUERY_IDENTIFIER, readWhereIsGestionnaireOrBeneficiaireByAdministrativeUnitsCodes);
		return __readMany__(properties, ____getQueryParameters____(properties,administrativeUnitsCodes));
	}
	
	@Override
	public Collection<AdministrativeUnitActivity> readByActivitiesCodes(Collection<String> codes,Properties properties) {
		if(CollectionHelper.isEmpty(codes))
			return null;
		if(properties == null)
			properties = new Properties();
		properties.setIfNull(Properties.QUERY_IDENTIFIER, readByActivitiesCodes);
		return __readMany__(properties, ____getQueryParameters____(properties,codes));
	}
	
	@Override
	public Collection<AdministrativeUnitActivity> readByAdministrativeUnitsCodesByActivitiesCodes(Collection<String> administrativeUnitsCodes, Collection<String> activitiesCodes, Properties properties) {
		if(CollectionHelper.isEmpty(administrativeUnitsCodes) || CollectionHelper.isEmpty(activitiesCodes))
			return null;
		if(properties == null)
			properties = new Properties();
		properties.setIfNull(Properties.QUERY_IDENTIFIER, readByAdministrativeUnitsCodesByActivitiesCodes);
		return __readMany__(properties, ____getQueryParameters____(properties,administrativeUnitsCodes,activitiesCodes));
	}

	@Override
	protected String __getQueryIdentifier__(Class<?> klass, Properties properties, Object... objects) {
		if(PersistenceFunctionReader.class.equals(klass)) {
			if(Boolean.TRUE.equals(__isFilterByKeys__(properties, AdministrativeUnitActivity.FIELD_ADMINISTRATIVE_UNIT)))
				return readByAdministrativeUnitsCodes;
		}
		return super.__getQueryIdentifier__(klass, properties, objects);
	}
	
	@Override
	protected Object[] __getQueryParameters__(QueryContext queryContext, Properties properties,Object... objects) {
		if(queryContext.getQuery().isIdentifierEqualsToOrQueryDerivedFromQueryIdentifierEqualsTo(readByAdministrativeUnitsCodes)) {
			if(ArrayHelper.isEmpty(objects))
				objects = new Object[] {queryContext.getFilterByKeysValue(AdministrativeUnitActivity.FIELD_ADMINISTRATIVE_UNIT)};
			return new Object[]{"administrativeUnitsCodes",objects[0]};
		}
		if(queryContext.getQuery().isIdentifierEqualsToOrQueryDerivedFromQueryIdentifierEqualsTo(readByAdministrativeUnitBeneficiairesCodes)) {
			if(ArrayHelper.isEmpty(objects))
				objects = new Object[] {queryContext.getFilterByKeysValue(AdministrativeUnitActivity.FIELD_ADMINISTRATIVE_UNIT)};
			return new Object[]{"administrativeUnitsBeneficiairesCodes",objects[0]};
		}
		if(queryContext.getQuery().isIdentifierEqualsToOrQueryDerivedFromQueryIdentifierEqualsTo(readWhereIsGestionnaireOrBeneficiaireByAdministrativeUnitsCodes)) {
			if(ArrayHelper.isEmpty(objects))
				objects = new Object[] {queryContext.getFilterByKeysValue(AdministrativeUnitActivity.FIELD_ADMINISTRATIVE_UNIT)};
			System.out.println(queryContext.getQuery().getValue());
			System.out.println("AdministrativeUnitActivityPersistenceImpl.__getQueryParameters__() : "+objects[0]);
			return new Object[]{"administrativeUnitsCodes",objects[0]};
		}
		if(queryContext.getQuery().isIdentifierEqualsToOrQueryDerivedFromQueryIdentifierEqualsTo(readByActivitiesCodes)) {
			if(ArrayHelper.isEmpty(objects))
				objects = new Object[] {queryContext.getFilterByKeysValue(AdministrativeUnitActivity.FIELD_ACTIVITY)};
			return new Object[]{"activitiesCodes",objects[0]};
		}
		if(queryContext.getQuery().isIdentifierEqualsToOrQueryDerivedFromQueryIdentifierEqualsTo(readByAdministrativeUnitsCodesByActivitiesCodes)) {
			//if(ArrayHelper.isEmpty(objects))
			//	objects = new Object[] {queryContext.getFilterByKeysValue(AdministrativeUnitActivity.FIELD_ADMINISTRATIVE_UNIT)};
			return new Object[]{"administrativeUnitsCodes",objects[0],"activitiesCodes",objects[1]};
		}
		return super.__getQueryParameters__(queryContext, properties, objects);
	}
}