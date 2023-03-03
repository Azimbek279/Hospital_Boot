package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import peaksoft.model.Department;

import java.util.List;
@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    @Query(value = "select d from Department d where d.hospital.id =:id")
    List<Department> getAllDepartment(Long id);
}