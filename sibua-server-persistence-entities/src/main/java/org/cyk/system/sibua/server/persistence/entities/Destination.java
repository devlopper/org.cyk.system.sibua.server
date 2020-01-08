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
@Entity @Table(name=Destination.TABLE_NAME)
public class Destination extends AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotNull @ManyToOne @JoinColumn(name = COLUMN_SECTION) private Section section;
	/*@NotNull*/ @ManyToOne @JoinColumn(name = COLUMN_TITLE) private Title title;
	
	@Transient private AdministrativeUnit administrativeUnit;
	@Transient private Activity activity;
	
	@Override
	public Destination setCode(String code) {
		return (Destination) super.setCode(code);
	}
	
	@Override
	public Destination setName(String name) {
		return (Destination) super.setName(name);
	}
	
	public Destination setSectionFromCode(String code) {
		if(StringHelper.isBlank(code))
			this.section = null;
		else
			this.section = InstanceGetter.getInstance().getByBusinessIdentifier(Section.class, code);
		return this;
	}
	
	public Destination setTitleFromCode(String code) {
		if(StringHelper.isBlank(code))
			this.title = null;
		else
			this.title = InstanceGetter.getInstance().getByBusinessIdentifier(Title.class, code);
		return this;
	}
	
	/**/
	
	public static final String FIELD_SECTION = "section";
	public static final String FIELD_TITLE = "title";
	public static final String FIELD_ADMINISTRATIVE_UNIT = "administrativeUnit";
	public static final String FIELD_ACTIVITY = "activity";
	
	public static final String COLUMN_SECTION = Section.TABLE_NAME;
	public static final String COLUMN_TITLE = Title.TABLE_NAME;
	
	public static final String TABLE_NAME = "destination_sigbud";	
	
	public static final String CODE_NEW_PREFIX = "ND";
}
