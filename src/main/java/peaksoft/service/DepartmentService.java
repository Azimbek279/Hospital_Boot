package peaksoft.service;

import peaksoft.model.Department;

import java.io.IOException;
import java.util.List;

public interface DepartmentService {

    List<Department> getAllDepartment(Long departmentId);

    void saveDepartment(Department department, Long hospitalId);

    Department getDepartmentById(Long id);

    void deleteDepartmentById(Long id);

    void updateDepartment(Long departmentId,Department department);

    void AssignDepartment(Long doctorId,Long departmentId) throws IOException;

    void AssignDepartmentToAppointment(Long appointmentId,Long departmentId) throws IOException;
}
