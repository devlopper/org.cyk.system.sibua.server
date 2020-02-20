package org.cyk.system.sibua.server.persistence.entities.user;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.cyk.system.sibua.server.persistence.entities.AdministrativeUnit;
import org.cyk.utility.__kernel__.instance.InstanceGetter;
import org.cyk.utility.__kernel__.object.__static__.persistence.AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl;
import org.cyk.utility.__kernel__.string.StringHelper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
@Entity @Table(name=Function.TABLE_NAME)
public class Function extends AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotNull @ManyToOne @JoinColumn(name = COLUMN_TYPE) private FunctionType type;
	
	@Transient private Collection<AdministrativeUnit> administrativeUnits;
	@Transient private Collection<String> categoriesCodes;
	
	@Override
	public Function setCode(String code) {
		return (Function) super.setCode(code);
	}
	
	@Override
	public Function setName(String name) {
		return (Function) super.setName(name);
	}
	
	public Function setTypeFromCode(String code) {
		if(StringHelper.isBlank(code))
			this.type = null;
		else
			this.type = InstanceGetter.getInstance().getByBusinessIdentifier(FunctionType.class, code);
		return this;
	}
	
	public static final String FIELD_TYPE = "type";
	public static final String FIELD_ADMINISTRATIVE_UNITS = "administrativeUnits";
	
	public static final String COLUMN_TYPE = "typfonc";
	
	public static final String TABLE_NAME = "fonction_budgetaire";	
}
