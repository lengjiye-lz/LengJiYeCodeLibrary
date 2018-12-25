package com.lengjiye.code.activity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.BatteryManager;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.OrientationEventListener;
import android.view.View;
import android.widget.TextView;

import com.code.lengjiye.mvp.BasicMvpActivity;
import com.code.lengjiye.mvp.presenter.MvpPresenter;
import com.lengjiye.code.R;
import com.lengjiye.tools.LogTool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author liuzhuo
 */
public class DeviceInformationActivity extends BasicMvpActivity implements SensorEventListener {
    private TextView text;
    private SensorManager sensorManager;

    private OrientationEventListener mOrientationListener;

    @Override
    protected void initViews() {
        super.initViews();

        text = findViewById(R.id.text);
        text.setMovementMethod(ScrollingMovementMethod.getInstance());
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
    }

    @Override
    public MvpPresenter createPresenter() {
        return null;
    }

    @Override
    protected void setListener() {
        super.setListener();
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkBlueStacksFiles()) {
                    text.setText("是模拟器");
                } else {
                    text.setText("不是模拟器");
                }
                getInfo();
                getScreenBrightness(DeviceInformationActivity.this);
                String battery = getBatteryCapacity(DeviceInformationActivity.this);
                text.append("电池容量：" + battery);
            }
        });

        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handler.sendEmptyMessageDelayed(1, 100);
            }
        });

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (handler != null) {
                    handler.removeMessages(1);
                }
                getSensorList();
            }
        });

        mOrientationListener = new OrientationEventListener(this,
                SensorManager.SENSOR_DELAY_NORMAL) {

            @Override
            public void onOrientationChanged(int orientation) {
                if (orientation == OrientationEventListener.ORIENTATION_UNKNOWN) {
                    // 手机平放时检测不到有效角度
                    return;
                }
                LogTool.e("实时角度：" + orientation);
                //可以根据不同角度检测处理，这里只检测四个角度的改变
                if (orientation > 350 || orientation < 10) {
                    // 0度
                    orientation = 0;
                } else if (orientation > 80 && orientation < 100) {
                    // 90度
                    orientation = 90;
                } else if (orientation > 170 && orientation < 190) {
                    // 180度
                    orientation = 180;
                } else if (orientation > 260 && orientation < 280) {
                    // 270度
                    orientation = 270;
                } else {
                    return;
                }

                LogTool.e("手机角度：" + orientation);
            }
        };

        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mOrientationListener.canDetectOrientation()) {
                    mOrientationListener.disable();
                } else {
                    mOrientationListener.enable();
                }
            }
        });
    }

    @Override
    public int getResourceId() {
        return R.layout.activity_device_information;
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    handler.sendEmptyMessageDelayed(1, 1000);
                    getSensor();
                    break;
            }
        }
    };


    /**
     * 获取传感器信息
     */
    private void getSensor() {
        text.setText("");
        //1	加速度传感器	TYPE_ACCELEROMETER
        //2	温度传感器	TYPE_AMBIENT_TEMPERATURE
        //3	陀螺仪传感器	TYPE_GYROSCOPE
        //4	光线传感器	TYPE_LIGHT
        //5	磁场传感器	TYPE_MAGNETIC_FIELD
        //6	压力传感器	TYPE_PRESSURE
        //7	临近传感器	TYPE_PROXIMITY
        //8	湿度传感器	TYPE_RELATIVE_HUMIDITY
        //Sensor.TYPE_LIGHT 代表光照传感器
//        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT), SensorManager.SENSOR_DELAY_NORMAL);
//
        //注册加速度传感器
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_FASTEST);

        // 注册磁场传感器
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
                SensorManager.SENSOR_DELAY_FASTEST);

        // 光线
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT), SensorManager.SENSOR_DELAY_NORMAL);

        //温度
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE),
                SensorManager.SENSOR_DELAY_FASTEST);

        // 陀螺仪
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE),
                SensorManager.SENSOR_DELAY_FASTEST);

        //压力
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY),
                SensorManager.SENSOR_DELAY_FASTEST);

        // 临近值
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY),
                SensorManager.SENSOR_DELAY_FASTEST);
        // 计步
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR),
                SensorManager.SENSOR_DELAY_NORMAL);
        // 方向传感器
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_FASTEST);


    }

    /**
     * 获取设备列表
     */
    private void getSensorList() {
        text.setText("");
        // 获得当前手机支持的所有传感器
        List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
        for (Sensor sensor : sensors) {
            // 输出当前传感器的名称
            text.append(sensor.getName() + "\n");
        }
    }

    /**
     * BASEBAND-VER
     * 基带版本
     * return String
     */
    public static String getBaseband_Ver() {
        String Version = "";
        try {
            Class cl = Class.forName("android.os.SystemProperties");
            Object invoker = cl.newInstance();
            Method m = cl.getMethod("get", new Class[]{String.class, String.class});
            Object result = m.invoke(invoker, new Object[]{"gsm.version.baseband", "no message"});
            // System.out.println(">>>>>>><<<<<<<" +(String)result);
            Version = (String) result;
        } catch (Exception e) {
        }
        return Version;
    }

    /**
     * CORE-VER
     * 内核版本
     * return String
     */
    public static String getLinuxCore_Ver() {
        Process process = null;
        String kernelVersion = "";
        try {
            process = Runtime.getRuntime().exec("cat /proc/version");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        // get the output line
        InputStream outs = process.getInputStream();
        InputStreamReader isrout = new InputStreamReader(outs);
        BufferedReader brout = new BufferedReader(isrout, 8 * 1024);


        String result = "";
        String line;
        // get the whole standard output string
        try {
            while ((line = brout.readLine()) != null) {
                result += line;
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        try {
            if (result != "") {
                String Keyword = "version ";
                int index = result.indexOf(Keyword);
                line = result.substring(index + Keyword.length());
                index = line.indexOf(" ");
                kernelVersion = line.substring(0, index);
            }
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return kernelVersion;
    }

    /**
     * INNER-VER
     * 内部版本
     * return String
     */
    public static String getInner_Ver() {
        String ver = "";

        if (android.os.Build.DISPLAY.contains(android.os.Build.VERSION.INCREMENTAL)) {
            ver = android.os.Build.DISPLAY;
        } else {
            ver = android.os.Build.VERSION.INCREMENTAL;
        }
        return ver;

    }

    /**
     * 获取屏幕亮度
     *
     * @param activity
     * @return
     */
    public static int getScreenBrightness(Activity activity) {
        int value = 0;
        ContentResolver cr = activity.getContentResolver();
        try {
            value = Settings.System.getInt(cr, Settings.System.SCREEN_BRIGHTNESS);
        } catch (Settings.SettingNotFoundException e) {

        }
        Log.e("lz", "value:" + value);
        return value;
    }

    /**
     * 获取信息
     */
    private void getInfo() {
        List<String> list = getThermalInfo();
        if (list.isEmpty()) {
            return;
        }
        int size = list.size();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < size; i++) {
            builder.append(list.get(i));
            builder.append("\n");
            Log.e("lz", "list.get(i):" + list.get(i));
        }
        text.setText(builder);

        text.append("基带版本:" + getBaseband_Ver() + "\n");
        text.append("内核版本:" + getLinuxCore_Ver() + "\n");
        text.append("内部版本:" + getInner_Ver() + "\n");
    }

    /**
     * 获取电量
     */
    private void getBattery() {
        BatteryManager batteryManager = (BatteryManager) getSystemService(BATTERY_SERVICE);
        int battery = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);

    }

    /**
     * 获取电池容量 mAh
     * <p>
     * 源头文件:frameworks/base/core/res\res/xml/power_profile.xml
     * <p>
     * Java 反射文件：frameworks\base\core\java\com\android\internal\os\PowerProfile.java
     */
    public String getBatteryCapacity(Context context) {
        Object mPowerProfile;
        double batteryCapacity = 0;
        final String POWER_PROFILE_CLASS = "com.android.internal.os.PowerProfile";

        try {
            mPowerProfile = Class.forName(POWER_PROFILE_CLASS)
                    .getConstructor(Context.class)
                    .newInstance(context);

            batteryCapacity = (double) Class
                    .forName(POWER_PROFILE_CLASS)
                    .getMethod("getBatteryCapacity")
                    .invoke(mPowerProfile);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return String.valueOf(batteryCapacity + " mAh");
    }


    /**
     * 获取温度信息
     *
     * @return
     */
    public static List<String> getThermalInfo() {
        List<String> result = new ArrayList<>();
        BufferedReader br = null;

        try {
            File dir = new File("/sys/class/thermal/");

            File[] files = dir.listFiles(new FileFilter() {
                @Override
                public boolean accept(File file) {
                    if (Pattern.matches("thermal_zone[0-9]+", file.getName())) {
                        return true;
                    }
                    return false;
                }
            });

            final int SIZE = files.length;
            String line = null;
            String type = null;
            String temp = null;
            for (int i = 0; i < SIZE; i++) {
                br = new BufferedReader(new FileReader("/sys/class/thermal/thermal_zone" + i + "/type"));
                line = br.readLine();
                if (line != null) {
                    type = line;
                }

                br = new BufferedReader(new FileReader("/sys/class/thermal/thermal_zone" + i + "/temp"));
                line = br.readLine();
                if (line != null) {
                    long temperature = Long.parseLong(line);
                    if (temperature < 0) {
                        temp = "Unknow";
                    } else {
                        temp = (float) (temperature / 1000.0) + "°C";
                    }

                }

                result.add(type + " : " + temp);
            }

            br.close();
        } catch (FileNotFoundException e) {
            result.add(e.toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }

    /**
     * 特殊路径检测是否是模拟器
     *
     * @return
     */
    public static boolean checkBlueStacksFiles() {
        for (int i = 0; i < emulatorFiles.length; i++) {
            String file_name = emulatorFiles[i];
            File qemu_file = new File(file_name);
            if (qemu_file.exists()) {
                Log.e("lz", "Result : Find BlueStacks Files!");
                Log.e("lz", "file_name:" + file_name);
                return true;
            }
        }
        Log.e("lz", "Result : Not Find BlueStacks Files!");
        return false;
    }

    static final String[] emulatorFiles = {

            // vbox模拟器文件
            "/data/youwave_id",
            "/dev/vboxguest",
            "/dev/vboxuser",
            "/mnt/prebundledapps/bluestacks.prop.orig",
            "/mnt/prebundledapps/propfiles/ics.bluestacks.prop.note",
            "/mnt/prebundledapps/propfiles/ics.bluestacks.prop.s2",
            "/mnt/prebundledapps/propfiles/ics.bluestacks.prop.s3",
            "/mnt/sdcard/bstfolder/InputMapper/com.bluestacks.appmart.cfg",
            "/mnt/sdcard/buildroid-gapps-ics-20120317-signed.tgz",
            "/mnt/sdcard/windows/InputMapper/com.bluestacks.appmart.cfg",
            "/proc/irq/9/vboxguest",
            "/sys/bus/pci/drivers/vboxguest",
            "/sys/bus/pci/drivers/vboxguest/0000:00:04.0",
            "/sys/bus/pci/drivers/vboxguest/bind",
            "/sys/bus/pci/drivers/vboxguest/module",
            "/sys/bus/pci/drivers/vboxguest/new_id",
            "/sys/bus/pci/drivers/vboxguest/remove_id",
            "/sys/bus/pci/drivers/vboxguest/uevent",
            "/sys/bus/pci/drivers/vboxguest/unbind",
            "/sys/bus/platform/drivers/qemu_pipe",
            "/sys/bus/platform/drivers/qemu_trace",
            "/sys/class/bdi/vboxsf-c",
            "/sys/class/misc/vboxguest",
            "/sys/class/misc/vboxuser",
            "/sys/devices/virtual/bdi/vboxsf-c",
            "/sys/devices/virtual/misc/vboxguest",
            "/sys/devices/virtual/misc/vboxguest/dev",
            "/sys/devices/virtual/misc/vboxguest/power",
            "/sys/devices/virtual/misc/vboxguest/subsystem",
            "/sys/devices/virtual/misc/vboxguest/uevent",
            "/sys/devices/virtual/misc/vboxuser",
            "/sys/devices/virtual/misc/vboxuser/dev",
            "/sys/devices/virtual/misc/vboxuser/power",
            "/sys/devices/virtual/misc/vboxuser/subsystem",
            "/sys/devices/virtual/misc/vboxuser/uevent",
            "/sys/module/vboxguest",
            "/sys/module/vboxguest/coresize",
            "/sys/module/vboxguest/drivers",
            "/sys/module/vboxguest/drivers/pci:vboxguest",
            "/sys/module/vboxguest/holders",
            "/sys/module/vboxguest/holders/vboxsf",
            "/sys/module/vboxguest/initsize",
            "/sys/module/vboxguest/initstate",
            "/sys/module/vboxguest/notes",
            "/sys/module/vboxguest/notes/.note.gnu.build-id",
            "/sys/module/vboxguest/parameters",
            "/sys/module/vboxguest/parameters/log",
            "/sys/module/vboxguest/parameters/log_dest",
            "/sys/module/vboxguest/parameters/log_flags",
            "/sys/module/vboxguest/refcnt",
            "/sys/module/vboxguest/sections",
            "/sys/module/vboxguest/sections/.altinstructions",
            "/sys/module/vboxguest/sections/.altinstr_replacement",
            "/sys/module/vboxguest/sections/.bss",
            "/sys/module/vboxguest/sections/.data",
            "/sys/module/vboxguest/sections/.devinit.data",
            "/sys/module/vboxguest/sections/.exit.text",
            "/sys/module/vboxguest/sections/.fixup",
            "/sys/module/vboxguest/sections/.gnu.linkonce.this_module",
            "/sys/module/vboxguest/sections/.init.text",
            "/sys/module/vboxguest/sections/.note.gnu.build-id",
            "/sys/module/vboxguest/sections/.rodata",
            "/sys/module/vboxguest/sections/.rodata.str1.1",
            "/sys/module/vboxguest/sections/.smp_locks",
            "/sys/module/vboxguest/sections/.strtab",
            "/sys/module/vboxguest/sections/.symtab",
            "/sys/module/vboxguest/sections/.text",
            "/sys/module/vboxguest/sections/__ex_table",
            "/sys/module/vboxguest/sections/__ksymtab",
            "/sys/module/vboxguest/sections/__ksymtab_strings",
            "/sys/module/vboxguest/sections/__param",
            "/sys/module/vboxguest/srcversion",
            "/sys/module/vboxguest/taint",
            "/sys/module/vboxguest/uevent",
            "/sys/module/vboxguest/version",
            "/sys/module/vboxsf",
            "/sys/module/vboxsf/coresize",
            "/sys/module/vboxsf/holders",
            "/sys/module/vboxsf/initsize",
            "/sys/module/vboxsf/initstate",
            "/sys/module/vboxsf/notes",
            "/sys/module/vboxsf/notes/.note.gnu.build-id",
            "/sys/module/vboxsf/refcnt",
            "/sys/module/vboxsf/sections",
            "/sys/module/vboxsf/sections/.bss",
            "/sys/module/vboxsf/sections/.data",
            "/sys/module/vboxsf/sections/.exit.text",
            "/sys/module/vboxsf/sections/.gnu.linkonce.this_module",
            "/sys/module/vboxsf/sections/.init.text",
            "/sys/module/vboxsf/sections/.note.gnu.build-id",
            "/sys/module/vboxsf/sections/.rodata",
            "/sys/module/vboxsf/sections/.rodata.str1.1",
            "/sys/module/vboxsf/sections/.smp_locks",
            "/sys/module/vboxsf/sections/.strtab",
            "/sys/module/vboxsf/sections/.symtab",
            "/sys/module/vboxsf/sections/.text",
            "/sys/module/vboxsf/sections/__bug_table",
            "/sys/module/vboxsf/sections/__param",
            "/sys/module/vboxsf/srcversion",
            "/sys/module/vboxsf/taint",
            "/sys/module/vboxsf/uevent",
            "/sys/module/vboxsf/version",
            "/sys/module/vboxvideo",
            "/sys/module/vboxvideo/coresize",
            "/sys/module/vboxvideo/holders",
            "/sys/module/vboxvideo/initsize",
            "/sys/module/vboxvideo/initstate",
            "/sys/module/vboxvideo/notes",
            "/sys/module/vboxvideo/notes/.note.gnu.build-id",
            "/sys/module/vboxvideo/refcnt",
            "/sys/module/vboxvideo/sections",
            "/sys/module/vboxvideo/sections/.data",
            "/sys/module/vboxvideo/sections/.exit.text",
            "/sys/module/vboxvideo/sections/.gnu.linkonce.this_module",
            "/sys/module/vboxvideo/sections/.init.text",
            "/sys/module/vboxvideo/sections/.note.gnu.build-id",
            "/sys/module/vboxvideo/sections/.rodata.str1.1",
            "/sys/module/vboxvideo/sections/.strtab",
            "/sys/module/vboxvideo/sections/.symtab",
            "/sys/module/vboxvideo/sections/.text",
            "/sys/module/vboxvideo/srcversion",
            "/sys/module/vboxvideo/taint",
            "/sys/module/vboxvideo/uevent",
            "/sys/module/vboxvideo/version",
            "/system/app/bluestacksHome.apk",
            "/system/bin/androVM-prop",
            "/system/bin/androVM-vbox-sf",
            "/system/bin/androVM_setprop",
            "/system/bin/get_androVM_host",
            "/system/bin/mount.vboxsf",
            "/system/etc/init.androVM.sh",
            "/system/etc/init.buildroid.sh",
            "/system/lib/hw/audio.primary.vbox86.so",
            "/system/lib/hw/camera.vbox86.so",
            "/system/lib/hw/gps.vbox86.so",
            "/system/lib/hw/gralloc.vbox86.so",
            "/system/lib/hw/sensors.vbox86.so",
            "/system/lib/modules/3.0.8-android-x86+/extra/vboxguest",
            "/system/lib/modules/3.0.8-android-x86+/extra/vboxguest/vboxguest.ko",
            "/system/lib/modules/3.0.8-android-x86+/extra/vboxsf",
            "/system/lib/modules/3.0.8-android-x86+/extra/vboxsf/vboxsf.ko",
            "/system/lib/vboxguest.ko",
            "/system/lib/vboxsf.ko",
            "/system/lib/vboxvideo.ko",
            "/system/usr/idc/androVM_Virtual_Input.idc",
            "/system/usr/keylayout/androVM_Virtual_Input.kl",

            "/system/xbin/mount.vboxsf",
            "/ueventd.android_x86.rc",
            "/ueventd.vbox86.rc",
//            "/ueventd.goldfish.rc",
            "/fstab.vbox86",
            "/init.vbox86.rc",
//            "/init.goldfish.rc",

            // ========针对原生Android模拟器 内核：goldfish===========
            "/sys/module/goldfish_audio",
            "/sys/module/goldfish_sync",

            // ========针对蓝叠模拟器===========
            "/data/app/com.bluestacks.appmart-1.apk",
            "/data/app/com.bluestacks.BstCommandProcessor-1.apk",
            "/data/app/com.bluestacks.help-1.apk",
            "/data/app/com.bluestacks.home-1.apk",
            "/data/app/com.bluestacks.s2p-1.apk",
            "/data/app/com.bluestacks.searchapp-1.apk",
            "/data/bluestacks.prop",
            "/data/data/com.androVM.vmconfig",
            "/data/data/com.bluestacks.accelerometerui",
            "/data/data/com.bluestacks.appfinder",
            "/data/data/com.bluestacks.appmart",
            "/data/data/com.bluestacks.appsettings",
            "/data/data/com.bluestacks.BstCommandProcessor",
            "/data/data/com.bluestacks.bstfolder",
            "/data/data/com.bluestacks.help",
            "/data/data/com.bluestacks.home",
            "/data/data/com.bluestacks.s2p",
            "/data/data/com.bluestacks.searchapp",
            "/data/data/com.bluestacks.settings",
            "/data/data/com.bluestacks.setup",
            "/data/data/com.bluestacks.spotlight",

            // ========针对逍遥安卓模拟器===========
            "/data/data/com.microvirt.download",
            "/data/data/com.microvirt.guide",
            "/data/data/com.microvirt.installer",
            "/data/data/com.microvirt.launcher",
            "/data/data/com.microvirt.market",
            "/data/data/com.microvirt.memuime",
            "/data/data/com.microvirt.tools",

            // ========针对Mumu模拟器===========
            "/data/data/com.mumu.launcher",
            "/data/data/com.mumu.store",
            "/data/data/com.netease.mumu.cloner"
    };

    //1	加速度传感器	TYPE_ACCELEROMETER
    //2	温度传感器	TYPE_AMBIENT_TEMPERATURE
    //3	陀螺仪传感器	TYPE_GYROSCOPE
    //4	光线传感器	TYPE_LIGHT
    //5	磁场传感器	TYPE_MAGNETIC_FIELD
    //6	压力传感器	TYPE_PRESSURE
    //7	临近传感器	TYPE_PROXIMITY
    //8	湿度传感器	TYPE_RELATIVE_HUMIDITY
    //9	方向传感器	TYPE_ORIENTATION
    //10	重力传感器	TYPE_GRAVITY
    //11	线性加速传感器	TYPE_LINEAR_ACCELERATION
    //12	旋转向量传感器	TYPE_ROTATION_VECTOR
    //1-8是硬件传感器，9是软件传感器，其中方向传感器的数据来自重力和磁场传感器，10-12是硬件或软件传感器。
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
//        StringBuilder builder = new StringBuilder();
        String builder = "";
        switch (sensorEvent.sensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER:
                //获取精度
                float acc = sensorEvent.accuracy;
                //获取光线强度
                float lux = sensorEvent.values[0];
                Log.e("lz", "加速度传感器:acc:" + acc + ";" + "lux：" + lux);
                builder = ("\n" + "加速度传感器:acc:" + acc + ";" + "lux：" + lux);
                sensorManager.unregisterListener(this, sensorEvent.sensor);
                break;
            case Sensor.TYPE_AMBIENT_TEMPERATURE:
                //获取精度
                float acc_TEMPERATURE = sensorEvent.accuracy;
                //获取光线强度
                float lux_TEMPERATURE = sensorEvent.values[0];
                Log.e("lz", "温度传感器:acc:" + acc_TEMPERATURE + ";" + "lux：" + lux_TEMPERATURE);
                builder = ("\n" + "温度传感器:acc:" + acc_TEMPERATURE + ";" + "lux：" + lux_TEMPERATURE);
                sensorManager.unregisterListener(this, sensorEvent.sensor);
                break;
            case Sensor.TYPE_GYROSCOPE:
                //获取精度
                float acc_GYROSCOPE = sensorEvent.accuracy;
                //获取光线强度
                float lux_TGYROSCOPE = sensorEvent.values[0];
                Log.e("lz", "陀螺仪:acc:" + acc_GYROSCOPE + ";" + "lux：" + lux_TGYROSCOPE);
                builder = ("\n" + "陀螺仪:acc:" + acc_GYROSCOPE + ";" + "lux：" + lux_TGYROSCOPE);
                sensorManager.unregisterListener(this, sensorEvent.sensor);
                break;
            case Sensor.TYPE_LIGHT:
                //监视传感器的改变

                //accuracy 传感器的精度 通过event.accuracy获取

                //values 传感器传回的数值  如event.values[0] 获取光线强度lux

                //timestamp 传感器事件发生的时间（以纳秒为单位）
                //获取精度
                float acc_LIGHT = sensorEvent.accuracy;
                //获取光线强度
                float lux_LIGHT = sensorEvent.values[0];
                Log.e("lz", "光照传感器:acc:" + acc_LIGHT + ";" + "lux：" + lux_LIGHT);
                builder = ("\n" + "光照传感器:acc:" + acc_LIGHT + ";" + "lux：" + lux_LIGHT);
                sensorManager.unregisterListener(this, sensorEvent.sensor);
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                //获取精度
                float acc_FIELD = sensorEvent.accuracy;
                //获取光线强度
                float lux_FIELD = sensorEvent.values[0];
                Log.e("lz", "磁场传感器:acc:" + acc_FIELD + ";" + "lux：" + lux_FIELD);
                builder = ("\n" + "磁场传感器:acc:" + acc_FIELD + ";" + "lux：" + lux_FIELD);
                sensorManager.unregisterListener(this, sensorEvent.sensor);
                break;
            case Sensor.TYPE_PRESSURE:
                //获取精度
                float acc_PRESSURE = sensorEvent.accuracy;
                //获取光线强度
                float lux_PRESSURE = sensorEvent.values[0];
                Log.e("lz", "压力传感器:acc:" + acc_PRESSURE + ";" + "lux：" + lux_PRESSURE);
                builder = ("\n" + "压力传感器:acc:" + acc_PRESSURE + ";" + "lux：" + lux_PRESSURE);
                sensorManager.unregisterListener(this, sensorEvent.sensor);
                break;
            case Sensor.TYPE_PROXIMITY:
                //获取精度
                float acc_PROXIMITY = sensorEvent.accuracy;
                //获取光线强度
                float lux_PROXIMITY = sensorEvent.values[0];
                Log.e("lz", "临近传感器:acc:" + acc_PROXIMITY + ";" + "lux：" + lux_PROXIMITY);
                builder = ("\n" + "临近传感器:acc:" + acc_PROXIMITY + ";" + "lux：" + lux_PROXIMITY);
                sensorManager.unregisterListener(this, sensorEvent.sensor);
                break;
            case Sensor.TYPE_RELATIVE_HUMIDITY:
                //获取精度
                float acc_HUMIDITY = sensorEvent.accuracy;
                //获取光线强度
                float lux_HUMIDITY = sensorEvent.values[0];
                Log.e("lz", "湿度传感器:acc:" + acc_HUMIDITY + ";" + "lux：" + lux_HUMIDITY);
                builder = ("\n" + "湿度传感器:acc:" + acc_HUMIDITY + ";" + "lux：" + lux_HUMIDITY);
                sensorManager.unregisterListener(this, sensorEvent.sensor);
                break;
            case Sensor.TYPE_STEP_DETECTOR:
                //获取精度
                float acc_DETECTOR = sensorEvent.accuracy;
                //获取光线强度
                float lux_DETECTOR = sensorEvent.values[0];
                Log.e("lz", "计步传感器:acc:" + acc_DETECTOR + ";" + "lux：" + lux_DETECTOR);
                builder = ("\n" + "计步传感器:acc:" + acc_DETECTOR + ";" + "lux：" + lux_DETECTOR);
                sensorManager.unregisterListener(this, sensorEvent.sensor);
                break;
            case Sensor.TYPE_ORIENTATION:
                //获取精度
                float acc_ORIENTATION = sensorEvent.accuracy;
                //获取光线强度
                float lux_ORIENTATION = sensorEvent.values[0];
                Log.e("lz", "方向传感器:acc:" + acc_ORIENTATION + ";" + "lux：" + lux_ORIENTATION);
                builder = ("\n" + "方向传感器:acc:" + acc_ORIENTATION + ";" + "lux：" + lux_ORIENTATION);
                sensorManager.unregisterListener(this, sensorEvent.sensor);
                break;
        }
        text.append(builder);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mOrientationListener.disable();
    }

    @Override
    public boolean isAlived() {
        return false;
    }

}
