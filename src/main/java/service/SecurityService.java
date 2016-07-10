package service;

import com.google.common.base.Optional;
import model.Security;

import java.util.Collection;
import java.util.Set;

public interface SecurityService {
    Optional<Security> loadSecurity(String currencyPair);

    Collection<Security> loadAllSecurities();
    Set<String> loadAllCurrencyPairs();
}
