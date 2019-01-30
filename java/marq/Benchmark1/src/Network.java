package bench1;

import java.util.*;

class Network{

  public static void subscribe(Node subscriber, int channel){
    Set<Node> subs = subscribers.get(channel);
    if(subs==null){
      subs = new HashSet<Node>();
      subscribers.put(channel,subs);
    }
    subs.add(subscriber); 
  }

  public static void broadcast(MessageWrapper wrapper){
    Message msg = wrapper.message;
    msg.markSending();
    Node sender = wrapper.sender;
    int channel = sender.broadcastChannel;
    Set<Node> subs = subscribers.get(channel);
    if(subs==null) return;
    for(Node sub : subs){
      sub.receive(wrapper);
    }
  }


  private static Map<Integer,Set<Node>> subscribers; 
  static{
    subscribers = new HashMap<Integer,Set<Node>>();
  }
}
