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
	public TaxCalculator() {
		this(	100,
				100, 
				new double[] {0, 150000.0, 400000.0, 650000.0},
				new double[] {0, 0.01, 0.02, 0.04},
				new double[] {25, 50, 60, 80, 100},
				new String[] {"Countryside","Village","Small town","Large town","City"},
				0.07);
	}

	/**
	 * @param propBounds Double array of Estimated Market Value boundary cost 
	 * @param rateBounds
	 * @param locCharge
	 * @param locType
	 */
	public TaxCalculator(double fixedCost, double flatCharge, double[] propBounds, 
			double[] rateBounds, double[] locCharge, String[] locType, double penalty) {
		TaxCalculator.fixedCost = fixedCost;
		TaxCalculator.flatCharge = flatCharge;
		TaxCalculator.propBounds = propBounds;
		TaxCalculator.rateBounds = rateBounds;
		TaxCalculator.locCharge = locCharge;
		TaxCalculator.locType = locType;
		TaxCalculator.penalty = penalty;
	}
	
	/*
	 * This method finds which rate the tax is calculated with.
	 * This depends on the property's estimated market value.
	 * Returns the rate the property has depend on the property's market value
	 */
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
	
	/*
	 * This method finds which location charge is applied to the property. 
	 * This depends on where the property is located. 
	 * Returns the charge of a property 
	 */
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
		return (prev*(1+penalty)) + TaxCalculator.calculateTax(current);	
	}
}
