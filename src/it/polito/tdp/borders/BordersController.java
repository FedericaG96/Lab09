/**
 * Skeleton for 'Borders.fxml' Controller Class
 */

package it.polito.tdp.borders;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import it.polito.tdp.borders.model.Border;
import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class BordersController {

	Model model;

	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;

	@FXML // URL location of the FXML file that was given to the FXMLLoader
	private URL location;

	@FXML // fx:id="txtAnno"
	private TextField txtAnno; // Value injected by FXMLLoader
	  
	@FXML
	private Button btnCalcola;

	@FXML
	private ComboBox<Country> cmbStati;

    @FXML
    private Button btnVicini;
    
	@FXML // fx:id="txtResult"
	private TextArea txtResult; // Value injected by FXMLLoader

	@FXML
	void doCalcolaConfini(ActionEvent event) {
		
		txtResult.clear();
		int anno;
		
    	try {
    	anno = Integer.parseInt(txtAnno.getText());
    	} catch(NumberFormatException e) {
    		txtResult.setText("Inserisci un numero! ");
    		return;
    	}

    	if(anno < 1816 || anno > 2016 ) {
    		txtResult.setText("Inserire un numero compreso tra 1816 e 2016! ");
    		return;
    	}
    	model.createGraph(anno);
    	
    	Map<Country, Integer> statoNumConfinanti = model.getCountryCounts();
    	for (Country country : statoNumConfinanti.keySet()) {
			txtResult.appendText(String.format("%s %d\n", country, statoNumConfinanti.get(country)));	
		}
    	
    	List<Country> listaStati = model.getCountries();
    	cmbStati.getItems().addAll(listaStati);
    	
    	txtResult.appendText(String.format("Numero componenti connesse: %d\n", model.getNumberOfConnectedComponents()));

    	
	}
	
	@FXML
	   void doTrovaVicini(ActionEvent event) {
		txtResult.clear();
		
		if(cmbStati.getItems().isEmpty()) {
			txtResult.setText("Creare un grafo");
		}
		
		Country paeseScelto = cmbStati.getValue();
		if(paeseScelto == null) {
			txtResult.setText("Selezionare un paese");
		}
		else {
			txtResult.setText(String.format("Nodi raggiungibili da %s \n",  paeseScelto));
		}
		
		List<Country> nodiRaggiungibili = model.trovaVicini(paeseScelto);
		for(Country c : nodiRaggiungibili) {
			txtResult.appendText(c.toString() + "\n");
		}
		
    }

	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize() {
		assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'Borders.fxml'.";
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Borders.fxml'.";
	}

	public void setModel(Model model) {
		this.model=model;
	}
}
