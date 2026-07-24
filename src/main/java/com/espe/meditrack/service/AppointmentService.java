package com.espe.meditrack.service;

import com.espe.meditrack.model.Appointment;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Collections;

@Service
public class AppointmentService {

    public Flux<Appointment> getValidAppointments() {
        return applyValidationPipeline(sourceAppointments());
    }

    Flux<Appointment> applyValidationPipeline(Flux<Appointment> source) {
        return source
                // filter: deja pasar solo las citas que cumplen la regla de negocio
                // (costUsd > 0 y notifyEmails no vacía).
                .filter(Appointment::isValid)
                // map: transforma cada cita válida, normalizando la especialidad a mayúsculas.
                .map(a -> new Appointment(a.getId(), a.getPatientName(),
                        a.getSpecialty().toUpperCase(), a.getCostUsd(), a.getNotifyEmails()))
                // defaultIfEmpty: si el filtro deja el flujo vacío, se emite una cita
                // genérica en lugar de completar sin elementos.
                .defaultIfEmpty(new Appointment("GEN", "Sin citas disponibles",
                        "GENERAL", 0.0, Collections.emptyList()));
    }

    private Flux<Appointment> sourceAppointments() {
        return Flux.just(
                new Appointment("A1", "Dra Elena Lopez", "cardiologia", 50.0,
                        Arrays.asList("elenap@gmail.com")),
                new Appointment("A2", "Dra Gabriela Lopez", "pediatria", 30.0,
                        Arrays.asList("gabrielal@gmail.com", "tutor@mail.com")),
                new Appointment("A3", "Dr Carlos Ruiz", "dermatologia", 0.0,   // inválida: costo 0
                        Arrays.asList("carlosr@gmail.com")),
                new Appointment("A4", "Dra Caty Muzo", "cardiologia", 40.0,    // inválida: sin emails
                        Collections.emptyList()),
                new Appointment("A5", "Dr Edison Chiluiza", "traumatologia", 60.0,
                        Arrays.asList("edisonc@gmail.com"))
        );
    }

    /**
     * Busca una cita por id dentro del flujo. Si no existe, se resuelve el
     * caso con switchIfEmpty(Mono.error(...)) en vez de bloquear el hilo
     * o evaluar un if sobre un valor ya extraído del flujo.
     */
    public Mono<Appointment> findById(String id) {
        return getValidAppointments()
                .filter(a -> a.getId().equals(id))
                .next()
                .switchIfEmpty(Mono.error(new AppointmentNotFoundException(id)));
    }
}
