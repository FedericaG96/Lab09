package it.polito.tdp.borders.model;

import java.util.List;
import java.util.Map;

public class TestModel {

	public static void main(String[] args) {

		Model model = new Model();

		System.out.println("TestModel -- TODO");
		
		System.out.println("Creo il grafo relativo al 2000");
		model.createGraph(2000);
		
		List<Country> countries = model.getCountries();
		System.out.format("Trovate %d nazioni\n", countries.size());

		
		Map<Country, Integer> stats = model.getCountryCounts();
		for (Country country : stats.keySet())
			System.out.format("%s %d\n", country, stats.get(country));		
		
		System.out.format("Numero componenti connesse: %d\n", model.getNumberOfConnectedComponents());
		
		Map<Integer, Country> idMap = model.getIdMap();
		List<Country> countriesVicini = model.trovaVicini(idMap.get(2));
		for(Country c : countriesVicini) {
			System.out.format("%s \n", c);
		}
	}

}
