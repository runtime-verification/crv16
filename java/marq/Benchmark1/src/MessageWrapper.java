package bench1;

class MessageWrapper{

  public final Message message;
  public final Node sender;

  public MessageWrapper(Message m, Node s){
    message=m;
    sender=s;
  }

}
