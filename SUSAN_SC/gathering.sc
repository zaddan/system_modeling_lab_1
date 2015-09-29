import "c_queue";
#include <string.h>
#include "constant.hh"
#include <stdio.h>
behavior gathering(i_receiver imageSusanOut1, i_receiver imageSusanOut2, i_sender imageSusanOut){
    unsigned char imageSusanOutArray1[splitSize];
    unsigned char imageSusanOutArray2[splitSize];
    unsigned char combinedArray[IMAGE_SIZE];
    
    int i; 
    void main(void) {
        
        imageSusanOut1.receive(imageSusanOutArray1, splitSize);
        imageSusanOut2.receive(imageSusanOutArray2, splitSize);
        
//        strcpy(combinedArray, imageSusanOutArray1);
//        strcat(combinedArray, imageSusanOutArray2);
//        
       for(i=0; i< IMAGE_SIZE/2; i++) {
          combinedArray[i] = imageSusanOutArray1[i]; 
          //combinedArray[i+IMAGE_SIZE/2] = imageSusanOutArray2[splitSize - IMAGE_SIZE/2 + i]; 
          combinedArray[i+IMAGE_SIZE/2] = imageSusanOutArray2[133 + i]; 
       }
        imageSusanOut.send(combinedArray, IMAGE_SIZE);
    }
};


behavior gathering_fsm(i_receiver imageSusanOut1, i_receiver imageSusanOut2, i_sender imageSusanOut)
{
    gathering myGathering(imageSusanOut1, imageSusanOut2, imageSusanOut);
    void main(void) {
        fsm {   
            myGathering: { 
                    goto myGathering;
                          }
            }
    }
};


