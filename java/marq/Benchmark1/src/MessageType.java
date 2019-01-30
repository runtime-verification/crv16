package bench1;

public enum MessageType{
  Log(false),
  Warning(false),
  Info(false),
  Error(true),
  Abort(true);

  public final boolean isError;
  private MessageType(boolean err){ isError=err; } 
}
