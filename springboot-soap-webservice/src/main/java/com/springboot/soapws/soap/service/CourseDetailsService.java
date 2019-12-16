package com.springboot.soapws.soap.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;

import com.springboot.soapws.soap.model.Course;

@Component
public class CourseDetailsService {
	
	public enum Status {
		SUCCESS,FAILURE;
	}

	private static List<Course> courses = new ArrayList<>();

	static {
		Course course1 = new Course(1, "Spring", "10 steps");
		courses.add(course1);
		Course course2 = new Course(2, "Spring MVC", "10 Examples");
		courses.add(course2);
		Course course3 = new Course(3, "Spring Boot", "6K students");
		courses.add(course3);
		Course course4 = new Course(4, "Maven", "Most popular maven course");
		courses.add(course4);
	}

	public Course findById(int id) {
		for (Course course : courses) {
			if (course.getId() == id) {
				return course;
			}
		}
		return null;
	}

	public List<Course> findAll() {
		return courses;
	}

	public Status deleteById(int id) {
		Iterator<Course> itr = courses.iterator();
		while (itr.hasNext()) {
			Course val = itr.next();
			if (val.getId() == id) {
				itr.remove();
				return Status.SUCCESS;
			}
		}
		return Status.FAILURE;
	}
}
