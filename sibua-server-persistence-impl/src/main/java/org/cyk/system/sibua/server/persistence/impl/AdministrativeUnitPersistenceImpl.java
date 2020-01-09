package org.cyk.system.sibua.server.persistence.impl;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.cyk.system.sibua.server.persistence.api.ActivityDestinationPersistence;
import org.cyk.system.sibua.server.persistence.api.ActivityPersistence;
import org.cyk.system.sibua.server.persistence.api.AdministrativeUnitHierarchyPersistence;
import org.cyk.system.sibua.server.persistence.api.AdministrativeUnitPersistence;
import org.cyk.system.sibua.server.persistence.api.DestinationPersistence;
import org.cyk.system.sibua.server.persistence.api.FunctionalClassificationPersistence;
import org.cyk.system.sibua.server.persistence.api.LocalisationPersistence;
import org.cyk.system.sibua.server.persistence.api.SectionPersistence;
import org.cyk.system.sibua.server.persistence.api.ServiceGroupPersistence;
import org.cyk.system.sibua.server.persistence.api.query.ReadActivityByAdministrativeUnits;
import org.cyk.system.sibua.server.persistence.api.query.ReadActivityDestinationByAdministrativeUnits;
import org.cyk.system.sibua.server.persistence.api.query.ReadAdministrativeUnitByPrograms;
import org.cyk.system.sibua.server.persistence.api.query.ReadAdministrativeUnitBySections;
import org.cyk.system.sibua.server.persistence.api.query.ReadDestinationByAdministrativeUnits;
import org.cyk.system.sibua.server.persistence.entities.AdministrativeUnit;
import org.cyk.system.sibua.server.persistence.entities.AdministrativeUnitHierarchy;
import org.cyk.system.sibua.server.persistence.entities.FunctionalClassification;
import org.cyk.system.sibua.server.persistence.entities.Localisation;
import org.cyk.system.sibua.server.persistence.entities.Section;
import org.cyk.system.sibua.server.persistence.entities.ServiceGroup;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.constant.ConstantEmpty;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.server.persistence.AbstractPersistenceEntityImpl;
import org.cyk.utility.server.persistence.PersistenceFunctionReader;
import org.cyk.utility.server.persistence.query.PersistenceQueryContext;
import org.cyk.utility.server.persistence.query.filter.Filter;

@ApplicationScoped
public class AdministrativeUnitPersistenceImpl extends AbstractPersistenceEntityImpl<AdministrativeUnit> implements AdministrativeUnitPersistence,ReadAdministrativeUnitBySections,ReadAdministrativeUnitByPrograms,Serializable {
	private static final long serialVersionUID = 1L;

	private String readBySectionsCodes,readByProgramsCodes,readMaxOrderNumberByServiceGroupCode,readMaxOrderNumberByServiceGroupCodeByFunctionalClassificationCode,readByFilters,readWhereCodeNotInByFilters,readByServiceGroupCodeByFunctionalClassificationCode,readChildrenByCodes;
	
	@Override
	protected void __listenPostConstructPersistenceQueries__() {
		super.__listenPostConstructPersistenceQueries__();
		addQueryCollectInstances(readBySectionsCodes, "SELECT administrativeUnit FROM AdministrativeUnit administrativeUnit WHERE administrativeUnit.section.code IN :sectionsCodes ORDER BY administrativeUnit.code ASC");
		addQueryCollectInstances(readByProgramsCodes, "SELECT administrativeUnit FROM AdministrativeUnit administrativeUnit "
				+ "WHERE EXISTS(SELECT activity FROM Activity activity WHERE activity.action.program.code IN :programsCodes AND EXISTS"
				+ "(SELECT administrativeUnitActivity FROM AdministrativeUnitActivity administrativeUnitActivity "
				+ "WHERE administrativeUnitActivity.administrativeUnit = administrativeUnit AND administrativeUnitActivity.activity = activity)) "
				+ "ORDER BY administrativeUnit.code ASC");
		addQueryCollectInstances(readByServiceGroupCodeByFunctionalClassificationCode, "SELECT administrativeUnit FROM AdministrativeUnit administrativeUnit "
				+ "WHERE administrativeUnit.serviceGroup.code = :serviceGroupCode AND administrativeUnit.functionalClassification.code = :functionalClassificationCode "
				+ "ORDER BY administrativeUnit.code ASC");
		addQuery(readMaxOrderNumberByServiceGroupCode, "SELECT MAX(administrativeUnit.orderNumber) FROM AdministrativeUnit administrativeUnit "
				+ "WHERE administrativeUnit.serviceGroup.code = :serviceGroupCode",Integer.class);
		
		addQuery(readMaxOrderNumberByServiceGroupCodeByFunctionalClassificationCode, "SELECT MAX(administrativeUnit.orderNumber) FROM AdministrativeUnit administrativeUnit "
				+ "WHERE administrativeUnit.serviceGroup.code = :serviceGroupCode AND administrativeUnit.functionalClassification.code = :functionalClassificationCode",Integer.class);
		
		addQueryCollectInstances(readChildrenByCodes, "SELECT administrativeUnit FROM AdministrativeUnit administrativeUnit "
				+ "WHERE EXISTS(SELECT administrativeUnitHierarchy FROM AdministrativeUnitHierarchy administrativeUnitHierarchy WHERE administrativeUnitHierarchy.parent.code IN :codes AND administrativeUnitHierarchy.child = administrativeUnit)");
		
		/*
		addQueryCollectInstances(readByFilters, "SELECT administrativeUnit FROM AdministrativeUnit administrativeUnit "
				+ "WHERE "
				+ "administrativeUnit.code NOT IN :codes "
				+ "AND LOWER(administrativeUnit.name) LIKE LOWER(:name) "
				+ "AND administrativeUnit.section.code IN :sectionsCodes "
				+ "AND administrativeUnit.serviceGroup.code IN :serviceGroupsCodes "
				+ "AND administrativeUnit.functionalClassification.code IN :functionalClassificationsCodes "
				+ "ORDER BY administrativeUnit.code ASC");
		*/
		addQueryCollectInstances(readByFilters, 
				"SELECT administrativeUnit FROM AdministrativeUnit administrativeUnit "
				+ "WHERE "
				+ "LOWER(administrativeUnit.code) LIKE LOWER(:code) "
				+ "AND LOWER(administrativeUnit.name) LIKE LOWER(:name) "
				+ "AND administrativeUnit.section.code IN :sectionsCodes "
				+ "AND administrativeUnit.serviceGroup.code IN :serviceGroupsCodes "
				+ "AND administrativeUnit.functionalClassification.code IN :functionalClassificationsCodes "
				+ "AND (administrativeUnit.localisation.code IN :localisationsCodes01 OR administrativeUnit.localisation.code IN :localisationsCodes02) "//FIXME Because of orcale limitation : ora-01795 maximum number of expressions in a list is 1000
				+ "ORDER BY administrativeUnit.code ASC");
		
		addQueryCollectInstances(readWhereCodeNotInByFilters, 
				"SELECT administrativeUnit FROM AdministrativeUnit administrativeUnit "
				+ "WHERE "
				+ "administrativeUnit.code NOT IN :codes "
				+ "AND LOWER(administrativeUnit.name) LIKE LOWER(:name) "
				+ "AND administrativeUnit.section.code IN :sectionsCodes "
				+ "AND administrativeUnit.serviceGroup.code IN :serviceGroupsCodes "
				+ "AND administrativeUnit.functionalClassification.code IN :functionalClassificationsCodes "
				+ "AND (administrativeUnit.localisation.code IN :localisationsCodes01 OR administrativeUnit.localisation.code IN :localisationsCodes02) "//FIXME Because of orcale limitation : ora-01795 maximum number of expressions in a list is 1000
				+ "ORDER BY administrativeUnit.code ASC");
	}
	
	@Override
	public Collection<AdministrativeUnit> readByServiceGroupCodeByFunctionalClassificationCode(String serviceGroupCode,String functionalClassificationCode, Properties properties) {
		if(StringHelper.isBlank(serviceGroupCode) || StringHelper.isBlank(functionalClassificationCode))
			return null;
		if(properties == null)
			properties = new Properties();
		properties.setIfNull(Properties.QUERY_IDENTIFIER, readByServiceGroupCodeByFunctionalClassificationCode);
		return __readMany__(properties, ____getQueryParameters____(properties,serviceGroupCode,functionalClassificationCode));
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
	public Integer readMaxOrderNumberByServiceGroupCode(String serviceGroupCode, Properties properties) {
		Integer maxOrderNumber = null;
		try {
			maxOrderNumber = __inject__(EntityManager.class).createNamedQuery(readMaxOrderNumberByServiceGroupCode, Integer.class)
					.setParameter("serviceGroupCode", serviceGroupCode).getSingleResult();
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
		}else if(field.getName().equals(AdministrativeUnit.FIELD_ACTIVITY_DESTINATIONS)) {
			administrativeUnit.setActivityDestinations(((ReadActivityDestinationByAdministrativeUnits)__inject__(ActivityDestinationPersistence.class))
					.readByAdministrativeUnits(administrativeUnit));
		}else if(field.getName().equals(AdministrativeUnit.FIELD_PARENT)) {
			Collection<AdministrativeUnitHierarchy> administrativeUnitHierarchies = __inject__(AdministrativeUnitHierarchyPersistence.class).readWhereIsChildByChildren(administrativeUnit);
			if(CollectionHelper.isNotEmpty(administrativeUnitHierarchies)) {
				administrativeUnit.setParent(CollectionHelper.getFirst(administrativeUnitHierarchies).getParent());	
			}			
		}else if(field.getName().equals(AdministrativeUnit.FIELD_CHILDREN)) {
			administrativeUnit.setChildren(read(new Properties().setQueryIdentifier(AdministrativeUnitPersistence.READ_CHILDREN_BY_CODES)
					.setQueryFilters(__inject__(Filter.class).addField(AdministrativeUnit.FIELD_CODE, List.of(administrativeUnit.getCode())))));
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

	@SuppressWarnings("unchecked")
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
		if(queryContext.getQuery().isIdentifierEqualsToOrQueryDerivedFromQueryIdentifierEqualsTo(readByServiceGroupCodeByFunctionalClassificationCode)) {
			return new Object[]{"serviceGroupCode",objects[0],"functionalClassificationCode",objects[1]};
		}
		if(queryContext.getQuery().isIdentifierEqualsToOrQueryDerivedFromQueryIdentifierEqualsTo(readChildrenByCodes)) {
			if(ArrayHelper.isEmpty(objects)) {
				objects = new Object[] {queryContext.getFilterFieldByKeys(AdministrativeUnit.FIELD_CODE).getValue()};
			}
			return new Object[]{"codes",objects[0]};
		}
		/*
		if(queryContext.getQuery().isIdentifierEqualsToOrQueryDerivedFromQueryIdentifierEqualsTo(readByFilters)) {
			if(ArrayHelper.isEmpty(objects))
				objects = new Object[] {queryContext.getFilterByKeysValue(AdministrativeUnit.FIELD_NAME)
						,queryContext.getFilterByKeysValue(AdministrativeUnit.FIELD_SECTION)
						,queryContext.getFilterByKeysValue(AdministrativeUnit.FIELD_SERVICE_GROUP)
						,queryContext.getFilterByKeysValue(AdministrativeUnit.FIELD_FUNCTIONAL_CLASSIFICATION)
						,queryContext.getFilterByKeysValue(AdministrativeUnit.FIELD_CODE) == null ? ConstantEmpty.STRINGS_WITH_ONE_ELEMENT : queryContext.getFilterByKeysValue(AdministrativeUnit.FIELD_CODE)
						};
			objects = new Object[]{AdministrativeUnit.FIELD_NAME,"%"+StringUtils.trimToEmpty((String) objects[0])+"%"
					,"sectionsCodes",objects[1],"serviceGroupsCodes",objects[2],"functionalClassificationsCodes",objects[3]
							,"codes",objects[4]
					};
			//System.out.println("AdministrativeUnitPersistenceImpl.__getQueryParameters__() : "+Arrays.deepToString(objects));
			return objects;
		}
		*/
		if(queryContext.getQuery().isIdentifierEqualsToOrQueryDerivedFromQueryIdentifierEqualsTo(readByFilters)) {
			if(ArrayHelper.isEmpty(objects)) {
				Object code = null;
				org.cyk.utility.server.persistence.query.filter.Field codeField = queryContext.getFilterFieldByKeys(AdministrativeUnit.FIELD_CODE);				
				if(codeField == null || codeField.getValue() == null || codeField.getValue() instanceof String) {
					code = "%"+(codeField == null ? ConstantEmpty.STRING : StringUtils.trimToEmpty((String) codeField.getValue()))+"%";
				}else if(codeField.getValue() instanceof Collection) {
					code = codeField.getValue();
				}
								
				Object name = null;
				org.cyk.utility.server.persistence.query.filter.Field nameField = queryContext.getFilterFieldByKeys(AdministrativeUnit.FIELD_NAME);				
				if(nameField == null || nameField.getValue() == null || nameField.getValue() instanceof String) {
					name = "%"+(nameField == null ? ConstantEmpty.STRING : StringUtils.trimToEmpty((String) nameField.getValue()))+"%";
				}			
				
				Collection<String> sectionsCodes = (Collection<String>) queryContext.getFilterByKeysValue(AdministrativeUnit.FIELD_SECTION);
				if(CollectionHelper.isEmpty(sectionsCodes)) {
					if(sectionsCodes == null)
						sectionsCodes = new ArrayList<>();
					sectionsCodes.addAll(__inject__(SectionPersistence.class).read().stream().map(Section::getCode).collect(Collectors.toList()));
				}
				
				Collection<String> functionalClassificationsCodes = (Collection<String>) queryContext.getFilterByKeysValue(AdministrativeUnit.FIELD_FUNCTIONAL_CLASSIFICATION);
				if(CollectionHelper.isEmpty(functionalClassificationsCodes)) {
					if(functionalClassificationsCodes == null)
						functionalClassificationsCodes = new ArrayList<>();
					functionalClassificationsCodes.addAll(__inject__(FunctionalClassificationPersistence.class).read().stream().map(FunctionalClassification::getCode).collect(Collectors.toList()));
				}

				Collection<String> serviceGroupsCodes = (Collection<String>) queryContext.getFilterByKeysValue(AdministrativeUnit.FIELD_SERVICE_GROUP);
				if(CollectionHelper.isEmpty(serviceGroupsCodes)) {
					if(serviceGroupsCodes == null)
						serviceGroupsCodes = new ArrayList<>();
					serviceGroupsCodes.addAll(__inject__(ServiceGroupPersistence.class).read().stream().map(ServiceGroup::getCode).collect(Collectors.toList()));
				}
				
				Collection<String> localisationsCodes = (Collection<String>) queryContext.getFilterByKeysValue(AdministrativeUnit.FIELD_LOCALISATION);
				if(CollectionHelper.isEmpty(localisationsCodes)) {
					if(localisationsCodes == null)
						localisationsCodes = new ArrayList<>();
					localisationsCodes.addAll(__inject__(LocalisationPersistence.class).read().stream().map(Localisation::getCode).collect(Collectors.toList()));
				}
				//FIXME Because of orcale limitation : ora-01795 maximum number of expressions in a list is 1000
				List<List<String>> lists = ListUtils.partition((List<String>) localisationsCodes, 999);
				Collection<String> localisationsCodes01 = lists.get(0);
				Collection<String> localisationsCodes02 = lists.size() == 1 ? CollectionHelper.listOf("x") : lists.get(1);
				objects = new Object[] {code,name,sectionsCodes,functionalClassificationsCodes,serviceGroupsCodes,localisationsCodes01,localisationsCodes02};
			}
			
			objects = new Object[]{AdministrativeUnit.FIELD_CODE,objects[0],AdministrativeUnit.FIELD_NAME,objects[1]
					,"sectionsCodes",objects[2],"functionalClassificationsCodes",objects[3],"serviceGroupsCodes",objects[4],"localisationsCodes01",objects[5]
							,"localisationsCodes02",objects[6]};
			return objects;
		}
		
		if(queryContext.getQuery().isIdentifierEqualsToOrQueryDerivedFromQueryIdentifierEqualsTo(readWhereCodeNotInByFilters)) {
			if(ArrayHelper.isEmpty(objects)) {
				Object code = null;
				org.cyk.utility.server.persistence.query.filter.Field codeField = queryContext.getFilterFieldByKeys(AdministrativeUnit.FIELD_CODE);				
				if(codeField == null || codeField.getValue() == null) {
					code = ConstantEmpty.STRINGS_WITH_ONE_ELEMENT;
				}else if(codeField.getValue() instanceof Collection) {
					code = codeField.getValue();
					if(CollectionHelper.isEmpty((Collection<?>) code))
						code = ConstantEmpty.STRINGS_WITH_ONE_ELEMENT;
				}
								
				Object name = null;
				org.cyk.utility.server.persistence.query.filter.Field nameField = queryContext.getFilterFieldByKeys(AdministrativeUnit.FIELD_NAME);				
				if(nameField == null || nameField.getValue() == null || nameField.getValue() instanceof String) {
					name = "%"+(nameField == null ? ConstantEmpty.STRING : StringUtils.trimToEmpty((String) nameField.getValue()))+"%";
				}			
				
				Collection<String> sectionsCodes = (Collection<String>) queryContext.getFilterByKeysValue(AdministrativeUnit.FIELD_SECTION);
				if(CollectionHelper.isEmpty(sectionsCodes)) {
					if(sectionsCodes == null)
						sectionsCodes = new ArrayList<>();
					sectionsCodes.addAll(__inject__(SectionPersistence.class).read().stream().map(Section::getCode).collect(Collectors.toList()));
				}
				
				Collection<String> functionalClassificationsCodes = (Collection<String>) queryContext.getFilterByKeysValue(AdministrativeUnit.FIELD_FUNCTIONAL_CLASSIFICATION);
				if(CollectionHelper.isEmpty(functionalClassificationsCodes)) {
					if(functionalClassificationsCodes == null)
						functionalClassificationsCodes = new ArrayList<>();
					functionalClassificationsCodes.addAll(__inject__(FunctionalClassificationPersistence.class).read().stream().map(FunctionalClassification::getCode).collect(Collectors.toList()));
				}

				Collection<String> serviceGroupsCodes = (Collection<String>) queryContext.getFilterByKeysValue(AdministrativeUnit.FIELD_SERVICE_GROUP);
				if(CollectionHelper.isEmpty(serviceGroupsCodes)) {
					if(serviceGroupsCodes == null)
						serviceGroupsCodes = new ArrayList<>();
					serviceGroupsCodes.addAll(__inject__(ServiceGroupPersistence.class).read().stream().map(ServiceGroup::getCode).collect(Collectors.toList()));
				}
				
				Collection<String> localisationsCodes = (Collection<String>) queryContext.getFilterByKeysValue(AdministrativeUnit.FIELD_LOCALISATION);
				if(CollectionHelper.isEmpty(localisationsCodes)) {
					if(localisationsCodes == null)
						localisationsCodes = new ArrayList<>();
					localisationsCodes.addAll(__inject__(LocalisationPersistence.class).read().stream().map(Localisation::getCode).collect(Collectors.toList()));
				}
				//FIXME Because of orcale limitation : ora-01795 maximum number of expressions in a list is 1000
				List<List<String>> lists = ListUtils.partition((List<String>) localisationsCodes, 999);
				Collection<String> localisationsCodes01 = lists.get(0);
				Collection<String> localisationsCodes02 = lists.size() == 1 ? CollectionHelper.listOf("x") : lists.get(1);
				objects = new Object[] {code,name,sectionsCodes,functionalClassificationsCodes,serviceGroupsCodes,localisationsCodes01,localisationsCodes02};
			}
			
			objects = new Object[]{"codes",objects[0],AdministrativeUnit.FIELD_NAME,objects[1]
					,"sectionsCodes",objects[2],"functionalClassificationsCodes",objects[3],"serviceGroupsCodes",objects[4],"localisationsCodes01",objects[5]
							,"localisationsCodes02",objects[6]};
			//System.out.println("AdministrativeUnitPersistenceImpl.__getQueryParameters__() : "+java.util.Arrays.deepToString(objects));
			return objects;
		}
		
		return super.__getQueryParameters__(queryContext, properties, objects);
	}
	
	
}