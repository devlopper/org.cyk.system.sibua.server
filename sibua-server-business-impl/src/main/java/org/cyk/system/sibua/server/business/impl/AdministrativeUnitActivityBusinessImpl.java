package org.cyk.system.sibua.server.business.impl;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.business.api.AdministrativeUnitActivityBusiness;
import org.cyk.system.sibua.server.persistence.api.AdministrativeUnitActivityPersistence;
import org.cyk.system.sibua.server.persistence.entities.AdministrativeUnitActivity;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.server.business.AbstractBusinessEntityImpl;
import org.cyk.utility.server.business.BusinessFunctionCreator;
import org.cyk.utility.server.business.BusinessFunctionModifier;

@ApplicationScoped
public class AdministrativeUnitActivityBusinessImpl extends AbstractBusinessEntityImpl<AdministrativeUnitActivity, AdministrativeUnitActivityPersistence> implements AdministrativeUnitActivityBusiness,Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void __listenExecuteCreateBefore__(AdministrativeUnitActivity administrativeUnitActivity, Properties properties,BusinessFunctionCreator function) {
		super.__listenExecuteCreateBefore__(administrativeUnitActivity, properties, function);
		if(administrativeUnitActivity.getActivity() != null)
			administrativeUnitActivity.getActivity().setModificationDate(LocalDateTime.now());
	}
	
	@Override
	protected void __listenExecuteUpdateBefore__(AdministrativeUnitActivity administrativeUnitActivity, Properties properties,BusinessFunctionModifier function) {
		super.__listenExecuteUpdateBefore__(administrativeUnitActivity, properties, function);
		if(administrativeUnitActivity.getActivity() != null)
			administrativeUnitActivity.getActivity().setModificationDate(LocalDateTime.now());
	}
	
}
