import java.util.Stack;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.ArrayList;

public class Main {
    //clase Paciente casi terminada, hay que verificar el metodo tiempoEsperaActual()
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
        	long ahora = System.currentTimeMillis()/1000;
       		return ahora-tiempoLLegada;
        }
        
        void registrarCambio(String descripcion){
            historialCambios.push(descripcion);
        }
        
        String obtenerUltimoCambio(){
            return historialCambios.pop();
        }
        
        
    }
	//Area de atención lista.
    class AreaAtencion{

    String nombre;
    PriorityQueue<Paciente> pacientesHeap;
    int capacidadMax;

    public AreaAtencion(int num){
        this.nombre = "";
        this.pacientesHeap = new PriorityQueue<>(Comparator.comparingInt((Paciente p)-> p.categoria).thenComparingLong(p-> p.tiempoLLegada));
        this.capacidadMax = num;
    }

    public AreaAtencion(String nombre, int capacidadMax) {
        this.nombre = nombre;
        this.capacidadMax = capacidadMax;
        this.pacientesHeap = new PriorityQueue<>(Comparator.comparingInt((Paciente p)-> p.categoria).thenComparingLong(p-> p.tiempoLLegada));
    }

    public void ingresarPaciente(Paciente pa){
        pacientesHeap.add(pa);
    }

    public Paciente atenderPaciente(){
        return pacientesHeap.poll();
    }

    public boolean estaSaturada(){
        if(pacientesHeap.size()<capacidadMax){
            return false;
        }else {
            return true;
        }
    }

    public List<Paciente> obtenerPacientesPorHeapSort(){
        List<Paciente> pacientes = new ArrayList<>();
        for(Paciente p: pacientesHeap){
            pacientes.add(p);
        }
        return pacientes;
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

    public class GeneradorPacientes {
        String[] palabras = {"Roberto","Juan","Alfredo","Francica","Martina","Gonzalo","Rodrigo"};
        int rnd1 = new Random().nextInt(palabras.length);
        String nombre = palabras[rnd1];        
        int rnd1 = new Random().nextInt(palabras.length);
        int rnd2 = (int) (Math.random() * 1000000) + 1;
        String rut = String.valueOf(rnd2);
        
        Paciente p = new Paciente(nombre, rut, "");
    }
    
	public static void main(String[] args) {
		System.out.println("Hello World");
	}
}
