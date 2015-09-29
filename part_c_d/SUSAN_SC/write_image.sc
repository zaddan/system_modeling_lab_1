import "c_queue"; 
#include "constant.hh"
import "c_double_handshake";
#include <stdio.h>
import "wait_behav";

behavior writeImage(i_receiver imageSusanOut, i_sender finalOutput) {
    int x_size = X_SIZE_CONST;
    int y_size = Y_SIZE_CONST;
    const unsigned long imageSize = IMAGE_SIZE; 
    void main(void)
    { 
        
        unsigned char image[imageSize]; 
        int terminateCounter;
        //for(terminateCounter = TERMINATE_COUNTER_MAX; terminateCounter > 0; terminateCounter = terminateCounter-TERMINATE_CONTER_STEP) { 
            
        imageSusanOut.receive(image, imageSize);
            finalOutput.send(image, imageSize);
        
        //} 
    }    
};


behavior writeImage_fsm(i_receiver writeFlag, i_receiver imageSusanOut, i_sender finalOutput) 
{
    writeImage myWriteImage(imageSusanOut, finalOutput);
    void main(void) {
        fsm {   
            myWriteImage: { 
                    goto myWriteImage;
                            }
            }
    }
};


