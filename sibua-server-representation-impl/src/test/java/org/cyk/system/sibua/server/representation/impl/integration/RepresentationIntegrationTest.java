package org.cyk.system.sibua.server.representation.impl.integration;

import java.util.ArrayList;
import java.util.List;

import org.cyk.system.sibua.server.business.api.user.UserFileBusiness;
import org.cyk.system.sibua.server.persistence.api.user.UserFilePersistence;
import org.cyk.system.sibua.server.persistence.entities.user.UserFileType;
import org.cyk.system.sibua.server.representation.api.user.UserRepresentation;
import org.cyk.system.sibua.server.representation.entities.user.FileDto;
import org.cyk.system.sibua.server.representation.entities.user.UserDto;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.server.representation.test.arquillian.AbstractRepresentationArquillianIntegrationTestWithDefaultDeployment;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class RepresentationIntegrationTest extends AbstractRepresentationArquillianIntegrationTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;
	
	@Test
	public void user_create() throws Exception{
		UserDto userDto = new UserDto();
		userDto.setElectronicMailAddress("a");
		__inject__(UserRepresentation.class).createOne(userDto);
	}

	@Test
	public void user_update() throws Exception{
		UserDto userDto = new UserDto();
		userDto.setIdentifier("1");
		userDto.setElectronicMailAddress("a");
		__inject__(UserRepresentation.class).createOne(userDto);
		userDto = (UserDto) __inject__(UserRepresentation.class).getOne("1", "system", null).getEntity();
		__inject__(UserRepresentation.class).updateOne(userDto,"files");
	}
	
	@Test
	public void user_update_files_01() throws Exception{
		UserDto user = new UserDto();
		user.setIdentifier("1");
		user.setElectronicMailAddress("a");
		__inject__(UserRepresentation.class).createOne(user);
		
		user = (UserDto) __inject__(UserRepresentation.class).getOne("1", "system", "identifier,files").getEntity();
		assertThat(user.getFiles()).isEmpty();
		
		FileDto file = new FileDto();
		file.setExtension("txt");
		file.setBytes("a".getBytes());
		file.setType(UserFileType.ADMINISTRATIVE_CERTIFICATE);
		user.setFiles(new ArrayList<FileDto>(List.of(file)));
		__inject__(UserRepresentation.class).updateOne(user,"files");
	}
	
	@Test
	public void user_update_files_02() throws Exception{
		assertThat(__inject__(UserFilePersistence.class).count()).isEqualTo(0);
		UserDto user = new UserDto();
		user.setIdentifier("1");
		user.setElectronicMailAddress("a");
		FileDto file = new FileDto();
		file.setReference("789");
		file.setName("a.txt");
		file.setBytes("a".getBytes());
		file.setType(UserFileType.ADMINISTRATIVE_CERTIFICATE);
		user.setFiles(new ArrayList<FileDto>(List.of(file)));
		__inject__(UserRepresentation.class).createOne(user);
		
		assertThat(__inject__(UserFilePersistence.class).count()).isEqualTo(1);
		user = (UserDto) __inject__(UserRepresentation.class).getOne("1", "system", "identifier,files,userFiles").getEntity();
		assertThat(user).isNotNull();
		file = CollectionHelper.getFirst(user.getFiles());
		assertThat(CollectionHelper.getFirst(user.getUserFiles()).getReference()).isEqualTo("789");
		assertThat(file.getExtension()).isEqualTo("txt");
		assertThat(new String(file.getBytes())).isEqualTo("a");
		file = new FileDto();
		file.setReference("789");
		file.setName("a.png");
		file.setExtension("png");
		file.setBytes("b".getBytes());
		user.setFiles(new ArrayList<>(List.of(file)));
		__inject__(UserFileBusiness.class).deleteAll();
		__inject__(UserRepresentation.class).updateOne(user,"files");
		
		assertThat(__inject__(UserFilePersistence.class).count()).isEqualTo(1);
		user = (UserDto) __inject__(UserRepresentation.class).getOne("1", "system", "identifier,files,userFiles").getEntity();
		assertThat(user).isNotNull();
		file = CollectionHelper.getFirst(user.getFiles());
		assertThat(CollectionHelper.getFirst(user.getUserFiles()).getReference()).isEqualTo("789");
		assertThat(file.getExtension()).isEqualTo("png");
		assertThat(new String(file.getBytes())).isEqualTo("b");
		file.setReference("aze");
		__inject__(UserRepresentation.class).updateOne(user,"files");
		
		user = (UserDto) __inject__(UserRepresentation.class).getOne("1", "system", "identifier,files,userFiles").getEntity();
		assertThat(user).isNotNull();
		file = CollectionHelper.getFirst(user.getFiles());
		assertThat(CollectionHelper.getFirst(user.getUserFiles()).getReference()).isEqualTo("aze");
		assertThat(file.getExtension()).isEqualTo("png");
		assertThat(new String(file.getBytes())).isEqualTo("b");
		
	}
	
}
