/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planificadorderutas;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
/**
 *
 * @author Edwin
 */

public class Rutas {
    private boolean isFitnessChanged=true;
    private double fitness = 0;
    private ArrayList<Ciudad> cities = new ArrayList<Ciudad>();
    
    public Rutas(AlgoritmoGenetico algGenetico){
	algGenetico.getInitialRoute().forEach(x -> cities.add(null));
    }
    
    public Rutas(ArrayList<Ciudad> cities){
	this.cities.addAll(cities);
	Collections.shuffle(this.cities);
    }

     public ArrayList<Ciudad> getCities(){
	isFitnessChanged = true;
	return cities;
     }


    public double getFitness() {
        if (isFitnessChanged == true) {
            fitness = (1/calculateTotalDistance())*10000; 
            isFitnessChanged = false;
        }
            return fitness;

    }
        
    public double calculateTotalDistance(){
	int citiesSize = this.cities.size();
	return (int) (this.cities.stream().mapToDouble(x-> {
            int cityIndex = this.cities.indexOf(x);
            double returnValue = 0;
            if (cityIndex < citiesSize - 1) returnValue = x.measureDistance(this.cities.get(cityIndex + 1));
            return returnValue;
	}).sum() + this.cities.get(0).measureDistance(this.cities.get(citiesSize - 1)));
    }

    public String toString(){	
        return	Arrays.toString(cities.toArray());
    } 


}
