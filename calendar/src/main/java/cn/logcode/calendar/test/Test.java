package cn.logcode.calendar.test;
import cn.logcode.calendar.Utils;

/**
 * Create by caostgrace on 2019-08-12 14:34
 *
 * @author caostgrace
 * @project_name: CalendarLibrary
 * @package: cn.logcode.calendar
 * @class_name: Test
 * @github: https://github.com/CaostGrace
 * @remark:
 */
public class Test {


    public static void main(String[] args){
        Utils utils = new Utils();

        String date = Utils.Companion.dateToString();
        System.out.println(date);
    }

}
