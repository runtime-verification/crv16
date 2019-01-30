package bench2; 


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

  before(Person to, Message message,Site site) : call(void Site.sendMessage(Person,Message)) && args(to,message) && target(site)
  {
    Person from = message.sender; 
    synchronized(MONITOR_LOCK){
    	if(TRACE){ System.out.println("send,"+from+","+to+","+site);}
	if(CHECK){ check(monitor.step(SEND,from,to,site)); }
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

  private void init(){

QEABuilder q = new QEABuilder("AnnoyingFriend");

	// Quantified variables
	int P1 = -1;
	int P2 = -2;
	// Free variables
	int site1 = 1;
	int site2 = 2;
	int site3 = 3;
	int count = 4;
	
	q.addQuantification(FORALL, P1);
	q.addQuantification(FORALL, P2);

	
	q.addTransition(1, SEND, new int[] { P1, P2, site1 }, setVal(count,1), 2);
	
	q.addTransition(2, SEND, new int[]{P1,P2,site2}, isNotEqual(site1,site2), increment(count), 3);
	q.addTransition(2, SEND, new int[]{P1,P2,site2}, isEqualToConstant(count,9),4);
	q.addTransition(2, SEND, new int[]{P1,P2,site2}, not(or(isNotEqual(site1,site2),isEqualToConstant(count,9))), increment(count), 2);	
	q.addTransition(2, SEND, new int[]{P2,P1,site1}, 1);
	
	q.addTransition(3, SEND, new int[]{P1,P2,site3}, and(isNotEqual(site1,site3),isNotEqual(site1,site3)),4);
	q.addTransition(3, SEND, new int[]{P1,P2,site3}, isEqualToConstant(count,9),4);
	// This is why we need else!
	q.addTransition(3, SEND, new int[]{P1,P2,site3},
			not(or(and(isNotEqual(site1,site3),isNotEqual(site1,site3)),isEqualToConstant(count,9))),increment(count),3);
	
	q.addTransition(3, SEND, new int[]{P2,P1,site1}, 1);	
	
	q.addFinalStates(1,2,3);
	q.setSkipStates(1,2,3,4);

  //Create monitor
  monitor = MonitorFactory.create(q.make());
  }
}
