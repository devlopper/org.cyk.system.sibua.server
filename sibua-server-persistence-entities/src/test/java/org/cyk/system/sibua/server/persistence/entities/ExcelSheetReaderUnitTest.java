package org.cyk.system.sibua.server.persistence.entities;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.cyk.utility.__kernel__.array.ArrayTwoDimensionString;
import org.cyk.utility.__kernel__.file.microsoft.excel.SheetGetter;
import org.cyk.utility.__kernel__.file.microsoft.excel.SheetReader;
import org.cyk.utility.__kernel__.file.microsoft.excel.WorkBookGetter;
import org.cyk.utility.__kernel__.number.Interval;
import org.cyk.utility.__kernel__.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class ExcelSheetReaderUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void read() throws Exception {
		Workbook workbook = (Workbook) WorkBookGetter.getInstance().get(getClass().getResourceAsStream("Canevas Rattachement des Activités aux Unités Administratives UA vf.xls"));
		Sheet sheet =  (Sheet) SheetGetter.getInstance().get(workbook, 0);
		ArrayTwoDimensionString array = SheetReader.getInstance().read(workbook, sheet,__inject__(Interval.class).setLow(3),__inject__(Interval.class).setLow(1));
		/*
		for(String[] arrayIndex : (String[][])array.getValue()) {
			System.out.println(String.format("%1$50s |", arrayIndex[3])+String.format("%1$55s |", arrayIndex[2])+String.format("%1$9s |"
					, StringUtils.substring(arrayIndex[1], 0, 9))+String.format("%1$11s |", StringUtils.substring(arrayIndex[0], 0, 11))+"\r\n");
		}
		*/
		assertThat(array.get(0, 0)).isEqualTo("21083010008 Prendre en charge le fonctionnement de la résidence du Ministre");
		assertThat(array.get(0, 1)).isEqualTo("332180101 Résidence du Ministre chargé du Budget");
		assertThat(array.get(0, 2)).isEqualTo("CABINET DU MINISTRE");
		assertThat(array.get(0, 3)).isEqualTo("CABINET");
	}
}
