package peaksoft.api;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import peaksoft.model.Appointment;
import peaksoft.model.Department;
import peaksoft.model.Doctor;
import peaksoft.model.Patient;
import peaksoft.service.AppointmentService;
import peaksoft.service.DepartmentService;
import peaksoft.service.DoctorService;
import peaksoft.service.PatientService;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/appointments")
public class AppointmentApi {

    private final AppointmentService appointmentService;
    private final PatientService patientService;

    private final DepartmentService departmentService;

    private final DoctorService doctorService;



    @GetMapping("/{id}")
    public String getAllAppointment(@PathVariable Long id, Model model,
                                    @ModelAttribute("patient")Patient patient,
                                    @ModelAttribute("doctor")Doctor doctor,
                                    @ModelAttribute("department")Department department,
                                    @ModelAttribute("appointment")Appointment appointment){
        model.addAttribute("departments",departmentService.getAllDepartment(id));
        model.addAttribute("doctors",doctorService.getAllDoctors(id));
        model.addAttribute("patients",patientService.getAllPatient(id));
        model.addAttribute("appointments",appointmentService.getAllAppointment(id));
        model.addAttribute("hospitalId",id);
        return "appointment/appointments";
    }

    @GetMapping("/{id}/newAppointment")
    public String createAppointment(@PathVariable Long id,Model model){
        model.addAttribute("newAppointment",new Appointment());
        model.addAttribute("hospitalId",id);
        return "appointment/addAppointment";
    }

    @PostMapping("/{id}/saveAppointment")
    public String saveAppointment(@ModelAttribute("newAppointment")Appointment appointment,
                                  @PathVariable("id") Long id){
        appointmentService.saveAppointment(appointment,id);
        return "redirect:/appointments/"+id;
    }

    @GetMapping("/edit/{id}")
    public String updateAppointment(@PathVariable Long id,Model model){
        Appointment appointment = appointmentService.getAppointmentById(id);
        model.addAttribute("appointment",appointment);
        model.addAttribute("hospitalId",appointment.getHospital().getId());
        return "appointment/updateAppointment";
    }

    @PostMapping("/{id}/{appointmentId}/update")
    public String saveUpdateAppointment(@ModelAttribute("appointment") Appointment appointment,
                                        @PathVariable("id")Long id,
                                        @PathVariable("appointmentId")Long appointmentId){
        appointmentService.updateAppointment(appointmentId,appointment);
        return "redirect:/appointments/"+id;
    }

    @GetMapping("/{id}/{hosId}")
    public String deleteAppointmentById(@PathVariable("id") Long id,
                                        @PathVariable("hosId")Long hosId){
        appointmentService.deleteAppointmentById(id);
        return "redirect:/appointments/"+hosId;
    }

    @PostMapping("{appointmentId}/assignPatient")
    public String assignPatient(@PathVariable("appointmentId")Long appointmentId,
                                @ModelAttribute("patient")Patient patient) throws IOException {
        Long id = patient.getId();
        patientService.assignPatient(appointmentId,id);
        return "redirect:/appointments/"+appointmentId;
    }

    @PostMapping("{appointmentId}/assignDoctor")
    public String assignDoctor(@PathVariable("appointmentId")Long appointmentId,
                               @ModelAttribute("doctor") Doctor doctor) throws IOException {
        Long id = doctor.getId();
        doctorService.assignDoctor(appointmentId,id);
        return "redirect:/appointments/"+appointmentId;
    }

    @PostMapping("{appointmentId}/assignDepartment")
    public String assignDepartmentToAppointment(@PathVariable("appointmentId")Long appointmentId,
                               @ModelAttribute("department") Department department) throws IOException {
        Long id = department.getId();
        departmentService.AssignDepartmentToAppointment(appointmentId,id);
        return "redirect:/appointments/"+appointmentId;
    }
}
