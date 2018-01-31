package Simulation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import entities.InputManager;
import entities.Tour;
import entities.TrashCan;
import enumerations.EFillLevel;

public class Simulation {

	static Scanner scanner = new Scanner(System.in);
	static InputManager manager = new InputManager();
	static List<Tour> tourList = new ArrayList<Tour>();
	static Tour tour = new Tour();
	static ResultSet resultSet = new ResultSet();
	
	public static void main(String[] args) {
		
		//Input Data
		manager = readInput();
		
		tour = createTour(manager);
		simulateTour(tour, manager);
		
		//simulation(tourList);
		
		printTour(tour);
		
		System.out.println("Fertig");
		

	}


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


	private static InputManager readInput() {
		InputManager manager = new InputManager();
		System.out.println("Anzahl Tonnen gesamt:");
		manager.setNumberOfCans(scanner.nextInt());
		System.out.println("Anzahl Tonnen mit Sensor");
		manager.setNumberOfSensors(scanner.nextInt());
		System.out.println("Prozentualer Anteil leerer Tonnen in Prozent");
		manager.setEmptyFillLevelPercentage(scanner.nextInt());
		System.out.println("Prozentualer Anteil übervoller Tonnen in Prozent");
		manager.setOverFullFillLevelPercentage(scanner.nextInt());
		/*
		System.out.println("Leerzeit pro Tonne");
		manager.setEmptyingTime(scanner.nextInt());
		System.out.println("Anfahrtszeit pro Tonne");
		manager.setSingleDrivingTime(scanner.nextInt());
		*/
		/*
		if(manager.getSensorIntelligencePercentage() > 0)
			manager.setSensorIntelligence(true);
		/*
		 * 	System.out.println("Prozentualer Anteil leerer Tonnen mit Sensor (intelligent) in Prozent");
			manager.setSensorIntelligencePercentage(scanner.nextInt());
			System.out.println("Leerzeit pro Tonne");
			manager.setEmptyingTime(scanner.nextInt());
			System.out.println("Anfahrtszeit pro Tonne");
			manager.setSingleDrivingTime(scanner.nextInt());
		 */

		
		return manager;
	}
	

	





	private static Tour createTour(InputManager manager) {
		Tour tour = new Tour();
		
		//Create list of all cans
		tour.setCanList(createTrashCanList(manager.getNumberOfCans()));
		//Assign sensor to cans
		assignSensors(tour.getCanList(), manager.getNumberOfSensors());
		
		//Assig random fillLevels
		assignRandomFillLevels(tour.getCanList(), manager);
				
		//Create different lists depending on sensor for tour
		fillTourLists(tour);
		
		return tour;
	}
	


	private static void simulateTour(Tour tour, InputManager manager) {
		
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
