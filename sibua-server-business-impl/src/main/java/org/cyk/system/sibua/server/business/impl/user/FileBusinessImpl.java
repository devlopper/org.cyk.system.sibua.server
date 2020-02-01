package org.cyk.system.sibua.server.business.impl.user;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.business.api.user.FileBusiness;
import org.cyk.system.sibua.server.persistence.api.user.FilePersistence;
import org.cyk.system.sibua.server.persistence.entities.user.File;
import org.cyk.utility.__kernel__.file.FileHelper;
import org.cyk.utility.__kernel__.number.ByteHelper;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.server.business.AbstractBusinessEntityImpl;
import org.cyk.utility.server.business.BusinessFunctionCreator;

@ApplicationScoped
public class FileBusinessImpl extends AbstractBusinessEntityImpl<File, FilePersistence> implements FileBusiness,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenExecuteCreateBefore__(File file, Properties properties, BusinessFunctionCreator function) {
		super.__listenExecuteCreateBefore__(file, properties, function);
		if(file.getBytes() == null || file.getBytes().length == 0)
			throw new RuntimeException("le contenu du fichier est obligatoire");
		if(StringHelper.isBlank(file.getSha1()))
			file.setSha1(ByteHelper.buildMessageDigest(file.getBytes()));
		if(StringHelper.isBlank(file.getSha1()))
			throw new RuntimeException("impossible de calculer le sha1");
		if(StringHelper.isBlank(file.getExtension()))
			file.setExtension(FileHelper.getExtension(file.getName()));
		if(StringHelper.isBlank(file.getExtension()))
			throw new RuntimeException("l'extension du fichier est obligatoire");	
	}
	
}
