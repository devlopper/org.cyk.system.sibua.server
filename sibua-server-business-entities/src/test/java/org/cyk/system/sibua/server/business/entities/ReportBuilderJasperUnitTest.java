package org.cyk.system.sibua.server.business.entities;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.cyk.utility.__kernel__.report.ReportBuilder;
import org.cyk.utility.__kernel__.report.Template;
import org.cyk.utility.__kernel__.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

import net.sf.jasperreports.engine.export.JRPdfExporter;

public class ReportBuilderJasperUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void text_without_datasource(){
		Template template = new Template().setInputStream(IdentificationSheet.class.getResourceAsStream("report/fiche_identification.jrxml"));
		ByteArrayOutputStream outputStream = (ByteArrayOutputStream) ReportBuilder.getInstance().build(template, null, JRPdfExporter.class);
		try {
			Files.write(new File(System.getProperty("user.dir")+"/target/t.pdf").toPath(), outputStream.toByteArray());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
