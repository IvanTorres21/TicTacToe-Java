package tres_raya;

import java.util.Scanner;

/**
 *
 * @author ivan
 */
public class TresRaya {

  public static void main(String[] args) {

    Scanner s = new Scanner(System.in);
    int[][] map = new int[3][3];
    boolean playing = true;
    int pos = 1;
    for (int i = 0; i < 3; i++) { //Bucle para indicar al jugador como se llaman las posciones
      System.out.print(" |");
      for (int j = 0; j < 3; j++) {

        System.out.print(pos + "|");
        pos++;
      }
      System.out.println();
    }
    int turnAct = 0; //Turno actual, los impares son el usuario, los pares el ordenador.
    do { //Bucle de juego

      turnAct++;
      if (turnAct % 2 == 1) {
        System.out.println("Introduzca la posición en la que quiere poner su pieza: ");
        int opt = Integer.parseInt(s.nextLine());
        while (opt < 1 || opt > 9 || !isEmpty(map, opt)) { //Comprobamos que se pueda poner en esa posición

          System.out.println("Introduzca un valor valido (1-9): ");
          opt = Integer.parseInt(s.nextLine());
        }
        putInput(opt, map, turnAct);
      } else if (turnAct % 2 == 0) {
        System.out.println("Turno máquina: ");
        turnOrd(map, 2);
      }
      printMap(map);
    } while (playing && !isWon(map, turnAct) && turnAct < 10);
    //Miramos quien ha ganado
    if (turnAct % 2 == 0) {

      System.out.println("Gana el ordenador");
    } else {

      System.out.println("Gana el jugador");
    }
  }

  /**
   * Función para hacer una copia del mapa
   *
   * @param map Mapa que se va a copiar
   * @return copia del mapa
   */
  public static int[][] copyMap(int[][] map) {

    int[][] copy = new int[3][3];
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {

        copy[i][j] = map[i][j];
      }
    }
    return copy;
  }

  public static boolean turnOrd(int[][] map, int turnAct) {

    int[][] copy = copyMap(map); //Copiamos el mapa ya que tenemos que modificarlo
    int posChoosed = 0;
    int lastDrawPos = 0; //Para acordarnos de la última posición en la que empatabamos.
    int points = 0;
    for (int i = 1; i <= 9 && points != 2 && points != -1; i++) { //Pasamos por las nueve posiciones

      if (isEmpty(copy, i)) {

        //Ponemos la pieza ahí
        putInput(i, copy, turnAct);
        //Comprobamos si hemos ganado con ese movimiento o si hemos perdido
        if (isWon(copy, turnAct)) {

          posChoosed = i;
          points = 2; //En caso de que hayamos ganado le asignamos 2 puntos
          if (turnAct % 2 != 0) {
            
            return true; //En caso de que comprobemos si gana el jugador devolvemos verdadero
          }
        } else {

          points = 0; //En caso de que no importe el momivimento le asignamos 0 puntos y seguimos comprobando
          if (turnAct % 2 == 0) {

            if (turnOrd(copy, 1)) {
              points = -1;
            }
          }
          if (points != -1) {
            
            lastDrawPos = i;
          }
        }
        putInput(i, copy, -1);
      }
    }
    if (points == 0 || points == -1) {

      posChoosed = lastDrawPos;
    }
    if (posChoosed == 0) {
      
      do {
        
        posChoosed = (int) (Math.random()*8) +1;
      } while (!isEmpty(copy, posChoosed));
    }
    if (turnAct % 2 == 0) {

      putInput(posChoosed, map, 2);
    }
    return false;
  }

  /**
   * Función que imprime el mapa una vez que ha empezado el juego
   *
   * @param map El mapa de juego
   */
  public static void printMap(int[][] map) {

    char toPrint = ' ';
    for (int i = 0; i < 3; i++) {
      System.out.print(" |");
      for (int j = 0; j < 3; j++) {

        switch (map[i][j]) { //Comprobamos el valor de la posición para imprimir un caracter u otro

          case -1:

            toPrint = 'X';
            break;
          case 0:

            toPrint = ' ';
            break;
          case 1:

            toPrint = 'O';
            break;
          default:
        }
        System.out.print(toPrint + "|");
      }
      System.out.println();
    }
  }

  /**
   * Función para convertir la opción del usuario en una posición en el array
   *
   * @param pos Opción del usuario
   * @return La posición en un array, el [0] es y y el [1] es x
   */
  public static int[] getPos(int pos) {

    int[] position = new int[2];
    switch (pos) {

      case 1:

        position[0] = 0;
        position[1] = 0;
        break;
      case 2:

        position[0] = 0;
        position[1] = 1;
        break;
      case 3:

        position[0] = 0;
        position[1] = 2;
        break;
      case 4:

        position[0] = 1;
        position[1] = 0;
        break;
      case 5:

        position[0] = 1;
        position[1] = 1;
        break;
      case 6:

        position[0] = 1;
        position[1] = 2;
        break;
      case 7:

        position[0] = 2;
        position[1] = 0;
        break;
      case 8:

        position[0] = 2;
        position[1] = 1;
        break;
      case 9:

        position[0] = 2;
        position[1] = 2;
        break;
      default:
    }
    return position;
  }

  /**
   * Función que coloca el input del usuario o la máquina en su posición
   *
   * @param pos Posicion en la que se va a colocar (1 - 9)
   * @param map Mapa en el que se va a colocar el input
   * @param turnAct Turno actual, para saber quien es el jugador. Par es máquina, impar es jugador.
   */
  public static void putInput(int pos, int[][] map, int turnAct) {

    int valJug = 0; //Variable que sirve para poner X o O, dependiendo de quien sea
    if (turnAct == -1) {

      valJug = 0;
    } else if (turnAct % 2 == 0) {

      valJug = -1;
    } else {

      valJug = 1;
    }
    int[] posis = getPos(pos); //Conseguimos la posisciones en las que se quiere poner
    map[posis[0]][posis[1]] = valJug; //Ponemos 1 o -1 dependiendo de quien este poniendolo

  }

  /**
   * Función que comprueba que la posición que se está mirando este vacía
   *
   * @param map Mapa en el que se comprueba la posición
   * @param pos Posición que se está comprobrando
   * @return True si está vacia, false si no lo está.
   */
  public static boolean isEmpty(int[][] map, int pos) {

    int[] posis = getPos(pos); //Conseguimos la posisciones en las que se quiere poner
    if (map[posis[0]][posis[1]] != 0) {

      return false;
    }
    return true;
  }

  /**
   * Función para comprobar si se ha ganado el juego
   *
   * @param map Mapa en el que se comprueba
   * @param turnAct Turno actual, para saber quien se tiene que ocmprobar
   * @return True si se ha ganado, False si no
   */
  public static boolean isWon(int[][] map, int turnAct) {

    int valJug = 0; //Variable que sirve para poner X o O, dependiendo de quien sea
    if (turnAct % 2 == 0) { //Determinamos quien está jugando ahora

      valJug = -1;
    } else {

      valJug = 1;
    }
    int cont = -1; //Tiene que alcanzar 2 para que se haya ganado
    //Prueba horizontal
    for (int i = 0; i < 3 && cont != 2; i++) {
      cont = -1;
      for (int j = 0; j < 3; j++) {

        if (map[i][j] == valJug) {
          cont++;
        }
      }
    }
    if (cont != 2) {

      cont = -1;
    }
    //Prueba vertical
    for (int i = 0; i < 3 && cont != 2; i++) {
      cont = -1;
      for (int j = 0; j < 3; j++) {

        if (map[j][i] == valJug) {

          cont++;
        }
      }
    }
    if (cont != 2) {

      cont = -1;
    }
    //Prueba diagonal 1
    for (int i = 0; i < 3 && cont != 2; i++) {

      if (map[i][i] == valJug) {

        cont++;
      }
    }
    if (cont != 2) {

      cont = -1;
    }
    //Prueba diagonal 2
    for (int i = 2; i >= 0 && cont != 2; i--) {

      if (map[i][i] == valJug) {

        cont++;
      }
    }
    if (cont != 2) {

      cont = -1;
    }
    //Devolvemos valor
    if (cont == 2) {

      return true;
    } else {

      return false;
    }
  }
}
