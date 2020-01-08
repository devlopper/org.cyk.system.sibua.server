package org.cyk.system.sibua.server.business.impl.integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.cyk.system.sibua.server.business.api.ActivityBusiness;
import org.cyk.system.sibua.server.business.api.AdministrativeUnitBusiness;
import org.cyk.system.sibua.server.business.api.DestinationBusiness;
import org.cyk.system.sibua.server.business.api.FunctionalClassificationBusiness;
import org.cyk.system.sibua.server.business.api.LocalisationBusiness;
import org.cyk.system.sibua.server.business.api.SectionBusiness;
import org.cyk.system.sibua.server.business.api.ServiceGroupBusiness;
import org.cyk.system.sibua.server.persistence.api.ActivityPersistence;
import org.cyk.system.sibua.server.persistence.api.AdministrativeUnitActivityPersistence;
import org.cyk.system.sibua.server.persistence.api.AdministrativeUnitHierarchyPersistence;
import org.cyk.system.sibua.server.persistence.api.AdministrativeUnitPersistence;
import org.cyk.system.sibua.server.persistence.entities.Activity;
import org.cyk.system.sibua.server.persistence.entities.ActivityDestination;
import org.cyk.system.sibua.server.persistence.entities.AdministrativeUnit;
import org.cyk.system.sibua.server.persistence.entities.Destination;
import org.cyk.system.sibua.server.persistence.entities.FunctionalClassification;
import org.cyk.system.sibua.server.persistence.entities.Localisation;
import org.cyk.system.sibua.server.persistence.entities.Section;
import org.cyk.system.sibua.server.persistence.entities.ServiceGroup;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.random.RandomHelper;
import org.cyk.utility.server.business.test.arquillian.AbstractBusinessArquillianIntegrationTestWithDefaultDeployment;
import org.junit.Test;

public class BusinessIntegrationTest extends AbstractBusinessArquillianIntegrationTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;
	
	@Test
	public void administrativeUnit_generateCodesBySectionsCodes_section_one() throws Exception{
		__inject__(SectionBusiness.class).createMany(List.of(new Section().setCode("1").setName("1"),new Section().setCode("2").setName("1"),new Section().setCode("3").setName("1")));
		__inject__(ServiceGroupBusiness.class).createMany(List.of(new ServiceGroup().setCode("1").setName("1")));
		__inject__(FunctionalClassificationBusiness.class).createMany(List.of(new FunctionalClassification().setCode("1").setName("1")));
		__inject__(LocalisationBusiness.class).create(new Localisation().setCode("1").setName("1"));
		__inject__(AdministrativeUnitBusiness.class).createMany(List.of(
				new AdministrativeUnit("1","1","1","1","1","1"),new AdministrativeUnit("2","1","1","1","1","1"),new AdministrativeUnit("3","1","1","1","1","1")
				,new AdministrativeUnit("4","1","2","1","1","1"),new AdministrativeUnit("5","1","2","1","1","1"),new AdministrativeUnit("6","1","2","1","1","1")
				,new AdministrativeUnit("7","1","3","1","1","1"),new AdministrativeUnit("8","1","3","1","1","1"),new AdministrativeUnit("9","1","3","1","1","1")
				));
		AdministrativeUnit administrativeUnit = __inject__(AdministrativeUnitPersistence.class).readByBusinessIdentifier("1");
		assertThat(administrativeUnit).isNotNull();
		assertThat(administrativeUnit.getOrderNumber()).isEqualTo(-1);
		administrativeUnit = __inject__(AdministrativeUnitPersistence.class).readByBusinessIdentifier("2");
		assertThat(administrativeUnit).isNotNull();
		assertThat(administrativeUnit.getOrderNumber()).isEqualTo(-1);
		administrativeUnit = __inject__(AdministrativeUnitPersistence.class).readByBusinessIdentifier("3");
		assertThat(administrativeUnit).isNotNull();
		assertThat(administrativeUnit.getOrderNumber()).isEqualTo(-1);
		
		__inject__(AdministrativeUnitBusiness.class).generateCodesBySectionsCodes("1");
		
		Collection<AdministrativeUnit> administrativeUnits = __inject__(AdministrativeUnitPersistence.class).readByServiceGroupCodeByFunctionalClassificationCode("1", "1");
		assertThat(administrativeUnits).isNotEmpty();
		assertThat(administrativeUnits.stream().map(AdministrativeUnit::getOrderNumber)).containsExactly(1,2,3,-1,-1,-1,-1,-1,-1);
		assertThat(administrativeUnits.stream().map(AdministrativeUnit::getCode)).containsExactly("100011","100021","100031","4","5","6","7","8","9");
		
		__inject__(AdministrativeUnitBusiness.class).generateCodesBySectionsCodes("3");
		
		administrativeUnits = __inject__(AdministrativeUnitPersistence.class).readByServiceGroupCodeByFunctionalClassificationCode("1", "1");
		assertThat(administrativeUnits).isNotEmpty();
		assertThat(administrativeUnits.stream().map(AdministrativeUnit::getOrderNumber)).containsExactly(1,2,3,4,5,6,-1,-1,-1);
		assertThat(administrativeUnits.stream().map(AdministrativeUnit::getCode)).containsExactly("100011","100021","100031","100041","100051","100061","4","5","6");
		
		__inject__(AdministrativeUnitBusiness.class).generateCodesBySectionsCodes("3");
		
		administrativeUnits = __inject__(AdministrativeUnitPersistence.class).readByServiceGroupCodeByFunctionalClassificationCode("1", "1");
		assertThat(administrativeUnits).isNotEmpty();
		assertThat(administrativeUnits.stream().map(AdministrativeUnit::getOrderNumber)).containsExactly(1,2,3,4,5,6,-1,-1,-1);
		assertThat(administrativeUnits.stream().map(AdministrativeUnit::getCode)).containsExactly("100011","100021","100031","100041","100051","100061","4","5","6");
		
		__inject__(AdministrativeUnitBusiness.class).generateCodesBySectionsCodes("2");
		
		administrativeUnits = __inject__(AdministrativeUnitPersistence.class).readByServiceGroupCodeByFunctionalClassificationCode("1", "1");
		assertThat(administrativeUnits).isNotEmpty();
		assertThat(administrativeUnits.stream().map(AdministrativeUnit::getOrderNumber)).containsExactly(1,2,3,4,5,6,7,8,9);
		assertThat(administrativeUnits.stream().map(AdministrativeUnit::getCode)).containsExactly("100011","100021","100031","100041","100051","100061","100071","100081","100091");
	}
	
	@Test
	public void administrativeUnit_generateCodesBySectionsCodes_section_many() throws Exception{
		__inject__(SectionBusiness.class).createMany(List.of(new Section().setCode("1").setName("1"),new Section().setCode("2").setName("1"),new Section().setCode("3").setName("1")));
		__inject__(ServiceGroupBusiness.class).createMany(List.of(new ServiceGroup().setCode("1").setName("1")));
		__inject__(FunctionalClassificationBusiness.class).createMany(List.of(new FunctionalClassification().setCode("1").setName("1")));
		__inject__(LocalisationBusiness.class).create(new Localisation().setCode("1").setName("1"));
		__inject__(AdministrativeUnitBusiness.class).createMany(List.of(
				new AdministrativeUnit("1","1","1","1","1","1"),new AdministrativeUnit("2","1","1","1","1","1"),new AdministrativeUnit("3","1","1","1","1","1")
				,new AdministrativeUnit("4","1","2","1","1","1"),new AdministrativeUnit("5","1","2","1","1","1"),new AdministrativeUnit("6","1","2","1","1","1")
				,new AdministrativeUnit("7","1","3","1","1","1"),new AdministrativeUnit("8","1","3","1","1","1"),new AdministrativeUnit("9","1","3","1","1","1")
				));
		AdministrativeUnit administrativeUnit = __inject__(AdministrativeUnitPersistence.class).readByBusinessIdentifier("1");
		assertThat(administrativeUnit).isNotNull();
		assertThat(administrativeUnit.getOrderNumber()).isEqualTo(-1);
		administrativeUnit = __inject__(AdministrativeUnitPersistence.class).readByBusinessIdentifier("2");
		assertThat(administrativeUnit).isNotNull();
		assertThat(administrativeUnit.getOrderNumber()).isEqualTo(-1);
		administrativeUnit = __inject__(AdministrativeUnitPersistence.class).readByBusinessIdentifier("3");
		assertThat(administrativeUnit).isNotNull();
		assertThat(administrativeUnit.getOrderNumber()).isEqualTo(-1);
		
		__inject__(AdministrativeUnitBusiness.class).generateCodesBySectionsCodes("1","2","3");
		
		Collection<AdministrativeUnit> administrativeUnits = __inject__(AdministrativeUnitPersistence.class).readByServiceGroupCodeByFunctionalClassificationCode("1", "1");
		assertThat(administrativeUnits).isNotEmpty();
		assertThat(administrativeUnits.stream().map(AdministrativeUnit::getOrderNumber)).containsExactly(1,2,3,4,5,6,7,8,9);
		assertThat(administrativeUnits.stream().map(AdministrativeUnit::getCode)).containsExactly("100011","100021","100031","100041","100051","100061","100071","100081","100091");
		
		__inject__(AdministrativeUnitBusiness.class).generateCodesBySectionsCodes("1","2","3");
		
		administrativeUnits = __inject__(AdministrativeUnitPersistence.class).readByServiceGroupCodeByFunctionalClassificationCode("1", "1");
		assertThat(administrativeUnits).isNotEmpty();
		assertThat(administrativeUnits.stream().map(AdministrativeUnit::getOrderNumber)).containsExactly(1,2,3,4,5,6,7,8,9);
		assertThat(administrativeUnits.stream().map(AdministrativeUnit::getCode)).containsExactly("100011","100021","100031","100041","100051","100061","100071","100081","100091");
	}
	
	@Test
	public void administrativeUnit_update_activities() throws Exception{
		__inject__(SectionBusiness.class).createMany(List.of(new Section().setCode("1").setName("1")));
		__inject__(ServiceGroupBusiness.class).createMany(List.of(new ServiceGroup().setCode("1").setName("1")));
		__inject__(FunctionalClassificationBusiness.class).createMany(List.of(new FunctionalClassification().setCode("1").setName("1")));
		__inject__(LocalisationBusiness.class).create(new Localisation().setCode("1").setName("1"));
		__inject__(AdministrativeUnitBusiness.class).createMany(List.of(
				new AdministrativeUnit("1","1","1","1","1","1"),new AdministrativeUnit("2","1","1","1","1","1"),new AdministrativeUnit("3","1","1","1","1","1")
				));
		__inject__(ActivityBusiness.class).createMany(List.of(new Activity("1","1"),new Activity("2","1"),new Activity("3","1"),new Activity("4","1"),new Activity("5","1")));
		AdministrativeUnit administrativeUnit = __inject__(AdministrativeUnitBusiness.class).findByBusinessIdentifier("1",new Properties().setFields(AdministrativeUnit.FIELD_ACTIVITIES));
		assertThat(administrativeUnit.getActivities()).isEmpty();
		administrativeUnit.addActivitiesByCodes("2");
		assertThat(__inject__(AdministrativeUnitActivityPersistence.class).count()).isEqualTo(0l);
		__inject__(AdministrativeUnitBusiness.class).update(administrativeUnit, new Properties().setFields(AdministrativeUnit.FIELD_ACTIVITIES));
		assertThat(__inject__(AdministrativeUnitActivityPersistence.class).count()).isEqualTo(1l);
		administrativeUnit = __inject__(AdministrativeUnitBusiness.class).findByBusinessIdentifier("1",new Properties().setFields(AdministrativeUnit.FIELD_ACTIVITIES));
		assertThat(administrativeUnit.getActivities()).isNotEmpty();
		assertThat(administrativeUnit.getActivities().stream().map(Activity::getCode).collect(Collectors.toList())).containsExactlyInAnyOrder("2");
		
		administrativeUnit.addActivitiesByCodes("1");
		__inject__(AdministrativeUnitBusiness.class).update(administrativeUnit, new Properties().setFields(AdministrativeUnit.FIELD_ACTIVITIES));
		administrativeUnit = __inject__(AdministrativeUnitBusiness.class).findByBusinessIdentifier("1",new Properties().setFields(AdministrativeUnit.FIELD_ACTIVITIES));
		assertThat(administrativeUnit.getActivities()).isNotEmpty();
		assertThat(administrativeUnit.getActivities().stream().map(Activity::getCode).collect(Collectors.toList())).containsExactlyInAnyOrder("1","2");
		
		administrativeUnit.setActivities(null);
		__inject__(AdministrativeUnitBusiness.class).update(administrativeUnit, new Properties().setFields(AdministrativeUnit.FIELD_ACTIVITIES));
		administrativeUnit = __inject__(AdministrativeUnitBusiness.class).findByBusinessIdentifier("1",new Properties().setFields(AdministrativeUnit.FIELD_ACTIVITIES));
		assertThat(administrativeUnit.getActivities()).isEmpty();
		
		administrativeUnit.addActivitiesByCodes("1","2","3");
		__inject__(AdministrativeUnitBusiness.class).update(administrativeUnit, new Properties().setFields(AdministrativeUnit.FIELD_ACTIVITIES));
		administrativeUnit = __inject__(AdministrativeUnitBusiness.class).findByBusinessIdentifier("1",new Properties().setFields(AdministrativeUnit.FIELD_ACTIVITIES));
		assertThat(administrativeUnit.getActivities().stream().map(Activity::getCode).collect(Collectors.toList())).containsExactlyInAnyOrder("1","2","3");
	}
	
	@Test
	public void administrativeUnit_readChildren() {
		__inject__(ServiceGroupBusiness.class).create(new ServiceGroup().setCode("1").setName("1"));
		__inject__(FunctionalClassificationBusiness.class).create(new FunctionalClassification().setCode("1").setName("1"));
		__inject__(LocalisationBusiness.class).create(new Localisation().setCode("1").setName("1"));		
		__inject__(SectionBusiness.class).create(new Section().setCode("1").setName("1"));
		
		__inject__(AdministrativeUnitBusiness.class).createMany(List.of(
				new AdministrativeUnit("1","1","1","1","1","1").setOrderNumber(1),new AdministrativeUnit("2","1","1","1","1","1").setOrderNumber(2)
				,new AdministrativeUnit("3","1","1","1","1","1").setOrderNumber(3),new AdministrativeUnit("4","1","1","1","1","1").setOrderNumber(4)
				,new AdministrativeUnit("5","1","1","1","1","1").setOrderNumber(5),new AdministrativeUnit("6","1","1","1","1","1").setOrderNumber(6)
				));
		
		AdministrativeUnit administrativeUnit = __inject__(AdministrativeUnitBusiness.class).findByBusinessIdentifier("1",new Properties().setFields(AdministrativeUnit.FIELD_CHILDREN));
		assertThat(administrativeUnit.getChildren()).isEmpty();
		
		administrativeUnit.setChildren(List.of(
				__inject__(AdministrativeUnitBusiness.class).findByBusinessIdentifier("2"),__inject__(AdministrativeUnitBusiness.class).findByBusinessIdentifier("3")
				,__inject__(AdministrativeUnitBusiness.class).findByBusinessIdentifier("4")
				));
		__inject__(AdministrativeUnitBusiness.class).update(administrativeUnit,new Properties().setFields(AdministrativeUnit.FIELD_CHILDREN));
		
		administrativeUnit = __inject__(AdministrativeUnitBusiness.class).findByBusinessIdentifier("1",new Properties().setFields(AdministrativeUnit.FIELD_CHILDREN));
		assertThat(administrativeUnit.getChildren()).isNotEmpty();
		assertThat(administrativeUnit.getChildren().stream().map(AdministrativeUnit::getCode).collect(Collectors.toList())).contains("2","3","4");
		
		administrativeUnit.setChildren(List.of(
				__inject__(AdministrativeUnitBusiness.class).findByBusinessIdentifier("5"),__inject__(AdministrativeUnitBusiness.class).findByBusinessIdentifier("4")
				));
		__inject__(AdministrativeUnitBusiness.class).update(administrativeUnit,new Properties().setFields(AdministrativeUnit.FIELD_CHILDREN));
		
		administrativeUnit = __inject__(AdministrativeUnitBusiness.class).findByBusinessIdentifier("1",new Properties().setFields(AdministrativeUnit.FIELD_CHILDREN));
		assertThat(administrativeUnit.getChildren()).isNotEmpty();
		assertThat(administrativeUnit.getChildren().stream().map(AdministrativeUnit::getCode).collect(Collectors.toList())).contains("4","5");
		
		administrativeUnit.setChildren(null);
		__inject__(AdministrativeUnitBusiness.class).update(administrativeUnit,new Properties().setFields(AdministrativeUnit.FIELD_CHILDREN));
		administrativeUnit = __inject__(AdministrativeUnitBusiness.class).findByBusinessIdentifier("1",new Properties().setFields(AdministrativeUnit.FIELD_CHILDREN));
		assertThat(administrativeUnit.getChildren()).isEmpty();
	}
	
	/* Create */
	
	//@Test
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
		assertThat(administrativeUnit.getCode()).isEqualTo(serviceGroup01.getCode()+functionalClassification01.getCode()+"00001");
		assertThat(administrativeUnit.getOrderNumber()).isEqualTo(1);
	}
	
	//@Test
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
		assertThat(administrativeUnit.getCode()).isEqualTo(serviceGroup01.getCode()+functionalClassification01.getCode()+"00001");
		assertThat(administrativeUnit.getOrderNumber()).isEqualTo(1);
		
		administrativeUnit = new AdministrativeUnit().setFunctionalClassification(functionalClassification01)
				.setServiceGroup(serviceGroup01).setLocalisation(localisation).setSection(section).setName(RandomHelper.getAlphabetic(4));
		assertThat(administrativeUnit.getCode()).isNull();
		assertThat(administrativeUnit.getOrderNumber()).isNull();
		assertThat(__inject__(AdministrativeUnitPersistence.class).readMaxOrderNumberByServiceGroupCodeByFunctionalClassificationCode(serviceGroup01.getCode()
				, functionalClassification01.getCode(), null)).isEqualTo(1);
		__inject__(AdministrativeUnitBusiness.class).create(administrativeUnit);
		assertThat(administrativeUnit.getCode()).isEqualTo(serviceGroup01.getCode()+functionalClassification01.getCode()+"00002");
		assertThat(administrativeUnit.getOrderNumber()).isEqualTo(2);
	}
	
	//@Test
	public void administrativeUnit_create_many() throws Exception{
		__inject__(SectionBusiness.class).create(new Section().setCode("1").setName("1"));
		__inject__(ServiceGroupBusiness.class).create(new ServiceGroup().setCode("1").setName("1"));
		__inject__(FunctionalClassificationBusiness.class).create(new FunctionalClassification().setCode("1").setName("1"));
		__inject__(LocalisationBusiness.class).create(new Localisation().setCode("1").setName("1"));
		
		__inject__(AdministrativeUnitBusiness.class).createMany(List.of(
				new AdministrativeUnit().setFunctionalClassificationFromCode("1").setServiceGroupFromCode("1").setLocalisationFromCode("1").setSectionFromCode("1").setName("1")
				,new AdministrativeUnit().setFunctionalClassificationFromCode("1").setServiceGroupFromCode("1").setLocalisationFromCode("1").setSectionFromCode("1").setName("2")
				));
		
	}
	
	//@Test
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
		assertThat(administrativeUnit.getCode()).isEqualTo(serviceGroup01.getCode()+functionalClassification01.getCode()+"00001");
		assertThat(administrativeUnit.getOrderNumber()).isEqualTo(1);
		
		administrativeUnit = __inject__(AdministrativeUnitBusiness.class).findBySystemIdentifier(administrativeUnit.getSystemIdentifier());
		administrativeUnit.setServiceGroup(serviceGroup02);
		__inject__(AdministrativeUnitBusiness.class).update(administrativeUnit,new Properties().setFields(AdministrativeUnit.FIELD_SERVICE_GROUP));
		administrativeUnit = __inject__(AdministrativeUnitBusiness.class).findBySystemIdentifier(administrativeUnit.getSystemIdentifier());
		assertThat(administrativeUnit.getCode()).isEqualTo(serviceGroup02.getCode()+functionalClassification01.getCode()+"00001");
		assertThat(administrativeUnit.getOrderNumber()).isEqualTo(1);	
	}
	
	//@Test
	public void administrativeUnit_create_one_with_activity_destination() throws Exception{
		__inject__(SectionBusiness.class).create(new Section().setCode("1").setName("1"));
		__inject__(ServiceGroupBusiness.class).create(new ServiceGroup().setCode("1").setName("1"));		
		__inject__(FunctionalClassificationBusiness.class).create(new FunctionalClassification().setCode("1").setName("1"));
		__inject__(LocalisationBusiness.class).create(new Localisation().setCode("1").setName("1"));
		__inject__(ActivityBusiness.class).create(new Activity().setCode("0").setName("0"));
		__inject__(DestinationBusiness.class).create(new Destination().setCode("0").setName("0").setSectionFromCode("1"));
		
		AdministrativeUnit administrativeUnit = new AdministrativeUnit().setFunctionalClassificationFromCode("1")
				.setServiceGroupFromCode("1").setLocalisationFromCode("1").setSectionFromCode("1").setName("1");
		administrativeUnit.setActivityDestinations(List.of(
				new ActivityDestination().setActivity(new Activity().setName("1")).setDestination(new Destination().setName("1"))
				,new ActivityDestination().setActivity(new Activity().setCode("2").setName("2")).setDestination(new Destination().setCode("0").setName("2"))
				,new ActivityDestination().setActivity(new Activity().setCode("2").setName("2")).setDestination(new Destination().setCode("0").setName("2"))
				));
		try {
			__inject__(AdministrativeUnitBusiness.class).create(administrativeUnit);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertThat(__inject__(ActivityPersistence.class).count()).isEqualTo(3l);
		assertThat(__inject__(ActivityPersistence.class).read().stream().map(Activity::getCode).collect(Collectors.toList())).contains("0","2");
		//assertThat(__inject__(DestinationPersistence.class).count()).isEqualTo(2l);
		//assertThat(__inject__(DestinationPersistence.class).read().stream().map(Destination::getCode).collect(Collectors.toList())).contains("2");
	}
	
	//@Test
	public void administrativeUnit_create_one_withParent() throws Exception{
		__inject__(SectionBusiness.class).create(new Section().setCode("1").setName(RandomHelper.getAlphabetic(4)));
		__inject__(ServiceGroupBusiness.class).create(new ServiceGroup().setCode("1").setName(RandomHelper.getAlphabetic(4)));
		__inject__(FunctionalClassificationBusiness.class).create(new FunctionalClassification().setCode("1").setName(RandomHelper.getAlphabetic(4)));
		__inject__(LocalisationBusiness.class).create(new Localisation().setCode("1").setName(RandomHelper.getAlphabetic(4)));
		__inject__(AdministrativeUnitBusiness.class).create(new AdministrativeUnit().setFunctionalClassificationFromCode("1")
				.setServiceGroupFromCode("1").setLocalisationFromCode("1").setSectionFromCode("1").setName("1"));
		assertThat(__inject__(AdministrativeUnitBusiness.class).findByBusinessIdentifier("11000011",new Properties().setFields(AdministrativeUnit.FIELD_PARENT)).getParent()).isNull();
		__inject__(AdministrativeUnitBusiness.class).create(new AdministrativeUnit().setFunctionalClassificationFromCode("1")
				.setServiceGroupFromCode("1").setLocalisationFromCode("1").setSectionFromCode("1").setName("1").setParentFromCode("11000011"));
		AdministrativeUnit administrativeUnitParent = __inject__(AdministrativeUnitBusiness.class).findByBusinessIdentifier("11000011"
				,new Properties().setFields(AdministrativeUnit.FIELD_PARENT));
		assertThat(administrativeUnitParent.getParent()).isNull();
		AdministrativeUnit administrativeUnitChild = __inject__(AdministrativeUnitBusiness.class).findByBusinessIdentifier("11000021"
				,new Properties().setFields(AdministrativeUnit.FIELD_PARENT));
		assertThat(administrativeUnitChild.getParent()).isNotNull();
	}
	
	//@Test
	public void administrativeUnit_update_one_withParent() throws Exception{
		__inject__(SectionBusiness.class).create(new Section().setCode("1").setName(RandomHelper.getAlphabetic(4)));
		__inject__(ServiceGroupBusiness.class).create(new ServiceGroup().setCode("1").setName(RandomHelper.getAlphabetic(4)));
		__inject__(FunctionalClassificationBusiness.class).create(new FunctionalClassification().setCode("1").setName(RandomHelper.getAlphabetic(4)));
		__inject__(LocalisationBusiness.class).create(new Localisation().setCode("1").setName(RandomHelper.getAlphabetic(4)));
		__inject__(AdministrativeUnitBusiness.class).create(new AdministrativeUnit().setFunctionalClassificationFromCode("1")
				.setServiceGroupFromCode("1").setLocalisationFromCode("1").setSectionFromCode("1").setName("1"));
		assertThat(__inject__(AdministrativeUnitHierarchyPersistence.class).read()).isEmpty();
		assertThat(__inject__(AdministrativeUnitBusiness.class).findByBusinessIdentifier("11000011",new Properties().setFields(AdministrativeUnit.FIELD_PARENT)).getParent()).isNull();		
		__inject__(AdministrativeUnitBusiness.class).create(new AdministrativeUnit().setFunctionalClassificationFromCode("1")
				.setServiceGroupFromCode("1").setLocalisationFromCode("1").setSectionFromCode("1").setName("1").setParentFromCode("11000011"));
		assertThat(__inject__(AdministrativeUnitHierarchyPersistence.class).read().stream().map(x-> x.getParent().getCode()).collect(Collectors.toList())).containsExactly("11000011");
		assertThat(__inject__(AdministrativeUnitBusiness.class).findByBusinessIdentifier("11000021",new Properties().setFields(AdministrativeUnit.FIELD_PARENT)).getParent().getCode()).isEqualTo("11000011");	
		__inject__(AdministrativeUnitBusiness.class).create(new AdministrativeUnit().setFunctionalClassificationFromCode("1").setServiceGroupFromCode("1")
				.setLocalisationFromCode("1").setSectionFromCode("1").setName("1"));
		assertThat(__inject__(AdministrativeUnitHierarchyPersistence.class).read().stream().map(x-> x.getParent().getCode()).collect(Collectors.toList())).containsExactly("11000011");
		__inject__(AdministrativeUnitBusiness.class).update(__inject__(AdministrativeUnitBusiness.class)
				.findByBusinessIdentifier("11000021",new Properties().setFields(AdministrativeUnit.FIELD_PARENT)).setParentFromCode("11000031")
				,new Properties().setFields(AdministrativeUnit.FIELD_PARENT));
		assertThat(__inject__(AdministrativeUnitHierarchyPersistence.class).read().stream().map(x-> x.getParent().getCode()).collect(Collectors.toList())).containsExactly("11000031");
		assertThat(__inject__(AdministrativeUnitBusiness.class).findByBusinessIdentifier("11000021",new Properties().setFields(AdministrativeUnit.FIELD_PARENT)).getParent().getCode()).isEqualTo("11000031");
		
		__inject__(AdministrativeUnitBusiness.class).update(__inject__(AdministrativeUnitBusiness.class).findByBusinessIdentifier("11000021",new Properties().setFields(AdministrativeUnit.FIELD_PARENT)).setParent(null)
				,new Properties().setFields(AdministrativeUnit.FIELD_PARENT));
		
		assertThat(__inject__(AdministrativeUnitBusiness.class).findByBusinessIdentifier("11000021",new Properties().setFields(AdministrativeUnit.FIELD_PARENT)).getParent()).isNull();
	}
}
