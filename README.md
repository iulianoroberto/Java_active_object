# Java_active_object
Active object in Java by dynamic proxy.

The Active Object pattern implements a concurrency model alternative to the thread-based one (provided by Java as its default model). According to this model, every object is provided with a thread that continuously extracts messages, from a queue, containing invocation requests received from the clients, and processes them. The existence of only one thread enabled to change the state of an object removes the necessity of controlling the accesses to its state to avoid inconsistency in case of concurrency.
The implementation of this pattern requires providing every (passive) object with a queue and a thread. The queue is used to enqueue requests coming from other objects while the thread processes previous requests. To provide access transparency, an active object should hide its internal structure and should expose to the clients the same interface of the managed passive object (servant).

## Spiegazione in italiano
In questo esercizio è stato implementato in Java il pattern Active Object. 
Con il termine Active Object ci si riferisce ad una micro-architettura, che permette l'invocazione asincrona dei metodi. In particolare, il chiamante non deve sospsendersi per aspettare l'esecuzione dell'operazione da parte del chiamato. Questo è possibile grazie alla presenza di un elemento di indirezione, cioè una coda. Le invocazioni, generate dal chiamato, vengono inserite all'interno della coda. Un thread di controllo si occuperà di scodare singolarmente le invocazioni dalla coda e indirizzarle al chiamato. Nel caso in cui l'invocazione preveda un valore di ritorno possono essere sfruttate le callback.

La creazione in Java di un sistema con una architettura di questo tipo sfrutta i proxy dinamici. 
La creazione dinamica dei proxy è ottenuta sfruttando la reflection. In particolare, la classe Proxy e l'interfaccia InvocationHandler del package java.lang.reflect.

Gli elementi del pattern Active Object sono:
- L'interfaccia dei servizi, che definisce i protipi dei metodi invocabili dal chiamante. Questa interfaccia è implementata dal proxy e dalla classe concreta che implementa i servizi.
- L'invocation handler rappresenta l'elemento disaccoppiante che intercetta le invocazioni e le insierisce all'interno della coda. Inoltre, lo stesso è responsabile di gestire lo scheduling delle invocazioni. Questo, in termini programmatici, consiste in un istanza di una classe che implementa l'interfaccia InvocationHandler.
- Il proxy rappresenta il punto di contatto tra chiamante e invocation handler. Il chiamante effettua le invocazioni tramite il proxy, e poi sono redirette tramite l'invocation handler all'implementazione vera e propria. In termini programmatici il proxy consiste in un'istanza della classe Proxy del package java.lang.reflect.
