package peaksoft.service;

import peaksoft.model.Hospital;

import java.util.List;

public interface HospitalService {

    List<Hospital> getAllHospitals();

    void saveHospital(Hospital hospital);

    Hospital getHospitalById(Long id);

    void deleteHospitalById(Long id);

    void updateHospital(Hospital hospital);
}
