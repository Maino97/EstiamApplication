package com.amigoscode.demo.Professeur;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.amigoscode.demo.EmailValidator;
import com.amigoscode.demo.exception.ApiRequestException;
import com.amigoscode.demo.professeur.Professeur;
import com.amigoscode.demo.professeur.ProfesseurCourse;
import com.amigoscode.demo.professeur.ProfesseurDataAccessService;

public class ProfesseurSerivce {
	private final ProfesseurDataAccessService professeurDataAccessService;
    private final EmailValidator emailValidator;

    @Autowired
    public ProfesseurService(ProfesseurDataAccessService professeurDataAccessService,
                          EmailValidator emailValidator) {
        this.professeurDataAccessService = professeurDataAccessService;
        this.emailValidator = emailValidator;
    }

    List<Professeur> getAllProfesseurs() {
        return professeurDataAccessService.selectAllProfesseurs();
    }

    void addNewProfesseur(Professeur professeur) {
        addNewProfesseur(null, professeur);
    }

    void addNewProfesseur(UUID professeurId, Professeur professeur) {
        UUID newProfesseurId = Optional.ofNullable(professeurId)
                .orElse(UUID.randomUUID());

        if (!emailValidator.test(professeur.getEmail())) {
            throw new ApiRequestException(professeur.getEmail() + " is not valid");
        }

        if (professeurDataAccessService.isEmailTaken(professeur.getEmail())) {
            throw new ApiRequestException(professeur.getEmail() + " is taken");
        }

        professeurDataAccessService.insertProfesseur(newprofesseurId, professeur);
    }

    List<ProfesseurCourse> getAllCoursesForProfesseur(UUID studentId) {
        return professeurDataAccessService.selectAllProfesseurCourses(ProfesseurId);
    }

    public void updateProfesseur(UUID professeurId, Professeur professeur) {
        Optional.ofNullable(professeur.getEmail())
                .ifPresent(email -> {
                    boolean taken = ProfesseurDataAccessService.selectExistsEmail(professeurId, email);
                    if (!taken) {
                        ProfesseurDataAccessService.updateEmail(professeurId, email);
                    } else {
                        throw new IllegalStateException("Email already in use: " + professeur.getEmail());
                    }
                });

        Optional.ofNullable(professeur.getFirstName())
                .filter(fistName -> !StringUtils.isEmpty(fistName))
                .map(StringUtils::capitalize)
                .ifPresent(firstName -> professeurDataAccessService.updateFirstName(professeurId, firstName));

        Optional.ofNullable(professeur.getLastName())
                .filter(lastName -> !StringUtils.isEmpty(lastName))
                .map(StringUtils::capitalize)
                .ifPresent(lastName -> professeurDataAccessService.updateLastName(professeurId, lastName));
    }

    void deleteProfesseur(UUID professeurId) {
        professeurDataAccessService.deleteProfesseurById(professeurId);
    }
}
