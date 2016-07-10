package sample;

import com.google.common.collect.Sets;
import model.PriceSide;
import org.apache.commons.lang3.RandomUtils;

import java.math.BigDecimal;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MockLevelDataSource implements LevelDataSource {

    private final Set<LevelDataSourceListener> listeners = Sets.newHashSet();

    public MockLevelDataSource() {
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleWithFixedDelay(() -> {
            BigDecimal buyPrice1 = getRandomBigDecimal(1.2, 1.21);
            BigDecimal sellPrice1 = buyPrice1.subtract(getRandomBigDecimal(0, 0.005));
            BigDecimal buyPrice2 = getRandomBigDecimal(110, 115);
            BigDecimal sellPrice2 = buyPrice2.subtract(getRandomBigDecimal(0, 2));
            fireNewLevelData(new LevelData("EUR/USD", PriceSide.BUY, buyPrice1, getRandomQuantity(1, 10)));
            fireNewLevelData(new LevelData("EUR/USD", PriceSide.SELL, sellPrice1, getRandomQuantity(1, 10)));
            fireNewLevelData(new LevelData("USD/JPY", PriceSide.BUY, buyPrice2, getRandomQuantity(1, 10)));
            fireNewLevelData(new LevelData("USD/JPY", PriceSide.SELL, sellPrice2, getRandomQuantity(1, 10)));
        }, 500, 500, TimeUnit.MILLISECONDS);
    }

    private BigDecimal getRandomBigDecimal(double min, double max) {
        return new BigDecimal(RandomUtils.nextDouble(min, max));
    }

    private BigDecimal getRandomQuantity(int min, int max) {
        return new BigDecimal(RandomUtils.nextInt(min, max) * 1000000);
    }

    @Override
    public void addListener(LevelDataSourceListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(LevelDataSourceListener listener) {
        listeners.remove(listener);
    }

    private void fireNewLevelData(LevelData levelData) {
        for (LevelDataSourceListener listener : listeners)
            listener.onNewLevelData(levelData);
    }


}
