package cn.oyp.link;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import cn.oyp.link.board.GameService;
import cn.oyp.link.board.impl.GameServiceImpl;
import cn.oyp.link.utils.GameConf;
import cn.oyp.link.utils.LinkInfo;
import cn.oyp.link.view.GameView;
import cn.oyp.link.view.Piece;

/**
 * 游戏Activity <br/>
 * <br/>
 * 关于本代码介绍可以参考一下博客: <a href="http://blog.csdn.net/ouyang_peng">欧阳鹏的CSDN博客</a> <br/>
 */
public class LinkActivity extends Activity implements BaseHandlerCallBack {
    /**
     * 游戏配置对象
     */
    private GameConf config;
    /**
     * 游戏业务逻辑接口
     */
    private GameService gameService;
    /**
     * 游戏界面
     */
    private GameView gameView;
    /**
     * 开始按钮
     */
    private Button startButton;
    /**
     * 记录剩余时间的TextView
     */
    private TextView timeTextView;
    /**
     * 失败后弹出的对话框
     */
    private AlertDialog.Builder lostDialog;
    /**
     * 游戏胜利后的对话框
     */
    private AlertDialog.Builder successDialog;
    /**
     * 定时器
     */
    private Timer timer = new Timer();
    /**
     * 记录游戏的剩余时间
     */
    private int gameTime;
    /**
     * 记录是否处于游戏状态
     */
    private boolean isPlaying;
    /**
     * 振动处理类
     */
    private Vibrator vibrator;
    /**
     * 记录已经选中的方块
     */
    private Piece selectedPiece = null;
    /**
     * Handler类，异步处理
     */
    private NoLeakHandler handler;
    /**
     * Handler发送消息的ID
     */
    private final int MESSAGE_ID = 0x123;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        handler = new NoLeakHandler(this);
        // 初始化界面
        init();
    }

    /**
     * 初始化游戏的方法
     */
    private void init() {
        config = new GameConf(8, 9, 2, 10, GameConf.DEFAULT_TIME, this);
        // 得到游戏区域对象
        gameView = (GameView) findViewById(R.id.gameView);
        // 获取显示剩余时间的文本框
        timeTextView = (TextView) findViewById(R.id.timeText);
        // 获取开始按钮
        startButton = (Button) this.findViewById(R.id.startButton);
        // 获取振动器
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        // 初始化游戏业务逻辑接口
        gameService = new GameServiceImpl(this.config);
        // 设置游戏逻辑的实现类
        gameView.setGameService(gameService);
        // 为开始按钮的单击事件绑定事件监听器
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View source) {
                startGame(GameConf.DEFAULT_TIME);
            }
        });
        // 为游戏区域的触碰事件绑定监听器
        this.gameView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent e) {
                if (!isPlaying) {
                    return false;
                }
                if (e.getAction() == MotionEvent.ACTION_DOWN) {
                    gameViewTouchDown(e);
                }
                if (e.getAction() == MotionEvent.ACTION_UP) {
                    gameViewTouchUp(e);
                }
                return true;
            }
        });
        // 初始化游戏失败的对话框
        lostDialog = createDialog(getString(R.string.lost), getString(R.string.lost_restart), R.drawable.lost)
                .setPositiveButton(R.string.dialog_sure, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startGame(GameConf.DEFAULT_TIME);
                    }
                });
        // 初始化游戏胜利的对话框
        successDialog = createDialog(getString(R.string.success), getString(R.string.success_restart),
                R.drawable.success).setPositiveButton(R.string.dialog_sure,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startGame(GameConf.DEFAULT_TIME);
                    }
                });
    }

    @Override
    protected void onPause() {
        // 暂停游戏
        stopTimer();
        super.onPause();
    }

    @Override
    protected void onResume() {
        // 如果处于游戏状态中
        if (isPlaying) {
            // 以剩余时间重新开始游戏
            startGame(gameTime);
        }
        super.onResume();
    }

    /**
     * 触碰游戏区域的处理方法
     *
     * @param event
     */
    private void gameViewTouchDown(MotionEvent event) {
        // 获取GameServiceImpl中的Piece[][]数组
        Piece[][] pieces = gameService.getPieces();
        // 获取用户点击的x座标
        float touchX = event.getX();
        // 获取用户点击的y座标
        float touchY = event.getY();
        // 根据用户触碰的座标得到对应的Piece对象
        Piece currentPiece = gameService.findPiece(touchX, touchY);
        // 如果没有选中任何Piece对象(即鼠标点击的地方没有图片), 不再往下执行
        if (currentPiece == null) {
            return;
        }
        // 将gameView中的选中方块设为当前方块
        this.gameView.setSelectedPiece(currentPiece);
        // 表示之前没有选中任何一个Piece
        if (this.selectedPiece == null) {
            // 将当前方块设为已选中的方块, 重新将GamePanel绘制, 并不再往下执行
            this.selectedPiece = currentPiece;
            this.gameView.postInvalidate();
            return;
        }
        // 表示之前已经选择了一个
        if (this.selectedPiece != null) {
            // 在这里就要对currentPiece和prePiece进行判断并进行连接
            LinkInfo linkInfo = this.gameService.link(this.selectedPiece, currentPiece);
            // 两个Piece不可连, linkInfo为null
            if (linkInfo == null) {
                // 如果连接不成功, 将当前方块设为选中方块
                this.selectedPiece = currentPiece;
                this.gameView.postInvalidate();
            } else {
                // 处理成功连接
                handleSuccessLink(linkInfo, this.selectedPiece, currentPiece, pieces);
            }
        }
    }

    /**
     * 触碰游戏区域的处理方法
     *
     * @param e
     */
    private void gameViewTouchUp(MotionEvent e) {
        this.gameView.postInvalidate();
    }

    /**
     * 以gameTime作为剩余时间开始或恢复游戏
     *
     * @param gameTime 剩余时间
     */
    private void startGame(int gameTime) {
        // 如果之前的timer还未取消，取消timer
        if (this.timer != null) {
            stopTimer();
        }
        // 重新设置游戏时间
        this.gameTime = gameTime;
        // 如果游戏剩余时间与总游戏时间相等，即为重新开始新游戏
        if (gameTime == GameConf.DEFAULT_TIME) {
            // 开始新的游戏游戏
            gameView.startGame();
        }
        isPlaying = true;
        this.timer = new Timer();
        // 启动计时器 ， 每隔1秒发送一次消息
        this.timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(MESSAGE_ID);
            }
        }, 0, 1000);
        // 将选中方块设为null。
        this.selectedPiece = null;
    }

    /**
     * 成功连接后处理
     *
     * @param linkInfo     连接信息
     * @param prePiece     前一个选中方块
     * @param currentPiece 当前选择方块
     * @param pieces       系统中还剩的全部方块
     */
    private void handleSuccessLink(LinkInfo linkInfo, Piece prePiece,
                                   Piece currentPiece, Piece[][] pieces) {
        // 它们可以相连, 让GamePanel处理LinkInfo
        this.gameView.setLinkInfo(linkInfo);
        // 将gameView中的选中方块设为null
        this.gameView.setSelectedPiece(null);
        this.gameView.postInvalidate();
        // 将两个Piece对象从数组中删除
        pieces[prePiece.getIndexX()][prePiece.getIndexY()] = null;
        pieces[currentPiece.getIndexX()][currentPiece.getIndexY()] = null;
        // 将选中的方块设置null。
        this.selectedPiece = null;
        // 手机振动(100毫秒)
        this.vibrator.vibrate(100);
        // 判断是否还有剩下的方块, 如果没有, 游戏胜利
        if (!this.gameService.hasPieces()) {
            // 游戏胜利
            this.successDialog.show();
            // 停止定时器
            stopTimer();
            // 更改游戏状态
            isPlaying = false;
        }
    }

    /**
     * 创建对话框的工具方法
     *
     * @param title         标题
     * @param message       内容
     * @param imageResource 图片
     * @return
     */
    private AlertDialog.Builder createDialog(String title, String message,
                                             int imageResource) {
        return new AlertDialog.Builder(this).setTitle(title)
                .setMessage(message).setIcon(imageResource);
    }

    /**
     * 停止计时
     */
    private void stopTimer() {
        // 停止定时器
        this.timer.cancel();
        this.timer = null;
    }

    @Override
    public void callBack(Message msg) {
        switch (msg.what) {
            case MESSAGE_ID:
                timeTextView.setText(String.format(getString(R.string.remaining_time), gameTime));
                gameTime--; // 游戏剩余时间减少
                // 时间小于0, 游戏失败
                if (gameTime < 0) {
                    // 停止计时
                    stopTimer();
                    // 更改游戏的状态
                    isPlaying = false;
                    // 失败后弹出对话框
                    lostDialog.show();
                    return;
                }
                break;
            default:
                break;
        }
    }

    private static class NoLeakHandler<T extends BaseHandlerCallBack> extends Handler {
        private WeakReference<T> wr;

        private NoLeakHandler(T t) {
            wr = new WeakReference<>(t);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            T t = wr.get();
            if (t != null) {
                t.callBack(msg);
            }
        }
    }
}