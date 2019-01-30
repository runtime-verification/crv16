package de.uni_luebeck.isp.rvwithunionfind.benchmarks.benchmark4.multiplexer;

/**
 * Client of a multiplexer. A client is used by calling {@link #use()}. Using a
 * client is only allowed if the client's channel is currently active.
 * 
 * @author Malte Schmitz (Institute for Software Engineering and Programming
 *         Languages, Universitaet zu Luebeck)
 *
 */
public class Client {
	final int channel;
	final Multiplexer multiplexer;

	Client(Multiplexer multiplexer) {
		this.multiplexer = multiplexer;
		this.channel = multiplexer.channels[0];
	}

	public void use() {
		if (multiplexer.channels[0] != channel) {
			if (channelExists()) {
				throw new ChannelNotActiveException();
			} else {
				throw new ChannelInvalidException();
			}
		}
		// Imagine some code really using the channel here ...
	}

	private boolean channelExists() {
		for (int i = 0; i < multiplexer.channels.length; i++) {
			if (multiplexer.channels[i] == channel) {
				return true;
			}
		}
		return false;
	}
}
