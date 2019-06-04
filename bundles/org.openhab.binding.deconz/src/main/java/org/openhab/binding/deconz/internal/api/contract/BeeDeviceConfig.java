package org.openhab.binding.deconz.internal.api.contract;

import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;

@NonNullByDefault
public class BeeDeviceConfig {
    public @Nullable String UTC;
    public @Nullable String active;
    public @Nullable Integer announceinterval;
    public String announceurl = "http://dresden-light.appspot.com/discover";
    public String apiversion = "";
    public @Nullable String bridgeid;
    public @Nullable String datastoreversion;
    public @Nullable Boolean deleteDB;
    public @Nullable String devicename;
    public @Nullable Boolean dhcp;
    public @Nullable Boolean discovery;
    public @Nullable String eth0;
    public @Nullable Boolean factorynew;
    public @Nullable Boolean fwneedupdate;
    public @Nullable String fwupdatestate;
    public String fwversion = "";
    public @Nullable String gateway;
    public @Nullable Integer groupdelay;
    public @Nullable String homebridge;
    public @Nullable String homebridgepin;
    public @Nullable InternetServices internetservices;

    public static class InternetServices {
        public @Nullable String remoteaccess;
        public @Nullable String internet;
        public @Nullable String time;
        public @Nullable String swupdate;
    }

    public String ipaddress = "";
    public @Nullable String ip = "";
    public @Nullable Boolean linkbutton;
    public @Nullable String lastupdated;
    public @Nullable String localtime;
    public @Nullable String mac;
    public @Nullable String modelid;// = "deCONZ";
    public String name = "";
    public @Nullable String netmask;
    public @Nullable Integer networkopenduration;
    public @Nullable Boolean otauactive;
    public @Nullable String otaustate;
    public @Nullable Integer panid;
    public @Nullable Boolean pageactive;
    public @Nullable String password;
    public @Nullable Integer permitjoin;
    public @Nullable Integer permitjoinfull;
    public @Nullable Integer port;
    public @Nullable Boolean portalservices;
    public @Nullable String proxyaddress;
    public @Nullable Integer proxyport;
    public @Nullable String pw;
    public @Nullable String replacesbridgeid;
    public @Nullable Boolean resetGW;
    public @Nullable Boolean rfconnected;
    public @Nullable String runmode;
    public @Nullable String starterkitid;
    public @Nullable String state;
    public @Nullable String swcommit;
    public @Nullable SWUpdate swupdate;

    public static class SWUpdate {
        public @Nullable DeviceTypes devicetypes;

        public static class DeviceTypes {
            public @Nullable Boolean bridge;
            public @Nullable String @Nullable [] lights;
            public @Nullable String @Nullable [] sensors;

        }

        public @Nullable Boolean checkforupdate;
        public @Nullable Boolean notify;
        public @Nullable String text;
        public @Nullable Integer updatestate;
        public @Nullable String url;
        public @Nullable String version;
    }

    public @Nullable SWUpdate2 swupdate2;

    public static class SWUpdate2 {
        public @Nullable BridgeUpdate bridge;

        public static class BridgeUpdate {
            public @Nullable String lastinstall;
            public @Nullable String state;
        }

        public @Nullable AutoInstall autoinstall;

        public static class AutoInstall {
            public @Nullable Boolean on;
            public @Nullable String updatetime;
        }

        public @Nullable Boolean checkforupdate;
        public @Nullable Boolean install;
        public @Nullable String lastchange;
        public @Nullable String lastinstall;
        public @Nullable String state;
    }

    public String swversion = "";
    public @Nullable String system;
    public @Nullable String timeformat;
    public @Nullable String timezone;
    public @Nullable String type;
    public @Nullable String updatechannel;
    public String uuid = "";
    public @Nullable Boolean websocketnotifyall;
    public Integer websocketport = 0;
    /**
     * Outer Map holds ApiKey/Username as Key inner map holds
     * <li>create date</li>
     * <li>last use date</li>
     * <li>name</li>
     * TODO: fix by replacing inner map with WhitelistEntry.class as soon as Dresden Elektronik decides to remove
     * SPACES!!! from variable names...
     */
    private Map<String, Map<String, String>> whitelist = Collections.emptyMap();

    public Map<String, WhitelistEntry> getWhitelist() {
        Map<String, WhitelistEntry> publicMap = Collections.emptyMap();
        for (Entry<String, Map<String, String>> e : whitelist.entrySet()) {
            WhitelistEntry entry = new WhitelistEntry();
            entry.create_date = e.getValue().get("create date");
            entry.last_use_date = e.getValue().get("last use date");
            entry.name = e.getValue().get("name");
            publicMap.put(e.getKey(), entry);
        }
        return publicMap;
    }

    public static class WhitelistEntry {
        public String create_date = "";
        public String last_use_date = "";
        public String name = "";
    }

    public @Nullable String wifi;
    public @Nullable String wifiappw;
    public @Nullable WifiEntry @Nullable [] wifiavailable;

    public static class WifiEntry {
        public @Nullable Integer channel;
        public @Nullable Integer rssi;
        public @Nullable String ssid;
    }

    public @Nullable String wifichannel;
    public @Nullable String wificlientname;
    public @Nullable String wificlientpw;
    public @Nullable String wifiip;
    public @Nullable Integer wifimgmt;
    public @Nullable String wifiname;
    public @Nullable String wifitype;
    public @Nullable String wlan0;
    public @Nullable String workingtype;
    public @Nullable String workingname;
    public @Nullable String workingpw;
    public Integer zigbeechannel = 0;
}
