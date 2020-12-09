import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;

/** Utility class for reading and writing to CSV files and for filtering lists of data
 * @author Clodagh Walsh 19230737
 * */
public class Utilities {
	
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
			//customReader.withSkipLines(1);
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
	public static void writeToFile(String file,String[] content) {  //change so pass column heading and then make that private method
		try {
			CSVWriter writer = new CSVWriter(new FileWriter(file,true));
			writer.writeNext(content,false);
			writer.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	/** Filter a given CSV file
	 * @param String file
	 * @param String columnHeading - The CSV column to be filtered
	 * @param Object filter - The specific value being searched for in the specified column of the CSV
	 * */
	public static ArrayList<String[]> filter(ArrayList<String[]> data,String columnHeading,Object filter){
			
		ArrayList<String[]> rows = new ArrayList<String[]>();
		int index = 0;
		String[] columnNames = data.get(0);
		
		for(int i=0;i<columnNames.length;i++)
		{
			if(columnNames[i].equals(columnHeading))
			{
				index = i;
				break;
			}
		}
		rows.add(columnNames); // need to have the column headings in the arraylist returned for future filtering 
		
		for(int i=0;i<data.size();i++)
			{
				String[] row = data.get(i);
	            
	            if(filter instanceof Integer && Integer.valueOf(row[index]).equals(filter))
	            {
	            	rows.add(row);
	            }
	            else if(filter instanceof Boolean && Boolean.valueOf(row[index]).equals(filter))
				{
	            	rows.add(row);
				}
	            else if(filter instanceof String && (row[index]).contains((CharSequence)filter))
				{
					rows.add(row);
					
				}
			}	
			
		   return rows;
	}
	
}
