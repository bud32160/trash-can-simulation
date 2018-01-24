package entities;

import java.util.List;

import enumerations.EDaySpecification;

public class Tour {
	
	private int tourNumber;
	private int weekNumber;
	private EDaySpecification daySpecification;
	private List<TrashCan> CanList;
	private List<TrashCan> CanWithoutSensorList;
	private List<TrashCan> CanWithSensorList;
	private List<TrashCan> EmptyIgnoredCansList;
	private double durationComplete;
	private double timeSaved;
	private double timeWasted;
	private double distanceComplete;
	private double distanceSaved;
	
	
	public int getTourNumber() {
		return tourNumber;
	}

	public void setTourNumber(int tourNumber) {
		this.tourNumber = tourNumber;
	}

	public int getWeekNumber() {
		return weekNumber;
	}

	public void setWeekNumber(int weekNumber) {
		this.weekNumber = weekNumber;
	}

	public EDaySpecification getDaySpecification() {
		return daySpecification;
	}

	public void setDaySpecification(EDaySpecification daySpecification) {
		this.daySpecification = daySpecification;
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
	
	public List<TrashCan> getEmptyIgnoredCansList() {
		return EmptyIgnoredCansList;
	}
	
	public void setEmptyIgnoredCansList(List<TrashCan> emptyIgnoredCansList) {
		EmptyIgnoredCansList = emptyIgnoredCansList;
	}
	
	public double getDurationComplete() {
		return durationComplete;
	}
	
	public void setDurationComplete(double durationComplete) {
		this.durationComplete = durationComplete;
	}
	
	public double getTimeSaved() {
		return timeSaved;
	}
	
	public void setTimeSaved(double timeSaved) {
		this.timeSaved = timeSaved;
	}
	
	public double getTimeWasted() {
		return timeWasted;
	}
	
	public void setTimeWasted(double timeWasted) {
		this.timeWasted = timeWasted;
	}

	public double getDistanceComplete() {
		return distanceComplete;
	}

	public void setDistanceComplete(double distanceComplete) {
		this.distanceComplete = distanceComplete;
	}

	public double getDistanceSaved() {
		return distanceSaved;
	}

	public void setDistanceSaved(double distanceSaved) {
		this.distanceSaved = distanceSaved;
	}

}
