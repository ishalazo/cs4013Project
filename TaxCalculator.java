/** TaxCalculator Class
 * @author Lakeisha Lazo 19277997*/

public class TaxCalculator {
	private static double tax, rate;
	private static double[] propBounds, rateBounds, locCharge; 
	private static String[] locType;
	
	public TaxCalculator() {
		this(new double[]{0, 150000.0, 400000.0, 650000.0},
				new double[] {0, 0.01, 0.02, 0.04},
				new double[] {25, 50, 60, 80, 100},
				new String[] {"Countryside","Village","Small town","Large town","City"});
		tax = 100;
	}

	/**
	 * @param propBounds
	 * @param rateBounds
	 * @param locCharge
	 * @param locType
	 */
	public TaxCalculator(double[] propBounds, double[] rateBounds, double[] locCharge, String[] locType) {
		TaxCalculator.propBounds = new double[propBounds.length];
		TaxCalculator.rateBounds = new double[rateBounds.length];
		TaxCalculator.locCharge = new double[locCharge.length];
		TaxCalculator.locType = new String[locType.length];
		
		setDoubleArray(TaxCalculator.propBounds, propBounds);
		setDoubleArray(TaxCalculator.rateBounds, rateBounds);
		setDoubleArray(TaxCalculator.locCharge, locCharge);
		
		//setting String array - its funky cause need to have the order to be the same in order for it to work :/
		for(int i = 0; i < TaxCalculator.locType.length; i++) {
			TaxCalculator.locType[i] = locType[i];
		}
		
		tax = 100;
	}
	
	private void setDoubleArray(double[] arr1, double[] arr2) {
		for(int i = 0; i < arr1.length; i++) {
			arr1[i] = arr2[i];
		}
	}

	private static double getRate(Property p) {
		double propVal = p.getMarketValue();
		double rate = 0;
		if (propVal > propBounds[propBounds.length-1]) {
			rate = rateBounds[rateBounds.length-1];
		} else {
			for(int i = 0; i < propBounds.length-1; i++) {
				if (propVal <= propBounds[i+1]) {
					rate = propBounds[i];
					break;
				}
			}
		}
		return rate;
	}
	
	private static double getCharge(Property p) {
		String propLoc = p.getLocation();
		double charge = 0;
		for(int i = 0; i < locType.length; i++) {
			if(propLoc.equals(locType[i])) {
				charge = locCharge[i];
				break;
			}
		}
		return charge;
	}
	
	public static double calculateTax(Property p) {
		if(p.isPrincipalRes() == true) tax += 100;
		tax += getCharge(p);
		rate = getRate(p);
	}

}
