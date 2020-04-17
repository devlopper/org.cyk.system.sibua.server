package org.cyk.system.sibua.server.persistence.impl;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.system.sibua.server.annotation.System;
import org.cyk.system.sibua.server.persistence.api.ActivitySelectQuerier;
import org.cyk.system.sibua.server.persistence.api.query.AdministrativeUnitReadingQuerier;
import org.cyk.utility.__kernel__.persistence.query.EntityReader;
import org.cyk.utility.__kernel__.persistence.query.QueryExecutorArguments;

@System
public class EntityReaderImpl extends EntityReader.AbstractImpl implements Serializable {

	@SuppressWarnings("unchecked")
	@Override
	public <T> Collection<T> readMany(Class<T> resultClass, QueryExecutorArguments arguments) {
		if(arguments != null && arguments.getQuery() != null) {
			if(AdministrativeUnitReadingQuerier.QUERY_IDENTIFIER_READ_VIEW_01.equals(arguments.getQuery().getIdentifier()))
				return (Collection<T>) AdministrativeUnitReadingQuerier.getInstance().readMany(arguments);
			if(AdministrativeUnitReadingQuerier.QUERY_IDENTIFIER_READ_VIEW_02.equals(arguments.getQuery().getIdentifier()))
				return (Collection<T>) AdministrativeUnitReadingQuerier.getInstance().readMany(arguments);
			
			if(ActivitySelectQuerier.QUERY_IDENTIFIER_READ_VIEW_01.equals(arguments.getQuery().getIdentifier()))
				return (Collection<T>) ActivitySelectQuerier.getInstance().readMany(arguments);
		}
		return super.readMany(resultClass, arguments);
	}	
}