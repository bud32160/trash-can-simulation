package entities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import Simulation.ResultSet;
import enumerations.EFillLevel;

public class Tour {
	
	private int tourNumber;
	//private int weekNumber;
	//private EDaySpecification daySpecification;
	InputManager manager;
	private List<TrashCan> CanList;
	private List<TrashCan> CanWithoutSensorList;
	private List<TrashCan> CanWithSensorList;
	private List<TrashCan> EmptyCansWithSensorList;
	private ResultSet resultSet;

	public Tour()
	{
		
	}
	
	public Tour(InputManager manager)
	{
		this.manager = manager;
		createCanList();
		assignSensors();
		assignRandomFillLevels();
		createLists();
	}
	
	public Tour(int tourNumber, List<TrashCan> canList) 
	{
		this.tourNumber = tourNumber;
		CanList = canList;
	}
	
	
	//Create list of all cans
	private void createCanList() {
		int i = 1;
		List<TrashCan> trashCanList = new ArrayList<TrashCan>();
		
		while(i <= manager.getNumberOfCans())
		{
			TrashCan can = new TrashCan(i, false);
			trashCanList.add(can);
			i++;
		};
		
		this.CanList = trashCanList;
	}
	
	
	//Assign sensors to cans in canList
	private void assignSensors() {
		int index;
		int count = manager.getNumberOfSensors();
		
		while(count > 0)
		{
			//Get random Element
			index = ThreadLocalRandom.current().nextInt(manager.getNumberOfCans());
			//Check if Level is FULL
			if(getCanList().get(index).isSensor() == false)
			{
				//Set to overFull
				getCanList().get(index).setSensor(true);
				count--;
			}
		}
	}
	
	
	//Assgin random fillLevels
	private void assignRandomFillLevels() {
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
		
		fillNormalFillLevelList(countOfEmptyCans, countOfOverFullCans);
	}
	
	//Calculate values function
	private static int calculateCounterOfCans(int size, int emptyFillLevelPercentage) {
		int result;
		double calculator;
		
		//Round result algorithm
		calculator = (double) size * ((double) emptyFillLevelPercentage / 100);
				
		result = (int) (Math.round(calculator));
		
		return result;
	}
	
	//FillLevel function
	private void fillNormalFillLevelList(int countOfEmptyCans, int countOfOverFullCans) {
		EFillLevel overFullLevel = EFillLevel.OVERFULL;
		EFillLevel fullLevel = EFillLevel.FULL;
		EFillLevel emptyLevel = EFillLevel.EMPTY;
		int helpCounter, index;
		
		//Set all levels to FULL
		for(int i = 0; i < manager.getNumberOfCans(); i++)
		{
			getCanList().get(i).setFillLevel(fullLevel);
		}
		
		helpCounter = countOfEmptyCans;
		//Set random levels to empty depending on countOfEmptyCans
		while(helpCounter > 0)
		{
			//Get random Element
			index = ThreadLocalRandom.current().nextInt(manager.getNumberOfCans());
			//Check if Level is FULL
			if(getCanList().get(index).getFillLevel() == fullLevel)
			{
				//Set to empty
				getCanList().get(index).setFillLevel(emptyLevel);
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
			if(getCanList().get(index).getFillLevel() == fullLevel)
			{
				//Set to overFull
				getCanList().get(index).setFillLevel(overFullLevel);
				helpCounter--;
			}
		}
	}

	
	//Asign cans to different list depending on having a sensor and fillLevel
	private void createLists() {
		List<TrashCan> listOfCansWithSensor = new ArrayList<TrashCan>();
		List<TrashCan> listOfCansWithoutSensor = new ArrayList<TrashCan>();
		List<TrashCan> listOfEmptyCansWithSensor = new ArrayList<TrashCan>();
		Iterator<TrashCan> iterator = getCanList().iterator();
		EFillLevel emptyLevel = EFillLevel.EMPTY;
		TrashCan can = new TrashCan();
		
		while (iterator.hasNext()) {
			can = iterator.next();
			if(can.isSensor() == false)
				listOfCansWithoutSensor.add(can);
			else
			{
				listOfCansWithSensor.add(can);
				if(can.getFillLevel() == emptyLevel)
					listOfEmptyCansWithSensor.add(can);
			}
		}

		this.CanWithSensorList = listOfCansWithSensor;
		this.CanWithoutSensorList = listOfCansWithoutSensor;
		this.EmptyCansWithSensorList = listOfEmptyCansWithSensor;
	}
	
	
	//Getters and Setters
	public int getTourNumber() {
		return tourNumber;
	}

	public void setTourNumber(int tourNumber) {
		this.tourNumber = tourNumber;
	}

	public InputManager getManager() {
		return manager;
	}

	public void setManager(InputManager manager) {
		this.manager = manager;
	}

	public List<TrashCan> getCanList() {
		return CanList;
	}

	public void setCanList(List<TrashCan> canList) {
		CanList = canList;
	}

	public List<TrashCan> getCanWithoutSensorList() {
		return CanWithoutSensorList;
	}

	public void setCanWithoutSensorList(List<TrashCan> canWithoutSensorList) {
		CanWithoutSensorList = canWithoutSensorList;
	}

	public List<TrashCan> getCanWithSensorList() {
		return CanWithSensorList;
	}

	public void setCanWithSensorList(List<TrashCan> canWithSensorList) {
		CanWithSensorList = canWithSensorList;
	}

	public List<TrashCan> getEmptyCansWithSensorList() {
		return EmptyCansWithSensorList;
	}

	public void setEmptyCansWithSensorList(List<TrashCan> emptyCansWithSensorList) {
		EmptyCansWithSensorList = emptyCansWithSensorList;
	}

	public ResultSet getResultSet() {
		return resultSet;
	}

	public void setResultSet(ResultSet resultSet) {
		this.resultSet = resultSet;
	}
	
}
