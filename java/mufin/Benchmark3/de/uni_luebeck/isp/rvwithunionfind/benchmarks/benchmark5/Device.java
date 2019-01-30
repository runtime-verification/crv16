package de.uni_luebeck.isp.rvwithunionfind.benchmarks.benchmark5;

/**
 * A device with two operation modes. The device can can create a workpiece that
 * depends on the current operation mode. It can be processed further only if
 * the device is in the corresponding mode again.
 *
 * @author Normann Decker <decker@isp.uni-luebeck.de>
 */
public class Device {

    public void toggleMode() {
    }

    public Piece createPiece() {
        return new Piece();
    }

}
