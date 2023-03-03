package peaksoft.service;


import peaksoft.model.Patient;

import java.io.IOException;
import java.util.List;

public interface PatientService {
    List<Patient> getAllPatient(Long patientId);

    void savePatient(Patient patient, Long hospitalId);

    Patient getPatientById(Long id);

    void deletePatientById(Long id);

    void updatePatient(Long patientId,Patient patient);

    void assignPatient(Long appointmentId,Long patientId) throws IOException;
}
