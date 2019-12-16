package com.springboot.soapws.soap;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.narayana.courses.CourseDetails;
import com.narayana.courses.DeleteCourseDetailsRequest;
import com.narayana.courses.DeleteCourseDetailsResponse;
import com.narayana.courses.GetAllCourseDetailsRequest;
import com.narayana.courses.GetAllCourseDetailsResponse;
import com.narayana.courses.GetCourseDetailsRequest;
import com.narayana.courses.GetCourseDetailsResponse;
import com.springboot.soapws.soap.exception.CourseNotFoundException;
import com.springboot.soapws.soap.model.Course;
import com.springboot.soapws.soap.service.CourseDetailsService;
import com.springboot.soapws.soap.service.CourseDetailsService.Status;

@Endpoint
public class CourseDetailsEndpoint {

	@Autowired
	private CourseDetailsService service;

	@PayloadRoot(namespace = "http://narayana.com/courses", localPart = "GetCourseDetailsRequest")
	public @ResponsePayload GetCourseDetailsResponse processCourseDetailsRequest(
			@RequestPayload GetCourseDetailsRequest request) {

		Course course = service.findById(request.getId());

		if (course == null) {
			throw new CourseNotFoundException("Invalid course Id " + request.getId());
		}

		return mapCourseDetails(course);

	}

	@PayloadRoot(namespace = "http://narayana.com/courses", localPart = "GetAllCourseDetailsRequest")
	public @ResponsePayload GetAllCourseDetailsResponse processAllCourseDetailsRequest(
			@RequestPayload GetAllCourseDetailsRequest request) {

		List<Course> courses = service.findAll();
		return mapAllCourseDetails(courses);

	}

	@PayloadRoot(namespace = "http://narayana.com/courses", localPart = "DeleteCourseDetailsRequest")
	public @ResponsePayload DeleteCourseDetailsResponse deleteCourseDetailsRequest(
			@RequestPayload DeleteCourseDetailsRequest request) {
		DeleteCourseDetailsResponse response = new DeleteCourseDetailsResponse();
		Status status = service.deleteById(request.getId());
		response.setStatus(mapStatus(status));
		return response;

	}

	private com.narayana.courses.Status mapStatus(Status status) {
		if (status == Status.FAILURE) {
			return com.narayana.courses.Status.FAILURE;
		}
		return com.narayana.courses.Status.SUCCESS;
	}

	private GetCourseDetailsResponse mapCourseDetails(Course course) {
		GetCourseDetailsResponse response = new GetCourseDetailsResponse();
		response.setCourseDetails(mapCourse(course));
		return response;
	}

	private GetAllCourseDetailsResponse mapAllCourseDetails(List<Course> courses) {
		GetAllCourseDetailsResponse response = new GetAllCourseDetailsResponse();
		for (Course course : courses) {
			CourseDetails mapCourse = mapCourse(course);
			response.getCourseDetails().add(mapCourse);
		}
		return response;
	}

	private CourseDetails mapCourse(Course course) {
		CourseDetails courseDetails = new CourseDetails();
		courseDetails.setId(course.getId());
		courseDetails.setName(course.getName());
		courseDetails.setDescription(course.getDescription());
		return courseDetails;
	}

}
