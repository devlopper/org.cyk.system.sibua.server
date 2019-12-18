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
@Entity @Table(name=AdministrativeUnitDestination.TABLE_NAME)
public class AdministrativeUnitDestination extends AbstractIdentifiableSystemScalarStringImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotNull @ManyToOne @JoinColumn(name = COLUMN_ADMINISTRATIVE_UNIT) private AdministrativeUnit administrativeUnit;
	@NotNull @ManyToOne @JoinColumn(name = COLUMN_DESTINATION,unique = true) private Destination destination;

	public AdministrativeUnitDestination setAdministrativeUnitFromCode(String code) {
		if(StringHelper.isBlank(code))
			this.administrativeUnit = null;
		else
			this.administrativeUnit = InstanceGetter.getInstance().getByBusinessIdentifier(AdministrativeUnit.class, code);
		return this;
	}
	
	public AdministrativeUnitDestination setDestinationFromCode(String code) {
		if(StringHelper.isBlank(code))
			this.destination = null;
		else
			this.destination = InstanceGetter.getInstance().getByBusinessIdentifier(Destination.class, code);
		return this;
	}
	
	public static final String FIELD_ADMINISTRATIVE_UNIT = "administrativeUnit";
	public static final String FIELD_DESTINATION = "destination";
	
	public static final String COLUMN_ADMINISTRATIVE_UNIT = "ua_code";
	public static final String COLUMN_DESTINATION = "dest_code";
	
	public static final String TABLE_NAME = "ua_dest";
	
}
