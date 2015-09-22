#include <stdio.h>
#include <stdlib.h>
#include "sim.sh"
#include <string.h>

#import "c_queue"
#import "susanedges"
#import "setupbrightness"

#define PORTSIZE 10

// IN = "in" array
// OUT = "r" and "mid" array
behavior detectedges(i_receiver in_port, i_sender out_port)
{
    c_queue local_port(PORTSIZE);
    setup_brightness_lut setbrightness(local_port); //outport - local_port
    susan_edges sedge(in_port,local_port,out_port);//find how are they connected
    void main(void)
    {
        setbrightness.main();
        sedge.main();
    }

}



