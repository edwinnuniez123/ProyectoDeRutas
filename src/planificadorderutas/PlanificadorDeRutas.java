/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planificadorderutas;

/**
 *
 * @author Edwin
 */
import java.util.*;
import java.time.*;
import java.io.*; 
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class PlanificadorDeRutas {
    
    /**
     * @param args the command line arguments
     */
    
    final static boolean VERBOSE_FLAG = false;
     public ArrayList<Ciudad> initialRoute = new ArrayList<Ciudad>(Arrays.asList(
              new Ciudad("Boston", 42.3601, -71.0589),
               new Ciudad("Houston", 29.7604, -95.3698), 
               new Ciudad("Austin", 30.2672, -97.7431), 
               new Ciudad("San Francisco", 37.7749, -122.4194),
               new Ciudad("Denver", 39.7392, -104.9903), 
               new Ciudad("Los Angeles", 34.0522, -118.2437), 
               new Ciudad("Chicago", 41.8781, -87.6298), 
               new Ciudad("New York", 40.7128,-74.0059),
               new Ciudad("Dallas",32.7767, -96.7970),
               new Ciudad("Seattle", 47.6062, -122.3321)
               //new City("Cape Town", -33.9249, 18.4241)
             //, -33.8675,151.2070),
                //35.6895, 139.6917),
        ));
    public static void main(String[] args) {
        // TODO code application logic here
        try{
            File salida=new File("Salida.txt");
            BufferedWriter output = new BufferedWriter(new FileWriter(salida));
            PlanificadorDeRutas driver = new PlanificadorDeRutas();
            Population population = new Population(AlgoritmoGenetico.POPULATION_SIZE, driver.Cargar()); 
            //Population population = new Population(AlgoritmoGenetico.POPULATION_SIZE, driver.initialRoute); 
            population.sortRoutesByFitness();
            AlgoritmoGenetico algGenetico=new AlgoritmoGenetico(driver.Cargar());
            // AlgoritmoGenetico algGenetico=new AlgoritmoGenetico(driver.initialRoute);
            int generationNumber = 0; 
            driver.printHeading(generationNumber++);
            driver.printPopulation(population);
            while (generationNumber < AlgoritmoGenetico.NUMB_OF_GENERATIONS) { 
                driver.printHeading(generationNumber++); 
                population = algGenetico.evolve(population); 
                population.sortRoutesByFitness(); 
                driver.printPopulation(population);
            }
            output.write("Best Route Found so far: " + population.getRoutes().get(0));
            output.write("w/ a distance of: "+String.format("%.2f",population.getRoutes().get(0).calculateTotalDistance())+"  miles");
        
            output.close();
        }catch (Exception ex){
           ex.printStackTrace();
        }
        
         
    }
    
    public void printPopulation(Population population) { 
        try{
            File salida=new File("Salida1.txt");
            BufferedWriter output = new BufferedWriter(new FileWriter(salida));
            population.getRoutes().forEach(x -> {
                try {
                    output.write(Arrays.toString(x.getCities().toArray()) + " |     "+
                            String.format("%.4f", x.getFitness()) +"    |     "+ String.format("‘%.2f", x.calculateTotalDistance()));
                } catch (IOException ex) {
                    Logger.getLogger(PlanificadorDeRutas.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            output.write("");
        
          output.close();
        }catch (Exception ex){
           ex.printStackTrace();
        }
    }
    
    public void printHeading(int generationNumber) 
    {
        try{
            File salida=new File("Salida2.txt");
            BufferedWriter output = new BufferedWriter(new FileWriter(salida));
            output.write("> Generation # "+generationNumber);
            String headingColumn1 = "Ruta";
            String remainingHeadingColumns = "Pepsi_Y | Pepsi_X (in miles)"; 
            int cityNamesLength = 0;
            for (int x = 0; x < initialRoute.size(); x++) cityNamesLength += initialRoute.get(x).getName().length();
            int arrayLength = cityNamesLength + initialRoute.size()*2;
            int partialLength = (arrayLength - headingColumn1.length())/2;
            for (int x=0; x < partialLength; x++) output.write(" ");
            output.write(headingColumn1);
            for (int x=0; x < partialLength; x++)output.write(" "); 
            if ((arrayLength % 2) == 0)System.out.print(" ");
            output.write(" | "+ remainingHeadingColumns); 
            cityNamesLength += remainingHeadingColumns.length() + 3;
            for (int x=0; x < cityNamesLength+initialRoute.size()*2; x++)output.write("-");
            output.write("");
        
        
           output.close();
        }catch (Exception ex){
           ex.printStackTrace();
        }
    }
    
    public ArrayList<Ciudad> Cargar() {
        try {
            File archivo = new File("Entrada.txt"); 
            BufferedWriter bw = null;
            if (archivo.exists()) {
                BufferedReader br = new BufferedReader(new FileReader( "Entrada.txt"));
                String linea;
                while ((linea = br.readLine()) != null) {
                    StringTokenizer st = new StringTokenizer(linea, ",");
                    String nom = st.nextToken().trim(); 
                    double latitud = Double.parseDouble(st.nextToken().trim());
                    double longitud = Double.parseDouble(st.nextToken().trim());                                       
                    Ciudad x = new Ciudad(nom, latitud, longitud);
                    x.getName();
                    x.getLatitude();
                    x.getLongitude();
                }
                br.close();
            } else
                bw.write("El archivo estudiantes.txt no existe");
         } catch (Exception x) {
             x.printStackTrace();
         }
        return initialRoute;

}
    
}

/*public void printPopulation(Population population) { p
opulation.getRoutes().forEach(x -> {
System.out.println(Arrays.toString(x.getCities().toArray()) + " |	"+
String./orroot("%.4f", x.getFitness()) +"	|	"+ String.format^"X.2f", x.calculateTotalDistance()));
»;
System.out.println("");*/

/* public void printHeading(int generationNumber) 
    {
        System.out.println("> Generation # "+generationNumber);
        String headingColumn1 = "Ruta";
        String remainingHeadingColumns = "Pepsi_Y | Pepsi_X (in miles)"; 
        int cityNamesLength = 0;
        for (int x = 0; x < initialRoute.size(); x++) cityNamesLength += initialRoute.get(x).getName().length();
        int arrayLength = cityNamesLength + initialRoute.size()*2;
        int partialLength = (arrayLength - headingColumn1.length())/2;
        for (int x=0; x < partialLength; x++) System.out.print(" ");
        System.out.print(headingColumn1);
        for (int x=0; x < partialLength; x++)System.out.print(" "); 
        if ((arrayLength % 2) == 0)System.out.print(" ");
        System.out.println(" | "+ remainingHeadingColumns); 
        cityNamesLength += remainingHeadingColumns.length() + 3;
        for (int x=0; x < cityNamesLength+initialRoute.size()*2; x++)System.out.print("-");
        System.out.println("");
        
       
    }*/


/* final int FILAS = 5, COLUMNAS = 4;
        Scanner sc = new Scanner(System.in);
        int i, j, mayor, menor;
        int filaMayor, filaMenor, colMayor, colMenor;
        int[][] A = new int[FILAS][COLUMNAS];
        System.out.println("Lectura de elementos de la matriz: ");
        for (i = 0; i < FILAS; i++) {
            for (j = 0; j < COLUMNAS; j++) {
                System.out.print("A[" + i + "][" + j + "]= ");
                A[i][j] = sc.nextInt();
            }
        }
        System.out.println("valores introducidos:");
        for (i = 0; i < A.length; i++) { 
            for (j = 0; j < A[i].length; j++) {
                System.out.print(A[i][j] + " ");
            }
            System.out.println();
        }
        mayor = menor = A[0][0];//se toma el primero como mayor y menor
        filaMayor = filaMenor = colMayor = colMenor = 0;

        for (i = 0; i < A.length; i++) {  //
            for (j = 0; j < A[i].length; j++) {
                if (A[i][j] > mayor) {
                    mayor = A[i][j];
                    filaMayor = i;
                    colMayor = j;
                } else if (A[i][j] < menor) {
                    menor = A[i][j];
                    filaMenor = i;
                    colMenor = j;
                }
            }           
        }
        System.out.print("Elemento mayor: " + mayor);
        System.out.println(" Fila: "+ filaMayor + " Columna: " + colMayor);
        System.out.print("Elemento menor: " + menor);
        System.out.println(" Fila: "+ filaMenor + " Columna: " + colMenor);*/
