package bench1;

import java.util.*;

public class Run{

  public static void main(String[] args){

    int channels = 5;
    int nodespc = 5;
    int goodmsgs = 200;
    int badmsgs = 100;

    if(args.length !=0){
      channels = Integer.parseInt(args[0]);
      nodespc = Integer.parseInt(args[1]);
      goodmsgs = Integer.parseInt(args[2]);
      badmsgs = Integer.parseInt(args[3]);
    }

    Random rand = new Random(222);
    ArrayList<Node> nodes = new ArrayList<Node>();

    for(int i=0;i<channels;i++){
      for(int j=0;j<nodespc;j++){
        int id = (i*nodespc)+j; 
        Node n = new Node(i,id);
        //System.out.println("Node "+id+" broadcasts to channel "+i);
        nodes.add(n);
        for(int k=0;k<channels;k++){
          if(k==i) continue;
          if(rand.nextBoolean()){
            //System.out.println("Node "+id+" subscribes to channel "+k);
            Network.subscribe(n,k);
          }
        }
      }
    }

    while(goodmsgs-->0){
      int index = rand.nextInt(nodes.size());
      Node n = nodes.get(index);
      n.sendNextMessage();
    }

    Node bad = nodes.get(rand.nextInt(nodes.size()));
    bad.broadcastChannel = channels*2;

    while(badmsgs-->0){
      int index = rand.nextInt(nodes.size());
      Node n = nodes.get(index);
      n.sendNextMessage();
    }

  }


}
