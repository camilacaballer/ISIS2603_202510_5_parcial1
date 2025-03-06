package uniandes.dse.examen1.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import jakarta.transaction.Transactional;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import uniandes.dse.examen1.entities.CourseEntity;
import uniandes.dse.examen1.entities.StudentEntity;
import uniandes.dse.examen1.entities.RecordEntity;
import uniandes.dse.examen1.exceptions.RepeatedCourseException;
import uniandes.dse.examen1.exceptions.RepeatedStudentException;
import uniandes.dse.examen1.exceptions.InvalidRecordException;
import uniandes.dse.examen1.repositories.CourseRepository;
import uniandes.dse.examen1.repositories.StudentRepository;
import uniandes.dse.examen1.services.CourseService;
import uniandes.dse.examen1.services.StudentService;
import uniandes.dse.examen1.services.RecordService;

@DataJpaTest
@Transactional
@Import({ RecordService.class, CourseService.class, StudentService.class })
public class RecordServiceTest {

    @Autowired
    private RecordService recordService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private StudentService studentService;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

    private String login;
    private String courseCode;

    @BeforeEach
    void setUp() throws RepeatedCourseException, RepeatedStudentException {
        CourseEntity newCourse = factory.manufacturePojo(CourseEntity.class);
        newCourse = courseService.createCourse(newCourse);
        courseCode = newCourse.getCourseCode();

        StudentEntity newStudent = factory.manufacturePojo(StudentEntity.class);
        newStudent = studentService.createStudent(newStudent);
        login = newStudent.getLogin();
    }

    /**
     * Tests the normal creation of a record for a student in a course
     */
    @Test
    void testCreateRecord() {
        // TODO

        RecordEntity newEntity = factory.manufacturePojo(RecordEntity.class);
        String login = newEntity.getStudent().getLogin();
        String courseCode = newEntity.getCourse().getCourseCode();
        Double grade = newEntity.getFinalGrade();
        String semester = newEntity.getSemester();

        try {
            RecordEntity storedEntity = recordService.createRecord(login, courseCode, grade, semester);
            RecordEntity retrieved = entityManager.find(RecordEntity.class, storedEntity.getId());

            assertEquals(newEntity.getClass(), retrieved.getClass(), "The class is not correct");
            assertEquals(newEntity.getCourse(), retrieved.getCourse(), "The course is not correct");
            assertEquals(newEntity.getFinalGrade(), retrieved.getFinalGrade(), "The final grade is not correct");
            assertEquals(newEntity.getSemester(), retrieved.getSemester(), "The semester is not correct");
            assertEquals(newEntity.getStudent(), retrieved.getStudent(), "The student is not correct");
        } catch (InvalidRecordException e) {
            fail("No exception should be thrown: " + e.getMessage());
        }
    }

    /**
     * Tests the creation of a record when the login of the student is wrong
     */
    @Test
    void testCreateRecordMissingStudent() {
        // TODO

        RecordEntity record = factory.manufacturePojo(RecordEntity.class);
        StudentEntity student = factory.manufacturePojo(StudentEntity.class);
        record.setStudent(student);

        assertThrows(InvalidRecordException.class, () -> {
            recordService.createRecord("123", "456", 5.0, "4");
        });
    }

    /**
     * Tests the creation of a record when the course code is wrong
     */
    @Test
    void testCreateInscripcionMissingCourse() {
        // TODO

        RecordEntity record = factory.manufacturePojo(RecordEntity.class);
        CourseEntity course = factory.manufacturePojo(CourseEntity.class);
        record.setCourse(course);

        assertThrows(InvalidRecordException.class, () -> {
            recordService.createRecord("123", "456", 5.0, "4");
        });
    }

    /**
     * Tests the creation of a record when the grade is not valid
     */
    @Test
    void testCreateInscripcionWrongGrade() {
        // TODO

        assertThrows(InvalidRecordException.class, () -> {
            recordService.createRecord("123", "456", 8.0 , "4");
        });
    }

    /**
     * Tests the creation of a record when the student already has a passing grade
     * for the course
     */
    @Test
    void testCreateInscripcionRepetida1() {
        // TODO

    }

    /**
     * Tests the creation of a record when the student already has a record for the
     * course, but he has not passed the course yet.
     */
    @Test
    void testCreateInscripcionRepetida2() {
        // TODO
    }
}
