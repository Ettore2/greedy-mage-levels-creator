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
        public ImageIcon getImage(){
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
        public ImageIcon getImage(){
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
        JLabel labelBackgroud;

        PowerBox(char id) {
            super(id);
            labelBackgroud = new JLabel();
            this.haveLineCode = true;
            this.havePower = true;
            setPower(1);
            setLineCode(0);
            setRotation_id(rotation_id);
        }

        public void setLineCode(int lineCode) {
            this.lineCode = lineCode;
            labelBackgroud.setIcon(getBgImage());
        }
        public int getLineCode() {
            return lineCode;
        }
        public ImageIcon getImage(){
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
        public void updateImage() {
            super.updateImage();
            ImageIcon img = getBgImage();

            if(img == null){
                labelBackgroud.setVisible(false);
            }else{
                labelBackgroud.setVisible(true);
                labelBackgroud.setIcon(img);
            }
        }

        public ImageIcon getBgImage(){
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

        @Override
        public String encode() {
            return super.encode()+power+""+lineCode;

        }
        @Override
        public void setRotation_id(int rotation) {
            super.setRotation_id(rotation);
            if(labelBackgroud != null && getBgImage() != null){
                labelBackgroud.setIcon(getBgImage());
            }


        }
        @Override
        public void setVisible(boolean flag) {
            super.setVisible(flag);
            labelBackgroud.setVisible(flag);

        }
        @Override
        public void removeLabels() {
            super.removeLabels();
            c.remove(labelBackgroud);
        }
        @Override
        public void addLabels(Container c) {
            super.addLabels(c);
            c.add(labelBackgroud);
        }
        @Override
        public void setBounds(int x, int y, int width, int height) {
            super.setBounds(x, y, width, height);
            if(labelBackgroud != null){
                labelBackgroud.setBounds(x, y, width, height);
            }
            setRotation_id(rotation_id);
        }

        @Override
        public void setBounds(Rectangle r) {
            super.setBounds(r);
            labelBackgroud.setBounds(r);
            setRotation_id(rotation_id);

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


        public void setLineCode(int lineCode) {
            this.lineCode = lineCode;
            label.setIcon(getImage());
        }
        public int getLineCode() {
            return lineCode;
        }
        public ImageIcon getImage(){
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
    JLabel label;
    Container c;


    protected int lineCode, power;
    protected boolean inverted;
    public boolean haveLineCode,canInvert,havePower;

    //constructor
    GameObject(char id, int width, int height){
        this.id = id;
        this.allowRotation = false;
        this.width = width;
        this.height = height;
        haveLineCode = false;
        canInvert = false;
        havePower = false;
        label = new JLabel();
        label.setFocusable(false);
        label.setIcon(LevelCreatorGui.getImg(id));

        setRotation_id(0);
    }
    GameObject(char id){
        this(id, LevelCreatorGui.LEVEL_GREED_SCALE, LevelCreatorGui.LEVEL_GREED_SCALE);

    }


    //other method

    public void setId(char id) {
        this.id = id;
        label.setIcon(getImage());
    }
    public void addLabels(Container c){
        this.c = c;
        c.add(label);

    }
    public void removeLabels(){
        c.remove(label);

    }
    public void setBounds(int x, int y, int width, int height){
        this.width = width;
        this.height = height;
        label.setBounds(x,y,width,height);
        setRotation_id(rotation_id);

    }
    public void setBounds(Rectangle r){
        this.width = r.width;
        this.height = r.height;
        label.setBounds(r);
        setRotation_id(rotation_id);

    }
    public void setVisible(boolean flag){
        label.setVisible(flag);

    }
    public void setRotation_id(int rotation) {
        if(allowRotation){
            this.rotation_id = rotation;
        }
        label.setIcon(getImage());

    }
    public int getRotation_id() {
        return rotation_id;
    }
    public ImageIcon getImage(){
        return LevelCreatorGui.getScaledImg(LevelCreatorGui.getRotatedImg(LevelCreatorGui.getImg(id), rotation_id), width, height);

    }
    public void updateImage(){
        label.setIcon(getImage());

    }
    public String encode(){
        return id+"1";

    }
    public Rectangle getBounds(){
        return label.getBounds();

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
