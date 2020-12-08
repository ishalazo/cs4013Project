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
	
	// dont think this is even needed anymore
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

	/** Filter a given CSV file
	 * @param String file
	 * @param String columnHeading - The CSV column to be filtered
	 * @param Object filter - The specific value being searched for in the specified column of the CSV
	 * */
	public static ArrayList<String[]> filter(String file,String columnHeading,Object filter){
		ArrayList<String[]> csv = readFromFile(file);
		ArrayList<String[]> rows = new ArrayList<String[]>();
		int index = 0;
		try {
			CSVReader reader = new CSVReader(new FileReader(file));
			String[] headings = reader.readNext();
			for(int i=0;i<headings.length;i++)
			{
				if(headings[i].equals(columnHeading))
				{
					index = i;
					break;
				}
			}
			reader.close();
			
			for(int i=0;i<csv.size();i++)
			{
				String[] row = csv.get(i);
	            
	            if(filter instanceof Integer && Integer.valueOf(row[index]).equals(filter))
	            {
	            	rows.add(row);
	            }
	            else if(filter instanceof Boolean && Boolean.valueOf(row[index]).equals(filter))
				{
	            	rows.add(row);
				}
	            else if(filter instanceof String)
				{
					String s = (String)filter;
					if(Character.isLetter(s.charAt(0)) && row[index].startsWith(s))
					{
						rows.add(row);
					}
					else if(row[index].equals(s))
					{
						rows.add(row);
					}
				}
			}	
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return rows;
	}
	
}
