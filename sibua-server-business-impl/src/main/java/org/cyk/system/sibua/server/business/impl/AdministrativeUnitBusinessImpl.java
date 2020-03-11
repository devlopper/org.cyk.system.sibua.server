package org.cyk.system.sibua.server.business.impl;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.sibua.server.business.api.AdministrativeUnitActivityBusiness;
import org.cyk.system.sibua.server.business.api.AdministrativeUnitBusiness;
import org.cyk.system.sibua.server.business.api.AdministrativeUnitDestinationBusiness;
import org.cyk.system.sibua.server.business.api.AdministrativeUnitHierarchyBusiness;
import org.cyk.system.sibua.server.persistence.api.AdministrativeUnitActivityPersistence;
import org.cyk.system.sibua.server.persistence.api.AdministrativeUnitDestinationPersistence;
import org.cyk.system.sibua.server.persistence.api.AdministrativeUnitHierarchyPersistence;
import org.cyk.system.sibua.server.persistence.api.AdministrativeUnitPersistence;
import org.cyk.system.sibua.server.persistence.api.query.ReadAdministrativeUnitActivityByAdministrativeUnits;
import org.cyk.system.sibua.server.persistence.api.query.ReadAdministrativeUnitBySections;
import org.cyk.system.sibua.server.persistence.api.query.ReadAdministrativeUnitDestinationByAdministrativeUnits;
import org.cyk.system.sibua.server.persistence.entities.Activity;
import org.cyk.system.sibua.server.persistence.entities.AdministrativeUnit;
import org.cyk.system.sibua.server.persistence.entities.AdministrativeUnitActivity;
import org.cyk.system.sibua.server.persistence.entities.AdministrativeUnitDestination;
import org.cyk.system.sibua.server.persistence.entities.AdministrativeUnitHierarchy;
import org.cyk.system.sibua.server.persistence.entities.Destination;
import org.cyk.system.sibua.server.persistence.entities.FunctionalClassification;
import org.cyk.system.sibua.server.persistence.entities.ServiceGroup;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.constant.ConstantCharacter;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.random.RandomHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.string.Strings;
import org.cyk.utility.server.business.AbstractBusinessEntityImpl;
import org.cyk.utility.server.business.BusinessFunctionCreator;
import org.cyk.utility.server.business.BusinessFunctionModifier;
import org.cyk.utility.server.business.BusinessFunctionRemover;

@ApplicationScoped
public class AdministrativeUnitBusinessImpl extends AbstractBusinessEntityImpl<AdministrativeUnit, AdministrativeUnitPersistence> implements AdministrativeUnitBusiness,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public void mergeByCodes(Collection<String> administrativeUnitSourcesCodes,String administrativeUnitDestinationCode) {
		if(CollectionHelper.isEmpty(administrativeUnitSourcesCodes) || StringHelper.isBlank(administrativeUnitDestinationCode))
			return;
		//source and destination must be not joined
		Collection<String> temp = administrativeUnitSourcesCodes;
		administrativeUnitSourcesCodes = new ArrayList<String>();
		administrativeUnitSourcesCodes.addAll(temp);
		administrativeUnitSourcesCodes.remove(administrativeUnitDestinationCode);
		if(CollectionHelper.isEmpty(administrativeUnitSourcesCodes))
			return;
		//get source information
		Collection<AdministrativeUnit> administrativeUnitsSources = __inject__(AdministrativeUnitPersistence.class)
				.readByBusinessIdentifiers(CollectionHelper.cast(Object.class,administrativeUnitSourcesCodes));
		if(CollectionHelper.isEmpty(administrativeUnitsSources))
			return;
		//get destination
		AdministrativeUnit administrativeUnitDestination = __inject__(AdministrativeUnitPersistence.class).readByBusinessIdentifier(administrativeUnitDestinationCode);
		if(administrativeUnitDestination == null)
			return;
		//Collection<AdministrativeUnitActivity> administrativeUnitActivitiesSources = __inject__(AdministrativeUnitActivityPersistence.class)
		//		.readWhereIsGestionnaireOrBeneficiaireByAdministrativeUnitsCodes(administrativeUnitSourcesCodes, null);
		
		Collection<AdministrativeUnitActivity> administrativeUnitActivitiesSources = new HashSet<>();
		Collection<AdministrativeUnitActivity> __administrativeUnitActivitiesSources__ = ((ReadAdministrativeUnitActivityByAdministrativeUnits)__inject__(AdministrativeUnitActivityPersistence.class)).readByAdministrativeUnits(administrativeUnitsSources);
		if(CollectionHelper.isNotEmpty(__administrativeUnitActivitiesSources__))
			administrativeUnitActivitiesSources.addAll(__administrativeUnitActivitiesSources__);
		
		__administrativeUnitActivitiesSources__ = __inject__(AdministrativeUnitActivityPersistence.class).readByAdministrativeUnitBeneficiairesCodes(administrativeUnitSourcesCodes, null);
		if(CollectionHelper.isNotEmpty(__administrativeUnitActivitiesSources__))
			administrativeUnitActivitiesSources.addAll(__administrativeUnitActivitiesSources__);
		
		//update link
		//System.out.println(" 0001 LINKS : "+administrativeUnitSourcesCodes+" ::: "+administrativeUnitActivitiesSources);
		if(CollectionHelper.isNotEmpty(administrativeUnitActivitiesSources)) {
			//System.out.println(" 0002 LINKS : "+administrativeUnitActivitiesSources.size());
			for(AdministrativeUnitActivity administrativeUnitActivitySource : administrativeUnitActivitiesSources) {
				if(administrativeUnitsSources.contains(administrativeUnitActivitySource.getAdministrativeUnit())) {
					administrativeUnitActivitySource.setAdministrativeUnit(administrativeUnitDestination);
					//System.out.println("Gest updated");
				}
				if(administrativeUnitsSources.contains(administrativeUnitActivitySource.getAdministrativeUnitBeneficiaire())) {
					administrativeUnitActivitySource.setAdministrativeUnitBeneficiaire(administrativeUnitDestination);
					//System.out.println("Ben updated");
				}
			}
			__inject__(AdministrativeUnitActivityBusiness.class).updateMany(administrativeUnitActivitiesSources);
		}		
		__inject__(AdministrativeUnitBusiness.class).deleteMany(administrativeUnitsSources);
	}
	
	@Override
	public void generateCodesBySectionsCodes(Collection<String> codes) {
		if(CollectionHelper.isEmpty(codes))
			return;
		Collection<AdministrativeUnit> administrativeUnits = ((ReadAdministrativeUnitBySections)__inject__(AdministrativeUnitPersistence.class)).readBySectionsCodes(codes);
		if(CollectionHelper.isEmpty(administrativeUnits))
			return;
		Map<String,Integer> latestOrderNumberMap = new HashMap<>();
		Collection<AdministrativeUnit> administrativeUnitsWithCodeGenerated = null;
		for(AdministrativeUnit administrativeUnit : administrativeUnits) {
			if(administrativeUnit.getOrderNumber() == null || administrativeUnit.getOrderNumber() > 0)
				continue;
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
	
	protected void __setOrderNumberAndCode__(AdministrativeUnit administrativeUnit) {
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
		administrativeUnit.setCreationDate(LocalDateTime.now());
		administrativeUnit.setOrderNumber(__getNextOrderNumber__(administrativeUnit.getServiceGroup(), new HashMap<>()));
		if(StringHelper.isBlank(administrativeUnit.getCode()))
			administrativeUnit.setCode(__generateCode__(administrativeUnit));
	}
	
	@Override
	protected void __listenExecuteCreateAfter__(Collection<AdministrativeUnit> administrativeUnits, Properties properties,BusinessFunctionCreator function) {
		super.__listenExecuteCreateAfter__(administrativeUnits, properties, function);
	}
	
	@Override
	protected void __listenExecuteCreateAfter__(AdministrativeUnit administrativeUnit, Properties properties,BusinessFunctionCreator function) {
		super.__listenExecuteCreateAfter__(administrativeUnit, properties, function);
		
		if(CollectionHelper.isNotEmpty(administrativeUnit.getActivities())) {
			__inject__(AdministrativeUnitActivityBusiness.class).createMany(administrativeUnit.getActivities().stream()
					.map(x -> new AdministrativeUnitActivity(administrativeUnit,x).setAdministrativeUnitBeneficiaire(administrativeUnit)).collect(Collectors.toList()));
		}
		
		if(CollectionHelper.isNotEmpty(administrativeUnit.getActivityDestinations())) {
			//some new activity and destination might need to be created. find all new one having same code and make them using only one instance
			/*
			Collection<Activity> activitiesWhereIdentifierIsBlankAndCodeIsNotBlank = administrativeUnit.getActivityDestinations().stream()
					.map(ActivityDestination::getActivity)
					.filter(activity -> StringHelper.isBlank(activity.getIdentifier()) && StringHelper.isNotBlank(activity.getCode()))
					.collect(Collectors.toList());
			*/
			//__createWhereIdentifierIsBlankAndCodeIsNotBlank__(administrativeUnit, activitiesWhereIdentifierIsBlankAndCodeIsNotBlank, __inject__(ActivityBusiness.class), ActivityDestination.FIELD_ACTIVITY);
			/*
			Collection<Destination> destinationsWhereIdentifierIsBlankAndCodeIsNotBlank = administrativeUnit.getActivityDestinations().stream()
					.map(ActivityDestination::getDestination)
					.filter(destination -> StringHelper.isBlank(destination.getIdentifier()) && StringHelper.isNotBlank(destination.getCode()))
					.collect(Collectors.toList());
			*/
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
			//Collection<Activity> activities = administrativeUnit.getActivityDestinations().stream().map(ActivityDestination::getActivity).collect(Collectors.toList());
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
				//get delete able
				Collection<AdministrativeUnitActivity> databaseAdministrativeUnitActivitiesDeletable = null;
				if(CollectionHelper.isEmpty(administrativeUnit.getActivities())) {
					//delete all
					if(CollectionHelper.isNotEmpty(databaseAdministrativeUnitActivities)) {
						if(databaseAdministrativeUnitActivitiesDeletable == null)
							databaseAdministrativeUnitActivitiesDeletable = new ArrayList<>();
						databaseAdministrativeUnitActivitiesDeletable.addAll(databaseAdministrativeUnitActivities);	
					}					
				}else {
					if(CollectionHelper.isNotEmpty(databaseActivities)) {
						databaseActivities.removeAll(administrativeUnit.getActivities());
						if(databaseAdministrativeUnitActivitiesDeletable == null)
							databaseAdministrativeUnitActivitiesDeletable = new ArrayList<>();
						databaseAdministrativeUnitActivitiesDeletable.addAll(databaseAdministrativeUnitActivities.stream()
								.filter(x -> databaseActivities.contains(x.getActivity())).collect(Collectors.toList()));
					}
				}
				
				Collection<Activity> _databaseActivities_ = CollectionHelper.isEmpty(databaseAdministrativeUnitActivities) ? null : databaseAdministrativeUnitActivities.stream()
						.map(AdministrativeUnitActivity::getActivity).collect(Collectors.toList());
				
				if(CollectionHelper.isNotEmpty(databaseAdministrativeUnitActivitiesDeletable)) {
					__inject__(AdministrativeUnitActivityBusiness.class).deleteMany(databaseAdministrativeUnitActivitiesDeletable);
				}
				//get save able
				Collection<AdministrativeUnitActivity> administrativeUnitActivityCreatable = null;
				if(CollectionHelper.isEmpty(administrativeUnit.getActivities())) {
					//create nothing								
				}else {
					administrativeUnitActivityCreatable = administrativeUnit.getActivities().stream()
							.filter(x -> CollectionHelper.isEmpty(_databaseActivities_) || !_databaseActivities_.contains(x))
							.map(x -> new AdministrativeUnitActivity(administrativeUnit,x).setAdministrativeUnitBeneficiaire(x.getAdministrativeUnitBeneficiaire()))
							.collect(Collectors.toList());
					
				}
				if(CollectionHelper.isNotEmpty(administrativeUnitActivityCreatable)) {
					__inject__(AdministrativeUnitActivityBusiness.class).createMany(administrativeUnitActivityCreatable);
				}
				//System.out.println("AdministrativeUnitBusinessImpl.__listenExecuteUpdateBefore__() DB : "+CollectionHelper.getSize(databaseActivities));
				
				//__delete__(administrativeUnit.getActivities(), databaseAdministrativeUnitActivities,AdministrativeUnitActivity.FIELD_ACTIVITY);
				//System.out.println("AdministrativeUnitBusinessImpl.__listenExecuteUpdateBefore__() SAVE : "+CollectionHelper.getSize(administrativeUnit.getActivities()));
				//__save__(AdministrativeUnitActivity.class,administrativeUnit.getActivities(), databaseActivities, AdministrativeUnitActivity.FIELD_ACTIVITY, administrativeUnit, AdministrativeUnitActivity.FIELD_ADMINISTRATIVE_UNIT);
			}else if(AdministrativeUnit.FIELD_SERVICE_GROUP.equals(index)) {
				administrativeUnit.setOrderNumber(__getNextOrderNumber__(administrativeUnit.getServiceGroup(), new HashMap<>()));
				administrativeUnit.setCode(__generateCode__(administrativeUnit));
				
				//administrativeUnit.setOrderNumber(-1);//need to be codify
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
		administrativeUnit.setModificationDate(LocalDateTime.now());
	}

	@Override
	protected void __listenExecuteDeleteBefore__(AdministrativeUnit administrativeUnit, Properties properties,BusinessFunctionRemover function) {
		super.__listenExecuteDeleteBefore__(administrativeUnit, properties, function);
		
		Collection<AdministrativeUnitActivity> administrativeUnitActivitiesSources = new HashSet<>();
		Collection<AdministrativeUnitActivity> __administrativeUnitActivitiesSources__ = ((ReadAdministrativeUnitActivityByAdministrativeUnits)__inject__(AdministrativeUnitActivityPersistence.class)).readByAdministrativeUnits(administrativeUnit);
		if(CollectionHelper.isNotEmpty(__administrativeUnitActivitiesSources__))
			administrativeUnitActivitiesSources.addAll(__administrativeUnitActivitiesSources__);
		
		__administrativeUnitActivitiesSources__ = __inject__(AdministrativeUnitActivityPersistence.class).readByAdministrativeUnitBeneficiairesCodes(List.of(administrativeUnit.getCode()), null);
		if(CollectionHelper.isNotEmpty(__administrativeUnitActivitiesSources__))
			administrativeUnitActivitiesSources.addAll(__administrativeUnitActivitiesSources__);
		
		if(CollectionHelper.isNotEmpty(administrativeUnitActivitiesSources)) {
			throw new RuntimeException("L'unité administrative est rattachée à "+administrativeUnitActivitiesSources.size()+" activités. Veuillez dabord détacher les activités avant la suppression.");
		}
		
		Collection<AdministrativeUnitDestination> administrativeUnitDestinations =  ((ReadAdministrativeUnitDestinationByAdministrativeUnits) __inject__(AdministrativeUnitDestinationPersistence.class))
				.readByAdministrativeUnits(administrativeUnit);
		if(CollectionHelper.isNotEmpty(administrativeUnitDestinations))
			__inject__(AdministrativeUnitDestinationBusiness.class).deleteMany(administrativeUnitDestinations);
	}
	
	@Override
	protected Boolean __isCallDeleteByInstanceOnDeleteByIdentifier__() {
		return Boolean.TRUE;
	}
}
