package exception;

public class MaxCantThreadsException extends Exception{

	private static final long serialVersionUID = -6946352366114577818L;

	private static String msj= "Se excedió la cantidad de Threads disponibles en ejecución";

	public static String getMsj() {
		return msj;
	}

	public static void setMsj(String msj) {
		MaxCantThreadsException.msj = msj;
	}
		
}
