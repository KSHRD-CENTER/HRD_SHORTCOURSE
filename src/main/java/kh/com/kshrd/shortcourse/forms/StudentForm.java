package kh.com.kshrd.shortcourse.forms;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StudentForm {
	
	public static class RegisterStudentForm{
		@JsonProperty("NAME")
		private String studentName;
		@JsonProperty("TELEPHONE")
		private String telephone;
		@JsonProperty("EMAIL")
		private String email;
		@JsonProperty("GENDER")
		private String gender;
		@JsonProperty("UNIVERSITY")
		private String university;
		@JsonProperty("YEAR")
		private String year;
		@JsonProperty("ADDRESS")
		private String address;
		@JsonProperty("STATUS")
		private String status;
		@JsonProperty("COURSES")
		private List<CourseDetails> courseDetails;
		
		public String getStudentName() {
			return studentName;
		}
		public void setStudentName(String studentName) {
			this.studentName = studentName;
		}
		public String getTelephone() {
			return telephone;
		}
		public void setTelephone(String telephone) {
			this.telephone = telephone;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getGender() {
			return gender;
		}
		public void setGender(String gender) {
			this.gender = gender;
		}
		public String getUniversity() {
			return university;
		}
		public void setUniversity(String university) {
			this.university = university;
		}
		public String getYear() {
			return year;
		}
		public void setYear(String year) {
			this.year = year;
		}
		public String getAddress() {
			return address;
		}
		public void setAddress(String address) {
			this.address = address;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public List<CourseDetails> getCourseDetails() {
			return courseDetails;
		}
		public void setCourseDetails(List<CourseDetails> courseDetails) {
			this.courseDetails = courseDetails;
		}
		@Override
		public String toString() {
			return "RegisterStudentForm [studentName=" + studentName + ", telephone=" + telephone + ", email=" + email
					+ ", gender=" + gender + ", university=" + university + ", year=" + year + ", address=" + address
					+ ", status=" + status + ", courseDetails=" + courseDetails + "]";
		}
	}
	
	public static class CourseDetails{
		@JsonProperty("COURSE")
		private Long course;
		@JsonProperty("SHIFT")
		private Long shift;
		@JsonProperty("COST_PRICE")
		private double costPrice;
		@JsonProperty("DISCOUNT")
		private double discount;
		@JsonProperty("PAID")
		private double paid;
		public Long getCourse() {
			return course;
		}
		public void setCourse(Long course) {
			this.course = course;
		}
		public Long getShift() {
			return shift;
		}
		public void setShift(Long shift) {
			this.shift = shift;
		}
		public double getCostPrice() {
			return costPrice;
		}
		public void setCostPrice(double costPrice) {
			this.costPrice = costPrice;
		}
		public double getDiscount() {
			return discount;
		}
		public void setDiscount(double discount) {
			this.discount = discount;
		}
		public double getPaid() {
			return paid;
		}
		public void setPaid(double paid) {
			this.paid = paid;
		}
		
	}
}
