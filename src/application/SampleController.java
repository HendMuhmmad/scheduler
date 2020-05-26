package application;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.xml.transform.Templates;




import Tabs.TabController;
import alertbox.*;


import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

public class SampleController implements Initializable  {
	//public int scheduler_type;   //0--->fcfs 1--->sjf 2--->priorty 3---->RR
	public Tab new_tab;
	public FXMLLoader tab_loader;
	public AlertBoxController alertboxcontroller;
	public TreeItem<String> root,FCFS,SJF,PRIORITY,ROUNDROBIN,new_FCFS,new_SJF,new_PRIORITY,new_ROUNDROBIN;
	public TreeCell<String> test;
	@FXML
	public TreeView<String> tree;
	@FXML
	public TabPane tabpane;
	 Map<TreeItem<String>, Tab> map =new HashMap<TreeItem<String>, Tab>();
	 Map<Tab, TabController> tab_map = new HashMap<Tab, TabController>();  
	@FXML
	public TextField tab_name;
	@FXML
	public TableColumn process_column;
	@FXML
	public TableColumn burst_time_column;
	@FXML
	public TableColumn arrival_time_column;
	@FXML
	public TableColumn waiting_time_column;
	@FXML
	public TableView<ProcessItem> table;
	public Stage alert_window;
	//Image plus_icon = new Image(getClass().getResourceAsStream("/img/icons8-plus-10.png"));

	public void treeMouseClick(MouseEvent e) 
	{
		TreeItem<String> item= tree.getSelectionModel().getSelectedItem();
		System.out.println(item);
		
		if(item==new_SJF)  
			laod_fxml_tab(1);	
		
		else if(item==new_FCFS)		
			laod_fxml_tab(0);
	
		else if(item==new_PRIORITY)
			laod_fxml_tab(2);

		else if(item==new_ROUNDROBIN)
			laod_fxml_tab(3);
		
		else {
			Set set=map.entrySet();//Converting to Set so that we can traverse  
		    Iterator itr=set.iterator();  
		    while(itr.hasNext()){    
	 	        Map.Entry entry=(Map.Entry)itr.next();  //Converting to Map.Entry so that we can get key and value separately 
	 	        if(item==entry.getKey()) 
	 	        {
	 	        	Tab ff=(Tab) entry.getValue();
	 	        	if(ff.getTabPane()==null)
	 	        		tabpane.getTabs().add((Tab) entry.getValue());
	 	        	tabpane.getSelectionModel().select(ff); 
	 	            break;
	 	        }
	    	 } 
		}
	}
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	
		
		process_column.setCellValueFactory(new PropertyValueFactory<>("name"));
		burst_time_column.setCellValueFactory(new PropertyValueFactory<>("burst"));
		arrival_time_column.setCellValueFactory(new PropertyValueFactory<>("arrival"));
		waiting_time_column.setCellValueFactory(new PropertyValueFactory<>("waiting"));
		
		
		
		//waiting_time_column.setCellValueFactory(features ->Bindings.ValueAt(features.getValue(), 3));

		treeView_filler();	
	}
	

	public void  laod_fxml_tab(int scheduler_type )
	{
		try {
			tab_loader = new FXMLLoader(getClass().getResource("/Tabs/tab.fxml"));
			new_tab=(Tab)tab_loader.load();
			tabpane.getSelectionModel().select(new_tab);
		    tabpane.setTabClosingPolicy(TabClosingPolicy.SELECTED_TAB);
		    tabpane.getTabs().add(new_tab);
		    tabpane.getSelectionModel().selectedItemProperty().addListener(
		    	    new ChangeListener<Tab>() {
		    	        @Override
		    	        public void changed(ObservableValue<? extends Tab> ov, Tab t, Tab t1) {
		    	          
		    	        	System.out.println("Tab Selection changed");
		    	        	Tab tab =t1;
		    		    	System.out.println(tab);
		    		    	Set set=tab_map.entrySet();//Converting to Set so that we can traverse  
		    			    Iterator itr=set.iterator();  
		    			    while(itr.hasNext()){    
		    		 	        Map.Entry entry=(Map.Entry)itr.next();  //Converting to Map.Entry so that we can get key and value separately 
		    		 	       System.out.println(entry.getKey());
		    		 	        if(tab==entry.getKey()) 
		    		 	        {
		    		 	        	TabController ff=(TabController) entry.getValue();
		    		 	        	table.setItems(ff.process_list);
		    		 	       
		    		 	            break;
		    		 	        }
		    		    	 } 
		    	        
		    	        
		    	        }

						
		    	    }
		    	);
		    
		    

		    
		} catch (IOException e) {
			e.printStackTrace();
		} 	
		alertbox_pop("Create new file");
		TabController tabcontroller=tab_loader.getController();
		tabcontroller.set_scheduler(scheduler_type);
		tabcontroller.SetAlertBox(alertboxcontroller);
		tabcontroller.Setmaniwindow(this, new_tab);	
	    tab_map.put(new_tab,tabcontroller);
	}
	public void treeView_filler()
	{
		root = new TreeItem<>();
	     root.setExpanded(true);
	     
	     FCFS=makeBranch("FCFS Scheduler",root,false);
	     SJF=makeBranch("SJF Scheduler",root,false);
	     PRIORITY=makeBranch("Priority Scheduler",root,false);
	     ROUNDROBIN=makeBranch("RondRoubin Scheduler",root,false);
	     FCFS.setExpanded(true);
	     SJF.setExpanded(true);
	     PRIORITY.setExpanded(true);
	     ROUNDROBIN.setExpanded(true);

	     new_FCFS=makeBranch("create new file...",FCFS,false);
	     new_SJF=makeBranch("create new file...",SJF,false);
	     new_PRIORITY=makeBranch("create new file...",PRIORITY,false);
	     new_ROUNDROBIN=makeBranch("create new file...",ROUNDROBIN,false);     
	     tree.setRoot(root);
	 	 tree.setShowRoot(false);
	 	 	
	     Callback<TreeView<String>, TreeCell<String>> defaultCellFactory = TextFieldTreeCell.forTreeView();
			  
			  tree.setCellFactory((TreeView<String> tv) -> 
			  { 				 
				  TreeCell<String> cell =
			      defaultCellFactory.call(tv);
			      cell.treeItemProperty().addListener((obs,oldTreeItem, newTreeItem) -> 
			      {
			    	  
			          if(newTreeItem == null) {} else
			          if(newTreeItem.getValue() == "create new file..." )
			          {
			        	  cell.setId("create"); 
			          }
			         else if (newTreeItem == FCFS || newTreeItem == SJF || newTreeItem == PRIORITY ||newTreeItem == ROUNDROBIN)
			         {
				          cell.setId("parent"); 
			         }
			      else 
			        {
				      cell.setId("child");
			        }			  
			  });
			  return cell ; });
			    
	}
	public TreeItem<
	String> makeBranch(String title, TreeItem<String> parent,Boolean order)
	{
		TreeItem<String> item = new TreeItem<>(title/*,new ImageView(plus_icon)*/);
		 item.setExpanded(false);
		if(!order)
			parent.getChildren().add(item);
		else
			{parent.getChildren().add(parent.getChildren().size()-1, item);}
			
       
		 return item;
        
    }
    public void alertbox_pop(String title) 
    {
    	try {
			FXMLLoader loader= new FXMLLoader(getClass().getResource("/alertbox/AlertBox.fxml"));	
    		VBox alert_vbox = (VBox)loader.load();
    		alertboxcontroller= loader.getController();
    		alertboxcontroller.Setmaniwindow(this);
		    alert_window = new Stage();
		    alertboxcontroller.set_window(alert_window);
		    alert_window.initModality(Modality.APPLICATION_MODAL);
			alert_window.setTitle(title);
	        Scene scene = new Scene(alert_vbox);
	        scene.getStylesheets().add("/alertbox/alert.css");
	        alert_window.setScene(scene);
	        alert_window.show(); 
	        alert_window.setOnCloseRequest(e->tabpane.getTabs().remove(new_tab));
	     
		} catch (IOException e) {
			System.out.println("failed");//e.printStackTrace();
		}
    }	
	
    public StringProperty getTabtextproperty() {
    	System.out.println(new_tab.textProperty());
    	return new_tab.textProperty();
    }

    public void add_newfile(TreeItem<String> tree_item,Tab _tab) 
    {
    	map.put(tree_item, _tab);
    }  
	
    
}





