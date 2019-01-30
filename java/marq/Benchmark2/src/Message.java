package bench2;

public class Message{

  public final Person sender;
  public final String contents;

  public Message(Person sender, String contents){
    this.sender=sender;
    this.contents=contents;
  }

}
