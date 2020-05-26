package ds;
import java.time.Instant;

import application.ProcessItem;
import javafx.scene.chart.XYChart;
public class Node {
	private String name;
	private int priority;	
	private double burst_time;
	private double remaining_time;
	private Instant arrival_time;
	private Instant stop_time;
	private Node next;
	private Instant start_time;
	private XYChart.Series series;
	private int arrival_index;
	private ProcessItem table_process;
	
	public void setTable_process(ProcessItem table_process) {
		this.table_process = table_process;
	}
	public ProcessItem getTable_process() {
		return table_process;
	}
	
	public void set_series(XYChart.Series _series) 
	{
		this.series=_series;
	}
	public XYChart.Series get_series()
	{
		return series;
	}
	public Node()
	{
		this.next=null;
		this.burst_time=0;
		this.name="p";
		this.arrival_time=null;
		this.priority=0;
		this.remaining_time=0;
		this.start_time=null;
		this.stop_time=null;
		this.series= new XYChart.Series();
		series.setName(name);
	}
	
	public Node(double _burst_time,String _name,Instant _arrival_time,int _priority,Node _next ) 
	{	
		this.burst_time=_burst_time;
		this.remaining_time=_burst_time;
		this.name=_name;
		this.arrival_time=_arrival_time;
		this.stop_time=_arrival_time;
		this.priority=_priority;
		this.next=_next;
		this.start_time=null;
		this.series= new XYChart.Series();
		series.setName(name);
	}
	public Node(double _burst_time,String _name,Instant _arrival_time,int _priority,int _index) 
	{	
		this.burst_time=_burst_time;
		this.remaining_time=_burst_time;
		this.name=_name;
		this.arrival_time=_arrival_time;
		this.stop_time=_arrival_time;
		this.priority=_priority;
		this.next = null;
		this.start_time=null;
		this.arrival_index=_index;
		this.table_process= new ProcessItem(name ,(int) burst_time,arrival_index,0);
		this.series= new XYChart.Series();
		series.setName(name);
	}

	public void set_arrival_index(int _arrival_index)
	{
		this.arrival_index=_arrival_index;			
	}
	public int get_arrival_index()
	{
		return this.arrival_index;	
	}
	//get&set of next node
	public void set_next(Node _next)
	{
		this.next=_next;			
	}
	public Node get_next()
	{
		return this.next;	
	}
	//get&set of burst time
	public void set_burst_time(int _burst_time)
	{
		this.burst_time=_burst_time;	
	}
	public double get_burst_time() 
	{
		return this.burst_time;
	}
	//get&set of arrival time
	public void set_arrival_time(Instant _arrival_time)
	{
		this.arrival_time=_arrival_time;
	}
	public Instant get_arrival_time()
	{
		return this.arrival_time;
	}
	//get&set of remaining time
	public void set_remaining_time(double _remaining_time)
	{
		this.remaining_time=_remaining_time;
	}
	public double get_remaining_time() 
	{
		return this.remaining_time;
	}
	//get&set of priority
	public void set_priority(int _priority) 
	{
		this.priority=_priority;
	}
	public int get_priority() 
	{
		return this.priority;
	}
	//get&set of name
	public void set_name(String _name) 
	{
		this.name=_name;
	}
	public String get_name() 
	{
		return this.name;
	}
	//get&set of start time
	public void set_start_time(Instant _start_time) 
	{
		this.start_time=_start_time;
	}
	public Instant get_start_time() 
	{
		return this.start_time;
	}
	//get&set of stop time
	public void set_stop_time(Instant _stop_time) 
	{
		this.stop_time=_stop_time;
	}
	public Instant get_stop_time() 
	{
		return this.stop_time;
	}
	
	

}
