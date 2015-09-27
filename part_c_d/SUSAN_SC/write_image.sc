import "c_image_queue"; 
#include "constant.hh"
import "c_double_handshake";
#include <stdio.h>

behavior writeImage(i_image_receiver imageSusanOut, i_image_sender finalOutput) {
    int x_size = X_SIZE_CONST;
    int y_size = Y_SIZE_CONST;
    const unsigned long imageSize = IMAGE_SIZE; 
    void main(void)
    { 
    unsigned char image[imageSize]; 
    imageSusanOut.receive(&image);
    finalOutput.send(image);
 }    
};
