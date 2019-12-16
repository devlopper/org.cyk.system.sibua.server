package org.cyk.system.sibua.server.representation.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.ws.rs.core.Response;

import org.cyk.system.sibua.server.business.api.ActionBusiness;
import org.cyk.system.sibua.server.business.api.ActivityBusiness;
import org.cyk.system.sibua.server.business.api.AdministrativeUnitBusiness;
import org.cyk.system.sibua.server.business.api.ProgramBusiness;
import org.cyk.system.sibua.server.business.api.SectionBusiness;
import org.cyk.system.sibua.server.persistence.entities.Action;
import org.cyk.system.sibua.server.persistence.entities.Activity;
import org.cyk.system.sibua.server.persistence.entities.AdministrativeUnit;
import org.cyk.system.sibua.server.persistence.entities.Program;
import org.cyk.system.sibua.server.persistence.entities.Section;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.server.representation.AbstractDataLoaderImpl;

@org.cyk.system.sibua.server.annotation.System
public class DataLoaderImpl extends AbstractDataLoaderImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public Response load() {
		try {
			Collection<Section> sections = null;
			Collection<AdministrativeUnit> administrativeUnits = null;
			Collection<Program> programs = null;
			Collection<Action> actions = null;
			Collection<Activity> activities = null;
			Integer uaIndex = 1;
			Integer programCount = 0;
			for(Integer index = 1 ; index < 100 ; index = index + 1) {
				if(sections == null)
					sections = new ArrayList<>();
				Section section = new Section().setCode("sect_"+index).setName("section "+index);
				sections.add(section);
				for(Integer indexAdministrativeUnit = 1 ; indexAdministrativeUnit <= 15 ; indexAdministrativeUnit = indexAdministrativeUnit + 1) {
					if(administrativeUnits == null)
						administrativeUnits = new ArrayList<>();
					AdministrativeUnit administrativeUnit = new AdministrativeUnit().setCode("ua_"+uaIndex).setName("ua "+uaIndex)
							.setSection(section);
					administrativeUnits.add(administrativeUnit);
					uaIndex = uaIndex + 1;
				}
				
				for(Integer indexProgram = 1 ; indexProgram <= 6 ; indexProgram = indexProgram + 1) {
					if(programs == null)
						programs = new ArrayList<>();
					Program program = new Program().setCode("prog_"+(programCount+1)).setName("programme "+(programCount+1)).setSection(section);
					for(Integer indexAction = 1 ; indexAction <= 5 ; indexAction = indexAction + 1) {
						if(actions == null)
							actions = new ArrayList<>();
						Action action = new Action().setCode(program.getCode()+"_action_"+indexAction).setName(program.getName()+" action "+indexAction).setProgram(program);
						for(Integer indexActivity = 1 ; indexActivity <= 7 ; indexActivity = indexActivity + 1) {
							if(activities == null)
								activities = new ArrayList<>();
							Activity activity = new Activity().setCode(action.getCode()+"_activite_"+indexActivity).setName(action.getName()+" activité "+indexActivity).setAction(action);
							activities.add(activity);
						}
						actions.add(action);
					}
					programs.add(program);
					programCount = programCount + 1;
				}
			}
			if(CollectionHelper.isNotEmpty(sections))
				__inject__(SectionBusiness.class).createByBatch(sections, 100);
			if(CollectionHelper.isNotEmpty(administrativeUnits))
				__inject__(AdministrativeUnitBusiness.class).createByBatch(administrativeUnits, 100);			
			if(CollectionHelper.isNotEmpty(programs))
				__inject__(ProgramBusiness.class).createByBatch(programs, 100);
			if(CollectionHelper.isNotEmpty(actions))
				__inject__(ActionBusiness.class).createByBatch(actions, 100);
			if(CollectionHelper.isNotEmpty(activities))
				__inject__(ActivityBusiness.class).createByBatch(activities, 100);
		} catch (Exception exception) {
			exception.printStackTrace();
			return Response.serverError().build();
		}
		return Response.ok().build();
	}
	
}