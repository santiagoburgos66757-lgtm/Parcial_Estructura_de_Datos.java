import java.util.ArrayList;

/**
 * ═══════════════════════════════════════════════════════════════════
 *  PUNTO 2 — Traza personalizada sobre ArrayList
 * ═══════════════════════════════════════════════════════════════════
 *
 *  Lista inicial L0 = [a+2, b+4, a+b, |a-b|+1, 2a+b, 2b+1]
 *                   = [7,   11,  12,  3,        17,   15]
 *
 *  Valores calculados:
 *    c+7     = 9+7     = 16
 *    a+b     = 5+7     = 12
 *    a*b     = 5×7     = 35
 *    (c%3)+1 = (9%3)+1 = 1
 *    2a+2b   = 10+14   = 24
 *    b+4     = 7+4     = 11
 *    a-b     = 5-7     = -2
 *
 *  TABLA DE TRAZAS:
 *  ┌────────┬───────────────────────────────────────────────┬──────┬──────────┐
 *  │ Estado │ Lista                                         │ Size │ Despla.  │
 *  ├────────┼───────────────────────────────────────────────┼──────┼──────────┤
 *  │ L0     │ [7, 11, 12, 3, 17, 15]                        │  6   │   —      │
 *  │ Op1    │ [7, 16, 11, 12, 3, 17, 15]                    │  7   │ 5 (→)    │
 *  │ Op2    │ [7, 16, 11, 3, 17, 15]                        │  6   │ 3 (←)    │
 *  │ Op3    │ [35, 7, 16, 11, 3, 17, 15]                    │  7   │ 6 (→)    │
 *  │ Op4    │ [35, 24, 16, 11, 3, 17, 15]                   │  7   │ 0        │
 *  │ Op5    │ [35, 24, 11, 3, 17, 15] ← rama IF (11 existe) │  6   │ 4 (←)    │
 *  │ Op6    │ [35, 24, 11, -2, 3, 17, 15]                   │  7   │ 3 (→)    │
 *  └────────┴────────────────────────────────────────────── ┴──────┴──────────┘
 *  Total desplazamientos: 5+3+6+0+4+3 = 21
 *
 *  CLASIFICACIÓN POR TIPO:
 *    Inserción ............... Op1, Op3, Op6
 *    Eliminación ............. Op2, Op5
 *    Búsqueda lineal ......... Op2 (por valor), Op5 (contains)
 *    Modif. sin cambio estr. . Op4 (set)
 */
public class Punto2Traza {

    static final int a = 5;
    static final int b = 7;
    static final int c = 9;

    public static void ejecutar() {
        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║     PUNTO 2 — Traza personalizada sobre ArrayList            ║");
        System.out.println("║     a=5, b=7, c=9                                            ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");

        // ── Construcción de L0 ────────────────────────────────────────────────
        ArrayList<Integer> L = new ArrayList<>();
        L.add(a + 2);                // 0 → 7
        L.add(b + 4);                // 1 → 11
        L.add(a + b);                // 2 → 12
        L.add(Math.abs(a - b) + 1);  // 3 → 3
        L.add(2 * a + b);            // 4 → 17
        L.add(2 * b + 1);            // 5 → 15

        System.out.println("\n  L0 (estado inicial):");
        imprimirEstado(L);
        System.out.println("  Composición: [a+2=" +(a+2)+ ", b+4=" +(b+4)+ ", a+b=" +(a+b)+
                ", |a-b|+1=" +Math.abs(a-b)+1+ ", 2a+b=" +(2*a+b)+ ", 2b+1=" +(2*b+1)+ "]");

        // ════════════════════════════════════════════════════════════════
        // OPERACIÓN 1: L.add(1, c + 7)  →  L.add(1, 16)
        // Tipo: INSERCIÓN por índice
        // Desplazamientos: 5 (índices 1..5 →)
        // ════════════════════════════════════════════════════════════════
        System.out.println("\n──────────────────────────────────────────────────────────────");
        System.out.println("  Op1: L.add(1, c+7)  →  L.add(1, " + (c+7) + ")");
        System.out.println("  Tipo: INSERCIÓN por índice | Costo: O(n)");
        System.out.println("  Desplazamientos (→ derecha): 5 elementos (índices 1..5)");
        System.out.println("    [5]→[6]:15  [4]→[5]:17  [3]→[4]:3  [2]→[3]:12  [1]→[2]:11");
        System.out.println("    → Insertar 16 en índice 1");
        L.add(1, c + 7);
        System.out.print("  Estado tras Op1: ");
        imprimirEstado(L);

        // ════════════════════════════════════════════════════════════════
        // OPERACIÓN 2: L.remove(Integer.valueOf(a + b))  →  remove(Integer 12)
        // Tipo: ELIMINACIÓN por valor (búsqueda lineal)
        // Desplazamientos: 3 (elementos tras idx eliminado se mueven ←)
        // ════════════════════════════════════════════════════════════════
        System.out.println("\n──────────────────────────────────────────────────────────────");
        System.out.println("  Op2: L.remove(Integer.valueOf(a+b))  →  remove(Integer " + (a+b) + ")");
        System.out.println("  Tipo: ELIMINACIÓN por valor + BÚSQUEDA LINEAL | Costo: O(n)");
        System.out.println("  Búsqueda lineal de 12 (4 comparaciones):");
        System.out.println("    idx0:7≠12  |  idx1:16≠12  |  idx2:11≠12  |  idx3:12=12 ✓ → eliminar idx3");
        System.out.println("  Desplazamientos (← izquierda): 3 elementos (idx 4,5,6 → 3,4,5)");
        L.remove(Integer.valueOf(a + b));
        System.out.print("  Estado tras Op2: ");
        imprimirEstado(L);

       //----------------------------------------------------------------
        // OPERACIÓN 3: L.add(0, a * b)  →  L.add(0, 35)
        // Tipo: INSERCIÓN al inicio (peor caso de add por índice)
        // Desplazamientos: 6 (TODOS →)
        // -----------------------------------------------------------------
        System.out.println("\n──────────────────────────────────────────────────────────────");
        System.out.println("  Op3: L.add(0, a*b)  →  L.add(0, " + (a*b) + ")");
        System.out.println("  Tipo: INSERCIÓN AL INICIO | Costo: O(n) — PEOR CASO de add(idx,val)");
        System.out.println("  Desplazamientos (→ derecha): 6 elementos (TODOS los existentes)");
        System.out.println("    [5]→[6]:15  [4]→[5]:17  [3]→[4]:3  [2]→[3]:11  [1]→[2]:16  [0]→[1]:7");
        System.out.println("    → Insertar 35 en índice 0");
        L.add(0, a * b);
        System.out.print("  Estado tras Op3: ");
        imprimirEstado(L);

        // -----------------------------------------------------------------------
        // OPERACIÓN 4: L.set((c % 3) + 1, 2 * a + 2 * b)  →  L.set(1, 24)
        // Tipo: MODIFICACIÓN sin cambio estructural
        // Desplazamientos: 0
        // ------------------------------------------------------------------------
        System.out.println("\n──────────────────────────────────────────────────────────────");
        int idxSet = (c % 3) + 1;    // (9%3)+1 = 0+1 = 1
        int valSet = 2 * a + 2 * b;  // 10+14 = 24
        System.out.println("  Op4: L.set((c%3)+1, 2a+2b)  →  L.set(" + idxSet + ", " + valSet + ")");
        System.out.println("  (c%3)+1 = (" +c+ "%3)+1 = 0+1 = " + idxSet);
        System.out.println("  2a+2b   = 2×" +a+ "+2×" +b+ " = " + valSet);
        System.out.println("  Tipo: MODIFICACIÓN directa sin cambio estructural | Costo: O(1)");
        System.out.println("  Desplazamientos: 0 — acceso por índice al arreglo interno");
        System.out.println("    L[" + idxSet + "] era " + L.get(idxSet) + " → ahora será " + valSet);
        L.set(idxSet, valSet);
        System.out.print("  Estado tras Op4: ");
        imprimirEstado(L);

        // ------------------------------------------------------------------------
        // OPERACIÓN 5: if (L.contains(b + 4)) { L.remove(2); }
        //              else { L.add(L.size(), b + 4); }
        // b+4 = 11 → contains(11) buscado en [35,24,16,11,3,17,15]
        // idx3 = 11 → TRUE → se ejecuta remove(2)
        // -----------------------------------------------------------------------
        System.out.println("\n──────────────────────────────────────────────────────────────");
        int buscarVal = b + 4;  // 11
        System.out.println("  Op5: if (L.contains(b+4=" + buscarVal + ")) { L.remove(2); }");
        System.out.println("       else { L.add(L.size(), b+4); }");
        System.out.println("  Tipo: BÚSQUEDA LINEAL (contains) + ELIMINACIÓN por índice");
        System.out.println("  Búsqueda de " + buscarVal + ":");
        System.out.println("    idx0:35≠11  |  idx1:24≠11  |  idx2:16≠11  |  idx3:11=11 ✓ → contains=true");
        System.out.println("  → Condición TRUE → se ejecuta L.remove(2) (elimina el valor en índice 2 = 16)");
        System.out.println("  Desplazamientos (← izquierda): 4 elementos (idx 3,4,5,6 → 2,3,4,5)");
        if (L.contains(buscarVal)) {
            L.remove(2);
            System.out.print("  Estado tras Op5 (rama IF): ");
        } else {
            L.add(L.size(), buscarVal);
            System.out.print("  Estado tras Op5 (rama ELSE): ");
        }
        imprimirEstado(L);
        System.out.println("  NOTA: Con otros valores de b, contains puede retornar false → rama else");

        // ---------------------------------------------------------------------------------------------
        // OPERACIÓN 6: L.add(L.size() / 2, a - b)
        // L.size()/2 = 6/2 = 3  |  a-b = 5-7 = -2
        // Tipo: INSERCIÓN por índice en mitad
        // Desplazamientos: 3
        // ----------------------------------------------------------------------------------------------
        System.out.println("\n──────────────────────────────────────────────────────────────");
        int idxMitad = L.size() / 2;
        int valMitad = a - b;   // -2
        System.out.println("  Op6: L.add(L.size()/2, a-b)  →  L.add(" + idxMitad + ", " + valMitad + ")");
        System.out.println("  L.size()/2 = " + L.size() + "/2 = " + idxMitad + "   |   a-b = " + valMitad);
        System.out.println("  Tipo: INSERCIÓN por índice (mitad) | Costo: O(n)");
        System.out.println("  Desplazamientos (→ derecha): 3 elementos (idx 3,4,5 → 4,5,6)");
        L.add(idxMitad, valMitad);
        System.out.print("  Estado tras Op6 (FINAL): ");
        imprimirEstado(L);

        // ── Resumen ───────────────────────────────────────────────────────────
        System.out.println("\n══════════════════════════════════════════════════════════════");
        System.out.println("  RESUMEN DE DESPLAZAMIENTOS:");
        System.out.println("  Op1 add(1, 16)    → 5 desplazamientos → derecha");
        System.out.println("  Op2 remove(val 12)→ 3 desplazamientos → izquierda");
        System.out.println("  Op3 add(0, 35)    → 6 desplazamientos → derecha  (máximo)");
        System.out.println("  Op4 set(1, 24)    → 0 desplazamientos  (O(1))");
        System.out.println("  Op5 remove(idx 2) → 4 desplazamientos → izquierda");
        System.out.println("  Op6 add(3, -2)    → 3 desplazamientos → derecha");
        System.out.println("  TOTAL             → 21 desplazamientos internos");
        System.out.println("\n  CLASIFICACIÓN:");
        System.out.println("  Inserciones ............. Op1, Op3, Op6");
        System.out.println("  Eliminaciones ........... Op2, Op5");
        System.out.println("  Búsqueda lineal ......... Op2 (por valor), Op5 (contains)");
        System.out.println("  Modif. sin cambio estr. . Op4 (set)");
        System.out.println("\n  Por qué distintos a,b,c generan trazas diferentes:");
        System.out.println("  - L0 depende directamente de a y b → valores únicos por estudiante");
        System.out.println("  - contains(b+4) puede ser true/false → bifurca el algoritmo");
        System.out.println("  - L.size()/2 varía según inserciones/eliminaciones previas");
        System.out.println("  - Valores insertados (c+7, a*b, 2a+2b, a-b) son únicos para cada a,b,c");
        System.out.println();
    }

    private static void imprimirEstado(ArrayList<Integer> L) {
        System.out.println(L.toString() + "  (size=" + L.size() + ")");
    }
}
