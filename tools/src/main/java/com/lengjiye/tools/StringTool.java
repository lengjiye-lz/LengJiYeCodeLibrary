package com.lengjiye.tools;

import android.content.Context;
import android.graphics.Paint;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringTool {

    private StringTool() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static boolean isBlank(String str) {
        // int strLen;
        if (str == null || str.trim().length() == 0) {
            return true;
        }
        return false;
    }

    public static boolean isNotBlank(String s) {
        return !StringTool.isBlank(s);
    }

    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static boolean isNotEmpty(String s) {
        return !isEmpty(s);
    }

    public static boolean isNull(String obj) {
        return obj == null;
    }

    public static boolean isNotNull(String obj) {
        return obj != null;
    }

    public static boolean isNumericString(String str) {
        return str.matches("[0-9]+");
    }

    public static boolean isPhoneNumber(String mobileNo) {
        String regex = "^[1][0-9]{10}$";
        return Pattern.matches(regex, mobileNo);
    }

    public static boolean isTelephoneNumber(String telephoneNo) {
        String regex = "^(([0\\+]\\d{2,3}-?)?(0\\d{2,3})-?)?(\\d{7,8})";

        return Pattern.matches(regex, telephoneNo);
    }

    public static boolean isValidEMail(String eMail) {
        String regex = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
        return Pattern.matches(regex, eMail);
    }

    public static boolean isValidPwd(String pwd) {
        //String regex = "^(?=.*?[a-zA-Z`~!@#$%^&*()_\\-+={}\\[\\]\\\\|:;\\\"'<>,.?/])[a-zA-Z\\d`~!@#$%^&*()_\\-+={}\\[\\]\\\\|:;\\\"'<>,.?/]{6,20}$";
        String regex = "^[a-zA-Z0-9`~!@#$%^&*()_]{6,20}$";
        return Pattern.matches(regex, pwd);
    }

    public static boolean isValidPayPwd(String pwd) {
        String regex = "^[0-9]{6}$";
        return Pattern.matches(regex, pwd);
    }

    public static boolean isStartWithAtoZChar(String str) {
        if (isBlank(str)) {
            return false;
        }
        try {
            String cha = str.substring(0, 1);
            return isCharacter(cha);
        } catch (IndexOutOfBoundsException e) {

        }
        return false;
    }

    public static boolean isCharacter(String str) {
        Pattern p = Pattern.compile("[a-zA-Z]");
        Matcher m = p.matcher(str);
        if (m.find())
            return true;
        else
            return false;
    }

    public static boolean isValidVerifyCode(String code) {
        String regex = "[0-9]{6}";
        return Pattern.matches(regex, code);
    }

    public static boolean isValidStringDate(String strDate) {
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date inputBirthday = fmt.parse(strDate);
            if (new Date().after(inputBirthday)) {
                return true;
            } else {
                return false;

            }
        } catch (ParseException e) {
            return false;
        }
    }

    public static String getBirthday(String cardID) {
        String returnDate = null;
        StringBuffer tempStr = null;
        if (cardID != null && cardID.trim().length() > 0) {
            tempStr = new StringBuffer(cardID.substring(6, 14));
            tempStr.insert(6, '-');
            tempStr.insert(4, '-');
        }
        if (tempStr != null && tempStr.toString().trim().length() > 0) {
            returnDate = tempStr.toString();
        }
        return returnDate;
    }

    public static String getGender(String cardID) {
        String returnGender = null;
        if (cardID != null && cardID.trim().length() > 0) {
            returnGender = cardID.substring(16, 17);
            if (Integer.parseInt(returnGender) % 2 == 0) {
                returnGender = "2";
            } else {
                returnGender = "1";
            }
        }
        return returnGender;
    }

    public static boolean isValidZipCode(String zipCode) {
        String regex = "[0-9]\\d{5}(?!\\d)";
        return Pattern.matches(regex, zipCode);
    }

    public static String getCurrentDate() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        return formatter.format(c.getTime());

    }

    public static String getDateFormat() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
        return formatter.format(c.getTime());
    }

    public String splitIt(String str, int length) {
        int loopCount;
        StringBuffer splitedStrBuf = new StringBuffer();
        loopCount = (str.length() % length == 0) ? (str.length() / length) : (str.length() / length + 1);
        System.out.println("Will Split into " + loopCount);
        for (int i = 1; i <= loopCount; i++) {
            if (i == loopCount) {
                splitedStrBuf.append(str.substring((i - 1) * length, str.length()));
            } else {
                splitedStrBuf.append(str.substring((i - 1) * length, (i * length)));
            }
        }

        return splitedStrBuf.toString();
    }

    /**
     * 手机号中间四位隐藏
     *
     * @param mobile 手机号
     * @return
     */
    public static String mobileFormat(String mobile) {
        if (isEmpty(mobile)) {
            return mobile;
        }
        String startString = mobile.substring(0, 3);
        String endString = mobile.substring(7);
        return startString + "****" + endString;
    }

    /**
     * 校验输入的是否是字母、数字、汉字
     *
     * @param s
     * @return
     */
    public static boolean isValidEditText(String s) {
        String regex = "^[a-zA-Z0-9\\u4e00-\\u9fa5]+$";
        return Pattern.matches(regex, s);
    }

    /**
     * 判断是否是Emoji表情
     *
     * @param codePoint
     * @return
     */
    public static boolean isEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) ||
                (codePoint == 0xD) || ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
                ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) || ((codePoint >= 0x10000)
                && (codePoint <= 0x10FFFF));
    }

    /**
     * 检测是否有emoji表情
     *
     * @param source
     * @return 表情返回true  否则返回false
     */
    public static boolean isEmojiCharacter(String source) {
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (!isEmojiCharacter(codePoint)) { //如果不能匹配,则该字符是Emoji表情
                return true;
            }
        }
        return false;
    }

    /**
     * 限制字符串长度
     *
     * @param source 字符串
     * @param limit  限制的长度
     * @param suffix 截取字符串后添加后缀，如"..."，如果不需要，设为null
     * @return
     */
    public static String limitStringLength(String source, int limit, String suffix) {
        int length = source.length();
        StringBuffer sb = new StringBuffer();
        if (length > limit) {
            sb.append(source.substring(0, limit))
                    .append(suffix);
        }
        return sb.toString();
    }

    /**
     * 生成价格字符串
     */
    public static String formatPriceString(String price) {
        if (isBlank(price)) {
            return price;
        }
        try {
            if (price.startsWith("￥")) {
                price = price.substring(1);
            }
            float priceFloat = Float.valueOf(price);
            DecimalFormat dFormat = new DecimalFormat("0.00");
            price = dFormat.format(priceFloat);
        } catch (NumberFormatException e) {

        }
        return "￥" + price;
    }

    /**
     * 带有下划线的Textview
     */
    public static void formatUnderlinePrice(TextView view, String price) {
        view.setVisibility(View.GONE);
        if (view != null && StringTool.isNotBlank(price)) {
            view.setText(formatPriceString(price));
            view.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            view.setVisibility(View.GONE);
        }
    }


    /**
     * 清除电话号码中的空格
     *
     * @return
     */
    private static String clearBlank(String number) {
        if (StringTool.isNotBlank(number)) {
            return number.replaceAll(" ", "");
        }
        return number;
    }

    /**
     * 电话号码的长度是否符合
     *
     * @param number
     * @return
     */
    private static boolean numberLength(String number) {
        if (StringTool.isNotBlank(number)) {
            int len = clearBlank(number).length();
            return (len == 11 || len == 14);
        }
        return false;
    }

    /**
     * 过滤掉电话号码中个的"+86"
     */
    public static String filterPlus86AndBlank(String number) {
        if (StringTool.isNotBlank(number)) {
            if (number.contains("+86")) {
                return clearBlank(number.replace("+86", ""));
            } else {
                return clearBlank(number);
            }
        }

        return number;
    }

    public static void setTextViewWithPlaceHolder(Context mcontext, TextView textView, int resId, Object[] args) {
        if (resId > 0) {

            String content = mcontext.getResources().getString(resId);
            if (content.isEmpty()) {
                return;
            }
            content = String.format(content, args);
            textView.setText(content);
        }
    }

    public static boolean isIdentifyNumber(String number) {

        return isIdcard(number);
    }


    final static Map<Integer, String> zoneNum = new HashMap<Integer, String>();

    static {
        zoneNum.put(11, "北京");
        zoneNum.put(12, "天津");
        zoneNum.put(13, "河北");
        zoneNum.put(14, "山西");
        zoneNum.put(15, "内蒙古");
        zoneNum.put(21, "辽宁");
        zoneNum.put(22, "吉林");
        zoneNum.put(23, "黑龙江");
        zoneNum.put(31, "上海");
        zoneNum.put(32, "江苏");
        zoneNum.put(33, "浙江");
        zoneNum.put(34, "安徽");
        zoneNum.put(35, "福建");
        zoneNum.put(36, "江西");
        zoneNum.put(37, "山东");
        zoneNum.put(41, "河南");
        zoneNum.put(42, "湖北");
        zoneNum.put(43, "湖南");
        zoneNum.put(44, "广东");
        zoneNum.put(45, "广西");
        zoneNum.put(46, "海南");
        zoneNum.put(50, "重庆");
        zoneNum.put(51, "四川");
        zoneNum.put(52, "贵州");
        zoneNum.put(53, "云南");
        zoneNum.put(54, "西藏");
        zoneNum.put(61, "陕西");
        zoneNum.put(62, "甘肃");
        zoneNum.put(63, "青海");
        zoneNum.put(64, "新疆");
        zoneNum.put(71, "台湾");
        zoneNum.put(81, "香港");
        zoneNum.put(82, "澳门");
        zoneNum.put(91, "外国");
    }

    final static int[] PARITYBIT = {'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};
    final static int[] POWER_LIST = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10,
            5, 8, 4, 2};

    /**
     * 身份证验证
     *
     * @param s 号码内容
     * @return 是否有效 null和"" 都是false
     */
    public static boolean isIdcard(String s) {
        if (s == null || (s.length() != 15 && s.length() != 18))
            return false;
        final char[] cs = s.toUpperCase().toCharArray();
        //校验位数
        int power = 0;
        for (int i = 0; i < cs.length; i++) {
            if (i == cs.length - 1 && cs[i] == 'X')
                break;//最后一位可以 是X或x
            if (cs[i] < '0' || cs[i] > '9')
                return false;
            if (i < cs.length - 1) {
                power += (cs[i] - '0') * POWER_LIST[i];
            }
        }

        //校验区位码
        if (!zoneNum.containsKey(Integer.valueOf(s.substring(0, 2)))) {
            return false;
        }

        //校验年份
        String year = s.length() == 15 ? getIdcardCalendar() + s.substring(6, 8) : s.substring(6, 10);

        final int iyear = Integer.parseInt(year);
        if (iyear < 1900 || iyear > Calendar.getInstance().get(Calendar.YEAR))
            return false;//1900年的PASS，超过今年的PASS

        //校验月份
        String month = s.length() == 15 ? s.substring(8, 10) : s.substring(10, 12);
        final int imonth = Integer.parseInt(month);
        if (imonth < 1 || imonth > 12) {
            return false;
        }

        //校验天数
        String day = s.length() == 15 ? s.substring(10, 12) : s.substring(12, 14);
        final int iday = Integer.parseInt(day);
        if (iday < 1 || iday > 31)
            return false;

        //校验一个合法的年月日:已经得到校验了
        /*if(!validate(iyear, imonth, iday))
            return false;*/

        //校验"校验码"
        if (s.length() == 15)
            return true;
        return cs[cs.length - 1] == PARITYBIT[power % 11];
    }

    private static int getIdcardCalendar() {
        GregorianCalendar curDay = new GregorianCalendar();
        int curYear = curDay.get(Calendar.YEAR);
        int year2bit = Integer.parseInt(String.valueOf(curYear).substring(2));
        return year2bit;
    }

    @Deprecated
    static boolean validate(int year, int imonth, int iday) {
        //比如考虑闰月，大小月等
        return true;
    }

    public static String numberDown2Up(int num) {
        if (num < 0 || num > 9) {
            return num + "";
        }
        String[] upNumbers = new String[]{"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
        return upNumbers[num];
    }

    /**
     * 设置特定子字符串高亮显示
     *
     * @param textView
     * @param orgstr           原字符串
     * @param highLightStrings 更改的字符串和对应颜色
     */
    public static void highlight(TextView textView, String orgstr, HighLightString[] highLightStrings) {

        if (textView == null || StringTool.isBlank(orgstr) || highLightStrings == null) {
            return;
        }
        SpannableStringBuilder spannable = new SpannableStringBuilder(orgstr);//用于可变字符串
        for (int i = 0; i < highLightStrings.length; i++) {
            int start = orgstr.indexOf(highLightStrings[i].str);
            int end = orgstr.indexOf(highLightStrings[i].str) + highLightStrings[i].str.length();
            ForegroundColorSpan span = new ForegroundColorSpan(highLightStrings[i].color);
            spannable.setSpan(span, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        textView.setText(spannable);
    }

    public static class HighLightString {
        public HighLightString(String str, int color) {
            this.color = color;
            this.str = str;
        }

        public String str;
        public int color;
    }
}
