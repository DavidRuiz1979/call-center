package entity;

public class Operador extends Empleado{

    /**
     * Es para que podamos obtener en el caso que se necesite una descripción de
     * las tareas que desarrolla el Operador
     */
    @Override
    public String tareasQueDesarrolla(){
        return " El Operador se encarga de atender llamadas ";
    }
}
