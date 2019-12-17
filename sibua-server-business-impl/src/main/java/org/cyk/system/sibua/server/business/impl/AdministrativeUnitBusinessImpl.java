package org.cyk.system.sibua.server.business.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.sibua.server.business.api.AdministrativeUnitBusiness;
import org.cyk.system.sibua.server.persistence.api.AdministrativeUnitActivityPersistence;
import org.cyk.system.sibua.server.persistence.api.AdministrativeUnitPersistence;
import org.cyk.system.sibua.server.persistence.api.query.ReadAdministrativeUnitActivityByAdministrativeUnits;
import org.cyk.system.sibua.server.persistence.entities.Activity;
import org.cyk.system.sibua.server.persistence.entities.AdministrativeUnit;
import org.cyk.system.sibua.server.persistence.entities.AdministrativeUnitActivity;
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
			}
		}
	}
	
}
