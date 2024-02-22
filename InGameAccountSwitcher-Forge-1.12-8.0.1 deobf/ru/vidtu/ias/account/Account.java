//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "F:\cocaine\mcpbot_rip"!

//Decompiled by Procyon!

package ru.vidtu.ias.account;

import java.util.*;
import java.util.function.*;
import java.util.concurrent.*;
import org.jetbrains.annotations.*;

public interface Account
{
    @NotNull
    UUID uuid();
    
    @NotNull
    String name();
    
    @NotNull
    CompletableFuture<AuthData> login(@NotNull final BiConsumer<String, Object[]> p0);
    
    public static class AuthData
    {
        public static final String MSA = "msa";
        public static final String MOJANG = "mojang";
        public static final String LEGACY = "legacy";
        private final String name;
        private final UUID uuid;
        private final String accessToken;
        private final String userType;
        
        public AuthData(@NotNull final String name, @NotNull final UUID uuid, @NotNull final String accessToken, @NotNull final String userType) {
            this.name = name;
            this.uuid = uuid;
            this.accessToken = accessToken;
            this.userType = userType;
        }
        
        @Contract(pure = true)
        @NotNull
        public String name() {
            return this.name;
        }
        
        @Contract(pure = true)
        @NotNull
        public UUID uuid() {
            return this.uuid;
        }
        
        @Contract(pure = true)
        @NotNull
        public String accessToken() {
            return this.accessToken;
        }
        
        @Contract(pure = true)
        @NotNull
        public String userType() {
            return this.userType;
        }
    }
}
