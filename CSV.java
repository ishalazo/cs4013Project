import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;

/** Utility class for reading and writing to CSV files
 * @author Clodagh Walsh 19230737
 * */
public class CSV {
	
	/** Read the contents from a specified CSV file
	 * @param String file 
	 * @return ArrayList<String>
	 * */
	public static ArrayList<String[]> readFromFile(String file) {
		ArrayList<String[]> data = new ArrayList<String[]>();
		String[] dataLine;
		try { // buffer for speed up ????
			
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
	
	// if could do column heading even better
	public static ArrayList<String> readFromColumn(String file,int column){
		ArrayList<String[]> csvFile = readFromFile(file);
		ArrayList<String> columnData = new ArrayList<String>();
		
		for(int i=0;i<csvFile.size();i++)
		{
			String[] temp = csvFile.get(i);
			columnData.add(temp[column]);
		}
		
		return columnData;
	}
	
	/** Add data to a specified CSV file
	 * @param String file 
	 * @param String[] contents
	 * */
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
	
	public static ArrayList<String[]> filterByYear(int year){
			ArrayList<String[]> csv = readFromFile("taxPayments.csv");
			ArrayList<String[]> rows = new ArrayList<String[]>();
			for(int i=0;i<csv.size();i++)
			{
				String[] row = csv.get(i);
	            double yr = Double.parseDouble(row[2]);
	            if(yr == year)
	            {
	            	rows.add(row);
	            }
			}
			
			return rows;
	}
	
	public static ArrayList<String[]> filterBasedOnPaymentStatus(boolean paid){
		ArrayList<String[]> csv = readFromFile("taxPayments.csv");
		ArrayList<String[]> rows = new ArrayList<String[]>();
		for(int i=0;i<csv.size();i++)
		{
			String[] row = csv.get(i);
            boolean paymentStatus = Boolean.parseBoolean(row[4]);
            if(paymentStatus == paid)
            {
            	rows.add(row);
            }
		}
		
		return rows;
	}

	public static ArrayList<String[]> filterByUserName(String username){
		ArrayList<String[]> csv = readFromFile("taxPayments.csv");
		ArrayList<String[]> rows = new ArrayList<String[]>();
		for(int i=0;i<csv.size();i++)
		{
			String[] row = csv.get(i);
            String login = row[1];
            if(login.equals(username))
            {
            	rows.add(row);
            }
		}
		
		return rows;
	}
	
	public static ArrayList<String[]> filterByEircode(String routingKey){
		ArrayList<String[]> csv = readFromFile("taxPayments.csv");
		ArrayList<String[]> rows = new ArrayList<String[]>();
		for(int i=0;i<csv.size();i++)
		{
			String[] row = csv.get(i);
            String eircode = row[0];
            if(eircode.startsWith(routingKey))
            {
            	rows.add(row);
            }
		}
		
		return rows;
	}
}
