
public class ActiveObjectTest {

	public static void main(String[] args) {
		// Creazione Factory
		MathFactory af = new MathActiveObjectFactory();
		// Si crea oggetto attivo a
		Math a = af.create();
		
		long startTime = System.currentTimeMillis();
		
		for(int i=0; i<100; i++) {
			/*
			 * IMPORTANTE
			 * Qui non si realizza la somma, ma si stanno accodando le chiamate dalla code che saranno poi scodate dallo scheduler,
			 * anche se si hanno molti task il client si libera subito.
			 */
			a.add(2*i, 3*i);
		}
		
		// Questo è il tempo di accodamento non di elaborazione, l'elaborazione è fatta in modalità asincrona (diventa difficile calcolare il tempo di esecuzione di un metodo, bisogna aspettare che la callback sia eseguita)
		long elapsed = System.currentTimeMillis() - startTime;
		
		System.out.println("All calls finished: " + elapsed + " ms elapsed");
		System.out.flush();
		ActiveObject.schedulers.interrupt();

	}

}
