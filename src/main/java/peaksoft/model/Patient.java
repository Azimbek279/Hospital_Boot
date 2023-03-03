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
@Entity
@Table(name = "patients")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "patient_id_gen")
    @SequenceGenerator(name = "patient_id_gen",
            sequenceName = "patient_seq",
            allocationSize = 1)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;


    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "gender")
    private String gender;

    @Column(name = "email",unique = true)
    private String email;

    @ManyToOne(cascade = {REFRESH,MERGE,PERSIST})
    private Hospital hospital;

    @OneToMany(cascade = ALL,fetch = FetchType.EAGER,mappedBy = "patient")
    private List<Appointment> appointments = new ArrayList<>();

    public void addAppointment(Appointment appointment){
        if(appointments==null){
            appointments = new ArrayList<>();
        }
        appointments.add(appointment);
    }


}
