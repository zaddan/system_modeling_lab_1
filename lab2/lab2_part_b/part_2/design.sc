#include "susan.sh"

import "detect_edges";
import "susan";
import "read_image";
import "write_image";
import "master_driver";
import "c_uchar7220_queue_both_SR";
//import "c_uchar7220_queue_R";
//import "c_uchar7220_queue_S";
import "slave_driver";
import "master_driver";

behavior Design(i_receive start, in uchar image_buffer[IMAGE_SIZE], i_sender out_image_susan)
{
    HardwareBus myHardwareBus;
    susan_driver_wrapper mySusan_driver_wrapper(myHardwareBus);
    readImage_driver_wrapper myRead_image_driver(myHardwareBus, image_buffer, start);
    writeImage_driver_wrapper myWrite_image_driver(myHardwareBus, out_image_susan);
    void main(void) {
       par {
            myRead_image_driver.main();
            mySusan_driver_wrapper.main();
            myWrite_image_driver.main();
        }
    }
    
};
