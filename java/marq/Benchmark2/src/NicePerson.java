package bench2;
import java.util.*;

class NicePerson extends Person {

   public NicePerson(List<Person> friends, List<Site> sites, int seed){
     super(friends,sites,seed);
   }

  public void init(){
     dangerZone = new HashMap<Person,Set<Site>>();
     history = new HashMap<Person,Integer>();
     for(Person friend : friends){
       dangerZone.put(friend,new HashSet<Site>());
       history.put(friend,0);
     }
     blockedFriends = new HashSet<Person>();
  }

  @Override
  protected Person nextFriend(){
    Person friend;
    int count=100;
    do{
      if(count-- == 0) return null;
      friend = super.nextFriend();
    }while(blockedFriends.contains(friend));
    return friend;
  }

   public boolean step(){
     if(blockedFriends.size() == friends.size()){
       // As I am not Annoying there are no friends I can send messages to
       return false;
     }
     Person friend = nextFriend();
     if(friend==null) return false;
     Site site = getSite();
     Message msg = new Message(this,"poke");
     site.sendMessage(friend,msg);
     Set<Site> previous = dangerZone.get(friend); 
     if(!previous.contains(site)){
        previous.add(site);
        if(previous.size()==2){ blockedFriends.add(friend); }
     }
     history.put(friend,history.get(friend)+1);
     if(history.get(friend)==9){
       blockedFriends.add(friend);
     }
     return true;
   }

   public void receiveMessage(Message msg){
     Person friend = msg.sender;
     if(friends.contains(friend)){
       dangerZone.get(friend).clear();
       history.put(friend,0);
       blockedFriends.remove(friend);
     }
   }

   private Map<Person,Set<Site>> dangerZone;
   private Map<Person,Integer> history;
   private Set<Person> blockedFriends;
}
