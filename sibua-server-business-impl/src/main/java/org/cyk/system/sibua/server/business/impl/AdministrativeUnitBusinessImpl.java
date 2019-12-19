package org.cyk.system.sibua.server.business.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.sibua.server.business.api.AdministrativeUnitBusiness;
import org.cyk.system.sibua.server.business.api.AdministrativeUnitDestinationBusiness;
import org.cyk.system.sibua.server.business.api.AdministrativeUnitHierarchyBusiness;
import org.cyk.system.sibua.server.persistence.api.AdministrativeUnitActivityPersistence;
import org.cyk.system.sibua.server.persistence.api.AdministrativeUnitDestinationPersistence;
import org.cyk.system.sibua.server.persistence.api.AdministrativeUnitHierarchyPersistence;
import org.cyk.system.sibua.server.persistence.api.AdministrativeUnitPersistence;
import org.cyk.system.sibua.server.persistence.api.query.ReadAdministrativeUnitActivityByAdministrativeUnits;
import org.cyk.system.sibua.server.persistence.api.query.ReadAdministrativeUnitDestinationByAdministrativeUnits;
import org.cyk.system.sibua.server.persistence.entities.Activity;
import org.cyk.system.sibua.server.persistence.entities.AdministrativeUnit;
import org.cyk.system.sibua.server.persistence.entities.AdministrativeUnitActivity;
import org.cyk.system.sibua.server.persistence.entities.AdministrativeUnitDestination;
import org.cyk.system.sibua.server.persistence.entities.AdministrativeUnitHierarchy;
import org.cyk.system.sibua.server.persistence.entities.Destination;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.constant.ConstantCharacter;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.string.Strings;
import org.cyk.utility.server.business.AbstractBusinessEntityImpl;
import org.cyk.utility.server.business.BusinessFunctionCreator;
import org.cyk.utility.server.business.BusinessFunctionModifier;

@ApplicationScoped
public class AdministrativeUnitBusinessImpl extends AbstractBusinessEntityImpl<AdministrativeUnit, AdministrativeUnitPersistence> implements AdministrativeUnitBusiness,Serializable {
	private static final long serialVersionUID = 1L;

	private void __setOrderNumberAndCode__(AdministrativeUnit administrativeUnit) {
		administrativeUnit.setOrderNumber(__persistence__.readMaxOrderNumberByServiceGroupCodeByFunctionalClassificationCode(administrativeUnit.getServiceGroup().getCode()
				, administrativeUnit.getFunctionalClassification().getCode(), properties)+1);
		if(administrativeUnit.getServiceGroup() != null && administrativeUnit.getFunctionalClassification() != null && administrativeUnit.getLocalisation() != null
				&& administrativeUnit.getOrderNumber() != null)
			administrativeUnit.setCode(administrativeUnit.getServiceGroup().getCode()+administrativeUnit.getFunctionalClassification().getBusinessIdentifier()+
				StringUtils.leftPad(administrativeUnit.getOrderNumber().toString(), 5, ConstantCharacter.ZERO)+administrativeUnit.getLocalisation().getCode());
	}
	
	@Override
	protected void __listenExecuteCreateBefore__(AdministrativeUnit administrativeUnit, Properties properties,BusinessFunctionCreator function) {
		super.__listenExecuteCreateBefore__(administrativeUnit, properties, function);
		__setOrderNumberAndCode__(administrativeUnit);
	}
	
	@Override
	protected void __listenExecuteCreateAfter__(AdministrativeUnit administrativeUnit, Properties properties,BusinessFunctionCreator function) {
		super.__listenExecuteCreateAfter__(administrativeUnit, properties, function);		
		if(CollectionHelper.isNotEmpty(administrativeUnit.getDestinations())) {
			__inject__(AdministrativeUnitDestinationBusiness.class).createMany(administrativeUnit.getDestinations().stream()
					.map(x -> new AdministrativeUnitDestination().setAdministrativeUnit(administrativeUnit).setDestination(x)).collect(Collectors.toList()));	
		}
		if(administrativeUnit.getParent() != null) {
			__inject__(AdministrativeUnitHierarchyBusiness.class).create(new AdministrativeUnitHierarchy().setParent(administrativeUnit.getParent()).setChild(administrativeUnit));	
		}
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
				AdministrativeUnit database = __persistence__.readBySystemIdentifier(administrativeUnit.getSystemIdentifier());
				if(!database.getServiceGroup().equals(administrativeUnit.getServiceGroup()) || !database.getFunctionalClassification().equals(administrativeUnit.getFunctionalClassification())) {
					__setOrderNumberAndCode__(administrativeUnit);
				}
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
			}
		}
	}
	
}
