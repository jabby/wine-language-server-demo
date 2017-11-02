package fr.jabbytechs.lsp4j.wls.server;

import org.junit.Test;

import fr.jabbytechs.lsp4j.wls.model.Attribute;

public class WineCompletionProcessorTest {

	@Test
	public void testProcessForDesignation() throws Exception {
		WineCompletionProcessor.process(Attribute.DESIGNATION, null).forEach(s -> System.out.println(s));
	}

	@Test
	public void testProcessForGrounds() throws Exception {
		WineCompletionProcessor.process(Attribute.GROUND, "Medoc").forEach(s -> System.out.println(s));
	}

	@Test
	public void testProcessForLocations() throws Exception {
		WineCompletionProcessor.process(Attribute.LOCATION, "Medoc").forEach(s -> System.out.println(s));
	}

	@Test
	public void testProcessForColors() throws Exception {
		WineCompletionProcessor.process(Attribute.COLOR, "Pessac Leognan").forEach(s -> System.out.println(s));
	}
}