import java.util.ArrayList;
import java.util.List;

/**
 * ═══════════════════════════════════════════════════════════════════
 *  PUNTO 4 — Caso aplicado de diseño con ArrayList (20 pts)
 * ═══════════════════════════════════════════════════════════════════
 *
 *  SISTEMA: Plataforma virtual de asesorías académicas — gestión de turnos.
 *  REPRESENTACIÓN: List<Integer> respaldada por ArrayList<Integer>
 *
 *  COMPLEJIDADES:
 *   registrarTurno  → O(1) amortizado  | add(e) al final, sin desplazamientos
 *   consultarTurno  → O(1)             | get(k) acceso directo al índice
 *   cancelarTurno   → O(n)             | búsqueda lineal + desplazamiento
 *   moverTurno      → O(n)             | remove(i) O(n) + add(j,t) O(n)
 *   procesarPrimeros→ O(m×n)           | m veces remove(0), cada una O(n)
 *
 *  CUELLO DE BOTELLA (n=200.000, m=80.000):
 *   Total desplazamientos ≈ 12.800.000.000 → INVIABLE en tiempo real.
 *
 *  ESTRATEGIA: puntero de inicio virtual → O(m) en lugar de O(m×n)
 */
public class Punto4Turnos {

    static final int a = 5;
    static final int b = 7;
    static final int c = 9;

    private final List<Integer> turnos = new ArrayList<>();

    /** Registra un turno al final. O(1) amortizado. */
    public void registrarTurno(int turno) {
        turnos.add(turno);
    }

    /** Consulta turno en posición k. O(1) — acceso directo. */
    public int consultarTurno(int k) {
        return turnos.get(k);
    }

    /** Cancela turno por valor. O(n) — búsqueda lineal + desplazamiento. */
    public boolean cancelarTurno(int valor) {
        return turnos.remove(Integer.valueOf(valor));
    }

    /**
     * Mueve turno de posición i a posición j. O(n).
     * remove(i) desplaza elementos → O(n)
     * add(j, t) desplaza elementos → O(n)
     */
    public void moverTurno(int i, int j) {
        int t = ((ArrayList<Integer>) turnos).remove(i);
        turnos.add(j, t);
    }

    /**
     * Procesa primeros m turnos eliminándolos desde el inicio.
     * CUELLO DE BOTELLA: cada remove(0) = O(n) → total O(m×n).
     */
    public void procesarPrimerosTurnos(int m) {
        for (int k = 0; k < m && !turnos.isEmpty(); k++) {
            turnos.remove(0);
        }
    }

    /**
     * Estrategia mejorada con puntero virtual. O(m).
     * No se llama a remove(0): se avanza un entero `inicio`.
     * Cada acceso = O(1). Total para m turnos = O(m).
     */
    public void procesarConPunteroVirtual(int m) {
        int inicio = 0;
        System.out.println("    [Estrategia O(m) — puntero virtual]");
        while (inicio < m && inicio < turnos.size()) {
            int turnoAtendido = turnos.get(inicio); // O(1)
            System.out.println("      Procesando turno [idx=" + inicio + "]: " + turnoAtendido);
            inicio++; // O(1) — sin desplazamiento
        }
        List<Integer> restantes = new ArrayList<>(turnos.subList(inicio, turnos.size()));
        System.out.println("    Turnos restantes: " + restantes);
        System.out.println("    Costo: O(" + m + ") en lugar de O(" + m + "×n)");
    }

    public List<Integer> getTurnos() { return turnos; }

    public static void ejecutar() {
        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║   PUNTO 4 — Caso aplicado de diseño con ArrayList            ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");

        Punto4Turnos sistema = new Punto4Turnos();

        // Registrar turnos O(1) amortizado
        System.out.println("\n  1. registrarTurno — O(1) amortizado:");
        for (int i = 1; i <= 8; i++) sistema.registrarTurno(i * 10);
        System.out.println("     add(e) × 8 veces. Sin desplazamientos. Lista: " + sistema.getTurnos());

        // Consultar O(1)
        System.out.println("\n  2. consultarTurno(k=2) — O(1):");
        System.out.println("     get(2) = " + sistema.consultarTurno(2) + " → acceso directo, sin recorrido");

        // Cancelar O(n)
        System.out.println("\n  3. cancelarTurno(40) — O(n):");
        System.out.println("     Búsqueda lineal: [10,20,30,40,...] → encontrado en idx=3");
        System.out.println("     Desplazamiento: 4 elementos ← izquierda");
        sistema.cancelarTurno(40);
        System.out.println("     Lista tras cancelar 40: " + sistema.getTurnos());

        // Mover O(n)
        System.out.println("\n  4. moverTurno(i=0, j=3) — O(n):");
        System.out.println("     remove(0) → extrae " + sistema.consultarTurno(0) + ", desplaza 6 ← | O(n)");
        System.out.println("     add(3,t) → inserta en pos 3, desplaza 3 → | O(n)");
        sistema.moverTurno(0, 3);
        System.out.println("     Lista tras mover: " + sistema.getTurnos());

        // Cuello de botella y estrategia
        System.out.println("\n  5. procesarPrimerosTurnos — CUELLO DE BOTELLA O(m×n):");
        System.out.println("     n=200.000, m=80.000 → ~12.800.000.000 desplazamientos → INVIABLE");
        System.out.println("\n  5b. Estrategia mejorada (puntero virtual, m=3):");
        sistema.procesarConPunteroVirtual(3);

        System.out.println("\n  ANÁLISIS CUELLO DE BOTELLA (n=200.000, m=80.000):");
        System.out.println("    remove(0) = O(n): desplaza TODOS los elementos ←");
        System.out.println("    Iter 0   → 199.999 desplazamientos");
        System.out.println("    Iter 1   → 199.998 desplazamientos ... etc.");
        System.out.println("    Total ≈ Σ(200.000-k) k=0..79.999 ≈ 12,8×10⁹ ops");
        System.out.println("    SOLUCIÓN: puntero `inicio` → O(1) por turno → O(m) total");
        System.out.println();
    }
}
