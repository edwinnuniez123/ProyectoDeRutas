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
public class AlgoritmoGenetico {
    public static final double MUTATION_RATE = 0.25;
    public static final int TOURNAMENT_SELECTION_SIZE = 3;
    public static final int POPULATION_SIZE=8;
    public static final int NUMB_OF_ELITE_ROUTES = 1;
    public static final int NUMB_OF_GENERATIONS = 30; 
    private ArrayList<Ciudad> initialRoute=null;
    public AlgoritmoGenetico(ArrayList<Ciudad> initialRoute){this.initialRoute=initialRoute;}
    public ArrayList<Ciudad> getInitialRoute(){return initialRoute;}
    public Population evolve(Population population) { return mutatePopulation(crossoverPopulation(population));}
    Population crossoverPopulation(Population population) { 
       Population crossoverPopulation = new Population(population.getRoutes().size(), initialRoute);//, this
       IntStream.range(0, NUMB_OF_ELITE_ROUTES).forEach(x -> crossoverPopulation.getRoutes().set(x, population.getRoutes().get(x)));
       IntStream.range(NUMB_OF_ELITE_ROUTES, crossoverPopulation.getRoutes().size()).forEach(x -> {
            Rutas ruta1 = selectTournamentPopulation(population).getRoutes().get(0);
            Rutas ruta2 = selectTournamentPopulation(population).getRoutes().get(0); 
            crossoverPopulation.getRoutes().set(x, crossoverRoute(ruta1, ruta2));
       });
       return crossoverPopulation;
    }

    Population mutatePopulation(Population population) { 
        population.getRoutes().stream().filter(x -> population.getRoutes().indexOf(x) >= NUMB_OF_ELITE_ROUTES).forEach(x -> mutateRoute(x)); 
        return population;
    }

    //an example crossover of rutal and ruta2
    //	rutal	: [New York, San Francisco, Houston, Chicago, Boston, Austin, Seattle, Denver, Dallas, Los Angeles]
    //	ruta2	: [Los Angeles, Seattle, Austin, Boston, Denver, New York, Houston, Dallas, San Francisco, Chicago]
    //intermediate crossoverRoute: [New York, San Francisco, Houston, Chicago, Boston, null, null, null, null, null]
    // final crossoverRoute: [New York, San Francisco, Houston, Chicago, Boston, Los Angeles, Seattle, Austin, Denver, Dallas] 
    Rutas crossoverRoute(Rutas ruta1, Rutas ruta2) { 
        Rutas crossoverRoute = new Rutas(initialRoute);
        Rutas tempRuta1 = ruta1;
        Rutas tempRuta2 = ruta2;
        if(Math.random()<0.5){
            tempRuta1 = ruta1;
            tempRuta2 = ruta2;
        }
        for (int x=0; x<crossoverRoute.getCities().size()/2; x++)
            crossoverRoute.getCities().set(x, tempRuta1.getCities().get(x));
        
        return fillNullsInCrossoverRoute(crossoverRoute, tempRuta2);
    }
    //crossverRoute: [slattle, Houston, Denver, Los Angeles, San Francisco, null, null, null, null, null]
    //    route:     [Los Angeles, Chicago, Austin, Houston, Denver, San Francisco, Seattle, Boston, New York, Dallas] 
    //crossverRoute: [Seattle, Houston, Denver, los Angeles, San Francisco, Chicago, Austin, Boston, New York, Dallas]
    private Rutas fillNullsInCrossoverRoute(Rutas crossoverRoute, Rutas route) {
        route.getCities().stream().filter(x -> !crossoverRoute.getCities().contains(x)).forEach(cityX -> { 
            for (int y = 0; y < route.getCities().size(); y++) { 
                if (crossoverRoute.getCities().get(y) == null) { 
                    crossoverRoute.getCities().set(y, cityX);
                    break;
                 } 
            }
        });
        return crossoverRoute;
    }


    //an example route mutation
    //original route: [Boston, Denver, Los Angeles, Austin, New York, Seattle, Chicago, San Francisco, Dallas, Houston]
    // mutated route: [Boston, Denver, New York, Austin, Los Angeles, Seattle, San frenciscp, Chicago, Dallas, Houston] 
    Rutas mutateRoute(Rutas ruta) {
         ruta.getCities().stream().filter(x -> Math.random() < MUTATION_RATE).forEach(cityX -> { 
             int y = (int) (ruta.getCities().size() * Math.random());
             Ciudad cityY = ruta.getCities().get(y);
             ruta.getCities().set(ruta.getCities().indexOf(cityX), cityY); ruta.getCities().set(y, cityX);
        });
        return ruta;
    }
    
    Population selectTournamentPopulation(Population population) {
        Population tournamentPopulation = new Population(TOURNAMENT_SELECTION_SIZE, initialRoute);//,this
        IntStream.range(0, TOURNAMENT_SELECTION_SIZE) .forEach(x -> tournamentPopulation.getRoutes().set(
                x, population.getRoutes().get((int) (Math.random() * population.getRoutes().size())))); 
        tournamentPopulation.sortRoutesByFitness(); 
        return tournamentPopulation;
    }
    




//an example route mutation

//original route: [Boston, Denver, Los Angeles, Austin, New York,	Seattle,	Chicago, San Francisco, Dallas,	Houston]

// mutated route: [Boston, Denver, New York, Austin, Los	Angeles,	Seattle,	San Francisco, Chicago., Dallas,	Houston]
}
