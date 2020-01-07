package controller;

import entity.Director;
import entity.Empleado;
import entity.Operador;
import entity.Supervisor;
import exception.MaxCantThreadsException;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;


public class Dispatcher {

	private static final String HILOS_EXCEDIDOS= "Se excedió la cantidad de hilos concurrentes, se reintentará en 12 segundos";
	private static final String REINTENTAR= "Se reintentará en 10 segundos";

	List<Operador> operadores= new ArrayList();
	List<Supervisor> supervisores= new ArrayList();
	List<Director> directores= new ArrayList();

	final static Logger logger = Logger.getLogger(Dispatcher.class);
	
	// asegura atomicidad al momento de leer la variable
	static AtomicInteger cantThreads= new AtomicInteger(1);
	
	// cantidad de reintentos para asignar a un empleado la llamada
	static AtomicInteger cantReintentosAsignacionLLamada= new AtomicInteger(0);
	
	// cantidad de reintentos al superar los 10 Threads concurrentes
	static AtomicInteger cantReintentosThreads= new AtomicInteger(0);


	/**
	 * método que valida la ejecución total de los threads, valida que solo se ejecuten 10 de manera sincronica
	 * tiene un proceso de Reintentos en caso que este ocupado el Empleado
	 */
	public Empleado dispatcherCall() {
		Empleado emp= null;
		
		try {
			validarCantidadThreadsEjecutandose();				
			emp= asignarLLamadaEmpleado();			
						
		}catch(MaxCantThreadsException max) {
			logger.info("ERROR: "+MaxCantThreadsException.getMsj());
			
		}catch(Exception e) {
			// agregar alguna lógica para que el sistema realice en este caso
			logger.info("ERROR DESCONOCIDO "+e.getMessage());
		}
		return emp;
	}
	
	
	/**
	 * Este método valida la cantidad de Threads en ejecución, si es menor de 10 retorna el numero del Thread y continua con la
	 * asignación del empleado, de llegar a los 10 Threads en ejecución el sistema realiza 3 reintentos en lapsos de 12 segundos
	 * cada uno, pasados estos reintentos lanza una excepcion, se imprime por log un mensaje y termina la ejecución
	 */
	
	private synchronized int validarCantidadThreadsEjecutandose() throws InterruptedException, MaxCantThreadsException{
		int reintentosThreads= cantReintentosThreads.get();

		if (cantThreads.get() <= 10) {
			
			int threadEjecutandose= cantThreads.getAndIncrement();
		
			return threadEjecutandose;
			
		}else {			
		
			if (reintentosThreads < 3) {									
				logger.info(HILOS_EXCEDIDOS);

				cantReintentosThreads.incrementAndGet();
				
				wait (12000);
				validarCantidadThreadsEjecutandose();
				
			}else {
				// se realizaron los 3 reintentos
				throw new MaxCantThreadsException();
			}
			
			return 0;
		}
		
	}
	
	
	/**
	 * Este método obtiene un empleado libre y le asigna la llamada. De no haber nadie disponible el método cuenta con un sistema de
	 * reintentos en lapsos de 10 segundos 
	 * 
	 */
	private Empleado asignarLLamadaEmpleado() throws InterruptedException  {		
		// obtengo la cantidad de reintentos realizados
		int reintentos= cantReintentosAsignacionLLamada.get();
		
		Empleado e= obtenerEmpleadoDisponible();
		
		if (e != null) {
			atendiendoLLamada(e);
			
			// luego se libera el empleado 
			e.setEstaLibre(true);			
		
		}else {
			// reintenta como maximo 5 veces asignarle un empleado
			if (reintentos < 5) {
				cantReintentosAsignacionLLamada.incrementAndGet();
				
				logger.info(REINTENTAR);
				 
				Thread.sleep(10000);
				asignarLLamadaEmpleado();
				
			}
		}
		return e;				
	}
	
	/**
	 * Este método simula un empleado atendiendo una llamada,al ingresar se le cambia su estado, para q no lo tome otro threads
	 */

	private void atendiendoLLamada(Empleado e) throws InterruptedException {	
		e.setEstaLibre(false);		
		
		// simulación del tiempo de llamada entre 5 y 10 segundos
		Random r = new Random();
		int tiempoLLamada = (int)(Math.random()*(5-10+1)+10);

		//simulación del empleado hablando por telefono
	    Thread.sleep(tiempoLLamada * 1000);
		
		logger.info("Se le asignó la llamada al empleado: "+e.getNombre()+ " y tuvo una duración de: "+
				tiempoLLamada+ " milisegundos");
		
		
	}
	
	
	/**
	 * Este método obtiene un empleado libre, primero comienza buscando por operadores libres, luego supervisores libres
	 * y por último directores libres, de encontrar retorna el empleado, caso contrario null
	 */
	private synchronized Empleado obtenerEmpleadoDisponible() {
		Optional<Operador> empleadoOperador = operadores.stream().filter(Operador::isEstaLibre).findFirst();

		if (empleadoOperador.isPresent()) {
			return empleadoOperador.get();

		}else {
			Optional<Supervisor> empleadoSupervisor = supervisores.stream().filter(Supervisor::isEstaLibre).findFirst();
			
			if (empleadoSupervisor.isPresent()) {
				return empleadoSupervisor.get();
			
			}else {

				Optional<Director> director = directores.stream().filter(Director::isEstaLibre).findFirst();
				
				if (director.isPresent()) {
					return director.get();
				
				}else {
					// no hay nadie disponible					
					return null;								
				}
			}
		}			
	}
	

	public List<Operador> getOperadores() {
		return operadores;
	}


	public void setOperadores(List<Operador> operadores) {
		this.operadores = operadores;
	}


	public List<Supervisor> getSupervisores() {
		return supervisores;
	}


	public void setSupervisores(List<Supervisor> supervisores) {
		this.supervisores = supervisores;
	}


	public List<Director> getDirectores() {
		return directores;
	}


	public void setDirectores(List<Director> directores) {
		this.directores = directores;
	}
	
	
}