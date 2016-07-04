package kh.com.kshrd.shortcourse.forms;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CourseForm {
	
	@JsonProperty("COURSE_NAME")
	private String courseName;
	@JsonProperty("DESCRIPTION")
	private String description;
	@JsonProperty("COST_PRICE")
	private Long costPrice;
	@JsonProperty("DISCOUNT")
	private Long discount;
	@JsonProperty("COURSE_TYPE")
	private Long courseType;
	@JsonProperty("GENERATION")
	private Long generation;
	@JsonProperty("STATUS")
	private String status;
	@JsonProperty("SHIFTS")
	private List<Long> shifts;
	
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public Long getCostPrice() {
		return costPrice;
	}
	public void setCostPrice(Long costPrice) {
		this.costPrice = costPrice;
	}
	public Long getDiscount() {
		return discount;
	}
	public void setDiscount(Long discount) {
		this.discount = discount;
	}
	public Long getCourseType() {
		return courseType;
	}
	public void setCourseType(Long courseType) {
		this.courseType = courseType;
	}
	public Long getGeneration() {
		return generation;
	}
	public void setGeneration(Long generation) {
		this.generation = generation;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<Long> getShifts() {
		return shifts;
	}
	public void setShifts(List<Long> shifts) {
		this.shifts = shifts;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "CourseForm [courseName=" + courseName + ", description=" + description + ", costPrice=" + costPrice
				+ ", discount=" + discount + ", courseType=" + courseType + ", generation=" + generation + ", status="
				+ status + ", shifts=" + shifts + "]";
	}

	public static class RegisterCourseForm extends CourseForm{
		
		
	}
		
}
