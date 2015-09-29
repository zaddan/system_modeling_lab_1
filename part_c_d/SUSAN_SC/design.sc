#include <stdio.h>
#include <stdlib.h>
#include "sim.sh"
#include <string.h>
#include "constant.hh"
import "read_image";
import "c_double_handshake";
import "c_queue";
import "susanedges";
import "detect_edges";
import "susan";
import "write_image";

// IN = "in" array
// OUT = "r" and "mid" array
behavior design(i_receiver stimulusStart, i_receiver imageIn, i_sender finalOutput) 
{
    //channels 
    const unsigned long Image_SIZE =  IMAGE_SIZE*4;
    c_double_handshake Trigger;
    c_queue imageReadOut1(Image_SIZE); 
    c_queue imageReadOut2(Image_SIZE); 
    c_queue imageSusanOut(Image_SIZE); 
    c_double_handshake susanOutHandShake;

    //behaviour instantiation 
    read_image_fsm myReadImage(imageIn, stimulusStart, Trigger, imageReadOut1, imageReadOut2);
    susan mySusan(Trigger, imageReadOut1, imageReadOut2, imageSusanOut, susanOutHandShake);
    writeImage_fsm myWriteImage(susanOutHandShake, imageSusanOut, finalOutput);
    void main(void)
    {
        par{ 
            myReadImage.main();
            mySusan.main();
            myWriteImage.main();
        } 
   }
};



