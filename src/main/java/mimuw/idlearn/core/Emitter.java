package mimuw.idlearn.core;

import java.util.*;

public class Emitter{
	private final Set<Listener> listeners;
	private final Queue<Event> eventQueue;
	
	public Emitter(){
		this.listeners = new HashSet<>();
		this.eventQueue = new LinkedList<>();
	}
	
	public void connect(Listener listener){
		if(listener != null)
			listeners.add(listener);
	}
	
	public void disconnect(Listener listener){
		if(listener != null)
			listeners.remove(listener);
	}
	
	public void notify(Object event){
		if(event != null)
			eventQueue.add(new Event(event));
	}
	
	public void fire(Object event){
		send(new Event(event));
	}
	
	public void processEvents(){
		while(!eventQueue.isEmpty()){
			send(eventQueue.poll());
		}
	}
	
	private void send(Event event){
		for(Listener l : listeners){
			l.onNotify(event);
		}
	}
}
