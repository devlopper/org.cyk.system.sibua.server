package org.cyk.system.sibua.server.business.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.sibua.server.business.api.AdministrativeUnitBusiness;
import org.cyk.system.sibua.server.business.api.AdministrativeUnitHierarchyBusiness;
import org.cyk.system.sibua.server.persistence.api.AdministrativeUnitActivityPersistence;
import org.cyk.system.sibua.server.persistence.api.AdministrativeUnitDestinationPersistence;
import org.cyk.system.sibua.server.persistence.api.AdministrativeUnitHierarchyPersistence;
import org.cyk.system.sibua.server.persistence.api.AdministrativeUnitPersistence;
import org.cyk.system.sibua.server.persistence.api.query.ReadAdministrativeUnitActivityByAdministrativeUnits;
import org.cyk.system.sibua.server.persistence.api.query.ReadAdministrativeUnitBySections;
import org.cyk.system.sibua.server.persistence.api.query.ReadAdministrativeUnitDestinationByAdministrativeUnits;
import org.cyk.system.sibua.server.persistence.entities.Activity;
import org.cyk.system.sibua.server.persistence.entities.ActivityDestination;
import org.cyk.system.sibua.server.persistence.entities.AdministrativeUnit;
import org.cyk.system.sibua.server.persistence.entities.AdministrativeUnitActivity;
import org.cyk.system.sibua.server.persistence.entities.AdministrativeUnitDestination;
import org.cyk.system.sibua.server.persistence.entities.AdministrativeUnitHierarchy;
import org.cyk.system.sibua.server.persistence.entities.Destination;
import org.cyk.system.sibua.server.persistence.entities.FunctionalClassification;
import org.cyk.system.sibua.server.persistence.entities.ServiceGroup;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.collection.CollectionInstance;
import org.cyk.utility.__kernel__.constant.ConstantCharacter;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.object.__static__.persistence.AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.random.RandomHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.string.Strings;
import org.cyk.utility.server.business.AbstractBusinessEntityImpl;
import org.cyk.utility.server.business.BusinessEntity;
import org.cyk.utility.server.business.BusinessFunctionCreator;
import org.cyk.utility.server.business.BusinessFunctionModifier;

@ApplicationScoped
public class AdministrativeUnitBusinessImpl extends AbstractBusinessEntityImpl<AdministrativeUnit, AdministrativeUnitPersistence> implements AdministrativeUnitBusiness,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public void generateCodesBySectionsCodes(Collection<String> codes) {
		if(CollectionHelper.isEmpty(codes))
			return;
		Collection<AdministrativeUnit> administrativeUnits = ((ReadAdministrativeUnitBySections)__inject__(AdministrativeUnitPersistence.class)).readBySectionsCodes(codes);
		if(CollectionHelper.isEmpty(administrativeUnits))
			return;
		//Map<String,Map<String,Integer>> latestOrderNumberMap = new HashMap<>();
		Map<String,Integer> latestOrderNumberMap = new HashMap<>();
		Collection<AdministrativeUnit> administrativeUnitsWithCodeGenerated = null;
		for(AdministrativeUnit administrativeUnit : administrativeUnits) {
			if(administrativeUnit.getOrderNumber() == null || administrativeUnit.getOrderNumber() > 0)
				continue;
			//Integer orderNumber = __getNextOrderNumber__(administrativeUnit, latestOrderNumberMap);
			Integer orderNumber = __getNextOrderNumber__(administrativeUnit.getServiceGroup(), latestOrderNumberMap);
			if(orderNumber == null) {
				LogHelper.logSevere("Erreur lors de la génération du code de l'unité administrative de la section "+administrativeUnit.getSection().getCode()
						+" : "+administrativeUnit.getName()+". Le numéro d'ordre n'a pas pu être déterminé.", getClass());
				continue;
			}
			administrativeUnit.setOrderNumber(orderNumber);
			String code = __generateCode__(administrativeUnit);
			if(StringHelper.isBlank(code)) {
				LogHelper.logSevere("Erreur lors de la génération du code de l'unité administrative de la section "+administrativeUnit.getSection().getCode()
						+" : "+administrativeUnit.getName()+". Le code n'a pas pu être déterminé.", getClass());
				continue;
			}
			administrativeUnit.setCode(code);
			if(administrativeUnitsWithCodeGenerated == null)
				administrativeUnitsWithCodeGenerated = new ArrayList<>();
			administrativeUnitsWithCodeGenerated.add(administrativeUnit);
		}
		updateMany(administrativeUnitsWithCodeGenerated);
	}
	
	private Integer __getNextOrderNumber__(AdministrativeUnit administrativeUnit,Map<String,Map<String,Integer>> latestOrderNumberMap) {
		if(administrativeUnit == null)
			return null;
		return __getNextOrderNumber__(administrativeUnit.getServiceGroup(), administrativeUnit.getFunctionalClassification(), latestOrderNumberMap);
	}
	
	private Integer __getNextOrderNumber__(ServiceGroup serviceGroup,FunctionalClassification functionalClassification,Map<String,Map<String,Integer>> latestOrderNumberMap) {
		if(functionalClassification == null || functionalClassification.getCode().equals(FunctionalClassification.CODE_NOT_SET)
			|| serviceGroup == null || serviceGroup.getCode().equals(ServiceGroup.CODE_NOT_SET))
			return null;
		Map<String,Integer> map = latestOrderNumberMap.get(serviceGroup.getCode());
		if(map == null)
			latestOrderNumberMap.put(serviceGroup.getCode(), map = new HashMap<>());
		Integer latestOrderNumber = map.get(functionalClassification.getCode());
		if(latestOrderNumber == null) {
			Integer value = __persistence__.readMaxOrderNumberByServiceGroupByFunctionalClassification(serviceGroup, functionalClassification);
			latestOrderNumber = value == null || value < 1 ? 0 : value;
		}
		latestOrderNumber = latestOrderNumber + 1;
		map.put(functionalClassification.getCode(), latestOrderNumber);
		return latestOrderNumber;
	}
	
	private Integer __getNextOrderNumber__(ServiceGroup serviceGroup,Map<String,Integer> latestOrderNumberMap) {
		if(serviceGroup == null || serviceGroup.getCode().equals(ServiceGroup.CODE_NOT_SET))
			return null;
		Integer latestOrderNumber = latestOrderNumberMap.get(serviceGroup.getCode());
		if(latestOrderNumber == null) {
			Integer value = __persistence__.readMaxOrderNumberByServiceGroup(serviceGroup);
			latestOrderNumber = value == null || value < 1 ? 0 : value;
		}
		latestOrderNumber = latestOrderNumber + 1;
		latestOrderNumberMap.put(serviceGroup.getCode(), latestOrderNumber);
		return latestOrderNumber;
	}
	
	private String __generateCode__(AdministrativeUnit administrativeUnit) {
		if(administrativeUnit == null)
			return null;
		return __generateCode__(administrativeUnit, administrativeUnit.getOrderNumber());
	}
	
	private String __generateCode__(AdministrativeUnit administrativeUnit,Integer orderNumber) {
		if(administrativeUnit == null || orderNumber == null)
			return null;
		return __generateCode__(administrativeUnit.getServiceGroup(), administrativeUnit.getFunctionalClassification(), orderNumber);
	}
	
	private String __generateCode__(ServiceGroup serviceGroup,FunctionalClassification functionalClassification,Integer orderNumber) {
		if(/*functionalClassification == null || functionalClassification.getCode().equals(FunctionalClassification.CODE_NOT_SET)
			|| */serviceGroup == null || serviceGroup.getCode().equals(ServiceGroup.CODE_NOT_SET)
			|| orderNumber == null || orderNumber <= 0)
			return null;
		//return serviceGroup.getCode()+StringUtils.leftPad(orderNumber.toString(), 4, ConstantCharacter.ZERO)+functionalClassification.getCode();
		return serviceGroup.getCode()+StringUtils.leftPad(orderNumber.toString(), 4, ConstantCharacter.ZERO);
	}
	
	private void __setOrderNumberAndCode__(AdministrativeUnit administrativeUnit) {
		/*if(administrativeUnit.getFunctionalClassification().getCode().equals(FunctionalClassification.CODE_NOT_SET) || administrativeUnit.getServiceGroup().getCode().equals(ServiceGroup.CODE_NOT_SET))
			administrativeUnit.setOrderNumber(RandomHelper.getNumeric(20).intValue());
		else
			//administrativeUnit.setOrderNumber(__persistence__.readMaxOrderNumberByServiceGroupCodeByFunctionalClassificationCode(administrativeUnit.getServiceGroup().getCode()
			//	, administrativeUnit.getFunctionalClassification().getCode(), properties)+1);
			administrativeUnit.setOrderNumber(Long.valueOf(System.currentTimeMillis()).intValue()+RandomHelper.getNumeric(5).intValue());
			
		if(administrativeUnit.getServiceGroup() != null && administrativeUnit.getFunctionalClassification() != null && administrativeUnit.getOrderNumber() != null) {
			administrativeUnit.setCode(administrativeUnit.getServiceGroup().getCode()+administrativeUnit.getFunctionalClassification().getBusinessIdentifier()+
				StringUtils.leftPad(administrativeUnit.getOrderNumber().toString(), 5, ConstantCharacter.ZERO));
			//System.out.println("AdministrativeUnitBusinessImpl.__setOrderNumberAndCode__() CODE : "+administrativeUnit.getCode());
		}*/
		
		administrativeUnit.setOrderNumber(-1);
		if(StringHelper.isBlank(administrativeUnit.getCode()))
			administrativeUnit.setCode(RandomHelper.getAlphanumeric(20));
	}
	
	@Override
	protected void __listenExecuteCreateBefore__(AdministrativeUnit administrativeUnit, Properties properties,BusinessFunctionCreator function) {
		super.__listenExecuteCreateBefore__(administrativeUnit, properties, function);
		__setOrderNumberAndCode__(administrativeUnit);
	}
	
	@Override
	protected void __listenExecuteCreateAfter__(Collection<AdministrativeUnit> administrativeUnits, Properties properties,BusinessFunctionCreator function) {
		super.__listenExecuteCreateAfter__(administrativeUnits, properties, function);
	}
	
	private <T extends AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl> void __createWhereIdentifierIsBlankAndCodeIsNotBlank__(AdministrativeUnit administrativeUnit,Collection<T> collection,BusinessEntity<T> business,String fieldName) {
		//some new activity and destination might need to be created. find all new one having same code and make them using only one instance
		/*
		Collection<T> activitiesWhereIdentifierIsBlankAndCodeIsNotBlank = administrativeUnit.getActivityDestinations().stream()
				.map(ActivityDestination::getActivity)
				.filter(activity -> StringHelper.isBlank(activity.getIdentifier()) && StringHelper.isNotBlank(activity.getCode()))
				.collect(Collectors.toList());
		*/
		if(CollectionHelper.isEmpty(collection))
			return;
		collection = CollectionHelper.removeDuplicate(collection, entity -> entity.getCode());
		if(CollectionHelper.isEmpty(collection))
			return;		
		business.createMany(collection);
		//update instance where those codes are used
		for(ActivityDestination activityDestination : administrativeUnit.getActivityDestinations()) {
			AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl fieldValue = (AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl) FieldHelper.read(activityDestination, fieldName);
			if(StringHelper.isBlank(fieldValue.getIdentifier()) && StringHelper.isNotBlank(fieldValue.getCode())) {
				for(T index : collection) {
					if(index.getCode().equals(fieldValue.getCode())) {
						FieldHelper.write(activityDestination, fieldName, index);
						break;
					}
				}
			}	
		}
	}
	
	@Override
	protected void __listenExecuteCreateAfter__(AdministrativeUnit administrativeUnit, Properties properties,BusinessFunctionCreator function) {
		super.__listenExecuteCreateAfter__(administrativeUnit, properties, function);
		
		if(CollectionHelper.isNotEmpty(administrativeUnit.getActivityDestinations())) {
			//some new activity and destination might need to be created. find all new one having same code and make them using only one instance
			Collection<Activity> activitiesWhereIdentifierIsBlankAndCodeIsNotBlank = administrativeUnit.getActivityDestinations().stream()
					.map(ActivityDestination::getActivity)
					.filter(activity -> StringHelper.isBlank(activity.getIdentifier()) && StringHelper.isNotBlank(activity.getCode()))
					.collect(Collectors.toList());
			//__createWhereIdentifierIsBlankAndCodeIsNotBlank__(administrativeUnit, activitiesWhereIdentifierIsBlankAndCodeIsNotBlank, __inject__(ActivityBusiness.class), ActivityDestination.FIELD_ACTIVITY);
			
			Collection<Destination> destinationsWhereIdentifierIsBlankAndCodeIsNotBlank = administrativeUnit.getActivityDestinations().stream()
					.map(ActivityDestination::getDestination)
					.filter(destination -> StringHelper.isBlank(destination.getIdentifier()) && StringHelper.isNotBlank(destination.getCode()))
					.collect(Collectors.toList());
			//__createWhereIdentifierIsBlankAndCodeIsNotBlank__(administrativeUnit, destinationsWhereIdentifierIsBlankAndCodeIsNotBlank, __inject__(DestinationBusiness.class), ActivityDestination.FIELD_DESTINATION);
			
			/*
			if(CollectionHelper.isNotEmpty(activitiesWhereIdentifierIsBlankAndCodeIsNotBlank))
				activitiesWhereIdentifierIsBlankAndCodeIsNotBlank = CollectionHelper.removeDuplicate(activitiesWhereIdentifierIsBlankAndCodeIsNotBlank, activity -> activity.getCode());
			if(CollectionHelper.isNotEmpty(activitiesWhereIdentifierIsBlankAndCodeIsNotBlank)) {
				__inject__(ActivityBusiness.class).createMany(activitiesWhereIdentifierIsBlankAndCodeIsNotBlank);
				//update instance where those codes are used
				for(ActivityDestination activityDestination : administrativeUnit.getActivityDestinations())
					if(StringHelper.isBlank(activityDestination.getActivity().getIdentifier()) && StringHelper.isNotBlank(activityDestination.getActivity().getCode())) {
						for(Activity activity : activitiesWhereIdentifierIsBlankAndCodeIsNotBlank)
							if(activity.getCode().equals(activityDestination.getActivity().getCode())) {
								activityDestination.setActivity(activity);
								break;
							}
					}
			}
			*/
			Collection<Activity> activities = administrativeUnit.getActivityDestinations().stream().map(ActivityDestination::getActivity).collect(Collectors.toList());
			//create new activities
			/*
			Collection<Activity> newActivities = new HashSet<>();
			for(Activity activity : activities) {
				if(StringHelper.isBlank(activity.getIdentifier())) {
					//This is a new activity
					System.out.println("AdministrativeUnitBusinessImpl.__listenExecuteCreateAfter__() ACTIVITY : "+activity.getCode());
					if(StringHelper.isBlank(activity.getCode()))
						activity.setCode(Activity.CODE_NEW_PREFIX+RandomHelper.getNumeric(10));
					activity.setIdentifier(activity.getCode());
					newActivities.add(activity);
				}
			}
			if(CollectionHelper.isNotEmpty(newActivities))
				__inject__(ActivityBusiness.class).createMany(newActivities);
			*/
			/*
			Collection<Destination> destinations = administrativeUnit.getActivityDestinations().stream().map(ActivityDestination::getDestination).collect(Collectors.toList());
			//create new destinations
			Collection<Destination> newDestinations = new HashSet<>();
			for(Destination destination : destinations) {
				if(StringHelper.isBlank(destination.getIdentifier())) {
					//This is a new activity
					destination.setCode(Destination.CODE_NEW_PREFIX+RandomHelper.getNumeric(10));
					destination.setIdentifier(destination.getCode());
					destination.setSection(administrativeUnit.getSection());
					newDestinations.add(destination);
				}
			}
			if(CollectionHelper.isNotEmpty(newDestinations))
				__inject__(DestinationBusiness.class).createMany(newDestinations);
			
			if(CollectionHelper.isNotEmpty(activities))
				__inject__(AdministrativeUnitActivityBusiness.class).createMany(activities.stream().map(activity -> new AdministrativeUnitActivity()
					.setAdministrativeUnit(administrativeUnit).setActivity(activity)).collect(Collectors.toList()));
			
			if(CollectionHelper.isNotEmpty(destinations))
				__inject__(AdministrativeUnitDestinationBusiness.class).createMany(destinations.stream().map(destination -> new AdministrativeUnitDestination()
					.setAdministrativeUnit(administrativeUnit).setDestination(destination)).collect(Collectors.toList()));
			
			Collection<ActivityDestination> activityDestinations = administrativeUnit.getActivityDestinations().stream().filter(x -> x.getActivity()!= null && x.getDestination()!=null).collect(Collectors.toList());
			if(CollectionHelper.isNotEmpty(activityDestinations)) {
				for(ActivityDestination activityDestination : activityDestinations) {
					if(StringHelper.isBlank(activityDestination.getActivity().getIdentifier())) {
						System.out.println("AdministrativeUnitBusinessImpl.__listenExecuteCreateAfter__() CA : "+activityDestination.getActivity().getCode()+" "+activityDestination.getActivity().getName());
						__inject__(ActivityBusiness.class).create(activityDestination.getActivity().setCode(Activity.CODE_NEW_PREFIX+RandomHelper.getNumeric(10)));
					}
					if(StringHelper.isBlank(activityDestination.getDestination().getIdentifier())) {
						System.out.println("AdministrativeUnitBusinessImpl.__listenExecuteCreateAfter__() CD : "+activityDestination.getDestination().getCode()+" "+activityDestination.getDestination().getName());
						__inject__(DestinationBusiness.class).create(activityDestination.getDestination().setCode(Destination.CODE_NEW_PREFIX+RandomHelper.getNumeric(10)));
					}
				}
				__inject__(ActivityDestinationBusiness.class).createMany(activityDestinations);
			}
			*/
		}
		
		/*if(administrativeUnit.getParent() != null) {
			__inject__(AdministrativeUnitHierarchyBusiness.class).create(new AdministrativeUnitHierarchy().setParent(administrativeUnit.getParent()).setChild(administrativeUnit));	
		}*/
		
	}
	
	@Override
	protected void __listenExecuteUpdateBefore__(AdministrativeUnit administrativeUnit, Properties properties,BusinessFunctionModifier function) {
		super.__listenExecuteUpdateBefore__(administrativeUnit, properties, function);
		Strings fields = __getFieldsFromProperties__(properties);
		if(CollectionHelper.isEmpty(fields))
			return;		
		for(String index : fields.get()) {
			if(AdministrativeUnit.FIELD_ACTIVITIES.equals(index)) {
				Collection<AdministrativeUnitActivity> databaseAdministrativeUnitActivities = ((ReadAdministrativeUnitActivityByAdministrativeUnits)__inject__(AdministrativeUnitActivityPersistence.class)).readByAdministrativeUnits(administrativeUnit);
				Collection<Activity> databaseActivities = CollectionHelper.isEmpty(databaseAdministrativeUnitActivities) ? null : databaseAdministrativeUnitActivities.stream()
						.map(AdministrativeUnitActivity::getActivity).collect(Collectors.toList());
				
				__delete__(administrativeUnit.getActivities(), databaseAdministrativeUnitActivities,AdministrativeUnitActivity.FIELD_ACTIVITY);
				__save__(AdministrativeUnitActivity.class,administrativeUnit.getActivities(), databaseActivities, AdministrativeUnitActivity.FIELD_ACTIVITY, administrativeUnit, AdministrativeUnitActivity.FIELD_ADMINISTRATIVE_UNIT);
			}else if(AdministrativeUnit.FIELD_SERVICE_GROUP.equals(index) || AdministrativeUnit.FIELD_FUNCTIONAL_CLASSIFICATION.equals(index)) {
				administrativeUnit.setOrderNumber(-1);//need to be codify
				//AdministrativeUnit database = __persistence__.readBySystemIdentifier(administrativeUnit.getSystemIdentifier());
				//if(!database.getServiceGroup().equals(administrativeUnit.getServiceGroup()) || !database.getFunctionalClassification().equals(administrativeUnit.getFunctionalClassification())) {
				//	__setOrderNumberAndCode__(administrativeUnit);
				//}
				
			}else if(AdministrativeUnit.FIELD_DESTINATIONS.equals(index)) {
				Collection<AdministrativeUnitDestination> databaseAdministrativeUnitDestinations = ((ReadAdministrativeUnitDestinationByAdministrativeUnits)__inject__(AdministrativeUnitDestinationPersistence.class)).readByAdministrativeUnits(administrativeUnit);
				Collection<Destination> databaseDestinations = CollectionHelper.isEmpty(databaseAdministrativeUnitDestinations) ? null : databaseAdministrativeUnitDestinations.stream()
						.map(AdministrativeUnitDestination::getDestination).collect(Collectors.toList());
				
				__delete__(administrativeUnit.getDestinations(), databaseAdministrativeUnitDestinations,AdministrativeUnitDestination.FIELD_DESTINATION);
				__save__(AdministrativeUnitDestination.class,administrativeUnit.getDestinations(), databaseDestinations, AdministrativeUnitDestination.FIELD_DESTINATION, administrativeUnit, AdministrativeUnitDestination.FIELD_ADMINISTRATIVE_UNIT);
			}else if(AdministrativeUnit.FIELD_PARENT.equals(index)) {
				AdministrativeUnitHierarchy administrativeUnitHierarchy = CollectionHelper.getFirst(__inject__(AdministrativeUnitHierarchyPersistence.class).readWhereIsChildByChildren(administrativeUnit));
				if(administrativeUnitHierarchy == null) {
					if(administrativeUnit.getParent() != null)
						__inject__(AdministrativeUnitHierarchyBusiness.class).create(new AdministrativeUnitHierarchy().setParent(administrativeUnit.getParent()).setChild(administrativeUnit));
				}else {
					if(administrativeUnit.getParent() == null) {
						__inject__(AdministrativeUnitHierarchyBusiness.class).delete(administrativeUnitHierarchy);
					}else if(administrativeUnit.getParent().equals(administrativeUnit)) {
						throw new RuntimeException("une unité administrative ne peut pas être son propre parent.");
					}else {
						administrativeUnitHierarchy.setParent(administrativeUnit.getParent());
						__inject__(AdministrativeUnitHierarchyBusiness.class).update(administrativeUnitHierarchy);	
					}
				}
			}else if(AdministrativeUnit.FIELD_CHILDREN.equals(index)) {
				Collection<AdministrativeUnitHierarchy> administrativeUnitHierarchies = __inject__(AdministrativeUnitHierarchyPersistence.class).readWhereIsParentByParents(administrativeUnit);
				Collection<AdministrativeUnit> databaseChildren = CollectionHelper.isEmpty(administrativeUnitHierarchies) ? null : administrativeUnitHierarchies.stream()
						.map(AdministrativeUnitHierarchy::getChild).collect(Collectors.toList());
				
				if(CollectionHelper.isNotEmpty(databaseChildren)) {
					for(AdministrativeUnit child : databaseChildren) {
						if(!CollectionHelper.contains(administrativeUnit.getChildren(),child)) {
							for(AdministrativeUnitHierarchy administrativeUnitHierarchy : administrativeUnitHierarchies) {
								if(administrativeUnitHierarchy.getParent().equals(administrativeUnit) && administrativeUnitHierarchy.getChild().equals(child)) {
									__inject__(AdministrativeUnitHierarchyBusiness.class).delete(administrativeUnitHierarchy);
								}
							}
						}							
					}
				}
				
				if(CollectionHelper.isNotEmpty(administrativeUnit.getChildren())) {
					for(AdministrativeUnit child : administrativeUnit.getChildren()) {
						if(!CollectionHelper.contains(databaseChildren,child)) {
							__inject__(AdministrativeUnitHierarchyBusiness.class).create(new AdministrativeUnitHierarchy().setParent(administrativeUnit).setChild(child));
						}
					}
				}
			}
		}
	}
}
