import java.math.*; 

public class BitCoin extends DebitPaymentMethod {

  private final String walletAddress;

  public BitCoin(String walletAddress, BigInteger balance) {
    super(balance);
    this.walletAddress = walletAddress;
  }

  public String getWalletAddress() {
    return walletAddress;
  }

}