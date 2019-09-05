package com.integration.networktechdemo.appstore;

/**
 * Created by Wongerfeng on 2019/8/30.
 */
public class ApkInfo {

    public String file_name;
    public String package_name;
    public int version_code;
    public String file_path;
    public int file_size;
    public String version_name;

    public ApkInfo() {
        file_name = "";
        package_name = "";
        version_code = 0;
        file_path = "";
        file_size = 0;
        version_name = "";
    }

    public ApkInfo(String file_name, String package_name, int version_code, String file_path, int file_size, String version_name) {
        this.file_name = file_name;
        this.package_name = package_name;
        this.version_code = version_code;
        this.file_path = file_path;
        this.file_size = file_size;
        this.version_name = version_name;
    }


}
