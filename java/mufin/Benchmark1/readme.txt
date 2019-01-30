This benchmark aims at measuring the performance of tracking the behaviour
of a large number of communicating entities. These entities are represented
by objects, but they are is asumed to be external to the benchmark program.
A scenario where this is applicable is a coordinator node in a large
networked cluster spanning several machines. In such a scenario it should
be avoided to replicate the behaviour of the external nodes for ther
purpose of runtime verification, as this defeats the purpose of
distributing work in a cluster.


== Benchmark Data ==

=== Specification Information ===

The benchmark maintains a tree data structure consisting of two different
type of objects: inner nodes and leaf nodes. There is a single root node of
type InnerNode. Each InnerNode has an ordered list of InnerNode children
and an ordered list of LeafNode children. The object graph is an actual
tree, i.e. there are no cycles or multiple references to the same object.

The structure of the tree can be changed dynamically by adding or removing
nodes of either type.

The specification considers 6 different abstract events:

* create(n)
* send(i)
* sendCritical(i)
* reset(i)
* process(l)
* invalidate(n)

Where i, l and n are a sequence of object IDs, which form a consecutive
path starting from the root node to an inner, a leaf or an arbitrary node
respectively. It can be assumed that all occuring events are a valid path
in the tree and end in a node of the correct type for the event.

Informally every leaf node has an external buffer that can hold up to one
message. Whenever a send operation is performed on an inner node, all
descendant leaf nodes which currently have an empty buffer store this
message in their buffer. Whenever a sendCritical operation is performed the
same behaviour as for a send operation is performed, but the specification
requires that all descendant leaf nodes have an empty buffer. A reset
operation on an inner node clears the buffers of all descendant leaf nodes.
A process operation on a leaf node, clears the buffer of the leaf node, but
the specification requires that the buffer contains a message for this
operation to be valid. Finally both types of nodes are invalidated before
they are removed from the tree structure. After an invalidate operation is
performed on a node, none of the previously described operations may be
performed on the node or any of its descendant nodes.

The formal specification is given in the form of a symbolic monitor for
leaf nodes and a symbolic monitor for inner nodes:

* Leaf Nodes:
** State space Q = {0,1,2,3,4}
** Initial state: 0
** Quantification: ∀l : LeafNodes
** Transitions:
*** δ(0, create(l)) = 1
*** δ(0, otherwise) = 0
*** δ(1, ∃m : send(m) ∧ prefix(m, l)) = 2
*** δ(1, ∃m : sendCritical(m) ∧ prefix(m, l)) = 2
*** δ(1, ∃m : reset(m) ∧ prefix(m, l)) = 1
*** δ(1, ∃m : invalidate(m) ∧ prefix(m, l)) = 3
*** δ(1, process(l)) = 4
*** δ(1, otherwise) = 1
*** δ(2, ∃m : send(m) ∧ prefix(m, l)) = 2
*** δ(2, ∃m : sendCritical(m) ∧ prefix(m, l)) = 4
*** δ(2, ∃m : reset(m) ∧ prefix(m, l)) = 1
*** δ(2, ∃m : invalidate(m) ∧ prefix(m, l)) = 3
*** δ(2, process(l)) = 1
*** δ(2, otherwise) = 2
*** δ(3, process(l)) = 4
*** δ(3, otherwise) = 3
*** δ(4, true) = 4
** Accepting states (with output ⊤) F = {0,1,2,3}
* Inner Nodes:
** State space Q = {0,1,2,3}
** Initial state: 0
** Quantification: ∀i : InnerNodes
** Transitions:
*** δ(0, create(i)) = 1
*** δ(0, otherwise) = 0
*** δ(1, ∃m : invalidate(m) ∧ prefix(m, i)) = 2
*** δ(1, otherwise) = 1
*** δ(2, send(i)) = 3
*** δ(2, sendCritical(i)) = 3
*** δ(2, reset(i)) = 3
*** δ(3, true) = 3
** Accepting states (with output ⊤) F = {0,1,2}


The prefix(m, n) is a reflexive relation that is true if m is a prefix of
(or equal to) n.

=== Instrumentation Information ===

The concrete events are specified as AspectJ pointcuts, and are also
specified in the file InstrumentationAspect.aj.

The concrete events only refer to a single object while the abstract events
require a sequence of object IDs that represents the path from the root
node. To allow the runtime verification to keep track of the required
paths, the two concrete events for the create(n) event contain the parent
node:

    public pointcut newInnerChild(InnerNode node, int i) :
        call(* InnerNode.addInnerNode(int)) && args(i) && target(node);

    public pointcut newLeafChild(InnerNode node, int i) :
        call(* InnerNode.addLeafNode(int)) && args(i) && target(node);

They have the parent node as a paremeter and the child node, which is the n
in create(n) as return value.

As a special case an abstract create([r]) event occurs first, when one of
the two concrete abstract events occurs with a parent object r that is not
yet a child of any other object.


The abstract events send(i), sendCritical(i), rest(i) and process(l)
correspond to the concrete events given by:

    public pointcut send(InnerNode node) :
        call(* InnerNode.send()) && target(node);

    public pointcut sendCritical(InnerNode node) :
        call(* InnerNode.sendCritical()) && target(node);

    public pointcut reset(InnerNode node) :
        call(* InnerNode.reset()) && target(node);

    public pointcut process(LeafNode node) :
        call(* LeafNode.process()) && target(node);

As mentioned before, the concrete events contain only a single object,
which has to be augmented by the path from the root to generate the
corresponding abstract event.


The invalidate(n) event is given by two different concrete events, one for
inner nodes and one for leaf nodes:

    public pointcut invalidateInner(InnerNode node, int i) :
        call(* InnerNode.removeInnerNode(int)) && args(i) && target(node);

    public pointcut invalidateLeaf(InnerNode node, int i) :
        call(* InnerNode.removeLeafNode(int)) && args(i) && target(node);

In this case, due to the chosen API, the concrete events do not contain the
target node, but its parent node. The InnerNode.getInnerNode(int index) and
InnerNode.getLeafNode(int index) methods can be used to get the target node
in the instrumentation. Again the path from the root has to be augmented to
generate the corresponding abstract event.

=== Program Information ===
Location: Java_track/Mufin/Benchmark3

* The number of times the (concrete) events should be observed in the program:
** RunGood
*** newInnerChild * 1117488
*** newLeafChild * 4053676
*** send * 2146847
*** sendCritical * 713656
*** reset * 704834
*** process * 2820272
*** invalidateInner * 1249
*** invalidateLeaf * 1724
** RunProcessAfterRemove
*** newInnerChild * 1094892
*** newLeafChild * 3964204
*** send * 2089862
*** sendCritical * 693990
*** reset * 683744
*** process * 2751055
*** invalidateInner * 1234
*** invalidateLeaf * 1630
** RunSendAfterRemove
*** newInnerChild * 1262283
*** newLeafChild * 4478350
*** send * 2066708
*** sendCritical * 688434
*** reset * 675714
*** process * 2699530
*** invalidateInner * 1260
*** invalidateLeaf * 1674
** RunSendCriticalAfterRemove
*** newInnerChild * 1336456
*** newLeafChild * 4721335
*** send * 2102046
*** sendCritical * 699816
*** reset * 689716
*** process * 2750065
*** invalidateInner * 1199
*** invalidateLeaf * 1689
** RunResetAfterRemove
*** newInnerChild * 1059894
*** newLeafChild * 3764131
*** send * 2117672
*** sendCritical * 702734
*** reset * 690549
*** process * 2781575
*** invalidateInner * 1234
*** invalidateLeaf * 1717
** RunEmptyProcess
*** newInnerChild * 1050737
*** newLeafChild * 3677949
*** send * 2166524
*** sendCritical * 721724
*** reset * 711998
*** process * 2830834
*** invalidateInner * 1169
*** invalidateLeaf * 1512
** RunFullSendCritical
*** newInnerChild * 1208958
*** newLeafChild * 4360356
*** send * 1996323
*** sendCritical * 665400
*** reset * 654291
*** process * 2601753
*** invalidateInner * 1282
*** invalidateLeaf * 1796

* Verdict:
** property is violated

=== Example Traces ===

Capital letters represent object IDs of inner nodes, numbers represent object IDs of leaf nodes.

Valid:

* create([A]) create([A, 0]) create([A, 1]) sendCritical([A]) send([A]) process([A, 0])
* create([A]) create([A, 0]) create([A, B]) create([A, B, 1]) send([A, 0]) reset([A]) send([A]) process([A, B, 1])
* create([A]) create([A, B]) create([A, B, 0]), create([A, B, 1]) send([A, B]) invalidate([A, B, 0]) process([A, B, 1]) sendCritical([A])

Invalid:

* create([A]) create([A, B]) create([A, B, 0]) invalidate([A, B]) process([A, B, 0])
* create([A]) create([A, B]) create([A, B, C]) invalidate([A, B]) send([A, B, C])
* create([A]) create([A, B]) create([A, B, 1]) invalidate([A, B]) sendCritical([A, B])
* create([A]) create([A, B]) create([A, B, C]) invalidate([A, B, C]) reset([A, B, C])
* create([A]) create([A, B]) create([A, B, 0]) create([A, C]) send([A, C]) process([A, B, 0])
* create([A]) create([A, 0]) send([A]) sendCritical([A])

== Comments ==

== Clarification Requests ==
