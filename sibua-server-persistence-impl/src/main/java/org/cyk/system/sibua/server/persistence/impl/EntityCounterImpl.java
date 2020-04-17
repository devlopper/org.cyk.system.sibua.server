package org.cyk.system.sibua.server.persistence.impl;

import java.io.Serializable;

import org.cyk.system.sibua.server.annotation.System;
import org.cyk.system.sibua.server.persistence.api.query.AdministrativeUnitQuerier;
import org.cyk.utility.__kernel__.persistence.query.EntityCounter;
import org.cyk.utility.__kernel__.persistence.query.QueryExecutorArguments;

@System
public class EntityCounterImpl extends EntityCounter.AbstractImpl implements Serializable {

	@Override
	public <T> Long count(Class<T> tupleClass, QueryExecutorArguments arguments) {
		if(arguments != null && arguments.getQuery() != null && AdministrativeUnitQuerier.QUERY_IDENTIFIER_COUNT_VIEW_01.equals(arguments.getQuery().getIdentifier()))
			return AdministrativeUnitQuerier.getInstance().count(arguments);
		if(arguments != null && arguments.getQuery() != null && AdministrativeUnitQuerier.QUERY_IDENTIFIER_COUNT_VIEW_02.equals(arguments.getQuery().getIdentifier()))
			return AdministrativeUnitQuerier.getInstance().count(arguments);
		return super.count(tupleClass, arguments);
	}
}