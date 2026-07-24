package com.espe.meditrack.model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

public class AppointmentTest {

    @Test
    public void getters_conDatosValidos_debenDevolverLoRecibidoEnElConstructor() {
        // Arrange
        List<String> emails = new ArrayList<>();
        emails.add("test@gmail.com");
        Appointment appointment = new Appointment("A1", "Dra Elena Lopez", "cardiologia", 50.0, emails);

        // Act
        // (no hay acción adicional: se prueban directamente los getters)

        // Assert
        assertEquals("A1", appointment.getId());
        assertEquals("Dra Elena Lopez", appointment.getPatientName());
        assertEquals("cardiologia", appointment.getSpecialty());
        assertEquals(50.0, appointment.getCostUsd(), 0.001);
        assertEquals(emails, appointment.getNotifyEmails());
    }

    @Test
    public void constructor_siSeModificaListaOriginalDespues_noDebeAfectarElEstadoInterno() {
        // Arrange
        List<String> emailsOriginales = new ArrayList<>();
        emailsOriginales.add("test@gmail.com");
        Appointment appointment = new Appointment("A1", "Dra Elena Lopez", "cardiologia", 50.0, emailsOriginales);

        // Act: se modifica la lista original DESPUES de crear el objeto
        emailsOriginales.add("otro@gmail.com");

        // Assert: el tamaño interno no cambió (copia defensiva en constructor)
        // y getNotifyEmails() no es la misma referencia que la lista original (copia defensiva en getter)
        assertEquals(1, appointment.getNotifyEmails().size());
        assertNotSame(emailsOriginales, appointment.getNotifyEmails());
    }
}
