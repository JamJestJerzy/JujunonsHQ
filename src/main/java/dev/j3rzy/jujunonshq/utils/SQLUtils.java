package dev.j3rzy.jujunonshq.utils;

import dev.j3rzy.jujunonshq.objects.CMessage;
import net.dv8tion.jda.api.entities.Message;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static dev.j3rzy.jujunonshq.utils.ConsoleUtils.log;

public class SQLUtils {
    private static final String ip;
    private static final String port;
    private static final String database;
    private static final String user;
    private static final String password;
    private static final String encrypt;
    private static final String trustServerCertificate;
    private static final String loginTimeout;

    static {
        try {
            ip = JSONUtils.getString("sql-ip");
            port = JSONUtils.getString("sql-port");
            database = JSONUtils.getString("sql-database");
            user = JSONUtils.getString("sql-user");
            password = JSONUtils.getString("sql-password");
            encrypt = JSONUtils.getString("sql-encrypt");
            trustServerCertificate = JSONUtils.getString("sql-trust-server-certificate");
            loginTimeout = JSONUtils.getString("sql-login-timeout");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static final String connectionUrl =
            "jdbc:sqlserver://" + ip + ":" + port + ";"
            + "databaseName=" + database + ";"
            + "user=" + user + ";"
            + "password=" + password + ";"
            + "encrypt=" + encrypt + ";"
            + "trustServerCertificate=" + trustServerCertificate + ";"
            + "loginTimeout=" + loginTimeout + ";";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(connectionUrl);
    }

    public static ResultSet query(String sql) {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            return statement.executeQuery(sql);
        }
        catch (SQLException e) { e.printStackTrace(); }
        catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static void saveMessage(Message message) throws SQLException {
        String guildId = message.getGuildId();
        String channelId = message.getChannelId();
        String messageId = message.getId();
        String authorId = message.getAuthor().getId();
        String content = message.getContentRaw();

        StringBuilder attachmentsBuilder = new StringBuilder();
        for (Message.Attachment attachment : message.getAttachments()) {
            attachmentsBuilder.append("[" + attachment.getFileName() + "]" + "(" + attachment.getUrl() + ") (" + MathUtils.getSize(attachment.getSize()) + ")" + "\n");
        }
        String attachments = attachmentsBuilder.toString();

        if (content.isEmpty()) content = null;
        if (attachments.isEmpty()) attachments = null;

        String[] values = {guildId, channelId, messageId, authorId, content, attachments};

        String sql = "INSERT INTO messages (guildId, channelId, messageId, authorId, content, attachments) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement pre = getConnection().prepareStatement(sql);
        for (int i = 0; i < values.length; i++) {
            if (values[i] == null || values[i].isEmpty()) {
                pre.setNull(i + 1, java.sql.Types.VARCHAR); // Set as NULL in the database
            } else {
                pre.setString(i + 1, values[i]);
            }
        }

        // Execute the prepared statement
        pre.executeUpdate();
    }

    public static CMessage getMessage(String messageId) throws SQLException {
        String sql = "select * from messages where messageId like ?";
        PreparedStatement pre = getConnection().prepareStatement(sql);
        pre.setString(1, messageId);

        ResultSet rs = pre.executeQuery();

        String guildId = null;
        String channelId = null;
        String authorId = null;
        String content = null;
        String modifiedContent = null;
        String attachments = null;

        while (rs.next()) {
            guildId = rs.getString("guildId");
            channelId = rs.getString("channelId");
            authorId = rs.getString("authorId");
            content = rs.getString("content");
            modifiedContent = rs.getString("modifiedContent");
            attachments = rs.getString("attachments");
        }

        return new CMessage(guildId, channelId, messageId, authorId, content, modifiedContent, attachments);
    }

    public static void checkForTables() throws SQLException {
        DatabaseMetaData dbm = getConnection().getMetaData();
        ResultSet tables = dbm.getTables(null, null, "messages", null);
        /* Table doesn't exists. */
        if (!tables.next()) {
            PreparedStatement pre = getConnection().prepareStatement("""
                create table messages (
                  id int identity(1,1) primary key,
                  guildId varchar(32) not null,
                  channelId varchar(32) not null,
                  messageId varchar(32) unique not null,
                  authorId varchar(32) not null,
                  content varchar(max),
                  modifiedContent varchar(max),
                  attachments varchar(max)
                )
                """);
            pre.executeQuery();
            log.info("Created table \"messages\" in selected database.");
        }
    }
}
