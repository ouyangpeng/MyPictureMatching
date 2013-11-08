package cn.oyp.link.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import cn.oyp.link.R;
import cn.oyp.link.view.PieceImage;

/**
 * ͼƬ��Դ������, ��Ҫ���ڶ�ȡ��ϷͼƬ��Դֵ<br/>
 * <br/>
 * ���ڱ�������ܿ��Բο�һ�²���: <a href="http://blog.csdn.net/ouyang_peng">ŷ������CSDN����</a> <br/>
 */
public class ImageUtil {
	/**
	 *  ��������������ͼƬ��Դֵ(int����)
	 */
	private static List<Integer> imageValues = getImageValues();

	/**
	 *  ��ȡ����������ͼƬ��ID��Լ������ͼƬID��p_��ͷ��
	 */
	public static List<Integer> getImageValues() {
		try {
			// �õ�R.drawable���е�����, ����ȡdrawableĿ¼�µ�����ͼƬ
			Field[] drawableFields = R.drawable.class.getFields();
			List<Integer> resourceValues = new ArrayList<Integer>();
			for (Field field : drawableFields) {
				// �����Field��������p_��ͷ
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
	 * �����sourceValues�ļ����л�ȡsize��ͼƬID, ���ؽ��ΪͼƬID�ļ���
	 * 
	 * @param sourceValues
	 *            ���л�ȡ�ļ���
	 * @param size
	 *            ��Ҫ��ȡ�ĸ���
	 * @return size��ͼƬID�ļ���
	 */
	public static List<Integer> getRandomValues(List<Integer> sourceValues,
			int size) {
		// ����һ�������������
		Random random = new Random();
		// �����������
		List<Integer> result = new ArrayList<Integer>();
		for (int i = 0; i < size; i++) {
			try {
				// �����ȡһ�����֣����ڡ�С��sourceValues.size()����ֵ
				int index = random.nextInt(sourceValues.size());
				// ��ͼƬID�����л�ȡ��ͼƬ����
				Integer image = sourceValues.get(index);
				// ���ӵ��������
				result.add(image);
			} catch (IndexOutOfBoundsException e) {
				return result;
			}
		}
		return result;
	}

	/**
	 * ��drawableĿ¼���л�ȡsize��ͼƬ��ԴID(��p_Ϊǰ׺����Դ����), ����sizeΪ��Ϸ����
	 * 
	 * @param size
	 *            ��Ҫ��ȡ��ͼƬID������
	 * @return size��ͼƬID�ļ���
	 */
	public static List<Integer> getPlayValues(int size) {
		if (size % 2 != 0) {
			// ���������2����������size��1
			size += 1;
		}
		// �ٴ����е�ͼƬֵ�������ȡsize��һ������,��N/2��ͼƬ
		List<Integer> playImageValues = getRandomValues(imageValues, size / 2);
		// ��playImageValues���ϵ�Ԫ������һ������֤����ͼƬ������֮��Ե�ͼƬ������N��ͼƬ
		playImageValues.addAll(playImageValues);
		// ������ͼƬID�����ϴ�ơ�
		Collections.shuffle(playImageValues);
		return playImageValues;
	}

	/**
	 * ��ͼƬID����ת��PieceImage���󼯺ϣ�PieceImage��װ��ͼƬID��ͼƬ����
	 * 
	 * @param context
	 * @param resourceValues
	 * @return size��PieceImage����ļ���
	 */
	public static List<PieceImage> getPlayImages(Context context, int size) {
		// ��ȡͼƬID��ɵļ���
		List<Integer> resourceValues = getPlayValues(size);
		List<PieceImage> result = new ArrayList<PieceImage>();
		// ����ÿ��ͼƬID
		for (Integer value : resourceValues) {
			// ����ͼƬ
			Bitmap bm = BitmapFactory.decodeResource(context.getResources(),
					value);
			// ��װͼƬID��ͼƬ����
			PieceImage pieceImage = new PieceImage(bm, value);
			result.add(pieceImage);
		}
		return result;
	}

	/**
	 *  ��ȡѡ�б�ʶ��ͼƬ
	 * @param context
	 * @return ѡ�б�ʶ��ͼƬ
	 */
	public static Bitmap getSelectImage(Context context) {
		Bitmap bm = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.selected);
		return bm;
	}
}