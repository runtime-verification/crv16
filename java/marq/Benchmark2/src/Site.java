package bench2;

public class Site{

  public final String name;
  public Site(String name){ this.name=name; }
  public String toString(){ return name; }

  void sendMessage(Person person, Message msg){
    // Instrumentation
    //System.out.println("send,"+msg.sender+","+person+","+this);
    person.receiveMessage(msg);
  }


}
