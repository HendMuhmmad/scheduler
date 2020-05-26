package application;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ProcessItem
{
 private SimpleStringProperty name =new SimpleStringProperty();
 private SimpleIntegerProperty burst =new SimpleIntegerProperty();
 private SimpleIntegerProperty arrival=new SimpleIntegerProperty();
 private SimpleIntegerProperty  waiting=new SimpleIntegerProperty();
	
public ProcessItem()
{
	 this.name.set("");
     this.burst.set(0);
     this.arrival.set(0);	
     this.waiting.set(0);	
}
 
public ProcessItem(String _name,int _burst ,int _arrival, int _waiting)
{
	 this.name.set(_name);
     this.burst.set(_burst);
     this.arrival.set(_arrival);	
     this.waiting.set(_waiting);	
}
public void setArrival(int arrival) {
	this.arrival.set(arrival);;
}public void setBurst(int burst) {
	this.burst.set(burst);;
}
public void setName(String name) {
	this.name.set(name);
}
public void setWaiting(int waiting) {
	this.waiting.set(waiting);
}
public int getArrival() {
	return arrival.get();
}
public int getBurst() {
	return burst.get();
}
public String getName() {
	return name.get();
}
 public int getWaiting() {
	return waiting.get();
}
 public SimpleIntegerProperty arrivalProperty() {
	    return this.arrival;
	}
 public SimpleIntegerProperty burstProperty() {
	    return this.burst;
	}
 public SimpleIntegerProperty waitingProperty() {
	    return this.waiting;
	}
 public SimpleStringProperty nameProperty() {
	    return this.name;
	}

}
