import java.lang.reflect.*;
import java.util.concurrent.*;

/*
 * Questo è un InvocationHandler. E' una classe che dovrà implemnetare il metodo invoke.
 */

public class ActiveObject implements InvocationHandler {
	private LinkedBlockingQueue<Call> activationQueue = new LinkedBlockingQueue<Call>(); // Coda (qu)
	private Object servant; // Servant, implemenatzione su cui deve operare, (implemntazione reale realizzata tramite un oggetto passivo, l'oggetto attivo è composto da due entità, una passiva che è il Servant e una attiva che è il thread di controllo)
	private Thread scheduler; // Scheduler ActiveObject
	public static final ThreadGroup schedulers = new ThreadGroup("ActiveObjectSchedulers"); // Mantenere tutti i thread degli oggetti attivi
	
	static { Runtime.getRuntime().addShutdownHook(new ActiveObject.ShutHook()); }
	
	/*
	 * Costruttore che riceve il Servant, parte passiva, lo stato e
	 * vengono inizializzate le varibili di istanza compresa
	 * l'attivazione del thread scheduler
	 */
	public ActiveObject(Object servant) {
		this.servant = servant;
		this.scheduler = new Scheduler();
		this.scheduler.start();
	}
	
	/*
	 * Metodi per operare sulla queue
	 */

	private void enqueue(Call call) {
		activationQueue.add(call);
	}
	
	private Call dequeue() throws InterruptedException {
		return activationQueue.take();
	}

	/*
	 * Quando sarà invocato questo metodo riceverà il metodo invocato sul proxy e gli argomenti.
	 */
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		// I due parametri sono incapsulati in una chiamata (classe Call)
		Call call = new Call(method, args);
		// Si realizza l'accodamento della chiamata sulla coda associata all'oggetto attivo
		enqueue(call);
		return null;
	}
	
	// Classe privata dello scheduler
	private class Scheduler extends Thread {
		public Scheduler() { super(schedulers, "Scheduler"); }
		// Metodo run dello scheduler, estrae e manda in esecuzione finché halt e false, il thread termina dopo aver svuotato la coda
		public void run() {
			boolean halt = false;
			while (!(halt && activationQueue.size() == 0)) {
				try {
					Call call = dequeue();
					// dispatch invoca la chiamata
					dispatch(call);
				} catch (Exception e) {
					halt = true;
				}
			}
			System.out.println("Sta terminando il thread " + this + " " + activationQueue.size());
		}
		private void dispatch(Call call) throws IllegalAccessException, InvocationTargetException {
			call.method.invoke(servant, call.args);
		}
	}
	
	// Classe privata 
	private static class Call {
		public Call(Method m, Object[] a) {
			method = m; args = a;
		}
		public Method method;
		public Object[] args;
	}
	
	public static class ShutHook extends Thread {
		public void run() {
			
		}
	}

}
