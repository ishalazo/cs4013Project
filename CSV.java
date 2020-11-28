import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

public class CSV {
	/** Utility class for reading and writing to CSV files*/
	
	public static void writeToFile(String file,String[] content) {
		try{
            FileWriter writer = new FileWriter(file,true);
            BufferedWriter buffer = new BufferedWriter(writer);
            PrintWriter print = new PrintWriter(buffer);
            String out = Arrays.toString(content).replace("[", "").replace("]", "");
            // try find regex for above instead
            print.println(out);
            print.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
	}
	
	public static ArrayList<String[]> readFromFile(String file) {
		ArrayList<String[]> data = new ArrayList<String[]>();
		String dataLine;
        try{
            BufferedReader buffer = new BufferedReader(new FileReader(file));
            
            while((dataLine = buffer.readLine()) != null)
            {
                data.add(dataLine.split(","));
            }
            buffer.close();
        }
        catch(FileNotFoundException e){
            System.out.println("Couldn't find file");
            e.printStackTrace();
        }
        catch(IOException e){
            System.out.println("Couldn't read file");
            e.printStackTrace();
        }
        return data;
	}
}