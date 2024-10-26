import javax.swing.*;
import java.awt.*;

public class GameObject {
    public static class Button extends GameObject {

        Button(char id) {
            super(id);
            this.allowRotation = true;
            this.haveLineCode = true;
            this.canInvert = true;
            setLineCode(0);
            setRotation_id(0);
        }
        public int getLineCode() {
            return lineCode;
        }
        public ImageIcon getBGImage(){
            ImageIcon img = null;
            if(!inverted){
                switch (lineCode){
                    case 0:
                        img = new ImageIcon("images/button_0.png");
                        break;
                    case 1:
                        img = new ImageIcon("images/button_1.png");
                        break;
                    case 2:
                        img = new ImageIcon("images/button_2.png");
                        break;
                    case 3:
                        img = new ImageIcon("images/button_3.png");
                        break;
                    case 4:
                        img = new ImageIcon("images/button_4.png");
                        break;
                }
            }else{
                switch (lineCode){
                    case 0:
                        img = new ImageIcon("images/button_inv_0.png");
                        break;
                    case 1:
                        img = new ImageIcon("images/button_inv_1.png");
                        break;
                    case 2:
                        img = new ImageIcon("images/button_inv_2.png");
                        break;
                    case 3:
                        img = new ImageIcon("images/button_inv_3.png");
                        break;
                    case 4:
                        img = new ImageIcon("images/button_inv_4.png");
                        break;
                }
            }

            return LevelCreatorGui.getScaledImg(LevelCreatorGui.getRotatedImg(img, rotation_id),width,height);
        }

        @Override
        public String encode() {
            return super.encode()+ rotation_id +""+lineCode+""+(inverted?'1':'0');
        }
        @Override
        public GameObject copy(){
            GameObject copy = new Button(this.id);
            copy.setRotation_id(rotation_id);
            copy.setPower(this.power);
            copy.setLineCode(this.lineCode);
            copy.setInverted(this.inverted);
            return copy;
        }
    }
    public static class SwitchButton extends GameObject {

        SwitchButton(char id) {
            super(id);
            this.allowRotation = true;
            this.haveLineCode = true;
            this.canInvert = true;
            setLineCode(0);
            setRotation_id(0);
        }
        public int getLineCode() {
            return lineCode;
        }
        public ImageIcon getBGImage(){
            ImageIcon img = null;
            if(!inverted){
                switch (lineCode){
                    case 0:
                        img = new ImageIcon("images/switch_button_0.png");
                        break;
                    case 1:
                        img = new ImageIcon("images/switch_button_1.png");
                        break;
                    case 2:
                        img = new ImageIcon("images/switch_button_2.png");
                        break;
                    case 3:
                        img = new ImageIcon("images/switch_button_3.png");
                        break;
                    case 4:
                        img = new ImageIcon("images/switch_button_4.png");
                        break;
                }
            }else{
                switch (lineCode){
                    case 0:
                        img = new ImageIcon("images/switch_button_inv_0.png");
                        break;
                    case 1:
                        img = new ImageIcon("images/switch_button_inv_1.png");
                        break;
                    case 2:
                        img = new ImageIcon("images/switch_button_inv_2.png");
                        break;
                    case 3:
                        img = new ImageIcon("images/switch_button_inv_3.png");
                        break;
                    case 4:
                        img = new ImageIcon("images/switch_button_inv_4.png");
                        break;
                }
            }

            return LevelCreatorGui.getScaledImg(LevelCreatorGui.getRotatedImg(img, rotation_id),width,height);
        }

        @Override
        public String encode() {
            return super.encode()+ rotation_id +""+lineCode+""+(inverted?'1':'0');
        }
        @Override
        public GameObject copy(){
            GameObject copy = new SwitchButton(this.id);
            copy.setRotation_id(rotation_id);
            copy.setPower(this.power);
            copy.setLineCode(this.lineCode);
            copy.setInverted(this.inverted);
            return copy;
        }
    }
    public static class PowerBox extends GameObject {

        PowerBox(char id) {
            super(id);
            this.haveLineCode = true;
            this.maxPower = 4;
            this.minPower = 1;
            labelFG.setVisible(true);
            setPower(1);
            setLineCode(0);
            setRotation_id(rotation_id);
        }
        public int getLineCode() {
            return lineCode;
        }
        public ImageIcon getBGImage(){
            ImageIcon img = null;
            switch (lineCode){
                case 0:
                    img = new ImageIcon("images/power_box_0.png");
                    break;
                case 1:
                    img = new ImageIcon("images/power_box_1.png");
                    break;
                case 2:
                    img = new ImageIcon("images/power_box_2.png");
                    break;
                case 3:
                    img = new ImageIcon("images/power_box_3.png");
                    break;
                case 4:
                    img = new ImageIcon("images/power_box_4.png");
                    break;
            }

            return LevelCreatorGui.getScaledImg(LevelCreatorGui.getRotatedImg(img, rotation_id), width, height);
        }
        public ImageIcon getFGImage(){
            ImageIcon img = null;

            switch (power){
                case 0:
                case 1:
                    img = null;
                    break;
                case 2:
                    img = new ImageIcon("images/power_box_indicator_2.png");
                    break;
                case 3:
                    img = new ImageIcon("images/power_box_indicator_3.png");
                    break;
                case 4:
                    img = new ImageIcon("images/power_box_indicator_4.png");
                    break;
            }
            if (img == null){
                return null;
            }
            return LevelCreatorGui.getScaledImg(LevelCreatorGui.getRotatedImg(img, rotation_id), width, height);
        }
        @Override
        public String encode() {
            return super.encode()+power+""+lineCode;

        }
        @Override
        public GameObject copy(){
            GameObject copy = new PowerBox(this.id);
            copy.setRotation_id(rotation_id);
            copy.setPower(this.power);
            copy.setLineCode(this.lineCode);
            copy.setInverted(this.inverted);
            return copy;
        }
    }
    public static class ActivationWall extends GameObject {

        ActivationWall(char id) {
            super(id);
            this.haveLineCode = true;
            this.canInvert = true;
            setLineCode(0);
        }
        public int getLineCode() {
            return lineCode;
        }
        public ImageIcon getBGImage(){
            ImageIcon img = null;
            if(!inverted){
                switch (lineCode){
                    case 0:
                        img = new ImageIcon("images/activation_wall_0.png");
                        break;
                    case 1:
                        img = new ImageIcon("images/activation_wall_1.png");
                        break;
                    case 2:
                        img = new ImageIcon("images/activation_wall_2.png");
                        break;
                    case 3:
                        img = new ImageIcon("images/activation_wall_3.png");
                        break;
                    case 4:
                        img = new ImageIcon("images/activation_wall_4.png");
                        break;
                }
            }else{
                switch (lineCode){
                    case 0:
                        img = new ImageIcon("images/activation_wall_0_active.png");
                        break;
                    case 1:
                        img = new ImageIcon("images/activation_wall_1_active.png");
                        break;
                    case 2:
                        img = new ImageIcon("images/activation_wall_2_active.png");
                        break;
                    case 3:
                        img = new ImageIcon("images/activation_wall_3_active.png");
                        break;
                    case 4:
                        img = new ImageIcon("images/activation_wall_4_active.png");
                        break;
                }
            }


            return LevelCreatorGui.getScaledImg(LevelCreatorGui.getRotatedImg(img, rotation_id), width, height);
        }


        @Override
        public String encode() {
            return super.encode()+lineCode+""+(inverted?'1':'0');
        }
        @Override
        public GameObject copy(){
            GameObject copy = new ActivationWall(this.id);
            copy.setRotation_id(rotation_id);
            copy.setPower(this.power);
            copy.setLineCode(this.lineCode);
            copy.setInverted(this.inverted);
            return copy;
        }
    }
    public static class Portal extends GameObject {

        Portal(char id) {
            super(id);
            this.haveLineCode = true;
            this.maxPower = 7;
            this.minPower = 0;
            setLineCode(0);
        }
        public int getLineCode() {
            return lineCode;
        }
        public ImageIcon getBGImage(){
            ImageIcon img = null;
            switch (lineCode){
                case 0:
                    img = new ImageIcon("images/portal_red.png");
                    break;
                case 1:
                    img = new ImageIcon("images/portal_blue.png");
                    break;
                case 2:
                    img = new ImageIcon("images/portal_green.png");
                    break;
                case 3:
                    img = new ImageIcon("images/portal_yellow.png");
                    break;
                case 4:
                    img = new ImageIcon("images/portal_purple.png");
                    break;
            }


            return LevelCreatorGui.getScaledImg(LevelCreatorGui.getRotatedImg(img, rotation_id), width, height);
        }
        public ImageIcon getFGImage(){
            ImageIcon img = null;
            if(power != 0){
                switch (lineCode){
                    case 0:
                        img = new ImageIcon("images/portal_red_uses_"+this.power+".png");
                        break;
                    case 1:
                        img = new ImageIcon("images/portal_blue_uses_"+this.power+".png");
                        break;
                    case 2:
                        img = new ImageIcon("images/portal_green_uses_"+this.power+".png");
                        break;
                    case 3:
                        img = new ImageIcon("images/portal_yellow_uses_"+this.power+".png");
                        break;
                    case 4:
                        img = new ImageIcon("images/portal_purple_uses_"+this.power+".png");
                        break;
                }
            }

            if(img != null){
                return LevelCreatorGui.getScaledImg(LevelCreatorGui.getRotatedImg(img, rotation_id), width, height);
            }
            return null;
        }


        @Override
        public String encode() {
            return super.encode()+lineCode+power;
        }
        @Override
        public GameObject copy(){
            GameObject copy = new Portal(this.id);
            copy.setRotation_id(rotation_id);
            copy.setPower(this.power);
            copy.setLineCode(this.lineCode);
            copy.setInverted(this.inverted);
            return copy;
        }
    }
    public static class FakeWall extends GameObject {
        FakeWall(char id) {
            super(id);
            maxPower = 9;
        }

        @Override
        public ImageIcon getBGImage() {
            return LevelCreatorGui.changeIconAlpha(super.getBGImage(),100);
        }

        @Override
        public String encode() {
            System.out.println("encode special");
            return (super.encode()+power);
        }
        @Override
        public GameObject copy(){
            GameObject copy = new FakeWall(this.id);
            copy.setRotation_id(rotation_id);
            copy.setPower(this.power);
            return copy;
        }
    }
    public static class Spike extends GameObject {

        Spike(char id) {
            super(id);
            allowRotation = true;

        }


        @Override
        public String encode() {
            return super.encode()+rotation_id;
        }

        @Override
        public GameObject copy(){
            GameObject copy = new Spike(this.id);
            copy.setRotation_id(rotation_id);
            copy.setPower(this.power);
            copy.setLineCode(this.lineCode);
            copy.setInverted(this.inverted);
            return copy;
        }

    }








    char id;
    boolean allowRotation;
    int rotation_id;
    int width, height;
    JLabel labelBG, labelFG;
    Container c;


    protected int lineCode, power;
    protected boolean inverted;
    public boolean haveLineCode,canInvert;
    public int maxPower,minPower;

    //constructor
    GameObject(char id, int width, int height){
        this.id = id;
        this.allowRotation = false;
        this.width = width;
        this.height = height;
        haveLineCode = false;
        canInvert = false;
        maxPower = -1;
        minPower = 0;
        labelBG = new JLabel();
        labelBG.setFocusable(false);
        labelBG.setIcon(LevelCreatorGui.getImg(id));
        labelFG = new JLabel();
        labelFG.setFocusable(false);
        labelFG.setVisible(false);

        setRotation_id(0);
    }
    GameObject(char id){
        this(id, LevelCreatorGui.LEVEL_GREED_SCALE, LevelCreatorGui.LEVEL_GREED_SCALE);

    }


    //other method

    public void setId(char id) {
        this.id = id;
        labelBG.setIcon(getBGImage());
    }
    public void addLabels(Container c){
        this.c = c;
        c.add(labelFG);
        c.add(labelBG);

    }
    public void removeLabels(){
        c.remove(labelBG);
        c.remove(labelFG);

    }
    public void setBounds(int x, int y, int width, int height){
        this.width = width;
        this.height = height;
        labelBG.setBounds(x,y,width,height);
        labelFG.setBounds(x, y, width, height);
        setRotation_id(rotation_id);

    }
    public void setBounds(Rectangle r){
        this.width = r.width;
        this.height = r.height;
        labelBG.setBounds(r);
        labelFG.setBounds(r);
        setRotation_id(rotation_id);

    }
    public void setVisible(boolean flag){
        labelBG.setVisible(flag);
        if(labelFG != null && getFGImage() != null){
            labelFG.setVisible(flag);
        }

    }
    public void setRotation_id(int rotation) {
        if(allowRotation){
            this.rotation_id = rotation;
        }
        labelBG.setIcon(getBGImage());
        if(labelFG != null && getFGImage() != null){
            labelFG.setIcon(getFGImage());
        }

    }
    public int getRotation_id() {
        return rotation_id;
    }
    public ImageIcon getBGImage(){
        return LevelCreatorGui.getScaledImg(LevelCreatorGui.getRotatedImg(LevelCreatorGui.getImg(id), rotation_id), width, height);

    }
    public void updateImage(){
        labelBG.setIcon(getBGImage());
        ImageIcon fg = getFGImage();
        if(fg == null){
            labelFG.setVisible(false);
        }else{
            labelFG.setVisible(true);
            labelFG.setIcon(fg);
        }

    }
    public ImageIcon getFGImage(){
        return null;
    }
    public String encode(){
        return id+"1";

    }
    public Rectangle getBounds(){
        return labelBG.getBounds();

    }
    public void setLineCode(int lineCode) {
        this.lineCode = lineCode;
        updateImage();
    }
    public int getLineCode() {
        return lineCode;
    }

    public int getPower() {
        return power;
    }
    public void setPower(int power) {
        this.power = power;
        updateImage();

    }
    public void setInverted(boolean inverted) {
        this.inverted = inverted;
        updateImage();
    }
    public boolean isInverted() {
        return inverted;
    }

    public GameObject copy(){
        GameObject copy = new GameObject(this.id);
        copy.setRotation_id(rotation_id);
        copy.setPower(this.power);
        copy.setLineCode(this.lineCode);
        copy.setInverted(this.inverted);
        return copy;
    }


}
