package edu.eci.arsw.primefinder;
public class PrimeFinderThread extends Thread {

    private int a, b;
    private List<Integer> primes;
    private PauseControl control;

    public PrimeFinderThread(int a, int b, PauseControl control) {
        this.primes = new LinkedList<>();
        this.a = a;
        this.b = b;
        this.control = control;
    }

    @Override
    public void run() {
        for (int i = a; i < b; i++) {

            control.awaitIfPaused();

            if (isPrime(i)) {
                primes.add(i);
            }
        }
    }

    public List<Integer> getPrimes() {
        return primes;
    }

    boolean isPrime(int n) {
        if (n <= 1) return false;
        if (n == 2) return true;
        if (n % 2 == 0) return false;
        for (int i = 3; i * i <= n; i += 2)
            if (n % i == 0) return false;
        return true;
    }
}
