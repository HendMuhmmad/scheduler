package ds ;

import java.time.Instant;

import application.ProcessItem;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

public class Queuefcfs extends Queue {

	public Queuefcfs(ObservableList<ProcessItem> _process_list) {
		super(_process_list);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean Enqueue(double _burst_time, String _name, Instant _arrival_time, int _priority,int _index) 
	{
		
		Node temp=new Node( _burst_time, _name, _arrival_time, _priority, _index);
		process_list.add(temp.getTable_process());
		if(is_empty())
		{
			this.set_head(temp);
			this.set_tail(temp);	
		}
		else 
		{
			this.get_tail().set_next(temp);
			this.set_tail(temp);
		}
			
		// TODO Auto-generated method stub
		return false;
		
	}

}
