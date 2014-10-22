package me.chanjar.weixin.common.util.fs;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileUtils {

  /**
   * 创建临时文件
   * @param inputStream
   * @param name  文件名
   * @param ext   扩展名
   * @return
   * @throws IOException
   */
  public static File createTmpFile(InputStream inputStream, String name, String ext) throws IOException {
    FileOutputStream fos = null;
    try {
      File tmpFile = File.createTempFile(name, '.' + ext);
      tmpFile.deleteOnExit();
      fos = new FileOutputStream(tmpFile);
      int read = 0;
      byte[] bytes = new byte[1024 * 100];
      while ((read = inputStream.read(bytes)) != -1) {
        fos.write(bytes, 0, read);
      }
      fos.flush();
      return tmpFile;
    } finally {
      if (inputStream != null) {
        try {
          inputStream.close();
        } catch (IOException e) {
        }
      }
      if (fos != null) {
        try {
          fos.close();
        } catch (IOException e) {
        }
      }
    }
  }
  
}
