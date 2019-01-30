This benchmark aims at measuring the performance of tracking a large number of
individual objects and their behaviour with respect to a central synchronising 
entity. A "device" has two switchable but exclusive operation modes. 
"Work pieces" depend on one of those modes and can be "processed" iff the device
is in the corresponding mode.

== Benchmark Data ==

=== Specification Information ===

It should be verified that no work piece depending on mode x is processed (by
invoking the method Piece.process()) while the device that created it 
(invocation of Device.createPiece()) is in a mode different from x.
The mode of a device is changed by invoking Device.toggleMode().

The resulting symbolic monitor is the following.

* State space Q = {1,2,3,4}
* Quantification: ∀d∀p
* Transitions
** δ(1,¬create(d,p)) = 1, δ(1,create(d,p)) = 2
** δ(2,¬toggleMode(d)) = 2, δ(2,toggleMode(d)) = 3
** δ(3,¬toggleMode(d)∧¬process(p)) = 3, δ(3,toggleMode(d)) = 2, δ(3,process(p)) = 4
** δ(4,true) = 4
* Accepting states (with output ⊤) F = {1,2,3}

The event create(d,p) holds iff the method Device.createPiece() is invoked on some
Device object d instantiating a Piece object p.
The predicate toggle(d) holds for some Device object d iff Device.toggleMode() 
is invoked on d and process(p) is true whenever Piece.process() is called on a 
Piece object p.


=== Instrumentation Information ===

Using AspectJ the events are identified as follows.


* The abstract event create(l,i) is identified by the pointcut/advice

  pointcut createPiece(Device d):  call(public Piece Device.createPiece() && target(d));
  after(Device d) returning(Piece p): createPiece(d) { ... }

* The abstract events toggleMode(d) and process(p) are identified by the pointcuts

    pointcut deviceModeToggle(Device d) :  call(public void Device.toggleMode() && target(d));
    pointcut processPiece(Piece p) :  call(public void Piece.process() && target(p));


=== Program Information ===
Location: Java_track/Mufin/Benchmark5

* The number of times the events should be observed in the program:
** create: 100282
** toggleMode: 9973
** process: 3

* Verdict
** property is violated

=== Example Traces ===

Valid:
* create(1,2) process(2) toggleMode(1)
* create(1,2) create(3,4) process(2) toggleMode(1) process(4) toggleMode(1) process(2)
* create(1,2) process(2) create(3,4) process(2) toggleMode(1) toggleMode(3) toggleMode(3) process(4)

Invalid:
* create(1,2) toggleMode(1) process(2)
* create(1,2) create(3,4) process(2) toggleMode(1) toggleMode(3) togglemode(1) process(4)
* create(1,2) process(2) create(3,4) toggleMode(1) toggleMode(1) toggleMode(1) process(4) process(2)

(The numbers 1,…,4 represent object identities.)

== Comments ==

== Clarification Requests ==
