package Simulation;

import java.util.Iterator;
import java.util.Scanner;

import entities.InputManager;
import entities.Tour;
import entities.TrashCan;
import enumerations.EFillLevel;

public class Simulation {

	static Scanner scanner = new Scanner(System.in);
	static InputManager manager = new InputManager();
	static Tour tour = new Tour();
	static ResultSet resultSet = new ResultSet();
	
	public static void main(String[] args) {
		
		//Input Data
		manager = readInput();
		//Create Tour
		tour = createTour(manager);
		//Simulate tour
		resultSet = simulateTour(tour);
		
		printTour(tour);
		printResultSet(resultSet);
		
		
		System.out.println("Fertig");
	}
	
	
	private static InputManager readInput() {
		InputManager manager = new InputManager();
		System.out.println("Anzahl Tonnen gesamt");
		manager.setNumberOfCans(scanner.nextInt());
		System.out.println("Anzahl Tonnen mit Sensor");
		manager.setNumberOfSensors(scanner.nextInt());
		System.out.println("Prozentualer Anteil leerer Tonnen in Prozent");
		manager.setEmptyFillLevelPercentage(scanner.nextInt());
		System.out.println("Prozentualer Anteil übervoller Tonnen in Prozent");
		manager.setOverFullFillLevelPercentage(scanner.nextInt());
		System.out.println("Leerzeit pro Tonne in Sekunden");
		manager.setEmptyingTime(scanner.nextDouble());
		System.out.println("Anfahrtszeit pro Tonne in Sekunden");
		manager.setSingleDrivingTime(scanner.nextDouble());
		System.out.println("Anfahrtsweg pro Tonne in Meter");
		manager.setSingleDrivingDistance(scanner.nextDouble());
		
		return manager;
	}
	
	
	//print functions
	private static void printTour(Tour tour) {
		// TODO Auto-generated method stub
		
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
	
	private static void printResultSet(ResultSet resultSet) {
		System.out.println("-----------------Result Set-----------------------\n");
		System.out.println("Anzahl Tonnen gesamt: " + resultSet.getTour().getManager().getNumberOfCans() + "  --  Anzahl Sensoren: " + resultSet.getTour().getManager().getNumberOfSensors());
		System.out.println("\n--------------------------------------------------\n");
		System.out.println("Geleerte Tonnen gesamt: " + resultSet.getAmountOfClearenceComplete() + " Stück");
		System.out.println("Zeit gesamt: " + resultSet.getDurationComplete() + " Sekunden");
		System.out.println("Strecke gesamt: " + resultSet.getDistanceComplete() + " Meter");
		System.out.println("\n--------------------------------------------------\n");
		System.out.println("Eingesparte Tonnen: " + resultSet.getAmountOfClearenceSaved() + " Stück");
		System.out.println("Eingesparte Zeit: " + resultSet.getTimeSaved() + " Sekunden");
		System.out.println("Eingesparte Strecke: " + resultSet.getDistanceSaved() + " Meter");
		System.out.println("\n--------------------------------------------------\n");
		System.out.println("Unnötig geleerte Tonnen: " + resultSet.getUnnecessaryCleared() + " Stück");
		System.out.println("Verschwendete Zeit: " + resultSet.getTimeWasted() + " Sekunden");
		System.out.println("Unnötig gefahrene Strecke: " + resultSet.getUnnecessaryDistance() + " Meter");
		
	}

	
	
	
	private static Tour createTour(InputManager manager) {
		Tour tour = new Tour(1, manager);
		return tour;
	}
	


	private static ResultSet simulateTour(Tour tour) {
		ResultSet resultSet = new ResultSet(tour);
		return resultSet;
	}

	
	
	
	
	
	/*
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
