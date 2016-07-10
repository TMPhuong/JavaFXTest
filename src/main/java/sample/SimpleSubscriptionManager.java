package sample;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SimpleSubscriptionManager implements SubscriptionManager {
    @Override
    public void subscribe(String currencyPair) {
        log.info("subscribe");
    }

    @Override
    public void unsubsribe(String currencyPair) {

    }
}
