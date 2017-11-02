package fr.jabbytechs.lsp4j.wls.model;

import static org.junit.Assert.assertEquals;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.TextDocumentPositionParams;
import org.junit.BeforeClass;
import org.junit.Test;

public class WineDocumentModelTest {

	private static WineDocumentModel model;

	@BeforeClass
	public static void setUp() throws Exception {
		Path path = Paths.get(ClassLoader.getSystemResource("example2.wd").toURI());
		String text = Files.lines(path).collect(Collectors.joining("\r"));
		model = new WineDocumentModel(text);
	}

	@Test
	public void testGetDesignation() throws Exception {
		assertEquals("Medoc", model.getDesignation());
	}

	@Test
	public void testGetAttribute() throws Exception {
		assertEquals(Attribute.NAME.name().toLowerCase(), model.getAttribute(0).key.toLowerCase());
		assertEquals(Attribute.DESIGNATION.name().toLowerCase(), model.getAttribute(1).key.toLowerCase());
		assertEquals(Attribute.LOCATION.name().toLowerCase(), model.getAttribute(2).key.toLowerCase());
		assertEquals(Attribute.GROUND.name().toLowerCase(), model.getAttribute(3).key.toLowerCase());
		assertEquals(Attribute.GRAPEVARIETY.name().toLowerCase(), model.getAttribute(4).key.toLowerCase());
		assertEquals(Attribute.COLOR.name().toLowerCase(), model.getAttribute(5).key.toLowerCase());
		assertEquals(null, model.getAttribute(6));
	}

	@Test
	public void testGetTypeAttribute() throws Exception {
		TextDocumentPositionParams positionParams = new TextDocumentPositionParams(null, new Position(6, 0));

		Attribute typeAttribute = model.getTypeAttribute(positionParams);

		assertEquals(null, typeAttribute);

	}

}
