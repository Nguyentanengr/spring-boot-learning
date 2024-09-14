package com.example.demoApplication;

import com.example.demoApplication.dao.AppDAO;
import com.example.demoApplication.entities.Course;
import com.example.demoApplication.entities.Instructor;
import com.example.demoApplication.entities.InstructorDetail;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(AppDAO appDAO) {
		return runner -> {
			findInstructorByJoinFetch(appDAO);
		};
	}

	public void findInstructorByJoinFetch(AppDAO appDAO) {
		System.out.println("Finding courses by instructor id ...");

		Long theId = 1L;

		Instructor instructor = appDAO.findInstructorByJoinFetch(theId);

		System.out.println(instructor);

		for (Course course : instructor.getCourses()) {
			System.out.println(course);
		}
	}

	public void findCoursesByInstructorId(AppDAO appDAO) {
		System.out.println("Finding courses by instructor id ...");

		Long theId = 1L;

		List<Course> courses = appDAO.findCoursesByInstructorId(theId);

		for (Course course : courses) {
			System.out.println(course);
		}
	}


	public void saveInstructor(AppDAO appDAO) {

		InstructorDetail instructorDetail = new InstructorDetail();
		instructorDetail.setLinkProfile("fb//nguyen.tan.link");

		Instructor instructor = new Instructor();
		instructor.setName("Tan Nguyen");
		instructor.setEmail("nguyentan@gmail.com");

		Course course1 = new Course();
		course1.setName("Business");
		Course course2 = new Course();
		course2.setName("Technology");

		instructor.setInstructorDetail(instructorDetail);
		instructor.setCourses(List.of(course1, course2));

		instructorDetail.setInstructor(instructor);

		course1.setInstructor(instructor);
		course2.setInstructor(instructor);

		appDAO.saveInstructor(instructor);
	}
}
