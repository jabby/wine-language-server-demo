package fr.jabbytechs.lsp4j.wls.server;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import fr.jabbytechs.lsp4j.wls.model.Attribute;
import fr.jabbytechs.lsp4j.wls.model.WineDescription;

public class WineCompletionProcessor {
	private static List<WineDescription> wineDescriptions;

	static {
		try {
			wineDescriptions = readFile();
		} catch (URISyntaxException | IOException e) {
			e.printStackTrace();
		}
	}

	public static List<String> process(Attribute attribute, String designation) {

		List<String> results = new ArrayList<>();

		switch (attribute) {
		case DESIGNATION:
			results.addAll(wineDescriptions.stream().map(w -> w.getDesignation()).collect(Collectors.toList()));
			break;
		case GROUNDS:
			results.addAll(wineDescriptions.stream().filter(w -> w.getDesignation().equals(designation))
					.map(w -> w.getGrounds()).collect(Collectors.toList()).get(0));
			break;
		case LOCATIONS:
			results.addAll(wineDescriptions.stream().filter(w -> w.getDesignation().equals(designation))
					.map(w -> w.getLocations()).collect(Collectors.toList()).get(0));
			break;
		case COLORS:
			results.addAll(wineDescriptions.stream().filter(w -> w.getDesignation().equals(designation))
					.map(w -> w.getColors()).collect(Collectors.toList()).get(0));
			break;
		default:
			results.addAll(Arrays.asList("name", "designation", "location", "ground", "grapeVariety", "color"));
			break;
		}

		return results;
	}

	private static List<WineDescription> readFile() throws URISyntaxException, IOException {
		Path path = Paths.get(ClassLoader.getSystemResource("wines.json").toURI());

		String content = new String(Files.readAllBytes(path));
		Gson gson = new Gson();
		List<WineDescription> wineDescriptions = gson.fromJson(content, new TypeToken<ArrayList<WineDescription>>() {
		}.getType());
		return wineDescriptions;
	}

}
