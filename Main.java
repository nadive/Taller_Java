package principal;

import excepciones.*;
import modelo.ProcesadorPoema;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ProcesadorPoema procesador = new ProcesadorPoema();
        Scanner scanner = new Scanner(System.in);

        try {
            procesador.cargarPoema("poema.txt");
            System.out.println("Poema cargado exitosamente. Total palabras: " + procesador.getCantidadPalabras());
        } catch (ErrorCargaPoemaException e) {
            System.out.println("Error inicial: " + e.getMessage());
        }

        int opcion = 0;

        do {
            System.out.println("\n--- MENÚ DE GESTIÓN DE EXCEPCIONES ---");
            System.out.println("1. Escenario A1 (Nativa): Intentar abrir archivo inexistente (FileNotFoundException)");
            System.out.println("2. Escenario A2 (Nativa): Ingresar texto en lugar de número (InputMismatchException)");
            System.out.println("3. Escenario B1 (Personalizada): Buscar palabra inexistente (PalabraNoEncontradaException)");
            System.out.println("4. Escenario B2 (Personalizada): Seleccionar palabra < 4 letras (PalabraMuyCortaException)");
            System.out.println("5. Escenario C1 (Encadenada): Encapsular error de lectura (ErrorCargaPoemaException)");
            System.out.println("6. Escenario C2 (Encadenada): Encapsular índice fuera de rango (PosicionPoemaInvalidaException)");
            System.out.println("7. Salir");
            System.out.print("Seleccione una opción: ");

            try {
                opcion = scanner.nextInt();
                scanner.nextLine();

                switch (opcion) {
                    case 1:
                        System.out.print("Ingrese nombre de archivo a abrir (ej: archivo_fantasma.txt): ");
                        String nombreArchivo = scanner.nextLine();
                        try {
                            FileReader fr = new FileReader(nombreArchivo);
                        } catch (FileNotFoundException e) {
                            System.out.println("[CAPTURA NATIVA]: " + e.toString());
                        }
                        break;

                    case 2:
                        System.out.print("Ingrese una letra en lugar de un número de posición: ");
                        try {
                            int pos = scanner.nextInt();
                        } catch (InputMismatchException e) {
                            System.out.println("[CAPTURA NATIVA]: Entrada inválida (InputMismatchException).");
                            scanner.nextLine();
                        }
                        break;

                    case 3:
                        System.out.print("Ingrese una palabra para buscar en el poema: ");
                        String palabraBuscar = scanner.nextLine();
                        try {
                            int idx = procesador.buscarPalabra(palabraBuscar);
                            System.out.println("Palabra encontrada en la posición: " + idx);
                        } catch (PalabraNoEncontradaException e) {
                            System.out.println("[CAPTURA PERSONALIZADA]: " + e.getMessage());
                        }
                        break;

                    case 4:
                        System.out.print("Ingrese la posición de una palabra corta (ej: posición de 'un' o 'mar'): ");
                        int posCorta = scanner.nextInt();
                        try {
                            String pal = procesador.obtenerPalabraPorPosicion(posCorta);
                            System.out.println("Palabra obtenida: " + pal);
                        } catch (PalabraMuyCortaException e) {
                            System.out.println("[CAPTURA PERSONALIZADA]: " + e.getMessage());
                        } catch (PosicionPoemaInvalidaException e) {
                            System.out.println("Posición fuera de rango.");
                        }
                        break;

                    case 5:
                        try {
                            ProcesadorPoema pTest = new ProcesadorPoema();
                            pTest.cargarPoema("ruta_invalida/no_existe.txt");
                        } catch (ErrorCargaPoemaException e) {
                            System.out.println("[CAPTURA ENCADENADA]: " + e.getMessage());
                            System.out.println("Causa original: " + e.getCause());
                        }
                        break;

                    case 6:
                        System.out.print("Ingrese un índice fuera de rango (ej: 999): ");
                        int posInvalida = scanner.nextInt();
                        try {
                            procesador.obtenerPalabraPorPosicion(posInvalida);
                        } catch (PosicionPoemaInvalidaException e) {
                            System.out.println("[CAPTURA ENCADENADA]: " + e.getMessage());
                            System.out.println("Causa original de Java: " + e.getCause());
                        } catch (PalabraMuyCortaException e) {
                            System.out.println(e.getMessage());
                        }
                        break;

                    case 7:
                        System.out.println("Saliendo de la aplicación...");
                        break;

                    default:
                        System.out.println("Opción no válida.");
                }
            } catch (InputMismatchException e) {
                System.out.println("[ERROR]: Debe ingresar un número entero para el menú.");
                scanner.nextLine();
            } finally {
                System.out.println("--> [Bloque FINALLY ejecutado para liberar recursos]");
            }

        } while (opcion != 7);

        scanner.close();
    }
}