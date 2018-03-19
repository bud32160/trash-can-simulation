package input;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import entities.GpsData;
import entities.TrashCan;
import enumerations.EDaySpecification;
import enumerations.EPublicStatus;

public class InputManager {

	private int numberOfCans;
	private int numberOfSensors;
	private double emptyingTime;
	private double singleDrivingTime;
	private double singleDrivingDistance;
	private int emptyFillLevelPercentage;
	private int overFullFillLevelPercentage;
	private List<TrashCan> canList;
	private int simulationIterations;
	
	
	public void readDataInput() throws IOException{
		String location = System.getProperty("user.dir") + "\\input\\input_data\\InputData.xlsx";
		InputStream inputStream = new FileInputStream(location);
        XSSFWorkbook wb = new XSSFWorkbook(inputStream);
        
        XSSFSheet sheet = wb.getSheetAt(0);
        Row row = sheet.getRow(2);
        
        Cell cell = row.getCell(1);
		setNumberOfSensors((int) cell.getNumericCellValue());
		
		row = sheet.getRow(3);
		cell = row.getCell(1);
		setEmptyFillLevelPercentage((int) cell.getNumericCellValue());

		row = sheet.getRow(4);
		cell = row.getCell(1);
		setOverFullFillLevelPercentage((int) cell.getNumericCellValue());
		
		row = sheet.getRow(5);
		cell = row.getCell(1);
		setEmptyingTime((int) cell.getNumericCellValue());

		row = sheet.getRow(6);
		cell = row.getCell(1);
		setSingleDrivingTime((int) cell.getNumericCellValue());
		
		row = sheet.getRow(7);
		cell = row.getCell(1);
		setSingleDrivingDistance((int) cell.getNumericCellValue());
		
		row = sheet.getRow(8);
		cell = row.getCell(1);
		setSimulationIterations((int) cell.getNumericCellValue());
		
		setCanList(readTourSheetInput());
		setNumberOfCans(canList.size());
		wb.close();
	}
	
	
	public List<TrashCan> readTourSheetInput() throws IOException {
        String location = System.getProperty("user.dir") + "\\input\\can_list\\TourSheet.xlsx";
        List<TrashCan> canList = new ArrayList<TrashCan>();
        TrashCan can = new TrashCan();
	
        InputStream InputFile = new FileInputStream(location);
        XSSFWorkbook wb = new XSSFWorkbook(InputFile);
        int start, end, count;
            
        XSSFSheet sheet = wb.getSheetAt(0);
        Row row = sheet.getRow(2);
        Cell cell = row.getCell(1);
        start = ((int) cell.getNumericCellValue()) - 1;
        count = start;
        cell = row.getCell(3);
        end = ((int) cell.getNumericCellValue()) - 1;
            
        while(count <= end)
        {
        	row = sheet.getRow(count);
            can = readTrashCan(row);
            canList.add(can);
            count++;
        }

        wb.close();
        return canList;
	}
	
	private static TrashCan readTrashCan(Row row)
	{
		TrashCan can = new TrashCan();
		GpsData gpsData = new GpsData();
		EPublicStatus publicStatus = EPublicStatus.PUBLIC;
		EPublicStatus privateStatus = EPublicStatus.PRIVATE;
		
		// Can number
		Cell cell = row.getCell(0);
		can.setCanNumber(Integer.toString((int) cell.getNumericCellValue())); 
		
		// Public status
		cell = row.getCell(1);
		if(cell.getStringCellValue().equals("public"))
			can.setPublicStatus(publicStatus);
		else
			can.setPublicStatus(privateStatus);
		
		// Address
		cell = row.getCell(2);
		can.setAddress(cell.getStringCellValue());
		
		// GpsData
		cell = row.getCell(3);
        gpsData.setLatitude(cell.getNumericCellValue());
        cell = row.getCell(4);
        gpsData.setLongitude(cell.getNumericCellValue());
        can.setGpsData(gpsData);
		
        /*
         * 	// SensorBoolean
			cell = row.getCell(5);
			if(cell.getStringCellValue().equals("true"))
				can.setSensor(true);
			else
				can.setSensor(false);
         * 
         */
        
        /*
         *  // FillLevel
			cell = row.getCell(6);
			if(cell.getStringCellValue().equals("overfull"))
				can.setFillLevel(overFull);
			else if(cell.getStringCellValue().equals("full"))
				can.setFillLevel(full);
			else if(cell.getStringCellValue().equals("half-full"))
				can.setFillLevel(halfFull);
			else if(cell.getStringCellValue().equals("empty"))
				can.setFillLevel(empty);
         * 
         */
        
        cell = row.getCell(6);
        switch(cell.getStringCellValue()) {
        	case "Monday" :
        		can.setDaySpecification(EDaySpecification.MONDAY);
        		break;
        	case "Tuesday" :
        		can.setDaySpecification(EDaySpecification.TUESDAY);
        		break;
        	case "Wednesday" :
        		can.setDaySpecification(EDaySpecification.WEDNESDAY);
        		break;
        	case "Thursday" :
        		can.setDaySpecification(EDaySpecification.THURSDAY);
        		break;
        	case "Friday" :
        		can.setDaySpecification(EDaySpecification.FRIDAY);
        		break;
        }
        
        can.setSensor(false);

		return can;
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

	public List<TrashCan> getCanList() {
		return canList;
	}

	public void setCanList(List<TrashCan> canList) {
		this.canList = canList;
	}

	public int getSimulationIterations() {
		return simulationIterations;
	}

	public void setSimulationIterations(int simulationIterations) {
		this.simulationIterations = simulationIterations;
	}
	
}
