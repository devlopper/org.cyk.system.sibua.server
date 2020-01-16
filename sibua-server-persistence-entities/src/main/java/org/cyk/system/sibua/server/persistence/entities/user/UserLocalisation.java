package org.cyk.system.sibua.server.persistence.entities.user;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.cyk.system.sibua.server.persistence.entities.Localisation;
import org.cyk.utility.__kernel__.object.__static__.persistence.AbstractIdentifiableSystemScalarStringImpl;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
@Entity @Table(name=UserLocalisation.TABLE_NAME,uniqueConstraints = {
		@UniqueConstraint(columnNames = {UserLocalisation.COLUMN_USER,UserLocalisation.COLUMN_LOCALISATION})
})
public class UserLocalisation extends AbstractIdentifiableSystemScalarStringImpl implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@NotNull @ManyToOne @JoinColumn(name = COLUMN_USER) private User user;
	@NotNull @ManyToOne @JoinColumn(name = COLUMN_LOCALISATION) private Localisation localisation;
	
	@Override
	public UserLocalisation setIdentifier(String identifier) {
		return (UserLocalisation) super.setIdentifier(identifier);
	}
	
	public static final String FIELD_USER = "user";
	public static final String FIELD_LOCALISATION = "localisation";
	
	public static final String COLUMN_USER = User.TABLE_NAME;
	public static final String COLUMN_LOCALISATION = Localisation.TABLE_NAME;
	
	public static final String TABLE_NAME = "localisation_demandee";	
}