package com.noahhendrickson.api.util;

import java.util.regex.Pattern;

public class Constants {

    public static final Pattern UUID_REGEX = Pattern.compile("^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$");

}
