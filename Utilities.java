import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

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
	
	// // Written by Lakeisha Lazo 19277997
	public static void writeToCell(String file,Object replace,String[] row,String columnHeading) {
		ArrayList<String[]> csvFile = readFromFile(file);
		int index = indexCol(csvFile.get(0),columnHeading);
		
		for(int i=0;i<csvFile.size();i++) 
		{
			if(Arrays.equals(csvFile.get(i), row)) 
			{
				csvFile.get(i)[index] = replace.toString();
			}
		}
		
		try {
			CSVWriter writer = new CSVWriter(new FileWriter(file));
			writer.writeAll(csvFile);
			writer.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private static int indexCol(String[] headings,String columnHeading) {
		int index = 0;
		for(int i=0;i<headings.length;i++)
		{
			if(headings[i].equals(columnHeading))
			{
				index = i;
				break;
			}
		}
		return index;
	}

	public static ArrayList<String> readFromColumn(String file,int column){
		ArrayList<String[]> csvFile = readFromFile(file);
		return readFromColumn(csvFile,column);
	}
	
	// dont actaully need anymore
	public static ArrayList<String> readFromColumn(ArrayList<String[]> data,int column){
		ArrayList<String> columnData = new ArrayList<String>();
		
		for(int i=0;i<data.size();i++)
		{
			String[] temp = data.get(i);
			columnData.add(temp[column]);
		}
		
		return columnData;
	}
	
	
	// Jointly written by Lakeisha Lazo and Clodagh Walsh
	/** Filter a given CSV file
	 * @param String file
	 * @param String columnHeading - The CSV column to be filtered
	 * @param Object filter - The specific value being searched for in the specified column of the CSV
	 * @apiNote The first String[] in the list will always contain the column headings this is to allows for future filtering
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
		
		for(int i=1;i<data.size();i++)
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
	
	// Written by Lakeisha Lazo 19277997
	/**Reading from file to make appropriate objects*/
	public static ArrayList<Object> fileToArrayList(String fileName,String column,String target) {
        ArrayList<String[]> data = Utilities.filter(Utilities.readFromFile(fileName), column, target);
        ArrayList<Object> output = new ArrayList<Object>();        
        if(fileName.equals("properties.csv")) {
            for(int i = 1; i < data.size(); i++) {
                String[] row = data.get(i);
                Property p = new Property(row[0], row[1], row[2], row[3],Double.parseDouble(row[4]), Boolean.parseBoolean(row[5]),false);
                output.add(p);
            }
        } else if(fileName.equals("systemLogins.csv")) {
            for(int i = 1; i < data.size(); i++) {
                String[] row = data.get(i);
                Owner o = new Owner(row[0], row[1], row[2],false);
                output.add(o);
            }
        }
        return output;
    }
	
	public static void taxRecalculation() {
		TaxCalculator.setCalculator();
		ArrayList<String> data = Utilities.readFromColumn("taxPayments.csv",3);
		data.remove(0);
		int lastTaxYear = Integer.parseInt(Collections.max(data));
		
		if(lastTaxYear != LocalDate.now().getYear())
		{
			ArrayList<Object> registeredProperties = Utilities.fileToArrayList("properties.csv", "Eircode", "");
			
			for(Object obj:registeredProperties)
			{
				if(obj instanceof Property)
				{
					((Property)obj).calculateCurrentTax();
				}
				
			}
			
		}
	}
}