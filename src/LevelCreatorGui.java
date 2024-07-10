import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

public class LevelCreatorGui extends JFrame implements ActionListener {
    public static final int GUI_WIDTH = 1200, GUI_HEGHT = 700;
    public static final String GUI_NAME = "mage quest levels creator";

    public static final int LEVEL_MAX_COLUMS = 32, LEVEL_MAX_ROWS = 15;
    public static final int LEVEL_GREED_SCALE = 20;
    public static final int NUMBER_OF_LINES = 5;
    public static final int NUMBER_OF_POWERS = 4;

    //powers codes:
    public static final char
            ID_POWER_NOTHING = '0',
            ID_POWER_Y_CUBE = '1',
            ID_POWER_B_CUBE = '2',
            ID_POWER_TELEPORT = '3',
            ID_POWER_PHASE = '4',
            ID_POWER_GRAPPLE = '5';

    //blocks codes:
    public static final char
            ID_BLOCK_EMPTY = 'E',//bg
            ID_BLOCK_WALL = 'X',//bg
            ID_BLOCK_PLAYER = 'P',//fg
            ID_BLOCK_COIN = 'C',//fg
            ID_BLOCK_BOX = 'M',//fg
            ID_BLOCK_Y_CUBE = 'Y',//fg
            ID_BLOCK_B_CUBE = 'B',//fg
            ID_BLOCK_SPIKE = 'V',//bg
            ID_BLOCK_NON_GRABBABLE_WALL = 'G',//bg
            ID_BLOCK_POWER_BULLET = 'Q',
            ID_BLOCK_SPIKE_BOX = 'S',//fg
            ID_BLOCK_BUTTON = 'O',//fg
            ID_BLOCK_SWITCH_BUTTON = 'T',//fg
            ID_BLOCK_ACTIVATION_WALL = 'A',//fg
            ID_BLOCK_POWER_BOX = 'J';//fg
    public final static int[] BACKGROUND_BLOCKS = {ID_BLOCK_EMPTY, ID_BLOCK_WALL, ID_BLOCK_NON_GRABBABLE_WALL};

    LevelManager manager;
    Container c;
    JPanel pnlLevelGreed;
    JButton[][] btnsGameGreed;
    GameObject[][] objsLevelBackground;
    Vector<GameObject>[][] objsLevelForeground;
    JTextArea textLevelNameInstr, textLevelHintInstr;
    JTextArea textLevelName, textLevelHint;
    JTextArea textLevelInstr, textLevelString;
    JScrollPane ScrollPaneLevelString;
    JTextArea textCoin, textCoinInstr;
    JButton btnCoinPlus, btnCoinLess;
    JTextArea textId, textIdInstr;
    JButton btnIdPlus, btnIdLess;
    JButton writeLevel;
    JButton btnFillAll, btnEmptyAll;
    JButton[][] btnsBlocks;
    GameObject[][] infosBtnsBlocks;
    JTextArea textPowersInstr;
    JTextArea[] powersAmount;
    JButton[] powers, btnsPowersPlus, btnsPowersLess;
    boolean[] usedPowers;
    JButton btnEncode;
    JTextArea moveLevelInstr;
    JButton btnMoveUp, btnMoveDown, btnMoveLeft,btnMoveRight;
    JButton btnRotateCw, btnRotateCounterCw;
    JButton[] btnsPowerLines;
    JTextArea powerLinesInstr;
    JButton[] btnsPowerPowerBox;
    JTextArea powerPowerBoxInstr;
    JButton btnInverte;

    GameObject selectedBlock;

    public LevelCreatorGui(){
        super(GUI_NAME);
        c = getContentPane();
        c.setLayout(null);

        manager = LevelManager.getInstance("default_levels", "custom_levels");
        manager.getLevel(1,true);

        createComponents();


        selectedBlock = infosBtnsBlocks[0][0];

        setSize(GUI_WIDTH, GUI_HEGHT);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
    }

    private void createComponents(){
        pnlLevelGreed = new JPanel();
        pnlLevelGreed.setLayout(null);
        pnlLevelGreed.setBounds(20,30,LEVEL_GREED_SCALE*LEVEL_MAX_COLUMS,LEVEL_GREED_SCALE*LEVEL_MAX_ROWS);
        //System.out.println(pnlLevelGreed.getWidth()+"    "+pnlLevelGreed.getHeight());
        c.add(pnlLevelGreed);

        btnsGameGreed = new JButton[LEVEL_MAX_COLUMS][LEVEL_MAX_ROWS];
        objsLevelBackground = new GameObject[LEVEL_MAX_COLUMS][LEVEL_MAX_ROWS];
        objsLevelForeground = new Vector[LEVEL_MAX_COLUMS][LEVEL_MAX_ROWS];

        for(int x = 0; x < LEVEL_MAX_COLUMS; x++){
            for(int y = 0; y < LEVEL_MAX_ROWS; y++){
                btnsGameGreed[x][y] = new JButton();
                btnsGameGreed[x][y].setFocusable(false);
                btnsGameGreed[x][y].setBounds(LEVEL_GREED_SCALE*x,LEVEL_GREED_SCALE*y,LEVEL_GREED_SCALE,LEVEL_GREED_SCALE);
                btnsGameGreed[x][y].setBackground(new Color(0,0,0,0));
                btnsGameGreed[x][y].setOpaque(false);
                btnsGameGreed[x][y].addActionListener(this);
                pnlLevelGreed.add(btnsGameGreed[x][y]);

                objsLevelBackground[x][y] = new GameObject(ID_BLOCK_WALL);
                objsLevelBackground[x][y].setBounds(LEVEL_GREED_SCALE*x,LEVEL_GREED_SCALE*y,LEVEL_GREED_SCALE,LEVEL_GREED_SCALE);
                objsLevelBackground[x][y].addLabels(pnlLevelGreed);

                objsLevelForeground[x][y] = new Vector<>();
            }
        }

        infosBtnsBlocks = new GameObject[5][6];
        infosBtnsBlocks[0][0] = new GameObject(ID_BLOCK_WALL);
        infosBtnsBlocks[0][1] = new GameObject(ID_BLOCK_EMPTY);
        infosBtnsBlocks[0][2] = new GameObject(ID_BLOCK_PLAYER);
        infosBtnsBlocks[0][3] = new GameObject(ID_BLOCK_COIN);
        infosBtnsBlocks[0][4] = new GameObject(ID_BLOCK_BOX);
        infosBtnsBlocks[0][5] = new GameObject.Spike(ID_BLOCK_SPIKE);
        infosBtnsBlocks[0][5].setRotation_id(0);
        infosBtnsBlocks[1][0] = new GameObject.Spike(ID_BLOCK_SPIKE);
        infosBtnsBlocks[1][0].setRotation_id(1);
        infosBtnsBlocks[1][1] = new GameObject.Spike(ID_BLOCK_SPIKE);
        infosBtnsBlocks[1][1].setRotation_id(2);
        infosBtnsBlocks[1][2] = new GameObject.Spike(ID_BLOCK_SPIKE);
        infosBtnsBlocks[1][2].setRotation_id(3);
        infosBtnsBlocks[1][3] = new GameObject(ID_BLOCK_NON_GRABBABLE_WALL);
        infosBtnsBlocks[1][4] = new GameObject(ID_BLOCK_SPIKE_BOX);
        infosBtnsBlocks[1][5] = new GameObject.ActivationWall(ID_BLOCK_ACTIVATION_WALL);
        infosBtnsBlocks[2][0] = new GameObject.Button(ID_BLOCK_BUTTON);
        infosBtnsBlocks[2][1] = new GameObject.PowerBox(ID_BLOCK_POWER_BOX);
        infosBtnsBlocks[2][2] = new GameObject.SwitchButton(ID_BLOCK_SWITCH_BUTTON);
        btnsBlocks = new JButton[infosBtnsBlocks.length][infosBtnsBlocks[0].length];
        for(int i = 0; i < btnsBlocks.length; i++){
            for(int j = 0; j < btnsBlocks[0].length; j++){
                btnsBlocks[i][j] = new JButton();
                btnsBlocks[i][j].setBounds(670 + (40 + 5)*i, 50 + (40 + 5)*j, 40, 40);
                btnsBlocks[i][j].setContentAreaFilled(false);
                btnsBlocks[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        selectedBlock = null;
                        for(int x = 0; x < infosBtnsBlocks.length && selectedBlock == null; x++){
                            for(int y = 0; y < infosBtnsBlocks[0].length && selectedBlock == null; y++){
                                if(e.getSource().equals(btnsBlocks[x][y])){
                                    selectedBlock = infosBtnsBlocks[x][y];
                                }
                            }
                        }


                        //things for visibility
                        powerPowerBoxInstr.setVisible(selectedBlock.havePower);
                        for(int i = 0; i < btnsPowerPowerBox.length; i++){
                            btnsPowerPowerBox[i].setVisible(selectedBlock.havePower);
                        }
                        powerLinesInstr.setVisible(selectedBlock.haveLineCode);
                        for(int i = 0; i < btnsPowerLines.length; i++){
                            btnsPowerLines[i].setVisible(selectedBlock.haveLineCode);
                        }
                        btnInverte.setVisible(selectedBlock.canInvert);

                    }
                });
                btnsBlocks[i][j].setOpaque(false);

                if(infosBtnsBlocks[i][j] != null){
                    //make images smaller to show button bounds
                    infosBtnsBlocks[i][j].setBounds(670 + (40 + 5)*i+2, 50 + (40 + 5)*j+2, 40-4, 40-4);
                    infosBtnsBlocks[i][j].addLabels(c);
                }

                //updateBocksButtons();

                c.add(btnsBlocks[i][j]);

            }
        }


        textPowersInstr = new JTextArea("powers:");
        textPowersInstr.setBounds(1000, 50, 80, 20);
        textPowersInstr.setEditable(false);
        textPowersInstr.setDisabledTextColor(Color.BLACK);
        c.add(textPowersInstr);

        powers = new JButton[6];
        powersAmount = new JTextArea[6];
        btnsPowersPlus = new JButton[6];
        btnsPowersLess = new JButton[6];
        usedPowers = new boolean[6];
        for(int i = 0; i < powers.length; i++){

            powers[i] = new JButton();
            powers[i].setBounds(textPowersInstr.getX(), textPowersInstr.getY() + textPowersInstr.getHeight() + 3 + (45 + 5)*i, textPowersInstr.getWidth(), 45);
            powers[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int index = -1;
                    for(int i = 0; i < powers.length; i++){
                        if(e.getSource().equals(powers[i]) && index == -1){
                            index = i;
                        }
                    }

                    int nUsedPowers = 0;
                    for(int i = 0; i < powers.length; i++){
                        if(usedPowers[i]){
                            nUsedPowers++;
                        }
                    }

                    if(usedPowers[index]){
                        powersAmount[index].setVisible(false);
                        btnsPowersLess[index].setVisible(false);
                        btnsPowersPlus[index].setVisible(false);
                        usedPowers[index] = ! usedPowers[index];
                    }else if(!usedPowers[index] && nUsedPowers < 5){
                        powersAmount[index].setVisible(true);
                        btnsPowersLess[index].setVisible(true);
                        btnsPowersPlus[index].setVisible(true);
                        powersAmount[index].setText("1");
                        usedPowers[index] = ! usedPowers[index];

                    }
                }
            });
            c.add(powers[i]);

            btnsPowersLess[i]= new JButton("-");
            btnsPowersLess[i].setBounds(powers[i].getX() - powers[i].getHeight() - 3, powers[i].getY(), powers[i].getHeight(),powers[i].getHeight());
            btnsPowersLess[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int index = -1;
                    for(int i = 0; i < btnsPowersLess.length && index == -1; i++){
                        if(e.getSource().equals(btnsPowersLess[i])){
                            index = i;
                        }
                    }

                    if(Integer.parseInt(powersAmount[index].getText()) > -1){
                        powersAmount[index].setText(Integer.parseInt(powersAmount[index].getText())-1+"");
                    }

                }
            });
            btnsPowersLess[i].setFocusable(false);
            c.add(btnsPowersLess[i]);

            btnsPowersPlus[i] = new JButton("+");
            btnsPowersPlus[i].setBounds(powers[i].getX() + powers[i].getWidth() + 3, powers[i].getY(), powers[i].getHeight(),powers[i].getHeight());
            btnsPowersPlus[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int index = -1;
                    for(int i = 0; i < btnsPowersPlus.length && index == -1; i++){
                        if(e.getSource().equals(btnsPowersPlus[i])){
                            index = i;
                        }
                    }

                    powersAmount[index].setText(Integer.parseInt(powersAmount[index].getText())+1+"");
                }
            });
            btnsPowersPlus[i].setFocusable(false);
            c.add(btnsPowersPlus[i]);

            powersAmount[i] = new JTextArea("1");
            powersAmount[i].setEnabled(false);
            powersAmount[i].setDisabledTextColor(Color.BLACK);
            powersAmount[i].setBounds(btnsPowersPlus[i].getX() + btnsPowersPlus[i].getWidth() + 3, powers[i].getY(), powers[i].getHeight(),powers[i].getHeight());
            c.add(powersAmount[i]);

            usedPowers[i] = false;
            powersAmount[i].setVisible(false);
            btnsPowersLess[i].setVisible(false);
            btnsPowersPlus[i].setVisible(false);
        }
        powers[0].setText("none");
        powers[1].setText("y cube");
        powers[2].setText("b cube");
        powers[3].setText("teleprt");
        powers[4].setText("phase");
        powers[5].setText("grapple");
        /*
        ID_POWER_NOTHING = '0',
        ID_POWER_Y_CUBE = '1',
        ID_POWER_B_CUBE = '2',
        ID_POWER_TELEPORT = '3',
        ID_POWER_PHASE = '4',
        ID_POWER_GRAPPLE = '5';*/


        btnFillAll = new JButton("fill");
        btnFillAll.setBounds(20, 420, 70,30);
        btnFillAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeForeground();
                for(int x = 0; x < LEVEL_MAX_COLUMS; x++){
                    for(int y = 0; y < LEVEL_MAX_ROWS; y++){
                        objsLevelBackground[x][y].setId(ID_BLOCK_WALL);
                        objsLevelBackground[x][y].setVisible(true);


                        objsLevelForeground[x][y] = new Vector<>();
                    }
                }
            }
        });
        btnFillAll.setFocusable(false);
        c.add(btnFillAll);

        btnEmptyAll = new JButton("empty");
        btnEmptyAll.setBounds(btnFillAll.getX() + btnFillAll.getWidth() + 10, btnFillAll.getY(), btnFillAll.getWidth(),btnFillAll.getHeight());
        btnEmptyAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeForeground();
                for(int x = 0; x < LEVEL_MAX_COLUMS; x++){
                    for(int y = 0; y < LEVEL_MAX_ROWS; y++){
                        objsLevelBackground[x][y].setId(ID_BLOCK_EMPTY);
                        objsLevelBackground[x][y].setVisible(true);

                        objsLevelForeground[x][y] = new Vector<>();
                    }
                }
            }
        });
        btnEmptyAll.setFocusable(false);
        c.add(btnEmptyAll);


        textCoinInstr = new JTextArea("coins x win:");
        textCoinInstr.setBounds(80, 335, 65,20);
        c.add(textCoinInstr);

        textCoin = new JTextArea("1");
        textCoin.setBounds(textCoinInstr.getX() + (textCoinInstr.getWidth() - 46)/2, textCoinInstr.getY() + textCoinInstr.getHeight() + 3, 46,46);
        c.add(textCoin);

        btnCoinLess= new JButton("-");
        btnCoinLess.setBounds(textCoin.getX() - textCoin.getWidth() - 5, textCoin.getY(), textCoin.getWidth(),textCoin.getHeight());
        btnCoinLess.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Integer.parseInt(textCoin.getText()) > 1)
                    textCoin.setText(Integer.parseInt(textCoin.getText())-1+"");
            }
        });
        btnCoinLess.setFocusable(false);
        c.add(btnCoinLess);

        btnCoinPlus = new JButton("+");
        btnCoinPlus.setBounds(textCoin.getX() + textCoin.getWidth() + 5, textCoin.getY(), textCoin.getWidth(),textCoin.getHeight());
        btnCoinPlus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textCoin.setText(Integer.parseInt(textCoin.getText())+1+"");
            }
        });
        btnCoinPlus.setFocusable(false);
        c.add(btnCoinPlus);

        btnEncode = new JButton("encode level");
        btnEncode.setBounds(300, 350, 120,40);
        btnEncode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textLevelString.setText(getLevelDescr());
            }
        });
        btnEncode.setFocusable(false);
        c.add(btnEncode);




        textLevelNameInstr = new JTextArea("level name:");
        textLevelNameInstr.setBounds(20,460,60,20);
        textLevelNameInstr.setEditable(false);
        textLevelNameInstr.setDisabledTextColor(Color.BLACK);
        c.add(textLevelNameInstr);

        textLevelName = new JTextArea();
        textLevelName.setBounds(textLevelNameInstr.getX() + textLevelNameInstr.getWidth() + 3,textLevelNameInstr.getY(),500,textLevelNameInstr.getHeight());
        c.add(textLevelName);


        textLevelHintInstr = new JTextArea("level hint:");
        textLevelHintInstr.setBounds(textLevelNameInstr.getX(),textLevelNameInstr.getY() + textLevelNameInstr.getHeight() + 10,textLevelNameInstr.getWidth(),textLevelNameInstr.getHeight());
        textLevelHintInstr.setEditable(false);
        textLevelHintInstr.setDisabledTextColor(Color.BLACK);
        c.add(textLevelHintInstr);

        textLevelHint = new JTextArea();
        textLevelHint.setBounds(textLevelHintInstr.getX() + textLevelHintInstr.getWidth() + 3,textLevelHintInstr.getY(),textLevelName.getWidth(),textLevelHintInstr.getHeight());
        c.add(textLevelHint);


        textLevelInstr = new JTextArea("encoded level structure:");
        textLevelInstr.setBounds(textLevelHintInstr.getX(),textLevelHintInstr.getY() + textLevelHintInstr.getHeight() + 10,130,textLevelNameInstr.getHeight());
        textLevelInstr.setEditable(false);
        textLevelInstr.setDisabledTextColor(Color.BLACK);
        c.add(textLevelInstr);

        textLevelString = new JTextArea();
        //textLevelString.setEditable(false);
        textLevelString.setDisabledTextColor(Color.BLACK);

        ScrollPaneLevelString = new JScrollPane(textLevelString);
        ScrollPaneLevelString.setBounds(textLevelInstr.getX(),
                textLevelInstr.getY()+textLevelInstr.getHeight(),GUI_WIDTH - 60,
                100);
        c.add(ScrollPaneLevelString);

        textIdInstr = new JTextArea("level id:");
        textIdInstr.setBounds(680, textLevelNameInstr.getY(), 45,20);
        c.add(textIdInstr);

        textId = new JTextArea("1");
        textId.setBounds(textIdInstr.getX(), textIdInstr.getY() + textIdInstr.getHeight() + 3, textIdInstr.getWidth(),textIdInstr.getWidth());
        c.add(textId);

        btnIdLess = new JButton("-");
        btnIdLess.setBounds(textId.getX() - textId.getWidth() - 5, textId.getY(), textId.getWidth(),textId.getHeight());
        btnIdLess.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Integer.parseInt(textId.getText()) > 1)
                    textId.setText(Integer.parseInt(textId.getText())-1+"");
            }
        });
        btnIdLess.setFocusable(false);
        c.add(btnIdLess);

        btnIdPlus = new JButton("+");
        btnIdPlus.setBounds(textId.getX() + textId.getWidth() + 5, textId.getY(), textId.getWidth(),textId.getHeight());
        btnIdPlus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textId.setText(Integer.parseInt(textId.getText())+1+"");
            }
        });
        btnIdPlus.setFocusable(false);
        c.add(btnIdPlus);


        writeLevel = new JButton("write on file");
        writeLevel.setBounds(800, textLevelNameInstr.getY(), 100, 50);
        writeLevel.setFocusable(false);
        writeLevel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(textLevelName.getText().isEmpty()){
                    JOptionPane.showMessageDialog(new JFrame(), "enter the name of the level first.");
                }else{
                    if(textLevelString.getText().equals("")){
                        textLevelString.setText(getLevelDescr());
                    }
                    File f = manager.writeLevel(Integer.parseInt(textId.getText()),new LevelManager.Level(false,textLevelName.getText(),textLevelHint.getText(),textLevelString.getText(),true));

                    if(f != null){
                        StringSelection stringSelection = new StringSelection(textLevelName.getText()+"\n"+textLevelHint.getText()+"\n"+textLevelString.getText());
                        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                        clipboard.setContents(stringSelection, null);

                        JOptionPane.showMessageDialog(new JFrame(), "level copied on you clipboard and wrote on the file: \""+f.getPath()+"\".");
                    }else{
                        JOptionPane.showMessageDialog(new JFrame(), "generic error :/ .");
                    }
                }
            }
        });
        c.add(writeLevel);


        moveLevelInstr = new JTextArea("move level:");
        moveLevelInstr.setEnabled(false);
        moveLevelInstr.setDisabledTextColor(Color.BLACK);
        moveLevelInstr.setBounds(500, 380,80,20);
        c.add(moveLevelInstr);

        btnMoveUp = new JButton("up");
        btnMoveUp.setFocusable(false);
        btnMoveUp.setBounds(moveLevelInstr.getX() + moveLevelInstr.getWidth() + 10, moveLevelInstr.getY() +moveLevelInstr.getHeight()/2 -(40 +10/2),80,40);
        btnMoveUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(int x = 0; x < objsLevelBackground.length; x++){
                    for(int i = 0; i < objsLevelForeground[x][0].size(); i++){
                        objsLevelForeground[x][0].get(i).removeLabels();
                    }

                    for(int y = 0; y < objsLevelBackground[0].length -1; y++){
                        objsLevelBackground[x][y].setId(objsLevelBackground[x][y+1].id);
                        objsLevelForeground[x][y] = objsLevelForeground[x][y+1];
                        for(int i = 0; i < objsLevelForeground[x][y].size(); i++){
                            objsLevelForeground[x][y].get(i).setBounds(objsLevelBackground[x][y].getBounds());
                        }
                        if(objsLevelForeground[x][y].size() > 0){
                            objsLevelBackground[x][y].setVisible(false);
                        }else {
                            objsLevelBackground[x][y].setVisible(true);
                        }

                    }

                    objsLevelBackground[x][objsLevelBackground[0].length-1].setId(ID_BLOCK_WALL);
                    objsLevelForeground[x][objsLevelBackground[0].length-1] = new Vector<>();
                }
            }
        });
        c.add(btnMoveUp);
        btnMoveDown = new JButton("down");
        btnMoveDown.setFocusable(false);
        btnMoveDown.setBounds(moveLevelInstr.getX() + moveLevelInstr.getWidth() + 10, moveLevelInstr.getY() +moveLevelInstr.getHeight()/2 +(10/2),80,40);
        btnMoveDown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(int x = 0; x < objsLevelBackground.length; x++){
                    for(int i = 0; i < objsLevelForeground[x][objsLevelBackground[0].length-1].size(); i++){
                        objsLevelForeground[x][objsLevelBackground[0].length-1].get(i).removeLabels();
                    }

                    for(int y = objsLevelBackground[0].length-1; y > 0; y--){
                        objsLevelBackground[x][y].setId(objsLevelBackground[x][y-1].id);
                        objsLevelForeground[x][y] = objsLevelForeground[x][y-1];
                        for(int i = 0; i < objsLevelForeground[x][y].size(); i++){
                            objsLevelForeground[x][y].get(i).setBounds(objsLevelBackground[x][y].getBounds());
                        }
                        if(objsLevelForeground[x][y].size() > 0){
                            objsLevelBackground[x][y].setVisible(false);
                        }else {
                            objsLevelBackground[x][y].setVisible(true);
                        }

                    }

                    objsLevelBackground[x][0].setId(ID_BLOCK_WALL);
                    objsLevelForeground[x][0] = new Vector<>();
                }
            }
        });
        c.add(btnMoveDown);

        btnMoveLeft = new JButton("left");
        btnMoveLeft.setFocusable(false);
        btnMoveLeft.setBounds(btnMoveUp.getX() + btnMoveUp.getWidth() + 20, moveLevelInstr.getY() + (moveLevelInstr.getHeight() - 40)/2,80,40);
        btnMoveLeft.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(int y = 0; y < objsLevelBackground[0].length; y++){
                    for(int i = 0; i < objsLevelForeground[0][y].size(); i++){
                        objsLevelForeground[0][y].get(i).removeLabels();
                    }

                    for(int x = 0; x < objsLevelBackground.length -1; x++){
                        objsLevelBackground[x][y].setId(objsLevelBackground[x+1][y].id);
                        objsLevelForeground[x][y] = objsLevelForeground[x+1][y];
                        for(int i = 0; i < objsLevelForeground[x][y].size(); i++){
                            objsLevelForeground[x][y].get(i).setBounds(objsLevelBackground[x][y].getBounds());
                        }
                        if(objsLevelForeground[x][y].size() > 0){
                            objsLevelBackground[x][y].setVisible(false);
                        }else {
                            objsLevelBackground[x][y].setVisible(true);
                        }

                    }

                    objsLevelBackground[objsLevelBackground.length -1][y].setId(ID_BLOCK_WALL);
                    objsLevelForeground[objsLevelBackground.length -1][y] = new Vector<>();
                }
            }
        });
        c.add(btnMoveLeft);
        btnMoveRight = new JButton("right");
        btnMoveRight.setFocusable(false);
        btnMoveRight.setBounds(btnMoveLeft.getX() + btnMoveLeft.getWidth() + 10, moveLevelInstr.getY() + (moveLevelInstr.getHeight() - 40)/2,80,40);
        btnMoveRight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(int y = 0; y < objsLevelBackground[0].length; y++){
                    for(int i = 0; i < objsLevelForeground[objsLevelForeground.length-1][y].size(); i++){
                        objsLevelForeground[objsLevelForeground.length-1][y].get(i).removeLabels();
                    }

                    for(int x = objsLevelForeground.length-1; x > 0; x--){
                        objsLevelBackground[x][y].setId(objsLevelBackground[x-1][y].id);
                        objsLevelForeground[x][y] = objsLevelForeground[x-1][y];
                        for(int i = 0; i < objsLevelForeground[x][y].size(); i++){
                            objsLevelForeground[x][y].get(i).setBounds(objsLevelBackground[x][y].getBounds());
                        }
                        if(objsLevelForeground[x][y].size() > 0){
                            objsLevelBackground[x][y].setVisible(false);
                        }else {
                            objsLevelBackground[x][y].setVisible(true);
                        }

                    }

                    objsLevelBackground[0][y].setId(ID_BLOCK_WALL);
                    objsLevelForeground[0][y] = new Vector<>();
                }
            }
        });
        c.add(btnMoveRight);


        btnRotateCw = new JButton("rot cwise");
        btnRotateCw.setFocusable(false);
        btnRotateCw.setBounds(685, 320, 100,40);
        btnRotateCw.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedBlock.setRotation_id((selectedBlock.getRotation_id()+1)%4);
                //updateBocksButtons();
            }
        });
        c.add(btnRotateCw);

        btnRotateCounterCw = new JButton("rot c cwise");
        btnRotateCounterCw.setFocusable(false);
        btnRotateCounterCw.setBounds(btnRotateCw.getX()+btnRotateCw.getWidth()+5,btnRotateCw.getY(), btnRotateCw.getWidth(),btnRotateCw.getHeight());
        btnRotateCounterCw.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedBlock.setRotation_id((selectedBlock.getRotation_id()+3)%4);
                //updateBocksButtons();

            }
        });
        c.add(btnRotateCounterCw);


        powerLinesInstr = new JTextArea("power line");
        powerLinesInstr.setEnabled(false);
        powerLinesInstr.setDisabledTextColor(Color.BLACK);
        powerLinesInstr.setBounds(905, 380,60,40);
        c.add(powerLinesInstr);

        btnsPowerLines = new JButton[NUMBER_OF_LINES];
        for(int i = 0; i < btnsPowerLines.length; i++){
            btnsPowerLines[i] = new JButton(i+"");
            btnsPowerLines[i].setFocusable(false);
            btnsPowerLines[i].setBounds(powerLinesInstr.getX()+powerLinesInstr.getWidth()+7 +powerLinesInstr.getHeight()*i,powerLinesInstr.getY(), powerLinesInstr.getHeight(),powerLinesInstr.getHeight());
            btnsPowerLines[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(selectedBlock.haveLineCode){
                        selectedBlock.setLineCode(Integer.parseInt(((JButton)e.getSource()).getText()));
                    }

                    //updateBocksButtons();
                }
            });
            c.add(btnsPowerLines[i]);

        }

        powerPowerBoxInstr = new JTextArea("power");
        powerPowerBoxInstr.setEnabled(false);
        powerPowerBoxInstr.setDisabledTextColor(Color.BLACK);
        powerPowerBoxInstr.setBounds(powerLinesInstr.getX(), powerLinesInstr.getY()+powerLinesInstr.getHeight()+10,powerLinesInstr.getWidth(),powerLinesInstr.getHeight());
        c.add(powerPowerBoxInstr);

        btnsPowerPowerBox = new JButton[NUMBER_OF_POWERS];
        for(int i = 0; i < btnsPowerPowerBox.length; i++){
            btnsPowerPowerBox[i] = new JButton((i+1)+"");
            btnsPowerPowerBox[i].setFocusable(false);
            btnsPowerPowerBox[i].setBounds(powerPowerBoxInstr.getX()+ powerPowerBoxInstr.getWidth()+7 + powerPowerBoxInstr.getHeight()*i, powerPowerBoxInstr.getY(), powerPowerBoxInstr.getHeight(), powerPowerBoxInstr.getHeight());
            btnsPowerPowerBox[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(selectedBlock.havePower){
                        selectedBlock.setPower(Integer.parseInt(((JButton)e.getSource()).getText()));
                    }

                    //updateBocksButtons();
                }
            });
            c.add(btnsPowerPowerBox[i]);

        }

        btnInverte = new JButton("inv");
        btnInverte.setFocusable(false);
        btnInverte.setBounds(powerPowerBoxInstr.getX(), powerPowerBoxInstr.getY()+powerPowerBoxInstr.getHeight()+10,powerPowerBoxInstr.getWidth(),powerPowerBoxInstr.getHeight());
        btnInverte.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(selectedBlock.canInvert){
                    selectedBlock.setInverted(!selectedBlock.isInverted());
                }

            }
        });
        c.add(btnInverte);

        //initialize things for visibility
        powerPowerBoxInstr.setVisible(false);
        for(int i = 0; i < btnsPowerPowerBox.length; i++){
            btnsPowerPowerBox[i].setVisible(false);
        }
        powerLinesInstr.setVisible(false);
        for(int i = 0; i < btnsPowerLines.length; i++){
            btnsPowerLines[i].setVisible(false);
        }
        btnInverte.setVisible(false);
    }


    public static String getImgStr(char id){
        switch (id){
            case ID_BLOCK_EMPTY:
                return "images/empty.png";
            case ID_BLOCK_WALL:
                return "images/wall.png";
            case ID_BLOCK_PLAYER:
                return "images/player_0.png";
            case ID_BLOCK_COIN:
                return "images/coin_0.png";
            case ID_BLOCK_BOX:
                return "images/box.png";
            case ID_BLOCK_Y_CUBE:
                return "images/yellow-cube.png";
            case ID_BLOCK_B_CUBE:
                return "images/black_cube.png";
            case ID_BLOCK_SPIKE:
                return "images/spike_down.png";
            case ID_BLOCK_NON_GRABBABLE_WALL:
                return "images/non_grabbable_wall.png";
            case ID_BLOCK_SPIKE_BOX:
                return "images/spike_box.png";
            case ID_BLOCK_ACTIVATION_WALL:
                return "images/activation_wall_0_active.png";
            case ID_BLOCK_BUTTON:
                return "images/button_0.png";
            case ID_BLOCK_SWITCH_BUTTON:
                return "images/switch_button_0.png";
            case ID_BLOCK_POWER_BOX:
                return "images/power_box_0.png";
        }
        return "images/wall.png";
    }//get images triggered by default i think
    public static ImageIcon getImg(char id){
        return new ImageIcon(getImgStr(id));

    }
    public static ImageIcon getImg(char id, int width, int height){
        return new ImageIcon(getImg(id).getImage().getScaledInstance(width, height,Image.SCALE_SMOOTH));

    }
    public static ImageIcon getScaledImg(ImageIcon img, int width, int height){
        return new ImageIcon(img.getImage().getScaledInstance(width, height,Image.SCALE_SMOOTH));

    }
    public static ImageIcon getScaledImg(ImageIcon img){
        return new ImageIcon(img.getImage().getScaledInstance(LEVEL_GREED_SCALE, LEVEL_GREED_SCALE,Image.SCALE_SMOOTH));

    }
    public static ImageIcon getImg(char id, int rotation){

        BufferedImage bi = null;
        try {
            bi = ImageIO.read(new File(getImgStr(id)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        int x = bi.getWidth()/2;
        int y = bi.getHeight()/2;

        Graphics2D g = (Graphics2D)bi.getGraphics();
        if(rotation == 1){
            g.rotate(Math.toRadians(90),x,y);
        }
        if(rotation == 2){
            g.rotate(Math.toRadians(180),x,y);
        }
        if(rotation == 3){
            g.rotate(Math.toRadians(270),x,y);
        }

        g.drawImage(bi, 0, 0, null);


        return new ImageIcon(bi);
    }
    public static ImageIcon getRotatedImg(ImageIcon img, int rotation){
        BufferedImage bi = new BufferedImage(img.getIconWidth(), img.getIconHeight(), BufferedImage.TYPE_INT_ARGB);

        int x = bi.getWidth()/2;
        int y = bi.getHeight()/2;

        Graphics2D g2D = (Graphics2D)bi.getGraphics();
        if(rotation == 3){
            g2D.rotate(Math.toRadians(90),x,y);
        }
        if(rotation == 2){
            g2D.rotate(Math.toRadians(180),x,y);
        }
        if(rotation == 1){
            g2D.rotate(Math.toRadians(270),x,y);
        }

        img.paintIcon(null, g2D, 0,0);

        return new ImageIcon(bi);
    }
    private String getLevelDescr(){
        String result = "";
        //powers
        for (int i = 0 ; i < usedPowers.length; i++){
            if(usedPowers[i]){
                result = result+i+" "+powersAmount[i].getText()+" ";
            }
        }
        if(result.equals("")){
            result = "0 0\n";
        }else {
            result = result.substring(0,result.length()-1)+"\n";
        }


        //strat pos && cons
        int xStart = LEVEL_MAX_COLUMS, yStart = LEVEL_MAX_ROWS;
        int xEnd = 0, yEnd = 0;
        for(int x = 0 ; x < objsLevelBackground.length; x++){
            for(int y = 0 ; y < objsLevelBackground[0].length; y++){
                if(objsLevelBackground[x][y].id != ID_BLOCK_WALL){
                    if(x < xStart){
                        xStart = x;
                    }
                    if(x > xEnd){
                        xEnd = x;
                    }
                    if(y < yStart){
                        yStart = y;
                    }
                    if(y > yEnd){
                        yEnd = y;
                    }
                }
            }
        }
        result = result +xStart+" "+yStart+" "+textCoin.getText()+"\n";

        //level greed
        for(int y = yStart ; y <= yEnd; y++){
            for(int x = xStart; x <= xEnd; x++){
                result = result + objsLevelBackground[x][y].encode();
                if(objsLevelBackground[x][y].id == ID_BLOCK_EMPTY){
                    for(int i = 0; i < objsLevelForeground[x][y].size(); i++){
                        result = result +"_"+ objsLevelForeground[x][y].get(i).encode();
                    }
                }
                result = result + ",";
            }
            result = result + "\n";
        }
        if(result.charAt(result.length()-2)==','){
            result = result.substring(0,result.length()-2);
        }





        return result;
    }
    private void removeForeground(){
        for(int x = 0; x < objsLevelForeground.length; x++){
            for(int y = 0; y < objsLevelForeground[0].length; y++){
                while (!objsLevelForeground[x][y].isEmpty()){
                    objsLevelForeground[x][y].remove(0).removeLabels();

                }
                objsLevelBackground[x][y].setVisible(true);
            }
        }
    }
    public boolean isBackground(char blockId){
        for(int i = 0; i < BACKGROUND_BLOCKS.length; i++){
            if(blockId == BACKGROUND_BLOCKS[i]){
                return true;
            }
        }
        return false;
    }
    public void updateBocksButtons(){
        for(int i = 0; i < btnsBlocks.length; i++){
            for(int j = 0; j < btnsBlocks[0].length; j++){
                if(infosBtnsBlocks[i][j] != null && btnsBlocks[i][j] != null){
                    btnsBlocks[i][j].setIcon(getScaledImg(infosBtnsBlocks[i][j].getImage(),btnsBlocks[i][j].getWidth(),btnsBlocks[i][j].getHeight()));
                }
            }
        }

    }


    @Override
    public void actionPerformed(ActionEvent e) {

        //only for the greed buttons
        if(selectedBlock != null){
            int xGreed = -1, yGreed = -1;
            for(int x = 0; x < LEVEL_MAX_COLUMS && xGreed == -1; x++) {
                for (int y = 0; y < LEVEL_MAX_ROWS && xGreed == -1; y++) {
                    if(e.getSource().equals(btnsGameGreed[x][y])){
                        xGreed = x;
                        yGreed = y;
                    }
                }
            }//find the button x and y

            if(isBackground(selectedBlock.id)){//background
                objsLevelBackground[xGreed][yGreed].setId(selectedBlock.id);
                //remove the foreground
                while (!objsLevelForeground[xGreed][yGreed].isEmpty()){
                    objsLevelForeground[xGreed][yGreed].remove(0).removeLabels();

                }
            }else{
                objsLevelBackground[xGreed][yGreed].setId(ID_BLOCK_EMPTY);

                int index = -1;
                for(int i = 0 ; i < objsLevelForeground[xGreed][yGreed].size(); i++){
                    if(objsLevelForeground[xGreed][yGreed].get(i).id == selectedBlock.id){
                        index = i;
                    }
                }

                if(index == -1){//aggiungo
                    GameObject objTmp = selectedBlock.copy();
                    System.out.println(objTmp.getLineCode());

                    objTmp.setBounds(objsLevelBackground[xGreed][yGreed].getBounds());
                    objsLevelForeground[xGreed][yGreed].add(objTmp);
                    objTmp.addLabels(pnlLevelGreed);
                    //System.out.println("aggiungo");

                }else {//tolgo
                    objsLevelForeground[xGreed][yGreed].remove(index).removeLabels();
                    //System.out.println("tolgo");

                }
            }
            objsLevelBackground[xGreed][yGreed].setVisible(objsLevelForeground[xGreed][yGreed].isEmpty());
            //System.out.println(levelForegroundIds[xGreed][yGreed].isEmpty() +" "+ selectedBlockId +" "+ (int)selectedBlockId);
            //System.out.println("--------------------");

        }
    }
}
