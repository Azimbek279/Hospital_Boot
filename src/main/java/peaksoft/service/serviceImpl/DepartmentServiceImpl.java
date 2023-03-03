package peaksoft.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.exception.BadRequestException;
import peaksoft.model.Appointment;
import peaksoft.model.Department;
import peaksoft.model.Doctor;
import peaksoft.model.Hospital;
import peaksoft.repository.AppointmentRepository;
import peaksoft.repository.DepartmentRepository;
import peaksoft.repository.DoctorRepository;
import peaksoft.repository.HospitalRepository;
import peaksoft.service.DepartmentService;

import java.io.IOException;
import java.util.List;
@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final HospitalRepository hospitalRepository;
    private final DoctorRepository doctorRepository;

    private final AppointmentRepository appointmentRepository;



    @Override
    public List<Department> getAllDepartment(Long departmentId) {
        return departmentRepository.getAllDepartment(departmentId);
    }

    @Override
    public void saveDepartment(Department department, Long hospitalId) {
        Hospital hos = hospitalRepository.getById(hospitalId);
        hos.addDepartment(department);
        department.setHospital(hos);
        departmentRepository.save(department);
    }

    @Override
    public Department getDepartmentById(Long id) {
        return departmentRepository.findById(id).orElseThrow(()->new BadRequestException("not Department with id=?"));
    }

    @Override
    public void deleteDepartmentById(Long id) {
        Department department = departmentRepository.getById(id);
        Hospital hospital = department.getHospital();

        List<Appointment> appointments = department.getAppointments();

        appointments.forEach(appointment -> appointment.getDepartment().setAppointments(null));

        appointments.forEach(appointment -> appointment.getDoctor().setAppointments(null));

        appointments.forEach(appointment -> appointment.getPatient().setAppointments(null));

        hospital.getAppointments().removeAll(appointments);

        for (int i = 0; i < appointments.size(); i++) {
            appointmentRepository.deleteById(appointments.get(i).getId());
        }

        for (int i = 0; i < department.getDoctors().size(); i++) {
            department.getDoctors().get(i).getDepartments().remove(department);
        }
        departmentRepository.delete(department);
    }

    @Override
    public void updateDepartment(Long departmentId, Department department) {
        Department department1 = departmentRepository.getById(departmentId);
        department1.setName(department.getName());
        departmentRepository.save(department1);
    }

    @Override
    public void AssignDepartment(Long doctorId, Long departmentId) throws IOException {
        Department department = departmentRepository.getById(departmentId);
        Doctor doctor = doctorRepository.getById(doctorId);
        if (doctor.getDepartments() != null){
            for (Department d: doctor.getDepartments()) {
                if(d.getId()==departmentId){
                    throw new IOException("Bul Department uje koshulgan");
                }
            }
        }
        department.addDoctors(doctor);
        doctor.addDepartments(department);
        departmentRepository.save(department);
        doctorRepository.save(doctor);
    }

    @Override
    public void AssignDepartmentToAppointment(Long appointmentId, Long departmentId) throws IOException {
        Department department = departmentRepository.getById(departmentId);
        Appointment appointment = appointmentRepository.getById(appointmentId);
        if (appointment.getDepartment() != null){
            for (Department d: appointment.getHospital().getDepartments()) {
                if (d.getId() ==departmentId){
                    throw  new IOException("but uje assign bolgon");
                }
            }
        }
        department.addAppointment(appointment);
        appointment.setDepartment(department);
        departmentRepository.save(department);
        appointmentRepository.save(appointment);
    }
}
