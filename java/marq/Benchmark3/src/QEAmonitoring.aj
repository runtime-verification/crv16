package bench3; 

public aspect QEAmonitoring{

  static boolean TRACE = true;
  static boolean CHECK = true;

  before(Resource r, Task t,Command c) : call(void Manager.requestFor(Resource,Task)) && args(r,t) && this(c)
  {
    synchronized(MONITOR_LOCK){
    	if(TRACE) System.out.println("request,"+c+","+t+","+r);
	if(CHECK){ /* add monitor code */ } 
    }
  }

  after(Resource r, Task t,Command c) returning(boolean b) : call(boolean Manager.getStatusFor(Resource,Task)) && args(r,t) && this(c)
  {
    synchronized(MONITOR_LOCK){
      if(b){
      	if(TRACE) System.out.println("grant,"+c+","+t+","+r);
	if(CHECK){ /* add monitor code */ }
      }
      else{
      	if(TRACE) System.out.println("deny,"+c+","+t+","+r);
	if(CHECK){ /* add monitor code */ }
      }
    }
  }

  before(Resource r, Task t,Command c) : call(void Manager.releaseFor(Resource,Task)) && args(r,t) && this(c)
  {
    synchronized(MONITOR_LOCK){
    	if(TRACE) System.out.println("release,"+c+","+t+","+r);
	if(CHECK){ /* add monitor code */ }
    }
  }

  after () : execution(* *.main(..)) {
   check(monitor.end());
   System.err.println("Property Satisfied");
  }

  public QEAmonitoring(){ init(); }

  // synchronizes all instrumentation
  private Object MONITOR_LOCK = new Object();

  private Monitor monitor;

private void check(Verdict v){

  if(v == Verdict.FAILURE){
    System.err.println("Property Violated");
    System.exit(0);
  }

}

  private void init(){
    // add init stuff here
  }
}
