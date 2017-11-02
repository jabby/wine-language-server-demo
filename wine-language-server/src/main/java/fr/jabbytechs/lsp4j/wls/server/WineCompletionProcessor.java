package fr.jabbytechs.lsp4j.wls.server;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import fr.jabbytechs.lsp4j.wls.model.Attribute;
import fr.jabbytechs.lsp4j.wls.model.WineDescription;

public class WineCompletionProcessor {
	public static List<WineDescription> wineDescriptions = new ArrayList<>();

	static {
		try {
			wineDescriptions = readFile();
		} catch (URISyntaxException | IOException e) {
			e.printStackTrace();
		}
	}

	public static List<String> process(Attribute attribute, String designation) {

		List<String> results = new ArrayList<>();

		List<List<String>> list = null;

		if (attribute != null) {
			switch (attribute) {
			case DESIGNATION:
				results.addAll(wineDescriptions.stream().map(w -> w.getDesignation()).collect(Collectors.toList()));
				break;
			case GROUND:
				if (designation != null) {
					list = wineDescriptions.stream()
							.filter(w -> w.getDesignation().toLowerCase().equals(designation.toLowerCase()))
							.map(w -> w.getGrounds()).collect(Collectors.toList());
					if (!list.isEmpty()) {
						results.addAll(list.get(0));
					}
				}
				break;
			case LOCATION:
				if (designation != null) {
					list = wineDescriptions.stream()
							.filter(w -> w.getDesignation().toLowerCase().equals(designation.toLowerCase()))
							.map(w -> w.getLocations()).collect(Collectors.toList());
					if (!list.isEmpty()) {
						results.addAll(list.get(0));
					}
				}
				break;
			case COLOR:
				if (designation != null) {
					list = wineDescriptions.stream()
							.filter(w -> w.getDesignation().toLowerCase().equals(designation.toLowerCase()))
							.map(w -> w.getColors()).collect(Collectors.toList());
					if (!list.isEmpty()) {
						results.addAll(list.get(0));
					}
				}
				break;
			default:
				results.addAll(Arrays.asList("name", "designation", "location", "ground", "grapeVariety", "color"));
				break;
			}
		} else {
			results.addAll(Arrays.asList("name", "designation", "location", "ground", "grapeVariety", "color"));
		}

		Collections.sort(results);
		return results;
	}

	private static List<WineDescription> readFile() throws URISyntaxException, IOException {

		String content = "[\n" + "   {\n" + "      \"designation\":\"Medoc\",\n" + "      \"locations\":[\n"
				+ "         \"Arcins\",\n" + "         \"Arsac\",\n" + "         \"Avensan\",\n"
				+ "         \"Bégadan\",\n" + "         \"Blaignan\",\n" + "         \"Blanquefort\",\n"
				+ "         \"Brach\",\n" + "         \"Cantenac\",\n" + "         \"Carcans\",\n"
				+ "         \"Castelnau-de-Médoc\",\n" + "         \"Cissac-Médoc\",\n"
				+ "         \"Civrac-en-Médoc\",\n" + "         \"Couquèques\",\n" + "         \"Cussac-Fort-Médoc\",\n"
				+ "         \"Eysines\",\n" + "         \"Gaillan-en-Médoc\",\n" + "         \"Grayan-et-l'Hôpital\",\n"
				+ "         \"Hourtin\",\n" + "         \"Jau-Dignac-et-Loirac\",\n" + "         \"Labarde\",\n"
				+ "         \"Lacanau\",\n" + "         \"Lamarque\",\n" + "         \"LeHaillan\",\n"
				+ "         \"LePian-Médoc\",\n" + "         \"LePorge(historiquementenBuch)\",\n"
				+ "         \"LeTaillan-Médoc\",\n" + "         \"LeTemple\",\n" + "         \"LeVerdon-sur-Mer\",\n"
				+ "         \"Lesparre-Médoc\",\n" + "         \"Listrac-Médoc\",\n" + "         \"Ludon-Médoc\",\n"
				+ "         \"Macau\",\n" + "         \"Margaux\",\n" + "         \"Moulis-en-Médoc\",\n"
				+ "         \"Naujac-sur-Mer\",\n" + "         \"Ordonnac\",\n" + "         \"Parempuyre\",\n"
				+ "         \"Pauillac\",\n" + "         \"Prignac-en-Médoc\",\n" + "         \"Queyrac\",\n"
				+ "         \"Saint-Aubin-de-Médoc\",\n" + "         \"Saint-Christoly-Médoc\",\n"
				+ "         \"Saint-Estèphe\",\n" + "         \"Saint-Germain-d'Esteuil\",\n"
				+ "         \"Sainte-Hélène\",\n" + "         \"Saint-Julien-Beychevelle\",\n"
				+ "         \"Saint-Médard-en-Jalles\",\n" + "         \"Saint-Sauveur\",\n"
				+ "         \"Saint-Seurin-de-Cadourne\",\n" + "         \"Saint-Laurent-Médoc\",\n"
				+ "         \"Saint-Vivien-de-Médoc\",\n" + "         \"Saint-Yzans-de-Médoc\",\n"
				+ "         \"Salaunes\",\n" + "         \"Saumos\",\n" + "         \"Soulac-sur-Mer\",\n"
				+ "         \"Soussans\",\n" + "         \"Talais\",\n" + "         \"Valeyrac\",\n"
				+ "         \"Vendays-Montalivet\",\n" + "         \"Vensac\",\n" + "         \"Vertheuil\"\n"
				+ "      ],\n" + "      \"grounds\": [\"Graviers\", \"Argilo-calcaire\"],\n"
				+ "      \"grapesVarietyByColor\": {\n"
				+ "      	\"red\": [\"Merlot\", \"Cabernet Sauvignon\", \"Cabernet Franc\", \"Malbec\", \"Petit Verdot\"]\n"
				+ "      },\n" + "      \"colors\": [\"red\"]      \n" + "   }, {\n"
				+ "      \"designation\":\"Saint Emilion\",\n" + "      \"locations\":[\n"
				+ "      	\"Saint Emilion\"\n" + "      ],\n"
				+ "      \"grounds\": [\"Calcaire\", \"Argilo-calcaire\", \"Graviers\"],\n"
				+ "      \"grapesVarietyByColor\": {\n" + "      	\"red\": [\"Merlot\", \"Cabernet Franc\"]\n"
				+ "      },\n" + "      \"colors\": [\"red\"]      \n" + "   }, {\n"
				+ "      \"designation\":\"Sauternes\",\n" + "      \"locations\":[\n"
				+ "      	 \"Barsac\", \"Bommes\", \"Fargues\", \"Preignac\", \"Sauternes\"\n" + "      ],\n"
				+ "      \"grounds\": [\"Calcaire\", \"Argilo-calcaire\", \"Graviers\"],\n"
				+ "      \"grapesVarietyByColor\": {\n"
				+ "      	\"white\": [\"Sauvignon\", \"Sémillon\", \"Muscadelle\"]\n" + "      },\n"
				+ "      \"colors\": [\"red\"]      \n" + "   }, {\n" + "      \"designation\":\"Pessac Leognan\",\n"
				+ "      \"locations\":[\n"
				+ "      	  \"Cadaujac\", \"Canéjan\", \"Gradignan\", \"Léognan\", \"Martillac\", \"Mérignac\", \"Pessac\", \"Saint-Médard-d'Eyrans\", \"Talence\", \"Villenave-d'Ornon\"\n"
				+ "      ],\n" + "      \"grounds\": [\"Graviers\"],\n" + "      \"grapesVarietyByColor\": {\n"
				+ "      	\"white\": [\"Sauvignon\", \"Sémillon\"],\n"
				+ "      	\"red\": [\"Cabernet\", \"Sauvignon\", \"Merlot\"]\n" + "      },\n"
				+ "      \"colors\": [\"red\", \"white\"]      \n" + "   }\n" + "]";
		Gson gson = new Gson();
		List<WineDescription> wineDescriptions = gson.fromJson(content, new TypeToken<ArrayList<WineDescription>>() {
		}.getType());
		return wineDescriptions;
	}
}
