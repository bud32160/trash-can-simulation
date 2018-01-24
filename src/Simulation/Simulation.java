package Simulation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import entities.InputManagement;
import entities.Tour;
import entities.TrashCan;

public class Simulation {

	static Scanner scanner = new Scanner(System.in);
	static InputManagement manager = new InputManagement();
	static Tour tour = new Tour();
	
	public static void main(String[] args) {
		
		//Input Data
		readInput();
		
		//TOO-DOO:
		//Assign random values to fillLevel
		
		
		//Create list of all cans
		tour.setCanList(createTrashCanList(manager.getNumberOfCans()));
		
		//Assign sensor to cans
		assignSensors(manager.getNumberOfSensors(), tour.getCanList());
		
		//Assign CanList to tour
		tour.setCanList(tour.getCanList());
		
		//Create different lists depending on sensor for tour
		tour.setCanWithoutSensorList(createListofCansWithoutSensor(tour.getCanList()));
		tour.setCanWithSensorList(createListofCansWithSensor(tour.getCanList()));
		
		System.out.println("Fertig");
		

	}


	private static void readInput() {
		System.out.println("Anzahl Tonnen gesamt:");
		manager.setNumberOfCans(scanner.nextInt());
		System.out.println("Anzahl Tonnen mit Sensor");
		manager.setNumberOfSensors(scanner.nextInt());
		/*
		System.out.println("Leerzeit pro Tonne");
		manager.setEmptyingTime(scanner.nextInt());
		System.out.println("Anfahrtszeit pro Tonne");
		manager.setSingleDrivingTime(scanner.nextInt());	
		System.out.println("Anzahl leerer Tonnen in Prozent");
		manager.setFillLevelPercentage(scanner.nextInt());
		*/
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
	private static void assignSensors(int count, List<TrashCan> trashCanList) {
		int j = 0;
		
		while(j < count)
		{
			trashCanList.get(j).setSensor(true);
			j++;
		}
	}
	
	//Asign cans without sensor to list
	private static List<TrashCan> createListofCansWithoutSensor(List<TrashCan> trashCanList) {
		List<TrashCan> listOfCansWithoutSensor = new ArrayList<TrashCan>();
		Iterator<TrashCan> iterator = trashCanList.iterator();
		TrashCan can = new TrashCan();
		
		while (iterator.hasNext()) {
			can = iterator.next();
			if(can.isSensor() == false)
				listOfCansWithoutSensor.add(can);
		}

		return listOfCansWithoutSensor;
	}
	
	//Asign cans with sensor to list
	private static List<TrashCan> createListofCansWithSensor(List<TrashCan> trashCanList) {
		List<TrashCan> listOfCansWithSensor = new ArrayList<TrashCan>();
		Iterator<TrashCan> iterator = trashCanList.iterator();
		TrashCan can = new TrashCan();
		
		while (iterator.hasNext()) {
			can = iterator.next();
			if(can.isSensor())
				listOfCansWithSensor.add(can);
		}

		return listOfCansWithSensor;
	}

}
