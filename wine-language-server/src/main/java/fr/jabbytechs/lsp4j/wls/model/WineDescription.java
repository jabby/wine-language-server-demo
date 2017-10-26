package fr.jabbytechs.lsp4j.wls.model;

import java.util.List;

public class WineDescription {

	private String name;
	private String designation;
	private List<String> locations;
	private List<String> grounds;
	private GrapeVariety grapesVarietyByColor;
	private List<String> colors;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public List<String> getLocations() {
		return locations;
	}

	public void setLocations(List<String> locations) {
		this.locations = locations;
	}

	public List<String> getGrounds() {
		return grounds;
	}

	public void setGrounds(List<String> grounds) {
		this.grounds = grounds;
	}

	public GrapeVariety getGrapesVarietyByColor() {
		return grapesVarietyByColor;
	}

	public void setGrapesVarietyByColor(GrapeVariety grapesVarietyByColor) {
		this.grapesVarietyByColor = grapesVarietyByColor;
	}

	public List<String> getColors() {
		return colors;
	}

	public void setColors(List<String> colors) {
		this.colors = colors;
	}

}
