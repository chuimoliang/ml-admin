package com.moliang.utils;


import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Use SCP命令工具
 * @Author Chui moliang
 * @Date 2021/2/18 1:00
 * @Version 1.0
 */
@Slf4j
public class ScpClientUtil {

    private final String ip;

    private final int port;

    private final String username;

    private final String password;

    private static Map<String, ScpClientUtil> instance = new HashMap<>();

    synchronized public static ScpClientUtil getInstance(String ip, int port, String username, String password) {
        if(instance.get(ip) == null) {
            instance.put(ip, new ScpClientUtil(ip, port, username, password));
        }
        return instance.get(ip);
    }

    public void getFile(String remoteFile, String localTargetDirectory) {
        Connection conn = new Connection(ip, port);
        try {
            conn.connect();
            boolean isAuthenticated = conn.authenticateWithPassword(username, password);
            if (!isAuthenticated) {
                log.error("ip : " + ip + "认证失败");
            }
            SCPClient client = new SCPClient(conn);
            client.get(remoteFile, localTargetDirectory);
        } catch (IOException e) {
            log.error(e.getMessage());
        }finally{
            conn.close();
        }
    }

    public void putFile(String localFile, String remoteTargetDirectory) {
        putFile(localFile, null, remoteTargetDirectory);
    }

    public void putFile(String localFile, String remoteFileName, String remoteTargetDirectory) {
        putFile(localFile, remoteFileName, remoteTargetDirectory,null);
    }

    public void putFile(String localFile, String remoteFileName, String remoteTargetDirectory, String mode) {
        Connection conn = new Connection(ip, port);
        try {
            conn.connect();
            boolean isAuthenticated = conn.authenticateWithPassword(username, password);
            if (!isAuthenticated) {
                log.error("ip : " + ip + "认证失败");
            }
            SCPClient client = new SCPClient(conn);
            if ((mode == null) || (mode.length() == 0)) {
                mode = "0600";
            }
            if (remoteFileName == null) {
                client.put(localFile, remoteTargetDirectory);
            } else {
                client.put(localFile, remoteFileName, remoteTargetDirectory, mode);
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }finally{
            conn.close();
        }
    }

    public ScpClientUtil(String ip, int port, String username, String password) {
        this.ip = ip;
        this.port = port;
        this.username = username;
        this.password = password;
    }
}
