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
	@JsonProperty("COURSE_DETAILS")
	private List<CourseDetailsForm> courseDetails;
	@JsonProperty("TOTAL_HOUR")
	public Integer totalHour;
	
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getTotalHour() {
		return totalHour;
	}
	public void setTotalHour(Integer totalHour) {
		this.totalHour = totalHour;
	}

	public List<CourseDetailsForm> getCourseDetails() {
		return courseDetails;
	}
	public void setCourseDetails(List<CourseDetailsForm> courseDetails) {
		this.courseDetails = courseDetails;
	}


	public static class RegisterCourseForm extends CourseForm{
		
		
	}
	
	public static class CourseDetailsForm {
		@JsonProperty("SHIFT")
		private Long shift;
		@JsonProperty("START_DATE")
		private String startDate;
		public Long getShift() {
			return shift;
		}
		public void setShift(Long shift) {
			this.shift = shift;
		}
		public String getStartDate() {
			return startDate;
		}
		public void setStartDate(String startDate) {
			this.startDate = startDate;
		}
		
	}
		
}
