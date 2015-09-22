#include <stdio.h>
#include <stdlib.h>
#include "sim.sh"
#include <string.h>
#include "constant.hh"

import "c_queue";
import "susanedges";
import "setupbrightness";


// IN = "in" array
// OUT = "r" and "mid" array
behavior detectedges(i_receiver in_port, i_receiver Trigger, i_sender out_port)
{
    const unsigned long len = BP_CONST;	
    c_queue local_port(len); //not sure why you did this
    setup_brightness_lut setbrightness(local_port); //outport - local_port
    
    susan_edges sedge(in_port,local_port,out_port);//find how are they connected
    void main(void)
    {
        int flag;  
        Trigger.receive(&flag, 1);
        setbrightness.main();
        sedge.main();
       
   }
};



