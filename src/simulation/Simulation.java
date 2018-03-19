package simulation;

import java.io.FileNotFoundException;
import java.io.IOException;

import input.InputManager;
import output.ResultSet;
import output.SimulationResult;
import entities.Tour;

public class Simulation {
	
	static InputManager manager = new InputManager();
	static ResultSet resultSet = new ResultSet();
	static SimulationResult simulationResult = new SimulationResult();

	
	public static void main(String[] args) throws FileNotFoundException {
		
		/*
		 * 
		 * 
		 *	Missing: 
		 * - GUI for use cases
		 * 
		 */
		
		try {
			manager.readDataInput();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// TODO switch InputManager outside the for-loop, one generation is enough
		for(int i = 1; i <= manager.getSimulationIterations(); i++) {
			
			try {
				manager.readDataInput();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			resultSet = simulateTour(createTour(manager, i));
			simulationResult.getResultSetList().add(resultSet);
		}
		
		simulationResult.createSimulationResult();
	}
	
	
	private static Tour createTour(InputManager manager, int tourNumber) {
		Tour tour = new Tour(Integer.toString(tourNumber), manager);
		return tour;
	}

	private static ResultSet simulateTour(Tour tour) throws FileNotFoundException {
		ResultSet resultSet = new ResultSet(tour);
		return resultSet;
	}
	
	
}



	
	/*
	 * Function for intelligent measuring depending on input parameter
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
		
		//Calculate the minimum amount of empty cans with sensor depending on the sensorIntelligencePercentage
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

