package dev.luizveronesi.upload.service.factory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import dev.luizveronesi.upload.model.UploadType;
import dev.luizveronesi.upload.service.strategy.UploadStrategy;

@Service
public class UploadServiceFactory {

    private final Map<UploadType, UploadStrategy> strategies = new HashMap<>();

    public UploadServiceFactory(Set<UploadStrategy> strategies) {
        this.createStrategyMap(strategies);
    }

    public UploadStrategy getStrategy(UploadType type) {
    	return this.strategies.get(type);
    }

    private void createStrategyMap(Set<UploadStrategy> strategies) {
        strategies.forEach(absenceStrategy -> this.strategies.put(absenceStrategy.getStrategyName(), absenceStrategy));
    }
}