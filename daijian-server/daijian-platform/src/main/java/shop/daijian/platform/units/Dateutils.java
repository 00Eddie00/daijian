package shop.daijian.platform.units;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * 时间工具类
 * @Author stronghwan
 * @Verison
 * @Date2019/8/5-17-29
 */
public class Dateutils {

    public static Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
