import java.util.Stack;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.ArrayList;

public class Main {
    
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

    public class AreaAtencion {
        String nombre;
        PriorityQueue <Paciente> pacientesHeap;
        int capacidadMaxima;
        
        public AreaAtencion(){}
        
        public void ingresarPaciente(Paciente p){}
        
        public Paciente atenderPaciente(){
            Paciente p = new Paciente("","","");
            return p;
        }
        
        public boolean estaSaturada(){ return false; }
        
        public ArrayList<Paciente>obtenerPacientesPorHeapSort(){
            ArrayList <Paciente> lista = new ArrayList <Paciente>();
            return lista;
        }
        
    }

    public class Hospital {
        Map <String, Paciente> pacientesTotales;
        Map <String, AreaAtencion> areasAtencion;
        ArrayList <Paciente> pacientesAtendidos;
        
        public Hospital(){}
        
        public void registrarPacientes(){}
        
        public void reasignarCategoria(){}
        
        public Paciente atenderSiguiente(){
            Paciente p = new Paciente();
            return p;
        }
        
        public ArrayList <Paciente> obtenerPacientesPorCategoria(int categoria){
            ArrayList <Paciente> lista = new ArrayList <Paciente>();
            return lista;
        }
        
        public AreaAtencion obtenerArea(String nombre){
            AreaAtencion a = new AreaAtencion();
            return a;
        }
        
        
    }
    
	public static void main(String[] args) {
		System.out.println("Hello World");
	}
}
