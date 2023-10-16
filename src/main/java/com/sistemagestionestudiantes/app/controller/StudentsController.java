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

@Controller
public class StudentsController {
	
	// Inyeccion de dependencias de SpringBoot
	@Autowired
	private StudentMockUpRepository studentRepository;
	
	// Endpoint para listar todos los estudiantes
	@GetMapping("listStudents")
	public String listStudents(Model model) {
		List<Student> students = studentRepository.getAllStudents();
		Integer edades = 0;
		for(Student s : students) {
			edades += s.getAge();
		}
		Double edadMedia = (double) (edades / students.size());
		model.addAttribute("students", students);
		model.addAttribute("edadMedia", edadMedia);
		return "listStudents.html";
	}
	
	// Endpoint que nos lleva a el formulario de estudiante y nos pone una instancia para nosotros
	// modificarla mediante el formulario
	@GetMapping("formAddStudent")
	public String formAddStudent(Model model) {
		Student student = new Student();
		model.addAttribute("student", student);
		return "addStudent.html";
	}
	
	// Endpoint que se activa una vez hacemos click en el boton de registrar usuario, en caso de que
	// el usuario haya hecho algo mal se envian mensajes de error
	@PostMapping("processFormAddStudent")
	public String  processFormAddStudent(@Valid @ModelAttribute("student") Student student, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "addStudent";
		}
		studentRepository.registerStudent(student);
		return "redirect:/listStudents";
	}
	
	@GetMapping("/searchStudentsByName")
	public String searchStudentsByName(@RequestParam("name") String name, Model model) {
	    List<Student> searchResults = studentRepository.findStudentsByName(name);
	    Integer edades = 0;
		for(Student s : searchResults) {
			edades += s.getAge();
		}
		Double edadMedia = (double) (edades / searchResults.size());

	    model.addAttribute("students", searchResults);
	    model.addAttribute("edadMedia", edadMedia);

	    return "listStudents"; 
	}
	
	@GetMapping("/filterStudentsByCourse")
	public String filterStudentsByCourse(@RequestParam("course") String course, Model model) {
	    List<Student> searchResults = studentRepository.filterStudentsByCourse(course);
	    Integer edades = 0;
		for(Student s : searchResults) {
			edades += s.getAge();
		}
		Double edadMedia = (double) (edades / searchResults.size());

	    model.addAttribute("students", searchResults);
	    model.addAttribute("edadMedia", edadMedia);

	    return "listStudents"; 
	}

}
