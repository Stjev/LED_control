package com.stef.arduino.led_control.MVC.Model;

public class StatusModel extends AbstractModel {

    private Boolean status;

    public StatusModel() {
        this(true);
    }

    public StatusModel(Boolean status){
        this.status = status;
    }

    /**
     * Model
     */

    public Boolean getStatus() {
        return this.status;
    }

    /**
     * This will set the status and fire the invalidationEvent to every invalidationListener that
     * needs this
     * @param status the new status
     */
    public void setStatus(Boolean status){
        this.status = status;
        fireInvalidationEvent();
    }
}
