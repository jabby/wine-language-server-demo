package fr.jabbytechs.lsp4j.wls.parser;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Test;

import com.jenkov.parsers.core.IndexBuffer;

import fr.jabbytechs.lsp4j.wls.parser.core.DataCharBuffer;

public class WineParserTest {

	@Test
	public void testParse() throws Exception {

		Path path = Paths.get(ClassLoader.getSystemResource("example.wd").toURI());

		String collect = Files.lines(path).collect(Collectors.joining(""));

		DataCharBuffer dataBuffer = new DataCharBuffer();
		dataBuffer.data = collect.toCharArray();
		dataBuffer.length = dataBuffer.data.length;

		IndexBuffer tokenBuffer = new IndexBuffer(dataBuffer.data.length, true);
		IndexBuffer elementBuffer = new IndexBuffer(dataBuffer.data.length, true);

		WineParser parser = new WineParser(tokenBuffer, elementBuffer);

		parser.parse(dataBuffer);

		Assert.assertEquals(WineElementTypes.WINE_OBJECT_START, elementBuffer.type[0]);
		Assert.assertEquals(WineElementTypes.WINE_PROPERTY_NAME, elementBuffer.type[1]);
		Assert.assertEquals(WineElementTypes.WINE_PROPERTY_VALUE_STRING, elementBuffer.type[2]);
		Assert.assertEquals(WineElementTypes.WINE_PROPERTY_NAME, elementBuffer.type[3]);
		Assert.assertEquals(WineElementTypes.WINE_PROPERTY_VALUE_STRING, elementBuffer.type[4]);
		Assert.assertEquals(WineElementTypes.WINE_PROPERTY_NAME, elementBuffer.type[5]);
		Assert.assertEquals(WineElementTypes.WINE_PROPERTY_VALUE_STRING, elementBuffer.type[6]);
		Assert.assertEquals(WineElementTypes.WINE_PROPERTY_NAME, elementBuffer.type[7]);
		Assert.assertEquals(WineElementTypes.WINE_PROPERTY_VALUE_STRING, elementBuffer.type[8]);
		Assert.assertEquals(WineElementTypes.WINE_PROPERTY_NAME, elementBuffer.type[9]);
		Assert.assertEquals(WineElementTypes.WINE_PROPERTY_VALUE_STRING, elementBuffer.type[10]);
		Assert.assertEquals(WineElementTypes.WINE_PROPERTY_NAME, elementBuffer.type[11]);
		Assert.assertEquals(WineElementTypes.WINE_PROPERTY_VALUE_STRING, elementBuffer.type[12]);
		Assert.assertEquals(WineElementTypes.WINE_OBJECT_END, elementBuffer.type[13]);

	}
}
