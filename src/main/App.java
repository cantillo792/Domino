package main;

import domino.Ficha;
import domino.Jugador;
import domino.Maquina;
import java.util.Scanner;

public class App {
    
    public static void main(String[] args) {

        Scanner sn = new Scanner(System.in);
        boolean fichaValida, pasoJugador, pasoMaquina;
        char opcion;
        int numRonda = 1; 

        Ficha ficha, fichaAux;
        Jugador j1 = new Jugador("Jugador 1");
        Maquina mq = new Maquina("IA", j1.getFichas());
        System.out.println(".:Bienvenido al Juego de Domino!:.");
        System.out.println(".:Jugador .vs. Máquina:.");
        mq.mostrarFichas();
        System.out.println("La Máquina Empieza...");
        mq.jugarPrimero(mq.getDobleFicha());
        System.out.println("La Máquina ha Jugado!");
        mq.mostrarFichas();

        do {
            fichaValida = pasoJugador = pasoMaquina = false;
            System.out.println("Tablero Actual - Ronda " + numRonda);
            mq.mostrarTablero();
            System.out.println("Turno del Jugador");
            j1.mostrarFichas();
            System.out.print("¿Desea pasar el Turno? [S/N]: ");
            opcion = sn.next().charAt(0);
            if (opcion == 'N' || opcion == 'n') {
                do {
                    System.out.print("Digite la Ficha a Escoger (Pos): ");
                    int pos = sn.nextInt();
                    if (pos > j1.getNumFichas()) {
                        fichaValida = false;
                    } else {
                        ficha = j1.getFicha(pos - 1);
                        fichaAux = mq.getExtremoIzq();
                        if (ficha.getNumA() == fichaAux.getNumA()) { 
                           
                            mq.ponerFichaIzq(new Ficha(ficha.getNumB(), ficha.getNumA()));
                          
                            j1.quitarFicha(ficha);
                            fichaValida = true;
                        } else if (ficha.getNumB() == fichaAux.getNumA()) {
                           
                            mq.ponerFichaIzq(new Ficha(ficha)); 
                            
                            j1.quitarFicha(ficha);
                            fichaValida = true;
                        }

                        if (!fichaValida) {
                          
                            fichaAux = mq.getExtremoDer();
                            if (ficha.getNumA() == fichaAux.getNumB()) {
                                
                                mq.ponerFichaDer(new Ficha(ficha));
                               
                                j1.quitarFicha(ficha);
                                fichaValida = true;
                            } else if (ficha.getNumB() == fichaAux.getNumB()) {
                                
                                mq.ponerFichaDer(new Ficha(ficha.getNumB(), ficha.getNumA())); 
                               
                                j1.quitarFicha(ficha);
                                fichaValida = true;
                            }
                        }

                        if (!fichaValida) {
                           
                            System.out.println("Ficha Invalida!");
                        }
                    }
                } while (!fichaValida);
                System.out.println("Tablero Actual");
                mq.mostrarTablero();
            } else {
                
                pasoJugador = true;
            }

            if(j1.getNumFichas() != 0) {
                System.out.println("Turno de la Maquina...");
                pasoMaquina = mq.jugar();
                if (!pasoMaquina) {
                    System.out.println("La Maquina Pasó el Turno!");
                    if (pasoJugador) {
                        
                        break;
                    }
                } else {
                    System.out.println("La Maquina Ha Jugado");
                    mq.mostrarFichas();
                }
            }
            System.out.println("\n-----------------------------------------------------------------------------\n");
            numRonda++;
        } while (j1.getNumFichas() > 0 && !mq.sinFichas());
        
        
        System.out.println("\n\n");
        System.out.println(".:JUEGO TERMINADO!:.");
        int puntosJ = j1.sumaPuntos();
        int puntosM = mq.sumaPuntos();
        if(puntosJ <= puntosM) {
           
            System.out.println("JUGADOR 1 HA GANADO!! FELICIDADES!!");
        } else{
           
            System.out.println("LA MAQUINA HA GANADO!! SUERTE LA PROXIMA!!");
        }
    }
}