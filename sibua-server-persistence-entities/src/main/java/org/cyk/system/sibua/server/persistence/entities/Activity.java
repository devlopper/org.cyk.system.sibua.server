package org.cyk.system.sibua.server.persistence.entities;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.cyk.utility.__kernel__.instance.InstanceGetter;
import org.cyk.utility.__kernel__.object.__static__.persistence.AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl;
import org.cyk.utility.__kernel__.string.StringHelper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
@Entity @Table(name=Activity.TABLE_NAME)
public class Activity extends AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	/*@NotNull */@ManyToOne @JoinColumn(name = COLUMN_ACTION) private Action action;
	@Column(name = COLUMN_YEAR) private Integer year;
	@Column(name = COLUMN_AMOUNT_AE) private Long amountAE;
	@Column(name = COLUMN_AMOUNT_CP) private Long amountCP;
	
	@Transient private Section section;
	@Transient private Program program;
	@Transient private AdministrativeUnit administrativeUnit;
	@Transient private Collection<Destination> destinations;
	
	public Activity(String code,String name,String actionCode) {
		super(code,name);
		setActionFromCode(actionCode);
	}
	
	@Override
	public Activity setCode(String code) {
		return (Activity) super.setCode(code);
	}
	
	@Override
	public Activity setName(String name) {
		return (Activity) super.setName(name);
	}
	
	public Activity setActionFromCode(String code) {
		if(StringHelper.isBlank(code))
			this.action = null;
		else
			this.action = InstanceGetter.getInstance().getByBusinessIdentifier(Action.class, code);
		return this;
	}
	
	public static final String FIELD_ACTION = "action";
	public static final String FIELD_YEAR = "year";
	public static final String FIELD_AMOUNT_AE = "amountAE";
	public static final String FIELD_AMOUNT_CP = "amountCP";
	public static final String FIELD_SECTION = "section";
	public static final String FIELD_PROGRAM = "program";
	public static final String FIELD_ADMINISTRATIVE_UNIT = "administrativeUnit";
	public static final String FIELD_DESTINATIONS = "destinations";
	
	public static final String COLUMN_ACTION = FIELD_ACTION;
	public static final String COLUMN_YEAR = "ANNEE";
	public static final String COLUMN_AMOUNT_AE = "MONTANT_AE";
	public static final String COLUMN_AMOUNT_CP = "MONTANT_CP";
	
	public static final String TABLE_NAME = "activite";	
	
	public static final String CODE_NEW_PREFIX = "NA";
}
