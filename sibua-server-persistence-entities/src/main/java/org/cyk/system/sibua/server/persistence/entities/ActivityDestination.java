package org.cyk.system.sibua.server.persistence.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.cyk.utility.__kernel__.instance.InstanceGetter;
import org.cyk.utility.__kernel__.object.__static__.persistence.AbstractIdentifiableSystemScalarStringImpl;
import org.cyk.utility.__kernel__.string.StringHelper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
@Entity @Table(name=ActivityDestination.TABLE_NAME)
public class ActivityDestination extends AbstractIdentifiableSystemScalarStringImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotNull @ManyToOne @JoinColumn(name = COLUMN_ACTIVITY) private Activity activity;
	@NotNull @ManyToOne @JoinColumn(name = COLUMN_DESTINATION,unique = true) private Destination destination;
	
	public ActivityDestination setDestinationFromCode(String code) {
		if(StringHelper.isBlank(code))
			this.destination = null;
		else
			this.destination = InstanceGetter.getInstance().getByBusinessIdentifier(Destination.class, code);
		return this;
	}
	
	public ActivityDestination setActivityFromCode(String code) {
		if(StringHelper.isBlank(code))
			this.activity = null;
		else
			this.activity = InstanceGetter.getInstance().getByBusinessIdentifier(Activity.class, code);
		return this;
	}
	
	public static final String FIELD_DESTINATION = "destination";
	public static final String FIELD_ACTIVITY = "activity";
	
	public static final String COLUMN_DESTINATION = "DEST_CODE";
	public static final String COLUMN_ACTIVITY = "ATV_CODE";
	
	public static final String TABLE_NAME = Activity.TABLE_NAME+"_destination";
	
}
