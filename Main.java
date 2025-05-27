import java.util.Stack;
public class Main
{
    
    public class Paciente {
        String nombre;
        String apellido;
        String id;
        int categoria;
        long tiempoLlegada;
        String estado;
        String area;
        Stack <String> historialCambios;
        
        public Paciente(String nombre, String apellido, String id){
            this.nombre = nombre;
            this.apellido = apellido;
            this.id = id;
        }
        
        public long tiempoEsperaActual(){
            return 0;
        }
        
        void registrarCambio(String descripcion){
            
        }
        
        String obtenerUltimoCambio(){
            return "";
        }
        
        
    }
    
	public static void main(String[] args) {
		System.out.println("Hello World");
	}
}
