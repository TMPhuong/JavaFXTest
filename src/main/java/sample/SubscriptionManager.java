package sample;

public interface SubscriptionManager {
    void subscribe(String currencyPair);
    void unsubsribe(String currencyPair);
}
