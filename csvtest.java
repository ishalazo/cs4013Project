import java.util.ArrayList;

public class csvtest {
	public static void main(String[] args) {
		String[] info = {"Clodagh","17","Female"};
		String[] info2 = {"Peter","17","Male"};
		CSV.writeToFile("test.csv", info);
		CSV.writeToFile("test.csv", info2);
		System.out.println("Done");
		
		ArrayList<String[]> data = CSV.readFromFile("test.csv");

        for(int i=1;i<data.size();i++){
        	String[] d = data.get(i);
            String name = d[0];
            double age = Double.parseDouble(d[1]);
            String gender = d[2];
            System.out.println(name + " " + age + " " + gender);
        }
	}
}
