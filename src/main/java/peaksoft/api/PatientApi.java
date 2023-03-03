package peaksoft.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import peaksoft.model.Patient;
import peaksoft.model.enums.Gender;
import peaksoft.service.PatientService;

@Controller
@RequestMapping("/patients")
public class PatientApi {

    private final PatientService patientService;

    @Autowired
    public PatientApi(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/{id}")
    public String getAllPatients(Model model,@PathVariable Long id){
        model.addAttribute("patients",patientService.getAllPatient(id));
        model.addAttribute("hospitalId",id);
        return "patient/patients";
    }

    @GetMapping("/{id}/newPatient")
    public String createPatient(@PathVariable Long id,Model model){
        model.addAttribute("newPatient",new Patient());
        model.addAttribute("genderMale", Gender.MALE);
        model.addAttribute("genderFeMale",Gender.FEMALE);
        model.addAttribute("hospitalId",id);
        return "patient/addPatient";
    }

    @PostMapping("/{id}/savePatient")
    public String savePatient(@ModelAttribute("newPatient") Patient patient,
                              @PathVariable("id") Long id){
        patientService.savePatient(patient,id);
        return "redirect:/patients/" +id;
    }

    @GetMapping("/edit/{id}")
    public String updatePatient(@PathVariable Long id,Model model){
        model.addAttribute("genderMale",Gender.MALE);
        model.addAttribute("genderFeMale",Gender.FEMALE);
        Patient patient = patientService.getPatientById(id);
        model.addAttribute("patient",patient);
        model.addAttribute("hospitalId",patient.getHospital().getId());
        return "patient/updatePatient";
    }

    @PostMapping("/{id}/{patientId}/update")
    public String updateSavePatient(@PathVariable("id")Long id,
                                    @PathVariable("patientId")Long patientId,
                                    @ModelAttribute("patient")Patient patient){
        patientService.updatePatient(patientId,patient);
        return "redirect:/patients/"+id;
    }

    @GetMapping("/{id}/{hosId}")
    public String deleteByPatientId(@PathVariable("id")Long id,
                                    @PathVariable("hosId")Long hosId){
        patientService.deletePatientById(id);
        return "redirect:/patients/"+hosId;
    }
}
