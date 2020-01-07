package entity;

public class Empleado {

	private int dni;
	private String nombre;
	private boolean estaLibre;

	/**
	 * metodo genérico, es para que podamos obtener en el caso que se necesite una descripción de
	 * las tareas que desarrolla cada uno de los diferentes empleados
 	 */
	public String tareasQueDesarrolla(){
		return "Empleado Genérico";
	}
	
	public int getDni() {
		return dni;
	}
	public void setDni(int dni) {
		this.dni = dni;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public boolean isEstaLibre() {
		return estaLibre;
	}
	public void setEstaLibre(boolean estaLibre) {
		this.estaLibre = estaLibre;
	}
	
	
}
