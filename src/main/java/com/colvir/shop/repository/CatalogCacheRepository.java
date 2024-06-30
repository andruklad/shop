package com.colvir.shop.repository;

import com.colvir.shop.model.Catalog;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class CatalogCacheRepository {

    private final RedisTemplate<String, String> redisTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public Optional<Catalog> findByCode(String code) {

        String stringValue = redisTemplate.opsForValue().get(code);
        if (stringValue == null) {
            return Optional.empty();
        }
        try {
            return Optional.of(objectMapper.readValue(stringValue, Catalog.class));
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public Catalog save(Catalog catalog) {

        try {
            redisTemplate.opsForValue().set(catalog.getCode(), objectMapper.writeValueAsString(catalog));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return catalog;
    }

//    @Scheduled(fixedDelay = 10000L)
    // TODO. Обеспечить проверку доступности Redis
    public void clearAll() {

        Set<String> keys = redisTemplate.keys("*");
        if (keys == null) {
            return;
        }
        System.out.printf("Из кэша будет удално %s записей", keys.size());
        redisTemplate.delete(keys);
    }
}
