import "c_queue"; 
#include "constant.hh"
import "c_double_handshake";
#include <stdio.h>

behavior readImage(i_receiver imageIn, i_receiver stimulusStart, i_sender Trigger, i_sender imageReadOut1, i_sender imageReadOut2) {
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
    imageIn.receive(image, imageSize);
    Trigger.send((void *)&start, 1);
    imageReadOut1.send(image, imageSize);
    imageReadOut2.send(image, imageSize);
 }    
};
