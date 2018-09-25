package com.imlianai.zjdoll.app.modules.publics.qiniu.pili;

import java.io.UnsupportedEncodingException;


import com.alibaba.fastjson.JSON;
import com.imlianai.zjdoll.app.modules.publics.qiniu.utils.UrlSafeBase64;

@SuppressWarnings("static-access")
public final class Stream {
    private StreamInfo info;
    private String baseUrl;
    private RPC cli;

    public Stream() {
    }

    Stream(StreamInfo info, RPC cli) throws UnsupportedEncodingException {
        this.info = info;
        String ekey = UrlSafeBase64.encodeToString(info.getKey());
        this.baseUrl = String.format("%s%s/v2/hubs/%s/streams/%s", Config.APIHTTPScheme, Config.APIHost, info.getHub(), ekey);
        this.cli = cli;
    }

    public String getHub() {
        return info.getHub();
    }

    public long getDisabledTill() {
        return info.getDisabledTill();
    }

    private void setDisabledTill(long disabledTill) throws PiliException {
        DisabledArgs args = new DisabledArgs(disabledTill);
        String path = baseUrl + "/disabled";
        String json = JSON.toJSONString(args);

        try {
            cli.callWithJson(path, json);
        } catch (PiliException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new PiliException(e);
        }
    }

    public String getKey() {
        return info.getKey();
    }

    public String toJson() {
        return JSON.toJSONString(info);
    }

    /*
        disable
     */
    public void disable() throws PiliException {
        setDisabledTill(-1);
    }

    /*
        enable
     */
    public void enable() throws PiliException {
        setDisabledTill(0);
    }

    public LiveStatus liveStatus() throws PiliException {
        String path = baseUrl + "/live";
        try {
            String resp = cli.callWithGet(path);
            LiveStatus status = JSON.parseObject(resp, LiveStatus.class);
            return status;
        } catch (PiliException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new PiliException(e);
        }
    }

    /*
        Save
     */
    private String appendQuery(String path, long start, long end) {
        String flag = "?";
        if (start > 0) {
            path += String.format("%sstart=%d", flag, start);
            flag = "&";
        }
        if (end > 0) {
            path += String.format("%send=%d", flag, end);
        }
        return path;
    }

    public String save(long start, long end) throws PiliException {
        String path = appendQuery(baseUrl + "/saveas", start, end);
        SaveArgs args = new SaveArgs(start, end);
        String json = JSON.toJSONString(args);

        try {
			String resp = cli.callWithJson(path, json);
            SaveRet ret = JSON.parseObject(resp, SaveRet.class);
            return ret.fname;
        } catch (PiliException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new PiliException(e);
        }

    }

    public Record[] historyRecord(long start, long end) throws PiliException {
        String path = appendQuery(baseUrl + "/historyrecord", start, end);
        try {
            String resp = cli.callWithGet(path);
            HistoryRet ret = JSON.parseObject(resp, HistoryRet.class);
            return ret.items;
        } catch (PiliException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new PiliException(e);
        }
    }

    private class DisabledArgs {
        @SuppressWarnings("unused")
		long disabledTill;

        public DisabledArgs(long disabledTill) {
            this.disabledTill = disabledTill;
        }
    }

    /*
        LiveStatus
     */
    public class FPSStatus {
        public int audio;
        public int video;
        public int data;
    }

    public class LiveStatus {
        public long startAt;
        public String clientIP;
        public int bps;
        public FPSStatus fps;

        public String toJson() {
            return JSON.toJSONString(this);
        }
    }

    @SuppressWarnings("unused")
    private class SaveArgs {
		long start;
        long end;

        public SaveArgs(long start, long end) {
            this.start = start;
            this.end = end;
        }
    }

    private class SaveRet {
        String fname;
    }

    /*
        history
     */
    public class Record {
        public long start;
        public long end;
    }

    private class HistoryRet {
        Record[] items;
    }


}
