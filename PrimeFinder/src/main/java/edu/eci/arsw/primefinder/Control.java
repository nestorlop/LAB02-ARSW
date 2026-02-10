public class Control extends Thread {

    private static final int NTHREADS = 3;
    private static final int MAXVALUE = 30000000;
    private static final int TMILISECONDS = 5000;

    private PrimeFinderThread[] pft;
    private PauseControl pauseControl;

    private Control() {
        pauseControl = new PauseControl();
        pft = new PrimeFinderThread[NTHREADS];

        int data = MAXVALUE / NTHREADS;
        for (int i = 0; i < NTHREADS; i++) {
            int start = i * data;
            int end = (i == NTHREADS - 1) ? MAXVALUE : (i + 1) * data;
            pft[i] = new PrimeFinderThread(start, end, pauseControl);
        }
    }

    public static Control newControl() {
        return new Control();
    }

    @Override
    public void run() {

        for (PrimeFinderThread t : pft) {
            t.start();
        }

        try {
            while (true) {
                Thread.sleep(TMILISECONDS);

                pauseControl.pause();

                int total = 0;
                for (PrimeFinderThread t : pft) {
                    total += t.getPrimes().size();
                }

                System.out.println("Primos encontrados: " + total);
                System.out.println("Presione ENTER para continuar...");
                System.in.read();

                pauseControl.resume();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
