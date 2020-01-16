package org.cyk.system.sibua.server.persistence.impl.user;
import java.io.Serializable;
import java.lang.reflect.Field;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.persistence.api.ActivityPersistence;
import org.cyk.system.sibua.server.persistence.api.AdministrativeUnitPersistence;
import org.cyk.system.sibua.server.persistence.api.LocalisationPersistence;
import org.cyk.system.sibua.server.persistence.api.SectionPersistence;
import org.cyk.system.sibua.server.persistence.api.query.ReadActivityByUsers;
import org.cyk.system.sibua.server.persistence.api.query.ReadAdministrativeUnitByUsers;
import org.cyk.system.sibua.server.persistence.api.query.ReadFileByUsers;
import org.cyk.system.sibua.server.persistence.api.query.ReadFunctionByUsers;
import org.cyk.system.sibua.server.persistence.api.query.ReadLocalisationByUsers;
import org.cyk.system.sibua.server.persistence.api.query.ReadSectionByUsers;
import org.cyk.system.sibua.server.persistence.api.user.FilePersistence;
import org.cyk.system.sibua.server.persistence.api.user.FunctionPersistence;
import org.cyk.system.sibua.server.persistence.api.user.UserPersistence;
import org.cyk.system.sibua.server.persistence.entities.user.User;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.server.persistence.AbstractPersistenceEntityImpl;

@ApplicationScoped
public class UserPersistenceImpl extends AbstractPersistenceEntityImpl<User> implements UserPersistence,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenExecuteReadAfterSetFieldValue__(User user, Field field, Properties properties) {
		super.__listenExecuteReadAfterSetFieldValue__(user, field, properties);
		if(field.getName().equals(User.FIELD_LOCALISATIONS))
			user.setLocalisations(((ReadLocalisationByUsers)__inject__(LocalisationPersistence.class)).readByUsers(user));
		if(field.getName().equals(User.FIELD_SECTIONS))
			user.setSections(((ReadSectionByUsers)__inject__(SectionPersistence.class)).readByUsers(user));
		if(field.getName().equals(User.FIELD_ACTIVITIES))
			user.setActivities(((ReadActivityByUsers)__inject__(ActivityPersistence.class)).readByUsers(user));
		if(field.getName().equals(User.FIELD_FUNCTIONS))
			user.setFunctions(((ReadFunctionByUsers)__inject__(FunctionPersistence.class)).readByUsers(user));
		if(field.getName().equals(User.FIELD_ADMINISTRATIVE_UNITS))
			user.setAdministrativeUnits(((ReadAdministrativeUnitByUsers)__inject__(AdministrativeUnitPersistence.class)).readByUsers(user));
		if(field.getName().equals(User.FIELD_FILES))
			user.setFiles(((ReadFileByUsers)__inject__(FilePersistence.class)).readByUsers(user));
	}
	
}