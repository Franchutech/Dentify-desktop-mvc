package com.example.dentify;

import com.example.dentify.Model.Doctor;
import com.example.dentify.Model.Especialidad;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;

// ANDREA 02-06-2026
public class DoctorTest {

    @Test
    public void testGettersYSettersDoctor() {
        // INICIO EL OBJETO CON LOS DATOS INICIALES CON EL CONSTRUCTOR
        LocalDate fechaInicio = LocalDate.of(1985, 4, 12);
        Doctor d = new Doctor(101, "Dr. Alberto Ruiz", fechaInicio, "Calle Mayor 10", "COL-282801", Especialidad.CIRUGIA);

        // COMPRUEBO QUE LOS GETTERS RETORNAN LO QUE METIMOS
        assertEquals(101, d.getIdDoctor());
        assertEquals("Dr. Alberto Ruiz", d.getNombre());
        assertEquals(fechaInicio, d.getFechaNacimiento());
        assertEquals("Calle Mayor 10", d.getDireccion());
        assertEquals("COL-282801", d.getNumColegiado());
        assertEquals(Especialidad.CIRUGIA, d.getEspecialidad());

        // PRUEBO LOS SETTERS MODIFICANDO LOS VALORES DEL OBJETO
        LocalDate nuevaFecha = LocalDate.of(1990, 12, 1);
        d.setNombre("Dra. Marina Costa");
        d.setFechaNacimiento(nuevaFecha);
        d.setDireccion("Paseo Marítimo 8");
        d.setNumColegiado("COL-292906");
        d.setEspecialidad(Especialidad.GENERAL);

        // VALIDO QUE EL OBJETO SE HAYA ACTUALIZADO EN MEMORIA
        assertEquals("Dra. Marina Costa", d.getNombre());
        assertEquals(nuevaFecha, d.getFechaNacimiento());
        assertEquals("Paseo Marítimo 8", d.getDireccion());
        assertEquals("COL-292906", d.getNumColegiado());
        assertEquals(Especialidad.GENERAL, d.getEspecialidad());
    }
}
