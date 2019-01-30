package bench3;

import java.util.*;

public class Command{

  private final Manager manager;

  private List<Resource> required_resources = new ArrayList<Resource>();
  public void addResourceRequired(Resource r){ required_resources.add(r); }
  public int numberOfRequiredResources(){ return required_resources.size(); }

  public final int duration;
  public Command(int d){ 
    duration=d;
    manager=Manager.getManager(); 
  }


  public boolean tryToExecute(Task task){
    Set<Resource> granted = new HashSet<Resource>();
    for(Resource r : required_resources){
      manager.requestFor(r,task);
      boolean status = manager.getStatusFor(r,task); // return false=deny, return true=grant
      if(!status){
         for(Resource g : granted) manager.releaseFor(r,task);
         return false;
      }
    }
    //System.err.println("Executing "+this);
    return true;
  }

  public void release(Task task){
    for(Resource r : required_resources){
      manager.releaseFor(r,task);
    }
  }

}
