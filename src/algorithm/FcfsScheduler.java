package algorithm;

import java.awt.event.ActionListener;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import javax.swing.Timer;


import Tabs.TabController;
import application.SampleController;
import ds.Node;
import ds.Queuefcfs;
import javafx.application.Platform;
import javafx.scene.chart.XYChart;

public class FcfsScheduler implements ActionListener {
	
////////////////////////////////
volatile int index=0;
TabController control;
public XYChart.Series series;
Timer timer_1 ;
////////////////////////////////
	Instant zero_time;
	private Queuefcfs q;
	private double waiting_time=0;
	private Timer timer;
	double averageTime;
	int num_of_process;
	private double table_waitingtime;
	private long table_arrival_time;

	public FcfsScheduler(TabController tabcontroller)
	{
		this.timer_1 = new Timer(1000,this);
		
		this.control=tabcontroller;
		this.q= new Queuefcfs(control.process_list);
		timer=new Timer(1, this);     
		this.averageTime = 0;
		this.num_of_process = 0;
		//control.areachart.dataProperty().bind(series.dataProperty());
	}
	
	@Override
    public void actionPerformed(java.awt.event.ActionEvent arg0)
    { 
		 if( arg0.getSource() ==timer ) {
				
			 Platform.runLater(new Runnable() {
			        @Override
			        public void run() {
			        	timer_interrupt();
			        }
			        });
						 						 	 
		 	}
			 else if(arg0.getSource() ==timer_1)
			 {
				 Platform.runLater(()->
					{
						
						index++;
						
						if(series!=null)
						{
							q.update();
						series.getData().add(new XYChart.Data(index-1,0));
						series.getData().add(new XYChart.Data(index-1,1));
						series.getData().add(new XYChart.Data(index,1));
						series.getData().add(new XYChart.Data(index,0));
						//System.out.println(series.getData().size());
						//q.update();
						}
						
						});
			 }

    }
	
	 public void new_process(String name,double burst_time) 
	{
		boolean p=false;
		num_of_process++;
		if(q.is_empty()) p=true;	//p to indicate weather to fir process or just add it
		Instant currentTime = Instant.now();
		q.Enqueue(burst_time, name,  currentTime, 0,index);	

		System.out.println("new process added");

		if(p)
			fire_process();
	}
	
	public void fire_process() 
	{
		series=q.get_head().get_series();
		if(series.getChart()==null)
		{control.areachart.getData().add(series);}
		timer.setInitialDelay((int) (q.get_head().get_burst_time()*1000));
		timer.setDelay((int) (q.get_head().get_burst_time()*1000));
		timer.restart();
		if(!timer_1.isRunning())
			{
			timer_1.restart();
			zero_time=q.get_head().get_arrival_time();
			}
	}
	
	public void timer_interrupt() 
	{ 
		Node temp=q.get_head();
		////////////////table information
		
	
		
		
		//////////table information
		averageTime+=q.get_head().getTable_process().getWaiting();
		
		
		handle_drawingError((int)  temp.get_burst_time());
		q.Dequeue();
		if(!q.is_empty())
		fire_process();
		else
		{
			series=null;
			timer.stop();
		}
	}
	public void handle_drawingError(int time) 
	{
		//System.out.println("in");

		//System.out.println(series.getData().size());
		//System.out.println(time*4);
		//if(series.getData().size()==0)return;
		if(series.getData().size()>(time*4)) 
		{
		while(series.getData().size()!=(time*4))	
		{
			//System.out.println("more data");
			series.getData().remove(series.getData().size()-4, series.getData().size());
			index--;
		q.decrease();
		}
		}
		else if(series.getData().size()<(time*4)) 
		{
			while(series.getData().size()!=(time*4))
			{
				//System.out.println("less data");

				index++;
				q.update();
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

