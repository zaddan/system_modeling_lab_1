#include <stdio.h>
#include <stdlib.h>
#include "sim.sh"
#include <string.h>
#include "constant.hh"

//import "c_queue";
import "c_image_queue";
import "susanedges";
import "setupbrightness";
import "c_BP_queue";

// IN = "in" array
// OUT = "r" and "mid" array
behavior detectedges(i_image_receiver in_port,i_intR_sender out_portR, i_image_sender out_portMid)
{
    const unsigned long len = BP_CONST;	
    c_BP_queue local_port(len); //not sure why you did this
    setup_brightness_lut setbrightness(local_port); //outport - local_port
    //printf("was here\n");
    susan_edges sedge(in_port,local_port,out_portR, out_portMid);//find how are they connected
    void main(void)
    {
        setbrightness.main();
        sedge.main();
   }
};



