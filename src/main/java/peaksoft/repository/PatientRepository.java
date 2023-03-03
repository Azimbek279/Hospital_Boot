package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import peaksoft.model.Patient;

import java.util.List;
@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    @Query(value = "select p from Patient p where p.hospital.id =:id")
    List<Patient> getAllPatient(Long id);
}