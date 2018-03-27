package output;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class SimulationResult {
	
	private List<ResultSet> resultSetList;
	private ResultSet avarageResultSet;
	
	public SimulationResult() {
		this.resultSetList = new ArrayList<ResultSet>();
	}
	
	// UNUSED function
	public void printAvarageResult() {
		System.out.println("Anzahl Sensoren: " + this.resultSetList.get(0).getTour().getManager().getNumberOfSensors() + "    Anzahl gesparte Tonnen: " + this.avarageResultSet.getAmountOfClearanceSaved() + "  Clearence Time saved: " + this.avarageResultSet.getClearenceTimeSaved() + "   Time saved: " + this.avarageResultSet.getTimeSaved() + "   Stecke gespart: " + this.avarageResultSet.getDistanceSaved());
	}
	
	public void createSimulationResult() throws FileNotFoundException {
		XSSFWorkbook wb = new XSSFWorkbook();
		String location = System.getProperty("user.dir") + "\\output\\SimulationResult" + resultSetList.get(0).getTour().getManager().getNumberOfSensors() + "Sensors.xlsx";
		FileOutputStream outputStream = new FileOutputStream(new File(location));
		XSSFSheet sheet = wb.createSheet("Simulation result");
		OutputFormatter formatter = new OutputFormatter();
		
		formatter.setCollumnWidthResultTable(sheet);
		XSSFRow headline = sheet.createRow(0);
		XSSFCell headlineCell = headline.createCell(0);
		headlineCell.setCellValue("Simulation result");
		headlineCell.setCellStyle(formatter.createHeadlineCellStyle(wb));
		
		createMenueBar(wb, sheet, 2);
		int rowIndex = 4;
		XSSFRow row;
		for(ResultSet resultSet : resultSetList) {
			row = sheet.createRow(rowIndex);
			resultSet.writeResultSet(wb, sheet, row);
			rowIndex++;
		}
		
		ResultSet avarageResultSet = new ResultSet();
		avarageResultSet.createAvarageResultSet(resultSetList);
		row = sheet.createRow(rowIndex);
		avarageResultSet.writeAvarageResultSet(wb, sheet, row);
		
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
		OutputFormatter formatter = new OutputFormatter();
		XSSFRow menueFirst = sheet.createRow(index);
		XSSFRow menueSecond = sheet.createRow(index + 1);
		XSSFCellStyle menueStyle = formatter.createMenueCellStyle(wb);
		menueFirst.createCell(0).setCellValue("Tour nr");
		menueSecond.createCell(0);
		menueFirst.createCell(1).setCellValue("Duration complete");
		menueSecond.createCell(1).setCellValue("in minutes");
		menueFirst.createCell(2).setCellValue("Distance complete");
		menueSecond.createCell(2).setCellValue("in kilometers");
		menueFirst.createCell(3).setCellValue("Clearence time saved");
		menueSecond.createCell(3).setCellValue("in minutes");
		menueFirst.createCell(4).setCellValue("Time saved");
		menueSecond.createCell(4).setCellValue("in minutes");
		menueFirst.createCell(5).setCellValue("Distance saved");
		menueSecond.createCell(5).setCellValue("in kilometers");
		menueFirst.createCell(6).setCellValue("Time wasted");
		menueSecond.createCell(6).setCellValue("in minutes");
		menueFirst.createCell(7).setCellValue("Unnecessary distance");
		menueSecond.createCell(7).setCellValue("in kilometers");
		menueFirst.createCell(8).setCellValue("Amount of clearances");
		menueSecond.createCell(8).setCellValue("complete");
		menueFirst.createCell(9).setCellValue("Amount of clearances");
		menueSecond.createCell(9).setCellValue("saved");
		menueFirst.createCell(10).setCellValue("Amount of clearances");
		menueSecond.createCell(10).setCellValue("unnecessary");
		
		for(int i = 0; i <= 10; i++) {
			menueFirst.getCell(i).setCellStyle(menueStyle);
			menueSecond.getCell(i).setCellStyle(menueStyle);
		}
	}

	public List<ResultSet> getResultSetList() {
		return resultSetList;
	}

	public void setResultSetList(List<ResultSet> resultSetList) {
		this.resultSetList = resultSetList;
	}

	public ResultSet getAvarageResultSet() {
		return avarageResultSet;
	}

	public void setAvarageResultSet(ResultSet avarageResultSet) {
		this.avarageResultSet = avarageResultSet;
	}
	
	

}
