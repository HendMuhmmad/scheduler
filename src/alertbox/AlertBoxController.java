package alertbox;


import java.net.URL;
import java.util.ResourceBundle;

import application.SampleController;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

public class AlertBoxController implements Initializable 
{
	public Stage window;
	private SampleController mainwindow ;
	//
	public TreeItem<String> new_item;
	public int tabIndex;
	public Tab tab;
	//
    @FXML
	public
     JFXButton cancel_button;

    @FXML
	public
	
	 JFXCheckBox non_primitive_checkbox;

    @FXML
	public
     JFXCheckBox primitive_checkbox;

    @FXML
	public
     JFXButton create_button;

    @FXML
	public
     JFXTextField tab_name;
	public void set_window(Stage _window) {
		this.window=_window;

		
	}
	public Stage get_window() 
	{
	return this.window;		
	}
	public void Setmaniwindow(SampleController _mainwindow)
	{
		this.mainwindow=_mainwindow;
		mainwindow.getTabtextproperty().bind(tab_name.textProperty());	

	}
	
/*	public void CancelAndClose(ActionEvent e)
	{
		mainwindow.alert_window.close();
		mainwindow.tabpane.getTabs().remove(tab);
		TreeItem<String> item = mainwindow.tree.getRoot().getChildren().get(tabIndex);
		item.getChildren().remove(new_item);
   	
	}*/

	@Override 
	public void initialize(URL location, ResourceBundle resources) {
		tab_name.setText("new");
		
		
		non_primitive_checkbox.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
		    if(isNowSelected) 
		    {
		    	primitive_checkbox.setSelected(false);
		    }
		    else
		    {
		    	primitive_checkbox.setSelected(true);
		    }
		});
		primitive_checkbox.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
		    if (isNowSelected) {
		    	non_primitive_checkbox.setSelected(false);
		    }
		    else
		    {
		    	non_primitive_checkbox.setSelected(true);
		    	
		    }
		});

		
		//non_primitive_checkbox.selectedProperty().bindBidirectional(primitive_checkbox.selectedProperty()); 
		//SimpleBooleanProperty p =(SimpleBooleanProperty) primitive_checkbox.selectedProperty();
		//SimpleBooleanProperty np =(SimpleBooleanProperty) non_primitive_checkbox.selectedProperty();
		//p.bindBidirectional(np);
		// TODO Auto-generated method stub
	}
}
