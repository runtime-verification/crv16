package de.uni_luebeck.isp.rvwithunionfind.benchmarks.benchmark5;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        benchmark(true);
    }

    public static void benchmark(boolean shallFail) {

        Device myDevice = new Device();
        Device myDevice2 = new Device();
        Piece firstPiece = myDevice.createPiece();
        Piece secondPiece = myDevice2.createPiece();

        Set<Piece> lotsOfPieces = new HashSet<>();

        Random rnd = new Random(1);

        for (int i = 0; i < 5000; i++) {
            if (!rnd.nextBoolean()) {
                for (int j = 0; j < 10; j++) {
                    lotsOfPieces.add(myDevice.createPiece());
                }
            } else {
                myDevice.toggleMode();
            }
        }

        for (int i = 0; i < 10000; i++) {
            if (!rnd.nextBoolean()) {
                for (int j = 0; j < 10; j++) {
                    lotsOfPieces.add(myDevice2.createPiece());
                }
            } else {
                myDevice2.toggleMode();
            }
        }

        for (int i = 0; i < 5000; i++) {
            if (!rnd.nextBoolean()) {
                for (int j = 0; j < 10; j++) {
                    lotsOfPieces.add(myDevice.createPiece());
                }
            } else {
                myDevice.toggleMode();
            }
        }

        System.out.println("Hello " + System.getProperty("user.name") + "! :)");

        // This is okay (no. of toggles on myDevice is even)
        firstPiece.process();

        // This is okay (no. of toggles on myDevice2 is now even)
        secondPiece.process();
        
        myDevice2.toggleMode();
        if (shallFail) {
            // This fails (no. of toggles on myDevice2 is odd)
            secondPiece.process();
        }
    }
}
