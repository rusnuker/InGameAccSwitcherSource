//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "F:\cocaine\mcpbot_rip"!

//Decompiled by Procyon!

package ru.vidtu.ias.legacy;

import org.jetbrains.annotations.*;
import java.util.concurrent.*;
import java.awt.image.*;
import ru.vidtu.ias.*;
import java.net.*;
import javax.net.ssl.*;
import ru.vidtu.ias.account.*;
import java.nio.charset.*;
import java.io.*;
import java.util.stream.*;
import com.google.gson.*;
import java.util.function.*;
import java.util.*;
import javax.imageio.*;

public class SkinLoader
{
    @NotNull
    public static CompletableFuture<Map.Entry<BufferedImage, Boolean>> loadSkin(@NotNull final UUID uuid) {
        final CompletableFuture<Map.Entry<BufferedImage, Boolean>> cf = new CompletableFuture<Map.Entry<BufferedImage, Boolean>>();
        final URL url;
        HttpsURLConnection conn;
        final BufferedReader bufferedReader;
        BufferedReader err;
        final IllegalArgumentException ex;
        final Throwable t4;
        final IllegalArgumentException ex2;
        final BufferedReader bufferedReader2;
        BufferedReader in;
        JsonObject resp;
        JsonObject json;
        final CompletableFuture<AbstractMap.SimpleImmutableEntry<BufferedImage, Boolean>> completableFuture;
        JsonObject skin;
        final AbstractMap.SimpleImmutableEntry value;
        final Throwable t6;
        SharedIAS.EXECUTOR.execute(() -> {
            try {
                new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid);
                conn = (HttpsURLConnection)url.openConnection();
                if (Auth.FIXED_CONTEXT != null) {
                    conn.setSSLSocketFactory(Auth.FIXED_CONTEXT.getSocketFactory());
                }
                conn.setConnectTimeout(15000);
                conn.setReadTimeout(15000);
                Label_0241_1: {
                    if (conn.getResponseCode() >= 200) {
                        if (conn.getResponseCode() <= 299) {
                            break Label_0241_1;
                        }
                    }
                    try {
                        new BufferedReader(new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8));
                        err = bufferedReader;
                        try {
                            new IllegalArgumentException("loadSkin response: " + conn.getResponseCode() + ", data: " + err.lines().collect(Collectors.joining("\n")));
                            throw ex;
                        }
                        catch (Throwable t3) {
                            throw t3;
                        }
                        finally {
                            if (err != null) {
                                if (t4 != null) {
                                    try {
                                        err.close();
                                    }
                                    catch (Throwable exception) {
                                        t4.addSuppressed(exception);
                                    }
                                }
                                else {
                                    err.close();
                                }
                            }
                        }
                    }
                    catch (Throwable t) {
                        new IllegalArgumentException("loadSkin response: " + conn.getResponseCode(), t);
                        throw ex2;
                    }
                }
                new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
                in = bufferedReader2;
                try {
                    resp = (JsonObject)SharedIAS.GSON.fromJson((String)in.lines().collect(Collectors.joining("\n")), (Class)JsonObject.class);
                    json = StreamSupport.stream((Spliterator<Object>)resp.getAsJsonArray("properties").spliterator(), false).map((Function<? super Object, ?>)JsonElement::getAsJsonObject).filter(jo -> jo.get("name").getAsString().equalsIgnoreCase("textures")).findAny().map(jo -> (JsonObject)SharedIAS.GSON.fromJson(new String(Base64.getDecoder().decode(jo.get("value").getAsString()), StandardCharsets.UTF_8), (Class)JsonObject.class)).orElse(null);
                    if (json == null || !json.has("textures") || !json.getAsJsonObject("textures").has("SKIN")) {
                        completableFuture.complete(null);
                    }
                    else {
                        skin = json.getAsJsonObject("textures").getAsJsonObject("SKIN");
                        new AbstractMap.SimpleImmutableEntry(ImageIO.read(new URL(skin.get("url").getAsString())), skin.has("metadata") && skin.getAsJsonObject("metadata").has("model") && skin.getAsJsonObject("metadata").get("model").getAsString().equalsIgnoreCase("slim"));
                        completableFuture.complete(value);
                    }
                }
                catch (Throwable t5) {
                    throw t5;
                }
                finally {
                    if (in != null) {
                        if (t6 != null) {
                            try {
                                in.close();
                            }
                            catch (Throwable exception2) {
                                t6.addSuppressed(exception2);
                            }
                        }
                        else {
                            in.close();
                        }
                    }
                }
            }
            catch (Throwable t2) {
                SharedIAS.LOG.warn("Unable to load skin for: " + uuid, t2);
            }
            return;
        });
        return cf;
    }
}
