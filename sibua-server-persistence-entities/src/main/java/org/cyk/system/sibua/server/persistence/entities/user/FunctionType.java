package org.cyk.system.sibua.server.persistence.entities.user;

import java.io.Serializable;

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
@Entity @Table(name=FunctionType.TABLE_NAME)
public class FunctionType extends AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotNull @ManyToOne @JoinColumn(name = COLUMN_CATEGORY) private FunctionCategory category;
	
	@Transient private AdministrativeUnit administrativeUnit;
	
	@Override
	public FunctionType setCode(String code) {
		return (FunctionType) super.setCode(code);
	}
	
	@Override
	public FunctionType setName(String name) {
		return (FunctionType) super.setName(name);
	}
	
	public FunctionType setCategoryFromCode(String code) {
		if(StringHelper.isBlank(code))
			this.category = null;
		else
			this.category = InstanceGetter.getInstance().getByBusinessIdentifier(FunctionCategory.class, code);
		return this;
	}
	
	public static final String FIELD_CATEGORY = "category";
	public static final String FIELD_ADMINISTRATIVE_UNIT = "administrativeUnit";
	
	public static final String COLUMN_CATEGORY = "catfonc";
	
	public static final String TABLE_NAME = "type_fonction_budgetaire";	
	
	
}
