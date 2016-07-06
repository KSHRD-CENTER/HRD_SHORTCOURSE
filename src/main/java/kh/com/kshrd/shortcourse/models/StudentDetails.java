package kh.com.kshrd.shortcourse.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StudentDetails {

	@JsonProperty("ID")
	private Long id;
	
	@JsonProperty("STUDENT")
	private Student student;
	
	@JsonProperty("COURSE")
	private Course course;
	
	@JsonProperty("SHIFT")
	private Shift shift;
	
	@JsonProperty("COST")
	private double cost;
	
	@JsonProperty("DISCOUNT")
	private double discount;
	
	@JsonProperty("PAID_AMOUNT")
	private double paidAmount;
	
	@JsonProperty("REGISTERED_DATE")
	private String registeredDate;
	
	@JsonProperty("REGISTERED_BY")
	private User registeredBy;
	
	@JsonProperty("STATUS")
	private String status;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	public Shift getShift() {
		return shift;
	}
	public void setShift(Shift shift) {
		this.shift = shift;
	}
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	public String getRegisteredDate() {
		return registeredDate;
	}
	public void setRegisteredDate(String registeredDate) {
		this.registeredDate = registeredDate;
	}
	public User getRegisteredBy() {
		return registeredBy;
	}
	public void setRegisteredBy(User registeredBy) {
		this.registeredBy = registeredBy;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public double getPaidAmount() {
		return paidAmount;
	}
	public void setPaidAmount(double paidAmount) {
		this.paidAmount = paidAmount;
	}
	@Override
	public String toString() {
		return "StudentDetails [id=" + id + ", student=" + student + ", course=" + course + ", shift=" + shift
				+ ", cost=" + cost + ", discount=" + discount + ", paidAmount=" + paidAmount + ", registeredDate="
				+ registeredDate + ", registeredBy=" + registeredBy + ", status=" + status + "]";
	}
}
