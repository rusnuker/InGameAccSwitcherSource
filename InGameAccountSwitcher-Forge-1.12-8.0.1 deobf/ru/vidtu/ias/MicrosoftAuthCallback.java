//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "F:\cocaine\mcpbot_rip"!

//Decompiled by Procyon!

package ru.vidtu.ias;

import java.util.function.*;
import java.util.concurrent.*;
import java.net.*;
import java.nio.charset.*;
import java.util.stream.*;
import org.jetbrains.annotations.*;
import ru.vidtu.ias.account.*;
import java.util.*;
import com.sun.net.httpserver.*;
import java.io.*;

public class MicrosoftAuthCallback implements Closeable
{
    public static final String MICROSOFT_AUTH_URL = "https://login.live.com/oauth20_authorize.srf?client_id=54fd49e4-2103-4044-9603-2b028c814ec3&response_type=code&scope=XboxLive.signin%20XboxLive.offline_access&redirect_uri=http://localhost:59125&prompt=select_account";
    private HttpServer server;
    
    @NotNull
    public CompletableFuture<MicrosoftAccount> start(@NotNull final BiConsumer<String, Object[]> progressHandler, @NotNull final String done) {
        final CompletableFuture<MicrosoftAccount> cf = new CompletableFuture<MicrosoftAccount>();
        try {
            final BufferedReader bufferedReader;
            BufferedReader in;
            byte[] b;
            OutputStream os;
            final Throwable t5;
            final CompletableFuture<MicrosoftAccount> completableFuture;
            final Throwable t7;
            final CompletableFuture completableFuture2;
            (this.server = HttpServer.create(new InetSocketAddress("localhost", 59125), 0)).createContext("/", ex -> {
                SharedIAS.LOG.info("Microsoft authentication callback request: " + ex.getRemoteAddress());
                try {
                    new BufferedReader(new InputStreamReader(MicrosoftAuthCallback.class.getResourceAsStream("/authPage.html"), StandardCharsets.UTF_8));
                    in = bufferedReader;
                    try {
                        progressHandler.accept("ias.loginGui.microsoft.progress", new Object[] { "preparing" });
                        b = in.lines().collect(Collectors.joining("\n")).replace("%message%", done).getBytes(StandardCharsets.UTF_8);
                        ex.getResponseHeaders().add("Content-Type", "text/html; charset=UTF-8");
                        ex.sendResponseHeaders(307, b.length);
                        os = ex.getResponseBody();
                        try {
                            os.write(b);
                        }
                        catch (Throwable t4) {
                            throw t4;
                        }
                        finally {
                            if (os != null) {
                                if (t5 != null) {
                                    try {
                                        os.close();
                                    }
                                    catch (Throwable exception) {
                                        t5.addSuppressed(exception);
                                    }
                                }
                                else {
                                    os.close();
                                }
                            }
                        }
                        this.close();
                        SharedIAS.EXECUTOR.execute(() -> {
                            try {
                                completableFuture.complete(this.auth(progressHandler, ex.getRequestURI().getQuery()));
                            }
                            catch (Throwable t) {
                                SharedIAS.LOG.error("Unable to authenticate via Microsoft.", t);
                                completableFuture.completeExceptionally(t);
                            }
                            return;
                        });
                    }
                    catch (Throwable t6) {
                        throw t6;
                    }
                    finally {
                        if (in != null) {
                            if (t7 != null) {
                                try {
                                    in.close();
                                }
                                catch (Throwable exception2) {
                                    t7.addSuppressed(exception2);
                                }
                            }
                            else {
                                in.close();
                            }
                        }
                    }
                }
                catch (Throwable t2) {
                    SharedIAS.LOG.error("Unable to process request on Microsoft authentication callback server.", t2);
                    this.close();
                    completableFuture2.completeExceptionally(t2);
                }
                return;
            });
            this.server.start();
            SharedIAS.LOG.info("Started Microsoft authentication callback server.");
        }
        catch (Throwable t3) {
            SharedIAS.LOG.error("Unable to run the Microsoft authentication callback server.", t3);
            this.close();
            cf.completeExceptionally(t3);
        }
        return cf;
    }
    
    @Nullable
    private MicrosoftAccount auth(@NotNull final BiConsumer<String, Object[]> progressHandler, @Nullable final String query) throws Exception {
        SharedIAS.LOG.info("Authenticating...");
        if (query == null) {
            throw new NullPointerException("query=null");
        }
        if (query.equals("error=access_denied&error_description=The user has denied access to the scope requested by the client application.")) {
            return null;
        }
        if (!query.startsWith("code=")) {
            throw new IllegalStateException("query=" + query);
        }
        SharedIAS.LOG.info("Step: codeToToken.");
        progressHandler.accept("ias.loginGui.microsoft.progress", new Object[] { "codeToToken" });
        final Map.Entry<String, String> authRefreshTokens = (Map.Entry<String, String>)Auth.codeToToken(query.replace("code=", ""));
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
        final Map.Entry<UUID, String> profile = (Map.Entry<UUID, String>)Auth.getProfile(accessToken);
        SharedIAS.LOG.info("Authenticated.");
        return new MicrosoftAccount((String)profile.getValue(), accessToken, refreshToken, (UUID)profile.getKey());
    }
    
    @Override
    public void close() {
        try {
            if (this.server != null) {
                this.server.stop(0);
                SharedIAS.LOG.info("Stopped Microsoft authentication callback server.");
            }
        }
        catch (Throwable t) {
            SharedIAS.LOG.error("Unable to stop the Microsoft authentication callback server.", t);
        }
    }
}
