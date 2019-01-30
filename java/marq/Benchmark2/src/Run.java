package bench2;

import java.util.*;

class Run{

  public static void main(String[] args){
    int noPeople = 500; // must be a multiple of 10
    int noSites = 15;
    int safeSteps = 10000*noPeople;
    int unsafeSteps = 100*noPeople;

    if(args.length > 0){
      if(args.length!=4){ throw new RuntimeException("Wrong arguments"); }
      noPeople = Integer.parseInt(args[0]);
      noSites = Integer.parseInt(args[1]);
      safeSteps = Integer.parseInt(args[2])*noPeople;
      unsafeSteps = Integer.parseInt(args[3])*noPeople;
    }

    List<Site> sites = new ArrayList<Site>(noSites);
    List<Person> allPeople = new ArrayList<Person>(noPeople);

    List<Person> people = null; 
    for(int i=0;i<noPeople-1;i++){
      if(i%10==0){ people = new ArrayList<Person>(10); }
      Person person = new NicePerson(people,sites,i);
      people.add(person);
      allPeople.add(person);
    }
    Person annoying = new AnnoyingPerson(people,sites,noPeople*2); 
    people.add(annoying);
    allPeople.add(annoying);

    for(int i=0;i<noSites;i++){
      sites.add(new Site("Site"+i));
    }
    for(int i=0;i<noPeople;i++){
      allPeople.get(i).init();
    }

    // Do the safe steps
    int index = 0;
    while(safeSteps>0){
      Person person = allPeople.get(index);
      if(person.step()){ safeSteps--; }
      index = (index + 1) % (noPeople-1);
    }

    index = 0;
    while(unsafeSteps>0){
      Person person = allPeople.get(index);
      if(person.step()){ unsafeSteps--; }
      index = (index + 1) % noPeople;
    }
    

  }

}
