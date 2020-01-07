package entity;

public class Supervisor extends Empleado{

    /**
     * Es para que podamos obtener en el caso que se necesite una descripción de
     * las tareas que desarrolla el Supervisor
     */
    @Override
    public String tareasQueDesarrolla(){
        return " El Supervisor se encarga de controlar a los operadores, y en caso de que esten" +
               " todos ocupados atiende llamadas ";
    }
}
