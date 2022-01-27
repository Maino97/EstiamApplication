package com.amigoscode.demo.Professeur;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.amigoscode.demo.professeur.Professeur;
import com.amigoscode.demo.professeur.ProfesseurCourse;
import com.amigoscode.demo.professeur.professeurService;

public class ProfesseurController {
	 private final ProfesseurService ProfesseurService;

	    @Autowired
	    public ProfesseurController(ProfesseurService professeurService) {
	        this.professeurService = professeurService;
	    }

	    @GetMapping
	    public List<Student> getAllProfesseurs() {
	        return professeurService.getAllProfesseur();
	    }

	    @GetMapping(path = "{professeurId}/courses")
	    public List<ProfesseurCourse> getAllCoursesForProfesseur(
	            @PathVariable("professeurId") UUID professeurId) {
	        return professeurService.getAllCoursesForProfesseur(professeurId);
	    }

	    @PostMapping
	    public void addNewProfesseur(@RequestBody @Valid Professeur professeur) {
	        professeurService.addNewProfesseur(professeur);
	    }

	    @PutMapping(path = "{professeurId}")
	    public void updateProfesseur(@PathVariable("professeurId") UUID ptofesseurId,
	                              @RequestBody Professeur professeur) {
	        ProfesseurService.updateStudent(professeurId, professeur);
	    }

	    @DeleteMapping("{professeurId}")
	    public void deleteProfesseur(@PathVariable("professeurId") UUID professeurId) {
	        professeurService.deleteProfesseur(professeurId);
	    }

	}