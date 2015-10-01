import "c_double_handshake";
import "c_queue";
import "susan_thin";
import "stimulus";
import "edge_draw";
import "monitor";
import "detect_edges";
import "splitting";
import "gathering";
#include "constant.hh"


behavior susan(i_receiver Trigger, i_receiver imageReadOut1, i_receiver imageReadOut2,  i_sender imageSusanOut, i_sender susanOutHandShake)
{
    //constants 
    const unsigned long  trigger_size = 1;
    const unsigned long  img_size = IMAGE_SIZE;
    const unsigned long  img_size4 = 4*IMAGE_SIZE;
    const unsigned long half_img_size = IMAGE_SIZE/2; 
    
    //internal channels 
    const unsigned long splitSizeValue = splitSize ;
    c_queue detectEdgeOutputR(img_size4);
    c_queue detectEdgeOutputMid (img_size);
    c_queue susanThinOutput(img_size);
    c_queue half_imageReadOut2_1(splitSizeValue);
    c_queue half_imageReadOut2_2(splitSizeValue);
    c_splitImage_queue imageSusanOut1(splitSizeValue);
    c_splitImage_queue imageSusanOut2(splitSizeValue);
    c_queue susanThinOutput_1(splitSizeValue); 
    c_queue susanThinOutput_2(splitSizeValue);
    
    
    signal int flag; 
    //behavior instantiation 
    detectedges mydetectEdges(imageReadOut1, detectEdgeOutputR, detectEdgeOutputMid);
    susan_thin_fsm mySusanThin(flag, detectEdgeOutputR, detectEdgeOutputMid,susanThinOutput); 
    
    
    splitting_fsm mySplitting(imageReadOut2, half_imageReadOut2_1, half_imageReadOut2_2, susanThinOutput, susanThinOutput_1, susanThinOutput_2);
    //edge_draw_fsm myEdgeDraw(susanThinOutput, imageReadOut2, imageSusanOut);
    edge_draw_fsm myEdgeDraw(susanThinOutput_1, susanThinOutput_2, half_imageReadOut2_1, half_imageReadOut2_2, imageSusanOut1, imageSusanOut2);
    gathering_fsm myGathering(imageSusanOut1, imageSusanOut2, imageSusanOut);
    
    int flagValue;
    void main(void) {
        int terminateCounter; 
        //for(terminateCounter = TERMINATE_COUNTER_MAX; terminateCounter > 0; terminateCounter -= TERMINATE_CONTER_STEP) { 
         //   Trigger.receive(&flagValue, 1);
            flag = flagValue; 
            par {
                mydetectEdges;
                mySusanThin;
                mySplitting; 
                myEdgeDraw;
                myGathering;
            }
        //} 
    }
};
