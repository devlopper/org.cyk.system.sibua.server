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
import org.cyk.system.sibua.server.persistence.api.AdministrativeUnitActivityPersistence;
import org.cyk.system.sibua.server.persistence.api.AdministrativeUnitHierarchyPersistence;
import org.cyk.system.sibua.server.persistence.api.AdministrativeUnitPersistence;
import org.cyk.system.sibua.server.persistence.api.DestinationPersistence;
import org.cyk.system.sibua.server.persistence.api.FunctionalClassificationPersistence;
import org.cyk.system.sibua.server.persistence.api.LocalisationPersistence;
import org.cyk.system.sibua.server.persistence.api.SectionPersistence;
import org.cyk.system.sibua.server.persistence.api.ServiceGroupPersistence;
import org.cyk.system.sibua.server.persistence.api.query.ReadActivityByAdministrativeUnits;
import org.cyk.system.sibua.server.persistence.api.query.ReadActivityDestinationByAdministrativeUnits;
import org.cyk.system.sibua.server.persistence.api.query.ReadAdministrativeUnitActivityByAdministrativeUnits;
import org.cyk.system.sibua.server.persistence.api.query.ReadAdministrativeUnitByActivities;
import org.cyk.system.sibua.server.persistence.api.query.ReadAdministrativeUnitByPrograms;
import org.cyk.system.sibua.server.persistence.api.query.ReadAdministrativeUnitBySections;
import org.cyk.system.sibua.server.persistence.api.query.ReadAdministrativeUnitByUsers;
import org.cyk.system.sibua.server.persistence.api.query.ReadDestinationByAdministrativeUnits;
import org.cyk.system.sibua.server.persistence.entities.Activity;
import org.cyk.system.sibua.server.persistence.entities.AdministrativeUnit;
import org.cyk.system.sibua.server.persistence.entities.AdministrativeUnitActivity;
import org.cyk.system.sibua.server.persistence.entities.AdministrativeUnitHierarchy;
import org.cyk.system.sibua.server.persistence.entities.FunctionalClassification;
import org.cyk.system.sibua.server.persistence.entities.Localisation;
import org.cyk.system.sibua.server.persistence.entities.Section;
import org.cyk.system.sibua.server.persistence.entities.ServiceGroup;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.computation.LogicalOperator;
import org.cyk.utility.__kernel__.constant.ConstantEmpty;
import org.cyk.utility.__kernel__.persistence.query.QueryContext;
import org.cyk.utility.__kernel__.persistence.query.QueryStringHelper;
import org.cyk.utility.__kernel__.persistence.query.filter.Filter;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.server.persistence.AbstractPersistenceEntityImpl;
import org.cyk.utility.server.persistence.PersistenceFunctionReader;

@ApplicationScoped
public class AdministrativeUnitPersistenceImpl extends AbstractPersistenceEntityImpl<AdministrativeUnit> implements AdministrativeUnitPersistence,ReadAdministrativeUnitBySections,ReadAdministrativeUnitByPrograms,ReadAdministrativeUnitByActivities,ReadAdministrativeUnitByUsers,Serializable {
	private static final long serialVersionUID = 1L;

	private String readBySectionsCodes,readByProgramsCodes,readByActivitiesCodes,readMaxOrderNumberByServiceGroupCode
		,readMaxOrderNumberByServiceGroupCodeByFunctionalClassificationCode,readByFilters,readWhereCodeNotInByFilters,readByServiceGroupCodeByFunctionalClassificationCode
		,readChildrenByCodes,readWhereCodeNotInByFiltersCodesLike,readByFiltersCodesLike,readByUsersIdentifiers,readWhereCodeOrNameContainsAndSectionCodeLikes;
	
	@Override
	protected void __listenPostConstructPersistenceQueries__() {
		super.__listenPostConstructPersistenceQueries__();
		if(ApplicationScopeLifeCycleListener.isUserEnabled())
			addQueryCollectInstances(readByUsersIdentifiers, "SELECT administrativeUnit FROM AdministrativeUnit administrativeUnit WHERE EXISTS (SELECT userAdministrativeUnit FROM UserAdministrativeUnit userAdministrativeUnit WHERE userAdministrativeUnit.administrativeUnit = administrativeUnit AND userAdministrativeUnit.user.identifier IN :usersIdentifiers) ORDER BY administrativeUnit.code ASC");
		
		addQueryCollectInstances(readWhereCodeOrNameContainsAndSectionCodeLikes, "SELECT administrativeUnit FROM AdministrativeUnit administrativeUnit WHERE LOWER(administrativeUnit.code) LIKE LOWER(:code) OR LOWER(administrativeUnit.name) LIKE LOWER(:name) AND LOWER(administrativeUnit.section.code) LIKE LOWER(:sectionCode) ORDER BY administrativeUnit.code ASC");
		addQueryCollectInstances(readBySectionsCodes, "SELECT administrativeUnit FROM AdministrativeUnit administrativeUnit WHERE administrativeUnit.section.code IN :sectionsCodes ORDER BY administrativeUnit.code ASC");
		addQueryCollectInstances(readByProgramsCodes, "SELECT administrativeUnit FROM AdministrativeUnit administrativeUnit "
				+ "WHERE EXISTS(SELECT activity FROM Activity activity WHERE activity.action.program.code IN :programsCodes AND EXISTS"
				+ "(SELECT administrativeUnitActivity FROM AdministrativeUnitActivity administrativeUnitActivity "
				+ "WHERE administrativeUnitActivity.administrativeUnit = administrativeUnit AND administrativeUnitActivity.activity = activity)) "
				+ "ORDER BY administrativeUnit.code ASC");
		addQueryCollectInstances(readByActivitiesCodes, "SELECT administrativeUnit FROM AdministrativeUnit administrativeUnit "
				+ "WHERE EXISTS(SELECT administrativeUnitActivity FROM AdministrativeUnitActivity administrativeUnitActivity "
				+ "WHERE administrativeUnitActivity.administrativeUnit = administrativeUnit AND administrativeUnitActivity.activity.code IN :activitiesCodes)"
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
		
		addQueryCollectInstances(readByFiltersCodesLike, 
				"SELECT administrativeUnit FROM AdministrativeUnit administrativeUnit "
				+ "WHERE "
				+ "LOWER(administrativeUnit.code) LIKE LOWER(:code) "
				+ "AND LOWER(administrativeUnit.name) LIKE LOWER(:name) "
				+ "AND LOWER(administrativeUnit.section.code) LIKE LOWER(:sectionCode) "
				+ "AND LOWER(administrativeUnit.serviceGroup.code) LIKE LOWER(:serviceGroupCode) "
				+ "AND LOWER(administrativeUnit.functionalClassification.code) LIKE LOWER(:functionalClassificationCode) "
				+ "AND LOWER(administrativeUnit.localisation.code) LIKE LOWER(:localisationCode) "
				+ "ORDER BY administrativeUnit.code ASC");
		
		addQueryCollectInstances(readByFiltersLike, 
				"SELECT administrativeUnit FROM AdministrativeUnit administrativeUnit "
				+ "WHERE "
				+ "("+QueryStringHelper.formatTupleFieldLike("administrativeUnit", "code","administrativeUnit") + " OR " + QueryStringHelper.formatTupleFieldLikeOrTokens("administrativeUnit", "name","administrativeUnitName",4,LogicalOperator.AND)+")"
				+ " AND ("+QueryStringHelper.formatTupleFieldLike("administrativeUnit", "section.code","section") + " OR " + QueryStringHelper.formatTupleFieldLike("administrativeUnit", "section.name","section")+")"
				+ " AND ("+QueryStringHelper.formatTupleFieldLike("administrativeUnit", "serviceGroup.code","serviceGroup") + " OR " + QueryStringHelper.formatTupleFieldLike("administrativeUnit", "serviceGroup.name","serviceGroup")+")"
				+ " AND ("+QueryStringHelper.formatTupleFieldLike("administrativeUnit", "functionalClassification.code","functionalClassification") + " OR " + QueryStringHelper.formatTupleFieldLike("administrativeUnit", "functionalClassification.name","functionalClassification")+")"
				+ " AND ("+QueryStringHelper.formatTupleFieldLike("administrativeUnit", "localisation.code","localisation") + " OR " + QueryStringHelper.formatTupleFieldLike("administrativeUnit", "localisation.name","localisation")+")"
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
		
		addQueryCollectInstances(readWhereCodeNotInByFiltersCodesLike, 
				"SELECT administrativeUnit FROM AdministrativeUnit administrativeUnit "
				+ "WHERE "
				+ "administrativeUnit.code NOT IN :codes "
				+ "AND LOWER(administrativeUnit.name) LIKE LOWER(:name) "
				+ "AND LOWER(administrativeUnit.section.code) LIKE LOWER(:sectionCode) "
				+ "AND LOWER(administrativeUnit.serviceGroup.code) LIKE LOWER(:serviceGroupCode) "
				+ "AND LOWER(administrativeUnit.functionalClassification.code) LIKE LOWER(:functionalClassificationCode) "
				+ "AND LOWER(administrativeUnit.localisation.code) LIKE LOWER(:localisationCode) "
				+ "ORDER BY administrativeUnit.code ASC");
		
	}
	
	@Override
	public Collection<AdministrativeUnit> readByUsersIdentifiers(Collection<String> identifiers, Properties properties) {
		if(CollectionHelper.isEmpty(identifiers))
			return null;
		if(properties == null)
			properties = new Properties();
		properties.setIfNull(Properties.QUERY_IDENTIFIER, readByUsersIdentifiers);
		return __readMany__(properties, ____getQueryParameters____(properties,identifiers));
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
	public Collection<AdministrativeUnit> readByActivitiesCodes(Collection<String> codes, Properties properties) {
		if(CollectionHelper.isEmpty(codes))
			return null;
		if(properties == null)
			properties = new Properties();
		properties.setIfNull(Properties.QUERY_IDENTIFIER, readByActivitiesCodes);
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
	protected void __listenExecuteReadAfterSetFieldValue__(AdministrativeUnit administrativeUnit, String fieldName,Properties properties) {
		super.__listenExecuteReadAfterSetFieldValue__(administrativeUnit, fieldName, properties);
		if(fieldName.equals(AdministrativeUnit.FIELD_ACTIVITIES+"."+Activity.FIELD_ADMINISTRATIVE_UNIT_BENEFICIAIRE)) {
			if(administrativeUnit.getActivities() != null) {
				Collection<AdministrativeUnitActivity> administrativeUnitActivities = 
						((ReadAdministrativeUnitActivityByAdministrativeUnits)__inject__(AdministrativeUnitActivityPersistence.class)).readByAdministrativeUnits(administrativeUnit);
				if(CollectionHelper.isNotEmpty(administrativeUnitActivities)) {
					for(Activity activity : administrativeUnit.getActivities()) {
						for(AdministrativeUnitActivity administrativeUnitActivity : administrativeUnitActivities)
							if(administrativeUnitActivity.getActivity().equals(activity)) {
								activity.setAdministrativeUnitBeneficiaire(administrativeUnitActivity.getAdministrativeUnitBeneficiaire());
								break;
							}	
					}	
				}				
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

	@SuppressWarnings("unchecked")
	@Override
	protected Object[] __getQueryParameters__(QueryContext queryContext, Properties properties,Object... objects) {
		if(queryContext.getQuery().isIdentifierEqualsToOrQueryDerivedFromQueryIdentifierEqualsTo(readByUsersIdentifiers)) {
			return new Object[]{"usersIdentifiers",objects[0]};
		}
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
		if(queryContext.getQuery().isIdentifierEqualsToOrQueryDerivedFromQueryIdentifierEqualsTo(readByActivitiesCodes)) {
			if(ArrayHelper.isEmpty(objects))
				objects = new Object[] {queryContext.getFilterByKeysValue(AdministrativeUnit.FIELD_ACTIVITIES)};
			return new Object[]{"activitiesCodes",objects[0]};
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
				org.cyk.utility.__kernel__.persistence.query.filter.Field codeField = queryContext.getFilterFieldByKeys(AdministrativeUnit.FIELD_CODE);				
				if(codeField == null || codeField.getValue() == null || codeField.getValue() instanceof String) {
					code = "%"+(codeField == null ? ConstantEmpty.STRING : StringUtils.trimToEmpty((String) codeField.getValue()))+"%";
				}else if(codeField.getValue() instanceof Collection) {
					code = codeField.getValue();
				}
								
				Object name = null;
				org.cyk.utility.__kernel__.persistence.query.filter.Field nameField = queryContext.getFilterFieldByKeys(AdministrativeUnit.FIELD_NAME);				
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
		
		if(queryContext.getQuery().isIdentifierEqualsToOrQueryDerivedFromQueryIdentifierEqualsTo(readByFiltersLike)) {
			if(ArrayHelper.isEmpty(objects)) {
				List<String> administrativeUnitTokens = queryContext.getFieldValueLikes(AdministrativeUnit.FIELD_ADMINISTRATIVE_UNIT,5);
				objects = new Object[] {administrativeUnitTokens.get(0),administrativeUnitTokens.get(0),administrativeUnitTokens.get(1),administrativeUnitTokens.get(2)
						,administrativeUnitTokens.get(3),administrativeUnitTokens.get(4),queryContext.getStringLike(AdministrativeUnit.FIELD_SECTION)
						,queryContext.getStringLike(AdministrativeUnit.FIELD_SERVICE_GROUP),queryContext.getStringLike(AdministrativeUnit.FIELD_FUNCTIONAL_CLASSIFICATION)
						,queryContext.getStringLike(AdministrativeUnit.FIELD_LOCALISATION)};
				//System.out.println("AdministrativeUnitPersistenceImpl.__getQueryParameters__() : "+Arrays.deepToString(objects));
			}
			int index = 0;
			objects = new Object[]{AdministrativeUnit.FIELD_ADMINISTRATIVE_UNIT,objects[index++],"administrativeUnitName",objects[index++]
					,"administrativeUnitName1",objects[index++],"administrativeUnitName2",objects[index++]
					,"administrativeUnitName3",objects[index++],"administrativeUnitName4",objects[index++],AdministrativeUnit.FIELD_SECTION,objects[index++]
					,AdministrativeUnit.FIELD_SERVICE_GROUP,objects[index++],AdministrativeUnit.FIELD_FUNCTIONAL_CLASSIFICATION,objects[index++]
					,AdministrativeUnit.FIELD_LOCALISATION,objects[index++]
				};
			//System.out.println("P ::: "+Arrays.deepToString(objects));
			return objects;
		}
		
		if(queryContext.getQuery().isIdentifierEqualsToOrQueryDerivedFromQueryIdentifierEqualsTo(readByFiltersCodesLike)) {
			if(ArrayHelper.isEmpty(objects)) {
				Object code = null;
				org.cyk.utility.__kernel__.persistence.query.filter.Field codeField = queryContext.getFilterFieldByKeys(AdministrativeUnit.FIELD_CODE);				
				if(codeField == null || codeField.getValue() == null || codeField.getValue() instanceof String) {
					code = "%"+(codeField == null ? ConstantEmpty.STRING : StringUtils.trimToEmpty((String) codeField.getValue()))+"%";
				}else if(codeField.getValue() instanceof Collection) {
					code = codeField.getValue();
				}
								
				Object name = null;
				org.cyk.utility.__kernel__.persistence.query.filter.Field nameField = queryContext.getFilterFieldByKeys(AdministrativeUnit.FIELD_NAME);				
				if(nameField == null || nameField.getValue() == null || nameField.getValue() instanceof String) {
					name = "%"+(nameField == null ? ConstantEmpty.STRING : StringUtils.trimToEmpty((String) nameField.getValue()))+"%";
				}			
				
				String sectionsCode = queryContext.getStringLike(AdministrativeUnit.FIELD_SECTION);
				String serviceGroupsCode = queryContext.getStringLike(AdministrativeUnit.FIELD_SERVICE_GROUP);
				String functionalClassificationsCode = queryContext.getStringLike(AdministrativeUnit.FIELD_FUNCTIONAL_CLASSIFICATION);
				String localisationsCode = queryContext.getStringLike(AdministrativeUnit.FIELD_LOCALISATION);
				objects = new Object[] {code,name,sectionsCode,serviceGroupsCode,functionalClassificationsCode,localisationsCode};
			}
			
			objects = new Object[]{AdministrativeUnit.FIELD_CODE,objects[0],AdministrativeUnit.FIELD_NAME,objects[1]
					,"sectionCode",objects[2],"functionalClassificationCode",objects[3],"serviceGroupCode",objects[4],"localisationCode",objects[5]};
			//System.out.println("AdministrativeUnitPersistenceImpl.__getQueryParameters__() : "+java.util.Arrays.deepToString(objects));
			return objects;
		}
		
		if(queryContext.getQuery().isIdentifierEqualsToOrQueryDerivedFromQueryIdentifierEqualsTo(readWhereCodeNotInByFilters)) {
			if(ArrayHelper.isEmpty(objects)) {
				Object code = null;
				org.cyk.utility.__kernel__.persistence.query.filter.Field codeField = queryContext.getFilterFieldByKeys(AdministrativeUnit.FIELD_CODE);				
				if(codeField == null || codeField.getValue() == null) {
					code = ConstantEmpty.STRINGS_WITH_ONE_ELEMENT;
				}else if(codeField.getValue() instanceof Collection) {
					code = codeField.getValue();
					if(CollectionHelper.isEmpty((Collection<?>) code))
						code = ConstantEmpty.STRINGS_WITH_ONE_ELEMENT;
				}
								
				Object name = null;
				org.cyk.utility.__kernel__.persistence.query.filter.Field nameField = queryContext.getFilterFieldByKeys(AdministrativeUnit.FIELD_NAME);				
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
		
		if(queryContext.getQuery().isIdentifierEqualsToOrQueryDerivedFromQueryIdentifierEqualsTo(readWhereCodeNotInByFiltersCodesLike)) {
			if(ArrayHelper.isEmpty(objects)) {
				Object code = null;
				org.cyk.utility.__kernel__.persistence.query.filter.Field codeField = queryContext.getFilterFieldByKeys(AdministrativeUnit.FIELD_CODE);				
				if(codeField == null || codeField.getValue() == null) {
					code = ConstantEmpty.STRINGS_WITH_ONE_ELEMENT;
				}else if(codeField.getValue() instanceof Collection) {
					code = codeField.getValue();
					if(CollectionHelper.isEmpty((Collection<?>) code))
						code = ConstantEmpty.STRINGS_WITH_ONE_ELEMENT;
				}
								
				Object name = null;
				org.cyk.utility.__kernel__.persistence.query.filter.Field nameField = queryContext.getFilterFieldByKeys(AdministrativeUnit.FIELD_NAME);				
				if(nameField == null || nameField.getValue() == null || nameField.getValue() instanceof String) {
					name = "%"+(nameField == null ? ConstantEmpty.STRING : StringUtils.trimToEmpty((String) nameField.getValue()))+"%";
				}			
				
				String sectionsCode = queryContext.getStringLike(AdministrativeUnit.FIELD_SECTION);
				String serviceGroupsCode = queryContext.getStringLike(AdministrativeUnit.FIELD_SERVICE_GROUP);
				String functionalClassificationsCode = queryContext.getStringLike(AdministrativeUnit.FIELD_FUNCTIONAL_CLASSIFICATION);
				String localisationsCode = queryContext.getStringLike(AdministrativeUnit.FIELD_LOCALISATION);
				objects = new Object[] {code,name,sectionsCode,serviceGroupsCode,functionalClassificationsCode,localisationsCode};
			}
			
			objects = new Object[]{"codes",objects[0],AdministrativeUnit.FIELD_NAME,objects[1]
					,"sectionCode",objects[2],"functionalClassificationCode",objects[3],"serviceGroupCode",objects[4],"localisationCode",objects[5]};
			//System.out.println("AdministrativeUnitPersistenceImpl.__getQueryParameters__() : "+java.util.Arrays.deepToString(objects));
			return objects;
		}
		
		if(queryContext.getQuery().isIdentifierEqualsToOrQueryDerivedFromQueryIdentifierEqualsTo(readWhereCodeOrNameContainsAndSectionCodeLikes)) {
			if(ArrayHelper.isEmpty(objects))
				objects = new Object[] {queryContext.getStringLike(Activity.FIELD_CODE),queryContext.getStringLike(Activity.FIELD_NAME)
						,queryContext.getStringLike(Activity.FIELD_SECTION)};
			return new Object[]{"code",objects[0],"name",objects[1],"sectionCode",objects[2]};
		}
		
		return super.__getQueryParameters__(queryContext, properties, objects);
	}
	
	
}