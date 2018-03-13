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
	
	public SimulationResult() {
		this.resultSetList = new ArrayList<ResultSet>();
	}
	
	public void createSimulationResult() throws FileNotFoundException {
		XSSFWorkbook wb = new XSSFWorkbook();
		String location = System.getProperty("user.dir") + "\\output\\SimulationResult.xlsx";
		FileOutputStream outputStream = new FileOutputStream(new File(location));
		XSSFSheet sheet = wb.createSheet("Simulation result");
		OutputFormatter formatter = new OutputFormatter();
		
		formatter.setCollumnWidthResultTable(sheet);
		XSSFRow headline = sheet.createRow(0);
		XSSFCell headlineCell = headline.createCell(0);
		headlineCell.setCellValue("Simulation result");
		headlineCell.setCellStyle(formatter.createHeadlineCellStyle(wb));
		
		createMenueBar(wb, sheet, 2);
		int rowIndex = 3;
		XSSFRow row;
		for(ResultSet resultSet : resultSetList) {
			row = sheet.createRow(rowIndex);
			resultSet.writeResultSet(wb, sheet, row);
			rowIndex++;
		}
		
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
		XSSFRow menue = sheet.createRow(index);
		XSSFCellStyle menueStyle = formatter.createMenueCellStyle(wb);
		menue.createCell(0).setCellValue("Tour nr:");
		menue.createCell(1).setCellValue("Duration complete:");
		menue.createCell(2).setCellValue("Distance complete:");
		menue.createCell(3).setCellValue("Time saved:");
		menue.createCell(4).setCellValue("Distance saved:");
		menue.createCell(5).setCellValue("Time wasted:");
		menue.createCell(6).setCellValue("Unnecessary distance:");
		menue.createCell(7).setCellValue("Amount of clearances complete:");
		menue.createCell(8).setCellValue("Amount of clearances saved:");
		menue.createCell(9).setCellValue("Amount of unnecessary cleared:");
		
		for(int i = 0; i <= 9; i++) {
			menue.getCell(i).setCellStyle(menueStyle);
		}
	}

	public List<ResultSet> getResultSetList() {
		return resultSetList;
	}

	public void setResultSetList(List<ResultSet> resultSetList) {
		this.resultSetList = resultSetList;
	}

}
