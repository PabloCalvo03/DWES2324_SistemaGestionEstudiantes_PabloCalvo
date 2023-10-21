package com.sistemagestionestudiantes.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sistemagestionestudiantes.app.model.Student;
import com.sistemagestionestudiantes.app.repository.StudentMockUpRepository;

import jakarta.validation.Valid;

/**
 * Clase de controlador de estudiantes
 * 
 * @author pablo
 */
@Controller
public class StudentsController {
	
	/**
	 * Repository de estudiantes con inyeccción de dependencias de SpringBoot
	 */
	@Autowired
	private StudentMockUpRepository studentRepository;
	
	/**
	 * Endpoint para listar todos los estudiantes, ademas se muestra la edad media
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping("/")
	public String listStudents(Model model) {
		List<Student> students = studentRepository.getAllStudents();
		Integer edades = 0;
		for(Student s : students) {
			edades += s.getAge();
		}
		Double edadMedia = (double) (edades / students.size());
		model.addAttribute("students", students);
		model.addAttribute("edadMedia", edadMedia);
		return "index";
	}
	

	/**
	 * Endpoint que nos lleva a el formulario de estudiante y nos pone una instancia para nosotros
	 * modificarla mediante el formulario
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping("formAddStudent")
	public String formAddStudent(Model model) {
		Student student = new Student();
		model.addAttribute("student", student);
		return "addStudent";
	}
	

	/**
	 * Endpoint que se activa una vez hacemos click en el boton de registrar usuario, en caso de que
	 * el usuario haya hecho algo mal se envian mensajes de error
	 * 
	 * @param student
	 * @param bindingResult
	 * @return
	 */
	@PostMapping("formAddStudent")
	public String  processFormAddStudent(@Valid @ModelAttribute("student") Student student, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "addStudent";
		}
		studentRepository.registerStudent(student);
		return "redirect:/";
	}
	
	/**
	 * Endpoint para realizar la busqueda por nombre, sirve tambien si comienzas a escribir la P de Pablo,
	 * por ejemplo ya que busca los estudiantes que empiezen por el la cadena que se pase, ademas se muestra
	 * la edad media de los resultados
	 * 
	 * @param name
	 * @param model
	 * @return
	 */
	@GetMapping("/searchStudentsByName")
	public String searchStudentsByName(@RequestParam("name") String name, Model model) {
		
		if(name.isBlank()) {
			return "redirect:/";
		}
		
	    List<Student> searchResults = studentRepository.findStudentsByName(name);
	    Integer edades = 0;
		for(Student s : searchResults) {
			edades += s.getAge();
		}
		if(searchResults.size() > 0) {
			Double edadMedia = (double) (edades / searchResults.size());
		    model.addAttribute("edadMedia", edadMedia);
		}

	    model.addAttribute("students", searchResults);

	    return "index"; 
	}
	
	/**
	 * Endpoint para buscar estudiantes por curso, ademas se muestra la edad media de los resultados
	 * 
	 * @param course
	 * @param model
	 * @return
	 */
	@GetMapping("/filterStudentsByCourse")
	public String filterStudentsByCourse(@RequestParam("course") String course, Model model) {
		if(course.equals("all")) {
			return "redirect:/";
		}
	    List<Student> searchResults = studentRepository.filterStudentsByCourse(course);
	    Integer edades = 0;
		for(Student s : searchResults) {
			edades += s.getAge();
		}
		if(searchResults.size() > 0) {
			Double edadMedia = (double) (edades / searchResults.size());
		    model.addAttribute("edadMedia", edadMedia);
		}
		
	    model.addAttribute("students", searchResults);

	    return "index"; 
	}

}
