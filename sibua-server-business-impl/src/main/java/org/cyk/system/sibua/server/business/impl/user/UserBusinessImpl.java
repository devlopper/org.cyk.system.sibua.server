package org.cyk.system.sibua.server.business.impl.user;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.business.api.user.UserActivityBusiness;
import org.cyk.system.sibua.server.business.api.user.UserAdministrativeUnitBusiness;
import org.cyk.system.sibua.server.business.api.user.UserBusiness;
import org.cyk.system.sibua.server.business.api.user.UserFileBusiness;
import org.cyk.system.sibua.server.business.api.user.UserFunctionBusiness;
import org.cyk.system.sibua.server.business.api.user.UserLocalisationBusiness;
import org.cyk.system.sibua.server.business.api.user.UserSectionBusiness;
import org.cyk.system.sibua.server.business.entities.IdentificationSheet;
import org.cyk.system.sibua.server.persistence.api.query.ReadUserFileByUsers;
import org.cyk.system.sibua.server.persistence.api.user.FilePersistence;
import org.cyk.system.sibua.server.persistence.api.user.UserFilePersistence;
import org.cyk.system.sibua.server.persistence.api.user.UserPersistence;
import org.cyk.system.sibua.server.persistence.entities.user.File;
import org.cyk.system.sibua.server.persistence.entities.user.User;
import org.cyk.system.sibua.server.persistence.entities.user.UserActivity;
import org.cyk.system.sibua.server.persistence.entities.user.UserAdministrativeUnit;
import org.cyk.system.sibua.server.persistence.entities.user.UserFile;
import org.cyk.system.sibua.server.persistence.entities.user.UserFileType;
import org.cyk.system.sibua.server.persistence.entities.user.UserFunction;
import org.cyk.system.sibua.server.persistence.entities.user.UserLocalisation;
import org.cyk.system.sibua.server.persistence.entities.user.UserSection;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.file.FileHelper;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.number.ByteHelper;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.protocol.smtp.MailSender;
import org.cyk.utility.__kernel__.random.RandomHelper;
import org.cyk.utility.__kernel__.report.ReportBuilder;
import org.cyk.utility.__kernel__.report.Template;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.string.Strings;
import org.cyk.utility.server.business.AbstractBusinessEntityImpl;
import org.cyk.utility.server.business.BusinessFunctionCreator;
import org.cyk.utility.server.business.BusinessFunctionModifier;
import org.cyk.utility.server.business.BusinessFunctionRemover;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;

@ApplicationScoped
public class UserBusinessImpl extends AbstractBusinessEntityImpl<User, UserPersistence> implements UserBusiness,Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void __listenExecuteCreateBefore__(User user, Properties properties, BusinessFunctionCreator function) {
		super.__listenExecuteCreateBefore__(user, properties, function);
		user.setCreationDate(LocalDateTime.now());
		user.setAccessToken(RandomHelper.getAlphanumeric(3)+"_"+RandomHelper.getAlphanumeric(4)+"_"+RandomHelper.getAlphanumeric(3));
		if(StringHelper.isNotBlank(user.getFirstName()))
			user.setFirstName(user.getFirstName().toUpperCase());
		if(StringHelper.isNotBlank(user.getRegistrationNumber()))
			user.setRegistrationNumber(user.getRegistrationNumber().toUpperCase());
	}
	
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
		
		notifyAccessToken(user);
	}
	
	@Override
	public void notifyAccessToken(Collection<User> users) {
		if(CollectionHelper.isEmpty(users))
			return;
		for(User user : users) {
			new Thread(new Runnable() {				
				@Override
				public void run() {
					//MailSender.getInstance().send("SIB - Identification", "Cliquer sur ce lien pour activer", user.getElectronicMailAddress());
					try {
						MailSender.getInstance().send("SIB - Identification", "Jeton d'accès : "+user.getAccessToken(), user.getElectronicMailAddress());
					} catch (Exception exception) {
						LogHelper.log(exception, getClass());
					}	
				}
			}).start();			
		}		
	}

	@Override
	protected void __listenExecuteUpdateBefore__(User user, Properties properties,BusinessFunctionModifier function) {
		super.__listenExecuteUpdateBefore__(user, properties, function);
		if(user.getAdministrativeUnitCertificateSignedDateTimestamp() == null)
			user.setAdministrativeUnitCertificateSignedDate(null);
		else
			user.setAdministrativeUnitCertificateSignedDate(LocalDateTime.ofInstant(new Date(user.getAdministrativeUnitCertificateSignedDateTimestamp()).toInstant()
				, ZoneOffset.UTC));
		Strings fields = __getFieldsFromProperties__(properties);
		if(CollectionHelper.isEmpty(fields))
			return;		
		for(String index : fields.get()) {
			if(User.FIELD_SENDING_DATE.equals(index)) {
				if(user.getSendingDate() != null)
					throw new RuntimeException("La fiche d'identification ne peut pas être transmise plus d'une fois.");
				Collection<String> requiredFieldsNames = new ArrayList<>();
				if(StringHelper.isBlank(user.getFirstName()))
					requiredFieldsNames.add("nom");
				if(StringHelper.isBlank(user.getLastNames()))
					requiredFieldsNames.add("prénom(s)");
				if(StringHelper.isBlank(user.getMobilePhoneNumber()))
					requiredFieldsNames.add("numéro de téléphone mobile");
				if(StringHelper.isBlank(user.getElectronicMailAddress()))
					requiredFieldsNames.add("adresse email");
				if(StringHelper.isBlank(user.getAdministrativeUnitCertificateReference()))
					requiredFieldsNames.add("référence de l'acte de nomination");
				if(StringHelper.isBlank(user.getAdministrativeUnitCertificateSignedBy()))
					requiredFieldsNames.add("signataire de l'acte de nomination");
				if(user.getAdministrativeUnitCertificateSignedDate() == null)
					requiredFieldsNames.add("date de signature de l'acte de nomination");
				if(user.getType() != null && ("fonctionnaire".equalsIgnoreCase(user.getType().getName()) || user.getType().getName().contains("agent")) && StringHelper.isBlank(user.getRegistrationNumber()))
					requiredFieldsNames.add("matricule");				
				if(CollectionHelper.isNotEmpty(requiredFieldsNames))
					throw new RuntimeException("Les informations suivantes doivent être renseignées avant de transmettre la fiche d'identification : "
							+StringHelper.concatenate(requiredFieldsNames, ","));
				
				user.setSendingDate(LocalDateTime.now());
			}else if(User.FIELD_FILES.equals(index)) {
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
						UserFile userFile = __inject__(UserFilePersistence.class).readByUserIdentifierByFileIdentifierByType(user.getIdentifier(), __file__.getIdentifier()
								, UserFileType.ADMINISTRATIVE_CERTIFICATE);
						if(userFile == null)
							userFile = new UserFile().setUser(user).setType(UserFileType.ADMINISTRATIVE_CERTIFICATE);
							
						userFiles.add(userFile.setFile(__file__).setReference(file.getReference()));
					}
					if(CollectionHelper.isNotEmpty(userFiles))
						__inject__(UserFileBusiness.class).saveMany(userFiles);
				}
			}else if(User.FIELD_FIRST_NAME.equals(index)) {
				if(StringHelper.isNotBlank(user.getFirstName()))
					user.setFirstName(user.getFirstName().toUpperCase());
			}else if(User.FIELD_REGISTRATION_NUMBER.equals(index)) {
				if(StringHelper.isNotBlank(user.getRegistrationNumber()))
					user.setRegistrationNumber(user.getRegistrationNumber().toUpperCase());
			}
		}
	}
	
	@Override
	protected void __listenExecuteDeleteBefore__(User user, Properties properties, BusinessFunctionRemover function) {
		super.__listenExecuteDeleteBefore__(user, properties, function);
		Collection<UserFile> userFiles = ((ReadUserFileByUsers)__inject__(UserFilePersistence.class)).readByUsers(user);
		if(CollectionHelper.isNotEmpty(userFiles))
			__inject__(UserFileBusiness.class).deleteMany(userFiles);
	}
	
	@Override
	public Collection<IdentificationSheet> buildIdentificationSheets(Collection<User> users) {
		if(CollectionHelper.isEmpty(users))
			return null;
		Collection<IdentificationSheet> identificationSheets = new ArrayList<>();
		for(User user : users) {
			IdentificationSheet identificationSheet = IdentificationSheet.instantiate(user);
			if(identificationSheet == null)
				continue;
			identificationSheets.add(identificationSheet);
		}
		return identificationSheets;
	}
	
	@Override
	public ByteArrayOutputStream buildIdentificationSheetsReport(Collection<User> users) {
		if(CollectionHelper.isEmpty(users))
			return null;
		Collection<IdentificationSheet> identificationSheets = buildIdentificationSheets(users);
		if(CollectionHelper.isEmpty(identificationSheets))
			return null;
		JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(identificationSheets);
		Template template = new Template().setInputStream(IdentificationSheet.class.getResourceAsStream("report/fiche_identification.jrxml"));
		ByteArrayOutputStream byteArrayOutputStream = ReportBuilder.getInstance().build(template, jrBeanCollectionDataSource, JRPdfExporter.class);
		return byteArrayOutputStream;
	}

	@Override
	protected Boolean __isCallDeleteByInstanceOnDeleteByIdentifier__() {
		return Boolean.TRUE;
	}
}
