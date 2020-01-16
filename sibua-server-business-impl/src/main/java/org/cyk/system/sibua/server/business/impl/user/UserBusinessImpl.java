package org.cyk.system.sibua.server.business.impl.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.business.api.user.UserActivityBusiness;
import org.cyk.system.sibua.server.business.api.user.UserAdministrativeUnitBusiness;
import org.cyk.system.sibua.server.business.api.user.UserBusiness;
import org.cyk.system.sibua.server.business.api.user.UserFileBusiness;
import org.cyk.system.sibua.server.business.api.user.UserFunctionBusiness;
import org.cyk.system.sibua.server.business.api.user.UserLocalisationBusiness;
import org.cyk.system.sibua.server.business.api.user.UserSectionBusiness;
import org.cyk.system.sibua.server.persistence.api.user.FilePersistence;
import org.cyk.system.sibua.server.persistence.api.user.UserPersistence;
import org.cyk.system.sibua.server.persistence.entities.user.File;
import org.cyk.system.sibua.server.persistence.entities.user.User;
import org.cyk.system.sibua.server.persistence.entities.user.UserActivity;
import org.cyk.system.sibua.server.persistence.entities.user.UserAdministrativeUnit;
import org.cyk.system.sibua.server.persistence.entities.user.UserFile;
import org.cyk.system.sibua.server.persistence.entities.user.UserFunction;
import org.cyk.system.sibua.server.persistence.entities.user.UserLocalisation;
import org.cyk.system.sibua.server.persistence.entities.user.UserSection;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.file.FileHelper;
import org.cyk.utility.__kernel__.number.ByteHelper;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.server.business.AbstractBusinessEntityImpl;
import org.cyk.utility.server.business.BusinessFunctionCreator;

@ApplicationScoped
public class UserBusinessImpl extends AbstractBusinessEntityImpl<User, UserPersistence> implements UserBusiness,Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void __listenExecuteCreateAfter__(User user, Properties properties, BusinessFunctionCreator businessFunctionCreator) {
		super.__listenExecuteCreateAfter__(user, properties, businessFunctionCreator);
		if(CollectionHelper.isNotEmpty(user.getFunctions()))
			__inject__(UserFunctionBusiness.class).createMany(user.getFunctions().stream().map(function -> new UserFunction().setUser(user).setFunction(function))
					.collect(Collectors.toList()));
		if(CollectionHelper.isNotEmpty(user.getSections()))
			__inject__(UserSectionBusiness.class).createMany(user.getSections().stream().map(section -> new UserSection().setUser(user).setSection(section))
					.collect(Collectors.toList()));
		if(CollectionHelper.isNotEmpty(user.getLocalisations()))
			__inject__(UserLocalisationBusiness.class).createMany(user.getLocalisations().stream().map(localisation -> new UserLocalisation().setUser(user).setLocalisation(localisation))
					.collect(Collectors.toList()));
		if(CollectionHelper.isNotEmpty(user.getActivities()))
			__inject__(UserActivityBusiness.class).createMany(user.getActivities().stream().map(activity -> new UserActivity().setUser(user).setActivity(activity))
					.collect(Collectors.toList()));
		if(CollectionHelper.isNotEmpty(user.getFiles())) {
			Collection<UserFile> userFiles = null;
			for(File file : user.getFiles()) {
				File __file__ = null;
				if(file.getBytes() == null || file.getBytes().length == 0)
					throw new RuntimeException("le contenu du fichier est obligatoire");
				String sha1 = ByteHelper.buildMessageDigest(file.getBytes());
				if(StringHelper.isBlank(sha1))
					throw new RuntimeException("impossible de calculer le sha1");
				__file__ = __inject__(FilePersistence.class).readBySha1(sha1);
				if(__file__ == null) {
					file.setSha1(sha1);
					if(StringHelper.isBlank(file.getExtension()))
						file.setExtension(FileHelper.getExtension(file.getName()));
					if(StringHelper.isBlank(file.getExtension()))
						throw new RuntimeException("l'extension du fichier est obligatoire");	
					__file__ = file;
				}else {
					
				}
				if(userFiles == null)
					userFiles = new ArrayList<>();
				userFiles.add(new UserFile().setUser(user).setFile(__file__).setType(file.getType()).setReference(file.getReference()));
			}
			if(CollectionHelper.isNotEmpty(userFiles))
				__inject__(UserFileBusiness.class).createMany(userFiles);
		}
		if(CollectionHelper.isNotEmpty(user.getAdministrativeUnits()))
			__inject__(UserAdministrativeUnitBusiness.class).createMany(user.getAdministrativeUnits().stream().map(administrativeUnit -> new UserAdministrativeUnit().setUser(user).setAdministrativeUnit(administrativeUnit))
					.collect(Collectors.toList()));
		//MailSender.getInstance().send("Identification", "Cliquer sur ce lien pour activer", user.getElectronicMailAddress());
	}
	
}
