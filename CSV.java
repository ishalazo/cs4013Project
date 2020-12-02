import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import com.opencsv.*;

public class CSV {
	public static ArrayList<String[]> readFromFile(String file) {
		ArrayList<String[]> data = new ArrayList<String[]>();
		String[] dataLine;
		try {
			
			CSVReaderBuilder customReader = new CSVReaderBuilder(new FileReader(file));
			CSVParserBuilder customParser = new CSVParserBuilder();
			customReader.withSkipLines(1);
			customParser.withIgnoreQuotations(false);
			customReader.withCSVParser(customParser.build());
			
			CSVReader csv = customReader.build();

			while ((dataLine = csv.readNext()) != null) {
				data.add(dataLine);
			}
			csv.close();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}
	
	public static void writeToFile(String file,String[] content) {
		try {
			CSVWriter writer = new CSVWriter(new FileWriter(file,true));
			writer.writeNext(content,false);
			writer.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
