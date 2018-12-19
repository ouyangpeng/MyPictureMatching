package cn.oyp.link.view;

import android.graphics.Point;

/**
 * 连连看游戏中的方块对象 <br/>
 * <br/>
 * 关于本代码介绍可以参考一下博客: <a href="http://blog.csdn.net/ouyang_peng">欧阳鹏的CSDN博客</a> <br/>
 */
public class Piece {
    /**
     * 保存方块对象的所对应的图片
     */
    private PieceImage pieceImage;
    /**
     * 该方块的左上角的x坐标
     */
    private int beginX;
    /**
     * 该方块的左上角的y座标
     */
    private int beginY;
    /**
     * 该对象在Piece[][]数组中第一维的索引值
     */
    private int indexX;
    /**
     * 该对象在Piece[][]数组中第二维的索引值
     */
    private int indexY;

    /**
     * 设置该Piece对象在数组中的索引值
     *
     * @param indexX 该方块的左上角的x坐标
     * @param indexY 该方块的左上角的y座标
     */
    public Piece(int indexX, int indexY) {
        this.indexX = indexX;
        this.indexY = indexY;
    }

    /**
     * 获取该Piece的中心位置
     *
     * @return 中心点的坐标对象Point
     */
    public Point getCenter() {
        return new Point(getBeginX() + getPieceImage().getImage().getWidth()
                / 2, getBeginY() + getPieceImage().getImage().getHeight() / 2);
    }

    /**
     * 判断两个Piece上的图片是否相同
     *
     * @param otherPieceImage 另外的一个Piece对象
     * @return 是否相同
     */
    public boolean isSameImage(Piece otherPieceImage) {
        if (pieceImage == null) {
            if (otherPieceImage.pieceImage != null) {
                return false;
            }
        }
        // 当两个Piece封装图片资源ID相同时，即可认为这两个Piece上的图片相同。
        return pieceImage.getImageId() == otherPieceImage.pieceImage.getImageId();
    }

    /**
     * @return 该方块的左上角的X坐标
     */
    public int getBeginX() {
        return beginX;
    }

    /**
     * 设置该方块的左上角的X坐标
     *
     * @param beginX
     */
    public void setBeginX(int beginX) {
        this.beginX = beginX;
    }

    /**
     * @return 该方块的左上角的Y座标
     */
    public int getBeginY() {
        return beginY;
    }

    /**
     * 设置该方块的左上角的Y坐标
     *
     * @param beginY
     */
    public void setBeginY(int beginY) {
        this.beginY = beginY;
    }

    /**
     * @return 该对象在Piece[][]数组中第一维的索引值
     */
    public int getIndexX() {
        return indexX;
    }

    /**
     * 设置该对象在Piece[][]数组中第一维的索引值
     *
     * @param indexX
     */
    public void setIndexX(int indexX) {
        this.indexX = indexX;
    }

    /**
     * @return 该对象在Piece[][]数组中第二维的索引值
     */
    public int getIndexY() {
        return indexY;
    }

    /**
     * 设置该对象在Piece[][]数组中第二维的索引值
     *
     * @param indexY
     */
    public void setIndexY(int indexY) {
        this.indexY = indexY;
    }

    /**
     * @return 保存方块对象的所对应的图片
     */
    public PieceImage getPieceImage() {
        return pieceImage;
    }

    /**
     * 设置保存方块对象的所对应的图片
     *
     * @param pieceImage
     */
    public void setPieceImage(PieceImage pieceImage) {
        this.pieceImage = pieceImage;
    }
}
