package com.espe.meditrack.service;

import com.espe.meditrack.model.Appointment;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.Collections;

public class AppointmentServiceTest {

    @Test
    public void getValidAppointments_debeEmitirSoloLasTresValidas() {
        // Arrange
        AppointmentService service = new AppointmentService();

        // Act
        Flux<Appointment> flujo = service.getValidAppointments();

        // Assert
        StepVerifier.create(flujo)
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test
    public void applyValidationPipeline_siTodasLasCitasSonInvalidas_debeEmitirUnaSolaGenerica() {
        // Arrange
        AppointmentService service = new AppointmentService();
        Flux<Appointment> todasInvalidas = Flux.just(
                new Appointment("X1", "Test1", "general", 0.0, Arrays.asList("a@gmail.com")),  // costo 0
                new Appointment("X2", "Test2", "general", 10.0, Collections.emptyList())       // sin emails
        );

        // Act
        Flux<Appointment> resultado = service.applyValidationPipeline(todasInvalidas);

        // Assert
        StepVerifier.create(resultado)
                .expectNextMatches(a -> a.getId().equals("GEN"))
                .verifyComplete();
    }

    @Test
    public void findById_conIdInexistente_debeTerminarEnError() {
        // Arrange
        AppointmentService service = new AppointmentService();

        // Act
        Mono<Appointment> resultado = service.findById("NO_EXISTE");

        // Assert
        StepVerifier.create(resultado)
                .expectError(AppointmentNotFoundException.class)
                .verify();
    }
}

