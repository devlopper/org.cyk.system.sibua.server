package org.cyk.system.sibua.server.business.impl;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.business.api.AdministrativeUnitBusiness;
import org.cyk.system.sibua.server.business.api.AdministrativeUnitFromDestinationBusiness;
import org.cyk.system.sibua.server.persistence.api.AdministrativeUnitFromDestinationPersistence;
import org.cyk.system.sibua.server.persistence.entities.AdministrativeUnit;
import org.cyk.system.sibua.server.persistence.entities.AdministrativeUnitFromDestination;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.server.business.AbstractBusinessEntityImpl;
import org.cyk.utility.server.business.BusinessFunctionCreator;
import org.cyk.utility.server.business.BusinessFunctionModifier;

@ApplicationScoped
public class AdministrativeUnitFromDestinationBusinessImpl extends AbstractBusinessEntityImpl<AdministrativeUnitFromDestination, AdministrativeUnitFromDestinationPersistence> implements AdministrativeUnitFromDestinationBusiness,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenExecuteCreateBefore__(AdministrativeUnitFromDestination administrativeUnitFromDestination, Properties properties,BusinessFunctionCreator function) {
		super.__listenExecuteCreateBefore__(administrativeUnitFromDestination, properties, function);
		if(StringHelper.isBlank(administrativeUnitFromDestination.getCode()) && administrativeUnitFromDestination.getDestination() != null)
			administrativeUnitFromDestination.setCode(administrativeUnitFromDestination.getDestination().getCode());
		if(StringHelper.isBlank(administrativeUnitFromDestination.getName()) && administrativeUnitFromDestination.getDestination() != null)
			administrativeUnitFromDestination.setName(administrativeUnitFromDestination.getDestination().getName());
	}
	
	@Override
	protected void __listenExecuteUpdateAfter__(AdministrativeUnitFromDestination administrativeUnitFromDestination, Properties properties,BusinessFunctionModifier function) {
		super.__listenExecuteUpdateAfter__(administrativeUnitFromDestination, properties, function);
		if(administrativeUnitFromDestination.getServiceGroup() != null && administrativeUnitFromDestination.getFunctionalClassification() != null 
				&& administrativeUnitFromDestination.getLocalisation() != null) {
			__inject__(AdministrativeUnitBusiness.class).create(new AdministrativeUnit()
					.setName(administrativeUnitFromDestination.getName())
					.setSection(administrativeUnitFromDestination.getSection())
					.setServiceGroup(administrativeUnitFromDestination.getServiceGroup())
					.setFunctionalClassification(administrativeUnitFromDestination.getFunctionalClassification())
					.setLocalisation(administrativeUnitFromDestination.getLocalisation()));
			delete(administrativeUnitFromDestination);
		}
	}
}
