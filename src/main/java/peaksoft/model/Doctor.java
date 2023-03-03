package peaksoft.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.*;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "doctors")
@Entity
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "doctors_seq")
    @SequenceGenerator(name = "doctors_seq",
            sequenceName = "doctors_seq",
            allocationSize = 1)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "position")
    private String position;

    @Column(name = "email")
    private String email;

    @OneToMany(cascade = {REFRESH,MERGE,PERSIST,DETACH,REMOVE},fetch = FetchType.EAGER,mappedBy = "doctor")
    private List<Appointment> appointments = new ArrayList<>();

    public void addAppointments(Appointment appointment){
        if(appointments==null){
            appointments = new ArrayList<>();
        }
        appointments.add(appointment);
    }

    @ManyToMany(cascade = {REFRESH,MERGE,DETACH},fetch = FetchType.EAGER)
    private List<Department> departments = new ArrayList<>();


    public void addDepartments(Department department){
        if (departments==null){
            departments = new ArrayList<>();
        }
        departments.add(department);
    }

    @ManyToOne(cascade = {REFRESH,MERGE,DETACH},fetch = FetchType.EAGER)
    private Hospital hospital;

}
