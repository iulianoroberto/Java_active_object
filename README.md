# Java_active_object
Active object in Java by dynamic proxy.

The Active Object pattern implements a concurrency model alternative to the thread-based one (provided by Java as its default model). According to this model, every object is provided with a thread that continuously extracts messages, from a queue, containing invocation requests received from the clients, and processes them. The existence of only one thread enabled to change the state of an object removes the necessity of controlling the accesses to its state to avoid inconsistency in case of concurrency.
The implementation of this pattern requires providing every (passive) object with a queue and a thread. The queue is used to enqueue requests coming from other objects while the thread processes previous requests. To provide access transparency, an active object should hide its internal structure and should expose to the clients the same interface of the managed passive object (servant).
