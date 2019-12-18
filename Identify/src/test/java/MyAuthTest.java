import com.hx.main.impl.DefaultApiAuthencator;

import java.security.MessageDigest;

public class MyAuthTest {

    public static void main(String[] args) {


        long currentTime = System.currentTimeMillis();
        String token = null;
        String userId = "123";
        //TODO 默认的密码是helloworld , 改成其他或报错
        String password = "helloworld";
        String url = "www.baidu.com?userID="+userId+"&timestamp="+currentTime+"&password="+password;

        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(url.getBytes());
            byte[] digest = md5.digest();
            token = new String(digest);
        }catch (Exception e) {
            token = "123456";
        }

        String checkUrl =  "www.baidu.com?userID="+userId+"&timestamp="+currentTime+"&token="+token;
        //TODO 验证失败抛出异常
        try {
            new DefaultApiAuthencator().auth(checkUrl);
        }catch(RuntimeException e){
            System.out.println("验证失败");
            return;
        }
        System.out.println("验证成功");

    }
}
