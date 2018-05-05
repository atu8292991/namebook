package com.atu.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;

import org.springframework.util.StringUtils;

/**
 * @author <a href="mailto:panhao.ph@alibaba-inc.com">mojin<a/>
 * @date 2018/05/04
 */
public class ResourceReader {

    public static void readFile(String fileName, String charset, LineTextCallback callback) throws IOException {
        InputStream res = ResourceReader.class.getClassLoader().getResourceAsStream(fileName);
        InputStreamReader inputStreamReader = new InputStreamReader(res, charset);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String lineText = null;

        while ((lineText = bufferedReader.readLine()) != null)
        {
            callback.solve(lineText);
        }
        bufferedReader.close();
        inputStreamReader.close();
        res.close();
    }

    public static void readString(String str, LineTextCallback callback) throws IOException {
        if (StringUtils.isEmpty(str)) {
            return;
        }
        StringReader stringReader = new StringReader(str);
        BufferedReader bufferedReader = new BufferedReader(stringReader);
        String lineText = null;

        while ((lineText = bufferedReader.readLine()) != null)
        {
            callback.solve(lineText);
        }
        bufferedReader.close();
        stringReader.close();
    }

    public interface LineTextCallback {
        void solve(String lineText);
    }
}
