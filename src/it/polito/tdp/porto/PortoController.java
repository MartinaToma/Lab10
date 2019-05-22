package it.polito.tdp.porto;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.porto.model.Author;
import it.polito.tdp.porto.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class PortoController {

	private Model model;
	
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Author> boxPrimo;

    @FXML
    private ComboBox<Author> boxSecondo;

    @FXML
    private TextArea txtResult;

    @FXML
    void handleCoautori(ActionEvent event) {

    	Author a=boxPrimo.getValue();
    	List<Author> vicini=this.model.listaVicini(a);
    	for(Author aa:vicini) {
    		txtResult.setText(aa.toString()+"\n");
    	}
    	
    	List<Author> all=this.model.autori();
    	all.removeAll(vicini);
    	all.remove(a);
    	boxSecondo.getItems().addAll(all);
    	
    	
    }

    @FXML
    void handleSequenza(ActionEvent event) {
    	Author b=boxSecondo.getValue();
    	
        

    }

    @FXML
    void initialize() {
        assert boxPrimo != null : "fx:id=\"boxPrimo\" was not injected: check your FXML file 'Porto.fxml'.";
        assert boxSecondo != null : "fx:id=\"boxSecondo\" was not injected: check your FXML file 'Porto.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Porto.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model=model;
    	this.model.creaGrafo();
    	List<Author> list= this.model.autori();
    	boxPrimo.getItems().addAll(list);
    }
}
