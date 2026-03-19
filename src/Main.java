

 //*  PRIMER PARCIAL — Punto de entrada principal
// *  Estructuras de Datos: Big O, List, ArrayList
 //*  Estudiante: Santiago Burgos Codigo: 66757 ---->| a=5, b=7, c=9 |


public class Main {

    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║         PRIMER PARCIAL — Estructuras de Datos                ║");
        System.out.println("║         Temas: Big O, List, ArrayList                        ║");
        System.out.println("║         Estudiante: Santiago Burgos                          ║");
        System.out.println("║         Datos Personales: a=5, b=7, c=9                      ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        System.out.println();

        // ── Punto 1: Análisis de complejidad temporal ──────────────────────
        Punto1Analisis.ejecutar();

        separarSecciones();

        // ── Punto 2: Traza personalizada sobre ArrayList ───────────────────
        Punto2Traza.ejecutar();

        separarSecciones();

        // ── Punto 3: Corrección de código y análisis técnico ───────────────
        Punto3Correccion.ejecutar();

        separarSecciones();

        // ── Punto 4: Sistema de turnos ─────────────────────────────────────
        Punto4Turnos.ejecutar();

        separarSecciones();

        // ── Ejercicio Práctico: Simulación con 8 enteros ──────────────────
        EjercicioPractico.ejecutar();


    }

    private static void separarSecciones() {
        System.out.println();
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println();
    }
}
