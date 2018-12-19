package cn.oyp.link.view;

import android.graphics.Bitmap;

/**
 * 封装图片ID与图片本身的工具类 <br/>
 * <br/>
 * 关于本代码介绍可以参考一下博客: <a href="http://blog.csdn.net/ouyang_peng">欧阳鹏的CSDN博客</a> <br/>
 */
public class PieceImage {
	/**
	 * 图片
	 */
	private Bitmap image;
	/**
	 * 图片资源ID
	 */
	private int imageId;

	/**
	 * 构造函数
	 *
	 * @param image
	 *            图片
	 * @param imageId
	 *            图片ID
	 */
	public PieceImage(Bitmap image, int imageId) {
		super();
		this.image = image;
		this.imageId = imageId;
	}

	/**
	 * @return 图片
	 */
	public Bitmap getImage() {
		return image;
	}

	/**
	 * 设置图片
	 *
	 * @param image
	 */
	public void setImage(Bitmap image) {
		this.image = image;
	}

	/**
	 * @return 图片ID
	 */
	public int getImageId() {
		return imageId;
	}

	/**
	 * 设置图片ID
	 *
	 * @param imageId
	 */
	public void setImageId(int imageId) {
		this.imageId = imageId;
	}
}
