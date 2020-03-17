package appframe.utils;

import java.io.File;

/**
 * Description : 把日志记录到本地文件中  log4j用的 mmap 方式写日志 似乎更加省时，不会经常io
 * Author :cgy
 * Date :2020/3/13
 */
public class LRecorder {
    File journalDir;//日志记录文件夹
    File journalFile;//日志文件

    public LRecorder() {

    }


}
