import java.math.*;

public interface PaymentMethod {

  public abstract boolean charge(BigInteger cost);

}