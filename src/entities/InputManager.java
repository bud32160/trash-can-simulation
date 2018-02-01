package entities;

import java.util.Scanner;

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
	
	
	public void readInput() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Anzahl Tonnen gesamt");
		setNumberOfCans(scanner.nextInt());
		System.out.println("Anzahl Tonnen mit Sensor");
		setNumberOfSensors(scanner.nextInt());
		System.out.println("Prozentualer Anteil leerer Tonnen in Prozent");
		setEmptyFillLevelPercentage(scanner.nextInt());
		System.out.println("Prozentualer Anteil übervoller Tonnen in Prozent");
		setOverFullFillLevelPercentage(scanner.nextInt());
		System.out.println("Leerzeit pro Tonne in Sekunden");
		setEmptyingTime(scanner.nextDouble());
		System.out.println("Anfahrtszeit pro Tonne in Sekunden");
		setSingleDrivingTime(scanner.nextDouble());
		System.out.println("Anfahrtsweg pro Tonne in Meter");
		setSingleDrivingDistance(scanner.nextDouble());
		scanner.close();
	}
	
	
	//Getters and Setters
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
