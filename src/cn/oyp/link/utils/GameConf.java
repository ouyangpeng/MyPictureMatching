package cn.oyp.link.utils;

import android.content.Context;

/**
 * 保存游戏配置的对象 <br/>
 * <br/>
 * 关于本代码介绍可以参考一下博客: <a href="http://blog.csdn.net/ouyang_peng">欧阳鹏的CSDN博客</a> <br/>
 */
public class GameConf {
	/**
	 * 连连看的每个方块的图片的宽
	 */
	public static final int PIECE_WIDTH = 40;
	/**
	 * 连连看的每个方块的图片的高s
	 */
	public static final int PIECE_HEIGHT = 40;
	/**
	 * 记录游戏的总事件（100秒）.
	 */
	public static int DEFAULT_TIME = 100;
	/**
	 * Piece[][]数组第一维的长度
	 */
	private int xSize;
	/**
	 * Piece[][]数组第二维的长度
	 */
	private int ySize;
	/**
	 * Board中第一张图片出现的x座标
	 */
	private int beginImageX;
	/**
	 * Board中第一张图片出现的y座标
	 */
	private int beginImageY;
	/**
	 * 记录游戏的总时间, 单位是秒
	 */
	private long gameTime;
	/**
	 * 应用上下文
	 */
	private Context context;

	/**
	 * 提供一个参数构造器
	 * 
	 * @param xSize
	 *            Piece[][]数组第一维长度
	 * @param ySize
	 *            Piece[][]数组第二维长度
	 * @param beginImageX
	 *            Board中第一张图片出现的x座标
	 * @param beginImageY
	 *            Board中第一张图片出现的y座标
	 * @param gameTime
	 *            设置每局的时间, 单位是豪秒
	 * @param context
	 *            应用上下文
	 */
	public GameConf(int xSize, int ySize, int beginImageX, int beginImageY,
			long gameTime, Context context) {
		this.xSize = xSize;
		this.ySize = ySize;
		this.beginImageX = beginImageX;
		this.beginImageY = beginImageY;
		this.gameTime = gameTime;
		this.context = context;
	}

	/**
	 * @return 游戏的总时间
	 */
	public long getGameTime() {
		return gameTime;
	}

	/**
	 * @return Piece[][]数组第一维的长度
	 */
	public int getXSize() {
		return xSize;
	}

	/**
	 * @return Piece[][]数组第二维的长度
	 */
	public int getYSize() {
		return ySize;
	}

	/**
	 * @return Board中第一张图片出现的x座标
	 */
	public int getBeginImageX() {
		return beginImageX;
	}

	/**
	 * @return Board中第一张图片出现的y座标
	 */
	public int getBeginImageY() {
		return beginImageY;
	}

	/**
	 * @return 应用上下文
	 */
	public Context getContext() {
		return context;
	}
}
