package com.amigoscode.demo.Professeur;

import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.amigoscode.demo.Professeur.Professeur.Gender;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Professeur {
    private final UUID ProfesseurId;

    @NotBlank
    private final String firstName;

    @NotBlank
    private final String lastName;

    @NotBlank
    private final String email;

    @NotNull
    private final Gender gender;

    public Professeur(@JsonProperty("ProfesseurId") UUID ProfesseurId,
                   @JsonProperty("firstName") String firstName,
                   @JsonProperty("lastName") String lastName,
                   @JsonProperty("email") String email,
                   @JsonProperty("gender") Gender gender) {
        this.ProfesseurId = ProfesseurId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
    }

    public UUID getProfesseurId() {
        return ProfesseurId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public Gender getGender() {
        return gender;
    }

    @Override
    public String toString() {
        return "Professeur{" +
                "studentId=" + ProfesseurId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", gender=" + gender +
                '}';
    }

    enum Gender {
        MALE, FEMALE
    }
}



