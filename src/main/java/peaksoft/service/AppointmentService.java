package peaksoft.service;

import peaksoft.model.Appointment;

import java.util.List;

public interface AppointmentService {
    List<Appointment> getAllAppointment(Long appointmentId);

    void saveAppointment(Appointment appointment,Long hospitalId);

    Appointment getAppointmentById(Long id);

    void deleteAppointmentById(Long id);

    void updateAppointment(Long appointmentId,Appointment appointment);
}
