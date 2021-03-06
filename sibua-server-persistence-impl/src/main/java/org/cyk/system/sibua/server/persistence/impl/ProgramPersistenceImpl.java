package org.cyk.system.sibua.server.persistence.impl;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collection;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.persistence.api.AdministrativeUnitPersistence;
import org.cyk.system.sibua.server.persistence.api.ProgramPersistence;
import org.cyk.system.sibua.server.persistence.api.query.ReadAdministrativeUnitByPrograms;
import org.cyk.system.sibua.server.persistence.api.query.ReadProgramBySections;
import org.cyk.system.sibua.server.persistence.entities.Program;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.persistence.query.QueryContext;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.server.persistence.AbstractPersistenceEntityImpl;
import org.cyk.utility.server.persistence.PersistenceFunctionReader;

@ApplicationScoped
public class ProgramPersistenceImpl extends AbstractPersistenceEntityImpl<Program> implements ProgramPersistence,ReadProgramBySections,Serializable {
	private static final long serialVersionUID = 1L;

	private String readBySectionsCodes;
	
	@Override
	protected void __listenPostConstructPersistenceQueries__() {
		super.__listenPostConstructPersistenceQueries__();
		addQueryCollectInstances(readBySectionsCodes, "SELECT program FROM Program program WHERE program.section.code IN :sectionsCodes ORDER BY program.code ASC");
	}
	
	@Override
	public Collection<Program> readBySectionsCodes(Collection<String> codes, Properties properties) {
		if(CollectionHelper.isEmpty(codes))
			return null;
		if(properties == null)
			properties = new Properties();
		properties.setIfNull(Properties.QUERY_IDENTIFIER, readBySectionsCodes);
		return __readMany__(properties, ____getQueryParameters____(properties,codes));
	}

	@Override
	protected String __getQueryIdentifier__(Class<?> klass, Properties properties, Object... objects) {
		if(PersistenceFunctionReader.class.equals(klass)) {
			if(Boolean.TRUE.equals(__isFilterByKeys__(properties, Program.FIELD_SECTION)))
				return readBySectionsCodes;
		}
		return super.__getQueryIdentifier__(klass, properties, objects);
	}
	
	@Override
	protected void __listenExecuteReadAfterSetFieldValue__(Program program, Field field, Properties properties) {
		super.__listenExecuteReadAfterSetFieldValue__(program, field, properties);
		if(field.getName().equals(Program.FIELD_ADMINISTRATIVE_UNITS)) {
			program.setAdministrativeUnits(((ReadAdministrativeUnitByPrograms)__inject__(AdministrativeUnitPersistence.class)).readByPrograms(program));
		}
	}
	
	@Override
	protected Object[] __getQueryParameters__(QueryContext queryContext, Properties properties,Object... objects) {
		if(queryContext.getQuery().isIdentifierEqualsToOrQueryDerivedFromQueryIdentifierEqualsTo(readBySectionsCodes)) {
			if(ArrayHelper.isEmpty(objects))
				objects = new Object[] {queryContext.getFilterByKeysValue(Program.FIELD_SECTION)};
			return new Object[]{"sectionsCodes",objects[0]};
		}
		return super.__getQueryParameters__(queryContext, properties, objects);
	}
	
}