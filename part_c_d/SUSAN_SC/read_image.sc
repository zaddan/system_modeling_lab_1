import "c_queue"; 
#include "constant.hh"
import "c_double_handshake";
#include <stdio.h>
import "wait_behav";

behavior readImage(i_receiver stimulusStart, i_receiver imageIn, i_sender Trigger, i_sender imageReadOut1, i_sender imageReadOut2) {
    int x_size = X_SIZE_CONST;
    int y_size = Y_SIZE_CONST;

    void main(void)
    { 
        const unsigned long imageSize = IMAGE_SIZE; 
        int start = 1; 
        int flag; 
        unsigned char image[imageSize]; 
        int terminateCounter; 
        //wait for stimulus start  
        stimulusStart.receive(&flag, 1);
        imageIn.receive(image, imageSize);
        //for(terminateCounter = TERMINATE_COUNTER_MAX; terminateCounter > 0; terminateCounter -= TERMINATE_CONTER_STEP) { 
            //Trigger.send((void *)&start, 1);
            imageReadOut1.send(image, imageSize);
            imageReadOut2.send(image, imageSize);
        
        //} 
    }    
};


behavior read_image_fsm(i_receiver imageIn, i_receiver stimulusStart, i_sender Trigger, i_sender imageReadOut1, i_sender imageReadOut2) {
    readImage myReadImage(stimulusStart, imageIn, Trigger, imageReadOut1, imageReadOut2);
    void main(void) {
        fsm {   
            myReadImage: { 
                    goto myReadImage;
                          }
            }
    }
};


