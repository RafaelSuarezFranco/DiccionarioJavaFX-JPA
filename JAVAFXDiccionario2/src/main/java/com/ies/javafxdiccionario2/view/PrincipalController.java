/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ies.javafxdiccionario2.view;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author usuario
 */
public class PrincipalController implements Initializable {

    @FXML
    private Button btnagregar;
    @FXML
    private Button btnbuscar;
    @FXML
    private Button btnsalir;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void mostrarAgregar(ActionEvent event) {
        try {

            URL url = new File("src/main/java/com/ies/javafxdiccionario2/view/Formularios.fxml").toURI().toURL();
            Parent root = FXMLLoader.load(url);
            Scene scene = new Scene(root);

            Stage stage = new Stage();
            stage.initModality(Modality.WINDOW_MODAL);
            scene.getStylesheets().add(new File("src/main/java/resources/css/principal.css").toURI().toURL().toString());
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void mostrarBuscar(ActionEvent event) {
        try {

            URL url = new File("src/main/java/com/ies/javafxdiccionario2/view/Formularios.fxml").toURI().toURL();
            FXMLLoader loader = new FXMLLoader(url);
            Parent root1 = (Parent) loader.load();

            FormulariosController controlador = loader.getController();

            Scene scene = new Scene(root1);
            Stage stage = new Stage();
            stage.initModality(Modality.WINDOW_MODAL);
            scene.getStylesheets().add(new File("src/main/java/resources/css/principal.css").toURI().toURL().toString());
            stage.setScene(scene);
            TabPane tbp = controlador.devolverPanel();
            //para abrir la otra ventana con la pestaña buscar, recuperamos el panel y seleccionamos aquí su pestaña
            tbp.getSelectionModel().select(1);
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void salir(ActionEvent event) {
        Stage stage = (Stage) this.btnsalir.getScene().getWindow();
        stage.close();
    }

}
