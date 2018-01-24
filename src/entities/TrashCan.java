package entities;

import enumerations.EDaySpecification;
import enumerations.EFillLevel;

public class TrashCan {
	
	private int trashCanNumber;
	private EDaySpecification daySpezification;
	private EFillLevel fillLevel;
	private boolean sensor;
	
	
	public int getTrashCanNumber() {
		return trashCanNumber;
	}

	public void setTrashCanNumber(int trashCanNumber) {
		this.trashCanNumber = trashCanNumber;
	}

	public EDaySpecification getDaySpezification() {
		return daySpezification;
	}

	public void setDaySpezification(EDaySpecification daySpezification) {
		this.daySpezification = daySpezification;
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
	
}
