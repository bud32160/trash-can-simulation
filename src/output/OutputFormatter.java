package output;

import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import enumerations.EFillLevel;

public class OutputFormatter {
	
	public OutputFormatter() {
	
	}
	
	public void setCollumnWidt(XSSFSheet sheet) {
		sheet.setColumnWidth(0, 10*256);
		sheet.setColumnWidth(1, 15*256);
		sheet.setColumnWidth(2, 35*256);
		sheet.setColumnWidth(3, 15*256);
		sheet.setColumnWidth(4, 15*256);
		sheet.setColumnWidth(5, 15*256);
		sheet.setColumnWidth(6, 15*256);
		sheet.setColumnWidth(7, 15*256);
	}
	
	public XSSFCellStyle createStandardTableCellStyle(XSSFWorkbook workbook) {
		XSSFCellStyle standardCellStyle = workbook.createCellStyle();
		XSSFFont tableFont = createStandardFont(workbook);
		standardCellStyle.setFont(tableFont);
		setStandardCellStyleAlignments(workbook, standardCellStyle);
		
		return standardCellStyle;
	}
	
	public XSSFCellStyle createGpsDataTableCellStyle(XSSFWorkbook workbook) {
		XSSFCellStyle gpsDataCellStyle = workbook.createCellStyle();
		XSSFFont tableFont = createStandardFont(workbook);
		gpsDataCellStyle.setFont(tableFont);
		setStandardCellStyleAlignments(workbook, gpsDataCellStyle);
		
		// TODO find solution for GPS format with ##.#####
		gpsDataCellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("##.#####"));
		
		return gpsDataCellStyle;
	}
	
	public XSSFCellStyle createFillLevelCellStyle(XSSFWorkbook workbook, EFillLevel fillLevel) {
		XSSFCellStyle fillLevelCellStyle = workbook.createCellStyle();
		XSSFFont tableFont = createStandardFont(workbook);
		fillLevelCellStyle.setFont(tableFont);
		setStandardCellStyleAlignments(workbook, fillLevelCellStyle);
		
		switch(fillLevel) {
			case EMPTY :
				fillLevelCellStyle.setFillForegroundColor(new XSSFColor(new java.awt.Color(144, 238, 118)));
				break;
			case HALFFULL :
				fillLevelCellStyle.setFillForegroundColor(new XSSFColor(new java.awt.Color(255, 255, 59)));
				break;
			case FULL :
				fillLevelCellStyle.setFillForegroundColor(new XSSFColor(new java.awt.Color(255, 51, 51)));
				break;
			case OVERFULL :
				fillLevelCellStyle.setFillForegroundColor(new XSSFColor(new java.awt.Color(192, 0, 0)));
				break;
		}
		
		return fillLevelCellStyle;
	}
	
	public XSSFCellStyle createSensorCellStyle(XSSFWorkbook workbook, boolean sensor) {
		XSSFCellStyle sensorCellStyle = workbook.createCellStyle();
		XSSFFont tableFont = createStandardFont(workbook);
		tableFont.setBold(sensor);
		sensorCellStyle.setFont(tableFont);
		setStandardCellStyleAlignments(workbook, sensorCellStyle);
		
		return sensorCellStyle;
	}
	
	public XSSFCellStyle createStandardTableCellFont(XSSFWorkbook workbook) {
		XSSFCellStyle standardCellStyle = workbook.createCellStyle();
		XSSFFont tableFont = createStandardFont(workbook);
		standardCellStyle.setFont(tableFont);
		setStandardCellStyleAlignments(workbook, standardCellStyle);

		return standardCellStyle;
	}
	
	public XSSFFont createStandardFont(XSSFWorkbook workbook) {
		XSSFFont tableFont = workbook.createFont();
		tableFont.setFontName("CALIBRI");
		tableFont.setFontHeightInPoints((short) 11);
		
		return tableFont;
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
	
	public void setStandardCellStyleAlignments(XSSFWorkbook workbook, XSSFCellStyle cellStyle) {
		XSSFColor backGroundColor = new XSSFColor(new java.awt.Color(234, 234, 234));
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		cellStyle.setFillForegroundColor(backGroundColor);
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setBorderBottom(BorderStyle.THIN);
		cellStyle.setBorderTop(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);
		cellStyle.setBorderLeft(BorderStyle.THIN);
	}

}
