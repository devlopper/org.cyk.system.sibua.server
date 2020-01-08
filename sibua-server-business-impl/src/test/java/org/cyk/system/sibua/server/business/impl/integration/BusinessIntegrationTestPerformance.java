package org.cyk.system.sibua.server.business.impl.integration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.cyk.system.sibua.server.business.api.AdministrativeUnitBusiness;
import org.cyk.system.sibua.server.business.api.FunctionalClassificationBusiness;
import org.cyk.system.sibua.server.business.api.LocalisationBusiness;
import org.cyk.system.sibua.server.business.api.SectionBusiness;
import org.cyk.system.sibua.server.business.api.ServiceGroupBusiness;
import org.cyk.system.sibua.server.persistence.entities.AdministrativeUnit;
import org.cyk.system.sibua.server.persistence.entities.FunctionalClassification;
import org.cyk.system.sibua.server.persistence.entities.Localisation;
import org.cyk.system.sibua.server.persistence.entities.Section;
import org.cyk.system.sibua.server.persistence.entities.ServiceGroup;
import org.cyk.utility.server.business.test.arquillian.AbstractBusinessArquillianIntegrationTestWithDefaultDeployment;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class BusinessIntegrationTestPerformance extends AbstractBusinessArquillianIntegrationTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;
	
	/* Create */
	
	@Test
	public void administrativeUnit_create_update_many_10() throws Exception{
		__assert__administrativeUnit_create_update_many(10, 1000l, 1000l);
	}
	
	@Test
	public void administrativeUnit_create_update_many_100() throws Exception{
		__assert__administrativeUnit_create_update_many(100, 1000l, 1000l);
	}
	
	@Test
	public void administrativeUnit_create_update_many_500() throws Exception{
		__assert__administrativeUnit_create_update_many(500, 1000l, 1000l);
	}
	
	@Test
	public void administrativeUnit_create_update_many_1000() throws Exception{
		__assert__administrativeUnit_create_update_many(1000, 1000l, 1000l);
	}
	
	//@Test
	public void administrativeUnit_create_update_many_10000() throws Exception{
		__assert__administrativeUnit_create_update_many(10000, 1000l, 1000l);
	}
	
	private void __assert__administrativeUnit_create_update_many(Integer numberOfAdministrativeUnits,Long expectedMaximumCreationDuration,Long expectedMaximumUpdateDuration) throws Exception{
		__inject__(SectionBusiness.class).createMany(List.of(new Section().setCode("1").setName("1")));
		__inject__(ServiceGroupBusiness.class).createMany(List.of(new ServiceGroup().setCode("1").setName("1"),new ServiceGroup().setCode("2").setName("2")));
		__inject__(FunctionalClassificationBusiness.class).createMany(List.of(new FunctionalClassification().setCode("1").setName("1"),new FunctionalClassification().setCode("2").setName("2")));
		__inject__(LocalisationBusiness.class).createMany(List.of(new Localisation().setCode("1").setName("1"),new Localisation().setCode("2").setName("2")));
		
		Collection<AdministrativeUnit> administrativeUnits = new ArrayList<>();
		for(Integer index = 0; index < numberOfAdministrativeUnits; index = index + 1)
			administrativeUnits.add(new AdministrativeUnit().setFunctionalClassificationFromCode("1").setServiceGroupFromCode("1").setLocalisationFromCode("1").setSectionFromCode("1").setName("1"));
		Long t = System.currentTimeMillis();
		__inject__(AdministrativeUnitBusiness.class).createMany(administrativeUnits);
		Long duration = System.currentTimeMillis() - t;
		System.out.println("Creation duration of "+numberOfAdministrativeUnits+" : "+duration);
		assertThat(duration).isLessThanOrEqualTo(expectedMaximumCreationDuration).as("creation duration of "+numberOfAdministrativeUnits);
		
		for(AdministrativeUnit administrativeUnit : administrativeUnits)
			administrativeUnit.setFunctionalClassificationFromCode("2").setServiceGroupFromCode("2").setLocalisationFromCode("2");
		t = System.currentTimeMillis();
		__inject__(AdministrativeUnitBusiness.class).updateMany(administrativeUnits);
		duration = System.currentTimeMillis() - t;
		System.out.println("Update duration of "+numberOfAdministrativeUnits+" : "+duration);
		assertThat(duration).isLessThanOrEqualTo(expectedMaximumCreationDuration).as("update duration of "+numberOfAdministrativeUnits);
	}
}
