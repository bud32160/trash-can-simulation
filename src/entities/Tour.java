package entities;

import java.util.List;

public class Tour {
	
	private int tourNumber;
	//private int weekNumber;
	//private EDaySpecification daySpecification;
	InputManager manager;
	private List<TrashCan> CanList;
	private List<TrashCan> CanWithoutSensorList;
	private List<TrashCan> CanWithSensorList;
	private List<TrashCan> EmptyIgnoredCansList;

	public Tour()
	{
		
	}
	
	public Tour(int tourNumber, List<TrashCan> canList) {
		this.tourNumber = tourNumber;
		CanList = canList;
	}
	
	

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
	
	public List<TrashCan> getEmptyIgnoredCansList() {
		return EmptyIgnoredCansList;
	}
	
	public void setEmptyIgnoredCansList(List<TrashCan> emptyIgnoredCansList) {
		EmptyIgnoredCansList = emptyIgnoredCansList;
	}

}
