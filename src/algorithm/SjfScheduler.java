package algorithm;

import java.awt.event.ActionListener;
import java.time.Duration;
import java.time.Instant;
import javax.swing.Timer;
import algorithm.*;
import ds.*;

import Tabs.TabController;
import application.SampleController;
import javafx.application.Platform;
import javafx.scene.chart.XYChart;



public class SjfScheduler  implements ActionListener {
	////////////////////////////////
	volatile int index=0;
	TabController control;
	public XYChart.Series series;
	Timer timer_1 ;
	////////////////////////////////
	
	private SjfQueue  Q ;
    double averageTime;
    int num_of_process;
	public Timer timer ;
	private double table_waitingtime;
	private long table_arrival_time;
////////////////////////

	public void handle_drawingError(int time) 
	{
		System.out.println("in");

	//	System.out.println(series.getData().size());
	//	System.out.println(time*4);
		
		if(series.getData().size()>(time*4)) 
		{
		while(series.getData().size()!=(time*4))	
		{
			//System.out.println("more data");
			series.getData().remove(series.getData().size()-4, series.getData().size());
			index--;
			Q.decrease();
		}
		}
		else if(series.getData().size()<(time*4)) 
		{
			while(series.getData().size()!=(time*4))
			{
			//	System.out.println("less data");

				index++;
				Q.update();
				series.getData().add(new XYChart.Data(index-1,0));
				series.getData().add(new XYChart.Data(index-1,1));
				series.getData().add(new XYChart.Data(index,1));
				series.getData().add(new XYChart.Data(index,0));
			}
		}
	}
	
	public  SjfScheduler(TabController tabController,boolean _type)
	{
		this.timer_1 = new Timer(1000,this);
		
		
		this.control=tabController;
		this.Q = new SjfQueue(control.process_list);
		this.Q.set_type(_type);
		this.timer = new Timer(1,this);
		this.averageTime = 0;
		this.num_of_process = 0; 	
	}
	 
 	public void new_process(String name,double burst_time) 
	{
		boolean start_flag = false, int_process = false;
      	Instant currentTime = Instant.now();
    
      	if(Q.is_empty())
      	{
      		start_flag = true;
      	}   
 
      	int_process = Q.Enqueue(burst_time, name,  currentTime,0,index);	
      	num_of_process++;
      	////
      	////
      	if(start_flag || int_process)	
      	{  		
      	   fire_process(); 
      	}  
	}
	 public void  fire_process()
	 {	 	
	 		series=Q.get_head().get_series();
			if(series.getChart()==null)
			{control.areachart.getData().add(series);}
			///////////////////////////////////
		    Node temp = Q.get_head();
	     /*   System.out.print(temp.get_name()+ " remaning time ");
	    	System.out.println(temp.get_remaining_time()+"arrival time");
	    	System.out.print(temp.get_arrival_time());*/
	    	
	    	 Instant startTime = Instant.now();
		 	// calculate difference between arrival and start then add it to average time
		 	Duration d = Duration.between(temp.get_stop_time(),startTime);
		// 	averageTime += d.getSeconds();
		    temp.set_start_time(startTime);
			timer.setInitialDelay((int)temp.get_remaining_time()*1000);
			timer.setDelay((int)temp.get_remaining_time()*1000);
			timer.restart();
			/////////////////////////
			if(!timer_1.isRunning())
			timer_1.restart();
			//////////////////////////
	 }
	 
	 @Override
	//timer event: first entered when the first process has finished
     public void actionPerformed(java.awt.event.ActionEvent arg0)
	 {
		 if( arg0.getSource() ==timer ) {
				
			 Platform.runLater(new Runnable() {
			        @Override
			        public void run() {
						averageTime+=Q.get_head().getTable_process().getWaiting();
						
						
					 	
					 	// dequeue the finished process
					 	////////////
					 	handle_drawingError((int) Q.get_head().get_burst_time());
					 	////////
					 	Q.Dequeue();
					 	// if q is empty stop timer and calculate average time
					 	if(Q.is_empty())
					 	{
					 		//averageTime /= num_of_process;
					 		//System.out.println(averageTime);
					 		//averageTime =0;
					 		timer.stop();
					 		series=null;
					 		//timer_1.stop();
					 		return;
					 	}
					 	// start the next Process
					 	fire_process();	//javaFX operations should go here
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

	 public void end()
	 {
		 System.out.println("in2");
		 averageTime /= num_of_process;
		 control.finish_label.setText("average time: "+ averageTime);
		 timer_1.stop();
		 averageTime=0;
	 }

}

