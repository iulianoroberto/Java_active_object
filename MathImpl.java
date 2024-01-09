
public class MathImpl implements Math{

	// Implementazione del metodo add
	public void add(float x, float y) {
		double sum = x + y;
		/*
		 * Non ho modo di restituire la somma e quindi la visualizzo sullo stdout, l'alternativa sarebbe quella di usare una callback.
		 * Se si vuole usare la callback bisogna modificare l'interfaccia, diventa meno trasparente si vede che si sta usando un esecuzione
		 * asincrona. A meno che non si usi una variabile futura e un pattern proxy per dare anche trasparenza alla variabile futura.
		 */
		System.out.println(x+"+"+y+"="+sum);
		//callback.addResults(x, y, sum);
	}


}
