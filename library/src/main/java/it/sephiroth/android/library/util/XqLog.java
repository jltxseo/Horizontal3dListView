package it.sephiroth.android.library.util;

import android.text.TextUtils;
import android.util.Log;

/**
 * Created by Administrator on 2015/9/20.
 */
public class XqLog {

    public static final int VERBOSE = 1;
    public static final int DEBUG = 2;
    public static final int INFO = 3;
    public static final int WARN = 4;
    public static final int ERROR = 5;
    public static final int NOTHING = 6;
    public static final int LEVEL = VERBOSE;
    public static final String SEPARATOR = ",";

    /**
     *
     * @param message
     */
    public static void v(String message){
        if(LEVEL <= VERBOSE){
            StackTraceElement stackTraceElement = getStackTraceElement();
            String tag = getDefaultTag(stackTraceElement);
            Log.v(tag,getLogInfo(stackTraceElement) + message);
        }
    }


    /**
     *
     * @param tag
     * @param message
     */
    public static void v(String tag,String message){
        if(LEVEL <= VERBOSE){
            StackTraceElement stackTraceElement = getStackTraceElement();
            String tagTmp = tag;
            if(TextUtils.isEmpty(tagTmp)){
                tagTmp = getDefaultTag(stackTraceElement);
            }
            Log.v(tagTmp, getLogInfo(stackTraceElement) + message);
        }
    }

    /**
     *
     * @param message
     */
    public static void d(String message){
        if(LEVEL <= DEBUG){
            StackTraceElement stackTraceElement = getStackTraceElement();
            String tag = getDefaultTag(stackTraceElement);
            Log.d(tag, getLogInfo(stackTraceElement) + message);
        }
    }

    /**
     *
     * @param tag
     * @param message
     */
    public static void d(String tag,String message){
        if(LEVEL <= DEBUG){
            StackTraceElement stackTraceElement = getStackTraceElement();
            String tagTmp = tag;
            if(TextUtils.isEmpty(tagTmp)){
                tagTmp = getDefaultTag(stackTraceElement);
            }
            Log.d(tagTmp, getLogInfo(stackTraceElement) + message);
        }
    }

    /**
     *
     * @param message
     */
    public static void i(String message){
        if(LEVEL <= INFO){
            StackTraceElement stackTraceElement = getStackTraceElement();
            String tag = getDefaultTag(stackTraceElement);
            Log.i(tag, getLogInfo(stackTraceElement) + message);
        }
    }

    /**
     *
     * @param tag
     * @param message
     */
    public static void i(String tag,String message){
        if(LEVEL <= INFO){
            StackTraceElement stackTraceElement = getStackTraceElement();
            String tagTmp = tag;
            if(TextUtils.isEmpty(tagTmp)){
                tagTmp = getDefaultTag(stackTraceElement);
            }
            Log.i(tagTmp, getLogInfo(stackTraceElement) + message);
        }
    }

    /**
     *
     * @param message
     */
    public static void w(String message){
        if(LEVEL <= WARN){
            StackTraceElement stackTraceElement = getStackTraceElement();
            String tag = getDefaultTag(stackTraceElement);
            Log.w(tag, getLogInfo(stackTraceElement) + message);
        }
    }

    /**
     *
     * @param tag
     * @param message
     */
    public static void w(String tag,String message){
        if(LEVEL <= WARN){
            StackTraceElement stackTraceElement = getStackTraceElement();
            String tagTmp = tag;
            if(TextUtils.isEmpty(tagTmp)){
                tagTmp = getDefaultTag(stackTraceElement);
            }
            Log.w(tagTmp, getLogInfo(stackTraceElement) + message);
        }
    }

    /**
     *
     * @param message
     */
    public static void e(String message){
        if(LEVEL <= ERROR){
            StackTraceElement stackTraceElement = getStackTraceElement();
            String tag = getDefaultTag(stackTraceElement);
            Log.e(tag, getLogInfo(stackTraceElement) + message);
        }
    }

    /**
     *
     * @param tag
     * @param message
     */
    public static void e(String tag,String message){
        if(LEVEL <= ERROR){
            StackTraceElement stackTraceElement = getStackTraceElement();
            String tagTmp = tag;
            if(TextUtils.isEmpty(tagTmp)){
                tagTmp = getDefaultTag(stackTraceElement);
            }
            Log.e(tagTmp,getLogInfo(stackTraceElement) + message);
        }
    }
    /**
     *
     * @return
     */
    public static StackTraceElement getStackTraceElement(){
        StackTraceElement caller = new Throwable().fillInStackTrace().getStackTrace()[2];
        return caller;
    }

    /**
     * 获取默认的TAG名称.
     * 比如在MainActivity.java中调用了日志输出.
     *  则TAG为MainActivity
     * @param stackTraceElement
     * @return
     */
    public static String getDefaultTag(StackTraceElement stackTraceElement){
        String fileName = stackTraceElement.getFileName();
        String stringArray[] = fileName.split("\\.");
        String tag = stringArray[0];
        return tag;
    }

    /**
     * 输出日志所包含的信息
     * @param stackTraceElement
     * @return
     */
    public static String getLogInfo(StackTraceElement stackTraceElement) {
        StringBuilder logInfoStringBuilder = new StringBuilder();
        // 获取线程名
        String threadName = Thread.currentThread().getName();
        // 获取线程ID
        long threadID = Thread.currentThread().getId();
        // 获取文件名.即xxx.java
        String fileName = stackTraceElement.getFileName();
        // 获取类名.即包名+类名
        String className = stackTraceElement.getClassName();
        // 获取方法名称
        String methodName = stackTraceElement.getMethodName();
        // 获取生日输出行数
        int lineNumber = stackTraceElement.getLineNumber();

        logInfoStringBuilder.append("[ ");
        logInfoStringBuilder.append("threadID=" + threadID).append(SEPARATOR);
        logInfoStringBuilder.append("threadName=" + threadName).append(SEPARATOR);
        logInfoStringBuilder.append("fileName=" + fileName).append(SEPARATOR);
        logInfoStringBuilder.append("className=" + className).append(SEPARATOR);
        logInfoStringBuilder.append("methodName=" + methodName).append(SEPARATOR);
        logInfoStringBuilder.append("lineNumber=" + lineNumber);
        logInfoStringBuilder.append(" ] ");
        return logInfoStringBuilder.toString();
    }



}
