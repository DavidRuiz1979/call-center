
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import controller.Dispatcher;
import entity.Empleado;
import entity.Operador;
import entity.Supervisor;
import org.junit.Test;
import thread.Proceso;

import static org.junit.Assert.assertTrue;


public class Test01 {

	/**
	 *  Test q prueba la elección de un operador sobre un supervisor
	 */
	
	@Test
	public void operadorSobreSupervisorTest() {
		Dispatcher dispatcher= new Dispatcher();
		
		List<Operador> operadores= new ArrayList();
		List<Supervisor> supervisores= new ArrayList();
		
		Operador op= new Operador();
		op.setNombre("op - Daniel");
		op.setDni(11111);
		op.setEstaLibre(true);						
		
		operadores.add(op);
		
		Supervisor sup= new Supervisor();
		sup.setNombre("sup - Maria");
		sup.setDni(22222);
		sup.setEstaLibre(true);
		supervisores.add(sup);
				
		dispatcher.setOperadores(operadores);
		dispatcher.setSupervisores(supervisores);
		
		Empleado empleado= dispatcher.dispatcherCall();

		assertTrue(empleado.getDni() == 11111);
	}
		

	@Test
	public void maximoProcesosConcurrentes(){
		Dispatcher dispatcher= new Dispatcher();

		List<Operador> operadores= new ArrayList();
		List<Supervisor> supervisores= new ArrayList();

		Operador op= new Operador();
		op.setNombre("op - Daniel");
		op.setDni(11111);
		op.setEstaLibre(true);

		operadores.add(op);

		Supervisor sup= new Supervisor();
		sup.setNombre("sup - Maria");
		sup.setDni(22222);
		sup.setEstaLibre(true);
		supervisores.add(sup);

		dispatcher.setOperadores(operadores);
		dispatcher.setSupervisores(supervisores);

		List<Proceso> procesos= new ArrayList<>();

		for(int i= 1; i < 15; i++) {
			procesos.add( new Proceso(dispatcher, "Proceso "+i));
		}

		procesos.stream().forEach( p ->{
			p.start();
			try {
				Thread.sleep(1000);
			} catch (Exception ex) {
				Thread.currentThread().interrupt();
			}
		});
	}
}
