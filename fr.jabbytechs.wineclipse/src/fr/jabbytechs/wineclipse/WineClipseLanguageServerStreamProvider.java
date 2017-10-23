package fr.jabbytechs.wineclipse;

import java.io.File;
import java.util.Arrays;

import org.eclipse.lsp4e.server.ProcessStreamConnectionProvider;
import org.eclipse.lsp4e.server.StreamConnectionProvider;

public class WineClipseLanguageServerStreamProvider extends ProcessStreamConnectionProvider
		implements StreamConnectionProvider {

	public WineClipseLanguageServerStreamProvider() {
		super(Arrays.asList("/usr/bin/java", "-jar",
				"/home/jabberwock/git/github/wine-language-server-demo/wine-language-server/target/wine-language-server-0.0.1-SNAPSHOT-jar-with-dependencies.jar"),
				new File(".").getAbsolutePath());
	}

}
