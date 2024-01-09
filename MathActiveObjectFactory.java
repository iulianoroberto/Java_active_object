import java.lang.reflect.Proxy;

/*
 * Classe Factory.
 */

public class MathActiveObjectFactory implements MathFactory {

	@Override
	public Math create() {
		// Crea istanza del proxy
		return (Math)Proxy.newProxyInstance(
				ActiveObjectTest.class.getClassLoader(), // Loader della classe ActiveObjectTest (si può usare qualunque loader)
				new Class<?>[] {Math.class},
				new ActiveObject(new MathImpl()) // PUNTO CHIAVE - Si istanzia l'handler e gli si passa la parte passiva dell'oggetto attivo
				);	
	}
}
