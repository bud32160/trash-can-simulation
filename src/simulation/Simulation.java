package simulation;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import input.InputManager;
import output.ResultSet;
import entities.Tour;
import entities.TrashCan;
import enumerations.EFillLevel;

public class Simulation {

	static InputManager manager = new InputManager();
	static Tour tour = new Tour();
	static ResultSet resultSet = new ResultSet();
	
	public static void main(String[] args) throws FileNotFoundException {
		
		/*
		 * 
		 * 
		 *	Missing: 
		 * - exception handling
		 * - gui for usecases
		 * 
		 */
		
		//Input Data
		try {
			manager.readDataInput();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Create Tour
		tour = createTour(manager);
		//Simulate tour
		resultSet = simulateTour(tour);
		//Print results
		
	}
	
	
	private static Tour createTour(InputManager manager) {
		Tour tour = new Tour(Integer.toString(1), manager);
		return tour;
	}

	private static ResultSet simulateTour(Tour tour) throws FileNotFoundException {
		ResultSet resultSet = new ResultSet(tour);
		return resultSet;
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

