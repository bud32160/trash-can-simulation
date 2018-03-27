package entities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import enumerations.EDaySpecification;
import enumerations.EFillLevel;
import input.InputManager;
import output.ResultSet;

public class Tour {
	
	private String tourNumber;
	private EDaySpecification daySpecification;
	InputManager manager;
	private List<TrashCan> canList;
	private List<TrashCan> cansWithoutSensorList;
	private List<TrashCan> cansWithSensorList;
	private List<TrashCan> emptyCansWithSensorList;
	private List<TrashCan> fullCansList;
	private ResultSet resultSet;

	public Tour()
	{
		
	}
	
	public Tour(String tourNumber, InputManager manager)
	{
		this.tourNumber = tourNumber;
		this.manager = manager;
		this.canList = copyCanList();
		
		assignSensors();
		assignRandomFillLevels();
		createLists();
	}
	
	private List<TrashCan> copyCanList() {
		List<TrashCan> canList = new ArrayList<TrashCan>();
		
		for(TrashCan can : manager.getCanList()) {
			canList.add(can);
		}
		
		return canList;
	}

	// TODO Change function (InputManager canList should not be changed)
	
	// Assign sensors to cans in canList
	private void assignSensors() {
		int index;
		int count = manager.getNumberOfSensors();
		
		while(count > 0)
		{
			// Get random Element
			index = ThreadLocalRandom.current().nextInt(manager.getNumberOfCans());
			// Check if there is not already a sensor installed
			if(canList.get(index).isSensor() == false)
			{
				// Assign sensor
				canList.get(index).setSensor(true);
				count--;
			}
		}
	}
	
	
	// Assign random fillLevels
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
	
	// Calculate values function
	private static int calculateCounterOfCans(int size, int emptyFillLevelPercentage) {
		int result;
		double calculator;
		
		//Round result algorithm
		calculator = (double) size * ((double) emptyFillLevelPercentage / 100);
				
		result = (int) (Math.round(calculator));
		
		return result;
	}
	
	// FillLevel function
	private void fillNormalFillLevelList(int countOfEmptyCans, int countOfOverFullCans) {
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
		// Set random levels to overFull depending on countOfOverFullCans
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

	
	// Assign cans to different list depending on having a sensor and fillLevel
	private void createLists() {
		Iterator<TrashCan> iterator = canList.iterator();
		List<TrashCan> cansWithoutSensorList = new ArrayList<TrashCan>();
		List<TrashCan> cansWithSensorList = new ArrayList<TrashCan>();
		List<TrashCan> emptyCansWithSensorList = new ArrayList<TrashCan>();
		List<TrashCan> fullCansList = new ArrayList<TrashCan>();
		EFillLevel emptyLevel = EFillLevel.EMPTY;
		EFillLevel fullLevel = EFillLevel.FULL;
		EFillLevel overfullLevel = EFillLevel.OVERFULL;
		TrashCan can = new TrashCan();
		
		while (iterator.hasNext()) {
			can = iterator.next();
			if(can.getFillLevel().equals(fullLevel) || can.getFillLevel().equals(overfullLevel))
				fullCansList.add(can);
			if(can.isSensor() == false)
				cansWithoutSensorList.add(can);
			else
			{
				cansWithSensorList.add(can);
				if(can.getFillLevel() == emptyLevel)
					emptyCansWithSensorList.add(can);
			}
		}
		
		this.fullCansList = fullCansList;
		this.cansWithoutSensorList = cansWithoutSensorList;
		this.cansWithSensorList = cansWithSensorList;
		this.emptyCansWithSensorList = emptyCansWithSensorList;
	}
	
	// Getters and Setters
	public String getTourNumber() {
		return tourNumber;
	}

	public void setTourNumber(String tourNumber) {
		this.tourNumber = tourNumber;
	}

	public EDaySpecification getDaySpecification() {
		return daySpecification;
	}

	public void setDaySpecification(EDaySpecification daySpecification) {
		this.daySpecification = daySpecification;
	}

	public InputManager getManager() {
		return manager;
	}

	public void setManager(InputManager manager) {
		this.manager = manager;
	}

	public List<TrashCan> getCanList() {
		return canList;
	}

	public void setCanList(List<TrashCan> canList) {
		this.canList = canList;
	}

	public List<TrashCan> getCansWithoutSensorList() {
		return cansWithoutSensorList;
	}

	public void setCansWithoutSensorList(List<TrashCan> cansWithoutSensorList) {
		this.cansWithoutSensorList = cansWithoutSensorList;
	}

	public List<TrashCan> getCansWithSensorList() {
		return cansWithSensorList;
	}

	public void setCansWithSensorList(List<TrashCan> cansWithSensorList) {
		this.cansWithSensorList = cansWithSensorList;
	}

	public List<TrashCan> getEmptyCansWithSensorList() {
		return emptyCansWithSensorList;
	}

	public void setEmptyCansWithSensorList(List<TrashCan> emptyCansWithSensorList) {
		this.emptyCansWithSensorList = emptyCansWithSensorList;
	}

	public List<TrashCan> getFullCansList() {
		return fullCansList;
	}

	public void setFullCansList(List<TrashCan> fullCansList) {
		this.fullCansList = fullCansList;
	}

	public ResultSet getResultSet() {
		return resultSet;
	}

	public void setResultSet(ResultSet resultSet) {
		this.resultSet = resultSet;
	}
	
}
