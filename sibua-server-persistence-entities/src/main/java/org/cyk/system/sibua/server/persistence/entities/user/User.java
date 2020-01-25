package org.cyk.system.sibua.server.persistence.entities.user;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.cyk.system.sibua.server.persistence.entities.Activity;
import org.cyk.system.sibua.server.persistence.entities.AdministrativeUnit;
import org.cyk.system.sibua.server.persistence.entities.Localisation;
import org.cyk.system.sibua.server.persistence.entities.Section;
import org.cyk.utility.__kernel__.object.__static__.persistence.AbstractIdentifiableSystemScalarStringImpl;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
@Entity @Table(name=User.TABLE_NAME)
public class User extends AbstractIdentifiableSystemScalarStringImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManyToOne @JoinColumn(name = COLUMN_TYPE) private UserType type;
	@ManyToOne @JoinColumn(name = COLUMN_CIVILITY) private Civility civility;
	@ManyToOne @JoinColumn(name = COLUMN_ADMINISTRATIVE_UNIT) private AdministrativeUnit administrativeUnit;
	
	@Column(name = COLUMN_REGISTRATION_NUMBER) private String registrationNumber;
	@Column(name = COLUMN_FIRST_NAME) private String firstName;
	@Column(name = COLUMN_LAST_NAMES) private String lastNames;
	@NotNull @Column(name = COLUMN_ELECTRONIC_MAIL_ADDRESS,unique = true) private String electronicMailAddress;
	@Column(name = COLUMN_MOBILE_PHONE_NUMBER) private String mobilePhoneNumber;
	@Column(name = COLUMN_DESK_PHONE_NUMBER) private String deskPhoneNumber;
	@Column(name = COLUMN_DESK_POST) private String deskPost;
	@Column(name = COLUMN_POSTAL_ADDRESS) private String postalAddress;
	
	@Column(name = COLUMN_PASS) private String pass;
	/**/
	
	@Column(name=COLUMN_CREATION_DATE) private LocalDateTime creationDate;
	@Column(name=COLUMN_ACTIVATION_DATE) private LocalDateTime activationDate;
	@Column(name=COLUMN_VALIDATION_DATE) private LocalDateTime validationDate;
	
	@Column(name = COLUMN_ACCESS_TOKEN,unique = true) private String accessToken;
	
	@Transient private Collection<Section> sections;
	@Transient private Collection<Localisation> localisations;
	@Transient private Collection<Activity> activities;
	@Transient private Collection<Function> functions;
	@Transient private Collection<File> files;
	@Transient private Collection<AdministrativeUnit> administrativeUnits;
	@Transient private String reportUniformResourceIdentifier;
	
	
	public static final String FIELD_PASS = "pass";
	public static final String FIELD_CIVILITY = "civility";
	public static final String FIELD_TYPE = "type";
	public static final String FIELD_FUNCTION = "function";
	public static final String FIELD_REGISTRATION_NUMBER = "registrationNumber";
	public static final String FIELD_FIRST_NAME = "firstName";
	public static final String FIELD_LAST_NAMES = "lastNames";
	public static final String FIELD_ELECTRONIC_MAIL_ADDRESS = "electronicMailAddress";
	public static final String FIELD_MOBILE_PHONE_NUMBER = "mobilePhoneNumber";
	public static final String FIELD_DESK_PHONE_NUMBER = "deskPhoneNumber";
	public static final String FIELD_POST_NUMBER = "postNumber";
	public static final String FIELD_POSTAL_ADDRESS = "postalAddress";
	public static final String FIELD_CERTIFICATE_REFERENCE = "certificateReference";
	public static final String FIELD_ADMINISTRATIVE_UNIT = "administrativeUnit";
	public static final String FIELD_SECTIONS = "sections";
	public static final String FIELD_ADMINISTRATIVE_UNITS = "administrativeUnits";
	public static final String FIELD_LOCALISATIONS = "localisations";
	public static final String FIELD_ACTIVITIES = "activities";
	public static final String FIELD_FUNCTIONS = "functions";
	public static final String FIELD_FILES = "files";
	public static final String FIELD_USER_FILES = "userFiles";
	public static final String FIELD_CREATION_DATE = "creationDate";
	public static final String FIELD_ACTIVATION_DATE = "activationDate";
	public static final String FIELD_VALIDATION_DATE = "validationDate";
	public static final String FIELD_ACCESS_TOKEN = "accessToken";
	public static final String FIELD_REPORT_UNIFORM_RESOURCE_IDENTIFIER = "reportUniformResourceIdentifier";
	
	public static final String COLUMN_PASS = "mot_de_passe";
	public static final String COLUMN_CIVILITY = Civility.TABLE_NAME;
	public static final String COLUMN_TYPE = UserType.TABLE_NAME;
	public static final String COLUMN_FUNCTION = Function.TABLE_NAME;
	public static final String COLUMN_ADMINISTRATIVE_UNIT = AdministrativeUnit.TABLE_NAME;
	public static final String COLUMN_REGISTRATION_NUMBER = "matricule";
	public static final String COLUMN_FIRST_NAME = "nom";
	public static final String COLUMN_LAST_NAMES = "prenoms";
	public static final String COLUMN_ELECTRONIC_MAIL_ADDRESS = "email";
	public static final String COLUMN_MOBILE_PHONE_NUMBER = "telephone_mobile";
	public static final String COLUMN_DESK_PHONE_NUMBER = "telephone_bureau";
	public static final String COLUMN_DESK_POST = "telephone_bureau_poste";
	public static final String COLUMN_POSTAL_ADDRESS = "adresse_postale";
	public static final String COLUMN_CREATION_DATE = "date_creation";
	public static final String COLUMN_ACTIVATION_DATE = "date_activation";
	public static final String COLUMN_VALIDATION_DATE = "date_validation";
	public static final String COLUMN_ACCESS_TOKEN = "jeton_acces";
	
	public static final String JOIN_TABLE_FUNCTIONS_MANAGED = "fonction_bud_dem";
	public static final String JOIN_TABLE_SECTIONS_MANAGED = "section_bud_dem";
	public static final String JOIN_TABLE_ADMINISTRATIVE_UNITS_MANAGED = "ua_geree";
	public static final String JOIN_TABLE_PROGRAMS_MANAGED = "programmes_geree";
	public static final String JOIN_TABLE_ACTIONS_MANAGED = "actions_geree";
	public static final String JOIN_TABLE_ACTIVITIES_MANAGED = "activites_geree";
	
	public static final String TABLE_NAME = "utilisateur";	
}