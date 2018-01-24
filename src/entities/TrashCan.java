package entities;

import enumerations.EFillLevel;

public class TrashCan {
	
	private int trashCanNumber;
	private EFillLevel fillLevel;
	private boolean sensor;
	
	public TrashCan()
	{
		
	};
	
	public TrashCan(int trashCanNumber, boolean sensor) {
		this.trashCanNumber = trashCanNumber;
		this.sensor = sensor;
	
	}

	public TrashCan(int trashCanNumber, EFillLevel fillLevel, boolean sensor) {
		this.trashCanNumber = trashCanNumber;
		this.fillLevel = fillLevel;
		this.sensor = sensor;
	}

	public int getTrashCanNumber() {
		return trashCanNumber;
	}

	public void setTrashCanNumber(int trashCanNumber) {
		this.trashCanNumber = trashCanNumber;
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
