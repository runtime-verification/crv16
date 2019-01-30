package de.uni_luebeck.isp.rvwithunionfind.benchmarks.benchmark4.multiplexer;

/**
 * A Multiplexer consists of four channels. Only one if the four channels is
 * active. Call {@link #nextChannel()} to activate the next channel. The
 * channels are activated in a cyclic manner.
 * 
 * On the outer side the multiplexer is connected to four streams. Calling
 * {@link #renewChannel()} closes the stream of the currently active channel and
 * directly opens up a new stream.
 * 
 * On the inner side many clients can be connected to the multiplexer. A new
 * client is created through a call of {@link #getClient()}. Such a client is
 * then fixed to the current channel and must not used if another channel is
 * active. Once the channel of a client gets renewed the client is invalid and
 * must not be used any more.
 * 
 * @author Malte Schmitz (Institute for Software Engineering and Programming
 *         Languages, Universitaet zu Luebeck)
 *
 */
public class Multiplexer {
	private static int lastChannel = 0;

	public Multiplexer() {
		for (int i = 0; i < channels.length; i++) {
			channels[i] = ++lastChannel;
		}
	}

	public void renewChannel() {
		channels[0] = ++lastChannel;
	}

	public void nextChannel() {
		int oldChannel = channels[0];
		for (int i = 0; i < channels.length - 1; i++) {
			channels[i] = channels[i + 1];
		}
		channels[channels.length - 1] = oldChannel;
	}

	public Client client() {
		return new Client(this);
	}

	int[] channels = new int[4];
}
