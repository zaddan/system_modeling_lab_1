//import "c_queue"; 
import "c_image_queue";
#include "constant.hh"
import "c_double_handshake";
#include <stdio.h>

behavior readImage(i_image_receiver imageIn, i_receiver stimulusStart, i_sender Trigger, i_image_sender imageReadOut1, i_image_sender imageReadOut2) {
    int x_size = X_SIZE_CONST;
    int y_size = Y_SIZE_CONST;
    
    void main(void)
    { 
    const unsigned long imageSize = IMAGE_SIZE; 
    int start = 1; 
    int flag; 
    unsigned char image[imageSize]; 
    

    //wait for stimulus start  
    stimulusStart.receive(&flag, 1);
    imageIn.receive(&image);
    Trigger.send((void *)&start, 1);
    imageReadOut1.send(image);
    imageReadOut2.send(image);
 }    
};
