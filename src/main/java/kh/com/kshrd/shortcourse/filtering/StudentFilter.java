package kh.com.kshrd.shortcourse.filtering;

public class StudentFilter {

	private String studentName;
	private String courseTypeId;
	private String generationId;
	private String courseId;
	private String shiftId;
	
	public StudentFilter() {
		this.studentName = "";
		this.courseTypeId = "";
		this.generationId = "";
		this.courseId = "";
		this.shiftId = "";
	}
	
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getCourseTypeId() {
		return courseTypeId;
	}
	public void setCourseTypeId(String courseTypeId) {
		this.courseTypeId = courseTypeId;
	}
	public String getGenerationId() {
		return generationId;
	}
	public void setGenerationId(String generationId) {
		this.generationId = generationId;
	}
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public String getShiftId() {
		return shiftId;
	}
	public void setShiftId(String shiftId) {
		this.shiftId = shiftId;
	}

	@Override
	public String toString() {
		return "StudentFilter [studentName=" + studentName + ", courseTypeId=" + courseTypeId + ", generationId="
				+ generationId + ", courseId=" + courseId + ", shiftId=" + shiftId + "]";
	}
	
}
