import java.util.ArrayList;
import java.util.List;

/**
 * ═══════════════════════════════════════════════════════════════════
 *  PUNTO 1 — Análisis de algoritmos sobre listas dinámicas
 * ═══════════════════════════════════════════════════════════════════-
 *
 *  COMPLEJIDADES (peor caso):
 *  ┌────────────────────────────────────────────────────────────────────────────────┐
 *  │       Método             │ Big O    │ Razón Principal                          │
 *  │                          │          │                                          │
 *  │ contarMayores            │ O(n)     │ Un bucle: n iteraciones × O(1) cada uno  │
 *  │ insertarSiNoExiste       │ O(n)     │ contains O(n) + add(0,x) O(n)            │
 *  │ depurarDuplicados        │ O(n²)    │ Doble bucle n(n-1)/2 + remove O(n)       │
 *  └────────────────────────────────────────────────────────────────────────────────┘
 *
 *  ORDEN DE ESCALABILIDAD (mejor → peor para n grande):
 *    1° contarMayores      → O(n)   — un recorrido simple, sin desplazamientos
 *    2° insertarSiNoExiste → O(n)   — también lineal pero mayor constante (2 ops O(n))
 *    3° depurarDuplicados  → O(n²)  — crecimiento cuadrático, inviable para n grande
 */
public class Punto1Analisis {

    static final int a = 5;
    static final int b = 7;
    static final int c = 9;


    // MÉTODO 1: contarMayores  —  Complejidad O(n)
    //-------------------------------------------------
    /**
     * Recorre la lista UNA SOLA VEZ contando elementos mayores al umbral.
     *
     * Justificación:
     *  - El bucle for itera exactamente n veces (n = datos.size()).
     *  - datos.get(i) en ArrayList → O(1) (acceso directo al arreglo interno).
     *  - La comparación > umbral → O(1).
     *  - Total: n × O(1) = O(n).
     *
     * Peor caso: TODOS los elementos son mayores al umbral.
     *  El bucle completa las n iteraciones sin ningún salto ni salida anticipada.
     */
    public static int contarMayores(List<Integer> datos, int umbral) {
        int c = 0;
        for (int i = 0; i < datos.size(); i++) {
            if (datos.get(i) > umbral) {
                c++;
            }
        }
        return c;
    }

    // ─────────────────────────────────────────────────────────────────────────
    // MÉTODO 2: insertarSiNoExiste  —  Complejidad O(n)
    // ─────────────────────────────────────────────────────────────────────────
    /**
     * Inserta x en la posición 0 SOLO si no existe ya en la lista.
     *
     * Justificación:
     *  - datos.contains(x): búsqueda lineal → O(n).
     *  - datos.add(0, x): desplaza los n elementos existentes → O(n).
     *  - Total: O(n) + O(n) = O(n).
     *
     * Peor caso: x NO existe → contains recorre n elementos + add(0,x) desplaza n.
     *
     * NOTA: add(0,x) ≠ add(x) en costo.
     *  add(x) al final → O(1) amortizado (sin desplazamientos).
     *  add(0,x) al inicio → O(n) (desplaza TODOS los elementos).
     */
    public static void insertarSiNoExiste(ArrayList<Integer> datos, int x) {
        if (!datos.contains(x)) {
            datos.add(0, x);
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // MÉTODO 3: depurarDuplicados  —  Complejidad O(n²)
    // ─────────────────────────────────────────────────────────────────────────
    /**
     * Elimina duplicados manteniendo la primera aparición.
     *
     * Justificación:
     *  - Bucle externo i: 0 → n-1
     *  - Bucle interno j: i+1 → datos.size()-1
     *  - Total comparaciones: (n-1)+(n-2)+...+1 = n(n-1)/2 → O(n²)
     *  - datos.remove(j): desplaza elementos posteriores → O(n) adicional.
     *
     * Peor caso: lista SIN duplicados → el doble bucle completa todas las iteraciones.
     *
     * CASO ESPECIAL — todos iguales (n=5, e.g. [7,7,7,7,7]):
     *  → Se producen exactamente n-1 = 4 eliminaciones.
     *  → El costo de remove(j) NO puede ignorarse: cada remove desplaza O(n) elementos.
     */
    public static void depurarDuplicados(ArrayList<Integer> datos) {
        for (int i = 0; i < datos.size(); i++) {
            for (int j = i + 1; j < datos.size(); j++) {
                if (datos.get(i).equals(datos.get(j))) {
                    datos.remove(j);
                    j--;
                }
            }
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // DEMOSTRACIÓN CON TRAZAS
    // ─────────────────────────────────────────────────────────────────────────
    public static void ejecutar() {
        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║  PUNTO 1 — Análisis de algoritmos sobre listas dinámicas     ║");
        System.out.println("║  a=5, b=7, c=9                                               ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");

        // ── contarMayores ─────────────────────────────────────────────────────
        System.out.println("\n── contarMayores — Complejidad O(n) ──────────────────────────");
        ArrayList<Integer> lista1 = new ArrayList<>(List.of(3, 7, 2, 9, 1, 8, 4, 6));
        int umbral = a;   // umbral = 5
        System.out.println("  Lista   : " + lista1);
        System.out.println("  Umbral  : " + umbral + "  (valor personalizado a=" + a + ")");
        System.out.println("  Traza del bucle:");
        System.out.println("    i=0: get(0)=3  → 3  > 5? NO");
        System.out.println("    i=1: get(1)=7  → 7  > 5? SI  → c=1");
        System.out.println("    i=2: get(2)=2  → 2  > 5? NO");
        System.out.println("    i=3: get(3)=9  → 9  > 5? SI  → c=2");
        System.out.println("    i=4: get(4)=1  → 1  > 5? NO");
        System.out.println("    i=5: get(5)=8  → 8  > 5? SI  → c=3");
        System.out.println("    i=6: get(6)=4  → 4  > 5? NO");
        System.out.println("    i=7: get(7)=6  → 6  > 5? SI  → c=4");
        int resultado1 = contarMayores(lista1, umbral);
        System.out.println("  Resultado: " + resultado1 + " elemento(s) mayores que " + umbral);

        // ── insertarSiNoExiste ────────────────────────────────────────────────
        System.out.println("\n── insertarSiNoExiste — Complejidad O(n) ─────────────────────");
        ArrayList<Integer> lista2 = new ArrayList<>(List.of(10, 20, 30, 40));
        System.out.println("  Lista inicial: " + lista2);

        System.out.println("\n  Caso 1 — Insertar 20 (YA EXISTE → no se inserta):");
        System.out.println("    contains(20): recorre [10,20,30,40] → encontrado en idx=1 → true");
        System.out.println("    → Condición falsa → no se ejecuta add(0,20)");
        insertarSiNoExiste(lista2, 20);
        System.out.println("    Lista resultante: " + lista2);

        System.out.println("\n  Caso 2 — Insertar 99 (NO EXISTE → se inserta al inicio):");
        System.out.println("    contains(99): recorre todos → no encontrado → false");
        System.out.println("    add(0, 99): desplaza [10,20,30,40] → derecha → 4 desplazamientos");
        insertarSiNoExiste(lista2, 99);
        System.out.println("    Lista resultante: " + lista2);
        System.out.println("    Diferencia de costo: add(0,99)=O(n) vs add(99)=O(1) amortizado");

        // ── depurarDuplicados ─────────────────────────────────────────────────
        System.out.println("\n── depurarDuplicados — Complejidad O(n²) ─────────────────────");
        ArrayList<Integer> lista3 = new ArrayList<>(List.of(5, 3, 5, 7, 3, 9, 7, 5));
        System.out.println("  Lista inicial: " + lista3 + " (n=8)");
        depurarDuplicados(lista3);
        System.out.println("  Lista después (sin duplicados): " + lista3);

        System.out.println("\n  CASO ESPECIAL — todos iguales [7,7,7,7,7] (n=5):");
        ArrayList<Integer> todosIguales = new ArrayList<>(List.of(7, 7, 7, 7, 7));
        System.out.println("  Traza de eliminaciones:");
        System.out.println("    i=0,j=1: 7==7 → remove(1) → [7,7,7,7]  j-- → j=0 → j=1");
        System.out.println("    i=0,j=1: 7==7 → remove(1) → [7,7,7]    j-- → j=0 → j=1");
        System.out.println("    i=0,j=1: 7==7 → remove(1) → [7,7]      j-- → j=0 → j=1");
        System.out.println("    i=0,j=1: 7==7 → remove(1) → [7]        j=2>size=1 → sale");
        System.out.println("    i=1: 1<size=1? NO → sale del bucle externo");
        System.out.println("    Eliminaciones totales: n-1 = 4");
        System.out.println("    Costo de cada remove: O(n) por desplazamiento interno del ArrayList");
        depurarDuplicados(todosIguales);
        System.out.println("  Lista final: " + todosIguales);

        System.out.println("\n── Orden de escalabilidad (mejor → peor) ─────────────────────");
        System.out.println("  1° contarMayores      → O(n)   → un recorrido simple");
        System.out.println("  2° insertarSiNoExiste → O(n)   → dos operaciones O(n) (mayor constante)");
        System.out.println("  3° depurarDuplicados  → O(n²)  → doble bucle + remove O(n) por eliminación");
        System.out.println();
    }
}
