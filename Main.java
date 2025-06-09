import java.util.Stack;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.Random;
import java.util.Comparator;
import java.util.List;

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

	public Paciente(){} //Constructor de prueba
	
        public Paciente(String nombre, String apellido, String id){
            this.nombre = nombre;
            this.apellido = apellido;
            this.id = id;
        }

	//Tiempo de espera de paciente
        public long tiempoEsperaActual(){
        	long ahora = System.currentTimeMillis()/1000;
       		return ahora-tiempoLlegada;
        }
        //Regstrar cambio de paciente
        void registrarCambio(String descripcion){
            historialCambios.push(descripcion);
        }
        //Obtener último cambio de paciente
        String obtenerUltimoCambio(){
            return historialCambios.pop();
        }
        
        
    }
    class AreaAtencion {

    String nombre;
    PriorityQueue<Paciente> pacientesHeap;
    int capacidadMax;

	public AreaAtencion(){} //Constructor de prueba


	public AreaAtencion(int num){
		this.nombre = "";
		this.pacientesHeap = new PriorityQueue<>(Comparator.comparingInt((Paciente p)-> p.categoria).thenComparingLong(p-> p.tiempoLlegada));
		this.capacidadMax = num;
	}
	
	public AreaAtencion(String nombre, int capacidadMax) {
		this.nombre = nombre;
		this.capacidadMax = capacidadMax;
		this.pacientesHeap = new PriorityQueue<>(Comparator.comparingInt((Paciente p)-> p.categoria).thenComparingLong(p-> p.tiempoLlegada));
	}
	//Ingresar paciente
	public void ingresarPaciente(Paciente pa){
		pacientesHeap.add(pa);
	}
	//Atender paciente
	public Paciente atenderPaciente(){
		return pacientesHeap.poll();
	}
	//Saturado
	public boolean estaSaturada(){
		if(pacientesHeap.size()<capacidadMax){
		    return false;
		}else {
		    return true;
		}
	}

	public List<Paciente> obtenerPacientesPorHeapSort(){
		List<Paciente> pacientes = new ArrayList<>();
		PriorityQueue<Paciente> copia = new PriorityQueue<>(pacientesHeap);
		while (!copia.isEmpty()){
		    pacientes.add(copia.poll());
		}
		return pacientes;
	}
}

    public class Hospital {
      
        Map <String, Paciente> pacientesTotales;
        PriorityQueue<Paciente> colaAtencion; 
        Map <String, AreaAtencion> areasAtencion;
        ArrayList <Paciente> pacientesAtendidos;
        
        public Hospital(){}
        
        //Registrar paciente
        public void registrarPacientes(Paciente p){
          colaAtencion.add(p);
          pacientesTotales.put(p.nombre, p);
        }
        
        //Asignar nueva categoria a un paciente
        public void reasignarCategoria(String id, int nuevaCategoria){
          Paciente p = pacientesTotales.get(id);
          p.categoria = nuevaCategoria;
          p.historialCambios.push("Se cambió a categoría " + nuevaCategoria);
        }
        
        //Atender siguiente paciente
        public Paciente atenderSiguiente(){
            Paciente p = new Paciente();
            return p;
        }
        
        //Obtener paciente por categoria
        public ArrayList <Paciente> obtenerPacientesPorCategoria(int categoria){
            ArrayList <Paciente> lista = new ArrayList <Paciente>();
            ArrayList<Paciente> temporal = new ArrayList<>(colaAtencion);
            for(int i = 0; i < colaAtencion.size(); i++){
              Paciente paciente = temporal.get(i);
              if (paciente.categoria == categoria) {
                lista.add(paciente);
              }
            }
            return lista;
        }
        
        //Obtener area de atención por nombre
        public AreaAtencion obtenerArea(String nombre){
            return areasAtencion.get(nombre);
        }
        
        
    }

	public class GeneradorPacientes {
		String[] palabras = {"Roberto","Juan","Alfredo","Francica","Martina","Gonzalo","Rodrigo"};
		int rnd1 = new Random().nextInt(palabras.length);
		String nombre = palabras[rnd1];        
		int rnd2 = (int) (Math.random() * 1000000) + 1;
		String rut = String.valueOf(rnd2);
		
		Paciente p = new Paciente(nombre, rut, "");
	}

	public class SimuladorUrgencia {
		public SimuladorUrgencia(){}
	}
	
	public static void main(String[] args) {
		System.out.println("Hello World");
	}
}
