package com.anjoyo.xyl.run.hookgps;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class dn {
    public static final Locale a;
    private static final int[] b;
    private Context c;
    private SharedPreferences d;
    private Editor e;

    static {
        b = new int[]{-16777216, -16776961, -16711681, -7829368, -16711936, -65281, -65536, -1, -256};
        a = new Locale("en");
    }

    public dn(Context context) {
        this.c = context;
        this.d = PreferenceManager.getDefaultSharedPreferences(context);
        this.e = this.d.edit();
    }

//    public static Drawable a(Drawable drawable, int i) {
//        ColorStateList valueOf = ColorStateList.valueOf(i);
//        Drawable f = a.f(drawable);
//        a.a(f, valueOf);
//        return f;
//    }

    public static String a(double d, double d2) {
        return String.format(a, "%.7f,%.7f", new Object[]{Double.valueOf(d), Double.valueOf(d2)}) + ",0,0,0,WGS84";
    }

    public static void a(Activity activity) {
        ((InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(activity.getCurrentFocus().getApplicationWindowToken(), 2);
    }

    public static void a(Context context) {
        Intent intent = new Intent();
        intent.setAction("xyl");
        intent.putExtra("changed", "1");
        context.sendBroadcast(intent);
    }

    public static void a(Context context, int i) {
        Toast.makeText(context, i, Toast.LENGTH_SHORT).show();
    }

    public static void a(Context context, String str) {
        ((ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("xyl", str));
    }

    public static void a(Throwable th) {
        Log.e("xyl", Log.getStackTraceString(th));
    }

    public static boolean a(String str, ApplicationInfo applicationInfo) {
        for (String contains : new String[]{"de.robv.android.xposed.installer", "org.mozilla.mozstumbler", "com.keramidas.TitaniumBackup", "com.github.shadowsocks"}) {
            if (contains.contains(str)) {
                return true;
            }
        }
        return applicationInfo != null ? (str.contains("com.google.android.webview") && (applicationInfo.flags & 1) > 0) ? true : !applicationInfo.packageName.contains("google") && !applicationInfo.packageName.contains("camera") && (applicationInfo.flags & 1) > 0  : true;
    }

    public static String[] a(String str, ApplicationInfo applicationInfo, Context context) {
        if (a(str, applicationInfo)) {
            return new String[]{"", ""};
        }
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return a(str, defaultSharedPreferences.getString("keyp0", ""), defaultSharedPreferences.getString("keyp1", ""), defaultSharedPreferences.getString("keyp2", ""));
    }

    public static String[] a(String str, String str2, String str3, String str4) {
        String[] strArr = new String[]{"", ""};
        String[] split = n(str2).split(",");
        String[] split2 = n(str3).split(",");
        String[] split3 = n(str4).split(",");
        int i = 0;
        for (CharSequence charSequence : split) {
            if (!charSequence.equals("")) {
                i = (i != 0 || str.contains(charSequence)) ? 1 : 0;
            }
        }
        int i2 = 0;
        for (CharSequence charSequence2 : split2) {
            if (!charSequence2.equals("")) {
                i2 = (i2 != 0 || str.contains(charSequence2)) ? 1 : 0;
            }
        }
        int i3 = 0;
        for (CharSequence charSequence22 : split3) {
            if (!charSequence22.equals("")) {
                i3 = (i3 != 0 || str.contains(charSequence22)) ? 1 : 0;
            }
        }
        if (!(i == 0 && i2 == 0 && i3 == 0)) {
            String str5 = i != 0 ? "0" : i2 != 0 ? "1" : "2";
            strArr[0] = str5;
            String str6 = i != 0 ? "1" : i2 != 0 ? "2" : "3";
            strArr[1] = str6;
        }
        return strArr;
    }

    public static void b(Context context) throws IOException {
        InputStream open;
        OutputStream fileOutputStream;
        IOException e;
        Throwable th;
        InputStream inputStream = null;
        File databasePath = context.getDatabasePath("location.db");
        if (!databasePath.exists()) {
            File parentFile = context.getDatabasePath("location.db").getParentFile();
            if (!parentFile.exists()) {
                try {
                    if (!parentFile.mkdirs()) {
                        Toast.makeText(context, "Can't mkdirs", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            try {
                open = context.getAssets().open("location.db");
                try {
                    fileOutputStream = new FileOutputStream(databasePath);
                    try {
                        byte[] bArr = new byte[10240];
                        while (true) {
                            int read = open.read(bArr);
                            if (read <= 0) {
                                break;
                            }
                            fileOutputStream.write(bArr, 0, read);
                        }
                        fileOutputStream.flush();
                        fileOutputStream.close();
                        OutputStream outputStream = null;
                        if (open != null) {
                            try {
                                open.close();
                            } catch (Exception e3) {
                                e3.printStackTrace();
                            }
                        }
                        if (null != null) {
                            try {
                                outputStream.close();
                            } catch (Exception e22) {
                                e22.printStackTrace();
                            }
                        }
                    } catch (IOException e4) {
                        e = e4;
                        inputStream = open;
                        try {
                            e.printStackTrace();
                            if (inputStream != null) {
                                try {
                                    inputStream.close();
                                } catch (Exception e222) {
                                    e222.printStackTrace();
                                }
                            }
                            if (fileOutputStream == null) {
                                try {
                                    fileOutputStream.close();
                                } catch (Exception e2222) {
                                    e2222.printStackTrace();
                                }
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            open = inputStream;
                            if (open != null) {
                                try {
                                    open.close();
                                } catch (Exception e5) {
                                    e5.printStackTrace();
                                }
                            }
                            if (fileOutputStream != null) {
                                try {
                                    fileOutputStream.close();
                                } catch (Exception e32) {
                                    e32.printStackTrace();
                                }
                            }
                            throw th;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        if (open != null) {
                            open.close();
                        }
                        if (fileOutputStream != null) {
                            fileOutputStream.close();
                        }
                        throw th;
                    }
                } catch (IOException e6) {
                    e = e6;
                    fileOutputStream = null;
                    inputStream = open;
                    e.printStackTrace();
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    if (fileOutputStream == null) {
                        fileOutputStream.close();
                    }
                } catch (Throwable th4) {
                    th = th4;
                    fileOutputStream = null;
                    if (open != null) {
                        open.close();
                    }
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                    }
                    throw th;
                }
            } catch (IOException e7) {
                e = e7;
                fileOutputStream = null;
                e.printStackTrace();
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
                if (fileOutputStream == null) {
                    try {
                        fileOutputStream.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            } catch (Throwable th5) {
                fileOutputStream = null;
                open = null;
                if (open != null) {
                    try {
                        open.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            }
        }
    }

    public static void b(Context context, String str) {
        Editor edit = PreferenceManager.getDefaultSharedPreferences(context).edit();
        edit.putString("key299", str);
        edit.putLong("6", System.currentTimeMillis());
        edit.commit();
        i();
        a(context);
    }

    public static void c(Context context, String str) {
        a(context, str);
        b(context, str);
    }

    public static void d(Context context, String str) {
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
    }

    public static void d(String str) {
        Log.w("xyl", str);
    }

    public static boolean e(String str) {
        return Pattern.compile("^((-?\\d+(\\.\\d+)?,){2}(\\d+,){3}.+)$").matcher(str).matches();
    }

    public static boolean f(String str) {
        return Pattern.compile("^([0-9A-Fa-f]{2}:){5}[0-9A-Fa-f]{2}$").matcher(str).matches();
    }

    public static boolean g(String str) {
        return Pattern.compile("^((-?\\d+(\\.\\d+)?,){2}(\\d+,){4}.+)$").matcher(str).matches();
    }

    public static boolean h(String str) {
        return Pattern.compile("^(\\d){5,6}$").matcher(str).matches();
    }

    public static void i() {
        new File("/data/data/com.anjoyo.xyl.run/shared_prefs/com.anjoyo.xyl.run_preferences.xml").setReadable(true, false);
    }

    public static boolean i(String str) {
        return Pattern.compile("^\\d+(\\.\\d+)?$").matcher(str).matches();
    }

    public static boolean j(String str) {
        return Pattern.compile("^-?\\d+(\\.\\d+)?$").matcher(str).matches();
    }

    public static boolean k(String str) {
        return Pattern.compile("^\\d+$").matcher(str).matches();
    }

    public static boolean l(String str) {
        return Pattern.compile("^\\d{1,2}$").matcher(str).matches();
    }

    public static boolean m(String str) {
        return Pattern.compile("^-?\\d+(\\.\\d+)?,-?\\d+(\\.\\d+)?$").matcher(str).matches();
    }

    public static String n(String str) {
        return str.replaceAll("\\s+", "");
    }

    public static String[] o(String str) {
        Matcher matcher = Pattern.compile("(GSM|CDMA|UMTS|LTE).*?(\\d{5,6})", Pattern.CASE_INSENSITIVE).matcher(str);
        if (!matcher.find()) {
            return null;
        }
        String toUpperCase = matcher.group(1).toUpperCase(a);
        String str2 = "1";
        if (toUpperCase.equals("GSM")) {
            str2 = "2";
        }
        if (toUpperCase.equals("CDMA")) {
            str2 = "4";
        }
        if (toUpperCase.equals("UMTS")) {
            str2 = "3";
        }
        if (toUpperCase.equals("LTE")) {
            str2 = "13";
        }
        return new String[]{str2, matcher.group(2)};
    }

    public String a() {
        return this.d.getString("key299", "");
    }

    public void a(double d) {
        if (d < 0.0d) {
            d += 360.0d;
        }
        this.e.putString("bearing", String.valueOf(d));
        this.e.commit();
        i();
    }

    public void a(float f) {
        this.e.putFloat("zoom", f);
        this.e.commit();
        i();
    }

    public void a(int i) {
        this.e.putInt("speedratio", i);
        this.e.commit();
        i();
    }

    public void a(String str) {
        a(this.c, str);
    }

    public float b() {
        return this.d.getFloat("zoom", 1.0f);
    }

    public void b(float f) {
        this.e.putString("speed", String.valueOf(f));
        this.e.commit();
        i();
        a(this.c);
    }

    public void b(String str) {
        this.e.putString("key299", str);
        this.e.putLong("6", System.currentTimeMillis());
        this.e.commit();
        i();
        a(this.c);
    }

    public int c() {
        return this.d.getInt("speedratio", 1);
    }

    public void c(String str) {
        a(str);
        b(str);
    }

    public float d() {
        return Float.parseFloat(this.d.getString("ratio", "1"));
    }

    public float e() {
        String string = this.d.getString("rotationratio", "1");
        return !string.equals("") ? Float.parseFloat(string) : 1.0f;
    }

    public String f() {
        return this.d.getString("floatingtype", "0");
    }

    public boolean g() {
        String[] strArr = new String[]{"location0", "location1", "location2"};
        int length = strArr.length;
        int i = 0;
        while (i < length) {
            if (!this.d.getString(strArr[i], "").equals("99")) {
                i++;
            } else if (e(a())) {
                return true;
            } else {
                a(this.c, 2131099722);
                return false;
            }
        }
        a(this.c, 2131099723);
        return false;
    }

    public int h() {
        return b[Integer.parseInt(this.d.getString("floatingcolor", "0"))];
    }
}
