package de.uni_luebeck.isp.rvwithunionfind.benchmarks.benchmark4.multiplexer;

/**
 * This runtime exception is raised if a client is used while its channel is no
 * more existing in its multiplexer.
 * 
 * @author Malte Schmitz (Institute for Software Engineering and Programming
 *         Languages, Universitaet zu Luebeck)
 *
 */
public class ChannelInvalidException extends RuntimeException {
	private static final long serialVersionUID = 1L;
}
