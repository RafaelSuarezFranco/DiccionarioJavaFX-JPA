/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ies.javafxdiccionario2.view;

import com.ies.javafxdiccionario2.controller.PalabraJpaController;
import com.ies.javafxdiccionario2.model.Palabra;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * FXML Controller class
 *
 * @author usuario
 */
public class FormulariosController implements Initializable {

    @FXML
    private Button btnlimpiar;
    @FXML
    private Button btnalta;
    @FXML
    private Button btnbaja;
    @FXML
    private Button btncerrar;
    @FXML
    private TextField txtnombre;
    @FXML
    private TextArea txadescripcion;
    @FXML
    private TextField txtfiltro;
    @FXML
    private Button btnfiltrar;
    @FXML
    private ListView<String> lstpalabras;
    @FXML
    private Button btncerrar2;
    @FXML
    private Button btndescripcion;
    @FXML
    private TextField txtvistanombre;
    @FXML
    private TextArea txavistadescripcion;
    @FXML
    private TabPane tbppanel;
    @FXML
    private Button btncerrar3;

    private ObservableList<Palabra> palabras;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtvistanombre.setEditable(false);
        txavistadescripcion.setEditable(false);

        actualizarLista();

    }

    public void actualizarLista() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistence");
        PalabraJpaController pal = new PalabraJpaController(emf);
        List<Palabra> lista = pal.findPalabraEntities();
        
        this.lstpalabras.getItems().clear();//vaciamos la lista primero
        palabras = FXCollections.observableArrayList();
        for (Palabra p1 : lista) {
            if (p1.getBaja() == 0) {//añadimos el nombre de aquellas cuya baja sea 0
                this.lstpalabras.getItems().add(p1.getPalabra());
            }
        }
    }

    @FXML
    private void limpiarFormulario(ActionEvent event) {
        txtnombre.setText("");
        txadescripcion.setText("");
    }

    @FXML
    private void darAlta(ActionEvent event) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistence");
        PalabraJpaController pal = new PalabraJpaController(emf);
        //List<Palabra> lista = pal.findPalabraEntities();
        //txtnombre.setText(lista.get(0).getPalabra());
        String nombre = txtnombre.getText();
        String descrip = txadescripcion.getText();
        if (!nombre.equals("") && !descrip.equals("")) {
            try {
                Palabra p = new Palabra(nombre, 0, descrip);
                pal.create(p);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setTitle("Atención");
                alert.setContentText("La palabra se ha añadido al diccionario");
                alert.showAndWait();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText(null);
                alert.setTitle("Atención");
                alert.setContentText("La palabra ya existe");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setTitle("Atención");
            alert.setContentText("Rellena todos los campos");
            alert.showAndWait();
        }
        actualizarLista();
    }

    @FXML
    private void darBaja(ActionEvent event) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistence");
        PalabraJpaController pal = new PalabraJpaController(emf);
        List<Palabra> lista = pal.findPalabraEntities();

        String nombre = txtnombre.getText();
        if (!nombre.equals("")) {
            try {
                boolean darbaja = false;

                for (Palabra p1 : lista) {
                    if (p1.getPalabra().equals(nombre)) {
                        Palabra p = p1; //si encontramos la palabra, la guardamos

                        if (p.getBaja() == 1) {
                            darbaja = false; //si la palabra ya estaba de baja, lo mostraremos en mensaje
                        } else { //si no estaba de baja, la damos de baja.
                            darbaja = true;
                            p.setBaja(1); //cambiamos su propiedad baja
                            pal.edit(p); //la editamos
                        }
                    }
                }
                if (darbaja) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setTitle("Atención");
                    alert.setContentText("La palabra se ha dado de baja");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText(null);
                    alert.setTitle("Atención");
                    alert.setContentText("La palabra no existe o ya se dió de baja anteriormente.");
                    alert.showAndWait();
                }
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText(null);
                alert.setTitle("Error");
                alert.setContentText("No se ha podido dar de baja la palabra");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setTitle("Atención");
            alert.setContentText("Introduce una palabra para darla de baja");
            alert.showAndWait();
        }
        actualizarLista();
    }

    @FXML
    private void cerrar(ActionEvent event) {
        Stage stage = (Stage) this.btncerrar.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void filtrarPalabra(ActionEvent event) {
    }

    @FXML
    private void verDescripcion(ActionEvent event) {
    }

    public TabPane devolverPanel() {
        return this.tbppanel;
    }
}
