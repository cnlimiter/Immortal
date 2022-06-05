package cn.evolvefield.mods.immortal.init.data;

import cn.evolvefield.mods.immortal.Static;
import cn.evolvefield.mods.immortal.api.core.lock.Requirement;
import cn.evolvefield.mods.atomlib.utils.JsonUtils;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/5/9 14:02
 * Version: 1.0
 */
public class LockJsonListener {

    private static final List<Requirement> requirements = new ArrayList<>();
    static Path dataPath;

    public static void init() {
        dataPath = Static.CONFIG_FOLDER.resolve("locks.json");
        if (dataPath.toFile().isFile()) {
            JsonElement element = JsonUtils.read(dataPath.toFile(), true);
            if(element.isJsonArray()){
                element.getAsJsonArray().forEach(jsonElement -> {
                    Requirement requirement;
                    if (jsonElement.isJsonObject()) {
                        JsonObject jsonobject = jsonElement.getAsJsonObject();
                        JsonElement attribute = jsonobject.get("attribute");
                        JsonElement type = jsonobject.get("type");
                        JsonElement name = jsonobject.get("name");
                        JsonElement level = jsonobject.get("level");
                        if (name != null && level != null) {
                            String attributeV = attribute.getAsString();
                            String typeV = type.getAsString();
                            String nameV = name.getAsString();
                            double levelV = level.getAsDouble();
                            requirement = new Requirement(nameV, typeV, attributeV, levelV);
                            addRequirement(requirement);

                        }
                    }
                });
            }


        } else {
            try {
                Files.createFile(dataPath);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }



    public static void addRequirement(Requirement requirement) {
        requirements.add(requirement);
    }

    public static List<Requirement> getRequirements() {
        return requirements;
    }
}
