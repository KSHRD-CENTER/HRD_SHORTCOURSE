package kh.com.kshrd.shortcourse.filtering;

public class CourseFilter {

	private String course;
	
	public CourseFilter() {
		course = "";
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	@Override
	public String toString() {
		return "CourseFilter [course=" + course + "]";
	}
	
}
