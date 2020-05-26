package algorithm;



import java.awt.event.ActionListener;
import java.time.Duration;
import java.time.Instant;
import javax.swing.Timer;

import Tabs.TabController;
import application.SampleController;
import ds.Node;
import ds.RRQueue;
import javafx.application.Platform;
import javafx.scene.chart.XYChart;

public class RRscheduler implements ActionListener {

////////////////////////////////
volatile int index=0;
TabController control;
public XYChart.Series series;
Timer timer_1 ;
//////////////////////////////// 

	private RRQueue q;
	double rem_t=0;
	int quantum;
	//private double averagewaitingTime =0;
	public Timer timer;
	   double averageTime;
	    int num_of_process;
	    private double table_waitingtime;
		private long table_arrival_time;

	
	public RRscheduler(TabController _control)
	{
//////////////
	this.timer_1 = new Timer(1000,this);
	///////////////
		
		this.control=_control;
		this.q= new RRQueue(control.process_list);
		timer=new Timer(1, this);     
		this.averageTime = 0;
		this.num_of_process = 0;
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
						System.out.println(series.getData().size());
						}
						});

				 
			 }
 
     }
	 public void new_process(String name, double burst_time, int _quantum) 
	{
		 num_of_process++;
		 boolean p=false;
		this.quantum=_quantum;
		if(q.is_empty()) p=true;
		Instant currentTime = Instant.now();
		q.Enqueue(burst_time, name, currentTime, 0,index);
		if(p)
			fire_process();
	}
	
	public void fire_process() {
		//////////////
		series=q.get_head().get_series();
		if(series.getChart()==null)
		{control.areachart.getData().add(series);}
		//////////////
		if(q.get_head().get_remaining_time() > quantum)
		{	
		timer.setInitialDelay((int) (quantum*1000));
		timer.setDelay((int) (quantum*1000));
		}
		else 
		{
			timer.setInitialDelay((int) (q.get_head().get_remaining_time()*1000));
		    timer.setDelay((int) (q.get_head().get_remaining_time()*1000));
		}
	    timer.restart();
	    /////////////////////////
	    if(!timer_1.isRunning())
	    	timer_1.restart();
	    //////////////////////////
	}
	
	public void timer_interrupt() 
	{ 
		int oldquantum=timer.getDelay()/1000;
		Instant now_ =Instant.now();
		
		if(timer.getDelay() == q.get_head().get_remaining_time()*1000)
			{System.out.println("finished");
			handle_drawingError((int)  q.get_head().get_burst_time());

averageTime+=q.get_head().getTable_process().getWaiting();


			q.Dequeue();
			}//process finished
		else
		{
			int rem= (int) (q.get_head().get_remaining_time() - oldquantum);
			q.get_head().set_remaining_time(rem);	
			
			handle_drawingError( (int) ((int) q.get_head().get_burst_time()-q.get_head().get_remaining_time()));

			q.DeEnqueue();
			
		}
		if(!q.is_empty())
		fire_process();
		else
		{
			series=null;
			//timer_1.stop();
			timer.stop();
		}
	}

	public void handle_drawingError(int time) 
	{
		System.out.println("in");

	

		if(series.getData().size()>(time*4)) 
		{
		while(series.getData().size()!=(time*4))	
		{
			
			series.getData().remove(series.getData().size()-4, series.getData().size());
			index--;
			q.decrease();
		}
		}
		else if(series.getData().size()<(time*4)) 
		{
			while(series.getData().size()!=(time*4))
			{
				System.out.println("less data");

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



