package peaksoft.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.exception.BadRequestException;
import peaksoft.exception.NotFoundException;
import peaksoft.model.Appointment;
import peaksoft.model.Hospital;
import peaksoft.repository.AppointmentRepository;
import peaksoft.repository.HospitalRepository;
import peaksoft.service.AppointmentService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final HospitalRepository hospitalRepository;

    @Override
    public List<Appointment> getAllAppointment(Long appointmentId){
        return appointmentRepository.getAllAppointment(appointmentId);
    }

    @Override
    public void saveAppointment(Appointment appointment,Long hospitalId) {
        Appointment appointment1 = new Appointment();
        if (hospitalRepository.getById(hospitalId) == null){
            throw new NotFoundException(String.format("Hospital with id %d not found", hospitalId));
        }
        Hospital hospital = hospitalRepository.getById(hospitalId);
        appointment1.setHospital(hospital);
        appointment1.setDate(appointment.getDate());
        appointmentRepository.save(appointment1);
    }

    @Override
    public Appointment getAppointmentById(Long id) {
        return appointmentRepository.findById(id).orElseThrow(()-> new BadRequestException("not Appointment with id=?"));
    }

    @Override
    public void deleteAppointmentById(Long id) {
        appointmentRepository.deleteById(id);
    }

    @Override
    public void updateAppointment(Long appointmentId, Appointment appointment) {
        Appointment appointment1 = appointmentRepository.getById(appointmentId);
        appointment1.setDate(appointment.getDate());
        appointmentRepository.save(appointment1);
    }
}
