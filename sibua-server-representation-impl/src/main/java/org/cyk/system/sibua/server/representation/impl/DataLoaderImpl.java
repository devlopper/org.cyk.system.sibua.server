package org.cyk.system.sibua.server.representation.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.ws.rs.core.Response;

import org.cyk.system.sibua.server.business.api.ActivityBusiness;
import org.cyk.system.sibua.server.business.api.AdministrativeUnitBusiness;
import org.cyk.system.sibua.server.business.api.SectionBusiness;
import org.cyk.system.sibua.server.persistence.entities.Activity;
import org.cyk.system.sibua.server.persistence.entities.AdministrativeUnit;
import org.cyk.system.sibua.server.persistence.entities.Section;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.random.RandomHelper;
import org.cyk.utility.server.representation.AbstractDataLoaderImpl;

@org.cyk.system.sibua.server.annotation.System
public class DataLoaderImpl extends AbstractDataLoaderImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public Response load() {
		try {
			Collection<Section> sections = null;
			Collection<AdministrativeUnit> administrativeUnits = null;
			for(Integer index = 0 ; index < 10 ; index = index + 1) {
				if(sections == null)
					sections = new ArrayList<>();
				Section section = new Section().setCode(RandomHelper.getAlphabetic(4)).setName(RandomHelper.getAlphabetic(10));
				sections.add(section);
				for(Integer indexAdministrativeUnit = 0 ; indexAdministrativeUnit < 15 ; indexAdministrativeUnit = indexAdministrativeUnit + 1) {
					if(administrativeUnits == null)
						administrativeUnits = new ArrayList<>();
					AdministrativeUnit administrativeUnit = new AdministrativeUnit().setCode(RandomHelper.getAlphabetic(6)).setName(RandomHelper.getAlphabetic(10))
							.setSection(section);
					administrativeUnits.add(administrativeUnit);
				}
			}
			if(CollectionHelper.isNotEmpty(sections))
				__inject__(SectionBusiness.class).createByBatch(sections, 100);
			if(CollectionHelper.isNotEmpty(administrativeUnits))
				__inject__(AdministrativeUnitBusiness.class).createByBatch(administrativeUnits, 100);
			
			Collection<Activity> activities = null;
			for(Integer index = 0 ; index < 10 ; index = index + 1) {
				if(activities == null)
					activities = new ArrayList<>();
				activities.add(new Activity().setCode(RandomHelper.getAlphabetic(4)).setName(RandomHelper.getAlphabetic(10)));
			}
			if(CollectionHelper.isNotEmpty(activities))
				__inject__(ActivityBusiness.class).createByBatch(activities, 100);
		} catch (Exception exception) {
			exception.printStackTrace();
			return Response.serverError().build();
		}
		return Response.ok().build();
	}
	
}