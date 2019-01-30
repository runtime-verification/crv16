package bench2;
import java.util.*;

abstract class Person{

  abstract void init();
  abstract boolean step();
  abstract void receiveMessage(Message msg);

   public Person(List<Person> friends, List<Site> sites, int seed){
     this.friends = friends;
     this.sites = sites;
     this.random = new Random(seed);
     this.name = "Person"+seed;
   }

   protected Person nextFriend(){
     Person friend;
     do{
       int index = random.nextInt(friends.size());
       friend = friends.get(index);
     }while(friend==this);
     return friend;
   }
   protected Site getSite(){
     int index = random.nextInt(sites.size());
     return sites.get(index);
   }

   public String toString(){ return name; }

   protected String name;
   protected Random random;
   protected final List<Person> friends;
   protected final List<Site> sites;

}
