package com.example.demo;

import com.example.demo.dao.StudentDAO;
import com.example.demo.entities.Student;
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
	public CommandLineRunner commandLineRunner(StudentDAO studentDAO) {
		return runner -> {
			deleteStudent(studentDAO);
		};
	}


	public void deleteStudent(StudentDAO studentDAO) {

		System.out.println("deleting the student ...");

		studentDAO.delete(2L);

		System.out.println("deleted the student successful!");

	}
	public void updateStudent(StudentDAO studentDAO) {

		System.out.println("updating the student ...");

		Student student = studentDAO.findById(2L);

		student.setEmail("costa.com");

		studentDAO.update(student);

		System.out.println("updated successful!");

	}


	public void queryListStudentByLastName(StudentDAO studentDAO) {

		System.out.println("Finding students list by last name ...");

		List<Student> students = studentDAO.findByLastName("costa");

		System.out.println("Founded! The students list by last name: ");

		for (Student s : students) {
			System.out.println(s);
		}
	}

	public void queryListStudent(StudentDAO studentDAO) {

		System.out.println("Finding the students list ...");

		List<Student> students = studentDAO.findAll();

		System.out.println("Founded! The students list: ");

		for (Student s : students) {
			System.out.println(s);
		}

	}


	public void readStudent(StudentDAO studentDAO) {

		System.out.println("Finding the student with id 1 ...");

		Student tempStudent = studentDAO.findById(1L);

		System.out.println("Founded! The student with name: "
				+ tempStudent.getFirstName() + " " + tempStudent.getLastName());

	}

	public void createStudent(StudentDAO studentDAO) {
		// create the student object

		System.out.println("Creating new student object ...");

		Student tempStudent = new Student( "Diago", "Costa", "costa.com");


		// save the student object

		System.out.println("Saving the student ...");

		studentDAO.save(tempStudent);

		// display id of the saved student

		System.out.println("Save student. Generated id: " + tempStudent.getId());
	}
}
