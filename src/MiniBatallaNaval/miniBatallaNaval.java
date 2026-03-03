package MiniBatallaNaval;
import java.util.Random;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class miniBatallaNaval {
	
	static Scanner input = new Scanner (System.in); //Agregamos Scanner global
	static ImageIcon iconoAcierto = new ImageIcon (miniBatallaNaval.class.getResource("/imagenes/Impacto.png"));
	static ImageIcon iconoAgua = new ImageIcon (miniBatallaNaval.class.getResource("/imagenes/Agua.png"));
	static ImageIcon victoria = new ImageIcon (miniBatallaNaval.class.getResource("/imagenes/Victoria.png"));
	static ImageIcon derrota = new ImageIcon (miniBatallaNaval.class.getResource("/imagenes/Derrota.png"));
	static ImageIcon turnoJugador = new ImageIcon (miniBatallaNaval.class.getResource("/imagenes/TurnoJugador.png"));
	static ImageIcon turnoPc = new ImageIcon (miniBatallaNaval.class.getResource("/imagenes/TurnoPC.png"));
	static ImageIcon tableroParcial = new ImageIcon (miniBatallaNaval.class.getResource("/imagenes/TableroParcial.png"));



	
    static final int TAM = 6; // Tamaño del tablero: 6x6
    static char[][] tableroJugador = new char[TAM][TAM];
    static char[][] tableroCompu = new char[TAM][TAM];
	static final int barcos = 3; //cantidad de barcos en el tablero	
	static final int largoBarcos = 3; //largo de los barcos
	static int aciertosComputadora = 0;
    static int aciertosJugador = 0;

    
        
    static boolean [][] revelado = new boolean [TAM][TAM];// variable para saber si una casilla de la PC ya fue atacada
    static boolean [][] reveladoJugador = new boolean [TAM][TAM];//variable para saber si una casilla del jugador fue atacada

    public static void main(String[] args) {
    	
    	
        tableros(tableroJugador);    // llena con '□'
        tableros(tableroCompu);      // llena con '□'

        
        System.out.println("\nTablero de la computadora:");
        mostrarTableros(tableroCompu); // esta función mostrará el tableroCompu
        System.out.println();
        
        colocarBarcosJugador();
        colocarBarcosPC();
        
      
        
        do {           //Bucle Principal donde sucede el juego. 	         
	         
	        turnoJugador(); //esta funcion inicia el turno del jugador
	        
	            
	        if(aciertosJugador == 9) {
	        break;
	        }
	            
	        
	            
	        turnoPC(); //esta funcion inicia el turno de la PC
	            
	        if(aciertosComputadora == 9) {
	        break;
	        }     
	        
			JOptionPane.showMessageDialog(null, "Jugador: " + aciertosJugador  + "\n"+ "Computadora: " + aciertosComputadora , "RESULTADO PARCIAL", JOptionPane.INFORMATION_MESSAGE, tableroParcial);
			
			
			mostrarTableroDialogo(tableroCompu, revelado);
			mostrarTableroDialogoPC(tableroJugador, reveladoJugador);
	        

	        
	        
        } while (aciertosJugador < 9 && aciertosComputadora < 9);
        
        //Como el bucle anterior se rompe cuando alguno llega a 9 aciertos, no llega a mostrar el resultado parcial. Por eso lo mostramos por ultima vez antes del cartel de Victoria/Derrota.
		JOptionPane.showMessageDialog(null, "Jugador: " + aciertosJugador  + "\n"+ "Computadora: " + aciertosComputadora , "RESULTADO PARCIAL", JOptionPane.INFORMATION_MESSAGE, tableroParcial); 

        if (aciertosComputadora == 9) {	
			JOptionPane.showMessageDialog(null, null, "DERROTA", JOptionPane.INFORMATION_MESSAGE, derrota);
        	
        	
        } else if (aciertosJugador == 9) {
			JOptionPane.showMessageDialog(null, null , "VICTORIA", JOptionPane.INFORMATION_MESSAGE, victoria);
        	
        }
        
    }//llave cierre Funcion
    
	static void tableros(char [][] tablero) { //funcion para armar el tablero
		for(int i = 0; i < TAM ; i ++ ) { //filas
			for(int j = 0; j < TAM; j ++ ) {// columnas
				tablero [i][j] = '□';
			}
		}	
	}


    // Muestra cualquier tablero que se le pase
	static void mostrarTableros(char [][] tableros) { //funcion para mostrar el tablero
		for(int i = 0; i<TAM; i++) { //filas 
			for(int j=0; j<TAM; j++) { //columnas
				System.out.print(tableros [i][j] +" "); 
			}System.out.println();
		}
	}
    
	static void colocar(char[][] tablero, int fila, int columna, char direccion) {
		if (direccion == 'H') {
			for(int j = columna; j < columna + largoBarcos ; j ++) {
				tablero[fila][j] = '*'; //coloca barco horizontalmente
			}
		}else {
			for(int i = fila; i < fila + largoBarcos; i++) {
				tablero[i][columna] = '*'; //coloca barco verticalemnte
			}
		}		
	}

	static boolean puedeColocar(char[][] tablero, int fila, int columna, char direccion) {  
		if (direccion == 'H') { 
			if(columna + largoBarcos > TAM) return false;
				for(int j = columna; j < columna + largoBarcos; j ++ ) {  
					if(tablero[fila][j] == '*') return false; 
				}
			
		}else if (direccion == 'V') { 
			if (fila + largoBarcos > TAM) return false; 
				for(int i = fila; i < fila + largoBarcos; i ++ ) { 
				if(tablero[i][columna] == '*') return false; 
			}
			
		}else {
			return false; 
		}
		return true;
	}
	
	static void colocarBarcosJugador() {
		 Random random = new Random(); //random para numeros aleatorios
		    int fila, columna;
		    int direccion;
		    char dirChar;

		    for (int i = 0; i < barcos; i++) { //se repite tantas veces como barcos tengas que colocar (3)
		        boolean colocado = false; //booleano para indicar que el barco fue colocado correctamente
		        while (!colocado) { //mientras no se haya colocado sigue probando
		            fila = random.nextInt(TAM);         // fila aleatoria entre 0 y 5
		            columna = random.nextInt(TAM);      // columna aleatoria entre 0 y 5
		            direccion = random.nextInt(2);      // 0 = H, 1 = V

		            if (direccion == 0) {
		                dirChar = 'H';
		            } else {
		                dirChar = 'V';
		            }

		            if (puedeColocar(tableroJugador, fila, columna, dirChar)) {
		                colocar(tableroJugador, fila, columna, dirChar);
		                colocado = true;
		            }
		        }
		    }

		    // Mostrar tablero del jugador en consola 
		    System.out.println("Barcos del jugador colocados aleatoriamente:");
		    mostrarTableros(tableroJugador);
		}
	
	
	static void colocarBarcosPC() {
		Random random = new Random();
		int fila, columna, direccion;
		
		for (int i = 0; i<barcos;i++) {
			boolean colocado = false;
			while (!colocado) {
				fila = random.nextInt(TAM);
				columna = random.nextInt(TAM);
				direccion = random.nextInt(2);
				
				if(puedeColocarPc(tableroCompu, fila, columna, direccion)) { 
	        		colocarPc(tableroCompu, fila, columna, direccion);
	        		colocado = true;
			}
		} 
			
		}//Repite el bucle hasta que la PC ponga todos los barcos
		
	} //cierre llave Funcion

	static boolean puedeColocarPc(char[][] tablero, int fila, int columna, int direccion) { //funcion boleana, devuelve true o false, 
		if (direccion == 0) { //si la PC elige ubicarlo de manera horizontal
			if(columna + largoBarcos > TAM) return false;//verifica que el barco no se pase del borde del tablero horizontalmente
				for(int j = columna; j < columna + largoBarcos; j ++ ) { //recorre todas las columnas donde se colocaria el barco 
					if(tablero[fila][j] == '*') return false; //si alguna de las casillas tiene *, no permite colocarlo
				}
				
		}else if (direccion == 1) { //si el usuario elige ubicarlo de manera vertical
			if (fila + largoBarcos > TAM) return false; //verifica que el barco no se pase del borde del tablero verticalmente
				for(int i = fila; i < fila + largoBarcos; i ++ ) { //recorre las filas del tablero donde iria el barco
				if(tablero[i][columna] == '*') return false; // si alguna casilla tiene *, no permite colocar el barco
			}
				
		}else {
			return false; // si la direccion no es ni V ni H, lo considera invalido y devuelve false 
		}
		return true; //si pasa todo puede colocar el barco 
	}//Cierre llave Funcion
	
	static void colocarPc(char[][] tablero, int fila, int columna, int direccion) {
		if (direccion == 0) {
			for(int j = columna; j < columna + largoBarcos ; j ++) {
				tablero[fila][j] = '*'; //coloca barco horizontalmente
			}
		}else {
			for(int i = fila; i < fila + largoBarcos; i++) {
				tablero[i][columna] = '*'; //coloca barco verticalemnte
			}
		}		
	}// Cierre llave funcion
		
    
    static void mostrarTableroJugador(char[][] tablero, boolean [][] revelado) { //Pasa a String el tablero del jugador para poder mostrar por JOptionPane
    	String texto = "";
        // Cabecera de columnas
        texto += "   ";
        for (int j = 0; j < TAM; j++) {
            texto += "  "+ j + " ";
        }
        texto += "\n";
        // Filas
        for (int i = 0; i < TAM; i++) {
            texto += i + "  ";
            for (int j = 0; j < TAM; j++) {
                
            }
            texto += "\n";
        }
        // Mostramos todo junto en un JOptionPane
        JOptionPane.showMessageDialog(
            null,
            texto,
            "Tablero del Jugador",
            JOptionPane.INFORMATION_MESSAGE
        );
    }//llave cierre final
    
    static void mostrarTableroDialogo(char[][] tablero, boolean[][] revelado) { //Pasa a String el tablero parcial para poder mostrar al jugador por JOptionPane
        String texto = "";
        // Cabecera de columnas
        texto += "   ";
        for (int j = 0; j < TAM; j++) {
            texto += "  "+ j + " ";
        }
        texto += "\n";
        // Filas
        for (int i = 0; i < TAM; i++) {
            texto += i + "  ";
            for (int j = 0; j < TAM; j++) {
                if (revelado[i][j]) {
                    // Si había barco ('*') mostramos 'X', si no, 'O'
                    texto += (tablero[i][j] == '*' ? " X " : " O ");
                } else {
                    texto += " □ ";
                }
            }
            texto += "\n";
        }
        // Mostramos todo junto en un JOptionPane
        JOptionPane.showMessageDialog(
            null,
            texto,
            "Tablero parcial de Ataque",
            JOptionPane.INFORMATION_MESSAGE
        );
    }//llave cierre final
    
    
	static void mostrarTableroDialogoPC(char[][] tablero, boolean[][] revelado) { //Pasa a String el tablero parcial para poder mostrar al jugador por JOptionPane
	    String texto = "";
	    // Cabecera de columnas
	    texto += "   ";
	    for (int j = 0; j < TAM; j++) {
	        texto += "  "+ j + " ";
	    }
	    texto += "\n";
	    // Filas
	    for (int i = 0; i < TAM; i++) {
	        texto += i + "  ";
	        for (int j = 0; j < TAM; j++) {
	            if (revelado[i][j]) {
	                // Si había barco ('*') mostramos 'X', si no, 'O'
	                texto += (tablero[i][j] == '*' ? " X " : " O ");
	            } else {
	                texto += " □ ";
	            }
	        }
	        texto += "\n";
	    }
	    // Mostramos todo junto en un JOptionPane
	    JOptionPane.showMessageDialog(
	        null,
	        texto,
	        "Tablero parcial de Defensa",
	        JOptionPane.INFORMATION_MESSAGE
	    );
	        
	    }//cierre llave Funcion
    
    
    //Turno del jugador
    
    static void turnoJugador () {
    	

    	
    	int fila, columna;
    	boolean validacionFila = false;
    	boolean validacionColumna = false;
    	boolean validacionCasilla = false;
    	String pedirFilas, pedirColumnas;
    	    	
    	
    	do { //El bucle principal, donde el jugador ingresa las coordenadas y se hacen las validaciones.
    		
	    	do {																			//Validamos que los datos ingresados esten en rango.
    			//JOptionPane.showMessageDialog(null, null, "Tu Turno", JOptionPane.INFORMATION_MESSAGE, turnoJugador);
	    		pedirFilas = (String) JOptionPane.showInputDialog(null, "Elija una fila (0 a " + (TAM - 1)+ "): ", "Tu Turno", JOptionPane.PLAIN_MESSAGE, turnoJugador, null, null); //Pide por JOptionPane la fila
	    		//System.out.print("Elija una fila (0 a " + (TAM - 1)+ "): ");
	    		fila = Integer.parseInt(pedirFilas);
	    		
	    		if (fila < 0 || fila > (TAM-1)) {
	    			JOptionPane.showInternalMessageDialog(null, "Coordenada Fuera de rango. Intenta de nuevo.", "ERROR", JOptionPane.OK_OPTION, null);
	    			//System.out.print("Coordenada Fuera de rango. Intenta de nuevo.");
	    			//System.out.println();
	    		}
	    		else {
	    			validacionFila = true;
	    		}
	    		    		
	    	} while (validacionFila == false);
	    	
	    	do {																			//Validamos que los datos ingresados esten en rango.
	    		
	    		pedirColumnas = (String) JOptionPane.showInputDialog(null, "Elija una columna (0 a " + (TAM - 1)+ "): ", "Tu Turno", JOptionPane.PLAIN_MESSAGE, turnoJugador, null, null); //Pide por JOptionPane la columna
	    		//System.out.print("Elija una columna (0 a " + (TAM - 1)+ "): ");
	    		columna = Integer.parseInt(pedirColumnas);
	    		
	    		if (columna < 0 || columna > (TAM-1)) {
	    			JOptionPane.showInternalMessageDialog(null, "Coordenada Fuera de rango. Intenta de nuevo.", "ERROR", JOptionPane.OK_OPTION, null);
	    			//System.out.print("Coordenada Fuera de rango. Intenta de nuevo.");
	    			//System.out.println();
	    		}
	    		else {
	    			validacionColumna = true;
	    		}
	    		    		
	    	} while (validacionColumna == false);
	    	
	    	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	    	
	    	if (!revelado [fila][columna]) { //Verificamos si la casilla ya estaba revelada
	    		revelado [fila][columna] = true; //Si no lo estaba, la revelamos
	    		validacionCasilla = true;
	    		
	    		if (tableroCompu [fila][columna] == '*') { //Si en la casilla habia un barco, se lo hacemos saber al jugador
	    			JOptionPane.showMessageDialog(null, null, "Le pegaste a un barco!", JOptionPane.INFORMATION_MESSAGE, iconoAcierto);
	    			//System.out.println("Le pegaste a un barco!");
	    			aciertosJugador++;
	    		}
	    		else {
	    			JOptionPane.showMessageDialog(null, null, "Agua..", JOptionPane.INFORMATION_MESSAGE, iconoAgua);
	    			//System.out.println("Agua.."); //Si en la casilla no habia nada
	    		}
	    	}
	    	
	    	else {
    			JOptionPane.showMessageDialog(null, "Ya atacaste esta casilla, intenta con otra", "Coordenada repetida", 2, null);
	    		//System.out.println("Ya atacaste esta casilla, intenta con otra");
	    	}
    	
    	} while (!validacionCasilla);
    	
    	
    	
    	
    }//llave cierre Funcion
    
    //Turno de la PC
    static void turnoPC () {
    	
    	Random random = new Random();
    	int fila, columna;
    	boolean validacionCasillas = false;
    	
    	JOptionPane.showMessageDialog(null, null, "TURNO DE LA COMPUTADORA", JOptionPane.INFORMATION_MESSAGE, turnoPc);
    	
    	do {			
    		fila = random.nextInt(TAM);
        	columna = random.nextInt(TAM);
        	
    		if (!reveladoJugador [fila][columna]) { //Verificamos si la casilla ya estaba revelada
	    		reveladoJugador [fila][columna] = true; //Si no lo estaba, la revelamos
	    		validacionCasillas = true;
	    		
	    		if (tableroJugador [fila][columna] == '*') { //Si en la casilla habia un barco, se lo hacemos saber al jugador
	    			JOptionPane.showMessageDialog(null, null, "Le pegaron a tu barco!", JOptionPane.INFORMATION_MESSAGE, iconoAcierto);
	    			//System.out.println("Le pegaron a tu Barco!");
	    			aciertosComputadora++;
	    		}
	    		else {
	    			JOptionPane.showMessageDialog(null, null , "Agua..", JOptionPane.INFORMATION_MESSAGE, iconoAgua);
	    			//System.out.println("Agua.."); //Si en la casilla no habia nada
	    		}
	    	}
    		
    		System.out.println();
	    	
	    	//El bucle se va a repetir hasta que la PC elija una casilla que aún no haya sido revelada.
    		
    	} while (!validacionCasillas);
    	
    	
    }//llave cierre Funcion
    
    
    
    
}//llave final