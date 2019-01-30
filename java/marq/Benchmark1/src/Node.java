package bench1;

import java.util.*;

class Node{

  // oops, should be final!
  public int broadcastChannel;

  public Node(int c,int id){ 
    broadcastChannel=c; 
    rand = new Random(id);
    this.id=id;
  }

  public void receive(MessageWrapper wrapper){
    Message msg = wrapper.message;
    Node sender = wrapper.sender; 

    // Instrumentation
    //System.out.println("ack,"+this+","+msg);

    sender.acknowledge(msg);


    if(msg.resend() && msg.originalSender!=this){
      MessageWrapper w = new MessageWrapper(msg,this);

      // Instrumentation
      //System.out.println("send,"+this+","+msg);

      Network.broadcast(w);
    }
  }

  public void acknowledge(Message msg){

  }


  public void sendNextMessage(){
    MessageType type = MessageType.values()[rand.nextInt(5)];
    Message m = new Message(type,this); 
    MessageWrapper w = new MessageWrapper(m,this);
    
    // Instrumentation
    //System.out.println("send,"+this+","+m);

    Network.broadcast(w);
  }
  private Random rand;
  private int id;
  public String toString(){ return "Node"+id; }
}
