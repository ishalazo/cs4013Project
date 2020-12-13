import java.util.Arrays;

/** TaxCalculator Class
 * @author Lakeisha Lazo 19277997
 */

public class TaxCalculator {
	private static double fixedCost, flatCharge, penalty;
	private static double[] propBounds, rateBounds, locCharge; 
	private static String[] locType;
	
	/**
	 * Sets the default values to calculate tax with.
	 */
	public static void setCalculator() {
		setFixedCost(100);
		setFlatCharge(100);
		setPenalty(0.07);
		setPropBounds(new double[] {0, 150000.0, 400000.0, 650000.0});				
		setRateBounds(new double[] {0, 0.01, 0.02, 0.04});
		setLocCharge(new double[] {25, 50, 60, 80, 100});
		setLocType(new String[] {"Countryside","Village","Small town","Large town","City"});
						
	}
	
	
	
	/**
	 * @param fixedCost
	 * @param flatCharge
	 * @param penalty
	 * @param propBounds
	 * @param rateBounds
	 * @param locCharge
	 * @param locType
	 */
	public static void setCalculator(double fixedCost, double flatCharge, double penalty, double[] propBounds, double[] rateBounds,
			double[] locCharge, String[] locType) {
		TaxCalculator.setFixedCost(fixedCost);
		TaxCalculator.setFlatCharge(flatCharge);
		TaxCalculator.setLocCharge(locCharge);
		TaxCalculator.setLocType(locType);
		TaxCalculator.setPenalty(penalty);
		TaxCalculator.setPropBounds(propBounds);
		TaxCalculator.setRateBounds(rateBounds);
	}



	public static String viewCalc(){
		return String.format("€%.2f fixed cost of tax\n"+ 
							 "€%.2f when property is not principle private residence\n" +
							 "%.0f%% penalty\n" +
							 "Property value boundaries "+ Arrays.toString(propBounds) + 
							 "\nTax rates " + Arrays.toString(rateBounds) + 
							 "\nLocation categories " + Arrays.toString(locType) +
							 "\nLocation charges " + Arrays.toString(locCharge), 
							 fixedCost, flatCharge, penalty*100);
	}
	
	/**
	 * @param fixedCost the fixedCost to set
	 */
	public static void setFixedCost(double fixedCost) {
		TaxCalculator.fixedCost = fixedCost;
	}

	/**
	 * @param flatCharge the flatCharge to set
	 */
	public static void setFlatCharge(double flatCharge) {
		TaxCalculator.flatCharge = flatCharge;
	}

	/**
	 * @param penalty the penalty to set
	 */
	public static void setPenalty(double penalty) {
		TaxCalculator.penalty = penalty;
	}

	/**
	 * @param propBounds the propBounds to set
	 */
	public static void setPropBounds(double[] propBounds) {
		TaxCalculator.propBounds = propBounds;
	}

	/**
	 * @param rateBounds the rateBounds to set
	 */
	public static void setRateBounds(double[] rateBounds) {
		TaxCalculator.rateBounds = rateBounds;
	}

	/**
	 * @param locCharge the locCharge to set
	 */
	public static void setLocCharge(double[] locCharge) {
		TaxCalculator.locCharge = locCharge;
	}

	/**
	 * @param locType the locType to set
	 */
	public static void setLocType(String[] locType) {
		TaxCalculator.locType = locType;
	}
	
	

	/*
	 * This method finds which rate the tax is calculated with.
	 * This depends on the property's estimated market value.
	 * Returns the rate the property has depend on the property's market value
	 */
	private static double getRate(Property p) {
		double propVal = p.getMarketValue();
		double rate = rateBounds[rateBounds.length-1];
		for(int i = 0; i < propBounds.length-1; i++) {
			if (propVal <= propBounds[i+1]) {
				rate = rateBounds[i];
				break;
			}
		}
		return rate;
	}
	
	/*
	 * This method finds which location charge is applied to the property. 
	 * This depends on where the property is located. 
	 * Returns the charge of a property 
	 */
	public static double getCharge(Property p) {
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
	
	/**
	 * This method calculates the current year's property tax if the previous year has been paid. It sums the fixed cost of tax, the property's market value multiplied by the rate, the location charge and the charge if it is a principal private residence of the owner. 
	 * @param p Any property
	 * @return Returns the tax for that property
	 */
	public static double calculateTax(Property p) {
		return fixedCost + p.getMarketValue()*getRate(p) + getCharge(p) + ((!p.isprincipalResidence()) ? flatCharge : 0);
	}
	
	/**
	 * This method calculates the current year's property tax if the previous year has <b>not</b> been paid. It sums the previous year's tax with the penalty applied and the current year's tax.
	 * @param p Any property.
	 * @param prev The previous year's property tax.
	 * @return Returns the compounded tax.
	 */
	public static double compoundTax(Property current, double prev) {
		return (prev*(1+penalty)) + calculateTax(current);	
	}
}
