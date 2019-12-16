package org.cyk.system.sibua.server.persistence.api.query;

import java.util.Collection;
import java.util.stream.Collectors;

import org.cyk.system.sibua.server.persistence.entities.Program;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.properties.Properties;

public interface ReadByPrograms<ENTITY> {

	Collection<ENTITY> readByProgramsCodes(Collection<String> codes,Properties properties);
	
	default Collection<ENTITY> readByProgramsCodes(Collection<String> codes) {
		if(CollectionHelper.isEmpty(codes))
			return null;
		return readByProgramsCodes(codes,null);
	}
	
	default Collection<ENTITY> readByProgramsCodes(Properties properties,String...codes) {
		if(ArrayHelper.isEmpty(codes))
			return null;
		return readByProgramsCodes(CollectionHelper.listOf(codes),properties);
	}
	
	default Collection<ENTITY> readByProgramsCodes(String...codes) {
		if(ArrayHelper.isEmpty(codes))
			return null;
		return readByProgramsCodes(CollectionHelper.listOf(codes),null);
	}
	
	default Collection<ENTITY> readByPrograms(Collection<Program> programs,Properties properties) {
		if(CollectionHelper.isEmpty(programs))
			return null;
		return readByProgramsCodes(programs.stream().map(Program::getCode).collect(Collectors.toList()), properties);
	}
	
	default Collection<ENTITY> readByPrograms(Collection<Program> programs) {
		if(CollectionHelper.isEmpty(programs))
			return null;
		return readByPrograms(programs,null);
	}
	
	default Collection<ENTITY> readByPrograms(Properties properties,Program...programs) {
		if(ArrayHelper.isEmpty(programs))
			return null;
		return readByPrograms(CollectionHelper.listOf(programs),properties);
	}
	
	default Collection<ENTITY> readByPrograms(Program...programs) {
		if(ArrayHelper.isEmpty(programs))
			return null;
		return readByPrograms(CollectionHelper.listOf(programs),null);
	}
}
