package org.cyk.system.sibua.server.business.entities;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.cyk.utility.__kernel__.report.ReportBuilder;
import org.cyk.utility.__kernel__.report.Template;
import org.cyk.utility.__kernel__.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;

public class ReportBuilderJasperUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void text_without_datasource(){
		Template template = new Template().setInputStream(IdentificationSheet.class.getResourceAsStream("report/fiche_identification.jrxml"));
		IdentificationSheet identificationSheet = IdentificationSheet.buildRandomlyOne("TITLE",null,null);
		ByteArrayOutputStream outputStream = (ByteArrayOutputStream) ReportBuilder.getInstance().build(template, new JRBeanCollectionDataSource(List.of(identificationSheet))
				, JRPdfExporter.class);
		try {
			Files.write(new File(System.getProperty("user.dir")+"/target/t"+System.currentTimeMillis()+".pdf").toPath(), outputStream.toByteArray());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
