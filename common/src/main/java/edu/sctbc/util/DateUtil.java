package edu.sctbc.util;



import com.alibaba.druid.util.StringUtils;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjuster;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * 时间工具类
 *
 * @author LNX
 */
public abstract class DateUtil {

    public static final String yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";

    public static final String yyyy_MM_dd = "yyyy-MM-dd";

    //JDK1.8新API 线程安全
    public static final DateTimeFormatter yyyy_MM_dd_HH_mm_ss_FORMATTER =
            DateTimeFormatter.ofPattern(DateUtil.yyyy_MM_dd_HH_mm_ss);

    public static final DateTimeFormatter yyyy_MM_dd_FORMATTER =
            DateTimeFormatter.ofPattern(DateUtil.yyyy_MM_dd);

    /**
     * 处理下行报文使用
     */
    public static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyMMddHHmmss");

    /**
     * 两时间相距多少时间
     *
     *
     * @param start 开始时间
     * @param end   结束时间
     * @param time  TimeUnit枚举
     * @return 两个时间间隔
     */
    @Deprecated //这样计算时间有点Low 建议 ChronoUnit 更好 by:Arjen10
    public static Long getBetween(LocalDateTime start, LocalDateTime end, TimeUnit time) {
        Duration duration = Duration.between(start, end);
        switch (time) {
            case DAYS:
                return duration.toDays();
            case HOURS:
                return duration.toHours();
            case MINUTES:
                return duration.toMinutes();
            case NANOSECONDS:
                return duration.toNanos();
            case SECONDS:
                return duration.getSeconds();
            case MICROSECONDS:
                return duration.getSeconds() * 1000000;
            case MILLISECONDS:
                return duration.getSeconds() * 1000;
            default:
                throw new IllegalArgumentException("time枚举类型不对！");
        }
    }

    /**
     * 传入得时间跟当前时间得时间间隔
     *
     * @param end   结束时间
     * @param time  TimeUnit枚举
     * @return 两个时间间隔
     */
    public static Long getBetweenByNow(LocalDateTime end, TimeUnit time) {
        return getBetween(LocalDateTime.now(), end ,time);
    }

    /**
     * 获取本周的第几天
     *
     * @param dayOfWeek 星期1到7枚举
     */
    public static LocalDate getDayOfWeek(DayOfWeek dayOfWeek) {
        return LocalDate.now().with(dayOfWeek);
    }

    /**
     * 获取本月、今年的最后一天、第一天
     *
     * @param temporalAdjuster TemporalAdjusters.firstDayOfMonth()
     */
    public static LocalDate getLocalDate(TemporalAdjuster temporalAdjuster) {
        return LocalDate.now().with(temporalAdjuster);
    }


    /**
     * 获取本周的所有日期  即周一到周日
     *
     * @param dayOfWeeks 不想要的星期
     */
    public static List<LocalDate> allDaysOfWeek(DayOfWeek... dayOfWeeks) {
        LocalDate now = LocalDate.now();
        return Stream.of(DayOfWeek.values())
                .filter(dayOfWeek -> Stream.of(dayOfWeeks)
                        .noneMatch(week -> week.equals(dayOfWeek))
                )
                .map(now::with)
                .collect(Collectors.toList());
    }

    /**
     * 从今天开始获取往后的第几天
     *
     * @param day 第几天
     */
    public static List<LocalDate> getLocalDateByDay(Integer day) {
        LocalDate now = LocalDate.now();
        return IntStream.range(0, day)
                .mapToObj(now::plusDays)
                .collect(Collectors.toList());
    }

    /**
     * 格式化当前时间，返回时间字符串
     *
     * @param formatterString 如果不传，默认返回yyyy-MM-dd HH:mm:ss。且只取第一个
     * @return
     */
    public static String getTimeNow(String... formatterString) {
        DateTimeFormatter formatter = Stream.of(formatterString)
                .filter(m->!StringUtils.isEmpty(m))
                .map(DateTimeFormatter::ofPattern)
                .findFirst()
                .orElse(DateUtil.yyyy_MM_dd_HH_mm_ss_FORMATTER);
        return LocalDateTime.now().format(formatter);
    }

    public static LocalDateTime parseTime(String time, String formatterString) {
        return LocalDateTime.parse(time, DateTimeFormatter.ofPattern(formatterString));
    }

}
