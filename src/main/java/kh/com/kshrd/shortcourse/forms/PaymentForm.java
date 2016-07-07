package kh.com.kshrd.shortcourse.forms;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PaymentForm {

	public static class RegisterNewPayment{
		@JsonProperty("PAID_AMOUNT")
		private double paidAmount;

		public double getPaidAmount() {
			return paidAmount;
		}

		public void setPaidAmount(double paidAmount) {
			this.paidAmount = paidAmount;
		}

		@Override
		public String toString() {
			return "RegisterNewPayment [paidAmount=" + paidAmount + "]";
		}
	}
}
