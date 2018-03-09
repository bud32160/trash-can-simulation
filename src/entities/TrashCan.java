package entities;

import enumerations.EDaySpecification;
import enumerations.EFillLevel;
import enumerations.EPublicStatus;

public class TrashCan {
	
	private String canNumber;
	private String address;
	private EPublicStatus publicStatus;
	private EFillLevel fillLevel;
	private boolean sensor;
	private GpsData gpsData;
	private EDaySpecification daySpecification;
	
	public TrashCan()
	{
		
	};
	
	public TrashCan(String canNumber, boolean sensor) {
		this.canNumber = canNumber;
		this.sensor = sensor;
	
	}
	
	public TrashCan(String canNumber, boolean sensor, GpsData gpsData) {
		this.canNumber = canNumber;
		this.sensor = sensor;
		this.gpsData = gpsData;
	
	}

	public TrashCan(String canNumber, EFillLevel fillLevel, boolean sensor) {
		this.canNumber = canNumber;
		this.fillLevel = fillLevel;
		this.sensor = sensor;
	}

	public String getCanNumber() {
		return canNumber;
	}

	public void setCanNumber(String canNumber) {
		this.canNumber = canNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public EPublicStatus getPublicStatus() {
		return publicStatus;
	}

	public void setPublicStatus(EPublicStatus publicStatus) {
		this.publicStatus = publicStatus;
	}

	public EFillLevel getFillLevel() {
		return fillLevel;
	}
	
	public void setFillLevel(EFillLevel fillLevel) {
		this.fillLevel = fillLevel;
	}
	
	public boolean isSensor() {
		return sensor;
	}
	
	public void setSensor(boolean sensor) {
		this.sensor = sensor;
	}

	public GpsData getGpsData() {
		return gpsData;
	}

	public void setGpsData(GpsData gpsData) {
		this.gpsData = gpsData;
	}

	public EDaySpecification getDaySpecification() {
		return daySpecification;
	}

	public void setDaySpecification(EDaySpecification daySpecification) {
		this.daySpecification = daySpecification;
	}
	
}
