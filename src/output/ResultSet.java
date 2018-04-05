package output;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import entities.AcoCanFormat;
import entities.Tour;
import entities.TrashCan;
import enumerations.EFillLevel;
import enumerations.EPublicStatus;

public class ResultSet {
	
	private Tour tour;
	private double durationComplete;
	private double distanceComplete;
	private double clearenceTimeSaved;
	private double timeSaved;
	private double distanceSaved;
	private double timeWasted;
	private double unnecessaryDistance;
	private int amountOfClearanceComplete;
	private int amountOfClearanceSaved;
	private int unnecessaryCleared;
	
	public ResultSet()
	{
		
	}
	
	public ResultSet(Tour tour) throws FileNotFoundException, UnsupportedEncodingException
	{
		this.tour = tour;
		calculateDurationComplete();
		calculateDistanceComplete();
		calculateClearenceTimeSaved();
		calculateTimeSaved();
		calculateDistanceSaved();
		calculateAmountOfClearences();
		calculateUnnecessaryAmounts();
		createOutputTourFile();
		createOutputTourFileCSV();
		createOptimizatedTourFile();
		createOutputTspFile();
	}
	
	private void createOutputTourFile() throws FileNotFoundException {
		XSSFWorkbook wb = new XSSFWorkbook();
		String tourNumber = tour.getTourNumber();
		String location = System.getProperty("user.dir") + "\\output\\TourSheet" + tourNumber + ".xlsx";
		FileOutputStream outputStream = new FileOutputStream(new File(location));
		XSSFSheet sheet = wb.createSheet("Tour " + tourNumber);
		OutputFormatter formatter = new OutputFormatter();
		formatter.setCollumnWidthTourTable(sheet);
		
		XSSFRow menueBar = sheet.createRow(0);
		createTourSheetMenueBar(wb, sheet, menueBar);
		int count = 1;
        
        for (TrashCan can : tour.getCanList()) {
            writeTourResult(wb, sheet.createRow(count), can);
            count++;
        }
        
        try {
			wb.write(outputStream);
			outputStream.close();
			wb.close();
			System.out.println("Successfully written in tour file!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void createOutputTourFileCSV() throws FileNotFoundException {
		XSSFWorkbook wb = new XSSFWorkbook();
		String tourNumber = tour.getTourNumber();
		String location = System.getProperty("user.dir") + "\\output\\TourSheet" + tourNumber + ".csv";
		FileOutputStream outputStream = new FileOutputStream(new File(location));
		XSSFSheet sheet = wb.createSheet("Tour " + tourNumber);
		//OutputFormatter formatter = new OutputFormatter();
		//formatter.setCollumnWidthTourTable(sheet);
		
		XSSFRow menueBar = sheet.createRow(0);
		createCSVTourSheetMenueBar(wb, sheet, menueBar);
		int count = 1;
        
        for (TrashCan can : tour.getCanList()) {
        	writeCSVTourResult(wb, sheet.createRow(count), can);
            count++;
        }
        
        try {
			wb.write(outputStream);
			outputStream.close();
			wb.close();
			System.out.println("Successfully written in csv file!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void createOptimizatedTourFile() throws FileNotFoundException {
		XSSFWorkbook wb = new XSSFWorkbook();
		String tourNumber = tour.getTourNumber();
		String location = System.getProperty("user.dir") + "\\output\\OptimizatedTourFile" + tourNumber + ".xlsx";
		FileOutputStream outputStream = new FileOutputStream(new File(location));
		XSSFSheet sheet = wb.createSheet("Tour " + tourNumber);
		OutputFormatter formatter = new OutputFormatter();
		formatter.setCollumnWidthTourTable(sheet);
		
		XSSFRow menueBar = sheet.createRow(0);
		createTourSheetMenueBar(wb, sheet, menueBar);
		int count = 1;
		
        for (TrashCan can : tour.getCansToBeClearedList()) {
            writeTourResult(wb, sheet.createRow(count), can);
            count++;
        }
        
        try {
			wb.write(outputStream);
			outputStream.close();
			wb.close();
			System.out.println("Successfully written in optimizated tour file!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void createOutputTspFile() throws FileNotFoundException, UnsupportedEncodingException {
		String tourNumber = tour.getTourNumber();
		String location = System.getProperty("user.dir") + "\\output\\Tour" + tourNumber + ".tsp";
		List<AcoCanFormat> acoCanList = createAcoCanList();
		AcoCanFormat startPoint = new AcoCanFormat("SP", calculateGeoCoordinate(4899488), calculateGeoCoordinate(1211977));
		
		PrintWriter writer = new PrintWriter(location, "UTF-8");
		writer.println("NAME: " + "Tour" + tour.getTourNumber());
		writer.println("TYPE: TSP");
		writer.println("COMMENT: Regensburg Trash Collecltion " + "Tour1");
		writer.println("DIMENSION: " + acoCanList.size());
		writer.println("EDGE_WEIGHT_TYPE: GEO");
		writer.println("DISPLAY_DATA_TYPE: COORD_DISPLAY");
		writer.println("NODE_COORD_SECTION");
		writer.println(startPoint.getDescription() + " " + startPoint.getLatitude() + " " + startPoint.getLongitude());
		for(AcoCanFormat acoCan : acoCanList) {
			writer.println(acoCan.getDescription() + " " + acoCan.getLatitude() + " " + acoCan.getLongitude());
		}
		writer.println("EOF");
		
		writer.close();
	}
	
	public List<AcoCanFormat> createAcoCanList() {
		List<AcoCanFormat> acoCanList = new ArrayList<AcoCanFormat>();
		EPublicStatus publicStatus = EPublicStatus.PUBLIC;
		
		 for (TrashCan can : tour.getCansToBeClearedList()) {
			 AcoCanFormat acoCan = new AcoCanFormat();
			 // Create description for acoCan:  CanNr. + publicStatus
			 if(can.getPublicStatus().equals(publicStatus))
				 acoCan.setDescription(can.getCanNumber() + "PB");
			 else
				 acoCan.setDescription(can.getCanNumber() + "PT");
			 
			 // Calculate GEOCoordinates from GPSData
			 acoCan.setLatitude(calculateGeoCoordinate(can.getGpsData().getLatitude()));
			 acoCan.setLongitude(calculateGeoCoordinate(can.getGpsData().getLongitude()));
			 
			 acoCanList.add(acoCan);
		 }
		
		return acoCanList;
	}
	
	public double calculateGeoCoordinate(double gpsCoordinate) {
		double geoCoordinate, coordinateRest;
		boolean coordinateNegative = false;
		
		// Check if coordinate is negative
		if(gpsCoordinate < 0) {
			coordinateNegative = true;
			gpsCoordinate = gpsCoordinate * -1;
		}
		
		gpsCoordinate = gpsCoordinate / 100000;
		
		// Calculate geoCoordinate
		geoCoordinate= ((int) gpsCoordinate);
		coordinateRest = gpsCoordinate - geoCoordinate;
		coordinateRest = (coordinateRest * 60) / 100;
		geoCoordinate = geoCoordinate + coordinateRest;
		// Round result
		geoCoordinate = geoCoordinate * 100000;
		geoCoordinate = Math.round(geoCoordinate);
		geoCoordinate = geoCoordinate / 100000;
		
		if(coordinateNegative)
			geoCoordinate = geoCoordinate * -1;
		
		return geoCoordinate;
	}
	
	public void createTourSheetMenueBar(XSSFWorkbook wb, XSSFSheet sheet, XSSFRow row) {
		OutputFormatter formatter = new OutputFormatter();
		XSSFCellStyle menueStyle = formatter.createMenueCellStyle(wb);
		row.createCell(0).setCellValue("Can Nr.");
		row.createCell(1).setCellValue("Public status");
		row.createCell(2).setCellValue("Location");
		row.createCell(3).setCellValue("GPS Lat");
		row.createCell(4).setCellValue("GPS Long");
		row.createCell(5).setCellValue("Filllevel");
		row.createCell(6).setCellValue("Sensor");
		row.createCell(7).setCellValue("Day");
		
		for(int i = 0; i <= 7; i++) {
			row.getCell(i).setCellStyle(menueStyle);
		}
	}
	
	public void createCSVTourSheetMenueBar(XSSFWorkbook wb, XSSFSheet sheet, XSSFRow row) {
		row.createCell(0).setCellValue("Can Nr.");
		row.createCell(1).setCellValue("Public status");
		row.createCell(2).setCellValue("Location");
		row.createCell(3).setCellValue("GPS Lat");
		row.createCell(4).setCellValue("GPS Long");
		row.createCell(5).setCellValue("Filllevel");
		row.createCell(6).setCellValue("Sensor");
		row.createCell(7).setCellValue("Day");
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
		cell.setCellValue(can.sensorBooleanToString());
		cell.setCellStyle(formatter.createSensorCellStyle(wb, can.isSensor()));
		cell = row.createCell(7);
		cell.setCellValue(can.getDaySpecification().toString());
		cell.setCellStyle(standardTableCellStyle);
	}
	
	public void writeCSVTourResult(XSSFWorkbook wb, XSSFRow row, TrashCan can) {
		XSSFCellStyle gpsDataCellStyle = wb.createCellStyle();
		String gpsDataCellFormat = "##\".\"#####";
		XSSFDataFormat dataFormat = wb.createDataFormat();
		gpsDataCellStyle.setDataFormat(dataFormat.getFormat(gpsDataCellFormat));
		
		XSSFCell cell = row.createCell(0);
		cell.setCellValue(Integer.parseInt(can.getCanNumber()));
		cell = row.createCell(1);
		cell.setCellValue(can.getPublicStatus().toString());
		cell = row.createCell(2);
		cell.setCellValue(can.getAddress());
		cell = row.createCell(3);
		cell.setCellValue(can.getGpsData().getLatitude());
		cell.setCellStyle(gpsDataCellStyle);
		cell = row.createCell(4);
		cell.setCellValue(can.getGpsData().getLongitude());
		cell.setCellStyle(gpsDataCellStyle);
		cell = row.createCell(5);
		cell.setCellValue(can.getFillLevel().toString());
		cell = row.createCell(6);
		cell.setCellValue(can.sensorBooleanToString());
		cell = row.createCell(7);
		cell.setCellValue(can.getDaySpecification().toString());
	}
	
	public void writeResultSet(XSSFWorkbook wb, XSSFSheet sheet, XSSFRow row) {
		OutputFormatter formatter = new OutputFormatter();
		XSSFCell cell;
		
		cell = row.createCell(0);
		cell.setCellStyle(formatter.createSensorCellStyle(wb, true));
		cell.setCellValue(Integer.parseInt(tour.getTourNumber()));
		
		cell = row.createCell(1);
		cell.setCellStyle(formatter.createDoubleNumericTableCellStyle(wb));
		cell.setCellValue(Math.round(getDurationComplete() / 60));
		
		cell = row.createCell(2);
		cell.setCellStyle(formatter.createDoubleNumericTableCellStyle(wb));
		cell.setCellValue(Math.round(getDistanceComplete() / 1000));
		
		cell = row.createCell(3);
		cell.setCellStyle(formatter.createDoubleNumericTableCellStyle(wb));
		cell.setCellValue(Math.round(getClearenceTimeSaved() / 60));

		cell = row.createCell(4);
		cell.setCellStyle(formatter.createDoubleNumericTableCellStyle(wb));
		cell.setCellValue(Math.round(getTimeSaved() / 60));

		cell = row.createCell(5);
		cell.setCellStyle(formatter.createDoubleNumericTableCellStyle(wb));
		cell.setCellValue(Math.round(getDistanceSaved() / 1000));

		cell = row.createCell(6);
		cell.setCellStyle(formatter.createDoubleNumericTableCellStyle(wb));
		cell.setCellValue(Math.round(getTimeWasted() / 60));

		cell = row.createCell(7);
		cell.setCellStyle(formatter.createDoubleNumericTableCellStyle(wb));
		cell.setCellValue(Math.round(getUnnecessaryDistance() / 1000));
		
		cell = row.createCell(8);
		cell.setCellStyle(formatter.createStandardTableCellStyle(wb));
		cell.setCellValue(getAmountOfClearanceComplete());
		
		cell = row.createCell(9);
		cell.setCellStyle(formatter.createStandardTableCellStyle(wb));
		cell.setCellValue(getAmountOfClearanceSaved());
		
		cell = row.createCell(10);
		cell.setCellStyle(formatter.createStandardTableCellStyle(wb));
		cell.setCellValue(getUnnecessaryCleared());
	}
	
	public void writeAvarageResultSet(XSSFWorkbook wb, XSSFSheet sheet, XSSFRow row) {
		OutputFormatter formatter = new OutputFormatter();
		XSSFCell cell;
		
		cell = row.createCell(0);
		cell.setCellStyle(formatter.createSensorCellStyle(wb, true));
		cell.setCellValue("Avarage");
		
		cell = row.createCell(1);
		cell.setCellStyle(formatter.createSensorCellStyle(wb, true));
		cell.setCellValue(Math.round(getDurationComplete() / 60));
		
		cell = row.createCell(2);
		cell.setCellStyle(formatter.createSensorCellStyle(wb, true));
		cell.setCellValue(Math.round(getDistanceComplete() / 1000));

		cell = row.createCell(3);
		cell.setCellStyle(formatter.createSensorCellStyle(wb, true));
		cell.setCellValue(Math.round(getClearenceTimeSaved() / 60));
		
		cell = row.createCell(4);
		cell.setCellStyle(formatter.createSensorCellStyle(wb, true));
		cell.setCellValue(Math.round(getTimeSaved() / 60));

		cell = row.createCell(5);
		cell.setCellStyle(formatter.createSensorCellStyle(wb, true));
		cell.setCellValue(Math.round(getDistanceSaved() / 1000));

		cell = row.createCell(6);
		cell.setCellStyle(formatter.createSensorCellStyle(wb, true));
		cell.setCellValue(Math.round(getTimeWasted() / 60));

		cell = row.createCell(7);
		cell.setCellStyle(formatter.createSensorCellStyle(wb, true));
		cell.setCellValue(Math.round(getUnnecessaryDistance() / 1000));
		
		cell = row.createCell(8);
		cell.setCellStyle(formatter.createSensorCellStyle(wb, true));
		cell.setCellValue(getAmountOfClearanceComplete());
		
		cell = row.createCell(9);
		cell.setCellStyle(formatter.createSensorCellStyle(wb, true));
		cell.setCellValue(getAmountOfClearanceSaved());
		
		cell = row.createCell(10);
		cell.setCellStyle(formatter.createSensorCellStyle(wb, true));
		cell.setCellValue(getUnnecessaryCleared());
	}
	
	// Calculation functions
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
	
	private void calculateClearenceTimeSaved()
	{
		double timeSaved = 0;
		
		timeSaved = tour.getEmptyCansWithSensorList().size() * (tour.getManager().getEmptyingTime());
		
		this.clearenceTimeSaved = timeSaved;
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
		this.amountOfClearanceComplete = tour.getCanList().size() - tour.getEmptyCansWithSensorList().size();
		this.amountOfClearanceSaved = tour.getEmptyCansWithSensorList().size();
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
	
	public void createAvarageResultSet(List<ResultSet> resultSetList) {
		for(ResultSet resultSet : resultSetList) {
			this.amountOfClearanceComplete += resultSet.getAmountOfClearanceComplete();
			this.amountOfClearanceSaved += resultSet.getAmountOfClearanceSaved();
			this.unnecessaryCleared += resultSet.getUnnecessaryCleared();
			this.distanceComplete += resultSet.getDistanceComplete();
			this.distanceSaved += resultSet.getDistanceSaved();
			this.durationComplete += resultSet.getDurationComplete();
			this.clearenceTimeSaved += resultSet.getClearenceTimeSaved();
			this.timeSaved += resultSet.getTimeSaved();
			this.timeWasted += resultSet.getTimeWasted();
			this.unnecessaryDistance += resultSet.getUnnecessaryDistance();
		}
		
		this.amountOfClearanceComplete = this.amountOfClearanceComplete / resultSetList.size();
		this.amountOfClearanceSaved = this.amountOfClearanceSaved / resultSetList.size();
		this.unnecessaryCleared = this.unnecessaryCleared / resultSetList.size();
		this.distanceComplete = Math.round((this.distanceComplete / resultSetList.size()));
		this.distanceSaved = Math.round((this.distanceSaved / resultSetList.size()));
		this.durationComplete = Math.round((this.durationComplete / resultSetList.size()));
		this.clearenceTimeSaved = Math.round((this.clearenceTimeSaved / resultSetList.size()));
		this.timeSaved = Math.round((this.timeSaved / resultSetList.size()));
		this.timeWasted = Math.round((this.timeWasted / resultSetList.size()));
		this.unnecessaryDistance = Math.round((this.unnecessaryDistance / resultSetList.size()));
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
	
	public double getClearenceTimeSaved() {
		return clearenceTimeSaved;
	}

	public void setClearenceTimeSaved(double clearenceTimeSaved) {
		this.clearenceTimeSaved = clearenceTimeSaved;
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

	public int getAmountOfClearanceSaved() {
		return amountOfClearanceSaved;
	}

	public void setAmountOfClearanceSaved(int amountOfClearanceSaved) {
		this.amountOfClearanceSaved = amountOfClearanceSaved;
	}
	
	public double getTimeWasted() {
		return timeWasted;
	}
	
	public void setTimeWasted(double timeWasted) {
		this.timeWasted = timeWasted;
	}

	public int getAmountOfClearanceComplete() {
		return amountOfClearanceComplete;
	}

	public void setAmountOfClearanceComplete(int amountOfClearanceComplete) {
		this.amountOfClearanceComplete = amountOfClearanceComplete;
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
