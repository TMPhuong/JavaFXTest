package sample;

import com.google.inject.AbstractModule;
import pricepod.presenter.PricePodPresenter;
import service.DummySecurityService;
import service.SecurityService;

public class SampleModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(SubscriptionManager.class).to(SimpleSubscriptionManager.class).asEagerSingleton();
        bind(LevelDataSource.class).to(MockLevelDataSource.class).asEagerSingleton();
        bind(SecurityService.class).to(DummySecurityService.class).asEagerSingleton();
        bind(PricePodPresenter.class).asEagerSingleton();
    }
}
