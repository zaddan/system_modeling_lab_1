#include "susan.sh"
import "OS.sc";
behavior SetupBrightnessLutThread(uchar bp[516], in int thID, OSAPI myOS)
{
       
    void main(void) {
        int taskID;
        int k;
        float temp;
        int thresh, form;
        
        taskID = thID;
        thresh = BT;
        form = 6;

        myOS.task_activate(taskID);
        printf("insided main is %d\n", thID); 
        //for(k=-256;k<257;k++)
       for(k=(-256)+512/PROCESSORS*thID; k<(-256)+512/PROCESSORS*thID+512/PROCESSORS+1; k++){
            temp=((float)k)/((float)thresh);
            temp=temp*temp;
            if (form==6)
                temp=temp*temp*temp;
            temp=100.0*exp(-temp);
            bp[(k+258)] = (uchar) temp;
            myOS.time_wait(2700);
        }
        myOS.task_terminate();
    }
};
 
behavior SetupBrightnessLut(uchar bp[516], OSAPI myOS)
{
       
    SetupBrightnessLutThread setup_brightness_thread_0(bp, 0, myOS);
    SetupBrightnessLutThread setup_brightness_thread_1(bp, 1, myOS);
    void main(void) {
        TASK master;
        master = 3; 
        myOS.start(master);
        master = myOS.par_start();
        par { 
            setup_brightness_thread_0;
            setup_brightness_thread_1;
        } 
        myOS.par_end(master); 
    }

};

