import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.Random;

class Paciente{

    String nombre;
    String apellido;
    String id;
    int categoria;
    long tiempoLLegada;
    String estado;
    String area;
    Stack<String> historialCambios;
    long tiempoDeAtencion;

    public  Paciente(String nombre, String apellido, String id, int categori, String area, long a) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.id = id;
        this.categoria = categori;
        this.area = area;
        this.estado = "en_espera";
        this.tiempoLLegada = a;
        this.historialCambios = new Stack<>();
        this.tiempoDeAtencion = -1;
    }

    public long tiempoEsperaActual(long TiempoActualSimulacion) {
        if (tiempoDeAtencion == -1) {
            return TiempoActualSimulacion - tiempoLLegada;
        }
        return tiempoDeAtencion - tiempoLLegada;
    }

    public void registrarCambio(String descripcion){
        historialCambios.push(descripcion);
    }

    public String obtenerUltimoCambio(){
        return historialCambios.pop();
    }

    public void registrarTiempoDeAtencion(long TiempoDeAtencion){
        this.tiempoDeAtencion = TiempoDeAtencion;
    }

}

class AreaAtencion{

    String nombre;
    PriorityQueue<Paciente> pacientesHeap;
    int capacidadMax;
    //CONSTRUCTOR
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
        if(pacientesHeap.size()<capacidadMax) return false;
        else {
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

class Hospital {
    Map<String, Paciente> pacientesTotales = new HashMap<>();
    PriorityQueue<Paciente> colaAtencion = new PriorityQueue<>(
            Comparator.comparingInt((Paciente p) -> p.categoria)
                    .thenComparingLong(p -> p.tiempoLLegada)
    );

    Map<String, AreaAtencion> areasAtencion = new HashMap<>();
    List<Paciente> pacientesAtendidos = new ArrayList<>();

    public void registrarPaciente(Paciente p) {
        pacientesTotales.put(p.id, p);
        colaAtencion.add(p);
        AreaAtencion area = areasAtencion.get(p.area);
        if (area != null) {
            area.ingresarPaciente(p);
        }else{
            System.out.println("Área no encontrada");
        }

    }

    public void reasignarCategoria(String id, int nuevaCategoria) {
        if (!pacientesTotales.containsKey(id)) {
            System.out.println("Error al reasignar categoria, paciente no encontrado");
        } else {

            Paciente paciente = pacientesTotales.get(id);
            colaAtencion.remove(paciente);
            AreaAtencion areaAtencionn = areasAtencion.get(paciente.area);
            if(areaAtencionn != null) {
                areaAtencionn.pacientesHeap.remove(paciente);//quita al paciente especificamente en esa area y en la lista de pacientesHeap(pacientes con prioridad)
            }
            paciente.categoria = nuevaCategoria;
            registrarPaciente(paciente);
        }
    }

    public Paciente atenderSiguiente(){
        return colaAtencion.poll();
    }

    public List<Paciente> obtenerPacientesPorCategoria(int categoria){
        List<Paciente> pacientes = new ArrayList<>();
        for(Paciente p: pacientesTotales.values()){
           if(p.categoria == categoria){
               pacientes.add(p);
           }
        }
        return pacientes;
    }

    public AreaAtencion obtenerArea(String nombre){
        return areasAtencion.get(nombre);
    }
}

class GeneradorPacientes{
    static String[] nombres ={"Maria", "Ana", "Sofia", "Laura", "Luisa", "Carmen", "Isabel", "Patricia", "Elena", "Claudia", "Adriana",
            "Gabriela", "Valeria", "Camila", "Natalia", "Andrea", "Teresa", "Rosa", "Diana", "Beatriz", "Juan", "Luis", "Carlos", "Pedro", "Jose", "Miguel",
            "Alejandro", "Andres", "Diego", "Fernando", "Javier", "Ricardo", "Roberto", "Sergio", "Eduardo", "Manuel", "Rafael", "Antonio", "Francisco", "Daniel"};
    static String[] apellidos ={"González", "Rodríguez", "López", "Martínez", "Pérez", "Gómez", "Hernández", "García", "Sánchez", "Ramírez", "Flores", "Díaz", "Castro", "Romero",
            "Torres", "Vargas", "Jiménez", "Mendoza", "Silva", "Rojas", "Medina", "Herrera", "Aguilar", "Guerrero", "Ramos", "Ortega", "Núñez", "Guzmán", "Cortés",
            "Navarro", "Peña", "Vega", "Sandoval", "Paredes"};
    static String[] Area ={"SAPU", "urgencia_adulto", "infantil"};
    static Random random = new Random();

    public static ArrayList<Paciente> generarPacientes (int cantidad){
        ArrayList<Paciente> pacientes = new ArrayList<>();
        long tiempoinicio = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toEpochSecond();

            for(int j=0; j<cantidad; j++) {
                long tiempodeLLegada = tiempoinicio + (j*600);
                String nombre = nombres[random.nextInt(nombres.length)];
                String apellido= apellidos[random.nextInt(apellidos.length)];

                String id = "K" + String.format("%04d", j);

                int categoria;
                int probabilidad =random.nextInt(101);
                if(probabilidad < 10) {
                    categoria=1;
                } else if (probabilidad < 25) {
                    categoria = 2;
                } else if (probabilidad < 43) {
                    categoria = 3;
                } else if (probabilidad < 70) {
                    categoria = 4;
                } else {
                    categoria = 5;
                }
                String area = Area[random.nextInt(Area.length)];

                pacientes.add(new Paciente(nombre,apellido, id, categoria, area, tiempodeLLegada));
            }
        return pacientes;
    }

    public static void GenerarArchivo(List<Paciente> pacientes, String nombreArchivo){
        try(PrintWriter writer = new PrintWriter(new File(nombreArchivo))){
            writer.println("Id, Nombre, Apellido, Categoria, Tiempo de llegada, Area ");

            for(Paciente p: pacientes){
                writer.printf("%s,%s,%s,%d,%d,%s\n", p.id, p.nombre, p.apellido, p.categoria, p.tiempoLLegada, p.area);
            }
            System.out.println("Archivo "+nombreArchivo+" generado");
        } catch (FileNotFoundException e) {
            System.out.println("Error al generar archivo");
        }
    }
}

class SimuladorUrgencia {
    Hospital hospital;
    List<Paciente> pacientes24horas;
    int[] pacientesAtendidosPorCategoria;
    List<Paciente> ListapacientesSuperaronTiempoMaximo;
    Map<Integer, List<Long>> tiemposPorCategoria;
    //long tiempoActual;
    int PacientesAcumulados;
    int minuto;
    Reloj relojSimulacion;


    public int obtenerTiempoMax(int categoria) {
        if (categoria == 1) {
            return 0;
        }else if (categoria == 2) {
            return 30;
        }else if (categoria == 3) {
            return 90;
        }else if (categoria == 4) {
            return 180;
        }else if (categoria == 5) {
            return Integer.MAX_VALUE;
        }else{
            return Integer.MAX_VALUE;
        }
    }

    public void simular(int cantidad) {
        hospital = new Hospital();
        hospital.areasAtencion.put("SAPU", new AreaAtencion("SAPU",100));
        hospital.areasAtencion.put("urgencia_adulto", new AreaAtencion("urgencia_adulto",100));
        hospital.areasAtencion.put("infantil", new AreaAtencion("infantil",100));

        pacientes24horas = new ArrayList<>();
        pacientes24horas = GeneradorPacientes.generarPacientes(cantidad);

        PacientesAcumulados = 0;

        pacientesAtendidosPorCategoria = new int[6]; 

        ListapacientesSuperaronTiempoMaximo = new ArrayList<>();

        tiemposPorCategoria = new HashMap<>();
        for(int i = 1; i <= 6; i++){
            tiemposPorCategoria.put(i, new ArrayList<>());
        }

        relojSimulacion = new Reloj();

        int indicePaciente = 0;

        for(minuto=0; minuto < 1440; minuto++){
            relojSimulacion.avanzarMin();
            long tiempoActual = relojSimulacion.getTiempoActualSegundos();

            //verificar si algun paciente excedio el timpo max
            PriorityQueue<Paciente> pq = new PriorityQueue<>(hospital.colaAtencion);

            List<Paciente> pacientesParaReasignar = new ArrayList<>();
            
            for(Paciente p: pq){

                long tiempoEspera= p.tiempoEsperaActual(tiempoActual)/60;
                int tiempo_max=obtenerTiempoMax(p.categoria);

                if(tiempoEspera>tiempo_max && p.estado.equals("en_espera")){
                    pacientesParaReasignar.add(p);
                }
            }
            for(Paciente p: pacientesParaReasignar){
                 hospital.reasignarCategoria(p.id, 1);
                 p.registrarCambio("Elevado a C1 por exceder tiempo máximo");
                 ListapacientesSuperaronTiempoMaximo.add(p);
                 System.out.println(relojSimulacion.HoraFormateada()+" - Paciente "+p.id+" elevado a C1");
            }
                    

            if(minuto%10==0 && indicePaciente<pacientes24horas.size()){

                Paciente nuevoPaciente;
                nuevoPaciente = pacientes24horas.get(indicePaciente);

                hospital.registrarPaciente(nuevoPaciente);

                indicePaciente++;
                System.out.println(relojSimulacion.HoraFormateada()+" ingreso el paciente: "+ nuevoPaciente.id);

                PacientesAcumulados++;

                if(PacientesAcumulados>=3){
                    int contador=0;
                    while(contador<2) {
                        Paciente paciente_a_atender = hospital.atenderSiguiente();
                        if(paciente_a_atender!=null) {
                            paciente_a_atender.registrarTiempoDeAtencion(tiempoActual);
                            //cambia el estado del paciente
                            paciente_a_atender.estado = "atendido";
                            //calcular tiempo

                            long TiempoEsperaTotalPaciente = tiempoActual - paciente_a_atender.tiempoLLegada;
                            //guarda el tiempo por categoria
                            tiemposPorCategoria.get(paciente_a_atender.categoria).add(TiempoEsperaTotalPaciente);
                            //contador de pacientes por categoria
                            pacientesAtendidosPorCategoria[paciente_a_atender.categoria]++;
                            hospital.pacientesAtendidos.add(paciente_a_atender);
                        
                        }
                        contador++;
                    }
                    PacientesAcumulados = 0;
                }
            }
                //atender paciente
            if(minuto%15==0){
                Paciente x=hospital.atenderSiguiente();

                if(x!=null){
                    x.registrarTiempoDeAtencion(relojSimulacion.getTiempoActualSegundos());
                    x.estado="atendido";
                    long timeW=x.tiempoEsperaActual(relojSimulacion.getTiempoActualSegundos());
                    tiemposPorCategoria.get(x.categoria).add(timeW);
                    pacientesAtendidosPorCategoria[x.categoria]++;
                    hospital.pacientesAtendidos.add(x);

                    x.registrarCambio("Paciente:"+x.id+" atendido, time:"+relojSimulacion.HoraFormateada());
                }
            }

        }
    }

    public void imprimirEstadisticas(int opcion){
        long tiempoFinal = relojSimulacion.getTiempoActualSegundos();
        switch(opcion){
        case 1:
            System.out.println("Tiempo de atención por paciente.");
                for(Paciente p: hospital.pacientesAtendidos) {
                    long tiempo = p.tiempoEsperaActual(tiempoFinal)/60;
                    System.out.println("NOMBRE:"+p.nombre + ", ID:" + p.id + ", CATEGORIA:" + p.categoria + ", TIEMPO:" + tiempo);

                }
                    System.out.println();
                    break;
                    
                    
        case 2:
                    System.out.println("Cantidad de pacientes atendidos por categoria.");
                    for(int i=1;i<6;i++){
                        System.out.println(i+","+pacientesAtendidosPorCategoria[i]);
                    }
                    System.out.println();
                    break;
                    
                    
        case 3:
                    System.out.println("Promedios de tiempo de espera por categoria.");
                    for(int i=1;i<6;i++){
                        List<Long> tiempos= tiemposPorCategoria.get(i);
                        double promedio=0.0;
                        if(!tiempos.isEmpty()){
                            long suma= 0;
                            for(Long aux: tiempos){
                                suma += aux;
                            }
                            promedio = suma / tiempos.size();
                        }
                        System.out.println("categoria:"+i+", promedio:"+promedio/60);
                    }
                    System.out.println();
                    break;
                    
                    

        case 4:
                    System.out.println("Pacientes que excedieron el tiempo de espera.");
                    if(ListapacientesSuperaronTiempoMaximo.isEmpty()){
                        System.out.println("No hay pacientes que excedieron el tiempo de espera.");
                        System.out.println();
                    }else{
                        for(Paciente p: ListapacientesSuperaronTiempoMaximo) {
                            long tiempoEspera = p.tiempoEsperaActual(relojSimulacion.getTiempoActualSegundos()) / 60;
                            int tiempoMAx = obtenerTiempoMax(p.categoria);
                            if (tiempoEspera > tiempoMAx) {
                                System.out.println("NOMBRE:"+p.nombre + ", ID:" + p.id + ", CATEGORIA:" + p.categoria + ", TIEMPO:" + tiempoEspera);
                            }
                        }
                        System.out.println();
                    }
                    break;
            }
    }
    
}

    class Reloj{
    private long TiempoInicioSimulacion;
    private long minutosTranscurridos;

    public Reloj(){
        this.TiempoInicioSimulacion = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toEpochSecond();
        this.minutosTranscurridos = 0;
    }
    public void avanzarMin(){
        minutosTranscurridos++;
    }
    public long getTiempoActualSegundos(){
        return TiempoInicioSimulacion + (minutosTranscurridos*60);
    }
    public int getMinutoActual(){
        return (int) (minutosTranscurridos%60);
    }
    public int getHoraActual(){
        return (int) (minutosTranscurridos/60);
    }
    public String HoraFormateada(){
        return String.format("%02d:%02d",getHoraActual(),getMinutoActual());
    }
    }

public class Main {
    public static void main(String[] args) {
        SimuladorUrgencia simulador1 = new SimuladorUrgencia();
        System.out.println("\n==== PRUEBA: 20 PACIENTES ====");
        simulador1.simular(200);
        simulador1.imprimirEstadisticas(1);
        simulador1.imprimirEstadisticas(2);
        simulador1.imprimirEstadisticas(3);
        simulador1.imprimirEstadisticas(4);

    }
}

