package utilities;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class DataReaders {
	private static Workbook book;
	private static Sheet sheet;
	private static DataFormatter df = new DataFormatter();

	public static Object[][] getTestData(String dataSheet_Path, String sheetName) throws InvalidFormatException, IOException {
		FileInputStream file = null;
		file = new FileInputStream(dataSheet_Path);
		book = WorkbookFactory.create(file);

		sheet = book.getSheet(sheetName);
		Object[][] data = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
		for (int i = 0; i < sheet.getLastRowNum(); i++) {
			for (int k = 0; k < sheet.getRow(0).getLastCellNum(); k++) {
				Cell cellValue = sheet.getRow(i + 1).getCell(k);
				data[i][k] = df.formatCellValue(cellValue);
			}
		}
		return data;
	}
	
	public static JSONArray getTestData_json(String jsonFile_Path, String variableName) throws IOException, ParseException{
		JSONParser jsonParser = new JSONParser();
		FileReader reader = new FileReader(jsonFile_Path);

		Object obj = jsonParser .parse(reader);

		JSONObject jsonObj = (JSONObject)obj;
		JSONArray jsonArray= (JSONArray)jsonObj .get(variableName);

		return jsonArray;
	}
}
