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
@Table(name = "departments")
@Entity
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "department_seq")
    @SequenceGenerator(name = "department_seq",
            sequenceName = "department_seq",
            allocationSize = 1)
    private Long id;

    @Column(name = "name",unique = true)
    private String name;

    @ManyToMany(cascade = {REFRESH,MERGE,DETACH},fetch = FetchType.EAGER,mappedBy = "departments")
    private List<Doctor> doctors = new ArrayList<>();


    public void addDoctors(Doctor doctor){
        if (doctors == null){
            doctors = new ArrayList<>();
        }
        doctors.add(doctor);
    }

    @ManyToOne(cascade = {REFRESH,PERSIST,MERGE,DETACH},fetch = FetchType.EAGER)
    private Hospital hospital;

    @OneToMany(cascade = {REFRESH,MERGE,DETACH,PERSIST,REMOVE},fetch = FetchType.EAGER,mappedBy = "department")
    private List<Appointment> appointments = new ArrayList<>();

    public void addAppointment(Appointment appointment){
        if (appointments==null){
            appointments = new ArrayList<>();
        }
        appointments.add(appointment);
    }


}
