package de.uni_luebeck.isp.rvwithunionfind.benchmarks.benchmark3;

public aspect InstrumentationAspect {
    public pointcut send(InnerNode node) : call(* InnerNode.send()) && target(node);

    public pointcut sendCritical(InnerNode node) : call(* InnerNode.sendCritical()) && target(node);

    public pointcut reset(InnerNode node) : call(* InnerNode.reset()) && target(node);

    public pointcut process(LeafNode node) : call(* LeafNode.process()) && target(node);
    
    public pointcut invalidateInner(InnerNode node, int i) : call(* InnerNode.removeInnerNode(int)) && args(i) && target(node);
    
    public pointcut invalidateLeaf(InnerNode node, int i) : call(* InnerNode.removeLeafNode(int)) && args(i) && target(node);
    
    public pointcut newInnerChild(InnerNode node, int i) : call(* InnerNode.addInnerNode(int)) && args(i) && target(node);
    
    public pointcut newLeafChild(InnerNode node, int i) : call(* InnerNode.addLeafNode(int)) && args(i) && target(node);
}
