package ds;
import java.time.Instant;

import application.ProcessItem;
import javafx.collections.ObservableList;
abstract  class Queue {
	private Node head;
	private Node tail;
	boolean type;
	public ObservableList<ProcessItem> process_list;
	
	public void update() 
	{
		
		Node temp =this.head;
		if(temp==null)return;
		while(temp.get_next()!=null)
		{
			int current =temp.get_next().getTable_process().getWaiting();
			temp.get_next().getTable_process().setWaiting(current+1);
			temp=temp.get_next();
		}
	}
	public void decrease() 
	{
		Node temp =this.head;
		if(temp==null)return;
		while(temp.get_next()!=null)
		{
			int current =temp.get_next().getTable_process().getWaiting();
			temp.get_next().getTable_process().setWaiting(current-1);
			temp=temp.get_next();
	}
	}
	public  Queue( ObservableList<ProcessItem> _process_list) 
	{
		head=null;
		tail=null;
		type = false;
		process_list=_process_list;
	}
	public  Queue(Node _head,Node _tail,boolean _type) 
	{
	this.head=_head;
	this.tail=_tail;
	this.type = _type;
	}
	public void set_type(boolean _type) 
	{
		this.type=_type;	
	}
	public boolean get_type() 
	{
		return type;	
	}
	
	public void set_head(Node _head) 
	{
		this.head=_head;	
	}
	public Node get_head() 
	{
		return head;	
	}
	public void set_tail(Node _tail) 
	{
		this.tail=_tail;	
	}
	public Node get_tail() 
	{
	return tail;	
	}
	public boolean is_empty() 
	{
		return (this.get_head()==null)&&(this.get_tail()==null);
	}
	public abstract boolean Enqueue(double _burst_time,String _name,Instant _arrival_time,int _priority,int _index);
	public Node Dequeue()
	{
	Node temp=this.get_head();
	if(this.is_empty()) 
	{
	System.out.println("queue is empty");	
	}
	else if(this.get_head()==this.get_tail()) 
	{
		this.set_head(null);
		this.set_tail(null);
	}
	else 
	{
		this.set_head(temp.get_next());
	}
	return temp;
	} 
	public void print_burst_time() 
	{
		Node temp=get_head();
		while(temp!=null) 
		{
			System.out.println(temp.get_burst_time());
			temp=temp.get_next();
		}
	}
	public void print_name() 
	{
		Node temp=get_head();
		while(temp!=null) 
		{
			System.out.println(temp.get_name());
			temp=temp.get_next();
		}
	}
	public void print_arrival_time() 
	{
		Node temp=get_head();
		while(temp!=null) 
		{
			System.out.println(temp.get_arrival_time());
			temp=temp.get_next();
		}
	}
	public void print_priority() 

	{
		Node temp=get_head();
		while(temp!=null) 
		{
			System.out.println(temp.get_priority());
			temp=temp.get_next();
		}
	}
   
}
