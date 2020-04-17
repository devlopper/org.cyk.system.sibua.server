package org.cyk.system.sibua.server.persistence.impl;

import java.io.Serializable;

import org.cyk.system.sibua.server.annotation.System;
import org.cyk.system.sibua.server.persistence.api.ActivitySelectQuerier;
import org.cyk.system.sibua.server.persistence.api.query.AdministrativeUnitReadingQuerier;
import org.cyk.utility.__kernel__.persistence.query.EntityCounter;
import org.cyk.utility.__kernel__.persistence.query.QueryExecutorArguments;

@System
public class EntityCounterImpl extends EntityCounter.AbstractImpl implements Serializable {

	@Override
	public <T> Long count(Class<T> tupleClass, QueryExecutorArguments arguments) {
		if(arguments != null && arguments.getQuery() != null) {
			if(AdministrativeUnitReadingQuerier.QUERY_IDENTIFIER_COUNT_VIEW_01.equals(arguments.getQuery().getIdentifier()))
				return AdministrativeUnitReadingQuerier.getInstance().count(arguments);
			if(AdministrativeUnitReadingQuerier.QUERY_IDENTIFIER_COUNT_VIEW_02.equals(arguments.getQuery().getIdentifier()))
				return AdministrativeUnitReadingQuerier.getInstance().count(arguments);
			
			if(ActivitySelectQuerier.QUERY_IDENTIFIER_COUNT_VIEW_01.equals(arguments.getQuery().getIdentifier()))
				return ActivitySelectQuerier.getInstance().count(arguments);
		}
		return super.count(tupleClass, arguments);
	}
}