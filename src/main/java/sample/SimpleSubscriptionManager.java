package sample;

import com.google.common.collect.Maps;
import com.google.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public class SimpleSubscriptionManager implements SubscriptionManager, LevelDataSourceListener {

    private final Map<String, List<SubscriptionListener>> listenerMap = Maps.newHashMap();

    @Inject
    public SimpleSubscriptionManager(LevelDataSource levelDataSource) {
        levelDataSource.addListener(this);
    }

    @Override
    public void onNewLevelData(LevelData levelData) {
        String currencyPair = levelData.getCurrencyPair();
        if (listenerMap.containsKey(currencyPair)) {
            for (SubscriptionListener listener : listenerMap.get(currencyPair))
                listener.onLevelData(levelData);
        }
    }

    @Override
    public void subscribe(String currencyPair, SubscriptionListener listener) {
        if (!listenerMap.containsKey(currencyPair))
            listenerMap.put(currencyPair, new ArrayList<>());

        listenerMap.get(currencyPair).add(listener);
    }

    @Override
    public void unsubsribe(String currencyPair, SubscriptionListener listener) {
        if (listenerMap.containsKey(currencyPair))
            listenerMap.get(currencyPair).remove(listener);
    }

}
