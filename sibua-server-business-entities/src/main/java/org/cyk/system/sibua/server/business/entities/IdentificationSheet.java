package org.cyk.system.sibua.server.business.entities;

import java.io.InputStream;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.cyk.system.sibua.server.persistence.entities.user.User;
import org.cyk.utility.__kernel__.string.StringHelper;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter //@Accessors(chain=true)
public class IdentificationSheet implements Serializable {
	private static final long serialVersionUID = 1L;

	private InputStream armoirieCoteDIvoire;
	private String section;
	private String function;
	private String administrativeUnit;
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
	
	private String currentDate = LocalDateTime.now().toString();
	private InputStream codeVisualRepresentation;
	
	/**/
	
	public static IdentificationSheet instantiate(User user) {
		if(user == null)
			return null;
		IdentificationSheet identificationSheet = new IdentificationSheet();
		identificationSheet.setAdministrativeUnit(StringHelper.get(user.getAdministrativeUnit()));
		identificationSheet.setArmoirieCoteDIvoire(null);
		identificationSheet.setBudgetaryYear("2020");
		identificationSheet.setCertificateReference("789456123");
		identificationSheet.setCivility(StringHelper.get(user.getCivility()));
		identificationSheet.setDeskPhoneNumber(user.getDeskPhoneNumber());
		identificationSheet.setDeskPost(user.getDeskPost());
		identificationSheet.setElectronicMailAddress(user.getElectronicMailAddress());
		identificationSheet.setFirstName(user.getFirstName());
		//identificationSheet.setFunction(StringHelper.get(user.getFunctions().toString()));
		identificationSheet.setLastNames(user.getLastNames());
		identificationSheet.setFirstNameAndLastNames(identificationSheet.getFirstName());
		if(StringHelper.isBlank(identificationSheet.getFirstNameAndLastNames()))
			identificationSheet.setFirstNameAndLastNames(identificationSheet.getLastNames());
		else if(StringHelper.isNotBlank(identificationSheet.getLastNames()))
			identificationSheet.setFirstNameAndLastNames(identificationSheet.getFirstName()+" "+identificationSheet.getLastNames());
		identificationSheet.setMobilePhoneNumber(user.getMobilePhoneNumber());
		identificationSheet.setPostalAddress(user.getPostalAddress());
		identificationSheet.setRegistrationNumber(user.getRegistrationNumber());
		//identificationSheet.setSection(StringHelper.get(user.getSection().toString()));
		identificationSheet.setUserType(StringHelper.get(user.getType()));
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
		identificationSheet.setCodeVisualRepresentation(IdentificationSheet.class.getResourceAsStream("report/code_qr.png"));
		identificationSheet.setArmoirieCoteDIvoire(IdentificationSheet.class.getResourceAsStream("report/armoirieci.jpg"));
		identificationSheet.setSection("327 Ministère auprès du Premier Ministre, chargé du Budget et du Portefeuille de l'Etat");
		identificationSheet.setFunction("GC2011010016 Gestionnaire de crédits de Cabinet du Ministre chargé du Budget");
		identificationSheet.setAdministrativeUnit("11010016 Cabinet du Ministre auprès du Premier Ministre, chargé du Budget et du Portefeuille de l'Etat");
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
		return identificationSheet;
	}
	
	
}
