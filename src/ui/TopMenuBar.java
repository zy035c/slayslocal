package ui;

import ui.icons.HeartIcon;
import ui.window.CustomFrame;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import static ui.GUI.calcX;
import static ui.GUI.calcY;

public class TopMenuBar extends JLayeredPane {

    private static final int TopBarWidth = CustomFrame.FRAME_WIDTH;
    private static final int TopBarHeight = CustomFrame.FRAME_HEIGHT / 18;

    JLayeredPane keyPanel;
    JLabel nameLabel;
    JLabel classLabel;
    JLabel HPLabel;
    JLabel foodPanel; // 一个设定 raw food可以在campfire处烤熟
    JLabel itemPanel;
    JLabel dungeonRoom;
    JLabel mapBtn;
    JLabel masterDeckPile;
    JButton settingBtn;

    /******************************************************************************
     * 顶菜单栏包含的元素：钥匙、名字、血量、金钱、药水栏、房间层数
     * ---居右：地图、卡组、设置
     ******************************************************************************/
    public TopMenuBar() {
        super();

        // 为了让border的左右边界和上下边界被隐藏
        this.setBounds(-5,
                -5,
                TopBarWidth + 10,
                TopBarHeight + 5);
        this.setBackground(GameColors.topMenuBar);
        BevelBorder topBarBorder = new BevelBorder(
                BevelBorder.RAISED,
                GameColors.topBarShadow,
                GameColors.topBarShadow
        );
        this.setBorder(topBarBorder);
        BoxLayout boxLayout = new BoxLayout(this, BoxLayout.LINE_AXIS);
        this.setLayout(boxLayout);
        this.setOpaque(true);
        this.setEnabled(true);
        this.setVisible(true);
        initBar();
        // this.repaint();
    }

    private static final int KEY_WIDTH = calcX(60);
    private static final int NAME_WIDTH = calcX(160);
    private static final int CLASS_WIDTH = calcX(90);
    private static final int HP_WIDTH = calcX(100);
    private static final float y_align = 0.43f; // 奇怪的align问题。
    // 通过这个数字让所有板块文字vertical居中

    public void initBar() {
        // 钥匙？
        keyPanel = new JLayeredPane();
        keyPanel.setBackground(GameColors.topBarSpaceholder);
        keyPanel.setMaximumSize(new Dimension(KEY_WIDTH, TopBarHeight));
        keyPanel.setMinimumSize(new Dimension(KEY_WIDTH, TopBarHeight));
        // keyPanel.setOpaque(true);
        keyPanel.setLayout(null);
        keyPanel.setEnabled(true);
        keyPanel.setVisible(true);
        keyPanel.setForeground(Color.white);
        BevelBorder keyBorder = new BevelBorder(BevelBorder.LOWERED);
        keyPanel.setBorder(keyBorder);
        // keyPanel.setAlignmentY(y_align);
        this.add(Box.createRigidArea(new Dimension(3, 5)));// 从边界内开始
        this.add(keyPanel);
        this.add(Box.createRigidArea(new Dimension(3, 5)));

        // 场上玩家名字
        nameLabel = new JLabel();
        nameLabel.setText("PLAYERNAME");
        nameLabel.setForeground(Color.white);
        nameLabel.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));
        nameLabel.setVisible(true);
        nameLabel.setEnabled(true);
        nameLabel.setMaximumSize(new Dimension(NAME_WIDTH, TopBarHeight - 5));
        nameLabel.setMinimumSize(new Dimension(NAME_WIDTH, TopBarHeight - 5));
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nameLabel.setVerticalAlignment(SwingConstants.CENTER);
        nameLabel.setAlignmentY(y_align);
        // nameLabel.setOpaque(true);
        this.add(nameLabel);
        this.add(Box.createRigidArea(new Dimension(calcX(10), 20)));

        // 出身
        classLabel = new JLabel();
        classLabel.setText("class");
        classLabel.setForeground(Color.white);
        classLabel.setEnabled(true);
        classLabel.setVisible(true);
        classLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        classLabel.setMaximumSize(new Dimension(CLASS_WIDTH, TopBarHeight - 5));
        classLabel.setMinimumSize(new Dimension(CLASS_WIDTH, TopBarHeight - 5));
        classLabel.setHorizontalAlignment(SwingConstants.CENTER);
        classLabel.setVerticalAlignment(SwingConstants.CENTER);
        classLabel.setAlignmentY(y_align);
        // classLabel.setOpaque(true);
        this.add(classLabel);
        this.add(Box.createRigidArea(new Dimension(calcX(10), 20)));

        // 生命值
        HPLabel = new JLabel();
        HPLabel.setText("00/00");
        HPLabel.setForeground(GameColors.topHPBar);
        HPLabel.setIcon(new HeartIcon(calcX(20), calcY(20)));
        HPLabel.setFont(new Font("Monospaced", Font.PLAIN, 15));
        HPLabel.setMaximumSize(new Dimension(HP_WIDTH, TopBarHeight - 5));
        HPLabel.setMinimumSize(new Dimension(HP_WIDTH, TopBarHeight - 5));
        HPLabel.setVisible(true);
        HPLabel.setEnabled(true);
        // HPLabel.setOpaque(true);
        HPLabel.setHorizontalAlignment(SwingConstants.CENTER);
        HPLabel.setVerticalAlignment(SwingConstants.CENTER);
        HPLabel.setAlignmentY(y_align);
        HPLabel.setSize(NAME_WIDTH, TopBarHeight - 3);
        this.add(HPLabel);

        settingBtn = new JButton();
        settingBtn.setAlignmentX(LEFT_ALIGNMENT);
        // this.add(Box.createHorizontalGlue());
        // this.add(settingBtn);

        repaint();
    }


    public void setName(String name) {
        this.nameLabel.setText(name);
        this.nameLabel.repaint();
    }

    // @Test
    public static void main() {

    }
}
