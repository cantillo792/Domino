
package domino;

import java.util.LinkedList;
import java.util.Random;

public class Maquina {

    private final String mNombre;
    private final LinkedList<Ficha> fichas;
    private final LinkedList<Ficha> tablero;

    public Maquina(String mNombre, LinkedList<Ficha> fichasJ) {
       
        this.mNombre = mNombre;
        this.fichas = new LinkedList<>();
        this.tablero = new LinkedList<>();
        repartirFichas(fichasJ);
    }

    private void repartirFichas(LinkedList<Ficha> fichasJ) {
        Random random = new Random();
        int numA, numB;
        for (int i = 0; i < 7; i++) {
            Ficha ficha, fichaAux;
            do {
                numA = random.nextInt(7);
                numB = random.nextInt(7);
                ficha = new Ficha(numA, numB);
                fichaAux = new Ficha(numB, numA);
            } while (fichas.contains(ficha) || fichas.contains(fichaAux) || fichasJ.contains(ficha) || fichasJ.contains(fichaAux));
            fichas.add(ficha);
        }
    }

    public void mostrarFichas() {
        System.out.println("Fichas de la " + mNombre + "\n\n");
        if(!fichas.isEmpty()) {
            
            for (Ficha f : fichas) {
                System.out.print("[" + f.getNumA() + " - " + f.getNumB() + "]");
            }
            System.out.println("\n\n");
        } else{
           
            System.out.println("No Hay Fichas!");
        }
    }

    public boolean sinFichas() {
        return this.fichas.isEmpty();
    }

    public Ficha getDobleFicha() {
        
        int numA, numB;
        for (Ficha f : fichas) {
            numA = f.getNumA();
            numB = f.getNumB();
            if (numA == numB) {
                return f;
            }
        }

        return null;
    }
    
    public void jugarPrimero(Ficha ficha) {
        
        ponerFichaIzq(ficha);
        this.fichas.remove(ficha);
    }

    public boolean jugar() {
        int iter = 0;
        boolean fichaValida;
        Random random = new Random();
        int rdPos;
        Ficha ficha, fichaAux;
        do {
            iter++;
            fichaValida = false;
            rdPos = random.nextInt(this.fichas.size());
            ficha = this.fichas.get(rdPos);
            fichaAux = getExtremoIzq();
            if (ficha.getNumA() == fichaAux.getNumA()) {
                ponerFichaIzq(new Ficha(ficha.getNumB(), ficha.getNumA()));
                this.fichas.remove(ficha);
                fichaValida = true;
            } else if (ficha.getNumB() == fichaAux.getNumA()) {
                ponerFichaIzq(new Ficha(ficha));
                this.fichas.remove(ficha);
                fichaValida = true;
            }

            if (!fichaValida) {
                fichaAux = getExtremoDer();
                if (ficha.getNumA() == fichaAux.getNumB()) {
                    ponerFichaDer(new Ficha(ficha));
                    this.fichas.remove(ficha);
                    fichaValida = true;
                } else if (ficha.getNumB() == fichaAux.getNumB()) {
                    ponerFichaDer(new Ficha(ficha.getNumB(), ficha.getNumA()));
                    this.fichas.remove(ficha);
                    fichaValida = true;
                }
            }
            
            if(iter > this.fichas.size() && !fichaValida) {
               
                return false;
            }
        } while (!fichaValida);
        
        return true;
    }
    
    public int sumaPuntos() {
        int contador = 0;
        for(Ficha f : fichas) {
            contador += f.getNumA() + f.getNumB();
        }
        return contador;
    }

  
    public void ponerFichaIzq(Ficha ficha) {
        this.tablero.addFirst(ficha);
    }
    
    public void ponerFichaDer(Ficha ficha) {
        this.tablero.addLast(ficha);
    }

    public void mostrarTablero() {
        for (Ficha f : tablero) {
            System.out.print("[" + f.getNumA() + " - " + f.getNumB() + "]");
        }
        System.out.println("\n\n");
    }

    public Ficha getExtremoIzq() {
        return this.tablero.getFirst();
    }

    public Ficha getExtremoDer() {
        return this.tablero.getLast();
    }
}