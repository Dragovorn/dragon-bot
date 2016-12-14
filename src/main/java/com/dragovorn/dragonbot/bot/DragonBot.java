/*
 * Copyright (c) 2016. Andrew Burr
 *  Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 *  associated documentation files (the "Software"), to deal in the Software without restriction,
 *  including without limitation the rights to use, copy, modify, merge, publish, distribute,
 *  sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN
 * ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH
 * THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.dragovorn.dragonbot.bot;

import com.amazonaws.AmazonClientException;
import com.amazonaws.event.ProgressEvent;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectMetadataRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.transfer.Download;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.dragovorn.dragonbot.Keys;
import com.dragovorn.dragonbot.Utils;
import com.dragovorn.dragonbot.api.bot.command.Command;
import com.dragovorn.dragonbot.api.bot.command.CommandManager;
import com.dragovorn.dragonbot.api.bot.event.ChannelEnterEvent;
import com.dragovorn.dragonbot.api.bot.event.ServerConnectEvent;
import com.dragovorn.dragonbot.api.bot.event.UserMessageEvent;
import com.dragovorn.dragonbot.api.bot.file.FileManager;
import com.dragovorn.dragonbot.api.bot.plugin.BotPlugin;
import com.dragovorn.dragonbot.api.bot.plugin.PluginLoader;
import com.dragovorn.dragonbot.api.github.GitHubAPI;
import com.dragovorn.dragonbot.api.twitch.TwitchAPI;
import com.dragovorn.dragonbot.command.Github;
import com.dragovorn.dragonbot.command.Uptime;
import com.dragovorn.dragonbot.command.VersionCmd;
import com.dragovorn.dragonbot.exceptions.ConnectionException;
import com.dragovorn.dragonbot.exceptions.InvalidPluginException;
import com.dragovorn.dragonbot.gui.MainWindow;
import com.dragovorn.dragonbot.gui.panel.UpdatePanel;
import com.dragovorn.dragonbot.log.DragonLogger;
import com.dragovorn.dragonbot.log.LoggingOutputStream;
import com.google.common.base.CharMatcher;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DragonBot extends Bot {

    private String name;
    private String auth;
    private String charset;
    private String channelPrefixes = "#&+!";

    private PluginLoader loader;

    private ImmutableList<BotPlugin> plugins;

    private Download download;

    private CommandManager commandManager;

    private TransferManager manager;

    private BotConfiguration config;

    private Connection connection;

    private InetAddress inetAddress;

    private InputThread inputThread;

    private OutputThread outputThread;

    private Queue outQueue;

    private GitHubAPI gitHubAPI;

    private TwitchAPI twitchAPI;

    private final Logger logger;

    private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    public DragonBot() throws Exception {
        setInstance(this);

        this.outQueue = new Queue();

        if (!FileManager.dir.exists()) {
            FileManager.dir.mkdirs();
        }

        File pathFile = new File(FileManager.dir, "path");

        if (!pathFile.exists()) {
            pathFile.createNewFile();

            FileWriter writer = new FileWriter(pathFile);

            writer.write("null");

            writer.close();
        }

        Scanner scanner = new Scanner(pathFile);

        String path = scanner.nextLine();

        scanner.close();

        if (!path.equals("null")) {
            FileManager.setDirectory(path);
        }

        if (!FileManager.getDirectory().exists()) {
            FileManager.getDirectory().mkdirs();
        }

        if (!FileManager.getConfig().exists()) {
            FileManager.getConfig().createNewFile();
        }

        this.config = new BotConfiguration();
        this.config.load();

        if (!FileManager.getLogs().exists()) {
            FileManager.getLogs().mkdirs();
        }

        if (!FileManager.getPlugins().exists()) {
            FileManager.getPlugins().mkdirs();
        }

        this.commandManager = new CommandManager();
        this.logger = new DragonLogger("Dragon Bot", FileManager.getLogs() + File.separator + this.format.format(new Date()) + "-%g.log");
        this.gitHubAPI = new GitHubAPI("dragovorn", "dragon-bot-twitch", this.config.getPreReleases());
        this.twitchAPI = new TwitchAPI(Keys.twitchClientID);
        this.loader = new PluginLoader();

        System.setErr(new PrintStream(new LoggingOutputStream(this.logger, Level.SEVERE), true));
        System.setOut(new PrintStream(new LoggingOutputStream(this.logger, Level.INFO), true));

        ImmutableList.Builder<BotPlugin> builder = new ImmutableList.Builder<>();

        getLogger().info("Loading plugins...");
        if (FileManager.getPlugins().listFiles() != null) {
            ExecutorService executorService = Executors.newCachedThreadPool();

            for (File file : FileManager.getPlugins().listFiles()) {
                if (!file.getName().matches("(.+).(jar)$")) {
                    continue;
                }

                executorService.execute(() -> {
                    try {
                        builder.add(this.loader.loadPlugin(file));
                    } catch (InvalidPluginException exception) {
                        exception.printStackTrace();
                    }
                });
            }

            executorService.shutdown();
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        }

        this.plugins = builder.build();

        getLogger().info("Loaded " + this.plugins.size() + " " + (this.plugins.size() == 1 ? "plugin" : "plugins") + "!");

        if (!path.equals("null")) {
            getLogger().info("Different file path found: " + FileManager.getDirectory().getPath());
        }

        start();
    }

    public static DragonBot getInstance() {
        return (DragonBot) Bot.getInstance();
    }

    @Override
    public void start() throws Exception {
        setState(BotState.STARTING);

        AmazonS3 client = new AmazonS3Client();
        this.manager = new TransferManager(client);

        UpdatePanel update = new UpdatePanel();

        new MainWindow(update);

        getLogger().info("Checking for updates...");
        getLogger().info("Checking for newer version of the updater...");

        if (client.getObjectMetadata(new GetObjectMetadataRequest("dl.dragovorn.com", "DragonBot/updater.jar")).getLastModified().getTime() > FileManager.getUpdater().lastModified()) {
            getLogger().info("Found a newer version of the updater, downloading it now...");

            FileManager.getUpdater().delete();

            GetObjectRequest request = new GetObjectRequest("dl.dragovorn.com", "DragonBot/updater.jar");
            request.setGeneralProgressListener((ProgressEvent event) -> {
                if (this.download == null) {
                    return;
                }

                getLogger().info("Downloaded " + this.download.getProgress().getBytesTransferred() + " out of " + this.download.getProgress().getTotalBytesToTransfer() + " bytes!");

                switch (event.getEventType()) {
                    case TRANSFER_COMPLETED_EVENT: {
                        getLogger().info("Download completed!");
                        break;
                    } case TRANSFER_FAILED_EVENT: {
                        try {
                            AmazonClientException exception = this.download.waitForException();

                            exception.printStackTrace();
                        } catch(InterruptedException exception) { /* Won't happen */ }

                        break;
                    }
                }
            });

            this.download = this.manager.download(request, FileManager.getUpdater());

            this.download.waitForCompletion();
        }

        update.update();

        if(update.shouldStop()) {
            stop();
            return;
        }

        getLogger().info("Initializing Dragon Bot " + getVersion() + "!");

        this.name = this.config.getName();
        this.auth = this.config.getAuth();

        this.commandManager.registerCommand(new Uptime());
        this.commandManager.registerCommand(new Github());
        this.commandManager.registerCommand(new VersionCmd());

        getLogger().info("Enabling " + this.plugins.size() + " " + (this.plugins.size() == 1 ? "plugin" : "plugins") +"...");

        ExecutorService executorService = Executors.newCachedThreadPool();

        this.plugins.forEach(plugin -> {
            getLogger().info("Enabling " + plugin.getInfo().getName() + "...");

            executorService.execute(plugin::onEnable);

            getLogger().info("Enabled " + plugin.getInfo().getName() + " v" + plugin.getInfo().getVersion() + "!");
        });

        executorService.shutdown();
        executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

        getLogger().info(this.plugins.size() + " plugins enabled!");

        if (!this.name.equals("") && !this.config.getAuth().equals("")) {
            getLogger().info("Connecting to twitch!");

            try {
                connect();
            } catch (ConnectionException | IOException exception) {
                getLogger().info("Unable to connect!");
            }

            if (this.config.getAutoConnect() && !this.config.getChannel().equals("")) {
                connectTo("#" + this.config.getChannel());
            }
        } else {
            getLogger().info("No twitch account detected.");
        }

        if (!this.isConnected()) {
            MainWindow.getInstance().getChannelButton().setEnabled(false);
            MainWindow.getInstance().getChannelButton().setToolTipText("The current account was unable to connect!");
        }

        setState(BotState.RUNNING);

        MainWindow.getInstance().setContentPane(MainWindow.getInstance().getPanel());
        MainWindow.getInstance().pack();

        getLogger().info("Dragon Bot " + getVersion() + " initialized!");
    }

    @Override
    public void stop() {
        setState(BotState.ENDING);

        new Thread("Shutdown Thread") {

            @Override
            public void run() {
                manager.shutdownNow();

                leaveChannel();

                getLogger().info("Disabling " + plugins.size() + " " + (plugins.size() == 1 ? "plugin" : "plugins") + "...");

                if (plugins != null) {
                    ExecutorService executorService = Executors.newCachedThreadPool();

                    plugins.forEach(plugin -> {
                        getLogger().info("Disabling " + plugin.getInfo().getName() + " " + plugin.getInfo().getVersion() + "!");

                        executorService.execute(plugin::onDisable);

                        getLogger().info(plugin.getInfo().getName() + " " + plugin.getInfo().getVersion() + " disabled!");
                    });

                    executorService.shutdown();
                    try {
                        executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
                    } catch (InterruptedException exception) {
                        exception.printStackTrace();
                    }
                }

                getLogger().info("Saving configuration...");

                config.save();

                for (Handler handler : getLogger().getHandlers()) {
                    handler.close();
                }

                System.exit(0);
            }
        }.start();
    }

    public synchronized void connect() throws ConnectionException, IOException {
        disconnect();
        connect("irc.twitch.tv", 6667, this.auth);
    }

    private synchronized void disconnect() {
        if (!this.isConnected()) {
            return;
        }

        this.sendRawLine("QUIT :");
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getPassword() {
        return this.auth;
    }

    @Override
    public void setName(String name) {
        this.name = name;
        this.config.setName(name);
    }

    @Override
    public void setPassword(String password) {
        this.auth = password;
        this.config.setAuth(password);
    }

    @Override
    public String getVersion() {
        return Version.PRETTY_VERSION;
    }

    @Override
    public String getAuthor() {
        return "Dragovorn";
    }

    @Override
    public Logger getLogger() {
        return this.logger;
    }

    @Override
    public File getPluginsFolder() {
        return null;
    }

    @Override
    public BotConfiguration getConfiguration() {
        return this.config;
    }

    @Override
    public String getChannel() {
        if (this.connection == null) {
            return "";
        }

        return this.connection.getChannel();
    }

    @Override
    public synchronized void connectTo(String channel) {
        this.connection.setChannel(channel.substring(1));
        this.config.setChannel(channel.substring(1));

        sendRawLine("JOIN " + channel);

        getEventBus().post(new ChannelEnterEvent(channel));
    }

    @Override
    public void leaveChannel() {
        if (this.connection == null || this.connection.getChannel().equals("")) {
            return;
        }

        sendRawLine("PART " + this.connection.getChannel());

        this.connection.setChannel("");
    }

    @Override
    public synchronized void connect(String ip, int port, String password) throws ConnectionException, IOException {
        if (isConnected()) {
            throw new ConnectionException("You are already connected to twitch!");
        }

        this.connection = new Connection(ip, port, password);

        Socket socket;

        if (this.connection.useSSL()) {
            try {
                SocketFactory factory;

                if (this.connection.verifySSL()) {
                    factory = SSLSocketFactory.getDefault();
                } else {
                    SSLContext context = UnverifiedSSL.getUnverifedSSLContext();
                    factory = context.getSocketFactory();
                }

                socket = factory.createSocket(this.connection.getServer(), this.connection.getPort());
            } catch (Exception exception) {
                throw new SSLException("SSL Failure");
            }
        } else {
            socket = new Socket(this.connection.getServer(), this.connection.getPort());
        }

        getLogger().info("Connected to twitch!");

        this.inetAddress = socket.getInetAddress();

        InputStreamReader reader;
        OutputStreamWriter writer;

        if (getEncoding() != null) {
            reader = new InputStreamReader(socket.getInputStream(), getEncoding());
            writer = new OutputStreamWriter(socket.getOutputStream(), getEncoding());
        } else {
            reader = new InputStreamReader(socket.getInputStream());
            writer = new OutputStreamWriter(socket.getOutputStream());
        }

        BufferedReader bufferedReader = new BufferedReader(reader);
        BufferedWriter bufferedWriter = new BufferedWriter(writer);

        if (this.connection.getPassword() != null && !this.connection.getPassword().equals("")) {
            OutputThread.sendRawLine(this, bufferedWriter, "PASS " + this.connection.getPassword());
        }

        OutputThread.sendRawLine(this, bufferedWriter, "NICK " + this.getName());
        OutputThread.sendRawLine(this, bufferedWriter, "USER " + this.getName() + " 8 * :" + this.getName());

        this.inputThread = new InputThread(this, socket, bufferedReader, bufferedWriter);

        String line;

        while ((line = bufferedReader.readLine()) != null) {
            this.handleLine(line);

            int firstSpace = line.indexOf(" ");
            int secondSpace = line.indexOf(" ", firstSpace + 1);

            if (secondSpace >= 0) {
                String code = line.substring(firstSpace + 1, secondSpace);

                if (code.equals("004")) {
                    // We've connected
                    break;
                } else if (code.equals("433")) {
                    // No action required
                } else if (code.equals("439")) {
                    // No action required
                } else if (code.startsWith("5") || code.startsWith("4")) {
                    socket.close();

                    this.inputThread = null;

                    throw new ConnectionException("Could not log into the IRC Server: " + line);
                }
            }
        }

        getLogger().info("Logged into the twitch server!");

        socket.setSoTimeout(5 * 60 * 1000);

        this.inputThread.start();

        if (this.outputThread == null) {
            this.outputThread = new OutputThread(this, this.outQueue);
            this.outputThread.start();
        }

        sendRawLine("CAP REQ :twitch.tv/membership");
        sendRawLine("CAP REQ :twitch.tv/commands");
        sendRawLine("CAP REQ :twitch.tv/tags");

        getEventBus().post(new ServerConnectEvent());
    }

    @Override
    public void sendMessage(String message) {
        if (this.connection.getChannel().equals("")) {
            return; // We are no in a channel then
        }

        this.outQueue.add("PRIVMSG #" + this.connection.getChannel() + " :" + message);
    }

    @Override
    public void sendMessage(String message, Object... objects) {
        this.sendMessage(String.format(message, objects));
    }

    @Override
    public synchronized boolean isConnected() {
        return this.inputThread != null && this.inputThread.isConnected();
    }

    @Override
    protected void handleLine(String rawLine) {
        String line = CharMatcher.WHITESPACE.trimFrom(rawLine);

        if (line.startsWith("PING")) {
            sendRawLine("PONG " + line.substring(5));
            return;
        }

        ImmutableMap.Builder<String, String> tags = ImmutableMap.builder();

        if (line.startsWith("@")) {
            String v3Tags = line.substring(1, line.indexOf(" "));
            line = line.substring(line.indexOf(" ") + 1);

            StringTokenizer tokenizer = new StringTokenizer(v3Tags);

            while (tokenizer.hasMoreTokens()) {
                String tag = tokenizer.nextToken(";");

                if (tag.contains("=")) {
                    String[] parts = tag.split("=");
                    tags.put(parts[0], (parts.length == 2 ? parts[1] : ""));
                } else {
                    tags.put(tag, "");
                }
            }
        }

        List<String> parsedLine = Utils.tokenizeLine(line);

        String senderInfo = parsedLine.remove(0);
        String command = parsedLine.remove(0);
        String target = null;
        String nick = "";
        String hostname = "";
        String login = "";

        int exclamation = senderInfo.indexOf("!");
        int at = senderInfo.indexOf("@");

        if (senderInfo.startsWith(":")) {
            if (exclamation > 0 && at > 0 && exclamation < at) {
                nick = senderInfo.substring(1, exclamation);
                login = senderInfo.substring(exclamation + 1, at);
                hostname = senderInfo.substring(at + 1);
            } else {
                if (!parsedLine.isEmpty()) {
                    String token = command;

                    int code = -1;

                    try {
                        code = Integer.parseInt(token);
                    } catch (NumberFormatException exception) {
                        // Leave it at -1
                    }

                    if (code == -1) {
                        return;
                    } else {
                        nick = senderInfo;
                        target = token;
                    }
                }
            }
        }

        command = command.toUpperCase();

        if (nick.startsWith(":")) {
            nick = nick.substring(1);
        }

        if (target == null) {
            target = parsedLine.remove(0);
        }

        if (target.startsWith(":")) {
            target = target.substring(1);
        }

        if (command.equals("PRIVMSG") && line.indexOf(":\u0001") > 0 && line.endsWith("\u0001")) {
            String request = line.substring(line.indexOf(":\u0001") + 2, line.length() - 1);

            // TODO: Check for CTP Requests
        } else if (command.equals("PRIVMSG") && this.channelPrefixes.indexOf(target.charAt(0)) >= 0) {
            User user = new User(nick, login, hostname, tags.build());

            String message = line.substring(line.indexOf(" :") + 2);
            boolean isCommand = false;

            getLogger().info("CHAT " + (user.isMod() ? "[M] " : "") + user.getName() + ": " + message);

            if (message.startsWith("!")) {
                for (Command cmd : getCommandManager().getCommands()) {
                    String[] args;

                    if ((args = getCommandManager().parseCommand(cmd.getName(), message)) != null) {
                        if (cmd.getArgs() == -1 || (cmd.isArgsRequired() ? args.length - 1 == cmd.getArgs() : args.length - 1 <= cmd.getArgs())) {
                            isCommand = true;
                            cmd.execute(user, getCommandManager().parseCommand(cmd.getName(), message));
                        }
                    }
                }
            }

            getEventBus().post(new UserMessageEvent(user, message, isCommand));
        }
    }

    @Override
    public synchronized void sendRawLine(String line) {
        if (isConnected()) {
            this.inputThread.sendRawLine(line);
        }
    }

    @Override
    public void sendRawLineViaQueue(String line) {
        if (line == null) {
            throw new NullPointerException("Cannot send null message to server");
        }

        if (isConnected()) {
            this.outQueue.add(line);
        }
    }

    @Override
    public int getMaxLineLength() {
        return InputThread.MAX_LINE_LENGTH;
    }

    @Override
    public int getMessageDelay() {
        return 1000;
    }

    @Override
    public void setEncoding(String charset) throws UnsupportedEncodingException {
        "".getBytes(charset);

        this.charset = charset;
    }

    @Override
    public String getEncoding() {
        return this.charset;
    }

    @Override
    public InetAddress getAddress() {
        return this.inetAddress;
    }

    @Override
    public CommandManager getCommandManager() {
        return this.commandManager;
    }

    public GitHubAPI getGitHubAPI() {
        return this.gitHubAPI;
    }

    public TwitchAPI getTwitchAPI() {
        return this.twitchAPI;
    }

    public ImmutableList<BotPlugin> getPlugins() {
        return this.plugins;
    }
}