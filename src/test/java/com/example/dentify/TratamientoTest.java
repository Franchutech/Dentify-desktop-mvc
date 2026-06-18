package com.example.dentify;

import com.example.dentify.Model.Tratamiento;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TratamientoTest {

    @Test
    public void testGettersYSettersTratamiento() {
        // INICIO EL OBJETO CON LOS DATOS INICIALES CON EL CONSTRUCTOR
        Tratamiento t = new Tratamiento(1, "Limpieza Bucal", "Eliminación de sarro y placa.");

        // COMPRUEBO QUE LOS GETTERS RETORNAN LO QUE METIMOS
        assertEquals("Limpieza Bucal", t.getNombre());
        assertEquals("Eliminación de sarro y placa.", t.getDescripcion());

        // PRUEBO LOS SETTERS MODIFICANDO LOS VALORES DEL OBJETO
        t.setNombre("Endodoncia");
        t.setDescripcion("Tratamiento del nervio.");

        // VALIDO QUE EL OBJETO SE HAYA ACTUALIZADO EN MEMORIA
        assertEquals("Endodoncia", t.getNombre());
        assertEquals("Tratamiento del nervio.", t.getDescripcion());
    }
}