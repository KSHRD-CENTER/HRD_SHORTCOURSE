package kh.com.kshrd.shortcourse.filtering;

public class DashboardFilter {
	
	private String generationId;
	private String courseTypeId;
	private String courseId;
	private String courseName;
	
	public DashboardFilter(){
		generationId = "";
		courseTypeId = "";
		courseId = "";
		courseName = "";
	}
	
	/**
	 * @return the generationId
	 */
	public String getGenerationId() {
		return generationId;
	}
	/**
	 * @param generationId the generationId to set
	 */
	public void setGenerationId(String generationId) {
		this.generationId = generationId;
	}
	/**
	 * @return the courseTypeId
	 */
	public String getCourseTypeId() {
		return courseTypeId;
	}
	/**
	 * @param courseTypeId the courseTypeId to set
	 */
	public void setCourseTypeId(String courseTypeId) {
		this.courseTypeId = courseTypeId;
	}
	/**
	 * @return the courseId
	 */
	public String getCourseId() {
		return courseId;
	}
	/**
	 * @param courseId the courseId to set
	 */
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	/**
	 * @return the courseName
	 */
	public String getCourseName() {
		return courseName;
	}
	/**
	 * @param courseName the courseName to set
	 */
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DashboardFilter [generationId=" + generationId + ", courseTypeId=" + courseTypeId + ", courseId="
				+ courseId + ", courseName=" + courseName + "]";
	}
}
