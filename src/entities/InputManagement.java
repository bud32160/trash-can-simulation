package entities;

public class InputManagement {

	private int numberOfCans;
	private int numberOfSensors;
	private int emptyingTime;
	private int singleDrivingTime;
	private int fillLevelPercentage;
	
	public int getNumberOfCans() {
		return numberOfCans;
	}

	public void setNumberOfCans(int numberOfCans) {
		this.numberOfCans = numberOfCans;
	}

	public int getNumberOfSensors() {
		return numberOfSensors;
	}

	public void setNumberOfSensors(int numberOfSensors) {
		this.numberOfSensors = numberOfSensors;
	}

	public int getEmptyingTime() {
		return emptyingTime;
	}
	
	public void setEmptyingTime(int emptyingTime) {
		this.emptyingTime = emptyingTime;
	}
	
	public int getSingleDrivingTime() {
		return singleDrivingTime;
	}
	
	public void setSingleDrivingTime(int singleDrivingTime) {
		this.singleDrivingTime = singleDrivingTime;
	}
	
	public int getFillLevelPercentage() {
		return fillLevelPercentage;
	}
	
	public void setFillLevelPercentage(int fillLevelPercentage) {
		this.fillLevelPercentage = fillLevelPercentage;
	}
	
}
