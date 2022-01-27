package com.amigoscode.demo.Professeur;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.amigoscode.demo.Professeur.Professeur;
import com.amigoscode.demo.student.StudentCourse;

public class ProfesseurDataAccessService {
	 private final JdbcTemplate jdbcTemplate;

	    @Autowired
	    public ProfesseurDataAccessService(JdbcTemplate jdbcTemplate) {
	        this.jdbcTemplate = jdbcTemplate;
	    }

	    List<Professeur> selectAllProfesseurs() {
	        String sql = "" +
	                "SELECT " +
	                " professeur_id, " +
	                " first_name, " +
	                " last_name, " +
	                " email, " +
	                " gender " +
	                "FROM student";

	        return jdbcTemplate.query(sql, mapProfesseurFomDb());
	    }

	    int insertProfesseur(UUID professeurId, Professeur professeur) {
	        String sql = "" +
	                "INSERT INTO professeur (" +
	                " professeur_id, " +
	                " first_name, " +
	                " last_name, " +
	                " email, " +
	                " gender) " +
	                "VALUES (?, ?, ?, ?, ?::gender)";
	        return jdbcTemplate.update(
	                sql,
	                professeur.getProfesseurId(),
	                professeur.getFirstName(),
	                professeur.getLastName(),
	                professeur.getEmail(),
	                professeur.getGender().name().toUpperCase()
	        );
	    }

	    @SuppressWarnings("ConstantConditions")
	    boolean isEmailTaken(String email) {
	        String sql = "" +
	                "SELECT EXISTS ( " +
	                " SELECT 1 " +
	                " FROM professeur " +
	                " WHERE email = ?" +
	                ")";
	        return jdbcTemplate.queryForObject(
	                sql,
	                new Object[]{email},
	                (resultSet, i) -> resultSet.getBoolean(1)
	        );
	    }

	    List<professeur>> selectAllProfesseur(UUID professeurId) {
	        String sql = "" +
	                "SELECT " +
	                " professeur.professeur_id, " +
	                " course.course_id, " +
	                " course.name, " +
	                " course.description," +
	                " course.department," +
	                " course.teacher_name," +
	                " profeseeur_course.start_date, " +
	                " professeur_course.end_date, " +
	                " professeur_course.grade " +
	                "FROM professeur " +
	                "JOIN professeur_course USING (professeur_id) " +
	                "JOIN course         USING (course_id) " +
	                "WHERE professeur.professeur_id = ?";
	        return jdbcTemplate.query(
	                sql,
	                new Object[]{professeurId},
	                mapPerofesseurCourseFromDb()
	        );
	    }


	    private RowMapper<Professeur> mapProfesseurFomDb() {
	        return (resultSet, i) -> {
	            String professeurIdStr = resultSet.getString("professeur_id");
	            UUID professeurId = UUID.fromString(professeurIdStr);

	            String firstName = resultSet.getString("first_name");
	            String lastName = resultSet.getString("last_name");
	            String email = resultSet.getString("email");

	            String genderStr = resultSet.getString("gender").toUpperCase();
	            Professeur.Gender gender = Professeur.Gender.valueOf(genderStr);
	            return new Professeur(
	                    professeurId,
	                    firstName,
	                    lastName,
	                    email,
	                    gender
	            );
	        };
	    }

	    int updateEmail(UUID professeurId, String email) {
	        String sql = "" +
	                "UPDATE professeur " +
	                "SET email = ? " +
	                "WHERE professeur_id = ?";
	        return jdbcTemplate.update(sql, email, professeurId);
	    }

	    int updateFirstName(UUID professeurId, String firstName) {
	        String sql = "" +
	                "UPDATE professeur " +
	                "SET first_name = ? " +
	                "WHERE student_id = ?";
	        return jdbcTemplate.update(sql, firstName, professeurId);
	    }

	    int updateLastName(UUID professeurId, String lastName) {
	        String sql = "" +
	                "UPDATE professeur " +
	                "SET last_name = ? " +
	                "WHERE professeur_id = ?";
	        return jdbcTemplate.update(sql, lastName, professeurId);
	    }

	    @SuppressWarnings("ConstantConditions")
	    boolean selectExistsEmail(UUID professeurId, String email) {
	        String sql = "" +
	                "SELECT EXISTS ( " +
	                "   SELECT 1 " +
	                "   FROM professeur " +
	                "   WHERE professeur_id <> ? " +
	                "    AND email = ? " +
	                ")";
	        return jdbcTemplate.queryForObject(
	                sql,
	                new Object[]{professeurId, email},
	                (resultSet, columnIndex) -> resultSet.getBoolean(1)
	        );
	    }

	    int deleteProfesseurById(UUID professeurId) {
	        String sql = "" +
	                "DELETE FROM professur " +
	                "WHERE professeur_id = ?";
	        return jdbcTemplate.update(sql, professeurId);
	    }
	}
