package entity;

public class Director extends Empleado{

    /**
     * Es para que podamos obtener en el caso que se necesite una descripción de
     * las tareas que desarrolla el Director
     */
    @Override
    public String tareasQueDesarrolla(){
        return " El Director solo delega tareas y rara vez atiende llamadas, solo en el caso que no haya otro  supervisor " +
               " disponible ";
    }
}
