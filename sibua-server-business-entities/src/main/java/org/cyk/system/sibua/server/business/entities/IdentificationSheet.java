package org.cyk.system.sibua.server.business.entities;

import java.io.InputStream;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.cyk.system.sibua.server.persistence.entities.user.User;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.constant.ConstantEmpty;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.value.ValueHelper;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter //@Accessors(chain=true)
public class IdentificationSheet implements Serializable {
	private static final long serialVersionUID = 1L;

	private InputStream headerAsInputStream;
	
	private InputStream armoirieCoteDIvoire;
	private String section;
	private String function;
	private String administrativeUnit;
	private String administrativeUnitFunction;
	private String userType;	
	private String civility;
	
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
	
	private String certificateReference;
	
	private String budgetaryYear;
	
	/* Dates */
	
	private String systemCreationDate;
	private String lastUpdateDate;
	private String lastPrintDate;
	private String lastSendDate;
	
	private String currentDate = DateTimeFormatter.ofPattern("EEEE dd LLLL yyyy à kk:mm", Locale.FRENCH).format(LocalDateTime.now());
	private InputStream codeVisualRepresentation;
	
	private String codeVisualRepresentationAsString;
	
	/**/
	
	public IdentificationSheet() {
		setHeaderAsInputStream(IdentificationSheet.class.getResourceAsStream("report/header.png"));
		setCodeVisualRepresentation(IdentificationSheet.class.getResourceAsStream("report/code_qr.png"));
	}
	
	public static IdentificationSheet instantiate(User user) {
		if(user == null)
			return null;
		IdentificationSheet identificationSheet = new IdentificationSheet();
		//identificationSheet.setHeaderAsInputStream(IdentificationSheet.class.getResourceAsStream("report/header.png"));
		identificationSheet.setAdministrativeUnit(ValueHelper.defaultToIfBlank(StringHelper.get(user.getAdministrativeUnit()),ConstantEmpty.STRING));
		if(user.getAdministrativeUnit() != null)
			identificationSheet.setSection(ValueHelper.defaultToIfBlank(StringHelper.get(user.getAdministrativeUnit().getSection()),ConstantEmpty.STRING));
		identificationSheet.setAdministrativeUnitFunction(ValueHelper.defaultToIfBlank(user.getAdministrativeUnitFunction(),ConstantEmpty.STRING));
		identificationSheet.setBudgetaryYear("2020");
		if(CollectionHelper.isNotEmpty(user.getUserFiles()))
			identificationSheet.setCertificateReference(ValueHelper.defaultToIfBlank(CollectionHelper.getFirst(user.getUserFiles()).getReference(),ConstantEmpty.STRING));
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
		for(Integer index = 0; index < 3 ; index = index + 1)
			identificationSheets.add(buildRandomlyOne());
		return identificationSheets;
	}
	
	public static IdentificationSheet buildRandomlyOne() {
		IdentificationSheet identificationSheet = new IdentificationSheet();
		identificationSheet.setBudgetaryYear("2020");
		
		identificationSheet.setSection("327 Ministère auprès du Premier Ministre, chargé du Budget et du Portefeuille de l'Etat");
		identificationSheet.setFunction("GC2011010016 Gestionnaire de crédits de Cabinet du Ministre auprès du Premier Ministre, chargé du Budget et du Portefeuille de l'Etat");
		identificationSheet.setAdministrativeUnit("11010016 Cabinet du Ministre auprès du Premier Ministre, chargé du Budget et du Portefeuille de l'Etat");
		identificationSheet.setAdministrativeUnitFunction("Directeur");
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
		
		identificationSheet.setCertificateReference("REF00200A412");
		
		identificationSheet.setSystemCreationDate("20/1/2020 10:25");
		identificationSheet.setLastUpdateDate("20/1/2020 18:00");
		identificationSheet.setLastPrintDate("21/1/2020 8:30");
		identificationSheet.setLastSendDate("21/1/2020 8:45");
		identificationSheet.setCodeVisualRepresentationAsString(UUID.randomUUID().toString());
		return identificationSheet;
	}
	
	
}
