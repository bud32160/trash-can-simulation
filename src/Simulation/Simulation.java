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
		resultSet = createResultSet(tour);
		
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
	
	private static void fillNormalFillLevelList(InputManager manager, List<TrashCan> canList, int countOfEmptyCans, int countOfOverFullCans) {
		EFillLevel overFullLevel = EFillLevel.OVERFULL;
		EFillLevel fullLevel = EFillLevel.FULL;
		EFillLevel emptyLevel = EFillLevel.EMPTY;
		int helpCounter, index;
		
		//Set all levels to FULL
		for(int i = 0; i < manager.getNumberOfCans(); i++)
		{
			canList.get(i).setFillLevel(fullLevel);
		}
		
		helpCounter = countOfEmptyCans;
		//Set random levels to empty depending on countOfEmptyCans
		while(helpCounter > 0)
		{
			//Get random Element
			index = ThreadLocalRandom.current().nextInt(manager.getNumberOfCans());
			//Check if Level is FULL
			if(canList.get(index).getFillLevel() == fullLevel)
			{
				//Set to empty
				canList.get(index).setFillLevel(emptyLevel);
				helpCounter--;
			}
		}
		
		helpCounter = countOfOverFullCans;
		//Set random levels to overFull depending on countOfOverFullCans
		while(helpCounter > 0)
		{
			//Get random Element
			index = ThreadLocalRandom.current().nextInt(manager.getNumberOfCans());
			//Check if Level is FULL
			if(canList.get(index).getFillLevel() == fullLevel)
			{
				//Set to overFull
				canList.get(index).setFillLevel(overFullLevel);
				helpCounter--;
			}
		}
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

	//Assign random fillLevels depending on input parameter
	private static void assignRandomFillLevels(List<TrashCan> canList, InputManager manager) {
		int countOfEmptyCans, countOfOverFullCans;
		
		countOfEmptyCans = calculateCounterOfCans(manager.getNumberOfCans(), manager.getEmptyFillLevelPercentage());
		countOfOverFullCans = calculateCounterOfCans(manager.getNumberOfCans(), manager.getOverFullFillLevelPercentage());
		
		/*
		if(manager.isSensorIntelligence())
		{
			fillIntelligentFillLevelList(manager, canList, countOfEmptyCans);
		}
		else
		{
			fillNormalFillLevelList(manager, canList, countOfEmptyCans);
		}
		*/
		
		fillNormalFillLevelList(manager, canList, countOfEmptyCans, countOfOverFullCans);
	}

	private static int calculateCounterOfCans(int size, int emptyFillLevelPercentage) {
		int result;
		double calculator;
		
		//Round result algorithm
		calculator = (double) size * ((double) emptyFillLevelPercentage / 100);
				
		result = (int) (Math.round(calculator));
		
		return result;
	}


	//Fill List with amount of trash cans
	private static List<TrashCan> createTrashCanList(int size) {
		int i = 1;
		List<TrashCan> trashCanList = new ArrayList<TrashCan>();
		
		while(i <= size)
		{
			TrashCan can = new TrashCan(i, false);
			trashCanList.add(can);
			i++;
		};
		
		return trashCanList;
	}
	
	//Assign sensors to cans in trashCanList
	private static void assignSensors(List<TrashCan> canList, int count) {
		int index;
		while(count > 0)
		{
			//Get random Element
			index = ThreadLocalRandom.current().nextInt(manager.getNumberOfCans());
			//Check if Level is FULL
			if(canList.get(index).isSensor() == false)
			{
				//Set to overFull
				canList.get(index).setSensor(true);
				count--;
			}
		}
	}
	
	//Asign cans without sensor to list
	private static List<TrashCan> createListOfCansWithoutSensor(List<TrashCan> canList) {
		List<TrashCan> listOfCansWithoutSensor = new ArrayList<TrashCan>();
		Iterator<TrashCan> iterator = canList.iterator();
		TrashCan can = new TrashCan();
		
		while (iterator.hasNext()) {
			can = iterator.next();
			if(can.isSensor() == false)
				listOfCansWithoutSensor.add(can);
		}

		return listOfCansWithoutSensor;
	}
	
	//Asign cans with sensor to list
	private static List<TrashCan> createListOfCansWithSensor(List<TrashCan> canList) {
		List<TrashCan> listOfCansWithSensor = new ArrayList<TrashCan>();
		Iterator<TrashCan> iterator = canList.iterator();
		TrashCan can = new TrashCan();
		
		while (iterator.hasNext()) {
			can = iterator.next();
			if(can.isSensor())
				listOfCansWithSensor.add(can);
		}

		return listOfCansWithSensor;
	}
	
	private static List<TrashCan> createListOfEmptyCansWithSensor(List<TrashCan> canList) {
		List<TrashCan> listOfEmptyCansWithSensor = new ArrayList<TrashCan>();
		Iterator<TrashCan> iterator = canList.iterator();
		TrashCan can = new TrashCan();
		EFillLevel emptyLevel = EFillLevel.EMPTY;
		
		while (iterator.hasNext()) {
			can = iterator.next();
			if(can.isSensor())
			{
				if(can.getFillLevel() == emptyLevel)
					listOfEmptyCansWithSensor.add(can);
			}
		}

		return listOfEmptyCansWithSensor;
	}
	
	private static void fillTourLists(Tour tour) {
		tour.setCanWithoutSensorList(createListOfCansWithoutSensor(tour.getCanList()));
		tour.setCanWithSensorList(createListOfCansWithSensor(tour.getCanList()));
		tour.setEmptyIgnoredCansList(createListOfEmptyCansWithSensor(tour.getCanList()));
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
	
	private static double calculateDurationComplete(Tour tour, InputManager manager)
	{
		double durationComplete = 0;
		double durationSaved = 0;
		
		//Calculate time for all cans
		durationComplete = tour.getCanList().size() * (manager.getEmptyingTime() + manager.getSingleDrivingTime());
		
		//Calculate time for empty cans with sensor
		durationSaved = tour.getEmptyIgnoredCansList().size() * (manager.getEmptyingTime() + manager.getSingleDrivingTime());
		
		return (durationComplete - durationSaved);
	}
	
	private static double calculateDistanceComplete(Tour tour, InputManager manager)
	{
		double distanceComplete = 0;
		double distanceSaved = 0;
		
		//Calculate time for all cans
		distanceComplete = (tour.getCanList().size() * manager.getSingleDrivingDistance());
		
		
		//Calculate time for empty cans with sensor
		distanceSaved = tour.getEmptyIgnoredCansList().size() * manager.getSingleDrivingDistance();
		
		return (distanceComplete - distanceSaved);
	}

	private static void simulateTour(Tour tour, InputManager manager) {
		
		tour.setDurationComplete(calculateDurationComplete(tour, manager));
		tour.setDistanceComplete(calculateDistanceComplete(tour, manager));
		
	}

	private static ResultSet createResultSet(Tour tour) {
		ResultSet resultSet = new ResultSet();
		
		return resultSet;
	}


}
