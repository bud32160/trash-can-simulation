package Simulation;

import java.util.Iterator;

import entities.InputManager;
import entities.Tour;
import entities.TrashCan;
import enumerations.EFillLevel;

public class Simulation {

	static InputManager manager = new InputManager();
	static Tour tour = new Tour();
	static ResultSet resultSet = new ResultSet();
	
	public static void main(String[] args) {
		
		/*
		 * First test dummy for a single simulation reading input from commandline
		 * 
		 * Missing: 
		 * - exception handling
		 * - input controller
		 * - gui for usecases
		 * - usable result display
		 * 
		 * Next step: 
		 * - introduce gps data to trashCans for visualizing in googlemap
		 */
		
		//Input Data
		manager.readInput();
		//Create Tour
		tour = createTour(manager);
		//Simulate tour
		resultSet = simulateTour(tour);
		//Print results
		printTour(tour);
		resultSet.printResultSet();
		
	}
	
	
	private static Tour createTour(InputManager manager) {
		Tour tour = new Tour(1, manager);
		return tour;
	}

	private static ResultSet simulateTour(Tour tour) {
		ResultSet resultSet = new ResultSet(tour);
		return resultSet;
	}
	
	
	//print function
	private static void printTour(Tour tour) {
		Iterator<TrashCan> canListIterator = tour.getCanList().iterator();
		TrashCan can = new TrashCan();
		EFillLevel levelEmpty = EFillLevel.EMPTY;
		EFillLevel levelOverFull = EFillLevel.OVERFULL;
		String fillLevel;
		
		while(canListIterator.hasNext())
		{
			can = canListIterator.next();
			if(can.getFillLevel() == levelEmpty)
				fillLevel = "EMPTY";
			else if(can.getFillLevel() == levelOverFull)
				fillLevel = "OVERFULL";
			else
				fillLevel = "FULL";
			
			if(can.isSensor())
				System.out.println("Tonne Nr. " + can.getTrashCanNumber() + " ----- " + " mit Sensor ----- " + fillLevel);
			else
				System.out.println("Tonne Nr. " + can.getTrashCanNumber() + " ----- " + " ohne Sensor ----- " + fillLevel);
		}
		
	}



	
	/*
	 * Functin for intelligent sensoring depending on input parameter
	 * 
	 * 
	private static void fillIntelligentFillLevelList(InputManager manager, List<TrashCan> canList, int countOfEmptyCans) {
		EFillLevel fullLevel = EFillLevel.FULL;
		EFillLevel emptyLevel = EFillLevel.EMPTY;
		int countOfSensorIntelligence, countOfRemainingEmptyCans, index;
		
		//Set all levels to FULL
		for(int i = 0; i < manager.getNumberOfCans(); i++)
		{
			canList.get(i).setFillLevel(fullLevel);
		}
		
		//Calculate the minium amount of empty cans with sensor depending on the sensorIntelligencePercentage
		countOfSensorIntelligence = calculateCounterOfEmptyCans(manager.getSensorIntelligencePercentage(), manager.);
		//Calculate the remaining empty cans
		countOfRemainingEmptyCans = countOfEmptyCans - countOfSensorIntelligence;
		
		//Set fillLevels of cans with sensor to empty
		while(countOfSensorIntelligence > 0)
		{
			index = 0;
			while(index < canList.size())
			{
				if(canList.get(index).isSensor() && canList.get(index).getFillLevel() == fullLevel) 
				{
					canList.get(index).setFillLevel(emptyLevel);
					countOfSensorIntelligence--;
				}
			}
		}
		
		//Set random fillLevels for rest with or without sensor
		while(countOfRemainingEmptyCans > 0)
		{
			//Get random Element
			index = ThreadLocalRandom.current().nextInt(manager.getNumberOfCans());
			//Check if Level is FULL
			if(canList.get(index).getFillLevel() == fullLevel)
			{
				//Set to empty
				canList.get(index).setFillLevel(emptyLevel);
				countOfRemainingEmptyCans--;
			}
		}
	}
	*/

}
