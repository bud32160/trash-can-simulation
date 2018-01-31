package entities;

public class InputManager {

	private int numberOfCans;
	private int numberOfSensors;
	private double emptyingTime;
	private double singleDrivingTime;
	private double singleDrivingDistance;
	private int emptyFillLevelPercentage;
	private int overFullFillLevelPercentage;
	private int sensorIntelligencePercentage;
	private boolean sensorIntelligence;
	
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

	public double getEmptyingTime() {
		return emptyingTime;
	}
	
	public void setEmptyingTime(double emptyingTime) {
		this.emptyingTime = emptyingTime;
	}
	
	public double getSingleDrivingTime() {
		return singleDrivingTime;
	}
	
	public void setSingleDrivingTime(double singleDrivingTime) {
		this.singleDrivingTime = singleDrivingTime;
	}
	
	public double getSingleDrivingDistance() {
		return singleDrivingDistance;
	}

	public void setSingleDrivingDistance(double singleDrivingDistance) {
		this.singleDrivingDistance = singleDrivingDistance;
	}
	
	public int getEmptyFillLevelPercentage() {
		return emptyFillLevelPercentage;
	}
	
	public void setEmptyFillLevelPercentage(int emptyFillLevelPercentage) {
		this.emptyFillLevelPercentage = emptyFillLevelPercentage;
	}
	
	public int getOverFullFillLevelPercentage() {
		return overFullFillLevelPercentage;
	}

	public void setOverFullFillLevelPercentage(int overFullFillLevelPercentage) {
		this.overFullFillLevelPercentage = overFullFillLevelPercentage;
	}

	public int getSensorIntelligencePercentage() {
		return sensorIntelligencePercentage;
	}

	public void setSensorIntelligencePercentage(int sensorIntelligencePercentage) {
		this.sensorIntelligencePercentage = sensorIntelligencePercentage;
	}

	public boolean isSensorIntelligence() {
		return sensorIntelligence;
	}

	public void setSensorIntelligence(boolean sensorIntelligence) {
		this.sensorIntelligence = sensorIntelligence;
	}
	
}
