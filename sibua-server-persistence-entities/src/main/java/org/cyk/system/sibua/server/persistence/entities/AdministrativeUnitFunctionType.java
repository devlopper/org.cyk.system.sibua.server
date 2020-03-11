package org.cyk.system.sibua.server.persistence.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.cyk.system.sibua.server.persistence.entities.user.FunctionType;
import org.cyk.utility.__kernel__.object.__static__.persistence.AbstractIdentifiableSystemScalarStringImpl;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
@Entity @Table(name=AdministrativeUnitFunctionType.TABLE_NAME,uniqueConstraints= {
		@UniqueConstraint(name=AdministrativeUnitFunctionType.UNIQUE_CONSTRAINT_ADMINISTRATIVE_UNIT_FUNCTION_TYPE
				,columnNames= {AdministrativeUnitFunctionType.COLUMN_ADMINISTRATIVE_UNIT,AdministrativeUnitFunctionType.COLUMN_FUNCTION_TYPE}
		)})
public class AdministrativeUnitFunctionType extends AbstractIdentifiableSystemScalarStringImpl implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@ManyToOne @JoinColumn(name = COLUMN_ADMINISTRATIVE_UNIT) @NotNull private AdministrativeUnit administrativeUnit;
	@ManyToOne @JoinColumn(name = COLUMN_FUNCTION_TYPE) @NotNull private FunctionType functionType;
	
	@Override
	public AdministrativeUnitFunctionType setIdentifier(String identifier) {
		return (AdministrativeUnitFunctionType) super.setIdentifier(identifier);
	}
	
	public static final String FIELD_ADMINISTRATIVE_UNIT = "administrativeUnit";
	public static final String FIELD_FUNCTION_TYPE = "functionType";
	
	public static final String TABLE_NAME = "ua_typfonction";	
	
	public static final String COLUMN_ADMINISTRATIVE_UNIT = "ua";
	public static final String COLUMN_FUNCTION_TYPE = "typfonc";
	
	public static final String UNIQUE_CONSTRAINT_ADMINISTRATIVE_UNIT_FUNCTION_TYPE = COLUMN_ADMINISTRATIVE_UNIT+"_"+COLUMN_FUNCTION_TYPE;
}