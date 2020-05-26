package algorithm;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Duration;
import java.time.Instant;
import javax.swing.Timer;
import ds.*;

import Tabs.TabController;
import javafx.application.Platform;
import javafx.scene.chart.XYChart;


public class priorityscheduler  implements ActionListener {

////////////////////////////////
volatile int index=0;
TabController control;
public XYChart.Series series;
Timer timer_1 ;
////////////////////////////////
	
	private priorityQueue Q;
	double averageTime;
   // int Num_of_process;
	public Timer timer ;
	//SampleController control;	//GUI controller
	private double num_of_process;
	private double table_waitingtime;
	private long table_arrival_time;
	public  priorityscheduler (TabController tabController,boolean _type)
	{
		//////////////
		this.control=tabController;
		this.timer_1 = new Timer(1000,this);
		///////////////
		this.Q = new priorityQueue(control.process_list);
		this.Q.set_type(_type);
		this.timer = new Timer(1,this);
		this.averageTime = 0;
		this.num_of_process = 0;
	}
	
	 public void new_process(String name,double burst_time,int priority) 
		{
		//get the arrival time
	     	Instant currentTime = Instant.now();
	     	
	     	if(Q.get_head() == null)
        	{ 
        	Q.Enqueue(burst_time, name,  currentTime, priority,index);
       
        		//Q.get_head().set_start_time(currentTime);
        		 fire_process();   	
        	}
	     
        	else
        	{  
        		 Node temp =Q.get_head();
        		        		 
        		 double time =Duration. between(temp.get_start_time(), currentTime).getSeconds();
        		 

        		 double new_rem = temp.get_remaining_time() - time ;
        			       
        	    
        		 if(!Q.Enqueue(burst_time, name,  currentTime, priority,index))
        	           	  { 

        	     		// timer.stop(); 	               
        	                temp.set_remaining_time(new_rem);
        	                temp.set_stop_time(currentTime);
        	                fire_process();
        	                
        	                          		  
        	           	  }
        	     	
        	     	  
            } 
	     	 num_of_process++;
		 
		}
	 
	 
	public void  fire_process() 
	{ 
		/////////////////
		series=Q.get_head().get_series();
		if(series.getChart()==null)
		{control.areachart.getData().add(series);}
		///////////////////////////////////
		
		Node temp = Q.get_head();
		System.out.print(Q.get_head().get_name()+ "  ");
    	System.out.println(temp.get_remaining_time());
    	//get the starting time
    	Instant startTime = Instant.now();
 
    	
    	temp.set_start_time(startTime);
    	timer.setInitialDelay((int)temp.get_remaining_time()*1000);
		timer.setDelay((int)temp.get_remaining_time()*1000);
		timer.restart();
		if(!timer_1.isRunning())
			timer_1.restart();
	
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
    	 if( arg0.getSource() ==timer ) {
				
			 Platform.runLater(new Runnable() {
			        @Override
			        public void run() {
			  			handle_drawingError((int) Q.get_head().get_burst_time());
			  			averageTime+=Q.get_head().getTable_process().getWaiting();
			  			Q.Dequeue();
			        	// if q is empty stop timer and calculate average time
			        	if(Q.get_head() == null)
			        	{
			        		timer.stop();
			        		series=null;
			        		return;
			        	}
			        	fire_process();
			        }
			   });
						 						 	 
		 }
			 else if(arg0.getSource() ==timer_1)
			 {
				 Platform.runLater(()->
					{
						index++;
						if(series!=null)
						{Q.update();
						series.getData().add(new XYChart.Data(index-1,0));
						series.getData().add(new XYChart.Data(index-1,1));
						series.getData().add(new XYChart.Data(index,1));
						series.getData().add(new XYChart.Data(index,0));
						System.out.println(series.getData().size());}
					});	 
			 }	
	}
	public void handle_drawingError(int time) 
	{
		
		
		if(series.getData().size()>(time*4)) 
		{
		while(series.getData().size()!=(time*4))	
		{
			
			series.getData().remove(series.getData().size()-4, series.getData().size());
			index--;
			Q.decrease();
		}
		}
		else if(series.getData().size()<(time*4)) 
		{
			while(series.getData().size()!=(time*4))
			{
				index++;
				Q.update();
				series.getData().add(new XYChart.Data(index-1,0));
				series.getData().add(new XYChart.Data(index-1,1));
				series.getData().add(new XYChart.Data(index,1));
				series.getData().add(new XYChart.Data(index,0));
			}
		}
	}
	 public void end()
	 {
		 averageTime /= num_of_process;
		 control.finish_label.setText("average time: "+ averageTime);
		 timer_1.stop();
		 averageTime=0;
	 }
}
