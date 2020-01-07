package thread;

import controller.Dispatcher;
import org.apache.log4j.Logger;

public class Proceso extends Thread {
    final static Logger logger = Logger.getLogger(Proceso.class);

    private Dispatcher dispatcher;
    private String texto;

    public Proceso(Dispatcher dispatcher, String texto) {
        this.dispatcher= dispatcher;
        this.texto= texto;
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

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
}
