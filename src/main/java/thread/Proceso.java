package thread;

import controller.Dispatcher;
import org.apache.log4j.Logger;

/**
 *  Clase que nos va a ayudar a probar la concurrencia, para este examen, que se
 *  corran como máximo 10 hilos de forma concurrente
 */
public class Proceso extends Thread {
    final static Logger logger = Logger.getLogger(Proceso.class);

    private Dispatcher dispatcher;

    public Proceso(Dispatcher dispatcher) {
        this.dispatcher= dispatcher;
    }
    @Override
    public void run() {
        try {
            dispatcher.dispatcherCall();

        } catch (Exception ex) {
            logger.info("ERROR: "+ex.getMessage());
        }
    }

    public Dispatcher getDispatcher() {
        return dispatcher;
    }

    public void setDispatcher(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

}
