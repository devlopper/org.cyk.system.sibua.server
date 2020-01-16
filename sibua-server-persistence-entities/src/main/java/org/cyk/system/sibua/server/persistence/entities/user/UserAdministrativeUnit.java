package org.cyk.system.sibua.server.persistence.entities.user;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.cyk.system.sibua.server.persistence.entities.AdministrativeUnit;
import org.cyk.utility.__kernel__.object.__static__.persistence.AbstractIdentifiableSystemScalarStringImpl;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
@Entity @Table(name=UserAdministrativeUnit.TABLE_NAME,uniqueConstraints = {
		@UniqueConstraint(columnNames = {UserAdministrativeUnit.COLUMN_USER,UserAdministrativeUnit.COLUMN_ADMINISTRATIVE_UNIT})
})
public class UserAdministrativeUnit extends AbstractIdentifiableSystemScalarStringImpl implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@NotNull @ManyToOne @JoinColumn(name = COLUMN_USER) private User user;
	@NotNull @ManyToOne @JoinColumn(name = COLUMN_ADMINISTRATIVE_UNIT) private AdministrativeUnit administrativeUnit;
	
	@Override
	public UserAdministrativeUnit setIdentifier(String identifier) {
		return (UserAdministrativeUnit) super.setIdentifier(identifier);
	}
	
	public static final String FIELD_USER = "user";
	public static final String FIELD_ADMINISTRATIVE_UNIT = "administrativeUnit";
	
	public static final String COLUMN_USER = User.TABLE_NAME;
	public static final String COLUMN_ADMINISTRATIVE_UNIT = AdministrativeUnit.TABLE_NAME;
	
	public static final String TABLE_NAME = "unite_administrative_demandee";	
}