package com.example.crypto;


public class HillCipher {
    public static boolean isSquare(int number) {
        double sqrt = Math.sqrt(number);
        return sqrt == Math.floor(sqrt);
    }

    static String encrypt(String message, String key) {

        int size = key.length();
        if (isSquare(size)) {
            int matrixSize = (int) Math.sqrt(size);
            int[][] keyMatrix = new int[matrixSize][matrixSize];
            int indice = 0;
            for (int i = 0; i < matrixSize; i++) {
                for (int j = 0; j < matrixSize; j++) {
                    keyMatrix[i][j] = (int) key.charAt(indice);
                    indice++;
                }
            }


            // check if det = 0
          if ( validateDeterminant(keyMatrix, matrixSize)) {


              int[][] messageVector = new int[matrixSize][1];
              String CipherText = "";
              int[][] cipherMatrix = new int[matrixSize][1];
              int j = 0;
              while (j < message.length()) {
                  for (int i = 0; i < matrixSize; i++) {
                      if (j >= message.length()) {
                          messageVector[i][0] = 88;
                      } else {
                          messageVector[i][0] = (message.charAt(j));
                      }
                      //      System.out.println("plain text : "+messageVector[i][0]);
                      j++;
                  }
                  int x, i;
                  for (i = 0; i < matrixSize; i++) {
                      cipherMatrix[i][0] = 0;

                      for (x = 0; x < matrixSize; x++) {
                          cipherMatrix[i][0] += keyMatrix[i][x] * messageVector[x][0];
                      }

                      cipherMatrix[i][0] = cipherMatrix[i][0] % 256;
                      // print values of the cipher message in
                      //   System.out.println("cipher : " +cipherMatrix[i][0]);
                  }
                  for (i = 0; i < matrixSize; i++) {
                      CipherText += (char) (cipherMatrix[i][0]);
                  }
              }
              return CipherText;
          } else return   "Invalid key ! ";
        } else {
            return "Invalid key ! ";
        }

    }


    // Determinant calculator
    public static int determinant(int[][] a, int n) {
        int det = 0, sign = 1, p = 0, q = 0;

        if (n == 1) {
            det = a[0][0];
        } else {
            int[][] b = new int[n - 1][n - 1];
            for (int x = 0; x < n; x++) {
                p = 0;
                q = 0;
                for (int i = 1; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        if (j != x) {
                            b[p][q++] = a[i][j];
                            if (q % (n - 1) == 0) {
                                p++;
                                q = 0;
                            }
                        }
                    }
                }
                det = det + a[0][x] * determinant(b, n - 1) * sign;
                sign = -sign;
            }
        }
        return det;
    }

    // Function to implement Hill Cipher

    // calculate detrminate 
    static boolean validateDeterminant(int[][] keyMatrix, int n) {
        if (determinant(keyMatrix, n) % 256 == 0) {
           return false;
        } else {
            return true ;
        }
    }

}