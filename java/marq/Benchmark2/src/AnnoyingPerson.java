package bench2;
import java.util.*;

class AnnoyingPerson extends Person {

   public AnnoyingPerson(List<Person> friends, List<Site> sites, int seed){
     super(friends,sites,seed);
   }

   @Override
   public String toString(){
     return "Annoying"+super.toString();
   }

   public void init(){}

   public boolean step(){

     Person friend = nextFriend();
     Site site = getSite();
     Message msg = new Message(this,"poke");
     site.sendMessage(friend,msg);

     return true;
   }

   public void receiveMessage(Message msg){}

}
