package de.uni_luebeck.isp.rvwithunionfind.benchmarks.benchmark4;

import java.util.HashSet;
import java.util.Set;

import de.uni_luebeck.isp.rvwithunionfind.benchmarks.benchmark4.multiplexer.Client;
import de.uni_luebeck.isp.rvwithunionfind.benchmarks.benchmark4.multiplexer.Multiplexer;

/**
 * Benchmark using {@link Multiplexer}.
 * 
 * @author Malte Schmitz (Institute for Software Engineering and Programming
 *         Languages, Universitaet zu Luebeck)
 *
 */
public class MainBadChannelNotActive {
	public static void main(String[] args) {
		Multiplexer multiplexer1 = new Multiplexer();
		Multiplexer multiplexer2 = new Multiplexer();

		Set<Client> lotsOfClients = new HashSet<>();

		for (int i = 0; i < 250000; i++) {
			lotsOfClients.add(multiplexer1.client());
			lotsOfClients.add(multiplexer2.client());
		}
		Client client1 = multiplexer1.client();
		Client client2 = multiplexer2.client();
		multiplexer1.nextChannel();
		multiplexer2.nextChannel();

		for (int i = 0; i < 250000; i++) {
			lotsOfClients.add(multiplexer1.client());
			lotsOfClients.add(multiplexer2.client());
		}
		Client client3 = multiplexer1.client();
		Client client4 = multiplexer2.client();
		multiplexer1.nextChannel();
		multiplexer2.nextChannel();

		for (int i = 0; i < 250000; i++) {
			lotsOfClients.add(multiplexer1.client());
			lotsOfClients.add(multiplexer2.client());
		}
		Client client5 = multiplexer1.client();
		Client client6 = multiplexer1.client();
		multiplexer1.nextChannel();
		multiplexer2.nextChannel();

		for (int i = 0; i < 250000; i++) {
			lotsOfClients.add(multiplexer1.client());
			lotsOfClients.add(multiplexer2.client());
		}
		Client client7 = multiplexer1.client();
		Client client8 = multiplexer1.client();
		multiplexer1.nextChannel();
		multiplexer2.nextChannel();

		// correct usage
		client1.use();
		client2.use();
		multiplexer1.nextChannel();
		multiplexer2.nextChannel();
		client3.use();
		client4.use();
		multiplexer1.nextChannel();
		multiplexer2.nextChannel();
		client5.use();
		client6.use();
		multiplexer1.nextChannel();
		multiplexer2.nextChannel();
		client7.use();
		client8.use();
		multiplexer1.nextChannel();
		multiplexer2.nextChannel();

		// renew some of the channels
		for (int i = 0; i < 2; i++) {
			multiplexer1.renewChannel();
			multiplexer2.renewChannel();
			multiplexer1.nextChannel();
			multiplexer2.nextChannel();
		}

		// wrong usage (channel not active)
		client7.use();
	}

}
