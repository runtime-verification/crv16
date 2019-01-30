package bench3;

import java.util.*;

public class Workload{

  public static final int n_tasks = 1000;
  public static final int n_commands = 500;
  public static final int n_resources = 1000;

  private static List<Resource> resources = new ArrayList<Resource>();
  private static List<Command> commands = new ArrayList<Command>();
  private static Queue<Task> tasks = new LinkedList<Task>();

  public static void setup(){

    for(int i=0;i<n_resources;i++){ resources.add(new Resource("resource "+i)); }
    for(int i=0;i<n_commands;i++){
      Command c = new Command(1+10*(i%5));
      commands.add(c);
      int shift = i+1;
      for(int j=0;j<1+(shift/2);j++){
        if(shift%(j+1)==0){
           c.addResourceRequired(resources.get(j)); 
        }
      }
      //System.err.println(i+" requires "+c.numberOfRequiredResources());
    }
    int kind = 0;
    for(int i=0;i<n_tasks;i++){
      Task t = new Task();
      tasks.add(t);
      int cs = 5+kind;
      int step = 1+(i%cs);
      for(int j=i;j<n_commands;j = (j+step)%n_commands){
        if(cs==0) break;
        t.addCommand(commands.get(j));
        cs--;
      }
      //System.err.println(i+" has "+t.hasCommands());
      kind = (kind+1);
      if(kind >= 20) kind = -4;
    }

  }

  public static void main(String[] args){

    setup();
   
    int max=tasks.size()/2;

    Set<Task> running = new HashSet<Task>();
    for(int i=0;i<max;i++){
       Task t = tasks.poll();
       running.add(t);
    }

    while(!running.isEmpty()){

      Iterator<Task> run_it = running.iterator();
      while(run_it.hasNext()){
        Task task = run_it.next();
        if(task.step()){
          System.err.println(task+" done");
          run_it.remove();
        }
      }

      for(int i=0;i<(max-running.size());i++){
        Task t = tasks.poll();
        if(t!=null){
          running.add(t);
        }
      } 
    }

  }

}
