package com.sistemagestionestudiantes.app.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.sistemagestionestudiantes.app.model.Student;

/**
 * Clase para recuperar los estudiantes (Repository), ademas como dice el enunciado es un MockUp y los
 *  estudiantes se guardan en una Lista.
 *  
 * @author pablo
 */
@Repository
public class StudentMockUpRepository {
	
	/**
	 * Lista de estudiantes inicializada con valores de prueba
	 */
	List<Student> students = new ArrayList<>(Arrays.asList(new Student("Emilio", 27, "DAW2"), new Student("Cinta", 18, "DAW2"),new Student("Beatriz", 27, "DAW2"), new Student("Alberto", 27, "DAW1"), new Student("Bea", 23, "DAW1"), new Student("Pablo", 20, "DAW2")));
	
	/**
	 * Devuelve todos los estudiantes
	 * 
	 * @return
	 */
	public List<Student> getAllStudents(){
		return this.students;
	}
	
	/**
	 * Añadir un estudiante
	 * 
	 * @param student
	 * @return
	 */
	public boolean registerStudent(Student student) {
		return students.add(student);
	}

	/**
	 * Encontrar estudiantes por nombre
	 * 
	 * @param name
	 * @return
	 */
	public List<Student> findStudentsByName(String name) {
		return new ArrayList<Student>(students.stream().filter(student -> student.getName().startsWith(name)).toList());
	}
	
	/**
	 * Encontrar estudiantes por curso
	 * 
	 * @param course
	 * @return
	 */
	public List<Student> filterStudentsByCourse(String course) {
		return new ArrayList<Student>(students.stream().filter(student -> student.getCourse().equals(course)).toList());
	}

}
