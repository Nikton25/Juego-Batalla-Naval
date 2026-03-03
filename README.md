# Mini Batalla Naval - Java 

Es una versión simplificada del clásico juego de estrategia, enfocada en la lógica de programación estructurada y el manejo de interfaces gráficas básicas.

##  El Juego
El objetivo es hundir la flota enemiga (la computadora) antes de que ella hunda la tuya. 

### Reglas y Configuración:
- **Tablero:** 6x6.
- **Flota:** 3 barcos por bando.
- **Tamaño de barcos:** Cada barco ocupa 3 casillas (total de 9 aciertos para ganar).
- **Interacción:** El juego utiliza cuadros de diálogo (`JOptionPane`) para la entrada de datos y mostrar el estado del juego.

##  Tecnologías y Conceptos Aplicados
- **Lenguaje:** Java (JDK 17).
- **Interfaz:** Swing (`JOptionPane` e `ImageIcon` para feedback visual).
- **Lógica Estructurada:** Uso intensivo de funciones estáticas (`static`) para modularizar el código.
- **Estructuras de Datos:** - Matrices de caracteres (`char[][]`) para representar los tableros.
  - Matrices booleanas (`boolean[][]`) para gestionar la "niebla de guerra" (casillas reveladas).
- **Algoritmos:** Generación de posiciones aleatorias para la IA y validación de colisiones de barcos.

##  Ejecución
1. Clonar el repositorio.
2. Asegurarse de tener instalado **JDK 17**.
3. El proyecto incluye recursos visuales en la carpeta `/imagenes`.
4. Ejecutar la clase `miniBatallaNaval.java`.

---
**Nota académica:** Este proyecto fue realizado con fines pedagógicos para asentar las bases de la programación procedural antes de la transición a Objetos.
