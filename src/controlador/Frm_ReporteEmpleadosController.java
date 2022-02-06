/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import controlador.daos.EmpleadoDao;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import modelo.enums.TipoEmpleado;

/**
 * FXML Controller class
 *
 * @author Jainer Pinta
 */
public class Frm_ReporteEmpleadosController implements Initializable {
    private @FXML ComboBox cbxCargo;
    private EmpleadoController ec = new EmpleadoController();
    private EmpleadoDao ed = new EmpleadoDao();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarCombo();
    }   
    
    @FXML
    private void generarReporte(){
        String ruta = obtenerRutaGuardado();
        if(ruta != null){
            ec.setEmpleados(ed.listar());
            if(ec.generarReporte(cbxCargo.getSelectionModel().getSelectedItem().toString(), ruta)){
                crearAlerta(Alert.AlertType.INFORMATION, "OK", "Reporte generado correctamente", "El reporte se ha guardado correctamente en la ruta especificada");
            }else{
                crearAlerta(Alert.AlertType.ERROR, "ERROR", "Reporte no generado", "El reporte no se ha podido generar correctmanete");
            }
        }
    }
    
    private String obtenerRutaGuardado(){
        DirectoryChooser dc = new DirectoryChooser();
        File directorio = dc.showDialog(new Stage());
        return directorio.getAbsolutePath();
    }
    
    private void cargarCombo(){
        cbxCargo.getItems().clear();
        cbxCargo.getItems().add("Todo");
        for (int i = 0; i < TipoEmpleado.values().length; i++) {
            cbxCargo.getItems().add(TipoEmpleado.values()[i]);
        }
    }
    
     private void crearAlerta(Alert.AlertType tipo, String titulo, String cabecera, String mensaje){
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(cabecera);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
