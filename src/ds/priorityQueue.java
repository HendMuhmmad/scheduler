package ds;
//import java.time.Duration;
import java.time.Instant;

import application.ProcessItem;
import javafx.collections.ObservableList;

public class priorityQueue extends Queue {

	
	public priorityQueue(ObservableList<ProcessItem> _process_list) {
		super(_process_list);
		// TODO Auto-generated constructor stub
	}

	@Override
  	public  boolean Enqueue(double _burst_time, String _name, Instant _arrival_time, int _priority,int _index) {
		
		Node temp=new Node( _burst_time, _name, _arrival_time, _priority,_index);
		process_list.add(temp.getTable_process());
		if(this.is_empty())
		{
			this.set_head(temp);
			this.set_tail(temp);
			return true;
		}
		
		//pemittive priority scheduling
		
		if(temp.get_priority() < (this.get_head()).get_priority() && type)
		{
			temp.set_next(this.get_head());
			this.set_head(temp);
			return false;
			
		}
		
		
		
		
		
		Node temp2 = this.get_head();
		while(temp2.get_next() != null)
		{ if (temp.get_priority() == (temp2.get_next()).get_priority() )
		{
			temp.set_next(temp2.get_next().get_next());
			temp2.get_next().set_next(temp);
			return true ;
		}
			
		else if(temp.get_priority() < (temp2.get_next()).get_priority())
			{
				temp.set_next(temp2.get_next());
				temp2.set_next(temp);	
				return true;
			}
		
			temp2=temp2.get_next();
		}
		//3.larger than all elements
		temp2.set_next(temp);
		this.set_tail(temp);
		return true;
	}

}
