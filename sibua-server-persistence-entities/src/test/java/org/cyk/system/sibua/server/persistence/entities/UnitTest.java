package org.cyk.system.sibua.server.persistence.entities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class UnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void load_section_code() {
		Pattern pattern = Pattern.compile(".*(ddd).*");
		Matcher matcher = pattern.matcher("Section: 101 Représentation Nationale");
		System.out.println(matcher.group(0));
	}
	
}
