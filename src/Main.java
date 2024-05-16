import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Ingrese un numero: ");
        int num = sc.nextInt();


        int resulFactorial = factorial(num);
        System.out.println("El factorial de " + num + " es " + resulFactorial);
    }

    public static int factorial(int numero){
        if(numero <= 1)
            return 1;
        else
            return numero * factorial(numero - 1);
    }
}