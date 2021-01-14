package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

public class Controller {

    @FXML
    TextField factura;
    @FXML
    ComboBox tipoprod;
    @FXML
    TextField importe;
    @FXML
    TextField importetotal;
    @FXML
    TextField descuento;
    @FXML
    TextField total;
    @FXML
    TextField pago;
    @FXML
    TextField vuelto;
    @FXML
    TextField producto;
    @FXML
    TextField cantidad;
    @FXML
    Button agregar;
    @FXML
    Button calcular;
    @FXML
    Button cobrar;
    @FXML
    TableView tabla;


    ObservableList<String> comboProducto = FXCollections.observableArrayList("Hamburguesas", "Bebidas", "Postres");

    public void initialize(){
        tipoprod.setItems(comboProducto);
        crearTabla();
    }

    private void crearTabla(){

        TableColumn c1=new TableColumn("tipoprod");
        c1.setCellValueFactory(new PropertyValueFactory<>("tipoprod"));

        TableColumn c2=new TableColumn("producto");
        c2.setCellValueFactory(new PropertyValueFactory<>("producto"));

        TableColumn c3=new TableColumn("importe");
        c3.setCellValueFactory(new PropertyValueFactory<>("importe"));

        TableColumn c4=new TableColumn("cantidad");
        c4.setCellValueFactory(new PropertyValueFactory<>("cantidad"));

        tabla.getColumns().addAll(c1,c2,c3,c4);
    }

    public void agregar (MouseEvent mouseEvent){

        if (producto.getText().length()<5){
            MnjError("El campo producto debe ser mayor a 5");
            return;
        }
        if (!Numerico(factura.getText())||!Numerico(importe.getText())||!Numerico(cantidad.getText())){
            MnjError("El campo factura, importe y cantidad deben ser numericos");
            return;
        }

        Constructor c = new Constructor();
        c.setFactura(Integer.parseInt(factura.getText()));
        c.setImporte(Integer.parseInt(importe.getText()));
        c.setCantidad(Integer.parseInt(cantidad.getText()));
        c.setProducto(producto.getText());
        c.setTipoprod(tipoprod.getSelectionModel().getSelectedItem().toString());

        tabla.getItems().addAll(c);

        limpiar();
    }

    public void calcular (MouseEvent mouseEvent){

        float imp=0;
        float imp2=0;
        for (int i=0;i<tabla.getItems().size();i++){

            Constructor con=(Constructor) tabla.getItems().get(i);
            imp=con.getImporte()*con.getCantidad();
            imp2=imp2+imp;

        }

        importetotal.setText(String.valueOf(imp2));
        descuento.setText("10%");
        total.setText(String.valueOf((imp2)-(imp2*10/100)));
    }

    public void cobrar (MouseEvent mouseEvent){


        float imp=0;
        float imp2=0;
        float imp3=0;
        float vuelto1=0;
        for (int i=0;i<tabla.getItems().size();i++){

            Constructor con=(Constructor) tabla.getItems().get(i);
            imp=con.getImporte()*con.getCantidad();
            imp2=imp2+imp;
            imp3=((imp2)-(imp2*10/100));
        }


        if (Float.parseFloat(pago.getText())>=imp3){
            vuelto1=(Integer.parseInt(pago.getText()))-imp3;
            vuelto.setText(String.valueOf(vuelto1));
            limpiar2();
            MnjCobrado("El pago fue realizado con exito");
        } else {
            MnjError("No recauda el dinero suficiente para abonar la factura, por favor intente de nuevo");
            return;
        }
    }



    public void MnjError (String c){
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle("Error");
        a.setContentText(c);
        a.showAndWait();
    }

    public void MnjCobrado (String c){
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("Pago realizado");
        a.setContentText(c);
        a.showAndWait();
    }

    private boolean Numerico (String cadena){
        try{
            Integer.parseInt(cadena);
            return true;
        } catch(Exception e){
            return false;
        }
    }

    public void limpiar(){
        producto.setText("");
        cantidad.setText("");
        importe.setText("");
    }

    public void limpiar2(){
        producto.setText("");
        cantidad.setText("");
        importe.setText("");
        factura.setText("");
        tipoprod.setValue("");
        tabla.setItems(FXCollections.observableArrayList());
    }


}
