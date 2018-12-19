package cn.oyp.link.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import cn.oyp.link.R;
import cn.oyp.link.view.PieceImage;

/**
 * 图片资源工具类, 主要用于读取游戏图片资源值<br/>
 * <br/>
 * 关于本代码介绍可以参考一下博客: <a href="http://blog.csdn.net/ouyang_peng">欧阳鹏的CSDN博客</a> <br/>
 */
public class ImageUtil {
    /**
     *  保存所有连连看图片资源值(int类型)
     */
    private static List<Integer> imageValues = getImageValues();

    /**
     *  获取连连看所有图片的ID（约定所有图片ID以p_开头）
     */
    public static List<Integer> getImageValues() {
        try {
            // 得到R.drawable所有的属性, 即获取drawable目录下的所有图片
            Field[] drawableFields = R.drawable.class.getFields();
            List<Integer> resourceValues = new ArrayList<Integer>();
            for (Field field : drawableFields) {
                // 如果该Field的名称以p_开头
                if (field.getName().indexOf("p_") != -1) {
                    resourceValues.add(field.getInt(R.drawable.class));
                }
            }
            return resourceValues;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 随机从sourceValues的集合中获取size个图片ID, 返回结果为图片ID的集合
     *
     * @param sourceValues
     *            从中获取的集合
     * @param size
     *            需要获取的个数
     * @return size个图片ID的集合
     */
    public static List<Integer> getRandomValues(List<Integer> sourceValues,
                                                int size) {
        // 创建一个随机数生成器
        Random random = new Random();
        // 创建结果集合
        List<Integer> result = new ArrayList<Integer>();
        for (int i = 0; i < size; i++) {
            try {
                // 随机获取一个数字，大于、小于sourceValues.size()的数值
                int index = random.nextInt(sourceValues.size());
                // 从图片ID集合中获取该图片对象
                Integer image = sourceValues.get(index);
                // 添加到结果集中
                result.add(image);
            } catch (IndexOutOfBoundsException e) {
                return result;
            }
        }
        return result;
    }

    /**
     * 从drawable目录中中获取size个图片资源ID(以p_为前缀的资源名称), 其中size为游戏数量
     *
     * @param size
     *            需要获取的图片ID的数量
     * @return size个图片ID的集合
     */
    public static List<Integer> getPlayValues(int size) {
        if (size % 2 != 0) {
            // 如果该数除2有余数，将size加1
            size += 1;
        }
        // 再从所有的图片值中随机获取size的一半数量,即N/2张图片
        List<Integer> playImageValues = getRandomValues(imageValues, size / 2);
        // 将playImageValues集合的元素增加一倍（保证所有图片都有与之配对的图片），即N张图片
        playImageValues.addAll(playImageValues);
        // 将所有图片ID随机“洗牌”
        Collections.shuffle(playImageValues);
        return playImageValues;
    }

    /**
     * 将图片ID集合转换PieceImage对象集合，PieceImage封装了图片ID与图片本身
     *
     * @return size个PieceImage对象的集合
     */
    public static List<PieceImage> getPlayImages(Context context, int size) {
        // 获取图片ID组成的集合
        List<Integer> resourceValues = getPlayValues(size);
        List<PieceImage> result = new ArrayList<PieceImage>();
        // 遍历每个图片ID
        for (Integer value : resourceValues) {
            // 加载图片
//            Bitmap bm = BitmapFactory.decodeResource(context.getResources(), value);
            Bitmap bm = drawableToBitmap(context.getResources().getDrawable(value));
            // 封装图片ID与图片本身
            PieceImage pieceImage = new PieceImage(bm, value);
            result.add(pieceImage);
        }
        return result;
    }

    public static Bitmap drawableToBitmap (Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable)drawable).getBitmap();
        }
        Bitmap bitmap = Bitmap.createBitmap(GameConf.PIECE_WIDTH, GameConf.PIECE_HEIGHT, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }


    /**
     *  获取选中标识的图片
     * @param context
     * @return 选中标识的图片
     */
    public static Bitmap getSelectImage(Context context) {
        return BitmapFactory.decodeResource(context.getResources(), R.drawable.selected);
    }
}
