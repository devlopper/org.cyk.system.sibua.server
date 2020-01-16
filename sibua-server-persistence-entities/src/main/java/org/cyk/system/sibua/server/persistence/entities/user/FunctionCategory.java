package org.cyk.system.sibua.server.persistence.entities.user;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.cyk.utility.__kernel__.object.__static__.persistence.AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
@Entity @Table(name=FunctionCategory.TABLE_NAME)
public class FunctionCategory extends AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public FunctionCategory setCode(String code) {
		return (FunctionCategory) super.setCode(code);
	}
	
	@Override
	public FunctionCategory setName(String name) {
		return (FunctionCategory) super.setName(name);
	}
	
	public static final String TABLE_NAME = "categorie_fonction_budgetaire";	
}
