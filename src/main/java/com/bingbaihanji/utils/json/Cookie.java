package com.bingbaihanji.utils.json;

/*
Copyright (c) 2002 JSON.org

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

The Software shall be used for Good, not Evil.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

/**
 * Convert a web browser cookie specification to a JSONObject and back.
 * 将web浏览器cookie规范转换为JSONObject并返回。
 * JSON and Cookies are both notations for name/value pairs.
 * JSON和Cookies都是名称/值对的表示法。
 *
 * @author JSON.org
 * @version 2015-12-09
 */
public class Cookie {

    /**
     * 生成一个字符串的副本，其中字符'+'，'%'，'='，';'
     * 和控制字符替换为“%hh”这是一种URL编码
     * Produce a copy of a string in which the characters '+', '%', '=', ';'
     * and control characters are replaced with "%hh". This is a gentle form
     * of URL encoding, attempting to cause as little distortion to the
     * string as possible. The characters '=' and ';' are meta characters in
     * cookies. By convention, they are escaped using the URL-encoding. This is
     * only a convention, not a standard. Often, cookies are expected to have
     * encoded values. We urlEncode '=' and ';' because we must. We urlEncode '%' and
     * '+' because they are meta characters in URL encoding.
     *
     * @param string The source string.
     * @return The escaped result.
     */
    public static String escape(String string) {
        char c;
        String s = string.trim();
        int length = s.length();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i += 1) {
            c = s.charAt(i);
            if (c < ' ' || c == '+' || c == '%' || c == '=' || c == ';') {
                sb.append('%');
                sb.append(Character.forDigit((char) ((c >>> 4) & 0x0f), 16));
                sb.append(Character.forDigit((char) (c & 0x0f), 16));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }


    /**
     * 将cookie规范字符串转换为JSONObject。的字符串将包含一个用'='分隔的名值对
     * Convert a cookie specification string into a JSONObject. The string
     * will contain a name value pair separated by '='. The name and the value
     * will be unescaped, possibly converting '+' and '%' sequences. The
     * cookie properties may follow, separated by ';', also represented as
     * name=value (except the secure property, which does not have a value).
     * The name will be stored under the key "name", and the value will be
     * stored under the key "value". This method does not do checking or
     * validation of the parameters. It only converts the cookie string into
     * a JSONObject.
     *
     * @param cookie The cookie specification string.符合cookie规范的字符串
     * @return A JSONObject containing "name", "value", and possibly other
     * members.
     * @throws JSONException
     */
    public static JSONObject toJSONObject(String cookie) throws JSONException {
        String name;
        JSONObject jo = new JSONObject();
        Object value;
        JSONTokener x = new JSONTokener(cookie);
        jo.put("name", x.nextTo('='));
        x.next('=');
        jo.put("value", x.nextTo(';'));
        x.next();
        while (x.more()) {
            name = unescape(x.nextTo("=;"));
            if (x.next() != '=') {
                if (name.equals("secure")) {
                    value = Boolean.TRUE;
                } else {
                    throw x.syntaxError("Missing '=' in cookie parameter.");
                }
            } else {
                value = unescape(x.nextTo(';'));
                x.next();
            }
            jo.put(name, value);
        }
        return jo;
    }


    /**
     * 将JSONObject转换为cookie规范字符串
     * Convert a JSONObject into a cookie specification string. The JSONObject
     * must contain "name" and "value" members.
     * If the JSONObject contains "expires", "domain", "path", or "secure"
     * members, they will be appended to the cookie specification string.
     * All other members are ignored.
     *
     * @param json A JSONObject
     * @return A cookie specification string
     * @throws JSONException
     */
    public static String toString(JSONObject json) throws JSONException {
        StringBuilder sb = new StringBuilder();

        sb.append(escape(json.getString("name")));
        sb.append("=");
        sb.append(escape(json.getString("value")));
        if (json.has("expires")) {
            sb.append(";expires=");
            sb.append(json.getString("expires"));
        }
        if (json.has("domain")) {
            sb.append(";domain=");
            sb.append(escape(json.getString("domain")));
        }
        if (json.has("path")) {
            sb.append(";path=");
            sb.append(escape(json.getString("path")));
        }
        if (json.optBoolean("secure")) {
            sb.append(";secure");
        }
        return sb.toString();
    }

    /**
     * 将%hh序列转换为单个字符，并将加号转换为空格。
     * Convert <code>%</code><i>hh</i> sequences to single characters, and
     * convert plus to space.
     *
     * @param string A string that may contain
     *               <code>+</code>&nbsp;<small>(plus)</small> and
     *               <code>%</code><i>hh</i> sequences.
     * @return The unescaped string.
     */
    public static String unescape(String string) {
        int length = string.length();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; ++i) {
            char c = string.charAt(i);
            if (c == '+') {
                c = ' ';
            } else if (c == '%' && i + 2 < length) {
                int d = JSONTokener.dehexchar(string.charAt(i + 1));
                int e = JSONTokener.dehexchar(string.charAt(i + 2));
                if (d >= 0 && e >= 0) {
                    c = (char) (d * 16 + e);
                    i += 2;
                }
            }
            sb.append(c);
        }
        return sb.toString();
    }
}
