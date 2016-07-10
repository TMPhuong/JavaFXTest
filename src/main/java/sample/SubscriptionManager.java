package sample;

public interface SubscriptionManager {
    void subscribe(String currencyPair, SubscriptionListener listener);
    void unsubsribe(String currencyPair, SubscriptionListener listener);
}
