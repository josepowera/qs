package com.qs.core.formatter;

import com.qs.core.model.QSArray;
import com.qs.core.model.QSObject;
import com.qs.core.model.ColorString;

import java.util.Map;

public class QSFormatter {

    private static int mLevel = 0;
    private static final String UNIT_TAB_SPACE = "\t";
    private static final String UNIT_SPACE = " ";

    public static String format(QSObject input) {
        StringBuilder builder = new StringBuilder(input.size() * 4);
        formatObject("", true, input, builder);
        return builder.toString();
    }

    private static <K, V> void formatObject(String inputKey, boolean last, QSObject input, StringBuilder builder) {
        tabIndent(builder);
        builder.append(colorKey(inputKey));
        if (!"".equals(inputKey)) {
            builder.append(":");
        }
        builder.append("{\n");
        int i = 0;
        int length = input.size();
        for (Map.Entry entity : input.entrySet()) {
            String key = entity.getKey().toString();
            Object value = entity.getValue();
            if (value instanceof QSObject) {
                mLevel++;
                formatObject(key, i == length - 1, (QSObject) value, builder);
                mLevel--;
            } else if (value instanceof QSArray) {
                mLevel++;
                formatArray(key, i == length - 1, (QSArray) value, builder);
                mLevel--;
            } else {
                mLevel++;
                tabIndent(builder);
                builder.append(colorKey(key));
                builder.append(": ");
                builder.append(colorValue(String.valueOf(value)));
                if (i < length - 1) {
                    builder.append(",");
                    builder.append("\n");
                }
                mLevel--;
            }
            i++;
        }
        builder.append("\n");
        tabIndent(builder);
        builder.append("}");
        if (!last) {
            builder.append(",");
            builder.append("\n");
        }
    }

    private static <T> void formatArray(String inputKey, boolean last, QSArray input, StringBuilder builder) {
        tabIndent(builder);
        builder.append(colorKey(inputKey));
        if (!"".equals(inputKey)) {
            builder.append(":");
        }
        builder.append("[\n");
        int i = 0;
        int length = input.size();
        for (Object value : input) {
            if (value instanceof QSObject) {
                mLevel++;
                formatObject("", i == length - 1, (QSObject) value, builder);
                mLevel--;
            } else if (value instanceof QSArray) {
                mLevel++;
                formatArray("", i == length - 1, (QSArray) value, builder);
                mLevel--;
            } else {
                mLevel++;
                tabIndent(builder);
                builder.append(colorValue(String.valueOf(value)));
                if (i < length - 1) {
                    builder.append(",");
                    builder.append("\n");
                }
                mLevel--;
            }
            i++;
        }
        builder.append("\n");
        tabIndent(builder);
        builder.append("]");
        if (!last) {
            builder.append(",");
            builder.append("\n");
        }
    }

    private static void tabIndent(StringBuilder builder) {
        for (int i = 0; i < mLevel; i++) {
            builder.append(UNIT_TAB_SPACE);
        }
    }

    private static void spaceIndent(StringBuilder builder) {
        builder.append(UNIT_SPACE);
    }

    private static String colorKey(String key) {
        return key;
        // return ColorString.GREEN.getCode() + key + ColorString.BLACK.getCode();
    }

    private static String colorValue(String value) {
        return ColorString.BLUE.getCode() + value + ColorString.BLACK.getCode();
    }
}
