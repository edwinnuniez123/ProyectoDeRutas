/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planificadorderutas;
import java.util.*;
import java.util.stream.IntStream;
/**
 *
 * @author Edwin
 */
public class Population {
    private ArrayList<Rutas> rutas =new ArrayList<Rutas>(AlgoritmoGenetico.POPULATION_SIZE);
    public Population(int populationSize, AlgoritmoGenetico algGenetico){
        IntStream.range(0,populationSize).forEach(x -> rutas.add(new Rutas(algGenetico.getInitialRoute())));
    }
    public Population(int populationSize, ArrayList<Ciudad> cities){
        IntStream.range(0,populationSize).forEach(x -> rutas.add(new Rutas(cities)));
    }
    public ArrayList<Rutas> getRoutes(){return rutas;}
    public void sortRoutesByFitness(){
        rutas.sort((ruta1, ruta2) -> {
            int flag = 0;
            if(ruta1.getFitness() > ruta2.getFitness()) flag = -1;
            else if(ruta1.getFitness() < ruta2.getFitness()) flag = 1;
            return flag;
        });
    }
}
