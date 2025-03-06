package uniandes.dse.examen1.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import uniandes.dse.examen1.repositories.CourseRepository;
import uniandes.dse.examen1.repositories.StudentRepository;
import uniandes.dse.examen1.repositories.RecordRepository;
import uniandes.dse.examen1.entities.CourseEntity;
import uniandes.dse.examen1.entities.RecordEntity;
import uniandes.dse.examen1.entities.StudentEntity;

@Slf4j
@Service
public class StatsService {

    @Autowired
    StudentRepository estudianteRepository;

    @Autowired
    CourseRepository cursoRepository;

    @Autowired
    RecordRepository inscripcionRepository;

    public Double calculateStudentAverage(String login) {
        // TODO
        Optional<StudentEntity> estudiante = estudianteRepository.findByLogin(login);

        double promedio = 0;
        double numClases = estudiante.get().getRecords().size();

        for (RecordEntity record: estudiante.get().getRecords()){

            double nuevaSuma = promedio + record.getFinalGrade();
            promedio = nuevaSuma/numClases;
            
        }

        return promedio;
    }

    public Double calculateCourseAverage(String courseCode) {
        // TODO

        Optional<CourseEntity> curso = cursoRepository.findByCourseCode(courseCode);

        double promedio = 0;

        for (StudentEntity student: curso.get().getStudents()){

            
        }

        return promedio;
    }

}
