package bench1;

class Message{

  public final MessageType type;
  public final Node originalSender;

  public Message(MessageType type, Node sender){
    this.type=type;
    this.originalSender = sender;
    this.id = counter++;
  }
  private final int id;
  private static int counter = 0;
  private int ttl = 10;

  public void markSending(){ ttl--; }

  public boolean resend(){
    if(!type.isError) return false;
    return ttl>0;
  }

  public String toString(){
    return "M"+id;
  }
}
