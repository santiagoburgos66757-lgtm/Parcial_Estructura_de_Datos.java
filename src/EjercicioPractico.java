import java.util.ArrayList;

/**
 * ═══════════════════════════════════════════════════════════════════
 *  EJERCICIO PRÁCTICO — Simulación de ArrayList con trazas (25 pts)
 *  Estudiante: Santiago Burgos | a=5, b=7, c=9
 * ═══════════════════════════════════════════════════════════════════
 *
 *  LISTA INICIAL (8 enteros con a=5, b=7, c=9):
 *  ┌─────┬──────────┬─────────┬───────┐
 *  │ Idx │ Expresión│ Cálculo │ Valor │
 *  ├─────┼──────────┼─────────┼───────┤
 *  │  0  │ a        │ 5       │   5   │
 *  │  1  │ b        │ 7       │   7   │
 *  │  2  │ c        │ 9       │   9   │
 *  │  3  │ a+b      │ 5+7     │  12   │
 *  │  4  │ a+c      │ 5+9     │  14   │
 *  │  5  │ b+c      │ 7+9     │  16   │
 *  │  6  │ a*b-c    │ 35-9    │  26   │
 *  │  7  │ a+b+c    │ 5+7+9   │  21   │
 *  └─────┴──────────┴─────────┴───────┘
 *  L = [5, 7, 9, 12, 14, 16, 26, 21]
 *
 *  OPERACIONES Y COSTO:
 *  ┌────┬──────────────────────────────────┬──────────────────┬──────────┐
 *  │ Op │ Operación                        │ Costo            │ Despla.  │
 *  ├────┼──────────────────────────────────┼──────────────────┼──────────┤
 *  │ 1  │ add(2, a*b=35)                   │ O(n)             │ 6   (→)  │
 *  │ 2  │ add(c+10=19)  — al final         │ O(1) amortizado  │ 0        │
 *  │ 3  │ remove(0)     — peor caso        │ O(n)             │ 9   (←)  │
 *  │ 4  │ remove(Integer.valueOf(a+b=12))  │ O(n)             │ 5   (←)  │
 *  │ 5  │ set(1, a+b+c=21)                 │ O(1)             │ 0        │
 *  │ 6  │ add(size/2=4, b=7)               │ O(n)             │ 4   (→)  │
 *  └────┴──────────────────────────────────┴──────────────────┴──────────┘
 *  TOTAL desplazamientos: 6+0+9+5+0+4 = 24
 *  OPERACIÓN MÁS COSTOSA: Op3 — remove(0): 9 desplazamientos (peor caso absoluto).
 */
public class EjercicioPractico {

    static final int a = 5;
    static final int b = 7;
    static final int c = 9;

    public static void ejecutar() {
        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║        EJERCICIO PRÁCTICO — Simulación ArrayList             ║");
        System.out.println("║        a=5, b=7, c=9                                         ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");

        // ── Lista inicial (8 elementos) ───────────────────────────────────────
        ArrayList<Integer> L = new ArrayList<>();
        L.add(a);           // idx 0 → 5
        L.add(b);           // idx 1 → 7
        L.add(c);           // idx 2 → 9
        L.add(a + b);       // idx 3 → 12
        L.add(a + c);       // idx 4 → 14
        L.add(b + c);       // idx 5 → 16
        L.add(a * b - c);   // idx 6 → 26
        L.add(a + b + c);   // idx 7 → 21

        System.out.println("\n  Estado inicial: " + L + "  (size=" + L.size() + ")");
        System.out.println("  [a=" +a+ ", b=" +b+ ", c=" +c+ ", a+b=" +(a+b)+ ", a+c=" +(a+c)+
                ", b+c=" +(b+c)+ ", a*b-c=" +(a*b-c)+ ", a+b+c=" +(a+b+c)+ "]");

        // --------------------------------------------------------------------------------------
        // OP 1: add(2, a*b) → add(2, 35)
        // Costo O(n): desplaza 6 elementos (índices 2..7) →
        //---------------------------------------------------------------------------------------
        System.out.println("\n──────────────────────────────────────────────────────────────");
        System.out.println("  Op1: L.add(2, a*b)  →  L.add(2, " + (a * b) + ")");
        System.out.println("  Tipo: INSERCIÓN por índice | Costo: O(n)");
        System.out.println("  Traza de desplazamientos (→ derecha, 6 movimientos):");
        System.out.println("    [7]→[8]:21  [6]→[7]:26  [5]→[6]:16");
        System.out.println("    [4]→[5]:14  [3]→[4]:12  [2]→[3]:9");
        System.out.println("    Insertar 35 en índice 2");
        L.add(2, a * b);
        System.out.println("  Estado: " + L + "  (size=" + L.size() + ")");

        // ════════════════════════════════════════════════════════════════
        // OP 2: add(c+10) → add(19) al final
        // Costo O(1) amortizado: sin desplazamientos
        // ════════════════════════════════════════════════════════════════
        System.out.println("\n──────────────────────────────────────────────────────────────");
        System.out.println("  Op2: L.add(c+10)  →  L.add(" + (c + 10) + ")");
        System.out.println("  Tipo: INSERCIÓN al final | Costo: O(1) amortizado");
        System.out.println("  Inserta en el siguiente hueco del arreglo interno → 0 desplazamientos");
        L.add(c + 10);
        System.out.println("  Estado: " + L + "  (size=" + L.size() + ")");

        // ════════════════════════════════════════════════════════════════
        // OP 3: remove(0) — eliminación al inicio, PEOR CASO
        // Costo O(n): desplaza TODOS los n-1 = 9 elementos ←
        // ════════════════════════════════════════════════════════════════
        System.out.println("\n──────────────────────────────────────────────────────────────");
        System.out.println("  Op3: L.remove(0)");
        System.out.println("  Tipo: ELIMINACIÓN por índice al inicio | Costo: O(n)");
        System.out.println("  ★ PEOR CASO de remove(): desplaza TODOS los n-1=9 elementos ← ★");
        System.out.println("  Traza: [1]→[0]:7  [2]→[1]:35  [3]→[2]:9  [4]→[3]:12");
        System.out.println("          [5]→[4]:14  [6]→[5]:16  [7]→[6]:26  [8]→[7]:21  [9]→[8]:19");
        L.remove(0);
        System.out.println("  Estado: " + L + "  (size=" + L.size() + ")");

        // ════════════════════════════════════════════════════════════════
        // OP 4: remove(Integer.valueOf(a+b=12)) — por valor
        // Costo O(n): búsqueda lineal + desplazamiento
        // ════════════════════════════════════════════════════════════════
        System.out.println("\n──────────────────────────────────────────────────────────────");
        System.out.println("  Op4: L.remove(Integer.valueOf(a+b))  →  remove(Integer " + (a+b) + ")");
        System.out.println("  Tipo: ELIMINACIÓN por valor + BÚSQUEDA LINEAL | Costo: O(n)");
        System.out.println("  Búsqueda lineal de " +(a+b)+ " (4 comparaciones):");
        System.out.println("    idx0:7≠12  |  idx1:35≠12  |  idx2:9≠12  |  idx3:12=12 ✓ → eliminar idx3");
        System.out.println("  Desplazamiento ← izquierda: 5 elementos (idx 4..8 → 3..7)");
        L.remove(Integer.valueOf(a + b));
        System.out.println("  Estado: " + L + "  (size=" + L.size() + ")");

        // ════════════════════════════════════════════════════════════════
        // OP 5: set(1, a+b+c=21) — modificación directa
        // Costo O(1): acceso por índice, 0 desplazamientos
        // ════════════════════════════════════════════════════════════════
        System.out.println("\n──────────────────────────────────────────────────────────────");
        System.out.println("  Op5: L.set(1, a+b+c)  →  L.set(1, " + (a + b + c) + ")");
        System.out.println("  Tipo: MODIFICACIÓN sin cambio estructural | Costo: O(1)");
        System.out.println("  Acceso directo: L[1] era " + L.get(1) + " → reemplaza por " + (a + b + c));
        System.out.println("  Sin desplazamientos. Sin búsqueda. O(1) puro.");
        L.set(1, a + b + c);
        System.out.println("  Estado: " + L + "  (size=" + L.size() + ")");

        // ════════════════════════════════════════════════════════════════
        // OP 6: add(L.size()/2, b) → add(4, 7)
        // L.size()/2 = 8/2 = 4 | valor = b = 7
        // Costo O(n): desplaza 4 elementos →
        // ════════════════════════════════════════════════════════════════
        int medio = L.size() / 2;
        System.out.println("\n──────────────────────────────────────────────────────────────");
        System.out.println("  Op6: L.add(L.size()/2, b)  →  L.add(" + medio + ", " + b + ")");
        System.out.println("  L.size()/2 = " + L.size() + "/2 = " + medio + "  |  b = " + b);
        System.out.println("  Tipo: INSERCIÓN por índice (mitad) | Costo: O(n)");
        System.out.println("  Desplazamientos (→ derecha): 4 elementos (idx " + medio + "..7 → " +(medio+1)+ "..8)");
        L.add(medio, b);
        System.out.println("  Estado: " + L + "  (size=" + L.size() + ")");

        // ── Resumen ───────────────────────────────────────────────────────────
        System.out.println("\n══════════════════════════════════════════════════════════════");
        System.out.println("  ESTADO FINAL: " + L);
        System.out.println("\n  TABLA RESUMEN:");
        System.out.println("  ┌────┬──────────────────────────────┬─────────────────┬─────────┐");
        System.out.println("  │ Op │ Operación                    │ Costo           │ Despla. │");
        System.out.println("  ├────┼──────────────────────────────┼─────────────────┼─────────┤");
        System.out.println("  │ 1  │ add(2, 35)  inserción índice │ O(n)            │ 6  (→)  │");
        System.out.println("  │ 2  │ add(19)     al final         │ O(1) amortizado │ 0       │");
        System.out.println("  │ 3  │ remove(0)   peor caso ★      │ O(n)            │ 9  (←)  │");
        System.out.println("  │ 4  │ remove(val 12) búsq+eliminar │ O(n)            │ 5  (←)  │");
        System.out.println("  │ 5  │ set(1, 21)  modificación     │ O(1)            │ 0       │");
        System.out.println("  │ 6  │ add(4, 7)   inserción mitad  │ O(n)            │ 4  (→)  │");
        System.out.println("  └────┴──────────────────────────────┴─────────────────┴─────────┘");
        System.out.println("  Total desplazamientos: 6+0+9+5+0+4 = 24");
        System.out.println();
        System.out.println("  OPERACIÓN MÁS COSTOSA: * Op3 — remove(0)");
        System.out.println("  ─────────────────────────────────────────────────────────────");
        System.out.println("  Eliminar el elemento en el índice 0 obligó a desplazar");
        System.out.println("  TODOS los n-1 = 9 elementos restantes hacia la izquierda.");
        System.out.println("  Este es el peor caso absoluto para ArrayList.remove() porque");
        System.out.println("  el arreglo interno es contiguo en memoria y no permite dejar");
        System.out.println("  huecos. Con n=10 → 9 desplazamientos, más que cualquier otra");
        System.out.println("  operación del ejercicio. A escala (n=200.000): remove(0) × m");
        System.out.println("  veces → O(m×n), el cuello de botella más severo en ArrayList.");
        System.out.println();
    }
}
