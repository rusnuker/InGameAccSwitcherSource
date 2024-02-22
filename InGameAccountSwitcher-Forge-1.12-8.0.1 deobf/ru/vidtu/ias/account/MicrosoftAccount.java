//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "F:\cocaine\mcpbot_rip"!

//Decompiled by Procyon!

package ru.vidtu.ias.account;

import org.jetbrains.annotations.*;
import java.util.function.*;
import java.util.concurrent.*;
import ru.vidtu.ias.*;
import java.util.*;

public class MicrosoftAccount implements Account
{
    private String name;
    private String accessToken;
    private String refreshToken;
    private UUID uuid;
    
    public MicrosoftAccount(@NotNull final String name, @NotNull final String accessToken, @NotNull final String refreshToken, @NotNull final UUID uuid) {
        this.name = name;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.uuid = uuid;
    }
    
    @NotNull
    public UUID uuid() {
        return this.uuid;
    }
    
    @NotNull
    public String name() {
        return this.name;
    }
    
    @Contract(pure = true)
    @NotNull
    public String accessToken() {
        return this.accessToken;
    }
    
    @Contract(pure = true)
    public String refreshToken() {
        return this.refreshToken;
    }
    
    @NotNull
    public CompletableFuture<Account.AuthData> login(@NotNull final BiConsumer<String, Object[]> progressHandler) {
        final CompletableFuture<Account.AuthData> cf = new CompletableFuture<Account.AuthData>();
        final CompletableFuture<Account.AuthData> completableFuture;
        SharedIAS.EXECUTOR.execute(() -> {
            try {
                this.refresh(progressHandler);
                completableFuture.complete(new Account.AuthData(this.name, this.uuid, this.accessToken, "msa"));
            }
            catch (Throwable t) {
                SharedIAS.LOG.error("Unable to login/refresh Microsoft account.", t);
                completableFuture.completeExceptionally(t);
            }
            return;
        });
        return cf;
    }
    
    private void refresh(@NotNull final BiConsumer<String, Object[]> progressHandler) throws Exception {
        try {
            SharedIAS.LOG.info("Refreshing...");
            progressHandler.accept("ias.loginGui.microsoft.progress", new Object[] { "getProfile" });
            final Map.Entry<UUID, String> profile = (Map.Entry<UUID, String>)Auth.getProfile(this.accessToken);
            SharedIAS.LOG.info("Access token is valid.");
            this.uuid = profile.getKey();
            this.name = profile.getValue();
        }
        catch (Exception e) {
            try {
                SharedIAS.LOG.info("Step: refreshToken.");
                progressHandler.accept("ias.loginGui.microsoft.progress", new Object[] { "refreshToken" });
                final Map.Entry<String, String> authRefreshTokens = (Map.Entry<String, String>)Auth.refreshToken(this.refreshToken);
                final String refreshToken = authRefreshTokens.getValue();
                SharedIAS.LOG.info("Step: authXBL.");
                progressHandler.accept("ias.loginGui.microsoft.progress", new Object[] { "authXBL" });
                final String xblToken = Auth.authXBL((String)authRefreshTokens.getKey());
                SharedIAS.LOG.info("Step: authXSTS.");
                progressHandler.accept("ias.loginGui.microsoft.progress", new Object[] { "authXSTS" });
                final Map.Entry<String, String> xstsTokenUserhash = (Map.Entry<String, String>)Auth.authXSTS(xblToken);
                SharedIAS.LOG.info("Step: authMinecraft.");
                progressHandler.accept("ias.loginGui.microsoft.progress", new Object[] { "authMinecraft" });
                final String accessToken = Auth.authMinecraft((String)xstsTokenUserhash.getValue(), (String)xstsTokenUserhash.getKey());
                SharedIAS.LOG.info("Step: getProfile.");
                progressHandler.accept("ias.loginGui.microsoft.progress", new Object[] { "getProfile" });
                final Map.Entry<UUID, String> profile2 = (Map.Entry<UUID, String>)Auth.getProfile(accessToken);
                SharedIAS.LOG.info("Refreshed.");
                this.uuid = profile2.getKey();
                this.name = profile2.getValue();
                this.accessToken = accessToken;
                this.refreshToken = refreshToken;
            }
            catch (Exception ex) {
                ex.addSuppressed(e);
                throw ex;
            }
        }
    }
}
