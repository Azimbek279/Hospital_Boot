package peaksoft.service.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.exception.BadRequestException;
import peaksoft.model.Appointment;
import peaksoft.model.Doctor;
import peaksoft.model.Hospital;
import peaksoft.repository.AppointmentRepository;
import peaksoft.repository.DoctorRepository;
import peaksoft.repository.HospitalRepository;
import peaksoft.service.DoctorService;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {
    private final DoctorRepository doctorRepository;
    private final HospitalRepository hospitalRepository;
    private final AppointmentRepository appointmentRepository;

    @Override
    public List<Doctor> getAllDoctors(Long doctorId) {
        return doctorRepository.getAllDoctors(doctorId);
    }

    @Transactional
    @Override
    public void saveDoctor(Doctor doctor, Long hospitalId) {
        Hospital hospital = hospitalRepository.getById(hospitalId);
        hospital.addDoctor(doctor);
        doctor.setHospital(hospital);
        doctorRepository.save(doctor);
        doctor.getHospital().plusDoctor();
    }

    @Override
    public Doctor getDoctorById(Long id) {
        return doctorRepository.findById(id).orElseThrow(()-> new BadRequestException("not Doctor with id=?"));
    }

    @Override
    public void deleteDoctorById(Long id) {
        Doctor doctor = doctorRepository.getById(id);
        Hospital hospital = doctor.getHospital();
        List<Appointment> appointments = doctor.getAppointments();

        appointments.forEach(appointment -> appointment.getDoctor().setAppointments(null));

        appointments.forEach(appointment -> appointment.getDepartment().setAppointments(null));

        appointments.forEach(appointment -> appointment.getPatient().setAppointments(null));

        hospital.getAppointments().removeAll(appointments);

        for (int i = 0; i < appointments.size(); i++) {
            appointmentRepository.deleteById(appointments.get(i).getId());
        }
        doctor.getHospital().minusDoctor();
        doctorRepository.delete(doctor);


    }

    @Override
    public void updateDoctor(Long doctorId, Doctor doctor) {
        Doctor doctor1 = doctorRepository.getById(doctorId);
        doctor1.setFirstName(doctor.getFirstName());
        doctor1.setLastName(doctor.getLastName());
        doctor1.setEmail(doctor.getEmail());
        doctor1.setPosition(doctor.getPosition());
        doctorRepository.save(doctor1);
    }

    @Override
    public void assignDoctor(Long appointmentId, Long doctorId) throws IOException {
        Doctor doctor = doctorRepository.getById(doctorId);
        Appointment appointment = appointmentRepository.getById(appointmentId);
        if(appointment.getDoctor()!=null){
            for (Doctor d: appointment.getHospital().getDoctors()) {
                if(d.getId()==doctorId){
                    throw new IOException("bul ujassign bolgon");
                }
            }
        }
        doctor.addAppointments(appointment);
        appointment.setDoctor(doctor);
        doctorRepository.save(doctor);
        appointmentRepository.save(appointment);
    }
}
