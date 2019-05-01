import cn.inc.common.utils.MD5Util;
import org.junit.Test;

public class MD5Test {
    @Test
    public void test01() {
        String str = "45jkhjkjkhjhkjhjkhkjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj";

        System.out.println(MD5Util.getMD5String(str));
        System.out.println(MD5Util.encode2hex(str));
    }
}
