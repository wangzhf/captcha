package com.wangzhf.other;

import com.sun.xml.internal.messaging.saaj.util.Base64;
import org.apache.commons.io.FileUtils;
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Base64ToImage {

    static int i = 9;

    public static void main(String[] args) throws IOException {
        String base64 = "data:image/jpg;base64,R0lGODlhRgAoAPcAAAMzLwMVaDFBEzJCFDRDFzREFjVFGTdGHDdIGjhGHDhIGzpJHTxLHjpJIDxK\n" +
                "Ij1KJD5MIj5NJEcfLVMVT0BOI0BPJUFNKEFRJkNQK0VSK0VRLkVUKkhWLUlYL0ddMEpWM0pYMExa\n" +
                "Mk1aNUxeM05cNE5ZOVBeNlBaPFFeOVNgOlZiPlJoPlReQVdhRVdkQFhhR1lmQVpkR1pjSV1mTVto\n" +
                "Q1xpRVxvRl1pSV9qTGFsTGNrVGRsVWZtWGV3Umd4U2hyVWp0U2p0VmhwWmpxXWt1WGxzX2x2WWx1\n" +
                "XW94XXB6W2x0YHB2ZHJ7YXJ4ZnN+YXV7Z3R5aHl/bww4sTobrCQghyk6tm5whn9svz6QFjrEPk+e\n" +
                "YG6BW3WKQHCDW3WAY3eOaH+GZnqCanmAbnqEaH6HbH2DcW3PGmbtLFjXUQ6Pqj6+1yjkiQDAwhHV\n" +
                "/SzU4nHfyIk0NKAHPLsCcaVKMolDZMURIOQMYcB3JNZXSNZvQJRvjb5X9cco1MhEroKXIIefNpmv\n" +
                "MoGGeIGLcIGIdoSLdISMdoOIeYSIe4aKfoaNeYeMfoiPe4iOfIGRcYiRd4mSeYmQfIiWfY2Uf+/K\n" +
                "XuH9eoiMgIyRhI+WgY+TiI+YgpKVjJKZhZWZjpeeiZeejZicjpeakZmekZuelZeii5aojpmijZ6i\n" +
                "lp+lk6GjnKGlmaKlnqSql6SpmaiunqSmoKapoauvo6utp6qyoq2zoq6zp622oa6xqqu9obG3qLK1\n" +
                "rbO4qrO8rrS5q7a6rba8rrO0sLW3s7e6s7a4tLi6s7i6tbq/s7q9tr2+uYGx7ovfiojSv7/Ht7zA\n" +
                "ub7AvKH2uJnF6Y723cGKtMWcyMDBu8LGvMLKvdDNr/zxq8HCwMPEwcTFwsbGxcbIw8fIxsnLxsjI\n" +
                "yMzNys7Ozc/QzNDQz9LVztTWz9HS0dPU09TV09TV1NXY0djZ1tna2Nre2Nzc293e3N/g3eDi3uHh\n" +
                "4OPk4uTk4+Xl5enr5+rq6uvs6uzs6+3t7fHx8fT19Pn5+f7+/gMzLywAAAAARgAoAEcI/wD9CRzo\n" +
                "L02agWbMCNSihaDDgc4eOnz2TKJFgXToXCSoj5OAj0L8xSulwsAADqWcRbQlABCgK1f83TO0gGEE\n" +
                "GjUqkCDDCtw+fev0bXRIZahAYB8NDPHH71WIAUHuCdynSsAAHsn8HTOwgOsAAx+QEGLl7R0/dlA+\n" +
                "Mvq0zdbAdttuDFhT9GKAAEYJyosT58+fAiqG9Rvqxk2fPkbHUXFgwICrvBaRIYNM2WLdyhIBAPC3\n" +
                "TckMKKLODWXoUJrp0yYLMEjRaV5eKVKIDtWn4eMAS8Z0QXLR+BI+gU0MCBgi1F++SxHYsCHXqseC\n" +
                "ASdL6dPk4KMAA514rZIjBwsWeIP9Xf/e+O2bphrbcIja900fs7gkcvVjJleINWsCww+8dk3iPmEz\n" +
                "JDCEOQNtM4MAVCygSRVVlPdNP/qEg5lE+k1omR2U6aGHhRxeFE00HVYGDTQLNeRQRA/tIw878ugz\n" +
                "2GnShGiRMNbZ9pUIocBo2jexWGIIQw2N8hwM6vBzywBWRcCJheNZdE8Rtj0mUDU2COAMKPv4E86B\n" +
                "A6Ay0Dyw1NCYSQwBgYtU9wQSwUdNpDMUFWjk5Y51AzQg3AAQzFLPQPd89EBW/dDS2CZC2dPIBdBd\n" +
                "UMMrlFDyEQQFmHDDGGDAwIBVEJxSlxVWPPSGQ/2A8tEBigj0ziSTGLENPVNMoYgAR7T/l0wwpuTy\n" +
                "SjMQflNPe/6wN8ccbbRxgwAybCOPP/p8I8YihVCxjT5wwOFPP7xe1E82vxADzDoCZZFFKiKgsIUx\n" +
                "rIygQCJ7crgPO+n600wLVFjlpYwOHUMEBB+UIYEEG+WRB2VN0kuQGmr4U0cdAhMU8IR33AEZHnhc\n" +
                "xAUX/vDBx0Z7LJwwh374sTFmBiGkkD8jkmYRihtRhFlGkN0Tjjn18LORySpJxI4pOsb4WmyyZbaZ\n" +
                "MBbUqEEZ2fDDT4X+mMbQab1GcoEBOVgiyAVWodAL0pBpPFBT1jXwgQEwPqKOQM68U4JLMJUowAJO\n" +
                "yLJMF19xgIQ2TBqlTyDWISKQPqyI/7C2M3vqc8hHUBSHThgbFDAAQwaEsEk5xQmzBCbFvdnyC7Yd\n" +
                "008/7pDigTMg0O2PKrVZ8A2fmaAgSBIgQAAdQ97wg80JSF6XCj775LMP0nDGaTcBNQoAAaIIOFI5\n" +
                "Bh+9MtAmC6CgXD/zfIHkAAsM0mjt19VQQwfcefcIPOL5w2le3dj2QSVNxLBABa0Q5M1HJ2T1TgbC\n" +
                "e+HLISEYMHwQkhQTjiZI2sA2flAIarxjHbXwgVXWQBe7fMohrkhKGQTCDVShYBoCkcfgHGAJfyTj\n" +
                "FTDYhggUwAJQhOMb89jctH4lgBxsIwNPeJBAzGGLFw6gKNHKz1D6YQnhJUAT0+IHJ8KCsA1PfKNV\n" +
                "DsKGg74hjiWe4QzJKs+uvhGsj+gAHA4Cxw4i8A0qvKJaK6qWRKLIBAfVw1uJ+Moj8rGLFRSgCfex\n" +
                "CH8uYo4oLMFNAsGGDOI1AFEwKGHm4ItfKHABU2QpRPppRrysooqP6WMQzzFABxjhjn7loTCHMQoV\n" +
                "hIEIKDThdB/bhzeMQQxw7GtjWqMXwT5GFAxBRkMWOtjHUkmZhg1EMheBmMQoZpSMsXJjFtsIFT70\n" +
                "yxB1rJgJU4YyJjABZEImIAA7\n";
        base64ToImage(base64, "jpg");

        FileUtils.write(new File("D:\\temp\\images\\pingan\\0" + i),
                base64, "UTF-8");


    }

    public static void base64ToImage(String base64, String format){
        if(base64 == null || base64.length() == 0){
            return ;
        }

        String newStr = base64.replace("data:image/jpg;base64,", "");
        System.out.println(newStr);
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            byte[] bytes = decoder.decodeBuffer(newStr);
            for(int i=0;i<bytes.length;++i) {
                if(bytes[i]<0){//调整异常数据
                    bytes[i]+=256;
                }
            }
            File file = new File("D:\\temp\\images\\pingan\\0" + i + ".jpg");
            OutputStream out = new FileOutputStream(file);

            out.write(bytes);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
