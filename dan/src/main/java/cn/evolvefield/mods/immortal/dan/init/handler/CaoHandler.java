package cn.evolvefield.mods.immortal.dan.init.handler;

import cn.evolvefield.mods.immortal.dan.Constant;
import cn.evolvefield.mods.immortal.dan.core.cao.Cao;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.fabric.api.resource.SimpleResourceReloadListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

/**
 * Project: Immortal
 * Author: cnlimiter
 * Date: 2023/3/2 12:55
 * Description:
 */
public class CaoHandler implements SimpleResourceReloadListener<CaoHandler.Wrapper> {

    public static CaoHandler INSTANCE = new CaoHandler();
    private static final ResourceLocation ID = new ResourceLocation("dan", "caos");
    private static final int PATH_SUFFIX_LENGTH = ".json".length();
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();
    private Map<ResourceLocation, Cao> caos = new LinkedHashMap<>();
    public Map<ResourceLocation, Cao> getCaos() {
        return caos;
    }
    @Nullable
    public Cao getCao(ResourceLocation id) {
        return this.caos.get(id);
    }

    public Collection<Cao> getAllCaos() {
        return this.caos.values();
    }

    @Override
    public ResourceLocation getFabricId() {
        return ID;
    }

    @Override
    public CompletableFuture<Wrapper> load(ResourceManager manager, ProfilerFiller profiler, Executor executor) {
        return CompletableFuture.supplyAsync(() -> {
            Map<ResourceLocation, Cao> cache = new HashMap<>();
            String location = "caos";
            int length = location.length() + 1;

            for (Map.Entry<ResourceLocation, Resource> entry : manager.listResources(location, (id) -> id.getPath().endsWith(".json")).entrySet()) {
                ResourceLocation resource = entry.getKey();
                String path = resource.getPath();
                ResourceLocation identifier = new ResourceLocation(resource.getNamespace(), path.substring(length, path.length() - PATH_SUFFIX_LENGTH));

                try {

                    try (BufferedReader reader = entry.getValue().openAsReader()) {
                        Cao json = GsonHelper.fromJson(GSON, reader, Cao.class);
                        if (json != null) {
                            Cao object = cache.put(identifier, json);
                            if (object != null) {
                                throw new IllegalStateException("Duplicate data file ignored with ID " + identifier);
                            }
                        } else {
                            Constant.LOGGER.error("Couldn't load data file {} from {} as it's null or empty", identifier, resource);
                        }
                    } finally {
                        continue;
                    }
                } catch (IllegalArgumentException var17) {
                    Constant.LOGGER.error("Couldn't parse data file {} from {}", identifier, resource, var17);
                }
            }

            caos.putAll(cache);

            return new Wrapper(cache);
        }, executor);
    }

    @Override
    public CompletableFuture<Void> apply(Wrapper data, ResourceManager manager, ProfilerFiller profiler, Executor executor) {
        return CompletableFuture.runAsync(() -> {
            ImmutableMap.Builder<ResourceLocation, Cao> builder = ImmutableMap.builder();
            var var10000 = data.getCaos();
            Objects.requireNonNull(builder);
            var10000.forEach(builder::put);
            this.caos = builder.build();
        }, executor);
    }


    public record Wrapper(Map<ResourceLocation, Cao> map){
        public Wrapper(Map<ResourceLocation, Cao> map){
            this.map = map;
        }

        public Map<ResourceLocation, Cao> getCaos(){
            return this.map;
        }
    }

}
