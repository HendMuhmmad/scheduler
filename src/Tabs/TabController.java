
package Tabs;

import java.net.URL;
import java.util.ResourceBundle;

import alertbox.AlertBoxController;
import application.*;
import algorithm.FcfsScheduler;
import algorithm.*;
import ds.*;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.VBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.NumberValidator;
import com.jfoenix.validation.RequiredFieldValidator;


public class TabController  implements Initializable {

	private SampleController mainwindow ;
	private AlertBoxController alertbox;
	private Tab tab;
	public int scheduler_type;   //0--->fcfs 1--->sjf 2--->priorty 3---->RR
	public TreeItem<String> new_branch; 
	
	
	
	@FXML VBox main_vbox;
	//@FXML Label extra_label;
	@FXML VBox label_vbox;
	@FXML VBox textfield_vbox;

	//
    @FXML
     JFXTextField process_name;
    @FXML
     JFXTextField extra_textfield;
	@FXML
	 JFXTextField burst_time;
	
	//
	SjfScheduler sjf ;
	FcfsScheduler fcfs;
	priorityscheduler priority;
	RRscheduler RR;
	
	NumberAxis xAxis;
	NumberAxis yAxis ;
	public AreaChart< Number, Number> areachart;
	public Label finish_label;
	public ObservableList<ProcessItem> process_list;
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		 areachart_init();
		 main_vbox.getChildren().add(areachart);
		 //////////////
		 process_list = FXCollections.observableArrayList();
		RequiredFieldValidator validator =new RequiredFieldValidator();
		NumberValidator n_validator = new NumberValidator();
		process_name.getValidators().add(validator); 
		burst_time.getValidators().addAll(validator,n_validator); 
		extra_textfield.getValidators().addAll(validator,n_validator); 
		// mainwindow.waiting_time_column.setCellValueFactory(features -> Bindings.valueAt(features.getValue(), 0));
 
		
		 //mainwindow.waiting_time_column.setCellValueFactory(process_list ->Bindings.ValueAt(process_list.getValue(), 3));
		 //mainwindow.table.setItems(process);
	
		 /////////////

		 finish_label = new Label("ss");
		 finish_label.setId("flabel");
		 main_vbox.getChildren().add(finish_label);
		 
	}
	public void add(ActionEvent e) 
	{ 
		int error=0;
		int extra=0;
		String name="";
		int burst_num=0;
		try {
			 name=process_name.getText();
			System.out.println(name);
			 if(process_name.getText().isEmpty())  
			{
				error=1;
				process_name.validate();
			}
		} catch (Exception e2) {
		// TODO: handle exception
				error=1;
				process_name.validate();
		}
		
		try {
			 burst_num=Integer.parseInt(burst_time.getText());
		} catch (Exception e2) {
			// TODO: handle exception
			error=1;
			burst_time.validate();
		}	
		
		if(scheduler_type==2||scheduler_type==3)
		{
		try {
		 extra=Integer.parseInt(extra_textfield.getText());	
		} catch (Exception e2) {
			error=1;
			extra_textfield.validate();
		}
		
		}
		
		if(error==0) {
			extra_textfield.validate();
			burst_time.validate();
			process_name.validate();
			switch (scheduler_type) {
		case 0:
			fcfs.new_process(name, burst_num);
			break;
		case 1:
			sjf.new_process(name, burst_num);
			break;
		case 2:
			priority.new_process(name, burst_num, extra);
			break;
		case 3:
			RR.new_process(name, burst_num, extra);
			break;
		}
		
		}
		
	}
	public void SetAlertBox(AlertBoxController _alertbox)
	{
		this.alertbox=_alertbox;
		alertbox.create_button.setOnAction(e-> inputalertbox());

		alertbox.cancel_button.setOnAction(e-> close_alertbox());
		alertbox.window.setOnCloseRequest(e->close_alertbox());
		switch (scheduler_type) {
		case 0:
			fcfs_init();
			break;
		case 1:
			sjf_init();
			break;
		case 2:
			priority_init();
			break;
		case 3:
			RR_init();
			break;
		}
	}
	public void Setmaniwindow(SampleController _mainwindow,Tab _tab)
	{
		this.mainwindow=_mainwindow;
		this.tab=_tab;
		
		
		
		mainwindow.table.setItems(process_list);
		
		
		
		new_branch  = mainwindow.makeBranch(alertbox.tab_name.getText(),mainwindow.tree.getSelectionModel().getSelectedItem().getParent(),true);
	    new_branch.valueProperty().bind(alertbox.tab_name.textProperty());
	    
	}
	public void set_scheduler(int _schedular_type ) {
		this.scheduler_type=_schedular_type;
	}
	
	public void areachart_init() {
		
		xAxis =new NumberAxis(); 
		yAxis =new NumberAxis();
		areachart =new AreaChart(xAxis, yAxis);	
		xAxis.setMinorTickCount(0);
	    yAxis.setMinorTickCount(0);
	     
	    yAxis.setTickLabelsVisible(false);
	    yAxis.setTickMarkVisible(false);
	      //xAxis.setLowerBound(1);  
        areachart.setCreateSymbols(false);  
        areachart.setVerticalGridLinesVisible(false);
	    areachart.setHorizontalGridLinesVisible(false);
	    xAxis.setAnimated(false);
	    yAxis.setAnimated(false);
	    areachart.setAnimated(false);
	    
	}
	
	public void inputalertbox()
	{
		boolean isprimitive;
		if(alertbox.non_primitive_checkbox.isSelected()) 
			isprimitive=false;
		else	
			isprimitive=true;
		switch (scheduler_type) {
		case 0:
			fcfs = new FcfsScheduler(this);
			break;
		case 1:
			sjf=new SjfScheduler(this, isprimitive);
			break;
		case 2:
			priority =new priorityscheduler(this, isprimitive);
			break;
		case 3:
			RR =new RRscheduler(this);
			break;
		}		
		mainwindow.add_newfile(new_branch, tab);	//add tree item and tab to map
		alertbox.get_window().close();
	
	}
	public void close_alertbox() 
	{
		
		mainwindow.tabpane.getTabs().remove(tab);	
		 TreeItem<String> item = mainwindow.tree.getRoot().getChildren().get(scheduler_type);
		 item.getChildren().remove(new_branch);
		 alertbox.window.close();
	}
	public void finish(ActionEvent e) 
	{
		switch (scheduler_type) {
		case 0:
			fcfs.end();
			break;
		case 1:
		System.out.println("in");
			sjf.end();
			//	sjf.new_process(name, burst_num);
			break;
		case 2:
			priority.end();
			break;
		case 3:
			RR.end();
			break;
		} 
	}
	
	public void fcfs_init() 
	{
		alertbox.non_primitive_checkbox.setDisable(true);
		alertbox.primitive_checkbox.setDisable(true);
		textfield_vbox.getChildren().remove(extra_textfield);
	
		//label_vbox.getChildren().remove(extra_label);
	}
	public void sjf_init() 
	{
		alertbox.non_primitive_checkbox.setSelected(true);
		textfield_vbox.getChildren().remove(extra_textfield);
	
		//label_vbox.getChildren().remove(extra_label);
	}
	public void priority_init() 
	{
		alertbox.non_primitive_checkbox.setSelected(true);
		//extra_label.setText("Priority");
		//
		extra_textfield.setPromptText("Priority");
		//
		
	}
	public void RR_init() {
		alertbox.non_primitive_checkbox.setDisable(true);
		alertbox.primitive_checkbox.setDisable(true);
		//extra_label.setText("Quantum");
		//
		extra_textfield.setPromptText("Priority");
		//
	}

}