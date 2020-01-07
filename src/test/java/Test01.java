
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


	/**
	 * Al ver la ejecucion del test se ve q el sistema procesa 10 hilos, al querer procesar el hilo 11 no lo puede hacer
	 * reintenta 3 veces mas y termina la ejecución
	 */
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

		Operador op2= new Operador();
		op2.setNombre("op2 - Agustin");
		op2.setDni(22222);
		op2.setEstaLibre(true);

		operadores.add(op2);

		Operador op3= new Operador();
		op3.setNombre("op3 - Maria");
		op3.setDni(3333);
		op3.setEstaLibre(true);

		operadores.add(op3);

		Operador op4= new Operador();
		op4.setNombre("op4 - Pamela");
		op4.setDni(4444);
		op4.setEstaLibre(true);

		operadores.add(op4);

		Operador op5= new Operador();
		op5.setNombre("op5 - Pedro");
		op5.setDni(5555);
		op5.setEstaLibre(true);

		operadores.add(op5);

		Operador op6= new Operador();
		op6.setNombre("op6 - Elisabet");
		op6.setDni(66666);
		op6.setEstaLibre(true);

		operadores.add(op6);

		Operador op7= new Operador();
		op7.setNombre("op7- Eva");
		op7.setDni(7777);
		op7.setEstaLibre(true);

		operadores.add(op7);

		Operador op8= new Operador();
		op8.setNombre("op8 - Enzo");
		op8.setDni(8888);
		op8.setEstaLibre(true);

		operadores.add(op8);

		Operador op9= new Operador();
		op9.setNombre("op9 - Joel");
		op9.setDni(99999);
		op9.setEstaLibre(true);

		operadores.add(op9);

		Operador op10= new Operador();
		op10.setNombre("op10 - Soledad");
		op10.setDni(101010);
		op10.setEstaLibre(true);

		operadores.add(op10);

		Supervisor sup= new Supervisor();
		sup.setNombre("sup - Sonia");
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
