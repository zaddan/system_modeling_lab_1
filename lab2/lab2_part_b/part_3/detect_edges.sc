#include "susan.sh"
//import "c_uchar7220_queue";
import "setup_brightness_lut";
import "susan_edges";
import "c_uchar7220_queue_both_SR";
behavior DetectEdges(i_uchar7220_receiver in_image,  i_int7220_sender out_r, i_uchar7220_sender out_mid, i_uchar7220_sender out_image, OSAPI myOS)
{

    uchar bp[516];
        
    SetupBrightnessLut setup_brightness_lut(bp, myOS);
    SusanEdges susan_edges(in_image, out_r, out_mid, bp, out_image, myOS);
    
    void main(void) {
        //printf("________________");      
        setup_brightness_lut.main(); 
        //waitfor(2700); 
        //printf("in detect edges\n"); 
        susan_edges.main();
        //printf("in detect edges\n"); 
        //myOS.task_terminate();
    }

};

behavior Edges(i_uchar7220_receiver in_image,  i_int7220_sender out_r, i_uchar7220_sender out_mid, i_uchar7220_sender out_image, OSAPI myOS)
{


    DetectEdges detect_edges(in_image,  out_r, out_mid, out_image, myOS);
    
    void main(void) {
        TASK task_id = 0;
        myOS.task_activate(task_id);
        fsm{
            detect_edges: {goto detect_edges;}
        }
    }
};

