package org.cyk.system.sibua.server.representation.api;
import javax.ws.rs.Path;

import org.cyk.system.sibua.server.representation.entities.SectionDto;
import org.cyk.utility.server.representation.RepresentationEntity;

@Path(SectionRepresentation.PATH)
public interface SectionRepresentation extends RepresentationEntity<SectionDto> {
	
	String PATH = "/section";
	
}
