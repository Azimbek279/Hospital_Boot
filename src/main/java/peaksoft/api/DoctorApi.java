package peaksoft.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import peaksoft.model.Department;
import peaksoft.model.Doctor;
import peaksoft.service.DepartmentService;
import peaksoft.service.DoctorService;

import java.io.IOException;

@Controller
@RequestMapping("/doctors")
public class DoctorApi {

    private final DoctorService doctorService;

    private final DepartmentService departmentService;

    @Autowired
    public DoctorApi(DoctorService doctorService, DepartmentService departmentService) {
        this.doctorService = doctorService;
        this.departmentService = departmentService;
    }

    @GetMapping("/{id}")
    public String getAllDoctor(@PathVariable Long id, Model model,
                               @ModelAttribute("department") Department department){
        model.addAttribute("departments",departmentService.getAllDepartment(id));
        model.addAttribute("doctors",doctorService.getAllDoctors(id));
        model.addAttribute("hospitalId",id);
        return "doctor/doctors";
    }

    @GetMapping("/{id}/newDoctor")
    public String createDoctor(@PathVariable Long id, Model model){
        model.addAttribute("newDoctor",new Doctor());
        model.addAttribute("hospitalId",id);
        return "doctor/addDoctor";
    }

    @PostMapping("/{id}/saveDoctor")
    public String saveDoctor(@ModelAttribute("newDoctor") Doctor doctor,
                             @PathVariable("id") Long id){
        doctorService.saveDoctor(doctor,id);
        return "redirect:/doctors/" + id;
    }

    @GetMapping("/edit/{id}")
    public String updateDoctor(@PathVariable Long id,Model model){
        Doctor doctor = doctorService.getDoctorById(id);
        model.addAttribute("doctor",doctor);
        model.addAttribute("hospitalId",doctor.getHospital().getId());
        return "doctor/updateDoctor";
    }

    @PostMapping("{id}/{doctorId}/update")
    public String updateSaveDoctor(@PathVariable("doctorId") Long doctorId,
                                   @PathVariable("id")Long id,
                                   @ModelAttribute("doctor") Doctor doctor){
        doctorService.updateDoctor(doctorId,doctor);
        return "redirect:/doctors/" +id;
    }

    @GetMapping("/{id}/{hosId}")
    public String deleteDoctorById(@PathVariable("id") Long id,
                                   @PathVariable("hosId")Long hosId){
        doctorService.deleteDoctorById(id);
        return "redirect:/doctors/" + hosId;
    }

    @PostMapping("{doctorId}/assignDepartment")
    public String assignDepartment(@PathVariable("doctorId")Long doctorId,
                                   @ModelAttribute("department")Department department) throws IOException {
        Long id = department.getId();
        departmentService.AssignDepartment(doctorId,id);
        return "redirect:/doctors/"+doctorId;
    }
}
