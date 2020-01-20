package org.cyk.system.sibua.server.persistence.impl.user;
import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.persistence.api.query.ReadFileByUsers;
import org.cyk.system.sibua.server.persistence.api.user.FilePersistence;
import org.cyk.system.sibua.server.persistence.entities.user.File;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.persistence.query.QueryContext;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.server.persistence.AbstractPersistenceEntityImpl;

@ApplicationScoped
public class FilePersistenceImpl extends AbstractPersistenceEntityImpl<File> implements FilePersistence,ReadFileByUsers,Serializable {
	private static final long serialVersionUID = 1L;

	private String readByUsersIdentifiers,readBySha1;
	
	@Override
	protected void __listenPostConstructPersistenceQueries__() {
		super.__listenPostConstructPersistenceQueries__();
		addQueryCollectInstances(readBySha1, "SELECT file FROM File file WHERE file.sha1 = :sha1");
		addQueryCollectInstances(readByUsersIdentifiers, "SELECT file FROM File file WHERE EXISTS (SELECT userFile FROM UserFile userFile WHERE userFile.file = file AND userFile.user.identifier IN :usersIdentifiers)");
	}
	
	@Override
	public Collection<File> readByUsersIdentifiers(Collection<String> identifiers, Properties properties) {
		if(CollectionHelper.isEmpty(identifiers))
			return null;
		if(properties == null)
			properties = new Properties();
		properties.setIfNull(Properties.QUERY_IDENTIFIER, readByUsersIdentifiers);
		return __readMany__(properties, ____getQueryParameters____(properties,identifiers));
	}
	
	@Override
	public File readBySha1(String sha1) {
		if(StringHelper.isBlank(sha1))
			return null;
		if(properties == null)
			properties = new Properties();
		properties.setIfNull(Properties.QUERY_IDENTIFIER, readBySha1);
		return __readOne__(properties, ____getQueryParameters____(properties,sha1));
	}
	
	@Override
	protected Object[] __getQueryParameters__(QueryContext queryContext, Properties properties, Object... objects) {
		if(queryContext.getQuery().isIdentifierEqualsToOrQueryDerivedFromQueryIdentifierEqualsTo(readByUsersIdentifiers)) {
			return new Object[]{"usersIdentifiers",objects[0]};
		}
		if(queryContext.getQuery().isIdentifierEqualsToOrQueryDerivedFromQueryIdentifierEqualsTo(readBySha1)) {
			return new Object[]{"sha1",objects[0]};
		}
		return super.__getQueryParameters__(queryContext, properties, objects);
	}
}