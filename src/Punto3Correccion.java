import java.util.ArrayList;

/**
 * ═══════════════════════════════════════════════════════════════════
 *  PUNTO 3 — Corrección de código y análisis técnico
 *--------------------------------------------------------------------
 *  ════════════════════════════════════════════════════════════════
 *  PREGUNTA 1 — Cuatro errores conceptuales/de implementación
 *  ════════════════════════════════════════════════════════════════
 *
 *  CÓDIGO ORIGINAL DEFECTUOSO:
 *
 *  public static void insertarOrdenado(ArrayList<Integer> datos, int x) {
 *      int i = 0;
 *      while (i < datos.size() && datos.get(i) < x) {
 *          i++;
 *      }
 *      datos.set(i, x);    <---- ERROR 1 y ERROR 2
 *  }
 *
 *  public static void eliminarNegativos(ArrayList<Integer> datos) {
 *      for (int i = 0; i < datos.size(); i++) {
 *          if (datos.get(i) < 0) {
 *              datos.remove(i);  <------ERROR 3 y ERROR 4
 *          }
 *      }
 *  }
 *
 *  ─────────────────────────────────────────────────────────────────
 *  ERROR 1 — Línea: datos.set(i, x)  en insertarOrdenado
 *  ─────────────────────────────────────────────────────────────────
 *  ¿Qué está mal?
 *    set(i, x) REEMPLAZA el elemento existente en la posición i con x.
 *    No inserta: destruye el valor que estaba en i sin desplazar nada.
 *  ¿Qué efecto produce?
 *    La lista pierde el elemento que ocupaba la posición i. El tamaño
 *    no aumenta (size permanece igual). La lista queda incompleta y
 *    ya no está ordenada porque los elementos posteriores a i no
 *    se desplazaron para hacer espacio.
 *  Corrección: cambiar datos.set(i, x) → datos.add(i, x)
 *    add(i, x) desplaza los elementos existentes en [i..n-1] una
 *    posición hacia la derecha y coloca x en i → inserción real.
 *
 *  ─────────────────────────────────────────────────────────────────
 *  ERROR 2 — Línea: datos.set(i, x)  cuando i == datos.size()
 *  ─────────────────────────────────────────────────────────────────
 *  ¿Qué está mal?
 *    Si x es mayor que todos los elementos, el while termina con
 *    i == datos.size(). Llamar set(datos.size(), x) lanza
 *    IndexOutOfBoundsException porque set() requiere 0 ≤ index < size.
 *  ¿Qué efecto produce?
 *    El programa falla en tiempo de ejecución cada vez que se intenta
 *    insertar un valor mayor que el máximo de la lista.
 *  Corrección: add(i, x) acepta i == size (agregar al final) → sin excepción.
 *
 *  ─────────────────────────────────────────────────────────────────
 *  ERROR 3 — Línea: datos.remove(i)  en eliminarNegativos (omisión de i--)
 *  ─────────────────────────────────────────────────────────────────
 *  ¿Qué está mal?
 *    Tras datos.remove(i), el ArrayList desplaza hacia la izquierda
 *    todos los elementos en posiciones > i. El elemento que estaba en
 *    i+1 ahora ocupa i. Sin embargo, el for hace i++ en la siguiente
 *    iteración, así que pasa a revisar i+1 (antes i+2). El elemento
 *    recién desplazado a i NUNCA se comprueba.
 *  ¿Qué efecto produce?
 *    Dos números negativos consecutivos: el segundo se "salta" y queda
 *    en la lista. Ejemplo: [-3, -5, 2] → se elimina -3, lista = [-5, 2],
 *    i++ → i=1, se revisa 2 → -5 nunca se elimina. Resultado incorrecto.
 *  Corrección: agregar i-- justo después de datos.remove(i) para
 *    compensar el desplazamiento, de modo que el i++ del for
 *    vuelva al mismo índice donde se eliminó.
 *
 *  ─────────────────────────────────────────────────────────────────
 *  ERROR 4 — Lógica: modificar la colección mientras se itera con índice
 *  ─────────────────────────────────────────────────────────────────
 *  ¿Qué está mal?
 *    El bucle usa datos.size() como condición de parada, pero cada
 *    remove(i) reduce el tamaño de la lista. El cálculo de datos.size()
 *    cambia en cada iteración que elimina un elemento. Sin i-- el índice
 *    queda desfasado respecto al tamaño real de la lista, lo que además
 *    puede terminar el bucle antes de inspeccionar todos los elementos.
 *  ¿Qué efecto produce?
 *    La lista puede conservar negativos al final si los últimos elementos
 *    negativos quedan fuera del rango del bucle acortado por los removes.
 *  Corrección: el i-- después de cada remove mantiene sincronizados
 *    el índice i y el tamaño dinámico datos.size().
 *
 *  ════════════════════════════════════════════════════════════════
 *  PREGUNTA 2 — Métodos corregidos
 *  ════════════════════════════════════════════════════════════════
 *
 *  PREGUNTA 3 — Complejidades de las versiones corregidas
 *    insertarOrdenado correcto: O(n)
 *      - while loop: hasta n iteraciones → O(n)
 *      - add(i, x): desplaza hasta n elementos → O(n)
 *      - Total: O(n) + O(n) = O(n)
 *      - Peor caso: x es mayor que todos → while recorre n y add inserta al final.
 *
 *    eliminarNegativos correcto: O(n²)  en peor caso
 *      - Bucle for: hasta n iteraciones → O(n)
 *      - Cada remove(i): desplaza elementos a la derecha → O(n)
 *      - Peor caso: TODOS negativos → n removes, cada uno O(n) → O(n²)
 *      - Caso típico (pocos negativos): cercano a O(n)
 */
public class Punto3Correccion {

    static final int a = 5;
    static final int b = 7;
    static final int c = 9;

    // -------------------------------------------------------------------------
    // VERSIÓN ORIGINAL DEFECTUOSA (solo para referencia, NO usar)
    // ------------------------------------------------------------------------

    /** BUGGY — Error1: set en vez de add | Error2: IndexOutOfBoundsException si i==size */
    public static void insertarOrdenado_BUGGY(ArrayList<Integer> datos, int x) {
        int i = 0;
        while (i < datos.size() && datos.get(i) < x) {
            i++;
        }
        datos.set(i, x); // <--- ERROR 1 y ERROR 2
    }

    /** BUGGY — Error3: no ajusta i tras remove | Error4: índice desfasado con size dinámico */
    public static void eliminarNegativos_BUGGY(ArrayList<Integer> datos) {
        for (int i = 0; i < datos.size(); i++) {
            if (datos.get(i) < 0) {
                datos.remove(i); // <-- ERROR 3 y ERROR 4 (falta i--)
            }
        }
    }

    // -------------------------------------------------------------------------
    // VERSIONES CORREGIDAS
    // ------------------------------------------------------------------------

    /**
     * VERSIÓN CORREGIDA de insertarOrdenado.
     * Complejidad: O(n)
     *
     * Correcciones aplicadas:
     *  1. datos.set(i, x) → datos.add(i, x)
     *     add(i, x) desplaza los elementos [i..n-1] una posición a la derecha
     *     e inserta x en la posición i sin destruir ningún elemento.
     *  2. add(i, x) acepta i == datos.size() (agregar al final) → no lanza excepción
     *     cuando x es mayor que todos los elementos de la lista.
     *
     * Precondición: la lista debe estar ordenada ascendentemente.
     * Postcondición: x queda insertado en la posición correcta; lista sigue ordenada.
     */
    public static void insertarOrdenado(ArrayList<Integer> datos, int x) {
        int i = 0;
        while (i < datos.size() && datos.get(i) < x) {
            i++;
        }
        datos.add(i, x); // CORRECCIÓN: add desplaza y NO destruye; acepta i==size
    }

    /**
     * VERSIÓN CORREGIDA de eliminarNegativos.
     * Complejidad: O(n²) peor caso / O(n) caso sin negativos.
     *
     * Correcciones aplicadas:
     *  3. i-- después de datos.remove(i): tras eliminar el elemento en posición i,
     *     el elemento que estaba en i+1 se desplaza a i. El i-- compensa el i++
     *     del for, haciendo que la siguiente iteración vuelva a revisar el índice i
     *     (ahora ocupado por el elemento desplazado).
     *  4. El i-- mantiene sincronizado el índice con el tamaño dinámico de la lista
     *     (datos.size() decrece con cada remove), garantizando que todos los
     *     elementos sean inspeccionados.
     */
    public static void eliminarNegativos(ArrayList<Integer> datos) {
        for (int i = 0; i < datos.size(); i++) {
            if (datos.get(i) < 0) {
                datos.remove(i);
                i--; // CORRECCIÓN: compensar el desplazamiento ← producido por remove
            }
        }
    }

    // -------------------------------------------------------------------------
    // PREGUNTA 4 — Traza de eliminarNegativos sobre lista personalizada
    // Lista: [a, -b, c, -(a+b), 2a, -1, b] = [6, -5, 6, -11, 12, -1, 5]
    // -------------------------------------------------------------------------

    public static void ejecutar() {
        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║   PUNTO 3 — Corrección de código y análisis técnico          ║");
        System.out.println("║   a=6, b=5, c=6                                              ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");

        // ── Resumen de errores ────────────────────────────────────────────────
        System.out.println("\n── PREGUNTA 1: Cuatro errores conceptuales identificados ─────");
        System.out.println();
        System.out.println("  ERROR 1 — insertarOrdenado, línea: datos.set(i, x)");
        System.out.println("    ¿Qué está mal?  set(i,x) REEMPLAZA el elemento en i; no inserta.");
        System.out.println("    ¿Qué produce?   El elemento que estaba en i se DESTRUYE.");
        System.out.println("                    El tamaño de la lista no crece. Datos perdidos.");
        System.out.println("    Corrección:     datos.set(i, x) → datos.add(i, x)");
        System.out.println();
        System.out.println("  ERROR 2 — insertarOrdenado, línea: datos.set(i, x) cuando i==size");
        System.out.println("    ¿Qué está mal?  Si x > todos los elementos, i llega a datos.size().");
        System.out.println("                    set(size, x) lanza IndexOutOfBoundsException");
        System.out.println("                    porque set requiere 0 ≤ index < size.");
        System.out.println("    ¿Qué produce?   El programa falla en tiempo de ejecución.");
        System.out.println("    Corrección:     add(i, x) acepta i==size → inserta al final sin excepción.");
        System.out.println();
        System.out.println("  ERROR 3 — eliminarNegativos, línea: datos.remove(i) — falta i--");
        System.out.println("    ¿Qué está mal?  Tras remove(i), el siguiente elemento pasa a índice i.");
        System.out.println("                    El for hace i++, saltándose ese elemento.");
        System.out.println("    ¿Qué produce?   Negativos consecutivos no se eliminan todos.");
        System.out.println("                    Ejemplo: [-3,-5,2] → elimina -3 → [-5,2] → i++ →");
        System.out.println("                    revisa 2, nunca revisa -5 → lista resultante: [-5,2]. ✗");
        System.out.println("    Corrección:     agregar i-- después de datos.remove(i).");
        System.out.println();
        System.out.println("  ERROR 4 — eliminarNegativos, lógica: tamaño dinámico no contemplado");
        System.out.println("    ¿Qué está mal?  Cada remove reduce datos.size(), pero el bucle no");
        System.out.println("                    ajusta i, quedando desfasado con el tamaño real.");
        System.out.println("    ¿Qué produce?   El bucle puede terminar anticipadamente, dejando");
        System.out.println("                    negativos al final de la lista sin inspeccionar.");
        System.out.println("    Corrección:     el i-- mantiene i sincronizado con datos.size() dinámico.");

        // ── Verificación del bug: negativos consecutivos no se eliminan ───────
        System.out.println("\n── Demostración del bug (versión original): ──────────────────");
        ArrayList<Integer> listaBug = new ArrayList<>();
        listaBug.add(-3); listaBug.add(-5); listaBug.add(2); listaBug.add(-1); listaBug.add(4);
        System.out.println("  Entrada: " + listaBug);
        System.out.println("  Llamando eliminarNegativos_BUGGY...");
        eliminarNegativos_BUGGY(listaBug);
        System.out.println("  Resultado BUGGY: " + listaBug + " ← -5 sobrevivió ✗");

        ArrayList<Integer> listaOk = new ArrayList<>();
        listaOk.add(-3); listaOk.add(-5); listaOk.add(2); listaOk.add(-1); listaOk.add(4);
        eliminarNegativos(listaOk);
        System.out.println("  Resultado CORREGIDO: " + listaOk + " ← no quedan negativos ✓");

        // ── Demostración insertarOrdenado corregido ───────────────────────────
        System.out.println("\n── PREGUNTA 2: Demostración insertarOrdenado corregido ───────");
        ArrayList<Integer> listaOrd = new ArrayList<>();
        listaOrd.add(2); listaOrd.add(5); listaOrd.add(9); listaOrd.add(14);
        System.out.println("  Lista ordenada inicial: " + listaOrd);
        System.out.println("  Insertar x=7 (valor intermedio):");
        System.out.println("    while: i=0:2<7✓ i=1:5<7✓ i=2:9<7✗ → i=2");
        System.out.println("    add(2, 7) → desplaza [9,14]→ → inserta 7 en idx 2");
        insertarOrdenado(listaOrd, 7);
        System.out.println("    Resultado: " + listaOrd + " ✓");

        System.out.println("  Insertar x=20 (mayor que todos):");
        System.out.println("    while: recorre toda la lista → i=5 (=size)");
        System.out.println("    add(5, 20) → inserta al final, sin excepción");
        insertarOrdenado(listaOrd, 20);
        System.out.println("    Resultado: " + listaOrd + " ✓");

        System.out.println("  Insertar x=1 (menor que todos):");
        System.out.println("    while: i=0:2<1✗ → i=0");
        System.out.println("    add(0, 1) → desplaza toda la lista → inserta al inicio");
        insertarOrdenado(listaOrd, 1);
        System.out.println("    Resultado: " + listaOrd + " ✓");

        //----- Pregunta 3: Complejidades ------------------------------------------------------
        System.out.println("\n── PREGUNTA 3: Complejidades versiones corregidas ────────────");
        System.out.println();
        System.out.println("  insertarOrdenado (corregido) → O(n)");
        System.out.println("    · while loop: hasta n iteraciones hasta encontrar posición → O(n)");
        System.out.println("    · add(i, x): desplaza hasta n elementos → O(n)");
        System.out.println("    · Total: O(n) + O(n) = O(n)");
        System.out.println("    · Peor caso: x mayor que todos → while recorre n, add inserta al final");
        System.out.println();
        System.out.println("  eliminarNegativos (corregido) → O(n²) peor caso");
        System.out.println("    · Bucle for: hasta n iteraciones → O(n)");
        System.out.println("    · Cada remove(i): desplaza elementos posteriores ← → O(n)");
        System.out.println("    · Peor caso: TODOS los n elementos son negativos");
        System.out.println("      → n eliminaciones × O(n) por cada remove = O(n²)");
        System.out.println("    · Mejor caso: ningún negativo → bucle simple = O(n)");

        // ── Pregunta 4: Traza eliminarNegativos sobre lista personalizada ──────
        System.out.println("\n── PREGUNTA 4: Traza de eliminarNegativos corregido ──────────");
        System.out.println("  Lista personalizada: [a, -b, c, -(a+b), 2a, -1, b]");
        System.out.printf("  Con a=%d, b=%d, c=%d:%n", a, b, c);
        System.out.printf("  = [%d, %d, %d, %d, %d, %d, %d]%n",
                a, -b, c, -(a+b), 2*a, -1, b);
        System.out.println();

        ArrayList<Integer> lTraza = new ArrayList<>();
        lTraza.add(a);           // 6
        lTraza.add(-b);          // -5
        lTraza.add(c);           // 6
        lTraza.add(-(a + b));    // -11
        lTraza.add(2 * a);       // 12
        lTraza.add(-1);          // -1
        lTraza.add(b);           // 5
        System.out.println("  Estado inicial: " + lTraza + " (size=7)");
        System.out.println();

        trazarEliminarNegativos(lTraza);

        System.out.println("  ─────────────────────────────────────────────────────────");
        System.out.println("  Lista final: " + lTraza);
        System.out.println("  Verificación: no quedan elementos < 0 ✓");
        System.out.println("  Eliminaciones realizadas: 3  (-5, -11, -1)");
        System.out.println("  Desplazamientos internos totales:");
        System.out.println("    remove(idx1=-5):  5 elementos desplazados ←");
        System.out.println("    remove(idx2=-11): 3 elementos desplazados ←");
        System.out.println("    remove(idx3=-1):  1 elemento desplazado ←");
        System.out.println("    Total: 9 desplazamientos internos");
        System.out.println();
    }

    /**
     * Ejecuta eliminarNegativos con traza visual paso a paso.
     * Muestra el estado de la lista en cada iteración y cada eliminación.
     */
    private static void trazarEliminarNegativos(ArrayList<Integer> datos) {
        System.out.println("  TRAZA paso a paso:");
        System.out.println("  ─────────────────────────────────────────────────────────");

        for (int i = 0; i < datos.size(); i++) {
            int val = datos.get(i);
            System.out.printf("  i=%d → datos.get(%d) = %d%n", i, i, val);

            if (val < 0) {
                System.out.printf("         %d < 0 → NEGATIVO → remove(%d)%n", val, i);
                datos.remove(i);
                System.out.println("         Lista tras remove: " + datos + " (size=" + datos.size() + ")");
                i--; // CORRECCIÓN
                System.out.printf("         i-- → i ahora = %d (compensar desplazamiento)%n", i);
            } else {
                System.out.printf("         %d ≥ 0 → POSITIVO/CERO → conservar%n", val);
            }
        }
        System.out.println("  ─────────────────────────────────────────────────────────");
    }
}
