/** TaxCalculator Class
 * @author Lakeisha Lazo 19277997*/

public class TaxCalculator {
	private static double flatCharge, penalty;
	private static double[] propBounds, rateBounds, locCharge; 
	private static String[] locType;
	
	public TaxCalculator() {
		this(100,
				new double[] {0, 150000.0, 400000.0, 650000.0},
				new double[] {0, 0.01, 0.02, 0.04},
				new double[] {25, 50, 60, 80, 100},
				new String[] {"Countryside","Village","Small town","Large town","City"},
				0.07);
	}

	/**
	 * @param propBounds
	 * @param rateBounds
	 * @param locCharge
	 * @param locType
	 */
	public TaxCalculator(double flatCharge, double[] propBounds, double[] rateBounds, double[] locCharge, String[] locType, double penalty) {
		TaxCalculator.flatCharge = flatCharge;
		TaxCalculator.propBounds = propBounds;
		TaxCalculator.rateBounds = rateBounds;
		TaxCalculator.locCharge = locCharge;
		TaxCalculator.locType = locType;
		TaxCalculator.penalty = penalty;
	}

	private static double getRate(Property p) {
		double propVal = p.getMarketValue();
		double rate = 0;
		rate = rateBounds[rateBounds.length-1];
		for(int i = 0; i < propBounds.length-1; i++) {
			if (propVal <= propBounds[i+1]) {
				rate = propBounds[i];
				break;
			}
		}
		return rate;
	}
	
	private static double getCharge(Property p) {
		String propLoc = p.getLocation();
		double charge = 0;
		for(int i = 0; i < locType.length; i++) {
			if(propLoc.equalsIgnoreCase(locType[i])) {
				charge = locCharge[i];
				break;
			}
		}
		return charge;
	}
	
	public static double calculateTax(Property p) {
		return flatCharge + (p.getMarketValue()*getRate(p)) + getCharge(p) + ((p.isprincipalResidence() == true) ? 100 : 0);
	}
	
	public static double compoundTax(Property current, Property prev) {
		return (TaxCalculator.calculateTax(prev)*(1+penalty)) + TaxCalculator.calculateTax(current);	
	}
}
