package model;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.poifs.filesystem.OfficeXmlFileException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class BankDataDocument {

	//Fields
	private InputStream inp;
	private HSSFWorkbook wb;
	private HSSFSheet sheet;
	private int startingRow;
	private String filePath;
	
	public BankDataDocument(String filePath, int startingRow) throws InvalidFormatException, IOException {
		this.filePath = filePath;
		this.inp = new FileInputStream(filePath);
        try {
            this.wb = new HSSFWorkbook(inp);
            this.sheet = wb.getSheetAt(0);
        } catch(OfficeXmlFileException e) {

        }
		this.startingRow = startingRow;
	}



	//SET & GET
	public InputStream getInp() {
		return inp;
	}

	public void setInp(InputStream inp) {
		this.inp = inp;
	}

	public HSSFWorkbook getWb() {
		return wb;
	}

	public void setWb(HSSFWorkbook wb) {
		this.wb = wb;
	}

	public HSSFSheet getSheet() {
		return sheet;
	}

	public void setSheet(HSSFSheet sheet) {
		this.sheet = sheet;
	}

	public int getStartingRow() {
		return startingRow;
	}

	public void setStartingRow(int startingRow) {
		this.startingRow = startingRow;
	}
	public String getFilePath() {return this.filePath;}
}
