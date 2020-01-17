package org.cyk.system.sibua.server.persistence.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.cyk.utility.__kernel__.object.__static__.persistence.AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
@Entity @Table(name=AdministrativeUnitActivityType.TABLE_NAME) @Deprecated
public class AdministrativeUnitActivityType extends AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	public AdministrativeUnitActivityType setCode(String code) {
		return (AdministrativeUnitActivityType) super.setCode(code);
	}
	
	@Override
	public AdministrativeUnitActivityType setName(String name) {
		return (AdministrativeUnitActivityType) super.setName(name);
	}
	
	@Override
	public AdministrativeUnitActivityType setIdentifier(String identifier) {
		return (AdministrativeUnitActivityType) super.setIdentifier(identifier);
	}
	
	public static final String CODE_GESTIONNAIRE = "GESTIONNAIRE";
	public static final String CODE_BENEFICIAIRE = "BENEFICIAIRE";
	
	public static final String TABLE_NAME = "type_rattachement_ua_atv";	
}