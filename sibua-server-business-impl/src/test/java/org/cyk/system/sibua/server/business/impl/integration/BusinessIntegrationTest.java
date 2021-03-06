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
import org.cyk.system.sibua.server.business.api.user.FunctionBusiness;
import org.cyk.system.sibua.server.business.api.user.FunctionCategoryBusiness;
import org.cyk.system.sibua.server.business.api.user.FunctionTypeBusiness;
import org.cyk.system.sibua.server.business.api.user.UserBusiness;
import org.cyk.system.sibua.server.persistence.api.ActivityPersistence;
import org.cyk.system.sibua.server.persistence.api.AdministrativeUnitActivityPersistence;
import org.cyk.system.sibua.server.persistence.api.AdministrativeUnitHierarchyPersistence;
import org.cyk.system.sibua.server.persistence.api.AdministrativeUnitPersistence;
import org.cyk.system.sibua.server.persistence.api.LocalisationPersistence;
import org.cyk.system.sibua.server.persistence.api.SectionPersistence;
import org.cyk.system.sibua.server.persistence.api.user.FilePersistence;
import org.cyk.system.sibua.server.persistence.api.user.FunctionPersistence;
import org.cyk.system.sibua.server.persistence.entities.Activity;
import org.cyk.system.sibua.server.persistence.entities.ActivityDestination;
import org.cyk.system.sibua.server.persistence.entities.AdministrativeUnit;
import org.cyk.system.sibua.server.persistence.entities.Destination;
import org.cyk.system.sibua.server.persistence.entities.FunctionalClassification;
import org.cyk.system.sibua.server.persistence.entities.Localisation;
import org.cyk.system.sibua.server.persistence.entities.Section;
import org.cyk.system.sibua.server.persistence.entities.ServiceGroup;
import org.cyk.system.sibua.server.persistence.entities.user.File;
import org.cyk.system.sibua.server.persistence.entities.user.Function;
import org.cyk.system.sibua.server.persistence.entities.user.FunctionCategory;
import org.cyk.system.sibua.server.persistence.entities.user.FunctionType;
import org.cyk.system.sibua.server.persistence.entities.user.User;
import org.cyk.system.sibua.server.persistence.entities.user.UserFileType;
import org.cyk.system.sibua.server.persistence.impl.ApplicationScopeLifeCycleListener;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.random.RandomHelper;
import org.cyk.utility.server.business.test.arquillian.AbstractBusinessArquillianIntegrationTestWithDefaultDeployment;
import org.junit.Test;

public class BusinessIntegrationTest extends AbstractBusinessArquillianIntegrationTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void __listenBefore__() {
		super.__listenBefore__();
		
	}
	
	@Override
	protected void __listenAfter__() {
		super.__listenAfter__();
		
	}
	
	//@Test
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
	public void administrativeUnit_generateCodesBySectionsCodes_section_one_v1() throws Exception{
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
		assertThat(administrativeUnits.stream().map(AdministrativeUnit::getCode)).containsExactly("10001","10002","10003","4","5","6","7","8","9");
		
		__inject__(AdministrativeUnitBusiness.class).generateCodesBySectionsCodes("3");
		
		administrativeUnits = __inject__(AdministrativeUnitPersistence.class).readByServiceGroupCodeByFunctionalClassificationCode("1", "1");
		assertThat(administrativeUnits).isNotEmpty();
		assertThat(administrativeUnits.stream().map(AdministrativeUnit::getOrderNumber)).containsExactly(1,2,3,4,5,6,-1,-1,-1);
		assertThat(administrativeUnits.stream().map(AdministrativeUnit::getCode)).containsExactly("10001","10002","10003","10004","10005","10006","4","5","6");
		
		__inject__(AdministrativeUnitBusiness.class).generateCodesBySectionsCodes("3");
		
		administrativeUnits = __inject__(AdministrativeUnitPersistence.class).readByServiceGroupCodeByFunctionalClassificationCode("1", "1");
		assertThat(administrativeUnits).isNotEmpty();
		assertThat(administrativeUnits.stream().map(AdministrativeUnit::getOrderNumber)).containsExactly(1,2,3,4,5,6,-1,-1,-1);
		assertThat(administrativeUnits.stream().map(AdministrativeUnit::getCode)).containsExactly("10001","10002","10003","10004","10005","10006","4","5","6");
		
		__inject__(AdministrativeUnitBusiness.class).generateCodesBySectionsCodes("2");
		
		administrativeUnits = __inject__(AdministrativeUnitPersistence.class).readByServiceGroupCodeByFunctionalClassificationCode("1", "1");
		assertThat(administrativeUnits).isNotEmpty();
		assertThat(administrativeUnits.stream().map(AdministrativeUnit::getOrderNumber)).containsExactly(1,2,3,4,5,6,7,8,9);
		assertThat(administrativeUnits.stream().map(AdministrativeUnit::getCode)).containsExactly("10001","10002","10003","10004","10005","10006","10007","10008","10009");
	}
	
	//@Test
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
				,new AdministrativeUnit("b1","1","1","1","1","1"),new AdministrativeUnit("b2","1","1","1","1","1"),new AdministrativeUnit("b3","1","1","1","1","1")
				));
		__inject__(ActivityBusiness.class).createMany(List.of(new Activity("1","1",null),new Activity("2","1",null),new Activity("3","1",null),new Activity("4","1",null),new Activity("5","1",null)));
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
		assertThat(__inject__(AdministrativeUnitActivityPersistence.class).count()).isEqualTo(2l);
		administrativeUnit = __inject__(AdministrativeUnitBusiness.class).findByBusinessIdentifier("1",new Properties().setFields(AdministrativeUnit.FIELD_ACTIVITIES));
		assertThat(administrativeUnit.getActivities()).isNotEmpty();
		assertThat(administrativeUnit.getActivities().stream().map(Activity::getCode).collect(Collectors.toList())).containsExactlyInAnyOrder("1","2");
		
		administrativeUnit.setActivities(null);
		__inject__(AdministrativeUnitBusiness.class).update(administrativeUnit, new Properties().setFields(AdministrativeUnit.FIELD_ACTIVITIES));
		assertThat(__inject__(AdministrativeUnitActivityPersistence.class).count()).isEqualTo(0l);
		administrativeUnit = __inject__(AdministrativeUnitBusiness.class).findByBusinessIdentifier("1",new Properties().setFields(AdministrativeUnit.FIELD_ACTIVITIES));
		assertThat(administrativeUnit.getActivities()).isEmpty();
		
		administrativeUnit.addActivitiesByCodes("1","2","3");
		__inject__(AdministrativeUnitBusiness.class).update(administrativeUnit, new Properties().setFields(AdministrativeUnit.FIELD_ACTIVITIES));
		assertThat(__inject__(AdministrativeUnitActivityPersistence.class).count()).isEqualTo(3l);
		administrativeUnit = __inject__(AdministrativeUnitBusiness.class).findByBusinessIdentifier("1",new Properties().setFields(AdministrativeUnit.FIELD_ACTIVITIES));
		assertThat(administrativeUnit.getActivities().stream().map(Activity::getCode).collect(Collectors.toList())).containsExactlyInAnyOrder("1","2","3");
		
		administrativeUnit.setActivities(null);
		__inject__(AdministrativeUnitBusiness.class).update(administrativeUnit, new Properties().setFields(AdministrativeUnit.FIELD_ACTIVITIES));
		assertThat(__inject__(AdministrativeUnitActivityPersistence.class).count()).isEqualTo(0l);
		administrativeUnit = __inject__(AdministrativeUnitBusiness.class).findByBusinessIdentifier("1",new Properties().setFields(AdministrativeUnit.FIELD_ACTIVITIES));
		assertThat(administrativeUnit.getActivities()).isEmpty();
		
		Activity activity = __inject__(ActivityPersistence.class).readByBusinessIdentifier("1");
		activity.setAdministrativeUnitBeneficiaire(__inject__(AdministrativeUnitPersistence.class).readByBusinessIdentifier("b2"));
		administrativeUnit.getActivities(Boolean.TRUE).add(activity);
		__inject__(AdministrativeUnitBusiness.class).update(administrativeUnit, new Properties().setFields(AdministrativeUnit.FIELD_ACTIVITIES));
		assertThat(__inject__(AdministrativeUnitActivityPersistence.class).count()).isEqualTo(1l);
		try {
			administrativeUnit = __inject__(AdministrativeUnitBusiness.class).findByBusinessIdentifier("1",new Properties()
					.setFields(AdministrativeUnit.FIELD_ACTIVITIES+","+AdministrativeUnit.FIELD_ACTIVITIES+"."+Activity.FIELD_ADMINISTRATIVE_UNIT_BENEFICIAIRE));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertThat(administrativeUnit.getActivities()).isNotEmpty();
		assertThat(administrativeUnit.getActivities().stream().map(Activity::getCode).collect(Collectors.toList())).containsExactlyInAnyOrder("1");
		assertThat(administrativeUnit.getActivities().stream().map(x -> x.getAdministrativeUnitBeneficiaire().getCode())
				.collect(Collectors.toList())).containsExactlyInAnyOrder("b2");
	}
	
	@Test
	public void administrativeUnit_mergeByCodes() throws Exception{
		__inject__(SectionBusiness.class).createMany(List.of(new Section().setCode("1").setName("1")));
		__inject__(ServiceGroupBusiness.class).createMany(List.of(new ServiceGroup().setCode("1").setName("1")));
		__inject__(FunctionalClassificationBusiness.class).createMany(List.of(new FunctionalClassification().setCode("1").setName("1")));
		__inject__(LocalisationBusiness.class).create(new Localisation().setCode("1").setName("1"));
		__inject__(ActivityBusiness.class).createMany(List.of(new Activity("1","1",null),new Activity("2","1",null),new Activity("3","1",null),new Activity("4","1",null)
				,new Activity("5","1",null)));
		
		__inject__(AdministrativeUnitBusiness.class).createMany(List.of(
				new AdministrativeUnit("1","1","1","1","1","1").addActivitiesByCodes("1","2"),new AdministrativeUnit("2","1","1","1","1","1").addActivitiesByCodes("3")
				,new AdministrativeUnit("3","1","1","1","1","1")
				,new AdministrativeUnit("b1","1","1","1","1","1").addActivitiesByCodes("4","5"),new AdministrativeUnit("b2","1","1","1","1","1")
				,new AdministrativeUnit("b3","1","1","1","1","1")
				));
		assertThat(__inject__(AdministrativeUnitBusiness.class).count()).isEqualTo(6);
		
		AdministrativeUnit administrativeUnit = __inject__(AdministrativeUnitBusiness.class).findByBusinessIdentifier("1",new Properties().setFields(AdministrativeUnit.FIELD_ACTIVITIES));
		assertThat(administrativeUnit.getActivities()).isNotEmpty();
		assertThat(administrativeUnit.getActivities().stream().map(Activity::getCode).collect(Collectors.toList())).containsExactlyInAnyOrder("1","2");
		
		administrativeUnit = __inject__(AdministrativeUnitBusiness.class).findByBusinessIdentifier("2",new Properties().setFields(AdministrativeUnit.FIELD_ACTIVITIES));
		assertThat(administrativeUnit.getActivities()).isNotEmpty();
		assertThat(administrativeUnit.getActivities().stream().map(Activity::getCode).collect(Collectors.toList())).containsExactlyInAnyOrder("3");
		
		administrativeUnit = __inject__(AdministrativeUnitBusiness.class).findByBusinessIdentifier("3",new Properties().setFields(AdministrativeUnit.FIELD_ACTIVITIES));
		assertThat(administrativeUnit.getActivities()).isEmpty();
		
		administrativeUnit = __inject__(AdministrativeUnitBusiness.class).findByBusinessIdentifier("b1",new Properties().setFields(AdministrativeUnit.FIELD_ACTIVITIES));
		assertThat(administrativeUnit.getActivities()).isNotEmpty();
		assertThat(administrativeUnit.getActivities().stream().map(Activity::getCode).collect(Collectors.toList())).containsExactlyInAnyOrder("4","5");
		
		administrativeUnit = __inject__(AdministrativeUnitBusiness.class).findByBusinessIdentifier("b2",new Properties().setFields(AdministrativeUnit.FIELD_ACTIVITIES));
		assertThat(administrativeUnit.getActivities()).isEmpty();
		
		administrativeUnit = __inject__(AdministrativeUnitBusiness.class).findByBusinessIdentifier("b3",new Properties().setFields(AdministrativeUnit.FIELD_ACTIVITIES));
		assertThat(administrativeUnit.getActivities()).isEmpty();
		
		__inject__(AdministrativeUnitBusiness.class).mergeByCodes(List.of("2"), "1");
		
		assertThat(__inject__(AdministrativeUnitBusiness.class).findByBusinessIdentifier("2")).isNull();
		assertThat(__inject__(AdministrativeUnitBusiness.class).count()).isEqualTo(5);
		
		administrativeUnit = __inject__(AdministrativeUnitBusiness.class).findByBusinessIdentifier("1",new Properties().setFields(AdministrativeUnit.FIELD_ACTIVITIES));
		assertThat(administrativeUnit.getActivities()).isNotEmpty();
		assertThat(administrativeUnit.getActivities().stream().map(Activity::getCode).collect(Collectors.toList())).containsExactlyInAnyOrder("1","2","3");
		
		administrativeUnit = __inject__(AdministrativeUnitBusiness.class).findByBusinessIdentifier("2",new Properties().setFields(AdministrativeUnit.FIELD_ACTIVITIES));
		assertThat(administrativeUnit).isNull();
		
		__inject__(AdministrativeUnitBusiness.class).mergeByCodes(List.of("3"), "1");
		assertThat(__inject__(AdministrativeUnitBusiness.class).findByBusinessIdentifier("3")).isNull();
		assertThat(__inject__(AdministrativeUnitBusiness.class).count()).isEqualTo(4);

		administrativeUnit = __inject__(AdministrativeUnitBusiness.class).findByBusinessIdentifier("1",new Properties().setFields(AdministrativeUnit.FIELD_ACTIVITIES));
		assertThat(administrativeUnit.getActivities()).isNotEmpty();
		assertThat(administrativeUnit.getActivities().stream().map(Activity::getCode).collect(Collectors.toList())).containsExactlyInAnyOrder("1","2","3");
		
		__inject__(AdministrativeUnitBusiness.class).mergeByCodes(List.of("1"), "b2");
		assertThat(__inject__(AdministrativeUnitBusiness.class).findByBusinessIdentifier("1")).isNull();
		assertThat(__inject__(AdministrativeUnitBusiness.class).count()).isEqualTo(3);

		administrativeUnit = __inject__(AdministrativeUnitBusiness.class).findByBusinessIdentifier("b2",new Properties().setFields(AdministrativeUnit.FIELD_ACTIVITIES));
		assertThat(administrativeUnit.getActivities()).isNotEmpty();
		assertThat(administrativeUnit.getActivities().stream().map(Activity::getCode).collect(Collectors.toList())).containsExactlyInAnyOrder("1","2","3");
		
		
	}
	
	@Test
	public void administrativeUnit_delete() throws Exception{
		__inject__(SectionBusiness.class).createMany(List.of(new Section().setCode("1").setName("1")));
		__inject__(ServiceGroupBusiness.class).createMany(List.of(new ServiceGroup().setCode("1").setName("1")));
		__inject__(FunctionalClassificationBusiness.class).createMany(List.of(new FunctionalClassification().setCode("1").setName("1")));
		__inject__(LocalisationBusiness.class).create(new Localisation().setCode("1").setName("1"));
		__inject__(ActivityBusiness.class).createMany(List.of(new Activity("1","1",null),new Activity("2","1",null),new Activity("3","1",null),new Activity("4","1",null)
				,new Activity("5","1",null)));
		__inject__(DestinationBusiness.class).createMany(List.of(new Destination("1","1","1"),new Destination("2","1","1"),new Destination("3","1","1"),new Destination("4","1","1")
				,new Destination("5","1","1")));
		
		__inject__(AdministrativeUnitBusiness.class).createMany(List.of(
				new AdministrativeUnit("1","1","1","1","1","1").addActivitiesByCodes("1","2").addDestinationsByCodes("1")
				,new AdministrativeUnit("2","1","1","1","1","1").addActivitiesByCodes("3")
				,new AdministrativeUnit("3","1","1","1","1","1").addDestinationsByCodes("1")
				,new AdministrativeUnit("b1","1","1","1","1","1").addActivitiesByCodes("4","5")
				,new AdministrativeUnit("b2","1","1","1","1","1")
				,new AdministrativeUnit("b3","1","1","1","1","1")
				));
		assertThat(__inject__(AdministrativeUnitBusiness.class).count()).isEqualTo(6);
		
		__inject__(AdministrativeUnitBusiness.class).deleteByBusinessIdentifier("3");
		
		assertThat(__inject__(AdministrativeUnitBusiness.class).count()).isEqualTo(5);
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
	
	@Test
	public void activity_update_administrativeUnits() throws Exception{
		__inject__(SectionBusiness.class).createMany(List.of(new Section().setCode("1").setName("1")));
		__inject__(ServiceGroupBusiness.class).createMany(List.of(new ServiceGroup().setCode("1").setName("1")));
		__inject__(FunctionalClassificationBusiness.class).createMany(List.of(new FunctionalClassification().setCode("1").setName("1")));
		__inject__(LocalisationBusiness.class).create(new Localisation().setCode("1").setName("1"));
		__inject__(AdministrativeUnitBusiness.class).createMany(List.of(
				new AdministrativeUnit("1","1","1","1","1","1"),new AdministrativeUnit("2","1","1","1","1","1"),new AdministrativeUnit("3","1","1","1","1","1")
				,new AdministrativeUnit("b1","1","1","1","1","1"),new AdministrativeUnit("b2","1","1","1","1","1"),new AdministrativeUnit("b3","1","1","1","1","1")
				));
		__inject__(ActivityBusiness.class).createMany(List.of(new Activity("1","1",null),new Activity("2","1",null),new Activity("3","1",null),new Activity("4","1",null),new Activity("5","1",null)));
		Activity activity = __inject__(ActivityBusiness.class).findByBusinessIdentifier("1",new Properties().setFields(Activity.FIELD_ADMINISTRATIVE_UNIT));
		assertThat(activity.getAdministrativeUnit()).isNull();
		assertThat(activity.getAdministrativeUnitBeneficiaire()).isNull();
		activity.setAdministrativeUnitFromCode("1");
		assertThat(__inject__(AdministrativeUnitActivityPersistence.class).count()).isEqualTo(0l);		
		__inject__(ActivityBusiness.class).update(activity, new Properties().setFields(Activity.FIELD_ADMINISTRATIVE_UNIT));		
		assertThat(__inject__(AdministrativeUnitActivityPersistence.class).count()).isEqualTo(1l);	
		
		activity = __inject__(ActivityBusiness.class).findByBusinessIdentifier("1",new Properties().setFields(Activity.FIELD_ADMINISTRATIVE_UNIT));
		assertThat(activity.getAdministrativeUnit()).isNotNull();
		assertThat(activity.getAdministrativeUnit().getCode()).isEqualTo("1");
		assertThat(activity.getAdministrativeUnitBeneficiaire()).isNull();
		
		activity.setAdministrativeUnit(null);
		__inject__(ActivityBusiness.class).update(activity, new Properties().setFields(Activity.FIELD_ADMINISTRATIVE_UNIT));	
		assertThat(__inject__(AdministrativeUnitActivityPersistence.class).count()).isEqualTo(0l);	
		activity = __inject__(ActivityBusiness.class).findByBusinessIdentifier("1",new Properties().setFields(Activity.FIELD_ADMINISTRATIVE_UNIT));
		assertThat(activity.getAdministrativeUnit()).isNull();
		assertThat(activity.getAdministrativeUnitBeneficiaire()).isNull();
		
		activity.setAdministrativeUnitFromCode("1").setAdministrativeUnitBeneficiaireFromCode("b2");
		__inject__(ActivityBusiness.class).update(activity, new Properties().setFields(Activity.FIELD_ADMINISTRATIVE_UNIT));	
		assertThat(__inject__(AdministrativeUnitActivityPersistence.class).count()).isEqualTo(1l);	
		
		activity = __inject__(ActivityBusiness.class).findByBusinessIdentifier("1",new Properties().setFields(Activity.FIELD_ADMINISTRATIVE_UNIT));
		assertThat(activity.getAdministrativeUnit()).isNotNull();
		assertThat(activity.getAdministrativeUnit().getCode()).isEqualTo("1");
		assertThat(activity.getAdministrativeUnitBeneficiaire()).isNotNull();
		assertThat(activity.getAdministrativeUnitBeneficiaire().getCode()).isEqualTo("b2");
		
		activity.setAdministrativeUnit(null);
		__inject__(ActivityBusiness.class).update(activity, new Properties().setFields(Activity.FIELD_ADMINISTRATIVE_UNIT));	
		assertThat(__inject__(AdministrativeUnitActivityPersistence.class).count()).isEqualTo(1l);	
		activity = __inject__(ActivityBusiness.class).findByBusinessIdentifier("1",new Properties().setFields(Activity.FIELD_ADMINISTRATIVE_UNIT));
		assertThat(activity.getAdministrativeUnit()).isNull();
		assertThat(activity.getAdministrativeUnitBeneficiaire()).isNotNull();
		
		activity.setAdministrativeUnitFromCode("1");
		__inject__(ActivityBusiness.class).update(activity, new Properties().setFields(Activity.FIELD_ADMINISTRATIVE_UNIT));	
		assertThat(__inject__(AdministrativeUnitActivityPersistence.class).count()).isEqualTo(1l);	
		activity = __inject__(ActivityBusiness.class).findByBusinessIdentifier("1",new Properties().setFields(Activity.FIELD_ADMINISTRATIVE_UNIT));
		assertThat(activity.getAdministrativeUnit()).isNotNull();
		assertThat(activity.getAdministrativeUnit().getCode()).isEqualTo("1");
		assertThat(activity.getAdministrativeUnitBeneficiaire()).isNotNull();
		assertThat(activity.getAdministrativeUnitBeneficiaire().getCode()).isEqualTo("b2");
		
		activity.setAdministrativeUnitBeneficiaire(null);
		__inject__(ActivityBusiness.class).update(activity, new Properties().setFields(Activity.FIELD_ADMINISTRATIVE_UNIT));	
		assertThat(__inject__(AdministrativeUnitActivityPersistence.class).count()).isEqualTo(1l);	
		activity = __inject__(ActivityBusiness.class).findByBusinessIdentifier("1",new Properties().setFields(Activity.FIELD_ADMINISTRATIVE_UNIT));
		assertThat(activity.getAdministrativeUnit()).isNotNull();
		assertThat(activity.getAdministrativeUnitBeneficiaire()).isNull();
		
		activity.setAdministrativeUnit(null);
		__inject__(ActivityBusiness.class).update(activity, new Properties().setFields(Activity.FIELD_ADMINISTRATIVE_UNIT));	
		assertThat(__inject__(AdministrativeUnitActivityPersistence.class).count()).isEqualTo(0l);	
		activity = __inject__(ActivityBusiness.class).findByBusinessIdentifier("1",new Properties().setFields(Activity.FIELD_ADMINISTRATIVE_UNIT));
		assertThat(activity.getAdministrativeUnit()).isNull();
		assertThat(activity.getAdministrativeUnitBeneficiaire()).isNull();
	}
	
	@Test
	public void user_create_twoFilesCreated() throws Exception{
		if(!ApplicationScopeLifeCycleListener.isUserEnabled())
			return;
		__inject__(SectionBusiness.class).createMany(List.of(new Section().setCode("1").setName("1"),new Section().setCode("2").setName("1"),new Section().setCode("3").setName("1")));
		__inject__(LocalisationBusiness.class).createMany(List.of(new Localisation().setCode("1").setName("1"),new Localisation().setCode("2").setName("1"),new Localisation().setCode("3").setName("1")));
		__inject__(ServiceGroupBusiness.class).create(new ServiceGroup().setCode("1").setName("1"));
		__inject__(FunctionalClassificationBusiness.class).create(new FunctionalClassification().setCode("1").setName("1"));
		__inject__(FunctionCategoryBusiness.class).create(new FunctionCategory().setCode("1").setName("1"));
		__inject__(FunctionTypeBusiness.class).create(new FunctionType().setCode("1").setName("1").setCategoryFromCode("1"));
		__inject__(FunctionBusiness.class).createMany(List.of(new Function().setCode("1").setName("1").setTypeFromCode("1")
				,new Function().setCode("2").setName("1").setTypeFromCode("1"),new Function().setCode("3").setName("1").setTypeFromCode("1")));
		__inject__(ActivityBusiness.class).createMany(List.of(new Activity().setCode("1").setName("1"),new Activity().setCode("2").setName("1"),new Activity().setCode("3").setName("1")));
		__inject__(AdministrativeUnitBusiness.class).createMany(List.of(new AdministrativeUnit().setCode("1").setName("1").setSectionFromCode("1").setLocalisationFromCode("1").setServiceGroupFromCode("1").setFunctionalClassificationFromCode("1").setOrderNumber(-1)
				,new AdministrativeUnit().setCode("2").setName("1").setSectionFromCode("1").setLocalisationFromCode("1").setServiceGroupFromCode("1").setFunctionalClassificationFromCode("1").setOrderNumber(-1)
				,new AdministrativeUnit().setCode("3").setName("1").setSectionFromCode("1").setLocalisationFromCode("1").setServiceGroupFromCode("1").setFunctionalClassificationFromCode("1").setOrderNumber(-1)));
		User user = new User();
		user.setIdentifier("1");
		user.setElectronicMailAddress("kycdev@gmail.com");
		user.setSections(List.of(__inject__(SectionPersistence.class).readByBusinessIdentifier("1")));
		user.setLocalisations(List.of(__inject__(LocalisationPersistence.class).readByBusinessIdentifier("1")));
		user.setFunctions(List.of(__inject__(FunctionPersistence.class).readByBusinessIdentifier("1")));
		user.setActivities(List.of(__inject__(ActivityPersistence.class).readByBusinessIdentifier("1")));
		user.setAdministrativeUnits(List.of(__inject__(AdministrativeUnitPersistence.class).readByBusinessIdentifier("1")));
		user.setFiles(List.of(new File().setBytes("text01".getBytes()).setExtension("txt").setType(UserFileType.ADMINISTRATIVE_CERTIFICATE)
				,new File().setBytes("text02".getBytes()).setExtension("txt").setType(UserFileType.BUDGETARY_CERTIFICATE)));
		
		__inject__(UserBusiness.class).create(user);
		user = __inject__(UserBusiness.class).findBySystemIdentifier("1",new Properties().setFields(User.FIELD_SECTIONS+","+User.FIELD_LOCALISATIONS
				+","+User.FIELD_FUNCTIONS+","+User.FIELD_ACTIVITIES+","+User.FIELD_ADMINISTRATIVE_UNITS));
		assertThat(user.getSections()).hasSize(1);
		assertThat(user.getLocalisations()).hasSize(1);
		assertThat(user.getFunctions()).hasSize(1);
		assertThat(user.getActivities()).hasSize(1);
		assertThat(user.getAdministrativeUnits()).hasSize(1);
		
		assertThat(user.getSections().stream().map(Section::getCode).collect(Collectors.toList())).contains("1");
		assertThat(user.getLocalisations().stream().map(Localisation::getCode).collect(Collectors.toList())).contains("1");
		assertThat(user.getFunctions().stream().map(Function::getCode).collect(Collectors.toList())).contains("1");
		assertThat(user.getActivities().stream().map(Activity::getCode).collect(Collectors.toList())).contains("1");
		assertThat(user.getAdministrativeUnits().stream().map(AdministrativeUnit::getCode).collect(Collectors.toList())).contains("1");
		
		assertThat(__inject__(FilePersistence.class).count()).isEqualTo(2l);
	}
	
	@Test
	public void user_create_onlyOneFileCreated() throws Exception{
		if(!ApplicationScopeLifeCycleListener.isUserEnabled())
			return;
		__inject__(SectionBusiness.class).createMany(List.of(new Section().setCode("1").setName("1"),new Section().setCode("2").setName("1"),new Section().setCode("3").setName("1")));
		__inject__(LocalisationBusiness.class).createMany(List.of(new Localisation().setCode("1").setName("1"),new Localisation().setCode("2").setName("1"),new Localisation().setCode("3").setName("1")));
		__inject__(ServiceGroupBusiness.class).create(new ServiceGroup().setCode("1").setName("1"));
		__inject__(FunctionalClassificationBusiness.class).create(new FunctionalClassification().setCode("1").setName("1"));
		__inject__(FunctionCategoryBusiness.class).create(new FunctionCategory().setCode("1").setName("1"));
		__inject__(FunctionTypeBusiness.class).create(new FunctionType().setCode("1").setName("1").setCategoryFromCode("1"));
		__inject__(FunctionBusiness.class).createMany(List.of(new Function().setCode("1").setName("1").setTypeFromCode("1")
				,new Function().setCode("2").setName("1").setTypeFromCode("1"),new Function().setCode("3").setName("1").setTypeFromCode("1")));
		__inject__(ActivityBusiness.class).createMany(List.of(new Activity().setCode("1").setName("1"),new Activity().setCode("2").setName("1"),new Activity().setCode("3").setName("1")));
		__inject__(AdministrativeUnitBusiness.class).createMany(List.of(new AdministrativeUnit().setCode("1").setName("1").setSectionFromCode("1").setLocalisationFromCode("1").setServiceGroupFromCode("1").setFunctionalClassificationFromCode("1").setOrderNumber(-1)
				,new AdministrativeUnit().setCode("2").setName("1").setSectionFromCode("1").setLocalisationFromCode("1").setServiceGroupFromCode("1").setFunctionalClassificationFromCode("1").setOrderNumber(-1)
				,new AdministrativeUnit().setCode("3").setName("1").setSectionFromCode("1").setLocalisationFromCode("1").setServiceGroupFromCode("1").setFunctionalClassificationFromCode("1").setOrderNumber(-1)));
		User user = new User();
		user.setIdentifier("1");
		user.setElectronicMailAddress("kycdev@gmail.com");
		user.setSections(List.of(__inject__(SectionPersistence.class).readByBusinessIdentifier("1")));
		user.setLocalisations(List.of(__inject__(LocalisationPersistence.class).readByBusinessIdentifier("1")));
		user.setFunctions(List.of(__inject__(FunctionPersistence.class).readByBusinessIdentifier("1")));
		user.setActivities(List.of(__inject__(ActivityPersistence.class).readByBusinessIdentifier("1")));
		user.setAdministrativeUnits(List.of(__inject__(AdministrativeUnitPersistence.class).readByBusinessIdentifier("1")));
		user.setFiles(List.of(new File().setBytes("text01".getBytes()).setExtension("txt").setType(UserFileType.ADMINISTRATIVE_CERTIFICATE)
				,new File().setBytes("text01".getBytes()).setExtension("txt").setType(UserFileType.BUDGETARY_CERTIFICATE)));
		
		__inject__(UserBusiness.class).create(user);
		user = __inject__(UserBusiness.class).findBySystemIdentifier("1",new Properties().setFields(User.FIELD_SECTIONS+","+User.FIELD_LOCALISATIONS
				+","+User.FIELD_FUNCTIONS+","+User.FIELD_ACTIVITIES+","+User.FIELD_ADMINISTRATIVE_UNITS));
		assertThat(user.getSections()).hasSize(1);
		assertThat(user.getLocalisations()).hasSize(1);
		assertThat(user.getFunctions()).hasSize(1);
		assertThat(user.getActivities()).hasSize(1);
		assertThat(user.getAdministrativeUnits()).hasSize(1);
		
		assertThat(user.getSections().stream().map(Section::getCode).collect(Collectors.toList())).contains("1");
		assertThat(user.getLocalisations().stream().map(Localisation::getCode).collect(Collectors.toList())).contains("1");
		assertThat(user.getFunctions().stream().map(Function::getCode).collect(Collectors.toList())).contains("1");
		assertThat(user.getActivities().stream().map(Activity::getCode).collect(Collectors.toList())).contains("1");
		assertThat(user.getAdministrativeUnits().stream().map(AdministrativeUnit::getCode).collect(Collectors.toList())).contains("1");
		
		assertThat(__inject__(FilePersistence.class).count()).isEqualTo(2l);
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

	/* Mail */
}
