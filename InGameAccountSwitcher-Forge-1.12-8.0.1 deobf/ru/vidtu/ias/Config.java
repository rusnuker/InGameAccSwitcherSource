//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "F:\cocaine\mcpbot_rip"!

//Decompiled by Procyon!

package ru.vidtu.ias;

import java.nio.charset.*;
import com.google.gson.*;
import java.util.*;
import ru.vidtu.ias.account.*;
import org.jetbrains.annotations.*;
import java.nio.file.attribute.*;
import java.nio.file.*;

public class Config
{
    private static final int CONFIG_VERSION = 2;
    public static List<Account> accounts;
    public static boolean titleScreenText;
    public static String titleScreenTextX;
    public static String titleScreenTextY;
    public static Alignment titleScreenTextAlignment;
    public static boolean titleScreenButton;
    public static String titleScreenButtonX;
    public static String titleScreenButtonY;
    public static boolean multiplayerScreenButton;
    public static String multiplayerScreenButtonX;
    public static String multiplayerScreenButtonY;
    public static boolean experimentalJavaFXBrowser;
    
    public static void load(@NotNull final Path gameDir) {
        try {
            final Path p = gameDir.resolve("config").resolve("ias.json");
            if (!Files.isRegularFile(p, new LinkOption[0])) {
                return;
            }
            final JsonObject jo = (JsonObject)SharedIAS.GSON.fromJson(new String(Files.readAllBytes(p), StandardCharsets.UTF_8), (Class)JsonObject.class);
            if (!jo.has("version")) {
                Config.accounts = (jo.has("accounts") ? loadAccounts(jo.getAsJsonArray("accounts"), 1) : new ArrayList<Account>());
                Config.titleScreenTextX = (jo.has("textX") ? jo.get("textX").getAsString() : null);
                Config.titleScreenTextX = (jo.has("textY") ? jo.get("textY").getAsString() : null);
                Config.titleScreenButtonX = (jo.has("btnX") ? jo.get("btnX").getAsString() : null);
                Config.titleScreenButtonY = (jo.has("btnY") ? jo.get("btnY").getAsString() : null);
                Config.multiplayerScreenButton = (jo.has("showOnMPScreen") && jo.get("showOnMPScreen").getAsBoolean());
                Config.titleScreenButton = (!jo.has("showOnTitleScreen") || jo.get("showOnTitleScreen").getAsBoolean());
                return;
            }
            final int version = jo.get("version").getAsInt();
            if (version != 2) {
                throw new IllegalStateException("Unknown config version: " + version + ", content: " + jo);
            }
            Config.accounts = (jo.has("accounts") ? loadAccounts(jo.getAsJsonArray("accounts"), version) : new ArrayList<Account>());
            Config.titleScreenText = (!jo.has("titleScreenText") || jo.get("titleScreenText").getAsBoolean());
            Config.titleScreenTextX = (jo.has("titleScreenTextX") ? jo.get("titleScreenTextX").getAsString() : null);
            Config.titleScreenTextY = (jo.has("titleScreenTextY") ? jo.get("titleScreenTextY").getAsString() : null);
            Config.titleScreenTextAlignment = (jo.has("titleScreenTextAlignment") ? Alignment.getOr(jo.get("titleScreenTextAlignment").getAsString(), Alignment.CENTER) : Alignment.CENTER);
            Config.titleScreenButton = (!jo.has("titleScreenButton") || jo.get("titleScreenButton").getAsBoolean());
            Config.titleScreenButtonX = (jo.has("titleScreenButtonX") ? jo.get("titleScreenButtonX").getAsString() : null);
            Config.titleScreenButtonY = (jo.has("titleScreenButtonY") ? jo.get("titleScreenButtonY").getAsString() : null);
            Config.multiplayerScreenButton = (jo.has("multiplayerScreenButton") && jo.get("multiplayerScreenButton").getAsBoolean());
            Config.multiplayerScreenButtonX = (jo.has("multiplayerScreenButtonX") ? jo.get("multiplayerScreenButtonX").getAsString() : null);
            Config.multiplayerScreenButtonY = (jo.has("multiplayerScreenButtonY") ? jo.get("multiplayerScreenButtonY").getAsString() : null);
            Config.experimentalJavaFXBrowser = (jo.has("experimentalJavaFXBrowser") && jo.get("experimentalJavaFXBrowser").getAsBoolean() && experimentalJavaFXBrowserAvailable());
        }
        catch (Throwable t) {
            SharedIAS.LOG.error("Unable to load IAS config.", t);
        }
    }
    
    @Contract(pure = true)
    @NotNull
    private static List<Account> loadAccounts(@NotNull final JsonArray accounts, final int version) {
        final List<Account> accs = new ArrayList<Account>();
        for (final JsonElement je : accounts) {
            final Account account = loadAccount(je.getAsJsonObject().get("type").getAsString(), (version == 1) ? je.getAsJsonObject().getAsJsonObject("data") : je.getAsJsonObject(), version);
            if (account != null) {
                accs.add(account);
            }
        }
        return accs;
    }
    
    @Contract(pure = true)
    @Nullable
    private static Account loadAccount(@NotNull final String type, @NotNull final JsonObject json, final int version) {
        if (type.equalsIgnoreCase("ias:microsoft") || type.equalsIgnoreCase("ru.vidtu.ias.account.MicrosoftAccount")) {
            return (Account)new MicrosoftAccount((version == 1) ? json.get("username").getAsString() : json.get("name").getAsString(), json.get("accessToken").getAsString(), json.get("refreshToken").getAsString(), UUID.fromString(json.get("uuid").getAsString()));
        }
        if (type.equalsIgnoreCase("ias:offline") || type.equalsIgnoreCase("ru.vidtu.ias.account.OfflineAccount")) {
            final String name = (version == 1) ? json.get("username").getAsString() : json.get("name").getAsString();
            return (Account)new OfflineAccount(name, (version == 1) ? Auth.resolveUUID(name) : UUID.fromString(json.get("uuid").getAsString()));
        }
        return null;
    }
    
    public static void save(@NotNull final Path gameDir) {
        try {
            final Path p = gameDir.resolve("config").resolve("ias.json");
            Files.createDirectories(p.getParent(), (FileAttribute<?>[])new FileAttribute[0]);
            final JsonObject jo = new JsonObject();
            jo.addProperty("version", (Number)2);
            jo.add("accounts", (JsonElement)saveAccounts(Config.accounts));
            jo.addProperty("titleScreenText", Boolean.valueOf(Config.titleScreenText));
            if (Config.titleScreenTextX != null) {
                jo.addProperty("titleScreenTextX", Config.titleScreenTextX);
            }
            if (Config.titleScreenTextY != null) {
                jo.addProperty("titleScreenTextY", Config.titleScreenTextY);
            }
            if (Config.titleScreenTextAlignment != null) {
                jo.addProperty("titleScreenTextAlignment", Config.titleScreenTextAlignment.name());
            }
            jo.addProperty("titleScreenButton", Boolean.valueOf(Config.titleScreenButton));
            if (Config.titleScreenButtonX != null) {
                jo.addProperty("titleScreenButtonX", Config.titleScreenButtonX);
            }
            if (Config.titleScreenButtonY != null) {
                jo.addProperty("titleScreenButtonY", Config.titleScreenButtonY);
            }
            jo.addProperty("multiplayerScreenButton", Boolean.valueOf(Config.multiplayerScreenButton));
            if (Config.multiplayerScreenButtonX != null) {
                jo.addProperty("multiplayerScreenButtonX", Config.multiplayerScreenButtonX);
            }
            if (Config.multiplayerScreenButtonY != null) {
                jo.addProperty("multiplayerScreenButtonY", Config.multiplayerScreenButtonY);
            }
            jo.addProperty("experimentalJavaFXBrowser", Boolean.valueOf(Config.experimentalJavaFXBrowser));
            Files.write(p, jo.toString().getBytes(StandardCharsets.UTF_8), new OpenOption[0]);
        }
        catch (Throwable t) {
            SharedIAS.LOG.error("Unable to save IAS config.", t);
        }
    }
    
    @Contract(pure = true)
    @NotNull
    private static JsonArray saveAccounts(@NotNull final List<Account> accounts) {
        final JsonArray ja = new JsonArray();
        for (final Account a : accounts) {
            final JsonObject jo = saveAccount(a);
            if (jo != null) {
                ja.add((JsonElement)jo);
            }
        }
        return ja;
    }
    
    @Contract(pure = true)
    @Nullable
    private static JsonObject saveAccount(@NotNull final Account account) {
        if (account instanceof MicrosoftAccount) {
            final JsonObject jo = new JsonObject();
            final MicrosoftAccount ma = (MicrosoftAccount)account;
            jo.addProperty("type", "ias:microsoft");
            jo.addProperty("name", ma.name());
            jo.addProperty("accessToken", ma.accessToken());
            jo.addProperty("refreshToken", ma.refreshToken());
            jo.addProperty("uuid", ma.uuid().toString());
            return jo;
        }
        if (account instanceof OfflineAccount) {
            final JsonObject jo = new JsonObject();
            jo.addProperty("type", "ias:offline");
            jo.addProperty("name", account.name());
            jo.addProperty("uuid", account.uuid().toString());
            return jo;
        }
        return null;
    }
    
    public static boolean experimentalJavaFXBrowserAvailable() {
        try {
            Class.forName("javafx.scene.web.WebView");
            return true;
        }
        catch (Throwable t) {
            return false;
        }
    }
    
    static {
        Config.accounts = new ArrayList<Account>();
        Config.titleScreenText = true;
        Config.titleScreenTextAlignment = Alignment.CENTER;
        Config.titleScreenButton = true;
        Config.multiplayerScreenButton = false;
        Config.experimentalJavaFXBrowser = false;
    }
    
    public enum Alignment
    {
        LEFT("ias.configGui.titleScreenText.alignment.left"), 
        CENTER("ias.configGui.titleScreenText.alignment.center"), 
        RIGHT("ias.configGui.titleScreenText.alignment.right");
        
        private final String key;
        
        private Alignment(final String key) {
            this.key = key;
        }
        
        @Contract(pure = true)
        @NotNull
        public String key() {
            return this.key;
        }
        
        @Contract(pure = true)
        @NotNull
        public static Alignment getOr(@NotNull final String name, @NotNull final Alignment fallback) {
            for (final Alignment v : values()) {
                if (v.name().equalsIgnoreCase(name)) {
                    return v;
                }
            }
            return fallback;
        }
    }
}
