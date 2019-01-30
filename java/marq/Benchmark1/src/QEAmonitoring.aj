package bench1; 

import java.util.*;

import qea.structure.impl.other.Verdict;
import qea.creation.QEABuilder;
import qea.structure.intf.QEA;
import static qea.structure.impl.other.Quantification.*;
import qea.monitoring.impl.MonitorFactory;
import qea.monitoring.intf.Monitor;
import qea.monitoring.impl.GarbageMode;
import static qea.structure.intf.Assignment.*;
import static qea.structure.intf.Guard.*;

public aspect QEAmonitoring{

  static boolean TRACE = false;
  static boolean CHECK = true;

  before(Node sender, MessageWrapper wrapper) : call(void Network.broadcast(MessageWrapper)) && args(wrapper) && this(sender)
  {
    Message msg = wrapper.message;
    synchronized(MONITOR_LOCK){
    	if(TRACE) System.out.println("send,"+sender+","+msg);
	if(CHECK){ check(monitor.step(SEND,sender,msg)); }
    }
  }
  before(Node sender, Message message) : call(void Node.acknowledge(Message)) && args(message) && this(sender)
  {
    synchronized(MONITOR_LOCK){
        if(TRACE) System.out.println("ack,"+sender+","+message);
        if(CHECK){ check(monitor.step(ACK,sender,message)); }
    }
  } 

  after () : execution(* *.main(..)) {
   check(monitor.end());
   System.err.println("Property Satisfied");
  }

  public QEAmonitoring(){ init(); }

  // synchronizes all instrumentation
  private Object MONITOR_LOCK = new Object();

private void check(Verdict v){

  if(v == Verdict.FAILURE){
    System.err.println("Property Violated");
    System.exit(0);
  }

}

private Monitor monitor;

private final int SEND = 1;
private final int ACK  = 2;

  private void init(){

	QEABuilder q = new QEABuilder("PublisherSubscriber");

	// Quantified variables
	int P = -1;
	int S = -2;
	int M = -3;
	
	q.addQuantification(FORALL, P);
	q.addQuantification(EXISTS, S);
	q.addQuantification(FORALL, M);

	
	q.addTransition(1, SEND, new int[] { P, M }, 2);
	q.addTransition(2, SEND, new int[] { P, M }, 3);
	q.addTransition(2, ACK, new int[] { S, M }, 1);
	
	
	q.addFinalStates(1);
	q.setSkipStates(1,2);


  //Create monitor
  monitor = MonitorFactory.create(q.make());
  }
}
