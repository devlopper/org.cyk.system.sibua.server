package org.cyk.system.sibua.server.business.entities;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.cyk.system.sibua.server.persistence.entities.user.User;
import org.cyk.system.sibua.server.persistence.entities.user.UserFile;
import org.cyk.system.sibua.server.persistence.entities.user.UserFileType;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.constant.ConstantEmpty;
import org.cyk.utility.__kernel__.random.RandomHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.string.barcode.BarCodeBuilder;
import org.cyk.utility.__kernel__.string.barcode.BarCodeBuilder.Parameters;
import org.cyk.utility.__kernel__.string.barcode.zxing.BarCodeBuilderImpl;
import org.cyk.utility.__kernel__.value.ValueHelper;

import com.google.zxing.BarcodeFormat;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter //@Accessors(chain=true)
public class IdentificationSheet implements Serializable {
	private static final long serialVersionUID = 1L;

	private String title;
	private InputStream headerAsInputStream,photoAsInputStream;
	
	private String section;
	private String function;
	private String administrativeUnit;
	private String administrativeUnitFunction;
	private String administrativeUnitCertificateReference;
	private String administrativeUnitCertificateSignedBy;
	private String administrativeUnitCertificateSignedDate;
	private String userType;	
	private String civility;
	private String budgetaryFunctionsAsString;
	private Collection<String> budgetaryFunctions;
	
	private String registrationNumber;
	private String firstName;
	private String lastNames;
	private String firstNameAndLastNames;
	
	private String electronicMailAddress;
	private String mobilePhoneNumber;
	private String deskPhoneNumber;
	private String deskPost;
	private String postalAddress;
	private String contacts;
	
	private String budgetaryYear;
	
	/* Dates */
	
	private String systemCreationDate;
	private String lastUpdateDate;
	private String lastPrintDate;
	private String lastSendDate;
	
	private String currentDate = DateTimeFormatter.ofPattern("EEEE dd LLLL yyyy à kk:mm", Locale.FRENCH).format(LocalDateTime.now());
	private InputStream codeVisualRepresentation;
	
	private String codeVisualRepresentationAsString;
	private Boolean validatedByOrdonnateur;
	/**/
	
	public IdentificationSheet() {
		setHeaderAsInputStream(IdentificationSheet.class.getResourceAsStream("report/header.png"));
	}
	
	public static IdentificationSheet instantiate(User user) {
		if(user == null)
			return null;
		IdentificationSheet identificationSheet = new IdentificationSheet();
		identificationSheet.title = "FICHE DE RENSEIGNEMENT ET DE DEPOT DE SIGNATURE DES ";
		if(CollectionHelper.isNotEmpty(user.getFunctions())) {
			Collection<String> codes = user.getFunctions().stream().map(function -> function.getType().getCategory().getCode()).collect(Collectors.toList());			
			if(codes.contains("3"))
				identificationSheet.title += "CÔNTROLEURS FINANCIERS";
			else if(codes.contains("1"))
				identificationSheet.title += "ORDONNATEURS";
			else {
				identificationSheet.title += "GESTIONNAIRES DE CREDITS";
				identificationSheet.validatedByOrdonnateur = Boolean.TRUE;
			}
			
			final AtomicInteger index = new AtomicInteger(1);
			identificationSheet.budgetaryFunctionsAsString = StringHelper.concatenate(user.getFunctions().stream().map(x->(index.getAndIncrement())+". "+x.getCode()+" "+x.getName())
					.collect(Collectors.toList()), "\r\n");
		}else
			identificationSheet.title += "GESTIONNAIRES DE CREDITS";
		
		if(StringHelper.isNotBlank(user.getIdentifier())) {
			identificationSheet.setCodeVisualRepresentation(new ByteArrayInputStream(BarCodeBuilder.getInstance().build(user.getIdentifier()
					, new Parameters().setFormat(BarcodeFormat.QR_CODE))));
		}
		
		if(CollectionHelper.isNotEmpty(user.getUserFiles())) {
			for(UserFile userFile : user.getUserFiles()) {
				if(UserFileType.PHOTO.equals(userFile.getType())) {
					identificationSheet.setPhotoAsInputStream(new ByteArrayInputStream(userFile.getFile().getBytes()));
				}
			}
		}
		
		if(user.getAdministrativeUnit() != null)
			identificationSheet.setAdministrativeUnit(ValueHelper.defaultToIfBlank(user.getAdministrativeUnit().getCode()+" "+user.getAdministrativeUnit().getName(),ConstantEmpty.STRING));
		if(user.getAdministrativeUnit() != null && user.getAdministrativeUnit().getSection() != null)
			identificationSheet.setSection(ValueHelper.defaultToIfBlank(user.getAdministrativeUnit().getSection().getCode()+" "+user.getAdministrativeUnit().getSection().getName(),ConstantEmpty.STRING));
		identificationSheet.setAdministrativeUnitFunction(ValueHelper.defaultToIfBlank(user.getAdministrativeUnitFunction(),ConstantEmpty.STRING));
		identificationSheet.setBudgetaryYear("2020");
		identificationSheet.setAdministrativeUnitCertificateReference(ValueHelper.defaultToIfBlank(user.getAdministrativeUnitCertificateReference(),ConstantEmpty.STRING));
		identificationSheet.setAdministrativeUnitCertificateSignedBy(ValueHelper.defaultToIfBlank(user.getAdministrativeUnitCertificateSignedBy(),ConstantEmpty.STRING));
		if(user.getAdministrativeUnitCertificateSignedDate() == null)
			identificationSheet.setAdministrativeUnitCertificateSignedDate(ConstantEmpty.STRING);
		else
			identificationSheet.setAdministrativeUnitCertificateSignedDate(DateTimeFormatter.ofPattern("EEEE dd LLLL yyyy", Locale.FRENCH).format(user.getAdministrativeUnitCertificateSignedDate()));
		if(user.getCivility() != null)
			identificationSheet.setCivility(ValueHelper.defaultToIfBlank(user.getCivility().getName(),ConstantEmpty.STRING));
		identificationSheet.setDeskPhoneNumber(ValueHelper.defaultToIfBlank(user.getDeskPhoneNumber(),ConstantEmpty.STRING));
		identificationSheet.setDeskPost(ValueHelper.defaultToIfBlank(user.getDeskPost(),ConstantEmpty.STRING));
		identificationSheet.setElectronicMailAddress(ValueHelper.defaultToIfBlank(user.getElectronicMailAddress(),ConstantEmpty.STRING));
		identificationSheet.setFirstName(ValueHelper.defaultToIfBlank(user.getFirstName(),ConstantEmpty.STRING));
		//identificationSheet.setFunction(StringHelper.get(user.getFunctions().toString()));
		identificationSheet.setLastNames(ValueHelper.defaultToIfBlank(user.getLastNames(),ConstantEmpty.STRING));
		identificationSheet.setFirstNameAndLastNames(ValueHelper.defaultToIfBlank(identificationSheet.getFirstName(),ConstantEmpty.STRING));
		if(StringHelper.isBlank(identificationSheet.getFirstNameAndLastNames()))
			identificationSheet.setFirstNameAndLastNames(ValueHelper.defaultToIfBlank(identificationSheet.getLastNames(),ConstantEmpty.STRING));
		else if(StringHelper.isNotBlank(identificationSheet.getLastNames()))
			identificationSheet.setFirstNameAndLastNames(ValueHelper.defaultToIfBlank(identificationSheet.getFirstName()+" "+identificationSheet.getLastNames(),ConstantEmpty.STRING));
		identificationSheet.setMobilePhoneNumber(ValueHelper.defaultToIfBlank(user.getMobilePhoneNumber(),ConstantEmpty.STRING));
		identificationSheet.setPostalAddress(ValueHelper.defaultToIfBlank(user.getPostalAddress(),ConstantEmpty.STRING));
		identificationSheet.setRegistrationNumber(ValueHelper.defaultToIfBlank(user.getRegistrationNumber(),ConstantEmpty.STRING));
		//identificationSheet.setSection(StringHelper.get(user.getSection().toString()));
		if(user.getType() != null)
			identificationSheet.setUserType(ValueHelper.defaultToIfBlank(user.getType().getName(),ConstantEmpty.STRING));
		identificationSheet.setCodeVisualRepresentationAsString(user.getIdentifier());
		return identificationSheet;
	}
	
	/**/
	
	public static Collection<IdentificationSheet> buildRandomlyMany() {
		List<IdentificationSheet> identificationSheets = new ArrayList<>();
		identificationSheets.add(buildRandomlyOne("FICHE DE RENSEIGNEMENT ET DE DEPOT DE SIGNATURE DES GESTIONNAIRES DE CREDITS",
				List.of("GC2011010009 Gestionnaire de crédits de Direction Générale de la Décentramisation et du Développement Local"),Boolean.TRUE));
		
		identificationSheets.add(buildRandomlyOne("FICHE DE RENSEIGNEMENT ET DE DEPOT DE SIGNATURE DES ORDONNATEURS",
				List.of("GC2011010009 Gestionnaire de crédits de Direction Générale de la Décentramisation et du Développement Local"
						,"ORD1113006 Ordonnateur de l' Autorite Nationale de Régulation des Marchés Publics (ANRMP)"),null));
		
		identificationSheets.add(buildRandomlyOne("FICHE DE RENSEIGNEMENT ET DE DEPOT DE SIGNATURE DES CÔNTROLEUR FINANCIERS",
				List.of("GC2011010009 Gestionnaire de crédits de Direction Générale de la Décentramisation et du Développement Local"
						,"CF001 Côntroleur fincancier du Département de Didiévi"),null));
		return identificationSheets;
	}
	
	public static IdentificationSheet buildRandomlyOne(String title,Collection<String> budgetaryFunctions,Boolean validatedByOrdonnateur) {
		IdentificationSheet identificationSheet = new IdentificationSheet();
		identificationSheet.title = title;
		identificationSheet.validatedByOrdonnateur = validatedByOrdonnateur;
		BarCodeBuilder barCodeBuilder = new BarCodeBuilderImpl();
		identificationSheet.setCodeVisualRepresentation(new ByteArrayInputStream(barCodeBuilder
				.build(RandomHelper.getAlphabetic(3)+"-"+RandomHelper.getAlphabetic(4)+"-"+RandomHelper.getAlphabetic(3)
				, new Parameters().setFormat(BarcodeFormat.QR_CODE))));
		
		identificationSheet.setPhotoAsInputStream(IdentificationSheet.class.getResourceAsStream("report/armoirieci.jpg"));
		identificationSheet.setBudgetaryYear("2020");
		
		identificationSheet.setSection("327 Ministère auprès du Premier Ministre, chargé du Budget et du Portefeuille de l'Etat");
		identificationSheet.setFunction("GC2011010016 Gestionnaire de crédits de Cabinet du Ministre auprès du Premier Ministre, chargé du Budget et du Portefeuille de l'Etat");
		identificationSheet.setAdministrativeUnit("11010016 Cabinet du Ministre auprès du Premier Ministre, chargé du Budget et du Portefeuille de l'Etat");
		identificationSheet.setAdministrativeUnitFunction("Directeur");
		identificationSheet.setAdministrativeUnitCertificateReference("REF00200A412");
		identificationSheet.setAdministrativeUnitCertificateSignedBy("Zadi Koffi Ouattara Bernard");
		identificationSheet.setAdministrativeUnitCertificateSignedDate("12/07/2019");
		identificationSheet.setUserType("Fonctionnaire");
		identificationSheet.setCivility("Mr");
		identificationSheet.setRegistrationNumber("100100A");
		identificationSheet.setFirstName("Tantan");
		identificationSheet.setLastNames("Pion");
		identificationSheet.setFirstNameAndLastNames(identificationSheet.getFirstName().toUpperCase()+" "+identificationSheet.getLastNames());
		identificationSheet.setElectronicMailAddress("tp@mail.com");
		identificationSheet.setPostalAddress("01 BP 100 Abidjan 01");
		identificationSheet.setMobilePhoneNumber("01020304");
		identificationSheet.setDeskPhoneNumber("11223344");
		identificationSheet.setDeskPost("10");

		identificationSheet.setBudgetaryFunctions(budgetaryFunctions);
		
		final AtomicInteger index = new AtomicInteger(1);
		identificationSheet.budgetaryFunctionsAsString = StringHelper.concatenate(identificationSheet.budgetaryFunctions.stream().map(x->(index.getAndIncrement())+". "+x)
				.collect(Collectors.toList()), "\r\n");
		
		identificationSheet.setSystemCreationDate("20/1/2020 10:25");
		identificationSheet.setLastUpdateDate("20/1/2020 18:00");
		identificationSheet.setLastPrintDate("21/1/2020 8:30");
		identificationSheet.setLastSendDate("21/1/2020 8:45");
		identificationSheet.setCodeVisualRepresentationAsString(UUID.randomUUID().toString());
		return identificationSheet;
	}
	
	
}
