package com.sistemagestionestudiantes.app.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public class Student {

	@NotBlank(message = "El nombre no puede quedar vacio")
	private String name;
	
	@NotNull(message = "La edad no puede quedar vacia")
	@Positive(message = "Debes introducir un valor positivo")
	@Min(value = 0, message = "La edad debe ser mayor o igual a 0")
    @Max(value = 100, message = "La edad debe ser menor o igual a 100")
	private Integer age;
	
    @Pattern(regexp = "^(DAW1|DAW2)$", message = "El campo curso no puede quedar vacio y solo admite los valores 'DAW1' o 'DAW2'")
	private String course;

	public Student(String name, Integer age, String course) {
		this.name = name;
		this.age = age;
		this.course = course;
	}

	public Student() {
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

}
