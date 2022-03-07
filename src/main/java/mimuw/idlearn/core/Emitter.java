package mimuw.idlearn.core;

import java.util.*;

/**
 * Class used to send events to connected listeners
 */
public class Emitter{
	private final Set<Listener> listeners;
	private final Queue<Event> eventQueue;
	
	public Emitter(){
		this.listeners = new HashSet<>();
		this.eventQueue = new LinkedList<>();
	}
	
	/**
	 * Connects listener to the emitter
	 * @param listener listener
	 */
	public void connect(Listener listener){
		if(listener != null)
			listeners.add(listener);
	}
	
	/**
	 * Disconects listener from the emitter
	 * @param listener listener
	 */
	public void disconnect(Listener listener){
		if(listener != null)
			listeners.remove(listener);
	}
	
	/**
	 * Add event to event queue to notify at later time
	 * @param event event
	 */
	public void notify(Object event){
		if(event != null)
			eventQueue.add(new Event(event));
	}
	
	/**
	 * Force notify an event
	 * @param event event
	 */
	public void fire(Object event){
		send(new Event(event));
	}
	
	/**
	 * Process events in event queue and send them to all listeners
	 */
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
