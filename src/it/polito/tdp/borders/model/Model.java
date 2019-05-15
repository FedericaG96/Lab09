package it.polito.tdp.borders.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.traverse.GraphIterator;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {
	
	private BordersDAO dao ;
	private Graph<Country, DefaultEdge> grafo ;
	private List<Country> listaStati;
	private Map<Integer, Country> countryIdMap;

	public Model() {
		this.dao = new BordersDAO();
	}

	public void createGraph(int anno) {
		
		this.grafo = new SimpleGraph<>(DefaultEdge.class);
		countryIdMap = new HashMap<>();
		listaStati= dao.loadAllCountries(countryIdMap);
		
		
		List<Border> confini = new LinkedList<Border>(dao.getCountryPairs(countryIdMap, anno));
		
		if (confini.isEmpty()) {
			throw new RuntimeException("Nessuna coppia di paesi per l'anno specificato");
		}

		//Aggiungo vertici e archi
		for (Border b : confini) {
			grafo.addVertex(b.getCountry1());
			grafo.addVertex(b.getCountry2());
			grafo.addEdge(b.getCountry1(), b.getCountry2());
		}
		
		System.out.format("Inseriti: %d vertici, %d archi\n", grafo.vertexSet().size(), grafo.edgeSet().size());

		listaStati = new ArrayList<> (grafo.vertexSet());
		Collections.sort(listaStati);
		
	}
	
	//
	public Map<Integer, Country> getIdMap(){
		return countryIdMap;
	}

	public List<Country> getCountries() {
		if(listaStati == null)
			listaStati = new ArrayList<Country>();
		return listaStati;
	}

	public Map<Country, Integer> getCountryCounts() {
		
		Map<Country, Integer> statoNumConfinanti = new HashMap <Country, Integer>();
		for(Country c : grafo.vertexSet()){
			statoNumConfinanti.put(c, grafo.degreeOf(c));
		}
		return statoNumConfinanti;
	}

	public int getNumberOfConnectedComponents() {
		ConnectivityInspector<Country, DefaultEdge> connInspector = new ConnectivityInspector<Country, DefaultEdge>(grafo);
		return connInspector.connectedSets().size();
		//Ritorna una lista di set, ogni set contiene tutti i vertici di una componente connessa
	}

	
	public List<Country> trovaVicini(Country paeseScelto) {
		
		if (!grafo.vertexSet().contains(paeseScelto)) {
			throw new RuntimeException("Il paese scelto non è nel grafo");
		}

		List<Country> risultato = new ArrayList<Country>();
		//risultato = this.trovaViciniJGraphT(paeseScelto);
		//risultato = this.trovaViciniRicorsivo(paeseScelto);
		 	risultato = this.trovaViciniIterativo(paeseScelto);
		return risultato;
	}
	
	/*
	 * LIBRERIA JGRAPHT
	 */
	private List<Country> trovaViciniJGraphT(Country paeseScelto) {

		List<Country> risultato = new ArrayList<Country>();
		GraphIterator<Country, DefaultEdge> it = new BreadthFirstIterator<>(this.grafo, paeseScelto);
		
		while(it.hasNext()) {
			risultato.add(it.next());	//next() restituisce il prossimo elemento e avanza l'iteratore all'elemento successivo
		}
		return risultato;
	}	
		// Versione 2 : utilizzo un DepthFirstIterator
//		GraphIterator<Country, DefaultEdge> dfv = new DepthFirstIterator<Country, DefaultEdge>(graph,
//				selectedCountry);
//		while (dfv.hasNext()) {
//			visited.add(bfv.next());
//		}
	//return risultato;
	
	
	/*
	 * VERSIONE RICORSIVA, VISITA IN PROFONDITA
	 */
	private List<Country> trovaViciniRicorsivo(Country paeseScelto) {
		List<Country> visitati = new ArrayList<Country>();
		
		visitaRicorsiva(paeseScelto,visitati);
		return visitati;
	}
	
	private void visitaRicorsiva(Country c, List<Country> visitati) {
			visitati.add(c);
			
			for(Country country : Graphs.neighborListOf(grafo, c)) {
				if(!visitati.contains(country)) {	//FILTRO
					
					visitaRicorsiva(c, visitati);
					
				}
			}
		}

	/*
	 * VERSIONE ITERATIVA
	 */
	private List<Country> trovaViciniIterativo(Country paeseScelto) {
		List<Country> visitati = new ArrayList<Country>();
		List<Country> daVisitare = new ArrayList<Country>();
		
		visitati.add(paeseScelto); 	// Aggiungo alla lista dei vertici visitati il nodo di partenza.
		
		// Aggiungo ai vertici da visitare tutti i vertici collegati a quello inserito
		daVisitare.addAll(Graphs.neighborListOf(grafo, paeseScelto));
		
		while(daVisitare.isEmpty()==false) {
			
			Country temp = daVisitare.remove(0); 	// Rimuovi il vertice in testa alla coda
			visitati.add(temp);						// Aggiungi il nodo alla lista di quelli visitati
			List<Country> viciniTemp = Graphs.neighborListOf(grafo, temp); 
			viciniTemp.removeAll(visitati);			// Rimuovi da questa lista tutti quelli che hai già visitato.
			viciniTemp.removeAll(daVisitare);		// e quelli che sai già che devi visitare.
			daVisitare.addAll(viciniTemp);
		}
		
		return visitati;
	}

}
