package org.cyk.system.sibua.server.persistence.entities.user;

import java.io.Serializable;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.cyk.system.sibua.server.persistence.entities.Activity;
import org.cyk.utility.__kernel__.object.__static__.persistence.AbstractIdentifiableSystemScalarStringImpl;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
/*
@Entity @Table(name=UserActivity.TABLE_NAME,uniqueConstraints = {
		@UniqueConstraint(columnNames = {UserActivity.COLUMN_USER,UserActivity.COLUMN_ACTIVITY})
})
*/
public class UserActivity extends AbstractIdentifiableSystemScalarStringImpl implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@NotNull @ManyToOne @JoinColumn(name = COLUMN_USER) private User user;
	@NotNull @ManyToOne @JoinColumn(name = COLUMN_ACTIVITY) private Activity activity;
	
	@Override
	public UserActivity setIdentifier(String identifier) {
		return (UserActivity) super.setIdentifier(identifier);
	}
	
	public static final String FIELD_USER = "user";
	public static final String FIELD_ACTIVITY = "activity";
	
	public static final String COLUMN_USER = User.TABLE_NAME;
	public static final String COLUMN_ACTIVITY = Activity.TABLE_NAME;
	
	public static final String TABLE_NAME = "activite_demandee";	
}