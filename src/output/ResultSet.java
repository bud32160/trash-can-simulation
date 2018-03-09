package output;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import entities.Tour;
import entities.TrashCan;
import enumerations.EFillLevel;

public class ResultSet {
	
	private Tour tour;
	private double durationComplete;
	private double distanceComplete;
	private double timeSaved;
	private double distanceSaved;
	private double timeWasted;
	private double unnecessaryDistance;
	private int amountOfClearenceComplete;
	private int amountOfClearenceSaved;
	private int unnecessaryCleared;
	
	public ResultSet()
	{
		
	}
	
	public ResultSet(Tour tour) throws FileNotFoundException
	{
		this.tour = tour;
		calculateDurationComplete();
		calculateDistanceComplete();
		calculateTimeSaved();
		calculateDistanceSaved();
		calculateAmountOfClearences();
		calculateUnnecessaryAmounts();
		
		createOutputTourFile();
		createOutputResultFile();
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
	
	private void calculateTimeSaved()
	{
		double timeSaved = 0;
		
		timeSaved = tour.getEmptyCansWithSensorList().size() * (tour.getManager().getEmptyingTime() + tour.getManager().getSingleDrivingTime());
		
		this.timeSaved = timeSaved;
	}

	private void calculateDistanceSaved()
	{
		double distanceSaved = 0;
		
		distanceSaved = tour.getEmptyCansWithSensorList().size() * tour.getManager().getSingleDrivingDistance();
		
		this.distanceSaved = distanceSaved;
	}
	
	
	private void calculateAmountOfClearences() {
		this.amountOfClearenceComplete = tour.getCanList().size() - tour.getEmptyCansWithSensorList().size();
		this.amountOfClearenceSaved = tour.getEmptyCansWithSensorList().size();
	}

	private void calculateUnnecessaryAmounts() {
		Iterator<TrashCan> iterator = tour.getCansWithoutSensorList().iterator();
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
	
	/*
	 * 	public void printResultSet() {
			System.out.println("-----------------Result Set-----------------------\n");
			System.out.println("Anzahl Tonnen gesamt: " + getTour().getManager().getNumberOfCans() + "  --  Anzahl Sensoren: " + getTour().getManager().getNumberOfSensors());
			System.out.println("\n--------------------------------------------------\n");
			System.out.println("Geleerte Tonnen gesamt: " + getAmountOfClearenceComplete() + " Stück");
			System.out.println("Zeit gesamt: " + getDurationComplete() + " Sekunden");
			System.out.println("Strecke gesamt: " + getDistanceComplete() + " Meter");
			System.out.println("\n--------------------------------------------------\n");
			System.out.println("Eingesparte Tonnen: " + getAmountOfClearenceSaved() + " Stück");
			System.out.println("Eingesparte Zeit: " + getTimeSaved() + " Sekunden");
			System.out.println("Eingesparte Strecke: " + getDistanceSaved() + " Meter");
			System.out.println("\n--------------------------------------------------\n");
			System.out.println("Unnötig geleerte Tonnen: " + getUnnecessaryCleared() + " Stück");
			System.out.println("Verschwendete Zeit: " + getTimeWasted() + " Sekunden");
			System.out.println("Unnötig gefahrene Strecke: " + getUnnecessaryDistance() + " Meter");
		}
	 */

	
	private void createOutputTourFile() throws FileNotFoundException {
		XSSFWorkbook wb = new XSSFWorkbook();
		String tourNumber = tour.getTourNumber();
		String location = System.getProperty("user.dir") + "\\output\\ResultTourSheet" + tourNumber + ".xlsx";
		FileOutputStream outputStream = new FileOutputStream(new File(location));
		XSSFSheet sheet = wb.createSheet("Tour " + tourNumber);
		OutputFormatter formatter = new OutputFormatter();
		
		
		formatter.setCollumnWidt(sheet);
		XSSFRow headline = sheet.createRow(0);
		headline.createCell(0).setCellValue("Tour " + tourNumber);
		int menueBarIndex = 2;
		
		createMenueBar(wb, sheet, menueBarIndex);
		
		int start = 3;
        int count = start;
        XSSFRow row;    
        
        for (TrashCan can : tour.getCanList()) {
        	row = sheet.createRow(count);
            writeTourResult(wb, row, can);
            count++;
        }
        
        try {
			wb.write(outputStream);
			outputStream.close();
			wb.close();
			System.out.println("Successfully written in file!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void createOutputResultFile() throws FileNotFoundException {
		XSSFWorkbook wb = new XSSFWorkbook();
		String tourNumber = tour.getTourNumber();
		String location = System.getProperty("user.dir") + "\\output\\ResultData" + tourNumber + ".xlsx";
		FileOutputStream outputStream = new FileOutputStream(new File(location));
		XSSFSheet sheet = wb.createSheet("Tour " + tourNumber);
		
		XSSFRow headline = sheet.createRow(0);
		headline.createCell(0).setCellValue("Resultset Tour " + tourNumber);
		
		createResultSet(wb, sheet);
		try {
			wb.write(outputStream);
			outputStream.close();
			wb.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void createMenueBar(XSSFWorkbook wb, XSSFSheet sheet, int index) {
		Row menue = sheet.createRow(index);
		menue.createCell(0).setCellValue("Can Nr.");
		menue.createCell(1).setCellValue("Public status");
		menue.createCell(2).setCellValue("Location");
		menue.createCell(3).setCellValue("GPS Lat");
		menue.createCell(4).setCellValue("GPS Long");
		menue.createCell(5).setCellValue("Filllevel");
		menue.createCell(6).setCellValue("Sensor");
		menue.createCell(7).setCellValue("Day");
		
		XSSFCellStyle menueStyle = createMenueFont(wb);
		for(int i = 0; i <= 7; i++) {
			menue.getCell(i).setCellStyle(menueStyle);
		}
	}
	
	public void writeTourResult(XSSFWorkbook wb, XSSFRow row, TrashCan can) {
		OutputFormatter formatter = new OutputFormatter();
		XSSFCellStyle standardTableCellStyle = formatter.createStandardTableCellStyle(wb);
		
		XSSFCell cell = row.createCell(0);
		cell.setCellValue(Integer.parseInt(can.getCanNumber()));
		cell.setCellStyle(standardTableCellStyle);
		cell = row.createCell(1);
		cell.setCellValue(can.getPublicStatus().toString());
		cell.setCellStyle(standardTableCellStyle);
		cell = row.createCell(2);
		cell.setCellValue(can.getAddress());
		cell.setCellStyle(standardTableCellStyle);
		cell = row.createCell(3);
		cell.setCellValue(can.getGpsData().getLatitude());
		cell.setCellStyle(formatter.createGpsDataTableCellStyle(wb));
		cell = row.createCell(4);
		cell.setCellValue(can.getGpsData().getLongitude());
		cell.setCellStyle(formatter.createGpsDataTableCellStyle(wb));
		cell = row.createCell(5);
		cell.setCellValue(can.getFillLevel().toString());
		cell.setCellStyle(formatter.createFillLevelCellStyle(wb, can.getFillLevel()));
		cell = row.createCell(6);
		cell.setCellValue(can.isSensor());
		cell.setCellStyle(formatter.createSensorCellStyle(wb, can.isSensor()));
		cell = row.createCell(7);
		cell.setCellValue(can.getDaySpecification().toString());
		cell.setCellStyle(standardTableCellStyle);
	}
	
	public void createResultSet(XSSFWorkbook wb, XSSFSheet sheet) {
		XSSFRow row = sheet.createRow(2);
		row.createCell(0).setCellValue("duration complete:");
		row.createCell(1).setCellValue(getDurationComplete());
		
		row = sheet.createRow(3);
		row.createCell(0).setCellValue("distance complete:");
		row.createCell(1).setCellValue(getDistanceComplete());
		
		row = sheet.createRow(4);
		row.createCell(0).setCellValue("distance complete:");
		row.createCell(1).setCellValue(getDistanceComplete());
	}
	
	public XSSFCellStyle createMenueFont(XSSFWorkbook wb) {
		XSSFCellStyle styleMenueBar = wb.createCellStyle();
		XSSFFont menueFont = wb.createFont();
		menueFont.setBold(true);
		menueFont.setFontName(XSSFFont.DEFAULT_FONT_NAME);
		menueFont.setFontHeightInPoints((short) 11);
		styleMenueBar.setFont(menueFont);
		styleMenueBar.setVerticalAlignment(VerticalAlignment.CENTER);
		
		return styleMenueBar;
	}
	
	public XSSFCellStyle createTableFontWithSensor(XSSFWorkbook wb) {
		XSSFCellStyle styleTable = wb.createCellStyle();
		XSSFFont tableFont = wb.createFont();
		tableFont.setFontName(XSSFFont.DEFAULT_FONT_NAME);
		tableFont.setFontHeightInPoints((short) 11);
		styleTable.setFont(tableFont);
		styleTable.setVerticalAlignment(VerticalAlignment.CENTER);
		
		return styleTable;
	}
	
	public XSSFCellStyle createTableFontWithoutSensor(XSSFWorkbook wb) {
		XSSFCellStyle styleTable = wb.createCellStyle();
		XSSFFont tableFont = wb.createFont();
		tableFont.setBold(true);
		tableFont.setFontName(XSSFFont.DEFAULT_FONT_NAME);
		tableFont.setFontHeightInPoints((short) 11);
		styleTable.setFont(tableFont);
		styleTable.setVerticalAlignment(VerticalAlignment.CENTER);
		
		return styleTable;
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

	public int getAmountOfClearenceComplete() {
		return amountOfClearenceComplete;
	}

	public void setAmountOfClearenceComplete(int amountOfClearenceComplete) {
		this.amountOfClearenceComplete = amountOfClearenceComplete;
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
