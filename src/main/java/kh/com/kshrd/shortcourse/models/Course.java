package kh.com.kshrd.shortcourse.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Course implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2318909832740615306L;
	
	@JsonProperty("ID")
	private Long id;
	
	@JsonProperty("COURSE")
	private String course;
	
	@JsonProperty("DESCRIPTION")
	private String description;
	
	@JsonProperty("GENERATION")
	private Generation generation;
	
	@JsonProperty("CREATED_DATE")
	private String createdDate;
	
	@JsonProperty("CREAETD_BY")
	private User createdBy;
	
	@JsonProperty("UPDATED_DATE")
	private String updatedDate;
	
	@JsonProperty("UPDATED_BY")
	private User updatedBy;
	
	@JsonProperty("COST")
	private double cost;
	
	@JsonProperty("DISCOUNT")
	private double discount;
	
	@JsonProperty("STATUS")
	private String status;
	
	@JsonIgnore
	private List<CourseDetails> courseDetails = new ArrayList<CourseDetails>();
	
	@JsonProperty("SHIFT")
	private String shift;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Generation getGeneration() {
		return generation;
	}
	public void setGeneration(Generation generation) {
		this.generation = generation;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public List<CourseDetails> getCourseDetails() {
		return courseDetails;
	}
	public void setCourseDetails(List<CourseDetails> courseDetails) {
		this.courseDetails = courseDetails;
	}
	public String getShift() {
		return shift;
	}
	public void setShift(String shift) {
		this.shift = shift;
	}
	@Override
	public String toString() {
		return "Course [id=" + id + ", course=" + course + ", description=" + description + ", generation=" + generation
				+ ", createdDate=" + createdDate + ", createdBy=" + createdBy + ", updatedDate=" + updatedDate
				+ ", updatedBy=" + updatedBy + ", cost=" + cost + ", discount=" + discount + ", status=" + status
				+ ", courseDetails=" + courseDetails + ", shift=" + shift + "]";
	}
}
