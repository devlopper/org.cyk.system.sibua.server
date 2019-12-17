package org.cyk.system.sibua.server.business.impl.integration;

import org.cyk.system.sibua.server.business.api.AdministrativeUnitBusiness;
import org.cyk.system.sibua.server.business.api.FunctionalClassificationBusiness;
import org.cyk.system.sibua.server.business.api.LocalisationBusiness;
import org.cyk.system.sibua.server.business.api.SectionBusiness;
import org.cyk.system.sibua.server.business.api.ServiceGroupBusiness;
import org.cyk.system.sibua.server.persistence.api.AdministrativeUnitPersistence;
import org.cyk.system.sibua.server.persistence.entities.AdministrativeUnit;
import org.cyk.system.sibua.server.persistence.entities.FunctionalClassification;
import org.cyk.system.sibua.server.persistence.entities.Localisation;
import org.cyk.system.sibua.server.persistence.entities.Section;
import org.cyk.system.sibua.server.persistence.entities.ServiceGroup;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.random.RandomHelper;
import org.cyk.utility.server.business.test.arquillian.AbstractBusinessArquillianIntegrationTestWithDefaultDeployment;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class BusinessIntegrationTest extends AbstractBusinessArquillianIntegrationTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;
	
	/* Create */
	
	@Test
	public void administrativeUnit_create_one() throws Exception{
		Section section = new Section().setCode(RandomHelper.getAlphabetic(4)).setName(RandomHelper.getAlphabetic(4));
		__inject__(SectionBusiness.class).create(section);
		ServiceGroup serviceGroup01 = new ServiceGroup().setCode(RandomHelper.getAlphabetic(4)).setName(RandomHelper.getAlphabetic(4));
		__inject__(ServiceGroupBusiness.class).create(serviceGroup01);
		FunctionalClassification functionalClassification01 = new FunctionalClassification().setCode(RandomHelper.getAlphabetic(4)).setName(RandomHelper.getAlphabetic(4));
		__inject__(FunctionalClassificationBusiness.class).create(functionalClassification01);
		Localisation localisation = new Localisation().setCode(RandomHelper.getAlphabetic(4)).setName(RandomHelper.getAlphabetic(4));
		__inject__(LocalisationBusiness.class).create(localisation);
		AdministrativeUnit administrativeUnit = new AdministrativeUnit().setFunctionalClassification(functionalClassification01)
				.setServiceGroup(serviceGroup01).setLocalisation(localisation).setSection(section).setName(RandomHelper.getAlphabetic(4));
		assertThat(administrativeUnit.getCode()).isNull();
		assertThat(administrativeUnit.getOrderNumber()).isNull();
		assertThat(__inject__(AdministrativeUnitPersistence.class).readMaxOrderNumberByServiceGroupCodeByFunctionalClassificationCode(serviceGroup01.getCode()
				, functionalClassification01.getCode(), null)).isEqualTo(0);
		__inject__(AdministrativeUnitBusiness.class).create(administrativeUnit);
		assertThat(administrativeUnit.getCode()).isEqualTo(serviceGroup01.getCode()+functionalClassification01.getCode()+"00001"+localisation.getCode());
		assertThat(administrativeUnit.getOrderNumber()).isEqualTo(1);
	}
	
	@Test
	public void administrativeUnit_create_two() throws Exception{
		Section section = new Section().setCode(RandomHelper.getAlphabetic(4)).setName(RandomHelper.getAlphabetic(4));
		__inject__(SectionBusiness.class).create(section);
		ServiceGroup serviceGroup01 = new ServiceGroup().setCode(RandomHelper.getAlphabetic(4)).setName(RandomHelper.getAlphabetic(4));
		__inject__(ServiceGroupBusiness.class).create(serviceGroup01);
		FunctionalClassification functionalClassification01 = new FunctionalClassification().setCode(RandomHelper.getAlphabetic(4)).setName(RandomHelper.getAlphabetic(4));
		__inject__(FunctionalClassificationBusiness.class).create(functionalClassification01);
		Localisation localisation = new Localisation().setCode(RandomHelper.getAlphabetic(4)).setName(RandomHelper.getAlphabetic(4));
		__inject__(LocalisationBusiness.class).create(localisation);
		AdministrativeUnit administrativeUnit = new AdministrativeUnit().setFunctionalClassification(functionalClassification01)
				.setServiceGroup(serviceGroup01).setLocalisation(localisation).setSection(section).setName(RandomHelper.getAlphabetic(4));
		assertThat(administrativeUnit.getCode()).isNull();
		assertThat(administrativeUnit.getOrderNumber()).isNull();
		assertThat(__inject__(AdministrativeUnitPersistence.class).readMaxOrderNumberByServiceGroupCodeByFunctionalClassificationCode(serviceGroup01.getCode()
				, functionalClassification01.getCode(), null)).isEqualTo(0);
		__inject__(AdministrativeUnitBusiness.class).create(administrativeUnit);
		assertThat(administrativeUnit.getCode()).isEqualTo(serviceGroup01.getCode()+functionalClassification01.getCode()+"00001"+localisation.getCode());
		assertThat(administrativeUnit.getOrderNumber()).isEqualTo(1);
		
		administrativeUnit = new AdministrativeUnit().setFunctionalClassification(functionalClassification01)
				.setServiceGroup(serviceGroup01).setLocalisation(localisation).setSection(section).setName(RandomHelper.getAlphabetic(4));
		assertThat(administrativeUnit.getCode()).isNull();
		assertThat(administrativeUnit.getOrderNumber()).isNull();
		assertThat(__inject__(AdministrativeUnitPersistence.class).readMaxOrderNumberByServiceGroupCodeByFunctionalClassificationCode(serviceGroup01.getCode()
				, functionalClassification01.getCode(), null)).isEqualTo(1);
		__inject__(AdministrativeUnitBusiness.class).create(administrativeUnit);
		assertThat(administrativeUnit.getCode()).isEqualTo(serviceGroup01.getCode()+functionalClassification01.getCode()+"00002"+localisation.getCode());
		assertThat(administrativeUnit.getOrderNumber()).isEqualTo(2);
	}
	
	@Test
	public void administrativeUnit_update_one() throws Exception{
		Section section = new Section().setCode(RandomHelper.getAlphabetic(4)).setName(RandomHelper.getAlphabetic(4));
		__inject__(SectionBusiness.class).create(section);
		ServiceGroup serviceGroup01 = new ServiceGroup().setCode(RandomHelper.getAlphabetic(4)).setName(RandomHelper.getAlphabetic(4));
		__inject__(ServiceGroupBusiness.class).create(serviceGroup01);
		ServiceGroup serviceGroup02 = new ServiceGroup().setCode(RandomHelper.getAlphabetic(4)).setName(RandomHelper.getAlphabetic(4));
		__inject__(ServiceGroupBusiness.class).create(serviceGroup02);
		FunctionalClassification functionalClassification01 = new FunctionalClassification().setCode(RandomHelper.getAlphabetic(4)).setName(RandomHelper.getAlphabetic(4));
		__inject__(FunctionalClassificationBusiness.class).create(functionalClassification01);
		Localisation localisation = new Localisation().setCode(RandomHelper.getAlphabetic(4)).setName(RandomHelper.getAlphabetic(4));
		__inject__(LocalisationBusiness.class).create(localisation);
		AdministrativeUnit administrativeUnit = new AdministrativeUnit().setFunctionalClassification(functionalClassification01)
				.setServiceGroup(serviceGroup01).setLocalisation(localisation).setSection(section).setName(RandomHelper.getAlphabetic(4));
		assertThat(administrativeUnit.getCode()).isNull();
		assertThat(administrativeUnit.getOrderNumber()).isNull();
		assertThat(__inject__(AdministrativeUnitPersistence.class).readMaxOrderNumberByServiceGroupCodeByFunctionalClassificationCode(serviceGroup01.getCode()
				, functionalClassification01.getCode(), null)).isEqualTo(0);
		__inject__(AdministrativeUnitBusiness.class).create(administrativeUnit);
		assertThat(administrativeUnit.getCode()).isEqualTo(serviceGroup01.getCode()+functionalClassification01.getCode()+"00001"+localisation.getCode());
		assertThat(administrativeUnit.getOrderNumber()).isEqualTo(1);
		
		administrativeUnit = __inject__(AdministrativeUnitBusiness.class).findBySystemIdentifier(administrativeUnit.getSystemIdentifier());
		administrativeUnit.setServiceGroup(serviceGroup02);
		__inject__(AdministrativeUnitBusiness.class).update(administrativeUnit,new Properties().setFields(AdministrativeUnit.FIELD_SERVICE_GROUP));
		administrativeUnit = __inject__(AdministrativeUnitBusiness.class).findBySystemIdentifier(administrativeUnit.getSystemIdentifier());
		assertThat(administrativeUnit.getCode()).isEqualTo(serviceGroup02.getCode()+functionalClassification01.getCode()+"00001"+localisation.getCode());
		assertThat(administrativeUnit.getOrderNumber()).isEqualTo(1);
		
	}
}
