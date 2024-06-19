package org.firstinspires.ftc.teamcode;

// reads a button
// read -- reads the state of the button
// onPress -- runs only once when the button first held
// onRelease -- runs only once when the button is released
// onHold -- runs so long as the button so is held


public class Button {
    public boolean oldState;
    public boolean curState;
    public void read(boolean state) {
        this.oldState = curState;
        this.curState = state;


    }

    public boolean onPress() {
        if(oldState == false && curState == true) {
            return true;
        }
        return false;
    }

    public boolean onRelease() {
        if(oldState == true && curState == false) {
            return true;
        }
        return false;
    }

    public boolean onHold() {
        if(oldState == true && curState == true) {
            return true;
        }
        return false;
    }
}
