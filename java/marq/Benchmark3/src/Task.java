package bench3;

import java.util.*;

public class Task{


  private Queue<Command> commands = new LinkedList<Command>();
  public void addCommand(Command c){ commands.add(c); } 
  public int hasCommands(){ return commands.size(); }

  private int duration = 0;
  private Command c = null;
  public boolean step(){

    if(duration>0){
      duration--;
      if(duration==0){
        c.release(this);
        c=null;
      }
      return false;
    }

    if(c==null){
      if(commands.isEmpty()) return true;
      c = commands.poll();
    }
    if(c.tryToExecute(this)) duration = c.duration; 

    return false; 
  }

}
