package bench3;

import java.util.*;

public class Manager{

  private Manager(){}
  private static final Manager single = new Manager();
  public static Manager getManager(){
    return single;
  }

  private Set<Resource> granted = new HashSet<>(); 
  private Map<Task,Set<Resource>> task_grants = new HashMap<>();

  public void requestFor(Resource r,Task task){
    Set<Resource> set = task_grants.get(task);
    if(set==null){
      set = new HashSet<Resource>();
      task_grants.put(task,set);
    } 

    if(!set.contains(r) && !granted.contains(r)){
        set.add(r);
        granted.add(r);
    }

  }
  public boolean getStatusFor(Resource r, Task task){
    Set<Resource> set = task_grants.get(task);
    if(set==null) return false;
    return set.contains(r);
  }

  public void releaseFor(Resource r, Task task){
    granted.remove(r);
    task_grants.get(task).remove(r);
  }

}
