package com.mjc.school.hadler;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class EntityProcessorFactory {
    private final Map<Integer, EntityProcessor> processorsMap;

    public EntityProcessorFactory(List<EntityProcessor> processors) {
        AtomicInteger counter = new AtomicInteger(1);
        HashMap<Integer, EntityProcessor> tempMap = processors.stream().collect(HashMap::new,
                (hashMap, entityProcessor) -> hashMap.put(counter.getAndIncrement(), entityProcessor),
                (hashMap, hashMap2) -> hashMap.values().addAll(hashMap2.values()));
        processorsMap = Collections.unmodifiableMap(tempMap);
    }

    public Map<Integer, EntityProcessor> getProcessorsMap() {
        return processorsMap;
    }

    public EntityProcessor getProcessor(int commandNumber) {
        return processorsMap.get(commandNumber);
    }
}
