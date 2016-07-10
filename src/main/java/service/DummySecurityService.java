package service;

import com.google.common.base.Optional;
import com.google.common.collect.Maps;
import model.SpotSecurity;
import model.Security;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class DummySecurityService implements SecurityService {

    private final Map<String, Security> securityMap = Maps.newHashMap();

    public DummySecurityService() {
        securityMap.put("EUR/USD", new SpotSecurity(5, 4, "EUR/USD"));
        securityMap.put("USD/JPY", new SpotSecurity(3, 2, "USD/JPY"));
    }

    @Override
    public Optional<Security> loadSecurity(String currencyPair) {
        return Optional.fromNullable(securityMap.get(currencyPair));
    }

    @Override
    public Collection<Security> loadAllSecurities() {
        return securityMap.values();
    }

    @Override
    public Set<String> loadAllCurrencyPairs() {
        return securityMap.keySet();
    }
}
