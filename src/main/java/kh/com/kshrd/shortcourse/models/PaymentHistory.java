package kh.com.kshrd.shortcourse.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PaymentHistory implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6476973257650906163L;

	@JsonProperty("ID")
	private Long id;
	
	@JsonProperty("STUDENT_DETAILS")
	private StudentDetails studentDetails;
	
	@JsonProperty("PAID_AMOUNT")
	private double paidAmount;
	
	@JsonProperty("PAID_DATE")
	private String paidDate;
	
	@JsonProperty("CREATED_DATE")
	private String createdDate;
	
	@JsonProperty("CREATED_BY")
	private User createdBy;
	
	@JsonProperty("UPDATED_DATE")
	private String updatedDate;
	
	@JsonProperty("UPDATED_BY")
	private User updatedBy;
	
	@JsonProperty("PAID_BY")
	private User paidBy;
	
	@JsonProperty("STATUS")
	private String status;
	
	@JsonProperty("LEFT_COST")
	private double leftCost;
	
	@JsonProperty("TOTAL PAID")
	private double totalPaid;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public StudentDetails getStudentDetails() {
		return studentDetails;
	}
	public void setStudentDetails(StudentDetails studentDetails) {
		this.studentDetails = studentDetails;
	}
	public double getPaidAmount() {
		return paidAmount;
	}
	public void setPaidAmount(double paidAmount) {
		this.paidAmount = paidAmount;
	}
	public String getPaidDate() {
		return paidDate;
	}
	public void setPaidDate(String paidDate) {
		this.paidDate = paidDate;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public User getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}
	public String getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}
	public User getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(User updatedBy) {
		this.updatedBy = updatedBy;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public User getPaidBy() {
		return paidBy;
	}
	public void setPaidBy(User paidBy) {
		this.paidBy = paidBy;
	}
	
	public double getLeftCost() {
		return leftCost;
	}

	public void setLeftCost(double leftCost) {
		this.leftCost = leftCost;
	}
	
	
	/**
	 * @return the totalPaid
	 */
	public double getTotalPaid() {
		return totalPaid;
	}
	/**
	 * @param totalPaid the totalPaid to set
	 */
	public void setTotalPaid(double totalPaid) {
		this.totalPaid = totalPaid;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PaymentHistory [id=" + id + ", studentDetails=" + studentDetails + ", paidAmount=" + paidAmount
				+ ", paidDate=" + paidDate + ", createdDate=" + createdDate + ", createdBy=" + createdBy
				+ ", updatedDate=" + updatedDate + ", updatedBy=" + updatedBy + ", paidBy=" + paidBy + ", status="
				+ status + ", leftCost=" + leftCost + ", totalPaid=" + totalPaid + "]";
	}
}
