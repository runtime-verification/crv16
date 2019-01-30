This benchmark is about a multiplexer with different channels and connected streams and clients. Such a Multiplexer consists of four channels. Only one if the four channels is active. Call Multiplexer#nextChannel() to activate the next channel. The four channels are activated in a cyclic manner.
 
On the outer side the multiplexer is connected to four streams. Calling Multiplexer#renewChannel() closes the stream of the currently active channel and directly opens up a new stream.
 
On the inner side many clients can be connected to the multiplexer. A new client is created through a call of Multiplexer#getClient().

== Benchmark Data ==

=== Specification Information ===
We want to monitor the following two properties:
(1) After its creation such a client is fixed to the current channel and must not be used if another channel is active.
(2) Once the channel of a client gets renewed the client is invalid and must not be used any more.

forall m, forall c: We use the following automaton consisting of six states and two fail states. If no matching transition is defined the automaton stays in its current state. The initial state is called start. The states channel_invalid_fail and channel_not_active_fail are the fail states. The states with the prefix off implement a modulo counter storing the offset of the client and the multiplexer.

* start, create(m,c) -> off0
* off0, next(m) -> off1
* off0, renew(m) -> invalid
* off1, next(m) -> off2
* off1, use(c) -> channel_not_active_fail
* off2, next(m) -> off3
* off2, use(c) -> channel_not_active_fail
* off3, next(m) -> off0
* off3, use(c) -> channel_not_active_fail
* invalid, use(c) -> channel_invalid_fail

The meaning of the conditions used in the transitions is as follows:
* create(m,c) calls m.client() on the multiplexer m with the resulting new client c,
* next(m) calls 'm.nextChannel()' on the multiplexer m,
* renew(m) calls 'm.renew()' on the multiplexer m and
* use(c) calls 'c.use()' on the client c.

=== Instrumentation Information ===

AspectJ is used for the instrumentation. The pointcuts are

# pointcut create(Multiplexer m) : call(Client Multiplexer.client()) && target(m);
# pointcut next(Multiplexer m) : call(* Multiplexer.nextChannel()) && target(m);
# pointcut renew(Multiplexer m) : call(* Multiplexer.renewChannel()) && target(m);
# pointcut use(Client c) : call(* Client.use()) && target(c);

The events from the specification are mapped to these pointcuts in the obvious manner.

=== Program Information ===
Location: Java_track/Mufin/Benchmark4

Number of times the pointcuts are called in the different main classes:

MainBadChannelInvalid:
  create: 2000008
  next: 20
  renew: 4
  use: 9
  verdict: property is violated

=== Example Traces ===

We use the same notation as in the formal specification. m is a multiplexer and c1 and c2 are clients.

Three valid traces are:
# create(m,c1)next(m)next(m)next(m)next(m)use(c1)
# create(m,c1)next(m)create(m,c2)next(m)next(m)next(m)use(c1)next(m)use(c2)
# create(m,c1)renew(m)create(m,c2)use(c2)

Three invalid traces are:
# create(m,c1)next(m)use(c1)
# create(m,c1)renew(m)use(c1)
# create(m,c1)next(m)create(m,c2)next(m)next(m)next(m)use(c1)use(c2)

== Comments ==

== Clarification Requests ==
