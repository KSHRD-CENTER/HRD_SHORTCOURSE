package kh.com.kshrd.shortcourse.models;

public class Balance {
	private double actualBalance;
	private double estimateBalance;
	private double remainingBalance;
	/**
	 * @return the actualBalance
	 */
	public double getActualBalance() {
		return actualBalance;
	}
	/**
	 * @param actualBalance the actualBalance to set
	 */
	public void setActualBalance(double actualBalance) {
		this.actualBalance = actualBalance;
	}
	/**
	 * @return the estimateBalance
	 */
	public double getEstimateBalance() {
		return estimateBalance;
	}
	/**
	 * @param estimateBalance the estimateBalance to set
	 */
	public void setEstimateBalance(double estimateBalance) {
		this.estimateBalance = estimateBalance;
	}
	/**
	 * @return the remainingBalance
	 */
	public double getRemainingBalance() {
		return remainingBalance;
	}
	/**
	 * @param remainingBalance the remainingBalance to set
	 */
	public void setRemainingBalance(double remainingBalance) {
		this.remainingBalance = remainingBalance;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Balance [actualBalance=" + actualBalance + ", estimateBalance=" + estimateBalance
				+ ", remainingBalance=" + remainingBalance + "]";
	}
}
