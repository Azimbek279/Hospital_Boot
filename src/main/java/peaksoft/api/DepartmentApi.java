package peaksoft.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import peaksoft.model.Department;
import peaksoft.service.DepartmentService;

@Controller
@RequestMapping("/departments")
public class DepartmentApi {

    private final DepartmentService departmentService;

    @Autowired
    public DepartmentApi(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/{id}")
    public String getAllDepartments(@PathVariable Long id, Model model){
        model.addAttribute("departments",departmentService.getAllDepartment(id));
        model.addAttribute("hospitalId",id);
        return "department/departments";
    }

    @GetMapping("/{id}/newDepartment")
    public String createDepartment(@PathVariable Long id,Model model){
        model.addAttribute("newDepartment",new Department());
        model.addAttribute("hospitalId",id);
        return "department/addDepartment";
    }

    @PostMapping("/{id}/saveDepartment")
    public String saveDepartment(@ModelAttribute("newDepartment") Department department,@PathVariable("id") Long id){
        departmentService.saveDepartment(department,id);
        return "redirect:/departments/" +id;
    }

    @GetMapping("/edit/{id}")
    public String updateDepartment(@PathVariable Long id, Model model){
        Department department = departmentService.getDepartmentById(id);
        model.addAttribute("department",department);
        model.addAttribute("hospitalId",department.getHospital().getId());
        return "department/updateDepartment";
    }

    @PostMapping("/{id}/{departmentId}/update")
    public String saveUpdateDepartment(@ModelAttribute("department") Department department,
                                       @PathVariable("departmentId") Long departmentId,
                                       @PathVariable("id") Long id){
        departmentService.updateDepartment(departmentId,department);
        return "redirect:/departments/" +id;
    }

    @GetMapping("/{id}/{hosId}")
    public String deleteByDepartmentId(@PathVariable("id") Long id,
                                       @PathVariable("hosId") Long hosId){
        departmentService.deleteDepartmentById(id);
        return "redirect:/departments/" + hosId;
    }
}
