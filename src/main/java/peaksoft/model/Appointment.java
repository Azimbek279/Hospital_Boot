package peaksoft.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

import static jakarta.persistence.CascadeType.*;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "appointments")
@Entity
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "appointment_seq")
    @SequenceGenerator(name = "appointment_seq",
            sequenceName = "appointment_seq",
            allocationSize = 1)
    private Long id;

    @Column(name = "date",unique = true)
    private LocalDate date;

    @ManyToOne(cascade = {REFRESH,MERGE,PERSIST,DETACH},fetch = FetchType.LAZY)
    private Patient patient;


    @ManyToOne(cascade = {REFRESH,MERGE,PERSIST,DETACH},fetch = FetchType.LAZY)
    private Doctor doctor;


    @ManyToOne(cascade = {REFRESH,MERGE,PERSIST,DETACH},fetch = FetchType.LAZY)

    private Department department;

    @ManyToOne(cascade = {REFRESH,PERSIST,DETACH,MERGE},fetch = FetchType.LAZY)
    private Hospital hospital;

}
