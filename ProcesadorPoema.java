package modelo;

import excepciones.ErrorCargaPoemaException;
import excepciones.PalabraMuyCortaException;
import excepciones.PalabraNoEncontradaException;
import excepciones.PosicionPoemaInvalidaException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ProcesadorPoema {
    private String[] palabras;

    public void cargarPoema(String rutaArchivo) throws ErrorCargaPoemaException {
        StringBuilder contenido = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                contenido.append(linea).append(" ");
            }
        } catch (IOException e) {
            throw new ErrorCargaPoemaException("Error al intentar procesar el archivo del poema", e);
        }

        String textoLimpio = contenido.toString().replaceAll("[,.]", "").trim();
        if (!textoLimpio.isEmpty()) {
            this.palabras = textoLimpio.split("\\s+");
        } else {
            this.palabras = new String[0];
        }
    }

    public String obtenerPalabraPorPosicion(int indice) 
            throws PosicionPoemaInvalidaException, PalabraMuyCortaException {
        try {
            String palabra = palabras[indice];
            if (palabra.length() < 4) {
                throw new PalabraMuyCortaException("La palabra '" + palabra + "' es muy corta (menos de 4 letras).");
            }
            return palabra;
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new PosicionPoemaInvalidaException("La posición " + indice + " no existe dentro del vector del poema.", e);
        }
    }

    public int buscarPalabra(String palabraBuscada) throws PalabraNoEncontradaException {
        for (int i = 0; i < palabras.length; i++) {
            if (palabras[i].equalsIgnoreCase(palabraBuscada)) {
                return i;
            }
        }
        throw new PalabraNoEncontradaException("La palabra '" + palabraBuscada + "' no se encuentra dentro del poema.");
    }

    public int getCantidadPalabras() {
        return palabras != null ? palabras.length : 0;
    }

    public String[] getPalabras() {
        return palabras;
    }
}