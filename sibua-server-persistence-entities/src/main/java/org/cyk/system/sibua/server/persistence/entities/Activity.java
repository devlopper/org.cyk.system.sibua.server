package org.cyk.system.sibua.server.persistence.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

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

	@NotNull @ManyToOne @JoinColumn(name = COLUMN_ACTION) private Action action;
	
	@Transient private Section section;
	@Transient private Program program;
	@Transient private AdministrativeUnit administrativeUnit;
	
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
	public static final String FIELD_SECTION = "section";
	public static final String FIELD_PROGRAM = "program";
	public static final String FIELD_ADMINISTRATIVE_UNIT = "administrativeUnit";
	
	public static final String COLUMN_ACTION = FIELD_ACTION;
	
	public static final String TABLE_NAME = "activite";	
}
