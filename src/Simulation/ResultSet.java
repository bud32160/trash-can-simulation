package Simulation;

import java.util.Iterator;

import entities.Tour;
import entities.TrashCan;
import enumerations.EFillLevel;

public class ResultSet {
	
	private Tour tour;
	private double durationComplete;
	private double distanceComplete;
	private double timeSaved;
	private double distanceSaved;
	private int amountOfClearenceSaved;
	private double timeWasted;
	private double unnecessaryDistance;
	private int unnecessaryCleared;
	
	public ResultSet()
	{
		
	}
	
	public ResultSet(Tour tour)
	{
		this.tour = tour;
		createResultSet();
	}
	
	private void createResultSet() {
		calculateDurationComplete();
		calculateDistanceComplete();
		calculateTimeSaved();
		calculateDistanceSaved();
		calculateAmountOfClearenceSaved();
		calculateUnnecessaryAmounts();
	}


	private void calculateDurationComplete()
	{
		double durationComplete = 0;
		double durationSaved = 0;
		
		//Calculate time for all cans
		durationComplete = tour.getCanList().size() * (tour.getManager().getEmptyingTime() + tour.getManager().getSingleDrivingTime());
		
		//Calculate time for empty cans with sensor
		durationSaved = tour.getEmptyCansWithSensorList().size() * (tour.getManager().getEmptyingTime() + tour.getManager().getSingleDrivingTime());
		
		this.durationComplete = (durationComplete - durationSaved);
	}
	
	private void calculateDistanceComplete()
	{
		double distanceComplete = 0;
		double distanceSaved = 0;
		
		//Calculate distance for all cans
		distanceComplete = (tour.getCanList().size() * tour.getManager().getSingleDrivingDistance());
		
		
		//Calculate distance for empty cans with sensor
		distanceSaved = tour.getEmptyCansWithSensorList().size() * tour.getManager().getSingleDrivingDistance();
		
		this.distanceComplete = (distanceComplete - distanceSaved);
	}
	
	public void calculateTimeSaved()
	{
		double timeSaved = 0;
		
		timeSaved = tour.getEmptyCansWithSensorList().size() * (tour.getManager().getEmptyingTime() + tour.getManager().getSingleDrivingTime());
		
		this.timeSaved = timeSaved;
	}

	public void calculateDistanceSaved()
	{
		double distanceSaved = 0;
		
		distanceSaved = tour.getEmptyCansWithSensorList().size() * tour.getManager().getSingleDrivingDistance();
		
		this.distanceSaved = distanceSaved;
	}
	
	
	private void calculateAmountOfClearenceSaved() {
		this.amountOfClearenceSaved = tour.getEmptyCansWithSensorList().size();
	}

	private void calculateUnnecessaryAmounts() {
		Iterator<TrashCan> iterator = tour.getCanWithoutSensorList().iterator();
		EFillLevel emptyLevel = EFillLevel.EMPTY;
		int unnecessaryCleared = 0;
		double timeWasted = 0;
		double unnecessaryDistance = 0;
		
		while(iterator.hasNext())
		{
			if(iterator.next().getFillLevel() == emptyLevel)
				unnecessaryCleared++;
		}
		
		timeWasted = unnecessaryCleared * (tour.getManager().getEmptyingTime() + tour.getManager().getSingleDrivingTime());
		unnecessaryDistance = unnecessaryCleared * tour.getManager().getSingleDrivingDistance();
		
		this.unnecessaryCleared = unnecessaryCleared;
		this.timeWasted = timeWasted;
		this.unnecessaryDistance = unnecessaryDistance;
	}
	
	
	//Getters and Setters
	public Tour getTour() {
		return tour;
	}
	public void setTour(Tour tour) {
		this.tour = tour;
	}
	
	public double getDurationComplete() {
		return durationComplete;
	}
	
	public void setDurationComplete(double durationComplete) {
		this.durationComplete = durationComplete;
	}
	
	public double getDistanceComplete() {
		return distanceComplete;
	}

	public void setDistanceComplete(double distanceComplete) {
		this.distanceComplete = distanceComplete;
	}
	
	public double getTimeSaved() {
		return timeSaved;
	}
	
	public void setTimeSaved(double timeSaved) {
		this.timeSaved = timeSaved;
	}

	public double getDistanceSaved() {
		return distanceSaved;
	}

	public void setDistanceSaved(double distanceSaved) {
		this.distanceSaved = distanceSaved;
	}

	public int getAmountOfClearenceSaved() {
		return amountOfClearenceSaved;
	}

	public void setAmountOfClearenceSaved(int amountOfClearenceSaved) {
		this.amountOfClearenceSaved = amountOfClearenceSaved;
	}
	
	public double getTimeWasted() {
		return timeWasted;
	}
	
	public void setTimeWasted(double timeWasted) {
		this.timeWasted = timeWasted;
	}

	public double getUnnecessaryDistance() {
		return unnecessaryDistance;
	}

	public void setUnnecessaryDistance(double unnecessaryDistance) {
		this.unnecessaryDistance = unnecessaryDistance;
	}

	public int getUnnecessaryCleared() {
		return unnecessaryCleared;
	}

	public void setUnnecessaryCleared(int unnecessaryCleared) {
		this.unnecessaryCleared = unnecessaryCleared;
	}
	
}
