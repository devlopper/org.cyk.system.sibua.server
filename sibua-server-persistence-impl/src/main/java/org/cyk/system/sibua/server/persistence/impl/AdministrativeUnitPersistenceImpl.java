package org.cyk.system.sibua.server.persistence.impl;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collection;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.sibua.server.persistence.api.ActivityPersistence;
import org.cyk.system.sibua.server.persistence.api.AdministrativeUnitHierarchyPersistence;
import org.cyk.system.sibua.server.persistence.api.AdministrativeUnitPersistence;
import org.cyk.system.sibua.server.persistence.api.DestinationPersistence;
import org.cyk.system.sibua.server.persistence.api.query.ReadActivityByAdministrativeUnits;
import org.cyk.system.sibua.server.persistence.api.query.ReadAdministrativeUnitByPrograms;
import org.cyk.system.sibua.server.persistence.api.query.ReadAdministrativeUnitBySections;
import org.cyk.system.sibua.server.persistence.api.query.ReadDestinationByAdministrativeUnits;
import org.cyk.system.sibua.server.persistence.entities.AdministrativeUnit;
import org.cyk.system.sibua.server.persistence.entities.AdministrativeUnitHierarchy;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.server.persistence.AbstractPersistenceEntityImpl;
import org.cyk.utility.server.persistence.PersistenceFunctionReader;
import org.cyk.utility.server.persistence.query.PersistenceQueryContext;

@ApplicationScoped
public class AdministrativeUnitPersistenceImpl extends AbstractPersistenceEntityImpl<AdministrativeUnit> implements AdministrativeUnitPersistence,ReadAdministrativeUnitBySections,ReadAdministrativeUnitByPrograms,Serializable {
	private static final long serialVersionUID = 1L;

	private String readBySectionsCodes,readByProgramsCodes,readMaxOrderNumberByServiceGroupCodeByFunctionalClassificationCode,readByFilters;
	
	@Override
	protected void __listenPostConstructPersistenceQueries__() {
		super.__listenPostConstructPersistenceQueries__();
		addQueryCollectInstances(readBySectionsCodes, "SELECT administrativeUnit FROM AdministrativeUnit administrativeUnit WHERE administrativeUnit.section.code IN :sectionsCodes ORDER BY administrativeUnit.code ASC");
		addQueryCollectInstances(readByProgramsCodes, "SELECT administrativeUnit FROM AdministrativeUnit administrativeUnit "
				+ "WHERE EXISTS(SELECT activity FROM Activity activity WHERE activity.action.program.code IN :programsCodes AND EXISTS"
				+ "(SELECT administrativeUnitActivity FROM AdministrativeUnitActivity administrativeUnitActivity "
				+ "WHERE administrativeUnitActivity.administrativeUnit = administrativeUnit AND administrativeUnitActivity.activity = activity)) "
				+ "ORDER BY administrativeUnit.code ASC");
		addQuery(readMaxOrderNumberByServiceGroupCodeByFunctionalClassificationCode, "SELECT MAX(administrativeUnit.orderNumber) FROM AdministrativeUnit administrativeUnit "
				+ "WHERE administrativeUnit.serviceGroup.code = :serviceGroupCode AND administrativeUnit.functionalClassification.code = :functionalClassificationCode",Integer.class);
		addQueryCollectInstances(readByFilters, "SELECT administrativeUnit FROM AdministrativeUnit administrativeUnit "
				+ "WHERE LOWER(administrativeUnit.name) LIKE LOWER(:name) "
				+ "AND administrativeUnit.section.code IN :sectionsCodes "
				+ "AND administrativeUnit.serviceGroup.code IN :serviceGroupsCodes "
				+ "AND administrativeUnit.functionalClassification.code IN :functionalClassificationsCodes "
				+ "ORDER BY administrativeUnit.code ASC");
	}
	
	@Override
	public Collection<AdministrativeUnit> readBySectionsCodes(Collection<String> codes, Properties properties) {
		if(CollectionHelper.isEmpty(codes))
			return null;
		if(properties == null)
			properties = new Properties();
		properties.setIfNull(Properties.QUERY_IDENTIFIER, readBySectionsCodes);
		return __readMany__(properties, ____getQueryParameters____(properties,codes));
	}
	
	@Override
	public Collection<AdministrativeUnit> readByProgramsCodes(Collection<String> codes, Properties properties) {
		if(CollectionHelper.isEmpty(codes))
			return null;
		if(properties == null)
			properties = new Properties();
		properties.setIfNull(Properties.QUERY_IDENTIFIER, readByProgramsCodes);
		return __readMany__(properties, ____getQueryParameters____(properties,codes));
	}
	
	@Override
	public Integer readMaxOrderNumberByServiceGroupCodeByFunctionalClassificationCode(String serviceGroupCode,String functionalClassificationCode,Properties properties) {
		Integer maxOrderNumber = null;
		try {
			maxOrderNumber = __inject__(EntityManager.class).createNamedQuery(readMaxOrderNumberByServiceGroupCodeByFunctionalClassificationCode, Integer.class)
					.setParameter("serviceGroupCode", serviceGroupCode).setParameter("functionalClassificationCode", functionalClassificationCode).getSingleResult();
		} catch (NoResultException exception) {}
		if(maxOrderNumber == null)
			maxOrderNumber = 0;
		return maxOrderNumber;
	}
	
	@Override
	protected void __listenExecuteReadAfterSetFieldValue__(AdministrativeUnit administrativeUnit, Field field, Properties properties) {
		super.__listenExecuteReadAfterSetFieldValue__(administrativeUnit, field, properties);
		if(field.getName().equals(AdministrativeUnit.FIELD_DESTINATIONS)) {
			administrativeUnit.setDestinations(((ReadDestinationByAdministrativeUnits)__inject__(DestinationPersistence.class))
					.readByAdministrativeUnits(administrativeUnit));
		}else if(field.getName().equals(AdministrativeUnit.FIELD_ACTIVITIES)) {
			administrativeUnit.setActivities(((ReadActivityByAdministrativeUnits)__inject__(ActivityPersistence.class))
					.readByAdministrativeUnits(administrativeUnit));
		}else if(field.getName().equals(AdministrativeUnit.FIELD_PARENT)) {
			Collection<AdministrativeUnitHierarchy> administrativeUnitHierarchies = __inject__(AdministrativeUnitHierarchyPersistence.class).readWhereIsChildByChildren(administrativeUnit);
			if(CollectionHelper.isNotEmpty(administrativeUnitHierarchies)) {
				administrativeUnit.setParent(CollectionHelper.getFirst(administrativeUnitHierarchies).getParent());	
			}			
		}
	}
	
	@Override
	protected String __getQueryIdentifier__(Class<?> klass, Properties properties, Object... objects) {
		if(PersistenceFunctionReader.class.equals(klass)) {
			if(Boolean.TRUE.equals(__isFilterByKeys__(properties, AdministrativeUnit.FIELD_SECTION)))
				return readBySectionsCodes;
		}
		return super.__getQueryIdentifier__(klass, properties, objects);
	}

	@Override
	protected Object[] __getQueryParameters__(PersistenceQueryContext queryContext, Properties properties,Object... objects) {
		if(queryContext.getQuery().isIdentifierEqualsToOrQueryDerivedFromQueryIdentifierEqualsTo(readBySectionsCodes)) {
			if(ArrayHelper.isEmpty(objects))
				objects = new Object[] {queryContext.getFilterByKeysValue(AdministrativeUnit.FIELD_SECTION)};
			return new Object[]{"sectionsCodes",objects[0]};
		}
		if(queryContext.getQuery().isIdentifierEqualsToOrQueryDerivedFromQueryIdentifierEqualsTo(readByProgramsCodes)) {
			if(ArrayHelper.isEmpty(objects))
				objects = new Object[] {queryContext.getFilterByKeysValue(AdministrativeUnit.FIELD_PROGRAMS)};
			return new Object[]{"programsCodes",objects[0]};
		}
		if(queryContext.getQuery().isIdentifierEqualsToOrQueryDerivedFromQueryIdentifierEqualsTo(readByFilters)) {
			if(ArrayHelper.isEmpty(objects))
				objects = new Object[] {queryContext.getFilterByKeysValue(AdministrativeUnit.FIELD_NAME),queryContext.getFilterByKeysValue(AdministrativeUnit.FIELD_SECTION)
						,queryContext.getFilterByKeysValue(AdministrativeUnit.FIELD_SERVICE_GROUP),queryContext.getFilterByKeysValue(AdministrativeUnit.FIELD_FUNCTIONAL_CLASSIFICATION)};
			objects = new Object[]{AdministrativeUnit.FIELD_NAME,"%"+StringUtils.trimToEmpty((String) objects[0])+"%"
					,"sectionsCodes",objects[1],"serviceGroupsCodes",objects[2],"functionalClassificationsCodes",objects[3]
					};
			return objects;
		}
		return super.__getQueryParameters__(queryContext, properties, objects);
	}
}