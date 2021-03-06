#include "susan.sh"

import "c_uchar7220_queue";
import "c_int7220_queue";
import "setup_brightness_lut";
import "susan_edges";

behavior DetectEdges(i_uchar7220_receiver in_image,  i_int7220_sender out_r, i_uchar7220_sender out_mid, i_uchar7220_sender out_image)
{

    uchar bp[516];
    OS myOS;
        
    SetupBrightnessLut setup_brightness_lut(bp, myOS);
    SusanEdges susan_edges(in_image, out_r, out_mid, bp, out_image, myOS);
    
    void main(void) {
        //printf("________________");      
        setup_brightness_lut.main(); 
        susan_edges.main();
        //printf("in detect edges\n"); 
    }

};

behavior Edges(i_uchar7220_receiver in_image,  i_int7220_sender out_r, i_uchar7220_sender out_mid, i_uchar7220_sender out_image)
{

    DetectEdges detect_edges(in_image,  out_r, out_mid, out_image);
    
    void main(void) {
        fsm{
            detect_edges: {goto detect_edges;}
        }
    }
};

