import "c_queue"; 
#include "constant.hh"
import "c_double_handshake";
#include <stdio.h>

behavior writeImage(i_receiver imageSusanOut, i_sender finalOutput) {
    int x_size = X_SIZE_CONST;
    int y_size = Y_SIZE_CONST;
    const unsigned long imageSize = IMAGE_SIZE; 
    void main(void)
    { 
    unsigned char image[imageSize]; 
    imageSusanOut.receive(image, imageSize);
    finalOutput.send(image, imageSize);
 }    
};
