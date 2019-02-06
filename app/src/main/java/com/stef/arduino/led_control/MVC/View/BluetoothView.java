package com.stef.arduino.led_control.MVC.View;

import com.stef.arduino.led_control.Interface.InvalidationListener;
import com.stef.arduino.led_control.Interface.Observable;
import com.stef.arduino.led_control.MVC.Model.StatusModel;

public class BluetoothView implements InvalidationListener {

    // Models
    private StatusModel statusModel;

    // Fields
    private boolean status;

    public void setStatusModel(StatusModel statusModel) {
        this.statusModel = statusModel;
        this.statusModel.addListener(this);
    }

    @Override
    public void invalidated(Observable observable) {
        // check if this is the model that changed
        if(! statusModel.getStatus().equals(this.status)){
            this.status = statusModel.getStatus();
            changeLedStatus();
        }
    }

    /**
     * This method will set the led status
     */
    private void changeLedStatus() {
        System.out.println("The current LED status is: " + this.status);
    }
}
