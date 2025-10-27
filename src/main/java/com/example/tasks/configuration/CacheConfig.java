package com.example.tasks.configuration;

import io.sixhours.memcached.cache.IMemcachedClient;
import io.sixhours.memcached.cache.MemcachedCacheManager;
import io.sixhours.memcached.cache.XMemcachedClient;
import net.rubyeye.xmemcached.MemcachedClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static java.util.Optional.ofNullable;

@Configuration
public class CacheConfig {

    @Value("${memcached.cache.servers:localhost:11211}")
    private String servers;

    /**
     * @return Cache Manager для memcached
     * @throws IOException при обрыве соединения, будет переподключаться
     */
    @Bean
    public CacheManager cacheManager() throws IOException {
        return new MemcachedCacheManager(iMemcachedClient());
    }

    /**
     * @return детали самого подключения
     * @throws IOException при обрыве соединения
     */
    @Bean
    public IMemcachedClient iMemcachedClient() throws IOException {
        return new XMemcachedClient(memcachedClient());
    }

    /**
     * @return другая реализация клиента, {@code IMemcachedClient} зачем-то ее использует
     * @throws IOException при обрыве соединения
     */
    @Bean
    public MemcachedClient memcachedClient() throws IOException {

        final net.rubyeye.xmemcached.XMemcachedClient client = new net.rubyeye.xmemcached.XMemcachedClient();

        for (final Server s : parseServers(servers)) {
            client.addServer(s.host, s.port);
        }

        return client;
    }

    /**
     * @param servers url
     * @return список String host + int port (типы как в memcached)
     */
    private static List<Server> parseServers(final String servers) {

        return ofNullable(servers).map(str -> Arrays.stream(str.split("\\s*,\\s*")).map(s -> {

            final String[] parts = s.split(":");

            final String host = parts[0];
            final int port = Integer.parseInt(parts[1]);

            return new Server(host, port);
        }).toList()).orElse(List.of());
    }

    private record Server(String host, int port) { }
}
