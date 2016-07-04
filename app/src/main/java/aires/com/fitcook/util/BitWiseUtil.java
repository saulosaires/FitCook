package aires.com.fitcook.util;

import java.util.List;

import aires.com.fitcook.FitCookApp;
import aires.com.fitcook.entity.Category;

/**
 * Created by Aires on 17/08/2015.
 */
public class BitWiseUtil {


    public static  int set(int position,int value){

        value |= (1<<position);

        return value;
    }

    public static int clear(int position, int value){

        value &= ~ (1<<position);

        return value;
    }

}
