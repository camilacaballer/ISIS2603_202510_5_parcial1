package uniandes.dse.examen1.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import uniandes.dse.examen1.entities.CourseEntity;
import uniandes.dse.examen1.entities.StudentEntity;
import uniandes.dse.examen1.entities.RecordEntity;
import uniandes.dse.examen1.exceptions.InvalidRecordException;
import uniandes.dse.examen1.repositories.CourseRepository;
import uniandes.dse.examen1.repositories.StudentRepository;
import uniandes.dse.examen1.repositories.RecordRepository;

@Slf4j
@Service
public class RecordService {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    RecordRepository recordRepository;

    public RecordEntity createRecord(String loginStudent, String courseCode, Double grade, String semester)
            throws InvalidRecordException {
    
        // TODO

        if (studentRepository.findByLogin(loginStudent).isEmpty()){
            throw new InvalidRecordException("El estudiante debe existir");
        }

        if (courseRepository.findByCourseCode(courseCode).isEmpty()){
            throw new InvalidRecordException("El curso debe existir");
        }

        if (grade < 1.5 || grade > 5.0){
            throw new InvalidRecordException("La nota debe ser un numero entre 1.5 y 5.0");
        }

        RecordEntity newRecord = new RecordEntity();

        newRecord.setStudent(studentRepository.findByLogin(loginStudent).get());
        newRecord.setCourse(courseRepository.findByCourseCode(courseCode).get());
        newRecord.setSemester(semester);
        newRecord.setFinalGrade(grade);
        
        return recordRepository.save(newRecord);

    }
}
