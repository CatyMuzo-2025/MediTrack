package com.espe.meditrack.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Appointment {

    private final String id;
    private final String patientName;
    private final String specialty;
    private final Double costUsd;
    private final List<String> notifyEmails;

    public Appointment(String id, String patientName, String specialty,
                       Double costUsd, List<String> notifyEmails) {
        this.id = id;
        this.patientName = patientName;
        this.specialty = specialty;
        this.costUsd = costUsd;
        // Copia defensiva en el constructor: si el llamador modifica la lista
        // original después, no afecta el estado interno del Appointment.
        this.notifyEmails = new ArrayList<>(notifyEmails);
    }

    public String getId() {
        return id;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getSpecialty() {
        return specialty;
    }

    public Double getCostUsd() {
        return costUsd;
    }

    public List<String> getNotifyEmails() {
        // Copia defensiva en el getter: se expone una lista de solo lectura
        // para que el llamador no pueda mutar la lista interna.
        return Collections.unmodifiableList(notifyEmails);
    }

    /**
     * Regla de negocio: una cita es válida si costUsd > 0 y notifyEmails no está vacía.
     */
    public boolean isValid() {
        return costUsd != null && costUsd > 0
                && notifyEmails != null && !notifyEmails.isEmpty();
    }

    @Override
    public String toString() {
        return "Appointment{id='" + id + "', patientName='" + patientName
                + "', specialty='" + specialty + "', costUsd=" + costUsd
                + ", notifyEmails=" + notifyEmails + '}';
    }
}
