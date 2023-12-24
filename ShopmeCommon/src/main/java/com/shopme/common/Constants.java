package com.shopme.common;

public class Constants {
    public static final String S3_BASE_URI;

    static {
//        String bucketName = System.getenv("AWS_BUCKET_NAME");
        String bucketName = "overclocked-files";
        String region = "ap-south-1";
        String pattern = "https://%s.s3.%s.amazonaws.com";

        String uri = String.format(pattern,bucketName,region);
        System.out.println(uri);
        S3_BASE_URI = bucketName==null?"": uri;

    }

    public static void main(String[] args) {
        System.out.println("S3 base uri :"+S3_BASE_URI);
    }
}
