package org.cyk.system.sibua.server.persistence.entities;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.cyk.utility.__kernel__.instance.InstanceGetter;
import org.cyk.utility.__kernel__.object.__static__.persistence.AbstractIdentifiableSystemScalarStringImpl;
import org.cyk.utility.__kernel__.string.StringHelper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
@Entity @Table(name=User.TABLE_NAME)
public class User extends AbstractIdentifiableSystemScalarStringImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotNull @ManyToOne @JoinColumn(name = COLUMN_FUNCTION) private Function function;
	@NotNull @ManyToOne @JoinColumn(name = COLUMN_ADMINISTRATIVE_UNIT) private AdministrativeUnit administrativeUnit;
	
	@OneToMany @JoinTable(name = JOIN_TABLE_SECTIONS_MANAGED,joinColumns = {@JoinColumn(name=TABLE_NAME)},inverseJoinColumns = {@JoinColumn(name=Section.TABLE_NAME)})
	private Collection<Section> sectionsManaged;
	
	@OneToMany @JoinTable(name = JOIN_TABLE_ADMINISTRATIVE_UNITS_MANAGED,joinColumns = {@JoinColumn(name=TABLE_NAME)},inverseJoinColumns = {@JoinColumn(name=AdministrativeUnit.TABLE_NAME)})
	private Collection<AdministrativeUnit> administrativeUnitsManaged;
	
	@NotNull @Column(name = COLUMN_REGISTRATION_NUMBER) private String registrationNumber;
	@NotNull @Column(name = COLUMN_FIRST_NAME) private String firstName;
	@NotNull @Column(name = COLUMN_LAST_NAMES) private String lastNames;
	@NotNull @Column(name = COLUMN_ELECTRONIC_MAIL_ADDRESS,unique = true) private String electronicMailAddress;
	@NotNull @Column(name = COLUMN_PHONE_NUMBER) private String phoneNumber;
	
	@Transient private Collection<UserFile> userFiles;
	
	public User setFunctionFromCode(String code) {
		if(StringHelper.isBlank(code))
			this.function = null;
		else
			this.function = InstanceGetter.getInstance().getByBusinessIdentifier(Function.class, code);
		return this;
	}
	
	public static final String FIELD_FUNCTION = "function";
	public static final String FIELD_REGISTRATION_NUMBER = "registrationNumber";
	public static final String FIELD_FIRST_NAME = "firstName";
	public static final String FIELD_LAST_NAMES = "lastNames";
	public static final String FIELD_ELECTRONIC_MAIL_ADDRESS = "electronicMailAddress";
	public static final String FIELD_PHONE_NUMBER = "phoneNumber";
	public static final String FIELD_ADMINISTRATIVE_UNIT = "administrativeUnit";
	public static final String FIELD_SECTIONS_MANAGED = "sectionsManaged";
	public static final String FIELD_ADMINISTRATIVE_UNITS_MANAGED = "administrativeUnitsManaged";
	public static final String FIELD_USER_FILES = "userFiles";
	
	public static final String COLUMN_FUNCTION = Function.TABLE_NAME;
	public static final String COLUMN_ADMINISTRATIVE_UNIT = AdministrativeUnit.TABLE_NAME;
	public static final String COLUMN_REGISTRATION_NUMBER = "matricule";
	public static final String COLUMN_FIRST_NAME = "nom";
	public static final String COLUMN_LAST_NAMES = "prenoms";
	public static final String COLUMN_ELECTRONIC_MAIL_ADDRESS = "email";
	public static final String COLUMN_PHONE_NUMBER = "telephone";
	
	public static final String JOIN_TABLE_SECTIONS_MANAGED = "sections_geree";
	public static final String JOIN_TABLE_ADMINISTRATIVE_UNITS_MANAGED = "ua_geree";
	
	public static final String TABLE_NAME = "utilisateur";	
}