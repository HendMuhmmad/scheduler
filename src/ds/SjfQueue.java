package ds;
import java.time.Duration;
import java.time.Instant;

import application.ProcessItem;
import javafx.collections.ObservableList;

public class SjfQueue extends Queue {
	public SjfQueue(ObservableList<ProcessItem> _process_list) {
		super(_process_list);
		// TODO Auto-generated constructor stub
	}

	public boolean Enqueue(double _burst_time,String _name,Instant _arrival_time,int _priority,int _index)
	{
		Node temp=new Node( _burst_time, _name, _arrival_time, _priority,_index);
		process_list.add(temp.getTable_process());
		//temp.set_arrival_index(_priority);
		//no elements in queue
		if(this.is_empty())
		{
			this.set_head(temp);
			this.set_tail(temp);
			return false;
		}
		//not empty 
		// if preemptive check from head node
        Node temp2 = this.get_head();
		if(this.type == true)
		{
            //get the remaining time of current running process
            Instant currentTime = Instant.now();
            double timepassed = Duration.between(temp2.get_start_time(),currentTime).getSeconds();
            double rem = temp2.get_remaining_time() - timepassed;   
            //1.less than head
         	if(rem > temp.get_burst_time())
         	{
         		
             	//must be set before enqueue as the scheduler now compares with the remaining time
         		temp2.set_remaining_time(rem);
         	    //must be set before enqueue because after enqueue and start the new  process we will lose track
         		//of this node
         		temp2.set_stop_time(currentTime);
         		temp.set_next(temp2);
				this.set_head(temp);
				return true;
         	} 
			
		}
		//not empty nonpreemptive  or preemptive but larger than head
		//check from second node
		//1.less than any element 
		while(temp2.get_next() != null)
		{
			if(temp.get_burst_time() < (temp2.get_next()).get_remaining_time())
			{
				temp.set_next(temp2.get_next());
				temp2.set_next(temp);	
				return false;
			}
			temp2=temp2.get_next();
		}
		//2.larger than all elements
		temp2.set_next(temp);
		this.set_tail(temp);
		return false;
		
	}

}
