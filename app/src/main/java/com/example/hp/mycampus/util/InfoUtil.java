package com.example.hp.mycampus.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import com.example.hp.mycampus.model.Lesson;

public class InfoUtil {

    private final static String url_safecode = "http://210.42.121.241/servlet/GenImg"; // 验证码
    private final static String url_login = "http://210.42.121.241/servlet/Login"; // 登录
    private static String url_lessons = "http://210.42.121.241/stu/stu_index.jsp";// 课表

    private final static String path1 = "safecode.png";
    private static Map<String, String> cookies;
    private static ArrayList<Lesson> lessons = new ArrayList<>();
    private static String reason = "";




    public static ArrayList<Lesson> getLessons() {
        return lessons;
    }

    public static String getReason() {
        return reason;
    }

    public static void getSafeCode() {
                Response res = null;
                try {
                    res = Jsoup.connect(url_safecode).ignoreContentType(true).method(Method.GET)
                            .execute();
                    byte[] bytes = res.bodyAsBytes();
                    System.out.println("byte文件已经获取！");

                    saveFile(bytes);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                cookies = res.cookies();

    }

    private static void saveFile(byte[] data) {
        if (data != null) {
            String filepath ="data/data/com.example.hp.mycampus/safecode.png";
            File file = new File(filepath);

            if (file.exists()) {
                file.delete();
            }
            try {
                file.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println("文件名称：" + file.getName()+"和"+file.getPath());
            try {

                FileOutputStream fos = new FileOutputStream(file);
                fos.write(data, 0, data.length);
                fos.flush();
                fos.close();
                System.out.println("图片已经成功存储!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean Login(String id, String pwd, String code) {
        Response res = null;
        try {
            res = Jsoup
                    .connect(url_login).cookies(cookies).data("id", id, "content",
                            new SimpleDateFormat("SSS").format(new Date()) + ",", "pwd", MD5(pwd), "xdvfb", code)
                    .method(Method.POST).execute();

            String result = res.body();

            if (result.contains("武汉大学教务部")) { // 登录成功

                String url = result;
                url = url.substring(url.indexOf("school"));
                url = url.substring(url.indexOf("('") + 2);
                url = url.substring(0, url.indexOf("'"));

                url_lessons = "http://210.42.121.241" + url+"&year=2018&term=%C9%CF";
                lessons = dealWithLessons(url_lessons);
                return true;
            } else {
                if (!result.contains("对不起，您无权访问当前页面")) { // 登录失败

                    result = result.substring(result.indexOf("center"));
                    result = result.substring(result.indexOf("px;\">"));
                    reason = result.substring(5, result.indexOf("<"));
                    return false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static ArrayList<Lesson> dealWithLessons(String url) {
        ArrayList<Lesson> lessons = new ArrayList<>();
        Response res = null;
        try {
            res = Jsoup.connect(url).cookies(cookies).method(Method.GET).execute();

            String result = res.body();
            lessons = parseLessons(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lessons;
    }

    private static ArrayList<Lesson> parseLessons(String s) {
        ArrayList<Lesson> list = new ArrayList<>();
        s = s.substring(s.indexOf("checkBrowserType"));
        s = s.substring(0, s.indexOf("</script>"));

        while (s.contains("var")) {
            s = s.substring(s.indexOf("=") + 1);
            String lessonName = s.substring(2, s.indexOf("\";"));

            s = s.substring(s.indexOf("=") + 1);
            String day = s.substring(2, s.indexOf("\";"));

            s = s.substring(s.indexOf("=") + 1);
            s = s.substring(s.indexOf("=") + 1);
            String beginWeek = s.substring(2, s.indexOf("\";"));

            s = s.substring(s.indexOf("=") + 1);
            String endWeek = s.substring(2, s.indexOf("\";"));

            s = s.substring(s.indexOf("=") + 1);
            String classNote = s.substring(2, s.indexOf("\";"));

            s = s.substring(s.indexOf("=") + 1);
            String beginTime = s.substring(2, s.indexOf("\";"));

            s = s.substring(s.indexOf("=") + 1);
            String endTime = s.substring(2, s.indexOf("\";"));

            s = s.substring(s.indexOf("=") + 1);
            String detail = s.substring(2, s.indexOf("\";"));

            s = s.substring(s.indexOf("=") + 1);
            String classRoom = s.substring(2, s.indexOf("\";"));

            s = s.substring(s.indexOf("=") + 1);
            String weekInterVal = s.substring(2, s.indexOf("\";"));

            s = s.substring(s.indexOf("=") + 1);
            String teacherName = s.substring(2, s.indexOf("\";"));

            s = s.substring(s.indexOf("=") + 1);
            String professionName = s.substring(2, s.indexOf("\";"));

            s = s.substring(s.indexOf("=") + 1);
            String planType = s.substring(2, s.indexOf("\";"));

            s = s.substring(s.indexOf("=") + 1);
            String credit = s.substring(2, s.indexOf("\";"));

            s = s.substring(s.indexOf("=") + 1);
            String areaName = s.substring(2, s.indexOf("\";"));

            s = s.substring(s.indexOf("=") + 1);
            s = s.substring(s.indexOf("=") + 1);
            String academicTeach = s.substring(2, s.indexOf("\";"));

            s = s.substring(s.indexOf("=") + 1);
            String grade = s.substring(2, s.indexOf("\";"));

            s = s.substring(s.indexOf("=") + 1);
            s = s.substring(s.indexOf("=") + 1);
            String note = s.substring(2, s.indexOf("\";"));

            s = s.substring(s.indexOf("=") + 1);
            String state = s.substring(2, s.indexOf("\";"));

            s = s.substring(s.indexOf("addCoursediv") + 1);
            s = s.substring(s.indexOf("addCoursediv") + 1);

            Lesson lesson = new Lesson(lessonName, day, beginWeek, endWeek, classNote, beginTime, endTime, detail,
                    classRoom, weekInterVal, teacherName, professionName, planType, credit, areaName, academicTeach,
                    grade, note, state);
            list.add(lesson);
        }
        return list;
    }


    private static String MD5(String key) {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
        try {
            byte[] btInput = key.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }
}

